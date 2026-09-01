package AbstractFactory;

public class TextFieldB extends TextField {
    private String text;

    public TextFieldB(String text) {
        this.text = text;
    }

    @Override
    void setText(String text) {
        this.text = text;
    }

    @Override
    void display() {
        System.out.println("Displaying Style B text field: " + text);
    }
}
