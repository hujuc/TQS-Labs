package tqs;

import java.time.LocalDateTime;

public class Book {
    private LocalDateTime published;
    private String author;
    private String title;

    public Book(LocalDateTime published, String author, String title) {
        this.published = published;
        this.author = author;
        this.title = title;
    }

    public String getAuthor() { return author; }
    public LocalDateTime getPublished() { return published; }
    public String getTitle() { return title; }

    @Override
    public String toString() {
        return "Book{" +
                "published=" + published +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}