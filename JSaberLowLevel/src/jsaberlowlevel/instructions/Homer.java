package jsaberlowlevel.instructions;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.PinEvent;
import jdk.dio.gpio.PinListener;
import jsaberlowlevel.LaserCutter;

public class Homer implements PinListener, Instruction {
    boolean inputListener;
    
    public Homer() throws IOException {
        //X_OFFSET = new PinOutConfiguration(27).getGPIOPin();
        //Y_OFFSET = new PinOutConfiguration(23).getGPIOPin();
        //X_HOME_LISTENER = new PinListnerConfiguration(4).getGPIOPin();
        //Y_HOME_LISTNER = new PinListnerConfiguration(17).getGPIOPin();
        //inputListener = true;
    }
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
        
    }

    private void homeY() throws IOException {
        LaserCutter.X_ENDSTOP_PIN.setInputListener(this);
        
    }
}
