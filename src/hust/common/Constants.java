package hust.common;

public class Constants {
	//Login
	public static final byte LOGIN_FAILED = 0;
	public static final byte LOGIN_SUCCESS = 1;
	public static final byte LOGIN_WRONG_UP = -1;
	public static final byte LOGIN_WRONG_PASSWORD = -2;
	
	//Logout
	public static final byte LOGOUT_SUCCESS = 1;
	public static final byte LOGOUT_FAILED = 0;
	
	//Field checking
	public static final byte USERNAME_PASSWORD_NOT_FILL = 0;
	public static final byte USERNAME_NOT_FILL = 1;
	public static final byte PASSWORD_NOT_FILL = 2;
	public static final byte UP_WRONG_TYPE = -1;
	
	//Register checking
	public static final byte DUPLICATE_USERNAME = 0;
	
	//Commons
	//public static final int PHONE_NUMBER_LENGTH = 10;
	public static final byte CARD_ID_LENGTH = 10;
	
	public static final byte ROLE_SUPERIOR_ADMIN = 0;
	public static final byte ROLE_ADMIN = 1;
	public static final byte ROLE_STAFF = 2;
	//public static final byte ROLE_CUSTOMER = 3;
	
	//CARD STATUS
	public static final byte CARD_LOCKED = 0;
	public static final byte CARD_NORMAL = 1;
	public static final byte CARD_NOT_ACTIVATED = -1;
	public static final byte CARD_NOT_EXIST = -2;
	
	//Card type
	public static final byte CARD_FOR_GUEST = 0;
	public static final byte CARD_FOR_CUSTOMER = 1;
	
//	//Fee
//	public static final double FEE_DAY_MOTOR = 10000;
//	public static final double FEE_DAY_DIFFERENT_MOTOR = 100000;
	
	//Parking Vehicle status
	public static final byte VEHICLE_INSIDE = 1;
	public static final byte VEHICLE_OUTSIDE = 0;
	
	//Suspicious
	public static final byte MARK_AS_DOUBT = 1;
	public static final byte MARK_AS_NORMAL = 0;
	
	//OS Detection
	public static final byte WINDOWS = 1;
	public static final byte MACOS = 2;
	public static final byte UNIX = 3;
	public static final byte SOLARIS = 4;
	public static final byte OS_NOT_SUPPORTED = 0;
	
	//Vehicle type
	public static final String TWO_WHEELS = "Motorbike, electric bike with LP";
	public static final String THREE_WHEELS = "Car, truck";
}
