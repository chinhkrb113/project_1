package hust.pms.controller;

import java.sql.SQLException;

import hust.pms.model.Vehicle;

public class VehicleController {
	
	public void addVehicle(Vehicle veh) {
		veh.addVehicle();
	}
	
	public boolean isVehicleExist(String plate) throws SQLException {
		Vehicle veh = new Vehicle();
		return veh.isPlateExist(plate);
	}	
}
