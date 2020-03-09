package unsw.dungeon;

public class PotionAbilityGhost implements PotionAbility {
	private int time = 20;
	
	public void activate(Player player) {
		player.setMovement(new MovementInvisible());
		player.setPlayerState(new PlayerStateInvisible(time));
		player.setStepsLeft(time);
		player.setInvisibleImage(true);
		player.setPlayerStateImage(2);
	}
}