package com.insider.CaseStudy.API.tests;

import com.github.javafaker.Faker;
import com.insider.CaseStudy.API.endpoints.EndPoints;
import com.insider.CaseStudy.API.pages.Store;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class StoreFlow extends Hooks {

    /**************************************************************
     * The test expecting to create successfully a tests on the page
     *************************************************************/

    /*****************************************
     * Retrieve the current store inventory
     * GET METHOD
     ****************************************/
    //Store store=new Store(1, 222, 2, "2024-12-17T13:55:36.824+0000", "placed", true);
    private static Store store;

    @BeforeAll
    public static void setup() {
        Faker faker = new Faker();
        store = new Store();

        store.setId(faker.number().numberBetween(1, 1000));
        store.setPetId(faker.number().numberBetween(1, 1000));
        store.setQuantity(faker.number().numberBetween(1, 100));
        store.setShipDate("2024-12-17T13:55:36.824+0000");
        store.setStatus("placed");
        store.setComplete(true);

    }


    @Test
    public void test1() {
        response =
                given().log().all().
                        accept("application/json").
                        when().
                        get(EndPoints.storeInventory);

        Map<String, Object> storeInventory = response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all().extract().as(Map.class);
    }

    /*****************************************
     * Place a new order in the store
     * POST METHOD
     ****************************************/

    @Test
    @Order(1)
    public void test2() {


        Map<String, Object> response = given().
                contentType(ContentType.JSON).
                accept("application/json").log().all().
                body(store).
                when().
                post(EndPoints.storeOrder).
                then().assertThat().
                statusCode(200).
                body("id", is(store.getId()),
                        "petId", is(store.getPetId()),
                        "quantity", is(store.getQuantity())).log().all().extract().as(Map.class);


        storeId = (int) response.get("id");

    }

    /*****************************************
     * Retrieve the details of an order by ID
     * GET METHOD
     ****************************************/
    @Test
    @Order(2)
    public void test3() {
        given().log().all().
                accept("application/json").
                pathParam("orderId", store.getId()).
                when().
                get(EndPoints.storeOrderId).
                then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all().
                body("id", is(store.getId()),
                        "petId", is(store.getPetId()),
                        "quantity", is(store.getQuantity()),
                        "shipDate", is(store.getShipDate()),
                        "status", is(store.getStatus()),
                        "complete", is(store.isComplete())).log().all();

    }

    /*****************************************
     * Delete an order by ID
     * DELETE METHOD
     ****************************************/
    @Test
    @Order(3)
    public void test4() {
        given().
                accept("application/json").
                pathParam("orderId", store.getId())
                .when().
                delete(EndPoints.storeOrderId).
                then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all().
                body("code", is(200),
                        "type", is("unknown"),
                        "message", is(store.getId().toString()));

    }
}
