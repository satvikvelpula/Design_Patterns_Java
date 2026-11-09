import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnitTest {
    @Test
    void constructorAndSettersExposeAllFields() {
        Unit unit = new Unit(1, "C", "temperature", "°C", "Celsius", "Metric", "en");
        assertEquals(1, unit.getId());
        assertEquals("C", unit.getCode());
        assertEquals("temperature", unit.getCategory());
        assertEquals("°C", unit.getSymbol());
        assertEquals("Celsius", unit.getName());
        assertEquals("Metric", unit.getDescription());
        assertEquals("en", unit.getLang());
        assertTrue(unit.toString().contains("Celsius"));

        unit.setId(2);
        unit.setCode("F");
        unit.setCategory("temperature");
        unit.setSymbol("°F");
        unit.setName("Fahrenheit");
        unit.setDescription("Imperial");
        unit.setLang("fi");
        assertEquals("F", unit.getCode());
        assertEquals("Fahrenheit", unit.getName());
        assertEquals("fi", unit.getLang());
    }

    @Test
    void defaultConstructorAllowsEmptyUnit() {
        Unit unit = new Unit();
        unit.setCode("K");
        assertEquals("K", unit.getCode());
    }
}
