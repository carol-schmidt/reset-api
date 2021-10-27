package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.EndToEndTests;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.PutBookingRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import static org.hamcrest.Matchers.greaterThan;

@Feature("Feature de Atualização de reservas")
public class PutBookingTest extends BaseTest {
    PutBookingRequest putBookingRequest = new PutBookingRequest();
    GetBookingRequest getBookingRequest = new GetBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Alterar uma reserva somente utilizando token")
    public void validarAlteracaoDeUmaReservaUtilizandoToken() {
        int primeiroId = getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .extract()
                .path("[0].bookingid");

        putBookingRequest.updateBookingToken(primeiroId, postAuthRequest.getToken())
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Alterar uma reserva usando o Basic auth")

    public void validarAlteracaoDeUmaReservaUtilizandoBasic() {
        int primeiroId = getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .extract()
                .path("[0].bookingid");

        putBookingRequest.updateBookingBasicAuth(primeiroId, getBasicAutorizationToken())
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
    @Test
    @Category({EndToEndTests.class,AllTests.class})
    @DisplayName("Alterar uma reserva quando o token for inválido")
    public void validarAlteracaoDeUmaReservaUtilizandoTokenInvalido() {
        int primeiroId = getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .extract()
                .path("[0].bookingid");

        putBookingRequest.updateBookingToken(primeiroId, "token=xxxxxx")
                .then()
                .statusCode(403);
    }
    @Test
    @Category({EndToEndTests.class, AllTests.class})
    @DisplayName("Alterar uma reserva quando o token não for enviado")
    public void validarAlteracaoDeUmaReservaUtilizandoTokenNaoEnviado() {
        int primeiroId = getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .extract()
                .path("[0].bookingid");

        putBookingRequest.updateBookingTokenNaoEnviado(primeiroId)
                .then()
                .statusCode(403);
    }

    @Test
   @Category({EndToEndTests.class, AllTests.class})
    @DisplayName("Alterar uma reserva que não existe")
    public void validarAlteracaoDeUmaReservaQueNaoExiste() {
        putBookingRequest.updateBookingToken(0, postAuthRequest.getToken())
                .then()
                .statusCode(405);
    }
}