package it.eng.utility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XSDoubleCustomBinder extends XmlAdapter<String, Double> {

	private final String pattern;
	private final Locale locale;
	private final DecimalFormatSymbols formatSymbols;
	// private final boolean allowDecimals = true;

	public XSDoubleCustomBinder() {
		this.pattern = "0.00";
		this.locale = Locale.ENGLISH;
		this.formatSymbols = locale != null ? DecimalFormatSymbols.getInstance(locale) : DecimalFormatSymbols.getInstance();
	}

	@Override
	public Double unmarshal(String s) throws Exception {
		if (s == null) {
			return null;
		}
		try {
			final Number nbr = getFormat().parse(s);
			return nbr.doubleValue();
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return Double.valueOf(s);
	}

	@Override
	public String marshal(Double value) throws Exception {
		if (value == null) {
			return null;
		}
		return getFormat().format(value);
	}

	private NumberFormat getFormat() {
		NumberFormat format = null;
		if (pattern != null) {
			format = new DecimalFormat(pattern, formatSymbols);
		} else if (locale != null) {
			format = NumberFormat.getNumberInstance(locale);
		} else {
			format = NumberFormat.getNumberInstance();
		}
		// if (!allowDecimals) {
		// format.setParseIntegerOnly(true);
		// }
		return format;
	}

}
