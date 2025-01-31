package com.insider.CaseStudy.UI.tests.BaseTest;


import com.insider.CaseStudy.API.utilities.ExtentReportManager;
import com.insider.CaseStudy.UI.utilities.BrowserUtils;
import com.insider.CaseStudy.UI.utilities.Driver;
import com.insider.CaseStudy.UI.utilities.ExtentReportManagerUI;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

@ExtendWith(ExtentReportManagerUI.class)
public class BaseTests {

    protected static WebDriver driver;
    public static ThreadLocal<SoftAssertions> softAssertionsThread = new ThreadLocal<>();
    public static ThreadLocal<SoftAssertions> softAssertionsThreadTotal = new ThreadLocal<>();

    @BeforeAll
    public static void setUp() {
        driver = Driver.getDriver();
        BrowserUtils.clearScreenshotsFolder();
        BrowserUtils.clearReportsFolder();
    }

    @BeforeEach
    public  void setUpEach() {
        softAssertionsThreadTotal.set(new SoftAssertions());
    }


    @AfterAll
    public static void tearDown() {
        Driver.closeDriver();
    }
}
