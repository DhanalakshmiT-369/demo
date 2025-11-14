package induction;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

public class AddTrain extends JFrame implements ActionListener {
    // -------------------COMPONENTS-------------------
    JTextField tId, tName, tMaintenance;
    JRadioButton fitBtn, unfitBtn,yesBtn, noBtn;;
    JComboBox<String> jobCardBox;
    JButton addButton;
    String rFitness = "Unfit", rJCStatus = "Completed", rBranding = "No",maintenanceTask = null,brandingType = null;;
    Date fitnessIssuanceDate = null, fitnessExpiryDate = null;
    int brandingHours = 0,intId=0, intMaintenance=0;

    //--------------------------------------CONTRUCTOR--------------------------------------
    public AddTrain() {
        setTitle("Train Induction Frame");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.decode("#eeeeee"));

        //-------------------TRAIN ID-------------------
        JLabel lId = new JLabel("Train ID:");
        tId = new JTextField();
        formPanel.add(lId);
        formPanel.add(tId);

        //-------------------TRAIN NAME-------------------
        JLabel lName = new JLabel("Train Name:");
        tName = new JTextField();
        formPanel.add(lName);
        formPanel.add(tName);

        //-------------------FITNESS STATUS-------------------
        JLabel lFitness = new JLabel("Fitness Status:");
        JPanel fitnessPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        fitBtn = new JRadioButton("Fit");
        unfitBtn = new JRadioButton("Unfit");
        ButtonGroup fitnessGroup = new ButtonGroup();
        fitnessGroup.add(fitBtn);
        fitnessGroup.add(unfitBtn);
        fitnessPanel.add(fitBtn);
        fitnessPanel.add(unfitBtn);
        fitnessPanel.setBackground(Color.decode("#eeeeee"));
        formPanel.add(lFitness);
        formPanel.add(fitnessPanel);

        //-------------------JOB CARD-------------------
        JLabel lJobCard = new JLabel("Job Card Status:");
        String[] jobOptions = {"Completed", "Pending", "In Progress"};
        jobCardBox = new JComboBox<>(jobOptions);
        formPanel.add(lJobCard);
        formPanel.add(jobCardBox);

        //-------------------BRANDING-------------------
        JLabel lBranding = new JLabel("Branding:");
        JPanel brandingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        yesBtn = new JRadioButton("Yes");
        noBtn = new JRadioButton("No");
        ButtonGroup brandingGroup = new ButtonGroup();
        brandingGroup.add(yesBtn);
        brandingGroup.add(noBtn);
        brandingPanel.add(yesBtn);
        brandingPanel.add(noBtn);
        brandingPanel.setBackground(Color.decode("#eeeeee"));
        formPanel.add(lBranding);
        formPanel.add(brandingPanel);

        //-------------------MILEAGE-------------------
        JLabel lMaintenance = new JLabel("Maintenance Balance:");
        tMaintenance = new JTextField();
        formPanel.add(lMaintenance);
        formPanel.add(tMaintenance);

        //-------------------ADD BUTTON-------------------
        addButton = new JButton("Add Train");
        formPanel.add(new JLabel());
        formPanel.add(addButton);

        fitBtn.addActionListener(this);
        jobCardBox.addActionListener(this);
        yesBtn.addActionListener(this);
        addButton.addActionListener(this);
        add(formPanel);
    }

    //--------------------------------------METHOD--------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {

        //-------------------FITNESS STATUS-------------------
        if (e.getSource() == fitBtn) {
            rFitness = "Fit";
            JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            inputPanel.add(new JLabel("Date of Issuance:"));
            JSpinner issuanceSpinner = new JSpinner(new SpinnerDateModel());
            issuanceSpinner.setEditor(new JSpinner.DateEditor(issuanceSpinner, "dd/MM/yyyy"));
            inputPanel.add(issuanceSpinner);

            inputPanel.add(new JLabel("Date of Expiry:"));
            JSpinner expirySpinner = new JSpinner(new SpinnerDateModel());
            expirySpinner.setEditor(new JSpinner.DateEditor(expirySpinner, "dd/MM/yyyy"));
            inputPanel.add(expirySpinner);
            //dialog box to enter
            //"this" The parent component (your current frame), so the dialog stays centered over it.
            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter Certificate Dates",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                fitnessIssuanceDate = (Date) issuanceSpinner.getValue();
                fitnessExpiryDate = (Date) expirySpinner.getValue();
            } else {
                fitBtn.setSelected(false);
                rFitness = "Unfit";
            }
        }

        //-------------------JOB CARD-------------------
        if (e.getSource() == jobCardBox) {
            String selected = (String) jobCardBox.getSelectedItem();
            rJCStatus = selected;
            if (selected.equals("Pending") || selected.equals("In Progress")) {
                JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));
                inputPanel.add(new JLabel("Enter Maintenance Task:"));
                JTextField maintenanceTaskField = new JTextField();
                inputPanel.add(maintenanceTaskField);
                int result = JOptionPane.showConfirmDialog(this, inputPanel, "Maintenance Task Entry", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    maintenanceTask = maintenanceTaskField.getText().trim();
                } else {
                    jobCardBox.setSelectedIndex(0);
                    rJCStatus = "Completed";
                }
            }
        }

        //-------------------BRANDING-------------------
        if (e.getSource() == yesBtn) {
            rBranding = "Yes";
            JPanel brandingInput = new JPanel(new GridLayout(2, 2, 10, 10));
            brandingInput.add(new JLabel("Branding Type:"));
            JTextField brandingTypeField = new JTextField();
            brandingInput.add(brandingTypeField);

            brandingInput.add(new JLabel("Branding Hours:"));
            JSpinner hoursSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            brandingInput.add(hoursSpinner);

            int result = JOptionPane.showConfirmDialog(this, brandingInput, "Enter Branding Details",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                brandingType = brandingTypeField.getText().trim();
                brandingHours = (int) hoursSpinner.getValue();
            } else {
                noBtn.setSelected(true);
                rBranding = "No";
            }
        }

        //-------------------ADD BUTTON-------------------
        if (e.getSource() == addButton){
            try {
                intId = Integer.parseInt(tId.getText().trim());
                intMaintenance = Integer.parseInt(tMaintenance.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid numbers for Train ID and Maintenance Balance!","Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //-------------------STORE IN DATABASE-------------------
            TrainDB.addTrain(intId, tName.getText(), rFitness, rJCStatus, rBranding, intMaintenance);
            TrainDB.getFitness(intId, rFitness, fitnessIssuanceDate, fitnessExpiryDate);
            TrainDB.getJobCard(intId, maintenanceTask, rJCStatus);
            TrainDB.getMileage(intId, intMaintenance);
            TrainDB.addBranding(intId, rBranding, brandingType, brandingHours);
            JOptionPane.showMessageDialog(this, "Train Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            TrainInductionFrame.inductTable.setModel(Table.createModel());

            setVisible(false);
        }
    }

    //--------------------------------------MAIN--------------------------------------
    public static void main(String[] args) {
        AddTrain ad = new AddTrain();
        ad.setVisible(true);

    }
}