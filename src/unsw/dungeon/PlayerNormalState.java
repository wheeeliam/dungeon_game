package unsw.dungeon;

public class PlayerNormalState implements PlayerState {
	
	@Override
	public boolean isInvincible() {
		return false;
	}
	
	@Override
	public void collideWithEnemy(Player player, Enemy enemy) {
		player.getsKilled();
	}
	
	@Override
	public void progress(Player player) {
		return;
	}
	
	@Override
	public int potionLeft() {
		return 0;
	}

	@Override
	public boolean isInvisible() {
		return false;
	}
}