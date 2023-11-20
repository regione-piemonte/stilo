/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.doqui.acta.acaris.common.EnumObjectType;

import java.util.EnumSet;

public enum EDossierType {

	FASCICOLO_REALE_EREDITATO(EnumObjectType.FASCICOLO_REALE_EREDITATO_PROPERTIES_TYPE),
	FASCICOLO_REALE_LIBERO(EnumObjectType.FASCICOLO_REALE_LIBERO_PROPERTIES_TYPE),
	FASCICOLO_REALE_LEGISLATURA(EnumObjectType.FASCICOLO_REALE_LEGISLATURA_PROPERTIES_TYPE),
	FASCICOLO_REALE_ANNUALE(EnumObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE),
	FASCICOLO_REALE_CONTINUO(EnumObjectType.FASCICOLO_REALE_CONTINUO_PROPERTIES_TYPE),
	FASCICOLO_STD(EnumObjectType.FASCICOLO_STD_PROPERTIES_TYPE),
	DOSSIER(EnumObjectType.DOSSIER_PROPERTIES_TYPE);

    private EnumObjectType code;

    EDossierType(final EnumObjectType eCode) {
        this.code = eCode;
	}

    public static final EDossierType resolve(final String eCode) {
        return EnumSet.allOf(EDossierType.class).stream().filter(dt -> dt.code.equals(eCode)).findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported code %s", eCode)));
    }

	public EnumObjectType getCode() {
		return code;
	}

}
