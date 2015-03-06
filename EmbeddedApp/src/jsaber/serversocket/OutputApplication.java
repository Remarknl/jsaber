/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 */
package jsaber.serversocket;

/**
 * Application which is a text data processor extending IMCDataReceiver. It logs
 * text received since the moment of the connection to IMC server until either
 * IMC connection is closed or an error occurs. This Application may be
 * registered in JAD file to be started when the listened by
 * {@link IMCDataReceiver} event occurs, if that is the case it may not be
 * running at the time when the event is posted to handle it. In case
 * Application is not registered to be launched by event, it must be started
 * manually to handle events. See MEEP PushRegistry javadoc for more
 * information.
 *
 * @see IMCDataReceiver
 */
public class OutputApplication extends IMCDataReceiver {

    @Override
    protected String getListeningEventName() {
        // Returning event name this Application is listening for
        return "jsaber.serversocket.IMC_DATA_FORWARDER_STARTED";
    }

    @Override
    protected void startProcessing() {
        // There is no need to do anything in this method
    }

    @Override
    protected void processLine(String line) {
        // Printing processed line
        
        if(line.contains("gcodeFilePath:")){
            String gcodeFilePath = line.substring(14);
            
            
            System.out.println("gcfp. "+gcodeFilePath);
        }
        System.out.println(shortClassName + " - Read \"" + line + "\" message");
    }

    @Override
    protected void stopProcessing() {
        // There is no need to do anything in this method
    }
}
