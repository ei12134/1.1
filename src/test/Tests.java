package test;

import static org.junit.Assert.*;

import java.util.HashMap;

import maze.Dragon;
import maze.GameState;
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
		j1.dragon = new Dragon(1, 6);
		j1.hero = new Hero(1, 1);
		j1.maze = new Maze(j1.hero, j1.dragon, 1);

		HashMap<Integer, Boolean> validMoves = j1.maze.getValidMoves(j1.hero);
		assertTrue(validMoves.containsKey(Movement.MOVE_RIGHT.getDirecaoInt()));
	}

	// her�i im�vel (quando se manda deloscar em
	// dire��o a uma parede);
	@Test
	public void testMovingtoWallHero() {
		Logic j1 = new Logic();
		j1.dragon = new Dragon(1, 6);
		j1.hero = new Hero(1, 1);
		j1.maze = new Maze(j1.hero, j1.dragon, 1);

		HashMap<Integer, Boolean> movimentosPossiveis = j1.maze
				.getValidMoves(j1.hero);
		assertFalse(movimentosPossiveis.containsKey(Movement.MOVE_UP
				.getDirecaoInt()));
	}

	// apanhar a espada;
	@Test
	public void testCatchingtheSword() {
		Logic j1 = new Logic();
		j1.dragon = new Dragon(3, 6);
		j1.hero = new Hero(1, 7);
		j1.maze = new Maze(j1.hero, j1.dragon, 1);

		assertFalse(j1.hero.getArmed());
		j1.maze.swapPieces(Movement.MOVE_DOWN.getDirecaoInt(), j1.hero);
		assertTrue(j1.hero.getArmed());
	}

	// ser morto pelo drag�o (derrota);
	@Test
	public void testGettingKilledByDragon() {
		Logic j1 = new Logic();
		j1.dragon = new Dragon(1, 3);
		j1.hero = new Hero(1, 1);
		j1.maze = new Maze(j1.hero, j1.dragon, 1);

		assertFalse(j1.hero.getArmed());
		assertFalse(j1.hero.getDead());
		assertFalse(j1.dragon.getDead());
		assertFalse(j1.dragon.getAsleep());
		j1.maze.swapPieces(Movement.MOVE_DOWN.getDirecaoInt(), j1.hero);
		assertEquals(j1.checkGame(), GameState.HERO_DIED.toString());
	}

	// matar o drag�o;
	@Test
	public void testSlayingTheDragon() {
		Logic j1 = new Logic();
		j1.dragon = new Dragon(1, 3);
		j1.hero = new Hero(1, 1);
		j1.maze = new Maze(j1.hero, j1.dragon, 1);

		j1.hero.setArmed(true);
		j1.maze.swapPieces(Movement.MOVE_DOWN.getDirecaoInt(), j1.hero);
		j1.checkGame();
		assertFalse(j1.hero.getDead());
		assertTrue(j1.dragon.getDead());
	}

	// alcan�ar a sa�da ap�s apanhar espada
	// e matar drag�o (vit�ria);
	@Test
	public void testReachingExitWinning() {
		Logic j1 = new Logic();
		j1.dragon = new Dragon(1, 3);
		j1.hero = new Hero(8, 5);
		j1.maze = new Maze(j1.hero, j1.dragon, 1);
		// Hero h1 = new Hero(8, 5);
		// Dragon d1 = new Dragon(1,3);
		// Maze t1=new Maze(h1, d1, 1);
		//
		j1.hero.setArmed(true);
		j1.dragon.setDead(true);
		assertTrue(j1.dragon.getDead());
		j1.maze.swapPieces(Movement.MOVE_RIGHT.getDirecaoInt(), j1.hero);
		assertEquals(j1.checkGame(), GameState.HERO_WON.toString());

	}

	// alcan�ar a sa�da sem ter apanhado a espada
	// ou morto o drag�o.
	@Test
	public void testReachingExitNotWinning() {
		Logic j1 = new Logic();
		j1.dragon = new Dragon(1, 3);
		j1.hero = new Hero(8, 5);
		j1.maze = new Maze(j1.hero, j1.dragon, 1);
		// Hero h1 = new Hero(8, 5);
		// Dragon d1 = new Dragon(1,3);
		// Maze t1=new Maze(h1, d1, 1);
		//
		j1.hero.setArmed(true);
		j1.dragon.setDead(false);
		assertFalse(j1.dragon.getDead());

		HashMap<Integer, Boolean> movimentosPossiveis = j1.maze
				.getValidMoves(j1.hero);
		assertTrue(movimentosPossiveis.containsKey(Movement.MOVE_RIGHT
				.getDirecaoInt()));
		j1.hero.setArmed(true);
		j1.dragon.setDead(true);
		assertTrue(movimentosPossiveis.containsKey(Movement.MOVE_RIGHT
				.getDirecaoInt()));

	}

}
