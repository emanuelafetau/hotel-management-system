import java.io.*;
import java.util.*;

public class FileIO {
   
    /**
     * Save the entire hotel to a binary file
     * This saves ALL data: guests, rooms, reservations, invoices, and employees
     */
    public static boolean saveHotel(Hotel hotel, String filename) {
        if (hotel == null) {
            System.out.println("Error: Hotel cannot be null.");
            return false;
        }
        
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            out.writeObject(hotel);
            System.out.println("Hotel saved successfully to: " + filename);
            System.out.println(hotel);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving hotel: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load a hotel from a binary file
     * This restores ALL data: guests, rooms, reservations, invoices, and employees
     */
    public static Hotel loadHotel(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(filename))) {
            Hotel hotel = (Hotel) in.readObject();
            System.out.println("Hotel loaded successfully from: " + filename);
            System.out.println(hotel);
            return hotel;
        } catch (FileNotFoundException e) {
            System.out.println("Error: Hotel file not found - " + filename);
            return null;
        } catch (IOException e) {
            System.out.println("Error loading hotel: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Hotel class not found - incompatible file format");
            return null;
        }
    }
    
    /**
     * Check if a hotel file exists
     */
    public static boolean hotelFileExists(String filename) {
        File file = new File(filename);
        return file.exists() && file.isFile();
    }
    
    /**
     * Create a backup of the hotel
     */
    public static boolean backupHotel(Hotel hotel, String backupFilename) {
        System.out.println("Creating backup...");
        return saveHotel(hotel, backupFilename);
    }
    
    /**
     * Display complete hotel information
     */
    public static void displayHotelDetails(Hotel hotel) {
        if (hotel == null) {
            System.out.println("No hotel to display.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("      HOTEL DETAILS");
        System.out.println("========================================");
        System.out.println("Hotel Name: " + hotel.getHotelName());
        System.out.println("\n--- GUESTS ---");
        System.out.println("Total Guests: " + hotel.getAllGuests().size());
        for (Guest guest : hotel.getAllGuests()) {
            System.out.println("  " + guest);
        }
        
        System.out.println("\n--- ROOMS ---");
        System.out.println("Total Rooms: " + hotel.getAllRooms().size());
        for (Room room : hotel.getAllRooms()) {
            System.out.println("  " + room);
        }
        
        System.out.println("\n--- RESERVATIONS ---");
        System.out.println("Total Reservations: " + hotel.getAllReservations().size());
        for (Reservation res : hotel.getAllReservations()) {
            System.out.println("  " + res);
        }
        
        System.out.println("\n--- INVOICES ---");
        System.out.println("Total Invoices: " + hotel.getAllInvoices().size());
        for (Invoice inv : hotel.getAllInvoices()) {
            System.out.println("  " + inv);
        }
        
        System.out.println("\n--- EMPLOYEES ---");
        System.out.println("Total Employees: " + hotel.getAllEmployees().size());
        for (Employee emp : hotel.getAllEmployees()) {
            System.out.println("  " + emp);
        }
        System.out.println("========================================\n");
    }
    
    // ========================================
    // OPTIONAL: DISPLAY HELPER METHODS
    // ========================================
    
    /**
     * Display only rooms from a hotel
     */
    public static void displayRooms(Hotel hotel) {
        if (hotel == null) {
            System.out.println("No hotel provided.");
            return;
        }
        
        List<Room> rooms = hotel.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms to display.");
            return;
        }
        
        System.out.println("Displaying " + rooms.size() + " rooms:");
        System.out.println("---------------------------------------");
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("---------------------------------------");
    }
    
    /**
     * Display only guests from a hotel
     */
    public static void displayGuests(Hotel hotel) {
        if (hotel == null) {
            System.out.println("No hotel provided.");
            return;
        }
        
        List<Guest> guests = hotel.getAllGuests();
        if (guests.isEmpty()) {
            System.out.println("No guests to display.");
            return;
        }
        
        System.out.println("Displaying " + guests.size() + " guests:");
        System.out.println("---------------------------------------");
        for (Guest guest : guests) {
            System.out.println(guest.getDisplayInfo());
        }
        System.out.println("---------------------------------------");
    }
    
    /**
     * Display only reservations from a hotel
     */
    public static void displayReservations(Hotel hotel) {
        if (hotel == null) {
            System.out.println("No hotel provided.");
            return;
        }
        
        List<Reservation> reservations = hotel.getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println("No reservations to display.");
            return;
        }
        
        System.out.println("Displaying " + reservations.size() + " reservations:");
        System.out.println("---------------------------------------");
        for (Reservation res : reservations) {
            System.out.println(res);
        }
        System.out.println("---------------------------------------");
    }
}