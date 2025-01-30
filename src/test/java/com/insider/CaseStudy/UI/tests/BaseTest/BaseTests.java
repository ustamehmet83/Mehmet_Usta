package com.insider.CaseStudy.UI.tests.BaseTest;


import com.insider.CaseStudy.UI.utilities.BrowserUtils;
import com.insider.CaseStudy.UI.utilities.Driver;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;


public class BaseTests {

    protected static WebDriver driver;
    public static ThreadLocal<SoftAssertions> softAssertionsThread = new ThreadLocal<>();
    public static ThreadLocal<SoftAssertions> softAssertionsThreadTotal = new ThreadLocal<>();

    @BeforeAll
    public static void setUp() {
        driver = Driver.getDriver();
        softAssertionsThreadTotal.set(new SoftAssertions());
        BrowserUtils.clearScreenshotsFolder();
    }


    @AfterAll
    public static void tearDown() {
        Driver.closeDriver();
        BrowserUtils.getThreadAssert();
    }
}
