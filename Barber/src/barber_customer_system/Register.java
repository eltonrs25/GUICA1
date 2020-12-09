/*
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barber_customer_system;




import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.util.regex.*;

public class Register{
    private final int PWD_MIN_LENGTH = 6;
    private Commen commen = new Commen();

    private  JFrame jframe;
    private  JPanel jpanel;
    private  JLabel name;
    private  JLabel phoneNo;
    private  JLabel email;
    private  JLabel password;
    private  JLabel confirmPassword;
    private  JLabel userType;
    private  JLabel location;

    private  JTextField nameField;
    private  JTextField phoneField;
    private  JTextField emailField;
    private  JPasswordField passwordField;
    private  JPasswordField confirmPasswordField;
    private  JTextField locationField;
    private JCheckBox checkBarber;

    private JButton submitButton;

    private int customerType = 1;
    String errorMessage = "";
    String regex = "^(?=.*[a-z])(?=."
        + "*[A-Z])(?=.*\\d)"
        + "(?=.*[-+_!@#$%^&*., ?]).+$";// regex pattern for check lovercase, uppercase, special caracter & numbers

    public Register() {
        jframe = new JFrame();
        jpanel = new JPanel();

        jframe.setSize(450, 400);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("register");

        jframe.add(jpanel);
        jpanel.setLayout(null);

        name = new JLabel("Name :");
        name.setBounds(20, 30, 80, 20);
        jpanel.add(name);

        nameField = new JTextField(20);
        nameField.setBounds(120, 30, 200, 20);
        jpanel.add(nameField);

        phoneNo = new JLabel("Phone No. :");
        phoneNo.setBounds(20, 70, 80, 20);
        jpanel.add(phoneNo);

        phoneField = new JTextField(20);
        phoneField.setBounds(120, 70, 200, 20);
        jpanel.add(phoneField);

        email = new JLabel("Email :");
        email.setBounds(20, 110, 80, 20);
        jpanel.add(email);

        emailField = new JTextField(20);
        emailField.setBounds(120, 110, 200, 20);
        jpanel.add(emailField);

        password = new JLabel("Password :");
        password.setBounds(20, 150, 80, 20);
        jpanel.add(password);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(120, 150, 200, 20);
        jpanel.add(passwordField);

        confirmPassword = new JLabel("Con. Password :");
        confirmPassword.setBounds(20, 190, 100, 20);
        jpanel.add(confirmPassword);

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBounds(120, 190, 200, 20);
        jpanel.add(confirmPasswordField);

        userType = new JLabel("User Type :");
        userType.setBounds(20, 230, 80, 20);
        jpanel.add(userType);

        checkBarber = new JCheckBox("I'm a barber");
        checkBarber.setBounds(120, 230, 200, 20);
        jpanel.add(checkBarber);

        location = new JLabel("location :");
        location.setBounds(20, 270, 80, 20);
        jpanel.add(location);
        location.setVisible(false);


        locationField = new JTextField(20);
        locationField.setBounds(120, 270, 200, 20);
        jpanel.add(locationField);
        locationField.setVisible(false);

        checkBarber.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                if (checkBarber.isSelected()) {
                    setCustomerType(0);
                    location.setVisible(true);
                    locationField.setVisible(true);


                }
                else {
                    setCustomerType(1);
                    location.setVisible(false);
                    locationField.setVisible(false);

                }
            }
        });

//		if(getCustomerType() == 0) {
//			location = new JLabel("location :");
//			location.setBounds(20, 270, 80, 20);
//			jpanel.add(location);
//			
//			locationField = new JTextField(20);
//			locationField.setBounds(270, 270, 200, 20);
//			jpanel.add(locationField);
//		}

        submitButton = new JButton("Register");
        submitButton.setBounds(120, 310, 80, 25);
        jpanel.add(submitButton);
        submitButton.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                        onClickSubmit();
                }

        });

        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);

    }

    public void setCustomerType(int type) {
            this.customerType = type;
    }

    public int getCustomerType() {
            return this.customerType;
    }
    public void onClickSubmit() {
        int insertSuccess = 0;
        String name = nameField.getText();
        String phoneNo = phoneField.getText();
        String email = emailField.getText();
        String con_pwd = confirmPasswordField .getText();
        String pwd = passwordField.getText();
        String locationTxt = null;
        if(customerType == 0) {
                locationTxt = locationField.getText();
        }
        if(name.equals("") || phoneNo.equals("") || email.equals("") || con_pwd.equals("") || pwd.equals("")) {
                errorMessage = "All fields are mandotary.";
        }else {
            if(customerType == 0 && (locationTxt.equals(null) || locationTxt.equals(""))) {
                    errorMessage = "location field is mandatory for barber.";
            }else {
                errorMessage = "";
                if(!pwd.equals(con_pwd)) {
                        errorMessage = "Password not mached.";
                }else {
                    errorMessage = "";
                    if(pwd.length()<PWD_MIN_LENGTH) {
                            errorMessage = "Password contains more than 6 characters";
                    }else {
                        errorMessage = "";
                        Pattern p = Pattern.compile(regex);// initialize the regex pattern
                        Matcher m = p.matcher(pwd);// check pwd contains lovercase, uppercase and spesial caracter
                        if (!m.matches()) {
                                errorMessage = "Password should contains at least one uppercase,\n"+"lovercase and spesial caracter";
                        }else {
                            errorMessage = "";
                            String hasedPassowrd = commen.hasheText(pwd);

                            Connection connection = DataBaseConnection.getConnection();
                            if(connection == null) {
                                errorMessage = "there is a connection problem!";
                            }else {
                                errorMessage = "";
                                try {
                                    System.out.println("trying to insert user deatails");
                                    String insert = "insert into users (name, phone_no, email, password, location) values (?, ?, ?, ?, ?)";
                                    try (PreparedStatement stm = (PreparedStatement) connection.prepareStatement(insert)) {
                                        stm.setString(1, name);
                                        stm.setString(2, phoneNo);
                                        stm.setString(3, email);
                                        stm.setString(4, hasedPassowrd);
                                        stm.setString(5, locationTxt);
                                            insertSuccess = stm.executeUpdate();
                                            // 1 when inserted 1 record
                                    }catch(Exception e) {
                                            System.out.println(e);
                                    }

                                        String insertType = "insert into user_type (user_id, user_type) values ((select user_id from users where email = ? and name = ?), ?)";
                                    try (PreparedStatement stm_2 = (PreparedStatement) connection.prepareStatement(insertType)) {
                                        stm_2.setString(1, email);
                                            stm_2.setString(2, name);
                                            stm_2.setInt(3, customerType);

                                            insertSuccess = stm_2.executeUpdate();
                                    }catch(Exception e) {
                                                System.out.println(e);
                                        }
                                    if(customerType == 0) {
                                        String insertLocation = "insert into location (user_id, location) values ((select user_id from users where email = ? and name = ?), ?)";
                                        try (PreparedStatement stm_3 = (PreparedStatement) connection.prepareStatement(insertLocation)) {
                                            stm_3.setString(1, email);
                                            stm_3.setString(2, name);
                                            stm_3.setString(3, locationTxt);

                                            insertSuccess = stm_3.executeUpdate();
                                        }catch(Exception e) {
                                            System.out.println(e);
                                        }
                                    } 

                                    if(insertSuccess != 1) {
                                        System.out.println("data insert fail!");
                                        errorMessage = "there is a connection problem please try again";
                                    }else {
                                        System.out.println("data insert success!");
                                        jframe.setVisible(false);
                                        new Login();
                                    }

                                }catch(Exception e) {
                                        System.out.println(e);
                                }
                            }

                        }
                    }

                }
            }

        }
        if(!errorMessage.equals("")) {
                commen.showErrorDialog(errorMessage);
        }

    }

}
