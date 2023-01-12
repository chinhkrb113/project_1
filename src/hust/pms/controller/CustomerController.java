package hust.pms.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import hust.pms.model.Customer;

public class CustomerController {
	public String[] getCustomer(String phone) {
		Customer cus = new Customer();
		return cus.getCustomer(phone);
	}
	
	public void addCustomer(Customer cus) {
		cus.addCustomer();
	}
	
	public long getLastID() {
		Customer cus = new Customer();
		return cus.getLastID();
	}
	
	public long getCustomerIDByPhone(String phoneNumber) {
		Customer cus = new Customer();
		return cus.getCustomerIDByPhone(phoneNumber);
	}
	
	public boolean isEmailExist(String email) throws SQLException {
		Customer cus = new Customer();
		return cus.isEmailExist(email);
	}
	
	public boolean isPhoneNumberExist(String phoneNumber) throws SQLException {
		Customer cus = new Customer();
		return cus.isPhoneNumberExist(phoneNumber);
	}
	
}
