/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsaber.pitestapplication;



/**
 *
 * @author rru20841
 */


import java.io.IOException;
import javax.microedition.midlet.MIDlet;

public class PiTestApplication extends MIDlet {
    private PinPulseExample pulsetest;
    private ButtonExample button;
    
    
    @Override
    public void startApp() {
        //amount of pulses, ns time on, ns time off
        pulsetest = new PinPulseExample(10, 1000_000_000, 1000_000_000);
        //button = new ButtonExample(23);
        try {
            pulsetest.start();
            //button.start();
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
            notifyDestroyed();
        }
    }
    
    @Override
    public void destroyApp(boolean unconditional) {
        if (pulsetest != null) {
            pulsetest.close();
        }
        if (button != null) {
            button.close();
        }
    }
}

