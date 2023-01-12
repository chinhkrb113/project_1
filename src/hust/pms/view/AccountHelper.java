package hust.pms.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hust.common.Constants;

public class AccountHelper {
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    public static final Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("^([ .-]*[0-9]+[ .-]*)+$");
    
    /**
     * This function to check all login text fields are filled
     * @param username
     * @param password
     * @return Constants
     */
    public int textAccountFilled(String username, String password) {
    	if (username.equals("") && password.equals("")) {
			//sceneRoute.sceneAlertWithTitleAndContent("Login Failed", "Username and Password are required.");
			return Constants.USERNAME_PASSWORD_NOT_FILL;
		} else if (username.equals("")) {
			//sceneRoute.sceneAlertWithTitleAndContent("Login Failed", "Username is required.");
			return Constants.USERNAME_NOT_FILL;
		} else if (password.equals("")) {
			//sceneRoute.sceneAlertWithTitleAndContent("Login Failed", "Password is required.");
			return Constants.PASSWORD_NOT_FILL;
		}
    	return Constants.UP_WRONG_TYPE;
    }
    
    /**
     * 
     * @param emailStr
     * @return
     */
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    
    /**
     * 
     * @param phoneNumber
     * @return
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        if(phoneNumber.length()>0){
            Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(phoneNumber);
            return matcher.find();
        }
        return true;
    }
    
//    /**
//     * This function to encrypt password. Using salt (10)
//     * @param password
//     * @return BCrypt.hashpw(password, gensalt)
//     */
//    public static String bcryptHashing(String password) {
//		return BCrypt.hashpw(password, BCrypt.gensalt(10));
//	}
//    
//    /**
//     * This function to check valid hasing password from database
//     * @param currentPassword
//     * @param storePassword
//     * @return validate
//     */
//    public static boolean rightPassword(String currentPassword, String storedPassword) {
//    	boolean validate = BCrypt.checkpw(currentPassword, storedPassword);
//    	return validate;
//    }
}
