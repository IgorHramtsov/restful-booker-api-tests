package restful_booker.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import restful_booker.dto.request.AuthRequestDto;
import restful_booker.dto.response.AuthResponseDto;

import static restful_booker.specs.ApiSpecs.okJsonResponseSpec;

public class AuthClient extends BaseBookerClient {

    @Step("Login as user: {requestDto.username}")
    public Response login(AuthRequestDto requestDto) {
        return request()
                .body(requestDto)
                .when()
                .post("/auth");
    }

    public AuthResponseDto loginAsDto(AuthRequestDto request) {
        return login(request)
                .then()
                .spec(okJsonResponseSpec())
                .extract()
                .as(AuthResponseDto.class);
    }
}
