package Composite;

public class Main {
    public static void main(String[] args) {
        Department company = new Department("Company");
        Department engineering = new Department("Engineering");
        Department backend = new Department("Backend");
        Department sales = new Department("Sales");

        Employee alice = new Employee("Alice", 70000);
        Employee bob = new Employee("Bob", 80000);
        Employee carol = new Employee("Carol", 90000);
        Employee dave = new Employee("Dave", 100000);
        Employee eve = new Employee("Eve", 65000);

        backend.add(carol);
        backend.add(dave);

        engineering.add(alice);
        engineering.add(bob);
        engineering.add(backend);

        sales.add(eve);

        company.add(engineering);
        company.add(sales);

        Employee frank = new Employee("Frank", 72000);
        sales.add(frank);
        sales.remove(frank);

        company.printTotalSalary();
        company.printXmlCaller();
    }
}
