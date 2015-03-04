package jsaberlowlevel.instructions;

public class Move extends MoveStep {
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

    @Override
    public void execute() {
        
    }  
}
