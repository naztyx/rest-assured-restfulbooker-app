package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PartialUpdateBookingTests extends BaseTest {
	
	@Test
	public void partialUpdateBookingTest() {
		//create the booking
		Response response = createBookings();
		
		//return the id of the new booking
		int bookingId = response.jsonPath().getInt("bookingid");
		
		//JSon body for the partial update
		JSONObject body = new JSONObject();
		body.put("firstname", "Area Fada");
		
		JSONObject bookindates = new JSONObject();
		bookindates.put("checkin","2022-09-20");
		bookindates.put("checkout","2022-10-01");
		body.put("bookingdates", bookindates);
		
		//authenticate the user input
		Response updateResponse = RestAssured.given(rspec).
				auth().preemptive().basic("admin","password123").
				contentType(ContentType.JSON).
				body(body.toString()).patch("booking/" + bookingId);
		
		//verify there's a connection with a statusCode of 200
		int responsecode = response.getStatusCode();
		Assert.assertEquals(responsecode, 200, "Status code should be 200 not " + responsecode);
		
		//verify fields being updated
		String actualFName = updateResponse.jsonPath().getString("firstname");
		String actualLName = updateResponse.jsonPath().getString("lastname");
		int price = updateResponse.jsonPath().getInt("totalprice");
		boolean actualDPaid = updateResponse.jsonPath().getBoolean("depositpaid");
		String actualCheckin = updateResponse.jsonPath().getString("bookingdates.checkin");
		String actualCheckout = updateResponse.jsonPath().getString("bookingdates.checkout");
		String actualNeeds = updateResponse.jsonPath().getString("additionalneeds");
		
		SoftAssert sa = new SoftAssert();
		
		sa.assertEquals(actualFName, "Area Fada");
		sa.assertEquals(actualLName, "Samuel");
		sa.assertEquals(price, 1000);
		sa.assertTrue(actualDPaid);
		sa.assertEquals(actualCheckin, "2022-09-20");
		sa.assertEquals(actualCheckout, "2022-10-01");
		sa.assertEquals(actualNeeds, "New Whip");
		
		//run all the assertions
		sa.assertAll();
	}

}
