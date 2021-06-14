package br.edu.insper.desagil.backend.model;

public class Track {
	private Artist artist;
	private String name;
	private int duration;
	public Track(Artist artist, String name, int duration) {
		this.artist = artist;
		this.name = name;
		this.duration = duration;
	}
	
	public Artist getArtist() {
		return artist;
	}
	public String getName() {
		return name;
	}
	public double getDuration() {
		return duration;
	}
	
	public String getDurationString () {
		int minutos=this.duration/60;
		int segundos=this.duration%60;
		
		String min=Integer.toString(minutos);
		String seg=Integer.toString(segundos);
		if (segundos<10) {
			seg="0"+seg;
			return min+":"+seg;
		}else {
			return min+":"+seg;
		}
	}
	public String getFullArtistName() {
		
		return this.artist.getName();
	}
}
