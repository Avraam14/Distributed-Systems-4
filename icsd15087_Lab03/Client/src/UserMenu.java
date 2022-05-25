/*	
 * Avraam Katsigras
 * 	321/2015087
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class UserMenu {
	public UserMenu(MusicLibrary mlib) {											//Main menu
		JFrame mainframe = new JFrame("Music Library");
		JPanel contentPane = new JPanel(new FlowLayout());
		JButton addlbm = new JButton("Add Album");									//Buttons for each function
		JButton editlbm = new JButton("Edit Album");
		JButton dellbm = new JButton("Delete Album");
		JButton printlbm = new JButton("View Album");
		
		addlbm.addActionListener(ActionEvent ->{
			new AddAlbumUI(mlib);
		});
		
		editlbm.addActionListener(ActionEvent ->{
			String title = JOptionPane.showInputDialog("Insert album name");
			if(title.equals(String.valueOf(JOptionPane.CLOSED_OPTION)) 
					|| title == null) return;
			Album result = null;
			try {
				result = mlib.getAlbum(title);										//Find album by title to edit
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if(result != null) {
				new ModifyAlbumUI(mlib, result);
			}
			else
				JOptionPane.showMessageDialog(null, "Album wasn't found");
		});
		
		
		dellbm.addActionListener(ActionEvent ->{
			String title = JOptionPane.showInputDialog("Insert album name");
			if(title.equals(String.valueOf(JOptionPane.CLOSED_OPTION)) 
					|| title == null) return;
			try {
				if(mlib.deleteAlbum(title))											//Delete album by title
					JOptionPane.showMessageDialog(null, "Album deleted");
				else 
					JOptionPane.showMessageDialog(null, "There was an error");
			} catch (HeadlessException | RemoteException e) {
				e.printStackTrace();
			}
		});
		
		printlbm.addActionListener(ActionEvent ->{
			String title = JOptionPane.showInputDialog("Insert album name");
			Album result = null;
			try {
				result = mlib.getAlbum(title);										//View album by title
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if(result != null) {
				new PrintUI(result);
			}
			else
				JOptionPane.showMessageDialog(null, "Album wasn't found");
		});
		
		contentPane.add(addlbm);
		contentPane.add(editlbm);
		contentPane.add(dellbm);
		contentPane.add(printlbm);
		mainframe.setContentPane(contentPane);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setSize(new Dimension(500, 100));
		mainframe.setLocationRelativeTo(null);
		mainframe.setVisible(true);
	}
}
