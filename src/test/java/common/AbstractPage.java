package common;

import DriverFactory.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.io.File;
import static automation.Constants.Constants.*;

public class AbstractPage {
    public static String reportType = "";
    public static String browserName = "";
    public static String env = "";
    public static String url = "";
    public static final String sp = File.separator;
    public static HashMap<String,String> userTestCaseMap = new HashMap<>();
    public static WebDriver driver;
    public static LinkedHashMap<String,HashMap<String,HashMap<String,String>>> resultMap = new LinkedHashMap<>();
    public static HashMap<String, Instant> startTime = new HashMap<>();
    public static HashMap<String, Instant> endTime = new HashMap<>();
    public static HashMap<String, Long> executionTime = new HashMap<>();
    public static HashMap<String,List<String>> lhnMenuMap = new HashMap<>();
    public static WebDriver getDriver(){
        return  DriverFactory.getDriver();
    }
    public static String getUserDirectory(){
        return System.getProperty("user.dir");
    }
    public AbstractPage(WebDriver driver){
        this.driver = DriverFactory.getDriver();
    }
    public static WebElement scrollToTheElement(String StringElement) {
        WebElement element = null;
        try{
            element = driver.findElement(By.xpath(StringElement));
        } catch(Exception e){
            System.out.println("Element not found trying again");
            customWait(3);
            element = driver.findElement(By.xpath(StringElement));
        }
        // Use JavaScript to scroll to the element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }
    public static boolean isElementPresent(By element){
        boolean isPresent = driver.findElements(element).size()>0;
        return isPresent;
    }
    public static boolean isElementPresent(WebElement element){
        boolean isPresent = element.isDisplayed();
        return isPresent;
    }
    public static void onClick(WebElement element){
        element.click();
    }

    public static String takeScreenShot(){
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = getUserDirectory()+sp+SCREENSHOT+ sp+ timestamp + PNG;
        File destFile = new File(filePath);

        // Copy file to destination
        try {
            FileUtils.copyFile(srcFile, destFile);
        }catch (Exception e){

        }
        return filePath;
    }
    public static void minorWait(){
        try{
            Thread.sleep(2000);
        } catch (Exception e){

        }
    }
    public static void customWait(int seconds){
        try{
            Thread.sleep(seconds*1000);
        } catch (Exception e){

        }
    }
}
