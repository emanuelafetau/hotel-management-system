package Guests;

public class Employee extends Person {

    private static int employeeCounter = 1;

    private int employeeId;
    private String position;
    private double salary;

    public Employee(String name, String phoneNumber, String position, double salary) {
        super(name, phoneNumber);
        this.employeeId = employeeCounter++;
        setPosition(position);
        setSalary(salary);
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if (position == null || position.isBlank()) {
            throw new IllegalArgumentException("Position cannot be empty.");
        }
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee " + employeeId + ": " + name + " (" + position + ", $" + salary + ")";
    }
}

