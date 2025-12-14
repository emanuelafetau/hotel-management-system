import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NightAudit {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Hotel hotel;
    private final LocalDate auditDate;
    private final Employee performedBy;

    private double totalRevenue;
    private double occupancyRate;
    private int unpaidInvoicesCount;

    public NightAudit(Hotel hotel, LocalDate auditDate, Employee performedBy) {
        if (hotel == null || auditDate == null || performedBy == null) {
            throw new IllegalArgumentException("Hotel, audit date, and employee cannot be null.");
        }
        this.hotel = hotel;
        this.auditDate = auditDate;
        this.performedBy = performedBy;
    }

    private void performAudit() {
        calculateRevenue();
        calculateOccupancy();
        countUnpaidInvoices();
    }

    private void calculateRevenue() {
        totalRevenue = hotel.calculateDailyRevenueFor(auditDate);
    }

    private void calculateOccupancy() {
        ArrayList<Room> allRooms = hotel.getAllRooms();
        int totalRooms = allRooms.size();
        
        if (totalRooms == 0) {
            occupancyRate = 0;
            return;
        }

        int occupiedCount = hotel.countOccupiedRoomsOn(auditDate);
        occupancyRate = (occupiedCount * 100.0) / totalRooms;
    }

    private void countUnpaidInvoices() {
        unpaidInvoicesCount = 0;
        for (Invoice inv : hotel.getAllInvoices()) {
            if (inv.getStatus() == Invoice.InvoiceStatus.UNPAID) {
                unpaidInvoicesCount++;
            }
        }
    }

    public void printDailyReport() {
        System.out.println("========================================");
        System.out.println("      NIGHT AUDIT REPORT");
        System.out.println("========================================");
        System.out.println("Date: " + auditDate.format(DATE_FORMATTER));
        System.out.println("Performed by: " + performedBy.getName());
        System.out.println();
        System.out.println("--- STATISTICS ---");
        System.out.println("Total Guests: " + hotel.getAllGuests().size());
        System.out.println("Total Rooms: " + hotel.getAllRooms().size());
        System.out.println("Active Reservations Today: " + hotel.getActiveReservations().size());
        System.out.printf("Occupancy Rate: %.2f%%\n", occupancyRate);
        System.out.println();
        System.out.println("--- FINANCIALS ---");
        System.out.println("Total Invoices: " + hotel.getAllInvoices().size());
        System.out.println("Unpaid Invoices: " + unpaidInvoicesCount);
        System.out.printf("Revenue Today: $%.2f\n", totalRevenue);
        System.out.println("========================================");
    }

    public void processEndOfDay() {
        performAudit();
        printDailyReport();
    }

    public double getTotalRevenue() { return totalRevenue; }
    public double getOccupancyRate() { return occupancyRate; }
    public int getUnpaidInvoicesCount() { return unpaidInvoicesCount; }

    @Override
    public String toString() {
        return String.format("Night Audit - %s - Revenue: $%.2f - Occupancy: %.2f%%",
                auditDate.format(DATE_FORMATTER),
                totalRevenue, occupancyRate);
    }
}
