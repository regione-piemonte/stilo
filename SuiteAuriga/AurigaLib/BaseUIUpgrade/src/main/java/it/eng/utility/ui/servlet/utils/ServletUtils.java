/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServletUtils {

	public static boolean findMatchSecurity(String riga) {
		Pattern pattern = Pattern.compile("[0-9,a-z,A-Z,_]*");
		Matcher matcher = pattern.matcher(riga);
		return matcher.matches();
	}
	
}
