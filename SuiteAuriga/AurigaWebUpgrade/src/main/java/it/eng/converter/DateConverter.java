/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import org.apache.commons.beanutils.converters.DateTimeConverter;

public class DateConverter extends DateTimeConverter {

	public DateConverter(){}
	public DateConverter(Object defaultValue) {super (defaultValue); }
	@Override
	protected Class getDefaultType() {
		
		return Date.class;
	}
	
	@Override
	public Object convert(Class type, Object value) {
		if (value == null) return null;
		return super.convert(type, value);
	}
	
}
