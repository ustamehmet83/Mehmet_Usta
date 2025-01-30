package com.insider.CaseStudy.API.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.junit.jupiter.api.extension.*;
import org.springframework.test.context.TestExecutionListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback, TestExecutionExceptionHandler {

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
        extentHtmlReporter.config().setReportName("Pet Store Users API");
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
        node.log(Status.FAIL, "Test Execution Exception");
        node.log(Status.FAIL, throwable.getMessage());
        throw throwable;

    }


}
