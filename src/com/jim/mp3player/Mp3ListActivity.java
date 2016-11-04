package com.jim.mp3player;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jim.AppConstant.AppConstant;
import com.jim.download.HttpDownloader;
import com.jim.model.Mp3Info;
import com.jim.xml.Mp3ListContentHandler;


public class Mp3ListActivity extends ListActivity {

	String DownloadedXML=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(0, AppConstant.UPDATE_ID, 1,R.string.action_update);
        menu.add(0, AppConstant.ABOUT_ID, 2,R.string.action_about);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        System.out.println("pressed id ------>" + id);
        
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == AppConstant.UPDATE_ID)
        {
        	System.out.println("you pressed update item");
        	downloadXML();
        	//System.out.println(DownloadedXML);
        	//user pressed update itme
        }
        else if(id == AppConstant.ABOUT_ID)
        {
        	//user pressed about item
        	System.out.println("you pressed about item");
        	parse(DownloadedXML);
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void downloadXML()
    {
    	XmlDownloadThread xmlDownloadThread = new XmlDownloadThread();
    	xmlDownloadThread.start();
    }
    
    private List<Mp3Info> parse(String xmlStr)
    {
    	SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    	try{
    		XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
    		List<Mp3Info> infos = new ArrayList<Mp3Info>();
    		Mp3ListContentHandler mp3ListContentHandler = new Mp3ListContentHandler(infos);
    		xmlReader.setContentHandler(mp3ListContentHandler);
    		xmlReader.parse(new InputSource(new StringReader(xmlStr)));
    		for(int i=0; i<infos.size();i++)
    		{
    			System.out.println("parse has completed-->"+infos.get(i));
    		}
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return null;
    }
    
	class XmlDownloadThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
	    	HttpDownloader httpDownloader = new HttpDownloader();
	    	DownloadedXML =httpDownloader.download(AppConstant.DOWNLOAD_XML_URL);
	    	System.out.println("download---->"+DownloadedXML);
		}
		
	}
}
