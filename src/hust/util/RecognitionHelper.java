package hust.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hust.common.Constants;

public class RecognitionHelper {
	public static String toPlateStr(String imgURL) {
		String plate = null;
		String _plate = null;

		String programs = "python";
		String currentRoot = System.getProperty("user.dir");
		String pathToImg = "/img_history/";
		String pathToPy = "/src/hust/util/";
		String pyFile = "NumberPlateRecognition.py";
		ProcessBuilder pb = null;
		Process process = null;
		BufferedReader reader = null;
		// String line = null;
		if (OSValidator.getOS() == Constants.WINDOWS) {
			System.out.println("Windows");
			try {
				pb = new ProcessBuilder(programs, currentRoot + pathToPy + pyFile, currentRoot + pathToImg + imgURL);
				process = pb.start();
				// process = Runtime.getRuntime().exec(programs + " " + currentRoot + pathToPy +
				// pyPath + " " + imgURL);
				// stdout = process.getInputStream();
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((plate = reader.readLine()) != null) {
					//System.out.println(plate);
					_plate = plate;
				}
				process.waitFor();
			} catch (IOException | InterruptedException ioe) {
				ioe.printStackTrace();
			}

		} else if (OSValidator.getOS() == Constants.MACOS) {
			try {
				pb = new ProcessBuilder(programs, currentRoot + pathToPy + pyFile, currentRoot + pathToImg + imgURL);
				process = pb.start();
				// process = Runtime.getRuntime().exec(programs + " " + currentRoot + pathToPy +
				// pyPath + " " + imgURL);
				// stdout = process.getInputStream();
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((plate = reader.readLine()) != null) {
					//System.out.println(plate);
					_plate = plate;
				}
				process.waitFor();
			} catch (IOException | InterruptedException ioe) {
				ioe.printStackTrace();
			}

		} else if (OSValidator.getOS() == Constants.UNIX) {
			try {
				pb = new ProcessBuilder(programs, currentRoot + pathToPy + pyFile, currentRoot + pathToImg + imgURL);
				process = pb.start();
				// process = Runtime.getRuntime().exec(programs + " " + currentRoot + pathToPy +
				// pyPath + " " + imgURL);
				// stdout = process.getInputStream();
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((plate = reader.readLine()) != null) {
					//System.out.println(plate);
					_plate = plate;
				}
				process.waitFor();
			} catch (IOException | InterruptedException ioe) {
				ioe.printStackTrace();
			}

		} else if (OSValidator.getOS() == Constants.SOLARIS) {
			try {
				pb = new ProcessBuilder(programs, currentRoot + pathToPy + pyFile, currentRoot + pathToImg + imgURL);
				process = pb.start();
				// process = Runtime.getRuntime().exec(programs + " " + currentRoot + pathToPy +
				// pyPath + " " + imgURL);
				// stdout = process.getInputStream();
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((plate = reader.readLine()) != null) {
					//System.out.println(plate);
					_plate = plate;
				}
				process.waitFor();
			} catch (IOException | InterruptedException ioe) {
				ioe.printStackTrace();
			}

		} else {
			
		}
		System.out.println("plate=" + _plate);
		return _plate;
	}
	/*
	 * public static void main(String[] args) {
	 * toPlateStr("0015158841_2021-06-18_08h34m17s.jpg"); }
	 */
}
