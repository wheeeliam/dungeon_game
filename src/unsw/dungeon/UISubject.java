package unsw.dungeon;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface UISubject {
	public void notifyObservers(ImageView view, Image image);
	public void removeObservers(UIObserver observer);
	public void addObservers(UIObserver observer);
}
