package com.jim.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.jim.model.Mp3Info;

/**
 * @author jim
 * 
 */
public class Mp3ListContentHandler extends DefaultHandler
{

	private List<Mp3Info> infos;
	private Mp3Info mp3Info = null;
	private String tagName = null;

	public List<Mp3Info> getInfos()
	{
		return infos;
	}

	public void setInfos(List<Mp3Info> infos)
	{
		this.infos = infos;
	}

	public Mp3ListContentHandler(List<Mp3Info> infos)
	{
		super();
		this.infos = infos;
	}

	@Override
	public void characters(char[] ch, int start, int length)
					throws SAXException
	{

		String tmp = new String(ch, start, length);

		if (tagName.equals("id"))
		{
			mp3Info.setId(tmp);
		} else if (tagName.equals("mp3.name"))
		{
			mp3Info.setMp3Name(tmp);
		} else if (tagName.equals("mp3.size"))
		{
			mp3Info.setMp3Size(tmp);
		} else if (tagName.equals("lrc.name"))
		{
			mp3Info.setLrcName(tmp);
		} else if (tagName.endsWith("lrc.size"))
		{
			mp3Info.setLrcSize(tmp);
		}

	}


	@Override
	public void endDocument() throws SAXException
	{
		// TODO Auto-generated method stub
		System.out.println("endDocument");
		super.endDocument();
		
	}

	@Override
	public void endElement(String uri, String localName, String qName)
					throws SAXException
	{

		if(localName.equals("resource"))
		{
			System.out.println("end of element" + mp3Info);
			infos.add(mp3Info);
		}
		tagName = "";
	}

	@Override
	public void startDocument() throws SAXException
	{
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
					Attributes attributes) throws SAXException
	{
		this.tagName = localName;
		if (tagName.equals("resource"))
		{
			mp3Info = new Mp3Info();
		}

	}

}
