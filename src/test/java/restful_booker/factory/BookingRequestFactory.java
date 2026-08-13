package restful_booker.factory;

import restful_booker.dto.request.BookingDatesDto;
import restful_booker.dto.request.CreateBookingRequestDto;

public final class BookingRequestFactory {

    private BookingRequestFactory() {
    }

    public static CreateBookingRequestDto validBooking() {
        BookingDatesDto dates = new BookingDatesDto();
        dates.setCheckin("2026-08-10");
        dates.setCheckout("2026-08-15");

        CreateBookingRequestDto request = new CreateBookingRequestDto();
        request.setFirstname("Igor");
        request.setLastname("Tester");
        request.setTotalprice(500);
        request.setDepositpaid(true);
        request.setBookingdates(dates);
        request.setAdditionalneeds("Breakfast");

        return request;
    }
}