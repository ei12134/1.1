package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import logic.Dragon;
import logic.Hero;
import logic.Logic;

import org.junit.Test;

public class Tests2 {
	/*
	 * Checks dragon movements .
	 */
	@Test
	public void testMovingDragon() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(4, 5));
		j1.setDragons(dragons);

		while (j1.getDragons().get(0).getPosY() == 5)
			j1.moveDragon(j1.getDragons().get(0));

		assertNotSame(5, j1.getDragons().get(0).getPosY());

		while (j1.getDragons().get(0).getPosX() == 4)
			j1.moveDragon(j1.getDragons().get(0));

		assertNotSame(4, j1.getDragons().get(0).getPosX());
	}

	/*
	 * Checks if dragon can fall asleep .
	 */
	@Test
	public void testSleepingDragon() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(4, 5));
		j1.setDragons(dragons);

		assertFalse(j1.getDragons().get(0).getAsleep());
		j1.getDragons().get(0).setAsleep(true);
		assertTrue(j1.getDragons().get(0).getAsleep());
	}

	/*
	 * checks if the hero(armed) can slay all dragons that surround him
	 */
	@Test
	public void testWithTonsofDragons() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(3, 5));
		dragons.add(new Dragon(4, 4));
		dragons.add(new Dragon(5, 5));
		j1.setDragons(dragons);
		j1.setHero(new Hero(4, 5));
		j1.getHero().setArmed(true);

		assertSame(j1.getDragons().size(), 3);// we had 3 dragons alive
		j1.checkGame();
		assertSame(j1.getDragons().size(), 0);// and now they are all dead
		assertFalse(j1.getHero().getDead());
	}
}
