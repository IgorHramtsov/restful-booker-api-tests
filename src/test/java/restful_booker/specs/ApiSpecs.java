package restful_booker.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public final class ApiSpecs {

    private ApiSpecs() {
    }

    public static RequestSpecification jsonRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification okJsonResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification createdResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }

    public static ResponseSpecification forbiddenResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(403)
                .build();
    }

    public static ResponseSpecification notFoundResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }
}
