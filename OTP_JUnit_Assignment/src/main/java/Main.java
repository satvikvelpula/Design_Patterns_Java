import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Localization console for In-class assignment 1:
 * language selection + TemperatureConverter operations via UTF-8 resource bundles.
 */
public class Main {
    private static final TemperatureConverter CONVERTER = new TemperatureConverter();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        System.out.println("Select your preferred language:");
        System.out.println("1) English");
        System.out.println("2) Finnish");
        System.out.println("3) Swedish");
        System.out.println("4) Japanese");
        System.out.print("Enter choice (1-4): ");

        Locale locale = resolveLocale(scanner.nextLine().trim());
        ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", locale);

        System.out.println();
        System.out.println(messages.getString("app.title"));

        boolean running = true;
        while (running) {
            printMenu(messages);
            System.out.print(messages.getString("menu.prompt") + " ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> convertFahrenheitToCelsius(scanner, messages);
                case "2" -> convertCelsiusToFahrenheit(scanner, messages);
                case "3" -> checkExtreme(scanner, messages);
                case "4" -> {
                    System.out.println(messages.getString("goodbye"));
                    running = false;
                }
                default -> System.out.println(messages.getString("error.menu"));
            }
            System.out.println();
        }
    }

    private static Locale resolveLocale(String choice) {
        return switch (choice) {
            case "1" -> Locale.US;
            case "2" -> Locale.forLanguageTag("fi-FI");
            case "3" -> Locale.forLanguageTag("sv-SE");
            case "4" -> Locale.forLanguageTag("ja-JP");
            default -> {
                System.out.println("Invalid choice. Using English (default).");
                yield Locale.US;
            }
        };
    }

    private static void printMenu(ResourceBundle messages) {
        System.out.println(messages.getString("menu.header"));
        System.out.println(messages.getString("menu.f_to_c"));
        System.out.println(messages.getString("menu.c_to_f"));
        System.out.println(messages.getString("menu.extreme"));
        System.out.println(messages.getString("menu.exit"));
    }

    private static void convertFahrenheitToCelsius(Scanner scanner, ResourceBundle messages) {
        Double fahrenheit = readDouble(scanner, messages.getString("prompt.fahrenheit"), messages);
        if (fahrenheit == null) {
            return;
        }
        double celsius = CONVERTER.fahrenheitToCelsius(fahrenheit);
        System.out.println(MessageFormat.format(
                messages.getString("result.f_to_c"),
                format(fahrenheit),
                format(celsius)
        ));
    }

    private static void convertCelsiusToFahrenheit(Scanner scanner, ResourceBundle messages) {
        Double celsius = readDouble(scanner, messages.getString("prompt.celsius"), messages);
        if (celsius == null) {
            return;
        }
        double fahrenheit = CONVERTER.celsiusToFahrenheit(celsius);
        System.out.println(MessageFormat.format(
                messages.getString("result.c_to_f"),
                format(celsius),
                format(fahrenheit)
        ));
    }

    private static void checkExtreme(Scanner scanner, ResourceBundle messages) {
        Double celsius = readDouble(scanner, messages.getString("prompt.celsius"), messages);
        if (celsius == null) {
            return;
        }
        String key = CONVERTER.isExtremeTemperature(celsius)
                ? "result.extreme.yes"
                : "result.extreme.no";
        System.out.println(MessageFormat.format(messages.getString(key), format(celsius)));
    }

    private static Double readDouble(Scanner scanner, String prompt, ResourceBundle messages) {
        System.out.print(prompt + " ");
        String raw = scanner.nextLine().trim();
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException ex) {
            System.out.println(messages.getString("error.number"));
            return null;
        }
    }

    private static String format(double value) {
        return String.format(Locale.ROOT, "%.2f", value);
    }
}
