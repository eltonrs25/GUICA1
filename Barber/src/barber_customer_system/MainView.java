/*
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barber_customer_system;


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
import com.placeholder.PlaceHolder;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.border.TitledBorder;

public class MainView {
	int user_id, userType;
	String email;
	Commen commen = new Commen();
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
            button.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    onClickLogout(frame);
                }


                });
            plafComponents.add(button);

            gui.add(plafComponents, BorderLayout.NORTH);

            JPanel dynamicLabels = new JPanel(new GridLayout(9,1));


            gui.add(dynamicLabels, BorderLayout.WEST);

            JLabel enterName = new JLabel("Enter Barber name to search");
            dynamicLabels.add(enterName);

            JTextField nameField = new JTextField();
            dynamicLabels.add(nameField);

            JButton search = new JButton("Barber search");
            search.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    onClickBarberSearch(nameField, connection);
                }

            });
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
//            JTextField name = new JTextField();
            
            
            if(userType == 0) {
                JLabel label_complain = new JLabel("customer complains");
                dynamicLabels.add(label_complain);

                JButton show_complains= new JButton("show All");
                show_complains.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    onClickGetAllComplaints(user_id, connection);
                }


                });
                
                dynamicLabels.add(show_complains);
            }else if(userType == 1) {
                    JLabel label_complain = new JLabel("put a complain");
                dynamicLabels.add(label_complain);
                JTextField barberName = new JTextField();
                
                JTextArea textArea = new JTextArea();
                JScrollPane tableScroll = new JScrollPane(textArea);
//              
                PlaceHolder placeHolder1 = new PlaceHolder(barberName, "Enter barber name");
                PlaceHolder placeHolder2 = new PlaceHolder(textArea, "Enter your complain");

                dynamicLabels.add(barberName);
                dynamicLabels.add(tableScroll);

                JButton newComplaint= new JButton("complain");
                newComplaint.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    onClickPushComplain(barberName, textArea, connection);
                }

                        

            });
                dynamicLabels.add(newComplaint);
            }

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

        frame.setLocationRelativeTo(null);
        
        try {
            // 1.6+
            frame.setLocationByPlatform(true);
            frame.setMinimumSize(frame.getSize());
        } catch(Throwable ignoreAndContinue) {
        }

        frame.setVisible(true);
	}
        
        private void onClickLogout(JFrame frame){

            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            
        }
        
        private void onClickGetAllComplaints(int user_id, Connection connection) {
             try{
                PreparedStatement st = (PreparedStatement) connection
                    .prepareStatement("select users.name, complaints.complaint, complaints.date from complaints join users on complaints.cus_id = users.user_id where complaints.barber_id = ?");
                st.setInt(1, user_id);
                ResultSet rs = st.executeQuery();
                commen.showSearchresult(rs, 1);
                while(rs.next()){
                    System.out.println(rs.getString("complaint"));
                    
                }
            }catch( Exception e){
                System.out.println(e);
            }
        }
        
        private void onClickPushComplain(JTextField name, JTextArea complain, Connection connection) {
            String barberName = name.getText();
            String comp = complain.getText();
            Date date = new Date();
            DateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateOfOrder = null;
            Date newDate = null;
            try {
                dateOfOrder = apiFormat.format(date);
                newDate = apiFormat.parse(dateOfOrder);
                
            } catch (Exception e) {
                   
            }
            if(barberName.equals("") || comp.equals("") || barberName.equals("Enter barber name") || comp.equals("Enter your complain")){
                commen.showErrorDialog("Barber name and complaint are mandotory.");
            }else{
                try{
                    PreparedStatement st = (PreparedStatement) connection
                        .prepareStatement("select users.user_id from users join user_type on users.user_id = user_type.user_id where users.name = ? and user_type.user_type = ?");
                    st.setString(1, barberName);
                    st.setInt(2, 0);
                    ResultSet rs = st.executeQuery();
                    rs.last();
                    int size = rs.getRow();// get the size of the result set
                    rs.beforeFirst();
                    int barberId = 0;
                    if(size > 0){
                       if(size != 1){
                           commen.showWorningMessage("There are more than one barber in given name.\n"+"Please contact administrator to put your complain");
                       }else{
                           while(rs.next()){
                               barberId = Integer.parseInt(rs.getString(1));
                           }
                            int insertSuccess =0;
                            
                            
                            String insertLocation = "insert into complaints (cus_id, barber_id, complaint, date) values (?, ?, ?, ?)";
                            try (PreparedStatement stm_3 = (PreparedStatement) connection.prepareStatement(insertLocation)) {
                                stm_3.setInt(1, user_id);
                                stm_3.setInt(2, barberId);
                                stm_3.setString(3, comp);
                                stm_3.setDate(4, new java.sql.Date(newDate.getTime()));

                                insertSuccess = stm_3.executeUpdate();
                                if(insertSuccess == 1){
                                    commen.showSuccessMessage("Complaint added successfully.!");
                                    name.setText("");
                                    complain.setText("");
                                }else{
                                    commen.showErrorDialog("Network error occered! please try again.");
                                }
                            }catch(Exception e) {
                                System.out.println(e);
                            }
                       }
                    }else{
                        commen.showErrorDialog("there is no barber with your given name.");
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }
	
	
	
	private void onClickNewOrder() {
		new NewOrder(user_id);
	}
        
        private void onClickBarberSearch(JTextField nameField, Connection connection){
            String name = nameField.getText();
            if(name.equals("")){
                commen.showErrorDialog("Enter barber name first");
            }else{
                try{
                    PreparedStatement st = (PreparedStatement) connection
                        .prepareStatement("select users.name, users.phone_no, users.location from users join user_type on users.user_id = user_type.user_id where name = ? and user_type.user_type = ?");
                    st.setString(1, name);
                    st.setInt(2, 0);
                    ResultSet rs = st.executeQuery();
                    rs.last();
                    int size = rs.getRow();// get the size of the result set
                    rs.beforeFirst();
                    if(size <= 0){
                        commen.showWorningMessage("there is no search result for given name");
                    }else{
                        nameField.setText("");
                        System.out.println("....search result....");
                        commen.showSearchresult(rs, 0);
                        rs.beforeFirst();
                        while(rs.next()){
                            System.out.println("Barber name : "+ rs.getString(1) + " phone number : "+ rs.getString(2)+ " location : "+ rs.getString(3));
                        }
                    }
                    
                }catch(Exception e){
                    System.out.println(e);
                }
            }
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
                String time ="";
                rs.last();// move curser in to last element
                int sizeOfTheList = rs.getRow();
                rs.beforeFirst();// move curser in to befour first element
                objectBarber = new Object[sizeOfTheList][4];
                while(rs.next()) {
                    time = commen.mappingTimeToTime(rs.getString(3));
                    objectBarber[count][0] = rs.getString(1);
                    objectBarber[count][1] = rs.getString(2);
                    objectBarber[count][2] = time;
                    objectBarber[count][3] = rs.getString(4);
                    count++;
                    
                    System.out.print(rs.getString(1) + ", "+ rs.getString(2)+", "+ time +", "+rs.getString(4)+"\n");
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
                String time = "";
                rs.last();// move curser in to last element
                int sizeOfTheList = rs.getRow();
                rs.beforeFirst();// move curser in to befour first element
                objectCustomer = new Object[sizeOfTheList][4];
                while(rs.next()) {
            	time = commen.mappingTimeToTime(rs.getString(2));
                
                objectCustomer[count][0] = rs.getString(1);
                objectCustomer[count][1] = time;
                objectCustomer[count][2] = rs.getString(3);
                objectCustomer[count][3] = rs.getString(4);
                
                count++;
                
            	System.out.print(rs.getString(1) + ", "+ time +", "+rs.getString(3)+", "+rs.getString(4)+"\n");
                }
            }catch(Exception e) {
                    System.out.println(e);
            }

	}
	
}
