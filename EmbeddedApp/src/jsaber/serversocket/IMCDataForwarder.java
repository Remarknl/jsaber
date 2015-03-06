/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 */
package jsaber.serversocket;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.microedition.event.Event;
import javax.microedition.event.EventManager;
import javax.microedition.io.Connector;
import javax.microedition.io.IMCConnection;
import javax.microedition.io.IMCServerConnection;
import javax.microedition.midlet.MIDletIdentity;

/**
 * Class which represents IMC server which can forward data to IMC clients. To
 * begin accepting new IMC clients, forwarder should be started using
 * {@link #start()} method. Data forwarder notifies that it is ready to accept
 * new IMC clients by posting {@value #EVENT_NAME} event. Applications may
 * listen for this event, retrieve IMC server name and version from its value
 * and use them to connect to the IMC server. After IMC forwarder has accepted
 * at least one IMC client it starts forwarding data provided via
 * {@link #forwardData(java.lang.String)} method to all connected IMC clients.
 * IMC forwarder may be stopped by calling {@link #stop()} method. It can be
 * restarted after it has been stopped. All public methods are not thread-safe.
 */
public class IMCDataForwarder implements Runnable {

    // Name of the event which is posted when server is started
    private static final String EVENT_NAME = "jsaber.serversocket.IMC_DATA_FORWARDER_STARTED";

    // Version of the IMC server
    private static final String IMC_SERVER_VERSION = "1.0";

    // List of connected clients
    private final List<IMCSender> imcClients = new ArrayList<>();

    // IMC server to accept IMC clients
    private IMCServerConnection imcServer;

    // IMC server name to listen for IMC clients
    private final String imcServerName;

    // Flag to stop IMC listening thread 
    private volatile boolean shouldRun = true;

    // Flag to determine whether IMC server has been started
    private volatile boolean isStarted = false;

    // Thread in which IMC server executes
    private Thread imcListeningThread;

    /**
     * Creates IMC data forwarder. Use {@link #start()} method to start
     * accepting IMC clients after forwarder has been created.
     */
    public IMCDataForwarder() {
        // Constructing IMC server name from the class name and
        // server version
        imcServerName = this.getClass().getName() + ":" + IMC_SERVER_VERSION + ";";
    }

    /**
     * Method to start IMC data forwarder. This method notifies that IMC data
     * forwarder is ready to accept new IMC clients. All Applications which want
     * to get notified should listen for the {@value #EVENT_NAME} event. After
     * receiving the event, IMC clients may connect to the IMCDataForwarder.
     * Data provided via {@link #forwardData(java.lang.String)} method will be
     * forwarded to the connected IMC clients. Nothing happens if this method is
     * called when IMC server has already been started and hasn't been stopped
     * yet. After IMC server has been stopped, it may be restarted by calling
     * this method. There may be only one IMC data forwarder running on the
     * platform at a time.
     *
     * @throws IOException when server could not open a connection
     */
    public void start() throws IOException {
        if (!isStarted) {
            // Opening server connection
            imcServer = (IMCServerConnection) Connector.open("imc://:" + imcServerName);

            shouldRun = true;
            isStarted = true;

            // Creating and starting a thread in which server will be accepting
            // IMC clients
            imcListeningThread = new Thread(this);
            imcListeningThread.start();
        }
    }

    /**
     * Posting the event {@value #EVENT_NAME}, accepting IMC connections and
     * creating IMC sender objects to perform communication with IMC clients.
     */
    @Override
    public void run() {
        // Posting an event that IMC server starts to accept IMC clients
        postEvent();

        System.out.println("Starting to accept IMC clients...");
        try {
            while (shouldRun) {
                // Accepting new clients until this thread is not interrupted
                IMCConnection newClient = (IMCConnection) imcServer.acceptAndOpen();

                // Obtaining client identity information
                MIDletIdentity clientIdentity = newClient.getRemoteIdentity();
                if (clientIdentity == null) {
                    System.out.println("Got new IMC connection. No identity information available");
                } else {
                    System.out.printf("Got new IMC connection from %s:%s:%s\r\n", clientIdentity.getVendor(), clientIdentity.getName(), clientIdentity.getVersion());
                }

                // Starting new client thread which will be used to send data to
                // the IMC client
                IMCSender imcSender = new IMCSender(newClient);
                imcSender.start();

                // Synchronizing access to add newly connected client into the
                // clients list
                synchronized (imcClients) {
                    imcClients.add(imcSender);
                    // Because forwardData() method blocks execution until there
                    // is at least one client, the thread, which has been
                    // blocked while waiting for clients to appear, has to be
                    // waken up
                    imcClients.notify();
                }
            }
        } catch (IOException e) {
            // Checking whether acceptAndOpen() has been interrupted from stop()
            // method
            if (e instanceof InterruptedIOException) {
                System.out.println("Stopped to accept IMC clients");
            } else {
                // Some other error has happened
                System.out.println("Error while accepting IMC clients: " + e.getMessage());
            }
        }
    }

    private void postEvent() {
        try {
            // Creating an event with the specified name and passing an IMC
            // server name as a value to notify listeners that it is currently
            // possible to connect to the IMC server.
            // Two null arguments mean that this event has no message and info
            // which can be used to pass additional data to the recipient
            Event gotConnectionEvent = new Event(EVENT_NAME, imcServerName, null, null);

            // Posting an event
            EventManager.getInstance().post(gotConnectionEvent);
            System.out.println("Event " + EVENT_NAME + " has been posted!");
        } catch (IOException e) {
            System.out.println("Failed to post an event: " + e.getMessage());
        }
    }

    /**
     * Method to send string to all accepted clients of the IMC data forwarder.
     * This method blocks execution unless at least one client connects to the
     * forwarder. After at least one client has been accepted, calling this
     * method does not block execution. Nothing happens (data is not sent and is
     * lost) if this method is called when IMC data forwarder has been stopped.
     *
     * @param string the string to be sent to all accepted clients of the server
     * @throws NullPointerException when the string is null
     */
    public void forwardData(String string) {
        // Checking string is not null
        Objects.requireNonNull(string, "String cannot be null");

        // Posting data is possible only when server is running
        if (!isStarted || !shouldRun) {
            return;
        }
        try {
            // Waiting until there is at least one recipient
            synchronized (imcClients) {
                while (imcClients.isEmpty()) {
                    imcClients.wait();
                }
            }

            // Sending string to all clients. Because client.send() method
            // does not block execution thread, this loop does not cause thread
            // blocking
            synchronized (imcClients) {
                for (IMCSender client : imcClients) {
                    client.send(string);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Forwarding data has been interrupted: " + e.getMessage());
        }
    }

    /**
     * Method to stop this IMC data forwarder from accepting new clients and
     * close current client connections. Calling this method after forwarder has
     * already been stopped has no effect. After IMC server has been stopped, it
     * can be restarted using {@link #start()} method.
     */
    public void stop() {
        if (!isStarted) {
            return;
        }
        isStarted = false;
        shouldRun = false;

        // Closing the IMC server
        try {
            this.imcServer.close();
        } catch (IOException e) {
            System.out.println("Failed to close IMC server: " + e.getMessage());
        }

        // Stopping IMC server thread. Interrupting its thread and waiting until
        // it finishes
        if (imcListeningThread != null) {
            imcListeningThread.interrupt();
            try {
                imcListeningThread.join();
            } catch (InterruptedException e) {
                System.out.println("Failed to wait until IMC server stops: " + e.getMessage());
            }
            imcListeningThread = null;
        }

        // Stopping IMC senders which have been associated with IMC clients
        stopClients();
    }

    private void stopClients() {
        // Closing client connections
        synchronized (imcClients) {

            // Interrupting senders
            for (IMCSender client : imcClients) {
                client.interrupt();
            }

            // Waiting for senders to finish execution
            for (IMCSender client : imcClients) {
                try {
                    client.join();
                } catch (InterruptedException e) {
                    System.out.println("Failed to join client thread: " + e.getMessage());
                }
            }

            // Emptying clients list
            imcClients.clear();
        }
    }
}
