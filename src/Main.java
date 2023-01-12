import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

public class Main {
	
//	public static void main(String args[]) {
//		String value = "abc12345";
//		//String value2 = "qwerty12";
//		//System.out.println("Băm sử dụng bcrypt của chuỗi " + value + " lần 1 là:\n" + bcryptHashing(value) + "\n");
//		//System.out.println("Băm sử dụng bcrypt của chuỗi " + value + " lần 2 là:\n" + bcryptHashing(value));
//		System.out.println("Kết quả so khớp bản rõ abc12345 \n");
//		System.out.println("$2a$12$5udY7LFE.Nu23zvJ9xXTLuTPAaksqbmLoXP1tQsPQ9M.HYkE0fAn6");
//		System.err.println(validPassword(value, "$2a$12$5udY7LFE.Nu23zvJ9xXTLuTPAaksqbmLoXP1tQsPQ9M.HYkE0fAn6"));
//		System.out.println("$2a$12$FiGstzw3oGha30Ovo1PTyu9LDnymfQ3nuERULT7rNo7.mZxiaLdYm");
//		System.err.println(validPassword(value, "$2a$12$FiGstzw3oGha30Ovo1PTyu9LDnymfQ3nuERULT7rNo7.mZxiaLdYm"));
//		System.out.println("Với một bản rõ lạ: acb34125");
//		System.err.println(validPassword("acb34125", "$2a$12$FiGstzw3oGha30Ovo1PTyu9LDnymfQ3nuERULT7rNo7.mZxiaLdYm"));
//		// $2a$12$5udY7LFE.Nu23zvJ9xXTLuTPAaksqbmLoXP1tQsPQ9M.HYkE0fAn6
//		// $2a$12$FiGstzw3oGha30Ovo1PTyu9LDnymfQ3nuERULT7rNo7.mZxiaLdYm
//	}
//	
//	public static String bcryptHashing(String password) {
//		return BCrypt.hashpw(password, BCrypt.gensalt(12));
//	}
//	
//	public static boolean validPassword(String currentPassword, String storedPassword) {
//    	boolean validate = BCrypt.checkpw(currentPassword, storedPassword);
//    	return validate;
//    }
	
	public static void main(String args[]) throws IOException {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		ImageIO.write(webcam.getImage(), "PNG", new File("hello-world.png"));
	}
	
}
