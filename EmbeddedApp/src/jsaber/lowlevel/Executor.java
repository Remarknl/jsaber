package jsaber.lowlevel;

import jsaber.lowlevel.LinkedTransferQueue;

public class Executor implements Runnable {

    public static void finish() {
        //kill worker thread
    }
    
    public static void abort() {
        //kill worker thread
    }
    
    private static void execute() {
        while(!LinkedTransferQueue.isQueueEmpty()) {
            LinkedTransferQueue.getFirstInstruction().execute();
        }
    }
    
    public static void start() {
        Runnable rb = new Executor();
        Thread executerThread = new Thread(rb);
        executerThread.setPriority(10);
        executerThread.setName("executerThread");
        executerThread.run();
    }
    
    public void run(){
        execute();
    }
}

