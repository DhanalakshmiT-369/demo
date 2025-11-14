package schedule;

import java.sql.*;
import java.util.*;

public class Conflicts {
	private String arrivalT;
	private String departureT;
	private int arrivalTime;
	private int departureTime;
	private int platform;
	private String arrivalInTime;
	private String departureInTime;
	
	platformManager assingingPlatform=new platformManager();
	
    public boolean conflictscheck(int selectedTrain,String currentStation,int arrival,int departure,int Currentplatform) {
    	   	
    	try {
    		Connection con=DBManager.getDBconnection();
    		Statement stmt=con.createStatement();
    		
    		ResultSet rs=stmt.executeQuery("select *from  scheduledtrains");
    		
    		while(rs.next()) {
    			int train=rs.getInt("train_id");
    			String station=rs.getString("stations");
    			arrivalT=rs.getString("arrivaltime");
    			departureT=rs.getString("departuretime");
    			platform=rs.getInt("platform");
    			
//    			if(String.valueOf(train).equalsIgnoreCase("NUll")) {
//    				return true;

    			int arrivalTime = convertTime(arrivalT);
    			int departureTime = convertTime(departureT);
 			
    			
    			if(train!=selectedTrain) {
    				boolean isOverlapping = (arrival < departureTime && departure > arrivalTime);

    				if (station.equalsIgnoreCase(currentStation) && Currentplatform == platform && isOverlapping) {
    				    return true; 
    				}
    			}
    		}
    	}

    	catch(SQLException ex){
    		ex.printStackTrace();
    	}
    	
    	return false;
    }
    
    public void conflictsManagement(int selectedTrain,String currentStation,int arrival,int departure,int Currentplatform,String direction){
    
    	try {
    	Connection con=DBManager.getDBconnection();
		Statement stmt2=con.createStatement();
		
		ResultSet rs=stmt2.executeQuery("select *from  scheduledtrains");
		
		while(rs.next()) {
			arrivalT=rs.getString("arrivaltime");
			departureT=rs.getString("departuretime");
			platform=rs.getInt("platform");
			
			int arrivalTime = convertTime(arrivalT);
			int departureTime = convertTime(departureT);
			
			int haltTime=departure-arrival;
			
			if(direction.equalsIgnoreCase("north")){

    	    	ArrayList<Integer> list = assingingPlatform.getNorthPlatforms(currentStation);
    	    	
                for (int platformNo : list) {
                	String key = currentStation + "_" + platformNo;
                	
                	if(platformNo!=Currentplatform && platformManager.occupiedNorthPlatforms.containsKey(key)) {
                         	if((platformManager.occupiedNorthPlatforms.get(key)<arrival) ) {
                             
                		      platform=platformNo;
    	    	    	      break;
                         	}
                	}
                	else if(platformNo!=Currentplatform && !platformManager.occupiedNorthPlatforms.containsKey(key)) {
                            
            		      platform=platformNo;
	    	    	      break;
            	    }
    	    	    else {
    	    	       arrivalTime=departure;
			           departureTime=arrivalTime+haltTime;
			           break;
    	    	    }
    	        }
		    }
			if(direction.equalsIgnoreCase("south")) {

    	    	ArrayList<Integer> list = assingingPlatform.getSouthPlatforms(currentStation);
                for (int platformNo : list) {
                	String key = currentStation + "_" + platformNo;
                	
                	if(platformNo!=Currentplatform && platformManager.occupiedSouthPlatforms.containsKey(key)) {
                     	if((platformManager.occupiedSouthPlatforms.get(key)<arrival) ) {
                         
            		      platform=platformNo;
	    	    	      break;
                     	}
            	}
            	else if(platformNo!=Currentplatform && !platformManager.occupiedSouthPlatforms.containsKey(key)) {
                        
        		      platform=platformNo;
    	    	      break;
        	    }
                else {
    	    	    arrivalTime=departure;
			        departureTime=arrivalTime+haltTime;
			        break;
                	}
    	       }
		    }
			
			int arrivalinhours=arrivalTime/60;
			int arrivalinmin=arrivalTime%60;
			int departureinhours=departureTime/60;
			int departureinmin=departureTime%60;
			
			arrivalInTime=arrivalinhours+":"+arrivalinmin;
			departureInTime=departureinhours+":"+departureinmin;
    	}
    	}
    	catch(SQLException ex) {
    		ex.printStackTrace();
    	}                                     
    }
    
    private int convertTime(String t) {

        if(t == null || t.trim().isEmpty() || t.equals("0:0"))
            return 0;

        String[] parts = t.split(":");

        int hr = Integer.parseInt(parts[0]);
        int min = Integer.parseInt(parts[1]);

        return hr * 60 + min;  
    }

    
    public String getAdjustedArrival() { 
    	return arrivalInTime; 
    	}
    public String getAdjustedDeparture() {
    	return departureInTime; 
    	}
    public int getAdjustedPlatform() {
    	return platform; 
    	}
}

