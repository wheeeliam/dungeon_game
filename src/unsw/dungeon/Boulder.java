package unsw.dungeon;

public class Boulder extends Entity {
	
	public Boulder(int x, int y, Dungeon dungeon) {
		super(x,y, dungeon);
		setMovement(new BoulderMovement());
		setInteraction(new PlayerPushInteraction());
	}
	
	@Override
	public boolean isPickUp() {
		return false;
	}
	
	@Override
	public boolean isBlocking() {
		return true;
	}
	
	public void interact(Player player) {
		getInteraction().interact(player, this);
	}

	
}
