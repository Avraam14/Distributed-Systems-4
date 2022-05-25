/*	
 * Avraam Katsigras
 * 	321/2015087
 */

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Album implements Serializable{						
	private String title;											//Album details
	private String genre;
	private String iconURL;
	private int year;
	private ArrayList<Song> tracklist;			
	
	public Album(String title, String genre, String iconURL, 
			int year, ArrayList<Song> tracklist) {
		this.title = title;
		this.genre = genre;
		this.iconURL = iconURL;
		this.year = year;
		this.tracklist = tracklist;
	}

	public String getTitle() {										//Getters
		return title;
	}

	public String getGenre() {
		return genre;
	}
	
	public String getIconURL() {
		return iconURL;
	}

	public int getYear() {
		return year;
	}
	
	@Override
	public String toString() {										//Override toString for printing	
		String result = "Title: " + title + "\nGenre: " + genre + 
				"\nYear: " + year + "\nTracklist: ";
		
		for(Song track : tracklist) {
			result += track.toString();
		}
		
		return result;
	}
}