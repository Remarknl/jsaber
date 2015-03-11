package jsaber.lowlevel.gcode;

import jsaber.lowlevel.instructions.*;
import java.util.LinkedList;
import static jsaber.lowlevel.LaserCutter.MM_PER_STEP_TIMES_10000;

public class GCodeInterpreter {

    int GPrintCode;
    long XCoordinate;
    long YCoordinate;
    LinkedList<Instruction> instructionList;

    public GCodeInterpreter(LinkedList<String> gCodeLines) {
        instructionList = new LinkedList<>();
        while (!gCodeLines.isEmpty()) {
            if (setGPrintCodeXCoordinateYCoordinate(deleteDotsFromLine(gCodeLines.remove()))) {
                instructionList.add(makeNewInstruction());
            }
            setValuesToNull();
        }
    }

    public LinkedList<Instruction> getInstructionList() {
        return instructionList;
    }

    private void setValuesToNull() {
        GPrintCode = 0;
        XCoordinate = 0;
        YCoordinate = 0;
    }

    private Instruction makeNewInstruction() {
        Instruction temp = null;

        switch (GPrintCode) {
            //Rapid positioning
            case 0: {
                //class RapidPositioning
                temp = (Instruction) new RapidPositioning(XCoordinate, YCoordinate);
            }
            break;
            //Linear interpolation -laser
            case 1: {
                // class LaserLine
                temp = (Instruction) new LaserLine(XCoordinate, YCoordinate);
            }
            break;
            //circular interpolation clockwise - laser
            case 2: {
                //class LaserCircularClockwise
                temp = (Instruction) new LaserCircularClockwise(XCoordinate, YCoordinate);
            }
            break;
            case 3: {
                //class LaserCiruclarCounterClockwise
                temp = (Instruction) new LaserCircularCounterClockwise(XCoordinate, YCoordinate);
            }
            default: {
            }
            break;
        }
        return temp;
    }

    private String deleteDotsFromLine(String line) {
        while (line.contains(".")) {
            int tempDotIndex = line.indexOf(".");
            line = line.substring(0, tempDotIndex) + line.substring(tempDotIndex + 1);
        }
        return line;
    }

    private boolean setGPrintCodeXCoordinateYCoordinate(String line) {
        boolean loop = true;
        String temp;
        int spaceIndex;

        //loop stops when YCoordinate is set or if G, X or Y can't be found 
        while (loop) {
            //when there is no space, the returned value is -1, this takes place when there is no space after the YCoordinate
            spaceIndex = line.indexOf(" ");
            temp = getTempString(line, spaceIndex);

            //conditions, looking for G, Y and Z
            //returns true if the wright information is obtaind and false if not
            if (temp.equals("")) {
                return false;
            }
            if (temp.startsWith("G")) {
                GPrintCode = Integer.parseInt(temp.substring(1));
            } else if (temp.startsWith("X")) {
                XCoordinate = Long.parseLong(temp.substring(1)) / MM_PER_STEP_TIMES_10000;
            } else if (temp.startsWith("Y")) {
                YCoordinate = Long.parseLong(temp.substring(1)) / MM_PER_STEP_TIMES_10000;
                return true;
            } else {
                return false;
            }

            line = line.substring(spaceIndex + 1);
        }
        return false;
    }

    private String getTempString(String line, int spaceIndex) {
        String temp;
        //set the wrigt string to temp whether spaceindex is -1 or not
        if (spaceIndex != -1) {
            temp = line.substring(0, spaceIndex);
        } else {
            temp = line;
        }
        return temp;
    }
}
