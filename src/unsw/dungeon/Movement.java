package unsw.dungeon;

public interface Movement {
	public boolean move(int x, int y, Entity entity);
	public boolean canMove(int x, int y, Dungeon dungeon);
}