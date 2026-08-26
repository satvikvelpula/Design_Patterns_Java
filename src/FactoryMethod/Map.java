package FactoryMethod;

public abstract class Map {
    protected Tile[][] grid;
    protected int width;
    protected int height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Tile[height][width];
        populateMap();
    }

    private void populateMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = createTile();
            }
        }
    }

    public abstract Tile createTile();

    public void display() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(grid[i][j].getCharacter() + " ");
            }
            System.out.println();
        }
    }
}