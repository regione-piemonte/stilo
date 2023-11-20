/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.model.util.EClassificationStatus;

public class ClassificationStatusConverter extends MappableConverter<EClassificationStatus> {

    @Override
    public EClassificationStatus convertToEntityAttribute(String classification) {
        return EClassificationStatus.resolve(classification);
    }

}
