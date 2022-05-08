package Classes;

import java.sql.Time;
import java.sql.Timestamp;

public class Message {
    public String sender ;
    public String receiver ;
    public String message;
    public Timestamp date ;

    public Message(String sender, String receiver, String message, Timestamp date) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date = date;
    }
}
