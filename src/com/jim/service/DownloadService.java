package com.jim.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.jim.AppConstant.AppConstant;
import com.jim.download.HttpDownloader;
import com.jim.model.Mp3Info;

public class DownloadService extends Service
{

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Mp3Info mp3Info = (Mp3Info)intent.getSerializableExtra("mp3Info");
		//add thread to download mp3 files
		DownloadThread downloadThread = new DownloadThread(mp3Info);
		Thread thread = new Thread(downloadThread);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	class DownloadThread implements Runnable
	{
		private Mp3Info mp3Info = null;

		public DownloadThread(Mp3Info mp3Info)
		{
			super();
			this.mp3Info = mp3Info;
		}

		@Override
		public void run()
		{
			String urlStr = AppConstant.DOWNLOAD_MP3_PATH + mp3Info.getMp3Name();
			System.out.println("Start thread download from " + urlStr);
			HttpDownloader httpDownloader = new HttpDownloader();
			int result = httpDownloader.downloadFile(urlStr, "mp3/", mp3Info.getMp3Name());
			System.out.println("Download result is " + result);
		}
		
	}
	

}
