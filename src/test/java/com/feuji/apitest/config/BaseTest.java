package com.feuji.apitest.config;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;

public class BaseTest {

	private static Logger logger = LogManager.getLogger(BaseTest.class);

	@BeforeMethod
	public void beforeMethod() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

//	@AfterMethod
//	public void afterMethod(ITestResult result) {
//		Throwable throwable = result.getThrowable();
//		StringWriter error = new StringWriter();
//		throwable.printStackTrace(new PrintWriter(error));
//		logger.error(error);
//	}
}
