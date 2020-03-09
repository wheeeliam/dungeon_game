package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class test_ES2 {
	
	Dungeon D1 = new Dungeon(10, 10);
	
	Player P1 = new Player(1, 1, D1);
	Wall W1 = new Wall(0,1,D1);
	Wall W2 = new Wall(1,0,D1);
	Boulder B1 = new Boulder(2, 1, D1);
	// Key 1 has ID 1
	Key K1 = new Key(2, 1, 1, D1);
	// Key 2 has ID 2
	Key K2 = new Key(3, 1, 2, D1);
	Door Door2 = new Door (0, 1, 2, D1);
	Door Door1 = new Door (4, 1, 1, D1);
	Sword S1 = new Sword (4, 1, D1);
	Sword S2 = new Sword (5, 1, D1);
	Sword S3 = new Sword (2, 1, D1);
	Enemy E1 = new Enemy(5,1,D1);
	Enemy E2 = new Enemy(6,1,D1);
	Enemy E3 = new Enemy(7,1,D1);
	Enemy E4 = new Enemy(8,1,D1);
	Enemy E5 = new Enemy(9,1,D1);
	Treasure T1 = new Treasure (6, 1, D1);
	Treasure T2 = new Treasure (7, 1, D1);
	
	private boolean entityIsInDungeon(Entity entity, Dungeon dungeon) {
		for (Entity e : dungeon.getEntities()) {
			if (e.equals(entity)) return true;
		}
		return false;
	}
	
	@Test 
	void Test21() {
		D1.setPlayer(P1);
		D1.addEntity(K1);
		D1.addEntity(K2);
		D1.addEntity(S1);
		D1.addEntity(S2);
		D1.addEntity(T1);
		D1.addEntity(T2);
		
		P1.moveRight();
		
		// check Player is on the same square as Key 1, can pick up
		assertTrue(P1.getX() == K1.getX());
		assertTrue(P1.getY() == K1.getY());
		// Have Player pickup Key 1
		assertFalse(P1.hasKey());
		P1.pickUp();
		assertFalse(entityIsInDungeon(K1, D1));
		assertTrue(P1.hasKey());
		assertTrue(P1.getKey().equals(K1));
		
		P1.moveRight();
		
		// check Player is on the same square as Key 2, will not pick up
		assertTrue(P1.getX() == K2.getX());
		assertTrue(P1.getY() == K2.getY());
		assertTrue(P1.hasKey());
		P1.pickUp();
		assertTrue(entityIsInDungeon(K2, D1));
		assertTrue(P1.getKey().equals(K1));
		
		P1.moveRight();
		
		// check Player is on the same square as Sword 1, can pick up
		assertTrue(P1.getX() == S1.getX());
		assertTrue(P1.getY() == S1.getY());
		// Have Player pickup sword 1
		assertFalse(P1.hasSword());
		P1.pickUp();
		assertFalse(entityIsInDungeon(S1, D1));
		assertTrue(P1.hasSword());
		assertTrue(P1.getSword().equals(S1));
		
		P1.moveRight();
		
		// check Player is on the same square as Sword 2, will not pick up
		assertTrue(P1.getX() == S2.getX());
		assertTrue(P1.getY() == S2.getY());
		assertTrue(P1.hasSword());
		P1.pickUp();
		assertTrue(entityIsInDungeon(S2, D1));
		assertTrue(P1.getSword().equals(S1));
		
		P1.moveRight();
	
		
		// check Player is on the same square as Treasure 1, can pick up
		assertTrue(P1.getX() == T1.getX());
		assertTrue(P1.getY() == T1.getY());
		// Have Player pickup Treasure 1
		assertTrue(P1.getNumberOfTreasures() == 0);
	
		P1.pickUp();
		assertFalse(entityIsInDungeon(T1, D1));
		assertTrue(P1.getNumberOfTreasures() == 1);
		assertTrue(P1.getTreasures().contains(T1));
		
		P1.moveRight();
		
		// check Player is on the same square as Treasure 2, can pick up
		assertTrue(P1.getX() == T2.getX());
		assertTrue(P1.getY() == T2.getY());
		assertTrue(P1.getNumberOfTreasures() == 1);
		P1.pickUp();
		assertFalse(entityIsInDungeon(T2, D1));
		assertTrue(P1.getNumberOfTreasures() == 2);
		assertTrue(P1.getTreasures().contains(T2));
		
		P1.moveRight();
	}
	
	
	@Test
	void Test22() {
		D1.setPlayer(P1);
		D1.addEntity(B1);
		D1.addEntity(W1);
		D1.addEntity(W2);
		// check Player is facing down, can't interact with boulder that is to the right of player
		assertTrue(P1.getDirection() instanceof PlayerDownDirection);
		assertTrue(P1.getX() + 1 == B1.getX());
		assertTrue(P1.getY() == B1.getY());
		P1.interactWithEntity();
		
		// check that boulder is still in same location
		assertTrue(P1.getX() + 1 == B1.getX());
		assertTrue(P1.getY() == B1.getY());
		
		// move player left into wall, can't interact with boulder that is to right of player
		P1.moveLeft();
		assertTrue(P1.getDirection() instanceof PlayerLeftDirection);
		assertTrue(P1.getX() + 1 == B1.getX());
		assertTrue(P1.getY() == B1.getY());
		P1.interactWithEntity();
		
		// check that boulder is still in same location
		assertTrue(P1.getX() + 1 == B1.getX());
		assertTrue(P1.getY() == B1.getY());
		
		// move player left into wall, can't interact with boulder that is to right of player
		P1.moveUp();
		assertTrue(P1.getDirection() instanceof PlayerUpDirection);
		assertTrue(P1.getX() + 1 == B1.getX());
		assertTrue(P1.getY() == B1.getY());
		P1.interactWithEntity();
		
		// check that boulder is still in same location
		assertTrue(P1.getX() + 1 == B1.getX());
		assertTrue(P1.getY() == B1.getY());
		
		// move player right into boulder, can interact with boulder that is to right of player
		P1.moveRight();
		assertTrue(P1.getDirection() instanceof PlayerRightDirection);
		assertTrue(P1.getX() + 1 == B1.getX());
		assertTrue(P1.getY() == B1.getY());
		P1.interactWithEntity();
		
		// check that boulder moved one square to the right
		assertTrue(P1.getX() + 2 == B1.getX());
		assertTrue(P1.getY() == B1.getY());
	}
	
	@Test 
	void Test23() {
		D1.setPlayer(P1);
		D1.addEntity(K1);
		D1.addEntity(K2);
		D1.addEntity(Door1);
		D1.addEntity(Door2);
		
		P1.moveRight();
		P1.pickUp();
		//check key has ID 1
		assertTrue(P1.getKey().getID() == 1);
		P1.moveLeft();
		
		//check that you're standing to the right of a closed Door with ID 2
		assertTrue(P1.getX() - 1 == Door2.getX());
		assertTrue(P1.getY() == Door2.getY());
		assertFalse(Door2.isOpen());
		assertTrue(Door2.getID() == 2);

		
		// check that Key 1 does not open Door 2
		P1.interactWithEntity();
		assertFalse(Door2.isOpen());
		assertTrue(P1.hasKey());
		
		P1.moveRight();
		P1.moveRight();
		
		//check that you're standing to the left of a closed Door with ID 1

		assertTrue(P1.getX() + 1 == Door1.getX());
		assertTrue(P1.getY() == Door1.getY());
		assertFalse(Door1.isOpen());
		assertTrue(Door1.getID() == 1);
		
		// check that Key 1 does open Door 1
		P1.interactWithEntity();
		assertTrue(Door1.isOpen());
		assertFalse(P1.hasKey());
		
		P1.pickUp();
		//check key has ID 2
		assertTrue(P1.getKey().getID() == 2);
		P1.moveLeft();
		// check that Key 2 cannot open Door 2 if they are not adjacent
		
		assertTrue(P1.getX() - 1 != Door2.getX());
		assertTrue(P1.getY() == Door2.getY());
		
		P1.interactWithEntity();
		assertFalse(Door2.isOpen());
		assertTrue(P1.hasKey());
		
		P1.moveLeft();
		
		//check that you're standing to the right of a closed Door with ID 2
		assertTrue(P1.getX() - 1 == Door2.getX());
		assertTrue(P1.getY() == Door2.getY());
		assertFalse(Door2.isOpen());
		assertTrue(Door2.getID() == 2);

		
		// check that Key 2 does open Door 2
		P1.interactWithEntity();
		assertTrue(Door2.isOpen());
		assertFalse(P1.hasKey());		
		
		
	}
	
	Dungeon D24 = new Dungeon(5, 1);
	
	Player P24 = new Player(0, 0, D24);
	Ghost G24 = new Ghost(4,0,D24);
	Sword S24 = new Sword(0,0,D24);
	
	@Test
	void Test24() {
		D1.setPlayer(P1);
		D1.addEntity(S3);
		D1.addEntity(E1);
		D1.addEntity(E2);
		D1.addEntity(E3);
		D1.addEntity(E4);
		D1.addEntity(E5);
		D1.addEnemy(E1);
		D1.addEnemy(E2);
		D1.addEnemy(E3);
		D1.addEnemy(E4);
		D1.addEnemy(E5);
		
		
		// pick up sword, check that it has 5 hits

		P1.moveRight();

		P1.pickUp();

		assertTrue(P1.hasSword());
		assertTrue(P1.getSword().getUses() == 5);
		
		
		// kill first enemy
		P1.interactWithEntity();
		//assertFalse(D1.getEntities().contains(E1));
		assertTrue(P1.hasSword());
		assertTrue(P1.getSword().getUses() == 4);
		
		// kill second enemy
		P1.interactWithEntity();
		assertFalse(D1.getEntities().contains(E2));
		assertTrue(P1.hasSword());
		assertTrue(P1.getSword().getUses() == 3);
		
		// kill third enemy
		P1.interactWithEntity();
		assertFalse(D1.getEntities().contains(E3));
		assertTrue(P1.hasSword());
		assertTrue(P1.getSword().getUses() == 2);
		
		//kill fourth enemy
		P1.interactWithEntity();
		assertFalse(D1.getEntities().contains(E4));
		assertTrue(P1.hasSword());
		assertTrue(P1.getSword().getUses() == 1);
		
		//kill fourth enemy
		P1.interactWithEntity();
		assertFalse(D1.getEntities().contains(E5));
		assertFalse(P1.hasSword());
		
		D24.setPlayer(P24);
		D24.addEntity(P24);
		D24.addEntity(G24);
		D24.addEntity(S24);
		
		P24.pickUp();
		P24.moveRight();
		assertTrue(P24.getX() == 1);
		assertTrue(P24.getY() == 0);
		assertTrue(G24.getX() == 2);
		assertTrue(G24.getY() == 0);
		assertTrue(P24.hasSword());
		assertTrue(P24.getSword().getUses() == 5);
		assertTrue(D24.getEntities().contains(G24));

		P24.interactWithEntity();
		assertTrue(G24.getX() == 1);
		assertTrue(G24.getY() == 0);
		assertTrue(P24.getSword().getUses() == 4);
		assertTrue(D24.getEntities().contains(G24));








	}
	
	Dungeon D2 = new Dungeon(10, 1);
	Player P2 = new Player(0,0,D2);
	Potion Pot1 = new Potion(1,0,D2, new PotionAbilityInvincible());
	Potion Pot2 = new Potion(9,0,D2, new PotionAbilityInvincible());
	Potion Pot3 = new Potion(7,0,D2, new PotionAbilityInvincible());
	Enemy E6 = new Enemy(3, 0, D2);

	@Test
	void Test25() {
		D2.setPlayer(P2);
		D2.addEntity(Pot1);
		D2.addEntity(E6);
		D2.addEnemy(E6);
		D2.addEntity(Pot2);
		D2.addEntity(Pot3);

		
		// move to right, track direction of enemy
		assertTrue(E6.getX() == 3);
		assertTrue(E6.getY() == 0);
		P2.moveRight();
		// moves closer to player
		assertTrue(E6.getX() == 2);
		assertTrue(E6.getY() == 0);
		
		// pick up potion
		P2.pickUp();
		assertFalse(D2.getEntities().contains(Pot1));
		assertTrue(P2.isInvincible());
		assertTrue(P2.potionLeft() == 10);
		
		// enemy moves farther away from player
		assertTrue(E6.getX() == 3);
		assertTrue(E6.getY() == 0);
		
		//player moves right
		P2.moveRight();
		assertTrue(P2.isInvincible());
		assertTrue(P2.potionLeft() == 9);
		
		// enemy moves farther away from player
		assertTrue(E6.getX() == 4);
		assertTrue(E6.getY() == 0);
		
		//player moves right
		P2.moveRight();
		assertTrue(P2.isInvincible());
		assertTrue(P2.potionLeft() == 8);
		
		// enemy moves farther away from player
		assertTrue(E6.getX() == 5);
		assertTrue(E6.getY() == 0);
		
		//player moves right
		P2.moveRight();
		assertTrue(P2.isInvincible());
		assertTrue(P2.potionLeft() == 7);
		
		// enemy moves farther away from player
		assertTrue(E6.getX() == 6);
		assertTrue(E6.getY() == 0);
		
		//player moves right
		P2.moveRight();
		assertTrue(P2.isInvincible());
		assertTrue(P2.potionLeft() == 6);
		
		// enemy moves farther away from player
		assertTrue(E6.getX() == 7);
		assertTrue(E6.getY() == 0);
		
		//player moves right
		P2.moveRight();
		assertTrue(P2.isInvincible());
		assertTrue(P2.potionLeft() == 5);
		
		// enemy moves farther away from player
		assertTrue(E6.getX() == 8);
		assertTrue(E6.getY() == 0);
		
		//player moves right
		P2.moveRight();
		assertTrue(P2.isInvincible());
		assertTrue(P2.potionLeft() == 4);
		
		// enemy moves farther away from player
		assertTrue(E6.getX() == 9);
		assertTrue(E6.getY() == 0);
		// enemy moves farther away from player
		assertTrue(P2.getX() == 7);
		assertTrue(P2.getY() == 0);
		
		//player moves right
		P2.moveRight();
		assertTrue(P2.isInvincible());
		assertTrue(P2.potionLeft() == 3);
		// enemy moves farther away from player
		assertTrue(E6.getX() == 9);
		assertTrue(E6.getY() == 0);
		
		//player moves right
		P2.moveRight();
		assertTrue(P2.isInvincible());
		assertTrue(P2.potionLeft() == 2);
		// enemy is killed
		assertFalse(D2.getEntities().contains(E6));
		
		//player moves left twice and loses invincibility
		P2.moveLeft();
		P2.moveLeft();
		assertFalse(P2.isInvincible());
		
		//player picks up second invincibility potion
		P2.pickUp();
		assertTrue(P2.isInvincible());
		assertTrue(P2.potionLeft() == 10);
		P2.moveRight();
		assertTrue(P2.potionLeft() == 9);
		P2.moveRight();
		assertTrue(P2.potionLeft() == 8);
		
		//player picks up third invincibility potion, resets counter
		P2.pickUp();
		assertTrue(P2.potionLeft() == 10);

		
		
		
		
		
		
	}
	
	Dungeon D26 = new Dungeon(9,3);
	Player P26 = new Player(0,0,D26);
	Potion Pot261 = new Potion(0, 0, D26, new PotionAbilityGhost());
	Potion Pot262 = new Potion(4, 0, D26, new PotionAbilityGhost());
	Potion Pot263 = new Potion(8, 0, D26, new PotionAbilityGhost());

	
	Wall W261 = new Wall(1,0,D26);
	Wall W262 = new Wall(0,1,D26);
	Wall W263 = new Wall(1,1,D26);
	Wall W264 = new Wall(2,1,D26);
	Wall W265 = new Wall(1,2,D26);
	
	Enemy E261 = new Enemy(8,0,D26);
	Ghost E262 = new Ghost(8,2,D26);




	
	@Test
	void Test26() {
		D26.setPlayer(P26);
		D26.addEntity(P26);
		D26.addEntity(Pot261);
		D26.addEntity(Pot262);
		D26.addEntity(Pot263);

		D26.addEntity(W261);
		D26.addEntity(W262);
		D26.addEntity(W263);
		D26.addEntity(W264);
		D26.addEntity(W265);
		D26.addEntity(E261);
		D26.addEntity(E262);

		
		assertTrue(P26.getX() == 0 && P26.getY() == 0);
		assertTrue(E261.getX() == 8 && E261.getY() == 0);
		assertTrue(E262.getX() == 8 && E262.getY() == 2);

		P26.moveRight();
		assertTrue(P26.getX() == 0 && P26.getY() == 0);
		assertTrue(E261.getX() == 7 && E261.getY() == 0);
		assertTrue(E262.getX() == 7 && E262.getY() == 2);

		P26.moveUp();
		assertTrue(P26.getX() == 0 && P26.getY() == 0);
		assertTrue(E261.getX() == 6 && E261.getY() == 0);
		assertTrue(E262.getX() == 6 && E262.getY() == 2);
		

		P26.moveDown();
		assertTrue(P26.getX() == 0 && P26.getY() == 0);
		assertTrue(E261.getX() == 5 && E261.getY() == 0);
		assertTrue(E262.getX() == 5 && E262.getY() == 2);

		P26.moveLeft();
		assertTrue(P26.getX() == 0 && P26.getY() == 0);
		assertTrue(E261.getX() == 4 && E261.getY() == 0);
		assertTrue(E262.getX() == 4 && E262.getY() == 2);
		
		P26.pickUp();
		assertFalse(D26.getEntities().contains(Pot261));

		// Normal enemy has lost sight of player
		assertTrue(E261.getX() == 4 && E261.getY() == 0);
		// Ghost enemy continues to approach player
		assertTrue(E262.getX() == 3 && E262.getY() == 2);
		
		// can walk through "shallow walls"
		//1st move
		P26.moveDown();
		assertTrue(P26.getX() == 0 && P26.getY() == 1);
		assertTrue(E261.getX() == 4 && E261.getY() == 0);
		assertTrue(E262.getX() == 2 && E262.getY() == 2);

		//2nd
		P26.moveDown();
		assertTrue(P26.getX() == 0 && P26.getY() == 2);
		assertTrue(E261.getX() == 4 && E261.getY() == 0);
		assertTrue(E262.getX() == 1 && E262.getY() == 2);
		
		// can't walk off grid
		//3rd
		P26.moveDown();
		assertTrue(P26.getX() == 0 && P26.getY() == 2);
		assertTrue(E261.getX() == 4 && E261.getY() == 0);

		// Ghost enemy has collided with player, will die
		assertFalse(D26.getEntities().contains(E262));
		
		// can't walk through "deep walls"
		//4th
		P26.moveUp();
		assertTrue(P26.getX() == 0 && P26.getY() == 1);
		//5th
		P26.moveRight();
		assertTrue(P26.getX() == 0 && P26.getY() == 1);
		//6th
		P26.moveUp();
		//7th
		P26.moveRight();
		//8th
		P26.moveRight();
		//9th
		P26.moveRight();
		assertTrue(D26.getEntities().contains(E261));
		//10th
		P26.moveRight();
		assertFalse(D26.getEntities().contains(E261));
		P26.interactWithEntity();
		P26.interactWithEntity();
		P26.interactWithEntity();
		P26.interactWithEntity();
		P26.interactWithEntity();
		P26.interactWithEntity();
		P26.interactWithEntity();
		P26.interactWithEntity();
		P26.interactWithEntity();
		assertTrue(P26.isInvisible());
		P26.interactWithEntity();
		assertFalse(P26.isInvisible());
		P26.pickUp();
		assertTrue(P26.isInvisible());
		P26.moveRight();
		P26.moveRight();
		P26.moveRight();
		P26.moveRight();
		assertTrue(P26.potionLeft() == 16);
		P26.pickUp();
		assertTrue(P26.potionLeft() == 20);


	
		
		

		




	}
	
	

}
