package jsaber.lowlevel.instructions;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsaber.lowlevel.LaserCutter;

/**
 *
 * @author rgi20802
 */
public class MoveStep implements Instruction {
    
    boolean xDirection = false;
    boolean yDirection = false;
    boolean alongX = false;
    boolean alongY = false;
    int feedrate = 0;
    
    @Override
    public void execute() {
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

    public void setDirection(Axis axis, boolean i) {
        if (axis == Axis.X) xDirection = i;
        if (axis == Axis.Y) yDirection = i;
    }

    void setFeedrate(int feedrate) {
        this.feedrate = feedrate;
    }

    void along(Axis axis) {
        if (axis == Axis.X) alongX = true;
        if (axis == Axis.Y) alongY = true;
    }
    
    void along(Axis axis, Axis axis2) {
        if (axis == Axis.X) alongX = true;
        if (axis == Axis.Y) alongY = true;
        if (axis2 == Axis.X) alongX = true;
        if (axis2 == Axis.Y) alongY = true;
    }
}
