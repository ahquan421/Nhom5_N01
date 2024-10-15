package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class SubjectManagementSystem {
    private JFrame frame;
    private JTable subjectTable;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        SubjectManagementSystem system = new SubjectManagementSystem();
        system.showSubjectManagement();
        system.loadSubjectsFromFile("DanhsachMH.txt");
    }

    public void showSubjectManagement() {
        frame = new JFrame("Subject Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        String[] columnNames = {"Ma mon hoc", "Ten mon hoc", "So tin chi", "Ngay bat dau", "Ngay ket thuc"};
        tableModel = new DefaultTableModel(columnNames, 0);
        subjectTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(subjectTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Them mon hoc");
        JButton updateButton = new JButton("Sua mon hoc");
        JButton deleteButton = new JButton("Xoa mon hoc");
        JButton exitButton = new JButton("Thoat"); 

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exitButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSubject();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSubject();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSubject();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                TeacherManagementSystem teacherManagement = new TeacherManagementSystem();
                teacherManagement.showTeacherManagement();
            }
        });

        frame.setVisible(true);
    }

    public void loadSubjectsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",\\s*");

                if (data.length == 5) {
                    tableModel.addRow(new Object[]{ data[0], data[1], data[2], data[3], data[4] });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Loi khi doc file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveSubjectsToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String id = tableModel.getValueAt(i, 0).toString();
                String name = tableModel.getValueAt(i, 1).toString();
                String credits = tableModel.getValueAt(i, 2).toString();
                String startDate = tableModel.getValueAt(i, 3).toString();
                String endDate = tableModel.getValueAt(i, 4).toString();
    
                String line = id + ", " + name + ", " + credits + ", " + startDate + ", " + endDate;
                writer.write(line);
                writer.newLine(); 
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Loi khi ghi file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addSubject() {
        JTextField codeField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField creditsField = new JTextField();
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();
        Object[] message = {
            "Ma mon hoc:", codeField,
            "Ten mon hoc:", nameField,
            "So tin chi:", creditsField,
            "Ngay bat dau (dd/mm/yyyy):", startDateField,
            "Ngay ket thuc (dd/mm/yyyy):", endDateField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Them mon hoc", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            StringBuilder errorMessage = new StringBuilder("Cac truong sau day khong duoc de trong:\n");
    
            boolean hasError = false;
            if (codeField.getText().trim().isEmpty()) {
                errorMessage.append("- Ma mon hoc\n");
                hasError = true;
            }
            if (nameField.getText().trim().isEmpty()) {
                errorMessage.append("- Ten mon hoc\n");
                hasError = true;
            }
            if (creditsField.getText().trim().isEmpty()) {
                errorMessage.append("- So tin chi\n");
                hasError = true;
            }
            if (startDateField.getText().trim().isEmpty()) {
                errorMessage.append("- Ngay bat dau (dd/mm/yyyy)\n");
                hasError = true;
            }
            if (endDateField.getText().trim().isEmpty()) {
                errorMessage.append("- Ngay ket thuc (dd/mm/yyyy)\n");
                hasError = true;
            }
    
            if (hasError) {
                JOptionPane.showMessageDialog(this.frame, errorMessage.toString(), "Loi", JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            this.tableModel.addRow(new Object[]{
                codeField.getText(), nameField.getText(),
                creditsField.getText(), startDateField.getText(), endDateField.getText()
            });
            saveSubjectsToFile("DanhsachMH.txt");
        }
    }     

    private void updateSubject() {
        int selectedRow = subjectTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Hay chon mot mon hoc de sua!", "Loi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField idField = new JTextField(subjectTable.getValueAt(selectedRow, 0).toString());
        JTextField nameField = new JTextField(subjectTable.getValueAt(selectedRow, 1).toString());
        JTextField creditsField = new JTextField(subjectTable.getValueAt(selectedRow, 2).toString());
        JTextField startDateField = new JTextField(subjectTable.getValueAt(selectedRow, 3).toString());
        JTextField endDateField = new JTextField(subjectTable.getValueAt(selectedRow, 4).toString());

        Object[] inputFields = {
            "Ma mon hoc:", idField,
            "Ten mon hoc:", nameField,
            "So tin chi:", creditsField,
            "Ngay bat dau (dd/mm/yyyy):", startDateField,
            "Ngay ket thuc (dd/mm/yyyy):", endDateField
        };

        int option = JOptionPane.showConfirmDialog(null, inputFields, "Sua mon hoc", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            subjectTable.setValueAt(idField.getText(), selectedRow, 0);
            subjectTable.setValueAt(nameField.getText(), selectedRow, 1);
            subjectTable.setValueAt(creditsField.getText(), selectedRow, 2);
            subjectTable.setValueAt(startDateField.getText(), selectedRow, 3);
            subjectTable.setValueAt(endDateField.getText(), selectedRow, 4);

            saveSubjectsToFile("DanhsachMH.txt");
        }
    }

    private void deleteSubject() {
        int selectedRow = subjectTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Hay chon mot mon hoc de xoa!", "Loi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(null, "Ban co chac chan muon xoa mon hoc nay?", "Xac nhan", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);

            saveSubjectsToFile("DanhsachMH.txt");
        }
    }
}
