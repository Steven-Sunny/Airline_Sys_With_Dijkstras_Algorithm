import java.util.*;
public class Dijkstra {
    //The main method for finding the shortest path from a start city to an end city
    public static PathResult findShortestPath(AirlineGraph graph, String start, String end, String criteria) {
        // Tracks the shortest known distance from the start city to each city (initialized later with inf distance
        // for all cities except start, which has 0)
        Map<String, Double> distances = new HashMap<>();
        // Used later on for path reconstruction; holds the optimal path
        Map<String, Flight> previousFlights = new HashMap<>();
        // A priority queue for always processing the city with the smallest known distance first, the most important
        // part of the method
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance));

        // Initialize distances; all cities that are not starting have inf distance; start city has 0 distance
        for (String city : graph.getCities()) {
            // MAX_VALUE is a large number, might as well be infinite
            distances.put(city, Double.MAX_VALUE);
        }
        // Start city has 0 distance
        distances.put(start, 0.0);
        // Enqueue start city into the priority queue to start
        pq.add(new Node(start, 0.0));

        // This is the main Dijkstra algorithm
        // Priority queue pq drives this algorithm, always expanding the city with the shortest distance first (greedy)
        while (!pq.isEmpty()) {
            //Gets the current city from pq
            Node current = pq.poll();
            String currentCity = current.city;
            // If current city is the end city (the city we wanted to reach) we can break out of the algo
            if (currentCity.equals(end)) break;
            // If a city has a longer (less optimal path) it is ignored and the loop is continued to the next iteration
            if (current.distance > distances.get(currentCity)) continue;

            // Edge relaxation: if going to a node V from a source node is easier through another node U than directly
            // from the source node to V, update the pathing to go through node U
            // This for loop iterates through all the flights originating from the current city
            for (Flight flight : graph.getFlightsFrom(currentCity)) {
                // Gets the destination city of the flight we are interested in for this iteration
                String neighbour = flight.getDestination();

                //Decides the weight of the edges based on cost or duration (User selected)
                double weight;
                if(criteria.equals("cost")){
                    weight = flight.getCost();
                }else{
                    weight = flight.getDuration();
                }
                // Creates the new weighted distance of the edge
                double newDist = current.distance + weight;

                // If a new, shorter distance is found, update path information
                if (newDist < distances.get(neighbour)) {
                    // Update shortest distance
                    distances.put(neighbour, newDist);
                    // Track the flight used to reach this city
                    previousFlights.put(neighbour, flight);
                    // Add the neighbour to the priority queue
                    pq.add(new Node(neighbour, newDist));
                }
            }
        }

        // Calls reconstruct path for building a list of flights that get from start to end cities
        List<Flight> path = reconstructPath(previousFlights, start, end);
        // Returns a pathing for the shortest path if there is a potential path
        double total;
        if(path != null){
            total = distances.get(end);
        }else{
            // Return 0 if the path is empty (The distance is 0)
            total = 0.0;
        }
        // Returns a pathResult object that includes a lists of flights you need to take to get to your destination
        return new PathResult(path, total);
    }

    // Helper method for path reconstruction; builds the path backwards from end to start using previousFlights
    private static List<Flight> reconstructPath(Map<String, Flight> previousFlights, String start, String end) {
        // List for Flights, stores the flights needed to reach the end city
        List<Flight> path = new ArrayList<>();
        // Stores the first city needed to reconstruct the path end to start
        String currentCity = end;
        // Loops through all cities in previousFlights
        while (previousFlights.containsKey(currentCity)) {
            // Get the flight object from previousFlights
            Flight flight = previousFlights.get(currentCity);
            // Add the flight obtained to the list of path
            path.addFirst(flight);
            // Get the next starting city using the current city; set current city to next city
            currentCity = flight.getSource();
        }

        // If path is empty (no path found) return null; else return the list of the cities on the path.
        if (path.isEmpty() || !path.getFirst().getSource().equals(start)){
            return null;
        }else {
            return path;
        }
    }

    // Helper class for cities in the graph; represents a city (just a convenient object format) and its distance
    // from the original starting city
    static class Node {
        String city;
        // Distance stores the currently known shortest distance from the start city
        double distance;
        // Constructors to build a Node instance
        Node(String city, double distance) { this.city = city; this.distance = distance; }
    }

    // Helper class that acts as a data container for the shortest path that was found
    public static class PathResult {
        // An ordered list that stores the shortest list of flights needed to get to the end city
        // This is a path that was reconstructed by the reconstructPath method, ensuring it has the shortest distance
        public final List<Flight> flights;
        // The final sum of all the weights in the path
        public final double total;
        // Constructors to build a PathResult instance
        PathResult(List<Flight> flights, double total) { this.flights = flights; this.total = total; }
    }
}
