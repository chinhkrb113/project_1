package hust.pms.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import hust.pms.model.Employee;
import hust.pms.model.Parking;

public class ParkingController {
	public ArrayList<String> getParkingNameBelongToCompanyToLoadComboBox() {
		Parking parking = new Parking();
		return parking.getParkingNameBelongToCompany(Employee.currentEmployeeCompanyID);
	}
	
	public ArrayList<String> getParkingNameDependOnSelectedCompanyToLoadComboBox(String companyName) {
		Parking parking = new Parking();
		return parking.getParkingNameDependOnSelectedCompany(companyName);
	}
	
	public long getParkingID(String parkName) {
		Parking parking = new Parking();
		return parking.getParkingIDFromParkingName(parkName);
	}
	
	public String getCurrentEmployeeParkingFromParkingID() throws SQLException {
		Parking p = new Parking();
		return p.getCurrentEmployeeParkingFromParkingID();
	}
	
	public long getNumberOfParkingBelongToCompany(long companyID) throws SQLException {
		Parking p = new Parking();
		return p.getNumberOfParkingBelongToCompany(companyID);
	}
	
	public ArrayList<Parking> getAllParkingBelongToCompany() {
		Parking p = new Parking();
		System.out.println(Employee.currentEmployeeCompanyID);
		return p.getAllParkingBelongToCompany(Employee.currentEmployeeCompanyID);
	}
	
	public void addParking(Parking p) {
		p.addParking();
	}
	
	public void increaseNoCurrentVehicle() {
		Parking p = new Parking();
		p.increaseNoCurrentVehicle();
	}
	
	public void decreaseNoCurrentVehicle() {
		Parking p = new Parking();
		p.decreaseNoCurrentVehicle();
	}
	
	public boolean isFullSlot() {
		Parking p = new Parking();
		return p.isFullSlot();
	}
}
