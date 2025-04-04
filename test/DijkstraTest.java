import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Test class for the Dijkstra's algorithm and entire implementation
 * Tests various scenarios for finding shortest paths in an airline graph
 * Tests some capabilities of booking flights
 */
public class DijkstraTest {

    /**
     * Test case for a direct flight between two cities
     * Verifies that the algorithm correctly finds a direct path
     * and calculates the proper cost/duration
     */
    @Test
    void testDirectFlight() {
        // Create a simple graph with two cities
        AirlineGraph graph = new AirlineGraph();
        // Add a flight from A to B with cost 100 and duration 2
        graph.addFlight("A", "B", 100.0, 2);

        // Test path optimization by cost
        Dijkstra.PathResult resultCost = Dijkstra.findShortestPath(graph, "A", "B", "cost");
        // Verify the path exists
        assertNotNull(resultCost.flights);
        // Verify only one flight is in the path
        assertEquals(1, resultCost.flights.size());
        // Verify correct source and destination
        assertEquals("A", resultCost.flights.getFirst().getSource());
        assertEquals("B", resultCost.flights.getFirst().getDestination());
        // Verify total cost
        assertEquals(100.0, resultCost.total);

        // Test path optimization by duration
        Dijkstra.PathResult resultDuration = Dijkstra.findShortestPath(graph, "A", "B", "duration");
        // Verify total duration
        assertEquals(2.0, resultDuration.total);
    }

    /**
     * Test case for negative inputs (invalid flight parameters)
     * Verifies that the algorithm properly handles negative costs/durations
     */
    @Test
    void testNegativeInputs() {
        AirlineGraph graph = new AirlineGraph();
        // Add an invalid flight with negative cost and duration
        graph.addFlight("A", "B", -1.0, -1);
        // Attempt to find path
        Dijkstra.PathResult resultCost = Dijkstra.findShortestPath(graph, "A", "B", "cost");
        // Verify no path is found (null result)
        assertNull(resultCost.flights);
    }

    /**
     * Test case for indirect paths that are cheaper than direct ones
     * Verifies that the algorithm selects the cheaper route when optimizing for cost
     * and the faster route when optimizing for duration
     */
    @Test
    void testIndirectPathCheaper() {
        AirlineGraph graph = new AirlineGraph();
        // Direct flight from A to B: expensive but fast
        graph.addFlight("A", "B", 150.0, 5);
        // Indirect path A->C->B: cheaper but slower
        graph.addFlight("A", "C", 50.0, 10);
        graph.addFlight("C", "B", 50.0, 2);

        // Test path optimization by cost
        Dijkstra.PathResult resultCost = Dijkstra.findShortestPath(graph, "A", "B", "cost");
        // Verify path exists
        assertNotNull(resultCost.flights);
        // Verify indirect path (2 flights) is selected
        assertEquals(2, resultCost.flights.size());
        // Verify the correct sequence of flights (A->C->B)
        assertEquals("A", resultCost.flights.get(0).getSource());
        assertEquals("C", resultCost.flights.get(0).getDestination());
        assertEquals("C", resultCost.flights.get(1).getSource());
        assertEquals("B", resultCost.flights.get(1).getDestination());
        // Verify total cost (50 + 50 = 100)
        assertEquals(100.0, resultCost.total);

        // Test path optimization by duration
        Dijkstra.PathResult resultDuration = Dijkstra.findShortestPath(graph, "A", "B", "duration");
        // Verify direct path (1 flight) is selected as it's faster
        assertEquals(1, resultDuration.flights.size());
        // Verify total duration
        assertEquals(5.0, resultDuration.total);
    }

    /**
     * Test case for when no path exists between cities
     * Verifies that the algorithm correctly returns null when no route is available
     */
    @Test
    void testNoPathExists() {
        // Create empty graph with no flights
        AirlineGraph graph = new AirlineGraph();

        // Attempt to find path between non-connected cities
        Dijkstra.PathResult result = Dijkstra.findShortestPath(graph, "A", "B", "cost");
        // Verify no path is found
        assertNull(result.flights);
        // Verify total cost is zero
        assertEquals(0.0, result.total);
    }

    /**
     * Test case for proper path reconstruction order
     * Verifies that flights are returned in the correct sequence
     */
    @Test
    void testPathReconstructionOrder() {
        AirlineGraph graph = new AirlineGraph();
        // Create a linear path A->B->C->D
        graph.addFlight("A", "B", 10.0, 1);
        graph.addFlight("B", "C", 10.0, 1);
        graph.addFlight("C", "D", 10.0, 1);

        // Find shortest path from A to D
        Dijkstra.PathResult result = Dijkstra.findShortestPath(graph, "A", "D", "cost");
        // Verify path exists
        assertNotNull(result.flights);
        // Verify path contains 3 flights
        assertEquals(3, result.flights.size());
        // Verify flights are in correct order A->B->C->D
        assertEquals("A", result.flights.get(0).getSource());
        assertEquals("B", result.flights.get(0).getDestination());
        assertEquals("B", result.flights.get(1).getSource());
        assertEquals("C", result.flights.get(1).getDestination());
        assertEquals("C", result.flights.get(2).getSource());
        assertEquals("D", result.flights.get(2).getDestination());
        // Verify total cost (10 + 10 + 10 = 30)
        assertEquals(30.0, result.total);
    }

    /**
     * Test case for when start equals end or there's a loop
     * Verifies that the algorithm handles same source and destination correctly
     */
    @Test
    void testStartIsEndOrLoop() {
        AirlineGraph graph = new AirlineGraph();
        // Find path from A to A (same city)
        Dijkstra.PathResult result = Dijkstra.findShortestPath(graph, "A", "A", "cost");

        // Verify no path is found (as expected)
        assertNull(result.flights);
        // Verify total cost is zero
        assertEquals(0.0, result.total);
    }

    /**
     * Test case for multiple flights between the same cities
     * Verifies that the algorithm selects the cheapest flight when optimizing for cost
     * and the fastest flight when optimizing for duration
     */
    @Test
    void testMultipleFlightsBetweenSameCities() {
        AirlineGraph graph = new AirlineGraph();
        // Start timing for performance measurement
        long startTime = System.nanoTime();

        // Add two different flights between A and B:
        // 1. More expensive but faster
        graph.addFlight("A", "B", 100.0, 5);
        // 2. Cheaper but slower
        graph.addFlight("A", "B", 80.0, 10);

        // Test path optimization by cost
        Dijkstra.PathResult resultCost = Dijkstra.findShortestPath(graph, "A", "B", "cost");
        // Verify cheaper flight is selected
        assertEquals(80.0, resultCost.total);

        // Test path optimization by duration
        Dijkstra.PathResult resultDuration = Dijkstra.findShortestPath(graph, "A", "B", "duration");
        // Verify faster flight is selected
        assertEquals(5.0, resultDuration.total);

        // Calculate and print execution time
        long duration = System.nanoTime() - startTime;
        System.out.println("testMultipleFlightsBetweenSameCities executed in: " +
                duration + " ns (" +
                (duration / 1_000_000.0) + " ms)");
    }

    /**
     * Test case with a more realistic flight network
     * Uses city codes to simulate a real-world scenario and tests
     * if Dijkstra's algorithm finds the optimal path
     */
    @Test
    void testDijkstraRealistic() {
        AirlineGraph graph = new AirlineGraph();

        // Create a complex flight network with realistic city codes
        // NY = New York, PD = Portland, LD = London, TK = Tokyo, DB = Dubai, LA = Los Angeles
        graph.addFlight("NY", "PD", 450.0, 1);
        graph.addFlight("NY", "LD", 500.0, 1);
        graph.addFlight("PD", "LD", 100.0, 1);
        graph.addFlight("PD", "TK", 850.0, 1);
        graph.addFlight("TK", "PD", 850.0, 1);
        graph.addFlight("LD", "DB", 600.0, 1);
        graph.addFlight("PD", "DB", 550.0, 1);
        graph.addFlight("DB", "TK", 100.0, 1);
        graph.addFlight("TK", "DB", 100.0, 1);
        graph.addFlight("TK", "LA", 900.0, 1);
        graph.addFlight("LA", "TK", 900.0, 1);

        // Start timing for performance measurement
        long startTime = System.nanoTime();

        // Find the cheapest path from New York to Tokyo
        Dijkstra.PathResult result = Dijkstra.findShortestPath(graph, "NY", "TK", "cost");

        // Verify optimal path has 3 flights
        assertEquals(3, result.flights.size());
        // Verify the path is NY->PD->DB->TK
        assertEquals("NY", result.flights.get(0).getSource());
        assertEquals("PD", result.flights.get(0).getDestination());
        assertEquals("PD", result.flights.get(1).getSource());
        assertEquals("DB", result.flights.get(1).getDestination());
        assertEquals("DB", result.flights.get(2).getSource());
        assertEquals("TK", result.flights.get(2).getDestination());

        // Print the path details for inspection
        for (Flight flight : result.flights) {
            System.out.println(
                    flight.getSource() + " -> " + flight.getDestination() +
                            " (Cost: $" + String.format("%.2f", flight.getCost()) +
                            ", Duration: " + flight.getDuration() + ")");
        }

        // Calculate and print execution time
        long duration = System.nanoTime() - startTime;
        System.out.println("testDijkstraRealistic executed in: " +
                duration + " ns (" +
                (duration / 1_000_000.0) + " ms)");
    }

    /**
     * Performance test for Dijkstra's algorithm
     * Creates a large graph and measures execution time for various path lengths
     */
    @Test
    void testDijkstraPerformance() {
        AirlineGraph graph = new AirlineGraph();
        // Create a chain of 1000 connected cities
        for (int i = 1; i <= 1000; i++) { // Can adjust upper bound for larger inputs
            graph.addFlight("City" + i, "City" + (i + 1), i * 10.5, i * 3);
        }

        // Test sizes to measure performance
        int[] testSizes = {1, 25, 250, 1000};

        // Warm up the JVM (first run often has overhead)
        Dijkstra.findShortestPath(graph, "City1", "City" + 1, "cost");

        // Test with different path lengths
        for (int size : testSizes) {
            long startTime = System.nanoTime();
            Dijkstra.findShortestPath(graph, "City1", "City" + size, "cost");
            long endTime = System.nanoTime();

            // Calculate execution time in milliseconds
            double elapsedTime = (endTime - startTime) / 1_000_000.0;

            // Print results
            System.out.println("Input Size: " + size + ", Time Taken: " + elapsedTime + " ms");
        }

        // NOTE: The commented out code below would verify that execution time increases
        // with input size, but was removed as it's unreliable due to JVM optimizations
        //
        // List<Double> times = new ArrayList<>(); // Uncomment to collect times
        // times.add(elapsedTime); // Uncomment to collect times
        //
        // Ensure that time increases as input size increases (basic validation)
        // assertTrue(times.get(0) <= times.get(1));
        // assertTrue(times.get(1) <= times.get(2));
        // assertTrue(times.get(2) <= times.get(3));
        // These assertions are too finicky and depend on too many factors to be reliable
    }

    /**
     * Test case for booking seats on flights
     * Verifies that booking requests are properly stored in the flight's waiting queue
     */
    @Test
    void testbookSeats() {
        AirlineGraph graph = new AirlineGraph();
        // Create a simple graph with one flight
        graph.addFlight("A", "B", 1.0, 1);

        // Find path and get the flight
        Dijkstra.PathResult path = Dijkstra.findShortestPath(graph, "A", "B", "cost");
        Flight flight = path.flights.getFirst();

        // Create a booking request
        flight.bookSeat(new BookingRequest("Great Customer", 1000));

        // Make a copy of the waiting queue for inspection
        PriorityQueue<BookingRequest> copy = new PriorityQueue<>(flight.getWaitingQueue());

        // Print all bookings in the queue
        while (!copy.isEmpty()) {
            BookingRequest booking = copy.poll();
            System.out.print("Customer Name: " + booking.getCustomerId() + "\nBooking ID: " + booking.getBookingTime());
        }

        // Get another copy of the queue and verify booking details
        copy = flight.getWaitingQueue();
        BookingRequest booking = copy.poll();
        // Verify customer name
        assertEquals("Great Customer", booking.getCustomerId());
        // Verify booking time
        assertEquals(1000, booking.getBookingTime());
    }
}