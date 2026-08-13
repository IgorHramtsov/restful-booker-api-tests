package restful_booker.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import restful_booker.client.AuthClient;
import restful_booker.dto.request.AuthRequestDto;
import restful_booker.dto.response.AuthResponseDto;
import restful_booker.factory.AuthRequestFactory;

import static org.hamcrest.core.IsEqual.equalTo;

@Epic("Restful Booker API")
@Feature("Authentication")
public class AuthTests extends BaseBookerTest {

    private AuthClient authClient;

    @BeforeClass
    public void setupClient() {
        authClient = new AuthClient();
    }

    @Story("Valid authentication")
    @Description("Checks that valid admin credentials return an authentication token")
    @Test
    public void loginWithValidCredentialsShouldReturnToken() {
        AuthRequestDto request = AuthRequestFactory.validAdminAuth();
        AuthResponseDto response = authClient.loginAsDto(request);

        Assert.assertNotNull(response.getToken(), "Token is null");
        Assert.assertFalse(response.getToken().isBlank(), "Token is blank");
        Assert.assertTrue(response.getToken().length() > 10, "Token is too short");
    }

    @Story("Invalid authentication")
    @Description("Checks that invalid credentials do not return an authentication token")
    @Test(dataProvider = "invalidCredentials")
    public void loginWithInvalidCredentialsShouldNotReturnToken(String username, String password) {
        AuthRequestDto request = new AuthRequestDto();
        request.setUsername(username);
        request.setPassword(password);

        Response response = authClient.login(request);

        response.then()
                .statusCode(200)
                .body("token", equalTo(null))
                .body("reason", equalTo("Bad credentials"));
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"admin", "wrong-password"},
                {"wrong-admin", "password123"},
                {"admin", ""},
                {"", "password123"}};
    }
}