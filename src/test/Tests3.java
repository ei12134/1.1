//package test;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//
//import maze.Dragon;
//import maze.State;
//import maze.Hero;
//import maze.Logic;
//import maze.Maze;
//import maze.Movement;
//
//import org.junit.Test;
//
////2.5 [Para casa] Pode existir uma �guia para ajudar o her�i a apanhar a espada, comandada pelo 
////her�i (isto �, pelo uilizador), com o seguinte comportamento: 
//// inicialmente a �guia est� poisada no bra�o do her�i e acompanha-o; 
//
//// por ordem do her�i, a �guia pode levantar voo em dire��o � espada, pelo caminho mais 
////pr�ximo poss�vel de uma linha reta; 
//
//// quando est� a voar, a �guia pode estar sobre qualquer quadr�cula; ao mostrar o estado do 
////labirinto, pode convir usar dois carateres para cada quadr�cula; 
//
//// quando chega � quadr�cula da espada, a �guia desce para apanhar a espada (se ainda a� 
////estiver); se um drag�o estive acordado nessa posi��o ou adjacente, mata a �guia; 
//
//// assim que pega a espada, a �guia levanta voo de novo em dire��o � posi��o de partida 
////(onde levantou voo do bra�o do her�i); 
//
//// voltando � posi��o de partida, se n�o estiver a� o her�i, a �guia permanece no solo at� o 
////her�i a apanhar, correndo o risco de ser morta por um drag�o. 
//
//public class Tests3 {
//
//	@Test
//	public void testmovingEaglewithhero() {
//		Logic j1 = new Logic(10);
//		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
//		dragons.add(new Dragon(4, 5));
//		j1.hero = new Hero(1, 1);
//		j1.maze = new Maze(j1.hero, j1.dragons);
//		assertFalse(j1.hero.getDead()); // o heroi tem que estar vivo
//		int posX = j1.eagle.getPosX();
//		int posY = j1.eagle.getPosY();
//		// Verifica se a aguia est� com o heroi:
//		j1.eagle.setPursuit(false);
//		assertEquals(posX, 1);
//		assertEquals(posY, 1);
//		// Verifica se a aguia acompanha o heroi
//		j1.maze.swapPieces(Movement.MOVE_RIGHT.getDirection(), j1.hero, j1.eagle);
//		assertEquals(j1.hero.getPosX(), 2); // verifica se o heroi se moveu uma
//											// casa para a direita
//		// a aguia tem de estar com o heroi agora
//		j1.moveEagle();
//		j1.checkGame();
//		assertEquals(j1.eagle.getPosX(), 2);
//		assertEquals(j1.eagle.getPosY(), 1);
//
//	}
//
//	@Test
//	public void testmovingEaglePursuingSword() {
//		Logic j1 = new Logic(10);
//		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
//		dragons.add(new Dragon(4, 5));
//		j1.hero = new Hero(1, 1);
//		j1.maze = new Maze(j1.hero, j1.dragons);
//
//		int posX = j1.eagle.getPosX();
//		int posY = j1.eagle.getPosY();
//
//		// verifica se a espada est� onde � suposto(1,8) para a aguia ir l�:
//		assertEquals(j1.maze.getSword().getPosX(), 1);
//		assertEquals(j1.maze.getSword().getPosY(), 8);
//
//		j1.eagle.setPursuit(true);
//		j1.moveEagle(); // descola uma casa em y para baixo para a apanhar a
//						// espada
//		j1.checkGame();
//
//		// verica que a aguia esta a voar em direcao � espada que est� numa
//		// linha reta para baixo
//		assertEquals(j1.eagle.getPosX(), 1);
//		assertEquals(j1.eagle.getPosY(), 2);
//
//	}
//
//	@Test
//	public void testEagleCatchingSwordandReturningwithit() {
//		Logic j1 = new Logic(10);
//		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
//		dragons.add(new Dragon(1, 7));
//		j1.hero = new Hero(1, 1);
//		j1.maze = new Maze(j1.hero, j1.dragons);
//		j1.eagle.setPursuit(true);
//
//		assertFalse(j1.eagle.getReturning());
//
//		while (j1.eagle.getPursuit() && !j1.eagle.getDead()) {
//			j1.moveEagle(); // descola 7 casas em y para baixo para a apanhar a
//							// espada
//
//		}
//		j1.checkGame();
//
//		// verica que a aguia esta na posi��o da espada
//		assertEquals(j1.eagle.getPosX(), 1);
//		assertEquals(j1.eagle.getPosY(), 8);
//
//		assertEquals(j1.maze.getMazePieceSymbol(1, 8), "E G"); // a aguia
//																// conseguiu
//																// apanhar a
//																// espada e
//																// mudou a sua
//																// aparencia de
//																// "E" para
//																// "E G"
//		assertTrue(j1.eagle.getReturning());// e agora est� de regresso ao ponto
//											// de onde foi enviada
//	}
//
//	@Test
//	public void testDragonkillingEagle() {
//		Logic j1 = new Logic(10);
//		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
//		dragons.add(new Dragon(1, 7));
//		j1.setDragons(dragons);
//		j1.hero = new Hero(1, 1);
//		j1.maze = new Maze(j1.hero, j1.dragons);
//		j1.eagle.setPursuit(true);
//
//		assertFalse(j1.eagle.getReturning());
//
//		while (j1.dragons.get(0).getPosY() != 8
//				|| j1.dragons.get(0).getPosX() != 1) {
//			j1.moveDragon(j1.dragons.get(0));
//		}
//		j1.dragons.get(0).setAtSword(true);
//		j1.checkGame();
//		assertEquals(j1.dragons.get(0).getPosY(), 8);
//		assertEquals(j1.maze.getMazePieceSymbol(1, 8), " F ");
//
//		while (j1.eagle.getPursuit() && !j1.eagle.getDead()) {
//			j1.moveEagle(); // descola 7 casas em y para baixo para a apanhar a
//							// espada
//
//		}
//		j1.checkGame();
//
//		// verica que a aguia esta na posi��o da espada
//		assertEquals(j1.eagle.getPosX(), 1);
//		assertEquals(j1.eagle.getPosY(), 8);
//		j1.moveEagle(); // aterra a aguia
//		assertEquals(j1.dragons.get(0).getPosY(), 8);
//		assertEquals(j1.maze.getMazePieceSymbol(1, 8), " F ");
//
//	}
//
//	@Test
//	public void testmovingEagleLanding() {
//		Logic j1 = new Logic(10);
//		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
//		dragons.add(new Dragon(1, 7));
//		j1.hero = new Hero(3, 1);
//		j1.maze = new Maze(j1.hero, j1.dragons);
//		j1.eagle.setPursuit(true);
//
//		assertFalse(j1.eagle.getReturning());
//	
//		while (j1.eagle.getPursuit() && !j1.eagle.getDead()) {
//			j1.moveEagle(); // descola 7 casas em y para baixo para a apanhar a
//							// espada
//			
//		}
//		j1.checkGame();
//		j1.eagle.setPursuit(false);
//		j1.eagle.setReturning(true);
//		// movo o heroi algumas casas para a direita para efeito de teste
//		j1.maze.swapPieces(Movement.MOVE_RIGHT.getDirection(), j1.hero, j1.eagle);
//		j1.maze.swapPieces(Movement.MOVE_RIGHT.getDirection(), j1.hero, j1.eagle);
//
//		// verifica a nova posi��o do heroi
//		assertEquals(j1.hero.getPosX(), 5);
//
//
//		while ( !j1.eagle.getGround()) {
//			j1.moveEagle(); // descola 7 casas em y para cima para a apanhar a
//							// espada
//			
//		}
//		j1.checkGame();
//
//		// verifica se a aguia aterra no sitio de onde o heroi a enviou e nao
//		// onde ele se encontra no momento
//		assertEquals(j1.eagle.getPosX(), 3);
//		assertEquals(j1.eagle.getPosY(), 1);
//
//	}
//	@Test
//	public void testEaglegettingkillbydragonwhilewaitingforHERO(){
//		
//	}
//}
