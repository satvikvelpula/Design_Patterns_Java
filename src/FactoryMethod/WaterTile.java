package FactoryMethod;

public class WaterTile extends Tile {
    @Override
    public char getCharacter() { return 'W'; }
    @Override
    public String getType() { return "water"; }
    @Override
    public void action() { System.out.println("You swim across the deep water."); }
}