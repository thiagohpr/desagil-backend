package br.edu.insper.desagil.backend.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Playlist {
	private int id;
	private List<Track>tracks;
	private Map<String, Integer> ratings;
	
	public Playlist(int id) {
		this.id = id;
		this.tracks=new ArrayList<>();
		this.ratings=new HashMap<>();
	}
	
	public int getId() {
		return id;
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public Map<String, Integer> getRatings() {
		return ratings;
	}

	public void addTrack(Track faixa) {
		this.tracks.add(faixa);
	}
	
	public void putRating (String nome,int avaliacao) {
		ratings.put(nome, avaliacao);
	}
	public double averageRatings () {
		double average=0;
		int soma=0;
		int n=this.ratings.size();
		
		for (int rating:this.ratings.values()) {
			soma+=rating;
		}
		average=soma/(double)n;
		
		int i = (int) average;
		double d = average - i;
		double aprox=0;
		if (d<0.26) {
			aprox=0;
		}
		else if (d>=0.26 && d<0.74) {
			aprox=0.5;
			
		}else if (d>=0.74) {
			aprox=1;
		}
		return i + aprox;
	}
}
