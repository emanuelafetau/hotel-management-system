import javafx.application.Application;
import javafx.stage.Stage;
import java.time.LocalDate;

public class HotelApp extends Application {
    
    private Hotel hotel;
    
    @Override
    public void start(Stage primaryStage) {
        // Load or create hotel
        hotel = loadHotel();
        
        // Create navigator
        SceneNavigator navigator = new SceneNavigator(primaryStage, hotel);
        
        // Show login screen
        navigator.showLogin();
    }
    
    private Hotel loadHotel() {
        String hotelFile = "grand_plaza_hotel.dat";
        
        // Try to load existing hotel
        if (FileIO.hotelFileExists(hotelFile)) {
            Hotel loadedHotel = FileIO.loadHotel(hotelFile);
            if (loadedHotel != null) {
                System.out.println("Hotel loaded from file.");
                return loadedHotel;
            }
        }
        
        // Create new hotel if file doesn't exist
        System.out.println("Creating new hotel...");
        return createSampleHotel(hotelFile);
    }
    
    private Hotel createSampleHotel(String filename) {
        Hotel hotel = new Hotel("Grand Plaza Hotel");
        
        // Add rooms
        hotel.addRoom(new Room(101, 100.0, Room.RoomType.SINGLE));
        hotel.addRoom(new Room(102, 150.0, Room.RoomType.DOUBLE));
        hotel.addRoom(new Room(201, 200.0, Room.RoomType.SUITE));
        hotel.addRoom(new Room(202, 120.0, Room.RoomType.SINGLE));
        
        // Add guests
        Guest guest1 = new Guest(1, "John Doe", "123-456-7890", "john@example.com");
        Guest guest2 = new Guest(2, "Jane Smith", "098-765-4321", "jane@example.com");
        hotel.addGuest(guest1);
        hotel.addGuest(guest2);
        
        // Add employees
        Employee manager = new Employee("Mike Johnson", "555-1234", 50000, "Manager");
        
        // Add admin with email and password
        Admin admin = new Admin("Alice Cooper", "555-9999", 75000, 
                               "admin@hotel.com", "admin123");
        
        hotel.addEmployee(manager);
        hotel.addEmployee(admin);
        
        // Create reservations
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate checkOut = tomorrow.plusDays(3);
        
        Reservation res1 = hotel.makeReservation(1, 101, tomorrow, checkOut);
        Reservation res2 = hotel.makeReservation(2, 102, tomorrow, checkOut);
        
        // Create invoices
        if (res1 != null) {
            Invoice inv1 = hotel.createInvoiceForReservation(res1.getReservationId());
            PayByCard payment = new PayByCard(inv1, inv1.getAmount(), 
                "1234567812345678", "John Doe", "12/26", "123");
            payment.processPayment();
        }
        
        if (res2 != null) {
            hotel.createInvoiceForReservation(res2.getReservationId());
        }
        
        // Save hotel
        FileIO.saveHotel(hotel, filename);
        
        System.out.println("Sample hotel created and saved.");
        System.out.println("Login with: admin@hotel.com / admin123");
        
        return hotel;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
