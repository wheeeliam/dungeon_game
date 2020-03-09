package unsw.dungeon;

public class PlayerExitCollision implements PlayerCollision {
	public void collide(Entity colliding, Entity collideOnto) {
		if (colliding instanceof Player && collideOnto instanceof Exit) {
			Exit exit = (Exit) collideOnto;
			exit.notifyObservers();
			colliding.getDungeon().checkGoal();
		}
	}
}