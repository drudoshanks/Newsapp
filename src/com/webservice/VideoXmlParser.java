package com.webservice;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.utils.Utils;



public class VideoXmlParser extends DefaultHandler {

	// string builder acts as a buffer
	StringBuilder builder;


	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);

		// Create StringBuilder object to store xml node value
		builder = new StringBuilder();

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);

		if (localName.equalsIgnoreCase("video_name")) {

			Utils.video_model.setStoryUrl(builder.toString());;
		}
		else if (localName.equalsIgnoreCase("Publish_Date")) {

			Utils.video_model.setPub_date(builder.toString());

			
		}  else if (localName.equalsIgnoreCase("Header")) {

			Utils.video_model.setPropertyValue(builder.toString());
		}

		else if (localName.equalsIgnoreCase("Content")) {

			Utils.video_model.setPropertySTORY(builder.toString());
		}

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);

		/****** Read the characters and append them to the buffer ******/
		String tempString = new String(ch, start, length);
		builder.append(tempString);
	}

}
