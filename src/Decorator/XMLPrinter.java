package Decorator;

public class XMLPrinter extends PrinterDecorator {
    public XMLPrinter(Printer printer) {
        super(printer);
    }

    @Override
    public void print(String message) {
        wrapped.print("<message>" + message + "</message>");
    }
}
