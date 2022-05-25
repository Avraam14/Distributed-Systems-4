/*	
 * Avraam Katsigras
 * 	321/2015087
 */

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

public class Client {												//Main client class
	public static void main(String[] args) {
		try {
			MusicLibrary mlib = (MusicLibrary) 						//Initiate remote object
					Naming.lookup("//localhost/MusicLib");
			new UserMenu(mlib);										//Initiate GUI
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "Server is offline");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
}
