package jsaber.lowlevel.testfileplusgcodeimplement;

public class Move {
    private final long X_OFFSET;
    private final long Y_OFFSET;
    private final boolean LASER_ON;
    
    public Move(){
        X_OFFSET = 0;
        Y_OFFSET = 0;
        LASER_ON = false;
    }
    
    public Move(long xCoordinate, long yCoordinate, boolean laserOn){
        this.X_OFFSET = xCoordinate;
        this.Y_OFFSET = yCoordinate;
        this.LASER_ON = laserOn;
    }
    
    public String toString(){
        return "\n\n" + X_OFFSET + " " + Y_OFFSET + " " + LASER_ON + "\n\n" ;
    }


}
