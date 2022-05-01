package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Students {
    public static List<String> getStudents(){
        List<String> students = new ArrayList<>() ;
        Connection conn = DataBaseConnection.getConnection();
        String query = String.format("select * from students") ;
        try {
            Statement st = conn.createStatement() ;
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                String userName = rs.getString("name");
                students.add(userName) ;
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  students ;
    }
}
