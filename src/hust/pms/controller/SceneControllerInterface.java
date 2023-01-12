package hust.pms.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public interface SceneControllerInterface {
	public void closeSceneWithStageRelatedButton(Button button);
	public void toScene(ActionEvent event, String fxml);
	public void toParallelScene(String fxml);
	public void toAlertWithTitleAndContent(String title, String contentText);
	//public void showComfirmationAlertWithoutHeaderText(String title, String headerText, String contentText);
}
