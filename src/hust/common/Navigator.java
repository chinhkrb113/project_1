package hust.common;

import java.io.File;

public class Navigator {
	
	//public static final String FXML_MAINFORM = "resources/MainForm.fxml";
	//Definition
	private static final String SEP = File.separator;
	private static final String ROOT = "resources" + SEP;
	private static final String EXT_FXML = ".fxml";
	private static final String EXT_PNG = ".png";
	
	/**
	 * 
	 * @param GUIName
	 * @return ROOT + GUIName + EXT_FXML
	 */
	private static String toRelativePath(String GUIName) {
		return ROOT + GUIName + EXT_FXML;
	}
	
	//FXML
	public static final String FXML_LOGIN = toRelativePath("Login");
	
	public static final String FXML_SUPERIOR_ADMINCENTER = toRelativePath("SuperiorAdminCenter");
	
	public static final String FXML_SUPERIOR_ADMIN_VIEW_SUPERIOR_ADMIN = toRelativePath("SuperiorAdmin_SAdmin");
	public static final String FXML_ADD_SUPERIOR_ADMIN = toRelativePath("SuperiorAdmin_SAdmin_Add");
	public static final String FXML_SADMIN_DETAIL = toRelativePath("SuperiorAdmin_SAdminDetail");
	
	public static final String FXML_SUPERIOR_ADMIN_VIEW_ADMIN = toRelativePath("SuperiorAdmin_Admin");
	public static final String FXML_ADD_ADMIN = toRelativePath("SuperiorAdmin_Admin_Add");
	public static final String FXML_ADMIN_DETAIL = toRelativePath("SuperiorAdmin_AdminDetail");
	
	public static final String FXML_SUPERIOR_ADMINROLE = toRelativePath("SuperiorAdmin_Role");
	public static final String FXML_ADD_ROLE = toRelativePath("SuperiorAdmin_Role_Add");
	
	public static final String FXML_SUPERIOR_ADMIN_COMPANY = toRelativePath("SuperiorAdmin_Company");
	public static final String FXML_ADDCOMPANY = "resources/SuperiorAdmin_Company_Add.fxml";
	public static final String FXML_MODIFYCOMPANY = "resources/SuperiorAdmin_Company_Modify.fxml";
	
	public static final String FXML_ADMINCENTER = "resources/AdminCenter.fxml";
	public static final String FXML_ADMINSTAFF = "resources/Admin_Staff.fxml";
	public static final String FXML_ADDSTAFF = "resources/Admin_Staff_Add.fxml";
	public static final String FXML_ADMINCUSTOMER = "resources/Admin_Customer.fxml";
	//public static final String FXML_ADMINSTAFF_MODIFY = "resources/Admin_ModifyStaff.fxml";
	public static final String FXML_STAFF_DETAIL = "resources/Admin_StaffDetail.fxml";
	
	public static final String FXML_ADMINPARKING = "resources/Admin_Parking.fxml";
	public static final String FXML_ADD_PARKING = "resources/Admin_Parking_Add.fxml";
	public static final String FXML_PARKING_DETAIL = "resources/Admin_ParkingDetail.fxml";
	
	public static final String FXML_CARDISSUE = "resources/Staff_CardIssue.fxml";
	public static final String FXML_STAFFCENTER = "resources/StaffCenter.fxml";
	public static final String FXML_STAFFCARD = "resources/Staff_CardIssue.fxml";
	
	public static final String FXML_STATISTIC_CENTER = toRelativePath("Admin_Statistic");
	public static final String FXML_CHANGE_FEE = "resources/Admin_ChangeFee.fxml";
	
	public static final String FXML_VIEW_HISTORY = toRelativePath("Staff_History");
	public static final String FXML_HISTORY_DETAIL = toRelativePath("Staff_HistoryDetail");
	
	public static final String FXML_STAFF_CONTRACT = toRelativePath("Staff_Contract");
	
	//Image
	public static final String IMG_PARKING = "resources/img/parking_logo.png";
	
	
	/*
	public static void main(String[] args) {
		System.out.println(root);
	}*/
}
