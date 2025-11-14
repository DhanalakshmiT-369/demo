package induction;

import java.sql.Connection;
import java.sql.Statement;


public class DeleteTrain {
    static void delTrain(int trainId){
        try {
            Connection con =DBConnector.getDBConnection();
            String query1 = "DELETE FROM TRAIN_INFO WHERE TRAIN_ID = "+trainId;
            String query2 = "DELETE FROM BRANDING WHERE TRAIN_ID = "+trainId;
            String query3 = "DELETE FROM FITNESS_CERTIFICATE WHERE TRAIN_ID = "+trainId;
            String query4 = "DELETE FROM JOB_CARD WHERE TRAIN_ID = "+trainId;
            String query5 = "DELETE FROM MILEAGE WHERE TRAIN_ID = "+trainId;
            Statement stmt1 = con.createStatement();
            stmt1.executeUpdate(query1);
            stmt1.executeUpdate(query2);
            stmt1.executeUpdate(query3);
            stmt1.executeUpdate(query4);
            stmt1.executeUpdate(query5);
        } catch (Exception e) {
            System.out.println("Delete not happening ;-;");
        }
        
    }

}