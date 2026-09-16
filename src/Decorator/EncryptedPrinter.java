package Decorator;

public class EncryptedPrinter extends PrinterDecorator {
    private static final int SHIFT = 3;

    public EncryptedPrinter(Printer printer) {
        super(printer);
    }

    @Override
    public void print(String message) {
        wrapped.print(encrypt(message));
    }

    public static String encrypt(String message) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : message.toCharArray()) {
            encrypted.append((char) (c + SHIFT));
        }
        return encrypted.toString();
    }

    public static String decrypt(String message) {
        StringBuilder decrypted = new StringBuilder();
        for (char c : message.toCharArray()) {
            decrypted.append((char) (c - SHIFT));
        }
        return decrypted.toString();
    }
}
