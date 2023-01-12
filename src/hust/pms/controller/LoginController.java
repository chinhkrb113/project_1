package hust.pms.controller;

import java.sql.SQLException;

import hust.pms.model.Employee;
import hust.pms.model.Role;
import hust.util.AuthService;

public class LoginController {
	
	
	//AccountHelper ah = new AccountHelper();
	
	public byte getLogin(String username, String password) {
		Employee emp = new Employee();
		return emp.getLogin(username, password);
	}
	
	public boolean validPassword(String currentPass, String storedPass) {
		return AuthService.getInstance().validPassword(currentPass, storedPass);
	}
	
	public String getRoleNameByUserName(String userName) {
		Role role = new Role();
		return role.getRoleNameByUserName(userName);
	}
	
	public int getRoleNoByUserName(String userName) {
		Role role = new Role();
		return role.getRoleNoByUserName(userName);
	}
	
	public String getUserCurrent() {
		return Employee.currentUser;
	}
	
	public int getUserRoleCurrent() {
		return Employee.currentUserRoleID;
	}
	
	public void toLogInStatus(String userName) throws SQLException {
		Employee emp = new Employee();
    	emp.updateLoginStatus((byte) 0, userName);
	}
	
	public byte getLogInStatus(String username) throws SQLException {
    	Employee emp = new Employee();
    	return emp.getLoginStatus(username);
    }
	
	
}
