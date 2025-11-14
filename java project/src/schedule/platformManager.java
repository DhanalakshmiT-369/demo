package schedule;

import java.sql.*;
import java.util.*;

public class platformManager {

    public HashMap<String, ArrayList<Integer>> northPlatforms = new HashMap<>();
    public HashMap<String, ArrayList<Integer>> southPlatforms = new HashMap<>();
    public static HashMap<String, Integer> occupiedNorthPlatforms = new HashMap<>();
    public static HashMap<String, Integer> occupiedSouthPlatforms = new HashMap<>();
    
//    platformManager(){
//    	
//    }

    public int assign_platform( String currentStation,String direction,int arrival,int departure) {
        int assignedPlatform = -1;
        
//        northPlatforms.clear();
//        southPlatforms.clear();

        try (Connection con = DBManager.getDBconnection()) {

            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();

            ResultSet ns = stmt1.executeQuery("SELECT * FROM northplatforms");
            ResultSet ss = stmt2.executeQuery("SELECT * FROM southplatforms");
          
            while (ns.next()) {
                String station = ns.getString("station");
                int platformNo = ns.getInt("platform_no");
                northPlatforms.computeIfAbsent(station, k -> new ArrayList<>()).add(platformNo);
            }

            while (ss.next()) {
                String station = ss.getString("station");
                int platformNo = ss.getInt("platform_no");
                southPlatforms.computeIfAbsent(station, k -> new ArrayList<>()).add(platformNo);
            }

            if (direction.equalsIgnoreCase("north")) {
                ArrayList<Integer> list = northPlatforms.get(currentStation);
                for (int platformNo : list) {
                	String key = currentStation + "_" + platformNo;
                    if (!occupiedNorthPlatforms.containsKey(key)) {
                         assignedPlatform = platformNo;
                         occupiedNorthPlatforms.put(key,departure);
                         break;
                    	
                    }
                    else if (occupiedNorthPlatforms.containsKey(key)) {
                    if((occupiedNorthPlatforms.get(key)<arrival) ) {
                        assignedPlatform = platformNo;
                        occupiedNorthPlatforms.put(key, departure); 
                        break; 
                    }
                    }
                }
            }
            else if (direction.equalsIgnoreCase("south")) {
                ArrayList<Integer> list = southPlatforms.get(currentStation);
                for (int platformNo : list) {
                	String key = currentStation + "_" + platformNo;
                	 if (!occupiedSouthPlatforms.containsKey(key)) {
                         assignedPlatform = platformNo;
                         occupiedSouthPlatforms.put(key,departure );
                         break;
                    	
                    }
                    else if (occupiedSouthPlatforms.containsKey(key)) {
                    if((occupiedSouthPlatforms.get(key)<arrival) ) {
                        assignedPlatform = platformNo;
                        occupiedSouthPlatforms.put(key, departure);  
                        break; 
                    }
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return assignedPlatform;
    }
    
    public ArrayList<Integer> getNorthPlatforms(String getstation) {
    	return northPlatforms.get(getstation);
    }
    public ArrayList<Integer> getSouthPlatforms(String getstation) {
    	return southPlatforms.get(getstation);
    }
    
}

