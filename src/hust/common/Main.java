package hust.common;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
//import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.net.URL;
import java.nio.file.Paths;

import hust.pms.controller.SceneController;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			URL url_fxml = Paths.get(Navigator.FXML_LOGIN).toUri().toURL();
			//URL url_logo = Paths.get(Constants.IMG_PARKING).toUri().toURL();
			Parent root = FXMLLoader.load(url_fxml);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
//			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(Navigator.IMG_PARKING)));
			primaryStage.setTitle("PMS");
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.resizableProperty().setValue(Boolean.FALSE);
			primaryStage.setOnCloseRequest(event -> {
				SceneController.getInstance().toAlertWithTitleAndContent("Warning", "Please click Logout or Close Button if it's avaiable instead of X-closing button!");
				event.consume();
			});
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			SceneController.getInstance().toAlertWithTitleAndContent("Error", "Something went wrong");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
