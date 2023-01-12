package hust.pms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hust.pms.controller.CompanyController;

public class Contract {
	private long contractID;
	private String time_created;
	private String time_end;
	private long contractValue;
	private byte status;
	private String cardID;
	private String licenseplate;
	private long customerID;
	
	private static final String ADD_CONTRACT = "insert into contract (time_created, time_end, contractvalue, status, cardid, licenseplate, customerid) values (?,?,?,?,?,?,?)";
	
	public Contract() {};
	public Contract(long contractID, String time_created, String time_end, String cardID, String licenseplate,
			long customerID) {
		
		this.contractID = contractID;
		this.time_created = time_created;
		this.time_end = time_end;
		this.cardID = cardID;
		this.licenseplate = licenseplate;
		this.customerID = customerID;
	}
	
	public void addContract() {
		try {
			PreparedStatement ps = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(ADD_CONTRACT);
			ps.setString(1, time_created);
			ps.setString(2, time_end);
			ps.setLong(3, contractValue);
			ps.setByte(4, status);
			ps.setString(5, cardID);
			ps.setString(6, licenseplate);
			ps.setLong(7, customerID);
			ps.executeUpdate();
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public long motorMonthFeeCalculate(int month) {
		CompanyController comCon = new CompanyController();
		long fee = 0;
		long _fee = comCon.getFee()[1];
		
		if (month < 3) {
			fee = month * _fee;
		} else if (month >= 3 || month <= 5) {
			fee = month * _fee - month * _fee * (10/100); 
		} else if (month > 5) {
			fee = month * _fee - month * _fee * (15/100); 
		}
		return fee; 
	}
	
	public long otherMonthFeeCalculate(int month) {
		CompanyController comCon = new CompanyController();
		long fee = 0;
		long _fee = comCon.getFee()[3];
		
		if (month < 3) {
			fee = month * _fee;
		} else if (month >= 3 || month <= 5) {
			fee = month * _fee - month * _fee * (10/100); 
		} else if (month > 5) {
			fee = month * _fee - month * _fee * (15/100); 
		}
		return fee;  
	}
	
	public void isPhoneNumberDuplicate() {
		
	}
	public long getContractID() {
		return contractID;
	}
	public void setContractID(long contractID) {
		this.contractID = contractID;
	}
	public String getTime_created() {
		return time_created;
	}
	public void setTime_created(String time_created) {
		this.time_created = time_created;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public long getContractValue() {
		return contractValue;
	}
	public void setContractValue(long contractValue) {
		this.contractValue = contractValue;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public String getCardID() {
		return cardID;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	public String getLicenseplate() {
		return licenseplate;
	}
	public void setLicenseplate(String licenseplate) {
		this.licenseplate = licenseplate;
	}
	public long getCustomerID() {
		return customerID;
	}
	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}	
}
