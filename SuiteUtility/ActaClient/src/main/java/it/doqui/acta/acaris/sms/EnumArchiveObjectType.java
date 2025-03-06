
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumArchiveObjectType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumArchiveObjectType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="ContenutoFisicoPropertiesType"/&gt;
 *     &lt;enumeration value="GruppoAllegatiPropertiesType"/&gt;
 *     &lt;enumeration value="DocumentoDBPropertiesType"/&gt;
 *     &lt;enumeration value="DocumentoRegistroPropertiesType"/&gt;
 *     &lt;enumeration value="DocumentoSemplicePropertiesType"/&gt;
 *     &lt;enumeration value="ClipsMetallicaPropertiesType"/&gt;
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
 *     &lt;enumeration value="DocumentAssociationPropertiesType"/&gt;
 *     &lt;enumeration value="HistoryModificheTecnichePropertiesType"/&gt;
 *     &lt;enumeration value="DocumentCompositionPropertiesType"/&gt;
 *     &lt;enumeration value="HistoryVecchieVersioniPropertiesType"/&gt;
 *     &lt;enumeration value="ActaACEPropertiesType"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumArchiveObjectType")
@XmlEnum
public enum EnumArchiveObjectType {

    @XmlEnumValue("ContenutoFisicoPropertiesType")
    CONTENUTO_FISICO_PROPERTIES_TYPE("ContenutoFisicoPropertiesType"),
    @XmlEnumValue("GruppoAllegatiPropertiesType")
    GRUPPO_ALLEGATI_PROPERTIES_TYPE("GruppoAllegatiPropertiesType"),
    @XmlEnumValue("DocumentoDBPropertiesType")
    DOCUMENTO_DB_PROPERTIES_TYPE("DocumentoDBPropertiesType"),
    @XmlEnumValue("DocumentoRegistroPropertiesType")
    DOCUMENTO_REGISTRO_PROPERTIES_TYPE("DocumentoRegistroPropertiesType"),
    @XmlEnumValue("DocumentoSemplicePropertiesType")
    DOCUMENTO_SEMPLICE_PROPERTIES_TYPE("DocumentoSemplicePropertiesType"),
    @XmlEnumValue("ClipsMetallicaPropertiesType")
    CLIPS_METALLICA_PROPERTIES_TYPE("ClipsMetallicaPropertiesType"),
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
    @XmlEnumValue("DocumentAssociationPropertiesType")
    DOCUMENT_ASSOCIATION_PROPERTIES_TYPE("DocumentAssociationPropertiesType"),
    @XmlEnumValue("HistoryModificheTecnichePropertiesType")
    HISTORY_MODIFICHE_TECNICHE_PROPERTIES_TYPE("HistoryModificheTecnichePropertiesType"),
    @XmlEnumValue("DocumentCompositionPropertiesType")
    DOCUMENT_COMPOSITION_PROPERTIES_TYPE("DocumentCompositionPropertiesType"),
    @XmlEnumValue("HistoryVecchieVersioniPropertiesType")
    HISTORY_VECCHIE_VERSIONI_PROPERTIES_TYPE("HistoryVecchieVersioniPropertiesType"),
    @XmlEnumValue("ActaACEPropertiesType")
    ACTA_ACE_PROPERTIES_TYPE("ActaACEPropertiesType");
    private final String value;

    EnumArchiveObjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumArchiveObjectType fromValue(String v) {
        for (EnumArchiveObjectType c: EnumArchiveObjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
