package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.tests.booking.payload.BookingPayloads;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PutBookingRequest {
    BookingPayloads bookingPayloads = new BookingPayloads();

    @Step("Atualiza uma reserva especifica com o parametro token")
    public Response updateBookingToken(int id, String token){
        return given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Cookie",token)
                .when()
                .body(bookingPayloads.payloadValidBooking().toString())
                .put("booking/"+id);
    }

    @Step("Atualiza uma reserva especifica com o parametro Basic Auth")
    public Response updateBookingBasicAuth(int id, String basicAuthorisation){
        return given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Authorization", basicAuthorisation)
                .when()
                .body(bookingPayloads.payloadValidBooking().toString())
                .put("booking/"+id);
    }
    @Step("Atualiza uma reserva especifica com o parametro token não enviado")
    public Response updateBookingTokenNaoEnviado(int id){
        return given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .when()
                .body(bookingPayloads.payloadValidBooking().toString())
                .put("booking/"+id);
    }
}
