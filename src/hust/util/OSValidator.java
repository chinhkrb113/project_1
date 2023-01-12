package hust.util;

import hust.common.Constants;

public class OSValidator {
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public static boolean isWindows() {
        return OS.contains("win");
    }

    public static boolean isMac() {
        return OS.contains("mac");
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    public static boolean isSolaris() {
        return OS.contains("sunos");
    }
    public static byte getOS(){
        if (isWindows()) {
            return Constants.WINDOWS;
        } else if (isMac()) {
            return Constants.MACOS;
        } else if (isUnix()) {
            return Constants.UNIX;
        } else if (isSolaris()) {
            return Constants.SOLARIS;
        } else {
            return Constants.OS_NOT_SUPPORTED;
        }
    }
}
