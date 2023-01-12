package hust.pms.controller;

import java.sql.SQLException;

import hust.pms.model.Employee;

public class LogoutController {
	public void toLogOutStatus(String userName) throws SQLException {
		Employee emp = new Employee();
    	emp.updateLoginStatus((byte) 0, userName);
	}
}
