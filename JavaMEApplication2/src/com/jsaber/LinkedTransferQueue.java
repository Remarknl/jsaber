package com.jsaber;
import java.util.LinkedList;

public class LinkedTransferQueue {
    static LinkedList<JSaberInstructions> instructionsQueue = new LinkedList<>();
    
    public static void addJSaberInstruction(JSaberInstructions instruction){
        instructionsQueue.addLast(instruction);
    }
    
    public static JSaberInstructions getJSaberInstructions(){
        while(true){
           if(!instructionsQueue.isEmpty()) return instructionsQueue.pollFirst();
        }
    }
    
    public static boolean isQueueEmpty(){
        return instructionsQueue.isEmpty();
    }
}
