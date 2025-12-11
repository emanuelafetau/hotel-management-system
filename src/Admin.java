package Guests;

public class Admin extends Employee {

    private boolean hasFullAccess;

    public Admin(String name, String phoneNumber, double salary) {
        super(name, phoneNumber, "Admin", salary);
        this.hasFullAccess = true;
    }

    public boolean hasFullAccess() {
        return hasFullAccess;
    }

    public void promoteEmployee(Employee e) {
        System.out.println("Admin " + getName() + " promoted " + e.getName() + "!");
    }

    @Override
    public String toString() {
        return "Admin: " + getName() + " (Full Access)";
    }
}




