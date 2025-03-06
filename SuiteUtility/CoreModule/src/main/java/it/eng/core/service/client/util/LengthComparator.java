package it.eng.core.service.client.util;

import java.util.Comparator;

public class LengthComparator implements Comparator<String> {

	@Override
	public int compare(String arg0, String arg1) {
		int length0 = arg0 != null ? arg0.length() : 0;
		int length1 = arg1 != null ? arg1.length() : 0;		
		if (length0 > length1) {
			return -1;
		} else if (length0 < length1) {
			return 1;
		}
		return 0;		
	}

}
