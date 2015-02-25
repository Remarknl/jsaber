/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsaberlowlevel;

import jsaberlowlevel.instructions.DebugInstruction;

/**
 *
 * @author rgi20802
 */
public class SocketListener {
    
    public void read() {
        String command = "EXECUTE_DEBUG";
        switch(command){
            case "EXECUTE_DEBUG":
                LinkedTransferQueue.addInstruction(new DebugInstruction("test"));
                break;
            
        }
    }
}
