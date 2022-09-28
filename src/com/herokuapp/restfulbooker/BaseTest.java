package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest {

	protected RequestSpecification rspec;
	
	@BeforeMethod
	public void setUp() {	
		String URL_POST = "https://restful-booker.herokuapp.com/";
		
		rspec = new RequestSpecBuilder().setBaseUri(URL_POST).build();
	}
	
	protected Response createBookings() {
		JSONObject body = new JSONObject();
		//add content to be uploaded
		body.put("firstname", "Paul Yila");
		body.put("lastname", "Samuel");
		body.put("totalprice", "1000");
		body.put("depositpaid", "true");
		
		JSONObject bDates = new JSONObject();
		bDates.put("checkin", "2022-09-26");
		bDates.put("checkout", "2022-09-30");
		
		body.put("bookingdates", bDates);
		body.put("additionalneeds", "New Whip");
		
		Response response = RestAssured.given().spec(rspec).contentType(ContentType.JSON).
				body(body.toString()).post("booking/");
		
		return response;
	}
}
