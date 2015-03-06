/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsaber.lowlevel.testfileplusgcodeimplement;


/**
 *
 * @author rru20841
 */


import java.util.LinkedList;
import javax.microedition.midlet.MIDlet;



public class Testfileplusgcodeimplement extends MIDlet {
    
    @Override
    public void startApp() {
        //ik heb nu nog even hardcoded een LinkedList met GCode toegevoegd om te laten zien dat het maken van instructies werkt.
        LinkedList gCodeLines = new LinkedList();
        gCodeLines.add("G00 X166.916807 Y200.779862");
        gCodeLines.add("G01 X18.547292 Y199.977293 Z-0.125000 F400.000000");
        gCodeLines.add("G01 X166.916807 Y200.779862 Z-0.125000");
        gCodeLines.add("G00 X0.0000 Y0.0000");
        InstructionFactory factory = new InstructionFactory(gCodeLines);
        System.out.println(factory.getMoveList());
        
        
        new GCodeLineReader().main();
    }
    
    @Override
    public void destroyApp(boolean unconditional) {
    }
}
