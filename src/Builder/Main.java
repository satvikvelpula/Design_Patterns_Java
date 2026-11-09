package Builder;

import java.util.Scanner;

/**
 * Demonstrates the Builder pattern: director + gaming / office builders,
 * plus a simple console UI for custom component selection.
 */
public class Main {
    public static void main(String[] args) {
        ComputerDirector director = new ComputerDirector();

        System.out.println("=== Gaming computer ===");
        director.setBuilder(new GamingComputerBuilder());
        Computer gamingPc = director.constructComputer();
        System.out.println(gamingPc);

        System.out.println("=== Office computer ===");
        director.setBuilder(new OfficeComputerBuilder());
        Computer officePc = director.constructComputer();
        System.out.println(officePc);

        System.out.println("=== Custom build (challenge UI) ===");
        Computer custom = buildInteractively(new Scanner(System.in));
        System.out.println(custom);
    }

    /** Lets the user pick grades for each component. */
    private static Computer buildInteractively(Scanner scanner) {
        Computer computer = new Computer();

        System.out.println("Processor: 1) Ryzen 5  2) Ryzen 9  3) Core i5");
        computer.setProcessor(switch (readChoice(scanner)) {
            case "2" -> "AMD Ryzen 9 7950X";
            case "3" -> "Intel Core i5-13400";
            default -> "AMD Ryzen 5 7600";
        });

        System.out.println("RAM (GB): 1) 8  2) 16  3) 32");
        computer.setRamGb(switch (readChoice(scanner)) {
            case "1" -> 8;
            case "3" -> 32;
            default -> 16;
        });

        System.out.println("Hard drive: 1) 512 GB SSD  2) 1 TB SSD  3) 2 TB NVMe");
        computer.setHardDrive(switch (readChoice(scanner)) {
            case "2" -> "1 TB SSD";
            case "3" -> "2 TB NVMe SSD";
            default -> "512 GB SSD";
        });

        System.out.println("Graphics: 1) Integrated  2) RTX 4060  3) RTX 4080");
        computer.setGraphicsCard(switch (readChoice(scanner)) {
            case "2" -> "NVIDIA GeForce RTX 4060";
            case "3" -> "NVIDIA GeForce RTX 4080";
            default -> "Integrated graphics";
        });

        System.out.println("OS: 1) Windows 11 Home  2) Windows 11 Pro  3) Linux");
        computer.setOperatingSystem(switch (readChoice(scanner)) {
            case "2" -> "Windows 11 Pro";
            case "3" -> "Ubuntu 24.04 LTS";
            default -> "Windows 11 Home";
        });

        System.out.println("Accessories: 1) None  2) Office kit  3) Gaming kit");
        computer.setAccessories(switch (readChoice(scanner)) {
            case "2" -> "Keyboard, mouse, office monitor";
            case "3" -> "RGB keyboard, gaming mouse, 144 Hz monitor";
            default -> "None";
        });

        return computer;
    }

    private static String readChoice(Scanner scanner) {
        System.out.print("> ");
        String line = scanner.nextLine().trim();
        return line.isEmpty() ? "1" : line;
    }
}
