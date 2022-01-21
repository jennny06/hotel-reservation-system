package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    // static reference
    private static HotelResource hotelResource = null;

    private HotelResource() {
    }

    public static HotelResource getInstance() {
        if (hotelResource == null) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createCustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(firstName, lastName, email);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.reservationARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservation(String customerEmail) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.getCustomersReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkin, Date checkout) {
        return reservationService.findRooms(checkin, checkout);
    }

    public Date getNewDay(Date day) {
        return reservationService.setNewDay(day);
    }
}
