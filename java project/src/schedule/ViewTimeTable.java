package schedule;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;
import javax.swing.*;

import java.awt.Font;
import java.awt.event.*;

public class ViewTimeTable extends JFrame implements ActionListener{
	
	private JPanel contentPane;
    private JTable timeTable;
    JButton bupdate,bexit;
    
    DefaultTableModel model;
    int selectedRow = -1;

	public ViewTimeTable() {
	
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 579, 483);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JScrollPane ttscrollpane = new JScrollPane();
	ttscrollpane.setBounds(39, 57, 462, 318);
	contentPane.add(ttscrollpane);
	
	 model = new DefaultTableModel(
		new Object[][] {},
		new String[] {"TrainID","Station", "Arrival Time", "Departure Time", "Platform"}
	);
	
	timeTable = new JTable(model);
	ttscrollpane.setViewportView(timeTable);
	
	bupdate= new JButton("Update");
	bupdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
	bupdate.setBounds(430, 393, 84, 20);
	contentPane.add(bupdate);
	
	JLabel selectedTrain = new JLabel("Schedule ");
	selectedTrain.setHorizontalAlignment(SwingConstants.CENTER);
	selectedTrain.setFont(new Font("Tahoma", Font.BOLD, 16));
	selectedTrain.setBounds(170, 7, 183, 25);
	contentPane.add(selectedTrain);
	
	bexit = new JButton("Exit");
	bexit.setFont(new Font("Tahoma", Font.PLAIN, 14));
	bexit.setBounds(294, 392, 84, 20);
	contentPane.add(bexit);
	
	
	try {
		 Connection con = DBManager.getDBconnection();
	     Statement stmt = con.createStatement();
	     ResultSet rs = stmt.executeQuery("SELECT * FROM scheduledtrains");
		
		while (rs.next()) {
			String arrival=rs.getString("arrivalTime");
			String departure=rs.getString("departureTime");
			
			model.addRow(new Object[] {
				rs.getInt("train_id"),rs.getString("stations"),arrival,departure,rs.getInt("platform")});
		}
		
		timeTable.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==1) {
					 selectedRow=timeTable.getSelectedRow();
				
//				if(selectedRow!=-1) {
//					int currentplatform=(int)model.getValueAt(selectedRow,4);
//				}
				}
			}
		});
		
		bupdate.addActionListener(this);
		bexit.addActionListener(this);
	 
	}
	 catch(SQLException ex){
		 ex.printStackTrace();
	 }
	
	}
	
	public void actionPerformed(ActionEvent ex) {
		if(ex.getSource()==bupdate) {
			if(selectedRow!=-1) {
				int trainId=(int)model.getValueAt(selectedRow, 0);
				String station=(model.getValueAt(selectedRow, 1).toString());
				String arrival=(model.getValueAt(selectedRow, 2).toString());
				String departure=(model.getValueAt(selectedRow, 3).toString());
				int currentplatform=(int)model.getValueAt(selectedRow,4);
				UpdatePlatform update=new UpdatePlatform(trainId,currentplatform,station,arrival,departure,this);
				update.setVisible(true);
			}
			else {
	            JOptionPane.showMessageDialog(null, "Please select a row first!");
	        }
		}
		if(ex.getSource()==bexit) {
			ScheduleTrains scheduletrains=new ScheduleTrains();
			scheduletrains.setVisible(true);
			this.dispose();
		}
	}
	
	public void reloadTable() {
		 DefaultTableModel newmodel = new DefaultTableModel(
					new Object[][] {},
					new String[] {"TrainID","Station", "Arrival Time", "Departure Time", "Platform"}
				);

	    try {
	        Connection con = DBManager.getDBconnection();
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM scheduledtrains");

	        while (rs.next()) {
	            String arrival = rs.getString("arrivalTime");
	            String departure = rs.getString("departureTime");

	            newmodel.addRow(new Object[]{
	                    rs.getInt("train_id"), rs.getString("stations"),arrival,departure,rs.getInt("platform")
	            });
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    
	    timeTable.setModel(newmodel);
	}
 
}

