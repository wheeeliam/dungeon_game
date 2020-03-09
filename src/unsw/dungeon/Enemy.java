package unsw.dungeon;

import java.util.ArrayList;

import unsw.dungeon.Observer;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 * 
 */
public class Enemy extends Entity implements Observer, Subject {
	private Player player;
	private EnemyState state;
	private ArrayList<Observer> observers;
	
	
	public Enemy(int x, int y, Dungeon dungeon) {
		super(x,y, dungeon);
		this.player = dungeon.getPlayer();
		dungeon.addEnemy(this);
		setMovement(new NormalMovement());
		setInteraction(new PlayerEnemyInteraction());
		setCollision(new PlayerEnemyCollision());
		this.state = new EnemyApproachState();
		if (this.player != null) {
			this.player.addObserver(this);
		}
		observers = new ArrayList<>();
		getDungeon().addObserver(this);
		
	}
	
	public void autoMove() {
		if (getDungeon().getPlayer() != null) {
			if (this.player == null) {
				this.player = getDungeon().getPlayer();
				this.player.addObserver(this);
			}
			state.optimalMove(player, this);
		}
		
	}
	
	
	@Override
	public void update(Subject s) {
		if (s.equals(this.player)) {
			changeState();
		}
		if (s.equals(getDungeon())) {
			autoMove();
		}
	}
	
	public void changeState() {
		if (player.isInvincible()) {
			this.state = new EnemyRetreatState();
		} else if (player.isInvisible()){
			this.state = new EnemyFrozenState();
		} else {
			this.state = new EnemyApproachState();
		}
	}
		
	public Player getPlayer() {
		return player;
	}

	public void setState(EnemyState state) {
		this.state = state;
	}
	
	public void removePlayer(Player player) {
		if (player.equals(this.player)) this.player = null;
		this.state = new EnemyFrozenState();
	}
	
    @Override
    public boolean isPickUp() {
    	return false;
    }
    
    @Override
    public boolean isBlocking() {
    	return false;
    }

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
		
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
		
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}
	
	public void getsKilled() {
		notifyObservers();
		getDungeon().removeEntity(this);
		getDungeon().removeEnemy(this);
		System.out.println("Made it to gets killed well");
		if (!(player == null)) player.removeObserver(this);
	}
	
	public void getsHit() {
		getsKilled();
	}
}