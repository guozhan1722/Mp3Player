package com.jim.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.jim.utils.FileUtils;

public class HttpDownloader {
	/*
	 * this function returns
	 *  -1 : download error
	 *   0 : download success
	 *   1 : file already exist
	 */
	public int downloadFile(String urlStr,String path, String fileName)
	{
		InputStream inputStream = null;
		try
		{
			FileUtils fileUtils = new FileUtils(); 
			if(fileUtils.isFileExist(fileName, path))
			{
				return 1;
			}
			else 
			{
				URL url = new URL(urlStr);
				HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
				inputStream = urlConn.getInputStream();
				File resultFile = fileUtils.write2SDFromInput(path, fileName, inputStream);
				if(resultFile == null)
				{
					return -1;
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
		finally{
			try
			{
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return 0;
	}
	
	public String download(String urlStr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer=null;
		
		try {
			URL url = new URL(urlStr);

			HttpURLConnection urlConn = (HttpURLConnection) url
							.openConnection();

			buffer = new BufferedReader(new InputStreamReader(
							urlConn.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
