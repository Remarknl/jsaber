package com.jsaber;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.dio.Device;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;

public class GPIOPinTest implements AutoCloseable {

//    private static final int X_STAP_PIN = 23;
//    private static final int X_RICHTING_PIN = 25;
    private static final int Y_STAP_PIN = 23;
    private static final int Y_RICHTING_PIN = 24;

//    private GPIOPin X_STAP;
//    private GPIOPin X_RICHTING;
    private GPIOPin Y_STAP;
    private GPIOPin Y_RICHTING;

    int lastXCoordinate = 0;
    int lastYCoordinate = 0;

    @Override
    public void close() {
        try {
//            if (X_STAP != null) {
//                X_STAP.setValue(false);
//                X_STAP.close();
//            }
//            if (X_RICHTING != null) {
//                X_RICHTING.setValue(false);
//                X_RICHTING.close();
//            }
            if (Y_STAP != null) {
                Y_STAP.setValue(false);
                Y_STAP.close();
            }
            if (Y_RICHTING != null) {
                Y_RICHTING.setValue(false);
                Y_RICHTING.close();
            }

            System.out.println("End of program");
        } catch (IOException ex) {
            System.out.println("Exception closing resources: " + ex);
        }
    }

    public void start() throws IOException {
          System.out.println("Start program...");
//        for (int i = 0; i < 100; i++) {
//            try {
//                Device dev = DeviceManager.open(i);
//                System.out.println(i + "\t" + dev.getDescriptor().getID() + "\t" + dev.getDescriptor().getName());
//            } catch (Exception ex) {
//                //ex.printStackTrace();
//            }
//        }
        // Open the LED pin (Output)
        
//        GPIOPinConfig X_STAP_CONFIG = new GPIOPinConfig(0, 16, GPIOPinConfig.DIR_OUTPUT_ONLY, DeviceConfig.DEFAULT, 0, false);
//        GPIOPinConfig X_RICHTING_CONFIG = new GPIOPinConfig(0, 18, GPIOPinConfig.DIR_OUTPUT_ONLY, DeviceConfig.DEFAULT, 0, false);
//        GPIOPinConfig Y_STAP_CONFIG = new GPIOPinConfig(0, 15, GPIOPinConfig.DIR_OUTPUT_ONLY, DeviceConfig.DEFAULT, 0, false);
//        GPIOPinConfig Y_RICHTING_CONFIG = new GPIOPinConfig(0, 22, GPIOPinConfig.DIR_OUTPUT_ONLY, DeviceConfig.DEFAULT, 0, false);
//        X_STAP = DeviceManager.open(X_STAP_CONFIG);
//        X_RICHTING = DeviceManager.open(X_RICHTING_CONFIG);
//        Y_STAP = DeviceManager.open(Y_STAP_CONFIG);
//        Y_RICHTING = DeviceManager.open(Y_RICHTING_CONFIG);
        
//        X_STAP = DeviceManager.open(X_STAP_PIN);
//        X_RICHTING = DeviceManager.open(X_RICHTING_PIN);
        Y_STAP = DeviceManager.open(Y_STAP_PIN);
        Y_RICHTING = DeviceManager.open(Y_RICHTING_PIN);
        System.out.println("After GPIO initialization");
 
                while(true){
            try{
                
                System.out.println("looperdieloop");
//                X_STAP.setValue(true);
//                X_RICHTING.setValue(true);
                Y_STAP.setValue(true);
                Y_RICHTING.setValue(true);
            
                Thread.sleep(300);
//                X_STAP.setValue(false);
//                X_RICHTING.setValue(false);
                Y_STAP.setValue(false);
                Y_RICHTING.setValue(false);
                
                Thread.sleep(300);
                
            } catch (InterruptedException e){
                System.out.println("whoops interrupted exception");
            }
        
//        boolean continueProgram = true;
//        try {
//            while (continueProgram) {
//                System.out.println("program still running");
//                moveJSaber(LinkedTransferQueue.getJSaberInstructions());
//            }
//        } catch (IOException e) {
//            System.out.println("Whoops... Something whent wrong!");
//        }

    }
    }

    private void moveJSaber(JSaberInstructions jSaberInstructions) throws IOException {
//        while (lastXCoordinate != jSaberInstructions.getXCoordinate()){// || lastYCoordinate != jSaberInstructions.getYCoordinate()) {
//            System.out.println("before movetox and y");
//            if (lastXCoordinate != jSaberInstructions.getXCoordinate()) {
//                moveToXCoordinate(jSaberInstructions);
//                System.out.println("X works");
//            }
//            if (lastYCoordinate != jSaberInstructions.getYCoordinate()) {
//                moveToYCoordinate(jSaberInstructions);
//                System.out.println("Y works");
//            }
        }
    }

//    private void moveToXCoordinate(JSaberInstructions jSaberInstructions) throws IOException {
//        int newXCoordinate = jSaberInstructions.getXCoordinate();
//        if (lastXCoordinate < newXCoordinate) {
//            X_RICHTING.setValue(true);
//            lastXCoordinate -= 1;
//        } else {
//            X_RICHTING.setValue(false);
//            lastYCoordinate += 1;
//        }
//        long starttime = System.nanoTime();
//        X_STAP.setValue(true);
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(GPIOPinTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        while (System.nanoTime() > (starttime + 1100000000)) {
//        }
//        X_STAP.setValue(false);
//        X_RICHTING.setValue(false);
//        
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(GPIOPinTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

//    private void moveToYCoordinate(JSaberInstructions jSaberInstructions) throws IOException {
//        int newYCoordinate = jSaberInstructions.getYCoordinate();
//        if (lastYCoordinate < newYCoordinate) {
//            Y_RICHTING.setValue(true);
//            lastYCoordinate += 1;
//        } else {
//            Y_RICHTING.setValue(false);
//            lastYCoordinate -= 1;
//        }
//        long starttime = System.nanoTime();
//        Y_STAP.setValue(true);
//        while (System.nanoTime() > (starttime + 1100)) {
//        }
//        Y_STAP.setValue(false);
//        Y_RICHTING.setValue(false);
//    }
