package tqs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibrarySearchSteps {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "input[name='search']")
    private WebElement searchInput;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    @FindBy(css = "div.books-container")
    private WebElement bookList;

    @FindBy(css = "div.no-results")
    private WebElement noResultsMessage;

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Headless mode to run without opening browser
        options.addArguments("--headless");

        driver = WebDriverManager.chromedriver().capabilities(options).create();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the library homepage")
    public void i_am_on_library_homepage() {
        driver.get("https://cover-bookstore.onrender.com/");
    }

    @When("I enter {string} in the search bar")
    public void i_enter_in_search_bar(String searchTerm) {
        wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        searchInput.clear();
        searchInput.sendKeys(searchTerm);
    }

    @When("I click the search button")
    public void i_click_search_button() {
        searchButton.click();
    }

    @Then("I should see book results containing {string}")
    public void i_should_see_book_results_containing(String expectedTitle) {
        wait.until(ExpectedConditions.visibilityOf(bookList));
        assertTrue(
                driver.getPageSource().contains(expectedTitle),
                "Expected title not found in search results"
        );
    }

    @Then("I should see book results by {string}")
    public void i_should_see_book_results_by(String expectedAuthor) {
        wait.until(ExpectedConditions.visibilityOf(bookList));
        assertTrue(
                driver.getPageSource().contains(expectedAuthor),
                "Expected author not found in search results"
        );
    }

    @Then("I should see a {string} message")
    public void i_should_see_no_results_message(String expectedMessage) {
        wait.until(ExpectedConditions.visibilityOf(noResultsMessage));
        assertTrue(
                noResultsMessage.isDisplayed(),
                "No results message not displayed"
        );
    }
}