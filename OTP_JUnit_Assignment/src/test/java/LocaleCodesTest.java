import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocaleCodesTest {
    @Test
    void mapsKnownLanguages() {
        assertEquals("en", LocaleCodes.fromLocale(Locale.US));
        assertEquals("fi", LocaleCodes.fromLocale(Locale.forLanguageTag("fi-FI")));
        assertEquals("sv", LocaleCodes.fromLocale(Locale.forLanguageTag("sv-SE")));
        assertEquals("ja", LocaleCodes.fromLocale(Locale.forLanguageTag("ja-JP")));
        assertEquals("ar", LocaleCodes.fromLocale(Locale.forLanguageTag("ar-SA")));
    }

    @Test
    void defaultsUnknownOrNullToEnglish() {
        assertEquals("en", LocaleCodes.fromLocale(null));
        assertEquals("en", LocaleCodes.fromLocale(Locale.GERMAN));
    }
}
