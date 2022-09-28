package com.herokuapp.restfulbooker;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BaseTest {

	String URL_POST = "https://restful-booker.herokuapp.com/booking/";
	
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
		
		Response response = RestAssured.given().contentType(ContentType.JSON).
				body(body.toString()).post(URL_POST);
		
		return response;
	}
}
