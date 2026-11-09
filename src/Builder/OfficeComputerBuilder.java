package Builder;

/**
 * Concrete builder for a modest office PC.
 */
public class OfficeComputerBuilder implements ComputerBuilder {
    private final Computer computer = new Computer();

    @Override
    public ComputerBuilder buildProcessor() {
        computer.setProcessor("Intel Core i5-13400");
        return this;
    }

    @Override
    public ComputerBuilder buildRAM() {
        computer.setRamGb(16);
        return this;
    }

    @Override
    public ComputerBuilder buildHardDrive() {
        computer.setHardDrive("512 GB SSD");
        return this;
    }

    @Override
    public ComputerBuilder buildGraphicsCard() {
        computer.setGraphicsCard("Integrated Intel UHD Graphics");
        return this;
    }

    @Override
    public ComputerBuilder buildOperatingSystem() {
        computer.setOperatingSystem("Windows 11 Home");
        return this;
    }

    @Override
    public ComputerBuilder buildAccessories() {
        computer.setAccessories("Standard keyboard and mouse, 24\" office monitor");
        return this;
    }

    @Override
    public Computer getResult() {
        return computer;
    }
}
