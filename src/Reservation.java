import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservation {
    public enum ReservationStatus {
        CONFIRMED,
        CANCELLED
    }

    private static int reservationCounter = 1; 
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    private final int reservationId;          
    private final Guest guest;                
    private final Room room;                  
    private final LocalDate checkInDate;      
    private final LocalDate checkOutDate;     
    private ReservationStatus status;         
    
    public Reservation(Guest guest, Room room, String checkInDate, String checkOutDate) {
        
        if (guest == null) {
            throw new IllegalArgumentException("Guest cannot be null.");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (checkInDate == null || checkInDate.isEmpty()) {
            throw new IllegalArgumentException("Check-in date cannot be empty.");
        }
        if (checkOutDate == null || checkOutDate.isEmpty()) {
            throw new IllegalArgumentException("Check-out date cannot be empty.");
        }
        
        
        LocalDate inDate = LocalDate.parse(checkInDate, DATE_FORMATTER);
        LocalDate outDate = LocalDate.parse(checkOutDate, DATE_FORMATTER);
        LocalDate today = LocalDate.now();
        
        
        if (inDate.isBefore(today)) {
            throw new IllegalArgumentException("Check-in date cannot be in the past.");
        }
        if (!outDate.isAfter(inDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date.");
        }
        
        
        this.reservationId = reservationCounter++;
        this.guest = guest;
        this.room = room;
        this.checkInDate = inDate;
        this.checkOutDate = outDate;
        this.status = ReservationStatus.CONFIRMED;
    }
    
    
    public boolean cancelReservation() {
        if (status == ReservationStatus.CONFIRMED) {
            this.status = ReservationStatus.CANCELLED;
            return true;
        }
        return false;
    }
    
    public int getReservationId() { 
        return reservationId; 
    }
    
    public Guest getGuest() { 
        return guest; 
    }
    
    public Room getRoom() { 
        return room; 
    }
    
    public LocalDate getCheckInDate() { 
        return checkInDate; 
    }
    
    public LocalDate getCheckOutDate() { 
        return checkOutDate; 
    }
    
    public ReservationStatus getStatus() { 
        return status; 
    }
    
    @Override
    public String toString() {
        return String.format(
            "Reservation %d: Room %d, Guest %d, %s to %s, Status: %s",
            reservationId, room.getRoomNumber(), guest.getGuestId(),
            checkInDate.format(DATE_FORMATTER), checkOutDate.format(DATE_FORMATTER), status
        );
    }
}