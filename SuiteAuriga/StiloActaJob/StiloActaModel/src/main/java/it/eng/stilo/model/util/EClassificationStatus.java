/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.EnumSet;

/**
 * IDLE: generic initial status
 * CONSUMED: classification successfully done
 * FIXABLE: waiting for a fix after a failure
 */
public enum EClassificationStatus implements Mappable {
	
	IDLE("I"),
	CLASSIFIED("C"),
	FIXABLE("F");
	
	private String code;

	EClassificationStatus(String code) {
		this.code = code;
	}
	
	public static EClassificationStatus resolve(final String eCode) {
        return EnumSet.allOf(EClassificationStatus.class).stream().filter(cs -> cs.code.equalsIgnoreCase(eCode)).findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported status %s", eCode)));
    }

    @Override
    public String getCode() {
        return code;
    }

}
