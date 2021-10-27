package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.EndToEndTests;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.requests.DeleteBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DeleteBookingTest extends BaseTest {
    DeleteBookingRequest deleteBookingRequest = new DeleteBookingRequest();
    GetBookingRequest getBookingRequest = new GetBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Deletar uma reserva somente utilizando token")
    public void validarExclusaoDeUmaReserva(){
        int primeiroId = getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .extract()
                .path("[0].bookingid");

        deleteBookingRequest.deleteBookingToken(primeiroId,postAuthRequest.getToken())
                .then()
                .statusCode(201);
    }

    @Test
    @Category({EndToEndTests.class, AllTests.class})
    @DisplayName("Deletar uma reserva que não existe")
    public void validarExclusaoDeUmaReservaQueNaoExiste(){
        deleteBookingRequest.deleteBookingToken(0, postAuthRequest.getToken())
              .then()
             .statusCode(405);
    }
    @Test
    @Category({EndToEndTests.class, AllTests.class})
    @DisplayName("Deletar uma reserva sem autorização")
    public void validarExclusaoDeUmaReservaSemAutorizacao(){
        deleteBookingRequest.deleteBookingSemToken(0)
                .then()
                .statusCode(403);
    }

}


