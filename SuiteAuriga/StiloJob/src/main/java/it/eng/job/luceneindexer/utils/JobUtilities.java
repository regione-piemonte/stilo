/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Calendar;
import java.util.Random;

public class JobUtilities {

	/**
	 * 
	 * @return String
	 */
	public static String getStrDate() {
		Calendar c = Calendar.getInstance();

		int m = c.get(Calendar.MONTH);
		int d = c.get(Calendar.DATE);
		int h = c.get(Calendar.HOUR_OF_DAY);
		int mi = c.get(Calendar.MINUTE);
		int ms = c.get(Calendar.MILLISECOND);
		String mm = Integer.toString(m);
		String dd = Integer.toString(d);
		String hh = Integer.toString(h);
		String mmi = Integer.toString(mi);
		String mms = Integer.toString(ms);

		String repDt = c.get(Calendar.YEAR) + (m < 10 ? "0" + mm : mm) + (d < 10 ? "0" + dd : dd);
		String repOra = (h < 10 ? "0" + hh : hh) + (mi < 10 ? "0" + mmi : mmi) + (ms < 10 ? "0" + mms : mms);

		return repDt + repOra;
	}

	// Metodi di utilit? per generare una stringa pseudocasuale di 20
	// cifre esadecimali

	// final e static perch?: 1) sono cos? e non server l'override
	// 2) ci guadagni un zinzino.
	public final static String randomHexString() {
		StringBuilder nmfl = new StringBuilder();
		byte casuali[] = new byte[10];
		new Random().nextBytes(casuali);
		for (int jj = 0; jj < casuali.length; jj++) {
			nmfl.append(upper(casuali[jj]));
			nmfl.append(lower(casuali[jj]));
		}
		return nmfl.toString();
	}

	private static final String[] hexdigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private static final String upper(byte ottetto) {
		return hexdigits[((ottetto + 128) & 0xF0) >> 4];
	}

	private static final String lower(byte ottetto) {
		return hexdigits[((ottetto + 128) & 0x0F)];
	}
}
