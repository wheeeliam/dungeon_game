package unsw.dungeon;

public class PlayerOpenDoorInteraction implements PlayerInteraction {
	public void interact(Player player, Entity entity) {
		
		if (player.hasKey()) {
			Door door = (Door) entity;
			if (door.isCorrespondingKey(player.getKey())) {
				door.open();
				player.removeKey();
			}
		}
	}
}