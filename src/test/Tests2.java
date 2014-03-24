package test;

import static org.junit.Assert.*;

import maze.Dragon;
import maze.Hero;
import maze.Logic;
import maze.Maze;

import org.junit.Test;

//Criar uma segunda classe de teste para testar o jogo com os comportamentos mais complexos 
//do dragão (movimentação, dormir, múltiplos dragões). Para efeito de teste, os movimentos do 
//dragão devem ser comandados pelo código de teste (em vez de ocorerem de forma aleatória). O 
//resto do programa (interação com o utilizador) deve continuar a funcionar da mesma forma. 
//Sugestão: "Virtualizar" o gerador aleatória usado pelo núcleo do jogo, usando a classe 
//java.util.Random no modo normal e uma classe especial controlada pelo código 

//
//public class Tests2 {
//
//	
//
//	@Test
//	public void testMovingDragon() {
//		Logic j1 = new Logic();
//		j1.dragon = new Dragon(4, 5);
//		j1.hero = new Hero(1, 1);
//		j1.maze = new Maze(j1.hero, j1.dragon, 1);
////		int posX = j1.dragon.getPosX();
////		int posY = j1.dragon.getPosY();
//		while(j1.dragon.getPosY()==5){
//				j1.moveDragon();} 
//	
//		assertNotSame(5, j1.dragon.getPosY());
//		
//		while(j1.dragon.getPosX()==4){
//			j1.moveDragon();}
//		
//		assertNotSame(4, j1.dragon.getPosX());
//	}
//
//	@Test
//	public void testSleepingDragon() {
//		Logic j1 = new Logic();
//		j1.dragon = new Dragon(4, 5);
//		j1.hero = new Hero(1, 1);
//		j1.maze = new Maze(j1.hero, j1.dragon, 1);
//		assertFalse(j1.dragon.getAsleep());
//		j1.dragon.setAsleep(true);
//		assertTrue(j1.dragon.getAsleep());
//
//	}
//	
//	@Test
//	public void testWithTonsofDragons() {
//		
//
//	}
//
//}
