package hust.pms.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.Label;

public class AlertForm {
	
    public void showAlertWithHeaderText(String title, String headerText, String contentText) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
 
        alert.showAndWait();
    }
 
    public void showAlertWithDefaultHeaderText(String title, String contentText) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
 
        // alert.setHeaderText("Results:");
        alert.setContentText(contentText);
 
        alert.showAndWait();
    }
 
    public void showAlertWithoutHeaderText(String title, String contentText) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
 
        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(contentText);
 
        alert.showAndWait();
    }
    
    public void labelAlert(Label labelError, String labelText) {
    	labelError.setText(labelText);
    }
}
