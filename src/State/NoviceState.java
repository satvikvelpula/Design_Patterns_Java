package State;

import java.util.List;
import java.util.Random;

public class NoviceState extends CharacterState {
    private final Random random = new Random();

    public NoviceState(GameCharacter character) {
        super(character);
    }

    @Override
    public String getLevelName() {
        return "Novice";
    }

    @Override
    public List<String> getAvailableActions() {
        return List.of("train");
    }

    @Override
    public void train() {
        int gained = 15 + random.nextInt(11); // 15-25 XP
        System.out.println(character.getName() + " trains hard as a novice...");
        character.addExperience(gained);
        int remaining = GameCharacter.INTERMEDIATE_THRESHOLD - character.getExperiencePoints();
        if (remaining > 0) {
            System.out.println("(" + remaining + " XP needed to reach Intermediate)");
        }
    }

    @Override
    public void meditate() {
        deny("meditate");
    }

    @Override
    public void fight() {
        deny("fight");
    }
}
