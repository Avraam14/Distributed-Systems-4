import java.awt.Dimension;
import java.awt.FlowLayout;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;

public class AddTracksUI {

	public AddTracksUI(MusicLibrary mlib, String album) {
		JFrame addframe = new JFrame("Add Tracks");
		JPanel contents = new JPanel();
		JScrollPane scrollPane = new JScrollPane(contents);
		JPanel buttons = new JPanel(new FlowLayout());
		JButton add = new JButton("Add Song");
		JButton done = new JButton("Done");
		DateFormat df = new SimpleDateFormat("mm:ss");
		ArrayList<JTextField> titles = new ArrayList<JTextField>();
		ArrayList<JTextField> artists = new ArrayList<JTextField>();
		ArrayList<JFormattedTextField> durs = new ArrayList<JFormattedTextField>();
		
		add.addActionListener(ActionEvent-> {
			if(!titles.isEmpty())
				if(titles.get(titles.size() - 1).getText().isEmpty() ||
						artists.get(artists.size() - 1).getText().isEmpty() || 
						durs.get(durs.size() - 1).getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Fill in all the fields");
					return;
				}
			JPanel title_pnl = new JPanel(new FlowLayout());
			JPanel artist_pnl = new JPanel(new FlowLayout());
			JPanel dur_pnl = new JPanel(new FlowLayout());
			JLabel title_lbl = new JLabel("Song title:");
			JLabel artist_lbl = new JLabel("Artist:");
			JLabel dur_lbl = new JLabel("Duration (mm:ss) :");
			JTextField title = new JTextField(30);
			JTextField artist = new JTextField(30);
			JFormattedTextField duration = new JFormattedTextField(df);
			duration.setColumns(5);
			titles.add(title);
			artists.add(artist);
			durs.add(duration);
			
			title_pnl.add(title);
			artist_pnl.add(artist);
			dur_pnl.add(duration);
			contents.add(title_lbl);
			contents.add(title_pnl);
			contents.add(artist_lbl);
			contents.add(artist_pnl);
			contents.add(dur_lbl);
			contents.add(dur_pnl);
			addframe.revalidate();
		});
		
		done.addActionListener(ActionEvent-> {
			if(!titles.isEmpty()) {
				if(titles.get(titles.size() - 1).getText().isEmpty() ||
						artists.get(artists.size() - 1).getText().isEmpty() || 
						durs.get(durs.size() - 1).getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Fill in all the fields");
				} else {
					ArrayList<String> sngtitles = new ArrayList<String>();
					ArrayList<String> sngartists = new ArrayList<String>();
					ArrayList<Integer> sngdurs = new ArrayList<Integer>();
					Calendar temp = Calendar.getInstance();
					for(int i = 0; i < titles.size(); i++) {
						try {
							temp.setTime(df.parse(durs.get(i).getText()));
						} catch (ParseException e) {
							JOptionPane.showMessageDialog(null, "Invalid Duration");
							return;
						}
						sngtitles.add(titles.get(i).getText());
						sngartists.add(artists.get(i).getText());
						sngdurs.add(temp.get(Calendar.SECOND));
					}
					try {
						mlib.addTracks(album, sngtitles, sngartists, sngdurs);
						addframe.dispose();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		buttons.add(add);
		buttons.add(done);
		scrollPane.setLayout(new ScrollPaneLayout());
		scrollPane.add(buttons);
		contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
		addframe.getContentPane().setLayout(new BoxLayout(addframe.getContentPane(), BoxLayout.Y_AXIS));
		addframe.getContentPane().add(scrollPane);
		addframe.getContentPane().add(buttons);
		addframe.setSize(new Dimension(500, 600));										
		addframe.setResizable(false);								
		addframe.setLocationRelativeTo(null);
		addframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addframe.setVisible(true);
	}

}