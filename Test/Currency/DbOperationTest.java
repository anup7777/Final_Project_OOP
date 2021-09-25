package Currency;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DbOperationTest {

    @Test
    public void select() throws SQLException {
        String result;
        String enteredamount = "100";
        DbOperation db = new DbOperation();
        String query = "Select enteramount from converter where enteramount='100'";
        ResultSet rs = db.select(query);
        while (rs.next()) {
            result = rs.getString("enteramount");
            assertEquals(enteredamount, result);
        }

    }


    @Test
    public void insert() {
        String convertedamount = "178";
        DbOperation db = new DbOperation();
        String query = "Insert into converter(convertamount) values('" + convertedamount + "')";
        int row = db.insert(query);
        assertEquals(1, row);
    }
}