package schedule;

import java.awt.Color;
import java.awt.event.*;
import java.awt.Font;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import induction.DBConnector;
import optimization.TrainRouteManager;

public class InsertValues extends JFrame implements ActionListener{
	
	ArrayList<Integer> trains=new ArrayList<>();
	
	int timeDiff,startTime,noOfTrains;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTable availabletrain;
	private JTextField tselectedTrain;
	private JTextField tstartingStation;
	private JTextField tendingStation;
	private JButton bcontinue;

	
	public InsertValues(int num,int timediff,int starttime){
		this();
		
		timeDiff=timediff;
		startTime=starttime;
		noOfTrains=num;
		
	try {
		Connection con= DBConnector.getDBConnection();		
		Statement stmt=con.createStatement();
		
		ResultSet rs=stmt.executeQuery("select *from train_info");
		
		int count=0;
		
		while(rs.next() && count<num) {
			int trainNo = rs.getInt("TRAIN_ID"); 
            trains.add(trainNo); 
            count++;
		}
		
		DefaultTableModel model = (DefaultTableModel) availabletrain.getModel();
        //model.setRowCount(0);

        for (int train_No : trains) {
            model.addRow(new Object[] {train_No });
        }
    	
		contentPane.revalidate();
        contentPane.repaint();
				
	  }
	catch(SQLException ex)
	  {
		  ex.printStackTrace();
	  }
	}
	
	public InsertValues(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 385);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	    DefaultTableModel model = new DefaultTableModel(
	            new Object[][] {},
	            new String[] { "Available Trains" }
	        );
	    
		availabletrain= new JTable(model);
		JScrollPane scrollPane = new JScrollPane(availabletrain);
		scrollPane.setBounds(10, 92, 151, 218);
		contentPane.add(scrollPane);
			
		bcontinue = new JButton("Continue");
		bcontinue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bcontinue.setBounds(424, 318, 92, 20);
		contentPane.add(bcontinue);
		
		JLabel lselectedTrain = new JLabel("Enter the Train selected");
		lselectedTrain.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lselectedTrain.setBounds(203, 123, 161, 20);
		contentPane.add(lselectedTrain);
		
		tselectedTrain = new JTextField();
		tselectedTrain.setBounds(386, 125, 96, 18);
		contentPane.add(tselectedTrain);
		tselectedTrain.setColumns(10);
		
		JLabel lstartingStation = new JLabel("Enter Starting Station");
		lstartingStation.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lstartingStation.setBounds(203, 178, 151, 20);
		contentPane.add(lstartingStation);
		
		tstartingStation = new JTextField();
		tstartingStation.setBounds(386, 180, 96, 18);
		contentPane.add(tstartingStation);
		tstartingStation.setColumns(10);
		
		JLabel lendingStation = new JLabel("Enter Ending Station");
		lendingStation.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lendingStation.setBounds(203, 240, 137, 15);
		contentPane.add(lendingStation);
		
		tendingStation = new JTextField();
		tendingStation.setBounds(386, 239, 96, 18);
		contentPane.add(tendingStation);
		tendingStation.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Train Selection");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel.setBounds(163, 22, 191, 36);
		contentPane.add(lblNewLabel);
		
		bcontinue.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bcontinue) {
			
			
			String startStation=tstartingStation.getText();
			String endStation=tendingStation.getText();
			int trainSelected=Integer.parseInt(tselectedTrain.getText());
			      
			TrainRouteManager routeManager=new TrainRouteManager();
			routeManager.startingMethod(trainSelected,startStation,endStation);		
					
			WaitingTime wt=new WaitingTime(timeDiff,startStation,endStation,trainSelected,startTime,noOfTrains);

			wt.setVisible(true);
			this.dispose();
			
			
		}
	}
		
}

