package hust.pms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hust.pms.controller.EmployeeController;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

public class Parking {
	private long parkingID;
	private String parkName;
	private String address;
	private int slot;
	private int no_veh;
	private long companyID;
		
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	private static final String GET_PARKING_NAME = "select parkname from Parking";
	private static final String GET_PARKING_NAME_WITHOUT_ORIGIN_DEV = "SELECT parkname FROM parking WHERE parkname NOT LIKE '%Demo%'";
	private static final String GET_PARKING_NAME_DEPEND_ON_SELECTED_COMPANY = "SELECT c.*, p.* FROM company c JOIN parking p ON c.companyid=p.companyid WHERE c.companyname=?";
	private static final String GET_PARKING_NAME_BELONG_TO_COMPANY = "SELECT parkname FROM parking WHERE companyid=?";
	private static final String GET_PARKING_ID_BY_PARKING_NAME = "select parkingid from Parking where parkname=?";
	private static final String GET_PARKING_NAME_FROM_PARKING_ID = "SELECT parkname FROM parking WHERE parkingid=?";
	private static final String GET_ALL_PARKING_BELONG_TO_COMPANY_EXCEPT_ORIGIN_DEV = "SELECT parkingid, parkname, address, slot, no_current_vehicle FROM parking WHERE parkname NOT LIKE '%Demo%' AND companyid=?";
	
	private static final String GET_NUMBER_OF_PARKING_BELONG_TO_COMPANY = "SELECT COUNT(*) FROM parking WHERE companyid=?";
	
	private static final String ADD_PARKING = "INSERT INTO parking (parkname, address, slot, companyid) VALUES (?,?,?,?)";
	
	private static final String INCREASE_NO_CURRENT_VEHICLE = "UPDATE parking SET no_current_vehicle=no_current_vehicle+1 where parkingid=?";
	private static final String DECREASE_NO_CURRENT_VEHICLE = "UPDATE parking SET no_current_vehicle=no_current_vehicle-1 where no_current_vehicle>0 && parkingid=?";
	
	private static final String IS_FULL_SLOT = "SELECT * FROM parking where parkingid=? && no_current_vehicle>=slot";
	
	//public static long currentParkingID;
	public static int noSlot;
	public static int noCurrentVehicle;
	
	
	public Parking() {
		try {
			DataAccessHelper.getInstance().parking = this;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Parking(long parkingID, String parkName, String address, int slot, int no_veh) {
		this.parkingID = parkingID;
		this.parkName = parkName;
		this.address = address;
		this.slot = slot;
		this.no_veh = no_veh;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}

	public long getParkingID() {
		return parkingID;
	}
	
	public void setParkingID(long parkingID) {
		this.parkingID = parkingID;
	}
	
	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getNo_veh() {
		return no_veh;
	}

	public void setNo_veh(int no_veh) {
		this.no_veh = no_veh;
	}

	public long getParkingIDFromParkingName(String parkName) {
		try {
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_PARKING_ID_BY_PARKING_NAME);
			ps.setString(1, parkName);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				parkingID = rs.getLong(1);
				//currentParkingID = rs.getLong(1);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return parkingID;
	}
	
	public ArrayList<String> getParkingName() {
		ArrayList<String> listParkName = new ArrayList<String>();
		try {
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_PARKING_NAME_WITHOUT_ORIGIN_DEV);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				listParkName.add(rs.getString("parkname"));
			}
		} catch (SQLException sqle) {	
			sqle.printStackTrace();
		}
		return listParkName;
	}
	
	public ArrayList<String> getParkingNameBelongToCompany(long companyID) {
		ArrayList<String> listParkName = new ArrayList<String>();
		try {
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_PARKING_NAME_BELONG_TO_COMPANY);
			ps.setLong(1, companyID);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				listParkName.add(rs.getString("parkname"));
			}
		} catch (SQLException sqle) {	
			sqle.printStackTrace();
		}
		return listParkName;
	}
	
	public ArrayList<Parking> getAllParkingBelongToCompany(long companyID) {
		ArrayList<Parking> listParking = new ArrayList<Parking>();
		try {
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_ALL_PARKING_BELONG_TO_COMPANY_EXCEPT_ORIGIN_DEV);
			ps.setLong(1, companyID);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				listParking.add(new Parking(rs.getLong("parkingid"), rs.getString("parkname"), rs.getString("address"), rs.getInt("slot"), rs.getInt("no_current_vehicle")));
			}
		} catch (SQLException sqle) {	
			sqle.printStackTrace();
		}
		return listParking;
	}
	
	public ArrayList<String> getParkingNameDependOnSelectedCompany(String companyName) {
		ArrayList<String> listParkName = new ArrayList<String>();
		try {
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_PARKING_NAME_DEPEND_ON_SELECTED_COMPANY);
			ps.setString(1, companyName);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				listParkName.add(rs.getString("parkname"));
			}
		} catch (SQLException sqle) {	
			sqle.printStackTrace();
		}
		return listParkName;
	}
	
	public String getCurrentEmployeeParkingFromParkingID() throws SQLException {
		EmployeeController ec = new EmployeeController();
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_PARKING_NAME_FROM_PARKING_ID);
		ps.setLong(1, ec.getCurrentEmployeeParkingID());
		rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getString("parkname");
		}
		return null;
	}

	public int getNumberOfParkingBelongToCompany(long companyID) throws SQLException {
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_NUMBER_OF_PARKING_BELONG_TO_COMPANY);
		ps.setLong(1, companyID);
		rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt("COUNT(*)");
		}
		return 0;
	}
	
	public void addParking() {
		try {
			PreparedStatement ps = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(ADD_PARKING);
			ps.setString(1, parkName);
			ps.setString(2, address);
			ps.setInt(3, slot);
			ps.setLong(4, Employee.currentEmployeeCompanyID);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void increaseNoCurrentVehicle() {
		try {
			PreparedStatement ps = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(INCREASE_NO_CURRENT_VEHICLE);
			ps.setLong(1, Employee.currentEmployeeParkingID);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void decreaseNoCurrentVehicle() {
		try {
			PreparedStatement ps = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(DECREASE_NO_CURRENT_VEHICLE);
			//System.out.println("currentParkingID="+currentParkingID);
			ps.setLong(1, Employee.currentEmployeeParkingID);
			
			ps.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public boolean isFullSlot() {
		boolean check = false;
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(IS_FULL_SLOT);
			ps.setLong(1, Employee.currentEmployeeParkingID);
			rs = ps.executeQuery();
			while (rs.next()) {
				check = true;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return check;
	}
}
