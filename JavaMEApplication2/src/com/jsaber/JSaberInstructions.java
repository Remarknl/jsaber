
package com.jsaber;

public class JSaberInstructions {
    private final int xCoordinate;
    private final int yCoordinate;
    private final boolean laserValue;
    
    public JSaberInstructions(){
        xCoordinate = 0;
        yCoordinate = 0;
        laserValue = false;
    }
    
    public JSaberInstructions(int xCoordinate, int yCoordinate, boolean laserOn){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.laserValue = laserOn;
    }
    
    public int getXCoordinate(){
        return xCoordinate;
    }
    
    public int getYCoordinate(){
        return yCoordinate;
    }
    
    public boolean getLaserValue(){
        return laserValue;
    }
}
