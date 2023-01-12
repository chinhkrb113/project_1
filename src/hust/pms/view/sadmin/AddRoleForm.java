package hust.pms.view.sadmin;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import hust.pms.controller.RoleController;
import hust.pms.controller.SceneController;
import hust.pms.model.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class AddRoleForm implements Initializable {
	
    @FXML
    private ComboBox<String> comboRoleNo;
    
    //private  roleNoList;
    private ObservableList<String> roleNoList;

    @FXML
    private TextField tfRoleName;

    @FXML
    private TextArea taDescription;

    @FXML
    private Button btAdd;

    @FXML
    private Button btClose;
    
    @FXML
    private Label lbNotice;
    
    public AddRoleForm() {
		SceneController.getInstance().sAdminAddRoleForm = this;
	}
    SceneController sr = SceneController.getInstance();

    @FXML
    void btAddAction(ActionEvent event) {
    	Role role = new Role();
    	if (comboRoleNo.getValue().isEmpty()) {
    		lbNotice.setTextFill(Color.RED);
    		lbNotice.setText("Please choose Role No");
    		return;
    	} else {
    		role.setRoleNo(Integer.parseInt(comboRoleNo.getValue()));
    	}
    		
    	if (tfRoleName.getText() == null || tfRoleName.getText().trim().isEmpty()){
    		lbNotice.setTextFill(Color.RED);
    		lbNotice.setText("Please fill Role Name");
    		return;
    	} else {
    		role.setRoleName(tfRoleName.getText());
    	}
    	
    	role.setDescription(taDescription.getText());
    	
    	RoleController rc = new RoleController();
    	
    	try {
    		if (rc.roleNoExist(tfRoleName.getText())) {
    			lbNotice.setTextFill(Color.RED);
    			lbNotice.setText("Role No is exist");
    			return;
    		} else if (rc.roleNameExist(Integer.parseInt(comboRoleNo.getValue()))) {
    			lbNotice.setTextFill(Color.RED);
    			lbNotice.setText("Role Name is exist");
    			return;
    		} else {
    			rc.addRole(role);
    			lbNotice.setTextFill(Color.GREEN);
    			lbNotice.setText("Add role successfully");
    			SceneController.getInstance().sAdminRoleForm.refreshTable();
    		}
    	} catch (ClassNotFoundException cnfe) {
    		cnfe.printStackTrace();
    	} catch (SQLException sqle) {
    		sqle.printStackTrace();
    	}
    }

    @FXML
    void btCloseAction(ActionEvent event) {
    	sr.closeSceneWithStageRelatedButton(btClose);
    }
    
    private void loadRoleNoComboBox() {
    	List<String> numsList = new ArrayList<>();
    	int length = 10;
    	
    	for (int i = 0; i < length;i++) {
    		numsList.add(Integer.toString(i + 1));
    	}
    	roleNoList = FXCollections.observableArrayList(numsList);
    	//System.out.println("roleNoList"+roleNoList);
    	comboRoleNo.setItems(roleNoList);
    }

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		loadRoleNoComboBox();
	}

}
