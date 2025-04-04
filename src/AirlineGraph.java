import java.util.*;

/**
 * AirlineGraph class for the Airline Reservation System
 * Provides the data structure for the flight network represented as a graph
 * Each city is a vertex/node and each flight is a directed edge with cost and duration attributes
 */
public class AirlineGraph {
    // Adjacency list representation of the graph
    // Key: Source city (String)
    // Value: List of flights departing from the source city
    private final Map<String, List<Flight>> adjacencyList = new HashMap<>();

    /**
     * Adds a new flight to the graph
     *
     * @param source The departure city
     * @param destination The arrival city
     * @param cost The flight cost in dollars
     * @param duration The flight duration in minutes
     */
    public void addFlight(String source, String destination, double cost, int duration) {
        // Validate that cost and duration are positive values
        if(cost <= 0 || duration <= 0){
            System.out.println("Zero or negative cost or duration, flight cannot be added");
            return;
        }

        // Create a new Flight object with the provided parameters
        Flight flight = new Flight(source, destination, cost, duration);

        // Add the flight to the adjacency list
        // If the source city doesn't exist in the map yet, create a new list for it
        // Uses computeIfAbsent, a method of Map class in Java introduced in Java 8+
        adjacencyList.computeIfAbsent(source, _ -> new ArrayList<>()).add(flight);
    }

    /**
     * Gets all flights departing from a specific city
     *
     * @param city The source city
     * @return List of Flight objects representing all outgoing flights from the city
     *         Returns an empty list if the city has no outgoing flights or doesn't exist
     */
    public List<Flight> getFlightsFrom(String city) {
        // Return the list of flights from the specified city
        // If the city doesn't exist in the map, return an empty list
        return adjacencyList.getOrDefault(city, new ArrayList<>());
    }

    /**
     * Gets all cities in the network (both source and destination)
     *
     * @return Set of city names represented as strings
     */
    public Set<String> getCities() {
        // Create a set to store unique city names
        Set<String> cities = new HashSet<>();

        // Iterate through all flights in the adjacency list
        for (List<Flight> flights : adjacencyList.values()) {
            for (Flight flight : flights) {
                // Add both source and destination cities to the set
                // HashSet automatically handles duplicates
                cities.add(flight.getSource());       // Add source city
                cities.add(flight.getDestination());  // Add destination city
            }
        }

        // Return the set of all cities in the network
        return cities;
    }
}
