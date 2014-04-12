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

	// her�i mover-se uma posi��o
	// (quando se manda deslocar em dire��o a c�lula livre);
	@Test
	public void testMovingHeroe() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(1, 6));

		HashMap<Integer, Boolean> validMoves = j1.validHeroMoves();
		assertTrue(validMoves.containsKey(Movement.MOVE_RIGHT.getDirection()));
	}

	// her�i im�vel (quando se manda deloscar em
	// dire��o a uma parede);
	@Test
	public void testMovingtoWallHero() {
		Logic j1 = new Logic();
		HashMap<Integer, Boolean> movimentosPossiveis = j1.validHeroMoves();
		assertFalse(movimentosPossiveis.containsKey(Movement.MOVE_UP
				.getDirection()));
	}

	// apanhar a espada;
	@Test
	public void testCatchingtheSword() {
		Logic j1 = new Logic();

		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(3, 6));

		j1.setDragons(dragons);
		j1.setHero(new Hero(1, 7));

		assertFalse(j1.getHero().getArmed());
		j1.swapHero(Movement.MOVE_DOWN.getDirection());
		assertTrue(j1.getHero().getArmed());
	}

	// ser morto pelo drag�o (derrota);
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
		// j1.hero.setDead(true);
		assertEquals(State.HERO_DEAD, j1.checkGame());
	}

	// matar o drag�o;
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

	// alcan�ar a sa�da ap�s apanhar espada
	// e matar drag�o (vit�ria);
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
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());
		assertEquals(j1.checkGame(), State.HERO_WON);
	}

	// alcan�ar a sa�da sem ter apanhado a espada
	// ou morto o drag�o.
	@Test
	public void testReachingExitNotWinning() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(1, 3));
		j1.setHero(new Hero(8, 5));
		j1.getHero().setArmed(true);
		dragons.get(0).setDead(false);
		
		assertFalse(dragons.get(0).getDead());
		HashMap<Integer, Boolean> movimentosPossiveis = j1.validHeroMoves();
		assertTrue(movimentosPossiveis.containsKey(Movement.MOVE_RIGHT
				.getDirection()));
		j1.getHero().setArmed(true);
		dragons.get(0).setDead(true);
		assertTrue(movimentosPossiveis.containsKey(Movement.MOVE_RIGHT
				.getDirection()));

	}
}
