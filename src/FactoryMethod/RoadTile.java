package FactoryMethod;

public class RoadTile extends Tile {
    @Override
    public char getCharacter() { return 'R'; }
    @Override
    public String getType() { return "road"; }
    @Override
    public void action() { System.out.println("You travel safely along the paved road."); }
}