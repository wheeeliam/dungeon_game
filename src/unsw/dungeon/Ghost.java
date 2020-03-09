package unsw.dungeon;

import java.util.ArrayList;

import unsw.dungeon.Observer;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Ghost extends Enemy {
	
	public Ghost(int x, int y, Dungeon dungeon) {
		super(x,y, dungeon);
		setMovement(new MovementInvisible());
		setState(new EnemyApproachState());
	}
	
	
	@Override
	public void changeState() {

		if (getPlayer().isInvincible()) {
			setState(new EnemyRetreatState());
		} else {
			setState(new EnemyApproachState());
		}

	}

	
	@Override
	public void getsHit() {
		return;
	}
}