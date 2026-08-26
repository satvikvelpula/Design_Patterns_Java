package FactoryMethod;

import java.util.Random;

public class CityMap extends Map {
    private static Random rand = new Random();

    public CityMap(int width, int height) {
        super(width, height);
    }

    @Override
    public Tile createTile() {
        int choice = rand.nextInt(3);
        switch (choice) { // Using switch - trying to avoid if/else here
            case 0: return new RoadTile();
            case 1: return new ForestTile();
            case 2: return new BuildingTile();
            default: return new RoadTile();
        }
    }
}