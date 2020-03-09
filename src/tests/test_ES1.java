package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class test_ES1 {
	Dungeon D = new Dungeon(4, 4);
	Player P = new Player(1, 1, D);
	Wall W = new Wall(2, 1, D);
	Boulder B1 = new Boulder(1, 3, D);
	Boulder B2 = new Boulder(2, 4, D);
	Exit E1 = new Exit(2, 1, D);
	Treasure T = new Treasure(2, 1, D);
	Key K = new Key(2, 1, 10, D);
	FloorSwitch FS = new FloorSwitch(2, 1, D);
	Portal P1 = new Portal(2, 1, 10, D);
	Enemy E2 = new Enemy(2, 1, D);
	Potion P2 = new Potion(2, 1, D, new PotionAbilityInvincible());
	Sword S = new Sword(2, 1, D);
	Potion P3 = new Potion(2, 1, D, new PotionAbilityGhost());
	Bomb B3 = new Bomb(2, 1, D, 60);


	private boolean checkEntitySameCoordinates(Entity a, Entity b) {
		if (a.getX() == b.getX() && a.getY() == b.getY()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Test
	void Test11() {
		D.addEntity(P);
		D.setPlayer(P);
		
		// Character can only move one square at a time
		int spacesMoved;
		int prevCoordinates = P.getX() + P.getY();
		assertTrue(P.getX() == 1 && P.getY() == 1);
		P.moveRight();
		int afterCoordinates = P.getX() + P.getY();
		assertTrue(P.getX() == 2 && P.getY() == 1);
		spacesMoved = afterCoordinates - prevCoordinates;
		assertTrue(spacesMoved == 1);

		// Character can only move up, down, left or right
		P.moveRight();
		assertTrue(P.getX() == 3 && P.getY() == 1);
		P.moveLeft();
		assertTrue(P.getX() == 2 && P.getY() == 1);
		P.moveDown();
		assertTrue(P.getX() == 2 && P.getY() == 2);
		P.moveUp();
		assertTrue(P.getX() == 2 && P.getY() == 1);

		// Character can only move within the boundaries of the dungeon grid of squares
		P.setPosition(3, 1);
		P.moveRight();
		assertTrue(P.getX() == 3 && P.getY() == 1);
		assertTrue(D.getWidth() > P.getX());
		
		P.setPosition(1, 3);
		P.moveDown();
		assertTrue(P.getX() == 1 && P.getY() == 3);
		assertTrue(D.getHeight() > P.getY());
		
		// When a character moves in a certain direction, it will "face" that direction as well.
		P.setPosition(2, 2);
		P.moveRight();
		assertTrue(P.getDirection() instanceof PlayerRightDirection);
		P.moveLeft();
		assertTrue(P.getDirection() instanceof PlayerLeftDirection);
		P.moveDown();
		assertTrue(P.getDirection() instanceof PlayerDownDirection);
		P.moveUp();
		assertTrue(P.getDirection() instanceof PlayerUpDirection);
	}
	
	@Test
	void Test12() {
		D.addEntity(P);
		D.setPlayer(P);
		D.addEntity(W);
		D.addEntity(B1);
		D.addEntity(B2);
		
		// If character is on an adjacent square to a wall entity, and moves in that direction, it should remain in the same grid square.
		assertTrue(P.getX() == 1 && P.getY() == 1);
		D.hasBlocking(2, 1);
		P.moveRight();
		assertTrue(D.hasBlocking(2, 1));
		assertTrue(W.isBlocking());
		assertTrue(P.getX() == 1 && P.getY() == 1);

		// If character is on an adjacent square to a boulder entity, and moves in that direction, it should remain in the same grid square.
		P.moveDown();
		assertTrue(P.getX() == 1 && P.getY() == 2);
		P.moveDown();
		assertTrue(D.hasBlocking(1, 3));
		assertTrue(B1.isBlocking());
		assertTrue(P.getX() == 1 && P.getY() == 2);
		
		// If character is on an adjacent square to a boulder entity, and moves in that direction, it should change to "face" that direction.
		P.setPosition(2, 2);
		P.moveDown();
		assertTrue(P.getDirection() instanceof PlayerDownDirection);
		assertTrue(P.getX() == 2 && P.getY() == 3);
		P.moveLeft();
		assertTrue(D.hasBlocking(1, 3));
		assertTrue(P.getDirection() instanceof PlayerLeftDirection);
		assertTrue(P.getX() == 2 && P.getY() == 3);
		P.moveDown();
		assertTrue(D.hasBlocking(2, 4));
		assertTrue(P.getDirection() instanceof PlayerDownDirection);
		assertTrue(P.getX() == 2 && P.getY() == 3);
	}
	
	@Test
	void Test13() {
		D.addEntity(P);
		D.setPlayer(P);
		
		// If character is on an adjacent square to a non blocking entity, and moves in that direction, it should move onto the same square as that entity.
		
		// Exit Entity
		D.addEntity(E1);
		assertFalse(D.hasBlocking(2, 1));
		assertTrue(P.getX() == 1 && P.getY() == 1);
		P.moveRight();
		assertTrue(P.getX() == 2 && P.getY() == 1);
		assertTrue(checkEntitySameCoordinates(P, E1));
		D.removeEntity(E1);
		
		// Treasure Entity
		D.addEntity(T);
		P.setPosition(1, 1);
		P.moveRight();
		assertTrue(P.getX() == 2 && P.getY() == 1);
		assertTrue(checkEntitySameCoordinates(P, T));
		D.removeEntity(T);
		
		// Key Entity
		D.addEntity(K);
		P.setPosition(1, 1);
		P.moveRight();
		assertTrue(P.getX() == 2 && P.getY() == 1);
		assertTrue(checkEntitySameCoordinates(P, K));
		D.removeEntity(K);
		
		// Treasure Entity
		D.addEntity(T);
		P.setPosition(1, 1);
		P.moveRight();
		assertTrue(P.getX() == 2 && P.getY() == 1);
		assertTrue(checkEntitySameCoordinates(P, T));
		D.removeEntity(T);
		
		// Portal Entity
		D.addEntity(P1);
		P.setPosition(1, 1);
		P.moveRight();
		assertTrue(P.getX() == 2 && P.getY() == 1);
		assertTrue(checkEntitySameCoordinates(P, P1));
		D.removeEntity(P1);
		
		// Sword Entity
		D.addEntity(S);
		P.setPosition(1, 1);
		P.moveRight();
		assertTrue(P.getX() == 2 && P.getY() == 1);
		assertTrue(checkEntitySameCoordinates(S, T));
		D.removeEntity(S);
		
		// Treasure Entity
		D.addEntity(T);
		P.setPosition(1, 1);
		P.moveRight();
		assertTrue(P.getX() == 2 && P.getY() == 1);
		assertTrue(checkEntitySameCoordinates(P, T));
		D.removeEntity(T);
		
		// Potion Entity
		D.addEntity(P2);
		P.setPosition(1, 1);
		P.moveRight();
		assertTrue(P.getX() == 2 && P.getY() == 1);
		assertTrue(checkEntitySameCoordinates(P, P2));
		D.removeEntity(P2);
		
		// Enemy Entity
		D.addEntity(E2);
		P.setPosition(1, 1);
		P.moveRight();
		assertTrue(P.getX() == 2 && P.getY() == 1);
		assertTrue(checkEntitySameCoordinates(P, E2));
		
		// Invisibility Potion
		D.addEntity(P3);
		P.setPosition(1, 1);
		P.moveRight();
		assertTrue(P.getX() == 2 && P.getY() == 1);
		assertTrue(checkEntitySameCoordinates(P, P3));
		D.removeEntity(P3);
		
		// Bomb Entity
		D.addEntity(B3);
		P.setPosition(1, 1);
		P.moveRight();
		assertTrue(P.getX() == 2 && P.getY() == 1);
		assertTrue(checkEntitySameCoordinates(P, B3));
		D.removeEntity(B3);
	}
	
}
