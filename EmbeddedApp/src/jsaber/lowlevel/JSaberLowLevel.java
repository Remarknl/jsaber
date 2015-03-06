/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsaber.lowlevel;


/**
 *
 * @author rru20841
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.microedition.midlet.MIDlet;

/**
 *
 * @author rgi20802
 */
public class JSaberLowLevel extends MIDlet {
    
    @Override
    public void startApp() {
        LaserCutter.init();
//        new SocketListener().read();
    }
    
    @Override
    public void destroyApp(boolean unconditional) {
    }
}

