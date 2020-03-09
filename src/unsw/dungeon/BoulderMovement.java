package unsw.dungeon;

public class BoulderMovement implements Movement {
	
	@Override
	public boolean move(int x, int y, Entity entity) {
		if (canMove(x,y, entity.getDungeon())) {
			entity.setPosition(x, y);
			entity.getDungeon().updateFloorSwitches();
			return true;
		}
		return false;
	}
	
	@Override
    public boolean canMove(int x, int y, Dungeon dungeon) {
    	if (x < 0) return false;
    	if (y < 0) return false;
    	if (x >= dungeon.getWidth()) return false;
    	if (y >= dungeon.getHeight()) return false;
    	if (dungeon.hasBlocking(x, y)) return false;
    	if (dungeon.hasPickUp(x, y)) return false;
    	if (dungeon.hasEnemy(x, y)) return false;
    	return true;
    }
    
}