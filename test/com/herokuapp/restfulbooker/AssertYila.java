package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AssertYila {

	String URL = "https://restful-booker.herokuapp.com/booking/29848";
	@Test
	public void assertYila() {

		Response response = RestAssured.get(URL);
		response.prettyPrint();
				
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should return 200!");
		
		SoftAssert sa = new SoftAssert();
		
		//get values of each returned JSon key
		String actualFName = response.jsonPath().getString("firstname");
		String actualLName = response.jsonPath().getString("lastname");
		int price = response.jsonPath().getInt("totalprice");
		boolean actualDPaid = response.jsonPath().getBoolean("depositpaid");
		String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
		String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
		String actualNeeds = response.jsonPath().getString("additionalneeds");
		
		//cross check all the fields with expected values 
		sa.assertEquals(actualFName, "Paul Yila");
		sa.assertEquals(actualLName, "Samuel");
		sa.assertEquals(price, 1000);
		sa.assertTrue(actualDPaid);
		sa.assertEquals(actualCheckin, "2022-09-26");
		sa.assertEquals(actualCheckout, "2022-09-30");
		sa.assertEquals(actualNeeds, "New Whip");
		
		//run all the assertions
		sa.assertAll();
	}
}
