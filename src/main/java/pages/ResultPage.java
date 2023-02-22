package pages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tinylog.Logger;

import java.time.Duration;

import static data.Constants.DRIVER_WAIT_SECONDS;

public class ResultPage extends BasePage {

    private static final String RESULT_TEXT = " TIRE RESULTS";
    private static final String TIRE_COUNT_ZERO = "0";
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = ".tsr-profile__results")
    private WebElement tireResultText;

    @FindBy(css = ".tsr-profile__vehicle")
    private WebElement tireDiameterText;

    public ResultPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_SECONDS));
        PageFactory.initElements(driver, this);
    }

    public boolean isResultPageCurrent() {
        return driver.getCurrentUrl().contains("/catalog/size/");
    }

    public String getTireDiameter() {
        String url = getTireUrl();
        return url.substring(45, url.length() - 1);
    }

    public String getTireAmount() {
        try {
            wait.until(ExpectedConditions.visibilityOf(tireResultText));
            return StringUtils.replace(tireResultText.getText(), RESULT_TEXT, "");
        } catch (TimeoutException te) {
            Logger.info("EDGE CASE - Specific diameter has no stock available.");
            return TIRE_COUNT_ZERO;
        }
    }

    public String getTireUrl() {
        return driver.getCurrentUrl();
    }

}