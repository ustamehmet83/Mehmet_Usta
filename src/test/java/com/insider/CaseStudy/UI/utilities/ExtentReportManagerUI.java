package com.insider.CaseStudy.UI.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManagerUI implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback, TestExecutionExceptionHandler {

    public ExtentHtmlReporter extentHtmlReporter;
    public ExtentReports extentReports;
    public ExtentTest extentTest;
    public ExtentTest node;
    public String repName;

    @Override
    public void beforeAll(ExtensionContext context) {

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";
        String reportPath = System.getProperty("user.dir") + "\\reports\\" + repName;

        extentReports = new ExtentReports();
        extentHtmlReporter = new ExtentHtmlReporter(reportPath);
        extentHtmlReporter.config().setDocumentTitle("InsiderCaseStudy");
        extentHtmlReporter.config().setReportName("Pet Store Users UI");
        extentHtmlReporter.config().setTheme(Theme.DARK);

        extentReports.attachReporter(extentHtmlReporter);
        extentHtmlReporter.config().setDocumentTitle("Extent Report");
        extentTest = extentReports.createTest("ExtentTest", "Test Report");

    }

    @Override
    public void beforeEach(ExtensionContext context) {
        node = extentTest.createNode(context.getDisplayName());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (node.getStatus() != Status.FAIL) {
            node.log(Status.PASS, "Test Passed");
        }
    }


    @Override
    public void afterAll(ExtensionContext context) {
        extentReports.flush();
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        node.log(Status.FAIL, "Test Execution Exception");
        node.log(Status.FAIL, throwable.getMessage());
        node.log(Status.FAIL,"ScreenShot "+ timestamp,
                captureScreenShot(Driver.getDriver()).build());
        throw throwable;

    }

    public static MediaEntityBuilder captureScreenShot(WebDriver driver) throws IOException {
            String screenshotFile = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
            return MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotFile);
    }


}
