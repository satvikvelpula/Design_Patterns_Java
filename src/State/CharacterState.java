package State;

import java.util.List;

public abstract class CharacterState {
    protected final GameCharacter character;

    protected CharacterState(GameCharacter character) {
        this.character = character;
    }

    public abstract String getLevelName();

    public abstract List<String> getAvailableActions();

    public abstract void train();

    public abstract void meditate();

    public abstract void fight();

    protected void deny(String action) {
        System.out.println("You cannot " + action + " at the " + getLevelName() + " level.");
    }
}
