/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * classe di utilità per la pulizia dei campi
 * 
 * @author jravagnan
 * 
 */
public class XmlFieldUtil {

	/**
	 * ripulisce da caratteri spuri la stringa del campo
	 * 
	 * @param in
	 * @return
	 */
	public static String cleanField(String in) {
		if (in != null) {
			in = in.trim();
			in = in.replace("\r", "");
			in = in.replace("\n", "");
			in = in.replace("\t", "");
		}
		return in;
	}

	public static boolean checkIfNumber(String in) {
		in = cleanField(in);
		if (in == null || in.length() == 0)
			return false;
		for (int i = 0; i < in.length(); i++) {
			// If we find a non-digit character we return false.
			if (!Character.isDigit(in.charAt(i)))
				return false;
		}

		return true;
	}

	public static boolean checkIfDate(String in, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
		try {
			if (in != null) {
				dateFormat.parse(cleanField(in));
			} else
				return false;
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

}
