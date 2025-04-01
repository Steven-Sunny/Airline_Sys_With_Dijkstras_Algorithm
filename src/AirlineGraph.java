import java.util.*;

public class AirlineGraph {
    private final Map<String, List<Flight>> adjacencyList = new HashMap<>();

    public void addFlight(String source, String destination, double cost, int duration) {
        Flight flight = new Flight(source, destination, cost, duration);
        adjacencyList.computeIfAbsent(source, k -> new ArrayList<>()).add(flight);
        //Adds a new flight attached to a destination, if there are no flights, it adds a new arraylist
        //using computeIfAbsent, a method of Map class in Java. Uses lambda syntax(->) introduced in Java 8+
    }

    public List<Flight> getFlightsFrom(String city) {
        return adjacencyList.getOrDefault(city, new ArrayList<>());
    }

    public Set<String> getCities() {
        Set<String> cities = new HashSet<>();

        // Iterate through all flights to collect source and destination cities
        for (List<Flight> flights : adjacencyList.values()) {
            for (Flight flight : flights) {
                cities.add(flight.getSource());       // Add source city
                cities.add(flight.getDestination());  // Add destination city
            }
        }
        return cities;
    }
}
