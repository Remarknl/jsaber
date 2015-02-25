package pitestapplication;

import java.io.IOException;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;

public class PinPulseExample implements AutoCloseable{
    private GPIOPin pin;
    private final int PIN_NUMBER;
    private final int TIMES;
    private final int ON_TIME;
    private final int OFF_TIME;
    
    public PinPulseExample(int pinNumber, int times, int onTime, int offTime) {
        this.PIN_NUMBER = pinNumber;
        this.TIMES = times;
        this.ON_TIME = onTime;
        this.OFF_TIME = offTime;
    }
    
    public void start() throws IOException {

        GPIOPinConfig pinConfig = new GPIOPinConfig(
                DeviceConfig.DEFAULT,
                PIN_NUMBER,
                GPIOPinConfig.DIR_OUTPUT_ONLY,
                DeviceConfig.DEFAULT,
                GPIOPinConfig.TRIGGER_NONE,
                false
                );

        pin = DeviceManager.open(pinConfig);
        
        for(int i = 0; i < TIMES; i++) {
            pin.setValue(true);
            hold(ON_TIME);
            pin.setValue(false);
            hold(OFF_TIME);
        }
        
        
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
    
    // alternatief voor Thread.sleep dat als het goed is preciezer is.
    // het houdt wel de cpu vast.
    private void hold(long time) {
        long initTime = System.nanoTime();
        while(System.nanoTime() < initTime + time) {}
    }
}


