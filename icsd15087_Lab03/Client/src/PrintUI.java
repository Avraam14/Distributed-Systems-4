/*	
 * Avraam Katsigras
 * 	321/2015087
 */

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PrintUI {

	public PrintUI(Album result) {										//GUI for printing an album
		JFrame printframe = new JFrame("Album View");
		JPanel contentPane = new JPanel(new BorderLayout());
		JTextArea albumtext = new JTextArea();
		JLabel icon = new JLabel(new ImageIcon(result.getIconURL()));	//Get icon from url
		
		albumtext.setText(result.toString());							//Set text from toString
		albumtext.setEditable(false);
		
		contentPane.add(icon, BorderLayout.NORTH);
		contentPane.add(albumtext, BorderLayout.SOUTH);
		printframe.setSize(new Dimension(500, 600));										
		printframe.setResizable(false);
		printframe.setContentPane(contentPane);											
		printframe.setLocationRelativeTo(null);
		printframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		printframe.setVisible(true);
	}
}