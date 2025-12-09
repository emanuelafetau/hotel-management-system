
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservation {

    public enum ReservationStatus {
        CONFIRMED,
        CANCELLED
    }

    private static int reservationCounter = 1; 
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private int reservationId;
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ReservationStatus status;

    
    public Reservation(Guest guest, Room room, String checkInDate, String checkOutDate) {
        this.reservationId = reservationCounter++;  
        setGuest(guest);
        setRoom(room);
        setCheckInDate(checkInDate);
        setCheckOutDate(checkOutDate);
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
    
    public void setGuest(Guest guest) {
        if (guest == null) throw new IllegalArgumentException("Guest cannot be null.");
        this.guest = guest;
    }

    public Room getRoom() { 
    	return room; 
    }
    
    public void setRoom(Room room) {
        if (room == null) throw new IllegalArgumentException("Room cannot be null.");
        this.room = room;
    }

    public LocalDate getCheckInDate() { 
    	return checkInDate; 
    }
    
    public void setCheckInDate(String checkInDate) {
        if (checkInDate == null || checkInDate.isEmpty())
            throw new IllegalArgumentException("Check-in date cannot be empty.");
        LocalDate date = LocalDate.parse(checkInDate, DATE_FORMATTER);
        this.checkInDate = date;

        if (this.checkOutDate != null && !this.checkOutDate.isAfter(this.checkInDate))
            throw new IllegalArgumentException("Check-out date must be after check-in date.");
    }

    public LocalDate getCheckOutDate() { 
    	return checkOutDate; 
    }
    
    public void setCheckOutDate(String checkOutDate) {
        if (checkOutDate == null || checkOutDate.isEmpty())
            throw new IllegalArgumentException("Check-out date cannot be empty.");
        LocalDate date = LocalDate.parse(checkOutDate, DATE_FORMATTER);

        if (this.checkInDate != null && !date.isAfter(this.checkInDate))
            throw new IllegalArgumentException("Check-out date must be after check-in date.");

        this.checkOutDate = date;
    }

    public ReservationStatus getStatus() { 
    	return status; 
    }

   
    @Override
    public String toString() {
        return String.format(
            "Reservation %d: Room %d, Guest %d, %s to %s, Status: %s",
            reservationId, room.getRoomNumber(), guest.getGuestId(),
            checkInDate, checkOutDate, status
        );
    }
}
