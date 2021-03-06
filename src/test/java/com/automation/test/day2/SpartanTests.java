package com.automation.test.day2;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class SpartanTests {
    String BASE_URL = "http://3.90.112.152:8000";
    @Test
    @DisplayName("Get list of all spartans")
    public void getAllSpartans(){
        // 401 UNAUTHORIZED
        // how to provide credentials?
        // there are different types of authentication: basic, oath, oath 2.0, api key, bearer token etc
        // spartan apps requires basic authentication: username and password

        given().
                auth().basic("admin","admin").
                baseUri(BASE_URL).
                when().
                get("/api/spartans").prettyPeek().then().statusCode(200);

    }

    @Test
    @DisplayName("Add new spartan")
    public void addSpartan() {
        //JSON supports different data types: string, integer, boolean
        String body = "{\"gender\": \"Male\", \"name\": \"Random User\", \"phone\": 9999999999}";
        //instead of string variable, we can use external JSON file
        //use File class to read JSON and pass it into body
        //provide path to the JSON as a parameter
        File jsonFile = new File(System.getProperty("user.dir") + "/spartan.json");
        //to create new item, we perform POST request
        //contentType(ContentType.JSON) - to tell web service what kind of media type we send
        given().
                contentType(ContentType.JSON).
                auth().basic("admin", "admin").
                body(jsonFile).
                baseUri(BASE_URL).
                when().
                post("/api/spartans").prettyPeek().
                then().
                statusCode(201);
    }

    @Test
    @DisplayName("delete one spartan")
    public void deleteSpartan(){

    }
}
