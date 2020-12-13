package barber;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package barber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*; 

public class DataBaseConnection {

	private DataBaseConnection() {
		
	}
	
	private static Connection connection = null;
	
	
	public static Connection getConnection() {
		if(connection == null) {
			try {
				System.out.println("Trying to connet to the database...");
				Class.forName("com.mysql.jdbc.Driver");  
				connection=DriverManager.getConnection(  
				"jdbc:mysql://52.50.23.197:3306/Elton_2019292","Elton_2019292","2019292");
				
				System.out.println("DataBase connection successfull.");
			}catch(Exception e) {
				System.out.println(e);
			}
			return connection;
		}else {
			return connection;
		}
	}

	
}

