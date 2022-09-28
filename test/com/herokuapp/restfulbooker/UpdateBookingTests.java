package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateBookingTests extends BaseTest  {
	
	@Test
	public void updateBookingTests() {
		//create the booking
		Response createResponse = createBookings();
		createResponse.print();
		
		//return new booking id
		int bookingid = createResponse.jsonPath().getInt("bookingid");
		
		//update booking
		JSONObject body = new JSONObject();
		//add content to be uploaded
		body.put("firstname", "Maisada");
		body.put("lastname", "Sabo");
		body.put("totalprice", 5000);
		body.put("depositpaid", true);
		
		JSONObject bDates = new JSONObject();
		bDates.put("checkin", "2022-09-20");
		bDates.put("checkout", "2022-09-30");
		
		body.put("bookingdates", bDates);
		body.put("additionalneeds", "New House");
		//update the booking
		Response updateResponse = RestAssured.given().spec(rspec).
				auth().preemptive().basic("admin","password123").
				contentType(ContentType.JSON).
				body(body.toString()).put("booking/" + bookingid); 
		
		//run verification tests
		Assert.assertEquals(updateResponse.getStatusCode(), 200, "Status code should return 200!");
		
		SoftAssert sa = new SoftAssert();
		
		//get values of each returned JSon key
		String actualFName = updateResponse.jsonPath().getString("firstname");
		String actualLName = updateResponse.jsonPath().getString("lastname");
		int price = updateResponse.jsonPath().getInt("totalprice");
		boolean actualDPaid = updateResponse.jsonPath().getBoolean("depositpaid");
		String actualCheckin = updateResponse.jsonPath().getString("bookingdates.checkin");
		String actualCheckout = updateResponse.jsonPath().getString("bookingdates.checkout");
		String actualNeeds = updateResponse.jsonPath().getString("additionalneeds");
		
		//cross check all the fields with expected values 
		sa.assertEquals(actualFName, "Maisada");
		sa.assertEquals(actualLName, "Sabo");
		sa.assertEquals(price, 5000);
		sa.assertTrue(actualDPaid);
		sa.assertEquals(actualCheckin, "2022-09-20");
		sa.assertEquals(actualCheckout, "2022-09-30");
		sa.assertEquals(actualNeeds, "New House");
		
		//run all the assertions
		sa.assertAll();
	}

}
