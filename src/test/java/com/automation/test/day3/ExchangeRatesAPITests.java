package com.automation.test.day3;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


public class ExchangeRatesAPITests {

    @BeforeAll
    public static void setup(){
        //for every single request this is a base URI
        baseURI = "http://api.openrates.io";

    }
    // get latest currency rates
    @Test
    public void getLatestRates(){
        // after ? we specify query parameters. If there are couple of them we use & to concatenate them
        //http://www.google.com/index.html?q=apple&zip=123123
        //q - query parameter
        //zip - another query parameter
        //with rest assured, we provide query parameters into given() part.
        //give() - request preparation
        //you can specify query parameters in URL explicitly: http://api.openrates.io/latest?base=USD
        //rest assured, will just assemble URL for you
        Response response = given().
                                queryParam("base", "USD").
                            when().
                                get("/latest").prettyPeek();

        //verify that GET request to the endpoint was successful
        Headers headers = response.getHeaders();
        String contentType = headers.getValue("Content-Type");
        System.out.println(contentType);

        response.then().statusCode(200);
        response.then().assertThat().body("base", is("USD"));

        //let's verify that response contains today's date
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        response.then().assertThat().body("date", containsString(date));
        //is - same as equals

    }
    @Test
    public void getHistoryOfRates(){
        Response response = given().
                queryParam("base", "USD").
                when().
                get("/2008-01-01").prettyPeek();
        Headers headers = response.getHeaders();
        response.then().assertThat().statusCode(200).and().body("date", is("2008-01-02")).and().body("rates.USD",is(0.6808279));
        float param = response.jsonPath().get("rates.EUR");
        System.out.println(param);


    }
}
