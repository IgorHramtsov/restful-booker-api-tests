package restful_booker.assertions;

import org.testng.Assert;
import restful_booker.dto.request.CreateBookingRequestDto;
import restful_booker.dto.response.BookingResponseDto;

public class BookingAssertions {

    private BookingAssertions() {
    }

    public static void assertBookingEqualsRequest(BookingResponseDto actual, CreateBookingRequestDto expected) {
        Assert.assertEquals(actual.getFirstname(), expected.getFirstname());
        Assert.assertEquals(actual.getLastname(), expected.getLastname());
        Assert.assertEquals(actual.getTotalprice(), expected.getTotalprice());
        Assert.assertEquals(actual.getAdditionalneeds(), expected.getAdditionalneeds());
        Assert.assertEquals(actual.isDepositpaid(), expected.isDepositpaid());
        Assert.assertEquals(actual.getBookingdates().getCheckin(), expected.getBookingdates().getCheckin());
        Assert.assertEquals(actual.getBookingdates().getCheckout(), expected.getBookingdates().getCheckout());
    }
}
