package hust.pms.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.logging.Level;

import hust.pms.controller.SceneController;


public class DataAccessHelper {
	
	private static final String URL_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL_DB = "jdbc:mysql://localhost:3306/parking?useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	
	private static final String GET_COUNT = "select count(*) from ";
	
	private Connection conn;
	private static DataAccessHelper instance;
	
	public Employee emp;
	public Parking parking;
	public Role role;
	public Company company;
	public EmployeeCompanyRole ecr;

	//AlertController alert = new AlertController();


	private DataAccessHelper() {
		try {
			Class.forName(URL_DRIVER);
			this.conn = DriverManager.getConnection(URL_DB, USERNAME, PASSWORD);
			// System.out.println("Connector notice: Connection successfully ...");

		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			System.out.println(cnfe);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			SceneController.getInstance().toAlertWithTitleAndContent("Database connection failed", sqle.toString());
			//alert.dialogAlert("Database connection failed", sqle.toString());
			//Logger.getLogger(DataAccessHelper.class.getClass()).log(Level.SEVERE, null, e);
			//System.exit(0);
		} catch (Exception e) {
			SceneController.getInstance().toAlertWithTitleAndContent("Database connection failed", e.toString());
		}
	}

	public Connection getConnection() {
		return conn;
	}


	public static DataAccessHelper getInstance() throws SQLException {
		if (instance == null) {
			instance = new DataAccessHelper();
		} else if (instance.getConnection().isClosed()) {
			instance = new DataAccessHelper();
		}
		return instance;
	}
	
	public int countRecord(String table) throws SQLException, ClassNotFoundException {
		int numberOfRows = 0;
		PreparedStatement ps = conn.prepareStatement(GET_COUNT + table);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			numberOfRows = rs.getInt(1);
		}
		return numberOfRows;
	}

	
}
