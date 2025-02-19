package com.insider.CaseStudy.API.tests;

import com.insider.CaseStudy.API.endpoints.EndPoints;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

public class UserTests extends Hooks {

    File file = new File("src/test/resources/requestFile/createUser.json");
    File fileUpdate=new File("src/test/resources/requestFile/updateUser.json");
    JsonPath jsonPathFile = new JsonPath(file);
    JsonPath jsonPathFileUpdate = new JsonPath(fileUpdate);
    String username,email;
    int id;

    /**************************************************************
     * The test expecting to create successfully a tests on the page
     *************************************************************/
    @Test
    public void test1() {

        response =
                given().
                    contentType(ContentType.JSON).
                    accept("application/json").
                    body(file).
                when().
                    post(EndPoints.user);

        response.
                then().
                assertThat().
                    statusCode(200).
                    contentType(ContentType.JSON).
                    log().all().
                    body(
                        "message", is("5555"),
                        "code", is(200)).
                    body(
                            matchesJsonSchemaInClasspath("responseSchema/createUserSchema.json"));

    }

    /******************************
     * Get created tests credentials
     * GET METHOD
     *****************************/

    @Test
    @Order(1)
    public void test2() {
        username = jsonPathFile.getString("username");
        email = jsonPathFile.getString("email");
        id = jsonPathFile.getInt("id");
            given().
                accept("application/json").
                    pathParam("username",username)
            .when().
                get(EndPoints.username).
            then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all().
                body("id", is(id),
                        "username", is(username),
                        "email", is(email));

    }

    /**************************************
     * Update the created tests
     * PUT METHOD
     ********************************/
    @Test
    @Order(2)
    public void test3() {
        username = jsonPathFileUpdate.getString("username");
            given().
                contentType(ContentType.JSON).
                accept("application/json").
        pathParam("username",username).
                body(fileUpdate).
            when().
                put(EndPoints.username).
            then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all().
                body("message", is("4444"),
                        "code", is(200)).log().all();

    }

    /******************************
     * Get updated tests
     * GET METHOD
     *****************************/
    @Test
    @Order(3)
    public void test4() {
        username = jsonPathFileUpdate.getString("username");
        email = jsonPathFileUpdate.getString("email");
            given().
                accept("application/json").
                    pathParam("username",username)
                    .when().
                get(EndPoints.username).
            then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all().
                body("id", is(4444),
                        "username", is(username),
                        "email", is(email));

    }

    /***********************************
     * Delete the updated tests
     * DELETE METHOD
     **********************************/
    @Test
    @Order(4)
    public void test5() {
        username=jsonPathFileUpdate.getString("username");
            given().
                contentType(ContentType.JSON).
                accept("application/json").
                    pathParam("username",username)
            .when().
                delete(EndPoints.username).
            then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all().
                body("message", is(username),
                        "code", is(200)).log().all();

    }

    /*****************************************
     * Delete the first created tests
     * DELETE METHOD
     ****************************************/
    @Test
    @Order(5)
    public void test6() {

        username=jsonPathFile.getString("username");
            given().
                contentType(ContentType.JSON).
                accept("application/json").
                    pathParam("username",username)
            .when().
                delete(EndPoints.username).
            then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("message", is(username),
                        "code", is(200)).log().all();

    }

    /*****************************************
     * Create a list of users from a JSON file
     * POST METHOD
     ****************************************/
    @Test
    public void test7() {
        File file = new File("src/test/resources/requestFile/createUserList.json");
        response =
                given().
                        contentType(ContentType.JSON).
                        accept("application/json").
                        body(file).
                        when().
                        post(EndPoints.createWithList);

        response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all().
                body(
                        "message", is("ok"),
                        "code", is(200)).
                body(
                        matchesJsonSchemaInClasspath("responseSchema/createUserSchema.json"));

    }




}
