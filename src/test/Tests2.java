package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import logic.Dragon;
import logic.Hero;
import logic.Logic;

import org.junit.Test;

/* Criar uma segunda classe de teste para testar o jogo com os comportamentos mais complexos 
 * do drag�o (movimenta��o, dormir, m�ltiplos drag�es). Para efeito de teste, os movimentos do 
 * drag�o devem ser comandados pelo c�digo de teste (em vez de ocorerem de forma aleat�ria). O 
 * resto do programa (intera��o com o utilizador) deve continuar a funcionar da mesma forma. 
 * Sugest�o: "Virtualizar" o gerador aleat�ria usado pelo n�cleo do jogo, usando a classe 
 * java.util.Random no modo normal e uma classe especial controlada pelo c�digo */

public class Tests2 {

	@Test
	public void testMovingDragon() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(4, 5));
		j1.setDragons(dragons);
		j1.setHero(new Hero(1, 1));

		while (j1.getDragons().get(0).getPosY() == 5)
			j1.moveDragon(j1.getDragons().get(0));

		assertNotSame(5, j1.getDragons().get(0).getPosY());

		while (j1.getDragons().get(0).getPosX() == 4)
			j1.moveDragon(j1.getDragons().get(0));

		assertNotSame(4, j1.getDragons().get(0).getPosX());
	}

	@Test
	public void testSleepingDragon() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(4, 5));
		j1.setDragons(dragons);
		j1.setHero(new Hero(1, 1));

		assertFalse(j1.getDragons().get(0).getAsleep());
		j1.getDragons().get(0).setAsleep(true);
		assertTrue(j1.getDragons().get(0).getAsleep());
	}

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

		assertSame(j1.getDragons().size(), 3);
		j1.checkGame();
		assertSame(j1.getDragons().size(), 0);
		assertFalse(j1.getHero().getDead());
	}
}
