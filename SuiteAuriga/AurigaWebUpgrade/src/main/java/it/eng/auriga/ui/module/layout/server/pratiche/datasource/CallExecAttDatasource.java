/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfCall_execattBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttProcBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttachmentInvioNotEmailBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.ImpostazioniUnioneFileBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.UfficioProponenteAttoDatasource;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UoProtocollanteBean;
import it.eng.auriga.ui.module.layout.server.task.bean.AttributiCustomCablatiAttoXmlBean;
import it.eng.auriga.ui.module.layout.server.task.bean.InfoFirmaGraficaBean;
import it.eng.auriga.ui.module.layout.server.task.bean.ParametriTipoAttoBean;
import it.eng.auriga.ui.module.layout.server.task.bean.XmlDatiEventoOutBean;
import it.eng.client.DmpkWfCall_execatt;
import it.eng.document.function.bean.Flag;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilityDeserializer;

@Datasource(id = "CallExecAttDatasource")
public class CallExecAttDatasource extends AbstractServiceDataSource<AttProcBean, AttProcBean> {

	private static final Logger log = Logger.getLogger(CallExecAttDatasource.class);

	@Override
	public AttProcBean call(AttProcBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkWfCall_execattBean input = new DmpkWfCall_execattBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdprocessin(new BigDecimal(bean.getIdProcess()));
		input.setActivitynamein(StringUtils.isNotBlank(bean.getActivityName()) ? bean.getActivityName() : bean.getNome());
		DmpkWfCall_execatt dmpkWfCall_execatt = new DmpkWfCall_execatt();
		StoreResultBean<DmpkWfCall_execattBean> output = dmpkWfCall_execatt.execute(getLocale(), loginBean, input);
	
		if (output.isInError()) {
			throw new StoreException(output);
		} else if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
		}
		
		bean.setIdGUIEvento(output.getResultBean().getUrltoredirecttoout());
		log.debug("Recupero l'xml");
		if (StringUtils.isNotBlank(output.getResultBean().getTaskinfoxmlout())) {
			XmlDatiEventoOutBean scXmlDatiEvento = new XmlDatiEventoOutBean();
			XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			log.debug("taskInfoXml: " + output.getResultBean().getTaskinfoxmlout());
			scXmlDatiEvento = lXmlUtilityDeserializer.unbindXml(output.getResultBean().getTaskinfoxmlout(), XmlDatiEventoOutBean.class);
			if (scXmlDatiEvento != null) {
				bean.setIdFolder(scXmlDatiEvento.getIdFolder());
				bean.setIdUd(scXmlDatiEvento.getIdUD());
				bean.setIdDoc(scXmlDatiEvento.getIdDocPrimario());
				bean.setIdTipoDoc(scXmlDatiEvento.getIdDocType());
				bean.setEstremiUd(scXmlDatiEvento.getEstremiUD());
				bean.setEsitiTaskAzioni(scXmlDatiEvento.getEsitiTaskAzioni());
				bean.setIdDocAzioni(scXmlDatiEvento.getIdDocAzioni());
				bean.setFlgFirmaFile(scXmlDatiEvento.getFlgFirmaFile() != null && scXmlDatiEvento.getFlgFirmaFile().equalsIgnoreCase("true"));
				bean.setFlgShowAnteprimaFileDaFirmare(scXmlDatiEvento.getFlgShowAnteprimaFileDaFirmare() != null && scXmlDatiEvento.getFlgShowAnteprimaFileDaFirmare().equalsIgnoreCase("true"));				
				bean.setFlgTimbraFile(scXmlDatiEvento.getFlgTimbraFile() != null && scXmlDatiEvento.getFlgTimbraFile().equalsIgnoreCase("true"));
				bean.setFlgProtocolla(scXmlDatiEvento.getFlgProtocolla() != null  && scXmlDatiEvento.getFlgProtocolla().equalsIgnoreCase("true"));
				bean.setFlgUnioneFile(scXmlDatiEvento.getFlgUnioneFile() != null && scXmlDatiEvento.getFlgUnioneFile().equalsIgnoreCase("true"));
				bean.setUnioneFileNomeFile(scXmlDatiEvento.getUnioneFileNomeFile());	
				bean.setUnioneFileNomeFileOmissis(scXmlDatiEvento.getUnioneFileNomeFileOmissis());					
				bean.setUnioneFileIdTipoDoc(scXmlDatiEvento.getUnioneFileIdDocType());
				bean.setUnioneFileNomeTipoDoc(scXmlDatiEvento.getUnioneFileNomeDocType());
				bean.setUnioneFileDescrizione(scXmlDatiEvento.getUnioneFileDescrizione());
				bean.setFlgShowAnteprimaDefinitiva(scXmlDatiEvento.getFlgShowAnteprimaDefinitiva() != null && scXmlDatiEvento.getFlgShowAnteprimaDefinitiva().equalsIgnoreCase("true"));				
				bean.setFlgConversionePdf(scXmlDatiEvento.getFlgConversionePdf() != null && scXmlDatiEvento.getFlgConversionePdf().equalsIgnoreCase("true"));				
				bean.setFlgInvioPEC(scXmlDatiEvento.getFlgInvioPEC() != null && scXmlDatiEvento.getFlgInvioPEC().equalsIgnoreCase("true"));
				bean.setInvioPECSubject(scXmlDatiEvento.getInvioPECSubject());
				bean.setInvioPECBody(scXmlDatiEvento.getInvioPECBody());
				bean.setInvioPECDestinatari(scXmlDatiEvento.getInvioPECDestinatari());
				bean.setInvioPECDestinatariCC(scXmlDatiEvento.getInvioPECDestinatariCC());
				bean.setInvioPECIdCasellaMittente(scXmlDatiEvento.getInvioPECIdCasellaMittente());
				bean.setInvioPECIndirizzoMittente(scXmlDatiEvento.getInvioPECIndirizzoMittente());
				bean.setInvioPECAttach(scXmlDatiEvento.getInvioPECAttach());	
				bean.setEventoSUETipo(scXmlDatiEvento.getEventoSUETipo());
				bean.setEventoSUEIdPratica(scXmlDatiEvento.getEventoSUEIdPratica());
				bean.setEventoSUECodFiscale(scXmlDatiEvento.getEventoSUECodFiscale());
				bean.setEventoSUEGiorniSospensione(scXmlDatiEvento.getEventoSUEGiorniSospensione());
				bean.setEventoSUEFlgPubblico(StringUtils.isNotBlank(scXmlDatiEvento.getEventoSUEFlgPubblico()) ? scXmlDatiEvento.getEventoSUEFlgPubblico().equalsIgnoreCase("true") : true); // di default deve essere true
				bean.setEventoSUEDestinatari(scXmlDatiEvento.getEventoSUEDestinatari());
				bean.setEventoSUEFileDaPubblicare(scXmlDatiEvento.getEventoSUEFileDaPubblicare());
				bean.setFlgInvioNotEmail(scXmlDatiEvento.getFlgInvioNotEmail() != null && scXmlDatiEvento.getFlgInvioNotEmail().equalsIgnoreCase("true"));
				bean.setInvioNotEmailSubject(scXmlDatiEvento.getInvioNotEmailSubject());
				bean.setInvioNotEmailBody(scXmlDatiEvento.getInvioNotEmailBody());
				bean.setInvioNotEmailDestinatari(scXmlDatiEvento.getInvioNotEmailDestinatari());
				bean.setInvioNotEmailDestinatariCC(scXmlDatiEvento.getInvioNotEmailDestinatariCC());
				bean.setInvioNotEmailDestinatariCCN(scXmlDatiEvento.getInvioNotEmailDestinatariCCN());
				bean.setInvioNotEmailIdCasellaMittente(scXmlDatiEvento.getInvioNotEmailIdCasellaMittente());
				bean.setInvioNotEmailIndirizzoMittente(scXmlDatiEvento.getInvioNotEmailIndirizzoMittente());
				bean.setInvioNotEmailAliasIndirizzoMittente(scXmlDatiEvento.getInvioNotEmailAliasIndirizzoMittente());				
				bean.setInvioNotEmailFlgPEC(scXmlDatiEvento.getInvioNotEmailFlgPEC() != null && scXmlDatiEvento.getInvioNotEmailFlgPEC().equalsIgnoreCase("true"));			
				bean.setInvioNotEmailAttachment(scXmlDatiEvento.getInvioNotEmailAttachment() != null ? scXmlDatiEvento.getInvioNotEmailAttachment() : new ArrayList<AttachmentInvioNotEmailBean>() );			
				bean.setInvioNotEmailFlgInvioMailXComplTask(scXmlDatiEvento.getInvioNotEmailFlgInvioMailXComplTask() != null && scXmlDatiEvento.getInvioNotEmailFlgInvioMailXComplTask().equalsIgnoreCase("true"));						
				bean.setInvioNotEmailFlgConfermaInvio(scXmlDatiEvento.getInvioNotEmailFlgConfermaInvio() != null && scXmlDatiEvento.getInvioNotEmailFlgConfermaInvio().equalsIgnoreCase("true"));		
				bean.setInvioNotEmailFlgCallXDettagliMail(scXmlDatiEvento.getInvioNotEmailFlgCallXDettagliMail() != null && scXmlDatiEvento.getInvioNotEmailFlgCallXDettagliMail().equalsIgnoreCase("true"));				
				// NON VENGONO USATI
//				bean.setFlgNotificaMail(scXmlDatiEvento.getFlgNotificaMail() != null && scXmlDatiEvento.getFlgNotificaMail().equalsIgnoreCase("true"));
//				bean.setNotificaMailSubject(scXmlDatiEvento.getNotificaMailSubject());
//				bean.setNotificaMailBody(scXmlDatiEvento.getNotificaMailBody());
//				bean.setNotificaMailDestinatari(scXmlDatiEvento.getNotificaMailDestinatari());
//				bean.setNotificaMailDestinatariCC(scXmlDatiEvento.getNotificaMailDestinatariCC());
//				bean.setNotificaMailIdCasellaMittente(scXmlDatiEvento.getNotificaMailIdCasellaMittente());
//				bean.setNotificaMailIndirizzoMittente(scXmlDatiEvento.getNotificaMailIndirizzoMittente());				
				bean.setFlgInvioFtpAlbo(scXmlDatiEvento.getFlgInvioFtpAlbo() != null && scXmlDatiEvento.getFlgInvioFtpAlbo().equalsIgnoreCase("true"));
				bean.setFlgPubblica(scXmlDatiEvento.getFlgPubblica() != null && scXmlDatiEvento.getFlgPubblica().equalsIgnoreCase("true"));				
				bean.setFlgCompilaDatiPubblicazione(scXmlDatiEvento.getFlgCompilaDatiPubblicazione() != null && scXmlDatiEvento.getFlgCompilaDatiPubblicazione().equalsIgnoreCase("true"));
				bean.setDataInizioPubblicazione(scXmlDatiEvento.getDataInizioPubblicazione());
				bean.setGiorniPubblicazione(scXmlDatiEvento.getGiorniPubblicazione());
				bean.setIdDocTipDocTask(scXmlDatiEvento.getIdDocType());
				bean.setNomeTipDocTask(scXmlDatiEvento.getNomeDocType());
				bean.setIdModAssDocTask(scXmlDatiEvento.getIdModelloDoc());
				bean.setUriModAssDocTask(scXmlDatiEvento.getUriModelloDoc());
				bean.setTipoModAssDocTask(scXmlDatiEvento.getTipoModelloDoc());
				bean.setNomeModAssDocTask(scXmlDatiEvento.getNomeModelloDoc());
				bean.setDisplayFilenameModAssDocTask(scXmlDatiEvento.getDisplaynameFileDaModello());
				bean.setMimetypeModAssDocTask(scXmlDatiEvento.getMimetypeModelloDoc());
				bean.setIdDocAssTask(scXmlDatiEvento.getIdDocPrimario());
				if (scXmlDatiEvento.getFilePrimario() != null) {
					bean.setUriUltimaVersDocTask(scXmlDatiEvento.getFilePrimario().getUri());
					bean.setNomeFileDocTipAssTask(scXmlDatiEvento.getFilePrimario().getDisplayFilename());
					bean.setMimetypeDocTipAssTask(scXmlDatiEvento.getFilePrimario().getMimetype());
					//File primario (vers. integrale)
					bean.setUriFile(scXmlDatiEvento.getFilePrimario().getUri());
					bean.setNomeFile(scXmlDatiEvento.getFilePrimario().getDisplayFilename());
					MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();					
					if (StringUtils.isNotBlank(scXmlDatiEvento.getFilePrimario().getImpronta())) {
						lMimeTypeFirmaBean.setImpronta(scXmlDatiEvento.getFilePrimario().getImpronta());
					}
					lMimeTypeFirmaBean.setCorrectFileName(scXmlDatiEvento.getFilePrimario().getDisplayFilename());
					lMimeTypeFirmaBean.setFirmato(scXmlDatiEvento.getFilePrimario().getFlgFirmato() == Flag.SETTED);
					lMimeTypeFirmaBean.setFirmaValida(!(scXmlDatiEvento.getFilePrimario().getFlgFirmeNonValideBustaCrittografica() == Flag.SETTED));					
					lMimeTypeFirmaBean.setPdfProtetto(scXmlDatiEvento.getFilePrimario().getFlgPdfProtetto() == Flag.SETTED);					
					lMimeTypeFirmaBean.setPdfEditabile(scXmlDatiEvento.getFilePrimario().getFlgPdfEditabile() == Flag.SETTED);					
					lMimeTypeFirmaBean.setPdfConCommenti(scXmlDatiEvento.getFilePrimario().getFlgPdfConCommenti() == Flag.SETTED);					
					lMimeTypeFirmaBean.setConvertibile(scXmlDatiEvento.getFilePrimario().getFlgConvertibilePdf() == Flag.SETTED);
					lMimeTypeFirmaBean.setDaScansione(false);
					lMimeTypeFirmaBean.setMimetype(scXmlDatiEvento.getFilePrimario().getMimetype());
					lMimeTypeFirmaBean.setBytes(scXmlDatiEvento.getFilePrimario().getDimensione() != null ? scXmlDatiEvento.getFilePrimario().getDimensione().longValue() : 0);
					if (lMimeTypeFirmaBean.isFirmato()) {
						lMimeTypeFirmaBean.setTipoFirma(scXmlDatiEvento.getFilePrimario().getDisplayFilename().toUpperCase().endsWith("P7M")
								|| scXmlDatiEvento.getFilePrimario().getDisplayFilename().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
						lMimeTypeFirmaBean.setFirmatari(StringUtils.isNotBlank(scXmlDatiEvento.getFilePrimario().getFirmatari()) ? scXmlDatiEvento.getFilePrimario().getFirmatari()
								.split(",") : null);
					}				
					bean.setInfoFile(lMimeTypeFirmaBean);
					//File primario (vers. con omissis)
					bean.setUriFileOmissis(scXmlDatiEvento.getFilePrimario().getUriOmissis());
					bean.setNomeFileOmissis(scXmlDatiEvento.getFilePrimario().getDisplayFilenameOmissis());
					MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = new MimeTypeFirmaBean();					
					if (StringUtils.isNotBlank(scXmlDatiEvento.getFilePrimario().getImprontaOmissis())) {
						lMimeTypeFirmaBeanOmissis.setImpronta(scXmlDatiEvento.getFilePrimario().getImprontaOmissis());
					}
					lMimeTypeFirmaBeanOmissis.setCorrectFileName(scXmlDatiEvento.getFilePrimario().getDisplayFilenameOmissis());
					lMimeTypeFirmaBeanOmissis.setFirmato(scXmlDatiEvento.getFilePrimario().getFlgFirmatoOmissis() == Flag.SETTED);
					lMimeTypeFirmaBeanOmissis.setFirmaValida(!(scXmlDatiEvento.getFilePrimario().getFlgFirmeNonValideBustaCrittograficaOmissis() == Flag.SETTED));					
					lMimeTypeFirmaBeanOmissis.setPdfProtetto(scXmlDatiEvento.getFilePrimario().getFlgPdfProtettoOmissis() == Flag.SETTED);					
					lMimeTypeFirmaBeanOmissis.setPdfEditabile(scXmlDatiEvento.getFilePrimario().getFlgPdfEditabileOmissis() == Flag.SETTED);					
					lMimeTypeFirmaBeanOmissis.setPdfConCommenti(scXmlDatiEvento.getFilePrimario().getFlgPdfConCommentiOmissis() == Flag.SETTED);					
					lMimeTypeFirmaBeanOmissis.setConvertibile(scXmlDatiEvento.getFilePrimario().getFlgConvertibilePdfOmissis() == Flag.SETTED);
					lMimeTypeFirmaBeanOmissis.setDaScansione(false);
					lMimeTypeFirmaBeanOmissis.setMimetype(scXmlDatiEvento.getFilePrimario().getMimetypeOmissis());
					lMimeTypeFirmaBeanOmissis.setBytes(scXmlDatiEvento.getFilePrimario().getDimensioneOmissis() != null ? scXmlDatiEvento.getFilePrimario().getDimensioneOmissis().longValue() : 0);
					if (lMimeTypeFirmaBeanOmissis.isFirmato()) {
						lMimeTypeFirmaBeanOmissis.setTipoFirma(scXmlDatiEvento.getFilePrimario().getDisplayFilenameOmissis().toUpperCase().endsWith("P7M")
								|| scXmlDatiEvento.getFilePrimario().getDisplayFilenameOmissis().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
						lMimeTypeFirmaBeanOmissis.setFirmatari(StringUtils.isNotBlank(scXmlDatiEvento.getFilePrimario().getFirmatariOmissis()) ? scXmlDatiEvento.getFilePrimario().getFirmatariOmissis()
								.split(",") : null);
					}				
					bean.setInfoFileOmissis(lMimeTypeFirmaBeanOmissis);													
				}
				bean.setNomeTastoSalvaProvvisorio(scXmlDatiEvento.getNomeTastoSalvaProvvisorio());
				bean.setNomeTastoSalvaDefinitivo(scXmlDatiEvento.getNomeTastoSalvaDefinitivo());
				bean.setNomeTastoSalvaProvvisorio_2(scXmlDatiEvento.getNomeTastoSalvaProvvisorio_2());
				bean.setNomeTastoSalvaDefinitivo_2(scXmlDatiEvento.getNomeTastoSalvaDefinitivo_2());
				bean.setAlertConfermaSalvaDefinitivo(scXmlDatiEvento.getAlertConfermaSalvaDefinitivo());		
				bean.setWarningMsg(scXmlDatiEvento.getWarningMsg());
				bean.setFileDaUnire(scXmlDatiEvento.getFileDaUnire());
				bean.setNomeFileUnione(scXmlDatiEvento.getNomeFileUnione());
				bean.setFileDaUnireVersIntegrale(scXmlDatiEvento.getFileDaUnireVersIntegrale());
				bean.setNomeFileUnioneVersIntegrale(scXmlDatiEvento.getNomeFileUnioneVersIntegrale());
				bean.setTipoDocFileUnioneVersIntegrale(scXmlDatiEvento.getTipoDocFileUnioneVersIntegrale());
				bean.setSiglaRegistroAtto(scXmlDatiEvento.getSiglaRegistroAtto());
				bean.setSiglaRegistroAtto2(scXmlDatiEvento.getSiglaRegistroAtto2());				
				bean.setEditor(scXmlDatiEvento.getEditor());
				bean.setNomeAttrCustomEsitoTask(scXmlDatiEvento.getNomeAttrCustomEsitoTask());
				bean.setFlgReadOnly(scXmlDatiEvento.getFlgReadOnly() == Flag.SETTED);
				bean.setIdModCopertina(scXmlDatiEvento.getIdModCopertina());
				bean.setNomeModCopertina(scXmlDatiEvento.getNomeModCopertina());
				bean.setUriModCopertina(scXmlDatiEvento.getUriModCopertina());
				bean.setTipoModCopertina(scXmlDatiEvento.getTipoModCopertina());
				bean.setIdModCopertinaFinale(scXmlDatiEvento.getIdModCopertinaFinale());
				bean.setNomeModCopertinaFinale(scXmlDatiEvento.getNomeModCopertinaFinale());
				bean.setUriModCopertinaFinale(scXmlDatiEvento.getUriModCopertinaFinale());
				bean.setTipoModCopertinaFinale(scXmlDatiEvento.getTipoModCopertinaFinale());
				bean.setIdModAllegatiParteIntSeparati(scXmlDatiEvento.getIdModAllegatiParteIntSeparati());
				bean.setNomeModAllegatiParteIntSeparati(scXmlDatiEvento.getNomeModAllegatiParteIntSeparati());
				bean.setUriModAllegatiParteIntSeparati(scXmlDatiEvento.getUriModAllegatiParteIntSeparati());
				bean.setTipoModAllegatiParteIntSeparati(scXmlDatiEvento.getTipoModAllegatiParteIntSeparati());
				bean.setIdModAllegatiParteIntSeparatiXPubbl(scXmlDatiEvento.getIdModAllegatiParteIntSeparatiXPubbl());
				bean.setNomeModAllegatiParteIntSeparatiXPubbl(scXmlDatiEvento.getNomeModAllegatiParteIntSeparatiXPubbl());
				bean.setUriModAllegatiParteIntSeparatiXPubbl(scXmlDatiEvento.getUriModAllegatiParteIntSeparatiXPubbl());
				bean.setTipoModAllegatiParteIntSeparatiXPubbl(scXmlDatiEvento.getTipoModAllegatiParteIntSeparatiXPubbl());
				bean.setFlgAppendiceDaUnire(scXmlDatiEvento.getFlgAppendiceDaUnire() == Flag.SETTED);
				bean.setIdModAppendice(scXmlDatiEvento.getIdModAppendice());
				bean.setNomeModAppendice(scXmlDatiEvento.getNomeModAppendice());
				bean.setUriModAppendice(scXmlDatiEvento.getUriModAppendice());
				bean.setTipoModAppendice(scXmlDatiEvento.getTipoModAppendice());
				bean.setUriAppendice(scXmlDatiEvento.getUriAppendice());
				bean.setDisplayFilenameModAppendice(scXmlDatiEvento.getDisplayFilenameModAppendice());
				bean.setIdModFoglioFirme(scXmlDatiEvento.getIdModFoglioFirme());
				bean.setNomeModFoglioFirme(scXmlDatiEvento.getNomeModFoglioFirme());
				bean.setDisplayFilenameModFoglioFirme(scXmlDatiEvento.getDisplayFilenameModFoglioFirme());
				bean.setIdModFoglioFirme2(scXmlDatiEvento.getIdModFoglioFirme2());
				bean.setNomeModFoglioFirme2(scXmlDatiEvento.getNomeModFoglioFirme2());
				bean.setDisplayFilenameModFoglioFirme2(scXmlDatiEvento.getDisplayFilenameModFoglioFirme2());				
				bean.setIdModSchedaTrasp(scXmlDatiEvento.getIdModSchedaTrasp());
				bean.setNomeModSchedaTrasp(scXmlDatiEvento.getNomeModSchedaTrasp());
				bean.setDisplayFilenameModSchedaTrasp(scXmlDatiEvento.getDisplayFilenameModSchedaTrasp());					
				bean.setAttributiAddDocTabs(scXmlDatiEvento.getAttributiAddDocTabs());
				bean.setEsitiTaskOk(scXmlDatiEvento.getEsitiTaskOk());
				bean.setEsitiTaskKo(scXmlDatiEvento.getEsitiTaskKo());
				bean.setFileUnioneDaFirmare(scXmlDatiEvento.getFileUnioneDaFirmare() == Flag.SETTED);
				bean.setFileUnioneDaTimbrare(scXmlDatiEvento.getFileUnioneDaTimbrare() == Flag.SETTED);
				// Federico Cacco 21.12.2015
				// Aggiunti parametri per la posizione del timbro della copertina (ove richiesto che la copertina sia timbrata)
				bean.setPosizioneTimbro(StringUtils.isNotBlank(scXmlDatiEvento.getTimbroPosizione()) ? scXmlDatiEvento.getTimbroPosizione() : "altoSn");
				bean.setTipoPagina(StringUtils.isNotBlank(scXmlDatiEvento.getTimbroSelezionePagine()) ? scXmlDatiEvento.getTimbroSelezionePagine() : "tutte");
				bean.setPaginaDa(StringUtils.isNotBlank(scXmlDatiEvento.getTimbroPaginaDa()) ? scXmlDatiEvento.getTimbroPaginaDa() : "1");
				bean.setPaginaA(StringUtils.isNotBlank(scXmlDatiEvento.getTimbroPaginaA()) ? scXmlDatiEvento.getTimbroPaginaA() : "10");
				bean.setRotazioneTimbro(StringUtils.isNotBlank(scXmlDatiEvento.getTimbroRotazione()) ? scXmlDatiEvento.getTimbroRotazione() : "verticale");
				bean.setCodTabDefault(scXmlDatiEvento.getCodTabDefault());
				bean.setIdTipoTaskDoc(scXmlDatiEvento.getIdTipoTaskDoc());
				bean.setNomeTipoTaskDoc(scXmlDatiEvento.getNomeTipoTaskDoc());
				bean.setFlgParereTaskDoc(scXmlDatiEvento.getFlgParereTaskDoc() == Flag.SETTED);
				bean.setFlgParteDispositivoTaskDoc(scXmlDatiEvento.getFlgParteDispositivoTaskDoc() == Flag.SETTED);
				bean.setFlgNoPubblTaskDoc(scXmlDatiEvento.getFlgNoPubblTaskDoc() == Flag.SETTED);
				bean.setFlgPubblicaSeparatoTaskDoc(scXmlDatiEvento.getFlgPubblicaSeparatoTaskDoc() == Flag.SETTED);
				bean.setControlliXEsitiTaskDoc(scXmlDatiEvento.getControlliXEsitiTaskDoc());
				bean.setValoriEsito(scXmlDatiEvento.getValoriEsito());		
				bean.setAttivaModificaEsclusionePubblicazione(scXmlDatiEvento.getAttivaModificaEsclusionePubblicazione());
				bean.setAttivaEliminazioneUOCoinvolte(scXmlDatiEvento.getAttivaEliminazioneUOCoinvolte());
				bean.setTaskArchivio(scXmlDatiEvento.getTaskArchivio() == Flag.SETTED);
				bean.setFlgDatiSpesaEditabili(scXmlDatiEvento.getFlgDatiSpesaEditabili() == Flag.SETTED);
				bean.setFlgCIGEditabile(scXmlDatiEvento.getFlgCIGEditabile() == Flag.SETTED);
				bean.setTipoEventoSIB(scXmlDatiEvento.getTipoEventoSIB());
				bean.setTipiEventoSIBXEsitoTask(scXmlDatiEvento.getTipiEventoSIBXEsitoTask());
				bean.setEsitiTaskEventoSIB(scXmlDatiEvento.getEsitiTaskEventoSIB());
				bean.setIdUoDirAdottanteSIB(scXmlDatiEvento.getIdUoDirAdottanteSIB());
				bean.setCodUoDirAdottanteSIB(scXmlDatiEvento.getCodUoDirAdottanteSIB());
				bean.setDesUoDirAdottanteSIB(scXmlDatiEvento.getDesUoDirAdottanteSIB());							
				bean.setTipiEventoContabiliaXEsitoTask(scXmlDatiEvento.getTipiEventoContabiliaXEsitoTask());
				bean.setTipiEventoSICRAXEsitoTask(scXmlDatiEvento.getTipiEventoSICRAXEsitoTask());
				bean.setTipiEventoCWOLXEsitoTask(scXmlDatiEvento.getTipiEventoCWOLXEsitoTask());
				bean.setFlgRecuperaMovimentiContabDefinitivi(scXmlDatiEvento.getFlgRecuperaMovimentiContabDefinitivi() != null && scXmlDatiEvento.getFlgRecuperaMovimentiContabDefinitivi().equalsIgnoreCase("true"));
				bean.setFlgAttivaSmistamento(scXmlDatiEvento.getFlgAttivaSmistamento() != null && scXmlDatiEvento.getFlgAttivaSmistamento().equalsIgnoreCase("true"));
				bean.setNomeAttrListaDatiContabili(scXmlDatiEvento.getNomeAttrListaDatiContabili());
				bean.setNomeAttrListaDatiContabiliRichiesti(scXmlDatiEvento.getNomeAttrListaDatiContabiliRichiesti());
				bean.setFlgAttivaRequestMovimentiDaAMC(scXmlDatiEvento.getFlgAttivaRequestMovimentiDaAMC() != null && scXmlDatiEvento.getFlgAttivaRequestMovimentiDaAMC().equalsIgnoreCase("true"));
				bean.setFlgAttivaSalvataggioMovimentiDaAMC(scXmlDatiEvento.getFlgAttivaSalvataggioMovimentiDaAMC() != null && scXmlDatiEvento.getFlgAttivaSalvataggioMovimentiDaAMC().equalsIgnoreCase("true"));		
				bean.setFlgEscludiFiltroCdCVsAMC(scXmlDatiEvento.getFlgEscludiFiltroCdCVsAMC() != null && scXmlDatiEvento.getFlgEscludiFiltroCdCVsAMC().equalsIgnoreCase("true"));
				// Editor organigramma
				bean.setLivelloMaxRevisione(scXmlDatiEvento.getLivelloMaxRevisione());
				bean.setTipiUORevisione(scXmlDatiEvento.getTipiUORevisione());
				bean.setNomeVersConfronto(scXmlDatiEvento.getNomeVersConfrontoOrganigramma());
				bean.setNroVersConfronto(scXmlDatiEvento.getNroVersConfrontoOrganigramma());	
				bean.setNroVersLavoro(scXmlDatiEvento.getNroVersLavoroOrganigramma());	
				bean.setAzione(scXmlDatiEvento.getAzioneOrganigramma());	
				bean.setIdDocOrganigramma(scXmlDatiEvento.getIdDocOrganigramma());			
				bean.setIdDocArchiviazionePdf(scXmlDatiEvento.getIdDocArchiviazionePdfOrganigramma());	
				bean.setLivelloRevisione(scXmlDatiEvento.getLivelloRevisioneOrganigramma());					
				// Delibere
				bean.setFlgDelibera(scXmlDatiEvento.getFlgDelibera() == Flag.SETTED);
				bean.setShowDirigentiProponenti(scXmlDatiEvento.getShowDirigentiProponenti());
				bean.setShowAssessori(scXmlDatiEvento.getShowAssessori());
				bean.setShowConsiglieri(scXmlDatiEvento.getShowConsiglieri());	
				bean.setFlgPubblicazioneAllegatiUguale(scXmlDatiEvento.getFlgPubblicazioneAllegatiUguale() != null && scXmlDatiEvento.getFlgPubblicazioneAllegatiUguale().equalsIgnoreCase("true"));				
				ParametriTipoAttoBean lParametriTipoAttoBean = new ParametriTipoAttoBean();
				lParametriTipoAttoBean.setAttributiCustomCablati(scXmlDatiEvento.getParametriTipoAttoAttributiCustomCablati());				
				//TODO serve qui caricare i valori dell'ufficio proponente? dopo l'avvio non può più essere modificato
				String altriParamLoadComboUfficioProponente = null;
				if(lParametriTipoAttoBean.getAttributiCustomCablati() != null) {
					for(AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBean : lParametriTipoAttoBean.getAttributiCustomCablati()) {
						if(lAttributiCustomCablatiAttoXmlBean.getAttrName() != null && lAttributiCustomCablatiAttoXmlBean.getAttrName().equalsIgnoreCase("ID_UO_PROPONENTE")) {
							altriParamLoadComboUfficioProponente = lAttributiCustomCablatiAttoXmlBean.getAltriParametriLoadCombo();
							break;
						}
					}
				}
				UfficioProponenteAttoDatasource lUfficioProponenteAttoDatasource = new UfficioProponenteAttoDatasource();		
				lUfficioProponenteAttoDatasource.setSession(getSession());
				Map<String, String> extraparams = new LinkedHashMap<String, String>();
				extraparams.put("altriParamLoadCombo", altriParamLoadComboUfficioProponente);			
				extraparams.put("idTipoDoc", bean.getIdTipoDoc());			
				lUfficioProponenteAttoDatasource.setExtraparams(extraparams);
				PaginatorBean<UoProtocollanteBean> valoriUfficioProponente = lUfficioProponenteAttoDatasource.fetch(null, null, null, null);
				lParametriTipoAttoBean.setValoriUfficioProponente(valoriUfficioProponente != null ? valoriUfficioProponente.getData() : new ArrayList<UoProtocollanteBean>());
				if(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegrante() != null && scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegrante() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAllegAttoParteIntDefaultXTipoAtto(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegrante() == Flag.SETTED);
				} else {
					lParametriTipoAttoBean.setFlgAllegAttoParteIntDefaultXTipoAtto(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FLG_ALLEG_ATTO_PARTE_INT_DEFAULT"));
				}
				if(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdPermanente() != null && scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdPermanente() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAllegAttoParteIntDefaultOrdPermanente(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdPermanente() == Flag.SETTED);
				}
				if(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea() != null && scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAllegAttoParteIntDefaultOrdTemporanea(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea() == Flag.SETTED);
				}
				if(scXmlDatiEvento.getParametriTipoAttoDefaultAllegPubblSeparata() != null && scXmlDatiEvento.getParametriTipoAttoDefaultAllegPubblSeparata() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAllegAttoPubblSepDefaultXTipoAtto(scXmlDatiEvento.getParametriTipoAttoDefaultAllegPubblSeparata() == Flag.SETTED);
				} else {
					lParametriTipoAttoBean.setFlgAllegAttoPubblSepDefaultXTipoAtto(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FLG_ALLEG_ATTO_PUBBL_SEPARATA_DEFAULT"));
				}
				if(scXmlDatiEvento.getParametriTipoAttoAttivaSceltaPosizioneAllegatiUniti() != null && scXmlDatiEvento.getParametriTipoAttoAttivaSceltaPosizioneAllegatiUniti() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAttivaSceltaPosizioneAllegatiUniti(scXmlDatiEvento.getParametriTipoAttoAttivaSceltaPosizioneAllegatiUniti() == Flag.SETTED);
				}
				bean.setParametriTipoAtto(lParametriTipoAttoBean);
				bean.setFlgSoloPreparazioneVersPubblicazione(scXmlDatiEvento.getFlgSoloPreparazioneVersPubblicazione() != null && scXmlDatiEvento.getFlgSoloPreparazioneVersPubblicazione().equalsIgnoreCase("true"));
				bean.setFlgCtrlMimeTypeAllegPI(scXmlDatiEvento.getFlgCtrlMimeTypeAllegPI() != null && scXmlDatiEvento.getFlgCtrlMimeTypeAllegPI().equalsIgnoreCase("true"));
				bean.setFlgProtocollazioneProsa(scXmlDatiEvento.getFlgProtocollazioneProsa() != null && "true".equalsIgnoreCase(scXmlDatiEvento.getFlgProtocollazioneProsa()) ? true : false);
				bean.setFlgPresentiEmendamenti(scXmlDatiEvento.getFlgPresentiEmendamenti() == Flag.SETTED);
				bean.setFlgFirmaVersPubblAggiornata(scXmlDatiEvento.getFlgFirmaVersPubblAggiornata() == Flag.SETTED);		
				bean.setAbilAggiornaStatoAttoPostDiscussione(scXmlDatiEvento.getAbilAggiornaStatoAttoPostDiscussione() != null && scXmlDatiEvento.getAbilAggiornaStatoAttoPostDiscussione().equalsIgnoreCase("true"));
				bean.setStatiXAggAttoPostDiscussione(scXmlDatiEvento.getStatiXAggAttoPostDiscussione());
				bean.setExportAttoInDocFmt(scXmlDatiEvento.getExportAttoInDocFmt());
				bean.setFlgAvvioLiquidazioneContabilia(scXmlDatiEvento.getFlgAvvioLiquidazioneContabilia() == Flag.SETTED);
				bean.setWarningMsgXEsitoTask(scXmlDatiEvento.getWarningMsgXEsitoTask());
				bean.setEsitoTaskDaPreimpostare(scXmlDatiEvento.getEsitoTaskDaPreimpostare());
				bean.setMsgTaskDaPreimpostare(scXmlDatiEvento.getMsgTaskDaPreimpostare());
				bean.setMsgTaskProvvisorio(scXmlDatiEvento.getMsgTaskProvvisorio());
				bean.setFlgAbilToSelProponentiEstesi(scXmlDatiEvento.getFlgAbilToSelProponentiEstesi() == Flag.SETTED);
				bean.setFlgAttivaUploadPdfAtto(scXmlDatiEvento.getFlgAttivaUploadPdfAtto() != null && scXmlDatiEvento.getFlgAttivaUploadPdfAtto().equalsIgnoreCase("true"));
				bean.setFlgAttivaUploadPdfAttoOmissis(scXmlDatiEvento.getFlgAttivaUploadPdfAttoOmissis() != null && scXmlDatiEvento.getFlgAttivaUploadPdfAttoOmissis().equalsIgnoreCase("true"));
				
				ImpostazioniUnioneFileBean impostazioneUnioneFile = new ImpostazioniUnioneFileBean();
				impostazioneUnioneFile.setInfoAllegatoAttiva(scXmlDatiEvento.getUnioneFileInfoAllegatoAttiva() == Flag.SETTED);
				impostazioneUnioneFile.setNroPaginaAttiva(scXmlDatiEvento.getUnioneFileNroPaginaAttiva() == Flag.SETTED);
				impostazioneUnioneFile.setNroPagineFirmeGrafiche(scXmlDatiEvento.getNroPagineFirmeGrafiche());
				impostazioneUnioneFile.setIdModelloDocFirmeGrafiche(scXmlDatiEvento.getIdModelloDocFirmeGrafiche());
				impostazioneUnioneFile.setNomeModelloDocFirmeGrafiche(scXmlDatiEvento.getNomeModelloDocFirmeGrafiche());
				impostazioneUnioneFile.setIdDocModelloFirmeGrafiche(scXmlDatiEvento.getIdDocModelloFirmeGrafiche());
				impostazioneUnioneFile.setTipoModelloDocFirmeGrafiche(scXmlDatiEvento.getTipoModelloDocFirmeGrafiche());				
				if (impostazioneUnioneFile.getInfoAllegatoAttiva()) {
					impostazioneUnioneFile.setInfoAllegatoFont(scXmlDatiEvento.getUnioneFileInfoAllegatoFont());
					impostazioneUnioneFile.setInfoAllegatoFontSize(scXmlDatiEvento.getUnioneFileInfoAllegatoFontSize());
					impostazioneUnioneFile.setInfoAllegatoStyle(StringUtils.isNotBlank(scXmlDatiEvento.getUnioneFileInfoAllegatoStyle()) ? scXmlDatiEvento.getUnioneFileInfoAllegatoStyle() : "normal");
					impostazioneUnioneFile.setInfoAllegatoTextColor(StringUtils.isNotBlank(scXmlDatiEvento.getUnioneFileInfoAllegatoTextColor()) ? scXmlDatiEvento.getUnioneFileInfoAllegatoTextColor() : "black");
					impostazioneUnioneFile.setInfoAllegatoMaxLenNomeFileAllegato(StringUtils.isNotBlank(scXmlDatiEvento.getUnioneFileInfoAllegatoMaxLenNomeFileAllegato()) ? Integer.parseInt(scXmlDatiEvento.getUnioneFileInfoAllegatoMaxLenNomeFileAllegato()) : 100);
					impostazioneUnioneFile.setInfoAllegatoFormato(StringUtils.isNotBlank(scXmlDatiEvento.getUnioneFileInfoAllegatoFormato()) ? scXmlDatiEvento.getUnioneFileInfoAllegatoFormato() : "Allegato N° $nroAllegato$");
					impostazioneUnioneFile.setInfoAllegatoMargine(StringUtils.isNotBlank(scXmlDatiEvento.getUnioneFileInfoAllegatoMargine()) ? scXmlDatiEvento.getUnioneFileInfoAllegatoMargine() : "alto dx");
					impostazioneUnioneFile.setInfoAllegatoOrientamento(StringUtils.isNotBlank(scXmlDatiEvento.getUnioneFileInfoAllegatoOrientamento()) ? scXmlDatiEvento.getUnioneFileInfoAllegatoOrientamento() : "orizzontale");
					impostazioneUnioneFile.setInfoAllegatoPagine(StringUtils.isNotBlank(scXmlDatiEvento.getUnioneFileInfoAllegatoPagine()) ? scXmlDatiEvento.getUnioneFileInfoAllegatoPagine() : "prima");		
				}
				if (impostazioneUnioneFile.getNroPaginaAttiva()) {
					impostazioneUnioneFile.setNroPaginaEscludiAllegatiMaggioreA4(scXmlDatiEvento.getUnioneFileNroPaginaEscludiAllegatiMaggioriA4() == Flag.SETTED);
					impostazioneUnioneFile.setNroPaginaFont(scXmlDatiEvento.getUnioneFileNroPaginaFont());
					impostazioneUnioneFile.setNroPaginaFontSize(scXmlDatiEvento.getUnioneFileNroPaginaFontSize());
					impostazioneUnioneFile.setNroPaginaFormato(StringUtils.isNotBlank(scXmlDatiEvento.getUnioneFileNroPaginaFormato()) ? scXmlDatiEvento.getUnioneFileNroPaginaFormato() : "Pag $nroPagina$");
					impostazioneUnioneFile.setNroPaginaPosizione(StringUtils.isNotBlank(scXmlDatiEvento.getUnioneFileNroPaginaPosizione()) ? scXmlDatiEvento.getUnioneFileNroPaginaPosizione() : "basso dx");
					impostazioneUnioneFile.setNroPaginaStyle(StringUtils.isNotBlank(scXmlDatiEvento.getUnioneFileNroPaginaStyle()) ? scXmlDatiEvento.getUnioneFileNroPaginaStyle() : "normal");
					impostazioneUnioneFile.setNroPaginaTextColor(StringUtils.isNotBlank(scXmlDatiEvento.getUnioneFileNroPaginaTextColor()) ? scXmlDatiEvento.getUnioneFileNroPaginaTextColor() : "black");
					impostazioneUnioneFile.setNroPaginaNumerazioneDistintaXFileUniti(scXmlDatiEvento.getUnioneFileNroPaginaNumerazioneDistintaXFileUniti() == Flag.SETTED);
					impostazioneUnioneFile.setNroPaginaEscludiAllegatiUnitiInMezzo(scXmlDatiEvento.getUnioneFileNroPaginaEscludiAllegatiUnitiInMezzo() == Flag.SETTED);
					impostazioneUnioneFile.setNroPaginaEscludiAllegatiUnitiInFondo(scXmlDatiEvento.getUnioneFileNroPaginaEscludiAllegatiUnitiInFondo() == Flag.SETTED);
					impostazioneUnioneFile.setNroPaginaEscludiFoglioFirmeGrafiche(scXmlDatiEvento.getUnioneFileNroPaginaEscludiFoglioFirmeGrafiche() == Flag.SETTED);
					impostazioneUnioneFile.setNroPaginaEscludiListaAllegatiSeparati(scXmlDatiEvento.getUnioneFileNroPaginaEscludiListaAllegatiSeparati() == Flag.SETTED);
					impostazioneUnioneFile.setNroPaginaEscludiAppendice(scXmlDatiEvento.getUnioneFileNroPaginaEscludiAppendice() == Flag.SETTED);
				}
				bean.setImpostazioniUnioneFile(impostazioneUnioneFile);
				
				bean.setFlgSoloOmissisModProprietaFile(scXmlDatiEvento.getFlgSoloOmissisModProprietaFile() != null && scXmlDatiEvento.getFlgSoloOmissisModProprietaFile().equalsIgnoreCase("true"));
				bean.setFlgDocActionsFirmaAutomatica(scXmlDatiEvento.getFlgDocActionsFirmaAutomatica() != null && scXmlDatiEvento.getFlgDocActionsFirmaAutomatica().equalsIgnoreCase("true"));
				bean.setDocActionsFirmaAutomaticaProvider(scXmlDatiEvento.getDocActionsFirmaAutomaticaProvider());
				bean.setDocActionsFirmaAutomaticaUseridFirmatario(scXmlDatiEvento.getDocActionsFirmaAutomaticaUseridFirmatario());
				bean.setDocActionsFirmaAutomaticaFirmaInDelega(scXmlDatiEvento.getDocActionsFirmaAutomaticaFirmaInDelega());
				bean.setDocActionsFirmaAutomaticaPassword(scXmlDatiEvento.getDocActionsFirmaAutomaticaPassword());
				bean.setInfoFirmaGrafica(scXmlDatiEvento.getInfoFirmaGrafica());
				if (bean.getInfoFirmaGrafica() != null && StringUtils.isNotBlank(scXmlDatiEvento.getNroPagineFirmeGrafiche())) {
					for (InfoFirmaGraficaBean lInfoFirmaGraficaBean : bean.getInfoFirmaGrafica()) {
						lInfoFirmaGraficaBean.setNroPagineFirmeGrafiche(Integer.valueOf(scXmlDatiEvento.getNroPagineFirmeGrafiche()));
					}
				}
			}
		}
//		setTestData(bean);
		return bean;
	}
	
	public void setTestData(AttProcBean bean) {
		
//		String idProcessToForcePubbl = ParametriDBUtil.getParametroDB(getSession(), "ID_PROC_TO_FORCE_PUBBL");
//		String activityNameToForcePubbl = ParametriDBUtil.getParametroDB(getSession(), "TASK_NAME_TO_FORCE_PUBBL");
//		if(idProcessToForcePubbl != null && bean.getIdProcess() != null && idProcessToForcePubbl.equals(bean.getIdProcess()) 
//		   && activityNameToForcePubbl != null && bean.getActivityName() != null && activityNameToForcePubbl.equals(bean.getActivityName())) {
//			bean.setFlgCompilaDatiPubblicazione(true);
//			bean.setFlgEseguibile("1");
//		}
		
//		if (bean.getParametriTipoAtto().getAttributiCustomCablati() != null) {
//			String s = "";
//			for (AttributiCustomCablatiAttoXmlBean attributo : bean.getParametriTipoAtto().getAttributiCustomCablati()) {
//				if ("DISPOSITIVO_ATTO".equalsIgnoreCase(attributo.getAttrName())) {
//					attributo.setFlgAbilitaIniezioneCkEditorDaFile("1");
//				}
//				s = s + "\n" + attributo.getAttrName() + " " + attributo.toString(); 
//			}
//			log.debug("Attributi custom cablati: " + s);
//		}
				
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanFORZA_MODIFICA_ATTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanFORZA_MODIFICA_ATTO.setAttrName("FORZA_MODIFICA_ATTO");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanFORZA_MODIFICA_ATTO);
		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG.setAttrName("UFF_COMPETENTE_RAG");
//		lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG.setAttrLabel("TEST");
//		lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG.setMaxNumValori("2");
//		lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG.setAltriParametriLoadCombo("TIPI_SOGG_INT|*|UO|*|ID_USER_LAVORO|*|$ID_USER_LAVORO$|*|FLG_ANCHE_CON_DELEGA|*|1|*|FLG_INCL_SOTTOUO_SV|*|1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanIMPORT_PDF_LIQUIDAZIONE_AMC = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanIMPORT_PDF_LIQUIDAZIONE_AMC.setAttrName("IMPORT_PDF_LIQUIDAZIONE_AMC");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanIMPORT_PDF_LIQUIDAZIONE_AMC);				
		
//		bean.setFlgFirmaFile(true);
//		bean.setFlgTimbraFile(true);
//		bean.setFlgFirmaVersPubblAggiornata(true);
//		bean.setSiglaRegistroAtto("DD");
//		bean.setFlgUnioneFile(true);
//		bean.setUnioneFileNomeFile("RispostaCompleta.pdf");
//		bean.setUnioneFileIdTipoDoc(null);
//		bean.setUnioneFileNomeTipoDoc(null);
//		bean.setUnioneFileDescrizione("Risposta completa");
		
//		bean.setFlgInvioPEC(true);
//		bean.setInvioPECSubject("Invio risposta completa");
//		bean.setInvioPECBody("Risposta completa bla bla bla...");
//		bean.setInvioPECDestinatari("daniele.cristiano@eng.it; federico.cacco@eng.it");
//		bean.setInvioPECDestinatariCC("mattia.zanetti@eng.it; mattia.zanin@eng.it");
//		bean.setInvioPECIndirizzoMittente("helpdesk2.documentale@eng.it");		
//		bean.setCodTabDefault("DATI_RAG");
//		bean.setIdTipoTaskDoc("30");
//		bean.setNomeTipoTaskDoc("Parere contabile");
//		bean.setFlgParteDispositivoTaskDoc(true);
		
//		List<ControlloXEsitoTaskDocBean> controlliXEsitiTaskDoc = new ArrayList<ControlloXEsitoTaskDocBean>();
//		ControlloXEsitoTaskDocBean controllo1 = new ControlloXEsitoTaskDocBean();
//		controllo1.setEsito("positiva");
//		controllo1.setFlgObbligatorio("1");
//		controllo1.setFlgFileObbligatorio("1");
//		controllo1.setFlgFileFirmato("1");
//		controlliXEsitiTaskDoc.add(controllo1);
//		ControlloXEsitoTaskDocBean controllo2 = new ControlloXEsitoTaskDocBean();
//		controllo2.setEsito("negativa");
//		controllo2.setFlgObbligatorio(null);
//		controllo2.setFlgFileObbligatorio(null);
//		controllo2.setFlgFileFirmato(null);
//		controlliXEsitiTaskDoc.add(controllo2);
//		bean.setControlliXEsitiTaskDoc(controlliXEsitiTaskDoc);
//		if ("pubblicazione_albo".equals(bean.getActivityName())) {
//			bean.setIdGUIEvento("PUBBLICAZIONE_ALBO_2");
//			bean.setFileUnioneDaFirmare(true);
//			bean.setFileUnioneDaTimbrare(true);
//		}
		
//		List<WarningMsgXEsitoTaskBean> warningMsgXEsitoTask = new ArrayList<WarningMsgXEsitoTaskBean>();
//		WarningMsgXEsitoTaskBean lWarningMsgXEsitoTaskBean = new WarningMsgXEsitoTaskBean();
//		lWarningMsgXEsitoTaskBean.setEsito("procedi a visti/approvazioni");
//		lWarningMsgXEsitoTaskBean.setWarningMsg("Sei proprio sicuro sicuro? Vuoi procedere?");
//		warningMsgXEsitoTask.add(lWarningMsgXEsitoTaskBean);
//		bean.setWarningMsgXEsitoTask(warningMsgXEsitoTask);
		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanALLEGATI_NON_PARTE_INTEGRANTE = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanALLEGATI_NON_PARTE_INTEGRANTE.setAttrName("#ALLEGATI_NON_PARTE_INTEGRANTE");
//		lAttributiCustomCablatiAttoXmlBeanALLEGATI_NON_PARTE_INTEGRANTE.setFlgEditabile("0");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanALLEGATI_NON_PARTE_INTEGRANTE);
		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DET_CON_SPESA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DET_CON_SPESA.setAttrName("TASK_RESULT_2_DET_CON_SPESA");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DET_CON_SPESA.setValoriPossibili("NO");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DET_CON_SPESA.setValoreFisso("NO");		
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DET_CON_SPESA);
		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO.setAttrName("IND_EMAIL_DEST_NOTIFICA_ATTO");
//		lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO.setAttrLabel("Dest. notifiche");
//		lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO);
		
//		if(bean.getIdDefProcFlow() != null && bean.getIdDefProcFlow().startsWith("VisuraUrbanisticaCMMI")) {
//			bean.setIdGUIEvento("DETT_RICH_ACCESSO_ATTI_URB");
//			bean.setIdUd(bean.getIdUdIstanza());	
//		}
		
//		bean.setFlgFirmaFile(true);
//		bean.setFlgDocActionsFirmaAutomatica(true);
//		bean.setDocActionsFirmaAutomaticaProvider("ARUBA_FA");
//		bean.setDocActionsFirmaAutomaticaFirmaInDelega("mferrign");
//		bean.setDocActionsFirmaAutomaticaUseridFirmatario("cosnigro");
//		bean.setDocActionsFirmaAutomaticaPassword("kVGDxMzlLVyY");

//		bean.setFlgUnioneFile(true);
//		bean.setUnioneFileNomeFile("RispostaCompleta.pdf");
//		bean.setUnioneFileIdTipoDoc(null);
//		bean.setUnioneFileNomeTipoDoc(null);
//		bean.setUnioneFileDescrizione("Risposta completa");
//		bean.getImpostazioniUnioneFile().setNomeModelloDocFirmeGrafiche("COPERTINA_FIRME_GRAFICHE");
//		bean.getImpostazioniUnioneFile().setIdModelloDocFirmeGrafiche("232");
//		bean.getImpostazioniUnioneFile().setNroPagineFirmeGrafiche("3");
//		bean.getImpostazioniUnioneFile().setTipoModelloDocFirmeGrafiche("odt_con_freemarkers");
		
//		bean.setFlgFirmaFile(true);
//		InfoFirmaGraficaBean infoFirmaGrafica1 = new InfoFirmaGraficaBean();
//		infoFirmaGrafica1.setRuolo("Presidente");
//		infoFirmaGrafica1.setTesto("Firmato dal presidente $intestatarioCertificato$ in data $dataCorrente$");
//		infoFirmaGrafica1.setAreaVerticale(2);
//		infoFirmaGrafica1.setAreaOrizzontale(1);
//		infoFirmaGrafica1.setNroPagina(1);
//		infoFirmaGrafica1.setTitolareFirma("titolareFirma1");
//		infoFirmaGrafica1.setCodiceActivityFirma("codiceActivityFirma1");
//		infoFirmaGrafica1.setIdUtenteLavoroFirma("0000011");
//		infoFirmaGrafica1.setIdUtenteLoggatoFirma("0000021");
//		InfoFirmaGraficaBean infoFirmaGrafica2 = new InfoFirmaGraficaBean();
//		infoFirmaGrafica2.setRuolo("Direttore");
//		infoFirmaGrafica2.setTesto("Firmato dal direttore $intestatarioCertificato$ in data $dataCorrente$");
//		infoFirmaGrafica2.setAreaVerticale(2);
//		infoFirmaGrafica2.setAreaOrizzontale(1);
//		infoFirmaGrafica2.setNroPagina(2);
//		infoFirmaGrafica2.setTitolareFirma("titolareFirma2");
//		infoFirmaGrafica2.setCodiceActivityFirma("codiceActivityFirma2");
//		infoFirmaGrafica2.setIdUtenteLavoroFirma("0000012");
//		infoFirmaGrafica2.setIdUtenteLoggatoFirma("0000022");
//		InfoFirmaGraficaBean infoFirmaGrafica3 = new InfoFirmaGraficaBean();
//		infoFirmaGrafica3.setRuolo("Responsabile");
//		infoFirmaGrafica3.setTesto("Firmato dal responsabile $intestatarioCertificato$ in data $dataCorrente$");
//		infoFirmaGrafica3.setAreaVerticale(2);
//		infoFirmaGrafica3.setAreaOrizzontale(3);
//		infoFirmaGrafica3.setNroPagina(2);
//		infoFirmaGrafica3.setTitolareFirma("titolareFirma3");
//		infoFirmaGrafica3.setCodiceActivityFirma("codiceActivityFirma3");
//		infoFirmaGrafica3.setIdUtenteLavoroFirma("0000013");
//		infoFirmaGrafica3.setIdUtenteLoggatoFirma("0000023");
//		InfoFirmaGraficaBean infoFirmaGrafica4 = new InfoFirmaGraficaBean();
//		infoFirmaGrafica4.setRuolo("Contabile");
//		infoFirmaGrafica4.setTesto("Firmato dal contabile $intestatarioCertificato$ in data $dataCorrente$");
//		infoFirmaGrafica4.setAreaVerticale(2);
//		infoFirmaGrafica4.setAreaOrizzontale(1);
//		infoFirmaGrafica4.setNroPagina(3);
//		infoFirmaGrafica4.setTitolareFirma("titolareFirma4");
//		infoFirmaGrafica4.setCodiceActivityFirma("codiceActivityFirma4");
//		infoFirmaGrafica4.setIdUtenteLavoroFirma("0000014");
//		infoFirmaGrafica4.setIdUtenteLoggatoFirma("0000024");
//		List<InfoFirmaGraficaBean> listaInfoFirmaGrafica = new ArrayList<InfoFirmaGraficaBean>();
//		listaInfoFirmaGrafica.add(infoFirmaGrafica1);
//		listaInfoFirmaGrafica.add(infoFirmaGrafica2);
//		listaInfoFirmaGrafica.add(infoFirmaGrafica3);
//		listaInfoFirmaGrafica.add(infoFirmaGrafica4);
//		bean.setInfoFirmaGrafica(listaInfoFirmaGrafica);
//		for (InfoFirmaGraficaBean lInfoFirmaGraficaBean : bean.getInfoFirmaGrafica()) {
//			lInfoFirmaGraficaBean.setNroPagineFirmeGrafiche(3);
//		}
		
//		if (bean.getParametriTipoAtto().getAttributiCustomCablati() != null) {
//			for (AttributiCustomCablatiAttoXmlBean attributo : bean.getParametriTipoAtto().getAttributiCustomCablati()) {
//				if ("DISPOSITIVO_ATTO".equalsIgnoreCase(attributo.getAttrName())) {
//					attributo.setFlgIgnoraGestioneOmissis("1");
//				} 
//			}
//		}
	}

}