package com.webservice;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.model.OpDetailSubModel;
import com.utils.Utils;

public class OpXmlParser extends DefaultHandler {

	// string builder acts as a buffer
	StringBuilder builder;

	ArrayList<OpDetailSubModel> mainmodel = new ArrayList<OpDetailSubModel>();

	OpDetailSubModel model;

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

		if (localName.equalsIgnoreCase("pub_date")) {
			Utils.op_model.setPub_date(builder.toString());
		} else if (localName.equalsIgnoreCase("image")) {
			Utils.op_model.setImage(builder.toString());
		} else if (localName.equalsIgnoreCase("question")) {
			Utils.op_model.setQuestion(builder.toString());
		} else if (localName.equalsIgnoreCase("option")) {

			model = new OpDetailSubModel();
			model.setOption(builder.toString());

		} else if (localName.equalsIgnoreCase("optionid")) {

			model.setOptionid(Integer.parseInt(builder.toString()));
			mainmodel.add(model);

		} else if (localName.equalsIgnoreCase("createdate")) {

			Utils.op_model.setOptions(mainmodel);
			Utils.op_model.setCreatedate(builder.toString());
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
