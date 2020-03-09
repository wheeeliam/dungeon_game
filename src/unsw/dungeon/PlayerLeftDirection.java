package unsw.dungeon;

public class PlayerLeftDirection implements PlayerDirection {
	
	@Override
	public int getAdjacentX(Entity entity) {
		return entity.getX() - 1;
	}
	@Override
	public int getAdjacentY(Entity entity) {
		return entity.getY();
	}
}