/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public interface AurigaSpringContext {

	public static final String SPRINGBEAN_SOCIETA_DAO = "societaDAO";
	public static final String SPRINGBEAN_APPLICAZ_EST_DAO = "applicazioniEsterneDAO";
	public static final String SPRINGBEAN_APPLICAZ_EFATT_DAO = "applicazioniEfattDAO";
	public static final String SPRINGBEAN_DIZIONARIO_DAO = "dizionarioDAO";
	public static final String SPRINGBEAN_PARAMETRI_DAO = "parametriDAO";
	public static final String SPRINGBEAN_NOTIFICHE_DAO = "notificheDAO";
	public static final String SPRINGBEAN_FATTURE_DAO = "fattureDAO";
	public static final String SPRINGBEAN_RICEVUTE_SDI_DAO = "ricevuteSdIDAO";
	public static final String SPRINGBEAN_DOCFLY_DAO = "conservazioneDocFlyDAO";
	public static final String SPRINGBEAN_VERIFICA_DOCFLY_DAO = "verificaDocFlyDAO";
	public static final String SPRINGBEAN_COMPRESSIONE_MAIL_DAO = "CompressioneMailDAO";
	public static final String SPRINGBEAN_CANCELLAZIONE_FILE_TEMPORANEI_MAIL_DAO = "CancellazioneFileTemporaneiMailDAO";
	public static final String SPRINGBEAN_FILE_LETTI_DAO = "fileLettiDaSftpDAO";
	public static final String SPRINGBEAN_MESSAGGI_MAIL_DAO = "messaggiMailDAO";
	public static final String SPRINGBEAN_ARCHIVIO_MAIL_DAO = "archivioMailDAO";
	public static final String SPRINGBEAN_SCONTRINI_DAO = "scontriniDAO";
	public static final String SPRINGBEAN_EMAILTORESEND_DAO = "emailToResendDAO";
	public static final String SPRINGBEAN_LOTTI_DAO = "lottiDocumentiDAO";
	public static final String SPRINGBEAN_EVENTI_LOTTI_DAO = "logEventiLottiDocumentiDAO";
	public static final String SPRINGBEAN_DOC_LOTTI_IN_ELAB_DAO = "documentiLottoInElaborazioneDAO";
	public static final String SPRINGBEAN_DOC_LOTTI_IN_ATTESA_DAO = "documentiLottoInAttesaDAO";
	public static final String SPRINGBEAN_DOCUMENTI_DAO = "documentiDAO";
	public static final String SPRINGBEAN_RICERCA_UD_DAO = "ricercaUnitaDocDAO";
	public static final String SPRINGBEAN_PEC_ATTIVAZIONI_DAO = "pecAttivazioniDAO";
	public static final String SPRINGBEAN_INTEGRAZIONE_DOCER_AURIGA_DAO = "integrazioneDocerAurigaDAO";
	public static final String SPRINGBEAN_PTT_JOB_PECATTIVAZIONI_DAO = "pttJobPecAttivazioniDAO";
	public static final String SPRINGBEAN_CVT_PARAMETRI_DAO = "cvtParametriDAO";
	public static final String SPRINGBEAN_CVT_ANAG_PD_DAO = "cvtAnagPdDAO";
	public static final String SPRINGBEAN_NIR_ANAG_COMUNI_DAO = "nirAnagComuniDAO";
	public static final String SPRINGBEAN_NIR_ASSEGNAZIONI_DAO = "nirAssegnazioniDAO";
	public static final String SPRINGBEAN_PTT_DOCUMENTI_DAO = "pttDocumentiDAO";
	public static final String SPRINGBEAN_RICERCA_DOC_DAO = "ricercaDocumentDAO";
	public static final String SPRINGBEAN_FATTURE_PA_DAO = "fatturePADAO";
	public static final String SPRINGBEAN_DATI_CLIENTI_FATT_DAO = "dmvDatiClientiFattDAO";
	public static final String SPRINGBEAN_AL_ESIGIBILITA_IVA_DAO = "tAlEsigibilitaIvaDAO";
	public static final String SPRINGBEAN_T_BA_DEC_INVOICE_TYPE_DAO = "tBaDecInvoiceTypeDAO";
	public static final String SPRINGBEAN_T_BT_DEC_TAX_DAO = "tBtDecTaxDAO";

	public static final String SPRINGBEAN_SINADOC_ALIGN_SI_DAO = "AlignSitiInquinatiDAO";
	public static final String SPRINGBEAN_ANA_JOB_DAO = "anaJobDAO";

	public static final String SPRINGBEAN_DOCUMENT_CONFIG = "DocumentConfiguration";
	public static final String SPRINGBEAN_DOCUMENTNOOUTPUT_CONFIG = "DocumentConfigurationNoOutput";
	public static final String SPRINGBEAN_IRIS_CONFIG = "invioConservazioneConfigBean";
	public static final String SPRINGBEAN_IRIS_RETRIEVER_CONFIG = "recuperoEsitiConfigBean";
	public static final String SPRINGBEAN_AURIGA_LOGIN = "aurigaLoginUtil";
	public static final String SPRINGBEAN_PROXY_CONFIG = "proxyConfig";
	public static final String SPRINGBEAN_MAIL_SENDER = "mailSender";
	public static final String SPRINGBEAN_MAIL_SEGNALAZIONI = "indirizziMailSegnalazioni";
	public static final String SPRINGBEAN_VELOCITY_ENGINE = "velocityEngine";
	public static final String SPRINGBEAN_LOTTO_LOGGER = "lottoDocumentiLogger";
	public static final String SPRINGBEAN_LOTTO_LOGGER_TEMP = "lottoDocumentiLoggerTemp";
	public static final String SPRINGBEAN_PROPS_INVIO_CONSERVAZIONE = "propsInvioConservazione";
	public static final String SPRINGBEAN_EXECUTOR_SERVICE = "executorService";
	public static final String SPRINGBEAN_FILE_EXTRACTOR = "fileExtractor";
	public static final String SPRINGBEAN_CONSERVAZIONE_MANAGER = "conservazioneManager";
	public static final String SPRINGBEAN_SIP_MANAGER = "sipManager";
	public static final String SPRINGBEAN_TRANSCODIFICHE = "transcodificheBean";
	public static final String SPRINGBEAN_MAPPA_SORGENTI = "mappaSorgenti";
	public static final String SPRINGBEAN_DMPK_INTCS_SERVICE = "DmpkIntCsService";
	public static final String SPRINGBEAN_INVIO_CONS_DA_FILE_SERVICE = "invioConsDaFileService";

	public static final String SPRINGBEAN_JAXBUTIL = "jaxbUtil";
	public static final String SPRINGBEAN_APPLICAZ_REGISTRI_DAO = "applicazioniRegistriDAO";
	
	public static final String SPRINGBEAN_UNIMATICA_DAO = "conservazioneUnimaticaDAO";
}
