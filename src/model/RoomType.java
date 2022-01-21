package model;

public enum RoomType {

    Single("1"),
    Double("2");

    private final String label;

    RoomType(String label) {
        this.label = label;
    }

    public static RoomType valueOfLabel(String label) {
        for (RoomType room : values()) {
            if (room.label.equals(label)) {
                return room;
            }
        }
        throw new IllegalArgumentException("Input not valid. Please try again.");
    }
}
