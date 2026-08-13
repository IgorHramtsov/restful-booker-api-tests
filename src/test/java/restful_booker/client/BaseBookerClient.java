package restful_booker.client;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static restful_booker.specs.ApiSpecs.jsonRequestSpec;

public abstract class BaseBookerClient {

    protected RequestSpecification request() {
        return given()
                .spec(jsonRequestSpec());
    }
}