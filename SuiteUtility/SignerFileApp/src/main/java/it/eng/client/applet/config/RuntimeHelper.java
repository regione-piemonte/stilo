package it.eng.client.applet.config;

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
