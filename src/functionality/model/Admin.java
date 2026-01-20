public class Admin extends Employee {
    private String email;
    private String password;

    public Admin(String name, String phoneNumber, double salary, String email, String password) {
        super(name, phoneNumber, salary, "Admin");
        setEmail(email);
        setPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        this.password = password;
    }

    public boolean verifyPassword(String password) {
        return this.password != null && this.password.equals(password);
    }

    public void approveInvoice(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice cannot be null.");
        }
        // Implementation for invoice approval
        System.out.println("Invoice " + invoice.getInvoiceId() + " approved by Admin " + getName());
    }

    @Override
    public String toString() {
        return "Admin - " + super.toString() + " [" + email + "]";
    }
}
