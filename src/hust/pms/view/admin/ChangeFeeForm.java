package hust.pms.view.admin;

import java.net.URL;
import java.util.ResourceBundle;

import hust.pms.controller.CompanyController;
import hust.pms.controller.SceneController;
import hust.pms.view.LabelHelper;
import hust.pms.view.TextFieldHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class ChangeFeeForm implements LabelHelper, Initializable, TextFieldHelper {

    @FXML
    private Button btClose;

    @FXML
    private TextField tfMotorcycleFee;

    @FXML
    private TextField tfOtherFee;

    @FXML
    private Label lbNotice;

    @FXML
    private Button btChange;
    
    @FXML
    private TextField tfMotorcycleMonthFee;

    @FXML
    private TextField tfOtherMonthFee;


    @FXML
    void btCloseAction() {
    	SceneController.getInstance().closeSceneWithStageRelatedButton(btClose);;
    }

    @FXML
    void btChangeAction(ActionEvent event) {
    	allFilled(); 	
    }

	@Override
	public void setLabel(Label label, Pos pos, Color color, String text) {
		label.setAlignment(pos);
		label.setTextFill(color);
		label.setText(text);
	}

	@Override
	public void clearLabel(Label label) {
		// TODO Auto-generated method stub
		
	}
	
	private void loadTextField() {
		long[] fee;
		CompanyController cc = new CompanyController();
		fee = cc.getFee();
		tfMotorcycleFee.setText(String.valueOf(fee[0]));
		tfMotorcycleMonthFee.setText(String.valueOf(fee[1]));
		tfOtherFee.setText(String.valueOf(fee[2]));
		tfOtherMonthFee.setText(String.valueOf(fee[3]));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadTextField();
		
	}

	@Override
	public void allFilled() {
		if (tfMotorcycleFee.getText() == null || tfMotorcycleFee.getText().trim().isEmpty()) {
    		setLabel(lbNotice, null, Color.RED, "Please fill Motorcycle Fee");
    		
    	} else if (tfMotorcycleMonthFee.getText() == null || tfMotorcycleMonthFee.getText().trim().isEmpty()) {
    		setLabel(lbNotice, null, Color.RED, "Please fill Motorcycle Month Fee");
    		
    	} else if (tfOtherFee.getText() == null || tfOtherFee.getText().trim().isEmpty()) {
    		setLabel(lbNotice, null, Color.RED, "Please fill Car, small truck Fee.");
    		
    	} else if (tfOtherMonthFee.getText() == null || tfOtherMonthFee.getText().trim().isEmpty()) {
    		setLabel(lbNotice, null, Color.RED, "Please fill Car, small truck Month Fee.");
    		
    	} else {
    		allCorrectFormat();
    	}
		
	}

	@Override
	public void allCorrectFormat() {
		byte count = 0;
		try {
			Long.parseLong(tfMotorcycleFee.getText());
			Long.parseLong(tfMotorcycleMonthFee.getText());
			Long.parseLong(tfOtherFee.getText());
			Long.parseLong(tfOtherMonthFee.getText());
		} catch (NumberFormatException nfe) {
			count = 1;
			nfe.printStackTrace();
			setLabel(lbNotice, null, Color.ORANGE, "Please input number.");
		}
		if (count == 0) {
			changeFee();
		}
	}

	@Override
	public void clearField(TextField textField) {
		// TODO Auto-generated method stub
		
	}
	
	public void changeFee() {
		CompanyController comController = new CompanyController();
		long motorcycleFeeValue = Long.parseLong(tfMotorcycleFee.getText());
    	long motorcycleMonthFeeValue = Long.parseLong(tfMotorcycleMonthFee.getText());
    	long otherFeeValue = Long.parseLong(tfOtherFee.getText());
    	long otherMonthFeeValue = Long.parseLong(tfOtherMonthFee.getText());
		comController.changeFee(motorcycleFeeValue, motorcycleMonthFeeValue, otherFeeValue, otherMonthFeeValue);
		setLabel(lbNotice, null, Color.GREEN, "Change successfully.");
	}

}
