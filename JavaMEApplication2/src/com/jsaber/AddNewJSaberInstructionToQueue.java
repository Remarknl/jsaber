package com.jsaber;

public class AddNewJSaberInstructionToQueue implements Runnable {
    private LinkedTransferQueue queue;
    
    public void run(){
        for(int i = 0; i < 4; i++)
        LinkedTransferQueue.addJSaberInstruction(new JSaberInstructions(5, 6, false));
        LinkedTransferQueue.addJSaberInstruction(new JSaberInstructions(10, 16, true));
        LinkedTransferQueue.addJSaberInstruction(new JSaberInstructions(5, 6, true));
        LinkedTransferQueue.addJSaberInstruction(new JSaberInstructions(10, 16, false));        

    }
}
