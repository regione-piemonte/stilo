
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumObjectType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumObjectType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="ContenutoFisicoPropertiesType"/&gt;
 *     &lt;enumeration value="VerifyReportPropertiesType"/&gt;
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
 *     &lt;enumeration value="FascicoloStdPropertiesType"/&gt;
 *     &lt;enumeration value="AnnotazionePropertiesType"/&gt;
 *     &lt;enumeration value="DocumentAssociationPropertiesType"/&gt;
 *     &lt;enumeration value="HistoryModificheTecnichePropertiesType"/&gt;
 *     &lt;enumeration value="DocumentCompositionPropertiesType"/&gt;
 *     &lt;enumeration value="HistoryVecchieVersioniPropertiesType"/&gt;
 *     &lt;enumeration value="ActaACEPropertiesType"/&gt;
 *     &lt;enumeration value="SoggettoPropertiesType"/&gt;
 *     &lt;enumeration value="RegistrazionePropertiesType"/&gt;
 *     &lt;enumeration value="CorrispondentePropertiesType"/&gt;
 *     &lt;enumeration value="RegistroPropertiesType"/&gt;
 *     &lt;enumeration value="RegistroProtocolloPropertiesType"/&gt;
 *     &lt;enumeration value="EntePropertiesType"/&gt;
 *     &lt;enumeration value="StrutturaPropertiesType"/&gt;
 *     &lt;enumeration value="NodoPropertiesType"/&gt;
 *     &lt;enumeration value="AOOPropertiesType"/&gt;
 *     &lt;enumeration value="UtentePropertiesType"/&gt;
 *     &lt;enumeration value="FormaDocumentariaPropertiesType"/&gt;
 *     &lt;enumeration value="ProfiloPropertiesType"/&gt;
 *     &lt;enumeration value="GruppoAOOPropertiesType"/&gt;
 *     &lt;enumeration value="SistemaPropertiesType"/&gt;
 *     &lt;enumeration value="UtenteProfilo"/&gt;
 *     &lt;enumeration value="ProtocolloPropertiesType"/&gt;
 *     &lt;enumeration value="NodoConStruttura"/&gt;
 *     &lt;enumeration value="SmistamentoDestinatarioPropertiesType"/&gt;
 *     &lt;enumeration value="SmistamentoMittentePropertiesType"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumObjectType", namespace = "common.acaris.acta.doqui.it")
@XmlEnum
public enum EnumObjectType {

    @XmlEnumValue("ContenutoFisicoPropertiesType")
    CONTENUTO_FISICO_PROPERTIES_TYPE("ContenutoFisicoPropertiesType"),
    @XmlEnumValue("VerifyReportPropertiesType")
    VERIFY_REPORT_PROPERTIES_TYPE("VerifyReportPropertiesType"),
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
    @XmlEnumValue("FascicoloStdPropertiesType")
    FASCICOLO_STD_PROPERTIES_TYPE("FascicoloStdPropertiesType"),
    @XmlEnumValue("AnnotazionePropertiesType")
    ANNOTAZIONE_PROPERTIES_TYPE("AnnotazionePropertiesType"),
    @XmlEnumValue("DocumentAssociationPropertiesType")
    DOCUMENT_ASSOCIATION_PROPERTIES_TYPE("DocumentAssociationPropertiesType"),
    @XmlEnumValue("HistoryModificheTecnichePropertiesType")
    HISTORY_MODIFICHE_TECNICHE_PROPERTIES_TYPE("HistoryModificheTecnichePropertiesType"),
    @XmlEnumValue("DocumentCompositionPropertiesType")
    DOCUMENT_COMPOSITION_PROPERTIES_TYPE("DocumentCompositionPropertiesType"),
    @XmlEnumValue("HistoryVecchieVersioniPropertiesType")
    HISTORY_VECCHIE_VERSIONI_PROPERTIES_TYPE("HistoryVecchieVersioniPropertiesType"),
    @XmlEnumValue("ActaACEPropertiesType")
    ACTA_ACE_PROPERTIES_TYPE("ActaACEPropertiesType"),
    @XmlEnumValue("SoggettoPropertiesType")
    SOGGETTO_PROPERTIES_TYPE("SoggettoPropertiesType"),
    @XmlEnumValue("RegistrazionePropertiesType")
    REGISTRAZIONE_PROPERTIES_TYPE("RegistrazionePropertiesType"),
    @XmlEnumValue("CorrispondentePropertiesType")
    CORRISPONDENTE_PROPERTIES_TYPE("CorrispondentePropertiesType"),
    @XmlEnumValue("RegistroPropertiesType")
    REGISTRO_PROPERTIES_TYPE("RegistroPropertiesType"),
    @XmlEnumValue("RegistroProtocolloPropertiesType")
    REGISTRO_PROTOCOLLO_PROPERTIES_TYPE("RegistroProtocolloPropertiesType"),
    @XmlEnumValue("EntePropertiesType")
    ENTE_PROPERTIES_TYPE("EntePropertiesType"),
    @XmlEnumValue("StrutturaPropertiesType")
    STRUTTURA_PROPERTIES_TYPE("StrutturaPropertiesType"),
    @XmlEnumValue("NodoPropertiesType")
    NODO_PROPERTIES_TYPE("NodoPropertiesType"),
    @XmlEnumValue("AOOPropertiesType")
    AOO_PROPERTIES_TYPE("AOOPropertiesType"),
    @XmlEnumValue("UtentePropertiesType")
    UTENTE_PROPERTIES_TYPE("UtentePropertiesType"),
    @XmlEnumValue("FormaDocumentariaPropertiesType")
    FORMA_DOCUMENTARIA_PROPERTIES_TYPE("FormaDocumentariaPropertiesType"),
    @XmlEnumValue("ProfiloPropertiesType")
    PROFILO_PROPERTIES_TYPE("ProfiloPropertiesType"),
    @XmlEnumValue("GruppoAOOPropertiesType")
    GRUPPO_AOO_PROPERTIES_TYPE("GruppoAOOPropertiesType"),
    @XmlEnumValue("SistemaPropertiesType")
    SISTEMA_PROPERTIES_TYPE("SistemaPropertiesType"),
    @XmlEnumValue("UtenteProfilo")
    UTENTE_PROFILO("UtenteProfilo"),
    @XmlEnumValue("ProtocolloPropertiesType")
    PROTOCOLLO_PROPERTIES_TYPE("ProtocolloPropertiesType"),
    @XmlEnumValue("NodoConStruttura")
    NODO_CON_STRUTTURA("NodoConStruttura"),
    @XmlEnumValue("SmistamentoDestinatarioPropertiesType")
    SMISTAMENTO_DESTINATARIO_PROPERTIES_TYPE("SmistamentoDestinatarioPropertiesType"),
    @XmlEnumValue("SmistamentoMittentePropertiesType")
    SMISTAMENTO_MITTENTE_PROPERTIES_TYPE("SmistamentoMittentePropertiesType");
    private final String value;

    EnumObjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumObjectType fromValue(String v) {
        for (EnumObjectType c: EnumObjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
