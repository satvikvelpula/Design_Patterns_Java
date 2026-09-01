package AbstractFactory;

public class CheckboxA extends Checkbox {
    private String text;

    public CheckboxA(String text) {
        this.text = text;
    }

    @Override
    void setText(String text) {
        this.text = text;
    }

    @Override
    void display() {
        System.out.println("Displaying Style A checkbox: " + text);
    }
}
