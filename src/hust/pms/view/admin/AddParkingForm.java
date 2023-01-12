package hust.pms.view.admin;

import hust.common.Navigator;
import hust.pms.controller.ParkingController;
import hust.pms.controller.SceneController;
import hust.pms.model.Parking;
import hust.pms.view.LabelHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class AddParkingForm implements LabelHelper {

    @FXML
    private Button btClose;

    @FXML
    private Button btAdd;

    @FXML
    private Button btClear;

    @FXML
    private TextField tfName;
    
    private String name;

    @FXML
    private TextArea tfAddress;
    
    private String address;

    @FXML
    private TextField tfSlot;
    
    @FXML
    private Label lbNotice;

    @FXML
    private void btAddAction() {
    	Parking p = new Parking();
    	String slot;
    	name = tfName.getText();
    	address = tfAddress.getText();
    	slot = tfSlot.getText();
    	if (name == null || name.trim().isEmpty()) {
    		setLabel(lbNotice, null, Color.RED, "Please fill parking name.");
			System.out.println("parking name is empty");
			return;
    	} else {
    		p.setParkName(tfName.getText());
    	}
    	
    	if (address == null || address.trim().isEmpty()) {
			setLabel(lbNotice, null, Color.RED, "Please fill address.");
			System.out.println("address is empty");
			return;
    	} else {
    		p.setAddress(tfAddress.getText());
    	}
    	
    	if (slot == null || slot.trim().isEmpty()) {
    		setLabel(lbNotice, null, Color.RED, "Please fill slot.");
			System.out.println("slot is empty");
			return;
    	} else {
    		p.setSlot(Integer.parseInt(slot));
    	}
    	
    	ParkingController pc = new ParkingController();
    	pc.addParking(p);
    	setLabel(lbNotice, null, Color.GREEN, "Add parking successfully.");
    	
		SceneController.getInstance().aParkingForm.refreshTable();
		System.out.println("Add parking successfully.");
    }

    @FXML
    private void btCloseAction() {
    	SceneController.getInstance().closeSceneWithStageRelatedButton(btClose);
    }

    @FXML
    private void btClearAction() {
    	tfName.clear();
    	tfAddress.clear();
    	tfSlot.clear();
    }

	@Override
	public void setLabel(Label label, Pos pos, Color color, String text) {
		label.setTextFill(color);
    	label.setText(text);
		
	}

	@Override
	public void clearLabel(Label label) {
		// TODO Auto-generated method stub
		
	}

}
