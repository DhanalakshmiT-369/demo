package schedule;

import java.awt.EventQueue;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ScheduleTrains extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	JButton bViewTimeTable,bUpdatePlatforms,bcontinue;
	private JPanel contentPane;
	private JTextField ttrain_no;
	private JTextField tstartingTime;
	private JRadioButton rbPeekHours, rbNormalHours;
	private int timeDiff = 0;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScheduleTrains frame = new ScheduleTrains();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public ScheduleTrains() 
    {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		bcontinue = new JButton("Continue");
		bcontinue.setFont(new Font("Tahoma", Font.PLAIN, 13));
		bcontinue.setBounds(462, 299, 84, 20);
		contentPane.add(bcontinue);
		
		JLabel ttrain_no_1 = new JLabel("Enter the number of Trains you want to schedule");
		ttrain_no_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ttrain_no_1.setBounds(10, 136, 346, 34);
		contentPane.add(ttrain_no_1);
		
		ttrain_no = new JTextField();
		ttrain_no.setBounds(366, 146, 96, 20);
		contentPane.add(ttrain_no);
		ttrain_no.setColumns(10);
		
		JLabel startingTime = new JLabel("Enter the starting time of the first Train");
		startingTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		startingTime.setBounds(10, 191, 346, 20);
		contentPane.add(startingTime);
		
		tstartingTime = new JTextField();
		tstartingTime.setBounds(366, 191, 96, 20);
		contentPane.add(tstartingTime);
		tstartingTime.setColumns(10);
		
		JLabel tschedulingHour = new JLabel("Currently Scheduling for");
		tschedulingHour.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tschedulingHour.setBounds(10, 65, 147, 12);
		contentPane.add(tschedulingHour);
		
		rbPeekHours = new JRadioButton("Peek Hours");
		rbPeekHours.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbPeekHours.setBounds(105, 98, 124, 20);
		contentPane.add(rbPeekHours);
		
		rbNormalHours = new JRadioButton("Normal Hours");
		rbNormalHours.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rbNormalHours.setBounds(284, 99, 139, 20);
		contentPane.add( rbNormalHours);
		
		ButtonGroup group=new ButtonGroup();
		group.add(rbPeekHours);
		group.add(rbNormalHours);
		
		JLabel tHeader = new JLabel("Train Schedule");
		tHeader.setForeground(new Color(0, 0, 0));
		tHeader.setBackground(new Color(0, 0, 0));
		tHeader.setHorizontalAlignment(SwingConstants.CENTER);
		tHeader.setFont(new Font("Tahoma", Font.BOLD, 20));
		tHeader.setBounds(10, 10, 536, 25);
		contentPane.add(tHeader);
		
		bViewTimeTable = new JButton("View");
		bViewTimeTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		bViewTimeTable.setBounds(188, 241, 115, 18);
		contentPane.add(bViewTimeTable);
		
		JLabel tTimeTable = new JLabel("Train Timetable ");
		tTimeTable.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tTimeTable.setBounds(39, 243, 106, 15);
		contentPane.add(tTimeTable);
		
		bcontinue.addActionListener(this);
		bViewTimeTable.addActionListener(this);
		rbPeekHours.addActionListener(this);
		rbNormalHours.addActionListener(this);
		
	}
    
    public void actionPerformed (ActionEvent ex) {
    
    	if(ex.getSource()==rbPeekHours) {
			timeDiff=7;
		}
	
    	else if(ex.getSource()==rbNormalHours) {
			timeDiff=12;
		}
    	else if(ex.getSource()==bcontinue) {
    		
    		if(validateInputs())
    		{
			int num = Integer.parseInt(ttrain_no.getText());
			int starttime=Integer.parseInt(tstartingTime.getText());
			
			InsertValues in=new InsertValues(num,timeDiff,starttime);
     		in.setVisible(true); 
    		}
		
       }
    	else if (ex.getSource() == bViewTimeTable) {
            ViewTimeTable vw=new ViewTimeTable();
            vw.setVisible(true);
        } 
    }
    
    private boolean validateInputs() {

        if (!rbPeekHours.isSelected() && !rbNormalHours.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select Peak Hours or Normal Hours");
            return false;
        }

        if (ttrain_no.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter number of trains");
            return false;
        }

        if (tstartingTime.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter starting time");
            return false;
        }
        
        return true;
    }

}