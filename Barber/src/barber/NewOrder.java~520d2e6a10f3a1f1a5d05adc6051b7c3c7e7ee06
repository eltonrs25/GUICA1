package barber;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package barber;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;  


import com.mysql.jdbc.PreparedStatement;

//import org.jdatepicker.*;


public class NewOrder implements ActionListener{
	
	private JFrame jframe;
	private JPanel jpanel;
	private JLabel location;
	private JTextField locationField;
	private JLabel date;
	private JTextField dateField;
	private JLabel name;
	private JTextField nameField;
	private JButton submit;
	private JLabel time_Slot;
	private int selectedTime = 0;
	Commen commmen = new Commen();

	
	ArrayList<String> timeList = new ArrayList<String>();
	String[] array = new String[10];
	static JComboBox c1;
//	JDatePicker datePicker;
	int customer_id;
//	private 
	
	public NewOrder(int customer_id) {
		
		this.customer_id = customer_id;
		
		timeList.add("--select a time slot--");
		timeList.add("8.00 to 9.00");
		timeList.add("9.00 to 10.00");
		timeList.add("10.00 to 11.00");
		timeList.add("11.00 to 12.00");
		timeList.add("1.00 to 2.00");
		timeList.add("2.00 to 3.00");

		
		jframe = new JFrame();
		jpanel = new JPanel();
		
		jframe.setSize(450, 250);
//		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setTitle("New Order");
		
		jframe.add(jpanel);
		jpanel.setLayout(null);
		
		location = new JLabel("location :");
		location.setBounds(20, 30, 80, 20);
		jpanel.add(location);
		
		locationField = new JTextField();
		locationField.setBounds(120, 30, 200, 20);
		jpanel.add(locationField);
		
		date = new JLabel("date :");
		date.setBounds(20, 70, 80, 20);
		jpanel.add(date);
		
//		
//		UtilDateModel model = new UtilDateModel();
//        //model.setDate(20,04,2014);
//		Properties p = new Properties();
//		p.put("text.today", "Today");
//		p.put("text.month", "Month");
//		p.put("text.year", "Year");
//        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
//        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
//        
//        jpanel.add(datePicker);
		
		dateField = new JTextField("yyyy-MM-dd");
		dateField.setBounds(120, 70, 200, 20);
		jpanel.add(dateField);
		
		
		name = new JLabel("barber name :");
		name.setBounds(20, 110, 100, 20);
		jpanel.add(name);
		
		nameField = new JTextField();
		nameField.setBounds(120, 110, 200, 20);
		jpanel.add(nameField);
		
		
		time_Slot = new JLabel("Time slot :");
		time_Slot.setBounds(20, 150, 80, 20);
		jpanel.add(time_Slot);
		
		array = timeList.toArray(new String[timeList.size()]);
		
	    JComboBox cb=new JComboBox(array);    
	    cb.setBounds(120, 150, 200, 20); 
	    jpanel.add(cb);
	    cb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) cb.getSelectedItem();
				System.out.println("selected time is :"+ s);
				switch(s){
					case "8.00 to 9.00":
						selectedTime = 1;
						break;
					case "9.00 to 10.00":
						selectedTime = 2;
						break;
					case "10.00 to 11.00":
						selectedTime = 3;
						break;
					case "11.00 to 12.00":
						selectedTime = 4;
						break;
					case "1.00 to 2.00":
						selectedTime = 5;
						break;
					case "2.00 to 3.00":
						selectedTime = 6;
						break;
					default:
						selectedTime = 0;
				}
				
			}
			
	    	
	    });
	    
	    
	    submit = new JButton("Order place");
	    submit.setBounds(120, 190, 200, 20);
		jpanel.add(submit);
		submit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				onClickSubmit();
			}

		});
		
		jframe.setVisible(true);
		
	}
	private void onClickSubmit() {
		int insertSuccess = 0;
		boolean isFiled = false;
		String loc = locationField.getText();
		String dat = dateField.getText();
		String nameTxt = nameField.getText();
		DateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateOfOrder = null;
		try {
			dateOfOrder = apiFormat.parse(dat);
			
		} catch (ParseException e) {
			commmen.showErrorDialog("Please enter valid date. (2020-11-01)");
		}
		
		System.out.println("enterd date is :" + dateOfOrder +", "+ new java.sql.Date(dateOfOrder.getTime()));
		
		if(selectedTime == 0) {
			commmen.showErrorDialog("Please select a time slot.");
		}else {
			if(loc.equals("") || dat.equals("") || nameTxt.equals("")) {
				commmen.showErrorDialog("All fields are mandatory.");
			}else {
				Connection connection = DataBaseConnection.getConnection();
				if(connection == null) {
					commmen.showErrorDialog("there is a connection problem..");
				}else {
					PreparedStatement st;
					try {
						st = (PreparedStatement) connection
						        .prepareStatement("select users.user_id from users join location on users.user_id = location.user_id where users.name = ? and location.location = ?");
						st.setString(1, nameTxt);
	                    st.setString(2, loc);
	                    ResultSet rs = st.executeQuery();
	                    if(rs.next()) {
	                    	int id = Integer.parseInt(rs.getString(1));
	                    	st = (PreparedStatement) connection
							        .prepareStatement("select time_slot from orders where service_id = ? and date = ?");
							st.setInt(1, id);
//		                    st.setDate(1, (java.sql.Date) dateOfOrder);
		                    st.setDate(2, new java.sql.Date(dateOfOrder.getTime()));
		                    rs = st.executeQuery();
		                   
		                    if(rs != null) {
		                    	while(rs.next()) {
		                    		
		                    		if(rs.getString(1).equals(String.valueOf(selectedTime))) {
		                    			commmen.showErrorDialog("sorry!, time slot already filed.");
		                    			isFiled = true;
		                    		}
		                    		
		                    	}
		                    	if(isFiled == false) {
		                    		//insert the new order...
		                    		insertNewOrder(connection, dateOfOrder, id, insertSuccess);
		                    	}
		                    }else {
		                    	insertNewOrder(connection, dateOfOrder, id, insertSuccess);
		                    }
						}else {
							commmen.showErrorDialog("there is no any barbers in input location");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
			}
		}
		
		
		

		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
	
	private void insertNewOrder(Connection connection, Date dateOfOrder, int id, int insertSuccess) {
		System.out.println("trying to insert new order...");
		String insert = "insert into orders (customer_id, date, time_slot, service_id) values (?, ?, ?, ?)";
		try (PreparedStatement stm = (PreparedStatement) connection.prepareStatement(insert)) {
		    stm.setInt(1, customer_id);
		    stm.setDate(2, new java.sql.Date(dateOfOrder.getTime()));
		    stm.setInt(3, selectedTime);
		    stm.setInt(4, id);
		    	insertSuccess = stm.executeUpdate();
		    	// 1 when inserted 1 record
		    	
		    if(insertSuccess == 1) {
		    	// success message
		    	jframe.setVisible(false);
		    	commmen.showSuccessMessage("Your new order placed..!");
		    }else {
		    	//error meggage
		    }
		}catch(Exception e) {
			System.out.println(e);
//			
		}
	}


}

