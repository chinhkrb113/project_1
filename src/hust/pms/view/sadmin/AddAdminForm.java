package hust.pms.view.sadmin;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import hust.pms.controller.CompanyController;
import hust.pms.controller.EmployeeController;
import hust.pms.controller.ParkingController;
import hust.pms.controller.RoleController;
import hust.pms.controller.SceneController;
import hust.pms.model.Employee;
import hust.pms.view.AccountHelper;
import hust.util.AuthService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class AddAdminForm implements Initializable {

	SceneController sceneRoute = SceneController.getInstance();
	EmployeeController empController = new EmployeeController();
	CompanyController comController = new CompanyController();
	ParkingController parkController = new ParkingController();
	RoleController roleController = new RoleController();

	@FXML
	private TextField tfName;

	@FXML
	private ComboBox<String> comboGender;


	@FXML
	private ComboBox<String> comboCompany;
	

	@FXML
	private ComboBox<String> comboParking;
	

	@FXML
	private ComboBox<String> comboRole;
	

	@FXML
	private TextField tfPhone;
	

	@FXML
	private DatePicker datePicker;
	

	@FXML
	private TextField tfEmail;

	@FXML
	private TextField tfAddress;

	@FXML
	private TextField tfUsername;

	@FXML
	private PasswordField pfPassword;

	@FXML
	private Button btAdd;

	@FXML
	private Button btClear;

	@FXML
	private Button btClose;

	@FXML
	private Label labelNotice;

	@FXML
	private void btAddAction() throws SQLException {
		Employee emp = new Employee();
		// Date date = new Date();
		// check name
		//String name = null;
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
		} else if (AccountHelper.validatePhoneNumber(tfPhone.getText())) {
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
		} else if (AccountHelper.validateEmail(tfEmail.getText())) {
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
		

		//String password = bcryptHashing(pfPassword.getText());
		if (pfPassword.getText().isEmpty()) {
			labelNotice.setTextFill(Color.RED);
			// labelNotice.setAlignment(Pos.CENTER);
			labelNotice.setText("Password is required.");
		} else {
			emp.setPassword(AuthService.getInstance().bEncrypt(pfPassword.getText()));
		}
		

		System.out.println("companyname=" + comboCompany.getValue());
		//int companyid = comController.getCompanyID(comboCompany.getValue().toString());
		emp.setCompanyID(comController.getCompanyIDByCompanyName(comboCompany.getValue().toString()));
		//System.out.println("companyid=" + companyid);

		//int parkingid = parkController.getParkingID(comboParking.getValue().toString());
		emp.setParkingID(parkController.getParkingID("Software Demo for Parking"));
		//emp.setParkingID(parkController.getParkingID(comboParking.getValue().toString()));
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
			if (empController.isEmailExist(tfEmail.getText())) {
				// sceneRoute.sceneAlertWithTitleAndContent("Error when adding Staff", "Email is
				// exist");
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
				empController.addStaff(emp);
				labelNotice.setTextFill(Color.GREEN);
				labelNotice.setText("Add staff successfully.");
				SceneController.getInstance().sadAdminForm.refreshTable();
			}
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			sceneRoute.toAlertWithTitleAndContent("Error when adding Staff", sqle.toString());
		}
	}

	@FXML
	private void btClearAction(ActionEvent event) {
		tfName.clear();
		comboGender.setValue(null);
		datePicker.setValue(null);
		tfEmail.clear();
		tfPhone.clear();
		tfAddress.clear();
		tfUsername.clear();
		pfPassword.clear();
		comboCompany.setValue(null);
		comboParking.setValue(null);
		comboRole.setValue(null);
	}

	@FXML
	private void btCloseAction(ActionEvent event) {
		sceneRoute.closeSceneWithStageRelatedButton(btClose);
	}

	private void loadComboBox() throws SQLException {
		final ObservableList<String> comboGenderList = FXCollections.observableArrayList("Male", "Female",
				"Unknow");
		final ObservableList<String> comboCompanyObList = FXCollections
				.observableArrayList(comController.getCompanyNameToLoadComboBox());
		
		//final ObservableList<String> comboRoleObList = FXCollections.observableArrayList("Admin");
		//CompanyController cc = new CompanyController();
		comboGender.setItems(comboGenderList);
		
		comboCompany.setItems(comboCompanyObList);
		/*
		comboCompany.valueProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue == null) {
				comboParking.getItems().clear();
				comboParking.setDisable(true);
			} else {
				final ObservableList<String> comboParkingObList = FXCollections.observableArrayList(parkController.getParkingNameDependOnSelectedCompanyToLoadComboBox(comboCompany.getValue()));
				comboParking.setItems(comboParkingObList);
				comboParking.setDisable(false);
			}
		});
		*/
		//comboRole.setItems(comboRoleObList);
		comboRole.getSelectionModel().select("Admin");
		//comboCompany.getSelectionModel().select(cc.getCurrentEmployeeCompanyFromCompanyID());
		
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			loadComboBox();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
