
import java.util.ArrayList;

public class Room {
    
    private static ArrayList<Room> allRooms = new ArrayList<>();
    private static int roomCounter = 100;
    private int roomNumber;
    private double pricePerNight;
    private boolean isAvailable;
    
    public enum RoomType {
        SINGLE, DOUBLE, SUITE
    }
    
    private RoomType roomType;
    
    public Room(int roomNumber, double pricePerNight, RoomType roomType) {
        this.roomNumber = roomCounter++;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true; 
        this.roomType = roomType;
        allRooms.add(this);
        roomCounter++;
    }
    
    
    public static void updateAvailability() {
        for (Room room : allRooms) {
            boolean available = true;

            for (Reservation res : Reservation.viewAllReservations()) {
                if (res.getRoom() == room &&  
                    res.getStatus() == Reservation.ReservationStatus.CONFIRMED) {
                    available = false;
                    break;
                }
            }

            room.setAvailable(available);
        }
    }
    
    public boolean getIsAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    public String displayForAllRooms() {
        return " Available: " + (isAvailable ? "Yes" : "No");
    }
    
    public static void displayAllRooms() {
        updateAvailability(); 
        System.out.println("==== 🏨 ROOMS 🏨 ====\n");
        for(Room r : allRooms) {
            System.out.println(r); 
            System.out.println(r.displayForAllRooms());
            System.out.println("=======================\n");
        }
    }
    
    @Override
    public String toString() {
        return " Room number # " + roomNumber + "\n Room type: "+ roomType + "\n Price per night --> $" + pricePerNight;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }
    
    public static int getRoomCounter() {
        return roomCounter;
    }

    public void setRoomCounter(int roomCounter) {
        Room.roomCounter = roomCounter;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }
    
    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
    
    public RoomType getRoomType() {
        return roomType;
    }
    
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    
    public static ArrayList<Room> getAllRooms() {
        return new ArrayList<>(allRooms);
    }
    
    public static Room findRoomByNumber(int roomNumber) {
        for (Room room : allRooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }
}