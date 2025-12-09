import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Hotel {
    private ArrayList<Reservation> reservations;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Hotel() {
        reservations = new ArrayList<>();
    }

    public Reservation makeReservation(Guest guest, Room room, String checkInDate, String checkOutDate) {
        if (!checkRoomAvailability(room, checkInDate, checkOutDate)) {
            System.out.println("❌ Room " + room.getRoomNumber() + " is not available for the selected dates.");
            return null;
        }

        try {
            Reservation reservation = new Reservation(guest, room, checkInDate, checkOutDate);
            reservations.add(reservation);
            System.out.println("✅ Reservation created successfully with ID " + reservation.getReservationId());
            return reservation;
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Error creating reservation: " + e.getMessage());
            return null;
        }
    }

    public boolean checkRoomAvailability(Room room, String checkInDate, String checkOutDate) {
        LocalDate checkIn = LocalDate.parse(checkInDate, DATE_FORMATTER);
        LocalDate checkOut = LocalDate.parse(checkOutDate, DATE_FORMATTER);

        if (!checkIn.isBefore(checkOut)) {
            System.out.println("⚠️ Invalid dates: check-out must be after check-in.");
            return false;
        }

        for (Reservation r : reservations) {
            if (r.getRoom().equals(room) && r.getStatus() == Reservation.ReservationStatus.CONFIRMED) {
                boolean overlaps = !(checkOut.isBefore(r.getCheckInDate()) || checkIn.isAfter(r.getCheckOutDate()));
                if (overlaps) {
                    return false;
                }
            }
        }
        return true;
    }

    
    public void viewAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation r : reservations) {
                System.out.println(r);
            }
        }
    }

    public boolean cancelReservation(int reservationId) {
        for (Reservation r : reservations) {
            if (r.getReservationId() == reservationId) {
                boolean result = r.cancelReservation();
                if (result) {
                    System.out.println("Reservation " + reservationId + " cancelled successfully.");
                } else {
                    System.out.println("Reservation " + reservationId + " was already cancelled.");
                }
                return result;
            }
        }
        System.out.println("Reservation ID not found.");
        return false;
    }

  
    public ArrayList<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
}

