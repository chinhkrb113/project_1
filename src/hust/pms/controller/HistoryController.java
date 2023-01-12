package hust.pms.controller;

import java.util.ArrayList;

import hust.common.Constants;
import hust.pms.model.Employee;
import hust.pms.model.History;

public class HistoryController {
	
	public ArrayList<History> getHistory() {
		History his = new History();
		return his.getHistory(Employee.currentEmployeeCompanyID);
	}
	
	public ArrayList<History> getHistoryByTime(String startDate, String endDate) {
		History his = new History();
		return his.getHistoryByTime(Employee.currentEmployeeCompanyID, startDate, endDate);
	}
	
	public void addHistory(History his) {
		his.addHistory();
	}
	
	public void updateHistory(History his) {
		his.updateHistory();
	}
	
	public boolean checkInOutSide(String cardID, byte visitStatus) {
		History his = new History();
		return his.checkInOutSide(cardID, visitStatus);
	}
	
	public String getURLImageLPIn(String cardID, byte visitStatus) {
		History his = new History();
		return his.getURLImageLPIn(cardID, visitStatus);
	}
	
	public String getURLImageLPOut(String cardID, byte visitStatus) {
		History his = new History();
		return his.getURLImageLPOut(cardID, visitStatus);
	}
	
	public String getStoredPlate(String cardID, byte visitStatus) {
		History his = new History();
		return his.getStoredPlate(cardID, visitStatus);
	}
	
	public String[] getURLImage(long historyID) {
		History his = new History();
		return his.getURLImage(historyID);
	}
	
	public String getTimeIn(String cardID, String imgLPOut) {
		History his = new History();
		return his.getTimeIn(cardID, imgLPOut);
	}
	
	public void markAsDoubt(String cardID) {
		History his = new History();
		his.markAsDoubt(Constants.MARK_AS_DOUBT, cardID);
	}
	
	public void markAsDoubt(String cardID, String imgLPOut) {
		History his = new History();
		his.markAsDoubt(Constants.MARK_AS_DOUBT, cardID, imgLPOut);
	}
}
