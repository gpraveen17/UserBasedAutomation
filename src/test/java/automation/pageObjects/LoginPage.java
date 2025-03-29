package automation.pageObjects;
import automation.util.FileType;
import automation.util.Util;
import common.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractPage {
    WebDriver driver;
    //private WebDriver driver = getDriver();
    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/div[2]/input")
    WebElement username;
    @FindBy(xpath = "//*[@type='password']")
    WebElement password;
    @FindBy(xpath = "//*[@type='submit']")
    WebElement submit;
    public LoginPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void logiPage(){
        username.sendKeys("Admin");
        password.sendKeys("admin123");
        submit.click();
    }
    public boolean loginPage(String user){
        boolean status = false;
        String userName = "";
        String passWord = "";
        String env = AbstractPage.env;
        AbstractPage.browserName = Util.getValueFromResource("browserName", FileType.CAPABILITIES);
        AbstractPage.env  = Util.getValueFromResource("env", FileType.ENV_FILE);
        AbstractPage.url  = Util.getValueFromResource(AbstractPage.env+".URL", FileType.ENV_FILE);
        if(AbstractPage.env.equalsIgnoreCase("qa")) {
            userName = Util.getValueFromResource(AbstractPage.env+"."+user+".userName", FileType.QA);
            passWord = Util.getValueFromResource(AbstractPage.env+"."+user+".password", FileType.QA);
        } else if(AbstractPage.env.equalsIgnoreCase("prod")){
            userName = Util.getValueFromResource(AbstractPage.env+"."+user+".userName", FileType.PROD);
            passWord = Util.getValueFromResource(AbstractPage.env+"."+user+".password", FileType.PROD);
        } else {
            System.out.println("Invalid Environment");
        }
        if(!userName.equalsIgnoreCase("") && !passWord.equalsIgnoreCase("")) {
//            username.sendKeys("Admin");
//            password.sendKeys("admin123");
            username.sendKeys(userName);
            password.sendKeys(passWord);
            submit.click();
            status = true;
        } else {
            System.out.println("Invalid User Name in " +env+ " property file");
        }
        return status;
    }
    public String validateTitle(){
        return driver.getTitle();
    }
}
