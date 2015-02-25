package com.jsaber;

import java.io.IOException;
import javax.microedition.midlet.MIDlet;

public class StartEndApplication extends MIDlet {

    private GPIOPinTest pinTest;

    @Override
    public void startApp() {
        
        //make a new thread to add instructions to the linkedTranserQueue
//        Runnable addNewJsaberInstructionToQueue = new AddNewJSaberInstructionToQueue();
//        Thread addsInstructions = new Thread(addNewJsaberInstructionToQueue);
//        addsInstructions.start();
        
        pinTest = new GPIOPinTest();
        try {
            pinTest.start();
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
            notifyDestroyed();
        }
    }

    @Override
    public void destroyApp(boolean unconditional) {
        if (pinTest != null) {
            pinTest.close();
        }
    }
}
