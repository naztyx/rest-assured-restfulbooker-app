package com.herokuapp.restfulbooker;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class HealthCheckTest extends BaseTest{
	
	//String URL = "https://restful-booker.herokuapp.com";
	
	@Test
	public void healthCheckTest() {
		
		given().spec(rspec).when().get("ping").then().assertThat().statusCode(201);
	}

	@Test
	public void headersCookies() {
		Header anodherHeader = new Header("Another header","Another value");
		rspec.header(anodherHeader);
		
		Cookie anotherCookie = new Cookie.Builder("Another cookie","Another cookie value").build();
		rspec.cookie(anotherCookie);
		
		Response resp = RestAssured.given(rspec).
				cookie("Test cookie name", "Test Cookie Value").
				header("Test header name", "Test header value").log().all().
				get("/ping"); //adding headers and cookies to the request directly
				
		// get headers
		Headers headers = resp.getHeaders();
		System.out.println("Headers: " + headers);
		
		//get header method 1
		Header serverH1 = headers.get("Server");
		System.out.println(serverH1.getName() + ": " + serverH1.getValue());
		
		//get header method 2
		String serverH2 = resp.getHeader("Server");
		System.out.println("Server: " + serverH2);
		
		//Get cookies
		Cookies cookies = resp.getDetailedCookies();
		System.out.println("Cookies: " + cookies);
	}

}
