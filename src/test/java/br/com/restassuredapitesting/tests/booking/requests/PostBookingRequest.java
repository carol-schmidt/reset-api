package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.tests.booking.payload.BookingPayloads;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostBookingRequest {
    BookingPayloads bookingPayloads = new BookingPayloads();

    @Step("Cria uma nova reserva")
    public Response createBooking(String token){
        return given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Cookie",token)
                .when()
                .body(bookingPayloads.payloadValidBooking().toString())
                .post("booking");
    }

    @Step("Cria uma nova reserva")
    public Response createBookingInvalid(String token){
        return given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Cookie",token)
                .when()
                .body(bookingPayloads.payloadInvalidBooking().toString())
                .post("booking");
    }
    @Step("Validar retorno 418 quando o header Accept for invalido")
    public Response createBookingInvalidAccept(String token){
        return given()
                .header("Content-Type","application/json")
                .header("Accept","xxxxxx")
                .header("Cookie",token)
                .when()
                .body(bookingPayloads.payloadValidBooking().toString())
                .post("booking");
    }

    @Step("Validar retorno 418 quando o header Accept for invalido")
    public Response createBookingValidParamiterPayload(String token){
        return given()
                .header("Content-Type","application/json")
                .header("Accept","xxxxxx")
                .header("Cookie",token)
                .when()
                .body(bookingPayloads.payloadValidBooking().toString())
                .post("booking");
    }
}
