package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetBookingsIDWIthFilterExerciseTest extends BaseTest {

	String URL = "https://restful-booker.herokuapp.com/";

	// String URL =
	// "https://restful-booker.herokuapp.com/booking?firstname=Abiola&lastname=Lamidi";
	@Test
	public void getBookingsIDWIthFilterExerciseTest() {

		// RequestSpecification rspec = new
		// RequestSpecBuilder().setBaseUri(URL).build();

		// create the booking
		Response createResponse = createBookings();
		createResponse.print();

		// return new booking id
		int bookingid = createResponse.jsonPath().getInt("bookingid");

		// Improve the code with path params
		rspec.pathParam("bookingId", bookingid);

		// get booking IDs response for first 10 bookings
		Response response = RestAssured.given(rspec).get("booking/{bookingId}");
		response.prettyPrint();

		Assert.assertEquals(response.getStatusCode(), 200, "Status code should return 200!");

		SoftAssert sa = new SoftAssert();

		// get values of each returned JSon key
		String actualFName = response.jsonPath().getString("firstname");
		String actualLName = response.jsonPath().getString("lastname");
		int price = response.jsonPath().getInt("totalprice");
		boolean actualDPaid = response.jsonPath().getBoolean("depositpaid");
		String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
		String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
		String actualNeeds = response.jsonPath().getString("additionalneeds");

		// cross check all the fields with expected values
		sa.assertEquals(actualFName, "Paul Yila");
		sa.assertEquals(actualLName, "Samuel");
		sa.assertEquals(price, 1000);
		sa.assertTrue(actualDPaid);
		sa.assertEquals(actualCheckin, "2022-09-26");
		sa.assertEquals(actualCheckout, "2022-09-30");
		sa.assertEquals(actualNeeds, "New Whip");

		// run all the assertions
		sa.assertAll();
	}

}
