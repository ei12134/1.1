package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import logic.Dragon;
import logic.State;
import logic.Hero;
import logic.Logic;
import logic.Movement;

import org.junit.Test;

/* Pode existir uma �guia para ajudar o her�i a apanhar a espada, comandada pelo 
 her�i (isto �, pelo uilizador), com o seguinte comportamento: 
 inicialmente a �guia est� poisada no bra�o do her�i e acompanha-o; 

 por ordem do her�i, a �guia pode levantar voo em dire��o � espada, pelo caminho mais 
 pr�ximo poss�vel de uma linha reta; 

 quando est� a voar, a �guia pode estar sobre qualquer quadr�cula; ao mostrar o estado do 
 labirinto, pode convir usar dois carateres para cada quadr�cula; 

 quando chega � quadr�cula da espada, a �guia desce para apanhar a espada (se ainda a� 
 estiver); se um drag�o estive acordado nessa posi��o ou adjacente, mata a �guia; 

 assim que pega a espada, a �guia levanta voo de novo em dire��o � posi��o de partida 
 (onde levantou voo do bra�o do her�i); 

 voltando � posi��o de partida, se n�o estiver a� o her�i, a �guia permanece no solo at� o 
 her�i a apanhar, correndo o risco de ser morta por um drag�o. */

public class Tests3 {

	@Test
	public void testmovingEaglewithhero() {
		Logic j1 = new Logic();
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(4, 5));
		j1.setDragons(dragons);

		assertFalse(j1.getHero().getDead()); // o heroi tem que estar vivo
		int posX = j1.getEagle().getPosX();
		int posY = j1.getEagle().getPosY();
		// Verifica se a aguia est� com o heroi:
		assertEquals(j1.getEagle().getState(), State.EAGLE_FOLLOWING);
		assertEquals(posX, 1);
		assertEquals(posY, 1);
		// Verifica se a aguia acompanha o heroi
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());
		assertEquals(j1.getHero().getPosX(), 2);
		/*
		 * verifica se o heroi se moveu uma casa para a direita a aguia tem de
		 * estar com o heroi
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

		// verifica se a espada est� onde � suposto(1,8) para a aguia ir l�:
		assertEquals(j1.getSword().getPosX(), 1);
		assertEquals(j1.getSword().getPosY(), 8);

		j1.getEagle().setState(State.EAGLE_PURSUING);

		// descola uma casa em y para baixo para a apanhar a espada
		j1.moveEagle();

		/*
		 * verica que a aguia esta a voar em direcao � espada que est� numa
		 * linha reta para baixo
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
							 * descola 7 casas em y para baixo para a apanhar a
							 * espada
							 */
		}
		// verica que a aguia esta na posi��o da espada
		assertEquals(j1.getEagle().getPosX(), 1);
		assertEquals(j1.getEagle().getPosY(), 8);

		assertEquals(j1.getMazePiece(1, 8).getSymbol(), "E G"); // a aguia
		/*
		 * conseguiu apanhar a espada e mudou a sua aparencia de "E" para "E G"
		 */
		assertSame(j1.getEagle().getState(), State.EAGLE_RETURNING);/*
																	 * e agora
																	 * est� de
																	 * regresso
																	 * ao ponto
																	 * de onde
																	 * foi
																	 * enviada
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
							 * descola 7 casas em y para baixo para a apanhar a
							 * espada
							 */
		}

		// verica que a aguia esta na posi��o da espada
		assertEquals(j1.getEagle().getPosX(), 1);
		assertEquals(j1.getEagle().getPosY(), 8);
		j1.moveEagle(); // aterra a aguia
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

		// movo o heroi algumas casas para a direita para efeito de teste
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());

		// verifica a nova posi��o do heroi
		assertEquals(j1.getHero().getPosX(), 5);

		assertEquals(j1.getEagle().getHeroX(), 3);
		assertEquals(j1.getEagle().getHeroY(), 1);

		while (!j1.getEagle().getState().equals(State.EAGLE_GROUND)) {
			j1.moveEagle();
		}

		// verifica se a aguia aterra no sitio de onde o heroi a enviou e nao
		// onde ele se encontra no momento
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

		// movo o heroi algumas casas para a direita para efeito de teste
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());
		j1.swapHero(Movement.MOVE_RIGHT.getDirection());

		// verifica a nova posi��o do heroi
		assertEquals(j1.getHero().getPosX(), 6);

		assertEquals(j1.getEagle().getHeroX(), 3);
		assertEquals(j1.getEagle().getHeroY(), 1);

		while (!j1.getEagle().getState().equals(State.EAGLE_GROUND)) {
			j1.moveEagle();
		}

		/*
		 * verifica se a aguia aterra no sitio de onde o heroi a enviou e nao
		 * onde ele se encontra no momento
		 */
		assertEquals(j1.getEagle().getPosX(), 3);
		assertEquals(j1.getEagle().getPosY(), 1);

		while (j1.getDragons().get(0).getPosX() != 3
				|| j1.getDragons().get(0).getPosY() != 1)
			j1.moveDragon(j1.getDragons().get(0));

		assertTrue(j1.getEagle().getDead());
		assertEquals(j1.getMazePiece(3, 1).getSymbol(), " F ");
	}
}
