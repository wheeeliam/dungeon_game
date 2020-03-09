package unsw.dungeon;

public class PlayerStateInvisible implements PlayerState {
	private int stepsLeft;
	
	public PlayerStateInvisible(int time) {
		this.stepsLeft = time + 1; // after picking up, it will decrement by 1 even though player has not moved
	}
	
	@Override
	public boolean isInvincible() {
		return false;
	}
	
	@Override
	public boolean isInvisible() {
		return true;
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
			player.setInvisibleImage(false);
			player.setPlayerStateImage(0);
			return;
		}
	}
	
	@Override
	public int potionLeft() {
		return this.stepsLeft;
	}
}