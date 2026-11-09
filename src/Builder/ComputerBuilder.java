package Builder;

/**
 * Builder interface: step-by-step assembly of a {@link Computer}.
 */
public interface ComputerBuilder {
    ComputerBuilder buildProcessor();

    ComputerBuilder buildRAM();

    ComputerBuilder buildHardDrive();

    ComputerBuilder buildGraphicsCard();

    ComputerBuilder buildOperatingSystem();

    /** Optional accessories / grade extras (challenge). */
    ComputerBuilder buildAccessories();

    Computer getResult();
}
