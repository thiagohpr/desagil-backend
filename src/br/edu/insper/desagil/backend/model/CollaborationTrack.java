package br.edu.insper.desagil.backend.model;

import java.util.ArrayList;
import java.util.List;

public class CollaborationTrack extends Track{
	private List<Artist> collaborators;
	public CollaborationTrack(Artist artist, String name, int duration,List<Artist> collaborators) {
		super(artist, name, duration);
		this.collaborators=collaborators;
		
	}
	@Override
	public  String getFullArtistName() {
		List<String> nomes = new ArrayList<>();
		String feat;
		for (Artist artist:this.collaborators) {
			nomes.add(artist.getName());
		}
		if (nomes.size()>1) {
			feat=String.join(", ", nomes);
		}else {
			feat=nomes.get(0);
		}
		String artistName=super.getArtist().getName();
		return artistName + " (feat. " + feat +")";
	}
}
