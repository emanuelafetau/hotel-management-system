import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestReservations {

    public static List<Reservation> createTestReservations() {
        List<Reservation> reservations = new ArrayList<>();
        LocalDate today = LocalDate.now();

        reservations.add(new Reservation(
                new Guest("John Doe", "1234567890"),
                new Room(101, 100, Room.RoomType.SINGLE),
                today,
                today.plusDays(3)));

        reservations.add(new Reservation(
                new Guest("Jane Smith", "9876543210"),
                new Room(102, 150, Room.RoomType.DOUBLE),
                today.plusDays(1),
                today.plusDays(5)));

        reservations.add(new Reservation(
                new Guest("Alice Johnson", "5551234567"),
                new Room(103, 120, Room.RoomType.SINGLE),
                today.plusDays(2),
                today.plusDays(6)));

        reservations.add(new Reservation(
                new Guest("Bob Brown", "5559876543"),
                new Room(104, 200, Room.RoomType.SUITE),
                today,
                today.plusDays(4)));

        reservations.add(new Reservation(
                new Guest("Charlie Lee", "5551112222"),
                new Room(105, 130, Room.RoomType.DOUBLE),
                today.plusDays(3),
                today.plusDays(7)));

        reservations.add(new Reservation(
                new Guest("Diana King", "5553334444"),
                new Room(106, 90, Room.RoomType.SINGLE),
                today.plusDays(4),
                today.plusDays(8)));

        reservations.add(new Reservation(
                new Guest("Ethan Hunt", "5555556666"),
                new Room(107, 180, Room.RoomType.SUITE),
                today.plusDays(2),
                today.plusDays(6)));

        reservations.add(new Reservation(
                new Guest("Fiona Adams", "5557778888"),
                new Room(108, 140, Room.RoomType.DOUBLE),
                today.plusDays(5),
                today.plusDays(9)));

        reservations.add(new Reservation(
                new Guest("George Clark", "5559990000"),
                new Room(109, 110, Room.RoomType.SINGLE),
                today.plusDays(1),
                today.plusDays(4)));

        reservations.add(new Reservation(
                new Guest("Hannah White", "5552223333"),
                new Room(110, 160, Room.RoomType.DOUBLE),
                today.plusDays(3),
                today.plusDays(7)));

        return reservations;
    }

    public static void main(String[] args) {
    
    	List<Reservation> reservations = createTestReservations();
        System.out.println("Created " + reservations.size() + " test reservations.");

        for (Reservation r : reservations) {
            System.out.println("Reservation ID: " + r.getReservationId()
                    + ", Guest: " + r.getGuest().getName()
                    + ", Room: " + r.getRoom().getRoomNumber()
                    + ", Check-in: " + r.getCheckInDate()
                    + ", Check-out: " + r.getCheckOutDate()
                    + ", Invoice Amount: $" + r.getInvoice().getAmount());
        }

        
        List<Payment> payments = PaymentFileIO.loadPayments("payments.txt", reservations);

        
        System.out.println("Payments loaded: " + payments.size());
        payments.forEach(p -> System.out.println(p.generateReceipt()));
    }
}

