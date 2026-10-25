package Memento;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * View: three color boxes, a checkbox, Ctrl+Z / Ctrl+Y, and a history window.
 */
public class Gui extends Application {
    private Controller controller;
    private ColorBox colorBox1;
    private ColorBox colorBox2;
    private ColorBox colorBox3;
    private CheckBox checkBox;
    private ListView<IMemento> historyListView;
    private boolean ignoreHistorySelection;

    @Override
    public void start(Stage stage) {
        Model model = new Model();
        model.setGui(this);
        controller = new Controller(model);
        controller.setGui(this);

        stage.setTitle("Memento — color boxes (Ctrl+Z undo, Ctrl+Y redo)");

        colorBox1 = new ColorBox(0, controller);
        colorBox2 = new ColorBox(1, controller);
        colorBox3 = new ColorBox(2, controller);

        checkBox = new CheckBox("CheckBox");
        checkBox.setSelected(controller.getIsSelected());
        checkBox.setOnAction(e -> controller.setIsSelected(checkBox.isSelected()));

        HBox boxes = new HBox(10,
                colorBox1.getRectangle(),
                colorBox2.getRectangle(),
                colorBox3.getRectangle()
        );
        VBox root = new VBox(12,
                new Label("Click boxes to cycle colors. Ctrl+Z = Undo, Ctrl+Y = Redo."),
                boxes,
                checkBox
        );
        root.setPadding(new Insets(16));

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(e -> {
            if (e.isControlDown() && e.getCode() == KeyCode.Z) {
                controller.undo();
            } else if (e.isControlDown() && e.getCode() == KeyCode.Y) {
                controller.redo();
            }
        });

        stage.setScene(scene);
        stage.show();
        openHistoryWindow();
    }

    public void updateGui() {
        colorBox1.updateColor();
        colorBox2.updateColor();
        colorBox3.updateColor();
        checkBox.setSelected(controller.getIsSelected());
    }

    public void refreshHistoryView() {
        if (historyListView == null) {
            return;
        }
        ignoreHistorySelection = true;
        historyListView.setItems(FXCollections.observableArrayList(controller.getUndoHistoryView()));
        historyListView.getSelectionModel().clearSelection();
        ignoreHistorySelection = false;
    }

    private void openHistoryWindow() {
        historyListView = new ListView<>();
        historyListView.setPrefSize(380, 300);
        historyListView.setPlaceholder(new Label("No saved states yet — change a box or the checkbox."));
        historyListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (!ignoreHistorySelection && selected != null) {
                controller.restoreFromHistory(selected);
            }
        });

        VBox historyRoot = new VBox(8,
                new Label("History (click a state to restore)"),
                historyListView
        );
        historyRoot.setPadding(new Insets(12));

        Stage historyStage = new Stage();
        historyStage.setTitle("State history");
        historyStage.setScene(new Scene(historyRoot));
        historyStage.setX(80);
        historyStage.setY(120);
        historyStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
