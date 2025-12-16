import java.io.*;
import java.util.*;

/**
 * File I/O class with exactly 4 methods:
 * 1. Read rooms from text file
 * 2. Write rooms to text file
 * 3. Read rooms from binary file (data persistence)
 * 4. Write rooms to binary file (data persistence)
 */
public class FileIO {
    
    // ==================== METHOD 1: READ FROM TEXT FILE ====================
    
    /**
     * Reads rooms from a CSV text file and creates Room objects
     * Expected format: RoomNumber,Price,RoomType,IsAvailable
     * Example: 101,99.99,SINGLE,true
     * 
     * @param filename The name of the text file to read from
     * @return List of Room objects created from the file data
     */
    public static List<Room> readRoomsFromText(String filename) {
        List<Room> rooms = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                // Skip header line if present
                if (lineNumber == 1 && (line.contains("RoomNumber") || line.contains("Room"))) {
                    continue;
                }
                
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                try {
                    Room room = parseRoomLine(line);
                    if (room != null) {
                        rooms.add(room);
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing line " + lineNumber + ": " + line);
                    System.out.println("Error details: " + e.getMessage());
                }
            }
            
            System.out.println("Successfully read " + rooms.size() + " rooms from text file: " + filename);
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        
        return rooms;
    }
    
    /**
     * Helper method to parse a single line from the text file
     */
    private static Room parseRoomLine(String line) throws IllegalArgumentException {
        String[] parts = line.split(",");
        
        if (parts.length < 3) {
            throw new IllegalArgumentException("Line must have at least 3 values separated by commas");
        }
        
        // Parse room number
        int roomNumber = Integer.parseInt(parts[0].trim());
        
        // Parse price
        double price = Double.parseDouble(parts[1].trim());
        
        // Parse room type
        String typeStr = parts[2].trim().toUpperCase();
        Room.RoomType roomType;
        try {
            roomType = Room.RoomType.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid room type: " + typeStr + 
                ". Valid types: SINGLE, DOUBLE, SUITE");
        }
        
        // Create room
        Room room = new Room(roomNumber, price, roomType);
        
        // Parse availability if provided (optional)
        if (parts.length >= 4) {
            boolean isAvailable = Boolean.parseBoolean(parts[3].trim());
            room.setAvailable(isAvailable);
        }
        
        return room;
    }
    
    // ==================== METHOD 2: WRITE TO TEXT FILE ====================
    
    /**
     * Writes rooms to a CSV text file
     * Format: RoomNumber,Price,RoomType,IsAvailable
     * 
     * @param rooms List of Room objects to write
     * @param filename The name of the text file to write to
     */
    public static void writeRoomsToText(List<Room> rooms, String filename) {
        if (rooms == null || rooms.isEmpty()) {
            System.out.println("Warning: No rooms to write to file");
            return;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write header
            writer.write("RoomNumber,Price,RoomType,IsAvailable");
            writer.newLine();
            
            // Write each room
            for (Room room : rooms) {
                String line = String.format("%d,%.2f,%s,%s",
                    room.getRoomNumber(),
                    room.getPrice(),
                    room.getType(),
                    room.isAvailable()
                );
                writer.write(line);
                writer.newLine();
            }
            
            System.out.println("Successfully wrote " + rooms.size() + 
                " rooms to text file: " + filename);
            
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    // ==================== METHOD 3: READ FROM BINARY FILE ====================
    
    /**
     * Reads rooms from a binary file for data persistence
     * This preserves the full object state including all attributes
     * 
     * @param filename The name of the binary file to read from
     * @return List of Room objects loaded from the binary file
     */
    @SuppressWarnings("unchecked")
    public static List<Room> readRoomsFromBinary(String filename) {
        List<Room> rooms = new ArrayList<>();
        
        try (ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(filename))) {
            
            // Read the entire list from binary file
            rooms = (List<Room>) inputStream.readObject();
            
            System.out.println("Successfully read " + rooms.size() + 
                " rooms from binary file: " + filename);
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: Binary file not found - " + filename);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Room class not found - incompatible file format");
        } catch (IOException e) {
            System.out.println("Error reading binary file: " + e.getMessage());
        }
        
        return rooms;
    }
    
    // ==================== METHOD 4: WRITE TO BINARY FILE ====================
    
    /**
     * Writes rooms to a binary file for data persistence
     * This saves the complete object state for later retrieval
     * 
     * @param rooms List of Room objects to save
     * @param filename The name of the binary file to write to
     */
    public static void writeRoomsToBinary(List<Room> rooms, String filename) {
        if (rooms == null || rooms.isEmpty()) {
            System.out.println("Warning: No rooms to write to binary file");
            return;
        }
        
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            
            // Write the entire list to binary file
            outputStream.writeObject(rooms);
            
            System.out.println("Successfully wrote " + rooms.size() + 
                " rooms to binary file: " + filename);
            
        } catch (IOException e) {
            System.out.println("Error writing to binary file: " + e.getMessage());
        }
    }
    
    // ==================== UTILITY METHODS (Optional) ====================
    
    /**
     * Optional: Display rooms in a formatted table
     */
    public static void displayRooms(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            System.out.println("No rooms to display.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ROOM LIST (" + rooms.size() + " rooms)");
        System.out.println("=".repeat(60));
        System.out.printf("%-10s %-10s %-15s %-10s\n", 
            "Room #", "Type", "Price/Night", "Available");
        System.out.println("-".repeat(60));
        
        for (Room room : rooms) {
            System.out.printf("%-10d %-10s $%-14.2f %-10s\n",
                room.getRoomNumber(),
                room.getType(),
                room.getPrice(),
                room.isAvailable() ? "Yes" : "No"
            );
        }
        System.out.println("=".repeat(60) + "\n");
    }
    
    /**
     * Optional: Find a room by room number
     */
    public static Room findRoomByNumber(List<Room> rooms, int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }
    
    /**
     * Optional: Filter rooms by type
     */
    public static List<Room> filterByType(List<Room> rooms, Room.RoomType type) {
        List<Room> filtered = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getType() == type) {
                filtered.add(room);
            }
        }
        return filtered;
    }
}
