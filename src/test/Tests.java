package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import logic.Dragon;
import logic.Hero;
import logic.Logic;
import logic.Movement;
import logic.State;

import org.junit.Test;

public class Tests {

	/*
	 * Checks if hero can move toward a free cell
	 */
	@Test
	public void testMovingHeroe() {
		Logic j1 = new Logic();
		HashMap<Integer, Boolean> validMoves = j1.validHeroMoves();

		assertTrue(validMoves.containsKey(Movement.MOVE_RIGHT.getDirection()));
	}

	/*
	 * Checks if hero stands in same spot (when to order him to move toward a
	 * Wall);
	 */
	@Test
	public void testMovingtoWallHero() {
		Logic j1 = new Logic();
		HashMap<Integer, Boolean> movimentosPossiveis = j1.validHeroMoves();

		assertFalse(movimentosPossiveis.containsKey(Movement.MOVE_UP
				.getDirection()));
	}

	// Checks if hero catchs the sword;
	@Test
	public void testCatchingtheSword() {
		Logic j1 = new Logic();
		j1.setHero(new Hero(1, 7));

		assertFalse(j1.getHero().getArmed());
		j1.swapHero(Movement.MOVE_DOWN.getDirection());
		assertTrue(j1.getHero().getArmed());
	}

	// checks if dragon can kill an unarmed hero(game over);
	@Test
	public void testGettingKilledByDragon() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(1, 3));
		j1.setDragons(dragons);
		j1.setHero(new Hero(1, 1));

		assertFalse(j1.getHero().getArmed());
		assertFalse(j1.getHero().getDead());
		assertFalse(dragons.get(0).getDead());
		assertFalse(dragons.get(0).getAsleep());
		j1.swapHero(Movement.MOVE_DOWN.getDirection());
		assertEquals(State.HERO_DEAD, j1.checkGame());
	}

	// check if hero(armed) can slay the dragon
	@Test
	public void testSlayingTheDragon() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(1, 3));
		j1.setDragons(dragons);
		int dragonsSize = dragons.size();
		j1.setHero(new Hero(1, 1));
		j1.getHero().setArmed(true);
		j1.swapHero(Movement.MOVE_DOWN.getDirection());
		j1.checkGame();

		assertFalse(j1.getHero().getDead());
		assertEquals(j1.getDragons().size(), dragonsSize - 1);
	}

	/*
	 * reaching the exit after killing the dragon(Hero Won);
	 */
	@Test
	public void testReachingExitWinning() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(1, 3));
		dragons.get(0).setDead(true);
		j1.setDragons(dragons);
		j1.setHero(new Hero(8, 5));
		j1.getHero().setArmed(true);

		assertTrue(dragons.get(0).getDead());
		dragons.remove(0);
		j1.setDragons(dragons);
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());
		assertEquals(j1.checkGame(), State.HERO_WON);
	}

	/*
	 * reaching the exit without killing the dragon .
	 */
	@Test
	public void testReachingExitNotWinning() {
		Logic j1 = new Logic();
		j1.setHero(new Hero(8, 5));
		j1.getHero().setArmed(true);

		HashMap<Integer, Boolean> movimentosPossiveis = j1.validHeroMoves();
		assertFalse(movimentosPossiveis.containsKey(Movement.MOVE_RIGHT
				.getDirection()));
		j1.getHero().setArmed(true);
		// tries to move to the exit (at hero´s right),but fails :
		assertFalse(movimentosPossiveis.containsKey(Movement.MOVE_RIGHT
				.getDirection()));
	}
}
