package automation.pageObjects;

import DriverFactory.DriverFactory;
import automation.util.FileType;
import automation.util.Util;
import common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.HashMap;
import java.util.List;
import static automation.Constants.Constants.*;
import static common.AbstractPage.*;

public class EachPageValidation {
    //WebDriver driver = DriverFactory.getDriver();
    WebDriver driver;
    String xpath = "//*[text()='<<ReplaceText>>']";
    @FindBy(xpath = "//h6[text()='Administrator Access']")
    WebElement AdministratorAccess;
    @FindBy(xpath = "//button[text()=' Cancel ']")
    WebElement cancel;

    public EachPageValidation(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    public List<String> getLHNMenuList(String testCase){
        List<String> lhnMenuList = AbstractPage.lhnMenuMap.get(testCase);
        return lhnMenuList;
    }
    public HashMap<String,HashMap<String,String>> navigateToEachPage(List<String> lhnMenuList){
        HashMap<String,HashMap<String,String>> pageStatusMap = new HashMap<>();
        for(String pageName : lhnMenuList){
            HashMap<String,String> map = new HashMap<>();
            try {
                WebElement pageElement = AbstractPage.scrollToTheElement(xpath.replace("<<ReplaceText>>", pageName));
                pageElement.click();
                boolean titleStatus = false;
                if (pageName.equalsIgnoreCase("Maintenance")) {
                    titleStatus = verifyUserLandedOnPage(AdministratorAccess);
                    onClick(cancel);
                    minorWait();
                } else {
                    //break;
                    titleStatus = verifyUserLandedOnPage(pageName);
                }
                String fileName = "";
                String status = "failed";
                String error_meessage = "";

                if (titleStatus) {
                    status = "Passed";
                    error_meessage = NA;
                } else {
                    status = "failed";
                    error_meessage = "page is not loaded Properly";
                }
                fileName = AbstractPage.takeScreenShot();
                String screenShot = Util.getValueFromResource(SCREENSHOT, FileType.CAPABILITIES);
                if (screenShot.equalsIgnoreCase(TRUE)) {
                    map.put(FILENAME, fileName);
                } else {
                    map.put(FILENAME, fileName);
                }
                map.put(STATUS, status);
                map.put(ERROR_MESSAGE, error_meessage);

            }catch(Exception e){
                String fileName = AbstractPage.takeScreenShot();
                String screenShot = Util.getValueFromResource(SCREENSHOT, FileType.CAPABILITIES);
                if (screenShot.equalsIgnoreCase(TRUE)) {
                    map.put(FILENAME, fileName);
                } else {
                    map.put(FILENAME, fileName);
                }
                map.put(STATUS, "failed");
                map.put(ERROR_MESSAGE, "page is not loaded Properly");
            }
            pageStatusMap.put(pageName, map);
        }
        return pageStatusMap;
    }
    public boolean verifyUserLandedOnPage(String pageName){
        String title = "//h6[text()='<<ReplaceText>>']";
        boolean titlePresent = AbstractPage.isElementPresent(By.xpath(title.replace("<<ReplaceText>>",pageName)));
        if(titlePresent){
            return true;
        } else {
            return false;
        }
    }
    public boolean verifyUserLandedOnPage(WebElement pageName){
        boolean titlePresent = AbstractPage.isElementPresent(pageName);
        if(titlePresent){
            return true;
        } else {
            return false;
        }

    }
}
