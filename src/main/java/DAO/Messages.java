package DAO;

import Classes.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Messages {

    public static List<Message> getMessages(String person1,String person2){
        List<Message> messages = new ArrayList<>() ;
        Connection conn = DataBaseConnection.getConnection();
        String query = String.format("select * from messages where (sender='%s' and receiver='%s') or (sender='%s' and receiver='%s')",person1,person2,person2,person1) ;
        try {
            Statement st = conn.createStatement() ;
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                String sender = rs.getString("sender");
                String receiver = rs.getString("receiver");
                String message = rs.getString("message");
                Timestamp date = rs.getTimestamp("date") ;
                Message m = new Message(sender,receiver,message,date) ;
                messages.add(m) ;
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages ;
    }

    public static void insertMessage(String sender,String receiver,String message,Timestamp date){
        Connection conn = DataBaseConnection.getConnection();
        String query = String.format("insert into messages (sender,receiver,message,date) values('%s','%s','%s','%s')",sender,receiver,message,date.toString()) ;
        Statement st = null;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
            st.close() ;
            System.out.println("message is inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
