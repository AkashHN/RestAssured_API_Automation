package com.feuji.apitest.utils;

import static io.restassured.RestAssured.given;

import com.jayway.jsonpath.JsonPath;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GenerateToken {

	public static String token;

	public static String getToken(String path) {
		if (token == null) {
			String tokenAPIRequestBody = FileReader.getData(path);
			Response tokenAPIResponse = given().contentType(ContentType.JSON).body(tokenAPIRequestBody)
					.baseUri(Route.BASE_URL).when().post(Route.AUTH_END_POINT).then().assertThat().statusCode(200)
					.extract().response();
			token = JsonPath.read(tokenAPIResponse.body().asString(), "$.token");
		}
		return token;
	}

}
