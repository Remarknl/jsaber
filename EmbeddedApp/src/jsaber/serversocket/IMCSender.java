/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 */
package jsaber.serversocket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import javax.microedition.io.IMCConnection;

/**
 * Class which allows to send data to the IMC client asynchronously. Sender
 * works in a separate thread and should be started like a thread. After sender
 * has been started, it waits for {@link #send(java.lang.String)} method calls
 * to send data. Calling {@link #send(java.lang.String)} method does not block
 * execution of the calling thread. Sender must be stopped in such way: it must
 * be interrupted by calling {@link #interrupt()} and then joined using
 * {@link #join()} method. IMCSender cannot be restarted.
 */
public class IMCSender extends Thread {

    // IMC connection with the client
    private final IMCConnection imcClient;

    // Queue of strings to send
    private final Queue<String> stringsQueue = new LinkedList<>();

    /**
     * Creates IMCSender over the specified IMC connection.
     *
     * @param client opened IMC connection
     * @throws NullPointerException when client is null
     */
    public IMCSender(IMCConnection client) {
        Objects.requireNonNull(client, "Client cannot be null");

        this.imcClient = client;
    }

    /**
     * Method to send string asynchronously to the client. Strings are sent as
     * is, line feed will not be appended while sending it. Calling this method
     * does not block the thread. If the sender has been interrupted or
     * connection has already been closed, string is not sent and is lost.
     *
     * @param string the string to send to the client
     * @throws NullPointerException when string is null
     */
    public void send(String string) {
        // Checking string is not null
        Objects.requireNonNull(string, "String to send cannot be null!");

        // Checking that thread has not been interrupted and is currently
        // running
        if (!isInterrupted() && isAlive()) {
            synchronized (stringsQueue) {
                // Adding a string into the queue to send. It is not needed to
                // make a copy of it, because String objects are immutable
                stringsQueue.add(string);

                // Notifying the sleeping thread that there is data to send
                stringsQueue.notify();
            }
        }
    }

    /**
     * Sending data to the IMC client. Method blocks when there is no data to
     * send and finishes execution when this sender has been interrupted by
     * calling {@link #interrupt()}.
     */
    @Override
    public void run() {
        // Opening data stream to send data to the client 
        try (BufferedWriter clientStream = new BufferedWriter(new OutputStreamWriter(imcClient.openOutputStream()))) {
            // Waiting for new messages to appear until thread is interrupted
            while (!isInterrupted()) {
                String stringToSend;
                synchronized (stringsQueue) {
                    // Waiting for data to appear if queue is empty
                    while (stringsQueue.isEmpty()) {
                        stringsQueue.wait();
                    }
                    // Getting first string in the queue to send it
                    stringToSend = stringsQueue.poll();
                }

                // Writing data to the client
                clientStream.write(stringToSend);
                clientStream.flush();
            }
        } catch (IOException e) {
            System.out.println("Failed to send via IMC: " + e.getMessage());
        } catch (InterruptedException e) {
            // Sender has been interrupted. It will stop to send data
        }
        // run() method stops after thread has been interrupted or an error has
        // occurred
    }
}
