package schedule;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdatePlatform extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField tnewPlatform;
    private JButton bUpdate, bCancel;
    private int trainId,currentPlatform;
    private String station,arrival,departure;
    private ViewTimeTable parentView;

    public UpdatePlatform(int trainId,int currentPlatform,String station,String arrival,String departure,ViewTimeTable parentView) {
        this.trainId = trainId;
        this.currentPlatform = currentPlatform;
        this.station=station;
        this.arrival=arrival;
        this.departure=departure;
        this.parentView = parentView;

        // --- Frame setup ---
        setTitle("Update Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 380);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- Labels ---
        JLabel Title = new JLabel("Update Train Platform");
        Title.setFont(new Font("Tahoma", Font.BOLD, 16));
        Title.setBounds(120, 10, 250, 30);
        contentPane.add(Title);

        JLabel train = new JLabel("Train ID: " + trainId);
        train.setFont(new Font("Tahoma", Font.PLAIN, 14));
        train.setBounds(50, 60, 250, 25);
        contentPane.add(train);

        JLabel Station = new JLabel("Station: " + station);
        Station.setFont(new Font("Tahoma", Font.PLAIN, 14));
        Station.setBounds(50, 90, 250, 25);
        contentPane.add(Station);

        JLabel Arrival = new JLabel("Arrival: " + arrival);
        Arrival.setFont(new Font("Tahoma", Font.PLAIN, 14));
        Arrival.setBounds(50, 120, 250, 25);
        contentPane.add(Arrival);

        JLabel Departure = new JLabel("Departure: " + departure);
        Departure.setFont(new Font("Tahoma", Font.PLAIN, 14));
        Departure.setBounds(50, 150, 250, 25);
        contentPane.add(Departure);

        JLabel current = new JLabel("Current Platform: " + currentPlatform);
        current.setFont(new Font("Tahoma", Font.PLAIN, 14));
        current.setBounds(50, 180, 250, 25);
        contentPane.add(current);

        JLabel newPlatform = new JLabel("New Platform:");
        newPlatform.setFont(new Font("Tahoma", Font.PLAIN, 14));
        newPlatform.setBounds(50, 215, 120, 25);
        contentPane.add(newPlatform);

        // --- Text field ---
        tnewPlatform = new JTextField();
        tnewPlatform.setBounds(180, 215, 150, 25);
        contentPane.add(tnewPlatform);

        // --- Buttons ---
        bUpdate = new JButton("Update");
        bUpdate.setBounds(90, 270, 100, 30);
        contentPane.add(bUpdate);

        bCancel = new JButton("Cancel");
        bCancel.setBounds(230, 270, 100, 30);
        contentPane.add(bCancel);

        // --- Add Action Listeners ---
        bUpdate.addActionListener(this);
        bCancel.addActionListener(this);

//         --- Make visible ---
//        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bUpdate) {
            try {
                int newPlatform = Integer.parseInt(tnewPlatform.getText());

                Connection con = DBManager.getDBconnection();
                PreparedStatement ps = con.prepareStatement(
                    "update scheduledtrains set platform=? where train_id=? and stations=? and arrivaltime=? and departuretime=?"
                );
                ps.setInt(1, newPlatform);
                ps.setInt(2, trainId);
                ps.setString(3, station);
                ps.setString(4, arrival);
                ps.setString(5,departure);

                int updated = ps.executeUpdate();
                
                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Platform updated successfully!");
                    if (parentView != null) {
                        parentView.reloadTable();
                    }
                    this.dispose();
                }

                else {
                    JOptionPane.showMessageDialog(this, "Train ID not found!");
                }

            } 
            catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Please enter a valid platform number!");
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }

        else if (e.getSource() == bCancel) {
            this.dispose();
        }
    }
}

