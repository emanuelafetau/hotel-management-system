
public class Admin extends Employee {

    public Admin(String name, String phoneNumber, double salary) {
        super(name, phoneNumber, salary, "Admin");
    }

    public void approveInvoice(Invoice invoice) {
        // Admin-only action
    }

    @Override
    public String toString() {
        return "Admin - " + super.toString();
    }
}





