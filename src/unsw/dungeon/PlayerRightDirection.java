package unsw.dungeon;

public class PlayerRightDirection implements PlayerDirection {
	
//	@Override
//	public void interactWithEntity() {
//		
//	}
	@Override
	public int getAdjacentX(Entity entity) {
		System.out.println(entity.getX() + 1);
		return entity.getX() + 1;
	}
	@Override
	public int getAdjacentY(Entity entity) {
		System.out.println(entity.getY());
		return entity.getY();
	}
}