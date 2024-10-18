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
        frame = new JFrame("Quan ly sinh vien");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        tableModel = new DefaultTableModel(new String[]{"Ma sinh vien", "Ho ten", "Gioi tinh", "Nam sinh", "Que quan", "Email"}, 0);
        studentTable = new JTable(tableModel);
        rowSorter = new TableRowSorter<>(tableModel);
        studentTable.setRowSorter(rowSorter);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Find:"));

        String[] filterOptions = {"Ma sinh vien", "Ho ten", "Nam sinh"};
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
        JButton addButton = new JButton("Them sinh vien");
        JButton editButton = new JButton("Sua sinh vien");
        JButton deleteButton = new JButton("Xoa sinh vien");
        JButton exitButton = new JButton("Thoat");

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
            JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Nam", "Nu"});
            JTextField yearField = new JTextField(yearText);
            JTextField hometownField = new JTextField(hometown);
            JTextField emailField = new JTextField(email);
    
            Object[] inputFields = {
                    "Ma sinh vien:", idField,
                    "Ho ten:", nameField,
                    "Gioi tinh:", genderComboBox,
                    "Nam sinh:", yearField,
                    "Que quan:", hometownField,
                    "Email:", emailField
            };
    
            int option = JOptionPane.showConfirmDialog(null, inputFields, "Them sinh vien", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    idText = idField.getText();
                    name = nameField.getText();
                    yearText = yearField.getText();
                    hometown = hometownField.getText();
                    email = emailField.getText();
    
                    StringBuilder missingFields = new StringBuilder("Vui long nhap: ");
                    boolean isError = false;
    
                    if (idText.isEmpty()) {
                        missingFields.append("Vui long nhap: ");
                        isError = true;
                    } else if (!idText.matches("\\d{8}")) {
                        throw new IllegalArgumentException("Ma sinh vien phai co 8 chu so");
                    } else if (studentIdExists(idText)) {
                        throw new IllegalArgumentException("Sinh vien da ton tai!");
                    }

                    int id = Integer.parseInt(idText);
    
                    if (name.isEmpty()) {
                        missingFields.append("Ho ten, ");
                        isError = true;
                    }

                    if (yearText.isEmpty()) {
                        missingFields.append("Nam sinh, ");
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
                    JOptionPane.showMessageDialog(frame, "Vui long nhap day du thong tin!", "Loi!", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                isAdding = false;
            }
        }
    }    
    
    private void updateStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Vui long chon mot sinh vien de xoa!", "Loi", JOptionPane.ERROR_MESSAGE);
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

        int option = JOptionPane.showConfirmDialog(null, inputFields, "Cap nhat sinh vien", JOptionPane.OK_CANCEL_OPTION);
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
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            System.out.println("Khong the tai du lieu tu tep: " + e.getMessage());
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
            System.out.println("Khong the luu du lieu vao tep: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementSystem::new);
    }
}
