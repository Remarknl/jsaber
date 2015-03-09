package jsaber.lowlevel;

import javax.microedition.midlet.MIDlet;

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

