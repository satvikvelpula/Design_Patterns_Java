package Memento;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Clickable rectangle whose color cycles: red → blue → yellow → red.
 * Values 0, 1, 2 match {@link Model} option indices.
 */
public class ColorBox {
    private final int index;
    private final Controller controller;
    private final Rectangle rectangle;

    public ColorBox(int index, Controller controller) {
        this.index = index;
        this.controller = controller;
        this.rectangle = new Rectangle(100, 100);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);
        updateColor();
        rectangle.setOnMouseClicked(e -> {
            int next = (controller.getOption(index) + 1) % 3;
            controller.setOption(index, next);
        });
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void updateColor() {
        int option = controller.getOption(index);
        Color color = switch (option) {
            case 1 -> Color.BLUE;
            case 2 -> Color.YELLOW;
            default -> Color.RED;
        };
        rectangle.setFill(color);
    }
}
