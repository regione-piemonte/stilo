/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailLoaddettemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.CalcolaImpronteService;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.AttachmentBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioMailBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.ItemLavorazioneMailBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.SimpleBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.AzioneDaFareBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioPostaElettronicaAllegato;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioPostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioPostaElettronicaDestinatario;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DownloadEmlFileBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaRetriewAttachBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.VisualizzaEmlBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.XmlDatiPostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DownloadDocsZipBean;
import it.eng.aurigamailbusiness.bean.EmailAttachsBean;
import it.eng.aurigamailbusiness.bean.EmailInfoBean;
import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.bean.MailAttachmentsInfoBean;
import it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean;
import it.eng.client.AurigaMailService;
import it.eng.client.DmpkIntMgoEmailLoaddettemail;
import it.eng.client.MailProcessorService;
import it.eng.client.RecuperoFile;
import it.eng.config.AurigaMailBusinessClientConfig;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.FileUtil;
import it.eng.utility.formati.FormatiUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.ui.html.HtmlNormalizeUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFormDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlUtilityDeserializer;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

@Datasource(id = "AurigaGetDettaglioPostaElettronicaDataSource")
public class AurigaGetDettaglioPostaElettronicaDataSource extends AbstractFormDataSource<DettaglioPostaElettronicaBean> {

	private static Logger mLogger = Logger.getLogger(AurigaGetDettaglioPostaElettronicaDataSource.class);

	@Override
	public PaginatorBean<DettaglioPostaElettronicaBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		PaginatorBean<DettaglioPostaElettronicaBean> result = new PaginatorBean<DettaglioPostaElettronicaBean>();
		result.setStartRow(0);
		result.setEndRow(1);
		List<DettaglioPostaElettronicaBean> lista = new ArrayList<DettaglioPostaElettronicaBean>();
		result.setData(lista);
		result.setTotalRows(1);
		return result;
	}

	@Override
	public DettaglioPostaElettronicaBean get(DettaglioPostaElettronicaBean bean) throws Exception {

		DettaglioPostaElettronicaBean result = new DettaglioPostaElettronicaBean();
		result.setIndexTab(bean.getIndexTab());

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkIntMgoEmailLoaddettemailBean input = new DmpkIntMgoEmailLoaddettemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdemailin(bean.getIdEmail());
		input.setIdemailprecin(bean.getIdEmailPrecIn());
		input.setCinodoscrivaniain(getExtraparams().get("idNode"));

		DmpkIntMgoEmailLoaddettemail loaddettemailBean = new DmpkIntMgoEmailLoaddettemail();
		StoreResultBean<DmpkIntMgoEmailLoaddettemailBean> output = loaddettemailBean.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		// Leggo AliasAccountMittente
		if (StringUtils.isNotBlank(output.getResultBean().getXmldatiemailout())) {
			XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();

			XmlDatiPostaElettronicaBean lXmlDatiPostaElettronicaBean = lXmlUtility.unbindXml(output.getResultBean().getXmldatiemailout(),
					XmlDatiPostaElettronicaBean.class);
			result.setAliasAccountMittente(lXmlDatiPostaElettronicaBean.getAliasAccountMittente());

			result.setIdEmail(bean.getIdEmail());
			//TEmailMgoBean lTEmailMgo = AurigaMailService.getDaoTEmailMgo().get(getLocale(), bean.getIdEmail());

			InvioMailBean invioMailBean = new InvioMailBean();

			result.setId(lXmlDatiPostaElettronicaBean.getId());
			result.setMessageId(lXmlDatiPostaElettronicaBean.getMessageId());
			result.setFlgIO(lXmlDatiPostaElettronicaBean.getFlgIO());
			result.setIconaMicroCategoria(
					lXmlDatiPostaElettronicaBean.getIconaMicroCategoria() != null ? lXmlDatiPostaElettronicaBean.getIconaMicroCategoria() : "");
			result.setTitoloGUIDettaglioEmail(lXmlDatiPostaElettronicaBean.getTitoloGUIDettaglioEmail());
			result.setFlgRicNotSenzaPredecessore(lXmlDatiPostaElettronicaBean.getFlgRicNotSenzaPredecessore());
			result.setMessageId(lXmlDatiPostaElettronicaBean.getMessageId());
			result.setCasellaId(lXmlDatiPostaElettronicaBean.getCasellaId());
			result.setCasellaAccount(lXmlDatiPostaElettronicaBean.getCasellaAccount());
			result.setCasellaIsPEC(lXmlDatiPostaElettronicaBean.getCasellaIsPEC());
			result.setCategoria(lXmlDatiPostaElettronicaBean.getCategoria());
			result.setDimensione(lXmlDatiPostaElettronicaBean.getDimensione());
			result.setUri(lXmlDatiPostaElettronicaBean.getUri());
			result.setFlgSpam(lXmlDatiPostaElettronicaBean.getFlgSpam());
			result.setIconaStatoConsolidamento(lXmlDatiPostaElettronicaBean.getIconaStatoConsolidamento());
			result.setStatoLavorazione(lXmlDatiPostaElettronicaBean.getStatoLavorazione());
			result.setTipo(lXmlDatiPostaElettronicaBean.getTipo());
			result.setSottotipo(lXmlDatiPostaElettronicaBean.getSottotipo());
			result.setFlgPEC(lXmlDatiPostaElettronicaBean.getFlgPEC());
			result.setFlgInteroperabile(lXmlDatiPostaElettronicaBean.getFlgInteroperabile());
			result.setNroAllegati(lXmlDatiPostaElettronicaBean.getNroAllegati());
			result.setNroAllegatiFirmati(lXmlDatiPostaElettronicaBean.getNroAllegatiFirmati());
			result.setFlgEmailFirmata(lXmlDatiPostaElettronicaBean.getFlgEmailFirmata());
			result.setFlgNoAssociazioneAutomatica(lXmlDatiPostaElettronicaBean.getFlgNoAssociazioneAutomatica());
			result.setAccountMittente(lXmlDatiPostaElettronicaBean.getAccountMittente());
			result.setSubject(lXmlDatiPostaElettronicaBean.getSubject());
			result.setProgrDownloadStampa(lXmlDatiPostaElettronicaBean.getProgMsgDownloadStampa());
			result.setTsInsRegistrazione(lXmlDatiPostaElettronicaBean.getTsInsRegistrazione());
			result.setMotivoEccezione(lXmlDatiPostaElettronicaBean.getMotivoEccezione());
			result.setAvvertimenti(lXmlDatiPostaElettronicaBean.getAvvertimenti());

			result.setFlgURIRicevuta(lXmlDatiPostaElettronicaBean.getFlgURIRicevuta());

			MailProcessorService lMailProcessorService = new MailProcessorService();
			EmailInfoBean lEmailInfoBean = null;

			lEmailInfoBean = lMailProcessorService.getbodyhtmlbyidemail(getLocale(), bean.getIdEmail());
			if (lEmailInfoBean.getMessaggio() != null && !"".equals(lEmailInfoBean.getMessaggio())) {
				//11-02-19: modificato metodo per recupere il testo dall'html. In caso di miglioramento evidente, spostare il metodo dentro la classe HtmlNormalizedUtil di Core-module.
				result.setEscapedHtmlBody(recuperaTestoSempliceDaEmail(lEmailInfoBean.getMessaggio()));
				result.setBody(lEmailInfoBean.getMessaggio());
				invioMailBean.setTextHtml("html");
			} else {
				lEmailInfoBean = lMailProcessorService.getbodytextbyidemail(getLocale(), bean.getIdEmail());
				result.setEscapedHtmlBody(HtmlNormalizeUtil.getPlainXmlWithCarriageReturn(lEmailInfoBean.getMessaggio()));
				result.setBody(lEmailInfoBean.getMessaggio());
				invioMailBean.setTextHtml("text");
			}

			result.setSiglaRegistroRegMitt(lXmlDatiPostaElettronicaBean.getSiglaRegistroRegMitt());
			result.setNumRegMitt(lXmlDatiPostaElettronicaBean.getNumRegMitt());
			result.setAnnoRegMitt(lXmlDatiPostaElettronicaBean.getAnnoRegMitt());
			result.setEnteRegMitt(lXmlDatiPostaElettronicaBean.getEnteRegMitt());
			result.setDtRegMitt(lXmlDatiPostaElettronicaBean.getDtRegMitt());
			result.setOggettoRegMitt(lXmlDatiPostaElettronicaBean.getOggettoRegMitt());
			result.setTsLock(lXmlDatiPostaElettronicaBean.getTsLock());
			result.setDesUtenteLock(lXmlDatiPostaElettronicaBean.getDesUtenteLock());
			result.setDesOperLock(lXmlDatiPostaElettronicaBean.getDesOperLock());
			result.setFlgRichConferma(lXmlDatiPostaElettronicaBean.getFlgRichConferma());
			result.setFlgRichConfermaLettura(lXmlDatiPostaElettronicaBean.getFlgRichConfermaLettura());
			result.setTsUltimaAssegnazione(lXmlDatiPostaElettronicaBean.getTsUltimaAssegnazione());
			result.setDesUOAssegnataria(lXmlDatiPostaElettronicaBean.getDesUOAssegnataria());
			result.setDesUtenteAssegnatario(lXmlDatiPostaElettronicaBean.getDesUtenteAssegnatario());
			result.setMsgUltimaAssegnazione(lXmlDatiPostaElettronicaBean.getMsgUltimaAssegnazione());
			result.setFlgInviataRisposta(lXmlDatiPostaElettronicaBean.getFlgInviataRisposta());
			result.setFlgInoltrata(lXmlDatiPostaElettronicaBean.getFlgInoltrata());
			result.setStatoProtocollazione(lXmlDatiPostaElettronicaBean.getStatoProtocollazione());
			result.setIdRecDizContrassegno(lXmlDatiPostaElettronicaBean.getIdRecDizContrassegno());
			result.setContrassegno(lXmlDatiPostaElettronicaBean.getContrassegno());
			result.setFlgRicevutaEccezioneInterop(lXmlDatiPostaElettronicaBean.getFlgRicevutaEccezioneInterop());
			result.setFlgRicevutaConfermaInterop(lXmlDatiPostaElettronicaBean.getFlgRicevutaConfermaInterop());
			result.setFlgRicevutoAggiornamentoInterop(lXmlDatiPostaElettronicaBean.getFlgRicevutoAggiornamentoInterop());
			result.setFlgRicevutoAnnRegInterop(lXmlDatiPostaElettronicaBean.getFlgRicevutoAnnRegInterop());
			result.setTsRicezione(lXmlDatiPostaElettronicaBean.getTsRicezione());
			result.setTsInvioCertificato(lXmlDatiPostaElettronicaBean.getTsInvioCertificato());
			if (result.getTsInvioCertificato() != null) {
				result.setTsInvio(lXmlDatiPostaElettronicaBean.getTsInvioCertificato());
			} else {
				result.setTsInvio(lXmlDatiPostaElettronicaBean.getTsInvio());
			}
			result.setLivPriorita(lXmlDatiPostaElettronicaBean.getLivPriorita());
			result.setFlgRicevutaCBS(lXmlDatiPostaElettronicaBean.getFlgRicevutaCBS());

			String destinatariPrincipali = lXmlDatiPostaElettronicaBean.getDestinatariPrincipali();
			if (StringUtils.isNotBlank(destinatariPrincipali)) {
				destinatariPrincipali = destinatariPrincipali.replace(",", ";");
			}
			result.setDestinatariPrincipali(destinatariPrincipali);
			result.setListaDestinatariPrincipali(lXmlDatiPostaElettronicaBean.getListaDestinatariPrincipali());
			
			if(lXmlDatiPostaElettronicaBean.getListaDestinatariPrincipali()!=null && !lXmlDatiPostaElettronicaBean.getListaDestinatariPrincipali().isEmpty()) {
				result.setNumeroTotaleDestinatariPrincipali(String.valueOf(lXmlDatiPostaElettronicaBean.getListaDestinatariPrincipali().size()));
			}

			String destinatariCC = lXmlDatiPostaElettronicaBean.getDestinatariCC();
			if (StringUtils.isNotBlank(destinatariCC)) {
				destinatariCC = destinatariCC.replace(",", ";");
			}
			result.setDestinatariCC(destinatariCC);
			result.setListaDestinatariCC(lXmlDatiPostaElettronicaBean.getListaDestinatariCC());
			
			if(lXmlDatiPostaElettronicaBean.getListaDestinatariCC()!=null && !lXmlDatiPostaElettronicaBean.getListaDestinatariCC().isEmpty()) {
				result.setNumeroTotaleDestinatariCopia(String.valueOf(lXmlDatiPostaElettronicaBean.getListaDestinatariCC().size()));
			}

			result.setListaDestinatariCCN(lXmlDatiPostaElettronicaBean.getListaDestinatariCCN());
			
			if(lXmlDatiPostaElettronicaBean.getListaDestinatariCCN()!=null && !lXmlDatiPostaElettronicaBean.getListaDestinatariCCN().isEmpty()) {
				result.setNumeroTotaleDestinatariCCNAttori(String.valueOf(lXmlDatiPostaElettronicaBean.getListaDestinatariCCN().size()));
			}

			result.setEmailPredecessoreIdEmail(lXmlDatiPostaElettronicaBean.getEmailPredecessoreIdEmail());
			result.setIdEmailInoltrate(lXmlDatiPostaElettronicaBean.getIdEmailInoltrate());
			result.setEmailPredecessoreMessageId(lXmlDatiPostaElettronicaBean.getEmailPredecessoreMessageId());
			result.setEmailPredecessoreFlgIO(lXmlDatiPostaElettronicaBean.getEmailPredecessoreFlgIO());
			result.setEmailPredecessoreIconaMicroCategoria(lXmlDatiPostaElettronicaBean.getEmailPredecessoreIconaMicroCategoria() != null
					? lXmlDatiPostaElettronicaBean.getEmailPredecessoreIconaMicroCategoria() : "");
			result.setEmailPredecessoreCategoria(lXmlDatiPostaElettronicaBean.getEmailPredecessoreCategoria());
			result.setEmailPredecessoreTsInvio(lXmlDatiPostaElettronicaBean.getEmailPredecessoreTsInvio());
			result.setEmailPredecessoreTsRicezione(lXmlDatiPostaElettronicaBean.getEmailPredecessoreTsRicezione());
			result.setEmailPredecessoreCasellaAccount(lXmlDatiPostaElettronicaBean.getEmailPredecessoreCasellaAccount());
			result.setEmailPredecessoreSubject(lXmlDatiPostaElettronicaBean.getEmailPredecessoreSubject());
			result.setEmailPredecessoreAccountMittente(lXmlDatiPostaElettronicaBean.getEmailPredecessoreAccountMittente());
			result.setEmailPredecessoreTipo(lXmlDatiPostaElettronicaBean.getEmailPredecessoreTipo());
			result.setEmailPredecessoreSottotipo(lXmlDatiPostaElettronicaBean.getEmailPredecessoreSottotipo());
			result.setEmailPredecessoreFlgPEC(lXmlDatiPostaElettronicaBean.getEmailPredecessoreFlgPEC());
			result.setEmailPredecessoreFlgInteroperabile(lXmlDatiPostaElettronicaBean.getEmailPredecessoreFlgInteroperabile());
			result.setEmailPredecessoreDestinatariPrincipali(lXmlDatiPostaElettronicaBean.getEmailPredecessoreDestinatariPrincipali());
			result.setEmailPredecessoreDestinatariCC(lXmlDatiPostaElettronicaBean.getEmailPredecessoreDestinatariCC());
			result.setListaEmailPredecessoreEstremiDocTrasmessi(lXmlDatiPostaElettronicaBean.getListaEmailPredecessoreEstremiDocTrasmessi());
			result.setEmailPredecessoreProgMsgDStampa(lXmlDatiPostaElettronicaBean.getEmailPredecessoreProgMsgDStampa());
			result.setEmailPredecessoreId(lXmlDatiPostaElettronicaBean.getEmailPredecessoreId());
			result.setEmailPredecessoreTsIns(lXmlDatiPostaElettronicaBean.getEmailPredecessoreTsIns());

			if (lXmlDatiPostaElettronicaBean.getListaEstremiDocDerivati() != null && lXmlDatiPostaElettronicaBean.getListaEstremiDocDerivati().size() > 0) {
				String estremiDocDerivati = "";
				HashMap<String, String> mappaEstremiDocDerivati = new HashMap<String, String>();
				for (int i = 0; i < lXmlDatiPostaElettronicaBean.getListaEstremiDocDerivati().size(); i++) {
					if (i == 0) {
						estremiDocDerivati += lXmlDatiPostaElettronicaBean.getListaEstremiDocDerivati().get(i).getEstremiProt();
					} else {
						estremiDocDerivati += ", " + lXmlDatiPostaElettronicaBean.getListaEstremiDocDerivati().get(i).getEstremiProt();
					}
					mappaEstremiDocDerivati.put(lXmlDatiPostaElettronicaBean.getListaEstremiDocDerivati().get(i).getIdUD(),
							lXmlDatiPostaElettronicaBean.getListaEstremiDocDerivati().get(i).getEstremiProt());
				}
				result.setEstremiDocDerivati(estremiDocDerivati);
				result.setMappaEstremiDocDerivati(mappaEstremiDocDerivati);
			}
			if (lXmlDatiPostaElettronicaBean.getListaEstremiDocTrasmessi() != null && lXmlDatiPostaElettronicaBean.getListaEstremiDocTrasmessi().size() > 0) {
				String estremiDocTrasmessi = "";
				HashMap<String, String> mappaEstremiDocTrasmessi = new HashMap<String, String>();
				for (int j = 0; j < lXmlDatiPostaElettronicaBean.getListaEstremiDocTrasmessi().size(); j++) {
					if (j == 0) {
						estremiDocTrasmessi += lXmlDatiPostaElettronicaBean.getListaEstremiDocTrasmessi().get(j).getEstremiProt();
					} else {
						estremiDocTrasmessi += ", " + lXmlDatiPostaElettronicaBean.getListaEstremiDocTrasmessi().get(j).getEstremiProt();
					}
					mappaEstremiDocTrasmessi.put(lXmlDatiPostaElettronicaBean.getListaEstremiDocTrasmessi().get(j).getIdUD(),
							lXmlDatiPostaElettronicaBean.getListaEstremiDocTrasmessi().get(j).getEstremiProt());
				}
				result.setEstremiDocTrasmessi(estremiDocTrasmessi);
				result.setMappaEstremiDocTrasmessi(mappaEstremiDocTrasmessi);
			}
//			mLogger.debug("Stato consolidamento globale " + lTEmailMgo.getStatoConsolidamento());

			boolean verificaFirmaFile = false;
			if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_CTRL_AUTO_FIRMA_ATTACH_MAIL")) {
				if (lXmlDatiPostaElettronicaBean.getListaAllegati() != null && lXmlDatiPostaElettronicaBean.getListaAllegati().size() > 0) {
					for (DettaglioPostaElettronicaAllegato attachItem : lXmlDatiPostaElettronicaBean.getListaAllegati()) {
						if (attachItem.getFlgFirmato() != null && attachItem.getFlgFirmato().equalsIgnoreCase("1") && attachItem.getFlgFirmaValida() != null
								&& attachItem.getFlgFirmaValida().equalsIgnoreCase("1")) {
							Long currentSize = StringUtils.isNotBlank(attachItem.getBytes()) ? new Long(attachItem.getBytes()) : new Long(0);
							Long sizeDB = StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(getSession(), "MAX_SIZE_ATTCH_CTRL_FIRMA"))
									? new Long(ParametriDBUtil.getParametroDB(getSession(), "MAX_SIZE_ATTCH_CTRL_FIRMA")) : 0;
							if(sizeDB == 0 || currentSize <= sizeDB) {
								verificaFirmaFile = true;
								break;
							}
						}
					}
				}
			}

			if (!verificaFirmaFile && lXmlDatiPostaElettronicaBean.getListaAllegati() != null && !lXmlDatiPostaElettronicaBean.getListaAllegati().isEmpty()) {

				int countAllegatoProtBean = 0;
				List<DettaglioPostaElettronicaAllegato> lListAllegati = new ArrayList<DettaglioPostaElettronicaAllegato>();
				for (DettaglioPostaElettronicaAllegato attachItem : lXmlDatiPostaElettronicaBean.getListaAllegati()) {

					DettaglioPostaElettronicaAllegato lAllegatoProtocolloBean = new DettaglioPostaElettronicaAllegato();
					lAllegatoProtocolloBean.setNomeFile(attachItem.getNomeFile());
					lAllegatoProtocolloBean.setDisplayFileName(attachItem.getDisplayFileName());

					MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
					lMimeTypeFirmaBean.setFirmato(attachItem.getFlgFirmato() != null && attachItem.getFlgFirmato().equalsIgnoreCase("1") ? true : false);
					lMimeTypeFirmaBean
							.setFirmaValida(attachItem.getFlgFirmaValida() != null && attachItem.getFlgFirmaValida().equalsIgnoreCase("1") ? true : false);
					lMimeTypeFirmaBean.setCorrectFileName(attachItem.getNomeFile());
					lMimeTypeFirmaBean.setMimetype(attachItem.getMimetype());
					lMimeTypeFirmaBean.setConvertibile(FormatiUtil.isConvertibile(getSession(), attachItem.getMimetype()));
					if (lMimeTypeFirmaBean.isFirmato()) {
						lMimeTypeFirmaBean.setTipoFirma(
								attachItem.getNomeFile().toUpperCase().endsWith("P7M") || attachItem.getNomeFile().toUpperCase().endsWith("TSD") ? "CAdES_BES"
										: "PDF");
					}

					lAllegatoProtocolloBean.setUri("_noUri");
					Integer dimensione = attachItem.getBytes() != null ? Integer.valueOf(attachItem.getBytes()) : 0;
					if(dimensione > 0) {
						lAllegatoProtocolloBean.setBytes(attachItem.getBytes());
					} else {
						EmailAttachsBean lEmailAttachsBean = lMailProcessorService.getattachmentsbyidemail(getLocale(), bean.getIdEmail());
						if (lEmailAttachsBean.getMailAttachments() != null && !lEmailAttachsBean.getMailAttachments().isEmpty()) {
							for (MailAttachmentsInfoBean info : lEmailAttachsBean.getMailAttachments()) {
								String fileName = Normalizer.normalize(info.getFilename(), Normalizer.Form.NFC);
								if(fileName.equalsIgnoreCase(attachItem.getDisplayFileName())) {
									lAllegatoProtocolloBean.setBytes(info.getSize().toString());
								}
							}
						}
					}
					lAllegatoProtocolloBean.setEstremiProt(lXmlDatiPostaElettronicaBean.getListaAllegati().get(countAllegatoProtBean).getEstremiProt());
					lAllegatoProtocolloBean.setIdUD(lXmlDatiPostaElettronicaBean.getListaAllegati().get(countAllegatoProtBean).getIdUD());
					lAllegatoProtocolloBean.setImpronta(attachItem.getImpronta());
					lAllegatoProtocolloBean.setEncoding(attachItem.getEncoding());
					lAllegatoProtocolloBean.setAlgoritmo(attachItem.getAlgoritmo());
					lAllegatoProtocolloBean.setFlgFirmato(String.valueOf(attachItem.getFlgFirmato()));
					lAllegatoProtocolloBean.setPdfConCommenti(attachItem.getPdfConCommenti());
					lAllegatoProtocolloBean.setPdfEditabile(attachItem.getPdfEditabile());
					lAllegatoProtocolloBean.setFlgFirmaValida(String.valueOf(attachItem.getFlgFirmaValida()));
					lAllegatoProtocolloBean
							.setFlgConvertibileInPdf(lXmlDatiPostaElettronicaBean.getListaAllegati().get(countAllegatoProtBean).getFlgConvertibileInPdf());
					lAllegatoProtocolloBean.setMimetype(lXmlDatiPostaElettronicaBean.getListaAllegati().get(countAllegatoProtBean).getMimetype());
					lAllegatoProtocolloBean.setInfoFile(lMimeTypeFirmaBean);
					lAllegatoProtocolloBean.setNumeroProgrAllegato(attachItem.getNumeroProgrAllegato());
					lListAllegati.add(lAllegatoProtocolloBean);

					countAllegatoProtBean++;

				}
				result.setListaAllegati(lListAllegati);
			} else {
				
				List<DettaglioPostaElettronicaAllegato> lListAllegati = new ArrayList<DettaglioPostaElettronicaAllegato>();
				EmailAttachsBean lEmailAttachsBean = lMailProcessorService.getattachmentsbyidemail(getLocale(), bean.getIdEmail());
				
				if(isPregresso(lXmlDatiPostaElettronicaBean,lEmailAttachsBean)) {
					result.setMessagePregresso("La mail contiene allegati: per visualizzarli devi scaricare la mail");
				} else {
				
					int count = 0;
					Map<String, String> mapFileAllegati = new HashMap<String, String>();
					if (lEmailAttachsBean.getMailAttachments() != null && !lEmailAttachsBean.getMailAttachments().isEmpty()) {
						for (MailAttachmentsInfoBean info : lEmailAttachsBean.getMailAttachments()) {
	
							String uriAttach = StorageImplementation.getStorage().store(lEmailAttachsBean.getFiles().get(count));
							String fileName = Normalizer.normalize(info.getFilename(), Normalizer.Form.NFC);
	
							/**
							 * PREGRESSO EGR
							 */
							long currentFileSize;
							if(info.getSize() != null && info.getSize().intValue() > 0) {
								currentFileSize = info.getSize();
							} else {
								currentFileSize = ricalcoloDimensioneAllegato(uriAttach);
							}
							
							String key = fileName.concat(";").concat(String.valueOf(currentFileSize));
							mapFileAllegati.put(key, uriAttach);
	
							count++;
						}
	
						Map<String, PostaElettronicaRetriewAttachBean> mapFiles = recuperaInfoAllegatoWithFOP(mapFileAllegati);
						Iterator<String> ite = mapFiles.keySet().iterator();
						while (ite.hasNext()) {
							String keyMapFiles = ite.next();
							
							PostaElettronicaRetriewAttachBean valueMapFiles = mapFiles.get(keyMapFiles);
	
							DettaglioPostaElettronicaAllegato lDettaglioPostaElettronicaAllegato = setValuesToAllegato(keyMapFiles, valueMapFiles);
							for(DettaglioPostaElettronicaAllegato item : lXmlDatiPostaElettronicaBean.getListaAllegati()) {							
								if(lDettaglioPostaElettronicaAllegato.getImpronta().equalsIgnoreCase(item.getImpronta())) {
									lDettaglioPostaElettronicaAllegato.setEstremiProt(item.getEstremiProt());
									lDettaglioPostaElettronicaAllegato.setIdUD(item.getIdUD());
									lListAllegati.add(lDettaglioPostaElettronicaAllegato);						
								}
							}
						}
						result.setListaAllegati(lListAllegati);
					}
				}
				
			}
			result.setListaEmailSuccessiveCollegate(lXmlDatiPostaElettronicaBean.getListaEmailSuccessiveCollegate());
			result.setAbilitaStampaFile(lXmlDatiPostaElettronicaBean.getAbilitaStampaFile());

			/* Gestione Paginazione con Briciole di Pane */
			result.setListaEmailPrecedenti(lXmlDatiPostaElettronicaBean.getListaEmailPrecedentiCollegate());

			if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_MAIL_REPLY")) {
				result.setAbilitaRispondi(lXmlDatiPostaElettronicaBean.getAbilitaRispondi());
				result.setAbilitaRispondiATutti(lXmlDatiPostaElettronicaBean.getAbilitaRispondiATutti());
			} else {
				result.setAbilitaRispondi("false");
				result.setAbilitaRispondiATutti("false");
			}
			result.setAbilitaInoltraEmail(lXmlDatiPostaElettronicaBean.getAbilitaInoltraEmail());
			result.setAbilitaInoltraContenuti(lXmlDatiPostaElettronicaBean.getAbilitaInoltraContenuti());
			result.setAbilitaAssegna(lXmlDatiPostaElettronicaBean.getAbilitaAssegna());
			result.setAbilitaAssociaProtocollo(lXmlDatiPostaElettronicaBean.getAbilitaAssociaProtocollo());
			result.setAbilitaArchivia(lXmlDatiPostaElettronicaBean.getAbilitaArchivia());
			result.setAbilitaRiapri(lXmlDatiPostaElettronicaBean.getAbilitaRiapri());
			result.setAbilitaProtocolla(lXmlDatiPostaElettronicaBean.getAbilitaProtocolla());
			result.setAbilitaRepertoria(lXmlDatiPostaElettronicaBean.getAbilitaRepertoria());
			result.setAbilitaScaricaEmail(lXmlDatiPostaElettronicaBean.getAbilitaScaricaEmail());
			result.setAbilitaScaricaEmailSenzaBustaTrasporto(lXmlDatiPostaElettronicaBean.getAbilitaScaricaEmailSenzaBustaTrasporto());
			// set abilita notifiche di { Conferma - Eccezione - Aggiornamento - Annullamento }
			result.setAbilitaNotifInteropConferma(lXmlDatiPostaElettronicaBean.getAbilitaNotifInteropConferma());
			result.setAbilitaNotifInteropEccezione(lXmlDatiPostaElettronicaBean.getAbilitaNotifInteropEccezione());
			result.setAbilitaNotifInteropAggiornamento(lXmlDatiPostaElettronicaBean.getAbilitaNotifInteropAggiornamento());
			result.setAbilitaNotifInteropAnnullamento(lXmlDatiPostaElettronicaBean.getAbilitaNotifInteropAnnullamento());
			result.setAbilitaInvia(lXmlDatiPostaElettronicaBean.getAbilitaInvia());
			result.setAbilitaInvioCopia(lXmlDatiPostaElettronicaBean.getAbilitaInvioCopia());
			result.setAbilitaAzioneDaFare(lXmlDatiPostaElettronicaBean.getAbilitaAzioneDaFare());
			result.setAbilitaAssociaAInvio(lXmlDatiPostaElettronicaBean.getAbilitaAssociaAInvio());
			result.setAbilitaAnnullamentoInvio(lXmlDatiPostaElettronicaBean.getAbilitaAnnullamentoInvio());
			result.setAbilitaRegIstanzaAutotutela(lXmlDatiPostaElettronicaBean.getAbilitaRegIstanzaAutotutela());
			result.setAbilitaRegIstanzaCED(lXmlDatiPostaElettronicaBean.getAbilitaRegIstanzaCED());
			result.setAbilitaProtocollaAccessoAttiSUE(lXmlDatiPostaElettronicaBean.getAbilitaProtocollaAccessoAttiSUE());

			AzioneDaFareBean azioneDaFareBean = new AzioneDaFareBean();
			azioneDaFareBean.setCodAzioneDaFare(lXmlDatiPostaElettronicaBean.getCodAzioneDaFare());
			azioneDaFareBean.setAzioneDaFare(lXmlDatiPostaElettronicaBean.getAzioneDaFare());
			azioneDaFareBean.setDettaglioAzioneDaFare(lXmlDatiPostaElettronicaBean.getDettaglioAzioneDaFare());

			result.setAzioneDaFareBean(azioneDaFareBean);
			result.setDescrizioneAzioneDaFare(lXmlDatiPostaElettronicaBean.getAzioneDaFare()); // E' settato pure in AzioneDaFare ma mi serve per gestirlo
																								// direttamente in input
			result.setDettaglioAzioneDaFare(lXmlDatiPostaElettronicaBean.getDettaglioAzioneDaFare());
			result.setStatoInvio(lXmlDatiPostaElettronicaBean.getStatoInvio());
			result.setStatoAccettazione(lXmlDatiPostaElettronicaBean.getStatoAccettazione());
			result.setStatoConsegna(lXmlDatiPostaElettronicaBean.getStatoConsegna());
			result.setIconaStatoInvio(lXmlDatiPostaElettronicaBean.getIconaStatoInvio());
			result.setIconaStatoAccettazione(lXmlDatiPostaElettronicaBean.getIconaStatoAccettazione());
			result.setIconaStatoConsegna(lXmlDatiPostaElettronicaBean.getIconaStatoConsegna());
			result.setDataAggStatoLavorazione(lXmlDatiPostaElettronicaBean.getDataAggStatoLavorazione());
			result.setOrarioAggStatoLavorazione(lXmlDatiPostaElettronicaBean.getOrarioAggStatoLavorazione());
			result.setDataLock(lXmlDatiPostaElettronicaBean.getDataUtenteAssegnatario());
			result.setOrarioLock(lXmlDatiPostaElettronicaBean.getOrarioUtenteAssegnatario());
			result.setDataUltimaAssegnazione(lXmlDatiPostaElettronicaBean.getDataUltimaAssegnazione());
			result.setOrarioUltimaAssegnazione(lXmlDatiPostaElettronicaBean.getOrarioUltimaAssegnazione());
			result.setAbilitaPresaInCarico(lXmlDatiPostaElettronicaBean.getAbilitaPresaInCarico());
			result.setAbilitaMessaInCarico(lXmlDatiPostaElettronicaBean.getAbilitaMessaInCarico());
			result.setAbilitaRilascio(lXmlDatiPostaElettronicaBean.getAbilitaRilascio());
			result.setMsgErrMancataAccettazione(lXmlDatiPostaElettronicaBean.getMsgErrMancataAccettazione());
			result.setMsgErrMancataConsegna(lXmlDatiPostaElettronicaBean.getMsgErrMancataConsegna());
			result.setMsgErrRicevutaPEC(lXmlDatiPostaElettronicaBean.getMsgErrRicevutaPEC());

			List<ItemLavorazioneMailBean> lListaItemLavorazione = lXmlDatiPostaElettronicaBean.getListaItemLavorazione();
			if (lListaItemLavorazione != null && lListaItemLavorazione.size() > 0) {
				for (ItemLavorazioneMailBean item : lListaItemLavorazione) {

					if (item.getItemLavTipo() == null || item.getItemLavTipo().equals("F")) {

						File lItemLavFile = null;
						FileExtractedIn lItemLavFileExtractedIn = new FileExtractedIn();
						lItemLavFileExtractedIn.setUri(item.getItemLavUriFile());
						FileExtractedOut lItemLavOut = new RecuperoFile().extractfile(UserUtil.getLocale(getSession()), loginBean, lItemLavFileExtractedIn);
						lItemLavFile = lItemLavOut.getExtracted();

						MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
						InfoFileUtility lFileUtility = new InfoFileUtility();
						lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lItemLavFile.toURI().toString(), item.getItemLavNomeFile(), false, null);
						item.setItemLavInfoFile(lMimeTypeFirmaBean);
					}
				}
			}
			result.setListaItemInLavorazione(lListaItemLavorazione);
			result.setAbilitaSalvaBozza(lXmlDatiPostaElettronicaBean.getAbilitaSalvaBozza());
			result.setAbilitaSalvaItemLav(lXmlDatiPostaElettronicaBean.getAbilitaSalvaItemLav());
			/**
			 * Caricamento Dettaglio di una BOZZA
			 */
			if (isBozza(result.getId())) {
				populateDatiBozza(result, invioMailBean, lXmlDatiPostaElettronicaBean);
				result.setInvioMailBean(invioMailBean);
			}

			/**
			 * Salvataggio dati per gestione chiusura mail risposte o inoltare
			 */
			result.setFlgMailPredecessoreStatoLavAperta(lXmlDatiPostaElettronicaBean.getFlgMailPredecessoreStatoLavAperta());
			result.setFlgMailPredecessoreConAzioneDaFare(lXmlDatiPostaElettronicaBean.getFlgMailPredecessoreConAzioneDaFare());
			result.setRuoloVsPredecessore(lXmlDatiPostaElettronicaBean.getRuoloVsPredecessore());
			result.setNroMailPredecessore(lXmlDatiPostaElettronicaBean.getNroMailPredecessore());
			result.setMailPredecessoriUnicaAzioneDaFare(lXmlDatiPostaElettronicaBean.getMailPredecessoriUnicaAzioneDaFare());
		}
		return result;
	}

	private void populateDatiBozza(DettaglioPostaElettronicaBean result, InvioMailBean invioMailBean, XmlDatiPostaElettronicaBean lXmlDatiPostaElettronicaBean)
			throws StorageException, Exception {
		invioMailBean.setMittente(lXmlDatiPostaElettronicaBean.getCasellaAccount());
		invioMailBean.setCasellaIsPec(lXmlDatiPostaElettronicaBean.getCasellaIsPEC());
		if (lXmlDatiPostaElettronicaBean.getListaDestinatariPrincipali() != null && !lXmlDatiPostaElettronicaBean.getListaDestinatariPrincipali().isEmpty()) {
			if (lXmlDatiPostaElettronicaBean.getListaDestinatariPrincipali().size() == 1) {
				invioMailBean.setDestinatari(lXmlDatiPostaElettronicaBean.getListaDestinatariPrincipali().get(0).getIndirizzo());
			} else {
				String destP = "";
				for (DettaglioPostaElettronicaDestinatario item : lXmlDatiPostaElettronicaBean.getListaDestinatariPrincipali()) {
					if (item != null && item.getIndirizzo() != null && !"".equals(item.getIndirizzo())) {
						if ("".equals(destP)) {
							destP = destP.concat(item.getIndirizzo());
						} else {
							destP = destP.concat(";").concat(item.getIndirizzo());
						}
					}
				}
				invioMailBean.setDestinatari(destP);
			}
		} else {
			invioMailBean.setDestinatari(lXmlDatiPostaElettronicaBean.getDestinatariPrincipali());
		}
		invioMailBean.setOggetto(lXmlDatiPostaElettronicaBean.getSubject());
		if ("text".equals(invioMailBean.getTextHtml())) {
			invioMailBean.setBodyText(result.getBody());
		} else {
			invioMailBean.setBodyHtml(result.getBody());
		}
		// Destinatari CC
		if (lXmlDatiPostaElettronicaBean.getListaDestinatariCC() != null && !lXmlDatiPostaElettronicaBean.getListaDestinatariCC().isEmpty()) {
			if (lXmlDatiPostaElettronicaBean.getListaDestinatariCC().size() == 1) {
				invioMailBean.setDestinatariCC(lXmlDatiPostaElettronicaBean.getListaDestinatariCC().get(0).getIndirizzo());
			} else {
				String destCC = "";
				for (DettaglioPostaElettronicaDestinatario item : lXmlDatiPostaElettronicaBean.getListaDestinatariCC()) {
					if (item != null && item.getIndirizzo() != null && !"".equals(item.getIndirizzo())) {
						if ("".equals(destCC)) {
							destCC = destCC.concat(item.getIndirizzo());
						} else {
							destCC = destCC.concat(";").concat(item.getIndirizzo());
						}
					}
				}
				invioMailBean.setDestinatariCC(destCC);
			}
		} else {
			invioMailBean.setDestinatariCC(lXmlDatiPostaElettronicaBean.getDestinatariCC());
		}

		// Destinatari CCN
		if (lXmlDatiPostaElettronicaBean.getListaDestinatariCCN() != null && !lXmlDatiPostaElettronicaBean.getListaDestinatariCCN().isEmpty()) {
			if (lXmlDatiPostaElettronicaBean.getListaDestinatariCCN().size() == 1) {
				invioMailBean.setDestinatariCCN(lXmlDatiPostaElettronicaBean.getListaDestinatariCCN().get(0).getIndirizzo());
			} else {
				String destCCN = "";
				for (DettaglioPostaElettronicaDestinatario item : lXmlDatiPostaElettronicaBean.getListaDestinatariCCN()) {
					if (item != null && item.getIndirizzo() != null && !"".equals(item.getIndirizzo())) {
						if ("".equals(destCCN)) {
							destCCN = destCCN.concat(item.getIndirizzo());
						} else {
							destCCN = destCCN.concat(";").concat(item.getIndirizzo());
						}
					}
				}
				invioMailBean.setDestinatariCCN(destCCN);
			}
		}

		invioMailBean.setUriMail(lXmlDatiPostaElettronicaBean.getUri());
		invioMailBean.setDimensioneMail(lXmlDatiPostaElettronicaBean.getDimensione().toString());
		invioMailBean.setIdEmail(result.getIdEmail());
		invioMailBean.setConfermaLettura(lXmlDatiPostaElettronicaBean.getFlgRichConferma() != null
				&& ("true".equals(lXmlDatiPostaElettronicaBean.getFlgRichConferma()) || "1".equals(lXmlDatiPostaElettronicaBean.getFlgRichConferma())) ? true
						: false);
		List<AttachmentBean> attach = new ArrayList<AttachmentBean>();
		if (result.getListaAllegati() != null && result.getListaAllegati().size() > 0) {
//			FileExtractedIn lFileExtractedAllegatoBozza = new FileExtractedIn();
//			lFileExtractedAllegatoBozza.setUri(result.getUri());
//			RecuperoFile lRecuperoAllegatoBozza = new RecuperoFile();
//			FileExtractedOut outAllegatoBozza = lRecuperoAllegatoBozza.extractfile(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
//					lFileExtractedAllegatoBozza);
//			File emlFileBozza = outAllegatoBozza.getExtracted();
			MailProcessorService lMailProcessorServiceBozza = new MailProcessorService();
			EmailAttachsBean lEmailAttachsBeanAllegatoBozza = lMailProcessorServiceBozza.getattachmentsbyidemail(getLocale(), result.getIdEmail());
			int count = 0;
			if (lEmailAttachsBeanAllegatoBozza.getMailAttachments() != null) {
				for (MailAttachmentsInfoBean infoItemAllegatoBozza : lEmailAttachsBeanAllegatoBozza.getMailAttachments()) {

					String uriAttachAllegatoBozza = StorageImplementation.getStorage().store(lEmailAttachsBeanAllegatoBozza.getFiles().get(count));

					InfoFileUtility lFileUtility = new InfoFileUtility();
					RebuildedFile lRebuildedFile = new RebuildedFile();
					lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uriAttachAllegatoBozza));
					MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(),
							infoItemAllegatoBozza.getFilename(), false, null);

					AttachmentBean lAttachmentBean = new AttachmentBean();
					lAttachmentBean.setFileNameAttach(infoItemAllegatoBozza.getFilename());
					lAttachmentBean.setUriAttach(uriAttachAllegatoBozza);

					MimeTypeFirmaBean lMimeTypeFirmaBeanAllegatoBozza = new MimeTypeFirmaBean();
					lMimeTypeFirmaBeanAllegatoBozza.setFirmato(lMimeTypeFirmaBean.isFirmato());
					lMimeTypeFirmaBeanAllegatoBozza.setTipoFirma(lMimeTypeFirmaBean.getTipoFirma());
					lMimeTypeFirmaBeanAllegatoBozza.setInfoFirma(lMimeTypeFirmaBean.getInfoFirma());
					lMimeTypeFirmaBeanAllegatoBozza.setConvertibile(lMimeTypeFirmaBean.isConvertibile());
					lMimeTypeFirmaBeanAllegatoBozza.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lMimeTypeFirmaBeanAllegatoBozza.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lMimeTypeFirmaBeanAllegatoBozza.setBytes(lMimeTypeFirmaBean.getBytes());
					lMimeTypeFirmaBeanAllegatoBozza.setImprontaPdf(lMimeTypeFirmaBean.getImprontaPdf());
					lMimeTypeFirmaBeanAllegatoBozza.setDaScansione(lMimeTypeFirmaBean.isDaScansione());

					lAttachmentBean.setInfoFileAttach(lMimeTypeFirmaBean);

					attach.add(lAttachmentBean);
					count++;
				}
			}
		}
		invioMailBean.setAttach(attach);

		invioMailBean.setInteroperabile(result.getFlgInteroperabile() != null && !"".equals(result.getFlgInteroperabile())
				&& ("true".equals(result.getFlgInteroperabile()) || "1".equals(result.getFlgInteroperabile())) ? true : false);
		invioMailBean.setAliasAccountMittente(result.getAliasAccountMittente());
		invioMailBean.setConfermaLettura(result.getFlgRichConfermaLettura() != null
				&& ("true".equals(result.getFlgRichConfermaLettura()) || "1".equals(result.getFlgRichConfermaLettura())) ? true : false);
	}

	public DettaglioPostaElettronicaBean retrieveAttach(DettaglioPostaElettronicaBean bean) throws Exception {
		CalcolaImpronteService calcolaImpronteService = new CalcolaImpronteService();
		Map<String, String> mapFileUnivoci = new HashMap<String, String>();
//		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
//		lFileExtractedIn.setUri(bean.getUri());
//		RecuperoFile lRecuperoFile = new RecuperoFile();
//		FileExtractedOut out = lRecuperoFile.extractfile(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);
//		File emlFile = out.getExtracted();
		EmailAttachsBean lEmailAttachsBean = new MailProcessorService().getattachmentsbyidemail(getLocale(), bean.getIdEmail());
		/**
		 * Viene costruita la mappa contenente le informazioni per gli allegati recuperati da MailProcessorService con valori { key = nome_file + dim_file -
		 * value = uri_file }
		 */
		int count = 0;
		if (lEmailAttachsBean.getMailAttachments() != null) {
			for (MailAttachmentsInfoBean info : lEmailAttachsBean.getMailAttachments()) {

				String uriAttach = StorageImplementation.getStorage().store(lEmailAttachsBean.getFiles().get(count));
				String fileName = Normalizer.normalize(info.getFilename(), Normalizer.Form.NFC);
				Long currentFileSize = info.getSize();

				String key = fileName.concat(";").concat(currentFileSize.toString());
				mapFileUnivoci.put(key, uriAttach);

				count++;
			}
		}
		/**
		 * Viene costruita una mappa ( mapFileUnivoci ) con chiave la coppia nome_file + dimensione_file e value l'uri del file Viene valorizzato il campo uri
		 * per ogni allegato presente nell'oggetto bean.getListaAllegati(), recuperato dalla mappa costruita in precedenza.
		 */
		if (bean.getListaAllegati() != null && bean.getListaAllegati().size() > 0) {
			for (DettaglioPostaElettronicaAllegato lDettPostElettrAttachItem : bean.getListaAllegati()) {
				String size = lDettPostElettrAttachItem.getBytes();
				String key = lDettPostElettrAttachItem.getNomeFile().concat(";").concat(size);
				String impronta = lDettPostElettrAttachItem.getImpronta();
				String uriFileAllegato = mapFileUnivoci.get(key);
				/**
				 * La coppia NOME_FILE+DIMENSIONE FILE RECUPERATA DA MAIL_PROCESSOR_SERVICE coincide con quelli recuperati dalla store, quindi procedo a
				 * valorizzare l'uri dell'allegato
				 */
				if (uriFileAllegato != null && !"".equals(uriFileAllegato)) {
					lDettPostElettrAttachItem.setUri(uriFileAllegato);
				}
				/**
				 * 
				 * La coppia NOME_FILE+DIMENSIONE FILE RECUPERATA DA MAIL_PROCESSOR_SERVICE non coincide con quelli restituiti dalla store, di conseguenza viene
				 * recuperata l'impronta di ogni singolo allegato restituito da MAIL_PROCESSOR_SERVICE e viene quindi recuperato l'uri del relativo allegato
				 * proveniente dalla store che abbia la medesima impronta.
				 *
				 */
				else {
					/**
					 * Viene recuperato l'uri del file allegato appartenente alla store, tramite impronta, da quelli restituiti da mail_processor_service senza
					 * l'ausilio di FOP.
					 */
					Map<String, String> mapImprontaFile = recuperaUriAllegatoWithoutFOP(calcolaImpronteService, mapFileUnivoci, lDettPostElettrAttachItem);
					String keyMapFileUnivoci = mapImprontaFile.get(impronta);
					if (mapFileUnivoci.get(keyMapFileUnivoci) != null && !"".equals(mapFileUnivoci.get(keyMapFileUnivoci))) {
						lDettPostElettrAttachItem.setUri(mapFileUnivoci.get(keyMapFileUnivoci));
					}
				}
			}
		} else {
			/**
			 * Manca il riferimento all'allegato in tabella, quindi tutte le sue info non sono presenti. Si procede al recupero delle stesse tramite l'ausilio
			 * di FOP.
			 */
			List<DettaglioPostaElettronicaAllegato> allegati = new ArrayList<DettaglioPostaElettronicaAllegato>();
			Map<String, PostaElettronicaRetriewAttachBean> mapFiles = recuperaInfoAllegatoWithFOP(mapFileUnivoci);
			Iterator<String> ite = mapFiles.keySet().iterator();
			while (ite.hasNext()) {
				String keyMapFiles = ite.next();
				PostaElettronicaRetriewAttachBean postaElettronicaRetriewAttachBean = mapFiles.get(keyMapFiles);
				String uriFileAllegatoTemp = mapFileUnivoci.get(keyMapFiles);
				if (postaElettronicaRetriewAttachBean.getUriFile().equals(uriFileAllegatoTemp)) {
					DettaglioPostaElettronicaAllegato lDettaglioPostaElettronicaAllegato = setValuesToAllegatoWithFOP(keyMapFiles,
							postaElettronicaRetriewAttachBean);
					allegati.add(lDettaglioPostaElettronicaAllegato);
				}
			}
			bean.setListaAllegati(allegati);
		}
		return bean;
	}

	private DettaglioPostaElettronicaAllegato setValuesToAllegatoWithFOP(String key, PostaElettronicaRetriewAttachBean lPostaElettronicaRetriewAttachBean) {

		DettaglioPostaElettronicaAllegato lDettPostElettrAttachItem = new DettaglioPostaElettronicaAllegato();
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		MimeTypeFirmaBean lMimeTypeFirmaBean = lPostaElettronicaRetriewAttachBean.getMimeTypeFirmaBean();
		String uriFileAllegato = lPostaElettronicaRetriewAttachBean.getUriFile();

		lDettPostElettrAttachItem.setMimetype(lMimeTypeFirmaBean.getMimetype());
		String[] fileName = key.split(";");// nome_file;dim_file
		lDettPostElettrAttachItem.setNomeFile(fileName[0]);
		lDettPostElettrAttachItem.setDisplayFileName(lMimeTypeFirmaBean.getCorrectFileName());
		lDettPostElettrAttachItem.setImpronta(lMimeTypeFirmaBean.getImpronta());
		lDettPostElettrAttachItem.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		lDettPostElettrAttachItem.setEncoding(lDocumentConfiguration.getEncoding().value());
		Long sizeFileAllegato = new Long(lMimeTypeFirmaBean.getBytes());
		lDettPostElettrAttachItem.setBytes(sizeFileAllegato.toString());
		lDettPostElettrAttachItem.setFlgConvertibileInPdf(lMimeTypeFirmaBean.isConvertibile() ? "1" : "0");
		lDettPostElettrAttachItem.setFlgFirmato(lMimeTypeFirmaBean.isFirmato() ? "1" : "0");
		lDettPostElettrAttachItem.setFlgFirmaValida(lMimeTypeFirmaBean.isFirmaValida() ? "1" : "0");
		lDettPostElettrAttachItem.setUri(uriFileAllegato);
		lDettPostElettrAttachItem.setInfoFile(lMimeTypeFirmaBean);

		return lDettPostElettrAttachItem;
	}

	private DettaglioPostaElettronicaAllegato setValuesToAllegato(String key, PostaElettronicaRetriewAttachBean lPostaElettronicaRetriewAttachBean) {

		DettaglioPostaElettronicaAllegato lDettPostElettrAttachItem = new DettaglioPostaElettronicaAllegato();
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		MimeTypeFirmaBean lMimeTypeFirmaBean = lPostaElettronicaRetriewAttachBean.getMimeTypeFirmaBean();
		String uriFileAllegato = lPostaElettronicaRetriewAttachBean.getUriFile();

		lDettPostElettrAttachItem.setMimetype(lMimeTypeFirmaBean.getMimetype());
		String[] fileName = key.split(";");// nome_file;dim_file
		lDettPostElettrAttachItem.setNomeFile(fileName[0]);
		lDettPostElettrAttachItem.setDisplayFileName(lMimeTypeFirmaBean.getCorrectFileName());
		lDettPostElettrAttachItem.setImpronta(lMimeTypeFirmaBean.getImpronta());
		lDettPostElettrAttachItem.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		lDettPostElettrAttachItem.setEncoding(lDocumentConfiguration.getEncoding().value());
		Long sizeFileAllegato = new Long(lMimeTypeFirmaBean.getBytes());
		lDettPostElettrAttachItem.setBytes(sizeFileAllegato.toString());
		lDettPostElettrAttachItem.setFlgConvertibileInPdf(lMimeTypeFirmaBean.isConvertibile() ? "1" : "0");
		lDettPostElettrAttachItem.setFlgFirmato(lMimeTypeFirmaBean.isFirmato() ? "1" : "0");
		lDettPostElettrAttachItem.setFlgFirmaValida(lMimeTypeFirmaBean.isFirmaValida() ? "1" : "0");
		lDettPostElettrAttachItem.setUri(uriFileAllegato);
		lDettPostElettrAttachItem.setInfoFile(lMimeTypeFirmaBean);

		return lDettPostElettrAttachItem;
	}

	private Map<String, String> recuperaUriAllegatoWithoutFOP(CalcolaImpronteService calcolaImpronteService, Map<String, String> mapFileUnivoci,
			DettaglioPostaElettronicaAllegato lDettPostElettrAttachItem) throws StorageException, Exception {
		String algoritmoAttach = lDettPostElettrAttachItem.getAlgoritmo();
		String encodingAttach = lDettPostElettrAttachItem.getEncoding();

		Map<String, String> mapImprontaFile = new HashMap<String, String>();
		Iterator<String> ite = mapFileUnivoci.keySet().iterator();
		while (ite.hasNext()) {
			String key = ite.next();
			String uriFileAllegato = mapFileUnivoci.get(key);
			if (uriFileAllegato != null && !"".equals(uriFileAllegato)) {
				// Viene recuperato l'uri del file allegato tramite storage_util
				File fileTemp = StorageImplementation.getStorage().extractFile(uriFileAllegato);
				String improntaFile = calcolaImpronteService.calcolaImprontaWithoutFileOp(fileTemp, algoritmoAttach, encodingAttach);
				mapImprontaFile.put(improntaFile, key);
			}
		}
		return mapImprontaFile;
	}

	private Map<String, PostaElettronicaRetriewAttachBean> recuperaInfoAllegatoWithFOP(Map<String, String> mapFileUnivoci) throws Exception {

		Map<String, PostaElettronicaRetriewAttachBean> mapFiles = new HashMap<String, PostaElettronicaRetriewAttachBean>();

		Iterator<String> ite = mapFileUnivoci.keySet().iterator();
		while (ite.hasNext()) {
			String key = ite.next();
			String uriFileAllegato = mapFileUnivoci.get(key);
			String nomeFile[] = key.split(";");
			File fileTemp = StorageImplementation.getStorage().extractFile(uriFileAllegato);

			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(fileTemp.toURI().toString(), nomeFile[0], false, null);
			if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
				throw new Exception("Si è verificato un errore durante il controllo del file allegato " + nomeFile[0]);
			}
			PostaElettronicaRetriewAttachBean lPostaElettronicaRetriewAttachBean = new PostaElettronicaRetriewAttachBean();
			lPostaElettronicaRetriewAttachBean.setMimeTypeFirmaBean(lMimeTypeFirmaBean);
			lPostaElettronicaRetriewAttachBean.setUriFile(uriFileAllegato);

			mapFiles.put(key, lPostaElettronicaRetriewAttachBean);
		}
		return mapFiles;
	}

	public SimpleBean<Boolean> recuperaInfoHtml(DettaglioPostaElettronicaBean bean) {
		String lStrHtml = bean.getBody();
		Boolean result = null;
		if (!StringUtils.isEmpty(lStrHtml)) {
			Document doc = Jsoup.parseBodyFragment(lStrHtml);
			if (!doc.body().text().equals(lStrHtml)) {
				// E' html
				result = true;
			} else
				result = false;
		} else
			result = false;
		SimpleBean<Boolean> lSimpleBean = new SimpleBean<Boolean>();
		lSimpleBean.setValue(result);
		return lSimpleBean;
	}

	@Override
	public DettaglioPostaElettronicaBean update(DettaglioPostaElettronicaBean bean, DettaglioPostaElettronicaBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(DettaglioPostaElettronicaBean bean) throws Exception {
		return null;
	}

	public EmailInfoBean recuperaDatiEmlAllegato(VisualizzaEmlBean bean) throws Exception {
		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
		lFileExtractedIn.setUri(bean.getUri());
		RecuperoFile lRecuperoFile = new RecuperoFile();
		FileExtractedOut out = lRecuperoFile.extractfile(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);
		File emlFile = out.getExtracted();

		MailProcessorService m = new MailProcessorService();
		EmailInfoBean bean2 = m.getmailinfo(getLocale(), emlFile);
		MailAttachmentsBean attachmentsBean = bean2.getAllegati().get(bean.getNroAllegato());

		EmailAttachsBean lEmailAttachsBean = m.getattachments(getLocale(), emlFile);
		int count = 0;
		Map<String, String> lMap = new HashMap<String, String>();
		if (lEmailAttachsBean.getMailAttachments() != null) {
			for (MailAttachmentsInfoBean info : lEmailAttachsBean.getMailAttachments()) {
				String uriAttach = StorageImplementation.getStorage().store(lEmailAttachsBean.getFiles().get(count));
				lMap.put(info.getFilename(), uriAttach);
				count++;
			}
		}
		String uriFinale = lMap.get(attachmentsBean.getFilename());
		EmailInfoBean bean3 = m.getmailinfo(getLocale(), StorageImplementation.getStorage().extractFile(uriFinale));
		return bean3;
	}

	public DettaglioPostaElettronicaBean recuperaDatiEmlSbustato(DettaglioPostaElettronicaBean bean) throws Exception {
		MailProcessorService mailProcessorService = new MailProcessorService();
		MailAttachmentsBean mab = mailProcessorService.getpostacertbyidemail(getLocale(), bean.getIdEmail());
		String uriFile = StorageImplementation.getStorage().store(mab.getFile());
		DettaglioPostaElettronicaBean lDettaglioPostaElettronicaBean = new DettaglioPostaElettronicaBean();
		lDettaglioPostaElettronicaBean.setIdEmail(bean.getIdEmail());
		lDettaglioPostaElettronicaBean.setUri(uriFile);

		return lDettaglioPostaElettronicaBean;
	}

	public DownloadDocsZipBean getZipAllegati(DettaglioPostaElettronicaBean bean) throws Exception {

		String errorMessage = getExtraparams().get("limiteMaxZipError");
		String maxSizeParam = ParametriDBUtil.getParametroDB(getSession(), "MAX_SIZE_ZIP");
		long maxSize = (maxSizeParam != null && maxSizeParam.length() > 0 ? Long.decode(maxSizeParam) : 104857600);
		DownloadDocsZipBean deb = new DownloadDocsZipBean();

//		if (bean.getListaAllegati() == null)
//			getListaAllegati(bean);

		bean = retrieveAttach(bean);
		List<DettaglioPostaElettronicaAllegato> allegati = bean.getListaAllegati();
		String tempPath = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");
		File zipFile = new File(tempPath + "File_mail_" + bean.getProgrDownloadStampa() + ".zip");
		Map<String, Integer> zipFileNames = new HashMap<String, Integer>();

		long lengthZip = 0;
		for (DettaglioPostaElettronicaAllegato dettaglioPostaElettronicaAllegato : allegati) {
			lengthZip += dettaglioPostaElettronicaAllegato.getBytes() != null && dettaglioPostaElettronicaAllegato.getBytes().length() > 0
					? Integer.decode(dettaglioPostaElettronicaAllegato.getBytes()) : 0;

			if (lengthZip > maxSize) {
				deb.setMessage(errorMessage);
				return deb;
			}

			String nomeFile = dettaglioPostaElettronicaAllegato.getNomeFile().replace("/", "_");
			nomeFile = checkAndRenameDuplicate(zipFileNames, nomeFile);
			
			File currentAttachmentTemp = File.createTempFile("temp", nomeFile);
			File currentAttachment;
			
			try {
				String pathWithoutTempName= currentAttachmentTemp.getPath().replaceAll(FilenameUtils.getName(currentAttachmentTemp.getPath()), nomeFile);
				
				currentAttachment = new File(pathWithoutTempName);
				
				currentAttachmentTemp.renameTo(currentAttachment);
			} catch (Exception e) {
				mLogger.error("Errore durante la rinomina del file da inserire nello zip: " + e.getMessage(), e);
				currentAttachment = currentAttachmentTemp;
			}

			// se ci sono file firmati devo aggiungere sia i firmati che i sbustati
			FileUtil.writeStreamToFile(StorageImplementation.getStorage().extract(dettaglioPostaElettronicaAllegato.getUri()), currentAttachment);
			StorageUtil.addFileToZip(currentAttachment, zipFile.getAbsolutePath());

			if ("true".equals(dettaglioPostaElettronicaAllegato.getFlgFirmato()) && nomeFile.toLowerCase().endsWith(".p7m")) {

				InfoFileUtility lInfoFileUtility = new InfoFileUtility();
				InputStream fis = lInfoFileUtility.sbusta(StorageImplementation.getStorage().extractFile(dettaglioPostaElettronicaAllegato.getUri()), "");

				FileUtil.writeStreamToFile(fis, currentAttachment);
				nomeFile = nomeFile.substring(0, nomeFile.length() - 4);
				// aggiungo il file sbustato
				StorageUtil.addFileToZip(currentAttachment, zipFile.getAbsolutePath());
			}
		}
		String uri = StorageImplementation.getStorage().store(zipFile);
		deb.setStorageZipRemoteUri(uri);
		deb.setZipName(zipFile.getName());

		FileUtil.deleteFile(zipFile);

		return deb;
	}

	private void getListaAllegati(DettaglioPostaElettronicaBean bean) throws Exception {

		List<DettaglioPostaElettronicaAllegato> lListAllegati = new ArrayList<DettaglioPostaElettronicaAllegato>();
		bean.setUri(bean.getUri());
		TFilterFetch<TAttachEmailMgoBean> filterAttach = new TFilterFetch<TAttachEmailMgoBean>();
		TAttachEmailMgoBean lTAttachEmailMgoBean = new TAttachEmailMgoBean();
		lTAttachEmailMgoBean.setIdEmail(bean.getIdEmail());
		filterAttach.setFilter(lTAttachEmailMgoBean);
		TPagingList<TAttachEmailMgoBean> resultAttach = AurigaMailService.getDaoTAttachEmailMgo().search(getLocale(), filterAttach);
		if (resultAttach.getData() != null && resultAttach.getData().size() > 0) {
			for (TAttachEmailMgoBean lTAttach : resultAttach.getData()) {
				if (lTAttach.getIdEmail().equals(bean.getIdEmail())) {
					DettaglioPostaElettronicaAllegato lAllegatoProtocolloBean = new DettaglioPostaElettronicaAllegato();
					lAllegatoProtocolloBean.setNomeFile(lTAttach.getDisplayFilename());
					MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
					lMimeTypeFirmaBean.setFirmato(lTAttach.getFlgFirmato());
					lMimeTypeFirmaBean.setFirmaValida(true);
					if (lMimeTypeFirmaBean.isFirmato()) {
						lMimeTypeFirmaBean.setTipoFirma(
								lTAttach.getDisplayFilename().toUpperCase().endsWith("P7M") || lTAttach.getDisplayFilename().toUpperCase().endsWith("TSD")
										? "CAdES_BES"
										: "PDF");
					}
					lMimeTypeFirmaBean.setCorrectFileName(lTAttach.getDisplayFilename());
					lMimeTypeFirmaBean.setMimetype(lTAttach.getMimetype());
					lMimeTypeFirmaBean.setConvertibile(FormatiUtil.isConvertibile(getSession(), lTAttach.getMimetype()));

					lAllegatoProtocolloBean.setUri("_noUri");
					lAllegatoProtocolloBean.setBytes(String.valueOf(lTAttach.getDimensione()));
					lAllegatoProtocolloBean.setImpronta(lTAttach.getImpronta());
					lAllegatoProtocolloBean.setEncoding(lTAttach.getEncodingImpronta());
					lAllegatoProtocolloBean.setAlgoritmo(lTAttach.getAlgoritmoImpronta());
					lAllegatoProtocolloBean.setFlgFirmato(String.valueOf(lTAttach.getFlgFirmato()));
					lAllegatoProtocolloBean.setInfoFile(lMimeTypeFirmaBean);

					lListAllegati.add(lAllegatoProtocolloBean);
				}
			}
		}
		bean.setListaAllegati(lListAllegati);
	}

	private String checkAndRenameDuplicate(Map<String, Integer> zipFileNames, String attachmentFileName) {

		boolean duplicated = zipFileNames.containsKey(attachmentFileName);
		Integer index = 0;

		if (duplicated) {
			int x = zipFileNames.get(attachmentFileName);
			String attachmentFileNameoriginale = attachmentFileName;
			while (zipFileNames.containsKey(attachmentFileName)) {
				attachmentFileName = normalizeDuplicate(attachmentFileNameoriginale, x);
				x++;
			}
			zipFileNames.put(attachmentFileName, index);
		} else {
			zipFileNames.put(attachmentFileName, index);
		}
		return attachmentFileName;
	}

	private String normalizeDuplicate(String value, Integer index) {

		String[] tokens = value.split("\\.");
		String attachmentFileName = "";
		if (tokens.length >= 2) {
			for (int x = 0; x < tokens.length - 1; x++)
				attachmentFileName += "." + tokens[x];

			attachmentFileName += "_" + index + "." + tokens[tokens.length - 1];
			// tolgo il primo punto
			attachmentFileName = attachmentFileName.substring(1, attachmentFileName.length());
		} else
			attachmentFileName = tokens[0] + "_" + index + (tokens.length > 1 ? "." + tokens[1] : "");

		return attachmentFileName;
	}

	private boolean isBozza(String id) {
		return id != null && id.toUpperCase().endsWith(".B");
	}
	
	private TrustManager trustManager = new X509TrustManager() {

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateExpiredException, CertificateNotYetValidException {
			chain[0].checkValidity();
			chain[0].getIssuerUniqueID();
			chain[0].getSubjectDN();
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {

		}
	};
	
	public DownloadEmlFileBean getUriFromIdEmail(DownloadEmlFileBean input){
		
		AurigaMailBusinessClientConfig config = (AurigaMailBusinessClientConfig) SpringAppContext.getContext().getBean("MailConfigurator");
		DownloadEmlFileBean downloadEmlFileBean = new DownloadEmlFileBean();
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { trustManager }, null);
			DefaultClientConfig defaultClientConfig = new com.sun.jersey.api.client.config.DefaultClientConfig();
			defaultClientConfig.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(null, sslContext));
			Client client = Client.create(defaultClientConfig);
			WebResource webResource = client.resource(config.getUrl().endsWith("/") ? config.getUrl()+"/rest/email/"+input.getIdEmail()+"/downloading"
						 : config.getUrl()+"/rest/email/"+input.getIdEmail()+"/downloading");
			ClientResponse response = webResource.accept("application/octet-stream").get(ClientResponse.class);
			File filerRespJson = response.getEntity(File.class);
			downloadEmlFileBean.setUriFile(StorageImplementation.getStorage().store(filerRespJson));
			
			return downloadEmlFileBean;
		} catch (Exception e) {
			addMessage(
					"Errore nella chiamata al WS "+config.getUrl()+"/swagger/#!/Interazione/downloadEmlFile"
							+ e.getMessage(), "", MessageType.ERROR);
			mLogger.warn(e);
		}
		return downloadEmlFileBean;
	}
	
	
	/**
	 * Metodo che recupera il testo a partire da un html, cercando di mantenere la formattazione originale il puù possibile. 
	 * @param pStrHtml testo in formato html da ripulire 
	 * @return testo semplice formattato
	 */
	private String recuperaTestoSempliceDaEmail(String pStrHtml){
		if (StringUtils.isNotEmpty(pStrHtml)){
			Document doc = Jsoup.parseBodyFragment(pStrHtml);
			if (!doc.body().text().equals(pStrHtml)){
				//E' html
				pStrHtml = pStrHtml.replaceAll("\n", " ").replaceAll("\r", " ") //eliminazione dei newline dal testo in html.
						.replaceAll("<\\s*div\\s*[^>]*>", "#PH#<div>")  // trova tutti i possibili tag div e li sostituisce con un div semplice con un placeholder davanti.
						.replaceAll("<\\s*p\\s*[^>]*>", "#PH#<p>")  // trova tutti i possibili paragrafi e li inserisce un placeholder davanti.
						.replaceAll("<br>", "#PH#").replaceAll("</br>", "#PH#"); // trova tutti i tag br e sostituisce il placeholder
				pStrHtml = Jsoup.parseBodyFragment(pStrHtml).body().text(); // recupero il testo semplice dal body.
				pStrHtml = pStrHtml.replaceAll("(#PH#\\s)", "#PH#").replaceAll("((#PH#)){4,}", "#PH#"); // sostituisce un gruppo composto da piu di 3 placeholder di seguito con uno solo.
				pStrHtml = pStrHtml.replaceAll("#PH#", "\n");  //sostituisco il placeholder con una newline.
				
				return pStrHtml.trim();
			} else return pStrHtml;
		} else return pStrHtml;		
	}
	
	private long ricalcoloDimensioneAllegato(String uriAttach) {
		mLogger.debug("ricalcoloDimensioneAllegato per: "+uriAttach);
		File file = new File(uriAttach);
		long dimensione = file.length();
		return dimensione;
	}

	private boolean isPregresso(XmlDatiPostaElettronicaBean lXmlDatiPostaElettronicaBean,EmailAttachsBean lEmailAttachsBean) {
		
		if( (lXmlDatiPostaElettronicaBean != null && (lXmlDatiPostaElettronicaBean.getListaAllegati() == null || lXmlDatiPostaElettronicaBean.getListaAllegati().isEmpty())) &&
			(lEmailAttachsBean != null && lEmailAttachsBean.getMailAttachments() != null && !lEmailAttachsBean.getMailAttachments().isEmpty()) ){
				return true;
		}
		return false;
	}

}