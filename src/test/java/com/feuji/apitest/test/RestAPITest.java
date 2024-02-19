package com.feuji.apitest.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.feuji.apitest.config.BaseTest;
import com.feuji.apitest.pojos.Booking;
import com.feuji.apitest.pojos.BookingDates;
import com.feuji.apitest.utils.FileNameConstants;
import com.feuji.apitest.utils.FileReader;
import com.feuji.apitest.utils.GenerateToken;
import com.feuji.apitest.utils.Route;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

@Epic("API Automation")
public class RestAPITest extends BaseTest {

	private static final Logger logger = LogManager.getLogger(RestAPITest.class);
	int bookingId;

	@Test(description = "To get all the bookings", priority = 0)
	@Description("Get Bookings")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Akash")
	public void testGetAllBookings() {
		logger.info("Get all bookings test started");
		Allure.step("Getting all the Bookings");
		Response response = given().contentType(ContentType.JSON).baseUri(Route.BASE_URL).log().headers().when()
				.get(Route.GET_ALL_END_POINT).then().assertThat().statusCode(200).statusLine("HTTP/1.1 200 OK")
				.contentType(ContentType.JSON).extract().response();
		Allure.step("Validating the Response body");
		Assert.assertTrue(response.getBody().asString().contains("bookingid"));
		logger.info("Get all bookings test ended");
	}

	@Test(description = "To create a new booking", priority = 1)
	@Description("Create Booking")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Akash")
	public void testCreateBooking() {
		try {
			String jsonSchema = FileReader.getData(FileNameConstants.JSON_SCHEMA);
			Allure.step("Create a new Booking");
			BookingDates bookingDates = new BookingDates("2024-03-25", "2024-03-30");
			Booking booking = new Booking("Gunda", "anna", 1300, false, bookingDates, "breakfast");
			Response response = given().contentType(ContentType.JSON).body(booking).baseUri(Route.BASE_URL).log().all()
					.when().post(Route.POST_END_POINT).then().assertThat().statusCode(200).extract().response();
			bookingId = response.path("bookingid");
			Allure.step("Validating wheather booking has been created");
			Allure.step("Validating the Schema of the booking");
			given().contentType(ContentType.JSON).baseUri(Route.BASE_URL).when()
					.get(Route.GET_BY_ID_END_POINT, bookingId).then().assertThat().statusCode(200)
					.body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(description = "To update an existing booking", priority = 2)
	@Description("Update Booking")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Akash")
	public void testUpdateBookings() {
		try {
			String token = GenerateToken.getToken(FileNameConstants.TOKEN_REQUEST_BODY);
			BookingDates bookingDates = new BookingDates("2024-03-25", "2024-03-30");
			Booking booking = new Booking("Gunda", "pandu", 1300, false, bookingDates, "breakfast");
			Allure.step("Update the booking details");
			given().contentType(ContentType.JSON).body(booking).baseUri(Route.BASE_URL)
					.header("Cookie", "token=" + token).log().all().when().put(Route.UPDATE_END_POINT, bookingId).then()
					.assertThat().statusCode(200).body("firstname", Matchers.equalTo("Gunda"))
					.body("lastname", Matchers.equalTo("pandu"));
			Allure.step("Validating wheather booking has been updated");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(description = "To delete the booking", priority = 3)
	@Description("Delete Booking")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Akash")
	public void testDeleteBooking() {
		try {
			Allure.step("Delete the booking");
			Allure.step("Validating wheather booking has been deleted");
			String token = GenerateToken.getToken(FileNameConstants.TOKEN_REQUEST_BODY);
			given().contentType(ContentType.JSON).baseUri(Route.BASE_URL).header("Cookie", "token=" + token).log().all()
					.when().delete(Route.DELETE_END_POINT, bookingId).then().assertThat().statusCode(201);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
