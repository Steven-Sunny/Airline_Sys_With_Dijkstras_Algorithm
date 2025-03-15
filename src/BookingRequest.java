public class BookingRequest {
    private String customerId;
    private long bookingTime;

    public BookingRequest(String customerId, long bookingTime) {
        this.customerId = customerId;
        this.bookingTime = bookingTime;
    }

    public long getBookingTime() { return bookingTime; }
    public String getCustomerId() { return customerId; }
}
