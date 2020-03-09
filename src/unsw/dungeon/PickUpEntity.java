package unsw.dungeon;

public abstract class PickUpEntity extends Entity {
	private Player player;
	
	public PickUpEntity (int x, int y, Dungeon dungeon) {
		super(x,y, dungeon);
		this.player = null;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void removePlayer() {
		this.player = null;
	}
	
	@Override
	public boolean isPickUp() {
		return true;
	}
	
	@Override
	public boolean isBlocking() {
		return false;
	}
	
	
}