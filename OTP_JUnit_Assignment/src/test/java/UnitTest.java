import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnitTest {
    @Test
    void toStringIncludesLocalizedNameAndSymbol() {
        Unit unit = new Unit(1, "C", "temperature", "°C", "Celsius", "Metric", "en");
        String text = unit.toString();
        assertTrue(text.contains("Celsius"));
        assertTrue(text.contains("°C"));
        assertEquals("C", unit.getCode());
    }
}
