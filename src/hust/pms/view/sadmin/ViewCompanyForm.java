package hust.pms.view.sadmin;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hust.common.Navigator;
import hust.pms.controller.CompanyController;
import hust.pms.controller.LogoutController;
import hust.pms.controller.SceneController;
import hust.pms.model.Company;
import hust.pms.model.Employee;
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
import javafx.scene.input.MouseEvent;

public class ViewCompanyForm implements Initializable {
	
    @FXML
    private TableView<Company> companyTable;

    private ObservableList<Company> companyList;
    
    @FXML
    private TableColumn<Company, String> col_id;

    @FXML
    private TableColumn<Company, String> col_name;

    @FXML
    private TableColumn<Company, String> col_address;

    @FXML
    private TableColumn<Company, String> col_phone;

    @FXML
    private TextField tfSearch;

    @FXML
    private Button btBack;

    @FXML
    private Button btAdd;

    @FXML
    private Button btLogout;
    
    public ViewCompanyForm() {
		SceneController.getInstance().sAdminCompanyForm = this;
	}
    
    public static int selectedCompanyID;
    public static String selectedCompanyName;
    public static String selectedAddress;
    public static String selectedPhoneNumber;

    @FXML
    void btAddAction() {
    	SceneController.getInstance().toParallelScene(Navigator.FXML_ADDCOMPANY);
    }

    @FXML
    void btBackAction(ActionEvent event) {
    	SceneController.getInstance().toScene(event, Navigator.FXML_SUPERIOR_ADMINCENTER);
    }

    @FXML
    void btLogoutAction(ActionEvent event) throws SQLException {
    	LogoutController loc = new LogoutController();
    	loc.toLogOutStatus(Employee.currentUserName);
    	SceneController.getInstance().toScene(event, Navigator.FXML_LOGIN);
    }
    
    @FXML
    void companyTableActionMouseClicked(MouseEvent event) {
    	if (event.getClickCount() == 2) {
    		selectedCompanyID = companyTable.getSelectionModel().getSelectedItem().getCompanyID();
    		selectedCompanyName = companyTable.getSelectionModel().getSelectedItem().getCompanyName();
    		selectedAddress = companyTable.getSelectionModel().getSelectedItem().getAddress();
    		selectedPhoneNumber = companyTable.getSelectionModel().getSelectedItem().getPhoneNumber();
    		System.out.println("selectedCompanyID="+selectedCompanyID);
    		SceneController.getInstance().toParallelScene(Navigator.FXML_MODIFYCOMPANY);
    	}
    }
    
    private void searchTableAndRefresh() {
    	FilteredList<Company> filteredData = new FilteredList<>(companyList, b -> true);
		
		tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(company -> {
				
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (company.getCompanyName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				} else if (company.getAddress().toLowerCase().indexOf(lowerCaseFilter)!=-1) {
					return true;
				} else if (company.getPhoneNumber().indexOf(lowerCaseFilter)!=-1) {
					return true;
				} else {
					return false;
				}
			});
		});
			
			// 3. Wrap the FilteredList in a SortedList. 
			SortedList<Company> sortedData = new SortedList<>(filteredData);
			
			// 4. Bind the SortedList comparator to the TableView comparator.
			// 	  Otherwise, sorting the TableView would have no effect.
			sortedData.comparatorProperty().bind(companyTable.comparatorProperty());
			
			// 5. Add sorted (and filtered) data to the table.
			companyTable.setItems(sortedData);
    }
    
    private void loadTable() {
    	col_id.setCellValueFactory(new PropertyValueFactory<Company, String>("companyID"));
    	col_name.setCellValueFactory(new PropertyValueFactory<Company, String>("companyName"));
    	col_address.setCellValueFactory(new PropertyValueFactory<Company, String>("address"));
    	col_phone.setCellValueFactory(new PropertyValueFactory<Company, String>("phoneNumber"));
    	
    	CompanyController cc = new CompanyController();
    	companyList = FXCollections.observableArrayList(cc.getCompanyDetail());
    	companyTable.setItems(companyList);
    }
    
    public void refreshTable() {
    	loadTable();
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadTable();
		searchTableAndRefresh();
		
	}

}
