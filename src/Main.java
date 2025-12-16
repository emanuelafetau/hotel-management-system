import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Invoice Testing ===\n");
        
        // Create a hotel
        Hotel hotel = new Hotel("Grand Plaza Hotel");
        
        // Create rooms and add them to a list for file I/O testing
        List<Room> rooms = new ArrayList<>();
        
        // Create a room (constructor: roomNumber, price, roomType)
        Room room = new Room(101, 100.0, Room.RoomType.SINGLE);
        hotel.addRoom(room);
        rooms.add(room);
        
        // Create additional rooms for file I/O testing
        Room room2 = new Room(102, 150.0, Room.RoomType.DOUBLE);
        Room room3 = new Room(201, 200.0, Room.RoomType.SUITE);
        Room room4 = new Room(202, 120.0, Room.RoomType.SINGLE);
        
        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);
        
        // Create a guest
        Guest guest = new Guest("John Doe", "john.doe@email.com", "123-456-7890");
        
        // Create a reservation
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(4); // 3 nights
        Reservation reservation = new Reservation(guest, room, checkIn, checkOut);
        
        // Create an invoice
        Invoice invoice = new Invoice(reservation);
        
        // Test 1: Display invoice details
        System.out.println("Test 1: Initial Invoice Creation");
        System.out.println("Invoice ID: " + invoice.getInvoiceId());
        System.out.println("Guest: " + invoice.getGuest().getName());
        System.out.println("Room: " + invoice.getRoom().getRoomNumber() + " (" + invoice.getRoom().getType() + ")");
        System.out.println("Check-in: " + reservation.getCheckInDate());
        System.out.println("Check-out: " + reservation.getCheckOutDate());
        System.out.println("Nights: " + invoice.getNights());
        System.out.println("Amount: $" + invoice.getAmount());
        System.out.println("Status: " + invoice.getStatus());
        System.out.println();
        
        // Test 2: Add a partial card payment
        System.out.println("Test 2: Adding Partial Card Payment");
        PayByCard payment1 = new PayByCard(
            invoice, 
            150.0,
            "1234567812345678",
            "John Doe",
            "12/26",
            "123"
        );
        // Process the payment
        if (payment1.processPayment()) {
            System.out.println("Payment successful!");
        }
        System.out.println("Total paid: $" + invoice.getTotalPaid());
        System.out.println("Status: " + invoice.getStatus());
        System.out.println();
        
        // Test 3: Add another payment to complete
        System.out.println("Test 3: Completing Payment");
        PayByCard payment2 = new PayByCard(
            invoice,
            150.0,
            "8765432187654321",
            "John Doe",
            "06/27",
            "456"
        );
        if (payment2.processPayment()) {
            System.out.println("Payment successful!");
        }
        System.out.println("Total paid: $" + invoice.getTotalPaid());
        System.out.println("Status: " + invoice.getStatus());
        System.out.println();
        
        // Test 4: Display all payments
        System.out.println("Test 4: Payment History");
        System.out.println("Total payments: " + invoice.getPayments().size());
        for (Payment payment : invoice.getPayments()) {
            System.out.println("- Payment ID: " + payment.getPaymentId() + 
                ", Amount: $" + payment.getAmount() + 
                ", Date: " + payment.getPaymentDate());
        }
        System.out.println();
        
        // Test 5: Create multiple invoices to test counter
        System.out.println("Test 5: Multiple Invoices");
        Guest guest2 = new Guest("Jane Smith", "jane.smith@email.com", "098-765-4321");
        Reservation reservation2 = new Reservation(guest2, room2, checkIn, checkOut.plusDays(1));
        Invoice invoice2 = new Invoice(reservation2);
        
        System.out.println("Invoice 1 ID: " + invoice.getInvoiceId());
        System.out.println("Invoice 2 ID: " + invoice2.getInvoiceId());
        System.out.println("Invoice 2 Amount: $" + invoice2.getAmount());
        System.out.println();
        
        // Test 6: Try expired card
        System.out.println("Test 6: Expired Card Test");
        Invoice invoice3 = new Invoice(reservation);
        PayByCard expiredCard = new PayByCard(
            invoice3,
            300.0,
            "1111222233334444",
            "Test User",
            "01/24", // Expired
            "999"
        );
        if (!expiredCard.processPayment()) {
            System.out.println("Payment correctly rejected!");
        }
        System.out.println("Status: " + invoice3.getStatus());
        System.out.println();
        
        // Test 7: Invalid card number
        System.out.println("Test 7: Invalid Card Test");
        Invoice invoice4 = new Invoice(reservation);
        PayByCard invalidCard = new PayByCard(
            invoice4,
            300.0,
            "123", // Too short
            "Test User",
            "12/26",
            "999"
        );
        if (!invalidCard.processPayment()) {
            System.out.println("Payment correctly rejected!");
        }
        System.out.println();
        
        // Test 8: Overpayment
        System.out.println("Test 8: Overpayment Test");
        Invoice invoice5 = new Invoice(reservation);
        PayByCard overpayment = new PayByCard(
            invoice5,
            500.0,
            "9999888877776666",
            "Rich Customer",
            "12/26",
            "777"
        );
        if (overpayment.processPayment()) {
            System.out.println("Overpayment accepted!");
        }
        System.out.println("Invoice amount: $" + invoice5.getAmount());
        System.out.println("Total paid: $" + invoice5.getTotalPaid());
        System.out.println("Status: " + invoice5.getStatus());
        System.out.println();
        
        // Test 9: Print full invoice
        System.out.println("Test 9: Print Invoice");
        invoice.printInvoice();
        
        System.out.println("\n=== File I/O Testing ===\n");
        
        // Display rooms
        FileIO.displayRooms(rooms);
        
        // ====== METHOD 1: Write to text file ======
        System.out.println("\nWriting rooms to text file...");
        FileIO.writeRoomsToText(rooms, "rooms.txt");
        
        // ====== METHOD 2: Read from text file ======
        System.out.println("\nReading rooms from text file...");
        List<Room> loadedFromText = FileIO.readRoomsFromText("rooms.txt");
        FileIO.displayRooms(loadedFromText);
        
        // ====== METHOD 3: Write to binary file ======
        System.out.println("\nWriting rooms to binary file...");
        FileIO.writeRoomsToBinary(rooms, "rooms.dat");
        
        // ====== METHOD 4: Read from binary file ======
        System.out.println("\nReading rooms from binary file...");
        List<Room> loadedFromBinary = FileIO.readRoomsFromBinary("rooms.dat");
        FileIO.displayRooms(loadedFromBinary);
        
        // Test filtering (optional utility method)
        System.out.println("\nFiltering single rooms...");
        List<Room> singleRooms = FileIO.filterByType(rooms, Room.RoomType.SINGLE);
        System.out.println("Single rooms: " + singleRooms.size());
        FileIO.displayRooms(singleRooms);
        
        // Test finding room (optional utility method)
        System.out.println("\nFinding room 202...");
        Room room202 = FileIO.findRoomByNumber(rooms, 202);
        if (room202 != null) {
            System.out.println("Found room: " + room202);
        }
        
        System.out.println("\n=== All Tests Complete ===");
    }
}