package hust.pms.view.sadmin;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hust.common.Navigator;
import hust.pms.controller.LogoutController;
import hust.pms.controller.RoleController;
import hust.pms.controller.SceneController;
import hust.pms.model.Employee;
import hust.pms.model.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class RoleForm implements Initializable {

	public RoleForm() {
		SceneController.getInstance().sAdminRoleForm = this;
	}
	
	SceneController sr = SceneController.getInstance();
	
	public static int selectedRoleID;
	
	
    @FXML
    private TableView<Role> roleTable;

    @FXML
    private TableColumn<Role, String> col_id;

    @FXML
    private TableColumn<Role, String> col_roleno;

    @FXML
    private TableColumn<Role, String> col_rolename;

    @FXML
    private TableColumn<Role, String> col_description;

    @FXML
    private Button btAddRole;

    @FXML
    private Button btBack;

    @FXML
    private Button btLogOut;

    @FXML
    private Button btDeleteRole;
    
    private ObservableList<Role> roleList;

    @FXML
    void btAddRoleAction(ActionEvent event) {
    	sr.toParallelScene(Navigator.FXML_ADD_ROLE);
    }

    @FXML
    void btBackAction(ActionEvent event) {
    	sr.toScene(event, Navigator.FXML_SUPERIOR_ADMINCENTER);
    }

    @FXML
    void btDeleteRoleAction(ActionEvent event) throws SQLException {
    	RoleController rc = new RoleController();
    	rc.deleteRole(selectedRoleID);
    	refreshTable();
    }

    @FXML
    void btLogOutAction(ActionEvent event) throws SQLException {
    	LogoutController loc = new LogoutController();
    	loc.toLogOutStatus(Employee.currentUserName);
    	sr.toScene(event, Navigator.FXML_LOGIN);
    }
    
    private void loadTable() {
    	RoleController rc = new RoleController();
    	
    	col_id.setCellValueFactory(new PropertyValueFactory<Role, String>("roleID"));
    	col_roleno.setCellValueFactory(new PropertyValueFactory<Role, String>("roleNo"));
    	col_rolename.setCellValueFactory(new PropertyValueFactory<Role, String>("roleName"));
    	col_description.setCellValueFactory(new PropertyValueFactory<Role, String>("description"));
    	
    	roleList = FXCollections.observableArrayList(rc.getRole());
    	
    	roleTable.setItems(roleList);
    	
    }
    
    public void refreshTable() {
    	loadTable();
    }
    
    @FXML
    void roleTableMouseAction(MouseEvent event) {
    	
    	if (event.getClickCount() == 1) {
    		selectedRoleID = roleTable.getSelectionModel().getSelectedItem().getRoleID();
    		System.out.println("selectedRoleID=" + selectedRoleID);
    	}
    }

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		loadTable();
		
		
		
	}

}
