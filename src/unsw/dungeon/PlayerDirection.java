package unsw.dungeon;

interface PlayerDirection {
	public int getAdjacentX(Entity entity);
	public int getAdjacentY(Entity entity);
}