package restful_booker.tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import restful_booker.config.BookerConfig;

public abstract class BaseBookerTest {

    @BeforeSuite
    public void configureBookerApi() {
        RestAssured.baseURI = BookerConfig.get("booker.base.url");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}