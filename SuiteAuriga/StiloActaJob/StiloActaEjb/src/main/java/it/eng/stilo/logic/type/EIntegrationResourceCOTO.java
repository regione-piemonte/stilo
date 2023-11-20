/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.EnumSet;

import it.eng.stilo.util.file.FileHandler;

public enum EIntegrationResourceCOTO {

    DETERMINA_COTO("DD", "yyyy", 12, EDocumentFormat.DETERMINA_DIR, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ALLEGATO_DETERMINA_COTO("DD_ALL", EDocumentFormat.DETERMINA, EEfficacy.PERFECT_EFFICACY),
    DETERMINA_OMISSIS_COTO("DDO", "yyyy", 12, EDocumentFormat.DETERMINA_PUBBLICATA, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ALLEGATO_DETERMINA_OMISSIS_COTO("DDO_ALL", EDocumentFormat.DETERMINA_OMISSIS, EEfficacy.PERFECT_EFFICACY),
    REGISTRO_TRIMESTRALE_DETERMINE_COTO("DDRT", "yyyy", 12, EDocumentFormat.REGISTRO_DETERMINE,
            EEfficacy.PERFECT_EFFICACY_UNSIGNED, 5, 99),
    REGISTRO_PUBBLICAZIONI_COTO("RPUBBL", "yyyy", 12, EDocumentFormat.REGISTRO_PUBBLICAZIONI,
            EEfficacy.PERFECT_EFFICACY, 5, 99),
    ORDINANZA_COTO("ORD", "yyyy", 1, EDocumentFormat.ORDINANZA, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ORDINANZA_OMISSIS_COTO("ORDO", "yyyy", 1, EDocumentFormat.ORDINANZA_OMISSIS, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ALLEGATO_ORDINANZA_COTO("ORD_ALL",  EDocumentFormat.ORDINANZA, EEfficacy.PERFECT_EFFICACY),
    ALLEGATO_ORDINANZA_OMISSIS_COTO("ORDO_ALL",  EDocumentFormat.ORDINANZA_OMISSIS, EEfficacy.PERFECT_EFFICACY),
    DECRETO_PRESIDENTE_GIUNTA_COTO("DPGR", "yyyy", 1, EDocumentFormat.DECRETO_PRESIDENTE_GIUNTA, EEfficacy.PERFECT_EFFICACY, 5, 99),
    DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO("DPGRO", "yyyy", 1, EDocumentFormat.DECRETO_PRESIDENTE_GIUNTA_OMISSIS, EEfficacy.PERFECT_EFFICACY, 5, 99),
    ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_COTO("DPGR_ALL",  EDocumentFormat.DECRETO_PRESIDENTE_GIUNTA, EEfficacy.PERFECT_EFFICACY),
    ALLEGATO_DECRETO_PRESIDENTE_GIUNTA_OMISSIS_COTO("DPGRO_ALL",  EDocumentFormat.DECRETO_PRESIDENTE_GIUNTA_OMISSIS, EEfficacy.PERFECT_EFFICACY);

    private String code;
    private String volumeDescrPattern;
    private int validityMonths;
    private EDocumentFormat documentFormat;
    private EEfficacy efficacy;
    private int volumeCurrRetention;
    private int volumeGenRetention;

    EIntegrationResourceCOTO(final String eCode, final String ePattern, final int eValidityMonths,
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

    EIntegrationResourceCOTO(final String eCode, final EDocumentFormat eDocumentFormat, final EEfficacy eEfficacy) {
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
