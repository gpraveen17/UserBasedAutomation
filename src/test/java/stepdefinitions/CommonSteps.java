package stepdefinitions;

import DriverFactory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;

import static DriverFactory.DriverFactory.driver;
import static DriverFactory.DriverFactory.getDriver;
import static common.AbstractPage.customWait;

public class CommonSteps {
    @Given("Close All Process")
    public void closeALlProcess(){
        try {
            Process process = Runtime.getRuntime().exec("taskkill /F /IM msedgedriver.exe");
            process.waitFor();
        }catch (Exception e){

        }
    }
    @Before
    public void setUp() {
        System.out.println("Launching browser...");
        WebDriver driver = DriverFactory.getDriver();
        driver = getDriver();
        customWait(5);
        //driver.manage().window().maximize();
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver= null;
            System.out.println("Browser closed successfully after scenario");
        }
        customWait(2);
    }

}
