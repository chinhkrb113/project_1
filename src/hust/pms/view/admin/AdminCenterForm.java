package hust.pms.view.admin;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hust.common.Navigator;
import hust.pms.controller.CompanyController;
import hust.pms.controller.EmployeeController;
import hust.pms.controller.LoginController;
import hust.pms.controller.LogoutController;
import hust.pms.controller.ParkingController;
import hust.pms.controller.SceneController;
import hust.pms.model.Employee;
import hust.pms.view.LabelHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class AdminCenterForm implements Initializable, LabelHelper{

	//SceneController sceneRouter = SceneController.getInstance();
	
	@FXML
    private Label lbWelcome;

    @FXML
    private Button btLogout;

    @FXML
    private Label lbCompany;

    @FXML
    private Label lbParking;

    @FXML
    private Label lbNumberOfStaff;

    @FXML
    private Label lbNumberOfParking;

    @FXML
    private Button btViewStaff;

    @FXML
    private Button btViewParking;
    
    @FXML
    private Button btStatistic;
    
    @FXML
    private Button btFee;
    
    @FXML
    void btFeeAction(ActionEvent event) {
    	SceneController.getInstance().toParallelScene(Navigator.FXML_CHANGE_FEE);
    }


    @FXML
    private void btLogoutAction(ActionEvent event) throws SQLException {
    	LogoutController loc = new LogoutController();
    	loc.toLogOutStatus(Employee.currentUserName);
    	//System.out.println("currentUser in AdminCenterForm="+Employee.currentUser);
    	SceneController.getInstance().toScene(event, Navigator.FXML_LOGIN);
    }

    @FXML
    private void btViewStaffAction(ActionEvent event) {
    	SceneController.getInstance().toScene(event, Navigator.FXML_ADMINSTAFF);
    }
    
    @FXML
    private void btViewParkingAction(ActionEvent event) {
    	SceneController.getInstance().toScene(event, Navigator.FXML_ADMINPARKING);
    }
    
    @FXML
    void btStatisticAction(ActionEvent event) {
    	SceneController.getInstance().toScene(event, Navigator.FXML_STATISTIC_CENTER);
    }
    
    private void loadLabel() throws SQLException {
    	CompanyController cc = new CompanyController();
    	EmployeeController ec = new EmployeeController();
    	ParkingController pc = new ParkingController();
    	//ParkingController pc = new ParkingController();
    	
    	lbCompany.setText(cc.getCurrentEmployeeCompanyFromCompanyID());
    	//lbParking.setText(pc.getCurrentEmployeeParkingFromParkingID());
    	
    	lbNumberOfStaff.setText(String.valueOf(ec.getNumberOfStaffBelongToCompany(Employee.currentEmployeeCompanyID)));
    	lbNumberOfParking.setText(String.valueOf(pc.getNumberOfParkingBelongToCompany(Employee.currentEmployeeCompanyID)));
    }
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		LoginController loginController = new LoginController();
		setLabel(lbCompany, null, null, "Hello "+ loginController.getUserCurrent());
		try {
			loadLabel();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setLabel(Label label, Pos pos, Color color, String text) {
		label.setText(text);
		
	}

	@Override
	public void clearLabel(Label label) {
		// TODO Auto-generated method stub
		
	}

}
