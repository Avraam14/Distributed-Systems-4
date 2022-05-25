/*	
 * Avraam Katsigras
 * 	321/2015087
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LibraryDB {
	private Statement stat;
	private Connection conn;
	
	public LibraryDB() {													//System's database 
		try {
			Class.forName("org.sqlite.JDBC");								//Load JDBC
			conn = DriverManager.getConnection("jdbc:sqlite:library.db");	//Connect to DB
			conn.setAutoCommit(false);										//Commit manually
			stat = conn.createStatement();									//Initialize statement
			System.out.println ("Database connection established");
			init();															//This runs the first time
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void init() {													//Create the tables needed if they don't exist
		try {
			stat.executeUpdate("CREATE table song "
					+ "(title varchar(20), artist varchar(20), "
					+ "seconds int, album varchar(20));");
			conn.commit();
			stat.executeUpdate("CREATE table album "
					+ "(title varchar(20) UNIQUE, genre varchar(20), "
					+ "icon varchar(50), year int);");
			conn.commit();
		} catch (SQLException e) {
			if(!e.getMessage().equals("[SQLITE_ERROR] SQL error "			//If they exist ignore this
					+ "or missing database (table song already exists)")) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean addAlbum(String title, String genre,						//Add album to database
			String iconURL, int year) {
		if(albumExists(title)) return false;								//No duplicates allowed
		try {
			stat.executeUpdate(
					"INSERT INTO ALBUM VALUES ('" + title + 				//Insert album into the table
					"', '" + genre + "', '" + iconURL + 
					"', '" + year + "');");
			conn.commit();													//Commit
			return true;													//Everything went smoothly
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addTracks(String title, ArrayList<String> titles,		//Add tracks to existing album
			ArrayList<String> artists, ArrayList<Integer> seconds) {
		try {
			PreparedStatement prep = conn.prepareStatement(					//Prepare a statement to send a batch of commands
					"INSERT INTO SONG VALUES (?, ?, ?, ?);");
			for(int i = 0; i < titles.size(); i++) {						//For each entry in the lists
				prep.setString(1, titles.get(i));							//Set each value in the command
				prep.setString(2, artists.get(i));
				prep.setInt(3, seconds.get(i));
				prep.setString(4, title);
				prep.addBatch();											//Add the batch
			}
			prep.executeBatch();											//Execute all
			conn.commit();													//Commit
			return true;													//No errors
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean albumExists(String title) {  							//Check if album exists
		try {
			ResultSet records = stat.executeQuery(							//Search for entry by title
					"SELECT * FROM ALBUM WHERE ALBUM.title = '"
							+ title + "';");
			return records.next();											//Return whether there is one or not
		} catch (SQLException e) {
			if(!e.getMessage().equals("[SQLITE_ERROR] SQL error or "
					+ "missing database (no such column: " + title + ")"));	//Ignore in this case
				e.printStackTrace();
		}
		return false;
	}

	public Album getAlbum(String title) {									//Get album by title
		Album result = null;
		ArrayList<Song> songs = new ArrayList<Song>();						//Tracklist
		try {
			ResultSet records = stat.executeQuery(
					"SELECT * FROM SONG WHERE SONG.album = '"				//Save all songs with registered album
							+ title + "';");								//the one we're looking for
			while(records.next()) {
				songs.add(new Song(records.getString("title"),
						records.getString("artist"), 
						records.getInt("seconds")));
			}
			
			records = stat.executeQuery(									//Find the album by title
					"SELECT * FROM ALBUM WHERE ALBUM.title = '"
							+ title + "';");
			if(records.next()) {
				result = new Album(records.getString("title"),				//Create an Object with the results
						records.getString("genre"),
						records.getString("icon"),
						records.getInt("year"), songs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean modifyAlbum(String title, Album album) {					//Modify album by image of another album
		Album og = getAlbum(title);											//Get original album by title
		try {
			if(!og.getTitle().equals(album.getTitle())) { 					//If title is updated, update it in the file
				stat.executeQuery("UPDATE SONG SET album = '"				//Both in all the songs and in the album
						+ album.getTitle() + "' WHERE album = '"
						+ title + "';");
				conn.commit();
				stat.executeUpdate("UPDATE ALBUM SET title = '"
				+ album.getTitle() +  "' WHERE title = '"
						+ og.getTitle() + "';");
				conn.commit();
			}
			if(!og.getGenre().equals(album.getGenre())) {					//If genre is updated, update it in the file etc etc
				stat.executeUpdate("UPDATE ALBUM SET genre = '"
						+ album.getGenre() + "' WHERE genre = '"
						+ og.getGenre() + "';");
				conn.commit();
			}
			if(!og.getIconURL().equals(album.getIconURL())) {
				stat.executeUpdate("UPDATE ALBUM SET icon = '"
						+ album.getIconURL() + "' WHERE icon = '"
						+ og.getIconURL() + "';");
				conn.commit();
			}
			if(og.getYear() != album.getYear()) {
				stat.executeUpdate("UPDATE ALBUM SET year = '"
						+ album.getYear() + "' WHERE year = '"
						+ og.getYear() + "';");
				conn.commit();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteAlbum(String title) {								//Delete album by title
		try {
			stat.executeUpdate("DELETE FROM ALBUM WHERE "					//Delete the album
					+ "ALBUM.title = '" + title + "';");
			conn.commit();
			stat.executeUpdate("DELETE FROM SONG WHERE "					//Delete the songs too
					+ "SONG.album ='" + title + "';");
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}