package unsw.dungeon;

public class Potion extends PickUpEntity {
	
	private PotionAbility potionAbility;
	
    public Potion(int x, int y, Dungeon dungeon, PotionAbility potionAbility) {
        super(x, y, dungeon);
//        setPlayerPickUp(new PlayerPickUpPotion());
        this.potionAbility = potionAbility;
    }
    
    public void activate(Player player) {
    	this.potionAbility.activate(player);
    }
    
    @Override
    public boolean pickUp(Player player) {
    	activate(player);
    	player.notifyObservers();
    	return true;
    }
    
    public PotionAbility getAbility() {
    	return this.potionAbility;
    }
}