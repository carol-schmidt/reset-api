package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetBookingRequest {

    @Step("Retorna os Ids das Listagens de Reservas")
    public Response bookingReturnIds(){

        return given()
               .when()
               .get("booking");
    }

}
