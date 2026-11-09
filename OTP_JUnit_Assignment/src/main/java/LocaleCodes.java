import java.util.Locale;

/**
 * Maps UI {@link Locale} to DB language codes used in unit_translation.lang.
 */
public final class LocaleCodes {
    public static final String DEFAULT = "en";

    private LocaleCodes() {
    }

    public static String fromLocale(Locale locale) {
        if (locale == null) {
            return DEFAULT;
        }
        return switch (locale.getLanguage()) {
            case "fi" -> "fi";
            case "sv" -> "sv";
            case "ja" -> "ja";
            case "ar" -> "ar";
            default -> DEFAULT;
        };
    }
}
