
import java.util.ArrayList;

public class Room {
    
    private static ArrayList<Room> allRooms = new ArrayList<>();
    private static int roomCounter = 100;
    private int roomNumber;
    private double price;
    private boolean isAvailable;
    
    public enum RoomType {
        SINGLE, DOUBLE, SUITE
    }
    
    private RoomType roomType;
    
    public Room(int roomNumber, double price, RoomType roomType) {
        this.roomNumber = roomCounter++;
        this.price = price;
        this.isAvailable = true; 
        this.roomType = roomType;
        allRooms.add(this);
        
    }
    
    
    public static void updateAvailability(Hotel hotel) {
        for (Room room : allRooms) {
            boolean available = true;

            for (Reservation res : hotel.getAllReservations()) {
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
    
    public static void displayAllRooms(Hotel hotel) {
        updateAvailability(hotel); 
        System.out.println("==== 🏨 ROOMS 🏨 ====\n");
        for(Room r : allRooms) {
            System.out.println(r); 
            System.out.println(r.displayForAllRooms());
            System.out.println("=======================\n");
        }
    }
    
    @Override
    public String toString() {
        return " Room number # " + roomNumber + "\n Room type: "+ roomType + "\n Price per night --> $" + price;
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

    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
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
