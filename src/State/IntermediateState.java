package State;

import java.util.List;
import java.util.Random;

public class IntermediateState extends CharacterState {
    private final Random random = new Random();

    public IntermediateState(GameCharacter character) {
        super(character);
    }

    @Override
    public String getLevelName() {
        return "Intermediate";
    }

    @Override
    public List<String> getAvailableActions() {
        return List.of("train", "meditate");
    }

    @Override
    public void train() {
        int gained = 20 + random.nextInt(16); // 20-35 XP
        System.out.println(character.getName() + " sharpens skills through training...");
        character.addExperience(gained);
        int remaining = GameCharacter.EXPERT_THRESHOLD - character.getExperiencePoints();
        if (remaining > 0) {
            System.out.println("(" + remaining + " XP needed to reach Expert)");
        }
    }

    @Override
    public void meditate() {
        int recovered = 10 + random.nextInt(11); // 10-20 HP
        System.out.println(character.getName() + " meditates and restores vitality...");
        character.heal(recovered);
    }

    @Override
    public void fight() {
        deny("fight");
    }
}
