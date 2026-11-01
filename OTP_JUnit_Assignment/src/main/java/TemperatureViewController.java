import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private final TemperatureUnitDAO unitDAO = new TemperatureUnitDAO();
    private final TempRecordDAO recordDAO = new TempRecordDAO();

    private Main app;
    private ResourceBundle bundle;
    private boolean updatingLanguage;

    @FXML private VBox root;
    @FXML private Label titleLabel;
    @FXML private Label languageLabel;
    @FXML private Label valueLabel;
    @FXML private Label fromLabel;
    @FXML private Label toLabel;
    @FXML private Label resultLabel;
    @FXML private Label historyLabel;
    @FXML private ComboBox<Locale> languageBox;
    @FXML private TextField inputField;
    @FXML private ComboBox<TemperatureUnit> fromUnitBox;
    @FXML private ComboBox<TemperatureUnit> toUnitBox;
    @FXML private Button convertButton;
    @FXML private Button saveButton;
    @FXML private Button refreshButton;
    @FXML private TableView<TempRecord> historyTable;
    @FXML private TableColumn<TempRecord, Integer> idColumn;
    @FXML private TableColumn<TempRecord, Double> valueColumn;
    @FXML private TableColumn<TempRecord, String> unitColumn;
    @FXML private TableColumn<TempRecord, Object> timeColumn;

    public void setApp(Main app) {
        this.app = app;
    }

    public void init(ResourceBundle bundle, Locale currentLocale) {
        this.bundle = bundle;
        setupLanguageBox(currentLocale);
        setupTable();
        loadUnits();
        refreshHistory();
        if ("ar".equals(currentLocale.getLanguage())) {
            root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unitCode"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("recordedAt"));
        historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

    @FXML
    private void onConvert() {
        try {
            double value = Double.parseDouble(inputField.getText().trim());
            TemperatureUnit from = fromUnitBox.getValue();
            TemperatureUnit to = toUnitBox.getValue();
            if (from == null || to == null) {
                showError(bundle.getString("error.units"));
                return;
            }
            double celsius = toCelsius(value, from.getCode());
            double converted = fromCelsius(celsius, to.getCode());
            resultLabel.setText(MessageFormat.format(
                    bundle.getString("result.format"),
                    String.format(Locale.ROOT, "%.2f", converted),
                    to.getCode()
            ));
        } catch (NumberFormatException ex) {
            showError(bundle.getString("error.number"));
        }
    }

    @FXML
    private void onSave() {
        try {
            double value = Double.parseDouble(inputField.getText().trim());
            TemperatureUnit from = fromUnitBox.getValue();
            if (from == null) {
                showError(bundle.getString("error.fromUnit"));
                return;
            }
            recordDAO.insert(new TempRecord(value, from.getId()));
            refreshHistory();
            resultLabel.setText(MessageFormat.format(
                    bundle.getString("result.saved"),
                    String.format(Locale.ROOT, "%.2f", value),
                    from.getCode()
            ));
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
            List<TemperatureUnit> units = unitDAO.findAll();
            fromUnitBox.setItems(FXCollections.observableArrayList(units));
            toUnitBox.setItems(FXCollections.observableArrayList(units));
            if (!units.isEmpty()) {
                fromUnitBox.getSelectionModel().selectFirst();
                toUnitBox.getSelectionModel().select(Math.min(1, units.size() - 1));
            }
        } catch (SQLException ex) {
            showError(MessageFormat.format(bundle.getString("error.db.units"), ex.getMessage()));
        }
    }

    private void refreshHistory() {
        try {
            historyTable.setItems(FXCollections.observableArrayList(recordDAO.findRecent(20)));
        } catch (SQLException ex) {
            showError(MessageFormat.format(bundle.getString("error.db.history"), ex.getMessage()));
        }
    }

    private double toCelsius(double value, String code) {
        return switch (code) {
            case "C" -> value;
            case "F" -> converter.fahrenheitToCelsius(value);
            case "K" -> converter.kelvinToCelsius(value);
            default -> throw new IllegalArgumentException("Unknown unit: " + code);
        };
    }

    private double fromCelsius(double celsius, String code) {
        return switch (code) {
            case "C" -> celsius;
            case "F" -> converter.celsiusToFahrenheit(celsius);
            case "K" -> celsius + 273.15;
            default -> throw new IllegalArgumentException("Unknown unit: " + code);
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
