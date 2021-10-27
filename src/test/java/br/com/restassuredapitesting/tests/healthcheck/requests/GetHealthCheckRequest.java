package br.com.restassuredapitesting.tests.healthcheck.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetHealthCheckRequest {

    @Step("Retorna api online")
    public Response healthCheckReturnApi(){
        return given()
                .header("Content-Type","application/json")
                .when()
                .get("ping");
    }
}