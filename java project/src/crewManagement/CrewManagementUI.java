package crewManagement;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class CrewManagementUI extends JFrame {
    //-------------------COMPONENTS-------------------
    private CrewDAO dao = new CrewDAO();
    private JTable table;
    private DefaultTableModel model;

    //--------------------------------------CONSTRUCTOR--------------------------------------
    public CrewManagementUI() {
        setTitle("Crew Management System");
        setSize(950, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        model = new DefaultTableModel(new String[]{
                "ID", "Name", "Role", "Contact", "Past Shift", "Current Shift", "Future Shift", "Train ID"
        }, 0);

        table = new JTable(model);
        loadCrewMembers();

        //-------------------BUTTONS-------------------
        JButton addBtn = new JButton("Add Crew Member");
        JButton updateBtn = new JButton("Update Selected");
        JButton deleteBtn = new JButton("Delete Selected");

        addBtn.addActionListener(e -> addCrewMember());
        updateBtn.addActionListener(e -> updateCrewMember());
        deleteBtn.addActionListener(e -> deleteCrewMember());

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadCrewMembers() {
        model.setRowCount(0);
        List<CrewMember> list = dao.getAllCrewMembers();
        for (CrewMember m : list) {
            model.addRow(new Object[]{
                    m.getId(),
                    m.getName(),
                    m.getRole(),
                    m.getContact(),
                    m.getPastShift(),
                    m.getCurrentShift(),
                    m.getFutureShift(),
                    m.getTrainId()  
            });
        }
    }

    //-------------------ADD CREW-------------------
    private void addCrewMember() {
        JTextField nameField = new JTextField();
        JTextField roleField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField pastField = new JTextField();
        JTextField currentField = new JTextField();
        JTextField futureField = new JTextField();
        JTextField trainIdField = new JTextField(); // New field

        Object[] inputs = {
                "Name:", nameField,
                "Role:", roleField,
                "Contact:", contactField,
                "Past Shift:", pastField,
                "Current Shift:", currentField,
                "Future Shift:", futureField,
                "Train ID:", trainIdField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Add Crew Member", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int trainId = 0;
            try {
                trainId = Integer.parseInt(trainIdField.getText().trim());
            } catch (NumberFormatException ignored) {}

            dao.addCrewMember(new CrewMember(
                    nameField.getText(),
                    roleField.getText(),
                    contactField.getText(),
                    pastField.getText(),
                    currentField.getText(),
                    futureField.getText(),
                    trainId
            ));
            loadCrewMembers();
        }
    }

    //-------------------DELETE CREW-------------------
    private void deleteCrewMember() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a crew member to delete!");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        dao.deleteCrewMember(id);
        loadCrewMembers();
    }

    //-------------------UPDATE CREW-------------------
    private void updateCrewMember() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a crew member to update!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        JTextField nameField = new JTextField((String) model.getValueAt(row, 1));
        JTextField roleField = new JTextField((String) model.getValueAt(row, 2));
        JTextField contactField = new JTextField((String) model.getValueAt(row, 3));
        JTextField pastField = new JTextField((String) model.getValueAt(row, 4));
        JTextField currentField = new JTextField((String) model.getValueAt(row, 5));
        JTextField futureField = new JTextField((String) model.getValueAt(row, 6));
        JTextField trainIdField = new JTextField(String.valueOf(model.getValueAt(row, 7)));

        Object[] inputs = {
                "Name:", nameField,
                "Role:", roleField,
                "Contact:", contactField,
                "Past Shift:", pastField,
                "Current Shift:", currentField,
                "Future Shift:", futureField,
                "Train ID:", trainIdField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Update Crew Member", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int trainId = 0;
            try {
                trainId = Integer.parseInt(trainIdField.getText().trim());
            } catch (NumberFormatException ignored) {}

            dao.updateCrewMember(new CrewMember(
                    id,
                    nameField.getText(),
                    roleField.getText(),
                    contactField.getText(),
                    pastField.getText(),
                    currentField.getText(),
                    futureField.getText(),
                    trainId
            ));
            loadCrewMembers();
        }
    }

    //--------------------------------------MAIN--------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CrewManagementUI().setVisible(true));
    }
}
