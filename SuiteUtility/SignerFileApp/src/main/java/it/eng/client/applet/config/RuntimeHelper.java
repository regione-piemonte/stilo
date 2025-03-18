/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Enumeration;
import java.util.Properties;

public class RuntimeHelper {
	public static void allprops(){
		Properties p = System.getProperties();
		Enumeration keys = p.keys();
		while (keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			String value = (String)p.get(key);
			System.out.println(key + ": " + value);
		}
	}
}
