package unsw.dungeon;

public class PlayerInvincibleState implements PlayerState {
	private int stepsLeft;
	
	public PlayerInvincibleState(int time) {
		this.stepsLeft = time + 1; // after picking up, it will decrement by 1 even though player has not moved
	}
	
	@Override
	public boolean isInvincible() {
		return true;
	}
	
	@Override
	public boolean isInvisible() {
		return false;
	}
	
	@Override
	public void collideWithEnemy(Player player, Enemy enemy) {
		enemy.getsKilled();
	}
	
	@Override
	public void progress(Player player) {
		this.stepsLeft--;
		if (player.getStepsLeft().intValue() >= 1) { 
			player.setStepsLeft(player.getStepsLeft().intValue() - 1);
		}
		if (stepsLeft == 0) {
			player.setNormal();
			player.setInvincibleImage(false);
			player.setPlayerStateImage(0);
			return;
		}
	}
	
	@Override
	public int potionLeft() {
		return this.stepsLeft;
	}
}