package it.eng.converter;

import org.apache.commons.beanutils.locale.converters.BigDecimalLocaleConverter;
import org.apache.commons.lang3.StringUtils;

public class BigDecimalConverters extends BigDecimalLocaleConverter{

	@Override
	public Object convert(Class arg0, Object arg1) {
		if (arg1 instanceof String){
			if (StringUtils.isEmpty((String)arg1)){
				return null;
			} else return super.convert(arg1);
		} else return super.convert(arg1);
	}

}
