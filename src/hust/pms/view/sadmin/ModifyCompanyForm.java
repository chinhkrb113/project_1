package hust.pms.view.sadmin;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import hust.pms.controller.CompanyController;
import hust.pms.controller.SceneController;
import hust.pms.model.Company;
import hust.pms.view.AccountHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class ModifyCompanyForm implements Initializable {

	//public static int confirmationValue;
	
    @FXML
    private TextField tfCompanyName;

    @FXML
    private TextArea taAddress;

    @FXML
    private TextField tfPhone;

    @FXML
    private Button btUpdate;

    @FXML
    private Button btDelete;

    @FXML
    private Button btClose;

    @FXML
    private Label lbNotice;

    @FXML
    void btCloseAction(ActionEvent event) {
    	SceneController.getInstance().closeSceneWithStageRelatedButton(btClose);
    }

    @FXML
    void btDeleteAction(ActionEvent event) throws SQLException {
    	confirmDelete();
    }
    
    private void confirmDelete() throws SQLException {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	String title = "Confirm Delete";
    	String headerText = "Do you want to delete this Company?";
    	String contentText = "Delete this Company cause remove Employee's records belong to this Company.";
    
    	alert.setTitle(title);
    	
    	alert.setHeaderText(headerText);
    	
    	Label labelContentText = new Label(contentText);
    	labelContentText.setWrapText(true);
    	alert.getDialogPane().setContent(labelContentText);
    	    	
    	ButtonType yes = new ButtonType("Yes");
    	ButtonType no = new ButtonType("No");
    	alert.getButtonTypes().clear();
    	alert.getButtonTypes().addAll(yes, no);
    	
    	Optional<ButtonType> option = alert.showAndWait();
    	
    	if (option.get() == null) {
    		System.out.println("confirm: null");
    		
    	} else if (option.get() == no) {
    		System.out.println("confirm: no");
    		
    	} else if (option.get() == yes) {
    		System.out.println("confirm: yes");
    		CompanyController comController = new CompanyController();
    		comController.deleteCompany(ViewCompanyForm.selectedCompanyID);
        	SceneController.getInstance().sAdminCompanyForm.refreshTable();
        	SceneController.getInstance().closeSceneWithStageRelatedButton(btClose);
        	//SceneController.getInstance().closeSceneWithoutButton(event);
    		//SceneController.getInstance().closeSceneWithoutButton(event);
    	}
    }

    @FXML
    void btUpdateAction(ActionEvent event) {
    	Company com = new Company();
    	
    	if (tfCompanyName.getText() == null || tfCompanyName.getText().trim().isEmpty()) {
    		lbNotice.setTextFill(Color.RED);
    		lbNotice.setText("Please fill company name");
    		return;
    	} else {
    		com.setCompanyName(tfCompanyName.getText());
    	}
    	
    	if (taAddress.getText() == null || taAddress.getText().trim().isEmpty()) {
    		lbNotice.setTextFill(Color.RED);
    		lbNotice.setText("Please fill address");
    		return;
    	} else {
    		com.setAddress(taAddress.getText());
    	}
    	
    	if (tfPhone.getText() == null || tfPhone.getText().trim().isEmpty()) {
    		lbNotice.setTextFill(Color.RED);
    		lbNotice.setText("Please fill phone number");
    		return;
    	} else if (!AccountHelper.validatePhoneNumber(tfPhone.getText())) {
    		lbNotice.setTextFill(Color.RED);
    		lbNotice.setText("Invalid phone number");
    		return;
    	} else {
    		com.setPhoneNumber(tfPhone.getText());	
    	}
    	
    	CompanyController comcontroller = new CompanyController();
    	
    	try {
    		if (comcontroller.isPhoneNumberExistExceptCurrent(ViewCompanyForm.selectedCompanyID, tfPhone.getText())) {
    			
    			comcontroller.updateCompany(com, ViewCompanyForm.selectedCompanyID);
    			lbNotice.setTextFill(Color.GREEN);
        		lbNotice.setText("Update Company successfully.");
        		SceneController.getInstance().sAdminCompanyForm.refreshTable();
        	} else if (comcontroller.isPhoneNumberExist(tfPhone.getText())) {
        		lbNotice.setTextFill(Color.RED);
        		lbNotice.setText("Phome Number is exist");
        		return;
        	} else {
        		comcontroller.updateCompany(com, ViewCompanyForm.selectedCompanyID);
    			lbNotice.setTextFill(Color.GREEN);
        		lbNotice.setText("Update Company successfully.");
        		SceneController.getInstance().sAdminCompanyForm.refreshTable();
        	}
    		
    	} catch (SQLException sqle) {
    		sqle.printStackTrace();
    	}
    }
    
    private void loadTextField() {
    	tfCompanyName.setText(ViewCompanyForm.selectedCompanyName);
    	taAddress.setText(ViewCompanyForm.selectedAddress);
    	tfPhone.setText(ViewCompanyForm.selectedPhoneNumber);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadTextField();
	}

}
