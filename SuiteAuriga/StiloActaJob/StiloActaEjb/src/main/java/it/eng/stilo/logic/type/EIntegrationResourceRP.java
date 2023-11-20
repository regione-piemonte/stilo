/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum EIntegrationResourceRP {

    DETERMINA("DD", "MMMM - yyyy", 1, EDocumentFormat.DETERMINA, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ALLEGATO_DETERMINA("DD_ALL", EDocumentFormat.DETERMINA, EEfficacy.PERFECT_EFFICACY),
    DETERMINA_OMISSIS("DDO", "MMMM - yyyy", 1, EDocumentFormat.DETERMINA_OMISSIS, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ALLEGATO_DETERMINA_OMISSIS("DDO_ALL", EDocumentFormat.DETERMINA_OMISSIS, EEfficacy.PERFECT_EFFICACY),
    
    ORDINANZA_OMISSIS("ORDO", "yyyy", 1, EDocumentFormat.ORDINANZA_OMISSIS, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ALLEGATO_ORDINANZA("ORD_ALL",  EDocumentFormat.ORDINANZA, EEfficacy.PERFECT_EFFICACY),
    ALLEGATO_ORDINANZA_OMISSIS("ORDO_ALL",  EDocumentFormat.ORDINANZA_OMISSIS, EEfficacy.PERFECT_EFFICACY),
    
    DECRETO_PRESIDENTE_GIUNTA("DPGR", "yyyy", 1, EDocumentFormat.DECRETO_PRESIDENTE_GIUNTA, EEfficacy.PERFECT_EFFICACY, 5, 99),
    DECRETO_PRESIDENTE_GIUNTA_OMISSIS("DPGRO", "yyyy", 1, EDocumentFormat.DECRETO_PRESIDENTE_GIUNTA_OMISSIS, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ALLEGATO_DECRETO_PRESIDENTE_GIUNTA("DPGR_ALL",  EDocumentFormat.DECRETO_PRESIDENTE_GIUNTA, EEfficacy.PERFECT_EFFICACY),
    ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS("DPGRO_ALL",  EDocumentFormat.DECRETO_PRESIDENTE_GIUNTA_OMISSIS, EEfficacy.PERFECT_EFFICACY),
    
    DELIBERA_GIUNTA("DELGR", "yyyy", 12, EDocumentFormat.DELIBERA_GIUNTA, EEfficacy.PERFECT_EFFICACY, 5, 99),
	ALLEGATO_DELIBERA_GIUNTA("DELGR_ALL",  EDocumentFormat.DELIBERA_GIUNTA, EEfficacy.PERFECT_EFFICACY),
	DELIBERA_GIUNTA_OMISSIS("DELGRO", "yyyy", 12, EDocumentFormat.DELIBERA_GIUNTA_OMISSIS, EEfficacy.PERFECT_EFFICACY, 5, 99),
	ALLEGATO_DELIBERA_GIUNTA_OMISSIS("DELGRO_ALL",  EDocumentFormat.DELIBERA_GIUNTA_OMISSIS, EEfficacy.PERFECT_EFFICACY),
	
	REGISTRO_DETERMINE("DDRT", "yyyy", 12, EDocumentFormat.REGISTRO_DETERMINE,
            EEfficacy.PERFECT_EFFICACY_UNSIGNED, 5, 99),
	REGISTRO_DECRETI_PRESIDENTE_GIUNTA("DDRT", "yyyy", 12, EDocumentFormat.REGISTRO_DETERMINE,
            EEfficacy.PERFECT_EFFICACY_UNSIGNED, 5, 99),
	REGISTRO_ORDINANZE("DDRT", "yyyy", 12, EDocumentFormat.REGISTRO_DETERMINE,
            EEfficacy.PERFECT_EFFICACY_UNSIGNED, 5, 99),
	REGISTRO_DELIBERE_GIUNTA("DDRT", "yyyy", 12, EDocumentFormat.REGISTRO_DETERMINE,
            EEfficacy.PERFECT_EFFICACY_UNSIGNED, 5, 99),
    
	REGISTRO_PUBBLICAZIONI("RPUBBL", "yyyy", 12, EDocumentFormat.REGISTRO_PUBBLICAZIONI,
            EEfficacy.PERFECT_EFFICACY, 5, 99),ORDINANZA("ORD", "yyyy", 1, EDocumentFormat.ORDINANZA, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ;

    private String code;
    private String volumeDescrPattern;
    private int validityMonths;
    private EDocumentFormat documentFormat;
    private EEfficacy efficacy;
    private int volumeCurrRetention;
    private int volumeGenRetention;

    EIntegrationResourceRP(final String eCode, final String ePattern, final int eValidityMonths,
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

    EIntegrationResourceRP(final String eCode, final EDocumentFormat eDocumentFormat, final EEfficacy eEfficacy) {
        this.code = eCode;
        this.documentFormat = eDocumentFormat;
        this.efficacy = eEfficacy;
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
