package it.eng.converter;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

public class DateConverter extends DateLocaleConverter{

	@Override
	public Object convert(Object value) {
		if (value == null) return null;
		return super.convert(value);
	}
}
