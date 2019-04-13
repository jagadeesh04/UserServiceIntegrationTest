package com.quiz.integrationTest;

import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.hamcrest.Matchers.containsString;

public class UserControllerITTest implements BeforeAllCallback, AfterAllCallback {

    private static final String path ="/user";


    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        RestAssured.reset();
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost:8080";
    }


    @Test
    public void testPutUsersToUpdateUser1() throws Exception
    {
        RestAssured.given().body("{\"id\":1,\"username\":\"cat\",\"email\":\"cat@cat.com\"}").
                header("Content-Type", "application/json").when().put(path + "/1").then().assertThat().statusCode(200).body("username", containsString("cat"));
    }

    @Test
    public void testPutUsersToNotUpdateUserForInvalidUserName() throws Exception
    {
        RestAssured.given().body("{\"id\":1,\"username\":\",\"email\":\"cat@cat.com\"}").
                header("Content-Type", "application/json").when().put(path + "/1").then().assertThat().statusCode(400);
    }

    @Test
    public void testPutUsersToNotUpdateUserForInvalidEmail() throws Exception
    {
        RestAssured.given().body("{\"id\":1,\"username\":\"cat\",\"email\":\"cat.com\"}").
                header("Content-Type", "application/json").when().put(path + "/1").then().assertThat().statusCode(400);
    }

    @Test
    public void testPutUsersToNotUpdateUserForNullInputs() throws Exception
    {
        RestAssured.given().body("{\"id\":1,\"username\":,\"email\":}").
                header("Content-Type", "application/json").when().put(path + "/1").then().assertThat().statusCode(400);
    }


}
