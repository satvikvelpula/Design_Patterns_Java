import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemperatureUnitTest {

    @Test
    void storesFieldsAndFormatsToString() {
        TemperatureUnit unit = new TemperatureUnit(1, "C", "Celsius");
        assertEquals(1, unit.getId());
        assertEquals("C", unit.getCode());
        assertEquals("Celsius", unit.getName());
        assertEquals("C (Celsius)", unit.toString());
    }
}
