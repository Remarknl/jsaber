package jsaber.lowlevel.gcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import java.io.InputStreamReader;

public class GCodeLineReader {

    LinkedList<String> GCodeLines = new LinkedList<>();

    public LinkedList<String> returnGCodeLinesFromFile(String fileName) {
        try {
            
//            get the root:
//            Enumeration drives = FileSystemRegistry.listRoots();
//            String root = (String) drives.nextElement();
//            String path = "file:///" + root;
//            System.out.println(path);
            
            
            String folder = "file:///rootfs/home/pi/gcode/";
//            String fileName = "output_0004.txt";
            FileConnection fc = (FileConnection) Connector.open(folder + fileName);
            System.out.println(fc.exists());

            BufferedReader readGCodeLines = new BufferedReader(new InputStreamReader(fc.openInputStream()));

            String line;
            while((line = readGCodeLines.readLine()) != null){
                GCodeLines.add(line);
            }
        } catch (IOException ex) {
             ex.getMessage();
        }
        return GCodeLines;
    }
}

