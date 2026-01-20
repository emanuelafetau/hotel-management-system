import java.io.Serializable;
import java.util.Objects;

public class PayByCard extends Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String cardNumber;
    private String cardOwner;
    private String expiryDate;
    private String cvv;
    
    // Store invoice reference temporarily during processing (transient = not saved)
    private transient Invoice invoice;

    public PayByCard(Invoice invoice, double amount,
                     String cardNumber,
                     String cardOwner,
                     String expiryDate,
                     String cvv) {
        super(invoice, amount);
        this.invoice = invoice;
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean processPayment() {
        if (isValidCard() && invoice != null) {
            invoice.addPayment(this);
            System.out.println("Card payment successful!");
            System.out.println(generateReceipt());
            return true;
        }
        System.out.println("Card payment failed!");
        return false;
    }

    private boolean isValidCard() {
        return !isExpired()
                && cardNumber != null
                && cardNumber.length() >= 13
                && cardNumber.length() <= 16
                && cvv != null
                && cvv.length() == 3;
    }

    private boolean isExpired() {
        try {
            String[] parts = expiryDate.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);

            int currentYear = 26;
            int currentMonth = 1;

            return year < currentYear ||
                   (year == currentYear && month < currentMonth);
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public String toString() {
        return "PayByCard{" +
               "cardNumber='" + maskCardNumber() + '\'' +
               ", cardOwner='" + cardOwner + '\'' +
               ", expiryDate='" + expiryDate + '\'' +
               ", paymentId=" + getPaymentId() +
               '}';
    }
    
    private String maskCardNumber() {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "****" + cardNumber.substring(cardNumber.length() - 4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cardNumber, cardOwner, expiryDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        PayByCard other = (PayByCard) obj;
        return Objects.equals(cardNumber, other.cardNumber) &&
               Objects.equals(cardOwner, other.cardOwner) &&
               Objects.equals(expiryDate, other.expiryDate);
    }
}
