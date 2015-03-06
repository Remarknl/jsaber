/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 */
package jsaber.serversocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.microedition.event.Event;
import javax.microedition.event.EventListener;
import javax.microedition.event.EventManager;
import javax.microedition.io.Connector;
import javax.microedition.io.IMCConnection;
import javax.microedition.midlet.MIDlet;

/**
 * Abstract class which processes text data received via IMC connection.
 * IMCDataReceiver listens for an event with name obtained from
 * {@link #getListeningEventName()}, gets string value of the event, which is
 * assumed to be a name of IMC server, establishes a connection with this server
 * via IMC. After that an implementation receives text data from IMC server line
 * by line and processes them. A concrete implementation of this abstract class
 * defines a particular data processing. For this purpose every implementation
 * must implement the following methods:<ul>
 * <li> {@link #getListeningEventName()} which is called to get event name to
 * listen for. This event must contain IMC server name and server version as a
 * value.
 * <li> {@link #startProcessing()} which is called after connection has been
 * established, but no input has been read yet
 * <li> {@link #processLine(java.lang.String)} which is called for each line of
 * the text data read via IMC
 * <li> {@link #stopProcessing()} which is called when connection is closed
 * </ul>
 * Other methods are made final for all implementation to share the same
 * sequence of abstract method calls.
 */
public abstract class IMCDataReceiver extends MIDlet implements Runnable, EventListener {

    // Event name to listen for
    private final String listeningEventName = getListeningEventName();

    /**
     * Short class name without package name. It will be used to prefix log
     * messages with a particular Application name
     */
    protected final String shortClassName = getShortClassName();

    // String to connect to the IMC server
    private String connectionURL;

    // Flag to stop this Application
    private volatile boolean shouldRun = true;

    // Thread where communication with IMC server occurs
    private Thread communicationThread;

    @Override
    public final void startApp() {
        System.out.println(shortClassName + " has been started!");
        // Adding event listener. Specification defines that if Application is 
        // registered to start when a specific event occurs, then to receive the
        // event, which has started this Application, it must register an event
        // listener in the startApp method. It is done here to receive the event
        // which should contain IMC server name as a value
        EventManager.getInstance().addEventListener(listeningEventName, this);
    }

    /**
     * Method which is called by Java stack to handle an event. Method gets the
     * string value of the event, which is supposed to be IMC connection URL.
     * After that a thread is created to perform IMC communication.
     *
     * @param value event which has been received
     */
    @Override
    public final void handleEvent(Event value) {
        // This Application supports only one IMC connection and allows to
        // handle only one event at a time. In order not to get notified about
        // the second event while serving the first one, listener should be
        // removed.
        EventManager.getInstance().removeEventListener(listeningEventName, this);
        // Building connection URL to establish connection with the server
        connectionURL = "imc://*:" + value.getString();

        // Starting a thread which will open connection, read and process
        // received data. This is done in a separate thread in order not to
        // block system thread while trying to connect to the IMC server and
        // read data.
        communicationThread = new Thread(this);
        communicationThread.start();
    }

    /**
     * Establishing connection with the IMC server and reading available text
     * data from it line by line. Reading is stopped when either IMC connection
     * is closed or reading Application is being destroyed.
     */
    @Override
    public final void run() {
        // Establishing connection with the server
        try (IMCConnection connectionWithServer = (IMCConnection) Connector.open(connectionURL)) {
            // Opening input stream to read data
            try (BufferedReader clientReader = new BufferedReader(new InputStreamReader(connectionWithServer.openInputStream()))) {
                System.out.println(shortClassName + " - Connected to the IMC server: " + connectionURL);
                // Calling method of the implementation to initialize reading
                // process
                startProcessing();

                String line;
                // Loop until either data stream ends (connection is closed) or
                // error occurs or Application is terminated
                while (shouldRun && (line = clientReader.readLine()) != null) {
                    // For each line of input, processing it with the
                    // method of the implementation
                    processLine(line);
                }
            } catch (IOException e) {
                System.out.println(shortClassName + " - Communication with the IMC server with URL [" + connectionURL + "] failed: " + e.getMessage());
            }
            // Calling method of the implementation which notifies that input
            // data has ended
            stopProcessing();
        } catch (IOException e) {
            System.out.println(shortClassName + " - Error while communicating with IMC server with URL [" + connectionURL + "]: " + e.getMessage());
        }
        // Application is destroyed. If Application is registered to be launched
        // when an event occurs, then a new instance of it will serve a new
        // event
        destroyApp(true);
    }

    private String getShortClassName() {
        // Getting the last part of the class name as a short name
        String className = this.getClass().getName();
        return className.substring(className.lastIndexOf('.') + 1);
    }

    @Override
    public final void destroyApp(boolean unconditional) {
        // destroyApp() can be called either from AMS or when run() loop has
        // finished (that occured in communicationThread)
        if (Thread.currentThread() != communicationThread && communicationThread != null) {
            // destroyApp() has been called due to AMS stop operation.
            // communicationThread should be interrupted 
            shouldRun = false;
            communicationThread.interrupt();
            // Waiting for the thread to finish execution
            try {
                communicationThread.join();
            } catch (InterruptedException e) {
                System.out.println(shortClassName + " - Failed to stop communication thread: " + e.getMessage());
            }
        }

        // destroyApp() is called from the communication thread (or 
        // communication thread hasn't been created), therefore run() method
        // has finished execution (or hasn't been started) and this Application
        // can gracefully terminate
        System.out.println(shortClassName + " has been destroyed!");
        notifyDestroyed();

    }

    /**
     * Method to get the name of the event which this IMC data receiver is
     * listening for
     *
     * @return name of the event
     */
    protected abstract String getListeningEventName();

    /**
     * Method to initialize receiver implementation for a new communication.
     * This method is called once per connection.
     */
    protected abstract void startProcessing();

    /**
     * Method to process line of the communication input. This method may be
     * called zero or more times according to the number of input lines.
     *
     * @param line input line which has been read from server
     */
    protected abstract void processLine(String line);

    /**
     * Method to deinitialize receiver implementation after all input has been
     * consumed. This method is called once per connection.
     */
    protected abstract void stopProcessing();
}
