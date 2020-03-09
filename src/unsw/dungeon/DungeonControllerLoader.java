package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader implements UISubject {

    private List<ImageView> entities;
    private List<UIObserver> UIobservers;
    
    private DungeonApplication da;
    
    //Images
    private Image playerImage;
    private Image playerUpImage;
    private Image playerDownImage;
    private Image playerLeftImage;
    private Image playerRightImage;
    private Image wallImage;
    private Image boulderImage;
    private Image exitImage;
    private Image treasureImage;
    private Image doorImage;
    private Image openDoorImage;
    private Image keyImage;
    private Image switchImage;
    private Image portalImage;
    private Image enemyImage;
    private Image swordImage;
    private Image potionImage;
    private Image invisPotionImage;
    private Image ghostImage;
    private Image bombImage;
    private Image invisibleUp;
    private Image invisibleDown;
    private Image invisibleLeft;
    private Image invisibleRight;
    private Image invincibleUp;
    private Image invincibleDown;
    private Image invincibleLeft;
    private Image invincibleRight;
//    private Image explosionImage;



    public DungeonControllerLoader(String filename, DungeonApplication da)
            throws FileNotFoundException {
        super(filename);
        
        this.da = da;
        entities = new ArrayList<>();
        playerImage = new Image("/human_new.png");
        playerUpImage = new Image("/player_up.png");
        playerDownImage = new Image("/player_down.png");
        playerLeftImage = new Image("/player_left.png");
        playerRightImage = new Image("/player_right.png");
        ghostImage = new Image("/ghost.png");
        bombImage = new Image("/bomb.png");
        wallImage = new Image("/brick_brown_0.png");
        boulderImage = new Image("/boulder.png");
        exitImage = new Image("/exit.png");
        treasureImage = new Image("/gold_pile.png");
        doorImage = new Image("/closed_door.png");
        openDoorImage = new Image("/open_door.png");
        keyImage = new Image("/key.png");
        switchImage = new Image("/pressure_plate.png");
        portalImage = new Image("/portal.png");
        enemyImage = new Image("/gnome.png");
        swordImage = new Image("/greatsword_1_new.png");
        potionImage = new Image("/brilliant_blue_new.png");
        invisPotionImage = new Image("/bubbly.png");
        
        UIobservers = new ArrayList<>();
        invisibleUp = new Image("/invisible_up.png");
        invisibleDown = new Image("/invisible_down.png");
        invisibleLeft = new Image("/invisible_left.png");
        invisibleRight = new Image("/invisible_right.png");
        invincibleUp = new Image("/invincible_up.png");
        invincibleDown = new Image("/invincible_down.png");
        invincibleLeft = new Image("/invincible_left.png");
        invincibleRight = new Image("/invincible_right.png");  
//        explosionImage = new Image("/explosion.png");
    }

    @Override
    public void onLoad(Player player) {
        ImageView view = new ImageView(playerDownImage);
        addEntity(player, view);
        trackPlayerState(player, view);
        trackPlayerImage(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }
    
    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }
    
    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
    }
    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorImage);
        addEntity(door, view);
        trackDoorImage(door, view);
    }
    
    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
    }
    @Override
    public void onLoad(FloorSwitch floorSwitch) {
        ImageView view = new ImageView(switchImage);
        addEntity(floorSwitch, view);
    }
    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }
    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
    }
    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
    }
    @Override
    public void onLoad(Potion potion) {
    	ImageView view = null;
    	if (potion.getAbility() instanceof PotionAbilityInvincible) {
    		view = new ImageView(potionImage);
    	} else if (potion.getAbility() instanceof PotionAbilityGhost) {
    		view = new ImageView(invisPotionImage);
    	}
        addEntity(potion, view);
    }
    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }
    @Override
    public void onLoad(Bomb bomb) {
        ImageView view = new ImageView(bombImage);
        addEntity(bomb, view);
    }
    @Override
    public void onLoad(Ghost ghost) {
        ImageView view = new ImageView(ghostImage);
        addEntity(ghost, view);
    }
    
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        trackOnMap(entity, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }
    
    private void trackOnMap(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.getOnMap().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, 
					Boolean oldValue, Boolean newValue) {
		    	if (newValue == false) {
		    		node.visibleProperty().bind(observable);
		    	}
			}
    	});
    }
    
    private void trackDoorImage(Door door, Node node) {
        GridPane.setColumnIndex(node, door.getX());
        GridPane.setRowIndex(node, door.getY());
        door.getClosedImage().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, 
					Boolean oldValue, Boolean newValue) {
		    	if (newValue == false) {
		    		notifyObservers((ImageView) node, openDoorImage);
		    	}
			}
    	});
    }
    
    private void trackPlayerState(Player player, Node node) {
        GridPane.setColumnIndex(node, player.getX());
        GridPane.setRowIndex(node, player.getY());
        player.getPlayerState().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				if (newValue.intValue() == 0) {
					if (player.getDirectionNumber() == 0) notifyObservers((ImageView) node, playerDownImage);
					else if (player.getDirectionNumber() == 1) notifyObservers((ImageView) node, playerLeftImage);
					else if (player.getDirectionNumber() == 2) notifyObservers((ImageView) node, playerUpImage);
					else if (player.getDirectionNumber() == 3) notifyObservers((ImageView) node, playerRightImage);
				} else if (newValue.intValue() == 1) {
					if (player.getDirectionNumber() == 0) notifyObservers((ImageView) node, invincibleDown);
					else if (player.getDirectionNumber() == 1) notifyObservers((ImageView) node, invincibleLeft);
					else if (player.getDirectionNumber() == 2) notifyObservers((ImageView) node, invincibleUp);
					else if (player.getDirectionNumber() == 3) notifyObservers((ImageView) node, invincibleRight);
				} else if (newValue.intValue() == 2) {
					if (player.getDirectionNumber() == 0) notifyObservers((ImageView) node, invisibleDown);
					else if (player.getDirectionNumber() == 1) notifyObservers((ImageView) node, invisibleLeft);
					else if (player.getDirectionNumber() == 2) notifyObservers((ImageView) node, invisibleUp);
					else if (player.getDirectionNumber() == 3) notifyObservers((ImageView) node, invisibleRight);
				}
			}	
		});
    }
    
    private void trackPlayerImage(Player player, Node node) {
        GridPane.setColumnIndex(node, player.getX());
        GridPane.setRowIndex(node, player.getY());
        player.getDirectionImage().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
		    	if (newValue.intValue() == 0) {
		    		notifyObservers((ImageView) node, playerDownImage);
		    	} else if (newValue.intValue() == 1) {
		    		notifyObservers((ImageView) node, playerLeftImage);
		    	} else if (newValue.intValue() == 2) {
		    		notifyObservers((ImageView) node, playerUpImage);
		    	} else if (newValue.intValue() == 3) {
		    		notifyObservers((ImageView) node, playerRightImage);
		    	} else if (newValue.intValue() == 4) {
		    		notifyObservers((ImageView) node, invincibleDown);
		    	} else if (newValue.intValue() == 5) {
		    		notifyObservers((ImageView) node, invincibleLeft);
		    	} else if (newValue.intValue() == 6) {
		    		notifyObservers((ImageView) node, invincibleUp);
		    	} else if (newValue.intValue() == 7) {
		    		notifyObservers((ImageView) node, invincibleRight);
		    	} else if (newValue.intValue() == 8) {
		    		notifyObservers((ImageView) node, invisibleDown);
		    	} else if (newValue.intValue() == 9) {
		    		notifyObservers((ImageView) node, invisibleLeft);
		    	} else if (newValue.intValue() == 10) {
		    		notifyObservers((ImageView) node, invisibleUp);
		    	} else if (newValue.intValue() == 11) {
		    		notifyObservers((ImageView) node, invisibleRight);
		    	}
			}
    	});
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        DungeonController dc = new DungeonController(load(), entities, this.da);
        addObservers(dc);
        return dc;
    }
    
    

	@Override
	public void notifyObservers(ImageView view, Image image) {
		for (UIObserver o : UIobservers) {
			o.update(view, image);
		}
	}


	@Override
	public void removeObservers(UIObserver observer) {
		UIobservers.remove(observer);
	}

	@Override
	public void addObservers(UIObserver observer) {
		UIobservers.add(observer);		
	}

}
