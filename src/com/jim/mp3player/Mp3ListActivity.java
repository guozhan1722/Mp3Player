package com.jim.mp3player;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jim.AppConstant.AppConstant;
import com.jim.download.HttpDownloader;
import com.jim.model.Mp3Info;
import com.jim.service.DownloadService;
import com.jim.xml.Mp3ListContentHandler;

public class Mp3ListActivity extends ListActivity
{
	private List<Mp3Info> infos = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, AppConstant.UPDATE_ID, 1, R.string.action_update);
		menu.add(0, AppConstant.ABOUT_ID, 2, R.string.action_about);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.action_settings)
		{
			return true;
		}
		else if (id == AppConstant.UPDATE_ID)
		{
			// user pressed update item
			UpdateXmlTask updateXmlTask = new UpdateXmlTask();
			updateXmlTask.execute(AppConstant.DOWNLOAD_XML_URL);
		}
		else if (id == AppConstant.ABOUT_ID)
		{
			// user pressed about item
		}
		return super.onOptionsItemSelected(item);
	}

	private List<Mp3Info> parse(String xmlStr)
	{
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try
		{
			XMLReader xmlReader = saxParserFactory.newSAXParser()
							.getXMLReader();
			infos = new ArrayList<Mp3Info>();
			Mp3ListContentHandler mp3ListContentHandler = new Mp3ListContentHandler(
							infos);
			xmlReader.setContentHandler(mp3ListContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return infos;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Mp3Info mp3Info = infos.get(position);
		Intent intent = new Intent();
		intent.putExtra("mp3Info", mp3Info);
		intent.setClass(this,DownloadService.class);
		startService(intent);
		
	}

	void buildList(List<Mp3Info> infos)
	{
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < infos.size(); i++)
		{
			Mp3Info mp3Info = infos.get(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("mp3_name", mp3Info.getMp3Name());
			map.put("mp3_size", mp3Info.getMp3Size());
			list.add(map);
		}

		String[] from = new String[] { "mp3_name", "mp3_size" };
		int[] to = new int[] { R.id.mp3_name, R.id.mp3_size };
		SimpleAdapter simpleAdapter = new SimpleAdapter(Mp3ListActivity.this,
						list, R.layout.mp3info_items, from, to);
		setListAdapter(simpleAdapter);

	}

	class UpdateXmlTask extends AsyncTask<String, Integer, String>
	{

		@Override
		protected String doInBackground(String... arg0)
		{
			// download xml from tomCat server
			HttpDownloader httpDownloader = new HttpDownloader();
			String xmlStr = httpDownloader.download(arg0[0]);

			// parse the xml file read from downloader
			infos = parse(xmlStr);

			return xmlStr;
		}

		@Override
		protected void onPostExecute(String result)
		{
			buildList(infos);
			Toast.makeText(Mp3ListActivity.this, "download is done!!",
							Toast.LENGTH_SHORT).show();
		}

	}

}
