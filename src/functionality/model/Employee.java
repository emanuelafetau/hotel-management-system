import java.io.Serializable;

public class Employee extends Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static int employeeCounter = 1;

    private final int employeeId;
    private double salary;
    private String role;

    public Employee(String name, String phoneNumber, double salary, String role) {
        super(name, phoneNumber);
        if (salary <= 0) throw new IllegalArgumentException("Salary must be positive.");
        this.employeeId = employeeCounter++;
        this.salary = salary;
        this.role = role;
    }

    public int getEmployeeId() { return employeeId; }
    public double getSalary() { return salary; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        return role + " - " + super.toString();
    }
}
