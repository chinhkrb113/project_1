package hust.pms.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import hust.pms.model.Employee;

public class EmployeeController {
	
	public ArrayList<Employee> getEmployee() {
		Employee emp = new Employee();
		return emp.getEmployee();
	}
	
	public ArrayList<Employee> getStaffToLoadTable() {
		Employee emp = new Employee();
		return emp.getStaffToLoadTable(Employee.currentEmployeeCompanyID);
	}
	
	public ArrayList<Employee> getSAdminToLoadTable() {
		Employee emp = new Employee();
		return emp.getSAdminToLoadTable();
	}
	
	public ArrayList<Employee> getAdminToLoadTable() {
		Employee emp = new Employee();
		return emp.getAdminToLoadTable();
	}
	
	public void addStaff(Employee emp) throws ClassNotFoundException, SQLException {
		//Employee emp = new Employee();
		//emp.addStaff(name, gender, birthDate, phonenumber, email, address, username, password, companyid, parkingid, roleid);
		emp.addStaff();
	}
	
	public void updateStaff(Employee emp, int employeeID) throws ClassNotFoundException, SQLException {
		emp.updateStaff(employeeID);
	}
	
	public void deleteStaff(int employeeID) throws SQLException {
		Employee emp = new Employee();
		emp.deleteStaff(employeeID);
	}
	
	public boolean isEmailExist(String email) throws SQLException {
		Employee emp = new Employee();
		return emp.isEmailExist(email);
	}
	
	public boolean isEmailExistExceptCurrent(int employeeID, String email) throws SQLException {
		Employee emp = new Employee();
		return emp.isEmailExistExceptCurrent(employeeID, email);
	}
	
	public boolean isPhoneNumberExist(String phoneNumber) throws SQLException {
		Employee emp = new Employee();
		return emp.isPhoneNumberExist(phoneNumber);
	}
	
	public boolean isPhoneNumberExistExceptCurrent(int employeeID, String phoneNumber) throws SQLException {
		Employee emp = new Employee();
		return emp.isPhoneNumberExistExceptCurrent(employeeID, phoneNumber);
	}
	
	public boolean userNameExist(String userName) throws SQLException {
		Employee emp = new Employee();
		return emp.isUserNameExist(userName);
	}
	
	public int getNumberOfAdmin() throws SQLException {
		Employee emp = new Employee();
		return emp.numberOfAdmin();
	}
	
	public int getNumberOfSAdmin() throws SQLException {
		Employee emp = new Employee();
		return emp.numberOfSAdmin();
	}	
	
	public int getNumberOfStaffBelongToCompany(long companyID) throws SQLException {
		Employee emp = new Employee();
		return emp.numberOfStaffBelongToCompany(companyID);
	}
	
	public long getCurrentEmployeeCompanyID() {
		return Employee.currentEmployeeCompanyID;
	}
	
	public long getCurrentEmployeeParkingID() {
		return Employee.currentEmployeeParkingID;
	}
}
