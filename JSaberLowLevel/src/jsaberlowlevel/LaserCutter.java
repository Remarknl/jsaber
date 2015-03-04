package jsaberlowlevel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;
import jsaberlowlevel.instructions.Homer;
import jsaberlowlevel.instructions.Instruction;
import jsaberlowlevel.instructions.Move;

public class LaserCutter implements AutoCloseable {
    private static final int X_DIRECTION_PIN_NUMBER = 0;
    private static final int Y_DIRECTION_PIN_NUMBER = 0;  
    private static final int X_STEP_PIN_NUMBER = 0;
    private static final int Y_STEP_PIN_NUMBER = 0;
    private static final int ENABLE_PIN_NUMBER = 0;
    private static final int X_ENDSTOP_PIN_NUMBER = 0;
    private static final int Y_ENDSTOP_PIN_NUMBER = 0;
    private static final int ABORT_PIN_NUMBER = 0;
    private static final int PAUSE_PIN_NUMBER = 0;
    private static final int START_PIN_NUMBER = 0;
    private static final int R_V_SWITCH_PIN_NUMBER = 0;
    
    public static final int STEPS_PER_MM = 160;
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

    static void init() {
        init_pins();
        try {
            LinkedTransferQueue.addInstruction((Instruction)new Homer());
        } catch (IOException ex) {
            Logger.getLogger(LaserCutter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void init_pins() {
        
        try {
            X_DIRECTION_PIN = DeviceManager.open(new GPIOPinConfig(DeviceConfig.DEFAULT, X_DIRECTION_PIN_NUMBER, GPIOPinConfig.DIR_OUTPUT_ONLY, DeviceConfig.DEFAULT, GPIOPinConfig.TRIGGER_NONE, false));
            Y_DIRECTION_PIN = DeviceManager.open(new GPIOPinConfig(DeviceConfig.DEFAULT, Y_DIRECTION_PIN_NUMBER, GPIOPinConfig.DIR_OUTPUT_ONLY, DeviceConfig.DEFAULT, GPIOPinConfig.TRIGGER_NONE, false));
            X_STEP_PIN = DeviceManager.open(new GPIOPinConfig(DeviceConfig.DEFAULT, X_STEP_PIN_NUMBER, GPIOPinConfig.DIR_OUTPUT_ONLY, DeviceConfig.DEFAULT, GPIOPinConfig.TRIGGER_NONE, false));
            Y_STEP_PIN = DeviceManager.open(new GPIOPinConfig(DeviceConfig.DEFAULT, Y_STEP_PIN_NUMBER, GPIOPinConfig.DIR_OUTPUT_ONLY, DeviceConfig.DEFAULT, GPIOPinConfig.TRIGGER_NONE, false));
            ENABLE_PIN = DeviceManager.open(new GPIOPinConfig(DeviceConfig.DEFAULT, ENABLE_PIN_NUMBER,GPIOPinConfig.DIR_OUTPUT_ONLY, DeviceConfig.DEFAULT, GPIOPinConfig.TRIGGER_NONE, false));
            X_ENDSTOP_PIN = DeviceManager.open(new GPIOPinConfig(DeviceConfig.DEFAULT, X_ENDSTOP_PIN_NUMBER,GPIOPinConfig.DIR_INPUT_ONLY, GPIOPinConfig.MODE_INPUT_PULL_DOWN, GPIOPinConfig.TRIGGER_RISING_EDGE, false));
            Y_ENDSTOP_PIN = DeviceManager.open(new GPIOPinConfig(DeviceConfig.DEFAULT, Y_ENDSTOP_PIN_NUMBER,GPIOPinConfig.DIR_INPUT_ONLY, GPIOPinConfig.MODE_INPUT_PULL_DOWN, GPIOPinConfig.TRIGGER_RISING_EDGE, false));
            ABORT_PIN = DeviceManager.open(new GPIOPinConfig(DeviceConfig.DEFAULT, ABORT_PIN_NUMBER,GPIOPinConfig.DIR_INPUT_ONLY, GPIOPinConfig.MODE_INPUT_PULL_DOWN, GPIOPinConfig.TRIGGER_RISING_EDGE, false));
            PAUSE_PIN = DeviceManager.open(new GPIOPinConfig(DeviceConfig.DEFAULT, PAUSE_PIN_NUMBER,GPIOPinConfig.DIR_INPUT_ONLY, GPIOPinConfig.MODE_INPUT_PULL_DOWN, GPIOPinConfig.TRIGGER_RISING_EDGE, false));
            START_PIN = DeviceManager.open(new GPIOPinConfig(DeviceConfig.DEFAULT, START_PIN_NUMBER,GPIOPinConfig.DIR_INPUT_ONLY, GPIOPinConfig.MODE_INPUT_PULL_DOWN, GPIOPinConfig.TRIGGER_RISING_EDGE, false));
            R_V_SWITCH_PIN = DeviceManager.open(new GPIOPinConfig(DeviceConfig.DEFAULT, R_V_SWITCH_PIN_NUMBER,GPIOPinConfig.DIR_INPUT_ONLY, GPIOPinConfig.MODE_INPUT_PULL_DOWN, GPIOPinConfig.TRIGGER_RISING_EDGE, false));
        } catch (IOException ex) {
            Logger.getLogger(LaserCutter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void hold(long time) {
        long initTime = System.nanoTime();
        while (System.nanoTime() < initTime + time) {
        }
    }

    public static long getOffTime(int i) {
        
        return 0-PULSE_TIME;
    }
    
    @Override
    public void close() {
        GPIOPin[] pinArray = {X_DIRECTION_PIN, Y_DIRECTION_PIN, X_STEP_PIN, Y_STEP_PIN, ENABLE_PIN, X_ENDSTOP_PIN, Y_ENDSTOP_PIN, ABORT_PIN, PAUSE_PIN, START_PIN, R_V_SWITCH_PIN};
        for(GPIOPin pin : pinArray) {
            try {
                pin.setValue(false);
                pin.close();
            } catch (IOException ex) {
                System.out.println("Exception closing resources: " + ex);
            }
        }
        
    }
}
