package unsw.dungeon;

public class PotionAbilityInvincible implements PotionAbility {
	private int time = 10;
	
	public void activate(Player player) {
		player.setMovement(new NormalMovement());
		player.setPlayerState(new PlayerInvincibleState(time));
		player.setStepsLeft(time);
		player.setInvincibleImage(true);
		player.setPlayerStateImage(1);
	}
}