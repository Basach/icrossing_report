import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tinylog.Logger;
import pages.DiameterPage;
import pages.ResultPage;
import pages.SizePage;

import static data.Constants.FAILURE_TIRE_SIZE_LIST_MISSING;
import static utils.Utilities.*;

public class AssessmentTest extends BaseTest {

    SizePage sizePage;
    DiameterPage diameterPage;
    ResultPage resultPage;
    WebDriver driver;
    long startTimeMillis;

    @BeforeMethod
    public void setup() {

        startTimeMillis = System.currentTimeMillis();
        driver = initDriverAndOpenUrl();

        sizePage = new SizePage(driver);
        sizePage.closeCookieBanner();
    }

    /**
     * Approach where user clicks on each Size option, and then on each Diameter option
     * to collect the information from the result page.
     */
    @Test
    public void verifyAssessmentTestV1() {

        Assert.assertTrue(sizePage.isTireSizesListDisplayed(), FAILURE_TIRE_SIZE_LIST_MISSING);

        SoftAssert softAssert = new SoftAssert();
        createCSVReport(String.format("tire_report_v1_%s.csv", startTimeMillis));

        int amountTireSizes = sizePage.getAmountOfTireSizes();
        for (int i = 0; i < amountTireSizes; i++) {

            String tireSizeText = sizePage.getTireSizeTextAtIndex(i);
            sizePage.clickOnTireSizeAtIndex(i);

            diameterPage = new DiameterPage(driver);
            softAssert.assertTrue(diameterPage.isDiameterPageCurrent(), "FAILURE :: Size redirect failed for " + tireSizeText);

            boolean diameterListDisplayed = diameterPage.isSpecificTireDiameterListDisplayed();

            int countDiameters = 0;
            if (diameterListDisplayed) {
                countDiameters = diameterPage.getAmountOfDiameterSizes();
            } else {
                Logger.info("No tire dimensions available. Skipping this size.");
            }

            for (int j = 0; j < countDiameters; j++) {

                Logger.info("Verifying Tire Diameter at index " + j);
                String tireDiameter = diameterPage.getTireDiameterAtIndex(j);
                diameterPage.clickOnDiameterSizeAtIndex(j);

                resultPage = new ResultPage(driver);
                softAssert.assertTrue(resultPage.isResultPageCurrent(), "FAILURE :: Size redirect failed for " + tireDiameter);

                updateCSVReport(new String[]{tireDiameter, resultPage.getTireAmount(), resultPage.getTireUrl()});

                Logger.info("Returning to previous page.");
                driver.navigate().back();
                diameterPage = new DiameterPage(driver);
            }

            browseToBaseUrl();
            sizePage = new SizePage(driver);
        }

        updateCSVReport(new String[]{getRunTimeMeasure(startTimeMillis, System.currentTimeMillis())});
        saveCSVReport();
        softAssert.assertAll();

    }

    /**
     * Approach where all Size hyperlinks are stored in a list. Urls are hit directly to access the Diameter page.
     * Then those Diameters hyperlinks are stored in a list once more to be hit directly and access the result page
     * to get the water information.
     */
    @Test
    public void verifyAssessmentTestV2() {

        Assert.assertTrue(sizePage.isTireSizesListDisplayed(), FAILURE_TIRE_SIZE_LIST_MISSING);

        createCSVReport(String.format("tire_report_v2_%s.csv", startTimeMillis));

        for (String sizeUrl : sizePage.getAllSizeHyperlinks()) {

            Logger.info("Verifying Tire Diameter - " + sizeUrl);
            driver.get(sizeUrl);

            diameterPage = new DiameterPage(driver);

            for (String diameterUrl : diameterPage.getAllDimensionHyperlinks()) {

                Logger.info("Verifying Tire Diameter - " + diameterUrl);
                driver.get(diameterUrl);

                resultPage = new ResultPage(driver);
                updateCSVReport(new String[]{resultPage.getTireDiameter(), resultPage.getTireAmount(), resultPage.getTireUrl()});
            }
            sizePage = new SizePage(driver);
        }
        updateCSVReport(new String[]{getRunTimeMeasure(startTimeMillis, System.currentTimeMillis())});
        saveCSVReport();
        Assert.assertTrue(true);
    }

    @AfterMethod
    public void tearDown() {

        quitDriver();
    }

}