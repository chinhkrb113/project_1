package hust.pms.view.staff;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import hust.common.Navigator;
import hust.pms.controller.HistoryController;
import hust.pms.controller.SceneController;
import hust.pms.model.Company;
import hust.pms.model.History;
import hust.pms.view.LabelHelper;
import hust.util.TimeHelper;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class HistoryForm implements Initializable, LabelHelper {

	public static long historyID;
	
	private ObservableList<History> listHistory;
	
	@FXML
    private AnchorPane anchorPane;
	
	@FXML
    private TextField tfSearch;
	
	@FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TableView<History> tbHistory;

    @FXML
    private TableColumn<History, String> col_id;

    @FXML
    private TableColumn<History, String> col_npin;

    @FXML
    private TableColumn<History, String> col_npout;

    @FXML
    private TableColumn<History, String> col_timein;

    @FXML
    private TableColumn<History, String> col_timeout;

    @FXML
    private TableColumn<History, String> col_visitstatus;

    @FXML
    private TableColumn<History, String> col_fee;

    @FXML
    private TableColumn<History, String> col_doubt;

    @FXML
    private TableColumn<History, String> col_cardid;

    @FXML
    private Button btClose;

    @FXML
    private Button btSearch;
    
    @FXML
    private Label lbNotice;
    
    @FXML
    private Button btRefresh;
    
    @FXML
    void btRefreshAction(ActionEvent event) {
    	loadTable();
    }

    @FXML
    private void btCloseAction(ActionEvent event) {
    	SceneController.getInstance().toScene(event, Navigator.FXML_STAFFCENTER);
    }

    @FXML
    private void btSearchAction(ActionEvent event) {
    	String startDateStr;
    	String endDateStr;
    	if (startDate.getValue() == null && endDate.getValue() == null) {
    		setLabel(lbNotice, null, Color.RED, "Input Date");
    		return;
    	}
    	startDateStr = startDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	endDateStr = endDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	loadTableByTime(startDateStr, endDateStr);
    }
    
    @FXML
    private void tbHistoryMouseAction(MouseEvent event) {
    	
    	if (event.getClickCount() == 2) {
    		System.out.println("You double click");
    		historyID = tbHistory.getSelectionModel().getSelectedItem().getHistoryID();
    		System.out.println(historyID);
    		SceneController.getInstance().toParallelScene(Navigator.FXML_HISTORY_DETAIL);
    	}
    	
    }
    
    private void loadTable() {
    	
    	col_id.setCellValueFactory(new PropertyValueFactory<History, String>("historyID"));
    	col_npin.setCellValueFactory(new PropertyValueFactory<History, String>("plateIn"));
    	col_npout.setCellValueFactory(new PropertyValueFactory<History, String>("plateOut"));
    	col_timein.setCellValueFactory(new PropertyValueFactory<History, String>("timeIn"));
    	col_timeout.setCellValueFactory(new PropertyValueFactory<History, String>("timeOut"));
    	col_visitstatus.setCellValueFactory(new PropertyValueFactory<History, String>("visitStatus"));
    	col_fee.setCellValueFactory(new PropertyValueFactory<History, String>("fee"));
    	col_doubt.setCellValueFactory(new PropertyValueFactory<History, String>("doubt"));
    	col_cardid.setCellValueFactory(new PropertyValueFactory<History, String>("cardID"));
    	
    	HistoryController hc = new HistoryController();
    	listHistory = FXCollections.observableArrayList(hc.getHistory());
    	tbHistory.setItems(listHistory);
    	
    }
    
    private void loadTableByTime(String startDate, String endDate) {
    	col_id.setCellValueFactory(new PropertyValueFactory<History, String>("historyID"));
    	col_npin.setCellValueFactory(new PropertyValueFactory<History, String>("plateIn"));
    	col_npout.setCellValueFactory(new PropertyValueFactory<History, String>("plateOut"));
    	col_timein.setCellValueFactory(new PropertyValueFactory<History, String>("timeIn"));
    	col_timeout.setCellValueFactory(new PropertyValueFactory<History, String>("timeOut"));
    	col_visitstatus.setCellValueFactory(new PropertyValueFactory<History, String>("visitStatus"));
    	col_fee.setCellValueFactory(new PropertyValueFactory<History, String>("fee"));
    	col_doubt.setCellValueFactory(new PropertyValueFactory<History, String>("doubt"));
    	col_cardid.setCellValueFactory(new PropertyValueFactory<History, String>("cardID"));
    	
    	HistoryController hc = new HistoryController();
    	listHistory = FXCollections.observableArrayList(hc.getHistoryByTime(startDate, endDate));
    	tbHistory.setItems(listHistory);
    }
    
    private void searchTableAndRefresh() {
    	FilteredList<History> filteredData = new FilteredList<>(listHistory, b -> true);
		
		tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(history -> {
				
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (history.getCardID().indexOf(lowerCaseFilter) != -1) {
					return true; 
				} else if (history.getPlateIn().indexOf(lowerCaseFilter) != -1){
					return true;
				} else if (history.getPlateOut().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else {
					return false;
				}
			});
		});
			
			// 3. Wrap the FilteredList in a SortedList. 
			SortedList<History> sortedData = new SortedList<>(filteredData);
			
			// 4. Bind the SortedList comparator to the TableView comparator.
			// 	  Otherwise, sorting the TableView would have no effect.
			sortedData.comparatorProperty().bind(tbHistory.comparatorProperty());
			
			// 5. Add sorted (and filtered) data to the table.
			tbHistory.setItems(sortedData);
    }
    /*
    private void searchTableByDate() {
    	FilteredList<History> filteredItems = new FilteredList<>(listHistory);
    	filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            LocalDate minDate = startDate.getValue();
            LocalDate maxDate = endDate.getValue();

            // get final values != null
            final LocalDate finalMin = minDate == null ? LocalDate.MIN : minDate;
            final LocalDate finalMax = maxDate == null ? LocalDate.MAX : maxDate;

            // values for openDate need to be in the interval [finalMin, finalMax]
            return ti -> !finalMin.isAfter(ti.getTimeIn()) && !finalMax.isBefore(ti.getOpenDate());
        },
        startDate.valueProperty(),
        endDate.valueProperty()));

    	tbHistory.setItems(filteredItems);
    }*/

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		anchorPane.requestFocus();
		loadTable();
		searchTableAndRefresh();
	}

	@Override
	public void setLabel(Label label, Pos pos, Color color, String text) {
		label.setAlignment(pos);
		label.setTextFill(color);
		label.setText(text);
		
	}

	@Override
	public void clearLabel(Label label) {
		label.setText(null);
	}

}
