package Flyweight;

import FactoryMethod.Map;


public class FlyweightGame {
    public static void main(String[] args) {
        System.out.println("=== Flyweight Pattern for RPG Map Rendering ===\n");

        // Create maps using Factory Method
        int mapWidth = 10;
        int mapHeight = 5;

        System.out.println("--- Generating City Map ---");
        Map cityMap = FactoryMethod.Game.createMap("CityMap", mapWidth, mapHeight);
        MapRenderer cityRenderer = new MapRenderer(cityMap);
        cityRenderer.render();
        System.out.println("\nCity map: " + cityRenderer.getTotalTiles() + " tiles total");
        System.out.println("Unique graphics used: " + TileGraphicFactory.getTotalGraphics());

        System.out.println("\n--- Generating Wilderness Map ---");
        Map wildernessMap = FactoryMethod.Game.createMap("WildernessMap", mapWidth, mapHeight);
        MapRenderer wildernessRenderer = new MapRenderer(wildernessMap);
        wildernessRenderer.render();
        System.out.println("\nWilderness map: " + wildernessRenderer.getTotalTiles() + " tiles total");
        System.out.println("Unique graphics used: " + TileGraphicFactory.getTotalGraphics());

        // Demonstrate memory optimization
        System.out.println("\n=== Memory Optimization Demonstration ===");
        System.out.println("Total tiles across both maps: " + 
                          (cityRenderer.getTotalTiles() + wildernessRenderer.getTotalTiles()));
        System.out.println("Total unique tile graphics: " + TileGraphicFactory.getTotalGraphics());
        System.out.println("\nWithout Flyweight, we would need " + 
                          (cityRenderer.getTotalTiles() + wildernessRenderer.getTotalTiles()) + 
                          " graphic instances.");
        System.out.println("With Flyweight, we only need " + TileGraphicFactory.getTotalGraphics() + 
                          " graphic instances!");
    }
}
