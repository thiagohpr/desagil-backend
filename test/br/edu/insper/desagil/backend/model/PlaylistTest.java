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
		assertEquals(true, false);
	}

	@Test
	void testRoundUpToHalf() {
		assertEquals(true, false);
	}

	@Test
	void testRoundDownToHalf() {
		assertEquals(true, false);
	}

	@Test
	void testRoundUpToOne() {
		assertEquals(true, false);
	}
}
