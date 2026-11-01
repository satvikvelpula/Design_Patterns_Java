import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Week-1 style console localization (optional). GUI entry point is {@link Main}.
 */
public class LocalizationConsole {
    private static final TemperatureConverter CONVERTER = new TemperatureConverter();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        System.out.println("Select your preferred language:");
        System.out.println("1) English");
        System.out.println("2) Finnish");
        System.out.println("3) Swedish");
        System.out.println("4) Japanese");
        System.out.println("5) Arabic");
        System.out.print("Enter choice (1-5): ");

        Locale locale = resolveLocale(scanner.nextLine().trim());
        ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", locale);

        System.out.println();
        System.out.println(messages.containsKey("app.title") ? messages.getString("app.title") : "Temperature Converter");

        boolean running = true;
        while (running) {
            System.out.println("1) F -> C");
            System.out.println("2) C -> F");
            System.out.println("3) Extreme check");
            System.out.println("4) Exit");
            System.out.print("> ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> {
                    System.out.print("F: ");
                    double f = Double.parseDouble(scanner.nextLine().trim());
                    System.out.println(MessageFormat.format("{0} F = {1} C", f, CONVERTER.fahrenheitToCelsius(f)));
                }
                case "2" -> {
                    System.out.print("C: ");
                    double c = Double.parseDouble(scanner.nextLine().trim());
                    System.out.println(MessageFormat.format("{0} C = {1} F", c, CONVERTER.celsiusToFahrenheit(c)));
                }
                case "3" -> {
                    System.out.print("C: ");
                    double c = Double.parseDouble(scanner.nextLine().trim());
                    System.out.println("Extreme? " + CONVERTER.isExtremeTemperature(c));
                }
                case "4" -> running = false;
                default -> System.out.println("Invalid");
            }
        }
    }

    private static Locale resolveLocale(String choice) {
        return switch (choice) {
            case "2" -> Locale.forLanguageTag("fi-FI");
            case "3" -> Locale.forLanguageTag("sv-SE");
            case "4" -> Locale.forLanguageTag("ja-JP");
            case "5" -> Locale.forLanguageTag("ar-SA");
            default -> Locale.US;
        };
    }
}
