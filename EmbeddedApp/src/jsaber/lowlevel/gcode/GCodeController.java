package jsaber.lowlevel.gcode;

import java.util.LinkedList;

public class GCodeController {
    
    public void GCodingProcess(String fileName) {
        
        GCodeLineReader gCodeLineReader = new GCodeLineReader();
        LinkedList<String> gCodeLines = gCodeLineReader.returnGCodeLinesFromFile(fileName);  
        System.out.println(gCodeLines);
        
        GCodeInterpreter factory = new GCodeInterpreter(gCodeLines);
        
    }
}