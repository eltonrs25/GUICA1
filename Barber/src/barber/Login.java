package barber;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package barber;

import barber.DataBaseConnection;
import barber.MainView;
import barber.Commen;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.PreparedStatement;

import java.sql.*; 

public class Login {
	private  JFrame jframe;
	private  JPanel jpanel;
	private  JLabel userName;
	private  JLabel password;
	private  JTextField userNameField;
	private  JPasswordField passwordField;
	private  JButton loginButton;
	private Commen common = new Commen();
	private String errorMessage = "";
	
	public Login() {
		init();
	}
	
	private void init() {
		jframe = new JFrame();
		jpanel = new JPanel();
		
		jframe.setSize(450, 200);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setTitle("Login");
		
		jframe.add(jpanel);
		
		jpanel.setLayout(null);
		userName = new JLabel("UserName");
		userName.setBounds(20, 30, 80, 20);
		jpanel.add(userName);
		
		userNameField = new JTextField(20);
		userNameField.setBounds(120, 30, 200, 20);
		jpanel.add(userNameField);
		
		password = new JLabel("Password");
		password.setBounds(20, 70, 80, 20);
		jpanel.add(password);
		
		passwordField = new JPasswordField(20);
		passwordField.setBounds(120, 70, 200, 20);
		jpanel.add(passwordField);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(120, 120, 80, 25);
		jpanel.add(loginButton);
		loginButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				onClickLogin();
			}

		});
		
		jframe.setVisible(true);
	}
	private void onClickLogin() {
		
		String x = "abc";
		String pwd = common.hasheText("Shashintha@1");
		errorMessage = "";
		System.out.println("click login");
		String userNameText = userNameField.getText();
		String passwordText = passwordField.getText();
		
		if(userName.equals("") || password.equals("")) {
			errorMessage = "username or password empty!";
		}else {
			String hasedPassword = common.hasheText(passwordText);
			Connection connection = DataBaseConnection.getConnection();
			
			if(connection == null) {
				errorMessage = "there is a connection problem!";
			}else {
				try {
					System.out.println("username :"+ userNameText + "password :"+ hasedPassword);
					PreparedStatement st = (PreparedStatement) connection
	                        .prepareStatement("select email, password, users.user_id, user_type from users join user_type on users.user_id = user_type.user_id where users.email = ? and users.password = ?");
					st.setString(1, userNameText);
                    st.setString(2, hasedPassword);
                    ResultSet rs = st.executeQuery();
                    
					if(rs.next()) {
						new MainView(Integer.parseInt(rs.getString(3)), Integer.parseInt(rs.getString(4)), userNameText);
						jframe.setVisible(false);
						
					}else {
						errorMessage = "username or password is incorrect";
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
			
		if(!errorMessage.equals("")) {
			common.showErrorDialog(errorMessage);
		}
		
	}

}
