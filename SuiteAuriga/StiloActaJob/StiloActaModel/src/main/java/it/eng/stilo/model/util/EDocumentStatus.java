/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.EnumSet;

/**
 * IDLE: generic initial status
 * READY: ready to be consumed
 * CONSUMED: already consumed
 * FIXABLE: waiting for a fix
 */
public enum EDocumentStatus implements Mappable {

    IDLE("I"),
    ELABORATION("E"), //stato dei documenti in elaborazione
    ERROR("X"), //stato dei docuemnti in errore
    READY("R"), // stato dei documenti pronti per essereprocessati
    READY2("R2"), // stato dei documenti che sono rimasti in elaborazione e che sono stati rimessi in cosa per l'elaborazione
    CONSUMED("C"), // stato dei documenti trasferiti 
    FIXABLE("F");

    private String code;

    EDocumentStatus(final String eCode) {
        this.code = eCode;
    }

    public static EDocumentStatus resolve(final String eCode) {
        return EnumSet.allOf(EDocumentStatus.class).stream().filter(ds -> ds.code.equalsIgnoreCase(eCode)).findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported status %s", eCode)));
    }

    @Override
    public String getCode() {
        return code;
    }

}
