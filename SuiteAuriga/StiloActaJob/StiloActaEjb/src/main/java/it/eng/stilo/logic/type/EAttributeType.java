/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.EnumSet;

public enum EAttributeType {

    CARTACEO("CAR"),
    COPIA_CARTACEA("CCA"),
    INTERNO("INT"),
    PRESENTE("PRE"),
    PERSONALE("PER"),
    SENSIBILE("SEN"),
    RISERVATO("RIS"),
    FORMALE("FOR"),
    ANNOTAZIONE_DOC("AND"),
    ANNOTAZIONE_CLASSIFICAZIONE_CORRENTE("ANC"),
    ANNOTAZIONE_FORMALE("AN_FO"),
    REGISTRATO("REG"),
    ALLEGATI("ALL"),
    MODIFICABILE("MOD"),
    DEFINITIVO("DEF"),
    AUTENTICATO("AUT"),
    AUTENTICATO_CON_FIRMA("AUF"),
    AUTENTICATO_AUTENTICO("AUA"),
    COMPOSIZIONE_MULTIPLA("CMU"),
    REINVIO_SBUSTAMENTO("RSB"),
    ARCHIVIO_CORRENTE("ARC"),
    FIRMA_ELETTRONICA_DIGITALE("FED"),
    FIRMA_ELETTRONICA("FEL"),
    DA_CONSERVARE("CNS"),
    PRONTO_PER_CONSERVARE("PCO"),
    CONSERVATO("CTO"),
    REGISTRAZIONE_IN_ARRIVO("RAR"),
    REGISTRAZIONE_IN_PARTENZA("RPA"),
    PROTOCCOLO_RISERVATO("PRI"),
    PROTOCOLLO_ANNULLATO("PAN"),
    VERIFICA_FIRMA("VER_FIRMA"),
    VITAL_RECORD("VIT_REC");

    private String code;

    EAttributeType(final String eCode) {
        this.code = eCode;
    }

    public static final EAttributeType resolve(final String eCode) {
        return EnumSet.allOf(EAttributeType.class).stream().filter(at -> at.code.equals(eCode)).findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported code %s", eCode)));
    }

    public String getCode() {
        return code;
    }

}
