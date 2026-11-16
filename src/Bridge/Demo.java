package Bridge;

import Bridge.devices.Device;
import Bridge.devices.Radio;
import Bridge.devices.Tv;
import Bridge.devices.SmartTV;
import Bridge.remotes.AdvancedRemote;
import Bridge.remotes.BasicRemote;
import Bridge.remotes.SmartRemote;

public class Demo {
    public static void main(String[] args) {
        System.out.println("=== Testing Original Devices ===");
        testDevice(new Tv());
        testDevice(new Radio());

        System.out.println("\n=== Testing Extended SmartTV ===");
        testSmartDevice(new SmartTV());
    }

    public static void testDevice(Device device) {
        System.out.println("Tests with basic remote.");
        BasicRemote basicRemote = new BasicRemote(device);
        basicRemote.power();
        device.printStatus();

        System.out.println("Tests with advanced remote.");
        AdvancedRemote advancedRemote = new AdvancedRemote(device);
        advancedRemote.power();
        advancedRemote.mute();
        device.printStatus();
    }

    public static void testSmartDevice(Device device) {
        System.out.println("Tests with smart remote.");
        SmartRemote smartRemote = new SmartRemote(device);
        smartRemote.power();
        smartRemote.voiceControl("browse internet");
        device.printStatus();

        System.out.println("Testing voice control for mute.");
        smartRemote.voiceControl("mute");
        device.printStatus();
    }
}
