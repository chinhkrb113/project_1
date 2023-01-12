package hust.pms.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import hust.pms.model.Company;

public class CompanyController {
	
	public ArrayList<String> getCompanyNameToLoadComboBox() {
		Company company = new Company();
		return company.getListCompanyName();
	}
	
	public ArrayList<Company> getCompanyDetail() {
		Company company = new Company();
		return company.getCompanyDetail();
	}
	
	public int getCompanyIDByCompanyName(String companyName) {
		Company company = new Company();
		return company.getCompanyIDByCompanyName(companyName);
	}
	
	public void addCompany(Company com) throws SQLException {
		com.addCompany();
	}
	
	public void updateCompany(Company com, int companyID) throws SQLException {
		com.updateCompany(companyID);
	}
	
	public void deleteCompany(int companyID) throws SQLException {
		Company com = new Company();
		com.deleteCompany(companyID);
	}
	
	public boolean isCompanyNameExist(String companyName) throws SQLException {
		Company com = new Company();
		return com.isCompanyNameExist(companyName);
	}
	
	public boolean isPhoneNumberExist(String phoneNumber) throws SQLException {
		Company com = new Company();
		return com.isPhoneNumberExist(phoneNumber);
	}
	
	public boolean isPhoneNumberExistExceptCurrent(int companyID, String inputPhoneNumber) throws SQLException {
		Company com = new Company();
		return com.isPhoneNumberExistExceptCurrent(companyID, inputPhoneNumber);
	}
	
	public int getNumberOfCompany() throws SQLException {
		Company com = new Company();
		return com.numberOfCompany();
	}
	
	public String getCurrentEmployeeCompanyFromCompanyID() throws SQLException {
		Company com = new Company();
		return com.getCurrentEmployeeCompanyFromCompanyID();
	}
	
	public long[] getFee() {
		Company com = new Company();
		return com.getFee();
	}
	
	public void changeFee(long motorcycleFee, long motorcycleMonthFee, long otherFee, long otherMonthFee) {
		Company com = new Company();
		com.changeFee(motorcycleFee, motorcycleMonthFee, otherFee, otherMonthFee);
	}
	
}
