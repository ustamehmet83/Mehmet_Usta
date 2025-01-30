package com.insider.CaseStudy.API.user;


import com.github.javafaker.Faker;
import com.insider.CaseStudy.API.endpoints.EndPoints;
import com.insider.CaseStudy.API.pages.Category;
import com.insider.CaseStudy.API.pages.Store;
import com.insider.CaseStudy.API.pages.Tag;
import com.insider.CaseStudy.API.pages.Pet;
import com.insider.CaseStudy.API.utilities.ExtentReportManager;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetFlow extends Hooks {

    private static Tag tag;
    private static Category category;
    private static Pet pet;
    private static Tag tagUpdate;
    private static Category categoryUpdate;
    private static Pet petUpdate;

    @BeforeAll
    public static void setup() {
        Faker faker = new Faker();
        List<String> tags = Arrays.asList("vaccinated", "playful", "adopted", "trained", "friendly", "energetic", "reserved");
        tag = new Tag();
        tag.setId(faker.number().numberBetween(1,1000));
        tag.setName(tags.get(faker.random().nextInt(tags.size())));

        List<String> categories = Arrays.asList("dog", "cat", "bird", "rabbit", "fish", "hamster", "turtle");
        category = new Category();
        category.setId(faker.number().numberBetween(1,1000));
        category.setName(categories.get(faker.random().nextInt(categories.size())));


        pet = new Pet();
        pet.setId(faker.number().numberBetween(1,10000));
        pet.setCategory(category);
        pet.setName(faker.animal().name());
        pet.setPhotoUrls(singletonList("null"));
        pet.setTags(singletonList(tag));
        pet.setStatus("available");

        tagUpdate = new Tag();
        tagUpdate.setId(faker.number().numberBetween(1,1000));
        tagUpdate.setName(tags.get(faker.random().nextInt(tags.size())));

        categoryUpdate = new Category();
        categoryUpdate.setId(faker.number().numberBetween(1,1000));
        categoryUpdate.setName(categories.get(faker.random().nextInt(categories.size())));

        petUpdate = new Pet();
        petUpdate.setId(faker.number().numberBetween(1,10000));
        petUpdate.setCategory(category);
        petUpdate.setName(faker.animal().name());
        petUpdate.setPhotoUrls(singletonList("null"));
        petUpdate.setTags(singletonList(tag));
        petUpdate.setStatus("available");
    }

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
                        post(EndPoints.pet);

        responseMap = response.body().as(Map.class);

        assertEquals(9898, responseMap.get("id"));
        assertEquals("fluffy", responseMap.get("name"));
        assertEquals("available", responseMap.get("status"));
        pet_id = (int) responseMap.get("id");
    }


    /*************************************************
     * The Test creating a pet using the POJO Pet classes structure
     * POST METHOD
     *************************************************/

    @Test
    public void test2() {

        given().
                contentType(ContentType.JSON).
                accept("application/json").
                body(pet).
                when().
                post(EndPoints.pet).
                then().assertThat().log().all().
                statusCode(200).
                body("id", equalTo(pet.getId()),
                        "name", is(pet.getName()),
                        "status", is(pet.getStatus()),
                        "tags[0].id", is(tag.getId()));


    }

    /*************************************************
     * Created previous test's pet calling details
     * GET METHOD
     *************************************************/

    @Test
    public void test3() {
        File file = new File("src/test/resources/requestFile/createPet.json");
        JsonPath jsonPathFile = new JsonPath(file);
        String name = jsonPathFile.getString("name");
        String status = jsonPathFile.getString("status");
        given().log().all().
                accept("application/json")
                .pathParam("petId", pet_id)
                .when().
                get(EndPoints.petId)
                .then().assertThat().
                statusCode(200).
                body("id", is(pet_id),
                        "name", equalTo(name),
                        "status", equalTo(status)).
                log().all();

    }

    /*************************************************
     * Update previous test's created pet
     * PUT METHOD
     *************************************************/

    @Test
    public void test4() {

        response =
                given().
                        contentType(ContentType.JSON).
                        accept("application/json").
                        body(petUpdate).
                        when().
                        put(EndPoints.pet);

        response.
                then().assertThat().
                statusCode(200).
                body("id", is(petUpdate.getId()),
                        "name", equalTo(petUpdate.getName()),
                        "status", equalTo(petUpdate.getStatus())).
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
    public void test5() {

        given().log().all().
                accept("application/json")
                .pathParam("petId", pet_id)
                .when().
                get(EndPoints.petId).
                then().assertThat().
                statusCode(200).
                body("id", is(pet_id),
                        "name", equalTo(petUpdate.getName()),
                        "status", equalTo(petUpdate.getStatus())).
                body(
                        matchesJsonSchemaInClasspath("responseSchema/getUpdatedPetSchema.json")).
                log().all();

    }


    /*************************************************
     * Delete first created test's  pet
     * DELETE METHOD
     *************************************************/

    @Test
    public void test6() {


        given().
                contentType(ContentType.JSON).
                accept("application/json")
                .pathParam("petId", pet_id)
                .when().
                delete(EndPoints.petId).
                then().assertThat().
                statusCode(200).
                body("code", is(200),
                        "message", is(petUpdate.getId().toString())).
                log().all();

    }

    /*************************************************
     * Get pets status by using queryParams
     * GET METHOD
     *************************************************/

    @Test
    public void test7() {


        JsonPath jsonPath = given().queryParams("status", "pending").
                contentType(ContentType.JSON).
                accept("application/json").
                when().
                get(EndPoints.findByStatus).
                then().assertThat().
                statusCode(200).
                log().all().extract().jsonPath();
        List<String> status = jsonPath.get("status");
        for (String s : status) {
            Assertions.assertEquals(s, "pending");
        }


    }


}
