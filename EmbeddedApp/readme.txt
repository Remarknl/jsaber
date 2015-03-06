#
# Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
#

DataForwarderSample v1.0
------------------------

Overview:
---------
This sample demonstrates the following functionality:
- MEEP IMC
- MEEP Events
- GCF Server Socket

Sample shows how to:
- listen for incoming TCP connections
- register Application to be started when specific event occurs
- post user-defined events
- establish IMC connection and send data via it

Sample forwards text data received from a connected TCP client to a set of Applications, which are interested in it. The sample includes three Applications. DataForwarderSample Application (the main one) uses an event to notify listening Applications about TCP client connection. The listening Applications (OutputApplication, LineCounterApplication) connect to the Data Forwarder via IMC, receive the text data line by line and process it.

Target Platforms:
-----------------
Any platform with Applications concurrency, CLDC GCF, MEEP IMC and MEEP Events support. For example Java ME Embedded SDK, Raspberry PI, Freescale K70.

Sample Configuration:
---------------------
- JAD property "DataForwarderSample-TcpListeningPort" - server socket port to accept TCP client. Must be positive integer. If there is no property with such name then 64111 is used by default. Only one client at a time is supported.

- JAD property "MIDlet-Event-Launch-<N>:<class_name>;com.oracle.jmee.samples.dataforwarding.IMC_DATA_FORWARDER_STARTED" - a standard MEEP property, that specifies an Application (<class_name>) which is launched when IMC_DATA_FORWARDER_STARTED event is posted. There may be several properties with different consequent numbers <N> starting from 1. Default JAD file includes two such properties specified for OutputApplication and LineCounterApplication. For more information about this property please see MEEP EventManager class javadocs in MEEP specification.

How To Run:
-----------
There are two ways to try this sample:
- Compile the sample using the provided project files and run it from IDE;
- Install and run already compiled JAD/JAR files without IDE.

This sample requires various permissions. The provided JAR file has already been signed and can be installed and run out of box.

For information about application compilation, signing, installation, starting, etc., with and/or without IDE, - see Oracle Java ME Embedded documentation.

Behaviour Description:
----------------------
- The sample consists of the three Applications in a suite: DataForwarderSample, OutputApplication, LineCounterApplication.

    * DataForwarderSample is the main Application of the sample which should be started manually

    * Other two Applications are examples of how to communicate with the main Application. These Applications are specified (see "Sample Configuration" section) to be started after the com.oracle.jmee.samples.dataforwarding.IMC_DATA_FORWARDER_STARTED event is posted. They perform text data processing

    * All Applications print log messages to the standard output stream, thus output of the Applications can get mixed up. Log messages which have no prefix belong to the Data Forwarder Application, other messages are prefixed with the Application class name

- DataForwarderSample checks whether IMC, Events and GCF optional packages are supported

- When a new incoming TCP connection is established, DataForwarderSample starts an IMC server and posts "com.oracle.jmee.samples.dataforwarding.IMC_DATA_FORWARDER_STARTED" event with the string value containing IMC server name and version

- DataForwarderSample starts to read text data from the connected TCP client and forwards it to all IMC clients after at least one IMC client has been accepted. Data from the TCP client is not buffered, connected IMC clients will start receiving data that is available from the moment of the IMC connection

- When current TCP client connection is closed or when an error occurs during the transmission, existing IMC server and IMC client connections are closed. OutputApplication and LineCounterApplication stop execution. DataForwarderSample resumes to listen for a new TCP client.

- DataForwarderSample doesn't finish by itself, AMS should be used to stop it

- OutputApplication is an example of primitive data processor. Application is registered statically (in JAD file) to be started when IMC_DATA_FORWARDER_STARTED event is posted. It receives text data via IMC and prints it to the console line by line. OutputApplication terminates after either IMC connection is closed or any error has occurred during the transmission.

- LineCounterApplication is an example of primitive data processor. Application is registered statically (in JAD file) to be started when IMC_DATA_FORWARDER_STARTED event is posted. It receives text data via IMC and counts number of lines. LineCounterApplication terminates after either IMC connection is closed or any error has occurred during the transmission.

- It is possible to add more Applications which receive and process data from DataForwarderSample.

Example Output:
---------------
**********************************
* Data Forwarder Sample started! *
**********************************
Listening for a new TCP client on the 64111 port
Got TCP connection from: 127.0.0.1:5533
IMC server has been started!
Event com.oracle.jmee.samples.dataforwarding.IMC_DATA_FORWARDER_STARTED has been posted!
Starting to accept IMC clients...
OutputApplication has been started!
LineCounterApplication has been started!
Got new IMC connection. No identity information available
LineCounterApplication - Connected to the IMC server: imc://*:com.oracle.jmee.samples.dataforwarding.IMCDataForwarder:1.0;
Got new IMC connection. No identity information available
OutputApplication - Connected to the IMC server: imc://*:com.oracle.jmee.samples.dataforwarding.IMCDataForwarder:1.0;
LineCounterApplication - Currently there are 1 lines of text
OutputApplication - Read "test1" message
LineCounterApplication - Currently there are 2 lines of text
OutputApplication - Read "test2" message
TCP client has disconnected
Stopped to accept IMC clients
LineCounterApplication - In total there were 2 lines of text
LineCounterApplication has been destroyed!
IMC server has been stopped!
Listening for a new TCP client on the 64111 port
OutputApplication has been destroyed!
Got TCP connection from: 127.0.0.1:5540
IMC server has been started!
Event com.oracle.jmee.samples.dataforwarding.IMC_DATA_FORWARDER_STARTED has been posted!
Starting to accept IMC clients...
OutputApplication has been started!
LineCounterApplication has been started!
OutputApplication - Connected to the IMC server: imc://*:com.oracle.jmee.samples.dataforwarding.IMCDataForwarder:1.0;
Got new IMC connection. No identity information available
LineCounterApplication - Connected to the IMC server: imc://*:com.oracle.jmee.samples.dataforwarding.IMCDataForwarder:1.0;
Got new IMC connection. No identity information available
LineCounterApplication - Currently there are 1 lines of text
OutputApplication - Read "test3" message
LineCounterApplication - Currently there are 2 lines of text
OutputApplication - Read "test4" message
TCP client has disconnected
Stopped to accept IMC clients
OutputApplication has been destroyed!
LineCounterApplication - In total there were 2 lines of text
LineCounterApplication has been destroyed!
IMC server has been stopped!
Listening for a new TCP client on the 64111 port
Stopped to accept TCP clients: Socket was closed
Data Forwarder Sample destroyed!
********************************