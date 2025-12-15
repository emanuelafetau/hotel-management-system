package Guests;

public class Employee extends Person {

    private static int employeeCounter = 1;
    private final int employeeId;
    private String role;

    public Employee(String name, String phoneNumber, String role) {
        super(name, phoneNumber);
        this.employeeId = employeeCounter++;
        this.role = role;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Employee)) return false;
        Employee other = (Employee) obj;
        return employeeId == other.employeeId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(employeeId);
    }

    @Override
    public String toString() {
        return "Employee #" + employeeId + " - " + super.toString() +
               " | Role: " + role;
    }
}
