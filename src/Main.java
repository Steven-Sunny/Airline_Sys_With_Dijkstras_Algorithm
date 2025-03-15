import java.util.*;
public class Main {
    private static AirlineGraph graph = new AirlineGraph();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        while (true) {
            System.out.println("\nAirline Reservation System");
            System.out.println("1. Add Flight");
            System.out.println("2. Find Cheapest/Fastest Route");
            System.out.println("3. Book Seats on Current Route");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

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

        System.out.print("Available Seats: ");
        int seats = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        graph.addFlight(source, destination, cost, duration, seats);
        System.out.println("Flight added successfully!");
    }

    private static void findRoute() {

    }

    private static void bookSeats() {

    }
}
