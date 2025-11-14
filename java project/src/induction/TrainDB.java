package induction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class TrainDB {
    //--------------------------------------ADDING DATA--------------------------------------
    //-------------------ADDING TRAIN-------------------
    static void addTrain(int trainId, String trainName, String fitnessStatus, String jobCardStatus, String brandingStatus, double maintenanceBalance) {
        String queryTrainAdd = "INSERT INTO TRAIN_INFO (TRAIN_ID,TRAIN_NAME,FITNESS_CERTIFICATE,JOB_CARD_STATUS,BRANDING_STATUS,MILAGE_BALANCE,PRIORITY_SCORE,CREW_STATUS) VALUES(?,?,?,?,?,?,?,?)";
        int pScore = PriorityScore.calPriorityScore(fitnessStatus, jobCardStatus, brandingStatus, maintenanceBalance);
        try {
            Connection con = DBConnector.getDBConnection();
            PreparedStatement pst = con.prepareStatement(queryTrainAdd);
            pst.setInt(1, trainId);
            pst.setString(2, trainName);
            pst.setString(3, fitnessStatus);
            pst.setString(4, jobCardStatus);
            pst.setString(5, brandingStatus);
            pst.setDouble(6, maintenanceBalance);
            pst.setInt(7, pScore);
            pst.setString(8, null);
            pst.executeUpdate();
            System.out.println("TRAIN record added successfully!");
        } catch (Exception ex) {
            System.out.println("Cannot add data!");
        }
    }

    //-------------------ADD FITNESS CERTIFICATE-------------------
    static void getFitness(int trainId, String fitnessStatus, Date issuance, Date expiry) {
        String query = "INSERT INTO FITNESS_CERTIFICATE(TRAIN_ID, FITNESS_CERTIFICATE_STATUS, DATE_OF_ISSUE, DATE_OF_EXPIRY) VALUES (?, ?, ?, ?)";
        try {
            Connection con = DBConnector.getDBConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, trainId);
            pst.setString(2, fitnessStatus);
            if (issuance != null) {
                pst.setDate(3, new java.sql.Date(issuance.getTime())); 
            }else {
                pst.setNull(3, java.sql.Types.DATE);
            }
            if (expiry != null) {
                pst.setDate(4, new java.sql.Date(expiry.getTime()));
            } else {
                pst.setNull(4, java.sql.Types.DATE);
            }
            pst.executeUpdate();
            pst.close();
            con.close();
            System.out.println("FITNESS record added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------ADD JOB CARD-------------------
    static void getJobCard(int trainId, String maintainTask, String jobCardStatus) {
        String query = "INSERT INTO JOB_CARD(TRAIN_ID,MAINTAINANCE_TASK,JOB_CARD_STATUS) VALUES (?,?,?)";
        try {
            Connection con = DBConnector.getDBConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, trainId);
            pst.setString(2, maintainTask);
            pst.setString(3, jobCardStatus);
            pst.executeUpdate();
            pst.close();
            con.close();
            System.out.println("JOBCARD record added successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //-------------------ADD MILEAGE-------------------
    static void getMileage(int trainId, int maintenance) {
        String query = "INSERT INTO MILEAGE(TRAIN_ID,MAINTAINANCE_INTERVAL,CURRENT_MILEAGE) VALUES (?,?,?)";
        try {
            Connection con = DBConnector.getDBConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, trainId);
            pst.setInt(2, maintenance);
            pst.setInt(3, 0);
            pst.executeUpdate();
            pst.close();
            con.close();
            System.out.println("MILAGE record added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------ADD BRANDING-------------------
    static void addBranding(int trainId, String brandingStatus, String brandingType, int brandingHours) {
        String query = "INSERT INTO BRANDING (TRAIN_ID, BRANDING_TYPE, BRANDING_HOURS, BRANDING_STATUS) VALUES (?, ?, ?, ?)";
        try {
            Connection con = DBConnector.getDBConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, trainId);
            pst.setString(2, brandingType);
            pst.setInt(3, brandingHours);
            pst.setString(4, brandingStatus);
            pst.executeUpdate();
            pst.close();
            con.close();
            System.out.println("Branding record added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot add branding record!");
        }
    }

    //--------------------------------------UPDATE PRIORITY SCORE--------------------------------------
    static void updatePriorityScore(int trainId) {
    Connection con = null;
    PreparedStatement fetchStmt = null;
    PreparedStatement updateStmt = null;
    ResultSet rs = null;
    try {
        con = DBConnector.getDBConnection();
        String fetchQuery = "SELECT FITNESS_CERTIFICATE, JOB_CARD_STATUS, BRANDING_STATUS, MILAGE_BALANCE FROM TRAIN_INFO WHERE TRAIN_ID = ?";
        fetchStmt = con.prepareStatement(fetchQuery);
        fetchStmt.setInt(1, trainId);
        rs = fetchStmt.executeQuery();

        if (rs.next()) {
            String fitness = rs.getString("FITNESS_CERTIFICATE");
            String job = rs.getString("JOB_CARD_STATUS");
            String branding = rs.getString("BRANDING_STATUS");
            double balance = rs.getDouble("MILAGE_BALANCE");

            int newScore = PriorityScore.calPriorityScore(fitness, job, branding, balance);

            String updateQuery = "UPDATE TRAIN_INFO SET PRIORITY_SCORE = ? WHERE TRAIN_ID = ?";
            updateStmt = con.prepareStatement(updateQuery);
            updateStmt.setInt(1, newScore);
            updateStmt.setInt(2, trainId);
            updateStmt.executeUpdate();

            System.out.println("Priority score updated for train " + trainId);
        } else {
            System.out.println("Train not found.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    //--------------------------------------UPDATE DATA--------------------------------------
    //-------------------UPDATE MILAGE-------------------
    static void mileageUp(int trainId, int maintenance) {
        String query = "UPDATE MILEAGE SET MAINTAINANCE_INTERVAL = ?, CURRENT_MILEAGE = ? WHERE TRAIN_ID = ?";
        try {
            Connection con = DBConnector.getDBConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(3, trainId);
            pst.setInt(1, maintenance);
            pst.setInt(2, 0);
            pst.executeUpdate();
            pst.close();
            con.close();
            updatePriorityScore(trainId);
            System.out.println("MILAGE record Updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------UPDATE JOB CARD-------------------
    static void jobCardUp(int trainId, String maintainTask, String jobCardStatus) {
        String query = "UPDATE JOB_CARD SET MAINTAINANCE_TASK = ?, JOB_CARD_STATUS = ? WHERE TRAIN_ID = ?";
        try {
            Connection con = DBConnector.getDBConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(3, trainId);
            pst.setString(1, maintainTask);
            pst.setString(2, jobCardStatus);
            pst.executeUpdate();
            pst.close();
            con.close();
            updatePriorityScore(trainId);
            System.out.println("JOBCARD record added successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //-------------------UPDATE FITNESS CERTIFICATE-------------------
    static void fitnessUp(int trainId, String fitnessStatus, Date issuance, Date expiry) {
        String query = "UPDATE FITNESS_CERTIFICATE SET FITNESS_CERTIFICATE_STATUS = ?, DATE_OF_ISSUE = ?, DATE_OF_EXPIRY = ? WHERE TRAIN_ID = ?";
        try {
            Connection con = DBConnector.getDBConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(4, trainId);
            pst.setString(1, fitnessStatus);
            pst.setDate(2, new java.sql.Date(issuance.getTime()));
            pst.setDate(3, new java.sql.Date(expiry.getTime()));
            pst.executeUpdate();
            pst.close();
            con.close();
            updatePriorityScore(trainId);
            System.out.println("FITNESS record added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------UPDATE BRANDING-------------------
    static void brandingUp(int trainId, String brandingStatus, String brandingType, int brandingHours) {
        String query = "UPDATE BRANDING SET BRANDING_TYPE = ?, BRANDING_HOURS = ?, BRANDING_STATUS = ? WHERE TRAIN_ID = ?";
        try {
            Connection con = DBConnector.getDBConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(4, trainId);
            pst.setString(1, brandingType);
            pst.setInt(2, brandingHours);
            pst.setString(3, brandingStatus);
            pst.executeUpdate();
            pst.close();
            con.close();
            updatePriorityScore(trainId);
            System.out.println("Branding record added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot add branding record!");
        }
    }
}

