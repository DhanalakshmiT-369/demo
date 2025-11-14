package schedule;

import java.sql.SQLException;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import optimization.TrainRouteManager;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class TimeTabl extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTable schedule;
	int averageSpeed=35;
	int starttime,timediff,number;
	JButton bcontinue, bexit;

	ArrayList<String> optimizedStations=new ArrayList<>();
	HashMap<String,Integer> stationConnections=new HashMap<>(); 
	HashMap<String,String> stationDirections = new HashMap<>();
	
	public TimeTabl(int TimeDiff,ArrayList<Integer> waitingTime,int selectedTrain,int startTime,String startStation,String endStation,int noOfTrains) {
		starttime=startTime+TimeDiff;
		timediff=TimeDiff;
		number=noOfTrains;
	try {
		Connection con=DBManager.getDBconnection();
		Statement stmt=con.createStatement();
		Statement stmt1=con.createStatement();
		Statement stmt2=con.createStatement();
		
		ResultSet rs=stmt.executeQuery("select *from stations_connections");
		ResultSet rs2=stmt2.executeQuery("select *from  station_directions");

		while (rs.next()) {
		    String key = rs.getString("source_station") + "-" + rs.getString("destination_station");
		    int distance = rs.getInt("distance");
		    stationConnections.put(key, distance);
		}
		
		ResultSet rs1=stmt1.executeQuery("select *from  optimized_roots");
		optimizedStations.clear();
		
		while (rs1.next()) {
			int train_id=rs1.getInt("train_id");
			if(train_id==selectedTrain) {
		    String optimizedRoot = rs1.getString("optimized_path");

		    StringTokenizer st = new StringTokenizer(optimizedRoot, ",");
		    while (st.hasMoreTokens()) {
		        optimizedStations.add(st.nextToken().trim());
		    }
			}
		}

		while (rs2.next()) {
		    stationDirections.put(rs2.getString("station"), rs2.getString("direction_located"));
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 496, 424);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel selectedtrain = new JLabel("Train no-"+selectedTrain);
		selectedtrain.setFont(new Font("Tahoma", Font.PLAIN, 12));
		selectedtrain.setBounds(199, 7, 96, 12);
		contentPane.add(selectedtrain);
		
		
		DefaultTableModel model=new DefaultTableModel(
				new Object[][] {},
		        new String[] {"Stations", "Arrival Time", "Departure Time", "Platform"}
				); 
		schedule=new JTable(model);
		JScrollPane ttscrollpane = new JScrollPane(schedule);
		ttscrollpane.setBounds(10, 29, 462, 318);
		contentPane.add(ttscrollpane);
		
//		PreparedStatement deleteOld = con.prepareStatement("DELETE FROM optimized_roots WHERE train_id = ?");
//        deleteOld.setInt(1, selectedTrain);
//        deleteOld.executeUpdate();
        
		int startingtime=startTime;
		int arrival=0;
	    int departure=0;
		
        stmt.execute("create table if not exists ScheduledTrains(train_id int,arrivaltime int,departuretime int,platform int,stations varchar(30))");      
        model.setRowCount(0);
        platformManager platmanager=new platformManager(); 
        
		for(int i=0;i<optimizedStations.size();i++) {
			String currentStation = optimizedStations.get(i);
			String previousStation="";
			
			if (i == 0) {
		        arrival = startingtime;
		        departure = arrival + waitingTime.get(i);
		   
		    } 
		    else {
			    previousStation= optimizedStations.get(i - 1);
			    
			    for(String str: stationConnections.keySet()) {
			    	
			    	String start_NextStations = previousStation + "-" + currentStation;
			    	
			    	if(start_NextStations.equalsIgnoreCase(str)) {
			    		
			    	
		            int dis=stationConnections.get(str);
		    
		            Double distance= Double.valueOf(dis);
		            
		            Double timeHours=(distance/averageSpeed);
		            int timeMinutes=(int)Math.round(timeHours*60);
		        
		            startingtime=startingtime+timeMinutes;
		            arrival=startingtime;
		            departure=startingtime+waitingTime.get(i);

		           }
			    }
		    }
        // previous station == starting station
		String direction="";
		if (i == 0) {			
			direction = stationDirections.get(currentStation);  
		} 
		else {
			
		String currentDirection = stationDirections.get(currentStation);
		String previousDirection = stationDirections.get(previousStation);
			    	
		if(previousDirection.equalsIgnoreCase("north") && (!currentDirection.equalsIgnoreCase("north")) ) {
			direction="south";
		}
		else if(previousDirection.equalsIgnoreCase("south") && (!currentDirection.equalsIgnoreCase("south")) ) {
			direction="north";
		}
		else if(previousDirection.equalsIgnoreCase("east")) {
			if(currentDirection.equalsIgnoreCase("west") || currentDirection.equalsIgnoreCase("north")) {
			direction="north";
		    }
			else if(currentDirection.equalsIgnoreCase("south")) {
				direction="south";
			}
		}
		else if(previousDirection.equalsIgnoreCase("west")) {
			if(currentDirection.equalsIgnoreCase("east") || currentDirection.equalsIgnoreCase("south")) {
			direction="south";
			}
			else if(currentDirection.equalsIgnoreCase("north")) {
				direction="north";
			}
		}
		if(previousDirection.equalsIgnoreCase(currentDirection)) {
			direction="north";
		}
		}
			
	    int arrivalinhours=arrival/60;
		int arrivalinmin=arrival%60;
		int departureinhours=departure/60;
		int departureinmin=departure%60;
		   
		int platform=platmanager.assign_platform(currentStation,direction,arrival,departure);
	    
	    model.addRow(new Object[] {currentStation,arrivalinhours+":"+arrivalinmin, departureinhours+":"+departureinmin,platform});
	        
		startingtime=departure;
		
		Conflicts checkConflict=new Conflicts();
		boolean check=checkConflict.conflictscheck(selectedTrain,currentStation,arrival,departure,platform);
		
//		int dbArrival = arrival;
//        int dbDeparture = departure;
//        int dbPlatform = platform;
		
		
		String dbArrival = arrivalinhours + ":" + arrivalinmin;
		String dbDeparture = departureinhours + ":" + departureinmin;
		int dbPlatform = platform;

        if (check) {
        	
        	checkConflict.conflictsManagement(currentStation, arrival, departure, platform,direction);
        	 
            dbArrival = checkConflict.getAdjustedArrival();
            dbDeparture = checkConflict.getAdjustedDeparture();
            dbPlatform = checkConflict.getAdjustedPlatform();
        }
        
        String query=("insert into ScheduledTrains (train_id, arrivaltime, departuretime,platform,stations) VALUES (?, ?, ?, ?,?)");
		
		PreparedStatement ps=con.prepareStatement(query);
		

			ps.setInt(1,selectedTrain);
//			ps.setString(2,"");
			ps.setString(2,dbArrival);
			ps.setString(3,dbDeparture);
			ps.setInt(4, dbPlatform);
			ps.setString(5,currentStation);
//			ps.setString(3,String.valueOf(arrivalinhours)+":"+String.valueOf(arrivalinmin));
//			ps.setString(4,String.valueOf(departureinhours)+":"+String.valueOf(departureinmin));
			
			ps.executeUpdate();
		
		}
		
		bcontinue = new JButton("continue");
		bcontinue.setFont(new Font("Tahoma", Font.PLAIN, 13));
		bcontinue.setBounds(388, 357, 84, 20);
		contentPane.add(bcontinue);
		
		bexit = new JButton("Exit");
		bexit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bexit.setBounds(294, 358, 84, 20);
		contentPane.add(bexit);
		
		bcontinue.addActionListener(this);
		bexit.addActionListener(this);
		
	
	}
	catch(SQLException ex) {
		ex.printStackTrace();
	}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bcontinue) {			
				   // System.out.println("Opening InsertValues with: number=, timediff=, starttime=");

		            InsertValues insertvalue = new InsertValues(number, timediff, starttime);
		            insertvalue.setVisible(true);
		            this.dispose();
		        
		    } 
		else if (e.getSource() == bexit) {
		        ScheduleTrains schedule = new ScheduleTrains();
		        schedule.setVisible(true);
		        this.dispose();
		    }
		}	
}

