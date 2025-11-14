package induction;

import crewManagement.CrewDAO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class AssignCrew extends JFrame implements ActionListener {
    //-------------------COMPONENTS-------------------
    int trainId;
    JTable crewTable;
    DefaultTableModel model;
    JButton assign, unassign;

    //--------------------------------------CONSTRUCTOR--------------------------------------
    AssignCrew(int trainId) {
        this.trainId = trainId;
        setSize(600, 400);
        setTitle("Assign Crew to Train " + trainId);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        //-------------------MAIN PANEL-------------------
        JPanel mainPanel = new JPanel(new BorderLayout(8, 8));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(mainPanel, BorderLayout.CENTER);

        //-------------------TABLE------------------- 
        model = CrewTable.createModel();
        crewTable = new JTable(model);
        JScrollPane scroll = new JScrollPane(crewTable);
        scroll.setPreferredSize(new Dimension(600, 250));
        mainPanel.add(scroll, BorderLayout.CENTER);

        //-------------------BUTTONS-------------------
        JPanel btnPanel = new JPanel(); 
        btnPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        assign = new JButton("Assign");
        unassign = new JButton("Unassign");
        assign.setPreferredSize(new Dimension(120, 35));
        unassign.setPreferredSize(new Dimension(120, 35));
        assign.addActionListener(this);
        unassign.addActionListener(this);
        btnPanel.add(assign);
        btnPanel.add(unassign);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    //--------------------------------------METHODS--------------------------------------
    public void actionPerformed(ActionEvent e) {
        int selRow = crewTable.getSelectedRow();
            if (selRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a train to assign crew", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
            }
            //-------------------ASSIGN-------------------
        if(e.getSource()==assign){
            if((int)crewTable.getValueAt(selRow, 7) == 0){
                CrewDAO.assignCrewToTrain((int)crewTable.getValueAt(selRow, 0), trainId);    
            }
            else{
                JOptionPane.showMessageDialog(this, "Crew Not Available!", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        }
        //-------------------UNASSIGN-------------------
        else if(e.getSource()==unassign){
            if((int)crewTable.getValueAt(selRow, 7) != 0){
                CrewDAO.assignCrewToTrain((int)crewTable.getValueAt(selRow, 0), 0);
            }
        }

        model = CrewTable.createModel(); 
        crewTable.setModel(model);
        TrainInductionFrame.inductTable.setModel(Table.createModel());
    }

    //--------------------------------------MAIN--------------------------------------
    public static void main(String[] args) {
       
    }
}