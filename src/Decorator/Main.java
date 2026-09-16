package Decorator;

public class Main {
    public static void main(String[] args) {
        Printer printer = new BasicPrinter();
        printer.print("Hello World!");

        Printer printer2 = new EncryptedPrinter(new XMLPrinter(new BasicPrinter()));
        printer2.print("Hello World!");

        // Show that the encryption is reversible
        String encrypted = EncryptedPrinter.encrypt("<message>Hello World!</message>");
        System.out.println("Decrypted back: " + EncryptedPrinter.decrypt(encrypted));
    }
}
