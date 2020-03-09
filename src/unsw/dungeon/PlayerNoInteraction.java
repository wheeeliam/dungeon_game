package unsw.dungeon;

public class PlayerNoInteraction implements PlayerInteraction {
	public void interact(Player player, Entity entity) {
		return;
	}
}