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

    public Reservation(Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        validateInputs(guest, room, checkInDate, checkOutDate);
        
        this.reservationId = reservationCounter++;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = ReservationStatus.CONFIRMED;
    }

    private void validateInputs(Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        if (guest == null || room == null || checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Guest, room, and dates cannot be null.");
        }

        LocalDate today = LocalDate.now();
        if (checkInDate.isBefore(today)) {
            throw new IllegalArgumentException("Check-in date cannot be in the past.");
        }
        if (!checkOutDate.isAfter(checkInDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date.");
        }
        if (room.getPrice() <= 0) {
            throw new IllegalArgumentException("Room price must be positive.");
        }
    }

    public boolean cancelReservation() {
        if (status == ReservationStatus.CONFIRMED) {
            status = ReservationStatus.CANCELLED;
            return true;
        }
        return false;
    }

    public boolean isActive() {
        return status == ReservationStatus.CONFIRMED;
    }

    public boolean isActiveOn(LocalDate date) {
        return status == ReservationStatus.CONFIRMED &&
               !date.isBefore(checkInDate) &&
               date.isBefore(checkOutDate);
    }

    public boolean overlaps(LocalDate start, LocalDate end) {
        return status == ReservationStatus.CONFIRMED &&
               start.isBefore(checkOutDate) &&
               end.isAfter(checkInDate);
    }

    public int getReservationId() { return reservationId; }
    public Guest getGuest() { return guest; }
    public Room getRoom() { return room; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public ReservationStatus getStatus() { return status; }

    @Override
    public String toString() {
        return String.format(
            "Reservation %d: Room %d, Guest %d, %s to %s, Status: %s",
            reservationId, room.getRoomNumber(), guest.getGuestId(),
            checkInDate.format(DATE_FORMATTER), 
            checkOutDate.format(DATE_FORMATTER), 
            status
        );
    }
}