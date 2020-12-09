package barber;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Barber{
	
	private static JFrame jframe;
	private static JPanel jpanel;
	private static JButton login;
	private static JButton signup;
	private static JLabel wellcomeMessage;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jframe = new JFrame();
		jpanel = new JPanel();
		
		jframe.setSize(450, 200);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setTitle("Barber/Hairdresser Appointment System");
		
		jframe.add(jpanel);
		
		jpanel.setLayout(null);
		wellcomeMessage = new JLabel("Wellcome to the Barber/Hairdresser Appointment System");
		wellcomeMessage.setBounds(20, 10, 400, 20);
		jpanel.add(wellcomeMessage);
		
		login = new JButton("Login");
		login.setBounds(100, 80, 80, 25);
		jpanel.add(login);
		login.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				onClickLogin();
			}

		});
		
		
		signup = new JButton("Register");
		signup.setBounds(200, 80, 80, 25);
		jpanel.add(signup);
		signup.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				onClickRegister();
			}

		});
		
		
		jframe.setVisible(true);
//		new DashBoard(0, 2, "eltonrs25@hotmail.com");
	}
	//login path
	private static void onClickLogin() {
		System.out.println("login is called from main frame");
		jframe.setVisible(false);
		new Login();
	}
	//registration path
	private static void onClickRegister() {
		System.out.println("register is called from main frame");
		jframe.setVisible(false);
		new Register();
	}


}

