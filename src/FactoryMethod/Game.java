package FactoryMethod;

public class Game {

    public static Map createMap(String type, int width, int height) {
        if (type.equals("CityMap")) {
            return new CityMap(width, height);
        } else if (type.equals("WildernessMap")) {
            return new WildernessMap(width, height);
        }
        throw new IllegalArgumentException("Unknown map type: " + type);
    }

    public static void main(String[] args) {
        int mapWidth = 10;
        int mapHeight = 5;

        System.out.println("--- Generating City Map ---");
        Map city = createMap("CityMap", mapWidth, mapHeight);
        city.display();

        System.out.println("\n--- Generating Wilderness Map ---");
        Map wilderness = createMap("WildernessMap", mapWidth, mapHeight);
        wilderness.display();
    }
}