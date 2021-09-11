package Currency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbOperation {
    Connection con;
    Statement st;
    int val;
    ResultSet rows;
    int valuess;
    int values;

    public DbOperation(){
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop", "root", "root");
            if (con != null) {
                System.out.println("connected");
                st = con.createStatement();
//                  con.commit();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int insert(String query) {
        try {

            val = st.executeUpdate(query);
            con.commit();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }


        return val;
    }

    public ResultSet select(String query) {
        try {
            rows = st.executeQuery(query);
            con.commit();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return rows;

    }
    public static void main(String[]args){
        new DbOperation();
    }
}

