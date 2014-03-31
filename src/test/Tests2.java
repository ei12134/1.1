package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import maze.Dragon;
import maze.GameState;
import maze.Hero;
import maze.Logic;
import maze.Maze;
import maze.Movement;

import org.junit.Test;
//
//Criar uma segunda classe de teste para testar o jogo com os comportamentos mais complexos 
//do dragão (movimentação, dormir, múltiplos dragões). Para efeito de teste, os movimentos do 
//dragão devem ser comandados pelo código de teste (em vez de ocorerem de forma aleatória). O 
//resto do programa (interação com o utilizador) deve continuar a funcionar da mesma forma. 
//Sugestão: "Virtualizar" o gerador aleatória usado pelo núcleo do jogo, usando a classe 
//java.util.Random no modo normal e uma classe especial controlada pelo código 


public class Tests2 {

	@Test
	public void testMovingDragon() {
		Logic j1 = new Logic(10);
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(4, 5));
		j1.hero = new Hero(1, 1);
		j1.maze = new Maze(j1.hero, j1.dragons);
		int posX = j1.dragons.get(0).getPosX();
		int posY = j1.dragons.get(0).getPosY();
		while(posY==5){
				j1.moveDragon(j1.dragons.get(0));} 
	
		assertNotSame(5, posY);
		
		while(posX==4){
			j1.moveDragon(j1.dragons.get(0));}
		
		assertNotSame(4, posX);
	}

	@Test
	public void testSleepingDragon() {
		Logic j1 = new Logic(10);
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(4, 5));
	j1.hero = new Hero(1, 1);
		j1.maze = new Maze(j1.hero, j1.dragons);
		assertFalse(j1.dragons.get(0).getAsleep());
		j1.dragons.get(0).setAsleep(true);
		assertTrue(j1.dragons.get(0).getAsleep());

	}
	
	@Test
	public void testWithTonsofDragons() {     //testa a criacao  de varios dragoes
		Logic j1 = new Logic(10);
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(4, 5));
		j1.setDragons(dragons);
	j1.hero = new Hero(1, 1);
		j1.maze = new Maze(j1.hero, j1.dragons);
		assertEquals(j1.dragons.size(), 1);
		dragons.add(new Dragon(4, 2));									//criacao de outro dragao
		assertEquals(j1.dragons.size(), 2);
		
	}

}
