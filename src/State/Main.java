package State;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Game Character Development System ===");
        System.out.print("Enter your character's name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = "Hero";
        }

        GameCharacter character = new GameCharacter(name);
        System.out.println();
        System.out.println("Welcome, " + character.getName() + "!");
        System.out.println("Train to grow. Meditate to heal. Fight for glory.");
        System.out.println("Reach Master level to win. Falling in battle ends the game.");
        System.out.println("XP thresholds — Intermediate: " + GameCharacter.INTERMEDIATE_THRESHOLD
                + ", Expert: " + GameCharacter.EXPERT_THRESHOLD
                + ", Master: " + GameCharacter.MASTER_THRESHOLD);

        while (!character.isGameOver()) {
            character.displayStatus();
            System.out.print("Choose action (or 'quit'): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("quit") || input.equals("q") || input.equals("exit")) {
                System.out.println("You leave the path unfinished. Farewell, " + character.getName() + ".");
                break;
            }

            switch (input) {
                case "train", "t" -> character.train();
                case "meditate", "m" -> character.meditate();
                case "fight", "f" -> character.fight();
                default -> System.out.println("Unknown action. Try: train, meditate, fight, or quit.");
            }
        }

        if (character.hasMastered()) {
            character.displayStatus();
            System.out.println("Congratulations! " + character.getName() + " is a true Master.");
        } else if (!character.isAlive()) {
            character.displayStatus();
            System.out.println("Better luck on your next adventure.");
        }

        scanner.close();
    }
}
