/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.EnumSet;

public enum EIntegrationResourceCMTO  {

    DETERMINA_CMTO("DD", "yyyy", 12, EDocumentFormat.DETERMINA_DIR, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ALLEGATO_DETERMINA_CMTO("DD_ALL", EDocumentFormat.DETERMINA, EEfficacy.PERFECT_EFFICACY),
    DETERMINA_OMISSIS_CMTO("DDO", "3MM - yyyy", 3, EDocumentFormat.DETERMINA_DIR_OMISSIS, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ALLEGATO_DETERMINA_OMISSIS_CMTO("DDO_ALL", EDocumentFormat.DETERMINA_OMISSIS, EEfficacy.PERFECT_EFFICACY),
    REGISTRO_TRIMESTRALE_DETERMINE_CMTO("DDRT", "yyyy", 12, EDocumentFormat.REGISTRO_DETERMINE,
            EEfficacy.PERFECT_EFFICACY_UNSIGNED, 5, 99),
    REGISTRO_PUBBLICAZIONI_CMTO("RPUBBL", "yyyy", 12, EDocumentFormat.REGISTRO_PUBBLICAZIONI,
            EEfficacy.PERFECT_EFFICACY, 5, 99),
    ORDINANZA_CMTO("ORD", "yyyy", 1, EDocumentFormat.ORDINANZA, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ORDINANZA_OMISSIS_CMTO("ORDO", "yyyy", 1, EDocumentFormat.ORDINANZA_OMISSIS, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ALLEGATO_ORDINANZA_CMTO("ORD_ALL",  EDocumentFormat.ORDINANZA, EEfficacy.PERFECT_EFFICACY),
    ALLEGATO_ORDINANZA_OMISSIS_CMTO("ORDO_ALL",  EDocumentFormat.ORDINANZA_OMISSIS, EEfficacy.PERFECT_EFFICACY),
    
    DECRETO_PRESIDENTE_GIUNTA_CMTO("DPGR", "yyyy", 1, EDocumentFormat.DECRETO_PRESIDENTE_GIUNTA, EEfficacy.PERFECT_EFFICACY, 5, 99),
    DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO("DPGRO", "yyyy", 1, EDocumentFormat.DECRETO_PRESIDENTE_GIUNTA_OMISSIS, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_CMTO("DPGR_ALL",  EDocumentFormat.DECRETO_PRESIDENTE_GIUNTA, EEfficacy.PERFECT_EFFICACY),
    ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_CMTO("DPGRO_ALL",  EDocumentFormat.DECRETO_PRESIDENTE_GIUNTA_OMISSIS, EEfficacy.PERFECT_EFFICACY),
    
	DECRETO_CMTO("DCR", "yyyy", 12, EDocumentFormat.DECRETO, EEfficacy.PERFECT_EFFICACY, 5, 99),
	ALLEGATO_DECRETO_CMTO("DCR_ALL",  EDocumentFormat.DECRETO, EEfficacy.PERFECT_EFFICACY),
	DECRETO_OMISSIS_CMTO("DCRO", "3MM - yyyy", 3, EDocumentFormat.DECRETO_OMISSIS, EEfficacy.PERFECT_EFFICACY, 5, 99),
	ALLEGATO_DECRETO_OMISSIS_CMTO("DCRO_ALL",  EDocumentFormat.DECRETO_OMISSIS, EEfficacy.PERFECT_EFFICACY),
	
	DECRETOSINDACOCONSIGLIERE_CMTO("DCRC", "yyyy", 12, EDocumentFormat.DECRETO_SINDACO_CONSIGLIERE, EEfficacy.PERFECT_EFFICACY, 5, 99),
	ALLEGATO_DECRETOSINDACOCONSIGLIERE_CMTO("DCRC_ALL",  EDocumentFormat.DECRETO_SINDACO_CONSIGLIERE, EEfficacy.PERFECT_EFFICACY),
	DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO("DCRCO", "3MM - yyyy", 3, EDocumentFormat.DECRETO_SINDACO_CONSIGLIERE_OMISSIS, EEfficacy.PERFECT_EFFICACY, 5, 99),
	ALLEGATO_DECRETOSINDACOCONSIGLIERE_OMISSIS_CMTO("DCRCO_ALL",  EDocumentFormat.DECRETO_SINDACO_CONSIGLIERE_OMISSIS, EEfficacy.PERFECT_EFFICACY),
	
	DECRETOSINDACOVICESINDACO_CMTO("DCRS", "yyyy", 12, EDocumentFormat.DECRETO_SINDACO_VICE_SINDACO, EEfficacy.PERFECT_EFFICACY, 5, 99),
	ALLEGATO_DECRETOSINDACOVICESINDACO_CMTO("DCRS_ALL",  EDocumentFormat.DECRETO_SINDACO_VICE_SINDACO, EEfficacy.PERFECT_EFFICACY),
	DECRETOSINDACOVICESINDACO_OMISSIS_CMTO("DCRSO", "3MM - yyyy", 3, EDocumentFormat.DECRETO_SINDACO_VICE_SINDACO_OMISSIS, EEfficacy.PERFECT_EFFICACY, 5, 99),
	ALLEGATO_DECRETOSINDACOVICESINDACO_OMISSIS_CMTO("DCRSO_ALL",  EDocumentFormat.DECRETO_SINDACO_VICE_SINDACO_OMISSIS, EEfficacy.PERFECT_EFFICACY),
	
	LIQUIDAZIONI_CMTO("LIQ", "yyyy", 12, EDocumentFormat.LIQUIDAZIONI, EEfficacy.PERFECT_EFFICACY, 5, 99),
	ALLEGATO_LIQUIDAZIONI_CMTO("LIQ_ALL",  EDocumentFormat.LIQUIDAZIONI, EEfficacy.PERFECT_EFFICACY),
	
	DELIBERA_CONSIGLIO_CMTO("DELCONS", "yyyy", 12, EDocumentFormat.DELIBERA_CONSIGLIO, EEfficacy.PERFECT_EFFICACY, 5, 99),
	ALLEGATO_DELIBERA_CONSIGLIO_CMTO("DELCONS_ALL",  EDocumentFormat.DELIBERA_CONSIGLIO, EEfficacy.PERFECT_EFFICACY),
	DELIBERA_CONSIGLIO_OMISSIS_CMTO("DELCONSO", "3MM - yyyy", 3, EDocumentFormat.DELIBERA_CONSIGLIO_OMISSIS, EEfficacy.PERFECT_EFFICACY, 5, 99),
	ALLEGATO_DELIBERA_CONSIGLIO_OMISSIS_CMTO("DELCONSO_ALL",  EDocumentFormat.DELIBERA_CONSIGLIO_OMISSIS, EEfficacy.PERFECT_EFFICACY)
	;
    

    private String code;
    private String volumeDescrPattern;
    private int validityMonths;
    private EDocumentFormat documentFormat;
    private EEfficacy efficacy;
    private int volumeCurrRetention;
    private int volumeGenRetention;

    EIntegrationResourceCMTO(final String eCode, final String ePattern, final int eValidityMonths,
            final EDocumentFormat eDocumentFormat, final EEfficacy eEfficacy, final int eVolumeCurrRetention,
            final int eVolumeGenRetention) {
        this.code = eCode;
        this.volumeDescrPattern = ePattern;
        this.validityMonths = eValidityMonths;
        this.documentFormat = eDocumentFormat;
        this.efficacy = eEfficacy;
        this.volumeCurrRetention = eVolumeCurrRetention;
        this.volumeGenRetention = eVolumeGenRetention;
    }

    EIntegrationResourceCMTO(final String eCode, final EDocumentFormat eDocumentFormat, final EEfficacy eEfficacy) {
        this.code = eCode;
        this.documentFormat = eDocumentFormat;
        this.efficacy = eEfficacy;
    }

    public static final EIntegrationResourceCMTO resolve(final String eCode) {
        
    	
    	return EnumSet.allOf(EIntegrationResourceCMTO.class).stream().filter(dt -> dt.code.equals(eCode)).findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported code %s", eCode)));
    }

    public String getCode() {
        return code;
    }

    public String getVolumeDescrPattern() {
        return volumeDescrPattern;
    }

    public int getVolumeCurrRetention() {
        return volumeCurrRetention;
    }

    public int getVolumeGenRetention() {
        return volumeGenRetention;
    }

    public EDocumentFormat getDocumentFormat() {
        return documentFormat;
    }

    public EEfficacy getEfficacy() {
        return efficacy;
    }

    public int getValidityMonths() {
        return validityMonths;
    }
}
