package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class BasePage {

    /**
     * Method used to close the cookie banner from any page.
     * @param driver
     */
    protected void verifyIfCookieBannerIsDisplayedAndClose(WebDriver driver) {
        WebElement element = driver.findElement(By.className("cc-close__btn"));
        if (element.isDisplayed()){
            Logger.info("Closing Cookie banner.");
            element.click();
        }
    }

    /**
     * Method used to click on a web element from a list.
     * @param elementList
     * @param index
     */
    protected void clickOnWebElementAtIndex(List<WebElement> elementList, int index) {
        WebElement element = elementList.get(index);
        element.click();
    }

    /**
     * Method used to get a list of hyperlinks from a web element list. If there are no web elements
     * the method will return an empty list,
     * @param sizeOfList
     * @param webElementList
     * @return List of Strings
     */
    protected List<String> getAllHyperlinksFromWebElementList(int sizeOfList, List<WebElement> webElementList) {

        List<String> hyperlinkList = new ArrayList<>();
        if(sizeOfList > 0) {
            for (WebElement element : webElementList) {
                hyperlinkList.add(element.getAttribute("href"));
            }
        } else {
            Logger.info("List is empty.");
        }
        return hyperlinkList;
    }

}