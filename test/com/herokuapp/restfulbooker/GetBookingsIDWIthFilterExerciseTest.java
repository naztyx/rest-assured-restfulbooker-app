package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBookingsIDWIthFilterExerciseTest {
	
	String URL = "https://restful-booker.herokuapp.com/booking/6426";
	//String URL = "https://restful-booker.herokuapp.com/booking?firstname=Abiola&lastname=Lamidi";
	@Test
	public void getBookingsIDWIthFilterExerciseTest() {
		//get booking ids response for first 10 bookings
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
		sa.assertEquals(actualFName, "Abiola");
		sa.assertEquals(actualLName, "Lamidi");
		sa.assertEquals(price, 111);
		sa.assertTrue(actualDPaid);
		sa.assertEquals(actualCheckin, "2018-01-01");
		sa.assertEquals(actualCheckout, "2019-01-01");
		sa.assertEquals(actualNeeds, "Breakfast");
		
		//run all the assertions
		sa.assertAll();
	}

}
