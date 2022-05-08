package com.bitalm.schoolpresence;

import java.net.Socket;
import com.bitalm.schoolpresence.chatting.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    public Client client ;
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        Parent fxmll = FXMLLoader.load(getClass().getResource("Welcome.fxml")) ;
        Scene scene = new Scene(fxmll);
        stage.setTitle("Welcome to Classroom 51");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        // connect to the server
        // Get a username for the user and a socket connection.
        //Scanner scanner = new Scanner(System.in);
        //System.out.print("Enter your username for the group chat: ");
        //String username = scanner.nextLine();
        //String username = "professor" ;
        // Create a socket to connect to the server.
        /*Socket socket = new Socket("localhost", 1234);
        // Pass the socket and give the client a username.
        client = new Client(socket, username);
        // Infinite loop to read and send messages.
        client.listenForMessage();*/
        // client.sendMessage();
    }

    @Override
    public void stop() throws Exception {
        client.closeEverything();
    }

    public static void main(String[] args) {
        launch();
    }
}