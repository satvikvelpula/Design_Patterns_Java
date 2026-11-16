package Flyweight;

import FactoryMethod.Map;
import FactoryMethod.Tile;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class JavaFXMapRenderer {
    private Map map;
    private Canvas canvas;
    private GraphicsContext gc;
    private int tileSize = 50; 

    public JavaFXMapRenderer(Map map) {
        this.map = map;
        this.canvas = new Canvas(map.getWidth() * tileSize, map.getHeight() * tileSize);
        this.gc = canvas.getGraphicsContext2D();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void render() {
        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw each tile
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Tile tile = map.getGrid()[y][x];
                TileGraphic graphic = TileGraphicFactory.getTileGraphic(tile.getType());
                drawTile(graphic, x, y);
            }
        }
    }

    private void drawTile(TileGraphic graphic, int x, int y) {
        int px = x * tileSize;
        int py = y * tileSize;

        // Parse color from hex string
        Color color = parseColor(graphic.getColor());
        gc.setFill(color);
        gc.fillRect(px, py, tileSize, tileSize);

        // Draw the symbol
        gc.setFill(Color.BLACK);
        gc.fillText(String.valueOf(graphic.getSymbol()), px + tileSize/2 - 5, py + tileSize/2 + 5);
    }

    private Color parseColor(String hexColor) {
        if (hexColor == null || hexColor.isEmpty()) {
            return Color.WHITE;
        }
        
        try {
            // Remove # if present
            String color = hexColor.startsWith("#") ? hexColor.substring(1) : hexColor;
            
            // Parse RGB values
            int r = Integer.parseInt(color.substring(0, 2), 16);
            int g = Integer.parseInt(color.substring(2, 4), 16);
            int b = Integer.parseInt(color.substring(4, 6), 16);
            
            return Color.rgb(r, g, b);
        } catch (Exception e) {
            return Color.WHITE;
        }
    }
}
