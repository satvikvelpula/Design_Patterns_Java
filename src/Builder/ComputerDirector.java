package Builder;

/**
 * Director: uses a {@link ComputerBuilder} to assemble a complete computer.
 */
public class ComputerDirector {
    private ComputerBuilder builder;

    public void setBuilder(ComputerBuilder builder) {
        this.builder = builder;
    }

    public Computer constructComputer() {
        if (builder == null) {
            throw new IllegalStateException("No ComputerBuilder set");
        }
        builder.buildProcessor()
                .buildRAM()
                .buildHardDrive()
                .buildGraphicsCard()
                .buildOperatingSystem()
                .buildAccessories();
        return builder.getResult();
    }
}
