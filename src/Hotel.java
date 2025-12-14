import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {
    private String hotelName;
    private final ArrayList<Guest> guests = new ArrayList<>();
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<Reservation> reservations = new ArrayList<>();
    private final ArrayList<Employee> staff = new ArrayList<>();
    private final ArrayList<Invoice> invoices = new ArrayList<>();

    public Hotel(String hotelName) { 
        this.hotelName = hotelName; 
    }

    public boolean addGuest(Guest guest) {
        if (guest == null || findGuest(guest.getGuestId()) != null) {
            return false;
        }
        guests.add(guest);
        return true;
    }

    public Guest findGuest(int guestId) {
        for (Guest g : guests) {
            if (g.getGuestId() == guestId) return g;
        }
        return null;
    }

    public boolean removeGuest(int guestId) {
        Guest guest = findGuest(guestId);
        if (guest == null) return false;
        
        if (hasActiveFutureReservations(guest)) {
            return false;
        }
        
        return guests.remove(guest);
    }

    private boolean hasActiveFutureReservations(Guest guest) {
        LocalDate today = LocalDate.now();
        for (Reservation r : reservations) {
            if (r.getGuest().equals(guest) && 
                r.isActive() && 
                r.getCheckOutDate().isAfter(today)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Guest> getAllGuests() { 
        return new ArrayList<>(guests); 
    }

    public boolean addRoom(Room room) {
        if (room == null || findRoom(room.getRoomNumber()) != null) {
            return false;
        }
        if (room.getPrice() <= 0) {
            return false; 
        }
        rooms.add(room);
        return true;
    }

    public Room findRoom(int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) return r;
        }
        return null;
    }

    public boolean removeRoom(int roomNumber) {
        Room room = findRoom(roomNumber);
        if (room == null) return false;
        
        if (hasActiveFutureReservations(room)) {
            return false;
        }
        
        return rooms.remove(room);
    }

    private boolean hasActiveFutureReservations(Room room) {
        LocalDate today = LocalDate.now();
        for (Reservation r : reservations) {
            if (r.getRoom().equals(room) && 
                r.isActive() && 
                r.getCheckOutDate().isAfter(today)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Room> getAllRooms() { 
        return new ArrayList<>(rooms); 
    }

    public ArrayList<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        ArrayList<Room> available = new ArrayList<>();
        for (Room room : rooms) {
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
            reservations.add(reservation);
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
        for (Reservation r : reservations) {
            if (r.getReservationId() == reservationId) return r;
        }
        return null;
    }

    public ArrayList<Reservation> getAllReservations() { 
        return new ArrayList<>(reservations); 
    }

    public ArrayList<Reservation> getActiveReservations() {
        ArrayList<Reservation> active = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.isActive()) {
                active.add(r);
            }
        }
        return active;
    }

    private boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        for (Reservation r : reservations) {
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
            invoices.add(invoice);
            return invoice;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public boolean hasInvoiceForReservation(Reservation reservation) {
        if (reservation == null) return false;
        for (Invoice inv : invoices) {
            if (inv.getReservation().equals(reservation)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Invoice> getAllInvoices() { 
        return new ArrayList<>(invoices); 
    }

    public double calculateDailyRevenueFor(LocalDate date) {
        double totalRevenue = 0;
        for (Invoice inv : invoices) {
            if (inv.getStatus() == Invoice.InvoiceStatus.PAID && inv.coversDate(date)) {
                double dailyAmount = inv.getAmount() / inv.getNights();
                totalRevenue += dailyAmount;
            }
        }
        return totalRevenue;
    }

    public int countOccupiedRoomsOn(LocalDate date) {
        int count = 0;
        for (Room room : rooms) {
            boolean isOccupied = false;
            for (Reservation r : reservations) {
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
        if (findEmployee(employee.getEmployeeId()) != null) {
            return false;
        }
        staff.add(employee);
        return true;
    }

    public Employee findEmployee(int employeeId) {
        for (Employee e : staff) {
            if (e.getEmployeeId() == employeeId) return e;
        }
        return null;
    }

    public ArrayList<Employee> getAllEmployees() { 
        return new ArrayList<>(staff); 
    }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    @Override
    public String toString() {
        return String.format("%s - Guests: %d, Rooms: %d, Reservations: %d, Invoices: %d",
                hotelName, guests.size(), rooms.size(), reservations.size(), invoices.size());
    }
}