package com.bitalm.schoolpresence;

import Classes.Message;
import DAO.Messages;
import com.bitalm.schoolpresence.Controllers.ReceiverController;
import com.bitalm.schoolpresence.Controllers.SenderController;
import com.bitalm.schoolpresence.chatting.Client;
import com.bitalm.schoolpresence.chatting.ClientInterface;
import com.bitalm.schoolpresence.chatting.Msg;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class StudentChat implements Initializable, ClientInterface {

    @FXML
    public TextArea messagetextfield;
    @FXML
    public VBox messagescontainer;
    @FXML
    public Text studentname ;
    @FXML
    public Text date ;
    @FXML
    public Text selectedprofessor ;

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public static String professor = "skander" ;
    public static String student="Mohamed Aziz Chebbi" ;

    public void sendMessage(ActionEvent event) throws IOException {
        String text = messagetextfield.getText() ;
        messagetextfield.setText("");
        //System.out.println("Student-Client: "+text);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Messages.insertMessage(student,professor,text,timestamp);
        sendMessage(text,professor);
        addMessageToTheView(text,student);
    }
    @Override
    public void sendMessage(String message, String to) throws IOException {
        bufferedWriter.write(student+"-"+to+"-"+message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    private void displayMessages() throws IOException {
        List<Message> messageslist = Messages.getMessages(professor,student);
        for(Message m : messageslist){
            addMessageToTheView(m.message,m.sender);
        }
    }
    private void addMessageToTheView(String message,String sender) throws IOException {
        if(sender.equals(student)){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("sender_item.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            SenderController senderController = fxmlLoader.getController();
            senderController.setMessage(message);
            messagescontainer.getChildren().add(anchorPane);
        }else{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("receiver_item.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            ReceiverController senderController = fxmlLoader.getController();
            senderController.setMessage(message);
            messagescontainer.getChildren().add(anchorPane);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // ------------------- set the classroom fields ----------------------------
        studentname.setText(student);
        selectedprofessor.setText("Skander Abdallah");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        date.setText(dtf.format(now));

        // ---------------- start a new client socket -----------------------
        try {
            socket = new Socket("localhost", 1234);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(student);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            displayMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listenForMessage();
    }

    // Sending a message isn't blocking and can be done without spawning a thread,
    // unlike waiting for a message.


    public void print(String s){
        System.out.println(s);
    }
    // Listening for a message is blocking so need a separate thread for that.
    @Override
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // While there is still a connection with the server, continue to listen for messages on a separate thread.
                    try {
                        while (socket.isConnected()) {
                        String messageFromClient = bufferedReader.readLine();
                        String[] msg = messageFromClient.split("-") ;
                        print("professor received this message from "+msg[0]+": "+msg[2]);
                        if(msg[0].equals(professor)){
                            Platform.runLater(()->{
                                try {
                                    addMessageToTheView(msg[2],msg[0]);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    }
                }catch (IOException e) {
                        e.printStackTrace();
                }
            }
        }).start();
    }


    // Helper method to close everything so you don't have to repeat yourself.
    @Override
    public void closeEverything() {
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
