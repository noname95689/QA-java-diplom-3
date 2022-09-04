package org.example;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.model.*;

import static io.restassured.RestAssured.given;

public class TestPreparation {

    //Send Post request with email/password/name to /api/auth/register
    public static Response sendPostRegister() {
        RestAssured.baseURI= "https://stellarburgers.nomoreparties.site";
        User user = new User(RegistrationData.getCorrectRegistrationData().getEmail(),
                RegistrationData.getCorrectRegistrationData().getPassword(),
                RegistrationData.getCorrectRegistrationData().getName());
        Response response = given()
                .header("Content-Type", "application/json")
                .body(user)
                .post("/api/auth/register");
        return response;
    }

    //"Send Post request with email/password to /api/auth/login, return accessToken"
    public static String getAccessToken() {
        RestAssured.baseURI= "https://stellarburgers.nomoreparties.site";
        Response response = sendPostLogin(LoginData.getCorrectLoginData().getEmail(), LoginData.getCorrectLoginData().getPassword());
        AuthAnswer authAnswer =
                response.body().as(AuthAnswer.class);
        String accessToken = authAnswer.getAccessToken().replace("Bearer ", "");
        System.out.println(accessToken);
        return accessToken;
    }

    //"Delete user if he exist"
    public static void checkIfUserCreatedAndDelete() {
        if (TestPreparation.sendPostLogin(LoginData.getCorrectLoginData().getEmail(), LoginData.getCorrectLoginData().getPassword()).statusCode() == 200) {
            TestPreparation.sendDeleteRegister();
        }
    }

    //"Send Post request with email/password to /api/auth/login"
    public static Response sendPostLogin(String email, String password) {
        RestAssured.baseURI= "https://stellarburgers.nomoreparties.site";
        AuthUser authUser = new AuthUser(email, password);
        Response response = given()
                .header("Content-type", "application/json")
                .body(authUser)
                .post("/api/auth/login");
        return response;
    }

    @Step("Send Post request with email/password to /api/auth/login")
    public static Response sendDeleteRegister() {
        RestAssured.baseURI= "https://stellarburgers.nomoreparties.site";
        Response response = given()
                .auth().oauth2(getAccessToken())
                .header("Content-type", "application/json")
                .delete("/api/auth/user");
        return response;
    }


}
