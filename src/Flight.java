import java.util.*;

/**
 * Flight class for the Airline Reservation System
 * Each instance of the flight class has a priority queue attached to self-manage bookings
 */
public class Flight {
    private final String source;
    private final String destination;
    private final double cost;
    private final int duration;
    private final PriorityQueue<BookingRequest> waitingQueue;

    public Flight(String source, String destination, double cost, int duration) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
        this.duration = duration;
        // Defines a new Priority Queue that is ordered based on booking time
        this.waitingQueue = new PriorityQueue<>(Comparator.comparing(BookingRequest::getBookingTime));
    }

    public void bookSeat(BookingRequest request) { // Books a seat
        waitingQueue.add(request);
    }


    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public double getCost() { return cost; }
    public int getDuration() { return duration; }
    public PriorityQueue<BookingRequest> getWaitingQueue() { return waitingQueue; }
}
