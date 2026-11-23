package Prototype;

import java.util.Scanner;

public class Main {
    private final RecommendationManager manager = new RecommendationManager();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Main app = new Main();
        app.seed();
        app.run();
    }

    private void seed() {
        Recommendation fantasy = new Recommendation("Fantasy fans");
        fantasy.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", 1937));
        fantasy.addBook(new Book("A Game of Thrones", "George R.R. Martin", "Fantasy", 1996));
        fantasy.addBook(new Book("The Name of the Wind", "Patrick Rothfuss", "Fantasy", 2007));
        manager.save(fantasy);

        Recommendation mystery = new Recommendation("Mystery lovers");
        mystery.addBook(new Book("Gone Girl", "Gillian Flynn", "Mystery", 2012));
        mystery.addBook(new Book("The Girl with the Dragon Tattoo", "Stieg Larsson", "Mystery", 2005));
        manager.save(mystery);

        Recommendation youngAdult = new Recommendation("Young adults");
        youngAdult.addBook(new Book("The Hunger Games", "Suzanne Collins", "Young Adult", 2008));
        youngAdult.addBook(new Book("The Fault in Our Stars", "John Green", "Young Adult", 2012));
        manager.save(youngAdult);
    }

    private void run() {
        while (true) {
            System.out.println();
            System.out.println("=== Book Recommendations System ===");
            System.out.println("1. View existing recommendations");
            System.out.println("2. Clone and modify a recommendation");
            System.out.println("3. Create a new recommendation from scratch");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> manager.displayAll();
                case "2" -> cloneAndModify();
                case "3" -> createFromScratch();
                case "4" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void cloneAndModify() {
        if (manager.size() == 0) {
            System.out.println("No recommendations to clone.");
            return;
        }
        int index = pickRecommendation();
        if (index == -1) {
            return;
        }
        Recommendation copy = manager.get(index).clone();
        System.out.println("Cloned:");
        copy.display();
        modify(copy);
        save(copy);
    }

    private void createFromScratch() {
        System.out.print("Target audience: ");
        String audience = scanner.nextLine().trim();
        Recommendation recommendation = new Recommendation(audience);
        modify(recommendation);
        save(recommendation);
    }

    private void modify(Recommendation recommendation) {
        while (true) {
            System.out.println();
            recommendation.display();
            System.out.println();
            System.out.println("-- Modify --");
            System.out.println("1. Change target audience");
            System.out.println("2. Add a book");
            System.out.println("3. Remove a book");
            System.out.println("4. Done");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("New target audience: ");
                    recommendation.setTargetAudience(scanner.nextLine().trim());
                }
                case "2" -> {
                    System.out.print("Title: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Author: ");
                    String author = scanner.nextLine().trim();
                    System.out.print("Genre: ");
                    String genre = scanner.nextLine().trim();
                    System.out.print("Publication year: ");
                    int year = readInt();
                    recommendation.addBook(new Book(title, author, genre, year));
                }
                case "3" -> {
                    if (recommendation.getBooks().isEmpty()) {
                        System.out.println("Nothing to remove.");
                        break;
                    }
                    System.out.print("Book number to remove: ");
                    int bookIndex = readInt();
                    if (bookIndex >= 1 && bookIndex <= recommendation.getBooks().size()) {
                        recommendation.removeBook(bookIndex - 1);
                    } else {
                        System.out.println("Invalid book number.");
                    }
                }
                case "4" -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void save(Recommendation recommendation) {
        System.out.print("Save this recommendation? (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        if (answer.equals("y") || answer.equals("yes")) {
            manager.save(recommendation);
            System.out.println("Saved.");
        } else {
            System.out.println("Discarded.");
        }
    }

    private int pickRecommendation() {
        System.out.println("Existing recommendations:");
        for (int i = 0; i < manager.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + manager.get(i));
        }
        System.out.print("Number to clone (0 to cancel): ");
        int index = readInt();
        if (index < 1 || index > manager.size()) {
            System.out.println("Cancelled.");
            return -1;
        }
        return index - 1;
    }

    private int readInt() {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Enter a number: ");
            }
        }
    }
}
