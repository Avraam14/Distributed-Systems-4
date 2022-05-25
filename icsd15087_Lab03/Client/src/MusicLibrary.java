/*	
 * Avraam Katsigras
 * 	321/2015087
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface MusicLibrary extends Remote {							//Interface for the remote objects with every function
	public boolean addAlbum(String title, String genre, 
			String iconURL, int year) throws RemoteException;
	
	public boolean addTracks(String title, ArrayList<String> titles, ArrayList<String> 
	artists, ArrayList<Integer> seconds) throws RemoteException;
	
	public Album getAlbum(String title) throws RemoteException;
	
	public boolean modifyAlbum(String title, Album album) throws RemoteException;
	
	public boolean deleteAlbum(String title) throws RemoteException;
}