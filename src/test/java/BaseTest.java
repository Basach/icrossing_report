import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.tinylog.Logger;

import java.time.Duration;

import static data.Constants.PAGE_LOAD_TIME_OUT_SECONDS;

public class BaseTest {

    private static final String BASE_URL = "https://www.bridgestonetire.com/size";
    WebDriver driver;

    protected WebDriver initDriverAndOpenUrl() {

        Logger.info("Initializing Headless Chrome WebDriver.");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless", "--window-size=1920,4000");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIME_OUT_SECONDS));

        browseToBaseUrl();
        return driver;
    }

    protected void browseToBaseUrl() {
        Logger.info("Navigating to BASE URL");
        driver.get(BASE_URL);
    }

    protected void quitDriver() {
        Logger.info("Tearing Down WebDriver.");
        driver.quit();
    }

}