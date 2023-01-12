package hust.pms.view.sadmin;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hust.common.Navigator;
import hust.pms.controller.CompanyController;
import hust.pms.controller.EmployeeController;
import hust.pms.controller.LogoutController;
import hust.pms.controller.SceneController;
import hust.pms.model.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SuperiorAdminCenterForm implements Initializable {

    @FXML
    private Button btLogout;

    @FXML
    private Button btViewCompany;
    
    @FXML
    private Button btViewSAdmin;

    @FXML
    private Button btViewAdmin;
    
    @FXML
    private Button btViewRole;

    @FXML
    private Label lbNumberOfCompany;
    
    @FXML
    private Label lbNumberOfSAdmin;
    
    @FXML
    private Label lbNumberOfAdmin;

    @FXML
    private void btLogoutAction(ActionEvent event) throws SQLException {
    	LogoutController loc = new LogoutController();
    	loc.toLogOutStatus(Employee.currentUserName);
		SceneController.getInstance().toScene(event, Navigator.FXML_LOGIN);
    }
    
    @FXML
    void btViewSAdminAction(ActionEvent event) {
    	SceneController.getInstance().toScene(event, Navigator.FXML_SUPERIOR_ADMIN_VIEW_SUPERIOR_ADMIN);
    }

    @FXML
    private void btViewAdminAction(ActionEvent event) {
    	SceneController.getInstance().toScene(event, Navigator.FXML_SUPERIOR_ADMIN_VIEW_ADMIN);
    }

    @FXML
    private void btViewCompanyAction(ActionEvent event) {
    	SceneController.getInstance().toScene(event, Navigator.FXML_SUPERIOR_ADMIN_COMPANY);
    }
    
    @FXML
    void btViewRoleAction(ActionEvent event) {
    	SceneController.getInstance().toScene(event, Navigator.FXML_SUPERIOR_ADMINROLE);
    }
    
    private void loadLabel() {
    	EmployeeController ec = new EmployeeController();
    	CompanyController cc = new CompanyController();
    	try {
			lbNumberOfAdmin.setText(String.valueOf(ec.getNumberOfAdmin()));
			lbNumberOfCompany.setText(String.valueOf(cc.getNumberOfCompany()));
	    	lbNumberOfSAdmin.setText(String.valueOf(ec.getNumberOfSAdmin()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		loadLabel();
		
	}

}
