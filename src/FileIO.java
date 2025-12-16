import java.io.*;
import java.util.*;

public class FileIO {
   
    public static List<Room> readRoomsFromText(String filename) {
        List<Room> rooms = new ArrayList<>();
       
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
           
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1 && (line.contains("RoomNumber") || line.contains("Room"))) {
                    continue;
                }
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
                }
            }
           
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
       
        return rooms;
    }
   
    private static Room parseRoomLine(String line) throws IllegalArgumentException {
        String[] parts = line.split(",");
       
        if (parts.length < 3) {
            throw new IllegalArgumentException("Line must have at least 3 values");
        }
       
        int roomNumber = Integer.parseInt(parts[0].trim());
        double price = Double.parseDouble(parts[1].trim());
        Room.RoomType roomType = Room.RoomType.valueOf(parts[2].trim().toUpperCase());
        Room room = new Room(roomNumber, price, roomType);
       
        if (parts.length >= 4) {
            room.setAvailable(Boolean.parseBoolean(parts[3].trim()));
        }
       
        return room;
    }
   
    
    public static void writeRoomsToText(List<Room> rooms, String filename) {
        if (rooms == null || rooms.isEmpty()) return;
       
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("RoomNumber,Price,RoomType,IsAvailable");
            writer.newLine();
           
            for (Room room : rooms) {
                writer.write(String.format("%d,%.2f,%s,%s",
                    room.getRoomNumber(), room.getPrice(),
                    room.getType(), room.isAvailable()));
                writer.newLine();
            }
            System.out.println("Successfully wrote " + rooms.size() + " rooms to text file: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    
    public static void appendRoomsToText(List<Room> rooms, String filename) {
        if (rooms == null || rooms.isEmpty()) return;
        
        
        File file = new File(filename);
        boolean fileExists = file.exists() && file.length() > 0;
       
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
           
            if (!fileExists) {
                writer.write("RoomNumber,Price,RoomType,IsAvailable");
                writer.newLine();
            }
            
            for (Room room : rooms) {
                writer.write(String.format("%d,%.2f,%s,%s",
                    room.getRoomNumber(), room.getPrice(),
                    room.getType(), room.isAvailable()));
                writer.newLine();
            }
            System.out.println("Successfully appended " + rooms.size() + " rooms to text file: " + filename);
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
        }
    }
   
    @SuppressWarnings("unchecked")
    public static List<Room> readRoomsFromBinary(String filename) {
        List<Room> rooms = new ArrayList<>();
       
        try (ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(filename))) {
            rooms = (List<Room>) inputStream.readObject();
            System.out.println("Successfully read " + rooms.size() + " rooms from binary file: " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Binary file not found - " + filename);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Room class not found - incompatible file format");
        } catch (IOException e) {
            System.out.println("Error reading binary file: " + e.getMessage());
        }
       
        return rooms;
    }
   
    
    public static void writeRoomsToBinary(List<Room> rooms, String filename) {
        if (rooms == null || rooms.isEmpty()) return;
       
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            outputStream.writeObject(rooms);
            System.out.println("Successfully wrote " + rooms.size() + " rooms to binary file: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to binary file: " + e.getMessage());
        }
    }
    
    
    public static void appendRoomsToBinary(List<Room> newRooms, String filename) {
        if (newRooms == null || newRooms.isEmpty()) return;
        
        List<Room> existingRooms = readRoomsFromBinary(filename);
        
        existingRooms.addAll(newRooms);
        
        writeRoomsToBinary(existingRooms, filename);
        System.out.println("Successfully appended " + newRooms.size() + " new rooms (total: " + existingRooms.size() + ")");
    }
    
    public static void displayRooms(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
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
}