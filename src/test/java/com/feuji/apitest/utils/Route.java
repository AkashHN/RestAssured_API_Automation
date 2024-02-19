package com.feuji.apitest.utils;

public class Route {

	public static final String BASE_URL = "https://restful-booker.herokuapp.com";
	public static final String AUTH_END_POINT = "/auth";
	public static final String POST_END_POINT = "/booking";
	public static final String GET_ALL_END_POINT = POST_END_POINT;
	public static final String GET_BY_ID_END_POINT = "/booking/{bookingid}";
	public static final String UPDATE_END_POINT = GET_BY_ID_END_POINT;
	public static final String DELETE_END_POINT = GET_BY_ID_END_POINT;
	
}
