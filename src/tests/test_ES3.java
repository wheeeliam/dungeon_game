package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;



public class test_ES3 {
	
	Dungeon D = new Dungeon(5, 5);
	Player P = new Player(1, 1, D);
	Boulder B1 = new Boulder(1, 2, D);
	Boulder B2 = new Boulder(2, 2, D);
	FloorSwitch FS = new FloorSwitch(1, 3, D);
	Portal P1 = new Portal(4, 1, 10, D);
	Portal P2 = new Portal(4, 4, 10, D);
	Door D1 = new Door(2, 1, 10, D);
	Key K1 = new Key(1, 1, 10, D);
	
	Dungeon D3 = new Dungeon(5, 1);
	Player Pl = new Player(0, 0, D3);
	Portal P3 = new Portal(1, 0, 1, D3);
	Portal P4 = new Portal(4, 0, 1, D3);
	Enemy E = new Enemy(2, 0, D3);

	
	private boolean checkEntitySameCoordinates(Entity a, Entity b) {
		if (a.getX() == b.getX() && a.getY() == b.getY()) {
			return true;
		} else {
			return false;
		}
	}
	
	Dungeon D2 = new Dungeon(4,4);
	Player player = new Player(0, 2, D2);
	Potion potion = new Potion(0, 2, D2, new PotionAbilityInvincible());
	Boulder boulder = new Boulder(0, 1, D2);
	Wall wall1 = new Wall(1, 0, D2);
	Wall wall2 = new Wall(1, 2, D2);
	Enemy enemy = new Enemy(1, 1,  D2);
	Door door = new Door(2, 1, 1, D2);
	Key key = new Key(1, 3, 1, D2);
	
	@Test 
	void Test31() {
		D2.setPlayer(player);
		D2.addEntity(potion);
		D2.addEntity(boulder);
		D2.addEntity(wall1);
		D2.addEntity(wall2);
		D2.addEntity(enemy);
		D2.addEntity(door);
		D2.addEntity(key);
		
		// check that enemy cannot move, as they are surrounded by blocking entities
		player.moveDown();
		assertTrue(enemy.getX() == 1);
		assertTrue(enemy.getY() == 1);
		player.moveRight();
		assertTrue(enemy.getX() == 1);
		assertTrue(enemy.getY() == 1);
		player.pickUp();
		assertTrue(enemy.getX() == 1);
		assertTrue(enemy.getY() == 1);
		player.moveLeft();
		assertTrue(enemy.getX() == 1);
		assertTrue(enemy.getY() == 1);
		player.moveUp();
		assertTrue(enemy.getX() == 1);
		assertTrue(enemy.getY() == 1);
		
		// push up boulder so that enemy can move
		// enemy only moves one adjacent square at a time
		player.interactWithEntity();
		assertTrue(enemy.getX() == 0);
		assertTrue(enemy.getY() == 1);
		
		// player is invincible, will try to move one square away from player, but boulder is there so will stay in same square
		player.pickUp();
		assertTrue(enemy.getX() == 0);
		assertTrue(enemy.getY() == 1);
		
		player.moveDown();
		assertTrue(enemy.getX() == 0);
		assertTrue(enemy.getY() == 1);
		
		player.moveRight();
		assertTrue(enemy.getX() == 0);
		assertTrue(enemy.getY() == 1);
		
		player.moveRight();
		assertTrue(enemy.getX() == 0);
		assertTrue(enemy.getY() == 1);
		
		player.moveRight();
		assertTrue(enemy.getX() == 0);
		assertTrue(enemy.getY() == 1);
		
		player.moveUp();
		assertTrue(enemy.getX() == 0);
		assertTrue(enemy.getY() == 1);
		
		player.moveUp();
		assertTrue(enemy.getX() == 0);
		assertTrue(enemy.getY() == 1);
		
		player.moveLeft();
		assertTrue(enemy.getX() == 0);
		assertTrue(enemy.getY() == 1);
		
		player.interactWithEntity();
		assertTrue(enemy.getX() == 0);
		assertTrue(enemy.getY() == 1);
		
		player.interactWithEntity();
		assertTrue(enemy.getX() == 0);
		assertTrue(enemy.getY() == 1);
		
		player.interactWithEntity();
		assertTrue(enemy.getX() == 1);
		assertTrue(enemy.getY() == 1);
	
		// Enemy can walk through doors
		player.moveUp();
		assertTrue(enemy.getX() == 2);
		assertTrue(enemy.getY() == 1);
		
		player.moveDown();
		assertTrue(enemy.getX() == 3);
		assertTrue(enemy.getY() == 1);
		
		
		player.pickUp();
		assertTrue(enemy.getX() == 3);
		assertTrue(enemy.getY() == 1);
		


	}
	
	@Test
	void Test32() {
		D.addEntity(P);
		D.setPlayer(P);
		D.addEntity(B1);
		D.addEntity(B2);
		D.addEntity(FS);
		
		// If the character is adjacent to a boulder, it should have the option of pushing the boulder.
		assertTrue(P.getY() + 1 == B1.getY());
		P.moveDown();
		assertTrue(P.getDirection() instanceof PlayerDownDirection);
		assertTrue(B1.getX() == 1 && B1.getY() == 2);
		// If the character chooses to push the boulder and the next adjacent square in line with both the character and the boulder is empty, an open door or a floor switch, the boulder will move onto that square.
		assertFalse(D.hasBlocking(1, 3));
		P.interactWithEntity();
		// If the character chooses to push the boulder and the next adjacent square in line with both the character and the boulder is a portal, the boulder will move onto that square.
		assertTrue(B1.getX() == 1 && B1.getY() == 3);
		assertTrue(checkEntitySameCoordinates(B1, FS));
		
		// Switch is triggered
		assertTrue(FS.isTriggered());
		
		// Boulder cannot push boulder in line with another blocking entity
		P.setPosition(3, 2);
		P.moveLeft();
		P.interactWithEntity();
		assertTrue(B1.getX() == 1 && B1.getY() == 3);
		assertTrue(B2.getX() == 1 && B2.getY() == 2);
		P.setPosition(1, 1);
		P.moveDown();
		assertFalse(D.hasBlocking(1, 4));
		P.interactWithEntity();
		assertTrue(B1.getX() == 1 && B1.getY() == 3);
		assertTrue(B2.getX() == 1 && B2.getY() == 2);
		
		// Push boulder off switch, switch is untriggered
		B2.setPosition(2, 1);
		P.moveDown();
		P.interactWithEntity();
		assertTrue(B1.getX() == 1 && B1.getY() == 4);
		assertFalse(FS.isTriggered());
		
		// Boulder cannot be pushed onto pickup entity
		P.setPosition(1, 1);
		D.addEntity(K1);
		B2.setPosition(2, 1);
		K1.setPosition(3, 1);
		P.moveRight();
		assertTrue(P.getDirection() instanceof PlayerRightDirection);
		assertTrue(B2.getX() == 2 && B2.getY() == 1);
		assertFalse(D.hasBlocking(3, 1));
		P.interactWithEntity();
		assertTrue(B2.getX() == 2 && B2.getY() == 1);
	}
	
	@Test
	void Test33And34() {
		D.addEntity(P);
		D.setPlayer(P);
		D.addEntity(D1);
		D.addEntity(K1);
		
		// Pick up key and checks if player has it
		P.moveRight();
		assertTrue(checkEntitySameCoordinates(P, K1));
		P.pickUp();
		assertTrue(P.hasKey());
		
		// Cannot walk through unopened doors
		assertTrue(P.getX() == 1 && P.getY() == 1);
		P.moveRight();
		assertTrue(P.getX() == 1 && P.getY() == 1);
		assertTrue(D1.getX() == 2 && D1.getY() == 1);
		assertTrue(D.hasBlocking(2, 1));
		
		// Can walk through opened doors
		assertTrue(P.getDirection() instanceof PlayerRightDirection);
		P.interactWithEntity();
		assertFalse(D.hasBlocking(2, 1));
		P.moveRight();
		assertTrue(checkEntitySameCoordinates(P, D1));
		P.moveRight();
		assertTrue(P.getX() == 3 && P.getY() == 1);

		// Unable to push boulders through closed doors
		D.addEntity(B1);
		B1.setPosition(2, 1);
		P.setPosition(1, 1);
		D1.setPosition(3, 1);
		P.moveRight();
		assertTrue(P.getDirection() instanceof PlayerRightDirection);
		D1.close();
		assertTrue(D.hasBlocking(3, 1));
		assertTrue(B1.getX() == 2 && B1.getY() == 1);
		P.interactWithEntity();
		assertTrue(B1.getX() == 2 && B1.getY() == 1);
		
		// Able to push boulders through opened doors
		D1.open();
		assertFalse(D.hasBlocking(3, 1));
		assertTrue(B1.getX() == 2 && B1.getY() == 1);
		P.interactWithEntity();
		assertTrue(B1.getX() == 3 && B1.getY() == 1);
		P.moveRight();
		P.interactWithEntity();
		assertTrue(B1.getX() == 4 && B1.getY() == 1);
	}
	
	@Test
	void Test35() {
		D.addEntity(P);
		D.setPlayer(P);
		D.addEntity(P1);
		D.addEntity(P2);
		D.addEntity(B1);
		D.addEntity(B2);
		
		// Non blocking corresponding portal 
		P.moveRight();
		P.moveRight();
		assertTrue(P.getX() == 3 && P.getY() == 1);
		assertTrue(P1.getX() == 4 && P1.getY() == 1);
		assertFalse(D.hasBlocking(4, 4));
		P.moveRight();
		assertTrue(P.getX() == 4 && P.getY() == 4);
		assertTrue(checkEntitySameCoordinates(P, P2));
		
		// Blocking corresponding portal
		P.setPosition(3, 1);
		B1.setPosition(4, 4);
		assertTrue(D.hasBlocking(4, 4));
		assertTrue(P.getX() == 3 && P.getY() == 1);
		P.moveRight();
		assertTrue(P.getX() == 4 && P.getY() == 1);

		// Portal with no blocking entity on corresponding portal allows for teleport
		B1.setPosition(1, 4);
		B2.setPosition(3, 1);
		P.setPosition(2, 1);
		P.moveRight();
		assertTrue(B2.getX() == 3 && B2.getY() == 1);
		assertTrue(P1.getX() == 4 && P1.getY() == 1);

		assertFalse(D.hasBlocking(4, 4));
		P.interactWithEntity();
		assertTrue(B2.getX() == 4 && B2.getY() == 4);
		assertTrue(checkEntitySameCoordinates(B2, P2));
		
		// Portal with blocking entity on corresponding portal does not allow for teleport
		P.setPosition(2, 1);
		B2.setPosition(3, 1);
		B1.setPosition(4, 4);
		P.moveRight();
		assertTrue(B2.getX() == 3 && B2.getY() == 1);
		assertTrue(P1.getX() == 4 && P1.getY() == 1);
		
		assertTrue(D.hasBlocking(4, 4));
		P.interactWithEntity();
		assertTrue(B2.getX() == 4 && B2.getY() == 1);
		assertTrue(checkEntitySameCoordinates(B2, P1));
		assertFalse(checkEntitySameCoordinates(B2, P2));
		
		// Portal with nonblocking entity on corresponding portal does not allow for teleport
		P.setPosition(2, 1);
		D.addEntity(K1);
		B2.setPosition(3, 1);
		B1.setPosition(1, 4);
		K1.setPosition(4, 4);
		P.moveRight();
		assertTrue(B2.getX() == 3 && B2.getY() == 1);
		assertFalse(D.hasBlocking(4, 4));
		P.interactWithEntity();
		assertTrue(checkEntitySameCoordinates(B2, P1));
		
		// Enemies are capable to teleport
		D3.addEntity(Pl);
		D3.setPlayer(Pl);
		D3.addEntity(E);
		D3.addEnemy(E);
		D3.addEntity(P3);
		D3.addEntity(P4);
		
		assertTrue(Pl.getX() == 0 && Pl.getY() == 0);
		assertTrue(E.getX() == 2 && E.getY() == 0);
		Pl.interactWithEntity();
		assertTrue(E.getX() == 4 && E.getY() == 0);
		assertTrue(checkEntitySameCoordinates(E, P4));
 	}
	
	@Test
	void Test36() {
		// Tested in test_ES4
	}
	
	Dungeon D37 = new Dungeon(3, 3);
	Player P37 = new Player(0, 0, D37);
	Potion Pot37 = new Potion(0, 0, D37, new PotionAbilityInvincible());
	
	Enemy E371 = new Enemy(0,1,D37);
	Enemy E372 = new Enemy(0,2,D37);
	Enemy E373 = new Enemy(1,0,D37);
	Enemy E374 = new Enemy(1,2,D37);
	Ghost E375 = new Ghost(2,0,D37);
	Ghost E376 = new Ghost(2,1,D37);
	Ghost E377 = new Ghost(2,2,D37);
	
	Bomb B37 = new Bomb (1,1,D37,1);


	
	@Test
	void Test37() {
		D37.setPlayer(P37);
		D37.addEntity(P37);
		D37.addEntity(E371);
		D37.addEntity(E372);
		D37.addEntity(E373);
		D37.addEntity(E374);
		D37.addEntity(E375);
		D37.addEntity(E376);
		D37.addEntity(E377);
		D37.addEntity(Pot37);
		D37.addEntity(B37);
		
		P37.pickUp();
		P37.pickUp();

		assertFalse(D37.getEntities().contains(P37));
		assertFalse(D37.getEntities().contains(E371));
		assertFalse(D37.getEntities().contains(E372));
		assertFalse(D37.getEntities().contains(E373));
		assertFalse(D37.getEntities().contains(E374));
		assertFalse(D37.getEntities().contains(E375));
		assertFalse(D37.getEntities().contains(E376));
		assertFalse(D37.getEntities().contains(E377));
		assertFalse(D37.getEntities().contains(B37));
		assertTrue(D37.getDungeonState().equals("Failed"));


		


		
	}
	
	Dungeon D38 = new Dungeon(3, 3);
	Player P38 = new Player(0, 0, D38);
	
	Wall W381 = new Wall(1,0,D38);
	Wall W382 = new Wall(1,1,D38);
	Wall W383 = new Wall(1,2,D38);

	
	Ghost E381 = new Ghost(2,2,D38);
	
	
	@Test
	void Test38() {
		D38.setPlayer(P38);
		D38.addEntity(P38);
		D38.addEntity(W381);
		D38.addEntity(W382);
		D38.addEntity(W383);
		D38.addEntity(E381);

		
		
		P38.pickUp();
		// Can walk through walls (Wall W383)
		assertTrue((E381.getX() == 1) && (E381.getY() == 2));
		P38.pickUp();
		assertTrue((E381.getX() == 0) && (E381.getY() == 2));
		P38.pickUp();
		assertTrue((E381.getX() == 0) && (E381.getY() == 1));
		P38.pickUp();
		assertTrue((E381.getX() == 0) && (E381.getY() == 0));


	
	}
	
}
