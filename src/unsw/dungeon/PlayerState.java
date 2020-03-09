package unsw.dungeon;

interface PlayerState {
	public boolean isInvincible();
	public boolean isInvisible();
	public void collideWithEnemy(Player player, Enemy enemy);
	public void progress(Player player);
	public int potionLeft();
}