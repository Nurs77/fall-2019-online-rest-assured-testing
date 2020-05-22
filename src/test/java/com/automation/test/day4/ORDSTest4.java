package com.automation.test.day4;

import com.automation.test.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.baseURI;

public class ORDSTest4 {
    @BeforeAll
   public static void setup(){
        baseURI = ConfigurationReader.getProperty("ORDS.URI");


    }
    @Test
    public void employeesTest1(){
        given().accept(ContentType.JSON)
        .when().get("/employees").
                prettyPeek().
                then().assertThat()
        .statusCode(200).contentType(ContentType.JSON).time(lessThan(3L), TimeUnit.SECONDS);
    }
    @Test
    public void countryID(){
        given().accept(ContentType.JSON).queryParam("q","{\"country_id\":\"US\"}").
                        when().get("/countries").prettyPeek().
                then().assertThat().statusCode(200).contentType(ContentType.JSON).
                    body("items[0].country_name" ,is("United States of America"));
// second
        Response response = given().accept(ContentType.JSON)
                .when().get("/countries").prettyPeek();
        String countryName = response.jsonPath().getString("items.find{it.country_id == 'US'}.country_name");
        Map<String, Object> countryUS = response.jsonPath().get("items.find{it.country_id == 'US'}");
        List<String> countryNames = response.jsonPath().getList("items.findAll{it.region_id == 2}.country_name");


        System.out.println("countryName = " + countryName);
        System.out.println(countryUS);
        System.out.println(countryNames);
        for (Map.Entry<String,Object> entry: countryUS.entrySet()){
            System.out.printf("key=%s, value = %s\n",entry.getKey(),entry.getValue());
        }

    }
    @Test
    public void getEmployeeTest(){
        Response response = when().get("/employees").prettyPeek();
        Map<String,?> bestEmployee = response.jsonPath().get("items.max{it.salary}");
        Map<String,?> poorEmployee = response.jsonPath().get("items.min{it.salary}");
        int companiesPayroll = response.jsonPath().get("items.collect{it.salary}.sum()");
        System.out.println(bestEmployee);
        System.out.println(poorEmployee);
        System.out.println(companiesPayroll);
    }

    @Test
    @DisplayName("")
    public  void testSalary(){
        when().get("/employees").then().assertThat().statusCode(200).body("items.salary",everyItem(greaterThan(0))).log().ifError();
    }
    @Test
    public void phoneCHeck(){
        Response response = when().get("/employees/{id}",101).prettyPeek();
        response.then().assertThat().statusCode(200);
        String expected = "515-123-4568";
        String actual = response.jsonPath().getString("phone_number").replace(".","-");

        assertEquals(200,response.statusCode());
        assertEquals(expected,actual);

    }

}
