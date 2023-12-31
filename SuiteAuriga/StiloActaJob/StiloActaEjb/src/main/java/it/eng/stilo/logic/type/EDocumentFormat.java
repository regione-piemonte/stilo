/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.EnumSet;

public enum EDocumentFormat {

	DETERMINA("DETERMINAZIONE"),
	DETERMINA_DIR("DETERMINAZIONE DIRIGENZIALE"),
    DETERMINA_OMISSIS("DETERMINAZIONE CON OMISSIS"),
    DETERMINA_DIR_OMISSIS("DETERMINAZIONE DIRIGENZIALE CON OMISSIS"),
    DETERMINA_PUBBLICATA("DETERMINAZIONE PUBBLICATA"),
    REGISTRO_DETERMINE("REGISTRO DELLE DETERMINAZIONI DIRIGENZIALI"),
    REGISTRO_ORDINANZE("REGISTRO DELLE DETERMINAZIONI DIRIGENZIALI"),
    REGISTRO_DECRETI_PRESIDENTE_GIUNTA("REGISTRO DELLE DETERMINAZIONI DIRIGENZIALI"),
    REGISTRO_DELIBERE_GIUNTA("REGISTRO DELLE DETERMINAZIONI DIRIGENZIALI"),
    DECRETO_SINDACO_CONSIGLIERE("DECRETO DEL SINDACO/CONSIGLIERE DELEGATO"),
    DECRETO_SINDACO_CONSIGLIERE_OMISSIS("DECRETO DEL SINDACO/CONSIGLIERE DELEGATO CON OMISSIS"),
    DECRETO_SINDACO_VICE_SINDACO("DECRETO DEL SINDACO/VICESINDACO"),
    DECRETO_SINDACO_VICE_SINDACO_OMISSIS("DECRETO DEL SINDACO/VICESINDACO CON OMISSIS"),
    DECRETO_PRESIDENTE_GIUNTA("DECRETO"),
    DECRETO_PRESIDENTE_GIUNTA_OMISSIS("DECRETO CON OMISSIS"),
    DECRETO("DECRETO"),
    DECRETO_OMISSIS("DECRETO CON OMISSIS"),
    LIQUIDAZIONI("ATTO DI LIQUIDAZIONE/RISCOSSIONE"),
    ORDINANZA("ORDINANZA"),
    ORDINANZA_OMISSIS("ORDINANZA CON OMISSIS"),
    DELIBERA_CONSIGLIO("DELIBERAZIONE DI CONSIGLIO"),
    DELIBERA_CONSIGLIO_OMISSIS("DELIBERAZIONE DI CONSIGLIO CON OMISSIS"),
    DELIBERA_GIUNTA("DELIBERAZIONE"),
    DELIBERA_GIUNTA_OMISSIS("DELIBERAZIONE CON OMISSIS"),
    REGISTRO_PUBBLICAZIONI("REGISTRO DELLE PUBBLICAZIONI");

    private String code;

    EDocumentFormat(final String eCode) {
        this.code = eCode;
    }

    public static final EDocumentFormat resolve(final String eCode) {
        return EnumSet.allOf(EDocumentFormat.class).stream().filter(dt -> dt.code.equals(eCode)).findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported code %s", eCode)));
    }
    
    public String getCode() {
        return this.code;
    }
}
