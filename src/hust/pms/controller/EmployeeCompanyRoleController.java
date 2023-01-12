package hust.pms.controller;

import java.util.ArrayList;

import hust.pms.model.EmployeeCompanyRole;

public class EmployeeCompanyRoleController {
	public ArrayList<EmployeeCompanyRole> getStaffDetail(int employeeID) {
		EmployeeCompanyRole ecr = new EmployeeCompanyRole();
		return ecr.getStaffDetail(employeeID);
	}
}
