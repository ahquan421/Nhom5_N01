package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class StudentManagementSystem {
    private JFrame frame;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public StudentManagementSystem() {
        frame = new JFrame("Sutdent Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        tableModel = new DefaultTableModel(new String[]{"Ma sinh vien", "Ho ten", "Gioi tinh", "Nam sinh", "Que quan", "Email"}, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Student");
        JButton editButton = new JButton("Update Student");
        JButton deleteButton = new JButton("Delete Student");
        JButton exitButton = new JButton("Return");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exitButton); 
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new TeacherManagementSystem(); 
            }
        });

        loadStudentsFromFile("DanhsachSV.txt");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addStudent() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Nam", "Nu"});
        JTextField yearField = new JTextField();
        JTextField hometownField = new JTextField();
        JTextField emailField = new JTextField();
    
        Object[] inputFields = {
            "Ma sinh vien:", idField,
            "Ho ten:", nameField,
            "Gioi tinh:", genderComboBox,
            "Nam sinh:", yearField,
            "Que quan:", hometownField,
            "Email:", emailField
        };
    
        int option = JOptionPane.showConfirmDialog(null, inputFields, "Add Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String gender = (String) genderComboBox.getSelectedItem(); 
                int year = Integer.parseInt(yearField.getText());
                String hometown = hometownField.getText();
                String email = emailField.getText();
    
                tableModel.addRow(new Object[]{id, name, gender, year, hometown, email});
    
                saveStudentsToFile("DanhsachSV.txt");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Vui long nhap day du thong tin!", "Loi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Vui long chon mot sinh vien de sua!", "Loi", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        JTextField idField = new JTextField(studentTable.getValueAt(selectedRow, 0).toString());
        JTextField nameField = new JTextField(studentTable.getValueAt(selectedRow, 1).toString());
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Nam", "Nu"});
        genderComboBox.setSelectedItem(studentTable.getValueAt(selectedRow, 2).toString());
        JTextField yearField = new JTextField(studentTable.getValueAt(selectedRow, 3).toString());
        JTextField hometownField = new JTextField(studentTable.getValueAt(selectedRow, 4).toString());
        JTextField emailField = new JTextField(studentTable.getValueAt(selectedRow, 5).toString());
    
        Object[] inputFields = {
            "Ma sinh vien:", idField,
            "Ho ten:", nameField,
            "Gioi tinh:", genderComboBox,
            "Nam sinh:", yearField,
            "Que quan:", hometownField,
            "Email:", emailField
        };
    
        int option = JOptionPane.showConfirmDialog(null, inputFields, "Sua sinh vien", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                studentTable.setValueAt(Integer.parseInt(idField.getText()), selectedRow, 0);
                studentTable.setValueAt(nameField.getText(), selectedRow, 1);
                studentTable.setValueAt(genderComboBox.getSelectedItem(), selectedRow, 2);
                studentTable.setValueAt(Integer.parseInt(yearField.getText()), selectedRow, 3);
                studentTable.setValueAt(hometownField.getText(), selectedRow, 4);
                studentTable.setValueAt(emailField.getText(), selectedRow, 5);
    
                saveStudentsToFile("DanhsachSV.txt");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Vui long nhap thong tin hop le!", "Loi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Vui long chon mot sinh vien de xoa!", "Loi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(null, "Ban co chac chan muon xoa sinh vien nay?", "Xac nhan", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            saveStudentsToFile("DanhsachSV.txt");
        }
    }

    private void loadStudentsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",\\s*");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String gender = data[2];
                int year = Integer.parseInt(data[3]);
                String hometown = data[4];
                String email = data[5];

                tableModel.addRow(new Object[]{id, name, gender, year, hometown, email});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Loi khi doc file: " + e.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Du lieu trong file khong hop le!", "Loi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveStudentsToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                int id = (int) tableModel.getValueAt(i, 0);
                String name = (String) tableModel.getValueAt(i, 1);
                String gender = (String) tableModel.getValueAt(i, 2);
                int year = (int) tableModel.getValueAt(i, 3);
                String hometown = (String) tableModel.getValueAt(i, 4);
                String email = (String) tableModel.getValueAt(i, 5);

                writer.write(id + ", " + name + ", " + gender + ", " + year + ", " + hometown + ", " + email);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Loi khi ghi file: " + e.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystem());
    }
}
