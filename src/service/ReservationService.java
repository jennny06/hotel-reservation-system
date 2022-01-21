package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    private static ReservationService reservationService = null;

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }


    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<Customer, Collection<Reservation>> customerReservations = new HashMap<>();
    private final Collection<Reservation> reservationList = new HashSet<>();

    public void addRooms(IRoom room) {
        String roomId = room.getRoomNumber();

        if (rooms.containsKey(roomId)) {
            System.out.println("Room is in the system. Do you want to continue update? y/n");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if ("n".equals(input)) {
                return;
            }
        }
        rooms.put(roomId, room);
        System.out.println(roomId + " added.\n");
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Reservation reservationARoom(Customer customer, IRoom room, Date checkin, Date checkout) {
        Reservation reservation = new Reservation(customer, room, checkin, checkout);
        System.out.println("Room " + room.getRoomNumber() + " has been reserved.\n");

        Collection<Reservation> thisCustomerRes = customerReservations.get(customer);
        if (thisCustomerRes == null) {
            thisCustomerRes = new ArrayList<>();
        }
        thisCustomerRes.add(reservation);
        customerReservations.put(customer, thisCustomerRes);
        reservationList.add(reservation);

        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkin, Date checkout) {
        if (reservationList.isEmpty()) {
            return rooms.values();
        } else {
            Collection<IRoom> unavailableRooms = findUnavailableRooms(checkin, checkout);
            Collection<IRoom> availableRooms = rooms.values();
            if (unavailableRooms.size() == availableRooms.size()) {
                return null;
            }

            availableRooms.removeAll(unavailableRooms);
            return availableRooms;
        }
    }

    public Collection<IRoom> findUnavailableRooms(Date checkin, Date checkout) {
        Collection<IRoom> unavailableRooms = new HashSet<>();
        System.out.println("Finding rooms from " + checkin + " to " + checkout + "\n");

        for (Reservation reservation : reservationList) {
            if (timeOverlaps(checkin, checkout, reservation)) {
                unavailableRooms.add(reservation.getRoom());
            }
        }

        return unavailableRooms;
    }

    public Date setNewDay(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        int shiftedDays = 7;
        calendar.add(Calendar.DATE, shiftedDays);

        return calendar.getTime();
    }


    public Boolean timeOverlaps(Date checkin, Date checkout, Reservation reservation) {
        return (checkin.after(reservation.getCheckInDate()) && checkin.before(reservation.getCheckOutDate())) ||
                (checkout.after(reservation.getCheckInDate()) && checkout.before(reservation.getCheckOutDate())) ||
                (checkin.equals(reservation.getCheckInDate())) || (checkout.equals(reservation.getCheckOutDate())) ||
                (checkin.before(reservation.getCheckInDate()) && checkout.after(reservation.getCheckOutDate()));
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        if (!customerReservations.containsKey(customer)) {
            System.out.println("No reservations found.\n");
            return null;
        }
        return customerReservations.get(customer);
    }

    public void printAllReservation() {
        if (reservationList.isEmpty()) {
            System.out.println("Reservation list is empty.\n");
            return;
        }
        reservationList.forEach(System.out::println);
        System.out.println();
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }
}
