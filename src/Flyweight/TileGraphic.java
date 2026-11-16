package Flyweight;

import java.awt.image.BufferedImage;


public class TileGraphic {
    private final String type;
    private final char symbol;
    private final String color;
    private BufferedImage image; // Would be loaded from file in a real implementation

    public TileGraphic(String type, char symbol, String color) {
        this.type = type;
        this.symbol = symbol;
        this.color = color;
        // In a real implementation, we would load the actual image here
        this.image = null;
    }

    public String getType() {
        return type;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getColor() {
        return color;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void render(int x, int y) {
        System.out.printf("Rendering %s tile at (%d, %d)%n", type, x, y);
    }
}
