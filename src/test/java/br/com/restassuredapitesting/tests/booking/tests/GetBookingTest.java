package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.ContractTests;
import br.com.restassuredapitesting.suites.EndToEndTests;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
import br.com.restassuredapitesting.utils.Utils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.greaterThan;

@Feature("Feature Retorno de reservas")
public class GetBookingTest extends BaseTest {

    GetBookingRequest getBookingRequest = new GetBookingRequest();
    PostBookingRequest postBookingRequest = new PostBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();

    public GetBookingTest(){
        // Crio uma reserva que vai ser usada para os filtros de busca para garantir que
        // existe pelo menos uma reserva com os filtros usados
        postBookingRequest.createBooking(postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class})
    @DisplayName("Listar Ids de reservas")
    public void validaListagemDeIdsDasReservas() {
        getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Garantir o Schema do retorno da listagem de reservas ")
    public void validaSchemaDaListagemDeReservas() {
        getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath("booking", "bookings"))));
    }

    @Test
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Garantir o schema do retorno da lista de reservas")
    public void validaSchemaRetornoListagemDeReservas() {
        // Tento validar a schema atraves do retorno dos IDS
        getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath("booking", "listbookings"))));
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Garantir o schema do retorno de uma reserva específica")
    public void validaSchemaReservaEspecifica() {
        //Tento selecionar o primeiro ID, para retornar uma reserva especifica
        int primeiroId = getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .extract()
                .path("[0].bookingid");

        getBookingRequest.bookingReturn(primeiroId)
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath("booking", "specificbooking"))));
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Listar IDs de reservas utilizando o filtro firstname")
    public void validaListagemDeReservaPeloFirstName() {
        // Tento validar a reserva atraves do primeiro nome
        getBookingRequest.bookingReturnIdsWithFirstname("Cristiano")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Listar IDs de reservas utilizando o filtro lastname")
    public void validaListagemDeReservaPeloLastName() {
        // Tento validar a reserva atraves do segundo nome
        getBookingRequest.bookingReturnIdsWithLastname("Ronaldo")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Listar IDs de reservas utilizando o filtro checkin")
    public void validaListagemDeReservaPeloCheckin() {
        // Tento validar a reserva atraves do checking
        getBookingRequest.bookingReturnIdsWithCheckin("2018-01-01")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Listar IDs de reservas utilizando o filtro checkou")
    public void validaListagemDeReservaPeloCheckou() {
        // Tento validar a reserva atraves do checkou
        getBookingRequest.bookingReturnIdsWithCheckout("2019-01-01")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Listar IDs de reservas utilizando o filtro checkout e checkout")
    public void validaListagemDeReservaPeloCheckoutnECheckou() {
        // Tento validar a reserva atraves do checkou  e checkou, adicionando duas datas diferentes
        // e espero receber erro 500
        getBookingRequest.bookingReturnIdsWithCheckoutECheckout("2018-01-01", "2019-01-01")
                .then()
                .statusCode(500);
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Listar IDs de reservas utilizando todos os filtros")
    public void validaListagemDeReservComTodosOsFiltros() {
        // Tento validar a reserva utilizando todos os filtros
        getBookingRequest.bookingReturnIdsWithAllParameters("Cristiano", "Ronaldo", "2017-01-01", "2020-01-01")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({EndToEndTests.class, AllTests.class})
    @DisplayName("Receber mensagem de erro quando busco as reservas com filtros de data mal formatados")
    public void validaListagemDeReservaPeloCheckinECheckouInvalido() {
        // Tento validar a listagem de reserva com as datas com formato errado e espero receber erro 500
        getBookingRequest.bookingReturnIdsWithCheckinECheckout("01032019", "27082021")
                .then()
                .statusCode(500);
    }
}