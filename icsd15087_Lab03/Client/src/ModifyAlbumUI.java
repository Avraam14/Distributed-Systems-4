/*	
 * Avraam Katsigras
 * 	321/2015087
 */

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.io.File;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ModifyAlbumUI {													//Modify Album GUI

	public ModifyAlbumUI(MusicLibrary mlib, Album result) {
		JFrame modframe = new JFrame("Modify an Album");
		JPanel contentPane = new JPanel(new FlowLayout());
		JPanel title_pnl = new JPanel(new FlowLayout());
		JPanel genre_pnl = new JPanel(new FlowLayout());
		JPanel icon_pnl = new JPanel(new FlowLayout());
		JPanel year_pnl = new JPanel(new FlowLayout());
		JLabel title_lbl = new JLabel("Album's title");
		JLabel genre_lbl = new JLabel("Album's genre");
		JLabel icon_lbl = new JLabel("Album Icon");
		JLabel year_lbl = new JLabel("Publishment Year");
		JTextField title = new JTextField(30);									//Text fields with details
		JTextField genre = new JTextField(30);
		JFileChooser icon_chsr = new JFileChooser();							//Same as AddAlbumUI
		icon_chsr.setAcceptAllFileFilterUsed(false);
		icon_chsr.addChoosableFileFilter(new FileNameExtensionFilter(			//File chooser for icon
				"Image files", ImageIO.getReaderFileSuffixes()));
		DateFormat format = new SimpleDateFormat("YYYY");
		JFormattedTextField year = new JFormattedTextField(format);
		year.setColumns(4);
		JButton done = new JButton("Continue");
		
		title.setText(result.getTitle());										//Fill the fields with the old values
		genre.setText(result.getGenre());
		icon_chsr.setCurrentDirectory(new File(result.getIconURL()));
		year.setText(String.valueOf(result.getYear()));
		
		done.addActionListener(ActionEvent ->{
			if(title.getText().isBlank() ||	genre.getText().isBlank() ||		//No blank fields
					icon_chsr.getSelectedFile() == null || 
					year.getText().isBlank()) {
				JOptionPane.showMessageDialog(contentPane,
						"Fill in all fields");
			} else if (title.getText().equals(result.getTitle()) &&				//If there are no changes just exit
					genre.getText().equals(result.getGenre()) &&
					icon_chsr.getSelectedFile().getPath().equals(
							result.getIconURL()) && 
					year.getText().equals(
							String.valueOf(result.getYear()))){
				modframe.dispose();
			} else {
				int lbmyear = Integer.parseInt(year.getText());					//Parse details
				String lbmtitle = title.getText();
				String lbmgenre = genre.getText();
				String lbmicon = icon_chsr.getSelectedFile()
						.getAbsolutePath();
				
				if(lbmyear > Calendar.getInstance().get(Calendar.YEAR)) {		//No music from the future
					JOptionPane.showMessageDialog(contentPane,
							"Definitely ahead of its time!");
				} else {
					try {
						if(mlib.modifyAlbum(result.getTitle(), 					//Go through with modifying
								new Album(lbmtitle, lbmgenre, 
										lbmicon, lbmyear, null))) {
							JOptionPane.showMessageDialog(contentPane,
									"Album modified successfully!");
							modframe.dispose();
						}
						else {													//Can't have duplicate events
							JOptionPane.showMessageDialog(contentPane,
									"There was an error");
						}
					} catch (HeadlessException | RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		});

		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		title_pnl.add(title);
		genre_pnl.add(genre);
		icon_pnl.add(icon_chsr);
		year_pnl.add(year);
		contentPane.add(title_lbl);
		contentPane.add(title_pnl);
		contentPane.add(genre_lbl);
		contentPane.add(genre_pnl);
		contentPane.add(icon_lbl);
		contentPane.add(icon_pnl);
		contentPane.add(year_lbl);
		contentPane.add(year_pnl);
		contentPane.add(done);
		title_lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		genre_lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		icon_lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		year_lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		modframe.setSize(new Dimension(500, 600));										
		modframe.setResizable(false);
		modframe.setContentPane(contentPane);											
		modframe.setLocationRelativeTo(null);
		modframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		modframe.setVisible(true);
	}
}