import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static int nextPaymentId = 100;
    private final int paymentId;
    private final LocalDate paymentDate;
    private final double amount;
    
    // Store only invoice ID instead of full Invoice reference to avoid circular dependency issues
    private final int invoiceId;
    
    protected static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    protected Payment(Invoice invoice, double amount) {
        if (invoice == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid payment.");
        }
        if (invoice.getStatus() == Invoice.InvoiceStatus.PAID) {
            throw new IllegalArgumentException("Invoice is already paid.");
        }
        
        this.paymentId = nextPaymentId++;
        this.paymentDate = LocalDate.now();
        this.amount = amount;
        this.invoiceId = invoice.getInvoiceId();
    }
    
    public abstract boolean processPayment();
    
    // Get invoice ID instead of full invoice object
    public int getInvoiceId() {
        return invoiceId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String generateReceipt() {
        return """
               Payment ID: %d
               Date: %s
               Invoice ID: %d
               Amount paid: $%.2f
               """.formatted(
                paymentId,
                paymentDate.format(DATE_FORMATTER),
                invoiceId,
                amount
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
               ", invoiceId=" + invoiceId +
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
