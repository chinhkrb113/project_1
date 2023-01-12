package hust.pms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Customer extends User{
	private long customerID;
	private String time_created;
	
	private static final String GET_CUSTOMER = "";
	private static final String GET_CUSTOMER_BY_PHONE = "select * from customer where phonenumber=?";
	private static final String GET_CUSTOMER_ID_BY_PHONE = "select customerid from customer where phonenumber=?";
	private static final String GET_CUSTOMER_BY_EMAIL = "select * from customer where email=?";
	
	private static final String GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	private static final String ADD_CUSTOMER = "INSERT INTO customer (name, phonenumber, email, address, time_created) values (?,?,?,?,?)";
	
	private static final String SEARCH_CUSTOMER_BY_EMAIL = "select * from customer where Email=?";
	private static final String SEARCH_CUSTOMER_BY_PHONE_NUMBER = "select * from customer where phonenumber=?";
	
	public ArrayList<Customer> getCustomer() {
		ArrayList<Customer> listCustomer = new ArrayList<Customer>();
		Customer cus = new Customer();
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_CUSTOMER);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				cus.setCustomerID(rs.getLong("customerid"));
				cus.setName(rs.getString("name"));
				cus.setPhoneNumber(rs.getString("phonenumber"));
				cus.setEmail(rs.getString("email"));
				cus.setAddress(rs.getString("address"));
				
				listCustomer.add(cus);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return listCustomer;
	}
	
	public String[] getCustomer(String phoneNumber) {
		String[] listCustomer = new String[3];
		//Customer cus = new Customer();
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_CUSTOMER_BY_PHONE);
			ps.setString(1, phoneNumber);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				//cus.setCustomerID(rs.getLong("customerid"));
				listCustomer[0] = rs.getString("name");
				//cus.setPhoneNumber(rs.getString("phonenumber"));
				listCustomer[1] = rs.getString("email");
				listCustomer[2] = rs.getString("address");
				
				//listCustomer.add(cus);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return listCustomer;
	}
		
	public void addCustomer() {
		try {
			PreparedStatement ps = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(ADD_CUSTOMER);
			ps.setString(1, super.getName());
			ps.setString(2, super.getPhoneNumber());
			ps.setString(3, super.getEmail());
			ps.setString(4, super.getAddress());
			ps.setString(5, time_created);
			
			ps.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public boolean isEmailExist(String email) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(SEARCH_CUSTOMER_BY_EMAIL);
		ps.setString(1, email);
		rs = ps.executeQuery();
		while (rs.next()) {
			return true;
		}
		return false;
	}
	
	public boolean isPhoneNumberExist(String phonenumber) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(SEARCH_CUSTOMER_BY_PHONE_NUMBER);
		ps.setString(1, phonenumber);
		rs = ps.executeQuery();
		while (rs.next()) {
			return true;
		}
		return false;
	}
	
	public long getCustomerIDByPhone(String phoneNumber) {
		long id = 0;
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_CUSTOMER_ID_BY_PHONE);
			ps.setString(1, phoneNumber);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getLong("customerid");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return id;
	}
	
	public long getLastID() {
		long lastID = 0;
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			while (rs.next()) {
				lastID = rs.getLong("LAST_INSERT_ID()");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return lastID;
	}
	
	public Customer() {
		super();
	}
	
	public Customer(long customerID, String name, String phoneNumber, String email, String address, String time_created) {
		super(name, phoneNumber, email, address);
		this.customerID = customerID;
		this.time_created = time_created;
	}
	
	public String getTime_created() {
		return time_created;
	}

	public void setTime_created(String time_created) {
		this.time_created = time_created;
	}

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}
}
