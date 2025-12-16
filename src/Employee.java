package Guests;

public class Employee extends Person {

    private static int employeeCounter = 1;

    private final int employeeId;
    private double salary;
    private String position;

    public Employee(String name, String phoneNumber, double salary, String position) {
        super(name, phoneNumber);

        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be positive.");
        }

        this.employeeId = employeeCounter++;
        this.salary = salary;
        this.position = position;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be positive.");
        }
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Employee #" + employeeId + " - " + super.toString()
               + ", Position: " + position
               + ", Salary: $" + salary;
    }
}
