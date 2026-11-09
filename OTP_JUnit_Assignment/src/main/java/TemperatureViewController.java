import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TemperatureViewController {
    private final TemperatureConverter converter = new TemperatureConverter();
    private final UnitDAO unitDAO = new UnitDAO();
    private final ConversionRecordDAO conversionDAO = new ConversionRecordDAO();

    private Main app;
    private ResourceBundle bundle;
    private Locale currentLocale = Locale.US;
    private String dbLang = LocaleCodes.DEFAULT;
    private boolean updatingLanguage;

    @FXML private VBox root;
    @FXML private ComboBox<Locale> languageBox;
    @FXML private TextField inputField;
    @FXML private ComboBox<Unit> fromUnitBox;
    @FXML private ComboBox<Unit> toUnitBox;
    @FXML private Label unitDescriptionLabel;
    @FXML private Label resultLabel;
    @FXML private TableView<ConversionRecord> historyTable;
    @FXML private TableColumn<ConversionRecord, Double> inputColumn;
    @FXML private TableColumn<ConversionRecord, String> fromColumn;
    @FXML private TableColumn<ConversionRecord, String> toColumn;
    @FXML private TableColumn<ConversionRecord, Double> resultColumn;
    @FXML private TableColumn<ConversionRecord, String> langColumn;
    @FXML private TableColumn<ConversionRecord, Object> timeColumn;

    public void setApp(Main app) {
        this.app = app;
    }

    public void init(ResourceBundle bundle, Locale currentLocale) {
        this.bundle = bundle;
        this.currentLocale = currentLocale;
        this.dbLang = LocaleCodes.fromLocale(currentLocale);
        setupLanguageBox(currentLocale);
        setupTable();
        setupUnitListeners();
        loadUnits();
        refreshHistory();
        root.setNodeOrientation("ar".equals(currentLocale.getLanguage())
                ? NodeOrientation.RIGHT_TO_LEFT
                : NodeOrientation.LEFT_TO_RIGHT);
    }

    private void setupLanguageBox(Locale currentLocale) {
        languageBox.setItems(FXCollections.observableArrayList(
                Locale.US,
                Locale.forLanguageTag("fi-FI"),
                Locale.forLanguageTag("sv-SE"),
                Locale.forLanguageTag("ja-JP"),
                Locale.forLanguageTag("ar-SA")
        ));
        languageBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Locale locale) {
                if (locale == null || bundle == null) {
                    return "";
                }
                return switch (locale.getLanguage()) {
                    case "fi" -> bundle.getString("lang.fi");
                    case "sv" -> bundle.getString("lang.sv");
                    case "ja" -> bundle.getString("lang.ja");
                    case "ar" -> bundle.getString("lang.ar");
                    default -> bundle.getString("lang.en");
                };
            }

            @Override
            public Locale fromString(String string) {
                return null;
            }
        });
        updatingLanguage = true;
        languageBox.getItems().stream()
                .filter(l -> l.getLanguage().equals(currentLocale.getLanguage()))
                .findFirst()
                .ifPresent(l -> languageBox.getSelectionModel().select(l));
        updatingLanguage = false;
        languageBox.setOnAction(e -> {
            if (updatingLanguage || app == null) {
                return;
            }
            Locale selected = languageBox.getSelectionModel().getSelectedItem();
            if (selected != null) {
                app.switchLanguage(selected);
            }
        });
    }

    private void setupTable() {
        inputColumn.setCellValueFactory(new PropertyValueFactory<>("inputValue"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("fromUnitName"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("toUnitName"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("resultValue"));
        langColumn.setCellValueFactory(new PropertyValueFactory<>("lang"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("recordedAt"));
        historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

    private void setupUnitListeners() {
        fromUnitBox.setOnAction(e -> updateDescription());
        toUnitBox.setOnAction(e -> updateDescription());
    }

    private void updateDescription() {
        Unit from = fromUnitBox.getValue();
        Unit to = toUnitBox.getValue();
        if (from == null || to == null) {
            unitDescriptionLabel.setText("");
            return;
        }
        unitDescriptionLabel.setText(from.getDescription() + " → " + to.getDescription());
    }

    @FXML
    private void onConvert() {
        try {
            double value = Double.parseDouble(inputField.getText().trim());
            Unit from = fromUnitBox.getValue();
            Unit to = toUnitBox.getValue();
            if (from == null || to == null) {
                showError(bundle.getString("error.units"));
                return;
            }
            double converted = convert(value, from.getCode(), to.getCode());
            resultLabel.setText(MessageFormat.format(
                    bundle.getString("result.format"),
                    String.format(Locale.ROOT, "%.2f", converted),
                    to.getName()
            ));
        } catch (NumberFormatException ex) {
            showError(bundle.getString("error.number"));
        }
    }

    @FXML
    private void onSave() {
        try {
            double value = Double.parseDouble(inputField.getText().trim());
            Unit from = fromUnitBox.getValue();
            Unit to = toUnitBox.getValue();
            if (from == null || to == null) {
                showError(bundle.getString("error.units"));
                return;
            }
            double converted = convert(value, from.getCode(), to.getCode());
            conversionDAO.insert(new ConversionRecord(
                    value, from.getId(), to.getId(), converted, dbLang
            ));
            resultLabel.setText(MessageFormat.format(
                    bundle.getString("result.saved"),
                    String.format(Locale.ROOT, "%.2f", converted),
                    to.getName()
            ));
            refreshHistory();
        } catch (NumberFormatException ex) {
            showError(bundle.getString("error.number"));
        } catch (SQLException ex) {
            showError(MessageFormat.format(bundle.getString("error.db.save"), ex.getMessage()));
        }
    }

    @FXML
    private void onRefresh() {
        refreshHistory();
    }

    private void loadUnits() {
        try {
            List<Unit> units = unitDAO.findTemperatureUnits(dbLang);
            fromUnitBox.setItems(FXCollections.observableArrayList(units));
            toUnitBox.setItems(FXCollections.observableArrayList(units));
            if (!units.isEmpty()) {
                fromUnitBox.getSelectionModel().selectFirst();
                toUnitBox.getSelectionModel().select(Math.min(1, units.size() - 1));
            }
            updateDescription();
        } catch (SQLException ex) {
            showError(MessageFormat.format(bundle.getString("error.db.units"), ex.getMessage()));
        }
    }

    private void refreshHistory() {
        try {
            historyTable.setItems(FXCollections.observableArrayList(
                    conversionDAO.findRecent(5, dbLang)
            ));
        } catch (SQLException ex) {
            showError(MessageFormat.format(bundle.getString("error.db.history"), ex.getMessage()));
        }
    }

    private double convert(double value, String fromCode, String toCode) {
        double celsius = switch (fromCode) {
            case "C" -> value;
            case "F" -> converter.fahrenheitToCelsius(value);
            case "K" -> converter.kelvinToCelsius(value);
            default -> throw new IllegalArgumentException("Unknown unit: " + fromCode);
        };
        return switch (toCode) {
            case "C" -> celsius;
            case "F" -> converter.celsiusToFahrenheit(celsius);
            case "K" -> celsius + 273.15;
            default -> throw new IllegalArgumentException("Unknown unit: " + toCode);
        };
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
