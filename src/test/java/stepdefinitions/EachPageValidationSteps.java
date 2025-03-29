package stepdefinitions;

import DriverFactory.DriverFactory;
import automation.pageObjects.EachPageValidation;
import common.AbstractPage;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static common.AbstractPage.*;

public class EachPageValidationSteps {

    @Given("Navigate to Each Page {}")
    public void navigateToEachPageValidation(String testCase){
        WebDriver driver = DriverFactory.getDriver();
        EachPageValidation eachPageValidation = new EachPageValidation(driver);
        List<String> lhnMenuList = eachPageValidation.getLHNMenuList(testCase);
        System.out.println(testCase +": lhnMenuList : "+lhnMenuList);
        if(driver!=null){
            //System.out.println("Driver is not null ");
        } else {
            System.out.println("Driver is null ");
            driver = DriverFactory.getDriver();
            eachPageValidation = new EachPageValidation(driver);
        }
        HashMap<String,HashMap<String,String>> pageStatusMap = eachPageValidation.navigateToEachPage(lhnMenuList);
        resultMap.put(testCase,pageStatusMap);
        System.out.println("resultMap : "+resultMap);
        Instant end = Instant.now();
        AbstractPage.endTime.put(testCase,end);
        AbstractPage.executionTime.put(testCase, Duration.between(startTime.get(testCase),endTime.get(testCase)).getSeconds());
    }

}
