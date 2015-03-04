/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsaberlowlevel.instructions;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rgi20802
 */
public class CalibrateBed implements Instruction {

    @Override
    public void execute() {
        try {
            new Homer().execute();
        } catch (IOException ex) {
            Logger.getLogger(CalibrateBed.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
