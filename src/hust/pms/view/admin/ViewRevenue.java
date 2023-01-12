package hust.pms.view.admin;

import java.net.URL;
import java.util.ResourceBundle;

import hust.common.Constants;
import hust.common.Navigator;
import hust.pms.controller.SceneController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class ViewRevenue implements Initializable {

    @FXML
    private Label lbAllRevenue;

    @FXML
    private Button btBack;

    @FXML
    private Button btViewChart;

    @FXML
    private ComboBox<String> cbYear;

    @FXML
    private BarChart<Number, Number> barChart;
    
    @FXML
    void btBackAction(ActionEvent event) {
    	SceneController.getInstance().toScene(event, Navigator.FXML_STATISTIC_CENTER);
    }

    @FXML
    void btViewChartAction(ActionEvent event) {
    	
    }
    
    private void loadComboBox() {
    	
    }
    
    private void loadBarChart() {
    	CategoryAxis xAxis = new CategoryAxis();
    	xAxis.setLabel("Year");
    	NumberAxis yAxis = new NumberAxis();
    	yAxis.setLabel("Revenue");
    	barChart = new BarChart(xAxis, yAxis);
    	barChart.setTitle("Revenue of Years");
    	//ObservableList<XYChart.Data<Integer, Double>> data = ha
    	XYChart.Series<Integer, Double> series = new XYChart.Series<>();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadComboBox();
		
	}

}
