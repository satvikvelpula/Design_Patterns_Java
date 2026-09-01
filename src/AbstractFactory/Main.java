package AbstractFactory;

public class Main {
    public static void main(String[] args) {
        UIFactory factory_a = new AFactory();
        factory_a.createButton("This is button text from Factory A");
        factory_a.createCheckbox("This is a checkbox text from Factory A");
        UIFactory factory_b = new BFactory();
        factory_b.createButton("This is button text from Factory B");
        factory_b.createCheckbox("This is a checkbox text from Factory B");
    }
}