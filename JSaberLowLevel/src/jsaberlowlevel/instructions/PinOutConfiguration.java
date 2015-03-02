package jsaberlowlevel.instructions;

import java.io.IOException;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;

public class PinOutConfiguration {
    private GPIOPin pin;
    private final int PIN_NUMBER;
    public PinOutConfiguration(int pinNumber) throws IOException{
        PIN_NUMBER = pinNumber;
        pin = DeviceManager.open(getPinGPIOPinConfig());
    }
    
    private GPIOPinConfig getPinGPIOPinConfig(){
    GPIOPinConfig pinConfig = new GPIOPinConfig(
                DeviceConfig.DEFAULT,
                PIN_NUMBER,
                GPIOPinConfig.DIR_OUTPUT_ONLY,
                DeviceConfig.DEFAULT,
                GPIOPinConfig.TRIGGER_NONE,
                false
                );
    return pinConfig;
    }
    
    public GPIOPin getGPIOPin(){
        return pin;
    }
}
