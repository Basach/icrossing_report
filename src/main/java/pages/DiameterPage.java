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

public class DiameterPage extends BasePage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = ".tires-by-diameter__links")
    private WebElement diameterSizesContainer;

    @FindBy(css = ".tires-by-diameter__links > li > a")
    private List<WebElement> diameterSizesList;

    public DiameterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_SECONDS));
        PageFactory.initElements(driver, this);
    }

    public boolean isDiameterPageCurrent() {
        return driver.getCurrentUrl().contains("-inch/");
    }

    public String getTireDiameterAtIndex(int index) {
        return diameterSizesList.get(index).getText();
    }

    // The Expected Condition throws a TimeoutException if the list's elements are not found. The catch forces a "false".
    public boolean isSpecificTireDiameterListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(diameterSizesList));
            return true;
        } catch (TimeoutException te) {
            Logger.error("Specific Tire Diameter list does not exist or is empty. " + te);
            return false;
        }
    }

    public int getAmountOfDiameterSizes() {
        return diameterSizesList.size();
    }

    public void clickOnDiameterSizeAtIndex(int index) {
        System.out.println("INFO :: Clicking on diameter size at index " + index);
        clickOnWebElementAtIndex(diameterSizesList, index);
    }

    // Used on verifyAssessmentTestV2
    /**
     * Method that return a String List of hyperlinks. If there are no web elements available the list will
     * be empty.
     *
     * @return List of Strings
     */
    public List<String> getAllDimensionHyperlinks() {
        return getAllHyperlinksFromWebElementList(getAmountOfDiameterSizes(), diameterSizesList);
    }

}