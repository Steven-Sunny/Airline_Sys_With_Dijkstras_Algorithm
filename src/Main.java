import java.util.*;
public class Main {
    private final static AirlineGraph graph = new AirlineGraph();
    private final static Scanner scanner = new Scanner(System.in);
    private static Dijkstra.PathResult currentRoute;
    public static void main(String[] args) {

        while (true) {
            System.out.println("\nAirline Reservation System");
            System.out.println("1. Add Flight");
            System.out.println("2. Find Cheapest/Fastest Route");
            System.out.println("3. Book Seats on Current Route");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // take in newline

            switch (choice) {
                case 1:
                    addFlight();
                    break;
                case 2:
                    findRoute();
                    break;
                case 3:
                    bookSeats();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addFlight() {
        System.out.println("\nAdd New Flight:");
        System.out.print("Source City: ");
        String source = scanner.nextLine().trim();

        System.out.print("Destination City: ");
        String destination = scanner.nextLine().trim();

        System.out.print("Cost: ");
        double cost = scanner.nextDouble();

        System.out.print("Duration (minutes): ");
        int duration = scanner.nextInt();

        //prevents users from adding an invalid cost or duration
        while (cost <=0 || duration <=0){
            System.out.println("Invalid cost or duration!");
            System.out.print("Cost: ");
            cost = scanner.nextDouble();
            System.out.print("Duration (minutes): ");
            duration = scanner.nextInt();
        }

        scanner.nextLine();  // Consume newline

        graph.addFlight(source, destination, cost, duration);
        System.out.println("Flight added successfully!");
    }

    private static void findRoute() {
        System.out.println("\nFind Route:");
        System.out.print("Departure City: ");
        String start = scanner.nextLine().trim();

        System.out.print("Destination City: ");
        String end = scanner.nextLine().trim();

        System.out.print("Optimize for (cost/duration): ");
        String criteria = scanner.nextLine().trim().toLowerCase();

        while (!criteria.equals("cost") && !criteria.equals("duration")) {
            System.out.println("Invalid criteria! Enter 'cost' or 'duration':");
            criteria = scanner.nextLine().trim().toLowerCase();
        }

        currentRoute = Dijkstra.findShortestPath(graph, start, end, criteria);

        if (currentRoute.flights == null) {
            System.out.println("\nNo route found between these cities!");
            return;
        }

        System.out.println("\nBest Route (" + criteria + "):");
        for (Flight flight : currentRoute.flights) {
            System.out.println(
                    flight.getSource() + " -> " + flight.getDestination() +
                            " (Cost: $" + String.format("%.2f", flight.getCost()) +
                            ", Duration: " + flight.getDuration() + ")");

        }
        System.out.println("Total " +
                (criteria.equals("cost") ? "Cost" : "Duration") + ": " +
                (criteria.equals("cost") ? "$" + currentRoute.total : currentRoute.total + " minutes"));

    }

    private static void bookSeats() {
        if (currentRoute == null || currentRoute.flights == null) {
            System.out.println("No route selected! Find a route first.");
            return;
        }

        System.out.print("\nEnter customer name: ");
        String customer = scanner.nextLine().trim();

        scanner.nextLine();

        String bookingRef = "BOOKING-" + System.currentTimeMillis();

        System.out.println("\nBooking Status (" + bookingRef + "):");
        for (Flight flight : currentRoute.flights) {
            flight.bookSeat(new BookingRequest(customer, System.currentTimeMillis()));
            System.out.println("✓ Booked " + flight.getSource() + " -> " + flight.getDestination());
        }

        System.out.println("\nAll flights booked successfully!");
    }
}
