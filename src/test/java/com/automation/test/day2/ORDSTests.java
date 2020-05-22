package com.automation.test.day2;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

public class ORDSTests {
    String BASE_URL = "http://3.90.112.152:1000/ords/hr";

    @Test
    @DisplayName("Get list of all employees")
    public void getAllEmpoyees(){
        //response can be saved in the Response object
        //prettyPeek() - method that prints response info in nice format
        //also ths method returns Response object
        //response contains body, header and status line
        //body (payload) - contains content that we requested from the web service
        //header - contains meta data
        Response response = given().baseUri(BASE_URL).when().get("/employees").prettyPeek();
        /**
         * RestAssured request has similar structure to BDD scenarios:
         * Start building the request part of the test
         *
         * given() - used for request setup and authentication
         * Syntactic sugar,
         * when() - to specify type of HTTP request: get, put, post, delete, patch, head, etc...
         * then() - to verify response, perform assertions
         */


    }
    @Test
    @DisplayName("Get employee under specific ID")
    public void getOneEmployee(){
        // in URL we can specify path and query parameters
        // path parameters are used to retrieve specific resource: for example 1 employee not all of them
        // {id} - path variable, that will be replace with a value after comma
        // after when() we specify HTTP request type/method/

        Response response = given().baseUri(BASE_URL).when().get("/employees/{id}",100).prettyPeek();

        // how we verify response? use assersions
        response.then().statusCode(200);

        int statusCode = response.statusCode();
        Assertions.assertEquals(200,statusCode);

    }

    @Test
    @DisplayName("Get list of all countries")
    public void getAllCountries(){
        Response response = given().baseUri(BASE_URL).when().get("/countries").prettyPeek();
        response.then().statusCode(200);
    }
}
