package jsaber.lowlevel.instructions;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.dio.gpio.PinEvent;
import jdk.dio.gpio.PinListener;
import jsaber.lowlevel.LaserCutter;

public class Homer implements PinListener, Instruction {
    boolean inputListener = true;
    
    @Override
    public void execute() {
        try {
            homeX();
            homeY();
        } catch (IOException ex) {
            Logger.getLogger(Homer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void valueChanged(PinEvent event) {
        inputListener = false;
    }

    private void homeX() throws IOException {
        LaserCutter.X_ENDSTOP_PIN.setInputListener(this);
        MoveStep moveOnce = new MoveStep();
        moveOnce.setDirection(Axis.X, 0);
        moveOnce.setFeedrate(50);
        moveOnce.along(Axis.X);
        while(inputListener){
            moveOnce.execute();
        }
        
    }

    private void homeY() throws IOException {
        LaserCutter.Y_ENDSTOP_PIN.setInputListener(this);
        MoveStep moveOnce = new MoveStep();
        moveOnce.setDirection(Axis.Y, 0);
        moveOnce.setFeedrate(50);
        moveOnce.along(Axis.Y);
        while(inputListener){
            moveOnce.execute();
        }
    }
}
