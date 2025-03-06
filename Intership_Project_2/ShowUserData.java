package Intership_Project_2;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class ShowUserData extends JPanel {
	private JTable table;
	private JTextField searchField;
	private DefaultTableModel model;
	private TableRowSorter<DefaultTableModel> sorter;

	ArrayList<String> columns = new ArrayList<>(
			Arrays.asList("Mobile Number", "User Name", "Plan Type", "Plan Price", "Account Number"));

	// Define user data using ArrayList of ArrayLists
	ArrayList<ArrayList<String>> data = new ArrayList<>();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("User Data");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(600, 400);
			frame.add(new ShowUserData()); // Removed extra parenthesis
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}

	public static JPanel refreshPanel(ShowUserData showUserData) {
		JPanel t = new JPanel();
		t.setLayout(new BoxLayout(t, BoxLayout.X_AXIS));

		JButton n = new JButton("Refresh");
		n.setFont(new Font("Arial", Font.BOLD, 12));

//        n.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 25));
		n.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SwingUtilities.invokeLater(() -> {
					showUserData.refreshTable();
				});

			}
		});

		t.add(Box.createHorizontalGlue());
		t.add(n);

		return t;
	}

	
	public ShowUserData() {
		setLayout(new BorderLayout()); // Set layout for ShowUserData itself

		// Sample Data
		String[] columns = { "Mobile Number", "User Name", "Plan Type", "Plan Price", "Account Number" };

		ArrayList<ArrayList<String>> data = fetchDataFromDatabase();
		Object[][] tableData = convertTo2DArray(data);

		// Create Table Model
		model = new DefaultTableModel(tableData, columns);

		table = new JTable(model);
		table.setAlignmentX(Component.CENTER_ALIGNMENT);
		table.isCellEditable(0, 0);

		// Enable Sorting
		sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);

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
		add(refreshPanel(this), BorderLayout.SOUTH);
	}

	// Method to Fetch Data from MySQL Database
	private ArrayList<ArrayList<String>> fetchDataFromDatabase() {
		ArrayList<ArrayList<String>> data1 = new ArrayList<>();

		try {
			// JOIN broadband_plans with users using mobileNumber
			String query = "SELECT b.mobileNumber, u.userName, b.planType, b.planPrice, b.accountNo "
					+ "FROM broaddband_plans b " + "JOIN nvidia_user_sigin u ON b.mobileNumber = u.mobileNumber";

			ConnnectionJDBC con = new ConnnectionJDBC(); // Fixed class name

			ResultSet rs = con.s.executeQuery(query);

			while (rs.next()) {
				ArrayList<String> row = new ArrayList<>();
				row.add(rs.getString("mobileNumber"));
				row.add(rs.getString("userName"));
				row.add(rs.getString("planType"));
				row.add(String.valueOf(rs.getDouble("planPrice")));
				row.add(rs.getString("accountNo"));
				data1.add(row);
			}

			rs.close();
			// Close statement properly

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data1;
	}

	// Convert ArrayList to Object[][] for JTable
	private Object[][] convertTo2DArray(ArrayList<ArrayList<String>> data) {
		Object[][] array = new Object[data.size()][5]; // 5 Columns
		for (int i = 0; i < data.size(); i++) {
			array[i] = data.get(i).toArray(new String[0]);
		}
		return array;
	}

	public void refreshTable() {
		ArrayList<ArrayList<String>> newData = fetchDataFromDatabase();
		Object[][] tableData = convertTo2DArray(newData);

		// Clear the table and add new data
		model.setRowCount(0); // Removes all rows
		for (Object[] row : tableData) {
			model.addRow(row);
		}
	}
}

































//
//public ShowUserData() {
//    setLayout(new BorderLayout()); // Set layout for ShowUserData itself
//
//    // Sample Data
//    String[] columns = { "Mobile Number", "User Name", "Plan Type", "Plan Price", "Account Number" };
//
//    ArrayList<ArrayList<String>> data = fetchDataFromDatabase();
//    Object[][] tableData = convertTo2DArray(data);
//
//    // Create Table Model
//    model = new DefaultTableModel(tableData, columns);
//
//    table = new JTable(model);
//    table.setAlignmentX(Component.CENTER_ALIGNMENT);
//    table.setRowHeight(30); // Increase row height
//    table.setFont(new Font("Arial", Font.PLAIN, 16)); // Set table font size
//
//    // Customize Table Header
//    JTableHeader header = table.getTableHeader();
//    header.setFont(new Font("Arial", Font.BOLD, 18)); // Increase header font size
//    header.setBackground(new Color(50, 50, 50)); // Dark background
//    header.setForeground(Color.WHITE); // White text
//
//    // Enable Sorting
//    sorter = new TableRowSorter<>(model);
//    table.setRowSorter(sorter);
//
//    // Grid & Row Selection Customization
//    table.setGridColor(Color.LIGHT_GRAY); // Set grid color
//    table.setSelectionBackground(new Color(255, 140, 0)); // Highlight selected row with orange
//    table.setSelectionForeground(Color.WHITE); // White text when selected
//
//    // Alternating Row Colors
//    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//                boolean hasFocus, int row, int column) {
//            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//            if (!isSelected) {
//                c.setBackground(row % 2 == 0 ? new Color(230, 230, 230) : Color.WHITE);
//            }
//            return c;
//        }
//    });
//
//    // Search Field
//    searchField = new JTextField();
//    searchField.setFont(new Font("Arial", Font.PLAIN, 16));
//    searchField.setPreferredSize(new Dimension(200, 30));
//    searchField.setToolTipText("Search Name or ID...");
//
//    // Search Feature: Live Filtering
//    searchField.addKeyListener(new KeyAdapter() {
//        @Override
//        public void keyReleased(KeyEvent e) {
//            String searchText = searchField.getText().trim();
//            if (searchText.isEmpty()) {
//                sorter.setRowFilter(null);
//            } else {
//                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
//            }
//        }
//    });
//
//    // Add Components
//    JPanel topPanel = new JPanel();
//    topPanel.add(new JLabel("🔍 Search: "));
//    topPanel.add(searchField);
//
//    add(topPanel, BorderLayout.NORTH);
//    add(new JScrollPane(table), BorderLayout.CENTER);
//    add(refreshPanel(this), BorderLayout.SOUTH);
//}	
