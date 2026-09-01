package Composite;

import java.util.ArrayList;
import java.util.List;

public class Department extends OrganizationComponent {
    private final String name;
    private final List<OrganizationComponent> children = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    public void add(OrganizationComponent component) {
        children.add(component);
    }

    public void remove(OrganizationComponent component) {
        children.remove(component);
    }

    @Override
    public double getSalary() {
        double totalSalary = 0;

        for (OrganizationComponent child : children) {
            totalSalary += child.getSalary();
        }

        return totalSalary;
    }

    @Override
    protected void printXml(int indentationLevel) {
        System.out.println(indentation(indentationLevel)
                + "<department name=\"" + escapeXml(name) + "\">");

        for (OrganizationComponent child : children) {
            child.printXml(indentationLevel + 1);
        }

        System.out.println(indentation(indentationLevel) + "</department>");
    }
}
