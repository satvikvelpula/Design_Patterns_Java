package FactoryMethod;

public class SwampTile extends Tile {
    @Override
    public char getCharacter() { return 'S'; }
    @Override
    public String getType() { return "swamp"; }
    @Override
    public void action() { System.out.println("You wade through the murky swamp."); }
}