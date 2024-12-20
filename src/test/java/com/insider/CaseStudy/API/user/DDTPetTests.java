package com.insider.CaseStudy.API.user;

import com.insider.CaseStudy.API.pages.Category;
import com.insider.CaseStudy.API.pages.Pet;
import com.insider.CaseStudy.API.pages.Tag;
import io.restassured.http.ContentType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class DDTPetTests extends Hooks {




    @ParameterizedTest
    @CsvSource({"1111,meow",
            "2222,Beagle_Eagle",
            "3333,Pepe",
            "4444,karabas",
            "5555,Hubert",
            "6666,Sharik"})
    public void setMultiplePet(int id, String name) {

        Tag tag=new Tag(1010, "crash");
        Category category=new Category(99,"flaky");
        Pet pet = new Pet(id,category, name, singletonList("null"), singletonList(tag), "available");
        given().log().all().
                contentType(ContentType.JSON).
                accept("application/json").
                when().
                body(pet).
                put("/pet").
                then().assertThat().
                statusCode(200).
                body("id", is(id),
                        "name", equalTo(name),
                        "status", equalTo("available")).
                log().all();

    }
    
    @ParameterizedTest
    @CsvSource({"1111,meow",
            "2222,Beagle_Eagle",
            "3333,Pepe",
            "4444,karabas",
            "5555,Hubert",
            "6666,Sharik"})
    public void getMultiplePet(int id, String expectedName) {

            given().log().all().
                accept("application/json").
            when().
                get("/pet/" + id).
            then().assertThat().
                statusCode(200).
                body("id", is(id),
                        "name", equalTo(expectedName),
                        "status", equalTo("available")).
                log().all();

    }

}
