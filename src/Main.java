import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hotel Management System ===\n");
        
        String hotelFile = "grand_plaza_hotel.dat";
        
        // Check if hotel file exists
        if (FileIO.hotelFileExists(hotelFile)) {
            System.out.println("Loading existing hotel...\n");
            testLoadAndModify(hotelFile);
        } else {
            System.out.println("Creating new hotel...\n");
            testCreateAndSave(hotelFile);
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Demonstrate night audit with loaded hotel
        testNightAudit(hotelFile);
    }
    
    /**
     * Create a new hotel with sample data and save it
     */
    private static void testCreateAndSave(String filename) {
        System.out.println("--- CREATING NEW HOTEL ---\n");
        
        // Create hotel
        Hotel hotel = new Hotel("Grand Plaza Hotel");
        
        // Add rooms
        hotel.addRoom(new Room(101, 100.0, Room.RoomType.SINGLE));
        hotel.addRoom(new Room(102, 150.0, Room.RoomType.DOUBLE));
        hotel.addRoom(new Room(201, 200.0, Room.RoomType.SUITE));
        hotel.addRoom(new Room(202, 120.0, Room.RoomType.SINGLE));
        
        // Add guests
        Guest guest1 = new Guest(1, "John Doe", "123-456-7890", "john@example.com");
        guest1.addPreference("Non-smoking");
        Guest guest2 = new Guest(2, "Jane Smith", "098-765-4321", "jane@example.com");
        hotel.addGuest(guest1);
        hotel.addGuest(guest2);
        
        // Add employees
        Employee manager = new Employee("Mike Johnson", "555-1234", 50000, "Manager");
        Admin admin = new Admin("Alice Admin", "555-9999", 75000, "alice@hotel.com", "admin123");
        hotel.addEmployee(manager);
        hotel.addEmployee(admin);
        
        // Create reservations
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate checkOut = tomorrow.plusDays(3);
        
        Reservation res1 = hotel.makeReservation(1, 101, tomorrow, checkOut);
        Reservation res2 = hotel.makeReservation(2, 102, tomorrow, checkOut);
        
        // Create invoices and process payments
        if (res1 != null) {
            Invoice inv1 = hotel.createInvoiceForReservation(res1.getReservationId());
            PayByCard payment = new PayByCard(inv1, inv1.getAmount(), 
                "1234567812345678", "John Doe", "12/26", "123");
            payment.processPayment();
        }
        
        if (res2 != null) {
            hotel.createInvoiceForReservation(res2.getReservationId());
        }
        
        // Display hotel details
        FileIO.displayHotelDetails(hotel);
        
        // Save hotel to binary file
        System.out.println("\n--- SAVING HOTEL ---");
        FileIO.saveHotel(hotel, filename);
        
        // Create a backup
        FileIO.backupHotel(hotel, "backup_" + filename);
    }
    
    /**
     * Load existing hotel, modify it, and save changes
     */
    private static void testLoadAndModify(String filename) {
        System.out.println("--- LOADING HOTEL ---\n");
        
        Hotel hotel = FileIO.loadHotel(filename);
        
        if (hotel != null) {
            System.out.println("\nHotel loaded successfully!");
            
            // Display current state
            FileIO.displayHotelDetails(hotel);
            
            // Make some modifications
            System.out.println("\n--- MAKING CHANGES ---");
            
            // Add a new guest
            Guest newGuest = new Guest(3, "Bob Wilson", "555-7777", "bob@example.com");
            if (hotel.addGuest(newGuest)) {
                System.out.println("Added new guest: " + newGuest);
            }
            
            // Add a new room
            Room newRoom = new Room(301, 250.0, Room.RoomType.SUITE);
            if (hotel.addRoom(newRoom)) {
                System.out.println("Added new room: " + newRoom);
            }
            
            // Save updated hotel
            System.out.println("\n--- SAVING CHANGES ---");
            FileIO.saveHotel(hotel, filename);
        }
    }
    
    /**
     * Load hotel and perform night audit
     */
    private static void testNightAudit(String filename) {
        System.out.println("--- NIGHT AUDIT ---\n");
        
        Hotel hotel = FileIO.loadHotel(filename);
        
        if (hotel != null) {
            Employee auditor = hotel.getAllEmployees().get(0);
            LocalDate auditDate = LocalDate.now().plusDays(1);
            
            NightAudit audit = new NightAudit(hotel, auditDate, auditor);
            audit.processEndOfDay();
            
            System.out.println("\n--- Audit Summary ---");
            System.out.println("Total Revenue: $" + String.format("%.2f", audit.getTotalRevenue()));
            System.out.println("Occupancy Rate: " + String.format("%.2f", audit.getOccupancyRate()) + "%");
            System.out.println("Unpaid Invoices: " + audit.getUnpaidInvoicesCount());
        }
    }
}