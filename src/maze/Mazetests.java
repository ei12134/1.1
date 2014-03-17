package maze;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

public class Mazetests {



	// her�i mover-se uma posi��o
	// (quando se manda deslocar em dire��o a c�lula livre);
	@Test
	public void testMovingHeroe() {
		Heroi h1 = new Heroi(1, 1);
		Dragao d1 = new Dragao(1,6);
		Tabuleiro t1=new Tabuleiro(h1, d1, 1);
		
		HashMap<Integer, Boolean> movimentosPossiveis = t1.getMovimentosPossiveis(h1);
		assertTrue(movimentosPossiveis.containsKey(Movimentos.MOVIMENTO_DIREITA.getDirecaoInt()));
	}

	// her�i im�vel (quando se manda deloscar em
	// dire��o a uma parede);
	@Test
	public void testMovingtoWallHeroe() {
		Heroi h1 = new Heroi(1, 1);
		Dragao d1 = new Dragao(1,6);
		Tabuleiro t1=new Tabuleiro(h1, d1, 1);
		
		HashMap<Integer, Boolean> movimentosPossiveis = t1.getMovimentosPossiveis(h1);
		assertFalse(movimentosPossiveis.containsKey(Movimentos.MOVIMENTO_CIMA.getDirecaoInt()));
		
		
	}

	// apanhar a espada;
	@Test
	public void testCatchingtheSword() {
		Heroi h1 = new Heroi(1, 1);
		Dragao d1 = new Dragao(3,6);
		Tabuleiro t1=new Tabuleiro(h1, d1, 1);
		Peca espada = new Peca(1, 2, TipoPeca.ESPADA.comoChar());
		
		assertFalse(h1.getHeroiTemEspada());
		t1.trocarPecas(Movimentos.MOVIMENTO_BAIXO.getDirecaoInt(), h1);
		//h1.atualizaPosicao(1, 7);
		if(t1.pecaHeroiArmadoProxima(1, h1)){h1.setHeroiTemEspada(true);}; // 1 ==para baixo
		assertTrue(h1.getHeroiTemEspada());
		
	}

	// ser morto pelo drag�o (derrota);
	@Test
	public void testGettingkilledbyDragon() {
		Heroi h1 = new Heroi(1, 1);
		Dragao d1 = new Dragao(1,6);
		Tabuleiro t1=new Tabuleiro(h1, d1, 1);
		Peca espada = new Peca(1, 8, TipoPeca.ESPADA.comoChar());
		
		HashMap<Integer, Boolean> movimentosPossiveis = t1.getMovimentosPossiveis(h1);
		assertFalse(movimentosPossiveis.containsKey(Movimentos.MOVIMENTO_BAIXO.getDirecaoInt()));
		t1.trocarPecas(Movimentos.MOVIMENTO_BAIXO.getDirecaoInt(), h1);
		//assertTrue(h1.estaMorto());
	}

	// matar o drag�o;
	@Test
	public void testSlayingtheDragon() {
		fail("Not yet implemented");
	}

	// alcan�ar a sa�da ap�s apanhar espada
	// e matar drag�o (vit�ria);
	@Test
	public void testReachingexitwin() {
		fail("Not yet implemented");
	}

	// alcan�ar a sa�da sem ter apanhado a espada
	// ou morto o drag�o.
	@Test
	public void testReachingexitnotwin() {
		fail("Not yet implemented");
	}

}
