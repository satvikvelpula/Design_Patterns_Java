package Flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for managing and sharing TileGraphic instances.
 * Ensures that each unique tile type has only one graphic instance.
 */
public class TileGraphicFactory {
    private static final Map<String, TileGraphic> graphics = new HashMap<>();

    // Static initializer to create default tile graphics
    static {
        graphics.put("road", new TileGraphic("road", 'R', "#8B4513")); // Brown
        graphics.put("forest", new TileGraphic("forest", 'F', "#228B22")); // Forest green
        graphics.put("building", new TileGraphic("building", 'B', "#808080")); // Gray
        graphics.put("water", new TileGraphic("water", 'W', "#1E90FF")); // Blue
        graphics.put("swamp", new TileGraphic("swamp", 'S', "#556B2F")); // Dark olive green
    }

    public static TileGraphic getTileGraphic(String type) {
        TileGraphic graphic = graphics.get(type);
        if (graphic == null) {
            // Create a default graphic for unknown types
            graphic = new TileGraphic(type, '?', "#FFFFFF");
            graphics.put(type, graphic);
        }
        return graphic;
    }

    public static int getTotalGraphics() {
        return graphics.size();
    }

    public static void addTileGraphic(String type, char symbol, String color) {
        graphics.put(type, new TileGraphic(type, symbol, color));
    }
}
