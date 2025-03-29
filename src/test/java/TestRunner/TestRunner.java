package TestRunner;

import automation.util.SummaryReport;
import automation.util.Util;
import common.AbstractPage;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.text.SimpleDateFormat;
import java.util.Date;

import static common.AbstractPage.getUserDirectory;
import static common.AbstractPage.sp;


@CucumberOptions(
features ="src/test/resources/features",
glue = "stepdefinitions",
plugin = {"pretty", "html:target/cucumber-reports"},
monochrome = true,
tags = "@login"
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass(alwaysRun = true)
    public void setupSuite() {
        System.out.println("Setting up the test suite...");
        // Initialize resources or environment
    }
    @AfterClass(alwaysRun = true)
    public void tearDown(){
        System.out.println("******* tearDown ************");
        if("page".equalsIgnoreCase(AbstractPage.reportType)){
            String summaryReportString = SummaryReport.generateSummaryReport();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String Filename = getUserDirectory()+sp+"Reports"+sp+"Customized_BDD_Cucumber_Report_"+simpleDateFormat.format(new Date())+".html";
            SummaryReport.writeFiles(summaryReportString,Filename);
            Util.cleanUpFiles();

        }
    }
}



