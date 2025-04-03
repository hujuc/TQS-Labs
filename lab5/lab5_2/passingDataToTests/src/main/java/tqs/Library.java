package tqs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> store = new ArrayList<>();

    public List<Book> findBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : store) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public void addBook(Book book) {
        store.add(book);
    }

    public List<Book> findBooks(LocalDateTime from, LocalDateTime to) {
        List<Book> result = new ArrayList<>();
        for (Book book : store) {
            if (book.getPublished().isAfter(from) && book.getPublished().isBefore(to)) {
                result.add(book);
            }
        }
        return result;
    }

}