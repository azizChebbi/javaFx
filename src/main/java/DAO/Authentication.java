package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Authentication {
    public static void signInStudent(String name,String cin) throws UserNotFoundException{
        String err = "Student is not found" ;
        try {
            signIn("students",cin);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(err) ;
        }
    }
    public static void signInProfessor(String name,String cin) throws  UserNotFoundException{
        String err = "Professor is not found" ;
        try {
            signIn("professors",cin);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(err) ;
        }
    }

    private static void signIn(String table,String cin) throws UserNotFoundException{
        Connection conn = DataBaseConnection.getConnection();
        String query = String.format("SELECT * FROM %s where cin='%s'",table,cin) ;
        try {
            Statement st = conn.createStatement() ;
            ResultSet rs = st.executeQuery(query);
            if(rs.next() == false) {
                throw new UserNotFoundException("User Not Found");
            }else{
                do{
                    String userName = rs.getString("name");
                    System.out.format("Hello %s\n", userName);
                }while (rs.next()) ;
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class UserNotFoundException extends Exception{
        public UserNotFoundException(String err){
            super(err) ;
        }
    }
}
