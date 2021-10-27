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
        // busco primeiro ID de reserva da listagem para alterar
        int primeiroId = getBookingRequest.getPrimeiroId();

        // tento alterar a reserva e espero receber retorno de sucesso código 200
        // passando o token atraves do getToken que esta no PostAuthRequest
        putBookingRequest.updateBookingToken(primeiroId, postAuthRequest.getToken())
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Alterar uma reserva usando o Basic auth")
    public void validarAlteracaoDeUmaReservaUtilizandoBasic() {
        // busco primeiro ID de reserva da listagem para alterar
        int primeiroId = getBookingRequest.getPrimeiroId();

        // tento alterar a reserva e espero receber retorno de sucesso código 200
        // passando o basic auth definido no BaseTest
        putBookingRequest.updateBookingBasicAuth(primeiroId, getBasicAutorizationToken())
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({EndToEndTests.class,AllTests.class})
    @DisplayName("Alterar uma reserva quando o token for inválido")
    public void validarAlteracaoDeUmaReservaUtilizandoTokenInvalido() {
        // busco primeiro ID de reserva da listagem para alterar
        int primeiroId = getBookingRequest.getPrimeiroId();

        // tento alterar a reserva passando um token invalido  e espero receber erro 403
        putBookingRequest.updateBookingToken(primeiroId, "token=xxxxxx")
                .then()
                .statusCode(403);
    }

    @Test
    @Category({EndToEndTests.class, AllTests.class})
    @DisplayName("Alterar uma reserva quando o token não for enviado")
    public void validarAlteracaoDeUmaReservaUtilizandoTokenNaoEnviado() {
        // busco primeiro ID de reserva da listagem para alterar
        int primeiroId = getBookingRequest.getPrimeiroId();

        // tento alterar a reserva sem passar o token e espero receber erro 403
        putBookingRequest.updateBookingTokenNaoEnviado(primeiroId)
                .then()
                .statusCode(403);
    }

    @Test
    @Category({EndToEndTests.class, AllTests.class})
    @DisplayName("Alterar uma reserva que não existe")
    public void validarAlteracaoDeUmaReservaQueNaoExiste() {
        // tento alterar uma reserva que não existe e espero receber erro 405
        putBookingRequest.updateBookingToken(0, postAuthRequest.getToken())
                .then()
                .statusCode(405);
    }
}