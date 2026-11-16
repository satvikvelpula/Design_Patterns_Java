package Bridge.remotes;

import Bridge.devices.Device;
import Bridge.devices.SmartTV;

public class SmartRemote extends BasicRemote {
    public SmartRemote(Device device) {
        super.device = device;
    }

    public void voiceControl(String command) {
        System.out.println("SmartRemote: Voice command received: " + command);
        if (device instanceof SmartTV) {
            SmartTV smartTV = (SmartTV) device;
            if (command.toLowerCase().contains("browse") || command.toLowerCase().contains("internet")) {
                smartTV.browseInternet();
            } else if (command.toLowerCase().contains("mute")) {
                device.setVolume(0);
            } else if (command.toLowerCase().contains("volume up")) {
                device.setVolume(device.getVolume() + 10);
            } else if (command.toLowerCase().contains("volume down")) {
                device.setVolume(device.getVolume() - 10);
            }
        }
    }
}
