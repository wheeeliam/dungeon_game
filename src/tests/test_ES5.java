package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class test_ES5 {
	Dungeon D1 = new Dungeon(5, 1);
	Player P1 = new Player(0, 0, D1);
	Bomb B1 = new Bomb(0,1, D1, 2);
	Enemy E1 = new Enemy(4, 0, D1);
	
	Dungeon D2 = new Dungeon(4, 1);
	Player P2 = new Player(0, 0, D2);
	Enemy E2 = new Enemy(3, 0, D2);
	
	Dungeon D3 = new Dungeon(5, 1);
	Player P3 = new Player(0, 0, D3);
	Ghost G1 = new Ghost(4, 0, D3);
	
	Dungeon D4 = new Dungeon(4, 1);
	Player P4 = new Player(0, 0, D4);
	Ghost G2 = new Ghost(3, 0, D4);
	

	
	@Test
	void Test51() {
		// If the character collides with a normal enemy while not having a special ability, the character should be destroyed by the enemy, and disappear from the dungeon.

		D1.addEntity(P1);
		D1.setPlayer(P1);
		D1.addEntity(E1);
		
		assertTrue(D1.getDungeonState() == "Ongoing");
		P1.moveRight();
		assertTrue(D1.getDungeonState() == "Ongoing");		
		P1.moveRight();
		assertFalse(D1.getEntities().contains(P1));
		assertTrue(D1.getDungeonState() == "Failed");
		
		
		D2.addEntity(P2);
		D2.setPlayer(P2);
		D2.addEntity(E2);
		
		assertTrue(D2.getDungeonState() == "Ongoing");
		P2.moveRight();
		assertTrue(D2.getDungeonState() == "Ongoing");
		P2.moveRight();
		assertFalse(D2.getEntities().contains(P2));
		assertTrue(D2.getDungeonState() == "Failed");
		
		// If the character collides with a ghost enemy while not having a special ability, the character should be destroyed by the enemy, and disappear from the dungeon.
		D3.addEntity(P3);
		D3.setPlayer(P3);
		D3.addEntity(G1);
		
		assertTrue(D3.getDungeonState() == "Ongoing");
		P3.moveRight();
		assertTrue(D3.getDungeonState() == "Ongoing");		
		P3.moveRight();
		assertFalse(D3.getEntities().contains(P3));
		assertTrue(D3.getDungeonState() == "Failed");
		
		
		D4.addEntity(P4);
		D4.setPlayer(P4);
		D4.addEntity(G2);
		
		assertTrue(D4.getDungeonState() == "Ongoing");
		P4.moveRight();
		assertTrue(D4.getDungeonState() == "Ongoing");
		P4.moveRight();
		assertFalse(D4.getEntities().contains(P4));
		assertTrue(D4.getDungeonState() == "Failed");


	}
	
	@Test
	void Test52() {
		// USER INTERFACE
	}
	
	@Test
	void Test53() {
		D1.setPlayer(P1);
		D1.addEntity(P1);
		D1.addEntity(B1);
		
		assertTrue(D1.getEntities().contains(B1));
		assertTrue(D1.getEntities().contains(P1));
		assertTrue(D1.getDungeonState() == "Ongoing");


		P1.moveRight();
		assertTrue(D1.getEntities().contains(B1));
		assertTrue(D1.getEntities().contains(P1));
		assertTrue(D1.getDungeonState() == "Ongoing");


		P1.moveRight();
		assertFalse(D1.getEntities().contains(B1));
		assertFalse(D1.getEntities().contains(P1));
		assertTrue(D1.getDungeonState() == "Failed");

	}
}
