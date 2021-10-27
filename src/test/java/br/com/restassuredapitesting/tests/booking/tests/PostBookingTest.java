package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.EndToEndTests;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class PostBookingTest extends BaseTest {
    PostBookingRequest postBookingRequest = new PostBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Validar uma nova reserva")
    public void CriandoUmaNovaReserva() {
        postBookingRequest.createBooking(postAuthRequest.getToken())
                .then()
                .statusCode(200)
                .body("bookingid", greaterThan(0));
    }

    @Test
    @Category({EndToEndTests.class,AllTests.class})
    @DisplayName("Validar uma reserva com payload inválido")
    public void validandoReservaComPayloadInvalido() {
        // Tento validar a reserva utilizando o payload de data de checkout inválida e espero recebre erro 500
        // Este payload inválido está definido em Bookings Payloads
        postBookingRequest.createBookingInvalid(postAuthRequest.getToken())
                .then()
                .statusCode(500);
    }

    @Test
    @Category({EndToEndTests.class,AllTests.class})
    @DisplayName("Validar uma nova reserva em sequencia")
    public void CriandoUmaNovaReservaEmSequencia() {
        // crio uma reserva e pega o id criado
        int primeiroId = postBookingRequest.createBooking(postAuthRequest.getToken())
                .then()
                .statusCode(200)
                .extract()
                .path("bookingid");

        // cria uma segunda reserva e comparo seu id com o primeiro id + 1
        postBookingRequest.createBooking(postAuthRequest.getToken())
                .then()
                .statusCode(200)
                .body("bookingid", equalTo(primeiroId + 1));
    }

    @Test
    @Category({EndToEndTests.class, AllTests.class})
    @DisplayName("Validar uma reserva enviando mais parâmetros no payload da reserva")
    public void validandoReservaComMaisParametrosNoPayloadDaReserva(){
        // Tento validar a criação de uma reserva enviando mais parametros dos que os definidos no payload
        // e espero receber erro 418
        postBookingRequest.createBookingValidParameterPayload(postAuthRequest.getToken())
                .then()
                .statusCode(418);
    }

    @Test
    @Category({EndToEndTests.class, AllTests.class})
    @DisplayName("Validar retorno 418 quando o header Accept for invalido")
    public void validandoReservaComOAcceptInvalido(){
        // Tento validar reserva enviando o Accept (que está no PostBookingRequest) inválido e espero receber
        // erro 418
        postBookingRequest.createBookingInvalidAccept(postAuthRequest.getToken())
                .then()
                .statusCode(418);
    }
}