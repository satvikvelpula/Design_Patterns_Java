package Decorator;

public abstract class PrinterDecorator implements Printer {
    protected final Printer wrapped;

    public PrinterDecorator(Printer printer) {
        this.wrapped = printer;
    }

    @Override
    public void print(String message) {
        wrapped.print(message);
    }
}
