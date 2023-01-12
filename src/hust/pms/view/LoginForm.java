package hust.pms.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.SQLException;

import hust.common.Constants;
import hust.common.Navigator;
import hust.pms.controller.LoginController;
import hust.pms.controller.SceneController;

public class LoginForm implements LabelHelper {

	SceneController sceneController = SceneController.getInstance();
	LoginController loginController = new LoginController();
	AccountHelper accHelper = new AccountHelper();
	
	@FXML
	private Button loginButton;

	@FXML
	private Button closeButton;

	@FXML
	private TextField tfUsername;

	@FXML
	private PasswordField tfPassword;
	
	@FXML
    private Label labelNotice;

	@FXML
	private void closeButtonAction() {
		sceneController.closeSceneWithStageRelatedButton(closeButton);
	}
	
	@FXML
    private void loginActionButton(ActionEvent event) throws SQLException {
		String username = tfUsername.getText().toString();
		System.out.println("username="+username);
		String password = tfPassword.getText().toString();
		
		
		int fieldCheck = accHelper.textAccountFilled(username, password);
		
		byte checkLogin;
		int checkRole;
		
		if (fieldCheck == 0) {
			setLabel(labelNotice, Pos.CENTER, Color.RED, "Username and Password are required.");
			return;
		} else if (fieldCheck == 1) {
			setLabel(labelNotice, Pos.CENTER, Color.RED, "Username is required.");
			return;
		} else if (fieldCheck == 2) {
			setLabel(labelNotice, Pos.CENTER, Color.RED, "Password is required.");
			return;
		}	
		
		checkLogin = loginController.getLogin(username, password);
		System.out.println("checkLogin="+checkLogin);
		System.out.println("username="+username);
		
		if (checkLogin == 0) {
			setLabel(labelNotice, Pos.CENTER, Color.RED, "Something wrong with login");
		} else if (checkLogin == 1) {
			byte loginStatus = loginController.getLogInStatus(username);
			if (loginStatus == 0) {
				checkRole = loginController.getRoleNoByUserName(username);
				System.out.println("checkRole="+checkRole);
				loginController.toLogInStatus(username);

				if (checkRole == -1) {
					return;
				} else switch (checkRole) {
					case Constants.ROLE_SUPERIOR_ADMIN:
						sceneController.toScene(event, Navigator.FXML_SUPERIOR_ADMINCENTER);
						break;
					case Constants.ROLE_ADMIN: 
						sceneController.toScene(event, Navigator.FXML_ADMINCENTER);
						break;
					case Constants.ROLE_STAFF:
						sceneController.toScene(event, Navigator.FXML_STAFFCENTER);
						break;
					default:
						System.out.println("Nothing");
				} 
			} else {
				setLabel(labelNotice, Pos.CENTER, Color.ORANGE, "You have logged in from another device");
			}		
		} else if (checkLogin == -1) {
			setLabel(labelNotice, Pos.CENTER, Color.RED, "Wrong Username/Password");
		} else if (checkLogin == -2) {
			setLabel(labelNotice, Pos.CENTER, Color.RED, "Wrong Password");
		}
    }

	@Override
	public void setLabel(Label label, Pos pos, Color color, String text) {
		label.setAlignment(pos);
		label.setTextFill(color);
		label.setText(text);
	}

	@Override
	public void clearLabel(Label label) {
		// TODO Auto-generated method stub
		
	}
}
