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
        // busco primeiro ID de reserva da listagem para excluir
        int primeiroId = getBookingRequest.getPrimeiroId();

        // tento excluir a reserva e espero receber erro 201
        deleteBookingRequest.deleteBookingToken(primeiroId,postAuthRequest.getToken())
                .then()
                .statusCode(201);
    }

    @Test
    @Category({EndToEndTests.class, AllTests.class})
    @DisplayName("Deletar uma reserva que não existe")
    public void validarExclusaoDeUmaReservaQueNaoExiste(){
        // Tento excluir uma reserva que não existe e espero receber erro 405
        deleteBookingRequest.deleteBookingToken(0, postAuthRequest.getToken())
              .then()
             .statusCode(405);
    }

    @Test
    @Category({EndToEndTests.class, AllTests.class})
    @DisplayName("Deletar uma reserva sem autorização")
    public void validarExclusaoDeUmaReservaSemAutorizacao(){
        // busco primeiro ID de reserva da listagem para excluir
        int primeiroId = getBookingRequest.getPrimeiroId();

        //Tento excluir uma reserva sem o token que seria a autorização e espero receber o erro 403
        deleteBookingRequest.deleteBookingSemToken(primeiroId)
                .then()
                .statusCode(403);
    }
}