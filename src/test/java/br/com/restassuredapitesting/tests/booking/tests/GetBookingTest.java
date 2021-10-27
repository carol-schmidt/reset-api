package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.ContractTests;
import br.com.restassuredapitesting.suites.EndToEndTests;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
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

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class})
    @DisplayName("Listar Ids de reservas")
    public void validaListagemDeIdsDasReservas(){
        getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Garantir o Schema do retorno da listagem de reservas ")
    public void validaSchemaDaListagemDeReservas (){
        getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath("booking","bookings"))));
    }

    @Test
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Garantir o schema do retorno da lista de reservas")
    public void validaSchemaRetornoListagemDeReservas(){
        getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath("booking","listbookings"))));
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Garantir o schema do retorno de uma reserva específica")
    public void validaSchemaReservaEspecifica(){
        int primeiroId = getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .extract()
                .path("[0].bookingid");

        getBookingRequest.bookingReturn(primeiroId)
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath("booking","specificbooking"))));
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Listar IDs de reservas utilizando o filtro firstname")
    public void validaListagemDeReservaPeloFirstName(){
        getBookingRequest.bookingReturnIdsWithFirstname("Cristiano")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Listar IDs de reservas utilizando o filtro lastname")
    public void validaListagemDeReservaPeloLastName(){
        getBookingRequest.bookingReturnIdsWithLastname("Ronaldo")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Listar IDs de reservas utilizando o filtro checkin")
    public void validaListagemDeReservaPeloCheckin(){
        getBookingRequest.bookingReturnIdsWithCheckin("2018-01-01")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Listar IDs de reservas utilizando o filtro checkou")
    public void validaListagemDeReservaPeloCheckou(){
        getBookingRequest.bookingReturnIdsWithCheckout("2019-01-01")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Listar IDs de reservas utilizando o filtro checkout e checkout")
    public void validaListagemDeReservaPeloCheckoutnECheckou(){
        getBookingRequest.bookingReturnIdsWithCheckoutECheckout("2018-01-01","2019-01-01")
                .then()
                .statusCode(500);
    }


    @Test
    @Category({AcceptanceTests.class, AllTests.class})
    @DisplayName("Listar IDs de reservas utilizando todos os filtros")
    public void validaListagemDeReservComTodosOsFiltros(){
        getBookingRequest.bookingReturnIdsWithAllParameters("Cristiano","Ronaldo","2017-01-01","2020-01-01")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Category({EndToEndTests.class, AllTests.class})
    @DisplayName("Receber mensagem de erro quando busco as reservas com filtros de data mal formatados")
    public void validaListagemDeReservaPeloCheckinECheckouInvalido(){
        getBookingRequest.bookingReturnIdsWithCheckinECheckout("01032019","27082021")
                .then()
                .statusCode(500);
    }
}

