/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsaber.lowlevel;

/**
 *
 * @author rru20841
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author rru20841
 */

import jsaber.lowlevel.instructions.Instruction;
import java.util.LinkedList;

public class LinkedTransferQueue {
    private static LinkedList<Instruction> instructionsQueue = new LinkedList<>();
    
    public static void addInstruction(Instruction instruction){
        instructionsQueue.addLast(instruction);
        Executor.start();
    }
    
    public static void addInstruction(Instruction[] instructionArray){
        for (Instruction instruction : instructionArray) {
            instructionsQueue.addLast(instruction);
        }
        Executor.start();
    }
    
    public static Instruction getFirstInstruction(){
        if(!instructionsQueue.isEmpty()) {
            return instructionsQueue.pollFirst();
        } else {
            Executor.finish();
            return null;
        }
        
    }
    
    public static boolean isQueueEmpty(){
        return instructionsQueue.isEmpty();
    }
}


