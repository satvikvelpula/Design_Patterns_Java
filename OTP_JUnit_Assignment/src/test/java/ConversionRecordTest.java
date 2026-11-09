import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConversionRecordTest {
    @Test
    void storesAllFieldsViaConstructorAndSetters() {
        ConversionRecord record = new ConversionRecord(25.0, 1, 2, 77.0, "fi");
        assertEquals(25.0, record.getInputValue());
        assertEquals(1, record.getFromUnitId());
        assertEquals(2, record.getToUnitId());
        assertEquals(77.0, record.getResultValue());
        assertEquals("fi", record.getLang());

        Timestamp now = new Timestamp(System.currentTimeMillis());
        record.setId(9);
        record.setInputValue(10.0);
        record.setFromUnitId(3);
        record.setToUnitId(4);
        record.setResultValue(50.0);
        record.setLang("en");
        record.setRecordedAt(now);
        record.setFromUnitName("Celsius");
        record.setToUnitName("Fahrenheit");

        assertEquals(9, record.getId());
        assertEquals(10.0, record.getInputValue());
        assertEquals(3, record.getFromUnitId());
        assertEquals(4, record.getToUnitId());
        assertEquals(50.0, record.getResultValue());
        assertEquals("en", record.getLang());
        assertEquals(now, record.getRecordedAt());
        assertEquals("Celsius", record.getFromUnitName());
        assertEquals("Fahrenheit", record.getToUnitName());
    }
}
