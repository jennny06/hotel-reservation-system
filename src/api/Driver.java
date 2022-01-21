package api;

import model.*;
import service.ReservationService;

import java.util.Date;

public class Driver {
    static ReservationService reservationService = ReservationService.getInstance();

    public static void main(String[] args) {
        Customer customer = new Customer("chang", "liu", "chl017@ucsd.com");
        IRoom room = new Room("1", 1.0, RoomType.Single);
        Reservation reservation = new Reservation(customer, room, new Date(2022, 1, 4), new Date(2022, 1, 10));
        System.out.println(reservationService.timeOverlaps(new Date(2022, 1, 2), new Date(2022, 1, 5), reservation));
    }
}
