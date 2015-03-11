package jsaber.lowlevel;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.dio.gpio.GPIOPin;
import jsaber.lowlevel.instructions.Homer;
import jsaber.lowlevel.instructions.Instruction;

public class LaserCutter implements AutoCloseable {

    private static final int X_DIRECTION_PIN_NUMBER = 4;
    private static final int Y_DIRECTION_PIN_NUMBER = 24;
    private static final int X_STEP_PIN_NUMBER = 22;
    private static final int Y_STEP_PIN_NUMBER = 17;
    private static final int ENABLE_PIN_NUMBER = 23;

    private static final int X_ENDSTOP_PIN_NUMBER = 26;
    private static final int Y_ENDSTOP_PIN_NUMBER = 16;
    private static final int ABORT_PIN_NUMBER = 13;
    private static final int PAUSE_PIN_NUMBER = 19;
    private static final int START_PIN_NUMBER = 6;
    private static final int R_V_SWITCH_PIN_NUMBER = 16;

    public static final int STEPS_PER_MM = 160;
    public static final int MM_PER_STEP_TIMES_10000 = 62; // gelijk gemaakt aan de gcode welke 4 cijfers achter de komma heeft
    public static final int ACCELLERATION = 50; // mm/sec^2
    public static final int DEFAULT_FEED_RATE = 500; // mm/min
    public static final int DEFAULT_SEEK_RATE = 500; // mm/min
    public static final String GCODE_DIRECTORY = "./";

    public static int PULSE_TIME = 1500; // nanoseconds

    private static long X_OFFSET = 0;
    private static long Y_OFFSET = 0;
    private static long BED_WIDTH = 0;
    private static long BED_HEIGHT = 0;

    public static GPIOPin X_DIRECTION_PIN;
    public static GPIOPin Y_DIRECTION_PIN;
    public static GPIOPin X_STEP_PIN;
    public static GPIOPin Y_STEP_PIN;
    public static GPIOPin ENABLE_PIN;
    public static GPIOPin X_ENDSTOP_PIN;
    public static GPIOPin Y_ENDSTOP_PIN;
    public static GPIOPin ABORT_PIN;
    public static GPIOPin PAUSE_PIN;
    public static GPIOPin START_PIN;
    public static GPIOPin R_V_SWITCH_PIN;

    static void init() {
        init_pins();
        LinkedTransferQueue.addInstruction((Instruction) new Homer());
    }

    private static void init_pins() {
        try {

            //Outgoing Pins
            X_DIRECTION_PIN = new PinOutConfiguration(X_DIRECTION_PIN_NUMBER).getGPIOPin();
            Y_DIRECTION_PIN = new PinOutConfiguration(Y_DIRECTION_PIN_NUMBER).getGPIOPin();
            X_STEP_PIN = new PinOutConfiguration(X_STEP_PIN_NUMBER).getGPIOPin();
            Y_STEP_PIN = new PinOutConfiguration(Y_STEP_PIN_NUMBER).getGPIOPin();
            ENABLE_PIN = new PinOutConfiguration(ENABLE_PIN_NUMBER).getGPIOPin();

            //Incoming Pins
            X_ENDSTOP_PIN = new PinInConfiguration(X_ENDSTOP_PIN_NUMBER).getGPIOPin();
            Y_ENDSTOP_PIN = new PinInConfiguration(Y_ENDSTOP_PIN_NUMBER).getGPIOPin();
            ABORT_PIN = new PinInConfiguration(ABORT_PIN_NUMBER).getGPIOPin();
            PAUSE_PIN = new PinInConfiguration(PAUSE_PIN_NUMBER).getGPIOPin();
            START_PIN = new PinInConfiguration(START_PIN_NUMBER).getGPIOPin();
            R_V_SWITCH_PIN = new PinInConfiguration(R_V_SWITCH_PIN_NUMBER).getGPIOPin();
        } catch (IOException ex) {
            Logger.getLogger(LaserCutter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isJobActive() {
        return false;
    }

    public static long getX_OFFSET() {
        return X_OFFSET;
    }

    public static void setX_OFFSET(long X_OFFSET) {
        LaserCutter.X_OFFSET = X_OFFSET;
    }

    public static long getY_OFFSET() {
        return Y_OFFSET;
    }

    public static void setY_OFFSET(long Y_OFFSET) {
        LaserCutter.Y_OFFSET = Y_OFFSET;
    }

    public static long getBED_WIDTH() {
        return BED_WIDTH;
    }

    public static void setBED_WIDTH(long BED_WIDTH) {
        LaserCutter.BED_WIDTH = BED_WIDTH;
    }

    public static long getBED_HEIGHT() {
        return BED_HEIGHT;
    }

    public static void setBED_HEIGHT(long BED_HEIGHT) {
        LaserCutter.BED_HEIGHT = BED_HEIGHT;
    }

    public static void hold(long time) {
        long initTime = System.nanoTime();
        while (System.nanoTime() < initTime + time) {
        }
    }

    public static long getOffTime(int feedrate) {
        long stepsPerSecond = feedrate * STEPS_PER_MM / 60;
        return 1000_000_000 / stepsPerSecond - PULSE_TIME * stepsPerSecond;
    }
    
    

    @Override
    public void close() {
        GPIOPin[] pinArray = {X_DIRECTION_PIN, Y_DIRECTION_PIN, X_STEP_PIN, Y_STEP_PIN, ENABLE_PIN, X_ENDSTOP_PIN, Y_ENDSTOP_PIN, ABORT_PIN, PAUSE_PIN, START_PIN, R_V_SWITCH_PIN};
        for (GPIOPin pin : pinArray) {
            try {
                pin.setValue(false);
                pin.close();
            } catch (IOException ex) {
                System.out.println("Exception closing resources: " + ex);
            }
        }

    }
}
