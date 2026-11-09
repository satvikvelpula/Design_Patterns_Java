package Builder;

/**
 * Concrete builder for a high-end gaming PC.
 */
public class GamingComputerBuilder implements ComputerBuilder {
    private final Computer computer = new Computer();

    @Override
    public ComputerBuilder buildProcessor() {
        computer.setProcessor("AMD Ryzen 9 7950X");
        return this;
    }

    @Override
    public ComputerBuilder buildRAM() {
        computer.setRamGb(32);
        return this;
    }

    @Override
    public ComputerBuilder buildHardDrive() {
        computer.setHardDrive("2 TB NVMe SSD");
        return this;
    }

    @Override
    public ComputerBuilder buildGraphicsCard() {
        computer.setGraphicsCard("NVIDIA GeForce RTX 4080");
        return this;
    }

    @Override
    public ComputerBuilder buildOperatingSystem() {
        computer.setOperatingSystem("Windows 11 Pro");
        return this;
    }

    @Override
    public ComputerBuilder buildAccessories() {
        computer.setAccessories("RGB keyboard, gaming mouse, 144 Hz monitor");
        return this;
    }

    @Override
    public Computer getResult() {
        return computer;
    }
}
