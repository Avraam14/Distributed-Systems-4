/*	
 * Avraam Katsigras
 * 	321/2015087
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class LibraryOperations extends UnicastRemoteObject 
implements MusicLibrary {												//Remote object
	private LibraryDB database;
	
	public LibraryOperations() throws RemoteException {
		super();														//Initiate object with the database
		database = new LibraryDB();
	}

	@Override
	public boolean addAlbum(String title, String genre, 				//Add album
			String iconURL, int year) throws RemoteException {
		boolean result;
		synchronized(database) {										//The database gets locked whenever the object uses it
			result = database.addAlbum(title, genre, iconURL, year);	//Add album to DB
		}
		return result;
	}

	@Override
	public boolean addTracks(String title, ArrayList<String> titles,	//Add tracks to album by title
			ArrayList<String> artists, ArrayList<Integer> seconds) 
					throws RemoteException {
		boolean result;
		synchronized(database) {										//Again lock database
			result = database.addTracks(title, titles, artists, seconds);
		}
		return result;
	}

	@Override
	public Album getAlbum(String title) throws RemoteException {		//Get album by title
		Album result = null;
		synchronized(database) {
			result = database.getAlbum(title);
		}
		return result;
	}

	@Override
	public boolean modifyAlbum(String title, Album album) 				//Modify album
			throws RemoteException {
		boolean result = false;
		synchronized(database) {
			result = database.modifyAlbum(title, album);
		}
		return result;
		
	}

	@Override
	public boolean deleteAlbum(String title) throws RemoteException {	//Delete album by title
		boolean result = false;
		synchronized(database) {
			result = database.deleteAlbum(title);
		}
		return result;
	}

}