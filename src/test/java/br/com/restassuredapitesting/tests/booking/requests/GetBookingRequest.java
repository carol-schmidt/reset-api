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

    @Step("Retorna uma reserva especifica")
    public Response bookingReturn(int id){
        return given()
                .when()
                .get("booking/"+id);
    }

    @Step("Retorna os Ids das Listagens de Reservas filtrando pelo primeiro nome")
    public Response bookingReturnIdsWithFirstname(String firstName){
        return given()
                .when()
                .get("booking?firstname="+firstName);
    }

    @Step("Retorna os Ids das Listagens de Reservas filtrando pelo sobrenome")
    public Response bookingReturnIdsWithLastname(String lastName){
        return given()
                .when()
                .get("booking?lastname="+lastName);
    }

    @Step("Retorna os Ids das Listagens de Reservas filtrando pelo checkin")
    public Response bookingReturnIdsWithCheckin(String checkin){
        return given()
                .when()
                .get("booking?checkin="+checkin);
    }

    @Step("Retorna os Ids das Listagens de Reservas filtrando pelo checkout")
    public Response bookingReturnIdsWithCheckout(String checkout){
        return given()
                .when()
                .get("booking?checkout="+checkout);
    }
    @Step("Retorna os Ids das Listagens de Reservas filtrando pelo checkout e checkout")
    public Response bookingReturnIdsWithCheckoutECheckout(String checkout1, String checkout2){
        return given()
                .when()
                .get("booking?checkout="+checkout1+"&checkout="+checkout2);
    }
    @Step("Retorna os Ids das Listagens de Reservas filtrando pelos nomes, checkin e checkout")
    public Response bookingReturnIdsWithAllParameters(String firstName, String lastName, String checkin, String checkout){
        return given()
                .when()
                .get("booking?firstname="+firstName+"&lastname="+lastName+"&checkin="+checkin+"&checkout="+checkout);
    }

    @Step("Retorna os Ids das Listagens de Reservas filtrando por datas mal formatadas")
    public Response bookingReturnIdsWithCheckinECheckout(String checkin, String checkout){
        return given()
                .when()
                .get("booking?checkin="+checkin+"&checkout="+checkout);
    }

}
