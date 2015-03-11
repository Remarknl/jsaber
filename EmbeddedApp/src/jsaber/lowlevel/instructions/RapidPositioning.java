package jsaber.lowlevel.instructions;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsaber.lowlevel.LaserCutter;

public class RapidPositioning extends MoveStep {

    private long X_OFFSET;
    private long Y_OFFSET;

    public RapidPositioning(long X_OFFSET, long Y_OFFSET) {
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
            while (alongX || alongY) {
                if (alongX) {
                    LaserCutter.X_STEP_PIN.setValue(this.xDirection);
                    LaserCutter.X_STEP_PIN.setValue(true);
                }
                if (alongY) {
                    LaserCutter.Y_DIRECTION_PIN.setValue(this.yDirection);
                    LaserCutter.Y_STEP_PIN.setValue(true);
                }

                LaserCutter.hold(LaserCutter.PULSE_TIME);

                if (alongX) {
                    LaserCutter.X_STEP_PIN.setValue(false);
                }
                if (alongY) {
                    LaserCutter.Y_STEP_PIN.setValue(false);
                }
                LaserCutter.hold(LaserCutter.getOffTime(feedrate));
                
                LaserCutter.setX_OFFSET(X_OFFSET + 1);

                if (X_OFFSET == LaserCutter.getX_OFFSET()) {
                    alongX = false;
                }

                if (Y_OFFSET == LaserCutter.getY_OFFSET()) {
                    alongY = false;

                }
            }
            LaserCutter.hold(LaserCutter.getOffTime(feedrate));
        } catch (IOException ex) {
            Logger.getLogger(MoveStep.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
