package jsaberlowlevel.instructions;
import java.io.IOException;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.PinEvent;
import jdk.dio.gpio.PinListener;

public class Homer implements PinListener, Instruction {
    GPIOPin X_OFFSET;
    GPIOPin Y_OFFSET;
    GPIOPin X_HOME_LISTENER;
    GPIOPin Y_HOME_LISTNER;
    boolean inputListener;
    
    public Homer() throws IOException {
        X_OFFSET = new PinOutConfiguration(27).getGPIOPin();
        Y_OFFSET = new PinOutConfiguration(23).getGPIOPin();
        X_HOME_LISTENER = new PinListnerConfiguration(4).getGPIOPin();
        Y_HOME_LISTNER = new PinListnerConfiguration(17).getGPIOPin();
        inputListener = true;
    }
    @Override
    public void execute() {
        try {
            moveX();
            moveY();
            closePins();
        } catch (IOException e){
            System.out.println("Error in Homer" + e);
        }
    }
    
    public void moveX () throws IOException{
        X_HOME_LISTENER.setInputListener(this);
        while(inputListener){
            X_OFFSET.setValue(true);
        }
        X_OFFSET.setValue(false);
        inputListener = true;
    }
    
    public void moveY () throws IOException{
        while(inputListener){
            Y_OFFSET.setValue(true);
        }
        Y_OFFSET.setValue(false);
        inputListener = true;
    }
    
    private void closePins() throws IOException{
        X_OFFSET.close();
        Y_OFFSET.close();
    }

    @Override
    public void valueChanged(PinEvent event) {
        inputListener = false;
    }
}
