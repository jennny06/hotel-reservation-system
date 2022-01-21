package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

public class AdminResource {
    private AdminResource() {
    }

    private static AdminResource adminResource = null;

    public static AdminResource getInstance() {
        if (null == adminResource) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();


    public void addRoom(IRoom room) {
        reservationService.addRooms(room);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}
