package unsw.dungeon;

public class Sword extends PickUpEntity {
	
	private int uses;
	
    public Sword(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        this.uses = 5;
//        setPlayerPickUp(new PlayerPickUpSword());
    }
    
    public void hitEnemy() {
    	this.uses--;
		this.getDungeon().getPlayer().setHitsLeft(this.uses);
    	if (this.uses == 0) {
    		getPlayer().removeSword();
    		removePlayer();
    	}
    }
    
    public int getUses() {
    	return this.uses;
    }
    
    @Override
    public boolean pickUp(Player player) {
    	if (!player.hasSword()) {
			player.setSword(this);
			player.setHitsLeft(5);
			setPlayer(player);
			return true;
		}
		
		//System.out.println("You already have a sword!");
		
    	return false;
    }
}