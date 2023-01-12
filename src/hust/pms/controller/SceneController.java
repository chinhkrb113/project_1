package hust.pms.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import hust.pms.view.AlertForm;
import hust.pms.view.admin.ViewParkingForm;
import hust.pms.view.admin.ViewStaffForm;
import hust.pms.view.sadmin.AddRoleForm;
import hust.pms.view.sadmin.ViewAdminForm;
import hust.pms.view.sadmin.ViewCompanyForm;
import hust.pms.view.sadmin.RoleForm;
import hust.pms.view.sadmin.ViewSAdminForm;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SceneController implements SceneControllerInterface {

	public ViewStaffForm adminStaffForm;
	public RoleForm sAdminRoleForm;
	public AddRoleForm sAdminAddRoleForm;
	public ViewCompanyForm sAdminCompanyForm;
	public ViewSAdminForm sAdminForm;
	public ViewAdminForm sadAdminForm;
	public ViewParkingForm aParkingForm;
	
	public static int confirmationValue;
	
	private static final SceneController INSTANCE = new SceneController();
	
	private SceneController() {}
	
	public static SceneController getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void closeSceneWithStageRelatedButton(Button button) {
		Stage stage = (Stage) button.getScene().getWindow();
		stage.close();
	}

	@Override
	public void toScene(ActionEvent event, String fxml) {
		try {
			URL url_fxml = Paths.get(fxml).toUri().toURL();
			Parent root = FXMLLoader.load(url_fxml);
			//Scene newScene = new Scene(root2);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setTitle("Parking System");
			stage.setScene(new Scene(root));
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void toParallelScene(String fxml) {
		try {
			URL url_fxml = Paths.get(fxml).toUri().toURL();
			
			Parent anotherRoot = FXMLLoader.load(url_fxml);
			
			Stage anotherStage = new Stage();
			Scene anotherScene = new Scene(anotherRoot);
			anotherStage.setTitle("Parking System");
			anotherStage.setScene(anotherScene);
			anotherStage.centerOnScreen();
			anotherStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void toAlertWithTitleAndContent(String title, String contentText) {
		Platform.runLater(() -> {
			AlertForm af = new AlertForm();
			af.showAlertWithoutHeaderText(title, contentText);
		});
	}
	
	/*
	@Override
	public void showComfirmationAlertWithoutHeaderText(String title, String headerText, String contentText) {
		Platform.runLater(() -> {	
			AlertForm af = new AlertForm();
			af.showComfirmationAlertWithoutHeaderText(title, headerText, contentText);
		});
		
	}
	
	@Override
	public int showComfirmationAlertWithoutHeaderText(String title, String contentText) {
		final CountDownLatch latch = new CountDownLatch(1);
		final IntegerProperty intProp = new SimpleIntegerProperty();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				intProp.set(SceneController.getInstance().showComfirmationAlertWithoutHeaderText(title, contentText));
				latch.countDown();
			}
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return intProp.get();
	}
	*/

}
