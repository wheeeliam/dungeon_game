package unsw.dungeon;

public class EnemyRetreatState implements EnemyState {
	@Override
	public void optimalMove(Player player, Enemy enemy) {
		if (enemy.getX()==player.getX() && enemy.getY() == player.getY()){
			return;
		} else if (enemy.getX() == player.getX()) {
			if (enemy.getY() > player.getY()) {
				enemy.move(enemy.getX(), enemy.getY() + 1);
			} else {
				enemy.move(enemy.getX(), enemy.getY() - 1);
			}
		} else if (enemy.getY() == player.getY()) {
			if (enemy.getX() > player.getX()) {
				enemy.move(enemy.getX() + 1, enemy.getY());
			} else {
				enemy.move(enemy.getX() - 1, enemy.getY());
			}
		} else if (enemy.getX() > player.getX()) {
			if (enemy.getY() > player.getY()) {
				if (enemy.canMove(enemy.getX() + 1, enemy.getY())) {
					enemy.move(enemy.getX() + 1, enemy.getY());
				} else { 
					enemy.move(enemy.getX(), enemy.getY() + 1);
				}
			} else {
				if (enemy.canMove(enemy.getX() + 1, enemy.getY())) {
					enemy.move(enemy.getX() + 1, enemy.getY());
				} else { 
					enemy.move(enemy.getX(), enemy.getY() - 1);
				}
			}
		} else if (enemy.getX() < player.getX()) {
			if (enemy.getY() > player.getY()) {
				if (enemy.canMove(enemy.getX() - 1, enemy.getY())) {
					enemy.move(enemy.getX() - 1, enemy.getY());
				} else { 
					enemy.move(enemy.getX(), enemy.getY() + 1);
				}
			} else {
				if (enemy.canMove(enemy.getX() - 1, enemy.getY())) {
					enemy.move(enemy.getX() - 1, enemy.getY());
				} else { 
					enemy.move(enemy.getX(), enemy.getY() - 1);
				}
			}	
		}
	}
}