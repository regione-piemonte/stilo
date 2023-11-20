/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.EnumSet;

public enum EEfficacy {

	PERFECT_EFFICACY("PERFETTO ED EFFICACE"),
	PERFECT_EFFICACY_UNSIGNED("PERFETTO ED EFFICACE MA NON FIRMATO");

    private String code;

    EEfficacy(final String eCode) {
        this.code = eCode;
    }

    public static final EEfficacy resolve(final String eCode) {
        return EnumSet.allOf(EEfficacy.class).stream().filter(dt -> dt.code.equals(eCode)).findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported code %s", eCode)));
    }
    
    public String getCode() {
        return this.code;
    }
}
