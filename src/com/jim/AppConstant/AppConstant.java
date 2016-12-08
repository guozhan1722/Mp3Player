package com.jim.AppConstant;

public interface AppConstant {
	public final int UPDATE_ID = 1;
	public final int ABOUT_ID = 2;

	// http://192.168.0.11/mp3list/resources.xml
	public final String SERVER_ADDR = "192.168.0.15";
	public final String DIR = "/mp3list/";
	public final String FILE_NAME = "resources.xml";
	public final String DOWNLOAD_XML_URL = "http://" + SERVER_ADDR + DIR
					+ FILE_NAME;
	public final String DOWNLOAD_MP3_PATH = "http://" + SERVER_ADDR + DIR;

	//public final String DOWNLOAD_XML_URL = "http://192.168.0.15/mp3list/resources.xml";
}
