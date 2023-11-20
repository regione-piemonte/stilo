/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.client.NumberFormat;


public class NumberFormatUtility {
	
	public static String getFormattedValue(String value) {
		if(value != null && !"".equals(value)) {
			try {
				String pattern = "#,##0.00";
				String groupingSeparator = LocaleInfo.getCurrentLocale().getNumberConstants().groupingSeparator();
				String decimalSeparator = LocaleInfo.getCurrentLocale().getNumberConstants().decimalSeparator();			
				double val = new Double(NumberFormat.getFormat(pattern).parse(value)).doubleValue();			
				value = NumberFormat.getFormat(pattern).format(val).replace(groupingSeparator, ".").replace(decimalSeparator, ",");
			} catch(Exception e) {}
		}	
		return value;
	}
}