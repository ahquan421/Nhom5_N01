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
                        missingFields.append("Vui lòng nhập: ");
                        isError = true;
                    } else if (studentIdExists(idText)) {
                        throw new IllegalArgumentException("Sinh viên đã tồn tại!");
                    }

                    int id = Integer.parseInt(idText);
    
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
    
                    int year = Integer.parseInt(yearText);
                    String gender = (String) genderComboBox.getSelectedItem();
    
                    tableModel.addRow(new Object[]{id, name, gender, year, hometown, email});
                    saveStudentsToFile("DanhsachSV.txt");
                    isAdding = false;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Vui lòng nhập đầy đủ thông tin!", "Lỗi!", JOptionPane.ERROR_MESSAGE);
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
    
        JTextField idField = new JTextField(studentTable.getValueAt(selectedRow, 0).toString());
        JTextField nameField = new JTextField(studentTable.getValueAt(selectedRow, 1).toString());
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Nam", "Nữ"});
        genderComboBox.setSelectedItem(studentTable.getValueAt(selectedRow, 2).toString());
        JTextField yearField = new JTextField(studentTable.getValueAt(selectedRow, 3).toString());
        JTextField hometownField = new JTextField(studentTable.getValueAt(selectedRow, 4).toString());
        JTextField emailField = new JTextField(studentTable.getValueAt(selectedRow, 5).toString());
    
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
                    String idText = idField.getText();
                    String name = nameField.getText();
                    String yearText = yearField.getText();
                    String hometown = hometownField.getText();
                    String email = emailField.getText();
    
                    StringBuilder missingFields = new StringBuilder("Vui lòng nhập: ");
                    boolean isError = false;
    
                    if (idText.isEmpty()) {
                        missingFields.append("Mã sinh viên, ");
                        isError = true;
                    }
                    else if (studentIdExists(idText) && !idText.equals(studentTable.getValueAt(selectedRow, 0).toString())) {
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
    
                    studentTable.setValueAt(idText, selectedRow, 0);
                    studentTable.setValueAt(name, selectedRow, 1);
                    studentTable.setValueAt(genderComboBox.getSelectedItem(), selectedRow, 2);
                    studentTable.setValueAt(Integer.parseInt(yearText), selectedRow, 3);
                    studentTable.setValueAt(hometown, selectedRow, 4);
                    studentTable.setValueAt(email, selectedRow, 5);
    
                    saveStudentsToFile("DanhsachSV.txt");
                    isUpdating = false; 
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Vui lòng nhập thông tin hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

        int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa sinh viên này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
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
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            System.out.println("Không thể tải file dữ liệu từ tệp: " + e.getMessage());
        }
    }

    private void saveStudentsToFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    writer.print(tableModel.getValueAt(i, j));
                    if (j < tableModel.getColumnCount() - 1) {
                        writer.print(",");
                    }
                }
                writer.println();
            }
        } catch (IOException e) {
            System.out.println("Không thể lưu dữ liệu vào tệp: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementSystem::new);
    }
}
