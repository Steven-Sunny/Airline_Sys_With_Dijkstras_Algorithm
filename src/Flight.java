//Each instance of the flight class has a priority queue attached to self-manage bookings
import java.util.*;
public class Flight {
    private String source;
    private String destination;
    private double cost;
    private int duration;
    private final PriorityQueue<BookingRequest> waitingQueue;

    public Flight(String source, String destination, double cost, int duration) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
        this.duration = duration;
        this.waitingQueue = new PriorityQueue<>(Comparator.comparing(BookingRequest::getBookingTime)); // Defines a new Priority Queue that is ordered based on booking time
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
