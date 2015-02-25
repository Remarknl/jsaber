///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.jsaber;
//
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.microedition.midlet.MIDlet;
//import javax.microedition.midlet.MIDletStateChangeException;
//import jdk.dio.DeviceManager;
//import jdk.dio.gpio.GPIOPin;
//
///**
// *
// * @author rbe20795
// */
//public class NewMEEPlet extends MIDlet {
//   private GPIOPin first;
//    private GPIOPin second;
//    private GPIOPin third;
//    private GPIOPin fourth;
//    
//    @Override
//    public void startApp() {
//        System.out.println("");
//        try {
//            first = DeviceManager.open(23);
//            second = DeviceManager.open(24);
//            third = DeviceManager.open(25);
//            fourth = DeviceManager.open(27);
//        } catch (IOException ex) {
//            Logger.getLogger(NewMEEPlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//        for (int i = 0; i < 10; i++) {
//            
//            try {
//                first.setValue(true);
//                Thread.sleep(500);
//                first.setValue(false);
//                Thread.sleep(500);
//            } catch (Exception ex) {
//                Logger.getLogger(NewMEEPlet.class.getName()).log(Level.SEVERE, null, ex);
//            }                      
//        }
//    }                    
//
//    @Override
//    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//}
