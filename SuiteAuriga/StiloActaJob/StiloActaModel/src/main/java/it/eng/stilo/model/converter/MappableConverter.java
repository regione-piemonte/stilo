/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.model.util.Mappable;

import javax.persistence.AttributeConverter;

public abstract class MappableConverter<T extends Mappable> implements AttributeConverter<T, String> {

    @Override
    public String convertToDatabaseColumn(T eStatus) {
        if( eStatus == null)
        	return null;
    	return eStatus.getCode();
    }

}
