package br.edu.insper.desagil.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrackTest {
	@BeforeEach
	void setUp() {
		
	}

	@Test
	void testZeroSeconds() {
		Artist thiago=new Artist ("Thiago");
		Track t1=new Track (thiago,"Desagil",0);
		assertEquals(t1.getDurationString(), "0:00");
	}

	@Test
	void testFiveSeconds() {
		Artist thiago=new Artist ("Thiago");
		Track t2=new Track (thiago,"Desagil",5);
		assertEquals(t2.getDurationString(), "0:05");
	}

	@Test
	void testTwentyFiveSeconds() {
		Artist thiago=new Artist ("Thiago");
		Track t3=new Track (thiago,"Desagil",25);
		assertEquals(t3.getDurationString(), "0:25");
	}

	@Test
	void testOneMinuteZeroSeconds() {
		Artist thiago=new Artist ("Thiago");
		Track t4=new Track (thiago,"Desagil",60);
		assertEquals(t4.getDurationString(), "1:00");
	}

	@Test
	void testOneMinuteFiveSeconds() {
		Artist thiago=new Artist ("Thiago");
		Track t5=new Track (thiago,"Desagil",65);
		assertEquals(t5.getDurationString(), "1:05");
	}

	@Test
	void testOneMinuteTwentyFiveSeconds() {
		Artist thiago=new Artist ("Thiago");
		Track t6=new Track (thiago,"Desagil",85);
		assertEquals(t6.getDurationString(), "1:25");
	}

	@Test
	void testTwoMinutesZeroSeconds() {
		Artist thiago=new Artist ("Thiago");
		Track t7=new Track (thiago,"Desagil",120);
		assertEquals(t7.getDurationString(), "2:00");
	}

	@Test
	void testTwoMinutesFiveSeconds() {
		Artist thiago=new Artist ("Thiago");
		Track t8=new Track (thiago,"Desagil",125);
		assertEquals(t8.getDurationString(), "2:05");
	}

	@Test
	void testTwoMinutesTwentyFiveSeconds() {
		Artist thiago=new Artist ("Thiago");
		Track t9=new Track (thiago,"Desagil",145);
		assertEquals(t9.getDurationString(), "2:25");
	}

	@Test
	void testOneCollaborator() {
		Artist anitta=new Artist ("Anitta");
		Artist becky=new Artist ("Becky G");
		List<Artist> colaboradores=new ArrayList<>();
		colaboradores.add(becky);
		CollaborationTrack poderosas=new CollaborationTrack (anitta,"Show das Poderosas",120,colaboradores);
		assertEquals(poderosas.getFullArtistName(), "Anitta (feat. Becky G)");
	}

	@Test
	void testTwoCollaborators() {
		Artist anitta=new Artist ("Anitta");
		Artist ludmilla=new Artist ("Ludmilla");
		Artist snoop=new Artist ("Snoop Dog");
		List<Artist> colaboradores=new ArrayList<>();
		colaboradores.add(ludmilla);
		colaboradores.add(snoop);
		CollaborationTrack poderosas2=new CollaborationTrack (anitta,"Show das Poderosas",120,colaboradores);
		assertEquals(poderosas2.getFullArtistName(), "Anitta (feat. Ludmilla, Snoop Dog)");
	}
}
