/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

public class DateConverter extends DateLocaleConverter{

	@Override
	public Object convert(Object value) {
		if (value == null) return null;
		return super.convert(value);
	}
}
