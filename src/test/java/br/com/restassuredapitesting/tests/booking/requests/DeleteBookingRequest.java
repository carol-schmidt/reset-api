package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteBookingRequest {

    @Step("Deleta uma reserva especifica com o parametro token")
    public Response deleteBookingToken(int id, String token){
        return given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Cookie",token)
                .when()
                .delete("booking/"+id);
    }

    @Step("Deleta uma reserva especifica sem autorização")
    public Response deleteBookingSemToken(int id){
        return given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .when()
                .delete("booking/"+id);
    }
}
