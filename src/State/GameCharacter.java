package State;

import java.util.List;

public class GameCharacter {
    public static final int MAX_HEALTH = 100;
    public static final int INTERMEDIATE_THRESHOLD = 100;
    public static final int EXPERT_THRESHOLD = 250;
    public static final int MASTER_THRESHOLD = 500;

    private final String name;
    private int experiencePoints;
    private int healthPoints;
    private CharacterState state;
    private boolean alive = true;
    private boolean mastered = false;

    public GameCharacter(String name) {
        this.name = name;
        this.experiencePoints = 0;
        this.healthPoints = MAX_HEALTH;
        this.state = new NoviceState(this);
    }

    public String getName() {
        return name;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public CharacterState getState() {
        return state;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean hasMastered() {
        return mastered;
    }

    public boolean isGameOver() {
        return !alive || mastered;
    }

    public void setState(CharacterState state) {
        this.state = state;
        System.out.println(">>> " + name + " advanced to " + state.getLevelName() + " level!");
    }

    public void addExperience(int amount) {
        experiencePoints += amount;
        System.out.println("Gained " + amount + " XP. Total XP: " + experiencePoints);
        checkLevelUp();
    }

    public void heal(int amount) {
        int before = healthPoints;
        healthPoints = Math.min(MAX_HEALTH, healthPoints + amount);
        System.out.println("Recovered " + (healthPoints - before) + " HP. Health: "
                + healthPoints + "/" + MAX_HEALTH);
    }

    public void takeDamage(int amount) {
        healthPoints -= amount;
        System.out.println("Lost " + amount + " HP. Health: " + Math.max(0, healthPoints) + "/" + MAX_HEALTH);
        if (healthPoints <= 0) {
            healthPoints = 0;
            alive = false;
            System.out.println(">>> " + name + " has fallen in battle. Game over.");
        }
    }

    public void reachMastery() {
        mastered = true;
        System.out.println(">>> " + name + " has become a Master. The journey is complete!");
    }

    public List<String> getAvailableActions() {
        return state.getAvailableActions();
    }

    public void train() {
        state.train();
    }

    public void meditate() {
        state.meditate();
    }

    public void fight() {
        state.fight();
    }

    public void displayStatus() {
        System.out.println();
        System.out.println("========================================");
        System.out.println(" Character: " + name);
        System.out.println(" Level:     " + state.getLevelName());
        System.out.println(" XP:        " + experiencePoints);
        System.out.println(" Health:    " + healthPoints + "/" + MAX_HEALTH);
        System.out.println("========================================");
        System.out.println(" Available actions: " + String.join(", ", getAvailableActions()));
    }

    private void checkLevelUp() {
        if (state instanceof NoviceState && experiencePoints >= INTERMEDIATE_THRESHOLD) {
            setState(new IntermediateState(this));
        } else if (state instanceof IntermediateState && experiencePoints >= EXPERT_THRESHOLD) {
            setState(new ExpertState(this));
        } else if (state instanceof ExpertState && experiencePoints >= MASTER_THRESHOLD) {
            setState(new MasterState(this));
            reachMastery();
        }
    }
}
