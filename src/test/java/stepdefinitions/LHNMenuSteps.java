package stepdefinitions;

import DriverFactory.DriverFactory;
import automation.pageObjects.LHNMenuPage;
import common.AbstractPage;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static common.AbstractPage.customWait;

public class LHNMenuSteps {
    WebDriver driver = DriverFactory.getDriver();
    @Then("Get The all LHN Menu {}")
    public void getALlLhnMenu(String testCase) {
        customWait(5);
        WebDriver driver = DriverFactory.getDriver();
        LHNMenuPage lhnMenuPage = new LHNMenuPage(driver);
        List<String> lhnMenu = lhnMenuPage.getLHNMenu();
        if(lhnMenu.size()==0){
            customWait(3);
            lhnMenu = lhnMenuPage.getLHNMenu();
        }
        AbstractPage.lhnMenuMap.put(testCase,lhnMenu);
    }
}
