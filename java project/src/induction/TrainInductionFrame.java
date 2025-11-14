package induction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class TrainInductionFrame extends JFrame implements ActionListener {
    
    //-------------------COMPONENTS-------------------
    JButton btn1, btn2,btn4, btn5;
    static JTable inductTable;
    static DefaultTableModel model;

    //--------------------------------------CONSTRUCTOR--------------------------------------
    public TrainInductionFrame() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Train Induction Frame");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //-------------------NAVBAR-------------------
        JPanel navbar = new JPanel();
        navbar.setLayout(new FlowLayout());
        navbar.setBackground(Color.gray);
        navbar.setPreferredSize(new Dimension(800, 50));
        JLabel navLabel = new JLabel("Train Induction Frame");
        navLabel.setForeground(Color.WHITE);
        navLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        navbar.add(navLabel, FlowLayout.LEFT);

        //-------------------BUTTON PANEL-------------------
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setPreferredSize(new Dimension(800, 70));

        btn1 = new JButton("Add Train");
        btn2 = new JButton("Remove Train");
        btn4 = new JButton("Assign Crew");
        btn5 = new JButton("Update Train");

        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn4.addActionListener(this);
        btn5.addActionListener(this);

        buttonPanel.add(btn1);
        buttonPanel.add(btn2);
        buttonPanel.add(btn4);
        buttonPanel.add(btn5);

        //-------------------TABLE-------------------
        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new EmptyBorder(5, 20, 5, 20));
        tablePanel.setLayout(new BorderLayout(10, 10));
        JLabel ltable = new JLabel("Induction Table:");
        ltable.setFont(new Font("SansSerif", Font.BOLD, 20));
        tablePanel.add(ltable, BorderLayout.NORTH);

        model = Table.createModel();
        inductTable = new JTable(model);

        inductTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(inductTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        //-------------------ADDING COMPONENTS-------------------
        mainPanel.add(navbar, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.PAGE_START);
        mainPanel.add(tablePanel, BorderLayout.AFTER_LAST_LINE);
        mainPanel.add(buttonPanel);
        add(mainPanel);

    }

    //--------------------------------------METHOD--------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        //-------------------ADDING TRAIN-------------------
        if (e.getSource() == btn1) {
            AddTrain adding = new AddTrain();
            adding.setVisible(true);
        }
        //-------------------DELETE TRAIN-------------------
        else if (e.getSource() == btn2) {
            int selRow = inductTable.getSelectedRow();
            if (selRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a train to remove.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int trainId = (int) inductTable.getValueAt(selRow, 0);
            DeleteTrain.delTrain(trainId);
            inductTable.setModel(Table.createModel());
        }
        //-------------------UPDATE TRAIN------------------- 
        else if (e.getSource() == btn5) {
            int selRow = inductTable.getSelectedRow();
            if (selRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a train to Update.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int trainId = (int) inductTable.getValueAt(selRow, 0);
            UpdateTrain adding = new UpdateTrain(trainId);   
        }
        //-------------------ASSIGN CREW------------------- 
        else if (e.getSource() == btn4) {
            int selRow = inductTable.getSelectedRow();
            if (selRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a train to assign crew.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
            }
            int trainId = (int) inductTable.getValueAt(selRow, 0);
            AssignCrew assignFrame = new AssignCrew(trainId);
            assignFrame.setVisible(true);
        }

    }
    //--------------------------------------MAIN--------------------------------------
    public static void main(String[] args) {
        TrainInductionFrame frame = new TrainInductionFrame();
        frame.setVisible(true);
    }
}

