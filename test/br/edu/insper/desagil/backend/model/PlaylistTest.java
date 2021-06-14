package br.edu.insper.desagil.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlaylistTest {
	private static double DELTA = 0.000001;

	@BeforeEach
	void setUp() {
	}

	@Test
	void testRoundDownToZero() {
		Playlist p1=new Playlist(1);
		p1.putRating("Thiago", 1);
		p1.putRating("Pedro", 2);
		p1.putRating("Tomás", 3);
		p1.putRating("Fabrizio", 3);
		assertEquals(p1.averageRatings(), 2.0);
	}

	@Test
	void testRoundUpToHalf() {
		Playlist p2=new Playlist(2);
		p2.putRating("Thiago", 1);
		p2.putRating("Pedro", 2);
		p2.putRating("Tomás", 1);
		assertEquals(p2.averageRatings(), 1.5);
	}

	@Test
	void testRoundDownToHalf() {
		Playlist p3=new Playlist(3);
		p3.putRating("Thiago", 1);
		p3.putRating("Pedro", 2);
		p3.putRating("Tomás", 2);
		assertEquals(p3.averageRatings(), 1.5);
	}

	@Test
	void testRoundUpToOne() {
		Playlist p4=new Playlist(4);
		p4.putRating("Thiago", 1);
		p4.putRating("Pedro", 1);
		p4.putRating("Tomás", 2);
		p4.putRating("Fabrizio", 3);
		assertEquals(p4.averageRatings(), 2.0);
	}
}
