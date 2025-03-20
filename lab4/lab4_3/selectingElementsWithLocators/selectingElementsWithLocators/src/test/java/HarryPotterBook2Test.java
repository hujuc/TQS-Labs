import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HarryPotterBook2Test {
  private WebDriver driver;
  private JavascriptExecutor js;
  private WebDriverWait wait;

  @BeforeAll
  void setupClass() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @AfterAll
  void tearDownClass() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  void harryPotterBook1() {
    driver.get("https://cover-bookstore.onrender.com/");
    driver.manage().window().setSize(new Dimension(550, 692));

    WebElement searchBar = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid=book-search-input]")));
    searchBar.click();
    searchBar.sendKeys("Harry");
    searchBar.sendKeys(Keys.ENTER);

    WebElement bookItem = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid=book-search-item]")));
    bookItem.click();

    js.executeScript("window.scrollTo(0,0)");

    WebElement bookTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid=book-title]")));
    bookTitle.click();

    List<WebElement> elements = driver.findElements(By.cssSelector("[data-testid=book-title]"));
    assertFalse(elements.isEmpty(), "Book title not found, search may have failed");
  }
}
