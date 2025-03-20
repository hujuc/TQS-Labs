import static io.github.bonigarcia.seljup.BrowserType.CHROME;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class DockerBrowserTest {

    @Test
    void testChromeDocker(@DockerBrowser(type = CHROME) WebDriver driver) {
        driver.get("https://www.example.com");
        assertThat(driver.getTitle()).contains("Example Domain");
    }

}
