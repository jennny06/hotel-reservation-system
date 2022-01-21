import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);
    final static String MENU = """
            Main Menu
            -------------------------------------------------
            1. Find and reserve a room\s
            2. See my reservation\s
            3. Create an account\s
            4. Admin\s
            5. Exit\s
            -------------------------------------------------
            Please select an option by entering the number.
            """;

    final private static HotelResource hotelResource = HotelResource.getInstance();
    final private static AdminResource adminResource = AdminResource.getInstance();

    public static void main(String[] args) {
        mainMenu();
    }

    static void mainMenu() {
        try {
            System.out.println(MENU);
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1" -> {
                    findAndReserveARoom();
                    mainMenu();
                }
                case "2" -> {
                    seeMyReservation();
                    mainMenu();
                }
                case "3" -> {
                    createAnAccount();
                    mainMenu();
                }
                case "4" -> AdminMenu.adminMenu();
                case "5" -> System.out.println("See you next time.");
                default -> throw new IllegalArgumentException("Invalid input, please try again.");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            mainMenu();
        }
    }

    public static void findAndReserveARoom() {
        if (adminResource.getAllRooms().isEmpty()) {
            System.out.println("There is no room in the system. Please contact admin to add a room first.\n");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date checkin = null;
        Date checkout = null;

        // get check in date
        while (checkin == null) {
            try {
                System.out.println("Please enter checkin Date (01/01/2021)");

                String input = scanner.nextLine();
                checkin = dateFormat.parse(input);
            } catch (ParseException ex) {
                System.out.println("Invalid date entered! Please try again.");
            }
        }
        // get check out date
        while (checkout == null) {
            try {
                System.out.println("Please enter checkout Date (01/01/2021)");
                String input = scanner.nextLine();
                checkout = dateFormat.parse(input);
                if (checkout.before(checkin)) {
                    throw new IllegalArgumentException();
                }
            } catch (ParseException | IllegalArgumentException ex) {
                System.out.println("Invalid date entered. Please try again.");
            }
        }

        if (checkin.after(checkout)) {
            System.out.println("Invalid data range. Please try again.");
            findAndReserveARoom();
            return;
        }


        Collection<IRoom> rooms = hotelResource.findARoom(checkin, checkout);
        if (rooms == null) {
            System.out.println("No rooms available between the selected date. " +
                    "Do you want to explore rooms a week later? y/n");
            String future = null;
            while (future == null) {
                future = scanner.nextLine();
                if ("y".equals(future)) {
                    checkin = hotelResource.getNewDay(checkin);
                    checkout = hotelResource.getNewDay(checkout);
                    rooms = hotelResource.findARoom(checkin, checkout);
                } else if ("n".equals(future)) {
                    return;
                } else {
                    future = null;
                }
            }
        }

        if (rooms == null) {
            System.out.println("No rooms available at this time.\n");
            return;
        }

        rooms.forEach(System.out::println);
        System.out.println();
        System.out.println("Would you like to book a room? y/n");
        String book = scanner.nextLine();
        if ("y".equals(book)) {
            selectARoom(rooms, checkin, checkout);
        }
    }

    public static void selectARoom(Collection<IRoom> rooms, Date checkin, Date checkout) {
        System.out.println("Do you have an account with us? y/n");
        String haveAccount = scanner.nextLine();
        if ("n".equals(haveAccount)) {
            createAnAccount();
            return;
        } else if (!"y".equals(haveAccount)) {
            selectARoom(rooms, checkin, checkout);
            return;
        }

        String email = null;
        while (email == null) {
            try {
                System.out.println("Enter Email format: name@domain.com");
                email = scanner.nextLine();
                hotelResource.getCustomer(email);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
                return;
            }
        }

        IRoom room = null;
        String selection;
        while (room == null) {
            try {
                rooms.forEach(System.out::println);
                System.out.println();
                System.out.println("What room would you like to reserve? Please only enter a number");
                selection = scanner.nextLine();
                if (!isValidRoomSelection(rooms, selection)) {
                    throw new Exception("Room selection is not valid\n");
                }
                room = hotelResource.getRoom(selection);
            } catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }

        System.out.println("Confirmation:\n" + hotelResource.bookARoom(email, room, checkin, checkout) + "\n");
    }


    public static void createAnAccount() {

        System.out.println("Enter email, format: name@domain.com");
        String email = scanner.nextLine();

        System.out.println("First name:");
        String firstName = scanner.nextLine();

        System.out.println("Last name:");
        String lastName = scanner.nextLine();

        try {
            hotelResource.createCustomer(email, firstName, lastName);
            System.out.println("Customer created. Back to the main page...\n");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }


    public static void seeMyReservation() {
        System.out.println("Enter email format: name@domain.com");
        String email = scanner.nextLine();

        Collection<Reservation> reservations = hotelResource.getCustomersReservation(email);
        if (reservations == null) {
            return;
        }
        reservations.forEach(System.out::println);
        System.out.println();
    }


    public static Boolean isValidRoomSelection(Collection<IRoom> rooms, String selection) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(selection)) {
                return true;
            }
        }
        return false;
    }
}



