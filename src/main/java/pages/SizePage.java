package pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tinylog.Logger;

import java.time.Duration;
import java.util.List;

import static data.Constants.DRIVER_WAIT_SECONDS;

public class SizePage extends BasePage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = ".cmp-in-page-nav > .button > a")
    private List<WebElement> tireSizesHyperlinkList;

    public SizePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_SECONDS));
        PageFactory.initElements(driver, this);
    }

    public void closeCookieBanner() {
        verifyIfCookieBannerIsDisplayedAndClose(driver);
    }

    public int getAmountOfTireSizes() {
        return tireSizesHyperlinkList.size();
    }

    public void clickOnTireSizeAtIndex(int index) {
        Logger.info("Clicking on tire size at index " + index + ". Tire Size " + tireSizesHyperlinkList.get(index).getText());
        clickOnWebElementAtIndex(tireSizesHyperlinkList, index);
    }

    public String getTireSizeTextAtIndex(int index) {
        return tireSizesHyperlinkList.get(index).getText();
    }

    // The Expected Condition throws a TimeoutException if the list's elements are not found. The catch forces a "false".
    public boolean isTireSizesListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(tireSizesHyperlinkList));
            return true;
        } catch (TimeoutException te) {
            Logger.error("Tire diameter list does not exist or is empty. " + te);
            return false;
        }
    }

    // Used on verifyAssessmentTestV2
    /**
     * Method that return a String List of hyperlinks. If there are no web elements available the list will
     * be empty.
     *
     * @return List of Strings
     */
    public List<String> getAllSizeHyperlinks() {
        return getAllHyperlinksFromWebElementList(getAmountOfTireSizes(), tireSizesHyperlinkList);
    }

}