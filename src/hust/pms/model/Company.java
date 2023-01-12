package hust.pms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hust.pms.controller.EmployeeController;

public class Company {
	
	private int companyID;
	private String companyName;
	private String address;
	private String phoneNumber;
	
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	private static final String GET_COMPANY_WITHOUT_ORIGIN_DEV = "select * from Company WHERE companyname NOT LIKE '%Demo%' ORDER BY companyname ASC";
	private static final String GET_COMPANY_NAME_WITHOUT_ORIGIN_DEV = "SELECT companyname FROM company WHERE companyname NOT LIKE '%Demo%'";
	private static final String GET_COMPANY_FROM_COMPANY_ID = "SELECT companyname FROM company WHERE companyid=?";
	
	private static final String GET_NUMBER_OF_COMPANY = "SELECT COUNT(*) FROM company WHERE companyid!=10";
	
	private static final String GET_FEE = "SELECT fee_motorcycle, fee_month_motorcycle, fee_others, fee_month_others from company where companyid=?";
	
	private static final String SEARCH_COMPANY_ID_FROM_COMPANY_NAME = "select companyid from Company where companyname=?";
	private static final String SEARCH_PHONE_NUMBER_BY_COMPANY_ID = "select * from Company where companyid=? and phonenumber=?";
	
	private static final String COMPANY_NAME_EXIST = "select * from Company where companyname=?";
	private static final String PHONE_NUMBER_EXIST = "select * from Company where phonenumber=?";
	
	
	private static final String ADD_COMPANY = "insert into Company (companyname, address, phonenumber) values (?,?,?)";
	
	private static final String UPDATE_COMPANY = "update Company set companyname=?, address=?, phonenumber=? where companyid=?";
	private static final String UPDATE_FEE = "UPDATE Company set fee_motorcycle=?, fee_month_motorcycle=?, fee_others=?, fee_month_others=? where companyid=?";
	
	private static final String DELETE_COMPANY = "delete from Company where companyid=? where companyid=?";
	
	public Company() {
		try {
			DataAccessHelper.getInstance().company = this;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public Company(int companyID, String companyName, String address, String phoneNumber) {
		this.companyID = companyID;
		this.companyName = companyName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	public int getCompanyIDByCompanyName(String companyname) {
		try {
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(SEARCH_COMPANY_ID_FROM_COMPANY_NAME);
			ps.setString(1, this.companyName = companyname);
			rs = ps.executeQuery();
			while (rs.next()) {
				companyID = rs.getInt(1);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return companyID;
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyid) {
		this.companyID = companyid;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public ArrayList<String> getListCompanyName() {
		ArrayList<String> listCompany = new ArrayList<String>();
		//Company company = new Company();
		try {
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_COMPANY_NAME_WITHOUT_ORIGIN_DEV);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				listCompany.add(rs.getString("companyname"));
				//listCompany.add(company);
			} 
		} catch (SQLException sqle) {
			//Logger.getLogger(Company.class.getName()).log(Level.ERROR, sqle);
			sqle.printStackTrace();
		}
		return listCompany;
	}
	
	public ArrayList<Company> getCompanyDetail() {
		ArrayList<Company> listCompany = new ArrayList<Company>();
		try {
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_COMPANY_WITHOUT_ORIGIN_DEV);
			rs = ps.executeQuery();
			
			while (rs.next()) {
//				System.out.println("id="+rs.getInt("companyid"));
//				System.out.println("companyname="+rs.getString("companyname"));
				//System.out.println();
				listCompany.add(new Company(rs.getInt("companyid"), rs.getString("companyname"), rs.getString("address"), rs.getString("phonenumber")));
			}
			System.out.println("listCompany=" + listCompany);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return listCompany;
	}
	
	public void addCompany() throws SQLException {
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(ADD_COMPANY);
		ps.setString(1, companyName);
		ps.setString(2, address);
		ps.setString(3, phoneNumber);
		
		ps.executeUpdate();
	}
	
	public void updateCompany(int companyID) throws SQLException {
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(UPDATE_COMPANY);
		ps.setString(1, companyName);
		ps.setString(2, address);
		ps.setString(3, phoneNumber);
		ps.setInt(4, companyID);
		ps.executeUpdate();
	}
	
	public void deleteCompany(int companyID) throws SQLException {
		System.out.println("deleteToModel");
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(DELETE_COMPANY);
		ps.setInt(1, companyID);
		ps.executeUpdate();
	}

	public boolean isCompanyNameExist(String companyName) throws SQLException {
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(COMPANY_NAME_EXIST);
		ps.setString(1, companyName);
		rs = ps.executeQuery();
		while (rs.next()) {
			return true;
		}
		return false;
	}
	
	public boolean isPhoneNumberExist(String phoneNumber) throws SQLException {
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(PHONE_NUMBER_EXIST);
		ps.setString(1, phoneNumber);
		rs = ps.executeQuery();
		while (rs.next()) {
			return true;
		}
		return false;
	}
	
	public boolean isPhoneNumberExistExceptCurrent(int selectedCompanyID, String inputPhoneNumber) throws SQLException {
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(SEARCH_PHONE_NUMBER_BY_COMPANY_ID);
		ps.setInt(1, selectedCompanyID);
		ps.setString(2, inputPhoneNumber);
		rs = ps.executeQuery();
		while (rs.next()) {
			return true;
		}
		return false;
	}
	
	public int numberOfCompany() throws SQLException {
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_NUMBER_OF_COMPANY);
		rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt("COUNT(*)");
		}
		return 0;
	}
	
	public String getCurrentEmployeeCompanyFromCompanyID() throws SQLException {
		EmployeeController ec = new EmployeeController();
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_COMPANY_FROM_COMPANY_ID);
		ps.setLong(1, ec.getCurrentEmployeeCompanyID());
		rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getString("companyname");
		}
		return null;
	}
	
	public long[] getFee() {
		long[] fee;
		EmployeeController ec = new EmployeeController();
		fee = new long[4];
		try {
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_FEE);
			ps.setLong(1, ec.getCurrentEmployeeCompanyID());
			rs = ps.executeQuery();
			while (rs.next()) {
				fee[0] = rs.getLong("fee_motorcycle");
				fee[1] = rs.getLong("fee_month_motorcycle");
				fee[2] = rs.getLong("fee_others");
				fee[3] = rs.getLong("fee_month_others");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			//System.err.println(sqle.getErrorCode() + sqle.toString());
		}
		return fee;
	}
	
	public void changeFee(long fee_motorcycle, long month_fee_motorcycle, long fee_others, long month_fee_others) {
		EmployeeController ec = new EmployeeController();
		try {
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(UPDATE_FEE);
			ps.setLong(1, fee_motorcycle);
			ps.setLong(2, month_fee_motorcycle);
			ps.setLong(3, fee_others);
			ps.setLong(4, month_fee_others);
			ps.setLong(5, ec.getCurrentEmployeeCompanyID());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
	}
}
