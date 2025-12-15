import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Payment {

    private static int nextPaymentId = 100;

    private final int paymentId;
    private final LocalDate paymentDate;
    private final double amount;
    private final Invoice invoice;

    protected static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected Payment(Invoice invoice, double amount) {
        if (invoice == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid payment.");
        }
        this.paymentId = nextPaymentId++;
        this.paymentDate = LocalDate.now();
        this.amount = amount;
        this.invoice = invoice;
    }

    
    public abstract boolean processPayment();

    public void recordPayment() {
        invoice.addPayment(this);
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public double getAmount() {
        return amount;
    }

    public String generateReceipt() {
        Reservation reservation = invoice.getReservation();

        return """
               Payment ID: %d
               Date: %s
               Reservation ID: %d
               Guest ID: %d
               Amount paid: $%.2f
               Invoice Status: %s
               """.formatted(
                paymentId,
                paymentDate.format(DATE_FORMATTER),
                reservation.getReservationId(),
                reservation.getGuest().getGuestId(),
                amount,
                invoice.getStatus()
        );
    }

    public int getPaymentId() {
        return paymentId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    @Override
    public String toString() {
        return "Payment{id=" + paymentId +
               ", date=" + paymentDate +
               ", amount=" + amount +
               ", invoiceId=" + invoice.getInvoiceId() +
               "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Payment other = (Payment) obj;
        return paymentId == other.paymentId;
    }
}


	
	
	
	
	

