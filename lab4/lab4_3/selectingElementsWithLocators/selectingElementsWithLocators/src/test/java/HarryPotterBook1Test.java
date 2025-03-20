import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HarryPotterBook1Test {
  private WebDriver driver;
  private JavascriptExecutor js;

  @BeforeAll
  void setupClass() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
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

    WebElement searchBar = driver.findElement(By.cssSelector("div:nth-child(1) > .Navbar_searchBar__3q5Gb > .Navbar_searchBarInput__w8FwI"));
    searchBar.click();
    searchBar.sendKeys("Harry");
    searchBar.sendKeys(Keys.ENTER);

    driver.findElement(By.cssSelector(".SearchList_bookCoverImage__1COZ9")).click();
    js.executeScript("window.scrollTo(0,0)");

    driver.findElement(By.cssSelector(".BookDetails_bookTitle__1eJ1S")).click();

    List<WebElement> elements = driver.findElements(By.cssSelector(".BookDetails_bookTitle__1eJ1S"));
    assertFalse(elements.isEmpty(), "Book title not found, search may have failed");
  }
}