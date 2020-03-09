package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import unsw.dungeon.AndCompletion;
import unsw.dungeon.BaseGoal;
import unsw.dungeon.Boulder;
import unsw.dungeon.BoulderGoalStrategy;
import unsw.dungeon.ComplexGoal;
import unsw.dungeon.Dungeon;
import unsw.dungeon.EnemiesGoalStrategy;
import unsw.dungeon.Enemy;
import unsw.dungeon.Entity;
import unsw.dungeon.Exit;
import unsw.dungeon.ExitGoalStrategy;
import unsw.dungeon.FloorSwitch;
import unsw.dungeon.Key;
import unsw.dungeon.OrCompletion;
import unsw.dungeon.Player;
import unsw.dungeon.PlayerDownDirection;
import unsw.dungeon.Potion;
import unsw.dungeon.Sword;
import unsw.dungeon.Treasure;
import unsw.dungeon.TreasureGoalStrategy;
import unsw.dungeon.Wall;

public class test_ES4 {

	Dungeon D = new Dungeon(4, 4);
	Player P = new Player(1, 1, D);
	Sword S = new Sword(1, 1, D);
	Enemy E1 = new Enemy(2, 3, D);
	Enemy E2 = new Enemy(1, 3, D);
	Boulder B1 = new Boulder(1, 2, D);
	Boulder B2 = new Boulder(2, 2, D);
	FloorSwitch FS = new FloorSwitch(1, 3, D);
	Wall W = new Wall (1, 0, D);
//	Treasure T1 = new Treasure (1, 2, D);
//	Treasure T2 = new Treasure (1, 3, D);
	Treasure T3 = new Treasure (2, 1, D);
	Treasure T4 = new Treasure (3, 1, D);
	Exit E = new Exit(3, 1, D);
	Key K = new Key(2, 1, 10, D);

	
	private boolean checkEntitySameCoordinates(Entity a, Entity b) {
		if (a.getX() == b.getX() && a.getY() == b.getY()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Test
	void Test41() {
		D.addEntity(P);
		D.setPlayer(P);
		D.addEntity(B1);
		D.addEntity(B2);
		D.addEntity(FS);
		D.addFloorSwitch(FS);

		
		// There should be equal or more boulders than floor switches in the dungeon.
		int boulderCount = 0, floorSwitchCount = 0;
		for (Entity e : D.getEntities()) {
			if (e instanceof Boulder) {
				boulderCount++;
			} else if (e instanceof FloorSwitch) {
				floorSwitchCount++;
			}
		}
		assertTrue(boulderCount >= floorSwitchCount);
		
		// If all floor switches are covered, this goal is considered complete.
		BaseGoal boulderGoal = new BaseGoal(D, new BoulderGoalStrategy(D), "boulder");
		D.setGoal(boulderGoal);
		assertTrue(D.getGoal() instanceof BaseGoal);
		P.moveDown();
		assertTrue(P.getDirection() instanceof PlayerDownDirection);
		assertTrue(D.getDungeonState().equals("Ongoing"));
		P.interactWithEntity();
		assertTrue(checkEntitySameCoordinates(B1, FS));
		assertTrue(FS.isTriggered());
		assertTrue(boulderGoal.isComplete());
		
		// If this is the only goal, the dungeon is considered complete.
		assertTrue(D.getDungeonState().equals("Complete"));

	}
	
	@Test
	void Test42() {
		D.addEntity(P);
		D.setPlayer(P);
		D.addEntity(S);
		D.addEntity(E1);
		D.addEntity(E2);

		BaseGoal enemiesGoal = new BaseGoal(D, new EnemiesGoalStrategy(D), "enemies");
		D.setGoal(enemiesGoal);
 		assertTrue(D.getGoal() instanceof BaseGoal);
		
		// There should be more than 0 enemies.
		int enemyCount = 0;
		for (Entity e : D.getEntities()) {
			if (e instanceof Enemy) {
				enemyCount++;
			}
		}
		assertTrue(enemyCount > 0);
		
		// There should be an entity that allows the character to destroy enemies.
		boolean destroyEnemies = false;
		for (Entity e : D.getEntities()) {
			if (e instanceof Sword || e instanceof Potion) {
				destroyEnemies = true;
			}
		}
		assertTrue(destroyEnemies);

		// The means of destroying enemies should be able to destroy all enemies in the dungeon.
		assertTrue(S.getUses() >= enemyCount);
		
		// If all enemies are destroyed, this goal is considered complete.
		P.pickUp();
		assertTrue(D.getDungeonState().equals("Ongoing"));
		P.interactWithEntity();
		P.interactWithEntity();
		enemyCount = 0;
		for (Entity e : D.getEntities()) {
			if (e instanceof Enemy) {
				enemyCount++;
			}
		}
		assertTrue(enemyCount == 0);
		assertTrue(enemiesGoal.isComplete());
      // If this is the only goal, the dungeon is considered complete.
		assertTrue(D.getDungeonState().equals("Complete"));
	}
	
	@Test
	void Test43() {
		D.addEntity(P);
		D.setPlayer(P);
		D.removeEntity(E1);
		D.removeEntity(E2);
		D.addEntity(T3);
		D.addEntity(T4);
		BaseGoal treasureGoal = new BaseGoal(D, new TreasureGoalStrategy(D), "treasure");
		D.setGoal(treasureGoal);
		assertTrue(D.getGoal() instanceof BaseGoal);

		// There should be more than 0 treasure present in the dungeon.
		int treasureCount = 0;
		for (Entity e : D.getEntities()) {
			if (e instanceof Treasure) {
				treasureCount++;
			}
		}
		assertTrue(treasureCount > 0);




		P.moveRight();
		assertTrue(checkEntitySameCoordinates(P, T3));
		P.pickUp();
		P.moveRight();
		assertTrue(checkEntitySameCoordinates(P, T4));

		P.pickUp();

		// If all treasure is collected, this goal is considered complete.
		assertTrue(treasureGoal.isComplete());
		assertTrue(treasureCount == P.getNumberOfTreasures());

		
		// If this is the only goal, the dungeon is considered complete.
		System.out.println(D.getDungeonState());
		System.out.println(D.getGoal());
		assertTrue(D.getDungeonState().equals("Complete"));
	}
	
	@Test
	void Test44() {
		D.addEntity(P);
		D.setPlayer(P);
		D.addEntity(E);
		D.addExit(E);
		BaseGoal exitGoal = new BaseGoal(D, new ExitGoalStrategy(D), "exit");
		D.setGoal(exitGoal);
		assertTrue(D.getGoal() instanceof BaseGoal);

		// There should only be one exit point accessible in the dungeon - never 0 or more than 1 exit point.
		int exitCount = 0;
		for (Entity e : D.getEntities()) {
			if (e instanceof Exit) {
				exitCount++;
			}
		}
		assertTrue(exitCount == 1);
		
		P.moveRight();
		assertFalse(checkEntitySameCoordinates(P, E));

		P.moveRight();
		
		// If exit goal is accessed, this goal is considered complete.
		assertTrue(checkEntitySameCoordinates(P, E));

		// If this is the only goal, the dungeon is considered complete.
		assertTrue(exitGoal.isComplete());
		assertTrue(D.getDungeonState().equals("Complete"));
	}
	
	@Test
	void TestComplexGoalWithoutExit() {
		// If this is the last goal, and exit is not a goal, the dungeon is considered complete.
		D.addEntity(P);
		D.setPlayer(P);
		D.addEntity(B1);
		D.addEntity(B2);
//		D.addEntity(T1);
//		D.addEntity(T2);
		D.addEntity(T3);
		D.addEntity(T4);
		D.addEntity(FS);
		D.addFloorSwitch(FS);
		
		BaseGoal boulderGoal = new BaseGoal(D, new BoulderGoalStrategy(D), "boulder");
		BaseGoal treasureGoal = new BaseGoal(D, new TreasureGoalStrategy(D), "treasure");
		ComplexGoal AndGoalCG = new ComplexGoal(new AndCompletion());
		AndGoalCG.addGoal(boulderGoal);
		AndGoalCG.addGoal(treasureGoal);
		D.setGoal(AndGoalCG);
		assertFalse(AndGoalCG.isComplete());
		
		P.moveDown();
		P.interactWithEntity();
		assertTrue(boulderGoal.isComplete());
		assertFalse(AndGoalCG.isComplete());
		
		P.moveRight();
		P.pickUp();
		P.moveRight();
		P.pickUp();
		P.moveLeft();
		P.moveLeft();
		P.moveDown();
		P.pickUp();
		P.moveRight();
		P.pickUp();
		assertTrue(treasureGoal.isComplete());
		assertTrue(AndGoalCG.isComplete());
	}
	
	
	@Test
	void TestComplexGoalWithExit() {
		// If this is the last goal, and exit is not a goal, the dungeon is considered complete.
		D.addEntity(P);
		D.setPlayer(P);
		D.addEntity(B1);
		D.addEntity(B2);
		D.addEntity(FS);
		D.addEntity(E);
		D.addExit(E);
		D.addFloorSwitch(FS);
		
		BaseGoal boulderGoal = new BaseGoal(D, new BoulderGoalStrategy(D), "boulder");
		BaseGoal exitGoal = new BaseGoal(D, new ExitGoalStrategy(D), "exit");
		ComplexGoal AndGoalCG = new ComplexGoal(new AndCompletion());
		AndGoalCG.addGoal(boulderGoal);
		AndGoalCG.addGoal(exitGoal);
		D.setGoal(AndGoalCG);
		assertFalse(AndGoalCG.isComplete());
		
		P.moveRight();
		P.moveRight();
		assertTrue(exitGoal.isComplete());
		assertFalse(AndGoalCG.isComplete());
		
		P.moveLeft();
		P.moveLeft();
		P.moveDown();
		P.interactWithEntity();
		assertTrue(boulderGoal.isComplete());
		System.out.println(E.getNumTurns());

		assertFalse(exitGoal.isComplete());
		assertFalse(AndGoalCG.isComplete());
		
		P.moveRight();
		P.moveRight();
		assertTrue(exitGoal.isComplete());
		assertTrue(AndGoalCG.isComplete());
		
		P.pickUp();
		assertFalse(exitGoal.isComplete());
		assertFalse(AndGoalCG.isComplete());
	}
		
	
	@Test
	void TestComplexGoalWithOr() {
		D.addEntity(P);
		D.setPlayer(P);
		D.addEntity(B1);
		D.addEntity(FS);
		D.addEntity(T3);
		D.addEntity(T4);
		D.addEntity(E);
		D.addFloorSwitch(FS);
		D.addExit(E);
		
		BaseGoal boulderGoal = new BaseGoal(D, new BoulderGoalStrategy(D), "boulder");
		BaseGoal exitGoal = new BaseGoal(D, new ExitGoalStrategy(D), "exit");
		BaseGoal treasureGoal = new BaseGoal(D, new TreasureGoalStrategy(D), "treasure");
		
		ComplexGoal AndGoalCG = new ComplexGoal(new AndCompletion());
		ComplexGoal OrGoalCG = new ComplexGoal(new OrCompletion());
		AndGoalCG.addGoal(boulderGoal);
		OrGoalCG.addGoal(exitGoal);
		OrGoalCG.addGoal(treasureGoal);
		AndGoalCG.addGoal(OrGoalCG);
		
		P.interactWithEntity();
		assertTrue(boulderGoal.isComplete());
		assertFalse(AndGoalCG.isComplete());
		
		P.moveRight();
		P.moveRight();
		assertTrue(OrGoalCG.isComplete());
		assertTrue(AndGoalCG.isComplete());
		P.pickUp();
		P.moveLeft();
		assertFalse(exitGoal.isComplete());
		assertFalse(AndGoalCG.isComplete());

		P.pickUp();
		
		assertTrue(treasureGoal.isComplete());
		
		assertTrue(AndGoalCG.isComplete());
	}
}
