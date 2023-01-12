package hust.pms.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import hust.pms.model.Role;

public class RoleController {
	public ArrayList<String> getRoleNameToLoadComboBox() {
		Role role = new Role();
		return role.getRoleNameToLoadComboBox();
	}
	
	public int getRoleNo(String roleName) {
		Role role = new Role();
		return role.getRoleNo(roleName);
	}
	
	public ArrayList<Role> getRole() {
		Role role = new Role();
		return role.getRole();
	}
	
	public boolean roleNoExist(String roleName) throws SQLException {
		Role role = new Role();
		return role.isRoleNoExist(roleName);
	}
	
	public boolean roleNameExist(int roleNo) throws SQLException {
		Role role = new Role();
		return role.isRoleNameExist(roleNo);
	}
	
	public void addRole(Role role) throws ClassNotFoundException, SQLException {
		role.addRole();
	}
	
	public void deleteRole(int roleID) throws SQLException {
		Role role = new Role();
		role.deleteRole(roleID);
	}
}
