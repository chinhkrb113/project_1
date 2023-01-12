package hust.pms.view.sadmin;

import java.sql.SQLException;

import hust.pms.controller.CompanyController;
import hust.pms.controller.SceneController;
import hust.pms.model.Company;
import hust.pms.view.AccountHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class AddCompanyForm {

    @FXML
    private TextField tfCompanyName;

    @FXML
    private TextArea taAddress;

    @FXML
    private TextField tfPhone;

    @FXML
    private Button btAdd;

    @FXML
    private Button btClear;

    @FXML
    private Button btClose;
    
    @FXML
    private Label lbNotice;

    @FXML
    void btAddAction(ActionEvent event) {
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
    		if (comcontroller.isCompanyNameExist(tfCompanyName.getText())) {
    			lbNotice.setTextFill(Color.RED);
        		lbNotice.setText("Company Name is exist.");
        		return;
    		} else if (comcontroller.isPhoneNumberExist(tfPhone.getText())) {
    			lbNotice.setTextFill(Color.RED);
        		lbNotice.setText("Phone number is exist.");
        		return;
    		} else {
    			comcontroller.addCompany(com);
    			lbNotice.setTextFill(Color.GREEN);
        		lbNotice.setText("Add Company successfully.");
        		SceneController.getInstance().sAdminCompanyForm.refreshTable();
    		}
    	} catch (SQLException sqle) {
    		sqle.printStackTrace();
    	}
    }

    @FXML
    void btClearAction() {
    	tfCompanyName.clear();
    	taAddress.clear();
    	tfPhone.clear();
    }

    @FXML
    void btCloseAction(ActionEvent event) {
    	SceneController.getInstance().closeSceneWithStageRelatedButton(btClose);
    }

}
