
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("🏨 HOTEL MANAGEMENT SYSTEM TEST 🏨\n");
        
        
        System.out.println("=== CREATING ROOMS ===");
        Room room101 = new Room(101, 100.0, Room.RoomType.SINGLE);
        Room room102 = new Room(102, 150.0, Room.RoomType.DOUBLE);
        Room room103 = new Room(103, 300.0, Room.RoomType.SUITE);
        
        
        Room.displayAllRooms();
        
        
        System.out.println("\n=== REGISTERING GUESTS ===");
        Guest guest1 = Guest.registerGuest("John Smith", "555-0101");
        Guest guest2 = Guest.registerGuest("Maria Garcia", "555-0102");
        Guest guest3 = Guest.registerGuest("David Johnson", "555-0103");
        
       
        System.out.println("\n=== ALL GUESTS ===");
        for (Guest guest : Guest.viewAllGuests()) {
            System.out.println(guest);
        }
        
       
        System.out.println("\n=== MAKING RESERVATIONS ===");
        Reservation res1 = Reservation.makeReservation(guest1, room101, "2024-02-01", "2024-02-05");
        Reservation res2 = Reservation.makeReservation(guest2, room102, "2024-02-01", "2024-02-03");
        
     
        System.out.println("\n=== TESTING UNAVAILABLE ROOM ===");
        Reservation res3 = Reservation.makeReservation(guest3, room101, "2024-02-03", "2024-02-04");
        
        Reservation res4 = Reservation.makeReservation(guest3, room103, "2024-02-01", "2024-02-07");
        
        System.out.println("\n=== ALL RESERVATIONS ===");
        for (Reservation res : Reservation.viewAllReservations()) {
            System.out.println(res);
        }
        
        System.out.println("\n=== ROOM AVAILABILITY AFTER RESERVATIONS ===");
        Room.displayAllRooms();
        
        System.out.println("\n=== CANCELLING RESERVATION ===");
        if (res1 != null) {
            res1.cancelReservation();
        }
        
        System.out.println("\n=== RESERVATIONS AFTER CANCELLATION ===");
        for (Reservation res : Reservation.viewAllReservations()) {
            System.out.println(res);
        }
        
        System.out.println("\n=== ROOM AVAILABILITY AFTER CANCELLATION ===");
        Room.displayAllRooms();
        
        
        System.out.println("\n=== TESTING GUEST OPERATIONS ===");
        guest1.addPreference("Non-smoking room");
        guest1.addPreference("High floor");
        guest1.checkIn();
        guest1.viewDetails();
        
        System.out.println("\n=== FINDING GUEST BY ID ===");
        Guest foundGuest = Guest.findGuestById(2);
        if (foundGuest != null) {
            System.out.println("Found guest: " + foundGuest);
        }
        
        System.out.println("\n=== FINDING ROOM BY NUMBER ===");
        Room foundRoom = Room.findRoomByNumber(103);
        if (foundRoom != null) {
            System.out.println("Found room: " + foundRoom);
        }
        
        System.out.println("\n=== TESTING CANCELLED ROOM AVAILABILITY ===");
        Reservation res5 = Reservation.makeReservation(guest1, room101, "2024-02-06", "2024-02-08");
        
        System.out.println("\n=== TESTING OVERLAPPING DATES ===");
        Reservation res6 = Reservation.makeReservation(guest2, room102, "2024-02-02", "2024-02-04");
        
        System.out.println("\n=== FINAL STATE ===");
        System.out.println("\n--- ALL RESERVATIONS ---");
        for (Reservation res : Reservation.viewAllReservations()) {
            System.out.println(res);
        }
        
        System.out.println("\n--- ALL GUESTS ---");
        for (Guest guest : Guest.viewAllGuests()) {
            System.out.println(guest);
        }
        
        System.out.println("--- Testing findGuestById with existing ID ---");
        Guest existingGuest = Guest.findGuestById(1);
        if (existingGuest != null) {
            System.out.println("Successfully found guest with ID 1: " + existingGuest.getName());
        } else {
            System.out.println("Guest with ID 1 not found!");
        }
        
        System.out.println("\n--- ALL ROOMS ---");
        Room.displayAllRooms();
        
        System.out.println("\n=== TESTING OBJECT REFERENCES ===");
        if (res2 != null) {
            System.out.println("Reservation 2 Room: " + res2.getRoom());
            System.out.println("Reservation 2 Guest: " + res2.getGuest());
            System.out.println("Are these the same objects? " + 
                (res2.getRoom() == room102) + " (room) " + 
                (res2.getGuest() == guest2) + " (guest)");
        }
        
       
    }
}