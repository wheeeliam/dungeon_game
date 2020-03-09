package unsw.dungeon;

public class Portal extends Entity{
	
    private int id;
	
	public Portal(int x, int y, int id, Dungeon dungeon) {
		super(x,y, dungeon);
		this.id = id;
		setCollision(new EntityPortalCollision());
	}

	
	public Portal getCorrespondingPortal() {
		for (Entity e : getDungeon().getEntities()) {
			if (e instanceof Portal) {
				if (((Portal) e).getID() == id && !e.equals(this)) {
					return (Portal) e;
				}
			}
		}
		return null;
	}
	
	public int getCorrespondingX() {
		Portal correspondingPortal = getCorrespondingPortal();
		if (correspondingPortal == null) {
			return this.getX();
		} else {
			return correspondingPortal.getX();
		}
	}
	
	public int getCorrespondingY() {
		Portal correspondingPortal = getCorrespondingPortal();
		if (correspondingPortal == null) {
			return this.getY();
		} else {
			return correspondingPortal.getY();
		}
	}
	
	
	public void teleport(Entity entity) {
		if (entity.canMove(getCorrespondingX(), getCorrespondingY())) {
			entity.setPosition(getCorrespondingX(), getCorrespondingY());
		}
	}
	
    public int getID() {
    	return this.id;
    }  
    
	@Override
    public boolean isPickUp() {
    	return false;
    }
    
	@Override
    public boolean isBlocking() {
    	return false;
    }
	
}
