package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteBookingTests extends BaseTest {
	
	@Test
	public void deleteBookingTest() {
		
		//create the booking
		Response response = createBookings();
				
		//return the id of the new booking
		int bookingId = response.jsonPath().getInt("bookingid");
		System.out.println(bookingId);
		
		//authenticate the user delete action
		Response deleteResponse = RestAssured.given().
					auth().preemptive().basic("admin","password123").delete(URL_POST + bookingId);
		
		int statusCOde = deleteResponse.getStatusCode();
		
		//verify there's a successful connection with a statusCode of 201
		Assert.assertEquals(statusCOde, 201, "Status code should be 201 not " + statusCOde);
		
		//run some other verifications
		Response getResponse = RestAssured.get(URL_POST+bookingId);
		getResponse.print();
		Assert.assertEquals(getResponse.getBody().asString(), "Not Found", "Content body should not be found!");
	}

}
