package FactoryMethod;

public class ForestTile extends Tile {
    @Override
    public char getCharacter() { return 'F'; }
    @Override
    public String getType() { return "forest"; }
    @Override
    public void action() { System.out.println("You navigate the dense forest trees."); }
}