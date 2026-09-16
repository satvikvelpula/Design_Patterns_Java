package State;

import java.util.List;
import java.util.Random;

public class ExpertState extends CharacterState {
    private final Random random = new Random();

    public ExpertState(GameCharacter character) {
        super(character);
    }

    @Override
    public String getLevelName() {
        return "Expert";
    }

    @Override
    public List<String> getAvailableActions() {
        return List.of("train", "meditate", "fight");
    }

    @Override
    public void train() {
        int gained = 25 + random.nextInt(16); // 25-40 XP
        System.out.println(character.getName() + " trains with expert focus...");
        character.addExperience(gained);
        printXpRemaining();
    }

    @Override
    public void meditate() {
        int recovered = 15 + random.nextInt(16); // 15-30 HP
        System.out.println(character.getName() + " enters a deep meditation...");
        character.heal(recovered);
    }

    @Override
    public void fight() {
        int damage = 15 + random.nextInt(21); // 15-35 HP
        int gained = 40 + random.nextInt(31); // 40-70 XP
        System.out.println(character.getName() + " charges into battle!");
        character.takeDamage(damage);
        if (character.isAlive()) {
            character.addExperience(gained);
            printXpRemaining();
        }
    }

    private void printXpRemaining() {
        int remaining = GameCharacter.MASTER_THRESHOLD - character.getExperiencePoints();
        if (remaining > 0 && character.isAlive()) {
            System.out.println("(" + remaining + " XP needed to reach Master)");
        }
    }
}
