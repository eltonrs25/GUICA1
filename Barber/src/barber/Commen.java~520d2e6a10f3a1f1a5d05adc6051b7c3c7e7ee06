package barber;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package barber;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Commen {
	private final int REMOVE_POS = 3;
	
	public String hasheText(String word) {//hashing algorithm
		String remove = "";
		String hashed = "";
		for(int i=0;i<word.length();i++) {
			
			if(i%REMOVE_POS != 0 ) {
				hashed = hashed + word.charAt(i);
			}else {
				remove = remove+ word.charAt(i);
			}
		}
		hashed = hashed + remove;
		
		return hashed;
	}
	//show error dialog
	public void showErrorDialog(String errorMessage) {
		JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Error!",
		        JOptionPane.ERROR_MESSAGE);
	}
	// show worning dialog
	public void showWorningMessage(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Werning!",
		        JOptionPane.WARNING_MESSAGE);
	}
	public void showSuccessMessage(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Success!",
		        JOptionPane.PLAIN_MESSAGE);
	}

}

