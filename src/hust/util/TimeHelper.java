package hust.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {
	
	public static String getCurrentTimeToStr() {
		String formattedDateTime = null;
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH'h'mm'm'ss's'");
		formattedDateTime = currentDateTime.format(formatter);
		return formattedDateTime;
	}
	/*
	public static String timeToStr(LocalDate time) {
		String date = null;
		time = LocalDate.parse("yyyy-MM-dd");
		LocalDateTime ldt = time.atStartOfDay();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH'h'mm'm'ss's'");
		date = ldt.format(formatter);
		return date;
	}*/
	
	public static String strToTime(String timeStr) {
		String date = null;
		try {
			SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd_HH'h'mm'm'ss's'");
			Date date1 = sdfIn.parse(timeStr);
			SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			date = sdfOut.format(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static String addMonth(String oldTime, int month) {
		String newTime = null;
		
		try {
			SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd_HH'h'mm'm'ss's'");
			Date date1 = sdfIn.parse(oldTime);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);
			cal.add(Calendar.MONTH, month);
			
			SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MM-dd_HH'h'mm'm'ss's'");
			newTime = sdfOut.format(cal.getTime());
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return newTime;
	}
	
	/*
	public static void main(String[] args) {
		System.out.println(addMonth(getCurrentTimeToStr(), 5));
	}*/
}
