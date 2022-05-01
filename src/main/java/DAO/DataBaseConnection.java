package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    static Connection conn = null ;

    public static Connection getConnection(){
        if(conn != null) return conn ;
        String url = "jdbc:mysql://localhost:3306/users";
        String user = "root" ;
        String password = "" ;
        return getConnection(url,user,password) ;
    }

    private static Connection getConnection(String url,String user,String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("Connected successfully!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void disconnectFromDataBase(){
        try {
            conn.close();
            System.out.println("Disconnected from database!") ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
