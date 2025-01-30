package com.insider.CaseStudy.API.tests;

import com.insider.CaseStudy.API.endpoints.EndPoints;
import com.insider.CaseStudy.API.utilities.ExtentReportManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

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
