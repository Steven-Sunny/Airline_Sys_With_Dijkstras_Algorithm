import java.util.*;

/**
 * Main class for the Airline Reservation System
 * Provides a console interface for managing flights and bookings
 */
public class Main {
    // Core system components
    private final static AirlineGraph graph = new AirlineGraph();  // Graph representing flight network
    private final static Scanner scanner = new Scanner(System.in);  // For user input
    private static Dijkstra.PathResult currentRoute;  // Stores the currently selected route

    /**
     * Main method that displays the menu and handles user choices
     */
    public static void main(String[] args) {

        while (true) {
            // Display main menu options
            System.out.println("\nAirline Reservation System");
            System.out.println("1. Add Flight");
            System.out.println("2. Find Cheapest/Fastest Route");
            System.out.println("3. Book Seats on Current Route");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            // Get user's menu choice
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline after reading integer

            // Process user choice
            switch (choice) {
                case 1:
                    addFlight();  // Add a new flight to the system
                    break;
                case 2:
                    findRoute();  // Find and display optimal route
                    break;
                case 3:
                    bookSeats();  // Book seats on the current route
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();  // Close scanner before exiting
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    /**
     * Handles adding a new flight to the airline network
     * Gets flight details from user and adds to the graph
     */
    private static void addFlight() {
        System.out.println("\nAdd New Flight:");

        // Get flight details from user
        System.out.print("Source City: ");
        String source = scanner.nextLine().trim();

        System.out.print("Destination City: ");
        String destination = scanner.nextLine().trim();

        System.out.print("Cost: ");
        double cost = scanner.nextDouble();

        System.out.print("Duration (minutes): ");
        int duration = scanner.nextInt();

        // Data validation - ensure cost and duration are positive values
        while (cost <= 0 || duration <= 0){
            System.out.println("Invalid cost or duration!");
            System.out.print("Cost: ");
            cost = scanner.nextDouble();
            System.out.print("Duration (minutes): ");
            duration = scanner.nextInt();
        }

        scanner.nextLine();  // Consume newline after reading numeric input

        // Add the validated flight to the graph
        graph.addFlight(source, destination, cost, duration);
        System.out.println("Flight added successfully!");
    }

    /**
     * Finds the optimal route between two cities
     * Uses Dijkstra's algorithm to find shortest path based on cost or duration
     */
    private static void findRoute() {
        System.out.println("\nFind Route:");

        // Get route parameters from user
        System.out.print("Departure City: ");
        String start = scanner.nextLine().trim();

        System.out.print("Destination City: ");
        String end = scanner.nextLine().trim();

        // Get optimization criteria (cost or duration)
        System.out.print("Optimize for (cost/duration): ");
        String criteria = scanner.nextLine().trim().toLowerCase();

        // Validate criteria input
        while (!criteria.equals("cost") && !criteria.equals("duration")) {
            System.out.println("Invalid criteria! Enter 'cost' or 'duration':");
            criteria = scanner.nextLine().trim().toLowerCase();
        }

        // Find the shortest path using Dijkstra's algorithm
        currentRoute = Dijkstra.findShortestPath(graph, start, end, criteria);

        // Check if a valid route was found
        if (currentRoute.flights == null) {
            System.out.println("\nNo route found between these cities!");
            return;
        }

        // Display the optimal route details
        System.out.println("\nBest Route (" + criteria + "):");
        for (Flight flight : currentRoute.flights) {
            System.out.println(
                    flight.getSource() + " -> " + flight.getDestination() +
                            " (Cost: $" + String.format("%.2f", flight.getCost()) +
                            ", Duration: " + flight.getDuration() + ")");
        }

        // Display the total cost or duration based on optimization criteria
        // Uses ternary operators
        System.out.println("Total " +
                (criteria.equals("cost") ? "Cost" : "Duration") + ": " +
                (criteria.equals("cost") ? "$" + currentRoute.total : currentRoute.total + " minutes"));
    }

    /**
     * Books seats on all flights in the currently selected route
     * Creates booking requests and assigns them to each flight
     */
    private static void bookSeats() {
        // Check if a route has been selected
        if (currentRoute == null || currentRoute.flights == null) {
            System.out.println("No route selected! Find a route first.");
            return;
        }

        // Get customer information
        System.out.print("\nEnter customer name: ");
        String customer = scanner.nextLine().trim();

        // Generate a unique booking reference
        String bookingRef = "BOOKING-" + System.currentTimeMillis();

        // Book seats on each flight in the route
        System.out.println("\nBooking Status (" + bookingRef + "):");
        for (Flight flight : currentRoute.flights) {
            flight.bookSeat(new BookingRequest(customer, System.currentTimeMillis()));
            System.out.println("✓ Booked " + flight.getSource() + " -> " + flight.getDestination());
        }

        System.out.println("\nAll flights booked successfully!");
    }
}
