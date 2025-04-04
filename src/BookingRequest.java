/**
 * BookingRequest class for the Airline Reservation System
 * Stores simple information about the customer who is booking and the time they booked at
 */
public class BookingRequest {
    private final String customerId;
    private final long bookingTime;

    public BookingRequest(String customerId, long bookingTime) {
        this.customerId = customerId;
        this.bookingTime = bookingTime;
    }

    public long getBookingTime() { return bookingTime; }
    public String getCustomerId() { return customerId; }
}
