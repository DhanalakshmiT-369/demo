package induction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class CrewTable {
    static DefaultTableModel createModel() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Role");
        model.addColumn("Contact");
        model.addColumn("Past Shift");
        model.addColumn("Current Shift");
        model.addColumn("Future Shift");
        model.addColumn("Train ID"); 

        try {
            Connection con = DBConnector.getDBConnection();
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM crew ORDER BY id ASC;";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getString("contact"),
                        rs.getString("past_shift"),
                        rs.getString("current_shift"),
                        rs.getString("future_shift"),
                        rs.getInt("train_id")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }
}
