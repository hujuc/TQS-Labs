import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReserveFlightTest {
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
  void reserveFlight() {
    driver.get("https://blazedemo.com/");
    driver.manage().window().setSize(new Dimension(550, 691));

    WebElement fromPort = driver.findElement(By.name("fromPort"));
    fromPort.findElement(By.xpath("//option[. = 'São Paolo']")).click();

    WebElement toPort = driver.findElement(By.name("toPort"));
    toPort.findElement(By.xpath("//option[. = 'Dublin']")).click();

    driver.findElement(By.cssSelector(".btn-primary")).click();
    driver.findElement(By.cssSelector("tr:nth-child(4) .btn")).click();

    driver.findElement(By.id("inputName")).sendKeys("Hugo Castro");
    driver.findElement(By.id("address")).sendKeys("123 Rua X");
    driver.findElement(By.id("city")).sendKeys("Cidade");
    driver.findElement(By.id("state")).sendKeys("Estado");
    driver.findElement(By.id("zipCode")).sendKeys("4500-500");

    WebElement cardType = driver.findElement(By.id("cardType"));
    cardType.findElement(By.xpath("//option[. = 'American Express']")).click();

    driver.findElement(By.id("creditCardNumber")).sendKeys("100000000");
    driver.findElement(By.id("creditCardMonth")).sendKeys("10");
    driver.findElement(By.id("creditCardYear")).sendKeys("2025");
    driver.findElement(By.id("nameOnCard")).sendKeys("Hugo Castro");
    driver.findElement(By.id("rememberMe")).click();
    driver.findElement(By.cssSelector(".btn-primary")).click();

    List<WebElement> elements = driver.findElements(By.cssSelector("h1"));
    assertFalse(elements.isEmpty(), "Page header not found, reservation may have failed");
  }
}