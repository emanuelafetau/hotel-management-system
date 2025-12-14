import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Invoice {
    public enum InvoiceStatus 
    { PAID, UNPAID }

    private static int invoiceCounter = 1;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final int invoiceId;
    private final Reservation reservation;
    private final LocalDate issueDate;
    private final long nights;
    private final double amount;
    private InvoiceStatus status;

    public Invoice(Reservation reservation) {
        validateReservation(reservation);
        
        this.invoiceId = invoiceCounter++;
        this.reservation = reservation;
        this.issueDate = LocalDate.now();
        this.nights = ChronoUnit.DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());
        this.amount = nights * reservation.getRoom().getPrice();
        this.status = InvoiceStatus.UNPAID;
    }

    private void validateReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null.");
        }
        if (reservation.getStatus() == Reservation.ReservationStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot create invoice for cancelled reservation.");
        }
        
        if (!reservation.getCheckOutDate().isAfter(reservation.getCheckInDate())) {
            throw new IllegalArgumentException("Invalid reservation dates.");
        }
    }

    public void markAsPaid() { 
        status = InvoiceStatus.PAID; 
    }

    public boolean coversDate(LocalDate date) {
        return reservation.isActiveOn(date);
    }

    public int getInvoiceId() { return invoiceId; }
    public Reservation getReservation() { return reservation; }
    public Guest getGuest() { return reservation.getGuest(); }
    public Room getRoom() { return reservation.getRoom(); }
    public LocalDate getIssueDate() { return issueDate; }
    public double getAmount() { return amount; }
    public long getNights() { return nights; }
    public InvoiceStatus getStatus() { return status; }

    public void printInvoice() {
        System.out.println("========================================");
        System.out.println("              INVOICE");
        System.out.println("========================================");
        System.out.println("Invoice ID: " + invoiceId);
        System.out.println("Issue Date: " + issueDate.format(DATE_FORMATTER));
        System.out.println();
        System.out.println("Guest: " + reservation.getGuest().getName());
        System.out.println("Room: " + reservation.getRoom().getRoomNumber());
        System.out.println();
        System.out.println("Check-in:  " + reservation.getCheckInDate().format(DATE_FORMATTER));
        System.out.println("Check-out: " + reservation.getCheckOutDate().format(DATE_FORMATTER));
        System.out.println("Nights: " + nights);
        System.out.printf("Rate per night: $%.2f%n", reservation.getRoom().getPrice());
        System.out.printf("Total Amount: $%.2f%n", amount);
        System.out.println("Status: " + status);
        System.out.println("========================================");
    }

    @Override
    public String toString() {
        return String.format(
            "Invoice %d - Guest: %s, Room: %d, Amount: $%.2f, Status: %s",
            invoiceId,
            reservation.getGuest().getName(),
            reservation.getRoom().getRoomNumber(),
            amount,
            status
        );
    }
}