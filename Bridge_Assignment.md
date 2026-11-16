# Bridge Pattern Implementation Report

**Student:** Satvi  
**Date:** November 16, 2025  
**Course:** OTP2 Assignment 3

---

## Table of Contents

1. [Introduction](#1-introduction)
2. [Original Implementation](#2-original-implementation)
3. [New Functionality](#3-new-functionality)
4. [Implementation Details](#4-implementation-details)
5. [Verification](#5-verification)
6. [Conclusion](#6-conclusion)

---

## 1. Introduction

The Bridge pattern is a structural design pattern that divides business logic or a huge class into separate class hierarchies that can be developed independently. The key idea is to separate abstraction from implementation, allowing both to vary independently.

**Reference Implementation:** [Refactoring.Guru Bridge Pattern Example](https://refactoring.guru/design-patterns/bridge/java/example)

The original implementation demonstrates the Bridge pattern using a system with **devices** (Radio, TV) and **remotes** (BasicRemote, AdvancedRemote). The abstraction (Remote) is separated from the implementation (Device), allowing new devices and remotes to be added independently.

## 2. Original Implementation

The base implementation includes:

### Device Hierarchy
- **Device Interface**: Common interface for all devices with methods:
  - `isEnabled()`: Check if device is on
  - `enable()` / `disable()`: Turn device on/off
  - `getVolume()` / `setVolume(int percent)`: Control volume
  - `getChannel()` / `setChannel(int channel)`: Control channel
  - `printStatus()`: Display device status

- **Concrete Devices**:
  - `Radio`: Implements Device interface for radio
  - `Tv`: Implements Device interface for television

### Remote Hierarchy
- **Remote Interface**: Common interface for all remotes with methods:
  - `power()`: Toggle power
  - `volumeDown()` / `volumeUp()`: Adjust volume
  - `channelDown()` / `channelUp()`: Change channel

- **Concrete Remotes**:
  - `BasicRemote`: Basic remote control functionality
  - `AdvancedRemote`: Extends BasicRemote with `mute()` method

- **Demo Class**: Demonstrates the pattern by testing devices with different remotes

## 3. New Functionality

### Extended Device: SmartTV

Added a new device class `SmartTV` that extends the Device interface with additional functionality:

- `browseInternet()`: Method to toggle internet browsing capability
- Enhanced `printStatus()`: Shows internet connection status
- Maintains all standard device functionality (power, volume, channel)

### Extended Remote: SmartRemote

Added a new remote class `SmartRemote` that extends BasicRemote with:

- `voiceControl(String command)`: Method to control devices using voice commands
- Supports commands like "browse internet", "mute", "volume up", "volume down"
- Intelligent command parsing for SmartTV-specific features

## 4. Implementation Details

### SmartTV.java

```java
package Bridge.devices;

public class SmartTV implements Device {
    private boolean on = false;
    private int volume = 30;
    private int channel = 1;
    private boolean internetConnected = false;

    @Override
    public boolean isEnabled() {
        return on;
    }

    @Override
    public void enable() {
        on = true;
    }

    @Override
    public void disable() {
        on = false;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void setVolume(int volume) {
        if (volume > 100) {
            this.volume = 100;
        } else if (volume < 0) {
            this.volume = 0;
        } else {
            this.volume = volume;
        }
    }

    @Override
    public int getChannel() {
        return channel;
    }

    @Override
    public void setChannel(int channel) {
        this.channel = channel;
    }

    public void browseInternet() {
        internetConnected = !internetConnected;
        System.out.println("SmartTV: Internet browsing " + 
                          (internetConnected ? "enabled" : "disabled"));
    }

    @Override
    public void printStatus() {
        System.out.println("------------------------------------");
        System.out.println("| I'm Smart TV.");
        System.out.println("| I'm " + (on ? "enabled" : "disabled"));
        System.out.println("| Current volume is " + volume + "%");
        System.out.println("| Current channel is " + channel);
        System.out.println("| Internet: " + 
                         (internetConnected ? "connected" : "disconnected"));
        System.out.println("------------------------------------\n");
    }
}
```

### SmartRemote.java

```java
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
            if (command.toLowerCase().contains("browse") || 
                command.toLowerCase().contains("internet")) {
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
```

## 5. Verification

The implementation was tested using the updated Demo class:

```java
System.out.println("=== Testing Original Devices ===");
testDevice(new Tv());
testDevice(new Radio());

System.out.println("\n=== Testing Extended SmartTV ===");
testSmartDevice(new SmartTV());
```

**Verification Results:**
- ✅ Original devices (Radio, TV) work with all remotes
- ✅ SmartTV can be controlled with BasicRemote and AdvancedRemote
- ✅ SmartRemote works with all devices
- ✅ Voice control correctly interprets commands
- ✅ SmartTV-specific features (internet browsing) work correctly
- ✅ Bridge pattern maintained: Device and Remote hierarchies remain independent

## 6. Conclusion

The Bridge pattern implementation was successfully extended with new functionality while maintaining the separation of concerns between the Device and Remote hierarchies.

**Design Decisions:**
- Used instance checking in SmartRemote to handle SmartTV-specific commands
- Maintained backward compatibility with existing devices and remotes
- Followed the existing code style and patterns from the refactoring.guru example

---

## File Structure

```
src/Bridge/
├── Demo.java
├── devices/
│   ├── Device.java         # Device interface
│   ├── Radio.java          # Radio device
│   ├── Tv.java             # TV device
│   └── SmartTV.java        # Extended SmartTV device
└── remotes/
    ├── Remote.java         # Remote interface
    ├── BasicRemote.java    # Basic remote
    ├── AdvancedRemote.java # Advanced remote with mute
    └── SmartRemote.java    # Extended smart remote with voice control
```

---

## How to Run

```bash
cd /Users/satvi/DES/DesignPatterns
javac -d target src/Bridge/devices/*.java src/Bridge/remotes/*.java src/Bridge/Demo.java
java -cp target Bridge.Demo
```

---

## References

1. Refactoring.Guru - Bridge Pattern: https://refactoring.guru/design-patterns/bridge/java/example
2. Assignment - Bridge Pattern: https://github.com/vesavvo/design_patterns/blob/master/markdown/assignments/bridge.md
