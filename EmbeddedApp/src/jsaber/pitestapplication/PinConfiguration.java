/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsaber.pitestapplication;

/**
 *
 * @author rru20841
 */


import java.io.IOException;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;


public class PinConfiguration {
    private GPIOPin pin;
    private final int PIN_NUMBER;
    public PinConfiguration(int pinNumber) throws IOException{
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

