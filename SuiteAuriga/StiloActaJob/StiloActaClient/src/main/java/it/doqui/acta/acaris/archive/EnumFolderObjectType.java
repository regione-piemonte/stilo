/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.archive;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumFolderObjectType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumFolderObjectType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="TitolarioPropertiesType"/&gt;
 *     &lt;enumeration value="VocePropertiesType"/&gt;
 *     &lt;enumeration value="ClassificazionePropertiesType"/&gt;
 *     &lt;enumeration value="FascicoloTemporaneoPropertiesType"/&gt;
 *     &lt;enumeration value="DocumentoFisicoPropertiesType"/&gt;
 *     &lt;enumeration value="SottofascicoloPropertiesType"/&gt;
 *     &lt;enumeration value="DossierPropertiesType"/&gt;
 *     &lt;enumeration value="VolumeSerieFascicoliPropertiesType"/&gt;
 *     &lt;enumeration value="VolumeSerieTipologicaDocumentiPropertiesType"/&gt;
 *     &lt;enumeration value="VolumeFascicoliPropertiesType"/&gt;
 *     &lt;enumeration value="VolumeSottofascicoliPropertiesType"/&gt;
 *     &lt;enumeration value="SerieDossierPropertiesType"/&gt;
 *     &lt;enumeration value="SerieTipologicaDocumentiPropertiesType"/&gt;
 *     &lt;enumeration value="SerieFascicoliPropertiesType"/&gt;
 *     &lt;enumeration value="FascicoloRealeEreditatoPropertiesType"/&gt;
 *     &lt;enumeration value="FascicoloRealeLiberoPropertiesType"/&gt;
 *     &lt;enumeration value="FascicoloRealeLegislaturaPropertiesType"/&gt;
 *     &lt;enumeration value="FascicoloRealeAnnualePropertiesType"/&gt;
 *     &lt;enumeration value="FascicoloRealeContinuoPropertiesType"/&gt;
 *     &lt;enumeration value="GruppoAllegatiPropertiesType"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumFolderObjectType")
@XmlEnum
public enum EnumFolderObjectType {

    @XmlEnumValue("TitolarioPropertiesType")
    TITOLARIO_PROPERTIES_TYPE("TitolarioPropertiesType"),
    @XmlEnumValue("VocePropertiesType")
    VOCE_PROPERTIES_TYPE("VocePropertiesType"),
    @XmlEnumValue("ClassificazionePropertiesType")
    CLASSIFICAZIONE_PROPERTIES_TYPE("ClassificazionePropertiesType"),
    @XmlEnumValue("FascicoloTemporaneoPropertiesType")
    FASCICOLO_TEMPORANEO_PROPERTIES_TYPE("FascicoloTemporaneoPropertiesType"),
    @XmlEnumValue("DocumentoFisicoPropertiesType")
    DOCUMENTO_FISICO_PROPERTIES_TYPE("DocumentoFisicoPropertiesType"),
    @XmlEnumValue("SottofascicoloPropertiesType")
    SOTTOFASCICOLO_PROPERTIES_TYPE("SottofascicoloPropertiesType"),
    @XmlEnumValue("DossierPropertiesType")
    DOSSIER_PROPERTIES_TYPE("DossierPropertiesType"),
    @XmlEnumValue("VolumeSerieFascicoliPropertiesType")
    VOLUME_SERIE_FASCICOLI_PROPERTIES_TYPE("VolumeSerieFascicoliPropertiesType"),
    @XmlEnumValue("VolumeSerieTipologicaDocumentiPropertiesType")
    VOLUME_SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE("VolumeSerieTipologicaDocumentiPropertiesType"),
    @XmlEnumValue("VolumeFascicoliPropertiesType")
    VOLUME_FASCICOLI_PROPERTIES_TYPE("VolumeFascicoliPropertiesType"),
    @XmlEnumValue("VolumeSottofascicoliPropertiesType")
    VOLUME_SOTTOFASCICOLI_PROPERTIES_TYPE("VolumeSottofascicoliPropertiesType"),
    @XmlEnumValue("SerieDossierPropertiesType")
    SERIE_DOSSIER_PROPERTIES_TYPE("SerieDossierPropertiesType"),
    @XmlEnumValue("SerieTipologicaDocumentiPropertiesType")
    SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE("SerieTipologicaDocumentiPropertiesType"),
    @XmlEnumValue("SerieFascicoliPropertiesType")
    SERIE_FASCICOLI_PROPERTIES_TYPE("SerieFascicoliPropertiesType"),
    @XmlEnumValue("FascicoloRealeEreditatoPropertiesType")
    FASCICOLO_REALE_EREDITATO_PROPERTIES_TYPE("FascicoloRealeEreditatoPropertiesType"),
    @XmlEnumValue("FascicoloRealeLiberoPropertiesType")
    FASCICOLO_REALE_LIBERO_PROPERTIES_TYPE("FascicoloRealeLiberoPropertiesType"),
    @XmlEnumValue("FascicoloRealeLegislaturaPropertiesType")
    FASCICOLO_REALE_LEGISLATURA_PROPERTIES_TYPE("FascicoloRealeLegislaturaPropertiesType"),
    @XmlEnumValue("FascicoloRealeAnnualePropertiesType")
    FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE("FascicoloRealeAnnualePropertiesType"),
    @XmlEnumValue("FascicoloRealeContinuoPropertiesType")
    FASCICOLO_REALE_CONTINUO_PROPERTIES_TYPE("FascicoloRealeContinuoPropertiesType"),
    @XmlEnumValue("GruppoAllegatiPropertiesType")
    GRUPPO_ALLEGATI_PROPERTIES_TYPE("GruppoAllegatiPropertiesType");
    private final String value;

    EnumFolderObjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumFolderObjectType fromValue(String v) {
        for (EnumFolderObjectType c: EnumFolderObjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
