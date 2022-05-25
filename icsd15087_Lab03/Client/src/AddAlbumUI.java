/*	
 * Avraam Katsigras
 * 	321/2015087
 */

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
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

public class AddAlbumUI {													//GUI for adding an album
	public AddAlbumUI (MusicLibrary mlib) {									
		JFrame addframe = new JFrame();
		JPanel contentPane = new JPanel(new FlowLayout());
		JPanel title_pnl = new JPanel(new FlowLayout());
		JPanel genre_pnl = new JPanel(new FlowLayout());
		JPanel icon_pnl = new JPanel(new FlowLayout());
		JPanel year_pnl = new JPanel(new FlowLayout());
		JLabel title_lbl = new JLabel("Album's title");
		JLabel genre_lbl = new JLabel("Album's genre");
		JLabel icon_lbl = new JLabel("Album Icon");
		JLabel year_lbl = new JLabel("Publishment Year");
		JTextField title = new JTextField(30);								//Textfields containing user input
		JTextField genre = new JTextField(30);
		JFileChooser icon_chsr = new JFileChooser();
		icon_chsr.setAcceptAllFileFilterUsed(false);						//Filechooser for album icon
		icon_chsr.addChoosableFileFilter(new FileNameExtensionFilter(
				"Image files", ImageIO.getReaderFileSuffixes()));
		DateFormat format = new SimpleDateFormat("YYYY");
		JFormattedTextField year = new JFormattedTextField(format);			//Formatted textfield for year
		year.setColumns(4);
		JButton done = new JButton("Continue");
		
		done.addActionListener(ActionEvent ->{
			if(title.getText().isBlank() ||	genre.getText().isBlank() ||
					icon_chsr.getSelectedFile() == null || 
					year.getText().isBlank()) {
				JOptionPane.showMessageDialog(contentPane,
						"Fill in all fields");
			} else {
				int lbmyear = Integer.parseInt(year.getText());				//Parse details
				String lbmtitle = title.getText();
				String lbmgenre = genre.getText();
				String lbmicon = icon_chsr.getSelectedFile().
						getAbsolutePath();
				
				if(lbmyear > Calendar.getInstance().get(Calendar.YEAR)) {	//No music from the future
					JOptionPane.showMessageDialog(contentPane,
							"Definitely ahead of its time!");
				} else {
					try {
						if(mlib.addAlbum(
								lbmtitle, lbmgenre, lbmicon, lbmyear)) {
							JOptionPane.showMessageDialog(contentPane,
									"Album added successfully!");			//If no albums exist with that name
							new AddTracksUI(mlib, title.getText());			//Add tracklist
							addframe.dispose();
						}
						else {												//Can't have duplicate albums
							JOptionPane.showMessageDialog(contentPane,
									"Album already exists");
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
		addframe.setSize(new Dimension(500, 600));										
		addframe.setResizable(false);
		addframe.setContentPane(contentPane);											
		addframe.setLocationRelativeTo(null);
		addframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addframe.setVisible(true);
	}
}