package it.eng.utility;

import java.util.Calendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XSDateTimeCustomBinder extends XmlAdapter<String, Calendar> {

	@Override
	public Calendar unmarshal(String s) throws Exception {
		return javax.xml.bind.DatatypeConverter.parseDateTime(s);
	}

	@Override
	public String marshal(Calendar value) throws Exception {
		if (value == null) {
			return null;
		}
		return javax.xml.bind.DatatypeConverter.printDateTime(value);
	}

}
