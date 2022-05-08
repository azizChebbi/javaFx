package com.bitalm.schoolpresence.chatting;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * When a client connects the server spawns a thread to handle the client.
 * This way the server can handle multiple clients at the same time.
 *
 * This keyword should be used in setters, passing the object as an argument,
 * and to call alternate constructors (a constructor with a different set of
 * arguments.
 */

// Runnable is implemented on a class whose instances will be executed by a thread.
public class ClientHandler implements Runnable {

    // Array list of all the threads handling clients so each message can be sent to the client the thread is handling.
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    // Id that will increment with each new client.

    // Socket for a connection, buffer reader and writer for receiving and sending data respectively.
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    private ObjectOutputStream objectOutputStream ;
    private ObjectInputStream objectInputStream;

    // Creating the client handler from the socket the server passes.
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            //this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            //this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // When a client connects their username is sent.
            this.clientUsername = bufferedReader.readLine();
            System.out.println("Hello "+this.clientUsername);
            // Add the new client handler to the array so they can receive messages from others.
            clientHandlers.add(this);
            //sendMessage("SERVER: " + clientUsername + " has entered the chat!");
        } catch (IOException e) {
            // Close everything more gracefully.
            closeEverything();
        }
    }

    // Everything in this method is run on a separate thread. We want to listen for messages
    // on a separate thread because listening (bufferedReader.readLine()) is a blocking operation.
    // A blocking operation means the caller waits for the callee to finish its operation.
    @Override
    public void run() {

        // Continue to listen for messages while a connection with the client is still established.
        try{
            while (true) {
                    // Read what the client sent and then send it to every other client.
                    String messageFromClient = bufferedReader.readLine();
                    System.out.println(this.clientUsername+" Handler: "+messageFromClient);
                    sendMessage(messageFromClient);
                    //sendMessage(messageFromClient,"Mohamed aziz chebbi");
                    //Msg m = (Msg) objectInputStream.readObject();
                    //sendMessage(m);
            }
        } catch (IOException e) {
            // Close everything gracefully.
            closeEverything();
        }
    }

    // Send a message through each client handler thread so that everyone gets the message.
    // Basically each client handler is a connection to a client. So for any message that
    // is received, loop through each connection and send it down it.
    public void sendMessage(String message){
        String[] msg = message.split("-") ;
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                // You don't want to broadcast the message to the user who sent it.
                if (clientHandler.clientUsername.equals(msg[1])) {
                    //objectOutputStream.writeObject(m);
                    System.out.println(msg[1]+" is found");
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                System.err.println("error at send function in Client handler");
                e.printStackTrace();
                // Gracefully close everything.
                closeEverything();
            }
        }
    }

    // If the client disconnects for any reason remove them from the list so a message isn't sent down a broken connection.
    public void removeClientHandler() {
        clientHandlers.remove(this);
        //broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
    }

    // Helper method to close everything so you don't have to repeat yourself.
    public void closeEverything() {
        // Note you only need to close the outer wrapper as the underlying streams are closed when you close the wrapper.
        // Note you want to close the outermost wrapper so that everything gets flushed.
        // Note that closing a socket will also close the socket's InputStream and OutputStream.
        // Closing the input stream closes the socket. You need to use shutdownInput() on socket to just close the input stream.
        // Closing the socket will also close the socket's input stream and output stream.
        // Close the socket after closing the streams.

        // The client disconnected or an error occurred so remove them from the list so no message is broadcasted.
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
