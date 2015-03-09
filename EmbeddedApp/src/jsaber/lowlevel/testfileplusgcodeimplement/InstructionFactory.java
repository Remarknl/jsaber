package jsaber.lowlevel.testfileplusgcodeimplement;

import java.util.LinkedList;

public class InstructionFactory {

    int GPrintCode;
    long XCoordinate;
    long YCoordinate;
    LinkedList<Move> moveList;

    public InstructionFactory(LinkedList<String> GCodeLines) {
        moveList = new LinkedList<>();
        while (!GCodeLines.isEmpty()) {
            setGPrintCodeXCoordinateYCoordinate(deleteDotsFromLine(GCodeLines.remove()));
            moveList.add(makeNewInstruction());
            setValuesToNull();
            
        }
    }
    public LinkedList<Move> getMoveList(){
        return moveList;
    }
    
    private void setValuesToNull(){
        GPrintCode = 0;
        XCoordinate = 0;
        YCoordinate = 0;
    }
    
    private Move makeNewInstruction() {
        boolean laserOn = false;
        if (GPrintCode == 0) {
            laserOn = false;
        } else if (GPrintCode == 1) {
            laserOn = true;
        }
        Move temp = new Move(XCoordinate, YCoordinate, laserOn);
        return temp;
    }

    private String deleteDotsFromLine(String line) {
        while (line.contains(".")) {
            int tempDotIndex = line.indexOf(".");
            line = line.substring(0, tempDotIndex) + line.substring(tempDotIndex + 1);
        }
        return line;
    }

    private void setGPrintCodeXCoordinateYCoordinate(String line) {
        boolean loop = true;
        String temp;
        int spaceIndex;
        
        //loop stops when YCoordinate is set or if G, X or Y can't be found 
        while (loop) {
            //when there is nog space the returned value is -1, this takes place when there is no space after the YCoordinate
            spaceIndex = line.indexOf(" ");
            if (spaceIndex != -1) {
                temp = line.substring(0, spaceIndex);
                if (temp.startsWith("G")) {
                    GPrintCode = Integer.parseInt(temp.substring(1));
                } else if (temp.startsWith("X")) {
                    XCoordinate = Long.parseLong(temp.substring(1));
                } else if (temp.startsWith("Y")) {
                    YCoordinate = Long.parseLong(temp.substring(1));
                    loop = false;
                } else {
                    //maybe throw an exception? because something went wrong
                    loop = false;
                }
                line = line.substring(spaceIndex + 1);
            // when spaceIndex is -1
            } else {
                if (line.startsWith("Y")) {
                    YCoordinate = Long.parseLong(line.substring(1));
                    loop = false;
                } else {
                    //maybe throw an exception? because something went wrong
                    loop = false;
                }
            }
        }
    }
}
