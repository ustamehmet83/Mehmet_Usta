package com.insider.CaseStudy.API.user;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.insider.CaseStudy.API.endpoints.EndPoints;
import com.insider.CaseStudy.API.pages.Pet;
import com.insider.CaseStudy.API.pages.Store;
import com.insider.CaseStudy.API.utilities.ExtentReportManager;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
@ExtendWith(ExtentReportManager.class)
public class Hooks {

    public Response response;
    public Map<Object, Object> responseMap;
    public static int pet_id;
    public static int storeId;

    @BeforeAll
    public static void createSetup() {
        baseURI = EndPoints.base_url;
    }


}
