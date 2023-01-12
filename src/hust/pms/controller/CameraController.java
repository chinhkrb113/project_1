package hust.pms.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

import hust.pms.model.WebcamInfo;
import hust.pms.view.staff.StaffCenterForm;

public class CameraController {
	
	public ArrayList<WebcamInfo> getWebcam() {
		int webcamCounter = 0;
		ArrayList<WebcamInfo> options = new ArrayList<>();
		for (Webcam webcam:Webcam.getWebcams()) {
//			Webcam webcam = Webcam.getDefault();
    		WebcamInfo webcamInfo = new WebcamInfo();
    		webcamInfo.setWebCamIndex(webcamCounter);
    		webcamInfo.setWebCamName(webcam.getName());
    		options.add(webcamInfo);
    		webcamCounter++;
    	}
		return options;
	}
	
	public void getImage(Webcam webcam) {
		//StaffCenterForm.currentDateTime = TimeHelper.getCurrentTimeToString();
		//StaffCenterForm.currentCIDAndDateTime = "./img_history/" + StaffCenterForm.currentCID + "_" + StaffCenterForm.currentDateTime + ".jpg";
		try {
    		//File outputFile = new File();
			ImageIO.write(webcam.getImage(), "jpg", new File(StaffCenterForm.currentFullCIDAndDateTime));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
