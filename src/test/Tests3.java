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
////2.5 [Para casa] Pode existir uma águia para ajudar o herói a apanhar a espada, comandada pelo 
////herói (isto é, pelo uilizador), com o seguinte comportamento: 
//// inicialmente a águia está poisada no braço do herói e acompanha-o; 
//
//// por ordem do herói, a águia pode levantar voo em direção à espada, pelo caminho mais 
////próximo possível de uma linha reta; 
//
//// quando está a voar, a águia pode estar sobre qualquer quadrícula; ao mostrar o estado do 
////labirinto, pode convir usar dois carateres para cada quadrícula; 
//
//// quando chega à quadrícula da espada, a águia desce para apanhar a espada (se ainda aí 
////estiver); se um dragão estive acordado nessa posição ou adjacente, mata a águia; 
//
//// assim que pega a espada, a águia levanta voo de novo em direção à posição de partida 
////(onde levantou voo do braço do herói); 
//
//// voltando à posição de partida, se não estiver aí o herói, a águia permanece no solo até o 
////herói a apanhar, correndo o risco de ser morta por um dragão. 
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
//		// Verifica se a aguia está com o heroi:
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
//		// verifica se a espada está onde é suposto(1,8) para a aguia ir lá:
//		assertEquals(j1.maze.getSword().getPosX(), 1);
//		assertEquals(j1.maze.getSword().getPosY(), 8);
//
//		j1.eagle.setPursuit(true);
//		j1.moveEagle(); // descola uma casa em y para baixo para a apanhar a
//						// espada
//		j1.checkGame();
//
//		// verica que a aguia esta a voar em direcao á espada que está numa
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
//		// verica que a aguia esta na posição da espada
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
//		assertTrue(j1.eagle.getReturning());// e agora está de regresso ao ponto
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
//		// verica que a aguia esta na posição da espada
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
//		// verifica a nova posição do heroi
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
