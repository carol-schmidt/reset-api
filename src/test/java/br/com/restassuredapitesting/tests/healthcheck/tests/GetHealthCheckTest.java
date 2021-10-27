package br.com.restassuredapitesting.tests.healthcheck.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.HealthcheckTests;
import br.com.restassuredapitesting.tests.healthcheck.requests.GetHealthCheckRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Feature("Feature Api online")
public class GetHealthCheckTest extends BaseTest {
    GetHealthCheckRequest getHealthCheckRequest = new GetHealthCheckRequest();

    @Test
    @Category({HealthcheckTests.class, AllTests.class})
    @DisplayName("Verificar se API está online")
    public void validaApiOnline(){
        // Tento validar se a Api esta online e espero receber retorno de sucesso código 201
        getHealthCheckRequest.healthCheckReturnApi()
                .then()
                .statusCode(201);
    }
}