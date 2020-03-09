package unsw.dungeon;

public class PlayerEnemyCollision implements PlayerCollision {
	public void collide(Entity colliding, Entity collideOnto) {
		if (colliding instanceof Player && collideOnto instanceof Enemy) {
			Player player = (Player) colliding;
			Enemy enemy = (Enemy) collideOnto;
			player.collideWithEnemy(enemy);

		} else if (colliding instanceof Enemy && collideOnto instanceof Player) {
			Player player = (Player) collideOnto;
			Enemy enemy = (Enemy) colliding;
			player.collideWithEnemy(enemy);

		}
	}
}