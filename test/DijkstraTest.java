import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class DijkstraTest {

    @Test
    void testDirectFlight() {

        AirlineGraph graph = new AirlineGraph();
        graph.addFlight("A", "B", 100.0, 2);

        Dijkstra.PathResult resultCost = Dijkstra.findShortestPath(graph, "A", "B", "cost");
        assertNotNull(resultCost.flights);
        assertEquals(1, resultCost.flights.size());
        assertEquals("A", resultCost.flights.getFirst().getSource());
        assertEquals("B", resultCost.flights.getFirst().getDestination());
        assertEquals(100.0, resultCost.total);

        Dijkstra.PathResult resultDuration = Dijkstra.findShortestPath(graph, "A", "B", "duration");
        assertEquals(2.0, resultDuration.total);

    }

    @Test
    void testNegativeInputs(){
        AirlineGraph graph = new AirlineGraph();
        graph.addFlight("A", "B", -1.0, -1);
        Dijkstra.PathResult resultCost = Dijkstra.findShortestPath(graph, "A", "B", "cost");
        assertNull(resultCost.flights);
    }

    @Test
    void testIndirectPathCheaper() {

        AirlineGraph graph = new AirlineGraph();
        graph.addFlight("A", "B", 150.0, 5);
        graph.addFlight("A", "C", 50.0, 10);
        graph.addFlight("C", "B", 50.0, 2);

        Dijkstra.PathResult resultCost = Dijkstra.findShortestPath(graph, "A", "B", "cost");
        assertNotNull(resultCost.flights);
        assertEquals(2, resultCost.flights.size());
        assertEquals("A", resultCost.flights.get(0).getSource());
        assertEquals("C", resultCost.flights.get(0).getDestination());
        assertEquals("C", resultCost.flights.get(1).getSource());
        assertEquals("B", resultCost.flights.get(1).getDestination());
        assertEquals(100.0, resultCost.total);

        Dijkstra.PathResult resultDuration = Dijkstra.findShortestPath(graph, "A", "B", "duration");
        assertEquals(1, resultDuration.flights.size());
        assertEquals(5.0, resultDuration.total);

    }

    @Test
    void testNoPathExists() {
        AirlineGraph graph = new AirlineGraph();

        Dijkstra.PathResult result = Dijkstra.findShortestPath(graph, "A", "B", "cost");
        assertNull(result.flights);
        assertEquals(0.0, result.total);

    }

    @Test
    void testPathReconstructionOrder() {
        AirlineGraph graph = new AirlineGraph();
        graph.addFlight("A", "B", 10.0, 1);
        graph.addFlight("B", "C", 10.0, 1);
        graph.addFlight("C", "D", 10.0, 1);

        Dijkstra.PathResult result = Dijkstra.findShortestPath(graph, "A", "D", "cost");
        assertNotNull(result.flights);
        assertEquals(3, result.flights.size());
        assertEquals("A", result.flights.get(0).getSource());
        assertEquals("B", result.flights.get(0).getDestination());
        assertEquals("B", result.flights.get(1).getSource());
        assertEquals("C", result.flights.get(1).getDestination());
        assertEquals("C", result.flights.get(2).getSource());
        assertEquals("D", result.flights.get(2).getDestination());
        assertEquals(30.0, result.total);

    }

    @Test
    void testStartIsEndOrLoop() {
        AirlineGraph graph = new AirlineGraph();
        Dijkstra.PathResult result = Dijkstra.findShortestPath(graph, "A", "A", "cost");


        assertNull(result.flights);
        assertEquals(0.0, result.total);
    }

    @Test
    void testMultipleFlightsBetweenSameCities() {
        AirlineGraph graph = new AirlineGraph();
        long startTime = System.nanoTime();
        graph.addFlight("A", "B", 100.0, 5);
        graph.addFlight("A", "B", 80.0, 10);

        Dijkstra.PathResult resultCost = Dijkstra.findShortestPath(graph, "A", "B", "cost");
        assertEquals(80.0, resultCost.total);

        Dijkstra.PathResult resultDuration = Dijkstra.findShortestPath(graph, "A", "B", "duration");
        assertEquals(5.0, resultDuration.total);

        long duration = System.nanoTime() - startTime;
        System.out.println("testMultipleFlightsBetweenSameCities executed in: " +
                duration + " ns (" +
                (duration / 1_000_000.0) + " ms)");
    }

    @Test
    void testDijkstraRealistic(){
        AirlineGraph graph = new AirlineGraph();

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

        long startTime = System.nanoTime();
        Dijkstra.PathResult result = Dijkstra.findShortestPath(graph, "NY", "TK", "cost");
        assertEquals(3, result.flights.size());
        assertEquals("NY", result.flights.get(0).getSource());
        assertEquals("PD", result.flights.get(0).getDestination());
        assertEquals("PD", result.flights.get(1).getSource());
        assertEquals("DB", result.flights.get(1).getDestination());
        assertEquals("DB", result.flights.get(2).getSource());
        assertEquals("TK", result.flights.get(2).getDestination());

        for (Flight flight : result.flights) {
            System.out.println(
                    flight.getSource() + " -> " + flight.getDestination() +
                            " (Cost: $" + String.format("%.2f", flight.getCost()) +
                            ", Duration: " + flight.getDuration() + ")");
        }

        long duration = System.nanoTime() - startTime;
        System.out.println("testDijkstraRealistic executed in: " +
                duration + " ns (" +
                (duration / 1_000_000.0) + " ms)");
    }

    @Test
    void testDijkstraPerformance() {
        AirlineGraph graph = new AirlineGraph();
        // Populate graph with test flights
        for (int i = 1; i <= 1000; i++) { // Adjust i upper bound for larger inputs
            graph.addFlight("City" + i, "City" + (i + 1), i * 10.5, i * 3);
        }
        //List<Double> times = new ArrayList<>();
        int[] testSizes = {1, 25, 250, 1000};
        Dijkstra.findShortestPath(graph, "City1", "City" + 1, "cost");

        for (int size : testSizes) {
            long startTime = System.nanoTime();
            Dijkstra.findShortestPath(graph, "City1", "City" + size, "cost");
            long endTime = System.nanoTime();

            double elapsedTime = (endTime - startTime) / 1_000_000.0; // Convert to milliseconds
            //times.add(elapsedTime);

            System.out.println("Input Size: " + size + ", Time Taken: " + elapsedTime + " ms");
        }

//        Ensure that time increases as input size increases (basic validation)
//        assertTrue(times.get(0) <= times.get(1));
//        assertTrue(times.get(1) <= times.get(2));
//        assertTrue(times.get(2) <= times.get(3));
//        These lines of assertations are too finicky and depend on way too many factors to be a reliable test case
//        Uncomment these and the lines related to times to run the assertations
    }

    @Test
    void testbookSeats(){
        AirlineGraph graph = new AirlineGraph();
        graph.addFlight("A", "B", 1.0, 1);
        Dijkstra.PathResult path = Dijkstra.findShortestPath(graph, "A", "B" , "cost");
        Flight flight = path.flights.getFirst();
        flight.bookSeat(new BookingRequest("Great Customer", 1000));
        PriorityQueue<BookingRequest> copy = new PriorityQueue<>(flight.getWaitingQueue());
        while (!copy.isEmpty()) {
            BookingRequest booking = copy.poll();
            System.out.print("Customer Name: " + booking.getCustomerId() + "\nBooking ID: " + booking.getBookingTime());
        }
        copy = flight.getWaitingQueue();
        BookingRequest booking = copy.poll();
        assertEquals("Great Customer", booking.getCustomerId());
        assertEquals(1000, booking.getBookingTime());
    }
}