package restful_booker.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import restful_booker.dto.request.CreateBookingRequestDto;
import restful_booker.dto.request.PatchBookingRequestDto;
import restful_booker.dto.response.BookingResponseDto;
import restful_booker.dto.response.CreateBookingResponseDto;

import static restful_booker.specs.ApiSpecs.okJsonResponseSpec;

public class BookingClient extends BaseBookerClient {

    @Step("Create booking")
    public Response createBooking(CreateBookingRequestDto requestDto) {
        return request()
                .body(requestDto)
                .when()
                .post("/booking");
    }

    public CreateBookingResponseDto createBookingAsDto(CreateBookingRequestDto requestDto) {
        return createBooking(requestDto).then()
                .spec(okJsonResponseSpec())
                .extract()
                .as(CreateBookingResponseDto.class);
    }

    @Step("Get booking by id: {bookingId}")
    public Response getBooking(int bookingId) {
        return request()
                .pathParam("bookingId", bookingId)
                .when()
                .get("/booking/{bookingId}");
    }

    public BookingResponseDto getBookingAsDto(int bookingId) {
        return getBooking(bookingId)
                .then()
                .spec(okJsonResponseSpec())
                .extract()
                .as(BookingResponseDto.class);
    }

    @Step("Update booking by id: {bookingId}")
    public Response updateBooking(
            int bookingId,
            String token,
            CreateBookingRequestDto requestDto
    ) {
        return request()
                .cookie("token", token)
                .pathParam("bookingId", bookingId)
                .body(requestDto)
                .when()
                .put("/booking/{bookingId}");
    }

    public BookingResponseDto updateBookingAsDto(
            int bookingId,
            String token,
            CreateBookingRequestDto requestDto
    ) {
        return updateBooking(bookingId, token, requestDto)
                .then()
                .spec(okJsonResponseSpec())
                .extract()
                .as(BookingResponseDto.class);
    }

    @Step("Patch booking by id: {bookingId}")
    public Response patchBooking(
            int bookingId,
            String token,
            PatchBookingRequestDto requestDto
    ) {
        return request()
                .cookie("token", token)
                .pathParam("bookingId", bookingId)
                .body(requestDto)
                .when()
                .patch("/booking/{bookingId}");
    }

    public BookingResponseDto patchBookingAsDto(
            int bookingId,
            String token,
            PatchBookingRequestDto requestDto
    ) {
        return patchBooking(bookingId, token, requestDto)
                .then()
                .spec(okJsonResponseSpec())
                .extract()
                .as(BookingResponseDto.class);
    }

    @Step("Delete booking by id: {bookingId}")
    public Response deleteBooking(int bookingId, String token) {
        return request()
                .cookie("token", token)
                .pathParam("bookingId", bookingId)
                .when()
                .delete("/booking/{bookingId}");
    }

    public Response updateBookingWithoutAuth(
            int bookingId,
            CreateBookingRequestDto requestDto
    ) {
        return request()
                .pathParam("bookingId", bookingId)
                .body(requestDto)
                .when()
                .put("/booking/{bookingId}");
    }
}