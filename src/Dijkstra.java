import java.util.*;
public class Dijkstra {
    //The main method for finding the shortest path from a start city to an end city
    public static PathResult findShortestPath(AirlineGraph graph, String start, String end, String criteria) {
        // Tracks the shortest known distance from the start city to each city (initialized later with inf distance
        // for all cities except start, which has 0)
        Map<String, Double> distances = new HashMap<>();
        // Used later on for path reconstruction
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

        List<Flight> path = reconstructPath(previousFlights, start, end);
        double total;
        if(path != null){
            total = distances.get(end);
        }else{
            total = 0.0;
        }
        return new PathResult(path, total);
    }

    //Helper method for path reconstruction?
    private static List<Flight> reconstructPath(Map<String, Flight> previousFlights, String start, String end) {
        List<Flight> path = new ArrayList<>();
        String currentCity = end;
        while (previousFlights.containsKey(currentCity)) {
            Flight flight = previousFlights.get(currentCity);
            path.addFirst(flight);
            currentCity = flight.getSource();
        }
        if (path.isEmpty() || !path.getFirst().getSource().equals(start)){
            return null;
        }else {
            return path;
        }
    }

    // Helper Class for nodes in the graph?
    static class Node {
        String city;
        double distance;
        Node(String city, double distance) { this.city = city; this.distance = distance; }
    }

    // Helper class for something?
    public static class PathResult {
        public final List<Flight> flights;
        public final double total;
        PathResult(List<Flight> flights, double total) { this.flights = flights; this.total = total; }
    }
}
