package model;

import java.util.Objects;

public class Room implements IRoom {

    private final String roomNumber;
    private final Double price;
    private final RoomType type;

    public Room(String number, Double price, RoomType type) {
        this.roomNumber = number;
        this.price = price;
        this.type = type;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return type;
    }

    @Override
    public Boolean isFree() {
        return this.price != null && this.price.equals(0.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    @Override
    public String toString() {
        return "Room number: " + roomNumber + "\tRoom Price: " + price + "\tRoom type: " + type;
    }

}
