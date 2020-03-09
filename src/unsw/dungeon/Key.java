package unsw.dungeon;

public class Key extends PickUpEntity {
	
	private int id;
	
    public Key(int x, int y, int id, Dungeon dungeon) {
        super(x, y, dungeon);
        this.id = id;
//        setPlayerPickUp(new PlayerPickUpKey());
    }
    
    public boolean isCorrespondingDoor(Door door) {
    	if (door.getID() == this.id) return true;
    	return false;
    }
    
    public int getID() {
    	return this.id;
    }
    
    @Override
    public boolean pickUp(Player player) {
    	if (!player.hasKey()) {
    		player.setKey(this);
    		setPlayer(player);
    		return true;
    	}
    	return false;
    }

}
