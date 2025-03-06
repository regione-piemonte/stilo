package it.eng.dm.sira.service.util;

import org.apache.commons.lang3.StringUtils;

public class PopulationUtils {

	public static String createLabel(String name) {
		String out;
		if (StringUtils.isNotEmpty(name)) {
			out = name.substring(0, 1).toUpperCase();
			out = out.concat(name.substring(1, name.length()));
			for (int i = 1; i < out.length(); i++) {
				char lettera = out.charAt(i);
				char precedente;
				if (i != 1) {
					precedente = out.charAt(i - 1);
					if (Character.isUpperCase(lettera) && !Character.isUpperCase(precedente) && i != out.length() - 1) {
						out = out.substring(0, i) + " " + out.substring(i, out.length());
						i++;
					}
				}
			}
		} else {
			out = name;

		}
		return out;
	}

	public static void main(String[] args) {
		String name = "DataInizio";
		System.out.println(PopulationUtils.createLabel(name));
	}
}
