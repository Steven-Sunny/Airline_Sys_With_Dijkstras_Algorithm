//Each instance of the flight class has a priority queue attached to self-manage bookings
import java.util.*;
public class Flight {
    private String source;
    private String destination;
    private double cost;
    private int duration;
    private int availableSeats;
    private final PriorityQueue<BookingRequest> waitingQueue;

    public Flight(String source, String destination, double cost, int duration, int availableSeats) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
        this.duration = duration;
        this.availableSeats = availableSeats;
        this.waitingQueue = new PriorityQueue<>(Comparator.comparing(BookingRequest::getBookingTime)); // Defines a new Priority Queue that is ordered based on booking time
    }

    public boolean bookSeat() { // Books a seat
        if(availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    public void cancelBooking() { // Cancels the booking made with the greatest priority
        availableSeats++;
        if (!waitingQueue.isEmpty()) { // Check to see if priority queue is empty
            BookingRequest request = waitingQueue.poll(); // If not empty removes the first in priority queue
            availableSeats--; // Next in line becomes new head
        }
    }

    public void addToQueue(BookingRequest request) {
        waitingQueue.add(request);
    }

    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public double getCost() { return cost; }
    public int getDuration() { return duration; }
    public int getAvailableSeats() { return availableSeats; }
}
