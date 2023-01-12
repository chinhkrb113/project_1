package hust.pms.view.admin;

import hust.common.Navigator;
import hust.pms.controller.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Statistic {

    @FXML
    private Button btNoVehicle;

    @FXML
    private Button btRevenue;

    @FXML
    private Button btSuspectVehicle;

    @FXML
    private Button btBack;

    @FXML
    void btBackAction(ActionEvent event) {
    	SceneController.getInstance().toScene(event, Navigator.FXML_ADMINCENTER);
    }

    @FXML
    void btNoVehicleAction(ActionEvent event) {
    	
    }

    @FXML
    void btRevenueAction(ActionEvent event) {
    	
    }

    @FXML
    void btSuspectVehicleAction(ActionEvent event) {
    	
    }

}
