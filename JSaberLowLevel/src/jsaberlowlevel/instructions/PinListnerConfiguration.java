package jsaberlowlevel.instructions;

import java.io.IOException;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;

public class PinListnerConfiguration {
    private GPIOPin pin;
    private final int PIN_NUMBER;
    public PinListnerConfiguration(int pinNumber) throws IOException{
        PIN_NUMBER = pinNumber;
        pin = DeviceManager.open(getPinGPIOPinConfig());
    }
    
    private GPIOPinConfig getPinGPIOPinConfig(){
    GPIOPinConfig pinConfig = new GPIOPinConfig(
                DeviceConfig.DEFAULT,
                PIN_NUMBER,
                GPIOPinConfig.DIR_INPUT_ONLY,
                GPIOPinConfig.MODE_INPUT_PULL_DOWN,
                GPIOPinConfig.TRIGGER_RISING_EDGE,
                false
                );
    return pinConfig;
    }
    
    public GPIOPin getGPIOPin(){
        return pin;
    }
}
