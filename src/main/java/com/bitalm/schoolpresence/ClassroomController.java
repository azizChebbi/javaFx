package com.bitalm.schoolpresence;

import Classes.Message;
import DAO.Messages;
import DAO.Students;
import com.bitalm.schoolpresence.Controllers.ItemController;
import com.bitalm.schoolpresence.Controllers.PresenceController;
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
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ClassroomController implements Initializable,ClientInterface{
    public static String professor = "skander";
    public static String student = "Mohamed Aziz Chebbi";
    @FXML
    public VBox students;
    @FXML
    public VBox presence;
    @FXML
    public Text date;
    @FXML
    public Text professorname;
    @FXML
    public TextArea messagetextfield;
    @FXML
    public VBox messagescontainer;
    @FXML
    public Text selectedstudent;

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    //public static Client client ;
    private MyListener myListener;

    public void sendMessage(ActionEvent event) throws IOException {
        String text = messagetextfield.getText();
        messagetextfield.setText("");
        //System.out.println("Professor-client");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Messages.insertMessage(professor, student, text, timestamp);
        sendMessage(text,student);
        addMessageToTheView(text, professor);
    }
    @Override
    public void sendMessage(String message, String to) throws IOException {
        bufferedWriter.write(professor+"-"+to+"-"+message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public void setSelectedStudent(String std) throws IOException {
        messagescontainer.getChildren().clear();
        student = std;
        selectedstudent.setText(std);
        print(student) ;
        displayMessages();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // ------------------- set the classroom fields ----------------------------
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        date.setText(dtf.format(now));
        professorname.setText(professor);
        try {
            setSelectedStudent(student);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ---------------- start a new client socket -----------------------
        try {
            socket = new Socket("localhost", 1234);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(professor);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            // Pass the socket and give the client a username.
            //client = new Client(socket, professor);
            // Infinite loop to read and send messages.
            //client.listenForMessage();*/

        } catch (IOException e) {
            e.printStackTrace();
        }


        // ------------------- define the student item click function --------------
        myListener = new MyListener() {
            @Override
            public void onClickListener(String s) throws IOException {
                setSelectedStudent(s);
            }
        };
        try {
            List<String> studentslist = Students.getStudents();
            for (int i = 0; i < studentslist.size(); i++) {
                // loading the student list item
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                System.out.println(itemController);
                itemController.setData(studentslist.get(i), myListener);

                students.getChildren().add(anchorPane);
                // loading the presence list item
            }
            for (int i = 0; i < studentslist.size(); i++) {
                // loading the student list item
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("student_item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                PresenceController itemController = fxmlLoader.getController();
                System.out.println(studentslist.get(i));
                itemController.setData(studentslist.get(i));
                presence.getChildren().add(anchorPane);
                // loading the presence list item
            }
            listenForMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayMessages() throws IOException {
        List<Message> messageslist = Messages.getMessages(professor, student);
        for (Message m : messageslist) {
            addMessageToTheView(m.message, m.sender);
        }
    }

    private void addMessageToTheView(String message, String sender) throws IOException {
        if(sender.equals(professor)){
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
                            if(msg[0].equals(student)){
                                print("its equal");
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
                } catch (IOException e) {
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

