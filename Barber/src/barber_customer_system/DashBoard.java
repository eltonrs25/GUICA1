/*
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barber_customer_system;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.mysql.jdbc.PreparedStatement;

public class DashBoard {
	
	int userType;
	int user_id;
	String email;
	private JFrame jframe;
	private JPanel jpanel;
	private  JLabel userDashboard;
	private JLabel username;
	private JButton newOrder;
	private JLabel location;
	private JLabel date;
	private JTextField locationTextField;
	private JTextField dateTextField;
	private JButton barbersearch;
	private JButton complain;
	private JButton complainList;
	
	
	public DashBoard(int id, int userType, String email) {
		this.userType = userType; // 0: barber & 1: customer
		this.user_id = id;//user id
		this.email = email;
		Connection connection = DataBaseConnection.getConnection();
		
		jframe = new JFrame();
		jpanel = new JPanel();
		jpanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "DASHBOARD", TitledBorder.CENTER, TitledBorder.TOP));

		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setTitle("Barber/Hairdresser Appointment System");
		
		
		
		jpanel.setLayout(null);
		
		
		if(userType == 0) {
			userDashboard = new JLabel("Barber Dashboard");
			
		}else {
			userDashboard = new JLabel("Customer Dashboard");
		}
		userDashboard.setBounds(20, 20, 200, 20);	
		jpanel.add(userDashboard);
		
		username = new JLabel(email);
		username.setBounds(480, 20, 250,20);
		jpanel.add(username);
		
		newOrder = new JButton("New Order");
		newOrder.setBounds(20, 60, 150,20);
		jpanel.add(newOrder);
		newOrder.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				onClickNewOrder();
			}

		});
		
		location = new JLabel("Barber name");
		location.setBounds(20, 100, 100,20);
		jpanel.add(location);
		
		locationTextField = new JTextField();
		locationTextField.setBounds(20, 120, 150,20);
		jpanel.add(locationTextField);
		
		barbersearch = new JButton("Barber search");
		barbersearch.setBounds(20, 160, 150,20);
		jpanel.add(barbersearch);
		
		if(userType == 1) {
			complain = new JButton("Complain");
			complain.setBounds(20, 190, 150,20);
			jpanel.add(complain);
		}else {
			complain = new JButton("Complain list");
			complain.setBounds(20, 200, 150,20);
			jpanel.add(complain);
		}
		if(this.userType == 0) {
			displayTableDataFroBarber(connection);
		}else if(this.userType == 1) {
			
			displayTableDataFroCustomer(connection);
		}

	    jframe.add(jpanel);
	    jframe.setSize(700, 500);
		jframe.setVisible(true);
	}
	
	public void displayTableDataFroBarber(Connection connection) {
		Date today = new Date();
		
		try {
			PreparedStatement st = (PreparedStatement) connection
                    .prepareStatement("select users.name, orders.date, orders.time_slot, users.phone_no from orders join users on users.user_id = orders.customer_id where orders.service_id = ?");
			st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
            	System.out.print(rs.getString(1) + ", "+ rs.getString(2)+", "+rs.getString(3)+", "+rs.getString(4)+"\n");
            }
		}catch(Exception e) {
			
		}
	}
	private void displayTableDataFroCustomer(Connection connection) {
		Date today = new Date();
		
		try {
			PreparedStatement st = (PreparedStatement) connection
                    .prepareStatement("select orders.date, orders.time_slot, users.name, users.phone_no from orders join users on orders.service_id = users.user_id where orders.customer_id = ?");
			st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
            	System.out.print(rs.getString(1) + ", "+ rs.getString(2)+", "+rs.getString(3)+", "+rs.getString(4)+"\n");
            }
		}catch(Exception e) {
			System.out.println(e);
		}

	}
	private void onClickNewOrder() {
		new NewOrder(user_id);
//		new Test();
	}

}
