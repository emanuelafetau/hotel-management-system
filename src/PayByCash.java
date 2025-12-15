import java.util.Objects;

public class PayByCash extends Payment {

    private double amountReceived;
    private double changeGiven;

    public PayByCash(Invoice invoice, double amount, double amountReceived) {
        super(invoice, amount);
        this.amountReceived = amountReceived;
    }

    @Override
    public boolean processPayment() {
        if (amountReceived >= getAmount()) {
            changeGiven = amountReceived - getAmount();
            recordPayment();
            getInvoice().markAsPaid();
            System.out.println("Cash payment successful!");
            System.out.println(generateReceipt());
            System.out.printf("Change given: $%.2f%n", changeGiven);

            return true;
        }
        System.out.println("Insufficient cash!");
        return false;
    }

    public double getChangeGiven() {
        return changeGiven;
    }
    

    public double getAmountReceived() {
		return amountReceived;
	}

	@Override
    public String toString() {
        return "PayByCash{" +
               "amountReceived=" + amountReceived +
               ", changeGiven=" + changeGiven +
               ", paymentId=" + getPaymentId() +
               '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amountReceived, changeGiven);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        PayByCash other = (PayByCash) obj;
        return Double.compare(amountReceived, other.amountReceived) == 0 &&
               Double.compare(changeGiven, other.changeGiven) == 0;
    }
}

