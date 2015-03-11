package jsaber.lowlevel.instructions;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsaber.lowlevel.LaserCutter;

public class LaserCircularClockwise extends MoveStep {
    private long XCoordinate;
    private long YCoordinate;
    
    public LaserCircularClockwise(long XCoordinate, long YCoordinate) {
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        
    }
    
    public void execute(){
        try {
            if (alongX) LaserCutter.X_STEP_PIN.setValue(true);
            if (alongY) LaserCutter.Y_STEP_PIN.setValue(true);
            
            LaserCutter.hold(LaserCutter.PULSE_TIME);
            
            if (alongX) {LaserCutter.X_STEP_PIN.setValue(false); alongX = false;}
            if (alongY) {LaserCutter.Y_STEP_PIN.setValue(false); alongY = false;}
            LaserCutter.hold(LaserCutter.getOffTime(feedrate));
        } catch (IOException ex) {
            Logger.getLogger(MoveStep.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
