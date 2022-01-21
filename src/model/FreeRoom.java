package model;

public class FreeRoom extends Room {

    public FreeRoom(String number, RoomType type) {
        super(number, 0.0, type);
    }

    @Override
    public String toString() {
        return "This is a free room";
    }
}
