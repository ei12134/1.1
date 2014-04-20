package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import logic.Dragon;
import logic.State;
import logic.Hero;
import logic.Logic;
import logic.Movement;

import org.junit.Test;

public class Tests3 {

	@Test
	public void testmovingEaglewithhero() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(4, 5));
		j1.setDragons(dragons);

		assertFalse(j1.getHero().getDead()); // hero must be alive
		int posX = j1.getEagle().getPosX();
		int posY = j1.getEagle().getPosY();
		// checks whether the eagle is with the hero or not:
		assertEquals(j1.getEagle().getState(), State.EAGLE_FOLLOWING);
		assertEquals(posX, 1);
		assertEquals(posY, 1);
		// checks if the eagle follows the hero around
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());
		assertEquals(j1.getHero().getPosX(), 2);
		/*
		 * checks if the eagle is with the hero after u move him to the right
		 */
		j1.moveEagle();

		assertEquals(j1.getEagle().getState(), State.EAGLE_FOLLOWING);
		assertEquals(j1.getEagle().getPosX(), 2);
		assertEquals(j1.getEagle().getPosY(), 1);
	}

	@Test
	public void testmovingEaglePursuingSword() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(4, 5));
		j1.setDragons(dragons);

		// checks if the sword is at (1,8) before sending the eagle there
		assertEquals(j1.getSword().getPosX(), 1);
		assertEquals(j1.getSword().getPosY(), 8);

		j1.getEagle().setState(State.EAGLE_PURSUING);

		// moves eagle once toward the sword( in this case it means moving 1
		// cell down in yy axis)
		j1.moveEagle();

		/*
		 * checks if eagle moved , where it should
		 */
		assertEquals(j1.getEagle().getPosX(), 1);
		assertEquals(j1.getEagle().getPosY(), 2);

	}

	@Test
	public void testEagleCatchingSwordandReturningwithit() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(1, 7));
		j1.setDragons(dragons);
		j1.getEagle().setState(State.EAGLE_PURSUING);

		assertSame(j1.getEagle().getState(), State.EAGLE_PURSUING);

		while (j1.getEagle().getState().equals(State.EAGLE_PURSUING)
				&& !j1.getEagle().getDead()) {
			j1.moveEagle(); /*
							 * sends the eagle 7 cells down in y´s axis
							 */
		}
		// checks if eagle is at sword position
		assertEquals(j1.getEagle().getPosX(), 1);
		assertEquals(j1.getEagle().getPosY(), 8);

		/*
		 * checks if eagle catched sword and changed his appearance from "E" to
		 * "E G"
		 */
		assertEquals(j1.getMazePiece(1, 8).getSymbol(), "E G");

		assertSame(j1.getEagle().getState(), State.EAGLE_RETURNING);/*
																	 * and now
																	 * is
																	 * returning
																	 * to the
																	 * place
																	 * where it
																	 * was sent
																	 * by the
																	 * hero
																	 */
	}

	@Test
	public void testDragonKillingEagle() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(1, 7));
		j1.setDragons(dragons);
		j1.getEagle().setState(State.EAGLE_PURSUING);

		assertNotSame(j1.getEagle().getState(), State.EAGLE_RETURNING);

		while (j1.getDragons().get(0).getPosY() != 8
				|| j1.getDragons().get(0).getPosX() != 1) {
			j1.moveDragon(j1.getDragons().get(0));
		}
		j1.getDragons().get(0).setGuarding(true);

		assertEquals(j1.getDragons().get(0).getPosY(), 8);
		assertEquals(j1.getMazePieceSymbol(1, 8), " F ");

		while (j1.getEagle().getState().equals(State.EAGLE_PURSUING)
				&& !j1.getEagle().getDead()) {
			j1.moveEagle(); /*
							 * sends the eagle 7 cells down in y´s axis
							 */
		}

		// checks if eagle is at sword position
		assertEquals(j1.getEagle().getPosX(), 1);
		assertEquals(j1.getEagle().getPosY(), 8);
		j1.moveEagle(); // eagle lands
		assertEquals(" F ", j1.getMazePieceSymbol(1, 8));
	}

	@Test
	public void testmovingEagleLanding() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(1, 7));
		j1.setDragons(dragons);
		j1.setHero(new Hero(3, 1));

		j1.getEagle().setHeroX(j1.getHero().getPosX());
		j1.getEagle().setHeroY(j1.getHero().getPosY());
		j1.getEagle().setState(State.EAGLE_PURSUING);

		while (j1.getEagle().getState().equals(State.EAGLE_PURSUING)
				&& !j1.getEagle().getDead()) {
			j1.moveEagle();
		}

		// moving hero some cells to the right for this test
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());

		// checks the new hero position
		assertEquals(j1.getHero().getPosX(), 5);

		assertEquals(j1.getEagle().getHeroX(), 3);
		assertEquals(j1.getEagle().getHeroY(), 1);

		while (!j1.getEagle().getState().equals(State.EAGLE_GROUND)) {
			j1.moveEagle();
		}

		// checks if the eagle landed where it was sent by the hero and not
		// where the hero is now standying after moving
		assertEquals(j1.getEagle().getPosX(), 3);
		assertEquals(j1.getEagle().getPosY(), 1);
	}

	@Test
	public void testEagleGettingKillWhileGround() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(2, 1));
		j1.setDragons(dragons);
		j1.setHero(new Hero(3, 1));

		j1.getEagle().setHeroX(j1.getHero().getPosX());
		j1.getEagle().setHeroY(j1.getHero().getPosY());
		j1.getEagle().setState(State.EAGLE_PURSUING);

		while (j1.getEagle().getState().equals(State.EAGLE_PURSUING)
				&& !j1.getEagle().getDead()) {
			j1.moveEagle();
		}

		// moving hero some cells to the right for this test
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());

		// checks the new hero position
		assertEquals(j1.getHero().getPosX(), 6);

		assertEquals(j1.getEagle().getHeroX(), 3);
		assertEquals(j1.getEagle().getHeroY(), 1);

		while (!j1.getEagle().getState().equals(State.EAGLE_GROUND)) {
			j1.moveEagle();
		}

		// checks if the eagle landed where it was sent by the hero and not
				// where the hero is now standying after moving
		assertEquals(j1.getEagle().getPosX(), 3);
		assertEquals(j1.getEagle().getPosY(), 1);

		while (j1.getDragons().get(0).getPosX() != 3
				|| j1.getDragons().get(0).getPosY() != 1)
			j1.moveDragon(j1.getDragons().get(0));

		
		//checks if the eagle is killed by the dragon while in the ground
		assertTrue(j1.getEagle().getDead());
		assertEquals(j1.getMazePiece(3, 1).getSymbol(), " F ");
	}
}
