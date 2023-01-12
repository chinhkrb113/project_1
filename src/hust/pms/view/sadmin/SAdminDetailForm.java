package hust.pms.view.sadmin;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import hust.pms.controller.CompanyController;
import hust.pms.controller.EmployeeCompanyRoleController;
import hust.pms.controller.EmployeeController;
import hust.pms.controller.LoginController;
import hust.pms.controller.ParkingController;
import hust.pms.controller.RoleController;
import hust.pms.controller.SceneController;
import hust.pms.model.Employee;
import hust.pms.model.EmployeeCompanyRole;
import hust.pms.view.AccountHelper;
import hust.pms.view.admin.ViewStaffForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class SAdminDetailForm extends AccountHelper implements Initializable {

	SceneController sceneRoute = SceneController.getInstance();
	EmployeeController empController = new EmployeeController();
	CompanyController comController = new CompanyController();
	ParkingController parkController = new ParkingController();
	RoleController roleController = new RoleController();
	//Admin_StaffForm adf = new Admin_StaffForm();
	
    @FXML
    private TextField tfName;

    @FXML
    private ComboBox<String> comboGender;
    
    private final ObservableList<String> comboGenderList = FXCollections.observableArrayList("Male", "Female",
			"Unknow");

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> comboRole;
    
    private final ObservableList<String> comboRoleObList = FXCollections
			.observableArrayList(roleController.getRoleNameToLoadComboBox());

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfUsername;

    @FXML
    private Button btUpdate;

    @FXML
    private Button btDelete;

    @FXML
    private Button btClose;

    @FXML
    private ComboBox<String> comboCompany;
    
    private final ObservableList<String> comboCompanyObList = FXCollections
			.observableArrayList(comController.getCompanyNameToLoadComboBox());

    @FXML
    private ComboBox<String> comboParking;

    private final ObservableList<String> comboParkingObList = FXCollections
			.observableArrayList(parkController.getParkingNameBelongToCompanyToLoadComboBox());
    
    @FXML
    private Label labelNotice;

    @FXML
    private void btUpdateAction(ActionEvent event) {
    	Employee emp = new Employee();
    	if (tfName.getText() == null || tfName.getText().trim().isEmpty()) {
			// sceneRoute.sceneAlertWithTitleAndContent("Missing full Name", "Please fill
			// your full name");
			labelNotice.setTextFill(Color.RED);
			// labelNotice.setAlignment(Pos.CENTER);
			labelNotice.setText("Please fill your full name.");
			System.out.println("name is empty");
			return;
		} else {
			emp.setName(tfName.getText());
		}
		//String gender = comboGender.getValue();
		if (comboGender.getValue().isEmpty()) {
			labelNotice.setTextFill(Color.RED);
			// labelNotice.setAlignment(Pos.CENTER);
			labelNotice.setText("Please fill your gender.");
			return;
		} else {
			emp.setGender(comboGender.getValue());
		}
		
		//System.out.println("gender=" + gender);
		
		datePicker.setConverter(new StringConverter<LocalDate>() {

			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate localDate) {
				if (localDate == null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}
		});
		//String birthdate = datePicker.getValue().toString();
		if (datePicker.getValue() == null) {
			labelNotice.setTextFill(Color.RED);
			// labelNotice.setAlignment(Pos.CENTER);
			labelNotice.setText("Please fill your date of birth.");
			return;
		} else {
			emp.setBirthDate(datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
		
		//.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				
		// check phone number
		//String phone;
		if (tfPhone.getText() == null || tfPhone.getText().trim().isEmpty()) {
			// sceneRoute.sceneAlertWithTitleAndContent("Missing phone number", "Please fill
			// your phone number");
			labelNotice.setTextFill(Color.RED);
			// labelNotice.setAlignment(Pos.CENTER);
			labelNotice.setText("Please fill your phone number.");
			System.out.println("phone number is empty");
			return;
		} else if (validatePhoneNumber(tfPhone.getText())) {
			//phone = tfPhone.getText();
			emp.setPhoneNumber(tfPhone.getText());
		} else {
			// sceneRoute.sceneAlertWithTitleAndContent("Invalid phone number format",
			// "Please check your phone number");
			labelNotice.setTextFill(Color.RED);
			// labelNotice.setAlignment(Pos.CENTER);
			labelNotice.setText("Invalid phone number format.");
			System.out.println("Invalid phone number format.");
			return;
		}

		// check email
		//String email = null;
		if (tfEmail.getText() == null || tfEmail.getText().trim().isEmpty()) {
			// sceneRoute.sceneAlertWithTitleAndContent("Missing email", "Please fill your
			// email");
			labelNotice.setTextFill(Color.RED);
			// labelNotice.setAlignment(Pos.CENTER);
			labelNotice.setText("Please fill your email.");
			System.out.println("Please fill your email.");
			return;
		} else if (validateEmail(tfEmail.getText())) {
			//email = tfEmail.getText();
			emp.setEmail(tfEmail.getText());
		} else {
			// sceneRoute.sceneAlertWithTitleAndContent("Invalid email format", "Please
			// check your email");
			labelNotice.setTextFill(Color.RED);
			// labelNotice.setAlignment(Pos.CENTER);
			labelNotice.setText("Invalid email format.");
			System.out.println("Invalid email format.");
			return;
		}

		//String address = tfAddress.getText();
		emp.setAddress(tfAddress.getText());

		//String username = tfUsername.getText();
		if (tfUsername.getText().isEmpty()) {
			labelNotice.setTextFill(Color.RED);
			// labelNotice.setAlignment(Pos.CENTER);
			labelNotice.setText("Username is required.");
			return;
		} else {
			emp.setUsername(tfUsername.getText());
		}
		

		System.out.println("companyname=" + comboCompany.getValue());
		//int companyid = comController.getCompanyID(comboCompany.getValue().toString());
		emp.setCompanyID(comController.getCompanyIDByCompanyName(comboCompany.getValue().toString()));
		//System.out.println("companyid=" + companyid);

		//int parkingid = parkController.getParkingID(comboParking.getValue().toString());
		emp.setParkingID(parkController.getParkingID(comboParking.getValue().toString()));
		//System.out.println("parkingid=" + parkingid);

		//int roleno = roleController.getRoleNo(comboRole.getValue().toString());
		if (comboRole.getValue().isEmpty()) {
			labelNotice.setTextFill(Color.RED);
			// labelNotice.setAlignment(Pos.CENTER);
			labelNotice.setText("Missing Role.");
			return;
		} else {
			emp.setRoleID(roleController.getRoleNo(comboRole.getValue().toString()));
		}
		
		//System.out.println("roleno=" + roleno);

		try {
			if (empController.isEmailExistExceptCurrent(ViewStaffForm.selectedEmployeeID, tfEmail.getText())) {
				// sceneRoute.sceneAlertWithTitleAndContent("Error when adding Staff", "Email is exist");
				if (empController.isPhoneNumberExist(tfPhone.getText())) {
					// sceneRoute.sceneAlertWithTitleAndContent("", "");
					labelNotice.setTextFill(Color.RED);
					labelNotice.setText("Phone number is exist.");
					System.out.println("Phone number is exist.");
					return;
				} else {
					//Employee emp = new Employee();
					empController.updateStaff(emp, ViewStaffForm.selectedEmployeeID);
					labelNotice.setTextFill(Color.GREEN);
					labelNotice.setText("Update staff successfully.");
					sceneRoute.adminStaffForm.refreshTable();
				}
				
			} else if (empController.isEmailExist(tfEmail.getText())) {
					labelNotice.setTextFill(Color.RED);
					labelNotice.setText("Email is exist.");
					System.out.println("Email is exist.");
					return;
			} else if (empController.isPhoneNumberExist(tfPhone.getText())) {
				// sceneRoute.sceneAlertWithTitleAndContent("", "");
				labelNotice.setTextFill(Color.RED);
				labelNotice.setText("Phone number is exist.");
				System.out.println("Phone number is exist.");
				return;
			} else {
				//Employee emp = new Employee();
				empController.updateStaff(emp, ViewStaffForm.selectedEmployeeID);
				labelNotice.setTextFill(Color.GREEN);
				labelNotice.setText("Update staff successfully.");
				sceneRoute.adminStaffForm.refreshTable();
			}
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			sceneRoute.toAlertWithTitleAndContent("Error when updating Staff", sqle.toString());
		}
    }

    @FXML
    private void btDeleteAction(ActionEvent event) {
		EmployeeController empController = new EmployeeController();
		try {
			empController.deleteStaff(ViewStaffForm.selectedEmployeeID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sceneRoute.adminStaffForm.refreshTable();
		sceneRoute.closeSceneWithStageRelatedButton(btDelete);
    }

    @FXML
    private void btCloseAction(ActionEvent event) {
    	sceneRoute.closeSceneWithStageRelatedButton(btClose);
    }

    private void loadTextField() {
    	//System.out.println("selectedEmpID=" + SuperiorAdmin_SAdminForm.selectedEmployeeID);
    	//String[] arr = empController.getStaffDetail(Admin_StaffForm.selectedEmployeeID);
    	EmployeeCompanyRoleController ecrc = new EmployeeCompanyRoleController();
    	//LoginController lc = new LoginController();
    	
    	ArrayList<EmployeeCompanyRole> listECR = null;
    	listECR = ecrc.getStaffDetail(ViewSAdminForm.selectedEmployeeID);
    	
    	/*
    	if (lc.getRoleNoByUserName(Employee.currentUserName) == 0) {
    		listECR = ecrc.getStaffDetail(SuperiorAdmin_SAdminForm.selectedEmployeeID);
    	} else if (lc.getRoleNoByUserName(Employee.currentUserName) == 1) {
    		listECR = ecrc.getStaffDetail(Admin_StaffForm.selectedEmployeeID);
    	} else if (lc.getRoleNoByUserName(Employee.currentUserName) == 2) {
    		//nothing
    	}
    	*/
    	
    	for (EmployeeCompanyRole ecr : listECR) {
    		tfName.setText(ecr.getName());
    		comboGender.getSelectionModel().select(ecr.getGender());
    		try {
    			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ecr.getBirthDate());
    			LocalDate dt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    			datePicker.setValue(dt);
    		} catch (ParseException pe) {
    			// TODO Auto-generated catch block
    			pe.printStackTrace();
    		}
    		tfPhone.setText(ecr.getPhoneNumber());
    		tfEmail.setText(ecr.getEmail());
    		tfAddress.setText(ecr.getAddress());
    		tfUsername.setText(ecr.getUsername());
    		comboCompany.getSelectionModel().select(ecr.getCompanyName());
        	comboParking.getSelectionModel().select(ecr.getParkingName());
        	comboRole.getSelectionModel().select(ecr.getRoleName());
    	}
    }
    
    private void loadComboBox() {
		comboGender.setItems(comboGenderList);
		comboCompany.setItems(comboCompanyObList);
		comboParking.setItems(comboParkingObList);
		comboRole.setItems(comboRoleObList);
	}
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		loadTextField();
		loadComboBox();	
	}
}
