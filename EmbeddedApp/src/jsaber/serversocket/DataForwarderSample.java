/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 */
package jsaber.serversocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;
import javax.microedition.midlet.MIDlet;

/**
 * Sample which forwards received text data to subscribed Applications line by
 * line. After start Data Forwarder accepts a TCP client and posts an event. All
 * Applications which are interested in the received text data should listen for
 * this event. Having received the event each Application establishes the IMC
 * connection with Data Forwarder. After at least one IMC client has connected,
 * Data Forwarder reads TCP client text data and resends it to all connected IMC
 * clients line by line. When communication with TCP client ends in either way,
 * all IMC communications are closed.
 * <br><br>
 * Data Forwarder accepts only TCP clients. Only one connection at a time is
 * maintained. Port for TCP socket is obtained from the JAD property with name
 * {@value #TCP_SERVER_LISTENING_PORT_PROPERTY_NAME}. If there is no property
 * with such name then the {@value #DEFAULT_TCP_LISTENING_PORT} is used by
 * default. It is possible to connect to Data Forwarder using any TCP client,
 * e.g. "telnet" or "nc".
 * <br><br>
 * After a TCP client has successfully connected, Data Forwarder starts IMC data
 * forwarding server {@link IMCDataForwarder}, which posts an event with its IMC
 * server name and version as a string value of the event in the following
 * format: "&lt;server_name&gt;:&lt;server_version&gt;;". All subscribers listen
 * for the specified event and extract IMC server information from it. If there
 * is a need to start Application when this event occurs, event and Application
 * class names may be mentioned in "MIDlet-Event-Launch-&lt;N&gt;" JAD property.
 * <br><br>
 * All Applications which have received an event and extracted server
 * information may open an IMC connection with the IMC server and start reading
 * text data line by line. IMC server does not read any data from clients, any
 * IMC clients replies are ignored. After at least one IMC connection has been
 * initialized, Data Forwarder starts to read TCP client text data and forwards
 * it to all opened IMC connections line by line. All IMC communications are
 * terminated when TCP connection is closed anyhow. After that Data Forwarder
 * waits for a new TCP client to accept until it is stopped using AMS.
 *
 * <p>
 * For more information, including target platforms, please see the accompanying
 * readme.txt file.</p>
 */
public class DataForwarderSample extends MIDlet implements Runnable {

    // Listening port property name and default value
    private static final String TCP_SERVER_LISTENING_PORT_PROPERTY_NAME = "DataForwarderSample-TcpListeningPort";
    private static final int DEFAULT_TCP_LISTENING_PORT = 64111;

    // Flag that is used to stop TCP listening thread
    private volatile boolean shouldRun = true;

    // Server socket which is accepting TCP clients
    private ServerSocketConnection tcpServerSocket;

    // IMC server which is used to accept IMC clients and to forward TCP client
    // messages to them
    private IMCDataForwarder imcServer;

    // Thread to listen for incoming TCP connections
    private Thread tcpListeningThread;

    @Override
    public void startApp() {
        System.out.println("**********************************");
        System.out.println("* Data Forwarder Sample started! *");
        System.out.println("**********************************");

        // Checking support for optional packages
        if (!isGCFSupported()) {
            System.out.println("CLDC GCF must be supported in order to run this sample.");
            notifyDestroyed();
            return;
        }
        if (!isIOPackageSupported()) {
            System.out.println("MEEP IO package must be supported in order to run this sample.");
            notifyDestroyed();
            return;
        }
        if (!isEventPackageSupported()) {
            System.out.println("MEEP Event package must be supported in order to run this sample.");
            notifyDestroyed();
            return;
        }

        // Creating IMC data forwarder
        imcServer = new IMCDataForwarder();

        // Starting new thread which will accept TCP clients because all
        // blocking and time consuming operations should not be performed in the
        // system thread
        tcpListeningThread = new Thread(this);
        tcpListeningThread.start();
    }

    /**
     * Listening for incoming TCP connections. After TCP connection is
     * successfully established, TCP client input is being read line by line and
     * forwarded to all connected IMC clients
     */
    @Override
    public void run() {
        // Obtaining listening port from JAD properties or using the default one
        int listeningPort = getPortFromProperty(TCP_SERVER_LISTENING_PORT_PROPERTY_NAME, DEFAULT_TCP_LISTENING_PORT);
        try {
            // Opening listening socket
            tcpServerSocket = (ServerSocketConnection) Connector.open("socket://:" + listeningPort);

            while (shouldRun) {
                System.out.println("Listening for a new TCP client on the " + listeningPort + " port");
                // Accepting new clients
                try (SocketConnection client = (SocketConnection) tcpServerSocket.acceptAndOpen()) {
                    System.out.println("Got TCP connection from: " + client.getAddress() + ":" + client.getPort());

                    // Starting to listen for IMC clients
                    imcServer.start();
                    System.out.println("IMC server has been started!");

                    // Performing communication until connection is closed
                    // ordinarily or because of an error. Reading from TCP
                    // client, writing to IMC clients
                    performCommunication(client);

                    System.out.println("TCP client has disconnected");
                    // Stopping IMC server
                    imcServer.stop();
                    System.out.println("IMC server has been stopped!");
                } catch (IOException e) {
                    // Server socket is closed or any error has happened
                    System.out.println("Stopped to accept TCP clients: " + e.getMessage());
                }
                // Work with the current client has finished
            }
        } catch (IOException e) {
            System.out.println("Failed to open server socket: " + e.getMessage());
        }

        // Application is either stopped by AMS or some error occurred
        destroyApp(true);
    }

    // Methods to check optional package support
    private static boolean isGCFSupported() {
        return System.getProperty("microedition.io.gcf") != null;
    }

    private static boolean isEventPackageSupported() {
        return System.getProperty("microedition.event") != null;
    }

    private static boolean isIOPackageSupported() {
        return System.getProperty("microedition.io") != null;
    }

    private void performCommunication(SocketConnection client) {
        // Using try-with-resources code block to open reader and writer to
        // communicate with TCP client and close them automatically after
        // leaving code block
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.openOutputStream()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.openInputStream()))) {
            // Sending greetings message
            writer.write("Connected... Send your text data. No more messages will be sent from the server.\r\n");
            writer.flush();

            // Reading a line from TCP client and posting it via IMC data
            // forwarder to all accepted IMC clients. readLine() method blocks
            // until line feed character is received. forwardData() blocks if
            // there are no IMC clients. 
            String line;
            while ((line = reader.readLine()) != null) {
                // readLine() method removes line feed character, need to
                // restore it back. That is why "\n" is added to the forwarded
                // data
                imcServer.forwardData(line + "\n");
            }
            // The end of the stream has been reached (socket is closed)
        } catch (IOException e) {
            System.out.println("Error while reading TCP client data: " + e.getMessage());
        }
    }

    @Override
    public void destroyApp(boolean unconditional) {
        // Setting flag to stop communication loop in the run() method
        shouldRun = false;

        if (tcpServerSocket != null) {
            // Closing server socket
            try {
                tcpServerSocket.close();
            } catch (IOException e) {
                System.out.println("Failed to close server socket:" + e.getMessage());
            }
        }

        // destroyApp() can be called either from AMS or when run() loop has
        // finished (that occured in tcpListeningThread)
        if (Thread.currentThread() != tcpListeningThread) {
            // destroyApp() is called by AMS. Need to interrupt currently
            // blocked IO operations in tcpListeningThread, if any, and join
            // the thread
            tcpListeningThread.interrupt();
            try {
                tcpListeningThread.join();
            } catch (InterruptedException e) {
                System.out.println("Failed to wait for sample stop: " + e.getMessage());
            }
        } else {
            // destroyApp() has been called when run() loop has finished.
            // Application can be destroyed by notifyDestroyed() 
            System.out.println("Data Forwarder Sample destroyed!");
            System.out.println("********************************");
            notifyDestroyed();
        }
    }

    /**
     * Method either to retrieve port as integer from JAD file or to return
     * default one.
     *
     * @param propertyName name of the property to retrieve port
     * @param defaultValue default value of the port in case property was not
     * found by name
     * @return listening port as integer from JAD file or
     * <code>defaultValue</code> if property is absent
     */
    private static int getPortFromProperty(String propertyName, int defaultValue) {
        // Getting property from the current Application properties 
        String[] listeningPort = MIDlet.getAppProperty(null, null, propertyName, null);
        if (listeningPort == null || listeningPort.length == 0) {
            return defaultValue;
        } else {
            try {
                int port = Integer.parseInt(listeningPort[0]);
                if (port < 0 || port > 65535) {
                    System.out.println("Port is out of possible range (from 0 to 65535): " + port + ". Using default value.");
                    return defaultValue;
                }
                return port;
            } catch (NumberFormatException e) {
                System.out.println("Port has wrong format: " + listeningPort[0] + ". Using default value.");
                return defaultValue;
            }
        }
    }
}
