import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Collection;
import java.util.Scanner;

public class AdminMenu {

    final static String MENU = """
            Admin Menu
            --------------------------------------
            1. See all Customers\s
            2. See all Rooms\s
            3. See all Reservations\s
            4. Add a Room\s
            5. Back to Main Menu
            --------------------------------------
            Please select an option by entering the number.""";

    final private static Scanner scanner = new Scanner(System.in);
    final private static AdminResource adminResource = AdminResource.getInstance();

    static void adminMenu() {
        try {
            System.out.println(MENU);
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1" -> {
                    seeAllCustomers();
                    adminMenu();
                }
                case "2" -> {
                    seeAllRooms();
                    adminMenu();
                }
                case "3" -> {
                    seeAllReservations();
                    adminMenu();
                }
                case "4" -> {
                    addARoom();
                    adminMenu();
                }
                case "5" -> MainMenu.mainMenu();
                default -> throw new IllegalArgumentException("Invalid input. Please try again.");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            adminMenu();
        }
    }

    public static void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers in the system.\n");
            return;
        }
        customers.forEach(System.out::println);
        System.out.println();
    }


    public static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms in the system.\n");
            return;
        }
        rooms.forEach(System.out::println);
        System.out.println();
    }


    public static void seeAllReservations() {
        adminResource.displayAllReservations();
    }

    public static void addARoom() {
        System.out.println("Enter a room number");
        String roomNumber = scanner.nextLine();

        System.out.println("Enter price per night");
        Double price = processPrice();

        System.out.println("Enter room type: 1 for single, 2 for double");
        RoomType type = processType();

        IRoom room = new Room(roomNumber, price, type);

        adminResource.addRoom(room);
        keepAddingRoom();
    }

    public static void keepAddingRoom() {
        try {
            System.out.println("Would you like to add another room? y/n");
            String keepAddingRoom = scanner.nextLine();
            if ("y".equals(keepAddingRoom)) {
                addARoom();
            } else if (!"n".equals(keepAddingRoom)) {
                throw new Exception("Invalid input.");
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            keepAddingRoom();
        }
    }

    public static Double processPrice() {
        try {
            String input = scanner.nextLine();
            return Double.parseDouble(input);
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again");
            return processPrice();
        }
    }

    public static RoomType processType() {
        try {
            String input = scanner.nextLine();
            return RoomType.valueOfLabel(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
            return processType();
        }
    }


}
