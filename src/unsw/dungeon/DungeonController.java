package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController implements UIObserver{

    @FXML
    private GridPane squares;
    
    @FXML
    private Button restartButton;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Pane goalsPane;

    @FXML
    private Pane inventoryPane;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;
    
    private DungeonApplication da;
    
    
    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, DungeonApplication da) {
        this.dungeon = dungeon;
        this.dungeon.setController(this);
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.da = da;
    }

    @FXML
    public void fillGoalsPane() {
    	Text text = new Text("Complete the following: \n" + getGoals(dungeon.getGoal()));
    	goalsPane.getChildren().add(text);
    }
    
    @FXML
    public String getGoals(Goal g) {
    	String goalText = "";
    	if (g instanceof BaseGoal) {
    		return (((BaseGoal) g).getName() + "\n");
    	} else if (g instanceof ComplexGoal) {
    		if (((ComplexGoal) g).getCompletion() instanceof AndCompletion) {
				goalText += "\nAND: \n";
			} else if (((ComplexGoal) g).getCompletion() instanceof OrCompletion) {
				goalText += "\nOR: \n";
			}
    		for (Goal go : ((ComplexGoal) g).getGoals()) {
    			goalText += getGoals(go);
    		}
    	}
		return goalText;

    }
    
    @FXML
    public void fillInventoryPane() {    	
    	TextField swordHits = new TextField();
    	swordHits.setText("Sword hits left: 0");
    	swordHits.setDisable(true);
//    	swordHits.disabledProperty();
    	player.getHitsLeft().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, 
					Number oldValue, Number newValue) {
		    	swordHits.setText("Sword hits left: " + newValue.toString());
			}
    	});
    	
    	TextField keyCollected = new TextField();
    	keyCollected.setText("Key collected: false");
    	keyCollected.setLayoutY(35);
//    	keyCollected.setEditable(false);
    	keyCollected.setDisable(true);
    	player.getHasKey().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, 
					Boolean oldValue, Boolean newValue) {
				keyCollected.setText("Key collected: " + newValue.toString());
			}
    	});
    	
    	TextField treasureCollected = new TextField();
    	treasureCollected.setText("Treasures collected: 0");
    	treasureCollected.setLayoutY(70);
//    	treasureCollected.setEditable(false);
    	treasureCollected.setDisable(true);
    	player.getTreasuresCollected().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, 
					Number oldValue, Number newValue) {
				treasureCollected.setText("Treasures collected: " + newValue.toString());
			}
    	});
    	
    	TextField potionState = new TextField();
    	potionState.setText("Potion left: 0");
    	potionState.setLayoutY(105);
//    	potionState.setEditable(false);
    	potionState.setDisable(true);
    	player.getStepsLeft().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, 
					Number oldValue, Number newValue) {
				potionState.setText("Potion left: " + newValue.toString());
			}
    	});
    	inventoryPane.getChildren().add(swordHits);
    	inventoryPane.getChildren().add(keyCollected);
    	inventoryPane.getChildren().add(treasureCollected);
    	inventoryPane.getChildren().add(potionState);
    }
    
    @FXML
    public void initialize() {
        Image ground = new Image("/dirt_0_new.png");

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
        
        for (ImageView entity : initialEntities) {      
            squares.getChildren().add(entity);
        }
        
        Image explosion = new Image("/explosion.png");
        for (Entity e : dungeon.getEntities()) {
        	if (e instanceof Bomb) {
        		for (int x = e.getX() - 1; x <= e.getX() + 1; x++) {
            		for (int y = e.getY() - 1; y <= e.getY() + 1; y++) {
            			if (dungeon.withinDungeon(x, y)) {
            				ImageView explosionImage = new ImageView(explosion);
                			squares.add(explosionImage, x, y);
                			explosionImage.visibleProperty().set(false);
                			
                			((Bomb) e).getHasBombActivated().addListener(new ChangeListener<Boolean>() {
            					@Override
            					public void changed(ObservableValue<? extends Boolean> observable, 
            							Boolean oldValue, Boolean newValue) {
            				    	if (newValue) {
            				    		explosionImage.visibleProperty().bind(observable);
            				    	}
            					}
            		    	});
            			}
            		}
        		}
        	}
        }
        
        fillGoalsPane();
        fillInventoryPane();
    }
    
    /**
     * On keyboard press, get reference to player imageView and change to
     * corresponding player direction sprite
     * @param playerDirectionFile
     */
    
    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            player.moveUp();
            break;
        case DOWN:
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
            break;
        case A:
        	player.interactWithEntity();
        	break;
        case S:
        	player.pickUp();
        	break;
        default:
            break;
        }
    }
    
    /**
     * Click button to go to main menu
     * @param event
     */
    
    @FXML
    void handleMainMenuButton(ActionEvent event) {
    	da.accessLevels();
    }

    /**
     * Click button to restart the level
     * @param event
     */
    
    @FXML
    void handleRestartButton(ActionEvent event) {
    	da.reloadLevel();
    }
    
	@Override
	public void update(ImageView view, Image image) {
		for (Node child : squares.getChildren()) {
			if (child.equals(view)) {
				ImageView correspImage = (ImageView) child;
				correspImage.setImage(image);
				correspImage.toFront();
				break;
			}
		}		
	}
	
	public void setVictoryScene() {
    	this.da.setVictoryScene();
    }
    
    public void setFailedScene() {
    	this.da.setFailedScene();
    }

}


