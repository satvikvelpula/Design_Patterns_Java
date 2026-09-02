package AbstractFactory;

public class Main {
    public static void main(String[] args) {
        UIFactory factoryA = new AFactory();

        Button buttonA = factoryA.createButton("This is button text from Factory A");
        Checkbox checkboxA = factoryA.createCheckbox("This is checkbox text from Factory A");
        TextField textFieldA = factoryA.createTextField("This is text field text from Factory A");

        buttonA.display();
        checkboxA.display();
        textFieldA.display();

        UIFactory factoryB = new BFactory();

        Button buttonB = factoryB.createButton("This is button text from Factory B");
        Checkbox checkboxB = factoryB.createCheckbox("This is checkbox text from Factory B");
        TextField textFieldB = factoryB.createTextField("This is text field text from Factory B");

        buttonB.display();
        checkboxB.display();
        textFieldB.display();

        buttonB.setText("This is updated button text from Factory B");
        buttonB.display();
    }
}
