package AbstractFactory;

/*
Define an abstract factory class UIFactory that defines methods for creating
different user interface elements. Each method should take a parameter text.
*/

abstract class UIFactory {
    public abstract Button createButton(String text);
    public abstract Checkbox createCheckbox(String text);
    public abstract TextField createTextField(String text);
}
