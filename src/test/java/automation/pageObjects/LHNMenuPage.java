package automation.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static common.AbstractPage.customWait;

public class LHNMenuPage {
    WebDriver driver ;
    @FindBy(xpath="//*[@class='oxd-main-menu']/li")
    WebElement lhnMenu;

    public LHNMenuPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    public List<String> getLHNMenu(){
        customWait(5);
        List<String> lhnMenu = new ArrayList<>();
        List<WebElement> elementList = driver.findElements(By.xpath("//*[@class='oxd-main-menu']/li"));
        for(WebElement e : elementList){
            lhnMenu.add(e.getText());
        }
        List<String> lhnMenu1 = elementList.stream().map(e->e.getText()).toList();
        return lhnMenu;
    }
}
