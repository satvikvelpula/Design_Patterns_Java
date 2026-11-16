package Flyweight;

import FactoryMethod.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Flyweight Pattern - RPG Map Renderer");

        // Create maps using Factory Method
        int mapWidth = 20;
        int mapHeight = 15;

        Map cityMap = FactoryMethod.Game.createMap("CityMap", mapWidth, mapHeight);
        Map wildernessMap = FactoryMethod.Game.createMap("WildernessMap", mapWidth, mapHeight);

        // Create renderers
        JavaFXMapRenderer cityRenderer = new JavaFXMapRenderer(cityMap);
        JavaFXMapRenderer wildernessRenderer = new JavaFXMapRenderer(wildernessMap);

        cityRenderer.render();
        wildernessRenderer.render();

        // Create UI
        VBox root = new VBox(10);
        root.getChildren().addAll(
            createLabel("City Map (Flyweight Pattern)"),
            cityRenderer.getCanvas(),
            createLabel("Wilderness Map (Flyweight Pattern)"),
            wildernessRenderer.getCanvas(),
            createLabel("Memory Optimization: Only " + TileGraphicFactory.getTotalGraphics() + 
                       " unique tile graphics for " + (mapWidth * mapHeight * 2) + " tiles!")
        );

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private javafx.scene.control.Label createLabel(String text) {
        javafx.scene.control.Label label = new javafx.scene.control.Label(text);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        return label;
    }
}
