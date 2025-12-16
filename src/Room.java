import java.io.Serializable;
import java.time.LocalDate;

public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int roomNumber;
    private double price;
    private boolean isAvailable;
    
    public enum RoomType {
        SINGLE, DOUBLE, SUITE
    }
    
    private RoomType roomType;
    
    // Constructor with roomNumber parameter provided by user
    public Room(int roomNumber, double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.isAvailable = true; 
        this.roomType = roomType;
    }
    
    // Check if room is available for specific date range
    public boolean isAvailableForDates(LocalDate checkIn, LocalDate checkOut, Hotel hotel) {
        // Implementation for date checking
        return isAvailable;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }

    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public RoomType getType() {
        return roomType;
    }
    
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    
    @Override
    public String toString() {
        return String.format("Room #%d - Type: %s, Price: $%.2f/night, Available: %s",
            roomNumber, roomType, price, (isAvailable ? "Yes" : "No"));
    }
}