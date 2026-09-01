package AbstractFactory;

public class CheckboxB extends Checkbox {
    private String text;

    public CheckboxB(String text) {
        this.text = text;
    }

    @Override
    void setText(String text) {
        this.text = text;
    }

    @Override
    void display() {
        System.out.println("Displaying Style B checkbox: " + text);
    }
}
