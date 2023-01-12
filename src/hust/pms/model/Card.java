package hust.pms.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Card {
	private String cardID;
	private long companyID;
	private byte status;
	private byte type;
	
	private static final String ADD_CARD = "INSERT INTO parkingcard (cardid, companyid, status) VALUES (?,?,?)";
	
	private static final String CARD_ID_EXIST = "select * from parkingcard where cardid=?";
	private static final String CARD_STATUS = "select status from parkingcard where cardid=? and companyid=?";
	private static final String CARD_TYPE = "SELECT type FROM parkingcard where cardid=?";
	private static final String CARD_PROMOTION = "update parkingcard set type=? where cardid=?";
	
	public Card() {}
	public Card(String cardID, long companyID, byte status) {
		this.cardID = cardID;
		this.companyID = companyID;
		this.status = status;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	public long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
	
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public void addCard() {
		try {
			PreparedStatement ps = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(ADD_CARD);
			ps.setString(1, cardID);
			ps.setLong(2, companyID);
			ps.setByte(3, status);
			ps.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public boolean isCardExist(String cardID) {
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(CARD_ID_EXIST);
			ps.setString(1, cardID);
			rs = ps.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return false;
	}
	
	public byte getCardStatus(String cardID) {
		byte status = 0;
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(CARD_STATUS);
			ps.setString(1, cardID);
			ps.setLong(2, Employee.currentEmployeeCompanyID);
			rs = ps.executeQuery();
			while (rs.next()) {
				status = rs.getByte("status");
				//return true;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return status;
	}
	
	public void promoteToCard(byte type, String cardID) {
		try {
			PreparedStatement ps = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(CARD_PROMOTION);
			ps.setByte(1, type);
			ps.setString(2, cardID);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	
	public byte getCardType(String cardID) {
		byte type = 0;
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DataAccessHelper.getInstance().getConnection().prepareStatement(CARD_TYPE);
			ps.setString(1, cardID);
			rs = ps.executeQuery();
			while (rs.next()) {
				type = rs.getByte("type");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return type;
	} 
}
