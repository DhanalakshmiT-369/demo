package induction;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class Table{
    static DefaultTableModel createModel(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Train_Id");
        model.addColumn("Train_Name");
        model.addColumn("Fitness_Certificate");
        model.addColumn("Job_Card");
        model.addColumn("Branding");
        model.addColumn("Maintenance_Balance");
        model.addColumn("Crew_Assigned");

       try{ 
        Connection con = DBConnector.getDBConnection();
        Statement stmt = con.createStatement();
        String querySort = "SELECT * FROM TRAIN_INFO ORDER BY PRIORITY_SCORE DESC;";

        ResultSet rs = stmt.executeQuery(querySort);
        while(rs.next()){
            model.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(8)});

        }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    return model;
    }
}
