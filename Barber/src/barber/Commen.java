/*
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barber_customer_system;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class Commen {
	private final int REMOVE_POS = 3;
        String[] BarberSearchHeader = {"Name", "Phone no", "Location"};
        String[] ComplaintSearchHeader = {"Customer name", "Complain", "Date"};

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

    public String hasheText(String word) {
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
    public String mappingTimeToTime(String number){
        String time = null;
        switch(number){
            case "1":
                time = "08.00 to 09.00";
                break;
            case "2":
                time = "09.00 to 10.00";
                break;
            case "3":
                time = "10.00 to 11.00";
                break;
            case "4":
                time = "11.00 to 12.00";
                break;    
            case "5":
                time = "13.00 to 14.00";
                break;    
            case "6":
                time = "14.00 to 15.00";
                break; 
            default:
                break;
        }
        return time;
    }
    public void showSearchresult(ResultSet rs, int num){
        JFrame resultFrame = new JFrame();
        resultFrame.setSize(500, 200);
        resultFrame.setTitle("Result");
        JPanel panel = new JPanel(new BorderLayout(2,2));
        DefaultTableModel model;
        try {
            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();
            String[][] data = new String[size][3];
            int count =0;
            while(rs.next()){
                data[count][0] = rs.getString(1);
                data[count][1] = rs.getString(2);
                data[count][2] = rs.getString(3);
                count++;
            }
            
            switch (num) {
                case 0:
                    // barber search
                    model = new DefaultTableModel(data, BarberSearchHeader);
                    break;
                case 1:
                    // complait list search
                    model = new DefaultTableModel(data, ComplaintSearchHeader);
                    break;
                default:
                    //default one...
                    model = new DefaultTableModel(data, BarberSearchHeader);
                    break;
            }
            JTable table = new JTable(model);
            try {
                // 1.6+
                table.setAutoCreateRowSorter(true);
            } catch(Exception continuewithNoSort) {
            }
            JScrollPane tableScroll = new JScrollPane(table);
            tableScroll.setBorder( new TitledBorder("Search Result") );
            
            Dimension tablePreferred = tableScroll.getPreferredSize();
//            tableScroll.setPreferredSize(new Dimension(400, 150) );
            
            panel.add(tableScroll, BorderLayout.CENTER);
            resultFrame.add(panel);
            resultFrame.setLocationRelativeTo(null);
            resultFrame.setVisible(true);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Commen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
