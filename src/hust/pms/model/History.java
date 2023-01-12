package hust.pms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class History {
	
	private long historyID;
	private String imgIn;
	private String imgOut;
	private String imgLPIn;
	private String imgLPOut;
	private String plateIn;
	private String plateOut;
	private String timeIn;
	private String timeOut;
	private byte visitStatus;
	private double fee;
	private byte doubt;
	private String cardID;
	
	private static final String GET_HISTORY = "SELECT h.historyid, h.plate_in, h.plate_out, h.time_in, h.time_out, h.visit_status, h.fee, h.doubt, h.cardid FROM history h JOIN parkingcard pc ON pc.cardid = h.cardid WHERE companyid=? order by historyid desc";
	private static final String GET_HISTORY_BY_TIME = "SELECT h.historyid, h.plate_in, h.plate_out, h.time_in, h.time_out, h.visit_status, h.fee, h.doubt, h.cardid FROM history h JOIN parkingcard pc ON pc.cardid = h.cardid WHERE companyid=? and time_in>=? and time_out<=? order by historyid desc";
	
	private static final String ADD_HISTORY = "INSERT INTO history (img_in,img_lp_in,plate_in,time_in,visit_status,cardid) VALUES (?,?,?,?,?,?)";
	private static final String UPDATE_HISTORY = "UPDATE history SET img_out=?,img_lp_out=?,plate_out=?,time_out=?,visit_status=?,fee=? where cardid=? and visit_status=1";
	
	private static final String CHECK_IN_OUTSIDE = "SELECT * FROM history where cardid=? and visit_status=?";
	
	private static final String GET_URL_IMAGE_LP_IN = "SELECT img_lp_in FROM history where cardid=? and visit_status=?";
	private static final String GET_URL_IMAGE_LP_OUT = "SELECT img_lp_out FROM history where cardid=? and visit_status=?";
	private static final String GET_URL_IMG = "select img_in, img_out, img_lp_in, img_lp_out from history where historyid=?";
	private static final String GET_TIME_IN = "SELECT time_in FROM history where cardID=? and img_lp_out=?";
	private static final String GET_ALL_REVENUE = "SELECT SUM(fee) FROM history";
	private static final String GET_REVENUE_BY_TIME = "SELECT SUM(fee) FROM history where time_in>=? and time_out<=?";
	private static final String GET_STORED_PLATE = "select plate_in from history where cardid=? and visit_status=?";
	
	private static final String MARK_AS_DOUBT_1 = "UPDATE history SET doubt=? where cardid=? and historyid=LAST_INSERT_ID()";
	private static final String MARK_AS_DOUBT_2 = "UPDATE history SET doubt=? where cardid=? and img_lp_out=?";

	public ArrayList<History> getHistory(long companyID) {
		ArrayList<History> listHistory = new ArrayList<History>();
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_HISTORY);
			ps.setLong(1, companyID);
			rs = ps.executeQuery();
			while (rs.next()) {
				listHistory.add(new History(
					rs.getLong("historyid"), 
					rs.getString("plate_in"), 
					rs.getString("plate_out"), 
					rs.getString("time_in"), 
					rs.getString("time_out"), 
					rs.getByte("visit_status"), 
					rs.getDouble("fee"),
					rs.getByte("doubt"),
					rs.getString("cardid")));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return listHistory;
	}
	
	public ArrayList<History> getHistoryByTime(long companyID, String startDate, String endDate) {
		ArrayList<History> listHistory = new ArrayList<History>();
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_HISTORY_BY_TIME);
			ps.setLong(1, Employee.currentEmployeeCompanyID);
			ps.setString(2, startDate);
			ps.setString(3, endDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				listHistory.add(new History(
					rs.getLong("historyid"), 
					rs.getString("plate_in"), 
					rs.getString("plate_out"), 
					rs.getString("time_in"), 
					rs.getString("time_out"), 
					rs.getByte("visit_status"), 
					rs.getDouble("fee"),
					rs.getByte("doubt"),
					rs.getString("cardid")));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return listHistory;
	}
	
	public void addHistory() {
		try {
			PreparedStatement ps = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(ADD_HISTORY);
			
			System.out.println("imgIn="+imgIn);
			ps.setString(1, imgIn);
			
			System.out.println("imgLPIn="+imgLPIn);
			ps.setString(2, imgLPIn);
			ps.setString(3, plateIn);
			
			System.out.println("timeIn="+timeIn);
			ps.setString(4, timeIn);
			
			System.out.println("visitStatus="+visitStatus);
			ps.setByte(5, visitStatus);
			
			System.out.println("cardID="+cardID);
			ps.setString(6, cardID);
			
			ps.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void updateHistory() {
		try {
			PreparedStatement ps = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(UPDATE_HISTORY);
			System.out.println("imgOut="+imgOut);
			ps.setString(1, imgOut);
			System.out.println("imgLPOut="+imgLPOut);
			ps.setString(2, imgLPOut);
			System.out.println("timeOut="+timeOut);
			ps.setString(3, plateOut);
			ps.setString(4, timeOut);
			System.out.println("visitStatus="+visitStatus);
			ps.setByte(5, visitStatus);
			System.out.println("cardID="+cardID);
			ps.setDouble(6, fee);
			ps.setString(7, cardID);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public boolean checkInOutSide(String cardID, byte visitStatus) {
		boolean check = false;
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(CHECK_IN_OUTSIDE);
			ps.setString(1, cardID);
			ps.setByte(2, visitStatus);
			rs = ps.executeQuery();
			while(rs.next()) {
				check = true;
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return check;
	}
	
	public String getURLImageLPIn(String cardID, byte visitStatus) {
		String imageLPIn = null;
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_URL_IMAGE_LP_IN);
			ps.setString(1, cardID);
			ps.setByte(2, visitStatus);
			rs = ps.executeQuery();
			while (rs.next()) {
				imageLPIn = rs.getString("img_lp_in");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace(); 
		}
		return imageLPIn;
	}
	
	public String getStoredPlate(String cardID, byte visitStatus) {
		String storedPlate = null;
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_STORED_PLATE);
			ps.setString(1, cardID);
			ps.setByte(2, visitStatus);
			rs = ps.executeQuery();
			while (rs.next()) {
				storedPlate = rs.getString("plate_in");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return storedPlate;
	}
	
	public String getURLImageLPOut(String cardID, byte visitStatus) {
		String imageLPOut = null;
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_URL_IMAGE_LP_OUT);
			ps.setString(1, cardID);
			ps.setByte(2, visitStatus);
			rs = ps.executeQuery();
			while (rs.next()) {
				imageLPOut = rs.getString("img_lp_out");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace(); 
		}
		return imageLPOut;
	}
	
	public String[] getURLImage(long historyID) {
		String[] urlArr = new String[4];
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_URL_IMG);
			ps.setLong(1, historyID);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getString("img_lp_in"));
				urlArr[0] = rs.getString("img_in");
				urlArr[1] = rs.getString("img_out");
				urlArr[2] = rs.getString("img_lp_in");
				urlArr[3] = rs.getString("img_lp_out");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return urlArr;
	}
	
	public String getTimeIn(String cardID, String img_lp_out) {
		String timeIn = null;
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_TIME_IN);
			ps.setString(1, cardID);
			ps.setString(2, img_lp_out);
			rs = ps.executeQuery();
			while (rs.next()) {
				timeIn = rs.getString("time_in");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return timeIn;
	}
	
	public void markAsDoubt(byte doubt, String cardID) {
		try {
			PreparedStatement ps = null;
			
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(MARK_AS_DOUBT_1);
			ps.setByte(1, doubt);
			ps.setString(2, cardID);
			//ps.setLong(3, historyID);
			ps.executeQuery();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void markAsDoubt(byte doubt, String cardID, String impLPOut) {
		try {
			PreparedStatement ps = null;
			
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(MARK_AS_DOUBT_2);
			ps.setByte(1, doubt);
			ps.setString(2, cardID);
			ps.setString(3, impLPOut);
			//ps.setLong(3, historyID);
			ps.executeQuery();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public double getAllRevenue() {
		double revenue = 0;
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_ALL_REVENUE);
			rs = ps.executeQuery();
			while (rs.next()) {
				revenue = rs.getDouble("SUM(fee)");
			}
			ps.close();
			rs.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} 
		return revenue;
	}
	
	public double getRevenueByTime(String startTime, String endTime) {
		double revenue = 0;
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(GET_ALL_REVENUE);
			rs = ps.executeQuery();
			while (rs.next()) {
				revenue = rs.getDouble("SUM(fee)");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return revenue;
	}
	
	public History() {}
	
	public History(long historyID, String plateIn, String plateOut, String timeIn,
			String timeOut, byte visitStatus, double fee, byte doubt, String cardID) {
		this.historyID = historyID;
		this.plateIn = plateIn;
		this.plateOut = plateOut;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
		this.visitStatus = visitStatus;
		this.fee = fee;
		this.doubt = doubt;
		this.cardID = cardID;
	}
	/**
	 * @param historyID
	 * @param imgIn
	 * @param imgOut
	 * @param imgLPIn
	 * @param imgLPOut
	 * @param timeIn
	 * @param timeOut
	 * @param fee
	 * @param visitStatus
	 * @param cardID
	 */
	public History(long historyID, String imgIn, String imgOut, String imgLPIn, String imgLPOut, String timeIn,
			String timeOut, byte visitStatus,double fee, String cardID) {
		super();
		this.historyID = historyID;
		this.imgIn = imgIn;
		this.imgOut = imgOut;
		this.imgLPIn = imgLPIn;
		this.imgLPOut = imgLPOut;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
		this.visitStatus = visitStatus;
		this.fee = fee;
		this.cardID = cardID;
	}

	public long getHistoryID() {
		return historyID;
	}

	public void setHistoryID(long historyID) {
		this.historyID = historyID;
	}

	public String getImgIn() {
		return imgIn;
	}

	public void setImgIn(String imgIn) {
		this.imgIn = imgIn;
	}

	public String getImgOut() {
		return imgOut;
	}

	public void setImgOut(String imgOut) {
		this.imgOut = imgOut;
	}

	public String getImgLPIn() {
		return imgLPIn;
	}

	public void setImgLPIn(String imgLPIn) {
		this.imgLPIn = imgLPIn;
	}

	public String getImgLPOut() {
		return imgLPOut;
	}

	public void setImgLPOut(String imgLPOut) {
		this.imgLPOut = imgLPOut;
	}

	public String getPlateIn() {
		return plateIn;
	}

	public void setPlateIn(String plateIn) {
		this.plateIn = plateIn;
	}

	public String getPlateOut() {
		return plateOut;
	}

	public void setPlateOut(String plateOut) {
		this.plateOut = plateOut;
	}

	public String getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public byte getVisitStatus() {
		return visitStatus;
	}

	public void setVisitStatus(byte visitStatus) {
		this.visitStatus = visitStatus;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public byte getDoubt() {
		return doubt;
	}

	public void setDoubt(byte doubt) {
		this.doubt = doubt;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
}
