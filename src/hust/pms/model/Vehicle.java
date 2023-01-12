package hust.pms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle {
	//private String color;
	private String licensePlate;
	private String type;
	private String brand;
	private String model;
	private String color;
	private String status;
	
	private long customerID;
	
	private static final String ADD_VEHICLE = "INSERT INTO vehicle (licenseplate,type,model,color,customerid) VALUES (?,?,?,?,?)";
	
	private static final String SEARCH_VEHICLE_BY_PLATE = "select * from vehicle where licenseplate=?";
	
	public Vehicle() {}
	
	/**
	 * @param licensePlate
	 * @param type
	 * @param brand
	 * @param model
	 * @param color
	 * @param status
	 * @param customerID
	 */
	public Vehicle(String licensePlate, String type, String brand, String model, String color, String status,
			long customerID) {
		this.licensePlate = licensePlate;
		this.type = type;
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.status = status;
		this.customerID = customerID;
	}
	
	public Vehicle(String licensePlate, long customerID) {
		this.licensePlate = licensePlate;
		this.customerID = customerID;
	}
	
	public void addVehicle() {
		try {
			PreparedStatement ps = null;
			
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(ADD_VEHICLE);
			ps.setString(1, licensePlate);
			ps.setString(2, type);
			ps.setString(3, brand);
			ps.setString(4, color);
			ps.setLong(5, customerID);
			
			ps.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public boolean isPlateExist(String plate) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(SEARCH_VEHICLE_BY_PLATE);
		ps.setString(1, plate);
		rs = ps.executeQuery();
		while (rs.next()) {
			return true;
		}
		return false;
	}
	
	public String getLicensePlate() {
		return licensePlate;
	}
	public long getCustomerID() {
		return customerID;
	}
	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	
}
