import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.*;

public class StudentManagementSystem {
    private JFrame frame;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField filterField;
    private JComboBox<String> filterComboBox;
    private TableRowSorter<DefaultTableModel> rowSorter;

    public StudentManagementSystem() {
        frame = new JFrame("Quản lý sinh viên");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        tableModel = new DefaultTableModel(new String[]{"Mã sinh viên", "Họ tên", "Giới tính", "Năm sinh", "Quê quán", "Email"}, 0);
        studentTable = new JTable(tableModel);
        rowSorter = new TableRowSorter<>(tableModel);
        studentTable.setRowSorter(rowSorter);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Find:"));

        String[] filterOptions = {"Mã sinh viên", "Họ tên", "Năm sinh"};
        filterComboBox = new JComboBox<>(filterOptions);
        filterPanel.add(filterComboBox);

        filterField = new JTextField(15);
        filterPanel.add(filterField);
        frame.add(filterPanel, BorderLayout.NORTH);

        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Thêm sinh viên");
        JButton editButton = new JButton("Sửa sinh viên");
        JButton deleteButton = new JButton("Xóa sinh viên");
        JButton exitButton = new JButton("Thoát");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exitButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        exitButton.addActionListener(e -> {
            frame.dispose();
            new TeacherManagementSystem();
        });

        loadStudentsFromFile("DanhsachSV.txt");

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void filterTable() {
        String text = filterField.getText();
        int selectedIndex = filterComboBox.getSelectedIndex();
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            String regex = "(?i).*" + text + ".*";
            switch (selectedIndex) {
                case 0:
                    rowSorter.setRowFilter(RowFilter.regexFilter(regex, 0));
                    break;
                case 1:
                    rowSorter.setRowFilter(RowFilter.regexFilter(regex, 1));
                    break;
                case 2:
                    rowSorter.setRowFilter(RowFilter.regexFilter(regex, 3));
                    break;
            }
        }
    }

    private boolean studentIdExists(String studentId) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String existingId = tableModel.getValueAt(i, 0).toString();
            if (existingId.equals(studentId)) {
                return true;
            }
        }
        return false;
    }

    private void addStudent() {
        boolean isAdding = true;
        String idText = "";
        String name = "";
        String yearText = "";
        String hometown = "";
        String email = "";
    
        while (isAdding) {
            JTextField idField = new JTextField(idText);
            JTextField nameField = new JTextField(name);
            JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Nam", "Nữ"});
            JTextField yearField = new JTextField(yearText);
            JTextField hometownField = new JTextField(hometown);
            JTextField emailField = new JTextField(email);
    
            Object[] inputFields = {
                "Mã sinh viên:", idField,
                "Họ tên:", nameField,
                "Giới tính:", genderComboBox,
                "Năm sinh:", yearField,
                "Quê quán:", hometownField,
                "Email:", emailField
            };
    
            int option = JOptionPane.showConfirmDialog(null, inputFields, "Thêm sinh viên", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    idText = idField.getText();
                    name = nameField.getText();
                    yearText = yearField.getText();
                    hometown = hometownField.getText();
                    email = emailField.getText();
    
                    StringBuilder missingFields = new StringBuilder("Vui lòng nhập: ");
                    boolean isError = false;
    
                    if (idText.isEmpty()) {
                        missingFields.append("Mã sinh viên, ");
                        isError = true;
                    } else if (studentIdExists(idText)) {
                        throw new IllegalArgumentException("Sinh viên đã tồn tại!");
                    }
    
                    if (name.isEmpty()) {
                        missingFields.append("Họ tên, ");
                        isError = true;
                    }
    
                    if (yearText.isEmpty()) {
                        missingFields.append("Năm sinh, ");
                        isError = true;
                    }
    
                    if (isError) {
                        missingFields.setLength(missingFields.length() - 2);
                        throw new IllegalArgumentException(missingFields.toString());
                    }
    
                    if (!yearText.matches("\\d+")) {
                        throw new IllegalArgumentException("Năm sinh phải là số!");
                    }
    
                    String gender = (String) genderComboBox.getSelectedItem();
                    tableModel.addRow(new Object[]{idText, name, gender, yearText, hometown, email});
                    saveStudentsToFile("DanhsachSV.txt");
                    isAdding = false;
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                isAdding = false;
            }
        }
    }    

    private void updateStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn 1 sinh viên để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Object idValue = studentTable.getValueAt(selectedRow, 0);
        Object nameValue = studentTable.getValueAt(selectedRow, 1);
        Object genderValue = studentTable.getValueAt(selectedRow, 2);
        Object yearValue = studentTable.getValueAt(selectedRow, 3);
        Object hometownValue = studentTable.getValueAt(selectedRow, 4);
        Object emailValue = studentTable.getValueAt(selectedRow, 5);
    
        String idText = idValue != null ? idValue.toString() : "";
        String nameText = nameValue != null ? nameValue.toString() : "";
        String genderText = genderValue != null ? genderValue.toString() : "Nam"; 
        String yearText = yearValue != null ? yearValue.toString() : "";
        String hometownText = hometownValue != null ? hometownValue.toString() : "";
        String emailText = emailValue != null ? emailValue.toString() : "";
    
        JTextField idField = new JTextField(idText);
        JTextField nameField = new JTextField(nameText);
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Nam", "Nữ"});
        genderComboBox.setSelectedItem(genderText);
        JTextField yearField = new JTextField(yearText);
        JTextField hometownField = new JTextField(hometownText);
        JTextField emailField = new JTextField(emailText);
    
        Object[] inputFields = {
            "Mã sinh viên:", idField,
            "Họ tên:", nameField,
            "Giới tính:", genderComboBox,
            "Năm sinh:", yearField,
            "Quê quán:", hometownField,
            "Email:", emailField
        };
    
        boolean isUpdating = true;
    
        while (isUpdating) {
            int option = JOptionPane.showConfirmDialog(null, inputFields, "Cập nhật sinh viên", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String idInput = idField.getText();
                    String nameInput = nameField.getText();
                    String yearInput = yearField.getText();
                    String hometownInput = hometownField.getText();
                    String emailInput = emailField.getText();
    
                    StringBuilder missingFields = new StringBuilder("Vui lòng nhập: ");
                    boolean isError = false;
    
                    if (idInput.isEmpty()) {
                        missingFields.append("Mã sinh viên, ");
                        isError = true;
                    } else if (studentIdExists(idInput) && !idInput.equals(studentTable.getValueAt(selectedRow, 0).toString())) {
                        throw new IllegalArgumentException("Sinh viên đã tồn tại!");
                    }
    
                    if (nameInput.isEmpty()) {
                        missingFields.append("Họ tên, ");
                        isError = true;
                    }
    
                    if (yearInput.isEmpty()) {
                        missingFields.append("Năm sinh, ");
                        isError = true;
                    }
    
                    if (isError) {
                        missingFields.setLength(missingFields.length() - 2);
                        throw new IllegalArgumentException(missingFields.toString());
                    }
    
                    if (!yearInput.matches("\\d+")) {
                        throw new IllegalArgumentException("Năm sinh phải là số!");
                    }
    
                    studentTable.setValueAt(idInput, selectedRow, 0);
                    studentTable.setValueAt(nameInput, selectedRow, 1);
                    studentTable.setValueAt(genderComboBox.getSelectedItem(), selectedRow, 2);
                    studentTable.setValueAt(yearInput, selectedRow, 3);
                    studentTable.setValueAt(hometownInput, selectedRow, 4);
                    studentTable.setValueAt(emailInput, selectedRow, 5);
                    saveStudentsToFile("DanhsachSV.txt");
                    isUpdating = false;
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                isUpdating = false;
            }
        }
    }    

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn 1 sinh viên để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Bạn có chắc muốn xóa sinh viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            saveStudentsToFile("DanhsachSV.txt");
        }
    }

    private void loadStudentsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] studentData = line.split(",");
                tableModel.addRow(studentData);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Không thể đọc dữ liệu từ file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveStudentsToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    bw.write(tableModel.getValueAt(i, j).toString());
                    if (j < tableModel.getColumnCount() - 1) {
                        bw.write(",");
                    }
                }
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Không thể ghi dữ liệu vào file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementSystem::new);
    }
}
