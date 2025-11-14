package schedule;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class WaitingTime extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tstationA;
	private JTextField tstationB;
	private JTextField tstationC;
	private JTextField tstationD;
	private JTextField tstationE;
	private JTextField tstationF;
	private JButton bcontinue;
	int timeDiff,startTime,selectedTrain,noOfTrains;
	String startingStation,endingStation;
	
	private ArrayList<String> optimizeStations=new ArrayList<>();

	public WaitingTime(int timeDiff,String startingStation,String endingStation,int selectedTrain,int startTime,int noOfTrains) {
		this.timeDiff=timeDiff;
		this.startTime=startTime;
		this.selectedTrain=selectedTrain;
		this.startingStation=startingStation;
		this.endingStation=endingStation;
		this.noOfTrains=noOfTrains;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 466, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Title = new JLabel("Waiting Time Entry");
		Title.setFont(new Font("Tahoma", Font.BOLD, 19));
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		Title.setBounds(90, 22, 246, 28);
		contentPane.add(Title);
		
		String optimizedRoot="";
		
		try {
			Connection con=DBManager.getDBconnection();		
			Statement stmt=con.createStatement();
			
			ResultSet rs=stmt.executeQuery("select *from optimized_roots");
			
			while(rs.next()) {
				if(selectedTrain==rs.getInt("train_id")) {
					optimizedRoot = rs.getString("optimized_path");

				    StringTokenizer st = new StringTokenizer(optimizedRoot, ",");
				    while (st.hasMoreTokens()) {
				        optimizeStations.add(st.nextToken().trim());
				    }
				}
				
			}
			
			JLabel loptimizedroute = new JLabel("Optimized Route :");
			loptimizedroute.setFont(new Font("Tahoma", Font.PLAIN, 13));
			loptimizedroute.setBounds(26, 73, 122, 22);
			contentPane.add(loptimizedroute);
			
			JLabel lroute = new JLabel(optimizedRoot);
			lroute.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lroute.setBounds(168, 74, 155, 22);
			contentPane.add(lroute);
			
			JLabel lwaitingTime = new JLabel("Enter the Waiting Time");
			lwaitingTime.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lwaitingTime.setBounds(26, 120, 146, 22);
			contentPane.add(lwaitingTime);
			
			JLabel lstationA = new JLabel("Station A");
			lstationA.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lstationA.setBounds(26, 162, 66, 12);
			contentPane.add(lstationA);
			
			tstationA = new JTextField();
			tstationA.setBounds(115, 160, 96, 18);
			contentPane.add(tstationA);
			tstationA.setColumns(10);
			
			JLabel lstationB = new JLabel("Station B");
			lstationB.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lstationB.setBounds(26, 194, 66, 12);
			contentPane.add(lstationB);
			
			tstationB = new JTextField();
			tstationB.setBounds(115, 188, 96, 18);
			contentPane.add(tstationB);
			tstationB.setColumns(10);
			
			JLabel lstationC = new JLabel("Station C");
			lstationC.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lstationC.setBounds(26, 225, 66, 12);
			contentPane.add(lstationC);
			
			tstationC = new JTextField();
			tstationC.setBounds(115, 216, 96, 18);
			contentPane.add(tstationC);
			tstationC.setColumns(10);
			
			JLabel lstationD = new JLabel("Station D");
			lstationD.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lstationD.setBounds(245, 160, 78, 12);
			contentPane.add(lstationD);
			
			tstationD = new JTextField();
			tstationD.setBounds(318, 158, 96, 18);
			contentPane.add(tstationD);
			tstationD.setColumns(10);
			
			JLabel lstationE = new JLabel("Station E");
			lstationE.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lstationE.setBounds(245, 191, 57, 12);
			contentPane.add(lstationE);
			
			tstationE = new JTextField();
			tstationE.setBounds(318, 188, 96, 18);
			contentPane.add(tstationE);
			tstationE.setColumns(10);
			
			JLabel lstationF = new JLabel("Station F");
			lstationF.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lstationF.setBounds(245, 226, 57, 12);
			contentPane.add(lstationF);
			
			tstationF = new JTextField();
			tstationF.setBounds(318, 223, 96, 18);
			contentPane.add(tstationF);
			tstationF.setColumns(10);
			
			bcontinue = new JButton("Continue");
			bcontinue.setFont(new Font("Tahoma", Font.PLAIN, 12));
			bcontinue.setBounds(358, 274, 84, 20);
			contentPane.add(bcontinue);
			
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		bcontinue.addActionListener(this);

	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bcontinue) {
			
			
			
			ArrayList<String> stationsSelected= new ArrayList<>();
			ArrayList<Integer> waitingTime= new ArrayList<>();

			// Add waiting time only for stations that appear in optimizeStations
			for (String station : optimizeStations) {

			    switch (station) {
			        case "A":
			            waitingTime.add(Integer.parseInt(tstationA.getText()));
			            break;

			        case "B":
			            waitingTime.add(Integer.parseInt(tstationB.getText()));
			            break;

			        case "C":
			            waitingTime.add(Integer.parseInt(tstationC.getText()));
			            break;

			        case "D":
			            waitingTime.add(Integer.parseInt(tstationD.getText()));
			            break;

			        case "E":
			            waitingTime.add(Integer.parseInt(tstationE.getText()));
			            break;

			        case "F":
			            waitingTime.add(Integer.parseInt(tstationF.getText()));
			            break;
			    }
			}
			
			TimeTabl tt=new TimeTabl(timeDiff,stationsSelected,waitingTime,selectedTrain,startTime,startingStation,endingStation,noOfTrains);
			tt.setVisible(true);
			this.dispose();

				
			}
		
		
   }
}

