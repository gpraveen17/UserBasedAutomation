package stepdefinitions;

import DriverFactory.DriverFactory;
import automation.pageObjects.LoginPage;
import common.AbstractPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.time.Instant;
import java.util.HashMap;

import static automation.Constants.Constants.*;
import static common.AbstractPage.resultMap;

public class LoginSteps {

    @Given("{} User is on the login page {}")
    public void user_is_ont_the_login_page(String user,String testCase){
        Instant start = Instant.now();
        AbstractPage.startTime.put(testCase,start);
        WebDriver driver = DriverFactory.getDriver();
        LoginPage login  = new LoginPage(driver);
        boolean loginStatus = login.loginPage(user);
        HashMap<String, HashMap<String,String>> pageStatusMap = new HashMap<>();
        if(loginStatus){
            System.out.println("SuccessFully user Logged in ");
        } else {
            resultMap.put(testCase,pageStatusMap);
            System.out.println("User not Logged in ");
        }
        if(testCase.contains("TC")){
            AbstractPage.userTestCaseMap.put(testCase,user);
            AbstractPage.reportType="page";
        }
    }

    @When("User Enter valid credentials {}")
    public void user_enters_valid_credentials(String testCase) {
        WebDriver driver = DriverFactory.getDriver();
        LoginPage login  = new LoginPage(driver);
        String actualTitle= login.validateTitle();
        Assert.assertEquals(actualTitle,ExpectedTitle,"User Not Logged in Properly");
    }

    @Then("User should be redirected to dashboard Page {}")
    public void user_should_be_redirected_to_the_dashboard(String testCase) {
        System.out.println("Dahsboard");
        // Code to verify if the user is redirected to the dashboard
    }

}
