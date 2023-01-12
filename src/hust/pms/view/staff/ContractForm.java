package hust.pms.view.staff;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import hust.common.Constants;
import hust.common.Navigator;
import hust.pms.controller.CardController;
import hust.pms.controller.ContractController;
import hust.pms.controller.CustomerController;
import hust.pms.controller.SceneController;
import hust.pms.controller.VehicleController;
import hust.pms.model.Card;
import hust.pms.model.Contract;
import hust.pms.model.Customer;
import hust.pms.model.Vehicle;
import hust.pms.view.AccountHelper;
import hust.pms.view.LabelHelper;
import hust.pms.view.TextFieldHelper;
import hust.util.TimeHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class ContractForm implements Initializable, LabelHelper, TextFieldHelper {

	@FXML
	private TextField tfPhone;

	@FXML
	private TextField tfName;

	@FXML
	private TextField tfEmail;

	@FXML
	private TextField tfAddress;

	@FXML
	private TextField tfPay;

	@FXML
	private TextField tfEndTime;

	@FXML
	private TextField tfPlate;

	@FXML
	private ComboBox<String> cbType;

	@FXML
	private TextField tfBrand;

	@FXML
    private TextField tfModel;
	
	@FXML
	private TextField tfColor;

	@FXML
	private TextField tfStatus;

	@FXML
	private Label lbNotice;

	@FXML
	private Button btBack;

	@FXML
	private Button btAdd;

	@FXML
	private Button btTerminate;

	@FXML
	private Button btSearch;

	@FXML
	private Button btClear;

	@FXML
	private TextField tfCard;

	@FXML
	private void btAddAction(ActionEvent event) {
		allFilled();
	}

	@FXML
	private void btBackAction(ActionEvent event) {
		SceneController.getInstance().toScene(event, Navigator.FXML_STAFFCENTER);
	}

	@FXML
	private void btSearchAction(ActionEvent event) {
		CustomerController cc = new CustomerController();
		String[] listCus = cc.getCustomer(tfPhone.getText());
		tfName.setText(listCus[0]);
		tfEmail.setText(listCus[1]);
		tfAddress.setText(listCus[2]);
		/*
		for(int i = 0;i < listCus.length;i++) {
			tfName.setText(str.get());
		}*/
	}

	@FXML
	private void btTerminateAction(ActionEvent event) {

	}

	@FXML
	private void btClearAction() {
		clearField(tfAddress);
		clearField(tfBrand);
		clearField(tfCard);
		clearField(tfColor);
		clearField(tfEmail);
		clearField(tfEndTime);
		clearField(tfName);
		clearField(tfPay);
		clearField(tfPhone);
		clearField(tfPlate);
		clearField(tfStatus);
		clearLabel(lbNotice);
	}

	private void addInfo() {
		Customer cus = new Customer();
		CustomerController cusc = new CustomerController();
		Vehicle veh = new Vehicle();
		VehicleController vehc = new VehicleController();
		Contract cont = new Contract();
		CardController cc = new CardController();
		ContractController conc = new ContractController();
		//Card c = new Card();
		cus.setPhoneNumber(tfPhone.getText());
		cus.setName(tfName.getText());
		cus.setEmail(tfEmail.getText());
		cus.setAddress(tfAddress.getText());
		cus.setTime_created(TimeHelper.getCurrentTimeToStr());
		cusc.addCustomer(cus);
		veh.setLicensePlate(tfPlate.getText());
		veh.setType(cbType.getValue().toString());
		veh.setBrand(tfBrand.getText());
		veh.setModel(tfModel.getText());
		veh.setColor(tfColor.getText());
		veh.setStatus(tfStatus.getText());
		long lastID;
		lastID = cusc.getLastID();
		veh.setCustomerID(lastID);
		vehc.addVehicle(veh);
		String currentTime = null;
		currentTime = TimeHelper.getCurrentTimeToStr();
		cont.setTime_created(currentTime);
		cont.setTime_end(TimeHelper.addMonth(currentTime, Integer.parseInt(tfEndTime.getText())));
		
		
		cc.promoteToCard(tfCard.getText());
		if (cbType.getValue().equals(Constants.TWO_WHEELS)) {
			cont.setContractValue(cont.motorMonthFeeCalculate(Integer.parseInt(tfEndTime.getText())));
		} else if (cbType.getValue().equals(Constants.THREE_WHEELS)) {
			cont.setContractValue(cont.otherMonthFeeCalculate(Integer.parseInt(tfEndTime.getText())));
		}
		
		cont.setCardID(tfCard.getText());
		cont.setLicenseplate(tfPlate.getText());
		cont.setCustomerID(lastID);
		conc.addContract(cont);
		setLabel(lbNotice, null, Color.GREEN, "Add Contract successfully");
	}
	
	private void addInfoOldCustomer() {
		CustomerController cusc = new CustomerController();
		Vehicle veh = new Vehicle();
		VehicleController vehc = new VehicleController();
		Contract cont = new Contract();
		CardController cc = new CardController();
		ContractController conc = new ContractController();
		veh.setLicensePlate(tfPlate.getText());
		veh.setType(cbType.getValue().toString());
		veh.setBrand(tfBrand.getText());
		veh.setModel(tfModel.getText());
		veh.setColor(tfColor.getText());
		veh.setStatus(tfStatus.getText());
		//long lastID;
		//lastID = cusc.getLastID();
		veh.setCustomerID(cusc.getCustomerIDByPhone(tfPhone.getText()));
		vehc.addVehicle(veh);
		String currentTime = null;
		currentTime = TimeHelper.getCurrentTimeToStr();
		cont.setTime_created(currentTime);
		cont.setTime_end(TimeHelper.addMonth(currentTime, Integer.parseInt(tfEndTime.getText())));
		cc.promoteToCard(tfCard.getText());
		
		if (cbType.getValue().equals(Constants.TWO_WHEELS)) {
			cont.setContractValue(cont.motorMonthFeeCalculate(Integer.parseInt(tfEndTime.getText())));
		} else if (cbType.getValue().equals(Constants.THREE_WHEELS)) {
			cont.setContractValue(cont.otherMonthFeeCalculate(Integer.parseInt(tfEndTime.getText())));
		}
		long customerID = cusc.getCustomerIDByPhone(tfPhone.getText());
		cont.setCardID(tfCard.getText());
		cont.setLicenseplate(tfPlate.getText());
		cont.setCustomerID(customerID);
		conc.addContract(cont);
		setLabel(lbNotice, null, Color.GREEN, "Add Contract successfully");
	}
	
	private void addInfoOldCustomerWithOldVehicle() {
		CustomerController cusc = new CustomerController();
		ContractController conc = new ContractController();
		//VehicleController vehc = new VehicleController();
		Contract cont = new Contract();
		CardController cc = new CardController();
		String currentTime = null;
		currentTime = TimeHelper.getCurrentTimeToStr();
		cont.setTime_created(currentTime);
		cont.setTime_end(TimeHelper.addMonth(currentTime, Integer.parseInt(tfEndTime.getText())));
		
		cc.promoteToCard(tfCard.getText());
		
		if (cbType.getValue().equals(Constants.TWO_WHEELS)) {
			cont.setContractValue(cont.motorMonthFeeCalculate(Integer.parseInt(tfEndTime.getText())));
		} else if (cbType.getValue().equals(Constants.THREE_WHEELS)) {
			cont.setContractValue(cont.otherMonthFeeCalculate(Integer.parseInt(tfEndTime.getText())));
		}
		long customerID = cusc.getCustomerIDByPhone(tfPhone.getText());
		cont.setCardID(tfCard.getText());
		cont.setLicenseplate(tfPlate.getText());
		cont.setCustomerID(customerID);
		
		conc.addContract(cont);
		setLabel(lbNotice, null, Color.GREEN, "Add Contract successfully");
	}
	
	@Override
	public void allFilled() {
		if (tfPhone.getText() == null || tfPhone.getText().trim().isEmpty()) {
			setLabel(lbNotice, null, Color.RED, "Missing phone number.");
			return;
		} else if (tfName.getText() == null || tfName.getText().trim().isEmpty()) {
			setLabel(lbNotice, null, Color.RED, "Missing name.");
			return;
		} else if (tfEmail.getText() == null || tfEmail.getText().trim().isEmpty()) {
			setLabel(lbNotice, null, Color.RED, "Missing email.");
			return;
		} else if (tfAddress.getText() == null || tfAddress.getText().trim().isEmpty()) {
			setLabel(lbNotice, null, Color.RED, "Missing address");
			return;
		} else if (tfPlate.getText() == null || tfPlate.getText().trim().isEmpty()) {
			setLabel(lbNotice, null, Color.RED, "Missing number plate");
			return;
		} else if (cbType.getSelectionModel().isEmpty()) {
			setLabel(lbNotice, null, Color.RED, "Missing vehicle type");
			return;
		} else if (tfStatus.getText() == null || tfStatus.getText().trim().isEmpty()) {
			setLabel(lbNotice, null, Color.RED, "Missing vehicle status");
			return;
		} else if (tfEndTime.getText() == null || tfEndTime.getText().trim().isEmpty()) {
			setLabel(lbNotice, null, Color.RED, "Missing end time");
			return;
		} else {
			allCorrectFormat();
		}
	}

	@Override
	public void setLabel(Label label, Pos pos, Color color, String text) {
		label.setAlignment(pos);
		label.setTextFill(color);
		label.setText(text);

	}

	@Override
	public void clearLabel(Label label) {
		// label.clear();
	}

	@Override
	public void allCorrectFormat() {
		CustomerController cusc = new CustomerController();
		VehicleController vehc = new VehicleController();
		try {
			Integer.parseInt(tfEndTime.getText());
		} catch (NumberFormatException nfe) {
			setLabel(lbNotice, null, Color.RED, "Invalid end time format. Please input integer of month.");
			nfe.printStackTrace();
		}
		if (!AccountHelper.validateEmail(tfEmail.getText())) {
			setLabel(lbNotice, null, Color.ORANGE, "Invaid email format.");
			return;
		} else if (!AccountHelper.validatePhoneNumber(tfPhone.getText())) {
			setLabel(lbNotice, null, Color.ORANGE, "Invalid phone number format.");
			return;
		} else {
			try {
				System.out.println(tfPlate.getText());
				if (cusc.isPhoneNumberExist(tfPhone.getText())) {
					System.out.println("Phonenumber exist");
					if (vehc.isVehicleExist(tfPlate.getText())) {
						System.out.println("OldVehicle");
						addInfoOldCustomerWithOldVehicle();
					} else {
						System.out.println("Old customer");
						addInfoOldCustomer();
					}
				} else {
					addInfo();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
	}

	@Override
	public void clearField(TextField textField) {
		textField.clear();
	}

	private void loadComboBox() {
		List<String> cbList = new ArrayList<>();
		cbList.add(Constants.TWO_WHEELS);
		cbList.add(Constants.THREE_WHEELS);
		cbType.setItems(FXCollections.observableArrayList(cbList));
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadComboBox();
	}

}
