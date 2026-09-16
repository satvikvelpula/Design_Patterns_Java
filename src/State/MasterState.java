package State;

import java.util.List;

public class MasterState extends CharacterState {
    public MasterState(GameCharacter character) {
        super(character);
    }

    @Override
    public String getLevelName() {
        return "Master";
    }

    @Override
    public List<String> getAvailableActions() {
        return List.of();
    }

    @Override
    public void train() {
        System.out.println("A Master has nothing left to train for.");
    }

    @Override
    public void meditate() {
        System.out.println("A Master rests in perfect balance.");
    }

    @Override
    public void fight() {
        System.out.println("A Master no longer seeks battle.");
    }
}
