package com.insider.CaseStudy.API.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.junit.jupiter.api.extension.*;
import org.springframework.test.context.TestExecutionListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements BeforeAllCallback,AfterEachCallback, AfterAllCallback, TestExecutionExceptionHandler  {

	public ExtentHtmlReporter extentHtmlReporter;
	public ExtentReports extentReports;
	public ExtentTest extentTest;
	public String repName;

	@Override
	public void beforeAll(ExtensionContext context) {
		// Get the timestamp for the report
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-" + timeStamp + ".html";
		String reportPath = System.getProperty("tests.dir") + "\\reports\\" + repName; // Absolute path

		extentReports = new ExtentReports();
		extentHtmlReporter = new ExtentHtmlReporter (reportPath);
		extentHtmlReporter.config().setDocumentTitle("RestAssuredAutomationProject"); // Title of report
		extentHtmlReporter.config().setReportName("Pet Store Users API"); // name of the report
		extentHtmlReporter.config().setTheme(Theme.DARK);
		extentReports.attachReporter(extentHtmlReporter);
		extentHtmlReporter.config().setDocumentTitle("Extent Report");
		extentTest=extentReports.createTest("ExtentTest","Test Raporu");
	}

	@Override
	public void afterEach(ExtensionContext context) {
		extentTest=extentReports.createTest(context.getDisplayName());
        if (extentTest.getStatus()!=Status.FAIL){
            extentTest.createNode(context.getDisplayName());
            extentTest.log(Status.PASS, "Test Passed");
        }

	}


	@Override
	public void afterAll(ExtensionContext context) {
		extentReports.flush();
	}

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
            extentTest = extentReports.createTest(context.getDisplayName());
            extentTest.log(Status.FAIL, "Test Execution Exception");
            extentTest.log(Status.FAIL, throwable.getMessage());
            throw throwable;

	}


}
