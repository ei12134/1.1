package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import maze.Dragon;
import maze.State;
import maze.Hero;
import maze.Logic;
import maze.Maze;
import maze.Movement;

import org.junit.Test;

public class Tests {

	// her�i mover-se uma posi��o
	// (quando se manda deslocar em dire��o a c�lula livre);
	@Test
	public void testMovingHeroe() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(1, 6));

		HashMap<Integer, Boolean> validMoves = j1.getValidHeroMoves();
		assertTrue(validMoves.containsKey(Movement.MOVE_RIGHT.getDirection()));
	}

	// her�i im�vel (quando se manda deloscar em
	// dire��o a uma parede);
	@Test
	public void testMovingtoWallHero() {
		Logic j1 = new Logic();

		HashMap<Integer, Boolean> movimentosPossiveis = j1.getValidHeroMoves();
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

//	// ser morto pelo drag�o (derrota);
//	@Test
//	public void testGettingKilledByDragon() {
//		Logic j1 = new Logic(10);
//		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
//		dragons.add(new Dragon(1, 3));
//		j1.setDragons(dragons);
//		j1.hero = new Hero(1, 1);
//		j1.maze = new Maze(j1.hero, j1.dragons);
//
//		assertFalse(j1.hero.getArmed());
//		assertFalse(j1.hero.getDead());
//		assertFalse(dragons.get(0).getDead());
//		assertFalse(dragons.get(0).getAsleep());
//		j1.maze.swapPieces(Movement.MOVE_DOWN.getDirection(), j1.hero);
//		// j1.hero.setDead(true);
//		assertEquals(State.HERO_DIED.toString(), j1.checkGame());
//	}
//
//	// matar o drag�o;
//	@Test
//	public void testSlayingTheDragon() {
//		Logic j1 = new Logic(10);
//		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
//		dragons.add(new Dragon(1, 3));
//		j1.setDragons(dragons);
//		int dragonsSize = dragons.size();
//		j1.hero = new Hero(1, 1);
//		j1.maze = new Maze(j1.hero, j1.dragons);
//
//		j1.hero.setArmed(true);
//		j1.maze.swapPieces(Movement.MOVE_DOWN.getDirection(), j1.hero);
//		j1.checkGame();
//		assertFalse(j1.hero.getDead());
//		// assertTrue(j1.getDragons().get(0).getDead(),dragonsSize-1);
//	}
//
//	// alcan�ar a sa�da ap�s apanhar espada
//	// e matar drag�o (vit�ria);
//	@Test
//	public void testReachingExitWinning() {
//		Logic j1 = new Logic(10);
//		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
//		dragons.add(new Dragon(1, 3));
//		j1.hero = new Hero(8, 5);
//		j1.maze = new Maze(j1.hero, j1.dragons);
//
//		j1.hero.setArmed(true);
//		dragons.get(0).setDead(true);
//		assertTrue(dragons.get(0).getDead());
//		j1.maze.swapPieces(Movement.MOVE_RIGHT.getDirection(), j1.hero);
//		assertEquals(j1.checkGame(), State.HERO_WON.toString());
//
//	}
//
//	// alcan�ar a sa�da sem ter apanhado a espada
//	// ou morto o drag�o.
//	@Test
//	public void testReachingExitNotWinning() {
//		Logic j1 = new Logic(10);
//		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
//		dragons.add(new Dragon(1, 3));
//		j1.hero = new Hero(8, 5);
//		j1.maze = new Maze(j1.hero, j1.dragons);
//		// Hero h1 = new Hero(8, 5);
//		// Dragon d1 = new Dragon(1,3);
//		// Maze t1=new Maze(h1, d1, 1);
//		//
//		j1.hero.setArmed(true);
//		dragons.get(0).setDead(false);
//		assertFalse(dragons.get(0).getDead());
//
//		HashMap<Integer, Boolean> movimentosPossiveis = j1.maze
//				.getValidMoves(j1.hero);
//		assertTrue(movimentosPossiveis.containsKey(Movement.MOVE_RIGHT
//				.getDirection()));
//		j1.hero.setArmed(true);
//		dragons.get(0).setDead(true);
//		assertTrue(movimentosPossiveis.containsKey(Movement.MOVE_RIGHT
//				.getDirection()));
//
//	}
//
}
