package com.insider.CaseStudy.API.user;


import com.insider.CaseStudy.API.pages.Category;
import com.insider.CaseStudy.API.pages.Tag;
import com.insider.CaseStudy.API.pages.Pet;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetFlow extends Hooks {
    Tag tag=new Tag(1010, "crash");
    Category category=new Category(99,"flaky");
    Pet pet = new Pet(9999,category, "ponpon", singletonList("null"), singletonList(tag), "available");

    Tag tagUpdate=new Tag(9090, "nope");
    Category categoryUpdate=new Category(88,"pluffy");
    Pet petUpdate = new Pet(8888,category, "tomtom", singletonList("picture"), singletonList(tag), "pending");



    /*************************************************
     * The Test creating a pet using the JSON file
     * The assertion using the map structure
     * POST METHOD
     *************************************************/

    @Test
    public void test1() {

        response =
                given().
                    contentType(ContentType.JSON).
                    accept("application/json").
                    body(new File("src/test/resources/requestFile/createPet.json")).
                when().
                    post("/pet");

        responseMap = response.body().as(Map.class);

        assertEquals( 9898,responseMap.get("id"));
        assertEquals( "fluffy",responseMap.get("name"));
        assertEquals( "available",responseMap.get("status"));
        pet_id = (int) responseMap.get("id");
    }


    /*************************************************
     * The Test creating a pet using the POJO Pet classes structure
     * The JSON file updated by the Pet pages
     * POST METHOD
     *************************************************/

    @Test
    public void test2() {


        /**
         * The constructor updating the request Json payload
         */


         given().
                contentType(ContentType.JSON).
                accept("application/json").
                body(pet).
         when().
                post("/pet").
         then().assertThat().log().all().
                statusCode(200).
                body("id", equalTo(pet.getId()),
                        "name",is(pet.getName()),
                        "status",is(pet.getStatus()),
                        "tags[0].id",is(tag.getId()));


    }

    /*************************************************
     * Created previous test's pet calling details
     * GET METHOD
     *************************************************/

    @Test
    public void test3(){
        File file = new File("src/test/resources/requestFile/createPet.json");
        JsonPath jsonPathFile = new JsonPath(file);
        String name = jsonPathFile.getString("name");
        String status = jsonPathFile.getString("status");
         given().log().all().
                accept("application/json").
         when().
            get("/pet/" + pet_id).
         then().assertThat().
            statusCode(200).
            body("id",is(pet_id),
                    "name",equalTo(name),
                    "status",equalTo(status)).
                log().all() ;

    }

    /*************************************************
     * Update previous test's created pet
     * PUT METHOD
     *************************************************/

    @Test
    public void test4(){

        response =
                given().
                    contentType(ContentType.JSON).
                    accept("application/json").
                    body(petUpdate).
                when().
                    put("/pet");

        response.
                then().assertThat().
                    statusCode(200).
                    body("id",is(petUpdate.getId()),
                    "name",equalTo(petUpdate.getName()),
                    "status",equalTo(petUpdate.getStatus())).
                        log().all();

        responseMap = response.body().as(Map.class);
        pet_id = (int) responseMap.get("id");

    }

    /*************************************************
     * Updated previous test's pet calling details
     * Validation has JSON Schema validation as well
     * GET METHOD
     *************************************************/

    @Test
    public void test5(){

        given().log().all().
                accept("application/json").
                when().
                get("/pet/" + pet_id).
                then().assertThat().
                statusCode(200).
                body("id",is(pet_id),
                        "name",equalTo(petUpdate.getName()),
                        "status",equalTo(petUpdate.getStatus())).
                body(
                        matchesJsonSchemaInClasspath("responseSchema/getUpdatedPetSchema.json")).
                log().all() ;

    }



    /*************************************************
     * Delete first created test's  pet
     * DELETE METHOD
     *************************************************/

    @Test
    public void test6(){


                given().
                        contentType(ContentType.JSON).
                        accept("application/json").
                when().
                        delete("/pet/"+pet_id).
                then().assertThat().
                        statusCode(200).
                        body("code",is(200),
                        "message",is(petUpdate.getId().toString())).
                        log().all();

    }
    /*************************************************
     * Get pets status by using queryParams
     * GET METHOD
     *************************************************/

    @Test
    public void test7(){


        JsonPath jsonPath = given().queryParams("status", "pending").
                contentType(ContentType.JSON).
                accept("application/json").
                when().
                get("/pet/findByStatus").
                then().assertThat().
                statusCode(200).
                log().all().extract().jsonPath();
        List<String> status = jsonPath.get("status");
        for (String s : status) {
            Assertions.assertEquals(s,"pending");
        }


    }






}
