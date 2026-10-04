import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class Main extends Application {
    private final TemperatureConverter converter = new TemperatureConverter();
    private final TemperatureUnitDAO unitDAO = new TemperatureUnitDAO();
    private final TempRecordDAO recordDAO = new TempRecordDAO();

    private ComboBox<TemperatureUnit> fromUnitBox;
    private ComboBox<TemperatureUnit> toUnitBox;
    private TextField inputField;
    private Label resultLabel;
    private TableView<TempRecord> historyTable;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Temperature Converter");

        inputField = new TextField();
        inputField.setPromptText("Value");

        fromUnitBox = new ComboBox<>();
        toUnitBox = new ComboBox<>();
        resultLabel = new Label("Result: —");

        Button convertButton = new Button("Convert");
        Button saveButton = new Button("Save input to DB");
        Button refreshButton = new Button("Refresh history");

        convertButton.setOnAction(e -> convert());
        saveButton.setOnAction(e -> saveInput());
        refreshButton.setOnAction(e -> refreshHistory());

        historyTable = buildHistoryTable();

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(new Label("Value"), 0, 0);
        form.add(inputField, 1, 0);
        form.add(new Label("From"), 0, 1);
        form.add(fromUnitBox, 1, 1);
        form.add(new Label("To"), 0, 2);
        form.add(toUnitBox, 1, 2);
        form.add(resultLabel, 1, 3);

        HBox actions = new HBox(10, convertButton, saveButton, refreshButton);

        VBox root = new VBox(16, form, actions, new Label("Recent conversions / records"), historyTable);
        root.setPadding(new Insets(16));

        stage.setScene(new Scene(root, 560, 480));
        stage.show();

        loadUnits();
        refreshHistory();
    }

    private TableView<TempRecord> buildHistoryTable() {
        TableView<TempRecord> table = new TableView<>();

        TableColumn<TempRecord, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<TempRecord, Double> valueCol = new TableColumn<>("Value");
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<TempRecord, String> unitCol = new TableColumn<>("Unit");
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unitCode"));

        TableColumn<TempRecord, Object> timeCol = new TableColumn<>("Recorded at");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("recordedAt"));

        table.getColumns().add(idCol);
        table.getColumns().add(valueCol);
        table.getColumns().add(unitCol);
        table.getColumns().add(timeCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        return table;
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
            showError("Could not load units from DB.\n" + ex.getMessage()
                    + "\n\nStart DB: docker compose up -d");
        }
    }

    private void convert() {
        try {
            double value = Double.parseDouble(inputField.getText().trim());
            TemperatureUnit from = fromUnitBox.getValue();
            TemperatureUnit to = toUnitBox.getValue();
            if (from == null || to == null) {
                showError("Select both units.");
                return;
            }
            double celsius = toCelsius(value, from.getCode());
            double converted = fromCelsius(celsius, to.getCode());
            resultLabel.setText(String.format("Result: %.2f %s", converted, to.getCode()));
        } catch (NumberFormatException ex) {
            showError("Enter a valid number.");
        }
    }

    private void saveInput() {
        try {
            double value = Double.parseDouble(inputField.getText().trim());
            TemperatureUnit from = fromUnitBox.getValue();
            if (from == null) {
                showError("Select a from-unit.");
                return;
            }
            recordDAO.insert(new TempRecord(value, from.getId()));
            refreshHistory();
            resultLabel.setText("Saved " + value + " " + from.getCode() + " to database.");
        } catch (NumberFormatException ex) {
            showError("Enter a valid number.");
        } catch (SQLException ex) {
            showError("Save failed: " + ex.getMessage());
        }
    }

    private void refreshHistory() {
        try {
            historyTable.setItems(FXCollections.observableArrayList(recordDAO.findRecent(20)));
        } catch (SQLException ex) {
            showError("Could not load history: " + ex.getMessage());
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

    public static void main(String[] args) {
        launch(args);
    }
}
