import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumJupiter.class)
public class TravelAgencyTest {
    private static TravelAgencyPage travelAgencyPage;
    private static WebDriver driver;
    private static final String URL = "https://blazedemo.com/";

    @BeforeAll
    public static void setUp() {
        driver = new FirefoxDriver();
        travelAgencyPage = new TravelAgencyPage(driver);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void travelAgencyTest() {
        driver.get(URL);
        driver.manage().window().setSize(new Dimension(1800, 1000));

        travelAgencyPage.selectFromPort("São Paolo");
        travelAgencyPage.selectToPort("Dublin");
        travelAgencyPage.findFlights();
        travelAgencyPage.chooseFlight();

        travelAgencyPage.fillPassengerDetails("Hugo Castro", "123 Rua X", "Cidade", "Estado", "4500-500");
        travelAgencyPage.fillPaymentDetails("American Express", "100000000", "10", "2025", "Hugo Castro");
        travelAgencyPage.purchaseFlight();

        assertTrue(travelAgencyPage.isConfirmationPageDisplayed(), "The reservation confirmation page was not displayed.");
    }
}
