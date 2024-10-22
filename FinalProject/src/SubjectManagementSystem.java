import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class SubjectManagementSystem {
    private JFrame frame;
    private JTable subjectTable;
    private DefaultTableModel tableModel;

    public SubjectManagementSystem() {
        showSubjectManagement();
        loadSubjectsFromFile("DanhsachMH.txt");
    }

    public void showSubjectManagement() {
        frame = new JFrame("Quản lý môn học");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);
        
        String[] columnNames = {"Mã môn học", "Tên môn học", "Số tín chỉ", "Ngày bắt đầu", "Ngày kết thúc"};
        tableModel = new DefaultTableModel(columnNames, 0);
        subjectTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(subjectTable);
        frame.add(scrollPane, BorderLayout.CENTER);
    
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Thêm môn học");
        JButton updateButton = new JButton("Sửa môn học");
        JButton deleteButton = new JButton("Xóa môn học");
        JButton exitButton = new JButton("Trở lại");
    
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
                new TeacherManagementSystem(); 
            }
        });
    
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                new TeacherManagementSystem(); 
            }
        });
    
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void loadSubjectsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",\\s*");
                if (data.length == 5) {
                    tableModel.addRow(new Object[]{data[0], data[1], data[2], data[3], data[4]});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Lỗi đọc file: " + e.getMessage(), "Lỗi!", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(frame, "Lỗi viết file: " + e.getMessage(), "lỗi!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addSubject() {
        JTextField codeField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField creditsField = new JTextField();
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();
        
        boolean hasError;
        
        do {
            hasError = false;
            StringBuilder errorMessage = new StringBuilder("Những trường sau không được để trống hoặc sai:\n");
    
            Object[] message = {
                "Mã môn học:", codeField,
                "Tên môn học:", nameField,
                "Số tín chỉ:", creditsField,
                "Ngày bắt đầu  (dd/mm/yyyy):", startDateField,
                "Ngày kết thúc (dd/mm/yyyy):", endDateField
            };
    
            int option = JOptionPane.showConfirmDialog(null, message, "Thêm môn học", JOptionPane.OK_CANCEL_OPTION);
            if (option != JOptionPane.OK_OPTION) {
                return; 
            }
    
            if (codeField.getText().trim().isEmpty()) {
                errorMessage.append("- Mã môn học\n");
                hasError = true;
            }
            if (creditsField.getText().trim().isEmpty()) {
                errorMessage.append("- Số tín chỉ (không được để trống và > 0)\n");
                hasError = true;
            } else {
                try {
                    int credits = Integer.parseInt(creditsField.getText().trim());
                    if (credits <= 0) {
                        errorMessage.append("- Số tín chỉ phải là số nguyên dương.\n");
                        hasError = true;
                    }
                } catch (NumberFormatException e) {
                    errorMessage.append("- Số tín chỉ phải là số nguyên dương.\n");
                    hasError = true;
                }
            }
            if (startDateField.getText().trim().isEmpty()) {
                errorMessage.append("- Ngày bắt đầu (dd/mm/yyyy)\n");
                hasError = true;
            }
            if (endDateField.getText().trim().isEmpty()) {
                errorMessage.append("- Ngày kết thúc (dd/mm/yyyy)\n");
                hasError = true;
            }
    
            if (hasError) {
                JOptionPane.showMessageDialog(this.frame, errorMessage.toString(), "Lỗi!", JOptionPane.WARNING_MESSAGE);
            }
            
        } while (hasError); 
    
        this.tableModel.addRow(new Object[]{
            codeField.getText(), nameField.getText(),
            creditsField.getText(), startDateField.getText(), endDateField.getText()
        });
        saveSubjectsToFile("DanhsachMH.txt");
    }        

    private void updateSubject() {
        int selectedRow = subjectTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn 1 môn học để sửa!", "Lỗi!", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        JTextField idField = new JTextField(subjectTable.getValueAt(selectedRow, 0).toString());
        JTextField nameField = new JTextField(subjectTable.getValueAt(selectedRow, 1).toString());
        JTextField creditsField = new JTextField(subjectTable.getValueAt(selectedRow, 2).toString());
        JTextField startDateField = new JTextField(subjectTable.getValueAt(selectedRow, 3).toString());
        JTextField endDateField = new JTextField(subjectTable.getValueAt(selectedRow, 4).toString());
    
        boolean hasError;
        
        do {
            hasError = false;
            Object[] inputFields = {
                "Mã môn học:", idField,
                "Tên môn học:", nameField,
                "Số tín chỉ:", creditsField,
                "Ngày bắt đầu (dd/mm/yyyy):", startDateField,
                "Ngày kết thúc (dd/mm/yyyy):", endDateField
            };
    
            int option = JOptionPane.showConfirmDialog(null, inputFields, "Sửa môn học", JOptionPane.OK_CANCEL_OPTION);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
    
            StringBuilder errorMessage = new StringBuilder("Những trường sau không được để trống hoặc sai:\n");
    
            if (idField.getText().trim().isEmpty()) {
                errorMessage.append("- Mã môn học\n");
                hasError = true;
            }
    
            if (creditsField.getText().trim().isEmpty()) {
                errorMessage.append("- Số tín chỉ (không được để trống và > 0)\n");
                hasError = true;
            } else {
                try {
                    int credits = Integer.parseInt(creditsField.getText().trim());
                    if (credits <= 0) {
                        errorMessage.append("- Số tín chỉ phải là số nguyên dương.\n");
                        hasError = true;
                    }
                } catch (NumberFormatException e) {
                    errorMessage.append("- Số tín chỉ phải là số nguyên dương.\n");
                    hasError = true;
                }
            }
    
            if (startDateField.getText().trim().isEmpty()) {
                errorMessage.append("- Ngày bắt đầu (dd/mm/yyyy)\n");
                hasError = true;
            }
    
            if (endDateField.getText().trim().isEmpty()) {
                errorMessage.append("- Ngày kết thúc (dd/mm/yyyy)\n");
                hasError = true;
            }
    
            if (hasError) {
                JOptionPane.showMessageDialog(this.frame, errorMessage.toString(), "Lỗi!", JOptionPane.WARNING_MESSAGE);
            }
            
        } while (hasError); 
    
        subjectTable.setValueAt(idField.getText(), selectedRow, 0);
        subjectTable.setValueAt(nameField.getText(), selectedRow, 1);
        subjectTable.setValueAt(creditsField.getText(), selectedRow, 2);
        subjectTable.setValueAt(startDateField.getText(), selectedRow, 3);
        subjectTable.setValueAt(endDateField.getText(), selectedRow, 4);
    
        saveSubjectsToFile("DanhsachMH.txt");
    }
        

    private void deleteSubject() {
        int selectedRow = subjectTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn 1 môn học để xóa!", "Lỗi!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(frame, "Bạn có chắc muốn xóa môn học này không?", "Xóa môn học", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            saveSubjectsToFile("DanhsachMH.txt");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SubjectManagementSystem());
    }
}
