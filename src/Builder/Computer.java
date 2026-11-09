package Builder;

/**
 * Product: a fully assembled computer configuration.
 */
public class Computer {
    private String processor;
    private int ramGb;
    private String hardDrive;
    private String graphicsCard;
    private String operatingSystem;
    private String accessories;

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public void setRamGb(int ramGb) {
        this.ramGb = ramGb;
    }

    public void setHardDrive(String hardDrive) {
        this.hardDrive = hardDrive;
    }

    public void setGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public void setAccessories(String accessories) {
        this.accessories = accessories;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Computer configuration:\n");
        sb.append("  Processor       : ").append(processor).append('\n');
        sb.append("  RAM             : ").append(ramGb).append(" GB\n");
        sb.append("  Hard drive      : ").append(hardDrive).append('\n');
        sb.append("  Graphics card   : ").append(graphicsCard).append('\n');
        sb.append("  Operating system: ").append(operatingSystem).append('\n');
        if (accessories != null && !accessories.isBlank()) {
            sb.append("  Accessories     : ").append(accessories).append('\n');
        }
        return sb.toString();
    }
}
