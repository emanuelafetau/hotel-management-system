import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hotel Management System Testing ===\n");
        
        testFileIO();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        testNightAudit();
    }
    
    private static void testFileIO() {
        System.out.println("--- FILE I/O TEST ---\n");
        
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(101, 99.99, Room.RoomType.SINGLE));
        rooms.add(new Room(102, 149.99, Room.RoomType.DOUBLE));
        rooms.add(new Room(201, 249.99, Room.RoomType.SUITE));
        
        System.out.println("Writing 3 rooms to file...");
        FileIO.writeRoomsToText(rooms, "rooms.txt");
        
        // Read rooms back
        System.out.println("\nReading rooms from file...");
        List<Room> loadedRooms = FileIO.readRoomsFromText("rooms.txt");
        FileIO.displayRooms(loadedRooms);
        
        // Append more rooms
        List<Room> moreRooms = new ArrayList<>();
        moreRooms.add(new Room(202, 119.99, Room.RoomType.SINGLE));
        
        System.out.println("Appending 1 more room...");
        FileIO.appendRoomsToText(moreRooms, "rooms.txt");
        
        List<Room> allRooms = FileIO.readRoomsFromText("rooms.txt");
        System.out.println("\nTotal rooms in file: " + allRooms.size());
    }
    
    private static void testNightAudit() {
        System.out.println("--- NIGHT AUDIT TEST ---\n");
        
        Hotel hotel = new Hotel("Grand Plaza Hotel");
        
        hotel.addRoom(new Room(101, 100.0, Room.RoomType.SINGLE));
        hotel.addRoom(new Room(102, 150.0, Room.RoomType.DOUBLE));
        hotel.addRoom(new Room(201, 200.0, Room.RoomType.SUITE));
        hotel.addRoom(new Room(202, 120.0, Room.RoomType.SINGLE));
        
        Guest guest1 = new Guest("John Doe", "123-456-7890");
        Guest guest2 = new Guest("Jane Smith", "098-765-4321");
        hotel.addGuest(guest1);
        hotel.addGuest(guest2);
        
        Employee auditor = new Employee("Mike Johnson", "555-1234", 50000, "Manager");
        hotel.addEmployee(auditor);
        
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate checkOut = tomorrow.plusDays(3);
        
        Reservation res1 = hotel.makeReservation(guest1.getGuestId(), 101, tomorrow, checkOut);
        Reservation res2 = hotel.makeReservation(guest2.getGuestId(), 102, tomorrow, checkOut);
        
        
        if (res1 != null) {
            Invoice inv1 = hotel.createInvoiceForReservation(res1.getReservationId());
            
            PayByCard payment = new PayByCard(inv1, inv1.getAmount(), 
                "1234567812345678", "John Doe", "12/26", "123");
            payment.processPayment();
        }
        
        if (res2 != null) {
            hotel.createInvoiceForReservation(res2.getReservationId());
        }
        
        System.out.println("Performing night audit for tomorrow...\n");
        NightAudit audit = new NightAudit(hotel, tomorrow, auditor);
        audit.processEndOfDay();
        
        System.out.println("\n--- Audit Summary ---");
        System.out.println("Total Revenue: $" + String.format("%.2f", audit.getTotalRevenue()));
        System.out.println("Occupancy Rate: " + String.format("%.2f", audit.getOccupancyRate()) + "%");
        System.out.println("Unpaid Invoices: " + audit.getUnpaidInvoicesCount());
    }
}