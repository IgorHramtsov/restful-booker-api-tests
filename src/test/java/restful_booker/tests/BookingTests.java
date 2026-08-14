package restful_booker.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import restful_booker.assertions.BookingAssertions;
import restful_booker.client.AuthClient;
import restful_booker.client.BookingClient;
import restful_booker.dto.request.AuthRequestDto;
import restful_booker.dto.request.BookingDatesDto;
import restful_booker.dto.request.CreateBookingRequestDto;
import restful_booker.dto.request.PatchBookingRequestDto;
import restful_booker.dto.response.BookingResponseDto;
import restful_booker.dto.response.CreateBookingResponseDto;
import restful_booker.factory.AuthRequestFactory;
import restful_booker.factory.BookingRequestFactory;

import static restful_booker.specs.ApiSpecs.*;

@Epic("Restful Booker API")
@Feature("Booking management")
public class BookingTests extends BaseBookerTest {

    private BookingClient bookingClient;
    private AuthClient authClient;
    private int bookingId;
    private String token;

    @BeforeClass(alwaysRun = true)
    public void setupClient() {
        bookingClient = new BookingClient();
        authClient = new AuthClient();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        if (bookingId > 0 && token != null) {
            bookingClient.deleteBooking(bookingId, token);
        }

        bookingId = 0;
        token = null;
    }

    private String getAdminToken() {
        AuthRequestDto authRequest = AuthRequestFactory.validAdminAuth();
        return authClient.loginAsDto(authRequest).getToken();
    }

    @Story("Create booking")
    @Description("Checks that a new booking can be successfully created")
    @Test(groups = "smoke")
    public void createBookingShouldReturnCreatedBooking() {
        CreateBookingRequestDto request = BookingRequestFactory.validBooking();
        CreateBookingResponseDto response = bookingClient.createBookingAsDto(request);

        bookingId = response.getBookingid();
        token = getAdminToken();

        Assert.assertTrue(response.getBookingid() > 0);
        Assert.assertNotNull(response.getBooking());

        Assert.assertEquals(
                response.getBooking().getFirstname(),
                request.getFirstname()
        );

        Assert.assertEquals(
                response.getBooking().getLastname(),
                request.getLastname()
        );

        Assert.assertEquals(
                response.getBooking().getTotalprice(),
                request.getTotalprice()
        );

        Assert.assertEquals(
                response.getBooking().isDepositpaid(),
                request.isDepositpaid()
        );

        Assert.assertEquals(
                response.getBooking().getBookingdates().getCheckin(),
                request.getBookingdates().getCheckin()
        );

        Assert.assertEquals(
                response.getBooking().getBookingdates().getCheckout(),
                request.getBookingdates().getCheckout()
        );

        Assert.assertEquals(
                response.getBooking().getAdditionalneeds(),
                request.getAdditionalneeds()
        );
    }

    @Story("Get booking")
    @Description("Checks that a created booking can be retrieved by its id")
    @Test(groups = "smoke")
    public void createdBookingShouldBeReturnedById() {
        CreateBookingRequestDto request = BookingRequestFactory.validBooking();
        CreateBookingResponseDto createResponse = bookingClient.createBookingAsDto(request);
        bookingId = createResponse.getBookingid();
        token = getAdminToken();
        BookingResponseDto getResponse = bookingClient.getBookingAsDto(bookingId);
        Assert.assertTrue(bookingId > 0);

        Assert.assertEquals(
                getResponse.getFirstname(),
                "soppppddasdsa"
        );

        Assert.assertEquals(
                getResponse.getLastname(),
                request.getLastname()
        );

        Assert.assertEquals(
                getResponse.getTotalprice(),
                request.getTotalprice()
        );

        Assert.assertEquals(
                getResponse.isDepositpaid(),
                request.isDepositpaid()
        );

        Assert.assertEquals(
                getResponse.getBookingdates().getCheckin(),
                request.getBookingdates().getCheckin()
        );

        Assert.assertEquals(
                getResponse.getBookingdates().getCheckout(),
                request.getBookingdates().getCheckout()
        );

        Assert.assertEquals(
                getResponse.getAdditionalneeds(),
                request.getAdditionalneeds()
        );
    }

    @Story("Get booking")
    @Description("Checks that requesting a non-existing booking returns 404")
    @Test(groups = "regression")
    public void getNonExistingBookingShouldReturnNotFound() {
        int nonExistingBookingId = 999999999;
        Response response = bookingClient.getBooking(nonExistingBookingId);

        response.then()
                .log().all()
                .statusCode(404);
    }

    @Story("Update booking")
    @Description("Checks that PUT fully updates booking data and persists the changes")
    @Test(groups = "regression")
    public void updateBookingShouldPersistUpdatedData () {
        CreateBookingRequestDto request = BookingRequestFactory.validBooking();
        CreateBookingResponseDto response = bookingClient.createBookingAsDto(request);
        bookingId = response.getBookingid();
        token = getAdminToken();

        BookingDatesDto dates = new BookingDatesDto();
        dates.setCheckin("2026-08-10");
        dates.setCheckout("2026-08-15");

        CreateBookingRequestDto updatedRequest = new CreateBookingRequestDto();
        updatedRequest.setFirstname("qweryty");
        updatedRequest.setLastname("asddfgh");
        updatedRequest.setTotalprice(2000);
        updatedRequest.setDepositpaid(false);
        updatedRequest.setBookingdates(dates);
        updatedRequest.setAdditionalneeds("Big Mac");

        BookingResponseDto updatedResponse = bookingClient.updateBookingAsDto(bookingId, token, updatedRequest);

        BookingAssertions.assertBookingEqualsRequest(updatedResponse, updatedRequest);

        BookingResponseDto getResponse = bookingClient.getBookingAsDto(bookingId);

        BookingAssertions.assertBookingEqualsRequest(getResponse, updatedRequest);
    }

    @Story("Partial booking update")
    @Description("Checks that PATCH updates only the provided booking fields")
    @Test(groups = "regression")
    public void patchBookingShouldUpdateOnlyProvidedFields() {
        CreateBookingRequestDto request = BookingRequestFactory.validBooking();
        CreateBookingResponseDto response = bookingClient.createBookingAsDto(request);
        bookingId = response.getBookingid();
        token = getAdminToken();

        PatchBookingRequestDto requestDto = new PatchBookingRequestDto();
        requestDto.setFirstname("Anton");
        requestDto.setAdditionalneeds("Laptop");

        BookingResponseDto patchResponse = bookingClient.patchBookingAsDto(bookingId, token, requestDto);

        Assert.assertEquals(requestDto.getFirstname(), patchResponse.getFirstname());
        Assert.assertEquals(requestDto.getAdditionalneeds(), patchResponse.getAdditionalneeds());
        Assert.assertEquals(request.getLastname(), patchResponse.getLastname());
        Assert.assertEquals(request.getTotalprice(), patchResponse.getTotalprice());
        Assert.assertEquals(request.isDepositpaid(), patchResponse.isDepositpaid());
        Assert.assertEquals(request.getBookingdates().getCheckout(), patchResponse.getBookingdates().getCheckout());
        Assert.assertEquals(request.getBookingdates().getCheckin(), patchResponse.getBookingdates().getCheckin());

        BookingResponseDto getResponse = bookingClient.getBookingAsDto(bookingId);

        Assert.assertEquals(requestDto.getFirstname(), getResponse.getFirstname());
        Assert.assertEquals(requestDto.getAdditionalneeds(), getResponse.getAdditionalneeds());
        Assert.assertEquals(request.getLastname(), getResponse.getLastname());
        Assert.assertEquals(request.getTotalprice(), getResponse.getTotalprice());
        Assert.assertEquals(request.isDepositpaid(), getResponse.isDepositpaid());
        Assert.assertEquals(request.getBookingdates().getCheckout(), getResponse.getBookingdates().getCheckout());
        Assert.assertEquals(request.getBookingdates().getCheckin(), getResponse.getBookingdates().getCheckin());
    }

    @Story("Delete booking")
    @Description("Checks that a deleted booking can no longer be retrieved")
    @Test(groups = "regression")
    public void deleteBookingShouldRemoveBooking() {
        CreateBookingRequestDto request = BookingRequestFactory.validBooking();
        CreateBookingResponseDto response = bookingClient.createBookingAsDto(request);
        bookingId = response.getBookingid();
        token = getAdminToken();

        Response deleteResponse  = bookingClient.deleteBooking(bookingId, token);
        deleteResponse.then().spec(createdResponseSpec());

        Response getResponse = bookingClient.getBooking(bookingId);
        getResponse.then().spec(notFoundResponseSpec());

        bookingId = 0;
        token = null;
    }

    @Story("Booking authorization")
    @Description("Checks that booking cannot be updated without authentication")
    @Test(groups = "regression")
    public void updateBookingWithoutTokenShouldReturnForbidden() {
        CreateBookingRequestDto request = BookingRequestFactory.validBooking();
        CreateBookingResponseDto response = bookingClient.createBookingAsDto(request);
        bookingId = response.getBookingid();

        CreateBookingRequestDto updatedRequest  = new CreateBookingRequestDto();
        BookingDatesDto dates = new BookingDatesDto();
        dates.setCheckin("2026-08-15");
        dates.setCheckout("2026-08-20");

        updatedRequest .setFirstname("sdfdsf");
        updatedRequest .setLastname("fghfghfghgf");
        updatedRequest .setTotalprice(12300);
        updatedRequest .setDepositpaid(true);
        updatedRequest .setBookingdates(dates);
        updatedRequest .setAdditionalneeds("sdfsdfgsdfgswwaas");

        Response putResponse = bookingClient.updateBookingWithoutAuth(bookingId, updatedRequest);

        putResponse.then().spec(forbiddenResponseSpec());

        BookingResponseDto getResponse = bookingClient.getBookingAsDto(bookingId);
        BookingAssertions.assertBookingEqualsRequest(getResponse, request);

        token = getAdminToken();
    }

    @Story("Booking authorization")
    @Description("Checks that booking cannot be updated with an invalid token")
    @Test(groups = "regression")
    public void updateBookingWithInvalidTokenShouldReturnForbidden() {
        CreateBookingRequestDto request = BookingRequestFactory.validBooking();
        CreateBookingResponseDto response = bookingClient.createBookingAsDto(request);
        bookingId = response.getBookingid();
        token = "invalid-token123";

        CreateBookingRequestDto updatedRequest  = new CreateBookingRequestDto();
        BookingDatesDto dates = new BookingDatesDto();
        dates.setCheckin("2026-08-15");
        dates.setCheckout("2026-08-20");

        updatedRequest.setFirstname("sdfdsf");
        updatedRequest.setLastname("fghfghfghgf");
        updatedRequest.setTotalprice(12300);
        updatedRequest.setDepositpaid(true);
        updatedRequest.setBookingdates(dates);
        updatedRequest.setAdditionalneeds("sdfsdfgsdfgswwaas");

        Response putResponse = bookingClient.updateBooking(bookingId, token, updatedRequest);

        putResponse.then().spec(forbiddenResponseSpec());

        BookingResponseDto getResponse = bookingClient.getBookingAsDto(bookingId);
        BookingAssertions.assertBookingEqualsRequest(getResponse, request);

        token = getAdminToken();
    }

    @Story("Booking CRUD flow")
    @Description("Checks the complete booking lifecycle: create, get, update, patch and delete")
    @Test(groups = "regression")
    public void fullBookingCrudFlowShouldWorkCorrectly() {
        CreateBookingRequestDto request = BookingRequestFactory.validBooking();
        CreateBookingResponseDto response = bookingClient.createBookingAsDto(request);
        bookingId = response.getBookingid();
        token = getAdminToken();

        BookingResponseDto getResponse = bookingClient.getBookingAsDto(bookingId);
        BookingAssertions.assertBookingEqualsRequest(getResponse, request);

        CreateBookingRequestDto updatedRequest  = new CreateBookingRequestDto();
        BookingDatesDto dates = new BookingDatesDto();
        dates.setCheckin("2026-08-15");
        dates.setCheckout("2026-08-20");

        updatedRequest.setFirstname("sdfdsf");
        updatedRequest.setLastname("fghfghfghgf");
        updatedRequest.setTotalprice(12300);
        updatedRequest.setDepositpaid(true);
        updatedRequest.setBookingdates(dates);
        updatedRequest.setAdditionalneeds("sdfsdfgsdfgswwaas");

        BookingResponseDto putResponse = bookingClient.updateBookingAsDto(bookingId, token, updatedRequest);
        BookingAssertions.assertBookingEqualsRequest(putResponse, updatedRequest);

        PatchBookingRequestDto requestDto = new PatchBookingRequestDto();
        requestDto.setFirstname("Anton");
        requestDto.setAdditionalneeds("Laptop");

        BookingResponseDto patchResponse = bookingClient.patchBookingAsDto(bookingId, token, requestDto);

        BookingResponseDto getResponse2 = bookingClient.getBookingAsDto(bookingId);

        Assert.assertEquals(requestDto.getFirstname(), patchResponse.getFirstname());
        Assert.assertEquals(requestDto.getAdditionalneeds(), patchResponse.getAdditionalneeds());
        Assert.assertEquals(updatedRequest.getLastname(), patchResponse.getLastname());
        Assert.assertEquals(updatedRequest.getTotalprice(), patchResponse.getTotalprice());
        Assert.assertEquals(updatedRequest.isDepositpaid(), patchResponse.isDepositpaid());
        Assert.assertEquals(updatedRequest.getBookingdates().getCheckout(), patchResponse.getBookingdates().getCheckout());
        Assert.assertEquals(updatedRequest.getBookingdates().getCheckin(), patchResponse.getBookingdates().getCheckin());

        Response deleteResponse = bookingClient.deleteBooking(bookingId, token);
        deleteResponse.then().spec(createdResponseSpec());

        Response getResponseAfterDelete = bookingClient.getBooking(bookingId);
        getResponseAfterDelete.then().spec(notFoundResponseSpec());

        bookingId = 0;
        token = null;
    }
}