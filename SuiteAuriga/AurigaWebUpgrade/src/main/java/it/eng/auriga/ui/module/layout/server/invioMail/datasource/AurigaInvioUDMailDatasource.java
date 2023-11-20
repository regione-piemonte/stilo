/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.config.VersioneConfig;
import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailAggdestinvioinrubricaemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailGenerasegnaturaxmlBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailPreparainvioricevutaregxemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailPreparainvioudxemailBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetestremiregnumud_jBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.filePerBustaTimbro.InfoFilePerBustaTimbro;
import it.eng.auriga.ui.module.layout.server.common.LoginDataSource;
import it.eng.auriga.ui.module.layout.server.firmaXades.bean.FirmaXadesBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.AttachmentUDBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.DestInvioMailXmlBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.DestinatariPecBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.GeneraSegnaturaXml;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioMailResultBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioUDMailBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioUDMailOutAttachmentBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioUDMailOutBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioUDMailOutDestinatariBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.SimpleBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.CorpoMailDataSource;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.CallExecAttDatasource;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttProcBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.ImpostazioniUnioneFileBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.UnioneFileAttoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.NuovaPropostaAtto2CompletaDataSource;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.timbra.OpzioniTimbroBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraResultBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraUtility;
import it.eng.auriga.ui.module.layout.shared.util.IndirizziEmailSplitter;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.InfoRelazioneProtocolloBean;
import it.eng.aurigamailbusiness.bean.MailLoginBean;
import it.eng.aurigamailbusiness.bean.ProtocolloAttachmentBean;
import it.eng.aurigamailbusiness.bean.RegistrazioneProtocollo;
import it.eng.aurigamailbusiness.bean.RegistrazioneProtocolloBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.bean.StatoConfermaAutomaticaBean;
import it.eng.client.AurigaMailService;
import it.eng.client.DmpkIntMgoEmailAggdestinvioinrubricaemail;
import it.eng.client.DmpkIntMgoEmailGenerasegnaturaxml;
import it.eng.client.DmpkIntMgoEmailPreparainvioricevutaregxemail;
import it.eng.client.DmpkIntMgoEmailPreparainvioudxemail;
import it.eng.client.DmpkUtilityGetestremiregnumud_j;
import it.eng.client.MailProcessorService;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.Flag;
import it.eng.fileOperation.clientws.TestoTimbro;
import it.eng.hsm.xades.HsmXadesUtility;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.TimbraUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AurigaInvioUDMailDatasource")
public class AurigaInvioUDMailDatasource extends AbstractServiceDataSource<ProtocollazioneBean, InvioUDMailBean> {

	private static Logger mLogger = Logger.getLogger(AurigaInvioUDMailDatasource.class);
	private static String invioSeparatoMessage = "L’opzione di invio con mail separate è consentita solo se non ci sono destinatari in cc";

	@Override
	public InvioUDMailBean call(ProtocollazioneBean pInBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		String finalita = getExtraparams().get("finalita");
		Integer flgInvioPecIn = null;
		String tipoMail = getExtraparams().get("tipoMail");
		String pecMulti = getExtraparams().get("PEC_MULTI") != null ? getExtraparams().get("PEC_MULTI") : "";
		if(tipoMail.equals("PEO")) {
			flgInvioPecIn = 0;
		} else if(tipoMail.equals("PEC") && pecMulti.equals("1")) {
			flgInvioPecIn = 2;
		} else {
			flgInvioPecIn = 1;
		}

		DmpkIntMgoEmailPreparainvioudxemail store = new DmpkIntMgoEmailPreparainvioudxemail();
		DmpkIntMgoEmailPreparainvioudxemailBean input = new DmpkIntMgoEmailPreparainvioudxemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudin(pInBean.getIdUd());
		input.setFlginviopecin(flgInvioPecIn);

		if (StringUtils.isNotBlank(finalita)) {
			input.setFinalitain(finalita);
		}

		// Invio pec ???
		StoreResultBean<DmpkIntMgoEmailPreparainvioudxemailBean> lResult = store.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(lResult.getDefaultMessage())) {
			if (lResult.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + lResult.getDefaultMessage());
				throw new StoreException(lResult);
			} else {
				addMessage(lResult.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		DmpkIntMgoEmailPreparainvioudxemailBean output = lResult.getResultBean();
		InvioUDMailOutBean lInvioMailUDoutBean = new XmlUtilityDeserializer().unbindXml(output.getXmldatiinviomailout(), InvioUDMailOutBean.class);

		InvioUDMailBean lInvioUDMailBean = new InvioUDMailBean();
		// Setto il tipo
		lInvioUDMailBean.setTipoMail(getExtraparams().get("tipoMail"));
		if (lInvioMailUDoutBean.getDestinatariList() != null) {
			List<DestinatariPecBean> lListDestinatariPEC = new ArrayList<DestinatariPecBean>();
			for (InvioUDMailOutDestinatariBean lInvioMailUDdestinatariBean : lInvioMailUDoutBean.getDestinatariList()) {
				DestinatariPecBean lDestinatariPecBean = new DestinatariPecBean();
				lDestinatariPecBean.setDestPrimario(lInvioMailUDdestinatariBean.getDestPrimario() == Flag.SETTED ? true : false);
				lDestinatariPecBean.setDestCC(lInvioMailUDdestinatariBean.getDestCC() == Flag.SETTED ? true : false);
				lDestinatariPecBean.setTipoDestinatario(lInvioMailUDdestinatariBean.getTipoDestinatario());
				lDestinatariPecBean.setIdRubricaAuriga(lInvioMailUDdestinatariBean.getIdRubricaAuriga());
				lDestinatariPecBean.setIntestazione(lInvioMailUDdestinatariBean.getIntestazione());
				lDestinatariPecBean.setCodiciIpa(lInvioMailUDdestinatariBean.getCodiciIpa());
				lDestinatariPecBean.setCipaPec(lInvioMailUDdestinatariBean.getCipaPec());
				lDestinatariPecBean.setIndirizzoMail(lInvioMailUDdestinatariBean.getEmail());
				lDestinatariPecBean.setIdMailInviata(lInvioMailUDdestinatariBean.getIdMailInviata());
				lDestinatariPecBean.setUriMail(lInvioMailUDdestinatariBean.getUriMail());
				lDestinatariPecBean.setDataOraMail(lInvioMailUDdestinatariBean.getDataOraMail());
				lDestinatariPecBean.setStatoMail(lInvioMailUDdestinatariBean.getStatoMail());
				lDestinatariPecBean.setIdInRubricaEmail(lInvioMailUDdestinatariBean.getIdInRubricaEmail());
				lListDestinatariPEC.add(lDestinatariPecBean);
			}
			lInvioUDMailBean.setDestinatariPec(lListDestinatariPEC);
		}

		List<AttachmentUDBean> lListAttachments = new ArrayList<AttachmentUDBean>();
		List<String> fileDaTimbrareNonConvertiti = new ArrayList<String>();
		for (InvioUDMailOutAttachmentBean lInvioMailUDattachmentBean : lInvioMailUDoutBean.getAttachments()) {
			if (lInvioMailUDattachmentBean.getDaTimbrare() != null)
				mLogger.debug("Attachment da timbrare: name = " + lInvioMailUDattachmentBean.getDaTimbrare().name() + " dbValue = "
						+ lInvioMailUDattachmentBean.getDaTimbrare().getDbValue());
			if (lInvioMailUDattachmentBean.getDaTimbrare() == Flag.SETTED) {
				mLogger.debug("L'attachment " + lInvioMailUDattachmentBean.getNomeFile() + " è da timbrare");
				generaFileTimbrato(lInvioMailUDattachmentBean, lListAttachments, fileDaTimbrareNonConvertiti, String.valueOf(pInBean.getIdUd().intValue()));
			} else {
				AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lInvioMailUDattachmentBean);
				lListAttachments.add(lAttachmentUDBean);
			}
		}

		lInvioUDMailBean.setMittente(lInvioMailUDoutBean.getCasellaMittente());
		lInvioUDMailBean.setIdCasellaMittente(lInvioMailUDoutBean.getIdCasellaMittente());
		lInvioUDMailBean.setAttach(lListAttachments);
		lInvioUDMailBean.setOggetto(lInvioMailUDoutBean.getOggetto());
		lInvioUDMailBean.setBodyHtml(lInvioMailUDoutBean.getBody());  
		lInvioUDMailBean.setDestinatari(lInvioMailUDoutBean.getDestinatari());
		lInvioUDMailBean.setDestinatariCC(lInvioMailUDoutBean.getDestinatariCC());
		lInvioUDMailBean.setSegnaturaPresente(getSegnatura(lInvioMailUDoutBean));
		lInvioUDMailBean.setSalvaInviati(lInvioMailUDoutBean.getFlagSalvaInviati() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setRichiestaConferma(lInvioMailUDoutBean.getRichiestaConferma() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setConfermaLetturaShow(lInvioMailUDoutBean.getConfermaLetturaShow() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setConfermaLettura(lInvioMailUDoutBean.getConfermaLettura() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setAvvertimenti(lInvioMailUDoutBean.getAvvertimenti());
		lInvioUDMailBean.setIdUD(pInBean.getIdUd().longValue() + "");
		lInvioUDMailBean.setFlgTipoProv(pInBean.getFlgTipoProv());

		if (fileDaTimbrareNonConvertiti != null && fileDaTimbrareNonConvertiti.size() > 0) {
			boolean first = true;
			StringBuffer lStringBuffer = new StringBuffer("Il formato del/i file ");
			if (first)
				first = false;
			else
				lStringBuffer.append(";");
			for (String lString : fileDaTimbrareNonConvertiti) {
				lStringBuffer.append(lString);
			}
			lStringBuffer.append(" non consente di apporvi il timbro con la segnatura");
			addMessage(lStringBuffer.toString(), lStringBuffer.toString(), MessageType.WARNING);
		}

		lInvioUDMailBean.setIdMailPartenza(pInBean.getIdEmailArrivo());

		return lInvioUDMailBean;
	}
	
	public InvioUDMailBean preparaEmailAttiDaLista(NuovaPropostaAtto2CompletaBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		String finalita = getExtraparams().get("finalita");
		Integer flgInvioPecIn = null;
		String tipoMail = getExtraparams().get("tipoMail");
		String pecMulti = getExtraparams().get("PEC_MULTI") != null ? getExtraparams().get("PEC_MULTI") : "";
		if(tipoMail.equals("PEO")) {
			flgInvioPecIn = 0;
		} else if(tipoMail.equals("PEC") && pecMulti.equals("1")) {
			flgInvioPecIn = 2;
		} else {
			flgInvioPecIn = 1;
		}

		DmpkIntMgoEmailPreparainvioudxemail store = new DmpkIntMgoEmailPreparainvioudxemail();
		DmpkIntMgoEmailPreparainvioudxemailBean input = new DmpkIntMgoEmailPreparainvioudxemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudin(new BigDecimal(pInBean.getIdUd()));
		input.setFlginviopecin(flgInvioPecIn);

		if (StringUtils.isNotBlank(finalita)) {
			input.setFinalitain(finalita);
		}

		// Invio pec ???
		StoreResultBean<DmpkIntMgoEmailPreparainvioudxemailBean> lResult = store.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(lResult.getDefaultMessage())) {
			if (lResult.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + lResult.getDefaultMessage());
				throw new StoreException(lResult);
			} else {
				addMessage(lResult.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		DmpkIntMgoEmailPreparainvioudxemailBean output = lResult.getResultBean();
		InvioUDMailOutBean lInvioMailUDoutBean = new XmlUtilityDeserializer().unbindXml(output.getXmldatiinviomailout(), InvioUDMailOutBean.class);

		List<AttachmentUDBean> lListAttachments = new ArrayList<AttachmentUDBean>();
		InvioUDMailBean lInvioUDMailBean = new InvioUDMailBean();
		// Setto il tipo
		lInvioUDMailBean.setTipoMail(getExtraparams().get("tipoMail"));
		if (lInvioMailUDoutBean.getDestinatariList() != null) {
			List<DestinatariPecBean> lListDestinatariPEC = new ArrayList<DestinatariPecBean>();
			for (InvioUDMailOutDestinatariBean lInvioMailUDdestinatariBean : lInvioMailUDoutBean.getDestinatariList()) {
				DestinatariPecBean lDestinatariPecBean = new DestinatariPecBean();
				lDestinatariPecBean.setDestPrimario(lInvioMailUDdestinatariBean.getDestPrimario() == Flag.SETTED ? true : false);
				lDestinatariPecBean.setDestCC(lInvioMailUDdestinatariBean.getDestCC() == Flag.SETTED ? true : false);
				lDestinatariPecBean.setTipoDestinatario(lInvioMailUDdestinatariBean.getTipoDestinatario());
				lDestinatariPecBean.setIdRubricaAuriga(lInvioMailUDdestinatariBean.getIdRubricaAuriga());
				lDestinatariPecBean.setIntestazione(lInvioMailUDdestinatariBean.getIntestazione());
				lDestinatariPecBean.setCodiciIpa(lInvioMailUDdestinatariBean.getCodiciIpa());
				lDestinatariPecBean.setCipaPec(lInvioMailUDdestinatariBean.getCipaPec());
				lDestinatariPecBean.setIndirizzoMail(lInvioMailUDdestinatariBean.getEmail());
				lDestinatariPecBean.setIdMailInviata(lInvioMailUDdestinatariBean.getIdMailInviata());
				lDestinatariPecBean.setUriMail(lInvioMailUDdestinatariBean.getUriMail());
				lDestinatariPecBean.setDataOraMail(lInvioMailUDdestinatariBean.getDataOraMail());
				lDestinatariPecBean.setStatoMail(lInvioMailUDdestinatariBean.getStatoMail());
				lDestinatariPecBean.setIdInRubricaEmail(lInvioMailUDdestinatariBean.getIdInRubricaEmail());
				lListDestinatariPEC.add(lDestinatariPecBean);
			}
			lInvioUDMailBean.setDestinatariPec(lListDestinatariPEC);
		}
		
		lInvioUDMailBean.setMittente(ParametriDBUtil.getParametroDB(getSession(), "INDIRIZZO_CASELLA_MITT_INVIO_PROP_ATTO"));//lInvioMailUDoutBean.getCasellaMittente()
		lInvioUDMailBean.setIdCasellaMittente(lInvioMailUDoutBean.getIdCasellaMittente());
		lInvioUDMailBean.setOggetto(lInvioMailUDoutBean.getOggetto());
		lInvioUDMailBean.setBodyHtml(lInvioMailUDoutBean.getBody());  
		lInvioUDMailBean.setDestinatari(lInvioMailUDoutBean.getDestinatari());
		lInvioUDMailBean.setDestinatariCC(lInvioMailUDoutBean.getDestinatariCC());
		lInvioUDMailBean.setSegnaturaPresente(getSegnatura(lInvioMailUDoutBean));
		lInvioUDMailBean.setSalvaInviati(lInvioMailUDoutBean.getFlagSalvaInviati() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setRichiestaConferma(lInvioMailUDoutBean.getRichiestaConferma() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setConfermaLetturaShow(lInvioMailUDoutBean.getConfermaLetturaShow() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setConfermaLettura(lInvioMailUDoutBean.getConfermaLettura() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setAvvertimenti(lInvioMailUDoutBean.getAvvertimenti());
		lInvioUDMailBean.setIdUD(pInBean.getIdUd());
		lInvioUDMailBean.setFlgTipoProv(null);//TODO
		lInvioUDMailBean.setInvioMailFromAtti(true);

		/** GENERAZIONE ALLEGATO FILE UNIONE */
		AttProcBean lAttProcBean = new AttProcBean();
		lAttProcBean.setIdUd(pInBean.getIdUd().toString());
		lAttProcBean.setIdProcess(pInBean.getIdProcess());
		lAttProcBean.setActivityName("#AGG_EXTRA_ITER");		
		lAttProcBean.setIdDefProcFlow(null);
		lAttProcBean.setIdInstProcFlow(null);			
		lAttProcBean = getCallExecAttDatasource().call(lAttProcBean);
		
		String nomeFileUnione = lAttProcBean.getUnioneFileNomeFile();
		String nomeFileUnioneOmissis = lAttProcBean.getUnioneFileNomeFileOmissis();
		ImpostazioniUnioneFileBean lImpostazioniUnioneFileBean = lAttProcBean.getImpostazioniUnioneFile();

		NuovaPropostaAtto2CompletaDataSource lNuovaPropostaAtto2CompletaDataSource = getNuovaPropostaAtto2CompletaDataSource();
		NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = new NuovaPropostaAtto2CompletaBean();
		lNuovaPropostaAtto2CompletaBean.setIdUd(pInBean.getIdUd().toString());
		lNuovaPropostaAtto2CompletaBean = lNuovaPropostaAtto2CompletaDataSource.get(lNuovaPropostaAtto2CompletaBean);
		
		lNuovaPropostaAtto2CompletaBean.setIdProcess(lAttProcBean.getIdProcess());
		lNuovaPropostaAtto2CompletaBean.setIdProcess(lAttProcBean.getIdProcess());						
		lNuovaPropostaAtto2CompletaBean.setIdModCopertina(lAttProcBean.getIdModCopertina());
		lNuovaPropostaAtto2CompletaBean.setNomeModCopertina(lAttProcBean.getNomeModCopertina());						
		lNuovaPropostaAtto2CompletaBean.setIdModCopertinaFinale(lAttProcBean.getIdModCopertinaFinale());
		lNuovaPropostaAtto2CompletaBean.setNomeModCopertinaFinale(lAttProcBean.getNomeModCopertinaFinale());						
		lNuovaPropostaAtto2CompletaBean.setIdModAllegatiParteIntSeparati(lAttProcBean.getIdModAllegatiParteIntSeparati());
		lNuovaPropostaAtto2CompletaBean.setNomeModAllegatiParteIntSeparati(lAttProcBean.getNomeModAllegatiParteIntSeparati());
		lNuovaPropostaAtto2CompletaBean.setUriModAllegatiParteIntSeparati(lAttProcBean.getUriModAllegatiParteIntSeparati());
		lNuovaPropostaAtto2CompletaBean.setTipoModAllegatiParteIntSeparati(lAttProcBean.getTipoModAllegatiParteIntSeparati());						
		lNuovaPropostaAtto2CompletaBean.setIdModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getIdModAllegatiParteIntSeparatiXPubbl());
		lNuovaPropostaAtto2CompletaBean.setNomeModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getNomeModAllegatiParteIntSeparatiXPubbl());
		lNuovaPropostaAtto2CompletaBean.setUriModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getUriModAllegatiParteIntSeparatiXPubbl());
		lNuovaPropostaAtto2CompletaBean.setTipoModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getTipoModAllegatiParteIntSeparatiXPubbl());						
		lNuovaPropostaAtto2CompletaBean.setFlgAppendiceDaUnire(lAttProcBean.getFlgAppendiceDaUnire());		
		lNuovaPropostaAtto2CompletaBean.setIdModAppendice(lAttProcBean.getIdModAppendice());
		lNuovaPropostaAtto2CompletaBean.setNomeModAppendice(lAttProcBean.getNomeModAppendice());						
		lNuovaPropostaAtto2CompletaBean.setIdModello(lAttProcBean.getIdModAssDocTask());
		lNuovaPropostaAtto2CompletaBean.setNomeModello(lAttProcBean.getNomeModAssDocTask());
		lNuovaPropostaAtto2CompletaBean.setDisplayFilenameModello(lAttProcBean.getDisplayFilenameModAssDocTask());
		lNuovaPropostaAtto2CompletaBean.setIdUoDirAdottanteSIB(lAttProcBean.getIdUoDirAdottanteSIB());
		lNuovaPropostaAtto2CompletaBean.setCodUoDirAdottanteSIB(lAttProcBean.getCodUoDirAdottanteSIB());
		lNuovaPropostaAtto2CompletaBean.setDesUoDirAdottanteSIB(lAttProcBean.getDesUoDirAdottanteSIB());						
		lNuovaPropostaAtto2CompletaBean.setImpostazioniUnioneFile(lImpostazioniUnioneFileBean);						
		
		lNuovaPropostaAtto2CompletaDataSource.getExtraparams().put("nomeFileUnione", nomeFileUnione);
		lNuovaPropostaAtto2CompletaDataSource.getExtraparams().put("nomeFileUnioneOmissis", nomeFileUnioneOmissis);
				
		int nroAttach = 1;
		
		UnioneFileAttoBean lUnioneFileAttoBean = lNuovaPropostaAtto2CompletaDataSource.unioneFile(lNuovaPropostaAtto2CompletaBean);
		if(lUnioneFileAttoBean != null) {
			AttachmentUDBean lAttachmentUDBean = new AttachmentUDBean();
			lAttachmentUDBean.setFileNameAttach(lUnioneFileAttoBean.getNomeFileVersIntegrale());
			lAttachmentUDBean.setFirmato(lUnioneFileAttoBean.getInfoFileVersIntegrale().isFirmato());
			lAttachmentUDBean.setFlgFirmaValida(lUnioneFileAttoBean.getInfoFileVersIntegrale().isFirmaValida());
			lAttachmentUDBean.setMimetype(lUnioneFileAttoBean.getInfoFileVersIntegrale().getMimetype());
			lAttachmentUDBean.setNroAttach(nroAttach++);
			lAttachmentUDBean.setRemoteUri(false);
			lAttachmentUDBean.setUriAttach(lUnioneFileAttoBean.getUriVersIntegrale());
			lListAttachments.add(lAttachmentUDBean);
		}
		
		/** GENERAZIONE ALLEGATO Parte Integrante & Da pubblicare separatamente */
		if (lNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2CompletaBean.getListaAllegati()) {
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null
						&& lAllegatoProtocolloBean.getFlgParteDispositivo()) { // se è parte integrante
					if (lAllegatoProtocolloBean.getFlgPubblicaSeparato() != null
								&& lAllegatoProtocolloBean.getFlgPubblicaSeparato()) { // se è da pubblicare separatamente
							AttachmentUDBean lAttachmentUDBean = new AttachmentUDBean();
							lAttachmentUDBean.setFileNameAttach(lAllegatoProtocolloBean.getNomeFileAllegato());
							lAttachmentUDBean.setFirmato(lAllegatoProtocolloBean.getInfoFile().isFirmato());
							lAttachmentUDBean.setFlgFirmaValida(lAllegatoProtocolloBean.getInfoFile().isFirmaValida());
							lAttachmentUDBean.setMimetype(lAllegatoProtocolloBean.getInfoFile().getMimetype());
							lAttachmentUDBean.setNroAttach(nroAttach++);
							lAttachmentUDBean.setRemoteUri(false);
							lAttachmentUDBean.setUriAttach(lAllegatoProtocolloBean.getUriFileAllegato());
							lAttachmentUDBean.setFlgAllegatoPISep(true);
							lListAttachments.add(lAttachmentUDBean);
					}
				}
			}
		}
		
		/** GENERAZIONE ALLEGATO MODULO FIRME */
		if(StringUtils.isNotBlank(lInvioMailUDoutBean.getIdModelloFoglioFirme()) && StringUtils.isNotBlank(lInvioMailUDoutBean.getNomeModelloFoglioFirme())) {
			
			String templateValues = getNuovaPropostaAtto2CompletaDataSource().getDatiXGenDaModello(lNuovaPropostaAtto2CompletaBean, lInvioMailUDoutBean.getNomeModelloFoglioFirme(), null); // RIEPILOGO FIRME E VISTI
			Map<String, Object> mappaValori = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
			FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(lNuovaPropostaAtto2CompletaBean.getIdProcess(),
					lInvioMailUDoutBean.getIdModelloFoglioFirme(), mappaValori, true, false, getSession());
			if(StringUtils.isNotBlank(lInvioMailUDoutBean.getDisplayFilenameFoglioFirme())) {
				fillModelBean.setNomeFile(lInvioMailUDoutBean.getDisplayFilenameFoglioFirme());
			} else {
				fillModelBean.setNomeFile("FOGLIO_FIRME.pdf");
			}
			
			if(fillModelBean != null && StringUtils.isNotBlank(fillModelBean.getUri())) {
				AttachmentUDBean lAttachmentUDBean = new AttachmentUDBean();
				lAttachmentUDBean.setFileNameAttach(fillModelBean.getNomeFile());
				lAttachmentUDBean.setFirmato(fillModelBean.getInfoFile().isFirmato());
				lAttachmentUDBean.setFlgFirmaValida(fillModelBean.getInfoFile().isFirmaValida());
				lAttachmentUDBean.setMimetype(fillModelBean.getInfoFile().getMimetype());
				lAttachmentUDBean.setNroAttach(nroAttach++);
				lAttachmentUDBean.setRemoteUri(false);
				lAttachmentUDBean.setUriAttach(fillModelBean.getUri());
				lListAttachments.add(lAttachmentUDBean);
			}
		} 
		
		lInvioUDMailBean.setAttach(lListAttachments);
		
		return lInvioUDMailBean;
	}
	
	public InvioUDMailBean preparaEmailAttiDaDettaglio(NuovaPropostaAtto2CompletaBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		String finalita = getExtraparams().get("finalita");
		Integer flgInvioPecIn = null;
		String tipoMail = getExtraparams().get("tipoMail");
		String pecMulti = getExtraparams().get("PEC_MULTI") != null ? getExtraparams().get("PEC_MULTI") : "";
		if(tipoMail.equals("PEO")) {
			flgInvioPecIn = 0;
		} else if(tipoMail.equals("PEC") && pecMulti.equals("1")) {
			flgInvioPecIn = 2;
		} else {
			flgInvioPecIn = 1;
		}

		DmpkIntMgoEmailPreparainvioudxemail store = new DmpkIntMgoEmailPreparainvioudxemail();
		DmpkIntMgoEmailPreparainvioudxemailBean input = new DmpkIntMgoEmailPreparainvioudxemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudin(new BigDecimal(pInBean.getIdUd()));
		input.setFlginviopecin(flgInvioPecIn);

		if (StringUtils.isNotBlank(finalita)) {
			input.setFinalitain(finalita);
		}

		// Invio pec ???
		StoreResultBean<DmpkIntMgoEmailPreparainvioudxemailBean> lResult = store.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(lResult.getDefaultMessage())) {
			if (lResult.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + lResult.getDefaultMessage());
				throw new StoreException(lResult);
			} else {
				addMessage(lResult.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		DmpkIntMgoEmailPreparainvioudxemailBean output = lResult.getResultBean();
		InvioUDMailOutBean lInvioMailUDoutBean = new XmlUtilityDeserializer().unbindXml(output.getXmldatiinviomailout(), InvioUDMailOutBean.class);

		List<AttachmentUDBean> lListAttachments = new ArrayList<AttachmentUDBean>();
		InvioUDMailBean lInvioUDMailBean = new InvioUDMailBean();
		// Setto il tipo
		lInvioUDMailBean.setTipoMail(getExtraparams().get("tipoMail"));
		if (lInvioMailUDoutBean.getDestinatariList() != null) {
			List<DestinatariPecBean> lListDestinatariPEC = new ArrayList<DestinatariPecBean>();
			for (InvioUDMailOutDestinatariBean lInvioMailUDdestinatariBean : lInvioMailUDoutBean.getDestinatariList()) {
				DestinatariPecBean lDestinatariPecBean = new DestinatariPecBean();
				lDestinatariPecBean.setDestPrimario(lInvioMailUDdestinatariBean.getDestPrimario() == Flag.SETTED ? true : false);
				lDestinatariPecBean.setDestCC(lInvioMailUDdestinatariBean.getDestCC() == Flag.SETTED ? true : false);
				lDestinatariPecBean.setTipoDestinatario(lInvioMailUDdestinatariBean.getTipoDestinatario());
				lDestinatariPecBean.setIdRubricaAuriga(lInvioMailUDdestinatariBean.getIdRubricaAuriga());
				lDestinatariPecBean.setIntestazione(lInvioMailUDdestinatariBean.getIntestazione());
				lDestinatariPecBean.setCodiciIpa(lInvioMailUDdestinatariBean.getCodiciIpa());
				lDestinatariPecBean.setCipaPec(lInvioMailUDdestinatariBean.getCipaPec());
				lDestinatariPecBean.setIndirizzoMail(lInvioMailUDdestinatariBean.getEmail());
				lDestinatariPecBean.setIdMailInviata(lInvioMailUDdestinatariBean.getIdMailInviata());
				lDestinatariPecBean.setUriMail(lInvioMailUDdestinatariBean.getUriMail());
				lDestinatariPecBean.setDataOraMail(lInvioMailUDdestinatariBean.getDataOraMail());
				lDestinatariPecBean.setStatoMail(lInvioMailUDdestinatariBean.getStatoMail());
				lDestinatariPecBean.setIdInRubricaEmail(lInvioMailUDdestinatariBean.getIdInRubricaEmail());
				lListDestinatariPEC.add(lDestinatariPecBean);
			}
			lInvioUDMailBean.setDestinatariPec(lListDestinatariPEC);
		}
		
		lInvioUDMailBean.setMittente(ParametriDBUtil.getParametroDB(getSession(), "INDIRIZZO_CASELLA_MITT_INVIO_PROP_ATTO"));//lInvioMailUDoutBean.getCasellaMittente()
		lInvioUDMailBean.setIdCasellaMittente(lInvioMailUDoutBean.getIdCasellaMittente());
		lInvioUDMailBean.setOggetto(lInvioMailUDoutBean.getOggetto());
		lInvioUDMailBean.setBodyHtml(lInvioMailUDoutBean.getBody());  
		lInvioUDMailBean.setDestinatari(lInvioMailUDoutBean.getDestinatari());
		lInvioUDMailBean.setDestinatariCC(lInvioMailUDoutBean.getDestinatariCC());
		lInvioUDMailBean.setSegnaturaPresente(getSegnatura(lInvioMailUDoutBean));
		lInvioUDMailBean.setSalvaInviati(lInvioMailUDoutBean.getFlagSalvaInviati() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setRichiestaConferma(lInvioMailUDoutBean.getRichiestaConferma() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setConfermaLetturaShow(lInvioMailUDoutBean.getConfermaLetturaShow() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setConfermaLettura(lInvioMailUDoutBean.getConfermaLettura() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setAvvertimenti(lInvioMailUDoutBean.getAvvertimenti());
		lInvioUDMailBean.setIdUD(pInBean.getIdUd());
		lInvioUDMailBean.setFlgTipoProv(null);//TODO
		lInvioUDMailBean.setInvioMailFromAtti(true);
		
		/** GENERAZIONE ALLEGATO FILE UNIONE */
		NuovaPropostaAtto2CompletaDataSource lNuovaPropostaAtto2CompletaDataSource = getNuovaPropostaAtto2CompletaDataSource();

		lNuovaPropostaAtto2CompletaDataSource.getExtraparams().put("nomeFileUnione", pInBean.getUnioneFileNomeFile());
		lNuovaPropostaAtto2CompletaDataSource.getExtraparams().put("nomeFileUnioneOmissis", pInBean.getUnioneFileNomeFileOmissis());
				
		int nroAttach = 1;
		
		UnioneFileAttoBean lUnioneFileAttoBean = lNuovaPropostaAtto2CompletaDataSource.unioneFile(pInBean);
		if(lUnioneFileAttoBean != null) {
			AttachmentUDBean lAttachmentUDBean = new AttachmentUDBean();
			lAttachmentUDBean.setFileNameAttach(lUnioneFileAttoBean.getNomeFileVersIntegrale());
			lAttachmentUDBean.setFirmato(lUnioneFileAttoBean.getInfoFileVersIntegrale().isFirmato());
			lAttachmentUDBean.setFlgFirmaValida(lUnioneFileAttoBean.getInfoFileVersIntegrale().isFirmaValida());
			lAttachmentUDBean.setMimetype(lUnioneFileAttoBean.getInfoFileVersIntegrale().getMimetype());
			lAttachmentUDBean.setNroAttach(nroAttach++);
			lAttachmentUDBean.setRemoteUri(false);
			lAttachmentUDBean.setUriAttach(lUnioneFileAttoBean.getUriVersIntegrale());
			lListAttachments.add(lAttachmentUDBean);
		}
		
		/** GENERAZIONE ALLEGATO Parte Integrante & Da pubblicare separatamente */
		if (pInBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pInBean.getListaAllegati()) {
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null
						&& lAllegatoProtocolloBean.getFlgParteDispositivo()) { // se è parte integrante
					if (lAllegatoProtocolloBean.getFlgPubblicaSeparato() != null
								&& lAllegatoProtocolloBean.getFlgPubblicaSeparato()) { // se è da pubblicare separatamente
							AttachmentUDBean lAttachmentUDBean = new AttachmentUDBean();
							lAttachmentUDBean.setFileNameAttach(lAllegatoProtocolloBean.getNomeFileAllegato());
							lAttachmentUDBean.setFirmato(lAllegatoProtocolloBean.getInfoFile().isFirmato());
							lAttachmentUDBean.setFlgFirmaValida(lAllegatoProtocolloBean.getInfoFile().isFirmaValida());
							lAttachmentUDBean.setMimetype(lAllegatoProtocolloBean.getInfoFile().getMimetype());
							lAttachmentUDBean.setNroAttach(nroAttach++);
							lAttachmentUDBean.setRemoteUri(false);
							lAttachmentUDBean.setUriAttach(lAllegatoProtocolloBean.getUriFileAllegato());
							lAttachmentUDBean.setFlgAllegatoPISep(true);
							lListAttachments.add(lAttachmentUDBean);
					}
				}
			}
		}
		
		/** GENERAZIONE ALLEGATO MODULO FIRME */
		if(StringUtils.isNotBlank(lInvioMailUDoutBean.getIdModelloFoglioFirme()) && StringUtils.isNotBlank(lInvioMailUDoutBean.getNomeModelloFoglioFirme())) {
			
			String templateValues = getNuovaPropostaAtto2CompletaDataSource().getDatiXGenDaModello(pInBean, lInvioMailUDoutBean.getNomeModelloFoglioFirme(), null); // RIEPILOGO FIRME E VISTI
			Map<String, Object> mappaValori = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
			FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(pInBean.getIdProcess(),
					lInvioMailUDoutBean.getIdModelloFoglioFirme(), mappaValori, true, false, getSession());
			if(StringUtils.isNotBlank(lInvioMailUDoutBean.getDisplayFilenameFoglioFirme())) {
				fillModelBean.setNomeFile(lInvioMailUDoutBean.getDisplayFilenameFoglioFirme());
			} else {
				fillModelBean.setNomeFile("FOGLIO_FIRME.pdf");
			}
			
			if(fillModelBean != null && StringUtils.isNotBlank(fillModelBean.getUri())) {
				AttachmentUDBean lAttachmentUDBean = new AttachmentUDBean();
				lAttachmentUDBean.setFileNameAttach(fillModelBean.getNomeFile());
				lAttachmentUDBean.setFirmato(fillModelBean.getInfoFile().isFirmato());
				lAttachmentUDBean.setFlgFirmaValida(fillModelBean.getInfoFile().isFirmaValida());
				lAttachmentUDBean.setMimetype(fillModelBean.getInfoFile().getMimetype());
				lAttachmentUDBean.setNroAttach(nroAttach++);
				lAttachmentUDBean.setRemoteUri(false);
				lAttachmentUDBean.setUriAttach(fillModelBean.getUri());
				lListAttachments.add(lAttachmentUDBean);
			}
		} 
		
		lInvioUDMailBean.setAttach(lListAttachments);
		
		return lInvioUDMailBean;
	}
	
	public CallExecAttDatasource getCallExecAttDatasource() {
		CallExecAttDatasource lCallExecAttDatasource = new CallExecAttDatasource();
		lCallExecAttDatasource.setSession(getSession());
		lCallExecAttDatasource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lCallExecAttDatasource.setMessages(getMessages()); 		
		return lCallExecAttDatasource;
	}	
	
	public NuovaPropostaAtto2CompletaDataSource getNuovaPropostaAtto2CompletaDataSource() {
		NuovaPropostaAtto2CompletaDataSource lNuovaPropostaAtto2CompletaDataSource = new NuovaPropostaAtto2CompletaDataSource();
		lNuovaPropostaAtto2CompletaDataSource.setSession(getSession());
		lNuovaPropostaAtto2CompletaDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lNuovaPropostaAtto2CompletaDataSource.setMessages(getMessages()); 		
		return lNuovaPropostaAtto2CompletaDataSource;
	}	

	private void generaFileTimbrato(InvioUDMailOutAttachmentBean lInvioMailUDattachmentBean, List<AttachmentUDBean> lListAttachments,
			List<String> fileDaTimbrareNonConvertiti, String idUd) throws Exception {
		File fileDaTimbrare = null;
		TimbraUtil lTimbraUtil = new TimbraUtil();
		mLogger.debug("Verifico se pdf o convertibile");
		mLogger.debug("Pdf vale: " + lInvioMailUDattachmentBean.getPdf().name() + " dbValue : " + lInvioMailUDattachmentBean.getPdf().getDbValue());
		mLogger.debug("Convertibile vale: " + lInvioMailUDattachmentBean.getConvertibile().name() + " dbValue : "
				+ lInvioMailUDattachmentBean.getConvertibile().getDbValue());
		// Se il file da timbrare non è pdf e non è convertibile genero alert
		String nomeFileDaTimbrare = lInvioMailUDattachmentBean.getNomeFile();
		if (lInvioMailUDattachmentBean.getPdf() == Flag.NOT_SETTED && lInvioMailUDattachmentBean.getConvertibile() == Flag.NOT_SETTED) {
			mLogger.debug("Mi risulta che non è PDF e non è convertibile, lo aggiungo alla lista dei non timbrati");
			fileDaTimbrareNonConvertiti.add(lInvioMailUDattachmentBean.getNomeFile());
			AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lInvioMailUDattachmentBean);
			lListAttachments.add(lAttachmentUDBean);
			return;
		} else {
			mLogger.debug("Recupero il file");
			// Se è da convertire
			RecuperoFile lRecuperoFile = new RecuperoFile();
			AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(lInvioMailUDattachmentBean.getUri());
			FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);
			mLogger.debug("Pdf vale: " + lInvioMailUDattachmentBean.getPdf().name() + " dbValue : " + lInvioMailUDattachmentBean.getPdf().getDbValue());
			mLogger.debug("Convertibile vale: " + lInvioMailUDattachmentBean.getConvertibile().name() + " dbValue : "
					+ lInvioMailUDattachmentBean.getConvertibile().getDbValue());
			if (lInvioMailUDattachmentBean.getPdf() == Flag.NOT_SETTED && lInvioMailUDattachmentBean.getConvertibile() == Flag.SETTED) {
				mLogger.debug("Mi risulta non pdf e convertibile, provo a convertirlo");
				try {
					String uriFilePdf = StorageImplementation.getStorage().storeStream(
							lTimbraUtil.converti(out.getExtracted(), lInvioMailUDattachmentBean.getNomeFile()));
					fileDaTimbrare = StorageImplementation.getStorage().extractFile(uriFilePdf);
					if (!lInvioMailUDattachmentBean.getMimetype().equals("application/pdf")) {
						nomeFileDaTimbrare = lInvioMailUDattachmentBean.getNomeFile() + ".pdf";
					}
				} catch (Exception e) {
					mLogger.error("Non sono riuscito a convertirlo per " + e.getMessage() + ", lo aggiungo alla lista dei non timbrati ", e);
					fileDaTimbrareNonConvertiti.add(lInvioMailUDattachmentBean.getNomeFile());
					AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lInvioMailUDattachmentBean);
					lListAttachments.add(lAttachmentUDBean);
					return;
				}
			} else {
				mLogger.debug("Mi risulta pdf");
				fileDaTimbrare = out.getExtracted();
			}
		}
		OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAttachEmail");
		TestoTimbro lTestoTimbro = new TestoTimbro();
		lTestoTimbro.setTesto(lInvioMailUDattachmentBean.getTestoVicinoAlTimbro());
		lOpzioniTimbroAttachEmail.setIntestazioneTimbro(lTestoTimbro);
		lOpzioniTimbroAttachEmail.setTestoTimbro(lTestoTimbro);

		boolean generaPdfA = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "TIMBRATURA_ABILITA_PDFA");
		
		InputStream timbrato = null;
		mLogger.debug("Provo a timbrarlo");
		
		try {						
			if(lInvioMailUDattachmentBean.getFirmato() == Flag.SETTED && StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_BUSTA_PDF_FILE_FIRMATO")) &&
					   "true".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_BUSTA_PDF_FILE_FIRMATO"))) {
				
				InfoFilePerBustaTimbro fileTimbrato = new InfoFilePerBustaTimbro();
				fileTimbrato.setFile(fileDaTimbrare);
				fileTimbrato.setNomeFile(lInvioMailUDattachmentBean.getNomeFile());
				
				List<InfoFilePerBustaTimbro> listaFileDaAggiungereAllaBusta = new ArrayList<InfoFilePerBustaTimbro>();
				listaFileDaAggiungereAllaBusta.add(fileTimbrato);
				
				TimbraResultBean timbraResultBean = TimbraUtility.creaTimbraturaPerFileFirmato(getSession(), getLocale(), generaPdfA, lOpzioniTimbroAttachEmail, idUd, lInvioMailUDattachmentBean.getIdDoc(), null, listaFileDaAggiungereAllaBusta);
				
				RecuperoFile lRecuperoFile = new RecuperoFile();
				AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(timbraResultBean.getUri());
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);
				File fileBusta = out.getExtracted();
				timbrato = new FileInputStream(fileBusta);			
			}else {
				timbrato = lTimbraUtil.timbra(fileDaTimbrare, nomeFileDaTimbrare, lOpzioniTimbroAttachEmail, generaPdfA);
			}			
			
		} catch (Exception e) {
			String message = e.getMessage();
			if(message.contains("BUSTA_ACCOMPAGNAMENTO_CON_TIMBRO_REGISTRAZIONE")) {
				message = "Per problema di configurazione impossibile allegare la versione del file con busta timbrata: manca il modello della busta timbrata";
			}
			addMessage(message, "", MessageType.WARNING);
			AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lInvioMailUDattachmentBean);
			lListAttachments.add(lAttachmentUDBean);
			return;
		}
		// se il file che si timbra è firmato digitalmente (colonna 5=1)
		// OR non è un pdf (colonna 6=0)
		// negli allegati mettiamo sia il file originale che quello timbrato con suffisso _con_ segnatura.pdf
		mLogger.debug("Firmato vale: " + lInvioMailUDattachmentBean.getFirmato().name() + " dbValue : " + lInvioMailUDattachmentBean.getFirmato().getDbValue());
		mLogger.debug("Pdf vale: " + lInvioMailUDattachmentBean.getPdf().name() + " dbValue : " + lInvioMailUDattachmentBean.getPdf().getDbValue());
		if (lInvioMailUDattachmentBean.getFirmato() == Flag.SETTED || lInvioMailUDattachmentBean.getPdf() == Flag.NOT_SETTED) {
			if(lInvioMailUDattachmentBean.getFirmato() == Flag.SETTED && StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_BUSTA_PDF_FILE_FIRMATO")) &&
					   "true".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_BUSTA_PDF_FILE_FIRMATO"))) {
				
				/*In questo caso (ATTIVA_BUSTA_PDF_FILE_FIRMATO = true) ho creato la busta sia con il timbro e il file firmato 
				 * e non c'è bisogno di aggiungere timbrato e originale separati*/				
				
				mLogger.debug("Aggiungo l'attachment timbrato con busta per file firmati");
				AttachmentUDBean lAttachmentUDBeanTimbrato = getAttachmentTimbrato(lInvioMailUDattachmentBean, timbrato);
				setFileNameFirmatoTimbrato(lAttachmentUDBeanTimbrato, lInvioMailUDattachmentBean.getNomeFile(), "firmatoBusta");
				lListAttachments.add(lAttachmentUDBeanTimbrato);
				
				
				/*In questo caso (AGGIUNGI_ATTACH_FILE_TIMBRATO_X_STAMPA = true) devo aggiungere sia la busta con il file firmato come attach sia lo sbustato timbrato per stampa*/	
				if(StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(getSession(), "AGGIUNGI_ATTACH_FILE_TIMBRATO_X_STAMPA")) &&
						   "true".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "AGGIUNGI_ATTACH_FILE_TIMBRATO_X_STAMPA"))) {
					try {
						timbrato = lTimbraUtil.timbra(fileDaTimbrare, nomeFileDaTimbrare, lOpzioniTimbroAttachEmail, generaPdfA);
						mLogger.debug("Aggiungo l'attachment timbrato oltre alla busta con allegato il file timbrato");
						AttachmentUDBean attachmentUDBeanTimbrato = getAttachmentTimbrato(lInvioMailUDattachmentBean, timbrato);
						setFileNameFirmatoTimbrato(attachmentUDBeanTimbrato, lInvioMailUDattachmentBean.getNomeFile(), "firmatoPerStampa");
						lListAttachments.add(attachmentUDBeanTimbrato);
					}catch (Exception e) {
							mLogger.error("Non sono riuscito a timbrarlo per " + e.getMessage() + ", lo aggiungo alla lista dei non timbrati ", e);
							fileDaTimbrareNonConvertiti.add(lInvioMailUDattachmentBean.getNomeFile());
							AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lInvioMailUDattachmentBean);
							lListAttachments.add(lAttachmentUDBean);						
					}
				}
				
				
			}else {
				mLogger.debug("Mi risulta firmato digitalmente oppure non pdf");
				AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lInvioMailUDattachmentBean);
				lListAttachments.add(lAttachmentUDBean);
				mLogger.debug("Aggiungo l'attachment timbrato oltre all'originale");
				AttachmentUDBean lAttachmentUDBeanTimbrato = getAttachmentTimbrato(lInvioMailUDattachmentBean, timbrato);
				lListAttachments.add(lAttachmentUDBeanTimbrato);
			}
		} else {
			mLogger.debug("Aggiungo l'attachment timbrato");
			AttachmentUDBean lAttachmentUDBeanTimbrato = getAttachmentTimbrato(lInvioMailUDattachmentBean, timbrato);
//			lAttachmentUDBeanTimbrato.setFileNameAttach(lInvioMailUDattachmentBean.getNomeFile());
			lListAttachments.add(lAttachmentUDBeanTimbrato);
		}

	}

	protected AttachmentUDBean getAttachmentOriginale(InvioUDMailOutAttachmentBean lInvioMailUDattachmentBean) {
		AttachmentUDBean lAttachmentUDBean = new AttachmentUDBean();
		lAttachmentUDBean.setFileNameAttach(lInvioMailUDattachmentBean.getNomeFile());
		lAttachmentUDBean.setNroAttach(StringUtils.isNotEmpty(lInvioMailUDattachmentBean.getNroAllegato()) ? Integer.valueOf(lInvioMailUDattachmentBean
				.getNroAllegato()) : null);
		lAttachmentUDBean.setUriAttach(lInvioMailUDattachmentBean.getUri());
		lAttachmentUDBean.setRemoteUri(true);
		lAttachmentUDBean.setFirmato(lInvioMailUDattachmentBean.getFirmato() == Flag.SETTED ? true : false);
		lAttachmentUDBean.setMimetype(lInvioMailUDattachmentBean.getMimetype());
		return lAttachmentUDBean;
	}

	protected AttachmentUDBean getAttachmentTimbrato(InvioUDMailOutAttachmentBean lInvioMailUDattachmentBean, InputStream timbrato) throws StorageException {
		String lStrUri = StorageImplementation.getStorage().storeStream(timbrato);
		String fileName;
		if (lInvioMailUDattachmentBean.getNomeFile().toLowerCase().endsWith(".p7m") || lInvioMailUDattachmentBean.getNomeFile().toLowerCase().endsWith(".tsd")) {
			String fileNameSbustato = lInvioMailUDattachmentBean.getNomeFile().substring(0, lInvioMailUDattachmentBean.getNomeFile().length() - 4) ;
			fileName = FilenameUtils.getBaseName(fileNameSbustato) + "_timbrato.pdf";
		}else {
		 fileName = FilenameUtils.getBaseName(lInvioMailUDattachmentBean.getNomeFile()) + "_timbrato.pdf";
		}
		AttachmentUDBean lAttachmentUDBean = new AttachmentUDBean();
		lAttachmentUDBean.setFileNameAttach(fileName);
		lAttachmentUDBean.setNroAttach(StringUtils.isNotEmpty(lInvioMailUDattachmentBean.getNroAllegato()) ? Integer.valueOf(lInvioMailUDattachmentBean
				.getNroAllegato()) : null);
		lAttachmentUDBean.setUriAttach(lStrUri);
		lAttachmentUDBean.setRemoteUri(false);
		lAttachmentUDBean.setMimetype("application/pdf");
		lAttachmentUDBean.setFirmato(false);
		
		return lAttachmentUDBean;
	}

	private void setFileNameFirmatoTimbrato(AttachmentUDBean attachmentBean, String nomeFileDaTimbrare, String operazione) {
		
		String fileName;
		if (nomeFileDaTimbrare.toLowerCase().endsWith(".p7m") || nomeFileDaTimbrare.toLowerCase().endsWith(".tsd")) {
			String fileNameSbustato = nomeFileDaTimbrare.substring(0, nomeFileDaTimbrare.length() - 4) ;
			fileName = FilenameUtils.getBaseName(fileNameSbustato);
		}else {
			fileName = FilenameUtils.getBaseName(nomeFileDaTimbrare);
		}
		
		String suffisso = "_timbrato.pdf";
		if("firmatoBusta".equalsIgnoreCase(operazione)) {
			if(StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(getSession(), "SUFF_FILE_FIRMATO_TIMBRATO_CON_ALLEG_ORIG"))) {
				suffisso = ParametriDBUtil.getParametroDB(getSession(), "SUFF_FILE_FIRMATO_TIMBRATO_CON_ALLEG_ORIG");
			}else {
				suffisso = "_timbrato_alleg_originale_firmato.pdf";
			}
		}else if("firmatoPerStampa".equalsIgnoreCase(operazione)) {
			if(StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(getSession(), "SUFF_FILE_FIRMATO_TIMBRATO_X_STAMPA"))) {
				suffisso = ParametriDBUtil.getParametroDB(getSession(), "SUFF_FILE_FIRMATO_TIMBRATO_X_STAMPA");
			}else {
				suffisso = "_timbrato_per_stampa.pdf";
			}
		}
		
		attachmentBean.setFileNameAttach(fileName + suffisso);				
		
	}
	
	private String retrieveSegnaturaAndStore(InvioUDMailBean pInvioUDMailBean, AurigaLoginBean loginBean) throws Exception {
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkIntMgoEmailGenerasegnaturaxml store = new DmpkIntMgoEmailGenerasegnaturaxml();
		DmpkIntMgoEmailGenerasegnaturaxmlBean bean = new DmpkIntMgoEmailGenerasegnaturaxmlBean();
		bean.setCodidconnectiontokenin(token);
		bean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		bean.setIdudin(new BigDecimal(pInvioUDMailBean.getIdUD()));
		bean.setVersionedtdin(((VersioneConfig) SpringAppContext.getContext().getBean("VersioneConfig")).getVersione());
		bean.setXmldatiinviomailin(buildXmlDatiInvio(pInvioUDMailBean));
		StoreResultBean<DmpkIntMgoEmailGenerasegnaturaxmlBean> lResult = store.execute(getLocale(), loginBean, bean);
		if (lResult.isInError()) {
			throw new StoreException(lResult);
		}
		bean = lResult.getResultBean();
		String xmlSegnatura = bean.getXmlsegnaturaout();
		
		// Verifico che nella segnatura ci siano tutti i file
		// Creo il parser per la segnatura xml
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document segnaturaDocument = docBuilder.parse(new InputSource(new StringReader(xmlSegnatura)));
		Node segnaturaAllegatiNode = ((segnaturaDocument.getElementsByTagName("Allegati") != null) && (segnaturaDocument.getElementsByTagName("Allegati").getLength() > 0)) ? segnaturaDocument.getElementsByTagName("Allegati").item(0) : null;
		NodeList segnaturaListaTitoliAllegati = (segnaturaAllegatiNode != null) ? ((Element) segnaturaAllegatiNode).getElementsByTagName("TitoloDocumento") : null;
		List<Node> segnaturaListaAllegatiDaAggiungere = new ArrayList<Node>();
		if (pInvioUDMailBean.getAttach() != null) {
			// Scorro gli attach
			for (AttachmentUDBean attach : pInvioUDMailBean.getAttach()) {
				// Verifico se l'attach è un file timbrato
				if (attach.getFileNameAttach().contains("_timbrato")) {
					// Controllo se l'attach è già nella lista file della segnatura
					boolean isPresente = false;
					if (segnaturaListaTitoliAllegati != null) {
						for (int i = 0; i < segnaturaListaTitoliAllegati.getLength(); i++){
							String titoloAllegatoSegnatura = segnaturaListaTitoliAllegati.item(i).getTextContent();
							if (titoloAllegatoSegnatura.equals(attach.getFileNameAttach())){
								isPresente = true;
								break;
							}
						}
					}
					if (!isPresente) {
						// Se l'attach non è presente lo aggiunto alla losta di allegati da inserire nella segnatura
						Element nuovoAllegatoInSegnaturaElement = creaSegnaturaDocumentoAllegatiDescrizioneElement(segnaturaDocument, attach);
						segnaturaListaAllegatiDaAggiungere.add(nuovoAllegatoInSegnaturaElement);
					}
				}
			}
			// Inserisco tutti gli elementi mancanti
			if (!segnaturaListaAllegatiDaAggiungere.isEmpty()) {
				// Se il nodo Allegati nella segnatura non esiste lo creo
				if (segnaturaAllegatiNode == null) {
					Node segnaturaDescrizioneNode = ((segnaturaDocument.getElementsByTagName("Descrizione") != null) && (segnaturaDocument.getElementsByTagName("Descrizione").getLength() > 0)) ? segnaturaDocument.getElementsByTagName("Descrizione").item(0) : null;
					segnaturaAllegatiNode = creaSegnaturaAllegatiDescrizioneElement(segnaturaDocument, segnaturaDescrizioneNode);
				}
				// Aggiungo gli allegati mancanti al nodo allegati della segnatura
				aggiungiAllegatiInSegnatura(segnaturaAllegatiNode, segnaturaListaAllegatiDaAggiungere);
				// Creo l'xml della segnatura
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
			    Transformer transformer = transformerFactory.newTransformer();
			    StringWriter writer = new StringWriter();
			    StreamResult result = new StreamResult(writer);
			    DOMSource source = new DOMSource(segnaturaDocument);
			    transformer.transform(source, result);
			    xmlSegnatura = writer.toString();
			}
		}
		return xmlSegnatura;
	}
	
	private Element creaSegnaturaDocumentoAllegatiDescrizioneElement(Document segnaturaDocument, AttachmentUDBean attach) {
		Element nuovoAllegatoElement = segnaturaDocument.createElement("Documento");
		nuovoAllegatoElement.setAttribute("nome", attach.getFileNameAttach());
		nuovoAllegatoElement.setAttribute("tipoRiferimento", "MIME");
		Element titoloDocumentoElement = segnaturaDocument.createElement("TitoloDocumento");
		titoloDocumentoElement.setTextContent(attach.getFileNameAttach());
		Element noteDocumentoElement = segnaturaDocument.createElement("Note");
		noteDocumentoElement.setTextContent("Versione timbrata digitalmente con segnatura di registrazione");
		nuovoAllegatoElement.appendChild(titoloDocumentoElement);
		nuovoAllegatoElement.appendChild(noteDocumentoElement);
		return nuovoAllegatoElement;
	}
	
	private Element creaSegnaturaAllegatiDescrizioneElement(Document segnaturaDocument, Node segnaturaDescrizioneNode) {
		Element segnaturaAllegatiDescrizioneElement = segnaturaDocument.createElement("Allegati");
		segnaturaDescrizioneNode.appendChild(segnaturaAllegatiDescrizioneElement);
		return segnaturaAllegatiDescrizioneElement;
	}
	
	private void aggiungiAllegatiInSegnatura(Node allegatiNodeSegnatura, List<Node> listaAllegatiDaAggiungereASegnatura) {
		if (allegatiNodeSegnatura != null && listaAllegatiDaAggiungereASegnatura != null) {
			for (Node allegatoDaAggiungereASegnatura : listaAllegatiDaAggiungereASegnatura) {
				allegatiNodeSegnatura.appendChild(allegatoDaAggiungereASegnatura);
			}
		}
	}

	private String buildXmlDatiInvio(InvioUDMailBean pInvioUDMailBean) throws JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		GeneraSegnaturaXml lGeneraSegnaturaXml = new GeneraSegnaturaXml();
		List<InvioUDMailOutDestinatariBean> lListDestinatari = new ArrayList<InvioUDMailOutDestinatariBean>();
				
		if (pInvioUDMailBean.getDestinatariPec()!=null){
			for (DestinatariPecBean lDestinatariPecBean : pInvioUDMailBean.getDestinatariPec()) {
				InvioUDMailOutDestinatariBean lInvioMailUDdestinatariBean = new InvioUDMailOutDestinatariBean();
				lInvioMailUDdestinatariBean.setDestPrimario((lDestinatariPecBean.getDestPrimario() != null && lDestinatariPecBean.getDestPrimario()) ? Flag.SETTED
						: Flag.NOT_SETTED);
				lInvioMailUDdestinatariBean.setDestCC((lDestinatariPecBean.getDestCC() != null && lDestinatariPecBean.getDestCC()) ? Flag.SETTED : Flag.NOT_SETTED);
				lInvioMailUDdestinatariBean.setIdRubricaAuriga(lDestinatariPecBean.getIdRubricaAuriga());
				lInvioMailUDdestinatariBean.setIntestazione(lDestinatariPecBean.getIntestazione());
				lInvioMailUDdestinatariBean.setEmail(lDestinatariPecBean.getIndirizzoMail());
				lListDestinatari.add(lInvioMailUDdestinatariBean);
			}
		}		
		lGeneraSegnaturaXml.setListaDestinatari(lListDestinatari);
		lGeneraSegnaturaXml.setRichConferma((pInvioUDMailBean.getRichiestaConferma() != null && pInvioUDMailBean.getRichiestaConferma()) ? Flag.SETTED
				: Flag.NOT_SETTED);
		lGeneraSegnaturaXml.setIndirizzoMittente(pInvioUDMailBean.getMittente());
		
		// Aggiungo i destinatari e i destinatari in cc letti da GUI. Serve per le mail con maschera PEO ce hanno comunque la segnatura
		lGeneraSegnaturaXml.setIndirizziDestinatari(pInvioUDMailBean.getDestinatari());
		lGeneraSegnaturaXml.setIndirizziDestinatariCC(pInvioUDMailBean.getDestinatariCC());
		String lStrSegnaturaXml = new XmlUtilitySerializer().bindXml(lGeneraSegnaturaXml);
		return lStrSegnaturaXml;
	}

	private boolean getSegnatura(InvioUDMailOutBean lInvioMailUDoutBean) {
		for (InvioUDMailOutAttachmentBean lInvioMailUDattachmentBean : lInvioMailUDoutBean.getAttachments()) {
			if (lInvioMailUDattachmentBean.getNomeFile().equalsIgnoreCase("segnatura.xml")) {
				return true;
			}
		}
		return false;
	}

	public InvioMailResultBean invioMail(InvioUDMailBean pInvioUDMailBean) throws Exception {
		
		// Recupera la modalità del Form (invio mail normale o interop)
		String modalitaForm = (getExtraparams().get("modalitaForm") != null ? getExtraparams().get("modalitaForm") : "");
		
		long start = new Date().getTime();
		mLogger.error("Start invio mail");
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		//Controllo il body
		pInvioUDMailBean = checkBodyHtml(pInvioUDMailBean);
				
		String token = lAurigaLoginBean.getToken();
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro();

		SenderBean bean = new SenderBean();
		bean.setIdUtenteModPec(lAurigaLoginBean.getSpecializzazioneBean().getIdUtenteModPec());
		bean.setAccount(pInvioUDMailBean.getMittente());
		bean.setFlgInvioSeparato(pInvioUDMailBean.getFlgInvioSeparato() != null && pInvioUDMailBean.getFlgInvioSeparato() ? true : false);
		
		if(ParametriDBUtil.getParametroDB(getSession(), "CASELLE_ALIAS_UTENTE_IN_INVIO") != null
				&& !"".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "CASELLE_ALIAS_UTENTE_IN_INVIO"))) {
			String caselleAliasUtenteInvio = ParametriDBUtil.getParametroDB(getSession(), "CASELLE_ALIAS_UTENTE_IN_INVIO");
			StringSplitterServer mittenteDBSplit = new StringSplitterServer(caselleAliasUtenteInvio, ";");
			while (mittenteDBSplit.hasMoreElements()) {
				if(pInvioUDMailBean.getMittente().equalsIgnoreCase(mittenteDBSplit.nextToken().trim())) {
					if(StringUtils.isNotBlank(lAurigaLoginBean.getDelegaDenominazione())) {
						bean.setAliasAddressFrom(lAurigaLoginBean.getDelegaDenominazione());
						// Setto anche il Reply-To
						bean.setReplyTo(getEmailReplyTo(lAurigaLoginBean.getUseridForPrefs()));
					} else {
						bean.setAliasAddressFrom(lAurigaLoginBean.getSpecializzazioneBean().getDesUserOut());
						// Setto anche il Reply-To
						bean.setReplyTo(getEmailReplyTo(lAurigaLoginBean.getUserid()));
					}
					break;
				}
			} 
		}
		
		if (modalitaForm.equals("invio_mail")) {
			List<String> destinatari = new ArrayList<String>();
			String[] lStringDestinatari = IndirizziEmailSplitter.split(pInvioUDMailBean.getDestinatari());
			destinatari = Arrays.asList(lStringDestinatari);
			bean.setAddressTo(destinatari);
			if (StringUtils.isNotEmpty(pInvioUDMailBean.getDestinatariCC())) {
				List<String> destinatariCC = new ArrayList<String>();
				String[] lStringDestinatariCC = IndirizziEmailSplitter.split(pInvioUDMailBean.getDestinatariCC());
				destinatariCC = Arrays.asList(lStringDestinatariCC);
				bean.setAddressCc(destinatariCC);
			}
			if(pInvioUDMailBean.getTipoMail() != null && "PEO".equals(pInvioUDMailBean.getTipoMail())){
				if (StringUtils.isNotEmpty(pInvioUDMailBean.getDestinatariCCN())) {
					List<String> destinatariCCN = new ArrayList<String>();
					String[] lStringDestinatariCCN = IndirizziEmailSplitter.split(pInvioUDMailBean.getDestinatariCCN());
					destinatariCCN = Arrays.asList(lStringDestinatariCCN);
					bean.setAddressBcc(destinatariCCN);
				}
			}
		} else if (modalitaForm.equals("invio_mail_interop")) {
			boolean canSent = false;
			for (DestinatariPecBean lDestinatariPecBean : pInvioUDMailBean.getDestinatariPec()) {
				if ( (lDestinatariPecBean.getDestPrimario() != null && lDestinatariPecBean.getDestPrimario()) ||
					 (lDestinatariPecBean.getDestCC() != null && lDestinatariPecBean.getDestCC()) ){
					canSent = true;
				}
			}
			if (!canSent)
				throw new StoreException("Nessun destinatario selezionato");

			if (pInvioUDMailBean.getDestinatariPec().size() > 0) {
				List<String> destinatari = new ArrayList<String>();
				List<String> destinatariCC = new ArrayList<String>();
				for (DestinatariPecBean lDestinatariPecBean : pInvioUDMailBean.getDestinatariPec()) {
					if (lDestinatariPecBean.getDestPrimario() != null && lDestinatariPecBean.getDestPrimario()) {
						destinatari.add(lDestinatariPecBean.getIndirizzoMail());
					} else if (lDestinatariPecBean.getDestCC() != null && lDestinatariPecBean.getDestCC()) {
						destinatariCC.add(lDestinatariPecBean.getIndirizzoMail());
					}
				}
				if (destinatari.size() > 0)
					bean.setAddressTo(destinatari);
				if (destinatariCC.size() > 0)
					bean.setAddressCc(destinatariCC);
			}
		}
		long end = new Date().getTime();
		mLogger.error("Recupero destinatari " + (end - start));
		start = new Date().getTime();
		bean.setAddressFrom(pInvioUDMailBean.getMittente());
		List<SenderAttachmentsBean> lista = new ArrayList<SenderAttachmentsBean>();
		// Preparo gli attachment
		for (AttachmentUDBean lAttachmentUDBean : pInvioUDMailBean.getAttach()) {
			if (lAttachmentUDBean.getFileNameAttach().equalsIgnoreCase("segnatura.xml")) {
				SenderAttachmentsBean atta = new SenderAttachmentsBean();
				byte[] attachByte = IOUtils.toByteArray(new StringReader(retrieveSegnaturaAndStore(pInvioUDMailBean, lAurigaLoginBean)));
				
				boolean isSigilloXades = false;
				byte[] attachSigilloByte = null;
				
				if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_INTEROP_VER_2022")) {
					
					FirmaXadesBean lFirmaXadesBean = getFirmaXadesBean();

					FirmaXadesBean firmaXadesBean = new FirmaXadesBean();
					firmaXadesBean.setAlias(lFirmaXadesBean.getAlias());
					firmaXadesBean.setPin(lFirmaXadesBean.getPin());
					firmaXadesBean.setOtp(lFirmaXadesBean.getOtp());
					firmaXadesBean.setEndpoint(lFirmaXadesBean.getEndpoint());
					
					attachSigilloByte = new HsmXadesUtility().sigilloXades(attachByte, firmaXadesBean);
					isSigilloXades = true;
				}
				
				ByteArrayInputStream bis = null;
				if(isSigilloXades) {
					bis  = new ByteArrayInputStream(attachSigilloByte);
				} else {
					bis  = new ByteArrayInputStream(attachByte);
				}
				
				String uri = StorageImplementation.getStorage().storeStream(bis);				
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(uri);
				FileExtractedOut out = lRecuperoFile.extractfile(getLocale(), lAurigaLoginBean, lFileExtractedIn);			
				atta.setFile(out.getExtracted());
				atta.setFilename(lAttachmentUDBean.getFileNameAttach());
				atta.setFirmato(false);
				atta.setMimetype("application/xml");
				atta.setOriginalName(lAttachmentUDBean.getFileNameAttach());
				lista.add(atta);
			} else {
				SenderAttachmentsBean atta = new SenderAttachmentsBean();
				if (lAttachmentUDBean.getRemoteUri()) {
					RecuperoFile lRecuperoFile = new RecuperoFile();
					FileExtractedIn lFileExtractedIn = new FileExtractedIn();
					lFileExtractedIn.setUri(lAttachmentUDBean.getUriAttach());
					FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);
					atta.setFile(out.getExtracted());
				} else {
					atta.setFile(StorageImplementation.getStorage().extractFile(lAttachmentUDBean.getUriAttach()));
				}
				atta.setFilename(lAttachmentUDBean.getFileNameAttach());
				lista.add(atta);
				atta.setFirmato(lAttachmentUDBean.getFirmato());
				atta.setMimetype(lAttachmentUDBean.getMimetype());
				atta.setOriginalName(lAttachmentUDBean.getFileNameAttach());
			}

		}
		end = new Date().getTime();
		mLogger.error("Recupero files " + (end - start));

		bean.setAttachments(lista);
		bean.setSubject(pInvioUDMailBean.getOggetto());
		
		//Il body che arriva è da interpretare come html
		bean.setIsHtml(true);
		if( pInvioUDMailBean.getBody() != null ){
			bean.setBody("<html>" + pInvioUDMailBean.getBody() + "</html>");
		}else {
			bean.setBody("<html></html>");
		}
		
		if (modalitaForm.equals("invio_mail_interop")) {
			DmpkIntMgoEmailAggdestinvioinrubricaemailBean lDmpkIntMgoEmailAggdestinvioinrubricaemailBean = new DmpkIntMgoEmailAggdestinvioinrubricaemailBean();
			lDmpkIntMgoEmailAggdestinvioinrubricaemailBean.setCodidconnectiontokenin(token);
			lDmpkIntMgoEmailAggdestinvioinrubricaemailBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			lDmpkIntMgoEmailAggdestinvioinrubricaemailBean.setIdudin(new BigDecimal(pInvioUDMailBean.getIdUD()));
			String xmlDestInvioMail = getXmlDestInvioMail(pInvioUDMailBean);
			lDmpkIntMgoEmailAggdestinvioinrubricaemailBean.setXmldestinviomailin(xmlDestInvioMail);
			DmpkIntMgoEmailAggdestinvioinrubricaemail lDmpkIntMgoEmailAggdestinvioinrubricaemail = new DmpkIntMgoEmailAggdestinvioinrubricaemail();
			StoreResultBean<DmpkIntMgoEmailAggdestinvioinrubricaemailBean> lDmpkIntMgoEmailAggdestinvioinrubricaemailResult = lDmpkIntMgoEmailAggdestinvioinrubricaemail
					.execute(getLocale(), lAurigaLoginBean, lDmpkIntMgoEmailAggdestinvioinrubricaemailBean);
			if (lDmpkIntMgoEmailAggdestinvioinrubricaemailResult.isInError()) {
				addMessage(
						"Non è stato possibile effettuare in maniera completa l'inserimento/aggiornamento in rubrica indirizzi di eventuali destinatari compilati ex-novo",
						"", MessageType.WARNING);
			}
		}

		if (pInvioUDMailBean.getConfermaLetturaShow() != null && pInvioUDMailBean.getConfermaLetturaShow() && pInvioUDMailBean.getConfermaLettura() != null
				&& pInvioUDMailBean.getConfermaLettura()) {
			bean.setReturnReceipt(true);
		}

		if (pInvioUDMailBean.getRichiestaConferma() != null && pInvioUDMailBean.getRichiestaConferma()) {
			bean.setRichiestaConfermaInteroperabile(true);
		}
		start = new Date().getTime();
		
		if(verificaInvioSeparato(pInvioUDMailBean)){
		
			ResultBean<EmailSentReferenceBean> output = AurigaMailService.getMailSenderService().sendandsave(getLocale(), bean);
			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (output.isInError()) {
					throw new StoreException(output.getDefaultMessage());
				} else {
					addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
				}
			}

			end = new Date().getTime();
			mLogger.error("Invio mail " + (end - start));
			start = end;
			EmailSentReferenceBean lEmailSentReferenceBean = output.getResultBean();
	
			DmpkUtilityGetestremiregnumud_jBean lDmpkUtilityGetestremiregnumud_jBean = new DmpkUtilityGetestremiregnumud_jBean();
			lDmpkUtilityGetestremiregnumud_jBean.setIdudin(new BigDecimal(pInvioUDMailBean.getIdUD()));
			SchemaBean lSchemaBean = new SchemaBean();
			lSchemaBean.setSchema(lAurigaLoginBean.getSchema());
			DmpkUtilityGetestremiregnumud_j lGetestremiregnumud_j = new DmpkUtilityGetestremiregnumud_j();
			StoreResultBean<DmpkUtilityGetestremiregnumud_jBean> result = lGetestremiregnumud_j.execute(getLocale(), lSchemaBean,
					lDmpkUtilityGetestremiregnumud_jBean);
			end = new Date().getTime();
			mLogger.error("Recupero registrazione " + (end - start));
			start = end;
			if (result.isInError()) {
				mLogger.debug("Andata in errore ");
				mLogger.debug(result.getDefaultMessage());
				mLogger.debug(result.getErrorContext());
				mLogger.debug(result.getErrorCode());
				throw new StoreException(result);
			}
			Integer anno = result.getResultBean().getAnnoregout();
			Integer numero = result.getResultBean().getNumregout().intValue();
			Date data = result.getResultBean().getTsregout();
	
			MailProcessorService lMailProcessorService = new MailProcessorService();
			for (String lStrIdEmail : lEmailSentReferenceBean.getIdEmails()) {
				start = end;
				RegistrazioneProtocolloBean lRegistrazioneProtocolloBean = new RegistrazioneProtocolloBean();
				lRegistrazioneProtocolloBean.setIdEmail(lStrIdEmail);
				MailLoginBean lMailLoginBean = new MailLoginBean();
				lMailLoginBean.setSchema(lAurigaLoginBean.getSchema());
				lMailLoginBean.setToken(lAurigaLoginBean.getToken());
				lMailLoginBean.setUserId(lAurigaLoginBean.getIdUserLavoro());
				lRegistrazioneProtocolloBean.setLoginBean(lMailLoginBean);
				RegistrazioneProtocollo lRegistrazioneProtocollo = new RegistrazioneProtocollo();
				lRegistrazioneProtocollo.setIdProvReg(pInvioUDMailBean.getIdUD());
				// BOZZA
				if (StringUtils.isBlank(pInvioUDMailBean.getFlgTipoProv())) {
					lRegistrazioneProtocollo.setSiglaRegistro("N.I.");
					lRegistrazioneProtocollo.setCategoriaReg("I");
				}
				// Registrazione ENTRATA o USCITA
				else if (pInvioUDMailBean.getFlgTipoProv().equalsIgnoreCase("E") || pInvioUDMailBean.getFlgTipoProv().equalsIgnoreCase("U")) {
					lRegistrazioneProtocollo.setSiglaRegistro(null);
					lRegistrazioneProtocollo.setCategoriaReg("PG");
				}
				// Registrazione INTERNA/TRA UFFICI
				else {
					lRegistrazioneProtocollo.setSiglaRegistro("P.I.");
					lRegistrazioneProtocollo.setCategoriaReg("I");
				}
				lRegistrazioneProtocollo.setAnnoReg(anno.shortValue());
				lRegistrazioneProtocollo.setNumReg(numero != null ? new BigDecimal(numero) : null);
				Calendar lGregorianCalendar = GregorianCalendar.getInstance();
				lGregorianCalendar.setTime(data);
				lRegistrazioneProtocollo.setDataRegistrazione(lGregorianCalendar);
				lRegistrazioneProtocolloBean.setRegistrazione(lRegistrazioneProtocollo);
				List<ProtocolloAttachmentBean> attachmentsProtocollati = new ArrayList<ProtocolloAttachmentBean>();
				lRegistrazioneProtocolloBean.setAttachmentsProtocollati(attachmentsProtocollati);
				ResultBean<InfoRelazioneProtocolloBean> lCreaRelazioneProtocolloResult = lMailProcessorService.crearelazioneprotocollo(getLocale(),
						lRegistrazioneProtocolloBean);
				end = new Date().getTime();
				mLogger.debug("Crea relazione protocollo per la mail " + lStrIdEmail + " " + (end - start));
				start = end;
	
				if (lCreaRelazioneProtocolloResult.getDefaultMessage() != null) {
					if (lCreaRelazioneProtocolloResult.isInError()) {
						throw new StoreException(lCreaRelazioneProtocolloResult.getDefaultMessage());
					} else {
						addMessage(lCreaRelazioneProtocolloResult.getDefaultMessage(), "", MessageType.WARNING);
					}
				}
	
				ResultBean<StatoConfermaAutomaticaBean> lInviaConfermaAutomaticaResult = lMailProcessorService.inviaconfermaautomatica(getLocale(),
						lRegistrazioneProtocolloBean);
	
				if (lInviaConfermaAutomaticaResult.getDefaultMessage() != null) {
					if (lInviaConfermaAutomaticaResult.isInError()) {
						throw new StoreException(lInviaConfermaAutomaticaResult.getDefaultMessage());
					} else {
						addMessage(lInviaConfermaAutomaticaResult.getDefaultMessage(), "", MessageType.WARNING);
					}
				}
				end = new Date().getTime();
				mLogger.error("Invio conferma automatica " + lStrIdEmail + " " + (end - start));
				start = end;
			}
		}else{
			throw new StoreException(invioSeparatoMessage);
		}
		return new InvioMailResultBean();
	}

	protected String getXmlDestInvioMail(InvioUDMailBean pInvioUDMailBean) throws JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		String xmlDestInvioMail;
		List<DestInvioMailXmlBean> lListDestInvioMail = new ArrayList<DestInvioMailXmlBean>();
		for (DestinatariPecBean lDestinatariPecBean : pInvioUDMailBean.getDestinatariPec()) {
			if((lDestinatariPecBean.getDestPrimario() != null && lDestinatariPecBean.getDestPrimario()) ||
			   (lDestinatariPecBean.getDestCC() != null && lDestinatariPecBean.getDestCC()) ) {
				DestInvioMailXmlBean lDestInvioMailXmlBean = new DestInvioMailXmlBean();
				lDestInvioMailXmlBean.setIdRubrica(lDestinatariPecBean.getIdRubricaAuriga());
				lDestInvioMailXmlBean.setDenominazione(lDestinatariPecBean.getIntestazione());
				lDestInvioMailXmlBean.setIndirizzoMail(lDestinatariPecBean.getIndirizzoMail());
				lListDestInvioMail.add(lDestInvioMailXmlBean);
			}
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlDestInvioMail = lXmlUtilitySerializer.bindXmlList(lListDestInvioMail);
		return xmlDestInvioMail;
	}

	public SimpleBean<String> downloadSegnatura(InvioUDMailBean pInvioUDMailBean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		InputStream lInputStream = IOUtils.toInputStream(retrieveSegnaturaAndStore(pInvioUDMailBean, lAurigaLoginBean));
		
		boolean isSigilloXades = false;
		byte[] attachSigilloByte = null;
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_INTEROP_VER_2022")) {
			
			FirmaXadesBean lFirmaXadesBean = getFirmaXadesBean();

			FirmaXadesBean firmaXadesBean = new FirmaXadesBean();
			firmaXadesBean.setAlias(lFirmaXadesBean.getAlias());
			firmaXadesBean.setPin(lFirmaXadesBean.getPin());
			firmaXadesBean.setOtp(lFirmaXadesBean.getOtp());
			firmaXadesBean.setEndpoint(lFirmaXadesBean.getEndpoint());
			
			attachSigilloByte = new HsmXadesUtility().sigilloXades(IOUtils.toByteArray(lInputStream), firmaXadesBean);
			isSigilloXades = true;
		}
		
		String lStrUri = null;
		if(isSigilloXades) {
			InputStream isXades = new ByteArrayInputStream(attachSigilloByte);
			lStrUri = StorageImplementation.getStorage().storeStream(isXades);
		} else {
			lStrUri = StorageImplementation.getStorage().storeStream(lInputStream);
		}
		
		SimpleBean<String> result = new SimpleBean<String>();
		result.setValue(lStrUri);
		return result;
	}
	
	public InvioUDMailBean caricaMailDocumentoIstruttoria(AllegatoProtocolloBean lAllegatoProtocolloBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		InvioUDMailBean lInvioUDMailBean = new InvioUDMailBean();
		lInvioUDMailBean.setTipoMail(getExtraparams().get("tipoMail"));
		boolean usaStorePreparaMail = Boolean.parseBoolean(getExtraparams().get("usaStorePreparaMail"));
		lInvioUDMailBean.setIdUD(lAllegatoProtocolloBean.getIdUdAppartenenza());
		
		String indirizzoEmailContribuente = getExtraparams().get("indirizzoEmailContribuente");
		if(StringUtils.isNotBlank(indirizzoEmailContribuente)) {
			lInvioUDMailBean.setDestinatari(indirizzoEmailContribuente);
		}
		
		if (usaStorePreparaMail) {
			String token = loginBean.getToken();
			String idUserLavoro = loginBean.getIdUserLavoro();

			String finalita = getExtraparams().get("finalita");
			Integer flgInvioPecIn = null;
			String tipoMail = getExtraparams().get("tipoMail");
			String pecMulti = getExtraparams().get("PEC_MULTI") != null ? getExtraparams().get("PEC_MULTI") : "";
			if(tipoMail.equals("PEO")) {
				flgInvioPecIn = 0;
			} else if(tipoMail.equals("PEC") && pecMulti.equals("1")) {
				flgInvioPecIn = 2;
			} else {
				flgInvioPecIn = 1;
			}

			DmpkIntMgoEmailPreparainvioudxemail store = new DmpkIntMgoEmailPreparainvioudxemail();
			DmpkIntMgoEmailPreparainvioudxemailBean input = new DmpkIntMgoEmailPreparainvioudxemailBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdudin(new BigDecimal(lAllegatoProtocolloBean.getIdUdAppartenenza()));
			input.setFlginviopecin(flgInvioPecIn);

			if (StringUtils.isNotBlank(finalita)) {
				input.setFinalitain(finalita);
			}

			// Invio pec ???
			StoreResultBean<DmpkIntMgoEmailPreparainvioudxemailBean> lResult = store.execute(getLocale(), loginBean, input);
			if (StringUtils.isNotBlank(lResult.getDefaultMessage())) {
				if (lResult.isInError()) {
					mLogger.error("Errore nel recupero dell'output: " + lResult.getDefaultMessage());
					throw new StoreException(lResult);
				} else {
					addMessage(lResult.getDefaultMessage(), "", MessageType.WARNING);
				}
			}

			DmpkIntMgoEmailPreparainvioudxemailBean output = lResult.getResultBean();
			InvioUDMailOutBean lInvioMailUDoutBean = new XmlUtilityDeserializer().unbindXml(output.getXmldatiinviomailout(), InvioUDMailOutBean.class);

			// Setto il tipo
			lInvioUDMailBean.setTipoMail(getExtraparams().get("tipoMail"));
			if (lInvioMailUDoutBean.getDestinatariList() != null) {
				List<DestinatariPecBean> lListDestinatariPEC = new ArrayList<DestinatariPecBean>();
				for (InvioUDMailOutDestinatariBean lInvioMailUDdestinatariBean : lInvioMailUDoutBean.getDestinatariList()) {
					DestinatariPecBean lDestinatariPecBean = new DestinatariPecBean();
					lDestinatariPecBean.setDestPrimario(lInvioMailUDdestinatariBean.getDestPrimario() == Flag.SETTED ? true : false);
					lDestinatariPecBean.setDestCC(lInvioMailUDdestinatariBean.getDestCC() == Flag.SETTED ? true : false);
					lDestinatariPecBean.setTipoDestinatario(lInvioMailUDdestinatariBean.getTipoDestinatario());
					lDestinatariPecBean.setIdRubricaAuriga(lInvioMailUDdestinatariBean.getIdRubricaAuriga());
					lDestinatariPecBean.setIntestazione(lInvioMailUDdestinatariBean.getIntestazione());
					lDestinatariPecBean.setCodiciIpa(lInvioMailUDdestinatariBean.getCodiciIpa());
					lDestinatariPecBean.setCipaPec(lInvioMailUDdestinatariBean.getCipaPec());
					lDestinatariPecBean.setIndirizzoMail(lInvioMailUDdestinatariBean.getEmail());
					lDestinatariPecBean.setIdMailInviata(lInvioMailUDdestinatariBean.getIdMailInviata());
					lDestinatariPecBean.setUriMail(lInvioMailUDdestinatariBean.getUriMail());
					lDestinatariPecBean.setDataOraMail(lInvioMailUDdestinatariBean.getDataOraMail());
					lDestinatariPecBean.setStatoMail(lInvioMailUDdestinatariBean.getStatoMail());
					lDestinatariPecBean.setIdInRubricaEmail(lInvioMailUDdestinatariBean.getIdInRubricaEmail());
					lListDestinatariPEC.add(lDestinatariPecBean);
				}
				lInvioUDMailBean.setDestinatariPec(lListDestinatariPEC);
			}

			List<AttachmentUDBean> lListAttachments = new ArrayList<AttachmentUDBean>();
			List<String> fileDaTimbrareNonConvertiti = new ArrayList<String>();
			for (InvioUDMailOutAttachmentBean lInvioMailUDattachmentBean : lInvioMailUDoutBean.getAttachments()) {
				if (lInvioMailUDattachmentBean.getDaTimbrare() != null)
					mLogger.debug("Attachment da timbrare: name = " + lInvioMailUDattachmentBean.getDaTimbrare().name() + " dbValue = "
							+ lInvioMailUDattachmentBean.getDaTimbrare().getDbValue());
				if (lInvioMailUDattachmentBean.getDaTimbrare() == Flag.SETTED) {
					mLogger.debug("L'attachment " + lInvioMailUDattachmentBean.getNomeFile() + " è da timbrare");
					generaFileTimbrato(lInvioMailUDattachmentBean, lListAttachments, fileDaTimbrareNonConvertiti, lAllegatoProtocolloBean.getIdUdAppartenenza());
				} else {
					AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lInvioMailUDattachmentBean);
					lListAttachments.add(lAttachmentUDBean);
				}
			}

			lInvioUDMailBean.setMittente(lInvioMailUDoutBean.getCasellaMittente());
			lInvioUDMailBean.setIdCasellaMittente(lInvioMailUDoutBean.getIdCasellaMittente());
			lInvioUDMailBean.setAttach(lListAttachments);
			lInvioUDMailBean.setOggetto(lInvioMailUDoutBean.getOggetto());
			lInvioUDMailBean.setBodyHtml(lInvioMailUDoutBean.getBody());  
			lInvioUDMailBean.setDestinatari(lInvioMailUDoutBean.getDestinatari());
			lInvioUDMailBean.setDestinatariCC(lInvioMailUDoutBean.getDestinatariCC());
			lInvioUDMailBean.setSegnaturaPresente(getSegnatura(lInvioMailUDoutBean));
			lInvioUDMailBean.setSalvaInviati(lInvioMailUDoutBean.getFlagSalvaInviati() == Flag.SETTED ? true : false);
			lInvioUDMailBean.setRichiestaConferma(lInvioMailUDoutBean.getRichiestaConferma() == Flag.SETTED ? true : false);
			lInvioUDMailBean.setConfermaLetturaShow(lInvioMailUDoutBean.getConfermaLetturaShow() == Flag.SETTED ? true : false);
			lInvioUDMailBean.setConfermaLettura(lInvioMailUDoutBean.getConfermaLettura() == Flag.SETTED ? true : false);
			lInvioUDMailBean.setAvvertimenti(lInvioMailUDoutBean.getAvvertimenti());
			lInvioUDMailBean.setIdUD(lAllegatoProtocolloBean.getIdUdAppartenenza());
//			lInvioUDMailBean.setFlgTipoProv(lAllegatoProtocolloBean.getFlgTipoProv());

			if (fileDaTimbrareNonConvertiti != null && fileDaTimbrareNonConvertiti.size() > 0) {
				boolean first = true;
				StringBuffer lStringBuffer = new StringBuffer("Il formato del/i file ");
				if (first)
					first = false;
				else
					lStringBuffer.append(";");
				for (String lString : fileDaTimbrareNonConvertiti) {
					lStringBuffer.append(lString);
				}
				lStringBuffer.append(" non consente di apporvi il timbro con la segnatura");
				addMessage(lStringBuffer.toString(), lStringBuffer.toString(), MessageType.WARNING);
			}

//			lInvioUDMailBean.setIdMailPartenza(lAllegatoProtocolloBean.getIdEmailArrivo());

		} else {
		
			List<AttachmentUDBean> lListAttachments = new ArrayList<AttachmentUDBean>();
			List<String> fileNonConvertiti = new ArrayList<String>();
			List<String> fileNonTimbrati = new ArrayList<String>();
			if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getEstremiProtUd()) && lAllegatoProtocolloBean.getInfoFile() !=  null && lAllegatoProtocolloBean.getInfoFile().isFirmato()) {
				// Il file è protocollato e firmato
				// Aggiungo nell'oggeto gli estremi di protocollo
				//FIXME l'id in lInvioUDMailBean è corretta? Credo vada messa quella del allegato (lAllegatoProtocolloBean.getEstremiProtUd())
				//FIXME Mettere estremi di protocollo
				lInvioUDMailBean.setOggetto("Protocollo " + lAllegatoProtocolloBean.getEstremiProtUd());
				// Aggiungo il file firmato e la versione sbustata timbrata con gli estemi di protocollo
				// Aggiungo il file firmato
				lListAttachments.add(getAttachmentOriginale(lAllegatoProtocolloBean));
				// Sbusto il file per protocollarlo
				AttachmentUDBean lAttachmentUDBean = sbustaFilePerInvioMail(lAllegatoProtocolloBean);
				if ("application/pdf".equalsIgnoreCase(lAttachmentUDBean.getMimetype()) || lAllegatoProtocolloBean.getInfoFile().isConvertibile()) {
					// Timbro il file se pdf o covertibile
					if (!"application/pdf".equalsIgnoreCase(lAttachmentUDBean.getMimetype())){
						// Se non è pdf lo converto
						File lFile = recuperaFilePerInvioMail(lAllegatoProtocolloBean, loginBean);
						lAttachmentUDBean = convertiInPdfPerInvioMail(lAllegatoProtocolloBean, lFile);
						try {
							//FIXME I dati di timbratura devono riguardare il protocollo dell'allegato
							timbraAttach(lAllegatoProtocolloBean.getIdUdAppartenenza(), lAttachmentUDBean);
							// Cambio il nome del file timbrato per distinguerlo da quello originale
							String nomefileTimbrato = lAttachmentUDBean.getFileNameAttach();
							lAttachmentUDBean.setFileNameAttach(FilenameUtils.getBaseName(nomefileTimbrato) + "_timbrato." +  FilenameUtils.getExtension(nomefileTimbrato));  
						}catch(Exception e) {
							mLogger.warn("Si è verificato un errore durante la timbratura del file: " + e.getMessage(), e);
							fileNonTimbrati.add(lAttachmentUDBean.getFileNameAttach());
						}
					}
					lListAttachments.add(lAttachmentUDBean);
				} else {
					mLogger.warn("Il file non è un pdf e non è convertibile, quindi lo aggiungo nel suo formato originale");
					fileNonConvertiti.add(lAllegatoProtocolloBean.getNomeFileAllegato());
					lListAttachments.add(getAttachmentOriginale(lAllegatoProtocolloBean));
				}
			} else if (lAllegatoProtocolloBean.getInfoFile().getMimetype().equals("application/pdf")) {
				mLogger.info("Il file è un pdf, quindi lo aggiungo nel suo formato originale");
				AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lAllegatoProtocolloBean);
				try {
					timbraAttach(lAllegatoProtocolloBean.getIdUdAppartenenza(), lAttachmentUDBean);
				}catch(Exception e) {
					mLogger.warn("Si è verificato un errore durante la timbratura del file: " + e.getMessage(), e);
					fileNonTimbrati.add(lAttachmentUDBean.getFileNameAttach());
				}
				lListAttachments.add(lAttachmentUDBean);
			} else if (lAllegatoProtocolloBean.getInfoFile().isFirmato()) {
				mLogger.warn("Il file non è un pdf ed è firmato, quindi lo aggiungo nel suo formato originale senza convertirlo");
				fileNonConvertiti.add(lAllegatoProtocolloBean.getNomeFileAllegato());
				lListAttachments.add(getAttachmentOriginale(lAllegatoProtocolloBean));
			} else if (!lAllegatoProtocolloBean.getInfoFile().isConvertibile()) {
				mLogger.warn("Il file non è un pdf e non è convertibile, quindi lo aggiungo nel suo formato originale");
				fileNonConvertiti.add(lAllegatoProtocolloBean.getNomeFileAllegato());
				lListAttachments.add(getAttachmentOriginale(lAllegatoProtocolloBean));
			} else {
				mLogger.debug("Il file non è un pdf ed è convertibile, quindi provo a convertirlo");
				File lFile = recuperaFilePerInvioMail(lAllegatoProtocolloBean, loginBean);			
				try {
					AttachmentUDBean lAttachmentUDBean = convertiInPdfPerInvioMail(lAllegatoProtocolloBean, lFile);
					try {
						timbraAttach(lAllegatoProtocolloBean.getIdUdAppartenenza(), lAttachmentUDBean);
					}catch(Exception e) {
						mLogger.warn("Si è verificato un errore durante la timbratura del file: " + e.getMessage(), e);
						fileNonTimbrati.add(lAttachmentUDBean.getFileNameAttach());
					}
					lListAttachments.add(lAttachmentUDBean);
				} catch (Exception e) {
					mLogger.warn("Non sono riuscito a convertire il file, quindi lo aggiungo nel suo formato originale. Errore di conversione del file: " + e.getMessage(), e);
					fileNonConvertiti.add(lAllegatoProtocolloBean.getNomeFileAllegato());
					lListAttachments.add(getAttachmentOriginale(lAllegatoProtocolloBean));
				}
			}
			lInvioUDMailBean.setAttach(lListAttachments);
			// Se il file non è pdf ed è firmato o non convertibile mando un messaggio di warning
			if (fileNonConvertiti != null && fileNonConvertiti.size() > 0) {
				boolean first = true;
				StringBuffer lStringBuffer = new StringBuffer("Il formato del/i file ");
				if (first) {
					first = false;
				} else {
					lStringBuffer.append(";");
				}
				for (String lString : fileNonConvertiti) {
					lStringBuffer.append(lString);
				}
				lStringBuffer.append(" non è convertibile in pdf");
				addMessage(lStringBuffer.toString(), "", MessageType.WARNING);
			}
			if (fileNonTimbrati != null && fileNonTimbrati.size() > 0) {			
				if(fileNonTimbrati.size() == 1) {
					addMessage("Il file " + fileNonTimbrati.get(0) + " non è stato timbrato", "", MessageType.WARNING);
				} else {
					boolean first = true;
					StringBuffer lStringBuffer = new StringBuffer("I file ");
					if (first) {
						first = false;
					} else {
						lStringBuffer.append(";");
					}
					for (String lString : fileNonTimbrati) {
						lStringBuffer.append(lString);
					}
					lStringBuffer.append(" non sono stati timbrati");
					addMessage(lStringBuffer.toString(), "", MessageType.WARNING);
				}						
			}
		}
		
		return lInvioUDMailBean;
	}

	private AttachmentUDBean sbustaFilePerInvioMail(AllegatoProtocolloBean lAllegatoProtocolloBean) throws StorageException, Exception {
		File lFile = StorageImplementation.getStorage().getRealFile(lAllegatoProtocolloBean.getUriFileAllegato());
		String nomeFile = lAllegatoProtocolloBean.getInfoFile().getCorrectFileName() != null ? lAllegatoProtocolloBean.getInfoFile().getCorrectFileName() : "";
		String nomeFileSbustato = (nomeFile != null && nomeFile.toLowerCase().endsWith(".p7m")) ? nomeFile.substring(0, nomeFile.length() - 4) : nomeFile;
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		String uriSbustato = StorageImplementation.getStorage().storeStream(lInfoFileUtility.sbusta(lFile.toURI().toString(), nomeFile));
		AttachmentUDBean lAttachmentUDBean = new AttachmentUDBean();
		lAttachmentUDBean.setFileNameAttach(nomeFileSbustato);
		lAttachmentUDBean.setUriAttach(uriSbustato);
		lAttachmentUDBean.setRemoteUri(false);
		lAttachmentUDBean.setFirmato(false);
		lAttachmentUDBean.setMimetype(lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null).getMimetype());
		return lAttachmentUDBean;
	}
	
	private File recuperaFilePerInvioMail(AllegatoProtocolloBean lAllegatoProtocolloBean, AurigaLoginBean lAurigaLoginBean) throws StorageException {
		if (lAllegatoProtocolloBean.getRemoteUri() != null && lAllegatoProtocolloBean.getRemoteUri()) {
			// File remoto
			RecuperoFile lRecuperoFile = new RecuperoFile();
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(lAllegatoProtocolloBean.getUriFileAllegato());
			FileExtractedOut out = lRecuperoFile.extractfile(getLocale(), lAurigaLoginBean, lFileExtractedIn);
			return StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));
		} else {
			// File locale
			return StorageImplementation.getStorage().extractFile(lAllegatoProtocolloBean.getUriFileAllegato());
		}			
	}
	
	private AttachmentUDBean convertiInPdfPerInvioMail(AllegatoProtocolloBean lAllegatoProtocolloBean, File lFile) throws StorageException, Exception {
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		String nomeFile = lAllegatoProtocolloBean.getInfoFile().getCorrectFileName() != null ? lAllegatoProtocolloBean.getInfoFile().getCorrectFileName() : "";
		String uriPdf = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(lFile.toURI().toString(), nomeFile));
		AttachmentUDBean lAttachmentUDBean = new AttachmentUDBean();
		lAttachmentUDBean.setFileNameAttach(FilenameUtils.getBaseName(nomeFile) + ".pdf");
		lAttachmentUDBean.setUriAttach(uriPdf);
		lAttachmentUDBean.setRemoteUri(false);
		lAttachmentUDBean.setFirmato(false);
		lAttachmentUDBean.setMimetype("application/pdf");
		return lAttachmentUDBean;
	}
	
	protected void timbraAttach(String idUd, AttachmentUDBean lAttachmentUDBean) throws Exception {
		
		if(StringUtils.isNotBlank(idUd) && lAttachmentUDBean != null && lAttachmentUDBean.getMimetype() != null && "application/pdf".equalsIgnoreCase(lAttachmentUDBean.getMimetype())) {
			// timbra
			OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
			lOpzioniTimbroBean.setMimetype("application/pdf");
			lOpzioniTimbroBean.setUri(lAttachmentUDBean.getUriAttach());
			lOpzioniTimbroBean.setNomeFile(lAttachmentUDBean.getFileNameAttach());
			lOpzioniTimbroBean.setIdUd(idUd);
			TimbraUtility timbraUtility = new TimbraUtility();
			lOpzioniTimbroBean = timbraUtility.loadSegnatureRegistrazioneDefault(lOpzioniTimbroBean, getSession(), getLocale());

			// Setto i parametri del timbro utilizzando dal config.xml il bean OpzioniTimbroAutoDocRegBean
			try{
				OpzioniTimbroAttachEmail lOpzTimbroAutoDocRegBean = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAutoDocRegBean");
				if(lOpzTimbroAutoDocRegBean != null){
					lOpzioniTimbroBean.setPosizioneTimbro(lOpzTimbroAutoDocRegBean.getPosizioneTimbro() != null &&
							!"".equals(lOpzTimbroAutoDocRegBean.getPosizioneTimbro()) ? lOpzTimbroAutoDocRegBean.getPosizioneTimbro().value() : "altoSn");
					lOpzioniTimbroBean.setRotazioneTimbro(lOpzTimbroAutoDocRegBean.getRotazioneTimbro() != null &&
							!"".equals(lOpzTimbroAutoDocRegBean.getRotazioneTimbro()) ? lOpzTimbroAutoDocRegBean.getRotazioneTimbro().value() : "verticale");
					if (lOpzTimbroAutoDocRegBean.getPaginaTimbro() != null) {
						if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina() != null) {
							lOpzioniTimbroBean.setTipoPagina(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina().value());
						} else if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine() != null) {
							lOpzioniTimbroBean.setTipoPagina("intervallo");
							if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa() != null) {
								lOpzioniTimbroBean.setPaginaDa(String.valueOf(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa()));
							}
							if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa() != null) {
								lOpzioniTimbroBean.setPaginaA(String.valueOf(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaA()));
							}
						}
					}
					lOpzioniTimbroBean.setTimbroSingolo(lOpzTimbroAutoDocRegBean.isTimbroSingolo());
					lOpzioniTimbroBean.setMoreLines(lOpzTimbroAutoDocRegBean.isRigheMultiple());
				}
			} catch (NoSuchBeanDefinitionException e) {
				/**
				 * Se il Bean OpzioniTimbroAutoDocRegBean non è correttamente configurato vengono utilizzare le preference del 
				 * bean OpzioniTimbroAttachEmail affinchè la timbratura vada a buon fine.
				 */
				mLogger.warn("OpzioniTimbroAutoDocRegBean non definito nel file di configurazione");
			}
			
			// Timbro il file
			TimbraResultBean lTimbraResultBean = timbraUtility.timbra(lOpzioniTimbroBean, getSession());
			// Verifico se la timbratura è andata a buon fine
			if (lTimbraResultBean.isResult()) {
				// Aggiungo il file timbrato
				lAttachmentUDBean.setUriAttach(lTimbraResultBean.getUri());
			} else {
				// La timbratura è fallita
				String errorMessage = "Si è verificato un errore durante la timbratura del file";
				if (StringUtils.isNotBlank(lTimbraResultBean.getError())) {
					errorMessage += ": " + lTimbraResultBean.getError();
				}
				throw new Exception(errorMessage);
			}
		}
	}
	
	protected AttachmentUDBean getAttachmentOriginale(AllegatoProtocolloBean lAllegatoProtocolloBean) {
		
		AttachmentUDBean lAttachmentUDBean = new AttachmentUDBean();
		lAttachmentUDBean.setFileNameAttach(lAllegatoProtocolloBean.getNomeFileAllegato());
		lAttachmentUDBean.setUriAttach(lAllegatoProtocolloBean.getUriFileAllegato());
		lAttachmentUDBean.setRemoteUri(lAllegatoProtocolloBean.getRemoteUri());
		lAttachmentUDBean.setFirmato(lAllegatoProtocolloBean.getInfoFile().isFirmato());
		lAttachmentUDBean.setMimetype(lAllegatoProtocolloBean.getInfoFile().getMimetype());
		return lAttachmentUDBean;
	}

	private InvioUDMailBean checkBodyHtml(InvioUDMailBean mailBean) {
		/*
		 * Richiamo il datasource in cui è definito il metodo che controlla se effettivamente
		 * il body deve essere vuoto o meno.
		 * Se ad esempio il body è formato solamente da <!-- commenti --> o <div>..... allora deve
		 * essere vuoto 
		 */
		CorpoMailDataSource corpoMailDataSource = new CorpoMailDataSource();
		
		String newBodyHtml = "";
		try {
			newBodyHtml = corpoMailDataSource.removeMarker(mailBean.getBody());
			newBodyHtml = corpoMailDataSource.checkBodyHtml(newBodyHtml);
			
			//Espressione regolare utilizzata per rimuovere i caratteri speciali non decodificabili nei vari charset
			newBodyHtml = newBodyHtml.replaceAll("\\p{C}", " ");
		} catch (Exception e) {
			mLogger.error("Errore InvioUDMailDS - checkBodyHtml", e);
		}
		
		mailBean.setBody(newBodyHtml);
		
		return mailBean;
	}
	
	/**
	 * Viene verificato l'invio separato della mail, se il chek flgInvioSeparato è spuntato e sono presenti
	 * destinatari in CC, l'invio della stessa non è consentito.
	 */
	private Boolean verificaInvioSeparato(InvioUDMailBean bean){
		
		Boolean verify = true;
		if(bean != null){
			if(bean.getFlgInvioSeparato() != null && bean.getFlgInvioSeparato()){
				if(bean.getDestinatariCC() != null && !"".equalsIgnoreCase(bean.getDestinatariCC())){
					verify = false;
				}
				if(bean.getDestinatariPec() != null && !bean.getDestinatariPec().isEmpty()){
					for(DestinatariPecBean item : bean.getDestinatariPec()){
						if(item != null && item.getDestCC() != null && item.getDestCC()){
							if(item.getIndirizzoMail() != null && !"".equals(item.getIndirizzoMail())){
								verify = false;
								break;
							}
						}
					}
				}
			}
		}
		return verify;
	}
	
	public InvioUDMailBean callInvioRicevuta(ProtocollazioneBean pInBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		String finalita = getExtraparams().get("finalita");

		DmpkIntMgoEmailPreparainvioricevutaregxemail store = new DmpkIntMgoEmailPreparainvioricevutaregxemail();
		DmpkIntMgoEmailPreparainvioricevutaregxemailBean input = new DmpkIntMgoEmailPreparainvioricevutaregxemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudin(pInBean.getIdUd());
		input.setFlginviopecin(getExtraparams().get("tipoMail").equals("PEC") ? 1 : 0);

		if (StringUtils.isNotBlank(finalita)) {
			input.setFinalitain(finalita);
		}

		// Invio pec ???
		StoreResultBean<DmpkIntMgoEmailPreparainvioricevutaregxemailBean> lResult = store.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(lResult.getDefaultMessage())) {
			if (lResult.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + lResult.getDefaultMessage());
				throw new StoreException(lResult);
			} else {
				addMessage(lResult.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		DmpkIntMgoEmailPreparainvioricevutaregxemailBean output = lResult.getResultBean();
		InvioUDMailOutBean lInvioMailUDoutBean = new XmlUtilityDeserializer().unbindXml(output.getXmldatiinviomailout(), InvioUDMailOutBean.class);

		InvioUDMailBean lInvioUDMailBean = new InvioUDMailBean();
		// Setto il tipo
		lInvioUDMailBean.setTipoMail(getExtraparams().get("tipoMail"));
		if (lInvioMailUDoutBean.getDestinatariList() != null) {
			List<DestinatariPecBean> lListDestinatariPEC = new ArrayList<DestinatariPecBean>();
			for (InvioUDMailOutDestinatariBean lInvioMailUDdestinatariBean : lInvioMailUDoutBean.getDestinatariList()) {
				DestinatariPecBean lDestinatariPecBean = new DestinatariPecBean();
				lDestinatariPecBean.setDestPrimario(lInvioMailUDdestinatariBean.getDestPrimario() == Flag.SETTED ? true : false);
				lDestinatariPecBean.setDestCC(lInvioMailUDdestinatariBean.getDestCC() == Flag.SETTED ? true : false);
				lDestinatariPecBean.setTipoDestinatario(lInvioMailUDdestinatariBean.getTipoDestinatario());
				lDestinatariPecBean.setIdRubricaAuriga(lInvioMailUDdestinatariBean.getIdRubricaAuriga());
				lDestinatariPecBean.setIntestazione(lInvioMailUDdestinatariBean.getIntestazione());
				lDestinatariPecBean.setCodiciIpa(lInvioMailUDdestinatariBean.getCodiciIpa());
				lDestinatariPecBean.setCipaPec(lInvioMailUDdestinatariBean.getCipaPec());
				lDestinatariPecBean.setIndirizzoMail(lInvioMailUDdestinatariBean.getEmail());
				lDestinatariPecBean.setIdMailInviata(lInvioMailUDdestinatariBean.getIdMailInviata());
				lDestinatariPecBean.setUriMail(lInvioMailUDdestinatariBean.getUriMail());
				lDestinatariPecBean.setDataOraMail(lInvioMailUDdestinatariBean.getDataOraMail());
				lDestinatariPecBean.setStatoMail(lInvioMailUDdestinatariBean.getStatoMail());
				lDestinatariPecBean.setIdInRubricaEmail(lInvioMailUDdestinatariBean.getIdInRubricaEmail());
				lListDestinatariPEC.add(lDestinatariPecBean);
			}
			lInvioUDMailBean.setDestinatariPec(lListDestinatariPEC);
		}

		List<AttachmentUDBean> lListAttachments = new ArrayList<AttachmentUDBean>();
		List<String> fileDaTimbrareNonConvertiti = new ArrayList<String>();
		if (lInvioMailUDoutBean.getAttachments() != null) {
			for (InvioUDMailOutAttachmentBean lInvioMailUDattachmentBean : lInvioMailUDoutBean.getAttachments()) {
				if (lInvioMailUDattachmentBean.getDaTimbrare() != null)
					mLogger.debug("Attachment da timbrare: name = " + lInvioMailUDattachmentBean.getDaTimbrare().name() + " dbValue = "
							+ lInvioMailUDattachmentBean.getDaTimbrare().getDbValue());
				if (lInvioMailUDattachmentBean.getDaTimbrare() == Flag.SETTED) {
					mLogger.debug("L'attachment " + lInvioMailUDattachmentBean.getNomeFile() + " è da timbrare");
					generaFileTimbrato(lInvioMailUDattachmentBean, lListAttachments, fileDaTimbrareNonConvertiti, String.valueOf(pInBean.getIdUd().intValue()));
				} else {
					AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lInvioMailUDattachmentBean);
					lListAttachments.add(lAttachmentUDBean);
				}
			}
		}

		lInvioUDMailBean.setMittente(lInvioMailUDoutBean.getCasellaMittente());
		lInvioUDMailBean.setIdCasellaMittente(lInvioMailUDoutBean.getIdCasellaMittente());
		lInvioUDMailBean.setAttach(lListAttachments);
		lInvioUDMailBean.setOggetto(lInvioMailUDoutBean.getOggetto());
		lInvioUDMailBean.setBodyHtml(lInvioMailUDoutBean.getBody());  
		lInvioUDMailBean.setDestinatari(lInvioMailUDoutBean.getDestinatari());
		lInvioUDMailBean.setDestinatariCC(lInvioMailUDoutBean.getDestinatariCC());
		if (lInvioMailUDoutBean.getAttachments() != null) {
			lInvioUDMailBean.setSegnaturaPresente(getSegnatura(lInvioMailUDoutBean));
		}
		lInvioUDMailBean.setSalvaInviati(lInvioMailUDoutBean.getFlagSalvaInviati() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setRichiestaConferma(lInvioMailUDoutBean.getRichiestaConferma() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setConfermaLetturaShow(lInvioMailUDoutBean.getConfermaLetturaShow() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setConfermaLettura(lInvioMailUDoutBean.getConfermaLettura() == Flag.SETTED ? true : false);
		lInvioUDMailBean.setAvvertimenti(lInvioMailUDoutBean.getAvvertimenti());
		lInvioUDMailBean.setIdUD(pInBean.getIdUd().longValue() + "");
		lInvioUDMailBean.setFlgTipoProv(pInBean.getFlgTipoProv());

		if (fileDaTimbrareNonConvertiti != null && fileDaTimbrareNonConvertiti.size() > 0) {
			boolean first = true;
			StringBuffer lStringBuffer = new StringBuffer("Il formato del/i file ");
			if (first)
				first = false;
			else
				lStringBuffer.append(";");
			for (String lString : fileDaTimbrareNonConvertiti) {
				lStringBuffer.append(lString);
			}
			lStringBuffer.append(" non consente di apporvi il timbro con la segnatura");
			addMessage(lStringBuffer.toString(), lStringBuffer.toString(), MessageType.WARNING);
		}

		lInvioUDMailBean.setIdMailPartenza(pInBean.getIdEmailArrivo());

		return lInvioUDMailBean;
	}
	
	private String getEmailReplyTo(String usernameIn) throws Exception {
		String result = null;
		LoginDataSource lDataSource = new LoginDataSource();
		try {
			lDataSource.setSession(getSession());
			result = lDataSource.getUsernameMail(usernameIn);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		return result; 
	}
	
	private FirmaXadesBean getFirmaXadesBean() {
		String xmlParametriHsm =  ParametriDBUtil.getParametroDB(getSession(), "PARAMETRI_SIGILLO");
		
		XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
		try {
			FirmaXadesBean firmaXadesBean = lXmlUtility.unbindXml(xmlParametriHsm, FirmaXadesBean.class);
			return firmaXadesBean;
		} catch(Exception e) {
			mLogger.error("Errore nel recupero di FirmaXadesBean per determinare i parametri del sigillo", e);
		}
		return null;
	}
	
}