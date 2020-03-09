package unsw.dungeon;

public class EntityPortalCollision implements PlayerCollision {
	public void collide(Entity colliding, Entity collideOnto) {
		if (collideOnto instanceof Portal) {
			Portal portal = (Portal) collideOnto;
			portal.teleport(colliding);
		}
	}
}