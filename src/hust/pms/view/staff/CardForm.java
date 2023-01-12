package hust.pms.view.staff;

import java.net.URL;
import java.util.ResourceBundle;

import hust.pms.controller.CardController;
import hust.pms.controller.SceneController;
import hust.pms.model.Card;
import hust.pms.model.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class CardForm implements Initializable {

    @FXML
    private TextField tfCardID;

    @FXML
    private Button btAdd;

    @FXML
    private Button btClose;
    
    @FXML
    private Label lbNotice;

    @FXML
    void btAddAction() {
    	Card c = new Card();
    	CardController cc = new CardController();
    	if (tfCardID.getText() == null || tfCardID.getText().trim().isEmpty()) {
    		lbNotice.setTextFill(Color.RED);
    		lbNotice.setText("Card ID has to fill");
    		return;
    	} else if (cc.isCardExist(tfCardID.getText())) {
    		lbNotice.setTextFill(Color.RED);
    		lbNotice.setText("Card "+tfCardID.getText()+" is exist.");
    		tfCardID.clear();
    		return;
    	} else {
    		c.setCardID(tfCardID.getText());
    		c.setCompanyID(Employee.currentEmployeeCompanyID);
    		c.setStatus((byte) 1);
    	}
    	
    	cc.addCard(c);
    	lbNotice.setTextFill(Color.GREEN);
		lbNotice.setText("Add card " + tfCardID.getText() + " successfully.");
    	System.out.println("Add card successfully.");
    	tfCardID.clear();
    }
    
    @FXML
    private void enterPressed() {
    	btAdd.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
    		if (ev.getCode() == KeyCode.ENTER) {
    			btAdd.fire();
    			tfCardID.clear();
    			ev.consume();
    		}
    	});
    }

    @FXML
    void btCloseAction() {
    	SceneController.getInstance().closeSceneWithStageRelatedButton(btClose);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		enterPressed();
	}

}
