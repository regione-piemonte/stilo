package it.eng.utility;

import java.util.Calendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XSDateCustomBinder extends XmlAdapter<String, Calendar> {

	@Override
	public Calendar unmarshal(String s) throws Exception {
		return javax.xml.bind.DatatypeConverter.parseDate(s);
	}

	@Override
	public String marshal(Calendar value) throws Exception {
		if (value == null) {
			return null;
		}
		String s = javax.xml.bind.DatatypeConverter.printDate(value);
		final int k = s.indexOf("-");
		if (k == 0) {
			if (11 <= s.length()) {
				s = s.substring(1, 11);
			}
		} else if (10 <= s.length()) {
			s = s.substring(0, 10);
		}

		// int k = s.indexOf("+");
		// if (k < 0) {
		// k = s.indexOf("-");
		// }
		// if (k > -1) {
		// s = s.substring(0, k);
		// }
		return s;
	}

}
