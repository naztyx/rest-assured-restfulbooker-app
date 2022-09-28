package com.herokuapp.restfulbooker;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class HealthCheckTest {
	
	String URL = "https://restful-booker.herokuapp.com/ping";
	@Test
	public void healthCheckTest() {
		given().when().get(URL).then().assertThat().statusCode(201);
	}

}
