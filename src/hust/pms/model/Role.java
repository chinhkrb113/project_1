package hust.pms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hust.pms.controller.SceneController;

//import hust.parkingmanagement.controller.AlertController;

public class Role {
	private int roleID;
	private int roleNo;
	private String roleName;
	private String description;
	
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	//AlertController alertController = new AlertController();
	
	private static final String GET_ROLE = "select * from role ORDER BY roleno ASC";
	private static final String GET_ROLE_NAME = "select rolename from Role";
	private static final String GET_ROLE_NAME_EXCEPT_ORIGIN_DEV = "SELECT rolename FROM role WHERE roleno >= 2";
	private static final String GET_ROLE_VALUE = "select roleno, rolename from Role";
	private static final String GET_EMPLOYEE_ROLE_NAME = "select rolename from Role r join Employee e on r.roleid = e.roleid where e.username=?";
	private static final String GET_EMPLOYEE_ROLE_NO = "select roleno from Role r join Employee e on r.roleid = e.roleid where e.username=?";
	
	private static final String SEARCH_ROLE_NO_BY_ROLE_NAME = "select roleno from Role where rolename=?";
	//private static final String SEARCH_ROLE_NO_BY_ROLE_ID = "select roleno from Role where roleid=?";
	
	private static final String ADD_ROLE = "insert into Role (roleno, rolename, description) values (?,?,?)";
	private static final String DELETE_ROLE = "delete from Role where roleid=?";

	public Role() {
		try {
			DataAccessHelper.getInstance().role = this;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Role(int roleID, int roleNo, String roleName, String description) {
		this.roleID = roleID;
		this.roleNo = roleNo;
		this.roleName = roleName;
		this.description = description;
	}

	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	
	public int getRoleNo() {
		return roleNo;
	}
	
	public int getRoleNo(String roleName) {
		//Role role = new Role();
		try {
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(SEARCH_ROLE_NO_BY_ROLE_NAME);
			ps.setString(1, roleName);
			rs = ps.executeQuery();
			while (rs.next()) {
				roleNo = rs.getInt(1);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return roleNo;
	}
	
	public void setRoleNo(int roleno) {
		this.roleNo = roleno;
	}
	
	
	public String getRoleName() {
		return roleName;
	}
	
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public ArrayList<String> getRoleNameToLoadComboBox() {
		ArrayList<String> listRole = new ArrayList<String>();
			
		try {
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_ROLE_NAME_EXCEPT_ORIGIN_DEV);
			rs = ps.executeQuery();
			while (rs.next()) {
				listRole.add(rs.getString("rolename"));			
			}			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return listRole;
	}
	
	public Map<Integer, String> getRoleValue() {
		Map<Integer, String> role = new HashMap<>();
		try {
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_ROLE_VALUE);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				role.put(rs.getInt("roleno"), rs.getString("rolename"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return role;
	}
	
	public ArrayList<Role> getRole() {
		ArrayList<Role> listRole = new ArrayList<Role>();
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_ROLE);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				listRole.add(new Role(rs.getInt("roleid"), rs.getInt("roleno"), rs.getString("rolename"), rs.getString("description")));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return listRole;
	}
	
	public int getRoleNoByUserName(String userName) {
		try {
			// getInstance();
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_EMPLOYEE_ROLE_NO);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			// System.out.println(rs);
			// System.out.println(rs.next());
			if (rs != null && rs.next()) {
				// System.out.println("roleno=" + rs.getInt("roleno"));
				return rs.getInt("roleno");
			}
		} catch (SQLException sqle) {
			// Logger.getLogger(Employee.class.getName()).log(, sqle);
			sqle.printStackTrace();
			SceneController.getInstance().toAlertWithTitleAndContent("Error", "Database Error");
			// Logger.getLogger(Employee.class.getName()).log(Level.ERROR, null, sqle);
		}
		return 0;
	}
	
	public String getRoleNameByUserName(String userName) {
		try {
			// getInstance();
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_EMPLOYEE_ROLE_NAME);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			// System.out.println(rs);
			// System.out.println(rs.next());
			if (rs != null && rs.next()) {
				// System.out.println("roleno=" + rs.getInt("roleno"));
				return rs.getString("rolename");
			}
		} catch (SQLException sqle) {
			// Logger.getLogger(Employee.class.getName()).log(, sqle);
			sqle.printStackTrace();
			SceneController.getInstance().toAlertWithTitleAndContent("Error", "Database Error");
			// Logger.getLogger(Employee.class.getName()).log(Level.ERROR, null, sqle);
		}
		return null;
	}
	
	public boolean isRoleNoExist(String roleName) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_EMPLOYEE_ROLE_NAME);
		ps.setString(1, roleName);
		rs = ps.executeQuery();
		while (rs.next()) {
			return true;
		}
		return false;
	}
	
	public boolean isRoleNameExist(int roleNo) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_EMPLOYEE_ROLE_NO);
		ps.setInt(1, roleNo);
		rs = ps.executeQuery();
		while(rs.next()) {
			return true;
		}
		return false;
	}
	
	public void addRole() throws SQLException {
		PreparedStatement ps = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(ADD_ROLE);
		ps.setInt(1, roleNo);
		ps.setString(2, roleName);
		ps.setString(3, description);
		ps.executeUpdate();
	}
	
	public void deleteRole(int roleID) throws SQLException {
		PreparedStatement ps = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(DELETE_ROLE);
		ps.setInt(1, roleID);
		ps.executeUpdate();
	}
}
