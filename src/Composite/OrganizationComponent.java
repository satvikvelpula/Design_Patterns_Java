package Composite;

public abstract class OrganizationComponent {
    public abstract double getSalary();

    public void printTotalSalary() {
        System.out.println("Total salary: " + getSalary());
    }

    public void printXmlCaller() {
        printXml(0);
    }

    protected abstract void printXml(int indentationLevel);

    protected String indentation(int indentationLevel) {
        return "    ".repeat(indentationLevel);
    }

    protected String escapeXml(String text) {
        return text.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;");
        // "Regex" like escaping XML/filtering bad instances, researched through https://stackoverflow.com/questions/1091945/what-characters-do-i-need-to-escape-in-xml-documents
    }

}
