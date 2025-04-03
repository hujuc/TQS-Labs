package tqs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.ParameterType;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class BookSearchSteps {
    private Library library = new Library();
    private List<Book> searchResult;

    @ParameterType("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")
    public LocalDateTime iso8601Date(String date) {
        return LocalDateTime.parse(date);
    }

    @Given("the following books are available in the library")
    public void the_following_books_are_available_in_the_library(List<Map<String, String>> books) {
        for (Map<String, String> book : books) {
            String title = book.get("Title");
            String author = book.get("Author");
            LocalDateTime publishedDate = LocalDateTime.parse(book.get("Published Date"));
            library.addBook(new Book(publishedDate, author, title));
        }
    }

    @When("the customer searches for books by author {string}")
    public void the_customer_searches_for_books_by_author(String author) {
        searchResult = library.findBooksByAuthor(author);
    }

    @Then("{int} book should be found")
    public void book_should_be_found(int expectedCount) {
        assertEquals(expectedCount, searchResult.size());
    }

    @Then("{int} books should be found")
    public void books_should_be_found(int expectedCount) {
        assertEquals(expectedCount, searchResult.size());
    }

    @Then("the book should have the title {string}")
    public void the_book_should_have_the_title(String expectedTitle) {
        assertEquals(expectedTitle, searchResult.get(0).getTitle());
    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void the_customer_searches_for_books_published_between_and(LocalDateTime from, LocalDateTime to) {
        searchResult = library.findBooks(from, to);
    }

    @Then("the book {int} should have the title {string}")
    public void the_book_index_should_have_the_title(int index, String expectedTitle) {
        assertEquals(expectedTitle, searchResult.get(index-1).getTitle());
    }
}