/*
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barber_customer_system;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Carolina
 */
public class Complain {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel(new GridLayout(5,1));
    JLabel nameLabel;
    JTextField name;
    JLabel complainLable;
    JTextField complain;
    JButton button;
    JTextArea textArea;
    
    public Complain(){
        frame.setSize(300, 150);
        frame.setTitle("Complain");
        
        nameLabel = new JLabel("Barber name");
        name = new JTextField();
        
        complainLable = new JLabel("Your Complain");
        
        textArea = new JTextArea(10, 10);
        panel.add(nameLabel);
        panel.add(name);
        
        panel.add(complainLable);
        panel.add(textArea);
        button = new JButton("Submit");
        
        panel.add(button);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
}
