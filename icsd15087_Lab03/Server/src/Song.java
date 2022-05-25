/*	
 * Avraam Katsigras
 * 	321/2015087
 */

import java.io.Serializable;

@SuppressWarnings("serial")
public class Song implements Serializable {
	private String title;
	private String artist;
	private int seconds;
	
	Song(String title, String artist, int seconds) {
		this.title = title;
		this.artist = artist;
		this.seconds = seconds;
	}
	
	@Override
	public String toString() {
		return "\nTitle: " + title + "\n\tArtist: " + artist +
				"\n\tDuration: " + seconds/60 + ":" + seconds%60;
	}
}