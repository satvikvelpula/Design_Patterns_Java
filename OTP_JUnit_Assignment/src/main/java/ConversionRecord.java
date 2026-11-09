import java.sql.Timestamp;

/**
 * Saved temperature conversion row (history table).
 */
public class ConversionRecord {
    private int id;
    private double inputValue;
    private int fromUnitId;
    private int toUnitId;
    private double resultValue;
    private String lang;
    private Timestamp recordedAt;
    private String fromUnitName;
    private String toUnitName;

    public ConversionRecord() {
    }

    public ConversionRecord(double inputValue, int fromUnitId, int toUnitId,
                            double resultValue, String lang) {
        this.inputValue = inputValue;
        this.fromUnitId = fromUnitId;
        this.toUnitId = toUnitId;
        this.resultValue = resultValue;
        this.lang = lang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getInputValue() {
        return inputValue;
    }

    public void setInputValue(double inputValue) {
        this.inputValue = inputValue;
    }

    public int getFromUnitId() {
        return fromUnitId;
    }

    public void setFromUnitId(int fromUnitId) {
        this.fromUnitId = fromUnitId;
    }

    public int getToUnitId() {
        return toUnitId;
    }

    public void setToUnitId(int toUnitId) {
        this.toUnitId = toUnitId;
    }

    public double getResultValue() {
        return resultValue;
    }

    public void setResultValue(double resultValue) {
        this.resultValue = resultValue;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Timestamp getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(Timestamp recordedAt) {
        this.recordedAt = recordedAt;
    }

    public String getFromUnitName() {
        return fromUnitName;
    }

    public void setFromUnitName(String fromUnitName) {
        this.fromUnitName = fromUnitName;
    }

    public String getToUnitName() {
        return toUnitName;
    }

    public void setToUnitName(String toUnitName) {
        this.toUnitName = toUnitName;
    }
}
