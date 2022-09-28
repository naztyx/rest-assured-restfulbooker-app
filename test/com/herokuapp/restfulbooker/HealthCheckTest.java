package com.herokuapp.restfulbooker;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

public class HealthCheckTest extends BaseTest{
	
	//String URL = "https://restful-booker.herokuapp.com";
	
	@Test
	public void healthCheckTest() {
		
		given().spec(rspec).when().get("ping").then().assertThat().statusCode(201);
	}

}
