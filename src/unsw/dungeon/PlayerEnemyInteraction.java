package unsw.dungeon;

public class PlayerEnemyInteraction implements PlayerInteraction {
	@Override
	public void interact(Player player, Entity entity) {
		if (player.hasSword()) {
			if (entity instanceof Enemy) {
				Enemy e = (Enemy) entity;
				e.getsHit();
				player.getSword().hitEnemy();
			}
			
			
		}
	}
}