package barber;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package barber;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.PreparedStatement;

import javax.swing.border.TitledBorder;

public class MainView {
	int user_id, userType;
	String email;
	
	String column_Customer[]={"Date", "Time", "Barber name", "Phone no."}; 
	String column_Barber[]={"Name", "Date", "Time", "phone no."}; 
	Object objectBarber[][];
	Object objectCustomer[][];
	
	public MainView(int id, int userType, String email) {
		this.user_id = id;
		this.userType = userType;
		this.email= email;
		
		Connection connection = DataBaseConnection.getConnection();
		
		JFrame frame = new JFrame("Dash board");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel gui = new JPanel(new BorderLayout(5,5));
		
        gui.setBorder( new TitledBorder("Main Dashboard") );
        
        JPanel plafComponents = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 3,3));
        
        JLabel name = new JLabel(email);
        plafComponents.add(name);
        JButton button = new JButton("LogOut");
        plafComponents.add(button);
		
        gui.add(plafComponents, BorderLayout.NORTH);
        
        JPanel dynamicLabels = new JPanel(new GridLayout(7,1));

        
        gui.add(dynamicLabels, BorderLayout.WEST);
        
        JLabel enterName = new JLabel("Enter Barber name to search");
        dynamicLabels.add(enterName);
        
        JTextField nameField = new JTextField();
        dynamicLabels.add(nameField);
        
        JButton search = new JButton("Barber search");
        dynamicLabels.add(search);
        
        JLabel label = new JLabel("You can place a new order");
        dynamicLabels.add(label);
        
        JButton newOrder= new JButton("New Order");
		newOrder.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				onClickNewOrder();
			}

		});
		
        dynamicLabels.add(newOrder);
        
        if(userType == 0) {
        	JLabel label_complain = new JLabel("customer complains");
            dynamicLabels.add(label_complain);
            
            JButton show_complains= new JButton("show All");
            dynamicLabels.add(show_complains);
        }else if(userType == 1) {
        	JLabel label_complain = new JLabel("put a complain");
            dynamicLabels.add(label_complain);
            
            JButton show_complains= new JButton("complain");
            dynamicLabels.add(show_complains);
        }
        
//        Object data_[][]={ 
//        		{1, "John", 40.0, false },
//                {2, "Rambo", 70.0, false },
//                {3, "Zorro", 60.0, true },
//                {1, "John", 40.0, false },
//                {2, "Rambo", 70.0, false },
//                {3, "Zorro", 60.0, true },
//                {1, "John", 40.0, false },
//                {2, "Rambo", 70.0, false },
//                {3, "Zorro", 60.0, true },
//                };    
//        String column[]={"Date", "Time", "name", "phone no."}; 
        DefaultTableModel model;
        if(userType == 1) {
        	displayTableDataFroCustomer(connection);
        	model = new DefaultTableModel(objectCustomer, column_Customer); 	
        }else {
        	displayTableDataFroBarber(connection);
        	model = new DefaultTableModel(objectBarber, column_Barber); 	
        }
        
        JTable table = new JTable(model);
        try {
            // 1.6+
            table.setAutoCreateRowSorter(true);
        } catch(Exception continuewithNoSort) {
        }
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder( new TitledBorder("My Orders") );
        
        Dimension tablePreferred = tableScroll.getPreferredSize();
        tableScroll.setPreferredSize(
            new Dimension(tablePreferred.width, tablePreferred.height/3) );

        gui.add( tableScroll, BorderLayout.CENTER );

        frame.setContentPane(gui);

        frame.pack();
//        frame.setSize(700, 300);

        frame.setLocationRelativeTo(null);
        
        try {
            // 1.6+
            frame.setLocationByPlatform(true);
            frame.setMinimumSize(frame.getSize());
        } catch(Throwable ignoreAndContinue) {
        }

        frame.setVisible(true);
	}
	
	
	
	private void onClickNewOrder() {
		new NewOrder(user_id);
	}
	
	
	public void displayTableDataFroBarber(Connection connection) {
		Date today = new Date();
		
		try {
			PreparedStatement st = (PreparedStatement) connection
                    .prepareStatement("select users.name, orders.date, orders.time_slot, users.phone_no from orders join users on users.user_id = orders.customer_id where orders.service_id = ?");
			st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();
//            Object object[][];
            int count  = 0;
            
            while(rs.next()) {
//            	System.out.println("ok");
//            	objectBarber[count][0] = rs.getString(1);
//            	objectBarber[count][1] = rs.getString(2);
//            	objectBarber[count][2] = rs.getString(3);
//            	objectBarber[count][3] = rs.getString(4);
            	count++;
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
            int count = 0;
            while(rs.next()) {
//            	System.out.println("ok");
//            	objectCustomer[count][0] = rs.getString(1);
//            	objectCustomer[count][1] = rs.getString(2);
//            	objectCustomer[count][2] = rs.getString(3);
//            	objectCustomer[count][3] = rs.getString(4);
            	count++;
            	System.out.print(rs.getString(1) + ", "+ rs.getString(2)+", "+rs.getString(3)+", "+rs.getString(4)+"\n");
            }
		}catch(Exception e) {
			System.out.println(e);
		}

	}
	
}

