package induction;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

public class UpdateTrain extends JFrame implements ActionListener {
    int trainId=0;
    //-------------------COMPONENTS-------------------
    JButton fitCerUp,mileageUp,brandingUp,jobCardUp;
    JTextField tMaintenance,tjcTask;
    JRadioButton complete,inProgress,pending,fit,unfit,yes,no;


    //--------------------------------------CONSTRUCTOR--------------------------------------
        UpdateTrain(int trainId){
        this.trainId = trainId;
        setVisible(true);
        setTitle("Train Induction Frame");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //-------------------BUTTONS PANNEL-------------------
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        btnPanel.setPreferredSize(new Dimension(800, 70));

        fitCerUp = new JButton("Fitness Certificate");
        mileageUp = new JButton("Mileage");
        brandingUp = new JButton("Branding");
        jobCardUp = new JButton("Job Card");

        fitCerUp.addActionListener(this);
        mileageUp.addActionListener(this);
        brandingUp.addActionListener(this);
        jobCardUp.addActionListener(this);

        add(btnPanel);
        btnPanel.add(fitCerUp);
        btnPanel.add(jobCardUp);
        btnPanel.add(brandingUp);
        btnPanel.add(mileageUp);

        }


        //--------------------------------------METHOD--------------------------------------
         @Override
    public void actionPerformed(ActionEvent e) {

        //-------------------MILEAGE-------------------
        if(e.getSource() == mileageUp){
        JPanel mPanel = new JPanel();    
        JLabel lMaintenance = new JLabel("Maintenance Balance:");
        tMaintenance = new JTextField(10);
        mPanel.add(lMaintenance);
        mPanel.add(tMaintenance);
        int result = JOptionPane.showConfirmDialog(this, mPanel, "Enter Maintenance Balance",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result == JOptionPane.OK_OPTION){
            //string to int :> (getText returns String)
        if (tMaintenance.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a maintenance value.", "Input Error", JOptionPane.WARNING_MESSAGE);
        return;
        }
        int newMainBal = Integer.parseInt(tMaintenance.getText());
        TrainDB.mileageUp(trainId, newMainBal);
        System.out.println("DataBase Updated!");
        }
        }


        //-------------------JOB CARD-------------------
        else if(e.getSource() == jobCardUp){
            JPanel jcJPanel = new JPanel(new GridLayout(2,3,10,10));
            complete = new JRadioButton("Complete");
            inProgress = new JRadioButton("In Progress");
            pending = new JRadioButton("Pending");
            ButtonGroup group = new ButtonGroup();
            group.add(complete);
            group.add(inProgress);
            group.add(pending);
            tjcTask = new JTextField(15);
            //to disable the textfield when status is complete
            tjcTask.setEnabled(false);
            ActionListener toggle = a -> tjcTask.setEnabled(inProgress.isSelected() || pending.isSelected());
            complete.addActionListener(toggle);
            inProgress.addActionListener(toggle);
            pending.addActionListener(toggle);
            jcJPanel.add(complete);
            jcJPanel.add(inProgress);
            jcJPanel.add(pending);
            jcJPanel.add(tjcTask);
            int result = JOptionPane.showConfirmDialog(this, jcJPanel, "Job Card Status",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result == JOptionPane.OK_OPTION){
                String jcStatus = null;
                String jcTask = null;
                if (complete.isSelected()) jcStatus = "Complete";
                else if (inProgress.isSelected()) jcStatus = "In Progress";
                else if (pending.isSelected()) jcStatus = "Pending";
                if(jcStatus.equalsIgnoreCase("In Progress")|| jcStatus.equalsIgnoreCase("Pending")){
                    jcTask = tjcTask.getText().trim();
                }
                TrainDB.jobCardUp(trainId, jcTask, jcStatus);
                System.out.println("Job card updated!");

            }
        }


        //-------------------FITNESS CERTIFICATE-------------------
        else if(e.getSource() == fitCerUp){
            JPanel fcJPanel = new JPanel(new GridLayout(2,2,10,10));
            fit = new JRadioButton("Fit");
            unfit = new JRadioButton("Unfit");
            ButtonGroup grp = new ButtonGroup();
            grp.add(fit);
            grp.add(unfit);
            JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            inputPanel.add(new JLabel("Date of Issuance:"));
            JSpinner issuanceSpinner = new JSpinner(new SpinnerDateModel());
            issuanceSpinner.setEditor(new JSpinner.DateEditor(issuanceSpinner, "dd/MM/yyyy"));
            inputPanel.add(issuanceSpinner);
            inputPanel.add(new JLabel("Date of Expiry:"));
            JSpinner expirySpinner = new JSpinner(new SpinnerDateModel());
            expirySpinner.setEditor(new JSpinner.DateEditor(expirySpinner, "dd/MM/yyyy"));
            inputPanel.add(expirySpinner);
            issuanceSpinner.setEnabled(false);
            expirySpinner.setEnabled(false);
            fit.addActionListener(ev -> {
            issuanceSpinner.setEnabled(true);
            expirySpinner.setEnabled(true);
            });
            unfit.addActionListener(ev -> {
            issuanceSpinner.setEnabled(false);
            expirySpinner.setEnabled(false);
            });
            fcJPanel.add(fit);
            fcJPanel.add(unfit);
            fcJPanel.add(inputPanel);
            int result = JOptionPane.showConfirmDialog(this, fcJPanel, "Fitness Certificate",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result == JOptionPane.OK_OPTION){
                String fitStatus = null;
                Date issuDate = null;
                Date expiryDate = null;
                if (fit.isSelected()) fitStatus = "Fit";
                else if (unfit.isSelected()) fitStatus = "Unfit";
                if(fitStatus.equalsIgnoreCase("Fit")){
                    issuDate = (Date) issuanceSpinner.getValue();
                    expiryDate = (Date) expirySpinner.getValue();
                }
                TrainDB.fitnessUp(trainId, fitStatus, issuDate, expiryDate);
                System.out.println("Fitness Updated!");
            }
        }

        //-------------------BRANDING-------------------
        else if(e.getSource() == brandingUp){
            JPanel brandJPanel = new JPanel(new GridLayout(2,2,10,10));
            yes = new JRadioButton("Yes");
            no = new JRadioButton("No");
            ButtonGroup grp = new ButtonGroup();
            grp.add(yes);
            grp.add(no);
            JPanel brandingInput = new JPanel(new GridLayout(2, 2, 10, 10));
            brandingInput.add(new JLabel("Branding Type:"));
            JTextField brandingTypeField = new JTextField();
            brandingInput.add(brandingTypeField);
            brandingInput.add(new JLabel("Branding Hours:"));
            JSpinner hoursSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            brandingInput.add(hoursSpinner);
            brandingTypeField.setEnabled(false);
            hoursSpinner.setEnabled(false);
            yes.addActionListener(ev -> {
            brandingTypeField.setEnabled(true);
            hoursSpinner.setEnabled(true);
            });
            no.addActionListener(ev -> {
            brandingTypeField.setEnabled(false);
            hoursSpinner.setEnabled(false);
            });
            brandJPanel.add(yes);
            brandJPanel.add(no);
            brandJPanel.add(brandingInput);
            int result = JOptionPane.showConfirmDialog(this, brandJPanel, "Branding",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result == JOptionPane.OK_OPTION){
                String isBradning = null;
                String brandingType = null;
                int brandingHours = 0;
                if (yes.isSelected()) isBradning = "Yes";
                else if (no.isSelected()) isBradning = "No";
                if(isBradning.equalsIgnoreCase("Yes")){
                    brandingType = brandingTypeField.getText().trim();
                    brandingHours = (int)hoursSpinner.getValue();
                }
                TrainDB.brandingUp(trainId, isBradning, brandingType, brandingHours);
                System.out.println("Branding updated!");
            }
        }
        JOptionPane.showMessageDialog(this, "Update Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        TrainInductionFrame.inductTable.setModel(Table.createModel());
        setVisible(false);
    }


    //--------------------------------------MAIN--------------------------------------
    public static void main(String[] args) {
    }


    }



