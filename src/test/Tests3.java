package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import logic.Dragon;
import logic.State;
import logic.Hero;
import logic.Logic;
import logic.Movement;

import org.junit.Test;

/* Pode existir uma águia para ajudar o herói a apanhar a espada, comandada pelo 
 herói (isto é, pelo uilizador), com o seguinte comportamento: 
 inicialmente a águia está poisada no braço do herói e acompanha-o; 

 por ordem do herói, a águia pode levantar voo em direção à espada, pelo caminho mais 
 próximo possível de uma linha reta; 

 quando está a voar, a águia pode estar sobre qualquer quadrícula; ao mostrar o estado do 
 labirinto, pode convir usar dois carateres para cada quadrícula; 

 quando chega à quadrícula da espada, a águia desce para apanhar a espada (se ainda aí 
 estiver); se um dragão estive acordado nessa posição ou adjacente, mata a águia; 

 assim que pega a espada, a águia levanta voo de novo em direção à posição de partida 
 (onde levantou voo do braço do herói); 

 voltando à posição de partida, se não estiver aí o herói, a águia permanece no solo até o 
 herói a apanhar, correndo o risco de ser morta por um dragão. */

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
		// Verifica se a aguia está com o heroi:
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

		// verifica se a espada está onde é suposto(1,8) para a aguia ir lá:
		assertEquals(j1.getSword().getPosX(), 1);
		assertEquals(j1.getSword().getPosY(), 8);

		j1.getEagle().setState(State.EAGLE_PURSUING);

		// descola uma casa em y para baixo para a apanhar a espada
		j1.moveEagle();

		/*
		 * verica que a aguia esta a voar em direcao á espada que está numa
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
		// verica que a aguia esta na posição da espada
		assertEquals(j1.getEagle().getPosX(), 1);
		assertEquals(j1.getEagle().getPosY(), 8);

		assertEquals(j1.getMazePiece(1, 8).getSymbol(), "E G"); // a aguia
		/*
		 * conseguiu apanhar a espada e mudou a sua aparencia de "E" para "E G"
		 */
		assertSame(j1.getEagle().getState(), State.EAGLE_RETURNING);/*
																	 * e agora
																	 * está de
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

		// verica que a aguia esta na posição da espada
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

		// verifica a nova posição do heroi
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

		// verifica a nova posição do heroi
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
