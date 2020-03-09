package unsw.dungeon;

public class PlayerPushInteraction implements PlayerInteraction {
	@Override
	public void interact(Player player, Entity entity) {
		// Player is left of boulder
		if (player.getX() == entity.getX() - 1 && player.getY() == entity.getY()) {
			entity.move(entity.getX() + 1, entity.getY());		
		// Player is right of boulder
		} else if (player.getX() == entity.getX() + 1 && player.getY() == entity.getY()) {
			entity.move(entity.getX() - 1, entity.getY());
		// Player is above boulder	
		} else if (player.getX() == entity.getX() && player.getY() == entity.getY() - 1) {
			entity.move(entity.getX(), entity.getY() + 1);
		// Player is below boulder
		} else if (player.getX() == entity.getX() && player.getY() == entity.getY() + 1) {
			entity.move(entity.getX(), entity.getY() - 1);
		}
	}
}