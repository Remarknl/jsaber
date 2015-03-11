/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsaber.lowlevel.instructions;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.dio.gpio.GPIOPin;
import jsaber.lowlevel.LaserCutter;

/**
 *
 * @author rbe20795
 */
public class LaserLine extends MoveStep {

    private long X_OFFSET;
    private long Y_OFFSET;

    public LaserLine(long X_OFFSET, long Y_OFFSET) {
        this.X_OFFSET = X_OFFSET;
        this.Y_OFFSET = Y_OFFSET;

        //Setting direction and along which axis the laser is going to move
        if (X_OFFSET != LaserCutter.getX_OFFSET()) {
            along(Axis.X);
            if (X_OFFSET >= LaserCutter.getX_OFFSET()) {
                setDirection(Axis.X, true);
            } else {
                setDirection(Axis.X, false);
            }
        }
        if (Y_OFFSET != LaserCutter.getY_OFFSET()) {
            along(Axis.Y);
            if (X_OFFSET >= LaserCutter.getX_OFFSET()) {
                setDirection(Axis.X, true);
            } else {
                setDirection(Axis.X, false);
            }
        }
        //Sets the speed at which the laser is moving
        setFeedrate(LaserCutter.DEFAULT_FEED_RATE);
    }

    public void execute() {
        try {
            
            double X_OFFSET_DIFFERENCE = Math.abs(X_OFFSET - LaserCutter.getX_OFFSET());
            double Y_OFFSET_DIFFERENCE = Math.abs(Y_OFFSET - LaserCutter.getY_OFFSET());

            if (X_OFFSET_DIFFERENCE > Y_OFFSET_DIFFERENCE) {
                LoopingSteps(X_OFFSET_DIFFERENCE, Y_OFFSET_DIFFERENCE, LaserCutter.X_STEP_PIN, LaserCutter.X_DIRECTION_PIN);
            }
            if (Y_OFFSET_DIFFERENCE > X_OFFSET_DIFFERENCE){
                LoopingSteps(Y_OFFSET_DIFFERENCE, X_OFFSET_DIFFERENCE, LaserCutter.Y_STEP_PIN, LaserCutter.Y_DIRECTION_PIN);
            }
        } catch (IOException ex) {
            Logger.getLogger(MoveStep.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public <A, B> void LoopingSteps(double A, double B, GPIOPin STEP_PIN, GPIOPin DIRECTION_PIN) throws IOException {
        double ratio = A / B;
        double StepsToDo = ratio;

        while (alongX || alongY) {
            long tempSteps = Math.round(StepsToDo);
            moveSteps(tempSteps, STEP_PIN, DIRECTION_PIN);

            //adds decimal leftover to the ratio
            //so you use a relative amount of steps during you're run
            StepsToDo -= tempSteps + ratio;

            moveSteps(1, STEP_PIN, DIRECTION_PIN);

            if (X_OFFSET == LaserCutter.getX_OFFSET()) {
                alongX = false;
            }

            if (Y_OFFSET == LaserCutter.getY_OFFSET()) {
                alongY = false;
            }
        }
    }

    public void moveSteps(long steps, GPIOPin STEP_PIN, GPIOPin DIRECTION_PIN) throws IOException {
        for (int i = 0; i < steps; i ++){
            
            STEP_PIN.setValue(true);
            LaserCutter.hold(LaserCutter.PULSE_TIME);
            STEP_PIN.setValue(false);
            LaserCutter.hold(LaserCutter.getOffTime(feedrate));
        }
    }
}
