//package Intership_Project_2;
//
//import java.awt.BorderLayout;
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import javax.swing.DefaultCellEditor;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//import javax.swing.RowFilter;
//import javax.swing.SwingUtilities;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellEditor;
//import javax.swing.table.TableRowSorter;
//import javax.swing.table.TableColumn;
//
//public class Service_Request_Table extends JPanel {
//
//    private JTable table;
//    private JTextField searchField;
//    private DefaultTableModel model;
//    private TableRowSorter<DefaultTableModel> sorter;
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Service Requests");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(600, 400);
//            frame.add(new Service_Request_Table());
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });
//    }
//
//    public Service_Request_Table() {
//        setLayout(new BorderLayout());
//
//        String[] columns = {"Request ID", "User Name", "Type", "Status", "Date"};
//        ArrayList<ArrayList<String>> data = fetchDataFromDatabase();
//        Object[][] tableData = convertTo2DArray(data);
//
//        // Create Table Model with non-editable columns except 'Status'
//        model = new DefaultTableModel(tableData, columns) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return column == 3; // Only Status column is editable
//            }
//        };
//
//        table = new JTable(model);
//        table.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        // Enable Sorting
//        sorter = new TableRowSorter<>(model);
//        table.setRowSorter(sorter);
//
//        // Adding ComboBox for Status column
//        String[] statusOptions = {"Pending", "Resolved", "In Progress", "Cancelled"};
//        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
//        TableColumn statusColumn = table.getColumnModel().getColumn(3);
//        statusColumn.setCellEditor(new DefaultCellEditor(statusComboBox));
//
//        // Search Field
//        searchField = new JTextField();
//        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
//        searchField.setPreferredSize(new Dimension(200, 30));
//        searchField.setToolTipText("Search Name or ID...");
//
//        // Search Feature: Live Filtering
//        searchField.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                String searchText = searchField.getText().trim();
//                if (searchText.isEmpty()) {
//                    sorter.setRowFilter(null);
//                } else {
//                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
//                }
//            }
//        });
//
//        // Add Components
//        JPanel topPanel = new JPanel();
//        topPanel.add(new JLabel("🔍 Search: "));
//        topPanel.add(searchField);
//
//        add(topPanel, BorderLayout.NORTH);
//        add(new JScrollPane(table), BorderLayout.CENTER);
//    }
//
//    private ArrayList<ArrayList<String>> fetchDataFromDatabase() {
//        ArrayList<ArrayList<String>> data1 = new ArrayList<>();
//        try {
//            String query = "SELECT * FROM service_request";
//            ConnnectionJDBC con = new ConnnectionJDBC();
//            ResultSet rs = con.s.executeQuery(query);
//
//            while (rs.next()) {
//                ArrayList<String> row = new ArrayList<>();
//                row.add(String.valueOf(rs.getInt("request_id")));
//                row.add(rs.getString("user"));
//                row.add(rs.getString("type"));
//                row.add(rs.getString("status")); // Show status as it is
//                row.add(rs.getString("date"));
//                data1.add(row);
//            }
//
//            rs.close();
//            con.s.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return data1;
//    }
//
//    private Object[][] convertTo2DArray(ArrayList<ArrayList<String>> data) {
//        Object[][] array = new Object[data.size()][5];
//        for (int i = 0; i < data.size(); i++) {
//            array[i] = data.get(i).toArray(new String[0]);
//        }
//        return array;
//    }
//}




package Intership_Project_2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableColumn;

public class Service_Request_Table extends JPanel {

    private JTable table;
    private JTextField searchField;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Service Requests");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.add(new Service_Request_Table());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public Service_Request_Table() {
        setLayout(new BorderLayout());

        String[] columns = {"Request ID", "User Name", "Type", "Status", "Date"};
        ArrayList<ArrayList<String>> data = fetchDataFromDatabase();
        Object[][] tableData = convertTo2DArray(data);

        // Create Table Model with non-editable columns except 'Status'
        model = new DefaultTableModel(tableData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only Status column is editable
            }
        };

        table = new JTable(model);
        table.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Enable Sorting
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Adding ComboBox for Status column
        String[] statusOptions = {"Pending", "Resolved", "In Progress", "Cancelled"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        TableColumn statusColumn = table.getColumnModel().getColumn(3);
        statusColumn.setCellEditor(new DefaultCellEditor(statusComboBox));

        // Listen for changes in the "Status" column and update the database
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    
                    if (column == 3) { // Status column
                        String newStatus = (String) model.getValueAt(row, column);
                        String requestId = (String) model.getValueAt(row, 0); // Request ID

                        // Update status in database
                        updateStatusInDatabase(requestId, newStatus);
                    }
                }
            }
        });

        // Search Field
        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Search Name or ID...");

        // Search Feature: Live Filtering
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText().trim();
                if (searchText.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
                }
            }
        });

        // Add Components
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("🔍 Search: "));
        topPanel.add(searchField);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private ArrayList<ArrayList<String>> fetchDataFromDatabase() {
        ArrayList<ArrayList<String>> data1 = new ArrayList<>();
        try {
            String query = "SELECT * FROM service_request";
            ConnnectionJDBC con = new ConnnectionJDBC();
            ResultSet rs = con.s.executeQuery(query);

            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                row.add(String.valueOf(rs.getInt("request_id")));
                row.add(rs.getString("user"));
                row.add(rs.getString("type"));
                row.add(rs.getString("status")); // Show status as it is
                row.add(rs.getString("date"));
                data1.add(row);
            }

            rs.close();
            con.s.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data1;
    }

    private void updateStatusInDatabase(String requestId, String newStatus) {
        try {
            ConnnectionJDBC con = new ConnnectionJDBC();
            Connection conn = con.getConnection(); // Ensure this method returns a valid Connection

            String updateQuery = "UPDATE service_request SET status = ? WHERE request_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, Integer.parseInt(requestId));

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
            	JOptionPane.showMessageDialog(Service_Request_Table.this, "Status updated successfully for Request ID:"+ requestId, "Successfully",JOptionPane.INFORMATION_MESSAGE);
               
            }

            pstmt.close();
            conn.close(); // Close the Connection instead of Statement

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Object[][] convertTo2DArray(ArrayList<ArrayList<String>> data) {
        Object[][] array = new Object[data.size()][5];
        for (int i = 0; i < data.size(); i++) {
            array[i] = data.get(i).toArray(new String[0]);
        }
        return array;
    }
}
