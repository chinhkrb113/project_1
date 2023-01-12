package hust.pms.view.sadmin;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hust.common.Navigator;
import hust.pms.controller.EmployeeController;
import hust.pms.controller.LoginController;
import hust.pms.controller.LogoutController;
import hust.pms.controller.SceneController;
import hust.pms.model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ViewSAdminForm implements Initializable {

	LoginController loginController = new LoginController();
	EmployeeController empController = new EmployeeController();

	SceneController sceneRoute = SceneController.getInstance();
	
	//Router r = new Router();
	
	public ViewSAdminForm() {
		SceneController.getInstance().sAdminForm = this;
	}
	
	public static int selectedEmployeeID;
	public static String selectedPhoneNumber;
	public static String selectedEmail;
	public static String selectedUsername;

	@FXML
	private TableView<Employee> employeeTable;
	
	@FXML
	private TableColumn<Employee, String> col_id;

	@FXML
	private TableColumn<Employee, String> col_name;

	@FXML
	private TableColumn<Employee, String> col_gender;

	@FXML
	private TableColumn<Employee, String> col_birthdate;

	@FXML
	private TableColumn<Employee, String> col_phonenumber;

	@FXML
	private TableColumn<Employee, String> col_email;

	@FXML
	private TableColumn<Employee, String> col_address;

	@FXML
	private Button btBack;

	@FXML
	private Button btLogout;

	@FXML
	private Button btAddStaff;

	@FXML
	private Label lbWelcome;

	@FXML
	private TextField tfSearch;

	private ObservableList<Employee> empList;

	@FXML
	private void btAddStaffAction() {
		//sceneRoute.toParallelScene(Navigator.FXML_ADDSTAFF);
	}

	@FXML
	private void btBackAction(ActionEvent event) {
		sceneRoute.toScene(event, Navigator.FXML_SUPERIOR_ADMINCENTER);
	}

	@FXML
	private void btLogoutAction(ActionEvent event) throws SQLException {
		LogoutController loc = new LogoutController();
    	loc.toLogOutStatus(Employee.currentUserName);
		sceneRoute.toScene(event, Navigator.FXML_LOGIN);
	}

	@FXML
	private void enterPressed() {
		tfSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keyEvent) {
				String text;
				if (keyEvent.getCode().isDigitKey() || keyEvent.getCode().isLetterKey()) {
					text = tfSearch.getText();
					System.out.println("search=" + text);
				}
			}
		});
	}

	private void loadTable() {
		
		//statusColumn.setCellValueFactory(cellData -> cellData.getValue().get);
		col_id.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeID"));
		col_name.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
		col_gender.setCellValueFactory(new PropertyValueFactory<Employee, String>("gender"));
		col_birthdate.setCellValueFactory(new PropertyValueFactory<Employee, String>("birthDate"));
		col_phonenumber.setCellValueFactory(new PropertyValueFactory<Employee, String>("phoneNumber"));
		col_email.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
		// col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		// col_parking.setCellValueFactory(new PropertyValueFactory<>("parkingid"));
		// col_parking.setCellValueFactory(new PropertyValueFactory<>("parkingid"));
		// col_role.setCellValueFactory(new PropertyValueFactory<>("roleid"));
		col_address.setCellValueFactory(new PropertyValueFactory<Employee, String>("address"));
//		col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
//		col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
		
		empList = FXCollections.observableArrayList(empController.getSAdminToLoadTable());
		employeeTable.setItems(empList);
	}
	
	public void refreshTable() {
		loadTable();
		//empList.add(emp);
	}

	private void searchTableAndRefresh() {
    	FilteredList<Employee> filteredData = new FilteredList<>(empList, b -> true);
		
		tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(employee -> {
				
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (employee.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				} else if (employee.getGender().toLowerCase().indexOf(lowerCaseFilter)!=-1) {
					return true;
				} else if (employee.getPhoneNumber().indexOf(lowerCaseFilter)!=-1) {
					return true;
				} else if (employee.getEmail().toLowerCase().indexOf(lowerCaseFilter)!=-1) {
					return true;
				} else {
					return false;
				}
			});
		});
			
			// 3. Wrap the FilteredList in a SortedList. 
			SortedList<Employee> sortedData = new SortedList<>(filteredData);
			
			// 4. Bind the SortedList comparator to the TableView comparator.
			// 	  Otherwise, sorting the TableView would have no effect.
			sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());
			
			// 5. Add sorted (and filtered) data to the table.
			employeeTable.setItems(sortedData);
		
    }
	
	 @FXML
	 private void empTableActionMouseClicked(MouseEvent event) {
		 
		 //TableRow<Employee> row = new TableRow<>();
		 if (event.getClickCount() == 2) {
			 //System.out.println("empID="+employeeTable.getSelectionModel().getSelectedItem().getEmployeeID());
			 /*
			 selectedEmployeeID = employeeTable.getSelectionModel().getSelectedItem().getEmployeeID();
			 selectedEmail = employeeTable.getSelectionModel().getSelectedItem().getEmail();
			 selectedPhoneNumber = employeeTable.getSelectionModel().getSelectedItem().getPhoneNumber();
			 selectedUsername = employeeTable.getSelectionModel().getSelectedItem().getUserName();
			 
			 System.out.println("currentEmployeeID=" + selectedEmployeeID);
			 sceneRoute.toParallelScene(Navigator.FXML_EMPLOYEE_DETAIL);
			 //loadTable();
			*/
			 sceneRoute.toAlertWithTitleAndContent("Warning", "Access denied!");
			  
		 }
	 }
	 
	 private void loadLabel() {
		 if (loginController.getUserRoleCurrent() == 0) {
			lbWelcome.setAlignment(Pos.BASELINE_RIGHT);
			lbWelcome.setText(loginController.getUserCurrent() + ", SAdmin");
		} else if (loginController.getUserRoleCurrent() == 1) {
			lbWelcome.setAlignment(Pos.BASELINE_RIGHT);
			lbWelcome.setText(loginController.getUserCurrent() + ", Admin");
		} else {
			lbWelcome.setAlignment(Pos.BASELINE_RIGHT);
			lbWelcome.setText(loginController.getUserCurrent() + ", Staff");
		}
	 }

	 @Override
	 public void initialize(URL url, ResourceBundle rb) {
		loadTable();
		
		//statusColumn.setCellValueFactory(null);
		searchTableAndRefresh();
		loadLabel();
//		tfSearch.getScene().getAccelerators().put(new KeyCombination(KeyCode.C, KeyCombination.CONTROL_ANY), new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				tfSearch.requestFocus();
//				String text = tfSearch.getText();
//				System.out.println(text);
//			}
//		});
		
		
	}
}
