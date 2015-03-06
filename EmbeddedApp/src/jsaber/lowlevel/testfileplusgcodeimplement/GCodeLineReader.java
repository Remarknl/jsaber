package jsaber.lowlevel.testfileplusgcodeimplement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;


public class GCodeLineReader {
    LinkedList<String> GCodeLines = new LinkedList<>();
    
    public void main (){
        FileConnection fc = null;
        try {
            fc = (FileConnection) Connector.open("D:\\projecten\\gcode\\output_002.txt"); //moet nog file:\\ voor d:\\ komen te staan, maar dan geeft ie nu nog even een exception en kan de applicatie niet starten
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fc.openInputStream()));
            
            String tempLine = null;
            do{
                tempLine = bufferedReader.readLine();
                if (tempLine != null){
                    GCodeLines.add(tempLine);
                } 
            }while (tempLine != null);
        } catch(IOException e){
            System.out.println("FileConnectionError" + e);
        }
        
    }
}
