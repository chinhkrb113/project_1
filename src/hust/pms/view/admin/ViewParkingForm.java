package hust.pms.view.admin;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hust.common.Navigator;
import hust.pms.controller.LogoutController;
import hust.pms.controller.ParkingController;
import hust.pms.controller.SceneController;
import hust.pms.model.Employee;
import hust.pms.model.Parking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class ViewParkingForm implements Initializable{
	
	public ViewParkingForm() {
		SceneController.getInstance().aParkingForm = this;
	}
	private ObservableList<Parking> parkingList;
	
    @FXML
    private Button btBack;

    @FXML
    private Button btAdd;

    @FXML
    private Button btLogout;

    @FXML
    private TableView<Parking> parkingTable;

    @FXML
    private TableColumn<Parking, String> col_id;

    @FXML
    private TableColumn<Parking, String> col_name;

    @FXML
    private TableColumn<Parking, String> col_address;

    @FXML
    private TableColumn<Parking, String> col_slot;
    
    @FXML
    private TableColumn<Parking, String> col_noveh;


    @FXML
    private TextField tfSearch;

    @FXML
    private void btAddAction() {
    	SceneController.getInstance().toParallelScene(Navigator.FXML_ADD_PARKING);
    }

    @FXML
    private void btBackAction(ActionEvent event) {
    	SceneController.getInstance().toScene(event, Navigator.FXML_ADMINCENTER);
    }

    @FXML
    private void btLogoutAction(ActionEvent event) throws SQLException {
    	LogoutController loc = new LogoutController();
    	loc.toLogOutStatus(Employee.currentUserName);
		SceneController.getInstance().toScene(event, Navigator.FXML_LOGIN);
    }
    
    private void loadTable() {
    	col_id.setCellValueFactory(new PropertyValueFactory<Parking, String>("parkingID"));
    	col_name.setCellValueFactory(new PropertyValueFactory<Parking, String>("parkName"));
    	col_address.setCellValueFactory(new PropertyValueFactory<Parking, String>("address"));
    	col_slot.setCellValueFactory(new PropertyValueFactory<Parking, String>("slot"));
    	col_noveh.setCellValueFactory(new PropertyValueFactory<Parking, String>("no_veh"));
    	
    	ParkingController pc = new ParkingController();
    	parkingList = FXCollections.observableArrayList(pc.getAllParkingBelongToCompany());
    	parkingTable.setItems(parkingList);
    }
    
    public void refreshTable() {
    	loadTable();
    }
    
    private void searchTableAndRefresh() {
    	FilteredList<Parking> filteredData = new FilteredList<>(parkingList, b -> true);
		
		tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(parking -> {
				
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (parking.getParkingID() != -1) {
					return true; 
				} else if (parking.getParkName().toLowerCase().indexOf(lowerCaseFilter)!=-1) {
					return true;
				} else if (parking.getAddress().toLowerCase().indexOf(lowerCaseFilter)!=-1) {
					return true;
				} else if (parking.getSlot()!=-1) {
					return true;
				} else {
					return false;
				}
			});
		});
			
			// 3. Wrap the FilteredList in a SortedList. 
			SortedList<Parking> sortedData = new SortedList<>(filteredData);
			
			// 4. Bind the SortedList comparator to the TableView comparator.
			// 	  Otherwise, sorting the TableView would have no effect.
			sortedData.comparatorProperty().bind(parkingTable.comparatorProperty());
			
			// 5. Add sorted (and filtered) data to the table.
			parkingTable.setItems(sortedData);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadTable();
		searchTableAndRefresh();
	}

}
