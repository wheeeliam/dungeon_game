package unsw.dungeon;

public class PlayerUpDirection implements PlayerDirection {
	

	@Override
	public int getAdjacentX(Entity entity) {
		return entity.getX();
	}
	@Override
	public int getAdjacentY(Entity entity) {
		return entity.getY() - 1;
	}
}