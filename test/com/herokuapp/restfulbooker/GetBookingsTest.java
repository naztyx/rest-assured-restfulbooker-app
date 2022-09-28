package com.herokuapp.restfulbooker;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBookingsTest {
	
	String URL = "https://restful-booker.herokuapp.com/booking";
	
	@Test
	public void getBookingsIdWithoutFilterTest() {
		//get booking id s response
		Response response = RestAssured.get(URL);
		response.prettyPrint();
		
		//assert response is OK with response 200 statusCode
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should return 200!");
		
		//verify the returned list is not empty
		List<Integer> bookingIds = response.jsonPath().getList("bookingid");
		Assert.assertFalse(bookingIds.isEmpty(),"List of booking Ids should not be empy!");
	}
}
