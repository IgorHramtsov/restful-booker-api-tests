package restful_booker.dto.response;

import restful_booker.dto.request.CreateBookingRequestDto;

public class CreateBookingResponseDto {

    private int bookingid;
    private CreateBookingRequestDto booking;

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public CreateBookingRequestDto getBooking() {
        return booking;
    }

    public void setBooking(CreateBookingRequestDto booking) {
        this.booking = booking;
    }
}