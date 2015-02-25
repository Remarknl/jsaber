package pitestapplication;

import java.io.IOException;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;
import jdk.dio.gpio.PinEvent;
import jdk.dio.gpio.PinListener;

public class ButtonExample implements AutoCloseable, PinListener {
    private GPIOPin pin;
    private final int PIN_NUMBER;
    
    public ButtonExample(int pinNumber) {
        this.PIN_NUMBER = pinNumber;
    }
    
    public void start() throws IOException {

        GPIOPinConfig pinConfig = new GPIOPinConfig(
                DeviceConfig.DEFAULT,
                PIN_NUMBER,
                GPIOPinConfig.DIR_INPUT_ONLY,
                GPIOPinConfig.MODE_INPUT_PULL_DOWN,
                GPIOPinConfig.TRIGGER_RISING_EDGE,
                false
                );

        pin = DeviceManager.open(pinConfig);
        pin.setInputListener(this);
                      
    }

    // ik vraag me af of dit absoluut nodig is..
    @Override
    public void close() {
        try {
            if (pin != null) {
                pin.setValue(false);
                pin.close();
            }
        } catch (IOException ex) {
            System.out.println("Exception closing resources: " + ex);
        }
    }

    @Override
    public void valueChanged(PinEvent event) {
        System.out.println("Button changed: " + event.getValue());
    }
}
