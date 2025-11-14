package schedule;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.*;

import javax.swing.JTextField;

public class SelectTrains extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ttrain_no;
	private JTextField tstartingTime;
	private JButton bcontinue;
	
	int timediff;

	public SelectTrains(int timeDiff) {
		timediff=timeDiff;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		bcontinue = new JButton("Continue");
		bcontinue.setBounds(307, 191, 100, 20);
		contentPane.add(bcontinue);
		
		JLabel train_no = new JLabel("Enter the number of Trains you want to schedule");
		train_no.setFont(new Font("Tahoma", Font.PLAIN, 14));
		train_no.setBounds(10, 0, 346, 34);
		contentPane.add(train_no);
		
		ttrain_no = new JTextField();
		ttrain_no.setBounds(122, 44, 96, 18);
		contentPane.add(ttrain_no);
		ttrain_no.setColumns(10);
		
		JLabel startingTime = new JLabel("Enter the starting time of the first Train");
		startingTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		startingTime.setBounds(10, 81, 346, 20);
		contentPane.add(startingTime);
		
		tstartingTime = new JTextField();
		tstartingTime.setBounds(122, 117, 96, 18);
		contentPane.add(tstartingTime);
		tstartingTime.setColumns(10);
		
		bcontinue.addActionListener(this);

	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bcontinue) {
			int num = Integer.parseInt(ttrain_no.getText());
			int starttime=Integer.parseInt(tstartingTime.getText());
			
			InsertValues in=new InsertValues(num,timediff,starttime);
     		in.setVisible(true); 
	        this.dispose();
		}
	}
}

