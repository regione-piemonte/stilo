/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.stilo.model.converter;

import it.eng.stilo.model.util.EDocumentStatus;

public class DocumentStatusConverter extends MappableConverter<EDocumentStatus> {

    @Override
    public EDocumentStatus convertToEntityAttribute(String status) {
        return EDocumentStatus.resolve(status);
    }

}
