package projectOpeneing;

import crewManagement.CrewManagementUI;
import schedule.ScheduleTrains;
import induction.TrainInductionFrame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SelectOperations extends JFrame implements ActionListener {

    JButton element2, element3, element4;

    public SelectOperations() {

        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setLocationRelativeTo(null);
        setTitle("Main Menu");
        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        add(mainPanel);

        JLabel title = new JLabel("Select Operation");
        title.setFont(title.getFont().deriveFont(24f));   
        title.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(title);

        element2 = new JButton("Manage Crew");
        element3 = new JButton("Schedule Train");
        element4 = new JButton("Induction");
        element2.setPreferredSize(new java.awt.Dimension(180, 55));
        element3.setPreferredSize(new java.awt.Dimension(180, 55));
        element4.setPreferredSize(new java.awt.Dimension(180, 55));

        element2.addActionListener(this);
        element3.addActionListener(this);
        element4.addActionListener(this);
        JPanel j1 = new JPanel();
        JPanel j2 = new JPanel();
        JPanel j3 = new JPanel();
        j1.add(element2);
        j2.add(element3);
        j3.add(element4);

        mainPanel.add(j1);
        mainPanel.add(j2);
        mainPanel.add(j3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == element2) {
            CrewManagementUI c = new CrewManagementUI();
            c.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            c.setVisible(true);
        } 
        else if (e.getSource() == element3) {
            ScheduleTrains s = new ScheduleTrains();
            s.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            s.setVisible(true);
        } 
        else if (e.getSource() == element4) {
            TrainInductionFrame t = new TrainInductionFrame();
            t.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
            t.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SelectOperations so = new SelectOperations();
        so.setVisible(true);
    }
}