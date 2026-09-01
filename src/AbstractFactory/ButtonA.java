package AbstractFactory;

public class ButtonA extends Button {
    private String text;

    public ButtonA(String text) {
        this.text = text;
    }

    @Override
    void setText(String text) {
        this.text = text;
    }

    @Override
    void display() {
        System.out.println("Displaying Style A button: " + text);
    }
}
