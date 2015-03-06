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


import jsaber.lowlevel.LinkedTransferQueue;

/**
 *
 * @author rgi20802
 */
public class Executor {

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
        //make new worker thread that does execute()
        execute();
    }
}

