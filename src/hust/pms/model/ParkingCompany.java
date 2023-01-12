package hust.pms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParkingCompany {
	private int parkingID;
	private String parkName;
	private String address;
	private int slot;
	private String companyName;
	
	private static final String FXML_GET_PARKING_COMPANY = "select p.parkingid, p.parkname, p.address, p.slot";
	
	public ParkingCompany() {};
	public ParkingCompany(int parkingID, String parkName, String address, int slot, String companyName) {
		this.parkingID = parkingID;
		this.parkName = parkName;
		this.address = address;
		this.slot = slot;
		this.companyName = companyName;
	}
	public int getParkingID() {
		return parkingID;
	}
	public void setParkingID(int parkingID) {
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
	public int getSlot() {
		return slot;
	}
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public ArrayList<ParkingCompany> getParkingCompanyToLoadTable() {
		ArrayList<ParkingCompany> listPC = new ArrayList<>();
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(FXML_GET_PARKING_COMPANY);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				listPC.add(new ParkingCompany(rs.getInt("parkingid"), rs.getString("parkname"), rs.getString("address"), rs.getInt("slot"), rs.getString("companyname")));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return listPC;
	}
}
