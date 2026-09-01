package AbstractFactory;

public class ButtonB extends Button {
    private String text;

    public ButtonB(String text) {
        this.text = text;
    }

    @Override
    void setText(String text) {
        this.text = text;
    }

    @Override
    void display() {
        System.out.println("Displaying Style B button: " + text);
    }
}
