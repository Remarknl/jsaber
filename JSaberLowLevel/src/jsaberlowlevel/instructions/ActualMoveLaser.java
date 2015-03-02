package jsaberlowlevel.instructions;

import java.io.IOException;
import jdk.dio.gpio.GPIOPin;

public class ActualMoveLaser {

    long lastX;
    long lastY;

    GPIOPin X_TO_THA_LEFT;
    GPIOPin X_TO_THA_RIGHT;
    GPIOPin Y_TO_THA_LEFT;
    GPIOPin Y_TO_THA_RIGHT;
    GPIOPin LASER;
    

    public ActualMoveLaser() throws IOException {
        X_TO_THA_LEFT = new PinOutConfiguration(22).getGPIOPin();
        X_TO_THA_RIGHT = new PinOutConfiguration(23).getGPIOPin();
        Y_TO_THA_LEFT = new PinOutConfiguration(24).getGPIOPin();
        Y_TO_THA_RIGHT = new PinOutConfiguration(25).getGPIOPin();
        LASER = new PinOutConfiguration(5).getGPIOPin();
        lastX = 0;
        lastY = 0;
    }

    public void MoveLaser(long X_INPUT, long Y_INPUT, boolean LASER_ON) throws IOException {
        if (X_INPUT != lastX) {
            moveX(X_INPUT, LASER_ON);
        }
        if (Y_INPUT != lastY) {
            moveY(Y_INPUT, LASER_ON);
        }
    }

    private void moveX(long X_INPUT, boolean LASER_ON) throws IOException {
        if (X_INPUT < lastX){
            X_TO_THA_LEFT.setValue(true);
            //time for one pulse
            hold(500);
            X_TO_THA_LEFT.setValue(false);
            //wait for machine to be moved to the wright posistion
            hold(500);
            if (LASER_ON){
               LASER.setValue(true);
               hold(500); 
               LASER.setValue(false);
            }
            lastX -= 1;
        } else if (X_INPUT > lastY) {
            X_TO_THA_RIGHT.setValue(true);
            //time for one pulse
            hold(500);
            X_TO_THA_RIGHT.setValue(false);
            //wait for machine to be moved to the wright posistion
            hold(500);
            if (LASER_ON){
               LASER.setValue(true);
               hold(500); 
               LASER.setValue(false);
            }
            lastX += 1;           
        }  
    }

    private void moveY(long Y_INPUT, boolean LASER_ON) throws IOException {
        if (Y_INPUT < lastY){
            Y_TO_THA_LEFT.setValue(true);
            //time for one pulse
            hold(500);
            Y_TO_THA_LEFT.setValue(false);
            //wait for machine to be moved to the wright posistion
            hold(500);
            if (LASER_ON){
               LASER.setValue(true);
               hold(500); 
               LASER.setValue(false);
            }
            lastY -= 1;
        } else if (Y_INPUT > lastY) {
            Y_TO_THA_RIGHT.setValue(true);
            //time for one pulse
            hold(500);
            Y_TO_THA_RIGHT.setValue(false);
            //wait for machine to be moved to the wright posistion
            hold(500);
            if (LASER_ON){
               LASER.setValue(true);
               hold(500); 
               LASER.setValue(false);
            }
            lastY += 1;           
        }       
    }

    private void hold(long time) {
        long initTime = System.nanoTime();
        while (System.nanoTime() < initTime + time) {
        }
    }
}
