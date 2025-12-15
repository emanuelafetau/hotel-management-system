import java.util.Objects;

public class PayByCard extends Payment {

    private String cardNumber;
    private String cardOwner;
    private String expiryDate;
    private String cvv;

    public PayByCard(Invoice invoice, double amount,
                     String cardNumber,
                     String cardOwner,
                     String expiryDate,
                     String cvv) {
        super(invoice, amount);
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean processPayment() {
        if (isValidCard()) {
            recordPayment();
            getInvoice().markAsPaid();
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

            int currentYear = 25;
            int currentMonth = 12;

            return year < currentYear ||
                   (year == currentYear && month < currentMonth);
        } catch (Exception e) {
            return true;
        }
    }
    

    public String getCardNumber() {
		return cardNumber;
	}

	public String getCardOwner() {
		return cardOwner;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public String getCvv() {
		return cvv;
	}

	@Override
    public String toString() {
        return "PayByCard{" +
               "cardNumber='" + cardNumber + '\'' +
               ", cardOwner='" + cardOwner + '\'' +
               ", expiryDate='" + expiryDate + '\'' +
               ", cvv='" + cvv + '\'' +
               ", paymentId=" + getPaymentId() +
               '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cardNumber, cardOwner, expiryDate, cvv);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        PayByCard other = (PayByCard) obj;
        return Objects.equals(cardNumber, other.cardNumber) &&
               Objects.equals(cardOwner, other.cardOwner) &&
               Objects.equals(expiryDate, other.expiryDate) &&
               Objects.equals(cvv, other.cvv);
    }
}

