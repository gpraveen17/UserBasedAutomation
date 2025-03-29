package Hooks;

import automation.util.FileType;
import automation.util.Util;
import common.AbstractPage;
import org.testng.annotations.BeforeSuite;

public class MasterHook {
    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        System.out.println("Setting up the test suite...");

        AbstractPage.browserName = Util.getValueFromResource("browserName", FileType.CAPABILITIES);
        AbstractPage.env  = Util.getValueFromResource("env", FileType.ENV_FILE);
        AbstractPage.url  = Util.getValueFromResource(AbstractPage.env+".URL", FileType.ENV_FILE);
    }
}
