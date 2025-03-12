public class Flight {
    private String source;
    private String destination;
    private double cost;
    private int duration;
    private int availableSeats;

    public Flight(String source, String destination, double cost, int duration, int availableSeats) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
        this.duration = duration;
        this.availableSeats = availableSeats;
    }

    public boolean bookSeat() {
        if(availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public double getCost() { return cost; }
    public int getDuration() { return duration; }
    public int getAvailableSeats() { return availableSeats; }
}
