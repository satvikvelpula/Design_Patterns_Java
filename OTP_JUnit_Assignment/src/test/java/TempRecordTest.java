import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TempRecordTest {

    @Test
    void storesValueAndUnitId() {
        TempRecord record = new TempRecord(36.6, 2);
        record.setId(10);
        record.setUnitCode("C");

        assertEquals(10, record.getId());
        assertEquals(36.6, record.getValue(), 0.0001);
        assertEquals(2, record.getUnitId());
        assertEquals("C", record.getUnitCode());
    }
}
