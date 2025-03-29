package DriverFactory;

import automation.util.FileType;
import automation.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.concurrent.TimeUnit;

import static automation.Constants.Constants.*;
import static common.AbstractPage.getUserDirectory;
import static common.AbstractPage.sp;

public class DriverFactory {
    public static WebDriver driver;
    public static WebDriver getDriver(){
    String browserName = Util.getValueFromResource("browserName", FileType.CAPABILITIES);
    String env = Util.getValueFromResource("env", FileType.ENV_FILE);
    String url = Util.getValueFromResource(env+".URL", FileType.ENV_FILE);
    if("Chrome".equalsIgnoreCase(browserName)) {
        try {
            String chrome_version = Util.getValueFromResource("chrome_version",FileType.CAPABILITIES);
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            if(null == driver) {
                try {
                    driver = new ChromeDriver();
                }catch (Exception e) {
                    System.out.println("Exception : "+e);
                    e.printStackTrace(); // Log the error to the console for debugging
                }
                driver.get(Util.getValueFromResource("URL",FileType.PROD));
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            }
        }catch(Exception e){

        }
    } else if ("edge".equalsIgnoreCase(browserName)) {
        try {
            //WebDriverManager.edgedriver().setup();
            String egeDriverPath=getUserDirectory()+sp+LIB+sp+edgeDriver;
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--remote-allow-origins=*");
            System.setProperty("webdriver.edge.driver",egeDriverPath);
            if(null == driver) {
                try {
                    driver = new EdgeDriver(options);
                }catch (Exception e) {
                    System.out.println("Exception : "+e);
                    e.printStackTrace(); // Log the error to the console for debugging
                }
                //driver.get(Util.getValueFromResource("URL",FileType.PROD));
                driver.get(url);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            }
        }catch(Exception e){

        }
    }
    return driver;
}
}
