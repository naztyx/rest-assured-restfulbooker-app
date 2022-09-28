package com.herokuapp.restfulbooker;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetBookingsTest {
	
	String URL = "https://restful-booker.herokuapp.com/booking";
	
	@Test
	public void getBookingsIdWithoutFilterTest() {
		
		RequestSpecification rspec = new RequestSpecBuilder().setBaseUri(URL).build();
		
		//get booking id s response
		Response response = RestAssured.given(rspec).get();
		response.prettyPrint();
		
		//assert response is OK with response 200 statusCode
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should return 200!");
		
		//verify the returned list is not empty
		List<Integer> bookingIds = response.jsonPath().getList("bookingid");
		Assert.assertFalse(bookingIds.isEmpty(),"List of booking Ids should not be empy!");
	}
}
