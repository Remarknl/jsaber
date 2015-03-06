/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsaber.lowlevel;

/**
 *
 * @author rru20841
 */

import jsaber.lowlevel.instructions.Move;
public class LaserCutter {
    public static final int X_DIRECTION_PIN_NUMBER = 0;
    public static final int Y_DIRECTION_PIN_NUMBER = 0;  
    public static final int X_STEP_PIN_NUMBER = 0;
    public static final int Y_STEP_PIN_NUMBER = 0;
    public static final int ENABLE_PIN_NUMBER = 0;
    public static final int X_ENDSTOP_PIN_NUMBER = 0;
    public static final int Y_ENDSTOP_PIN_NUMBER = 0;
    public static final int ABORT_PIN_NUMBER = 0;
    public static final int PAUSE_PIN_NUMBER = 0;
    public static final int START_PIN_NUMBER = 0;
    public static final int R_V_SWITCH_PIN_NUMBER = 0;
    
    public static final int STEPS_PER_MM = 160;
    public static final int ACCELLERATION = 50; // mm/sec^2
    public static final int DEFAULT_FEED_RATE = 500; // mm/min
    public static final int DEFAULT_SEEK_RATE = 500; // mm/min
    public static final String GCODE_DIRECTORY = "./";
    
    private static long X_OFFSET = 0;
    private static long Y_OFFSET = 0;
    private static long BED_WIDTH = 0;
    private static long BED_HEIGHT = 0;

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
        LinkedTransferQueue.addInstruction(new Move(LaserCutter.X_OFFSET,LaserCutter.Y_OFFSET));
    }
}

