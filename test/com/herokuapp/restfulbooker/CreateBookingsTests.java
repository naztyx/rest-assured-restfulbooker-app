package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateBookingsTests extends BaseTest {
	
//	String URL_POST = "https://restful-booker.herokuapp.com/booking";
	
	@Test(enabled = false)
	public void createBookingsTests() {
		Response response = createBookings();
		response.prettyPrint();
		
	}

	@Test
	public void creatBookingswithPOJO() {
		//create body for booking with POJOs
		Bookingdates bookingdates = new Bookingdates("2022-07-22", "2022-10-22");
		
		Booking  booking = new Booking("Amal", "Jake", 300, false, bookingdates, "Baby crib");
		
		Response response = RestAssured.given().spec(rspec).contentType(ContentType.JSON).
				body(booking).post("booking/");
		response.print();
		
		//run verifications
		SoftAssert sa = new SoftAssert();

		// get values of each returned JSon key
		String actualFName = response.jsonPath().getString("booking.firstname");
		String actualLName = response.jsonPath().getString("booking.lastname");
		int price = response.jsonPath().getInt("booking.totalprice");
		boolean actualDPaid = response.jsonPath().getBoolean("booking.depositpaid");
		String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
		String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
		String actualNeeds = response.jsonPath().getString("booking.additionalneeds");

		// cross check all the fields with expected values
		sa.assertEquals(actualFName, "Amal");
		sa.assertEquals(actualLName, "Jake");
		sa.assertEquals(price, 300);
		sa.assertFalse(actualDPaid);
		sa.assertEquals(actualCheckin, "2022-07-22");
		sa.assertEquals(actualCheckout, "2022-10-22");
		sa.assertEquals(actualNeeds, "Baby crib");

		// run all the assertions
		sa.assertAll();
	}
}
