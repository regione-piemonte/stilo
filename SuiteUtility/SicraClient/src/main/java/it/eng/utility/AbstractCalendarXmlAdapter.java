package it.eng.utility;

import java.util.Calendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public abstract class AbstractCalendarXmlAdapter extends XmlAdapter<String, Calendar> {

	// protected org.joda.time.format.DateTimeFormatter fmt;

	public Calendar unmarshal(String s) {
		// return fmt.parseDateTime(s).toCalendar(null);
		return null;
	}

	public String marshal(Calendar value) {
		// return fmt.print(value.getTime().getTime());
		return null;
	}

}
