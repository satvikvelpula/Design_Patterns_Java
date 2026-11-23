package Prototype;

import java.util.ArrayList;
import java.util.List;

public class Recommendation implements Cloneable {
    private String targetAudience;
    private final List<Book> books;

    public Recommendation(String targetAudience) {
        this.targetAudience = targetAudience;
        this.books = new ArrayList<>();
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(int index) {
        books.remove(index);
    }

    @Override
    public Recommendation clone() {
        Recommendation copy = new Recommendation(targetAudience);
        for (Book book : books) {
            copy.addBook(book.clone());
        }
        return copy;
    }

    public void display() {
        System.out.println("Target audience: " + targetAudience);
        if (books.isEmpty()) {
            System.out.println("  (no books)");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + books.get(i));
        }
    }

    @Override
    public String toString() {
        return "Recommendation for " + targetAudience + " (" + books.size() + " books)";
    }
}
