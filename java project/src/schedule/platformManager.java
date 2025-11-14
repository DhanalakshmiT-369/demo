/*package schedule;

import java.sql.*;
import java.util.*;

public class platformManager {
	
	HashMap<String,ArrayList<Integer>> northPlatforms=new HashMap<>();
	HashMap<String,ArrayList<Integer>> southPlatforms=new HashMap<>();
	ArrayList<Integer> values=new ArrayList<>();
	HashMap<String,Integer> ocupiednorthPlatforms=new HashMap<>();
	HashMap<String,Integer> ocupiedsouthPlatforms=new HashMap<>();
	int AssignedPlatform;
	
	public int assign_platform(ArrayList<String> stationsSelected,String current_station,String direction) {
		
		try {
			Connection con=DBManager.getDBconnection();
			Statement stmt=con.createStatement();
			
			ArrayList<Integer> northPlatformList = new ArrayList<>();
			ArrayList<Integer> southPlatformList = new ArrayList<>();
			
			ResultSet rs=stmt.executeQuery("select *from platforms");
			ResultSet ns=stmt.executeQuery("select *from northplatforms");
			ResultSet ss=stmt.executeQuery("select *from northplatforms");
			
			while(rs.next()) {
               String northstation = ns.getString("station");
               int northplatformNo = ns.getInt("platform_no");
               String southstation = ss.getString("station");
               int southplatformNo = ss.getInt("platform_no");

                              
               if (stationsSelected.contains(northstation) && direction.equalsIgnoreCase("north")){
				
            	   if (!northPlatforms.containsKey(northstation)) {
                       northPlatformList.add(northplatformNo);
                       northPlatforms.put(northstation, northPlatformList);
                   } else {
                      
                       northPlatforms.get(northstation).add(northplatformNo);
                   }
				}
               
               if (stationsSelected.contains(southstation) && direction.equalsIgnoreCase("south")){
   				
            	   if (!northPlatforms.containsKey(southstation)) {                      
                       southPlatformList.add(southplatformNo);
                       northPlatforms.put(southstation, southPlatformList);
                   } else {
                      
                       northPlatforms.get(southstation).add(southplatformNo);
                   }
				}
			}
			
			if(direction.equalsIgnoreCase("north")) {
			      for(String station_name:northPlatforms.keySet()) {
			    	  ArrayList<Integer> list=northPlatforms.get(station_name);
			    	  
				        for(int listNo:list) {
				        	if(!ocupiednorthPlatforms.containsKey(listNo)) {
				        		AssignedPlatform=listNo;
				        		return AssignedPlatform;
				        		break;
				        	}
				        }
			      }
			}
			if(direction.equalsIgnoreCase("south")) {
				for(String station_name:northPlatforms.keySet()) {
			    	  ArrayList<Integer> list=southPlatforms.get(station_name);
			    	  
				        for(int listNo:list) {
				        	if(!ocupiednorthPlatforms.containsKey(listNo)) {
				        		AssignedPlatform=listNo;
				        		return AssignedPlatform;
				        		break;
				        	}
				        }
			      }
			}
			
			return AssignedPlatform;
			
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
	}

}
*/
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
        
        northPlatforms.clear();
        southPlatforms.clear();


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

