package hust.pms.view.staff;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import hust.common.Constants;
import hust.common.Navigator;
import hust.pms.controller.CameraController;
import hust.pms.controller.CardController;
import hust.pms.controller.CompanyController;
import hust.pms.controller.ContractController;
import hust.pms.controller.CustomerController;
import hust.pms.controller.HistoryController;
import hust.pms.controller.LogoutController;
import hust.pms.controller.ParkingController;
import hust.pms.controller.SceneController;
import hust.pms.controller.VehicleController;
import hust.pms.model.Contract;
import hust.pms.model.Customer;
import hust.pms.model.Employee;
import hust.pms.model.History;
import hust.pms.model.Vehicle;
import hust.pms.model.WebcamInfo;
import hust.pms.view.LabelHelper;
import hust.util.RecognitionHelper;
import hust.util.TimeHelper;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class StaffCenterForm implements Initializable, LabelHelper {
	
	public static String currentCID;
	public static String currentDateTime= null;
	public static String currentFullCIDAndDateTime = null;
	public static String currentCIDAndDateTime = null;
	
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
    private Pane labelPane;
	
	@FXML
    private RadioButton rbtInMode;

    @FXML
    private RadioButton rbtOutMode;
	
	@FXML
    private RadioButton rbtTwoMode;

    @FXML
    private RadioButton rbtThreeMode;
	 
    @FXML
    private ImageView iv_img_in1;

    @FXML
    private ImageView iv_img_in2;
    
    @FXML
    private Button btHistory;

    @FXML
    private Button btContract;

    @FXML
    private Button btCustomer;

    @FXML
    private Button btCard;

    @FXML
    private Button btLogout;

    @FXML
    private TextField tfCID;
    
    @FXML
    private Label lbNotice;

    @FXML
    private Label lbCID;
    
    @FXML
    private Label lbType;
    
    @FXML
    private Label lbName;

    @FXML
    private Label lbContractStatus;
    
    @FXML
    private Label lbLicensePlate;

    @FXML
    private Label lbFee;
    
    @FXML
    private Label lbTimeIn;

    @FXML
    private Label lbTimeOut;
    
    @FXML
    private Label lbCurrentLP;

    @FXML
    private Label lbStoredLP;
    
    @FXML
    private Label lbMatched;
    
    @FXML
    private Button btMark;

    @FXML
    private ImageView iv_img_in_front;

    @FXML
    private ImageView iv_img_in_lp;

    @FXML
    private ImageView iv_img_out_front;

    @FXML
    private ImageView iv_img_out_lp;
    
    @FXML
    private Button btStartCam;

    @FXML
    private Button btStopCam;
    
    @FXML
    private ComboBox<WebcamInfo> cbCameraOptions1;

    @FXML
    private ComboBox<WebcamInfo> cbCameraOptions2;
    
    private boolean stopCamera = false;
	private Webcam webcam = null;
	private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
	private BufferedImage grabbedImage;
	
	@FXML
    void btHistoryAction(ActionEvent event) {
		SceneController.getInstance().toScene(event, Navigator.FXML_VIEW_HISTORY);
    }
	
	@FXML
    void btCardAction() {
    	SceneController.getInstance().toParallelScene(Navigator.FXML_CARDISSUE);
    }

    @FXML
    void btLogoutAction(ActionEvent event) throws SQLException {
    	LogoutController loc = new LogoutController();
    	loc.toLogOutStatus(Employee.currentUserName);
    	//System.out.println("currentUser in AdminCenterForm="+Employee.currentUser);
    	disposeCamera();
    	SceneController.getInstance().toScene(event, Navigator.FXML_LOGIN);
    }
    
    @FXML
    void btContractAction(ActionEvent event) {
    	SceneController.getInstance().toScene(event, Navigator.FXML_STAFF_CONTRACT);
    }
    
    @FXML
    void btCustomerAction(ActionEvent event) {

    }
    
    @FXML
    void btMarkAction(ActionEvent event) {

    }
    
    private void initRadioButton() {
    	ToggleGroup tg1 = new ToggleGroup();
    	ToggleGroup tg2 = new ToggleGroup();
    	rbtInMode.setToggleGroup(tg1);
    	rbtInMode.setSelected(true);
    	rbtOutMode.setToggleGroup(tg1);
    	rbtTwoMode.setToggleGroup(tg2);
    	rbtTwoMode.setSelected(true);
    	rbtThreeMode.setToggleGroup(tg2);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//tfCID.requestFocus();
		//onEnter();
		initRadioButton();
		
		CameraController camc = new CameraController();
//		ObservableList<WebcamInfo> options = FXCollections.observableArrayList(camc.getWebcam());
    	/*
		int webcamCounter = 0;
    	for (Webcam webcam:Webcam.getWebcams()) {
    		WebcamInfo webcamInfo = new WebcamInfo();
    		webcamInfo.setWebCamIndex(webcamCounter);
    		webcamInfo.setWebCamName(webcam.getName());
    		options.add(webcamInfo);
    		webcamCounter++;
    	}
    	*/
		
//    	cbCameraOptions1.setItems(options);
    	cbCameraOptions1.setPromptText("Please choose camera1");
    	cbCameraOptions1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WebcamInfo>() {

			@Override
			public void changed(ObservableValue<? extends WebcamInfo> arg0, WebcamInfo arg1, WebcamInfo arg2) {
				if (arg2 != null) {
					System.out.println("Webcam Index: " + arg2.getWebCamIndex() + ": Webcam Name:" + arg2.getWebCamName());
					initWebcam(arg2.getWebCamIndex());
				}	
			}
		});
    	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				setImageViewSize();
			}
		});
    	
    	//anchorPane.setOnKeyPressed(this::handleOnKeyPressed);
    	//handleOnKeyPressed();
    	
    	//Anchor Pane Event
    	
    	
    	// CID Text Field Event
    	tfCID.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keyEvent) {
				
				Customer cus = new Customer();
				Vehicle veh = new Vehicle();
				Contract cont = new Contract();
				History his = new History();
				
				CustomerController cusController = new CustomerController();
				VehicleController vehController = new VehicleController();
				ContractController contController = new ContractController();
				HistoryController hisController = new HistoryController();
				CameraController camController= new CameraController();
				CardController cardController = new CardController();
				ParkingController parkController = new ParkingController();
				
				long lastCustomerID = 0;
				long lastContractID = 0;
				
				Image img;
				
				if (keyEvent.getCode() == KeyCode.ENTER) {
					
					currentCID = tfCID.getText();
					if (cardController.isCardBeUsed(currentCID) == false) {
						setLabel(lbNotice, Pos.BASELINE_LEFT, Color.RED, "Card is locked or cannot be used.");
						setLabel(lbCID, null, Color.BLACK, tfCID.getText());
						setLabel(lbType, Pos.BASELINE_LEFT, Color.BLACK, "Unknow");
						clearLabel(lbName);
						clearLabel(lbContractStatus);
						clearLabel(lbFee);
						clearLabel(lbTimeIn);
						clearLabel(lbTimeOut);
						iv_img_in_lp.setImage(null);
						iv_img_out_lp.setImage(null);
						tfCID.clear();
						//return;
					} else if (cardController.isCardBeUsed(currentCID) == true) {
						System.out.println("Card valid");
		    			if (cardController.getCardType(currentCID) == Constants.CARD_FOR_GUEST) {
		    				System.out.println("Guest");
		    				
		    				//In mode
		    				if (rbtInMode.isSelected()) {
		    					
		    					System.out.println("Entrance mode");
		    					setLabel(lbCID, null, Color.BLACK, tfCID.getText());
		    					setLabel(lbType, null, Color.BLACK, "Guest");
		    					
		    					clearLabel(lbContractStatus);
	    						clearLabel(lbFee);
	    						clearLabel(lbName);
	    						clearLabel(lbLicensePlate);
	    						clearLabel(lbCurrentLP);
	    						clearLabel(lbStoredLP);
	    						clearLabel(lbMatched);
		    					if (hisController.checkInOutSide(currentCID, Constants.VEHICLE_INSIDE)) {   						
		    						setLabel(lbNotice, null, Color.RED, "Card with vehicle inside");
		    						iv_img_in_lp.setImage(null);
									iv_img_out_lp.setImage(null);
		    						tfCID.clear();
		    						return;
		    					} else if (parkController.isFullSlot()) {
		    						setLabel(lbNotice, null, Color.RED, "Full slot.");
		    						return;
		    					} else {
		    						//
				    				currentDateTime = TimeHelper.getCurrentTimeToStr();
									currentFullCIDAndDateTime = "./img_history/" + currentCID + "_" + currentDateTime + ".jpg";
									currentCIDAndDateTime = currentCID + "_" + currentDateTime + ".jpg";
									
									//get image from webcam
									camController.getImage(webcam);
									
									//populate image which captured to image view
									File file = new File(currentFullCIDAndDateTime);
									iv_img_in_lp.setImage(new Image(file.toURI().toString()));
									iv_img_out_lp.setImage(null);
					    			
									setLabel(lbNotice, null, Color.GREEN, "Card is valid.");
									setLabel(lbTimeIn, null, Color.BLACK, currentDateTime);
									tfCID.clear();
				    						    				
		    						
		    						his.setImgIn(null);
			    					his.setImgLPIn(currentFullCIDAndDateTime);
			    					his.setTimeIn(currentDateTime);
			    					his.setVisitStatus(Constants.VEHICLE_INSIDE);
			    					his.setCardID(currentCID);
			    					
			    					parkController.increaseNoCurrentVehicle();
			    					setLabel(lbTimeIn, null, Color.BLACK, TimeHelper.strToTime(currentDateTime));
			    					String plate = null;
			    					
			    					//System.out.println(currentCIDAndDateTime);
			    					plate = RecognitionHelper.toPlateStr(currentCIDAndDateTime); 
			    					System.out.println("plateInUsing="+plate);
			    					his.setPlateIn(plate);
			    					setLabel(lbCurrentLP, null, Color.BLACK, plate);
			    					hisController.addHistory(his);
			    					clearLabel(lbTimeOut);
		    					}
		    					
		    					//Out Mode
		    				} else if (rbtOutMode.isSelected()) {
		    					System.out.println("Exit mode");
		    					setLabel(lbCID, null, Color.BLACK, currentCID);
	    						setLabel(lbType, null, Color.BLACK, "Guest");
	    						clearLabel(lbContractStatus);
	    						clearLabel(lbFee);
	    						clearLabel(lbName);
	    						clearLabel(lbLicensePlate);
	    						clearLabel(lbTimeIn);
	    						clearLabel(lbTimeOut);
	    						clearLabel(lbCurrentLP);
	    						clearLabel(lbStoredLP);
	    						clearLabel(lbMatched);
	    						
		    					if (!hisController.checkInOutSide(currentCID, Constants.VEHICLE_INSIDE)) {
		    						
		    						setLabel(lbNotice, null, Color.RED, "Vehicle doesn't parking in the momment.");
		    						iv_img_in_lp.setImage(null);
									iv_img_out_lp.setImage(null);
		    						tfCID.clear();
		    						return;
		    					}
		    					
		    					if(rbtTwoMode.isSelected()) {
		    						System.out.println("2 wheeler mode");
		    						
		    						setLabel(lbNotice, null, Color.GREEN, "Card is valid.");
		    						
		    						// Time to capturing :)
		    						currentDateTime = TimeHelper.getCurrentTimeToStr();
		    						
									currentFullCIDAndDateTime = "./img_history/" + currentCID + "_" + currentDateTime + ".jpg";
									currentCIDAndDateTime = currentCID + "_" + currentDateTime + ".jpg";
		    						camController.getImage(webcam);
									
									//populate image which captured to image view
									File newRecord = new File(currentFullCIDAndDateTime);
									iv_img_out_lp.setImage(new Image(newRecord.toURI().toString()));
									
									//populate old record image which captured to image view
									File oldRecord = new File(hisController.getURLImageLPIn(currentCID, Constants.VEHICLE_INSIDE));
									iv_img_in_lp.setImage(new Image(oldRecord.toURI().toString()));
																		
		    						his.setImgOut(null);
		    						his.setImgLPOut(currentFullCIDAndDateTime);
		    						his.setTimeOut(currentDateTime);
		    						
		    						long[] _fee;
		    						CompanyController cc = new CompanyController();
		    						_fee = cc.getFee();
		    						his.setVisitStatus(Constants.VEHICLE_OUTSIDE);
		    						his.setFee(_fee[0]);
		    						his.setCardID(currentCID);
		    						
		    						String plate = null;
		    						String stored_plate = null;
		    						
		    						plate = RecognitionHelper.toPlateStr(currentCIDAndDateTime); 
			    					System.out.println("plateInUsing="+plate);
			    					his.setPlateOut(plate);
			    					setLabel(lbCurrentLP, null, Color.BLACK, plate);
		    						stored_plate = hisController.getStoredPlate(currentCID, Constants.VEHICLE_INSIDE);
			    					setLabel(lbStoredLP, null, Color.BLACK, stored_plate);
			    					if (plate.equals(stored_plate)) {
			    						setLabel(lbMatched, null, Color.GREEN, "Match");
			    					} else {
			    						setLabel(lbMatched, null, Color.RED, "Not match");
			    					}
			    					hisController.updateHistory(his);
		    						parkController.decreaseNoCurrentVehicle();
		    						
		    						String fee;
		    						fee = String.valueOf(_fee[0]/1000) + "k VND";
		    						setLabel(lbFee, null, Color.BROWN, fee);
		    						setLabel(lbTimeIn, null, Color.BLACK, TimeHelper.strToTime(hisController.getTimeIn(currentCID, currentFullCIDAndDateTime)));
		    						setLabel(lbTimeOut, null, Color.BLACK, TimeHelper.strToTime(currentDateTime));
		    						tfCID.clear();
		    					} else if (rbtThreeMode.isSelected()) {
		    						System.out.println(">= 3 wheeler mode");
		    						
		    						setLabel(lbNotice, null, Color.GREEN, "Card is valid.");
		    						
		    						// Time to capturing :)
		    						currentDateTime = TimeHelper.getCurrentTimeToStr();
									currentFullCIDAndDateTime = "./img_history/" + currentCID + "_" + currentDateTime + ".jpg";
		    						camController.getImage(webcam);
		    						
		    						//populate image which captured to image view
									File newRecord = new File(currentFullCIDAndDateTime);
									iv_img_out_lp.setImage(new Image(newRecord.toURI().toString()));
									
									//populate old record image which captured to image view
									File oldRecord = new File(hisController.getURLImageLPIn(currentCID, Constants.VEHICLE_INSIDE));
									iv_img_in_lp.setImage(new Image(oldRecord.toURI().toString()));
		    						
		    						his.setImgOut(null);
		    						his.setImgLPOut(currentFullCIDAndDateTime);
		    						his.setTimeOut(currentDateTime);
		    						his.setVisitStatus(Constants.VEHICLE_OUTSIDE);
		    						
		    						long[] _fee;
		    						CompanyController cc = new CompanyController();
		    						_fee = cc.getFee();
		    						his.setFee(_fee[1]);
		    						his.setCardID(currentCID);
		    						hisController.updateHistory(his);
		    						parkController.decreaseNoCurrentVehicle();
		    						
		    						String fee;
		    						fee = String.valueOf(_fee[1]/1000) + "k VND";
		    						setLabel(lbFee, null, Color.BROWN, fee);
		    						setLabel(lbTimeIn, null, Color.BLACK, TimeHelper.strToTime(hisController.getTimeIn(currentCID, currentFullCIDAndDateTime)));
		    						setLabel(lbTimeOut, null, Color.BLACK, TimeHelper.strToTime(currentDateTime));
		    						tfCID.clear();
		    					}
		    				}
		    				
		    				
		    				/*
		    				//Save guest record to customer table
		    				cus.setName("Guest");
		    				cus.setTime_created(currentCIDAndDateTime);
		    				cusController.addCustomer(cus);
		    				lastCustomerID = cusController.getLastID();
		    				
		    				//Save vehicle record to vehicle table
		    				veh.setLicensePlate(currentCIDAndDateTime);
		    				veh.setCustomerID(lastCustomerID);
		    				vehController.addVehicle(veh);
		    				
		    				//Save contract
		    				cont.setTime_created(currentDateTime);
		    				//cont.setTime_end();
		    				*/
		    			} else if (cardController.getCardType(currentCID) == Constants.CARD_FOR_CUSTOMER) {
		    				setLabel(lbType, null, Color.BLACK, "Customer with Contract");
		    				
		    				//In mode
		    				if (rbtInMode.isSelected()) {
		    					
		    					System.out.println("Entrance mode");
		    					setLabel(lbCID, null, Color.BLACK, tfCID.getText());
		    					//setLabel(lbType, null, Color.BLACK, "Customer");
		    					
		    					clearLabel(lbContractStatus);
	    						clearLabel(lbFee);
	    						clearLabel(lbName);
	    						clearLabel(lbLicensePlate);
	    						clearLabel(lbCurrentLP);
	    						clearLabel(lbStoredLP);
	    						clearLabel(lbMatched);
		    					if (hisController.checkInOutSide(currentCID, Constants.VEHICLE_INSIDE)) {   						
		    						setLabel(lbNotice, null, Color.RED, "Card with vehicle inside");
		    						iv_img_in_lp.setImage(null);
									iv_img_out_lp.setImage(null);
		    						tfCID.clear();
		    						return;
		    					} else if (parkController.isFullSlot()) {
		    						setLabel(lbNotice, null, Color.RED, "Full slot.");
		    						return;
		    					} else {
		    						//
				    				currentDateTime = TimeHelper.getCurrentTimeToStr();
									currentFullCIDAndDateTime = "./img_history/" + currentCID + "_" + currentDateTime + ".jpg";
									currentCIDAndDateTime = currentCID + "_" + currentDateTime + ".jpg";
									
									//get image from webcam
									camController.getImage(webcam);
									
									//populate image which captured to image view
									File file = new File(currentFullCIDAndDateTime);
									iv_img_in_lp.setImage(new Image(file.toURI().toString()));
									iv_img_out_lp.setImage(null);
					    			
									setLabel(lbNotice, null, Color.GREEN, "Card is valid.");
									setLabel(lbTimeIn, null, Color.BLACK, currentDateTime);
									tfCID.clear();
				    						    				
		    						
		    						his.setImgIn(null);
			    					his.setImgLPIn(currentFullCIDAndDateTime);
			    					his.setTimeIn(currentDateTime);
			    					his.setVisitStatus(Constants.VEHICLE_INSIDE);
			    					his.setCardID(currentCID);
			    					
			    					parkController.increaseNoCurrentVehicle();
			    					setLabel(lbTimeIn, null, Color.BLACK, TimeHelper.strToTime(currentDateTime));
			    					String plate = null;
			    					
			    					//System.out.println(currentCIDAndDateTime);
			    					plate = RecognitionHelper.toPlateStr(currentCIDAndDateTime); 
			    					System.out.println("plateInUsing="+plate);
			    					his.setPlateIn(plate);
			    					setLabel(lbCurrentLP, null, Color.BLACK, plate);
			    					hisController.addHistory(his);
			    					clearLabel(lbTimeOut);
		    					}
		    					
		    					//Out Mode
		    				} else if (rbtOutMode.isSelected()) {
		    					System.out.println("Exit mode");
		    					setLabel(lbCID, null, Color.BLACK, currentCID);
	    						setLabel(lbType, null, Color.BLACK, "Guest");
	    						clearLabel(lbContractStatus);
	    						clearLabel(lbFee);
	    						clearLabel(lbName);
	    						clearLabel(lbLicensePlate);
	    						clearLabel(lbTimeIn);
	    						clearLabel(lbTimeOut);
	    						clearLabel(lbCurrentLP);
	    						clearLabel(lbStoredLP);
	    						clearLabel(lbMatched);
	    						
		    					if (!hisController.checkInOutSide(currentCID, Constants.VEHICLE_INSIDE)) {
		    						
		    						setLabel(lbNotice, null, Color.RED, "Vehicle doesn't parking in the momment.");
		    						iv_img_in_lp.setImage(null);
									iv_img_out_lp.setImage(null);
		    						tfCID.clear();
		    						return;
		    					}
		    					
		    					if(rbtTwoMode.isSelected()) {
		    						System.out.println("2 wheeler mode");
		    						
		    						setLabel(lbNotice, null, Color.GREEN, "Card is valid.");
		    						
		    						// Time to capturing :)
		    						currentDateTime = TimeHelper.getCurrentTimeToStr();
		    						
									currentFullCIDAndDateTime = "./img_history/" + currentCID + "_" + currentDateTime + ".jpg";
									currentCIDAndDateTime = currentCID + "_" + currentDateTime + ".jpg";
		    						camController.getImage(webcam);
									
									//populate image which captured to image view
									File newRecord = new File(currentFullCIDAndDateTime);
									iv_img_out_lp.setImage(new Image(newRecord.toURI().toString()));
									
									//populate old record image which captured to image view
									File oldRecord = new File(hisController.getURLImageLPIn(currentCID, Constants.VEHICLE_INSIDE));
									iv_img_in_lp.setImage(new Image(oldRecord.toURI().toString()));
																		
		    						his.setImgOut(null);
		    						his.setImgLPOut(currentFullCIDAndDateTime);
		    						his.setTimeOut(currentDateTime);
		    						
		    						long[] _fee;
		    						CompanyController cc = new CompanyController();
		    						_fee = cc.getFee();
		    						his.setVisitStatus(Constants.VEHICLE_OUTSIDE);
		    						his.setFee(_fee[0]);
		    						his.setCardID(currentCID);
		    						
		    						String plate = null;
		    						String stored_plate = null;
		    						
		    						plate = RecognitionHelper.toPlateStr(currentCIDAndDateTime); 
			    					System.out.println("plateInUsing="+plate);
			    					his.setPlateOut(plate);
			    					setLabel(lbCurrentLP, null, Color.BLACK, plate);
		    						stored_plate = hisController.getStoredPlate(currentCID, Constants.VEHICLE_INSIDE);
			    					setLabel(lbStoredLP, null, Color.BLACK, stored_plate);
			    					if (plate.equals(stored_plate)) {
			    						setLabel(lbMatched, null, Color.GREEN, "Match");
			    					} else {
			    						setLabel(lbMatched, null, Color.RED, "Not match");
			    					}
			    					hisController.updateHistory(his);
		    						parkController.decreaseNoCurrentVehicle();
		    						
		    						String fee;
		    						fee = String.valueOf(_fee[0]/1000) + "k VND";
		    						setLabel(lbFee, null, Color.BROWN, fee);
		    						setLabel(lbTimeIn, null, Color.BLACK, TimeHelper.strToTime(hisController.getTimeIn(currentCID, currentFullCIDAndDateTime)));
		    						setLabel(lbTimeOut, null, Color.BLACK, TimeHelper.strToTime(currentDateTime));
		    						tfCID.clear();
		    					} else if (rbtThreeMode.isSelected()) {
		    						System.out.println(">= 3 wheeler mode");
		    						
		    						setLabel(lbNotice, null, Color.GREEN, "Card is valid.");
		    						
		    						// Time to capturing :)
		    						currentDateTime = TimeHelper.getCurrentTimeToStr();
									currentFullCIDAndDateTime = "./img_history/" + currentCID + "_" + currentDateTime + ".jpg";
		    						camController.getImage(webcam);
		    						
		    						//populate image which captured to image view
									File newRecord = new File(currentFullCIDAndDateTime);
									iv_img_out_lp.setImage(new Image(newRecord.toURI().toString()));
									
									//populate old record image which captured to image view
									File oldRecord = new File(hisController.getURLImageLPIn(currentCID, Constants.VEHICLE_INSIDE));
									iv_img_in_lp.setImage(new Image(oldRecord.toURI().toString()));
		    						
		    						his.setImgOut(null);
		    						his.setImgLPOut(currentFullCIDAndDateTime);
		    						his.setTimeOut(currentDateTime);
		    						his.setVisitStatus(Constants.VEHICLE_OUTSIDE);
		    						
		    						long[] _fee;
		    						CompanyController cc = new CompanyController();
		    						_fee = cc.getFee();
		    						his.setFee(_fee[1]);
		    						his.setCardID(currentCID);
		    						hisController.updateHistory(his);
		    						parkController.decreaseNoCurrentVehicle();
		    						
		    						String plate = null;
		    						String stored_plate = null;
		    						
		    						plate = RecognitionHelper.toPlateStr(currentCIDAndDateTime); 
			    					System.out.println("plateInUsing="+plate);
			    					his.setPlateOut(plate);
			    					setLabel(lbCurrentLP, null, Color.BLACK, plate);
		    						stored_plate = hisController.getStoredPlate(currentCID, Constants.VEHICLE_INSIDE);
			    					setLabel(lbStoredLP, null, Color.BLACK, stored_plate);
			    					if (plate.equals(stored_plate)) {
			    						setLabel(lbMatched, null, Color.GREEN, "Match");
			    					} else {
			    						setLabel(lbMatched, null, Color.RED, "Not match");
			    					}
		    						
		    						String fee;
		    						fee = String.valueOf(_fee[1]/1000) + "k VND";
		    						setLabel(lbFee, null, Color.BROWN, fee);
		    						setLabel(lbTimeIn, null, Color.BLACK, TimeHelper.strToTime(hisController.getTimeIn(currentCID, currentFullCIDAndDateTime)));
		    						setLabel(lbTimeOut, null, Color.BLACK, TimeHelper.strToTime(currentDateTime));
		    						tfCID.clear();
		    					}
		    				}
		    			}
					}
	    			
				}
			}
    		
		});
	}
	
	protected void setImageViewSize() {
		double height = 251;
		double width = 251;
		iv_img_in1.setFitHeight(height);
		iv_img_in1.setFitWidth(width);
		iv_img_in1.prefHeight(height);
		iv_img_in1.prefWidth(width);
    	iv_img_in1.setPreserveRatio(true);
    }
	
	protected void initWebcam(final int webcamIndex) {
    	Task<Void> webCamIntilizer = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
//				Dimension[] nonStandardResolutions = new Dimension[] {
//						WebcamResolution.PAL.getSize(),
//						WebcamResolution.HD720.getSize(),
//						new Dimension(1000, 500),
//						new Dimension(1000, 500),
//					};
				Dimension resolution = new Dimension(800, 800);
				if (webcam == null) {
					webcam = Webcam.getWebcams().get(webcamIndex);
//					webcam = Webcam.getDefault();
					webcam.setCustomViewSizes(new Dimension[] {resolution});
					webcam.setViewSize(resolution);
					webcam.open();
				} else {
					closeCamera();
//					webcam = Webcam.getWebcams().get(webcamIndex);
					webcam = Webcam.getDefault();
					webcam.open();
				}
				startWebcamStream();
				return null;
			}
    		
    	};
    	
    	new Thread(webCamIntilizer).start();
    	btStartCam.setDisable(true);
    }
    
    protected void startWebcamStream() {
    	stopCamera = false;
    	Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				
				while (!stopCamera) {
					try {
						if ((grabbedImage = webcam.getImage()) != null) {			
							Platform.runLater(new Runnable() {
								
								@Override
								public void run() {
									final Image mainImage = SwingFXUtils.toFXImage(grabbedImage, null);
									imageProperty.set(mainImage);
								}
							});
							grabbedImage.flush();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return null;
			}
    		
    	};
    	
    	Thread th = new Thread(task);
    	th.setDaemon(true);
    	th.start();
    	iv_img_in1.imageProperty().bind(imageProperty);
    	
    }
    
    private void closeCamera() {
    	if (webcam != null) {
    		webcam.close();
    	}
    }
    
    @FXML
    public void btStopCamAction(ActionEvent event) {
    	stopCamera = true;
    	btStartCam.setDisable(false);
    	btStopCam.setDisable(true);
    }
    
    @FXML
    public void btStartCamAction(ActionEvent event) {
    	stopCamera = false;
    	startWebcamStream();
    	btStartCam.setDisable(true);
    	btStopCam.setDisable(false);
    }   
    
    private void disposeCamera() {
    	stopCamera = true;
		closeCamera();
		Webcam.shutdown();
    }

	@Override
	public void setLabel(Label label, Pos pos, Color color, String text) {
		label.setAlignment(pos);
		label.setTextFill(color);
		label.setText(text);
		
	}

	@Override
	public void clearLabel(Label label) {
		label.setText("");
	}
    
    
}
