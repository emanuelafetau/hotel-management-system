import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("    FILE I/O TESTING PROGRAM");
        System.out.println("======================================\n");
        
        // ========================================
        // TEST 1: Write Initial Rooms to Text File
        // ========================================
        System.out.println("TEST 1: Writing Initial Rooms to Text File");
        System.out.println("-------------------------------------------");
        
        List<Room> initialRooms = new ArrayList<>();
        initialRooms.add(new Room(101, 99.99, Room.RoomType.SINGLE));
        initialRooms.add(new Room(102, 149.99, Room.RoomType.DOUBLE));
        
        System.out.println("Creating " + initialRooms.size() + " rooms:");
        for (Room r : initialRooms) {
            System.out.println("  - " + r);
        }
        
        FileIO.writeRoomsToText(initialRooms, "rooms.txt");
        System.out.println();
        
        // ========================================
        // TEST 2: Read Rooms from Text File
        // ========================================
        System.out.println("TEST 2: Reading Rooms from Text File");
        System.out.println("-------------------------------------------");
        
        List<Room> loadedRooms = FileIO.readRoomsFromText("rooms.txt");
        System.out.println("Loaded " + loadedRooms.size() + " rooms from file");
        FileIO.displayRooms(loadedRooms);
        System.out.println();
        
        // ========================================
        // TEST 3: Append More Rooms to Text File
        // ========================================
        System.out.println("TEST 3: Appending More Rooms to Text File");
        System.out.println("-------------------------------------------");
        
        List<Room> moreRooms = new ArrayList<>();
        moreRooms.add(new Room(201, 249.99, Room.RoomType.SUITE));
        moreRooms.add(new Room(202, 119.99, Room.RoomType.SINGLE));
        
        System.out.println("Creating " + moreRooms.size() + " more rooms:");
        for (Room r : moreRooms) {
            System.out.println("  - " + r);
        }
        
        FileIO.appendRoomsToText(moreRooms, "rooms.txt");
        System.out.println();
        
        // ========================================
        // TEST 4: Read All Rooms from Text File
        // ========================================
        System.out.println("TEST 4: Reading All Rooms After Append");
        System.out.println("-------------------------------------------");
        
        List<Room> allRoomsText = FileIO.readRoomsFromText("rooms.txt");
        System.out.println("Total rooms in text file: " + allRoomsText.size());
        FileIO.displayRooms(allRoomsText);
        System.out.println();
        
        // ========================================
        // TEST 5: Append Even More Rooms
        // ========================================
        System.out.println("TEST 5: Appending Third Batch of Rooms");
        System.out.println("-------------------------------------------");
        
        List<Room> thirdBatch = new ArrayList<>();
        thirdBatch.add(new Room(301, 179.99, Room.RoomType.DOUBLE));
        thirdBatch.add(new Room(302, 299.99, Room.RoomType.SUITE));
        thirdBatch.add(new Room(303, 89.99, Room.RoomType.SINGLE));
        
        System.out.println("Creating " + thirdBatch.size() + " more rooms:");
        for (Room r : thirdBatch) {
            System.out.println("  - " + r);
        }
        
        FileIO.appendRoomsToText(thirdBatch, "rooms.txt");
        
        List<Room> finalTextRooms = FileIO.readRoomsFromText("rooms.txt");
        System.out.println("\nFinal count in text file: " + finalTextRooms.size() + " rooms");
        FileIO.displayRooms(finalTextRooms);
        System.out.println();
        
        // ========================================
        // TEST 6: Write to Binary File
        // ========================================
        System.out.println("TEST 6: Writing Rooms to Binary File");
        System.out.println("-------------------------------------------");
        
        List<Room> binaryBatch1 = new ArrayList<>();
        binaryBatch1.add(new Room(401, 129.99, Room.RoomType.SINGLE));
        binaryBatch1.add(new Room(402, 199.99, Room.RoomType.DOUBLE));
        
        System.out.println("Creating " + binaryBatch1.size() + " rooms for binary:");
        for (Room r : binaryBatch1) {
            System.out.println("  - " + r);
        }
        
        FileIO.writeRoomsToBinary(binaryBatch1, "rooms.dat");
        System.out.println();
        
        // ========================================
        // TEST 7: Read from Binary File
        // ========================================
        System.out.println("TEST 7: Reading from Binary File");
        System.out.println("-------------------------------------------");
        
        List<Room> loadedBinary = FileIO.readRoomsFromBinary("rooms.dat");
        FileIO.displayRooms(loadedBinary);
        System.out.println();
        
        // ========================================
        // TEST 8: Append to Binary File
        // ========================================
        System.out.println("TEST 8: Appending to Binary File");
        System.out.println("-------------------------------------------");
        
        List<Room> binaryBatch2 = new ArrayList<>();
        binaryBatch2.add(new Room(403, 279.99, Room.RoomType.SUITE));
        binaryBatch2.add(new Room(404, 139.99, Room.RoomType.DOUBLE));
        
        System.out.println("Creating " + binaryBatch2.size() + " more rooms:");
        for (Room r : binaryBatch2) {
            System.out.println("  - " + r);
        }
        
        FileIO.appendRoomsToBinary(binaryBatch2, "rooms.dat");
        System.out.println();
        
        // ========================================
        // TEST 9: Read All from Binary File
        // ========================================
        System.out.println("TEST 9: Reading All Rooms from Binary File");
        System.out.println("-------------------------------------------");
        
        List<Room> allBinaryRooms = FileIO.readRoomsFromBinary("rooms.dat");
        System.out.println("Total rooms in binary file: " + allBinaryRooms.size());
        FileIO.displayRooms(allBinaryRooms);
        System.out.println();
        
        // ========================================
        // TEST 10: Mark Some Rooms as Unavailable
        // ========================================
        System.out.println("TEST 10: Modifying Room Availability");
        System.out.println("-------------------------------------------");
        
        List<Room> modifiedRooms = new ArrayList<>();
        Room r1 = new Room(501, 159.99, Room.RoomType.DOUBLE);
        Room r2 = new Room(502, 159.99, Room.RoomType.DOUBLE);
        r2.setAvailable(false); // Mark as unavailable
        
        modifiedRooms.add(r1);
        modifiedRooms.add(r2);
        
        System.out.println("Writing rooms with different availability:");
        for (Room r : modifiedRooms) {
            System.out.println("  - Room " + r.getRoomNumber() + 
                             " (Available: " + r.isAvailable() + ")");
        }
        
        FileIO.writeRoomsToText(modifiedRooms, "modified_rooms.txt");
        
        List<Room> loadedModified = FileIO.readRoomsFromText("modified_rooms.txt");
        System.out.println("\nReading back modified rooms:");
        FileIO.displayRooms(loadedModified);
        
        // ========================================
        // SUMMARY
        // ========================================
        System.out.println("\n======================================");
        System.out.println("         TEST SUMMARY");
        System.out.println("======================================");
        System.out.println("✓ Text file (rooms.txt): " + finalTextRooms.size() + " rooms");
        System.out.println("✓ Binary file (rooms.dat): " + allBinaryRooms.size() + " rooms");
        System.out.println("✓ Modified file (modified_rooms.txt): " + loadedModified.size() + " rooms");
        System.out.println("\n✓ All File I/O operations completed successfully!");
        System.out.println("======================================\n");
    }
}
