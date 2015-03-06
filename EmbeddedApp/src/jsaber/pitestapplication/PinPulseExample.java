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
import jdk.dio.gpio.GPIOPin;

public class PinPulseExample implements AutoCloseable {

    private final int TIMES;
    private final int ON_TIME;
    private final int OFF_TIME;
    
    private GPIOPin pin1;
    private GPIOPin pin2;
    private GPIOPin pin3;
    private GPIOPin pin4;
    private GPIOPin pin5;
    private GPIOPin pin6;
    private GPIOPin pin7;
    private GPIOPin pin8;

    
    // GPIOPin 4, 17, 18, 27, 22, 5, 24, 25 can be used
    public PinPulseExample(int times, int onTime, int offTime) {
        this.TIMES = times;
        this.ON_TIME = onTime;
        this.OFF_TIME = offTime;
    }

    public void start() throws IOException {
        pin1 = new PinConfiguration(4).getGPIOPin();
        pin2 = new PinConfiguration(17).getGPIOPin();
        pin3 = new PinConfiguration(18).getGPIOPin();
        pin4 = new PinConfiguration(27).getGPIOPin();
        pin5 = new PinConfiguration(22).getGPIOPin();
        pin6 = new PinConfiguration(5).getGPIOPin();
        pin7 = new PinConfiguration(24).getGPIOPin();
        pin8 = new PinConfiguration(25).getGPIOPin();

        for (int i = 0; i < TIMES; i++) {
            pin1.setValue(true);
            pin2.setValue(true);
            pin3.setValue(true);
            pin4.setValue(true);
            pin5.setValue(true);
            pin6.setValue(true);
            pin7.setValue(true);
            pin8.setValue(true);
            hold(ON_TIME);
            pin1.setValue(false);
            pin2.setValue(false);
            pin3.setValue(false);
            pin4.setValue(false);
            pin5.setValue(false);
            pin6.setValue(false);
            pin7.setValue(false);
            pin8.setValue(false);
            hold(OFF_TIME);
        }

    }

    // ik vraag me af of dit absoluut nodig is..
    @Override
    public void close() {
        try {
            if (pin1 != null) {
                pin1.setValue(false);
                pin1.close();
            }
            if (pin2 != null) {
                pin2.setValue(false);
                pin2.close();
            }
            if (pin3 != null) {
                pin3.setValue(false);
                pin3.close();
            }
            if (pin4 != null) {
                pin4.setValue(false);
                pin4.close();
            }
            if (pin5 != null) {
                pin5.setValue(false);
                pin5.close();
            }
            if (pin6 != null) {
                pin6.setValue(false);
                pin6.close();
            }
            if (pin7 != null) {
                pin7.setValue(false);
                pin7.close();
            }
            if (pin8 != null) {
                pin8.setValue(false);
                pin8.close();
            }
        } catch (IOException ex) {
            System.out.println("Exception closing resources: " + ex);
        }
    }

    // alternatief voor Thread.sleep dat als het goed is preciezer is.
    // het houdt wel de cpu vast.
    private void hold(long time) {
        long initTime = System.nanoTime();
        while (System.nanoTime() < initTime + time) {
        }
    }
}

