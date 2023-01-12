/*
 * 
 */

package hust.pms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hust.common.Constants;
import hust.pms.controller.LoginController;
import hust.pms.controller.SceneController;


public class Employee extends User {
	private int employeeID;
	private long companyID;
	private long parkingID;
	private String username;
	private String password;
	private int roleID;

	// private PreparedStatement ps = null;
	// private ResultSet rs = null;

	private static final String GET_LOGIN = "select * from Employee where username=?";
	private static final String GET_LOGIN_LOGOUT_STATUS = "select loginstatus from Employee where username=?";
	
	private static final String GET_SADMIN_TO_LOAD_TABLE = "SELECT e.employeeid, e.name, e.gender, e.birthdate, e.phonenumber, e.email, e.address FROM employee e JOIN role r ON r.roleid = e.roleid WHERE r.roleno IN (0) ORDER BY e.name ASC";
	private static final String GET_ADMIN_TO_LOAD_TABLE = "SELECT e.employeeid, e.name, e.gender, e.birthdate, e.phonenumber, e.email, e.address FROM employee e JOIN role r ON r.roleid = e.roleid WHERE r.roleno IN (1) ORDER BY e.name ASC";
	private static final String GET_STAFF = "select * from Employee";
	private static final String GET_STAFF_TO_LOAD_TABLE = "SELECT e.employeeid, e.name, e.gender, e.birthdate, e.phonenumber, e.email, e.address FROM employee e JOIN role r ON r.roleid=e.roleid WHERE r.roleno NOT IN (0,1) AND e.companyid=? ORDER BY e.name ASC";
	//public static final String GET_EMPLOYEE_DETAIL = "SELECT e.*, c.*, p.*, r.* FROM employee e JOIN company c on e.companyid = c.companyid JOIN parking p on p.parkingid = e.parkingid JOIN role r on r.roleid = e.roleid where e.employeeid=?";
	private static final String GET_NUMBER_OF_SADMIN = "SELECT COUNT(*) FROM employee e JOIN role r ON r.roleid = e.roleid WHERE r.roleno=0";
	private static final String GET_NUMBER_OF_ADMIN = "SELECT COUNT(*) FROM employee e JOIN role r ON r.roleid = e.roleid WHERE r.roleno=1";
	private static final String GET_NUMBER_OF_STAFF_BELONG_TO_COMPANY = "SELECT COUNT(*) FROM employee e JOIN role r ON r.roleid=e.roleid WHERE e.companyid=? AND r.roleno>=2";

	private static final String ADD_STAFF = "insert into Employee (name,gender,birthdate,phonenumber,email,address,username,password,companyid,parkingid,roleid) values (?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SEARCH_EMPLOYEE_BY_EMAIL = "select * from Employee where Email=?";
	private static final String SEARCH_EMPLOYEE_BY_EMAIL_EXCEPT_CURRENT = "select * from Employee where employeeid=? and email=?";
	private static final String SEARCH_EMPLOYEE_BY_PHONE_NUMBER = "select * from Employee where phonenumber=?";
	private static final String SEARCH_EMPLOYEE_BY_PHONE_NUMBER_EXCEPT_CURRENT = "select * from Employee where employeeid=? and phonenumber=?";
	private static final String SEARCH_EMPLOYEE_USERNAME = "select * from Employee where username=?";

	private static final String UPDATE_EMPLOYEE = "update Employee set name=?,gender=?,birthdate=?,phonenumber=?,email=?,address=?,username=?,companyid=?,parkingid=?,roleid=? where employeeid=?";
	private static final String UPDATE_LOGIN_STATUS = "update Employee set loginstatus=? where username=?";

	private static final String DELETE_EMPLOYEE = "delete from Employee where employeeid=?";

	public static int currentEmployeeID;
	public static String currentUser = null;
	public static String currentUserName = null;
	public static int currentUserRoleID;
	public static long currentEmployeeCompanyID;
	public static long currentEmployeeParkingID;

	LoginController loginController = new LoginController();

	public Employee() {
		try {
			DataAccessHelper.getInstance().emp = this;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public Employee(String name, String gender, String birthDat)

	public Employee(int id, String name, String gender, String birthDate, String phoneNumber, String email,
			String address) {
		super(name, gender, birthDate, phoneNumber, email, address);
		employeeID = id;
		
//		this.name = name;
//		this.gender = gender;
//		this.birthDate = birthDate;
//		this.phoneNumber = phoneNumber;
//		this.email = email;
//		this.address = address;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}

	public long getParkingID() {
		return parkingID;
	}

	public void setParkingID(long parkingID) {
		this.parkingID = parkingID;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getUserName() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte getLogin(String username, String password) {

		try {
			// getInstance();
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_LOGIN);
			ps.setString(1, username);
			// ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				if (loginController.validPassword(password, rs.getString("password"))) {
					currentEmployeeID = rs.getInt("employeeid");
					currentUser = rs.getString("name");
					currentUserName = rs.getString("username");
					currentUserRoleID = rs.getInt("roleid");
					currentEmployeeCompanyID = rs.getLong("companyid");
					currentEmployeeParkingID = rs.getLong("parkingid");
					return Constants.LOGIN_SUCCESS;
				} else {
					return Constants.LOGIN_WRONG_PASSWORD;
				}
			} else {
				return Constants.LOGIN_WRONG_UP;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			SceneController.getInstance().toAlertWithTitleAndContent("Error", "Database Error");
			// Logger.getLogger(Employee.class.getName()).log(Level.ERROR, null, sqle);
			
		}
		return Constants.LOGIN_FAILED;
	}
	
	public ArrayList<Employee> getEmployee() {
		ArrayList<Employee> listEmployee = new ArrayList<Employee>();
		
		try {
			Employee emp = new Employee();
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_STAFF);
			rs = ps.executeQuery();

			while (rs.next()) {
				emp.setEmployeeID(rs.getInt("employeeid"));
				emp.setName(rs.getString("name"));
				emp.setGender(rs.getString("gender"));
				// if (rs.getString("birthdate").isEmpty() || rs.getLong("phonenumber") == 0)
				emp.setBirthDate(rs.getString("birthdate"));
				emp.setPhoneNumber(rs.getString("phonenumber"));
				emp.setEmail(rs.getString("email"));
				emp.setAddress(rs.getString("address"));
				emp.setUsername(rs.getString("username"));
				emp.setPassword(rs.getString("password"));
				emp.setCompanyID(rs.getInt("companyid"));
				emp.setParkingID(rs.getInt("parkingid"));
				emp.setRoleID(rs.getInt("roleid"));

				listEmployee.add(emp);
			}
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			// Logger.getLogger(DataAccessHelper.class.getName()).log(Level.SEVERE, null,
			// sqle);
			sqle.printStackTrace();
		}
		return listEmployee;
	}

	public ArrayList<Employee> getStaffToLoadTable(long companyID) {
		ArrayList<Employee> listStaff = new ArrayList<Employee>();
		// Employee emp = new Employee();

		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_STAFF_TO_LOAD_TABLE);
			ps.setLong(1, companyID);
			rs = ps.executeQuery();

			while (rs.next()) {

				listStaff.add(new Employee(rs.getInt("employeeid"), rs.getString("name"), rs.getString("gender"),
						rs.getString("birthdate"), rs.getString("phonenumber"), rs.getString("email"),
						rs.getString("address")));
			}
			//listStaff.stream().forEach(System.out::println);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return listStaff;
	}
	
	public ArrayList<Employee> getSAdminToLoadTable() {
		ArrayList<Employee> listSAdmin = new ArrayList<Employee>();
		// Employee emp = new Employee();

		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_SADMIN_TO_LOAD_TABLE);
			rs = ps.executeQuery();

			while (rs.next()) {
//				emp.setEmployeeID(rs.getInt("employeeid"));
//				emp.setName(rs.getString("name"));
//				emp.setGender(rs.getString("gender"));
//				emp.setBirthDate(rs.getString("birthdate"));
//				emp.setPhoneNumber(rs.getString("phonenumber"));
//				emp.setEmail(rs.getString("email"));
//				emp.setAddress(rs.getString("address"));

				listSAdmin.add(new Employee(rs.getInt("employeeid"), rs.getString("name"), rs.getString("gender"),
						rs.getString("birthdate"), rs.getString("phonenumber"), rs.getString("email"),
						rs.getString("address")));
			}
			//listStaff.stream().forEach(System.out::println);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return listSAdmin;
	}
	
	public ArrayList<Employee> getAdminToLoadTable() {
		ArrayList<Employee> listAdmin = new ArrayList<Employee>();
		// Employee emp = new Employee();

		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_ADMIN_TO_LOAD_TABLE);
			rs = ps.executeQuery();

			while (rs.next()) {
//				emp.setEmployeeID(rs.getInt("employeeid"));
//				emp.setName(rs.getString("name"));
//				emp.setGender(rs.getString("gender"));
//				emp.setBirthDate(rs.getString("birthdate"));
//				emp.setPhoneNumber(rs.getString("phonenumber"));
//				emp.setEmail(rs.getString("email"));
//				emp.setAddress(rs.getString("address"));

				listAdmin.add(new Employee(rs.getInt("employeeid"), rs.getString("name"), rs.getString("gender"),
						rs.getString("birthdate"), rs.getString("phonenumber"), rs.getString("email"),
						rs.getString("address")));
			}
			//listStaff.stream().forEach(System.out::println);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return listAdmin;
	}
	
	public void addStaff() throws SQLException, ClassNotFoundException {
		PreparedStatement ps = null;

		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(ADD_STAFF);
		ps.setString(1, super.getName());
		ps.setString(2, super.getGender());
		ps.setString(3, super.getBirthDate());
		ps.setString(4, super.getPhoneNumber());
		ps.setString(5, super.getEmail());
		ps.setString(6, super.getAddress());
		ps.setString(7, username);
		ps.setString(8, password);
		ps.setLong(9, companyID);
		ps.setLong(10, parkingID);
		ps.setInt(11, roleID);

		ps.executeUpdate();

	}

	public void updateStaff(int employeeID)	throws SQLException, ClassNotFoundException {
		PreparedStatement ps = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(UPDATE_EMPLOYEE);
		ps.setString(1, super.getName());
		ps.setString(2, super.getGender());
		ps.setString(3, super.getBirthDate());
		ps.setString(4, super.getPhoneNumber());
		ps.setString(5, super.getEmail());
		ps.setString(6, super.getAddress());
		ps.setString(7, username);
		ps.setLong(8, companyID);
		ps.setLong(9, parkingID);
		ps.setInt(10, roleID);
		ps.setInt(11, employeeID);
		ps.executeUpdate();
	}
	
	public void deleteStaff(int employeeID) throws SQLException {
		PreparedStatement ps = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(DELETE_EMPLOYEE);
		ps.setInt(1, employeeID);
		ps.executeUpdate();
	}

	public boolean isEmailExist(String email) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(SEARCH_EMPLOYEE_BY_EMAIL);
		ps.setString(1, email);
		rs = ps.executeQuery();
		while (rs.next()) {
			return true;
		}
		return false;
	}
	
	public boolean isEmailExistExceptCurrent(int employeeID, String email) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(SEARCH_EMPLOYEE_BY_EMAIL_EXCEPT_CURRENT);
		ps.setInt(1, employeeID);
		ps.setString(2, email);
		rs = ps.executeQuery();
		while (rs.next()) {
			return true;
		}
		return false;
	}
	
	
	public boolean isPhoneNumberExist(String phonenumber) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(SEARCH_EMPLOYEE_BY_PHONE_NUMBER);
		ps.setString(1, phonenumber);
		rs = ps.executeQuery();
		while (rs.next()) {
			return true;
		}
		return false;
	}
	
	public boolean isPhoneNumberExistExceptCurrent(int employeeID, String phonenumber) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(SEARCH_EMPLOYEE_BY_PHONE_NUMBER_EXCEPT_CURRENT);
		ps.setInt(1, employeeID);
		ps.setString(2, phonenumber);
		rs = ps.executeQuery();
		while (rs.next()) {
			return true;
		}
		return false;
	}

	public boolean isUserNameExist(String username) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(SEARCH_EMPLOYEE_USERNAME);
		ps.setString(1, username);
		rs = ps.executeQuery();
		while (rs.next()) {
			return true;
		}
		return false;
	}
	
	public byte getLoginStatus(String username) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_LOGIN_LOGOUT_STATUS);
		ps.setString(1, username);
		rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println("loginstatus="+rs.getByte("loginstatus"));
			return rs.getByte("loginstatus");
		}
		return -1;
	}
	
	public void updateLoginStatus(byte status, String username) throws SQLException {
		PreparedStatement ps = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(UPDATE_LOGIN_STATUS);
		ps.setByte(1, status);
		ps.setString(2, username);
		ps.executeUpdate();
	}
	
	public int numberOfSAdmin() throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_NUMBER_OF_SADMIN);
		rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println("numberOfSAdmin=" + rs.getInt("COUNT(*)"));
			return rs.getInt("COUNT(*)");
		}
		return 0;
	}
	
	public int numberOfAdmin() throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_NUMBER_OF_ADMIN);
		rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt("COUNT(*)");
		}
		return 0;
	}
	
	public int numberOfStaffBelongToCompany(long companyID) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_NUMBER_OF_STAFF_BELONG_TO_COMPANY);
		ps.setLong(1, companyID);
		rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt("COUNT(*)");
		}
		return 0;
	}
	
	
}
