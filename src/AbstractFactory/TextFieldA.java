package AbstractFactory;

public class TextFieldA extends TextField {
    private String text;

    public TextFieldA(String text) {
        this.text = text;
    }

    @Override
    void setText(String text) {
        this.text = text;
    }

    @Override
    void display() {
        System.out.println("Displaying Style A text field: " + text);
    }
}
