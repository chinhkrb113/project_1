package hust.pms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeCompanyRole {
	private String name;
	private String gender;
	private String birthDate;
	private String phoneNumber;
	private String email;
	private String address;
	private String username;
	private String companyName;
	private String parkingName;
	private String roleName;

	public static final String GET_EMPLOYEE_DETAIL = "SELECT e.*, c.*, p.*, r.* FROM employee e JOIN company c on e.companyid = c.companyid JOIN parking p on p.parkingid = e.parkingid JOIN role r on r.roleid = e.roleid where e.employeeid=?";

	public EmployeeCompanyRole() {
		try {
			DataAccessHelper.getInstance().ecr = this;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public EmployeeCompanyRole(String name, String gender, String birthDate, String phoneNumber, String email,
			String address, String username, String companyName, String parkingName, String roleName) {

		this.name = name;
		this.gender = gender;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
		this.username = username;
		this.companyName = companyName;
		this.parkingName = parkingName;
		this.roleName = roleName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public ArrayList<EmployeeCompanyRole> getStaffDetail(int employeeID) {
		ArrayList<EmployeeCompanyRole> ecrList = new ArrayList<EmployeeCompanyRole>();
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;

			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_EMPLOYEE_DETAIL);
			ps.setInt(1, employeeID);
			rs = ps.executeQuery();

			while (rs.next()) {
				ecrList.add(new EmployeeCompanyRole(rs.getString("name"), rs.getString("gender"),
						rs.getString("birthdate"), rs.getString("phonenumber"), rs.getString("email"),
						rs.getString("address"), rs.getString("username"), rs.getString("companyname"),
						rs.getString("parkname"), rs.getString("rolename")));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return ecrList;
	}
	
	

}
