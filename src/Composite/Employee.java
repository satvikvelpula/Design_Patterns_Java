package Composite;

public class Employee extends OrganizationComponent {
    private final String name;
    private final double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public double getSalary() {
        return salary;
    }

    @Override
    protected void printXml(int indentationLevel) {
        System.out.println(indentation(indentationLevel)
                + "<employee name=\"" + escapeXml(name)
                + "\" salary=\"" + salary
                + "\"/>");
    }
}
