package Flyweight;

import FactoryMethod.Map;
import FactoryMethod.Tile;


public class MapRenderer {
    private Map map;

    public MapRenderer(Map map) {
        this.map = map;
    }

    public void render() {
        System.out.println("Map dimensions: " + map.getWidth() + "x" + map.getHeight());
        System.out.println("Total unique tile graphics in use: " + TileGraphicFactory.getTotalGraphics());
        System.out.println("\nRendering map:");
        
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Tile tile = map.getGrid()[y][x];
                TileGraphic graphic = TileGraphicFactory.getTileGraphic(tile.getType());
                renderTile(graphic, x, y);
            }
            System.out.println();
        }
    }

    private void renderTile(TileGraphic graphic, int x, int y) {
        // Use the graphic's symbol for rendering
        System.out.print(graphic.getSymbol() + " ");
    }

    public int getTotalTiles() {
        return map.getWidth() * map.getHeight();
    }
}
