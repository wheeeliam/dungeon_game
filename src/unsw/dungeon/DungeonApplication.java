package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DungeonApplication extends Application {
	
	private Stage primaryStage;
	Scene start, instructions, levels, game, fail, victory; 
	
	String gameFile;
	
    @Override
    public void start(Stage primaryStage) throws IOException {
    	this.primaryStage = primaryStage;
        primaryStage.setTitle("Dungeon Game");
        
        // Start scene
        HBox startButtons = new HBox();
        startButtons.setSpacing(10.0);
        Button startToDungeons = new Button("Start");
        Button startToInstructions = new Button("Instructions");
        startToDungeons.setAlignment(Pos.BOTTOM_CENTER);
        startToInstructions.setAlignment(Pos.BOTTOM_CENTER);
        startButtons.setAlignment(Pos.CENTER);


        
        startToDungeons.setOnAction(e -> primaryStage.setScene(levels));
        startToInstructions.setOnAction(e -> primaryStage.setScene(instructions));
        startButtons.getChildren().addAll(startToDungeons, startToInstructions);

        // ImagePattern pattern = new ImagePattern(new Image("/DungeonStartBG.jpg"));
        GridPane startLayout = new GridPane();
        startLayout.setAlignment(Pos.CENTER);
        startLayout.setHgap(10);
        startLayout.setVgap(12);
        
        startLayout.add(startButtons, 0, 2, 2, 1);
        
        
        start = new Scene(startLayout, 824, 512);
        
        // instructions scene
        Button instructionsToStart = new Button("Back");
        instructionsToStart.setOnAction(e->primaryStage.setScene(start));
        
        String instructionsText = "HOW TO PLAY \n"
        		+ "Move the character using the arrow keys. \n"
        		+ "Pick up items when standing on them using S key. \n"
        		+ "Interact with other elements of the dungeon when facing them using A key. \n"
        		+ "The list of elements in the dungeon can be found here: \n"
        		+ "	1. Wall (Square of bricks): You cannot walk through these.\n"
        		+ "	2. Boulder (Circular rock): You must push these onto floor switches. \n"
        		+ "	3. Floor Switches (Grey Plate): You must push the boulders onto these. \n"
        		+ "	4. Portals (Blue circle): These will transport you to a corresponding portal. \n"
        		+ "	5. Key: A key will open its corresponding door. You can only carry one key at a time.\n"
        		+ "	6. Door: You can only walk through these when they are opened. \n"
        		+ "	7. Sword: A sword will kill a normal enemy when the player uses it while facing them in an adjacent square. \n"
        		+ "	8. Treasure: These should be picked up to complete the treasure goal. \n"
        		+ "	9. Blue Potion: When picked up, the player will turn invincible (enemies will run away and will die on collision) \n"
        		+ "	10. Normal Enemy (Wizard): These will follow you and, upon collision, will kill you. \n"
        		+ "Extension Elements: \n"
        		+ "	11. Green Potion: When picked up, the player will turn invisible (normal enemies cannot see you but ghosts can)\n"
        		+ "	12. Ghost Enemy: These enemies can walk through walls and cannot be killed with a sword. \n"
        		+ "	13. Bomb: After a predetermined random number of steps, the bomb will kill any player or enemy within 1 aquare of it. \n"
        		+ "Your inventory of picked up items can be found on the left of the dungeon. \n"
        		+ "Win dungeon by completing the goals specified on the left. \n"
        		+ "There are 4 dungeon goals:\n"
        		+ "	1. boulders: all switches (the grey rectangle) must be covered by a boulder\n"
        		+ "	2. treasure: all treasure (gold) must be collected by the player\n"
        		+ "	3. enemies: all enemies must be killed\n"
        		+ "	4. exit: this must always be completed last. The player must reach the exit (the diamond exit)\n"
        		+ "Have fun!";
        Text instruct = new Text();
        instruct.setText(instructionsText);
        
        VBox instructionsButtons = new VBox(20);
        
        instructionsButtons.getChildren().addAll(instructionsToStart, instruct);
        
        GridPane instructionsLayout = new GridPane();
        instructionsLayout.setAlignment(Pos.CENTER);
        instructionsLayout.setHgap(10);
        instructionsLayout.setVgap(12);
        
        instructionsLayout.add(instructionsButtons, 0, 2, 2, 1);
        
        instructions = new Scene(instructionsLayout, 824, 512);
        
        
        
        
        // game selection scene
        Button levelsToStart = new Button("Back");
        levelsToStart.setOnAction(e->primaryStage.setScene(start));

        
        Button level1 = new Button("Basic 1");
        Button level2 = new Button("Basic 2");
        Button level3 = new Button("Intermediate 1");
        Button level4 = new Button("Intermediate 2");
        Button level5 = new Button("Advanced 1");
        Button level6 = new Button("Advanced 2");
        Button level7 = new Button("CSE Demo");

        
        level1.setOnAction(e->loadLevel("/basic1.json"));
        level2.setOnAction(e->loadLevel("/basic2.json"));
        level3.setOnAction(e->loadLevel("/intermediate1.json"));
        level4.setOnAction(e->loadLevel("/intermediate2.json"));
        level5.setOnAction(e->loadLevel("/advanced1.json"));
        level6.setOnAction(e->loadLevel("/advanced2.json"));
        level7.setOnAction(e->loadLevel("/marking.json"));

        
        
        
        HBox basicLevelsButtons = new HBox();
        startButtons.setSpacing(10.0);
        
        level1.setAlignment(Pos.BOTTOM_CENTER);
        level2.setAlignment(Pos.BOTTOM_CENTER);
        basicLevelsButtons.setAlignment(Pos.CENTER);
        
        basicLevelsButtons.getChildren().addAll(level1, level2);
        
        HBox intermediateLevelsButtons = new HBox();
        startButtons.setSpacing(10.0);
        
        level3.setAlignment(Pos.BOTTOM_CENTER);
        level4.setAlignment(Pos.BOTTOM_CENTER);
        intermediateLevelsButtons.setAlignment(Pos.CENTER);
        
        intermediateLevelsButtons.getChildren().addAll(level3, level4);

        
        HBox advancedLevelsButtons = new HBox();
        startButtons.setSpacing(10.0);
        
        level5.setAlignment(Pos.BOTTOM_CENTER);
        level6.setAlignment(Pos.BOTTOM_CENTER);
        advancedLevelsButtons.setAlignment(Pos.CENTER);
        
        advancedLevelsButtons.getChildren().addAll(level5, level6, level7);

        
        GridPane levelsLayout = new GridPane();
        levelsLayout.setAlignment(Pos.CENTER);
        levelsLayout.setHgap(10);
        levelsLayout.setVgap(12);
        levelsLayout.add(basicLevelsButtons, 0, 2);
        levelsLayout.add(intermediateLevelsButtons, 1, 2);
        levelsLayout.add(advancedLevelsButtons, 2, 2);
        levelsLayout.add(levelsToStart, 3, 2);

       
        levels = new Scene(levelsLayout, 824, 512);
        
        
        // Victory
        
        Button victoryToLevels = new Button("Choose another level");
        victoryToLevels.setOnAction(e->primaryStage.setScene(levels));
        Button victoryToRestart = new Button("Retry");
        victoryToRestart.setOnAction(e->reloadLevel());
        
        
        
        String victoryText = "Congrats! You won! \n"
        		+ "Select from the options. \n";
        Text vic = new Text();
        vic.setText(victoryText);
        
        HBox victoryOptions = new HBox();
        victoryOptions.setSpacing(10.0);
        
        victoryToLevels.setAlignment(Pos.BOTTOM_CENTER);
        victoryToRestart.setAlignment(Pos.BOTTOM_CENTER);
        victoryOptions.setAlignment(Pos.CENTER);
        
        victoryOptions.getChildren().addAll(victoryToLevels, victoryToRestart);
        
        
        VBox victoryStuff = new VBox(20);
        
        victoryStuff.getChildren().addAll(vic, victoryOptions);
        
        GridPane victoryLayout = new GridPane();
        victoryLayout.setAlignment(Pos.CENTER);
        victoryLayout.setHgap(10);
        victoryLayout.setVgap(12);
        
        victoryLayout.add(victoryStuff, 0, 2, 2, 1);
        
        victory = new Scene(victoryLayout, 824, 512);
        
        // Fail Scene
        
        Button failToLevels = new Button("Choose another level");
        failToLevels.setOnAction(e->primaryStage.setScene(levels));
        Button failToRestart = new Button("Retry");
        failToRestart.setOnAction(e->reloadLevel());
        
        
        
        String failText = "Unfortunately, you did not win. Would you like to try again? \n"
        		+ "Select from the options. \n";
        Text fa = new Text();
        fa.setText(failText);
        
        HBox failOptions = new HBox();
        failOptions.setSpacing(10.0);
        
        failToLevels.setAlignment(Pos.BOTTOM_CENTER);
        failToRestart.setAlignment(Pos.BOTTOM_CENTER);
        failOptions.setAlignment(Pos.CENTER);
        
        failOptions.getChildren().addAll(failToLevels, failToRestart);
        
        
        VBox failStuff = new VBox(20);
        
        failStuff.getChildren().addAll(fa, failOptions);
        
        GridPane failLayout = new GridPane();
        failLayout.setAlignment(Pos.CENTER);
        failLayout.setHgap(10);
        failLayout.setVgap(12);
        
        failLayout.add(failStuff, 0, 2, 2, 1);
        
        fail = new Scene(failLayout, 824, 512);
        

        

        
        // Always start off on start scene
        primaryStage.setScene(start);
        primaryStage.show();

    }
    
    public Scene loadGame() throws IOException {
    	DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(gameFile, this);

        DungeonController controller = dungeonLoader.loadController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        root.requestFocus();
        return scene;
    }
    
    public void loadLevel(String filename) {
    	gameFile = filename;
    	try {
			primaryStage.setScene(loadGame());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    }
    
    public void accessLevels () {
    	this.primaryStage.setScene(levels);
    }
    
    public void reloadLevel() {
    	loadLevel(gameFile);
    }
    
    public void setVictoryScene () {
    	primaryStage.setScene(victory);
    }
    
    public void setFailedScene () {
    	primaryStage.setScene(fail);

    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    

}
