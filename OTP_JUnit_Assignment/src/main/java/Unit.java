/**
 * Language-neutral unit plus localized name/description for the active language.
 */
public class Unit {
    private int id;
    private String code;
    private String category;
    private String symbol;
    private String name;
    private String description;
    private String lang;

    public Unit() {
    }

    public Unit(int id, String code, String category, String symbol,
                String name, String description, String lang) {
        this.id = id;
        this.code = code;
        this.category = category;
        this.symbol = symbol;
        this.name = name;
        this.description = description;
        this.lang = lang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return name + " (" + symbol + ")";
    }
}
