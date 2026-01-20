import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String hotelName;
    private final Map<Integer, Guest> guests = new HashMap<>();
    private final Map<Integer, Room> rooms = new HashMap<>();
    private final Map<Integer, Reservation> reservations = new HashMap<>();
    private final Map<Integer, Employee> staff = new HashMap<>();
    private final Map<Integer, Invoice> invoices = new HashMap<>();

    public Hotel(String hotelName) { 
        this.hotelName = hotelName; 
    }

    public boolean addGuest(Guest guest) {
        if (guest == null || guests.containsKey(guest.getGuestId())) {
            return false;
        }
        guests.put(guest.getGuestId(), guest);
        return true;
    }

    public Guest findGuest(int guestId) {
        return guests.get(guestId);
    }

    public boolean removeGuest(int guestId) {
        Guest guest = findGuest(guestId);
        if (guest == null) return false;
        
        if (hasActiveFutureReservations(guest)) {
            return false;
        }
        
        guests.remove(guestId);
        return true;
    }

    private boolean hasActiveFutureReservations(Guest guest) {
        LocalDate today = LocalDate.now();
        for (Reservation r : reservations.values()) {
            if (r.getGuest().equals(guest) && 
                r.isActive() && 
                r.getCheckOutDate().isAfter(today)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Guest> getAllGuests() { 
        return new ArrayList<>(guests.values()); 
    }

    public boolean addRoom(Room room) {
        if (room == null || rooms.containsKey(room.getRoomNumber())) {
            return false;
        }
        if (room.getPrice() <= 0) {
            return false; 
        }
        rooms.put(room.getRoomNumber(), room);
        return true;
    }

    public Room findRoom(int roomNumber) {
        return rooms.get(roomNumber);
    }

    public boolean removeRoom(int roomNumber) {
        Room room = findRoom(roomNumber);
        if (room == null) return false;
        
        if (hasActiveFutureReservations(room)) {
            return false;
        }
        
        rooms.remove(roomNumber);
        return true;
    }

    private boolean hasActiveFutureReservations(Room room) {
        LocalDate today = LocalDate.now();
        for (Reservation r : reservations.values()) {
            if (r.getRoom().equals(room) && 
                r.isActive() && 
                r.getCheckOutDate().isAfter(today)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Room> getAllRooms() { 
        return new ArrayList<>(rooms.values()); 
    }

    public ArrayList<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        ArrayList<Room> available = new ArrayList<>();
        for (Room room : rooms.values()) {
            if (isRoomAvailable(room, checkIn, checkOut)) {
                available.add(room);
            }
        }
        return available;
    }

    public Reservation makeReservation(int guestId, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        Guest guest = findGuest(guestId);
        Room room = findRoom(roomNumber);
        
        if (guest == null || room == null) {
            return null;
        }

        if (!isRoomAvailable(room, checkInDate, checkOutDate)) {
            return null;
        }

        try {
            Reservation reservation = new Reservation(guest, room, checkInDate, checkOutDate);
            reservations.put(reservation.getReservationId(), reservation);
            return reservation;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public boolean cancelReservation(int reservationId) {
        Reservation r = findReservation(reservationId);
        return r != null && r.cancelReservation();
    }

    public Reservation findReservation(int reservationId) {
        return reservations.get(reservationId);
    }

    public ArrayList<Reservation> getAllReservations() { 
        return new ArrayList<>(reservations.values()); 
    }

    public ArrayList<Reservation> getActiveReservations() {
        ArrayList<Reservation> active = new ArrayList<>();
        for (Reservation r : reservations.values()) {
            if (r.isActive()) {
                active.add(r);
            }
        }
        return active;
    }

    private boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        for (Reservation r : reservations.values()) {
            if (r.getRoom().equals(room) && r.overlaps(checkIn, checkOut)) {
                return false;
            }
        }
        return true;
    }

    public Invoice createInvoiceForReservation(int reservationId) {
        Reservation reservation = findReservation(reservationId);
        
        if (reservation == null || hasInvoiceForReservation(reservation)) {
            return null;
        }
        
        try {
            Invoice invoice = new Invoice(reservation);
            invoices.put(invoice.getInvoiceId(), invoice);
            return invoice;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public boolean hasInvoiceForReservation(Reservation reservation) {
        if (reservation == null) return false;
        for (Invoice inv : invoices.values()) {
            if (inv.getReservation().equals(reservation)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Invoice> getAllInvoices() { 
        return new ArrayList<>(invoices.values()); 
    }

    public double calculateDailyRevenueFor(LocalDate date) {
        double totalRevenue = 0;
        for (Invoice inv : invoices.values()) {
            if (inv.getStatus() == Invoice.InvoiceStatus.PAID && 
                inv.getReservation().isActiveOn(date)) {
                double dailyAmount = inv.getAmount() / inv.getNights();
                totalRevenue += dailyAmount;
            }
        }
        return totalRevenue;
    }

    public int countOccupiedRoomsOn(LocalDate date) {
        int count = 0;
        for (Room room : rooms.values()) {
            boolean isOccupied = false;
            for (Reservation r : reservations.values()) {
                if (r.getRoom().equals(room) && r.isActiveOn(date)) {
                    isOccupied = true;
                    break;
                }
            }
            if (isOccupied) {
                count++;
            }
        }
        return count;
    }

    public boolean addEmployee(Employee employee) {
        if (employee == null) return false;
        if (staff.containsKey(employee.getEmployeeId())) {
            return false;
        }
        staff.put(employee.getEmployeeId(), employee);
        return true;
    }

    public Employee findEmployee(int employeeId) {
        return staff.get(employeeId);
    }

    public ArrayList<Employee> getAllEmployees() { 
        return new ArrayList<>(staff.values()); 
    }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    @Override
    public String toString() {
        return String.format("%s - Guests: %d, Rooms: %d, Reservations: %d, Invoices: %d",
                hotelName, guests.size(), rooms.size(), reservations.size(), invoices.size());
    }
}