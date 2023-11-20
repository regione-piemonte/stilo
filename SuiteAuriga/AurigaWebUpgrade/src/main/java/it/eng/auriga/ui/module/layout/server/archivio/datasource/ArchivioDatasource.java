/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationInvioBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDelfolderBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreFirmadocumentoBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreGetidnodoclassificaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreSetvisionatoBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdfolderBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreVistadocumentoBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailPreparaemailnotassinvioccBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocExtractvermodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiLoadfilepertimbroconbustaBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityFindud_outputBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.CriteriPersonalizzatiUtil.TipoFiltro;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioXmlBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.AzioneSuccessivaBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CausaleAggNoteBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriPersonalizzati;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.DestInvioNotifica;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.EsistiVisionatoXmlBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.FormatiFile;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.MezziTrasmissioneFilterBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.NoteFolderBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.NoteUdBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.OperazioneMassivaArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ProcedimentiCollegatiBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Registrazione;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Stato;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.StatoRichAnnullamentoReg;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.SupportiOrigDoc;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.UoApertura;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.UoCompetente;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.UoProponente;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.UoRegistrazione;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Utente;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.filePerBustaTimbro.FilePerBustaConTimbroBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.filePerBustaTimbro.InfoFilePerBustaTimbro;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.TipoIdBean;
import it.eng.auriga.ui.module.layout.server.firmamultipla.bean.FirmaMassivaFilesBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.IstruttoreProcBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.ApposizioneVistoBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.StampaFileBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.ProtocolloDataSource;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ACLBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AltraViaProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AttestatoConformitaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DatiUdOutBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioCCBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatarioProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DocPraticaPregressaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DownloadDocsZipBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FileScaricoZipBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MittenteProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TipoDocumentoBean;
import it.eng.auriga.ui.module.layout.server.timbra.OpzioniTimbroBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraResultBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraUtility;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.client.AurigaMailService;
import it.eng.client.AurigaService;
import it.eng.client.DmpkCollaborationInvio;
import it.eng.client.DmpkCoreDelfolder;
import it.eng.client.DmpkCoreFirmadocumento;
import it.eng.client.DmpkCoreGetidnodoclassifica;
import it.eng.client.DmpkCoreSetvisionato;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkCoreUpdfolder;
import it.eng.client.DmpkCoreVistadocumento;
import it.eng.client.DmpkIntMgoEmailPreparaemailnotassinviocc;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.client.DmpkModelliDocExtractvermodello;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkRepositoryGuiLoadfilepertimbroconbusta;
import it.eng.client.DmpkUtilityFindud_output;
import it.eng.client.GestioneDocumenti;
import it.eng.client.GestioneFascicoli;
import it.eng.client.RecuperoDocumenti;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.ACLFolderBean;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.AltreUbicazioniBean;
import it.eng.document.function.bean.AssegnatariBean;
import it.eng.document.function.bean.AssegnatariOutBean;
import it.eng.document.function.bean.CollocazioneFisicaFascicoloBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.DestInvioCCOutBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.FolderAppartenenzaBean;
import it.eng.document.function.bean.FolderCustom;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.LoadFascicoloIn;
import it.eng.document.function.bean.LoadFascicoloOut;
import it.eng.document.function.bean.ModificaFascicoloIn;
import it.eng.document.function.bean.ModificaFascicoloOut;
import it.eng.document.function.bean.ProcedimentiCollegatiOutBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.SalvaFascicoloIn;
import it.eng.document.function.bean.SalvaFascicoloOut;
import it.eng.document.function.bean.TaskOutBean;
import it.eng.document.function.bean.TipoAssegnatario;
import it.eng.document.function.bean.TipoDestInvioCC;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.TipoProvenienza;
import it.eng.document.function.bean.ValueBean;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.document.function.bean.XmlFascicoloIn;
import it.eng.document.function.bean.XmlFascicoloOut;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.FirmaUtility;
import it.eng.utility.MessageUtil;
import it.eng.utility.XmlUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractDataSourceUtilities;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.servlet.bean.InfoFirmaMarca;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "ArchivioDatasource")
public class ArchivioDatasource extends AurigaAbstractFetchDatasource<ArchivioBean> {

	private static Logger mLogger = Logger.getLogger(ArchivioDatasource.class);
	
	/**
	 * CODICI PER GESTIRE L'ESITO DI ELABORAZIONE DEI FILE DA SCARICARE COME ZIP
	 * */
	public static int OK_CON_WARNING_TIMBRO = 3;
	public static int OK_CON_WARNING_SBUSTA= 4;

	@Override
	public PaginatorBean<ArchivioBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		List<ArchivioBean> data = new ArrayList<ArchivioBean>();

		boolean overflow = false;

		// creo il bean per la ricerca, ma inizializzo anche le variabili che mi servono per determinare se effettivamente posso eseguire il recupero dei dati
		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(false, criteria, loginBean);

		String xmlResultSetOut = null;
		String numTotRecOut = null;
		String numRecInPagOut = null;
		String xmlPercorsiOut = null;
		String dettagliCercaInFolderOut = null;
		String errorMessageOut = null;
		Integer nroPaginaIO = null;
		String errorCodeOut = null;

		if (StringUtils.isNotBlank(lFindRepositoryObjectBean.getFiltroFullText())
				&& (lFindRepositoryObjectBean.getCheckAttributes() == null || lFindRepositoryObjectBean.getCheckAttributes().length == 0)) {
			addMessage("Specificare almeno un attributo su cui effettuare la ricerca full-text", "", MessageType.ERROR);
		} else {

			List<Object> resFinder = null;
			try {
				resFinder = AurigaService.getFind().findrepositoryobject(getLocale(), loginBean, lFindRepositoryObjectBean).getList();
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}

			xmlResultSetOut = (String) resFinder.get(0);
			numTotRecOut = (String) resFinder.get(1);
			numRecInPagOut = (String) resFinder.get(2);
			if (resFinder.size() > 3) {
				xmlPercorsiOut = (String) resFinder.get(3);
			}
			if (resFinder.size() > 4) {
				dettagliCercaInFolderOut = (String) resFinder.get(4);
			}
			if (resFinder.size() > 5) {
				errorMessageOut = (String) resFinder.get(5);
			}
			if (resFinder.size() > 7) {
				String nroPagina = (String) resFinder.get(7);
				nroPaginaIO = nroPagina != null && !"".equalsIgnoreCase(nroPagina) ? Integer.valueOf(nroPagina) : null;
			}
			if (resFinder.size() > 8) {
				errorCodeOut = (String) resFinder.get(8);
			}
		
			//TODO quando il messaggio di warning è quello relativo alla paginazione (errorCode = -1) il + di overflow sotto la lista non va' mostrato
			if((errorMessageOut != null && !"".equals(errorMessageOut)) && (errorCodeOut != null && "-1".equals(errorCodeOut))) {
				addMessage(errorMessageOut, "", MessageType.WARNING);
			} else {
				overflow = manageOverflow(errorMessageOut);
			}

			// Conversione ListaRisultati ==> EngResultSet
			if (xmlResultSetOut != null) {
				data = XmlListaUtility.recuperaLista(xmlResultSetOut, ArchivioBean.class, new ArchivioBeanDeserializationHelper(createRemapConditionsMap()));
				//TODEL dati di test
				/*
				List<ArchivioBean> dataApp = new ArrayList<ArchivioBean>();
				for (ArchivioBean object : data) {
					object.setFlgPresenzaContratti(new Integer(1));
					dataApp.add(object);
				}
				data.clear();
				for (ArchivioBean object : dataApp) {
					data.add(object);
				}
				*/
				
			}
		}

		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);

		PaginatorBean<ArchivioBean> lPaginatorBean = new PaginatorBean<ArchivioBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);	
		//TODO PAGINAZIONE START
//		lPaginatorBean.setTotalRows(data.size());
		if(nroPaginaIO != null && nroPaginaIO.intValue() > 0) {
			lPaginatorBean.setRowsForPage(lFindRepositoryObjectBean.getNumRighePagina());// numRighePagina == Bachsizeio
			lPaginatorBean.setTotalRows(numTotRecOut != null && !"".equals(numTotRecOut) ? Integer.valueOf(numTotRecOut) : null);
		} else {
			lPaginatorBean.setTotalRows(data.size());
		}
		//TODO PAGINAZIONE END
		lPaginatorBean.setOverflow(overflow);
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FORZA_ORDINAMENTO_REP_DOC_OVERFLOW")) {
			lPaginatorBean.setOverflowSortProperties(lFindRepositoryObjectBean.getColsOrderBy(), lFindRepositoryObjectBean.getFlgDescOrderBy(), ArchivioXmlBean.class);
		}

		return lPaginatorBean;
	}

	private Map<String, String> createRemapConditionsMap() {

		Map<String, String> retValue = new LinkedHashMap<String, String>();

		String idNode = getExtraparams().get("idNode");
		retValue.put("idNode", idNode);

		return retValue;
	}

	@Override
	public ArchivioBean get(ArchivioBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		if (StringUtils.isBlank(bean.getIdUdFolder()) && StringUtils.isNotBlank(bean.getIdClassifica())) {
			BigDecimal idClassifica = new BigDecimal(bean.getIdClassifica());
			String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
			String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

			DmpkCoreGetidnodoclassificaBean input = new DmpkCoreGetidnodoclassificaBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdclassificazionein(idClassifica);

			DmpkCoreGetidnodoclassifica getidnodoclassifica = new DmpkCoreGetidnodoclassifica();
			StoreResultBean<DmpkCoreGetidnodoclassificaBean> output = getidnodoclassifica
					.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

			if (output.getResultBean().getIdnodoout() != null) {
				String idNode = output.getResultBean().getIdnodoout();
				bean.setIdUdFolder(idNode.substring(idNode.lastIndexOf("/") + 1));
			}
		}

		String flgSoloAbilAzioni = getExtraparams().get("flgSoloAbilAzioni");
		String flgFascInCreazione = getExtraparams().get("flgFascInCreazione");

		LoadFascicoloIn input = new LoadFascicoloIn();
		input.setIdFolderIn(StringUtils.isNotBlank(bean.getIdUdFolder()) ? new BigDecimal(bean.getIdUdFolder()) : null);
		input.setFlgSoloAbilAzioni(flgSoloAbilAzioni);
		input.setFlgFascInCreazione(flgFascInCreazione);
		if(StringUtils.isNotBlank(getExtraparams().get("idNode"))) {
			input.setIdNodeScrivania(getExtraparams().get("idNode"));
		} else if(StringUtils.isNotBlank(getExtraparams().get("taskName"))) {
			input.setIdNodeScrivania("#TASK_WF#" + getExtraparams().get("taskName"));
		}
		GestioneFascicoli gestioneFascicoli = new GestioneFascicoli();
		LoadFascicoloOut loadFascicoloOut = new LoadFascicoloOut();
		try {
			loadFascicoloOut = gestioneFascicoli.loadfascicolo(getLocale(), loginBean, input);
		} catch (Exception e) {
			throw new StoreException(" Errore : " + e.getMessage());
		}

		XmlFascicoloOut lXmlFascicoloOut = loadFascicoloOut.getXmlFascicoloOut();
		ArchivioBean result = null;

		if (loadFascicoloOut.isInError()) {
			throw new StoreException(loadFascicoloOut);
		} else {
			result = new ArchivioBean();

		
			// Setto i valori IDUDFOLDER e IDFOLDERAPP
			result.setIdFolderApp(lXmlFascicoloOut.getIdFolderApp() != null ? lXmlFascicoloOut.getIdFolderApp().toString() : null); // ID FOLDER APP
			result.setIdUdFolder(bean.getIdUdFolder());

			// sezione ESTREMI
			result.setFlgFascTitolario(lXmlFascicoloOut.getFlgFascTitolario() == Flag.SETTED);
			result.setPercorsoFolderApp(lXmlFascicoloOut.getPathFolderApp() != null ? lXmlFascicoloOut.getPathFolderApp().toString() : null); // PATH FOLDER APP
			result.setIdClassifica(lXmlFascicoloOut.getSegnatura() != null ? lXmlFascicoloOut.getSegnatura().getIdClassifica() : null); // ID CLASSIFICA
			result.setIndiceClassifica(lXmlFascicoloOut.getSegnatura() != null ? lXmlFascicoloOut.getSegnatura().getIndiceClassifica() : null); // INDICE
																																				// CLASSIFICA
			result.setDescClassifica(lXmlFascicoloOut.getSegnatura() != null ? lXmlFascicoloOut.getSegnatura().getDesClassifica() : null); // DESCRIZIONE
																																			// CLASSIFICA
			String lAnnoFascicolo = lXmlFascicoloOut.getSegnatura() != null ? lXmlFascicoloOut.getSegnatura().getAnno() : null;
			result.setAnnoFascicolo(StringUtils.isNotBlank(lAnnoFascicolo) ? new Integer(lAnnoFascicolo) : null); // ANNO FASCICOLO
			result.setFlgSottoFascInserto(lXmlFascicoloOut.getFlgSottoFascInserto()); // FLG INSERTO
			result.setNroFascicolo(lXmlFascicoloOut.getSegnatura() != null ? lXmlFascicoloOut.getSegnatura().getNroFasc() : null); // NR FASCICOLO
			result.setNroSottofascicolo(lXmlFascicoloOut.getSegnatura() != null ? lXmlFascicoloOut.getSegnatura().getNroSottofasc() : null); // NRO SOTTO
			result.setNroInserto(lXmlFascicoloOut.getSegnatura() != null ? lXmlFascicoloOut.getSegnatura().getNroInserto() : null); // NRO INSERTO
			
			result.setStatoFasc(lXmlFascicoloOut.getStato()); // STATO
			
			// Controllo per evitare salvataggi con dati/file non aggiornati
			result.setTimestampGetData(lXmlFascicoloOut.getTimestampGetData());
			
			// FASCICOLO
			result.setNome(lXmlFascicoloOut.getNomeFolder()); // NOME
			result.setNomeFascicolo(lXmlFascicoloOut.getNomeFolder()); // NOME FASCICOLO
			result.setNomeFolderType(lXmlFascicoloOut.getNomeFolderType());
			result.setCodice(lXmlFascicoloOut.getNroSecondario()); // NRO SECONDARIO
			result.setTsApertura(lXmlFascicoloOut.getTsApertura()); // DATA APERTURA
			result.setTsChiusura(lXmlFascicoloOut.getTsChiusura()); // DATA CHIUSURA
			result.setDesUserApertura(lXmlFascicoloOut.getDesUserApertura()); // DESCRIZIONE UTENTE APERTURA
			result.setDesUserChiusura(lXmlFascicoloOut.getDesUserChiusura()); // DESCRIZIONE UTENTE CHIUSURA
			result.setIdUdCapofila(lXmlFascicoloOut.getIdUdCapofila());
			result.setIdDocPrimario(lXmlFascicoloOut.getIdDocCapofila());
			result.setFlgUdDaDataEntryScansioni(lXmlFascicoloOut.getFlgUdCapofilaDaDataEntryScansioni() == Flag.SETTED);		     
			result.setCapofila(lXmlFascicoloOut.getEstremiDocCapofila());
			result.setPresenzaFascCollegati(lXmlFascicoloOut.getPresenzaFascCollegati()); // Flag che indica se ci sono fascicoli collegati
						
			// // sezione RESPONSABILE/UO COMPETENTE
			// // ID UO RESPONSABILE/UO COMPETENTE oppure ID SCRIVANIA RESPONSABILE
			// if (lXmlFascicoloOut.getIdUOResponsabile() != null) {
			// String idUOResponsabile = "" + lXmlFascicoloOut.getIdUOResponsabile().longValue();
			// result.setIdUOScrivResponsabile("UO" + idUOResponsabile);
			// result.setResponsabileFascicolo(idUOResponsabile);
			// } else if (lXmlFascicoloOut.getIdScrivResponsabile() != null) {
			// String idScrivResponsabile = "" + lXmlFascicoloOut.getIdScrivResponsabile().longValue();
			// result.setIdUOScrivResponsabile("SV" + idScrivResponsabile);
			// result.setResponsabileFascicolo(idScrivResponsabile);
			// }

			// sezione DATI PRINCIPALI
			result.setFolderType(lXmlFascicoloOut.getIdFolderType() != null ? lXmlFascicoloOut.getIdFolderType().toString() : null); // ID TIPO FOLDER
			result.setIdFolderType(lXmlFascicoloOut.getIdFolderType() != null ? lXmlFascicoloOut.getIdFolderType().toString() : null); // ID TIPO FOLDER
			result.setDescFolderType(lXmlFascicoloOut.getNomeFolderType()); // NOME TIPO FOLDER
			result.setDescContenutiFascicolo(lXmlFascicoloOut.getDesOgg()); // DESC CONTENUTI
			result.setLivelloRiservatezza(lXmlFascicoloOut.getLivRiservatezza()); // LIVELLO RISERVATEZZA
			result.setDesLivelloRiservatezza(lXmlFascicoloOut.getDesLivRiservatezza()); // DES LIVELLO RISERVATEZZA
			result.setDtTermineRiservatezza(lXmlFascicoloOut.getDtTermineRiservatezza()); // DATA TERMINE RISERVATEZZA
			result.setPriorita(StringUtils.isNotBlank(lXmlFascicoloOut.getLivEvidenza()) ? new BigDecimal(lXmlFascicoloOut.getLivEvidenza()) : null); // PRIORITA'
			result.setDesPriorita(lXmlFascicoloOut.getDesLivEvidenza()); // DES PRIORITA'
			
			// sezione ASSEGNAZIONE / U.O. COMPETENTE
			if (lXmlFascicoloOut.getAssegnatari() != null && lXmlFascicoloOut.getAssegnatari().size() > 0) {
				List<AssegnazioneBean> lAssegnazioni = new ArrayList<AssegnazioneBean>();
				for (AssegnatariOutBean lAssegnatariOutBean : lXmlFascicoloOut.getAssegnatari()) {
					AssegnazioneBean lAssegnazioneBean = new AssegnazioneBean();
					lAssegnazioneBean.setCodRapido(lAssegnatariOutBean.getCodiceUo());
					if (lAssegnatariOutBean.getInternalValue().matches("[A-Z]{2}[0-9]+")) {
						lAssegnazioneBean.setOrganigramma(lAssegnatariOutBean.getInternalValue());
						lAssegnazioneBean.setIdUo(lAssegnatariOutBean.getInternalValue().substring(2));
						lAssegnazioneBean.setTypeNodo(lAssegnatariOutBean.getInternalValue().substring(0, 2));
					} else {
						lAssegnazioneBean.setOrganigramma(lAssegnatariOutBean.getInternalValue());
						lAssegnazioneBean.setIdUo(lAssegnatariOutBean.getInternalValue());
					}
					lAssegnazioneBean.setDescrizione(lAssegnatariOutBean.getDescr());
					lAssegnazioneBean.setDescrizioneEstesa(lAssegnatariOutBean.getDescrEstesa());
					//TODO ASSEGNAZIONE	LOAD
					/*
					OpzioniInvioBean lOpzioniInvioBean = new OpzioniInvioBean();
					lOpzioniInvioBean.setMotivoInvio(lAssegnatariOutBean.getMotivoInvio());
					lOpzioniInvioBean.setLivelloPriorita(lAssegnatariOutBean.getLivelloPriorita());
					lOpzioniInvioBean.setMessaggioInvio(lAssegnatariOutBean.getMessaggioInvio());	
					lOpzioniInvioBean.setFlgPresaInCarico(lAssegnatariOutBean.getFeedback() == Flag.SETTED);
					lOpzioniInvioBean.setFlgMancataPresaInCarico(lAssegnatariOutBean.getNumeroGiorniFeedback() != null);
					lOpzioniInvioBean.setNumeroGiorniFeedback(lAssegnatariOutBean.getNumeroGiorniFeedback());
					lAssegnazioneBean.setOpzioniInvio(lOpzioniInvioBean);
					*/
					lAssegnazioni.add(lAssegnazioneBean);
				}
				result.setListaAssegnazioni(lAssegnazioni);
			}

			// sezione INVIO PER CONOSCENZA
			if (lXmlFascicoloOut.getDestInvioCC() != null && lXmlFascicoloOut.getDestInvioCC().size() > 0) {
				List<DestInvioCCBean> lDestInvioCC = new ArrayList<DestInvioCCBean>();
				for (DestInvioCCOutBean lDestInvioCCOutBean : lXmlFascicoloOut.getDestInvioCC()) {
					DestInvioCCBean lDestInvioCCBean = new DestInvioCCBean();
					lDestInvioCCBean.setTypeNodo(lDestInvioCCOutBean.getTipo());
					if (lDestInvioCCBean.getTypeNodo().equals(TipoDestInvioCC.GRUPPO.toString())) {
						lDestInvioCCBean.setGruppo(lDestInvioCCOutBean.getId());
						lDestInvioCCBean.setTipo("LD");
					} else {
						lDestInvioCCBean.setOrganigramma(lDestInvioCCOutBean.getTipo() + lDestInvioCCOutBean.getId());
						lDestInvioCCBean.setIdUo(lDestInvioCCOutBean.getId());
						lDestInvioCCBean.setTipo("SV;UO");
					}
					lDestInvioCCBean.setCodRapido(lDestInvioCCOutBean.getCodRapido());
					lDestInvioCCBean.setDescrizione(lDestInvioCCOutBean.getDescr());
					lDestInvioCCBean.setDescrizioneEstesa(lDestInvioCCOutBean.getDescrEstesa());
					//TODO CONDIVISIONE	LOAD
					/*
					OpzioniInvioCCBean lOpzioniInvioCCBean = new OpzioniInvioCCBean();
					lOpzioniInvioCCBean.setMotivoInvio(lDestInvioCCOutBean.getMotivoInvio());
					lOpzioniInvioCCBean.setLivelloPriorita(lDestInvioCCOutBean.getLivelloPriorita());
					lOpzioniInvioCCBean.setMessaggioInvio(lDestInvioCCOutBean.getMessaggioInvio());	
					lOpzioniInvioCCBean.setFlgPresaInCarico(lDestInvioCCOutBean.getFeedback() == Flag.SETTED);
					lOpzioniInvioCCBean.setFlgMancataPresaInCarico(lDestInvioCCOutBean.getNumeroGiorniFeedback() != null);
					lOpzioniInvioCCBean.setNumeroGiorniFeedback(lDestInvioCCOutBean.getNumeroGiorniFeedback());
					lDestInvioCCBean.setOpzioniInvio(lOpzioniInvioCCBean);
					*/					
					lDestInvioCC.add(lDestInvioCCBean);
				}
				result.setListaDestInvioCC(lDestInvioCC);
			}

			// sezione PERMESSI
			if (lXmlFascicoloOut.getListaACL() != null) {
				List<ACLBean> listaACL = new ArrayList<ACLBean>();
				for (int i = 0; i < lXmlFascicoloOut.getListaACL().size(); i++) {
					ACLFolderBean lACLFolderBean = lXmlFascicoloOut.getListaACL().get(i);
					ACLBean acl = new ACLBean();
					if ("AOO".equals(lACLFolderBean.getTipoDestinatario())) {
						acl.setTipoDestinatario("AOO");
					} else if ("UO".equals(lACLFolderBean.getTipoDestinatario()) || "SV".equals(lACLFolderBean.getTipoDestinatario())) {
						acl.setTipoDestinatario("UO+SV");
						acl.setOrganigramma(lACLFolderBean.getTipoDestinatario() + lACLFolderBean.getIdDestinatario());
						acl.setTipoOrganigramma(lACLFolderBean.getTipoDestinatario());
						acl.setIdOrganigramma(lACLFolderBean.getIdDestinatario());
						acl.setCodiceUo(lACLFolderBean.getCodiceRapido());
					} else if ("UT".equals(lACLFolderBean.getTipoDestinatario())) {
						acl.setTipoDestinatario("UT");
						acl.setIdUtente(lACLFolderBean.getIdDestinatario());
						acl.setCodiceRapido(lACLFolderBean.getCodiceRapido());
					} else if ("G".equals(lACLFolderBean.getTipoDestinatario())) {
						acl.setTipoDestinatario("G");
						acl.setIdGruppo(lACLFolderBean.getIdDestinatario());
						acl.setIdGruppoInterno(lACLFolderBean.getIdDestinatario());
						acl.setCodiceRapido(lACLFolderBean.getCodiceRapido());
					}
					// per verificare se ho il controllo completo (C) metto in OR i flag che riguardano esclusivamente quella voce (non metto in AND tutti i
					// flag per questioni di retrocompatibilità)
					if ((lACLFolderBean.getFlgModificaMetadati() == Flag.SETTED) || (lACLFolderBean.getFlgModificaACL() == Flag.SETTED)
							|| (lACLFolderBean.getFlgEliminaFolder() == Flag.SETTED) || (lACLFolderBean.getFlgRipristinoFolder() == Flag.SETTED)
							|| (lACLFolderBean.getFlgModificaContenutoFolder() == Flag.SETTED)) {
						acl.setOpzioniAccesso("C");
					} else if (lACLFolderBean.getFlgModificaContenutoUd() == Flag.SETTED) {
						acl.setOpzioniAccesso("D");
					} else {
						acl.setOpzioniAccesso("V");
					}
					acl.setFlgEreditata(lACLFolderBean.getFlgEreditata() == Flag.SETTED);
					acl.setDenominazione(lACLFolderBean.getDenominazioneDestinatario());
					acl.setDenominazioneEstesa(lACLFolderBean.getDenominazioneEstesaDestinatario());
					acl.setFlgAssegnatario(lACLFolderBean.getFlgAssegnatario() == Flag.SETTED);
					acl.setFlgInvioPerConoscenza(lACLFolderBean.getFlgInvioPerConoscenza() == Flag.SETTED);
					listaACL.add(acl);
				}
				result.setListaACL(listaACL);
			}

			// sezione COLLOCAZIONE FISICA
			result.setIdTopografico(lXmlFascicoloOut.getCollocazioneFisica() != null ? lXmlFascicoloOut.getCollocazioneFisica().getIdTopografico() : null); // ID TOPOGRAFICO
			result.setCodRapidoTopografico(lXmlFascicoloOut.getCollocazioneFisica() != null ? lXmlFascicoloOut.getCollocazioneFisica().getCodRapido() : null); // CODICE RAPIDO TOPOGRAFICO
			result.setNomeTopografico(lXmlFascicoloOut.getCollocazioneFisica() != null ? lXmlFascicoloOut.getCollocazioneFisica().getDescrizione() : null); // NOME TOPOGRAFICO

			// sezione ALTRI DATI
			result.setNoteFascicolo(lXmlFascicoloOut.getNote()); // NOTE FASCICOLO

			result.setFlgSelXFinalita(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getAggiuntaDoc() : null);
			result.setAbilAssegnazioneSmistamento(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getAssegnazioneSmistamento() : null);

			result.setAbilModAssInviiCC(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getModAssInviiCC() : null);
			result.setAbilSetVisionato(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getSetVisionato() : null);
			result.setAbilSmista(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getSmista() : null);
			result.setAbilSmistaCC(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getSmistaCC(): null);
			result.setAbilCondivisione(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getCondivisione() : null);
			result.setAbilEliminazione(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getEliminazione() : null);
			result.setAbilModificaDati(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getModificaDati() : null);
			result.setAbilAvvioIterWF(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getAvvioIterWF() : null);
			result.setAbilPresaInCarico(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getPresaInCarico() : null);
			result.setAbilRestituzione(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getRestituzione() : null);
			result.setAbilArchiviazione(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getArchiviazione() : null);
			result.setAbilChiudiFascicolo(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getChiusura() : null);
			result.setAbilRiapriFascicolo(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getRiapertura() : null);
			result.setAbilVersaInArchivioStoricoFascicolo(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getAbilVersaInArchivioStoricoFascicolo() : null);
			result.setFaseCorrenteProc(lXmlFascicoloOut.getFaseCorrenteProc());
			result.setAbilOsservazioniNotifiche(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getAbilOsservazioniNotifiche() : null);
			result.setAbilRegistrazionePrelievo(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getAbilRegistrazionePrelievo() : null); 	
			result.setAbilModificaPrelievo(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getAbilModificaPrelievo() : null);
			result.setAbilEliminazionePrelievo(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getAbilEliminazionePrelievo() : null);
			result.setAbilRestituzionePrelievo(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getAbilRestituzionePrelievo() : null);
			result.setAbilStampaCopertina(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getAbilStampaCopertina() : null);
			result.setAbilStampaSegnatura(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getAbilStampaSegnatura() : null);
			result.setAbilStampaListaContenuti(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getAbilStampaListaContenuti() : null);			
			result.setAbilModificaTipologia(lXmlFascicoloOut.getAbilitazioni() != null ? lXmlFascicoloOut.getAbilitazioni().getModificaTipologia() : null);
			result.setAbilGestioneCollegamentiFolder((lXmlFascicoloOut.getAbilitazioni() != null && lXmlFascicoloOut.getAbilitazioni().getGestioneCollegamentiFolder() != null) ? lXmlFascicoloOut.getAbilitazioni().getGestioneCollegamentiFolder() : false);
			
			// sezione Dati prelievo e archivio
			result.setDatiPrelievoDaArchivioIdUO(lXmlFascicoloOut.getDatiPrelievoDaArchivioIdUO());
			result.setDatiPrelievoDaArchivioCodUO(lXmlFascicoloOut.getDatiPrelievoDaArchivioCodUO());
			result.setDatiPrelievoDaArchivioDesUO(lXmlFascicoloOut.getDatiPrelievoDaArchivioDesUO());
			result.setDatiPrelievoDaArchivioOrganigrammaUO(lXmlFascicoloOut.getDatiPrelievoDaArchivioIdUO() != null ? "UO" + lXmlFascicoloOut.getDatiPrelievoDaArchivioIdUO().replace(".","") : null);
			result.setDatiPrelievoDaArchivioDataPrelievo(lXmlFascicoloOut.getDatiPrelievoDaArchivioDataPrelievo());
			result.setDatiPrelievoDaArchivioIdUserResponsabile(lXmlFascicoloOut.getDatiPrelievoDaArchivioIdUserResponsabile());
			result.setDatiPrelievoDaArchivioUsernameResponsabile(lXmlFascicoloOut.getDatiPrelievoDaArchivioUsernameResponsabile());
			result.setDatiPrelievoDaArchivioCognomeResponsabile(lXmlFascicoloOut.getDatiPrelievoDaArchivioCognomeResponsabile());
			result.setDatiPrelievoDaArchivioNomeResponsabile(lXmlFascicoloOut.getDatiPrelievoDaArchivioNomeResponsabile());
			result.setDatiPrelievoDaArchivioNoteRichiedente(lXmlFascicoloOut.getDatiPrelievoDaArchivioNoteRichiedente());
			result.setDatiPrelievoDaArchivioNoteArchivio(lXmlFascicoloOut.getDatiPrelievoDaArchivioNoteArchivio());
			result.setClassifPregressa(lXmlFascicoloOut.getClassifPregressa());
			result.setUdc(lXmlFascicoloOut.getUdc());
			result.setPresenzaInArchivioDeposito(lXmlFascicoloOut.getPresenzaInArchivioDeposito());
			result.setCompetenzaPratica(lXmlFascicoloOut.getCompetenzaPratica());
			result.setReperibilitaCartaceoPratica(lXmlFascicoloOut.getReperibilitaCartaceoPratica());
			result.setArchivioPresCartaceoPratica(lXmlFascicoloOut.getArchivioPresCartaceoPratica());

			// sezione TASK
			if (lXmlFascicoloOut.getListaTask() != null && lXmlFascicoloOut.getListaTask().size() > 0) {
				List<TaskBean> lTaskBeanList = new ArrayList<TaskBean>();
				for (TaskOutBean lTaskOutBean : lXmlFascicoloOut.getListaTask()) {
					TaskBean lTaskBean = new TaskBean();
					lTaskBean.setInstanceId(lTaskOutBean.getInstanceId());
					lTaskBean.setInstanceLabel(lTaskOutBean.getInstanceLabel());
					lTaskBean.setActivityName(lTaskOutBean.getActivityName());
					lTaskBean.setDisplayName(lTaskOutBean.getDisplayName());
					lTaskBean.setFlgEseguibile(lTaskOutBean.getFlgEseguibile() == Flag.SETTED);
					lTaskBean.setMotiviNonEseg(lTaskOutBean.getMotiviNonEseg());
					lTaskBean.setFlgDatiVisibili(lTaskOutBean.getFlgDatiVisibili() == Flag.SETTED);
					lTaskBean.setFlgFatta(lTaskOutBean.getFlgFatta() == Flag.SETTED);
					lTaskBean.setIdChildProcess(lTaskOutBean.getIdChildProcess());
					lTaskBean.setEstremiChildProcess(lTaskOutBean.getEstremiChildProcess());
					lTaskBean.setRowIdEvento(lTaskOutBean.getRowIdEvento());
					lTaskBean.setIdProcess(lXmlFascicoloOut.getIdProcess());
					lTaskBeanList.add(lTaskBean);
				}
				result.setListaTask(lTaskBeanList);
			}

			result.setIdDefProcFlow(lXmlFascicoloOut.getIdDefProcFlow());
			result.setIdInstProcFlow(lXmlFascicoloOut.getIdInstProcFlow());
			result.setIdProcess(lXmlFascicoloOut.getIdProcess());
			result.setEstremiProcess(lXmlFascicoloOut.getEstremiProcess());

			result.setRowidFolder(lXmlFascicoloOut.getRowidFolder());

			// FlgArchivio
			result.setFlgArchivio(lXmlFascicoloOut.getFlgArchivio().getDbValue().toString());

			// result.setAbilVersaInArchivioStoricoFascicolo(true);

			// sezione ISTRUTTORE PROC.
			List<IstruttoreProcBean> listaIstruttoriProc = new ArrayList<IstruttoreProcBean>();
			IstruttoreProcBean istruttoreProc = new IstruttoreProcBean();
			istruttoreProc.setTipoIstruttoreProc(lXmlFascicoloOut.getTipoIstruttoreProc());
			if (istruttoreProc.getTipoIstruttoreProc() != null && "UT".equals(istruttoreProc.getTipoIstruttoreProc())) {
				istruttoreProc.setIdUtenteIstruttoreProc(lXmlFascicoloOut.getIdIstruttoreProc());
				istruttoreProc.setNomeUtenteIstruttoreProc(lXmlFascicoloOut.getNomeIstruttoreProc());
			}
			if (istruttoreProc.getTipoIstruttoreProc() != null && "LD".equals(istruttoreProc.getTipoIstruttoreProc())) {
				istruttoreProc.setIdGruppoIstruttoreProc(lXmlFascicoloOut.getIdIstruttoreProc());
				istruttoreProc.setCodRapidoGruppoIstruttoreProc(lXmlFascicoloOut.getCodRapidoIstruttoreProc());
				istruttoreProc.setNomeGruppoIstruttoreProc(lXmlFascicoloOut.getNomeIstruttoreProc());
			}
			listaIstruttoriProc.add(istruttoreProc);
			result.setListaIstruttoriProc(listaIstruttoriProc);
			result.setDataPresentazioneInstanza(lXmlFascicoloOut.getDataPresentazioneIstanza());

			result.setFlgCaricamentoPregressoDaGUI(lXmlFascicoloOut.getFlgCaricamentoPregressoDaGUI() == Flag.SETTED);
			result.setFlgTipoFolderConVie(lXmlFascicoloOut.getFlgTipoFolderConVie() == Flag.SETTED);
			result.setDictionaryEntrySezione(lXmlFascicoloOut.getDictionaryEntrySezione());
			result.setTemplateNomeFolder(lXmlFascicoloOut.getTemplateNomeFolder());
			
			boolean isPraticaPregressa = getExtraparams().get("isPraticaPregressa") != null && "true".equals(getExtraparams().get("isPraticaPregressa"));
			if(lXmlFascicoloOut.getFlgCaricamentoPregressoDaGUI() == Flag.SETTED) {
				isPraticaPregressa = true;
			}
			if(isPraticaPregressa) {				
				// sezione DOCUMENTI PRATICA PREGRESSA
				if (lXmlFascicoloOut.getListaDocumentiIstruttoria() != null && lXmlFascicoloOut.getListaDocumentiIstruttoria().size() > 0) {
					List<DocPraticaPregressaBean> listaDocumentiPraticaPregressa = new ArrayList<DocPraticaPregressaBean>();
					for (AllegatiOutBean documento : lXmlFascicoloOut.getListaDocumentiIstruttoria()) {					
						AllegatoProtocolloBean lAllegatoProtocolloBean = recuperaDocumentoCaricaPregressa(documento, lXmlFascicoloOut.getDictionaryEntrySezione());
						DocPraticaPregressaBean lDocPraticaPregressaBean = new DocPraticaPregressaBean();
						BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lDocPraticaPregressaBean, lAllegatoProtocolloBean);
						lDocPraticaPregressaBean.setFlgFromLoadDett(true);
						lDocPraticaPregressaBean.setFlgAllegato(documento.getFlgAllegato() == Flag.SETTED);						
						lDocPraticaPregressaBean.setFlgCapofila(documento.getFlgCapofila() == Flag.SETTED);						
						lDocPraticaPregressaBean.setTipoProt(documento.getTipoProt());
						lDocPraticaPregressaBean.setSiglaProtSettore(documento.getSiglaProtSettore());
						lDocPraticaPregressaBean.setNroProt(documento.getNroProt());
						lDocPraticaPregressaBean.setAnnoProt(documento.getAnnoProt());
						lDocPraticaPregressaBean.setNroDeposito(documento.getNroDeposito());
						lDocPraticaPregressaBean.setAnnoDeposito(documento.getAnnoDeposito());
						lDocPraticaPregressaBean.setEsibenti(documento.getMittentiEsibenti());
						lDocPraticaPregressaBean.setOggetto(documento.getDescrizioneOggetto());
						lDocPraticaPregressaBean.setFlgUdDaDataEntryScansioni(documento.getFlgUdDaDataEntryScansioni() == Flag.SETTED);
						listaDocumentiPraticaPregressa.add(lDocPraticaPregressaBean);
					}
					result.setListaDocumentiPraticaPregressa(listaDocumentiPraticaPregressa);
				}				
			} else {
				// sezione DOCUMENTI INIZIALI
				if (lXmlFascicoloOut.getListaDocumentiIniziali() != null && lXmlFascicoloOut.getListaDocumentiIniziali().size() > 0) {
					List<AllegatoProtocolloBean> listaDocumentiIniziali = new ArrayList<AllegatoProtocolloBean>();
					for (AllegatiOutBean documento : lXmlFascicoloOut.getListaDocumentiIniziali()) {
						listaDocumentiIniziali.add(recuperaDocumento(documento));
					}
					result.setListaDocumentiIniziali(listaDocumentiIniziali);
				}						
				// sezione DOCUMENTI ISTRUTTORIA
				if (lXmlFascicoloOut.getListaDocumentiIstruttoria() != null && lXmlFascicoloOut.getListaDocumentiIstruttoria().size() > 0) {
					List<AllegatoProtocolloBean> listaDocumentiIstruttoria = new ArrayList<AllegatoProtocolloBean>();
					for (AllegatiOutBean documento : lXmlFascicoloOut.getListaDocumentiIstruttoria()) {
						AllegatoProtocolloBean lDocumentoBean = recuperaDocumento(documento, lXmlFascicoloOut.getDictionaryEntrySezione());
						lDocumentoBean.setFlgSalvato(true); // se true indica che il documento è già salvato in DB ed associato a quel fascicolo
						listaDocumentiIstruttoria.add(lDocumentoBean);
					}
					result.setListaDocumentiIstruttoria(listaDocumentiIstruttoria);
				}
			}
						
			result.setFlgDocumentazioneCompleta(lXmlFascicoloOut.getFlgDocumentazioneCompleta() == Flag.SETTED);
			
			result.setNumProtocolloGenerale(lXmlFascicoloOut.getNumProtocolloGenerale());
			result.setAnnoProtocolloGenerale(lXmlFascicoloOut.getAnnoProtocolloGenerale());
			result.setSiglaProtocolloSettore(lXmlFascicoloOut.getSiglaProtocolloSettore());
			result.setNumProtocolloSettore(lXmlFascicoloOut.getNumProtocolloSettore());
			result.setSubProtocolloSettore(lXmlFascicoloOut.getSubProtocolloSettore());
			result.setAnnoProtocolloSettore(lXmlFascicoloOut.getAnnoProtocolloSettore());
			result.setDtRichPraticaUrbanistica(lXmlFascicoloOut.getDtRichPraticaUrbanistica());
			result.setCodClassFascEdilizio(lXmlFascicoloOut.getCodClassFascEdilizio());
			result.setNumeroDeposito(lXmlFascicoloOut.getNumeroDeposito());
			result.setAnnoDeposito(lXmlFascicoloOut.getAnnoDeposito());					
			result.setNumProtocolloGeneraleRichPratica(lXmlFascicoloOut.getNumProtocolloGeneraleRichPratica());
			result.setAnnoProtocolloGeneraleRichPratica(lXmlFascicoloOut.getAnnoProtocolloGeneraleRichPratica());
			result.setNumProtocolloGeneraleRichPraticaWF(lXmlFascicoloOut.getNumProtocolloGeneraleRichPraticaWF());
			result.setAnnoProtocolloGeneraleRichPraticaWF(lXmlFascicoloOut.getAnnoProtocolloGeneraleRichPraticaWF());
			result.setNumeroEP(lXmlFascicoloOut.getNumeroEP());
			result.setAnnoEP(lXmlFascicoloOut.getAnnoEP());
			result.setNumeroLicenza(lXmlFascicoloOut.getNumeroLicenza());
			result.setAnnoLicenza(lXmlFascicoloOut.getAnnoLicenza());
			
			if (lXmlFascicoloOut.getListaEsibentiPraticaPregressa() != null && !lXmlFascicoloOut.getListaEsibentiPraticaPregressa().isEmpty()) {
				List<ValueBean> listaEsibentiPraticaPregressa = new ArrayList<ValueBean>();
				for(ValueBean esibenteItem : lXmlFascicoloOut.getListaEsibentiPraticaPregressa()) {
					ValueBean lValueBean = new ValueBean();
					lValueBean.setValue(esibenteItem.getValue());
					
					listaEsibentiPraticaPregressa.add(lValueBean);
				}
				result.setListaEsibentiPraticaPregressa(listaEsibentiPraticaPregressa);
			}
			
			result.setAttivaTimbroTipologia(lXmlFascicoloOut.getAttivaTimbroTipologia() == Flag.SETTED);
			
			ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
			// sezione ALTRE VIE			
			if (lXmlFascicoloOut.getAltreUbicazioni() != null && lXmlFascicoloOut.getAltreUbicazioni().size() > 0) {
				List<AltraViaProtBean> lListAltreVie = new ArrayList<AltraViaProtBean>();
				for (AltreUbicazioniBean lAltraVia : lXmlFascicoloOut.getAltreUbicazioni()) {
					AltraViaProtBean lAltraViaProtBean = new AltraViaProtBean();
					lAltraViaProtBean.setStato(lAltraVia.getStato());
					lAltraViaProtBean.setNomeStato(lAltraVia.getNomeStato());
					if (StringUtils.isBlank(lAltraViaProtBean.getStato()) || ProtocolloUtility._COD_ISTAT_ITALIA.equals(lAltraViaProtBean.getStato())
							|| ProtocolloUtility._NOME_STATO_ITALIA.equals(lAltraViaProtBean.getStato())) {
						if (StringUtils.isNotBlank(lAltraVia.getCodToponimo()) || StringUtils.isBlank(lAltraVia.getToponimoIndirizzo())) {
							lAltraViaProtBean.setFlgFuoriComune(false);
							lAltraViaProtBean.setCodToponimo(lAltraVia.getCodToponimo());
							lAltraViaProtBean.setIndirizzo(lAltraVia.getToponimoIndirizzo());
							lAltraViaProtBean.setComune(lProtocolloUtility.getCodIstatComuneRif());
							lAltraViaProtBean.setNomeComune(lProtocolloUtility.getNomeComuneRif());
						} else {
							lAltraViaProtBean.setFlgFuoriComune(true);
							lAltraViaProtBean.setTipoToponimo(lAltraVia.getTipoToponimo());
							lAltraViaProtBean.setToponimo(lAltraVia.getToponimoIndirizzo());
							lAltraViaProtBean.setComune(lAltraVia.getComune());
							lAltraViaProtBean.setNomeComune(lAltraVia.getNomeComuneCitta());
						}
						lAltraViaProtBean.setFrazione(lAltraVia.getFrazione());
						lAltraViaProtBean.setCap(lAltraVia.getCap());
					} else {
						lAltraViaProtBean.setIndirizzo(lAltraVia.getToponimoIndirizzo());
						lAltraViaProtBean.setCitta(lAltraVia.getNomeComuneCitta());
					}
					lAltraViaProtBean.setCivico(lAltraVia.getCivico());
					lAltraViaProtBean.setAppendici(lAltraVia.getAppendici());
					lAltraViaProtBean.setZona(lAltraVia.getZona());
					lAltraViaProtBean.setComplementoIndirizzo(lAltraVia.getComplementoIndirizzo());
					lListAltreVie.add(lAltraViaProtBean);
				}
				result.setListaAltreVie(lListAltreVie);
			}
			
			// Dati per protocollazione allegati ced autotutele
			result.setDatiProtDocIstrMittenteId(lXmlFascicoloOut.getDatiProtDocIstrMittenteId());
			result.setDatiProtDocIstrDestinatarioFlgPersonaFisica(lXmlFascicoloOut.getDatiProtDocIstrDestinatarioFlgPersonaFisica());
			result.setDatiProtDocIstrDestinatarioDenomCognome(lXmlFascicoloOut.getDatiProtDocIstrDestinatarioDenomCognome());
			result.setDatiProtDocIstrDestinatarioNome(lXmlFascicoloOut.getDatiProtDocIstrDestinatarioNome());
			result.setDatiProtDocIstrCodFiscale(lXmlFascicoloOut.getDatiProtDocIstrCodFiscale());
			
			// Dati procedimenti autotutele / ced
			if (lXmlFascicoloOut.getProcedimentiCollegati() != null && !lXmlFascicoloOut.getProcedimentiCollegati().isEmpty()) {
				List<ProcedimentiCollegatiBean> listaProcCollegati = new ArrayList<ProcedimentiCollegatiBean>();
				for(ProcedimentiCollegatiOutBean procItem : lXmlFascicoloOut.getProcedimentiCollegati()) {
					ProcedimentiCollegatiBean lProcedimentiCollegatiBean = new ProcedimentiCollegatiBean();
					lProcedimentiCollegatiBean.setIdProcess(procItem.getIdProcess());
					lProcedimentiCollegatiBean.setDescrizione(procItem.getDescrizione());
					
					listaProcCollegati.add(lProcedimentiCollegatiBean);
				}
				result.setListaProcCollegati(listaProcCollegati);
			}
			
		}

		return result;
	}

	
	protected AllegatoProtocolloBean recuperaDocumento(AllegatiOutBean documento) throws Exception {
		return recuperaDocumento(documento, null);
	}
	
	protected AllegatoProtocolloBean recuperaDocumento(AllegatiOutBean documento, String dictionaryEntrySezione) throws Exception {
		AllegatoProtocolloBean lDocumentoBean = new AllegatoProtocolloBean();
		BigDecimal idDocAllegato = StringUtils.isNotBlank(documento.getIdDoc()) ? new BigDecimal(documento.getIdDoc()) : null;
		lDocumentoBean.setIdUdAppartenenza(documento.getIdUd());
		lDocumentoBean.setRuoloUd(documento.getRuoloUd());
		lDocumentoBean.setIdDocAllegato(idDocAllegato);
		lDocumentoBean.setDescrizioneFileAllegato(documento.getDescrizioneOggetto());
		lDocumentoBean.setIdTipoFileAllegato(documento.getIdDocType());
		lDocumentoBean.setDescTipoFileAllegato(documento.getNomeDocType());
		lDocumentoBean.setListaTipiFileAllegato(documento.getIdDocType());
		if(StringUtils.isNotBlank(dictionaryEntrySezione)) {
			lDocumentoBean.setDictionaryEntrySezione(dictionaryEntrySezione);
		}
		lDocumentoBean.setNomeFileAllegato(documento.getDisplayFileName());
		lDocumentoBean.setFlgParteDispositivo(documento.getFlgParteDispositivo() == Flag.SETTED);
		lDocumentoBean.setFlgParteDispositivoSalvato(documento.getFlgParteDispositivo() == Flag.SETTED);
		lDocumentoBean.setIdTask(documento.getIdTask());
		lDocumentoBean.setFlgNoPubblAllegato(documento.getFlgNoPubbl() == Flag.SETTED); 
		lDocumentoBean.setIsPubblicato(documento.getFlgPubblicato() != null && "1".equals(documento.getFlgPubblicato()) ? true : false);
		lDocumentoBean.setFlgOriginaleCartaceo(documento.getFlgOriginaleCartaceo() == Flag.SETTED);
		lDocumentoBean.setFlgCopiaSostitutiva(documento.getFlgCopiaSostitutiva() == Flag.SETTED);
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lDocumentoBean.setUriFileAllegato(documento.getUri());
		lDocumentoBean.setRemoteUri(true);
		if (StringUtils.isNotBlank(documento.getImpronta())) {
			lMimeTypeFirmaBean.setImpronta(documento.getImpronta());
		}
		lMimeTypeFirmaBean.setCorrectFileName(lDocumentoBean.getNomeFileAllegato());
		lMimeTypeFirmaBean.setFirmato(documento.getFlgFileFirmato() == Flag.SETTED);
		lMimeTypeFirmaBean.setFirmaValida(documento.getFlgFileFirmato() == Flag.SETTED);
		lMimeTypeFirmaBean.setConvertibile(documento.getFlgConvertibilePdf() == Flag.SETTED);
		lMimeTypeFirmaBean.setDaScansione(false);
		lMimeTypeFirmaBean.setMimetype(documento.getMimetype());
		lMimeTypeFirmaBean.setBytes(documento.getDimensione() != null ? documento.getDimensione().longValue() : 0);
		if (lMimeTypeFirmaBean.isFirmato()) {
			lMimeTypeFirmaBean.setTipoFirma(documento.getDisplayFileName().toUpperCase().endsWith("P7M")
					|| documento.getDisplayFileName().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
			lMimeTypeFirmaBean.setFirmatari(StringUtils.isNotBlank(documento.getFirmatari()) ? documento.getFirmatari().split(",") : null);
		}
		InfoFirmaMarca lInfoFirmaMarca = new InfoFirmaMarca();
		lInfoFirmaMarca.setBustaCrittograficaFattaDaAuriga(documento.getFlgBustaCrittograficaAuriga() == Flag.SETTED);
		lInfoFirmaMarca.setFirmeNonValideBustaCrittografica(documento.getFlgFirmeNonValideBustaCrittografica() == Flag.SETTED);
		lInfoFirmaMarca.setFirmeExtraAuriga(documento.getFlgFirmeExtraAuriga() == Flag.SETTED);
		lInfoFirmaMarca.setMarcaTemporaleAppostaDaAuriga(documento.getFlgMarcaTemporaleAuriga() == Flag.SETTED);
		lInfoFirmaMarca.setDataOraMarcaTemporale(documento.getDataOraMarcaTemporale());
		lInfoFirmaMarca.setMarcaTemporaleNonValida(documento.getFlgMarcaTemporaleNonValida() == Flag.SETTED);
		lMimeTypeFirmaBean.setInfoFirmaMarca(lInfoFirmaMarca);
		lDocumentoBean.setInfoFile(lMimeTypeFirmaBean);
		lDocumentoBean.setIdAttachEmail(documento.getIdAttachEmail());
		lDocumentoBean.setMimetypeVerPreFirma(documento.getMimetypeVerPreFirma());
		lDocumentoBean.setUriFileVerPreFirma(documento.getUriVerPreFirma());
		lDocumentoBean.setNomeFileVerPreFirma(documento.getDisplayFilenameVerPreFirma());
		lDocumentoBean.setFlgConvertibilePdfVerPreFirma(documento.getFlgConvertibilePdfVerPreFirma());
		lDocumentoBean.setImprontaVerPreFirma(documento.getImprontaVerPreFirma());		
		lDocumentoBean.setNroUltimaVersione(documento.getNroUltimaVersione());
		lDocumentoBean.setIdEmail(documento.getIdEmail());
		lDocumentoBean.setEstremiProtUd(documento.getEstremiProtUd());
		lDocumentoBean.setNroProtocollo(documento.getNroProtocollo());
		lDocumentoBean.setAnnoProtocollo(documento.getAnnoProtocollo());
		lDocumentoBean.setDataProtocollo(documento.getDataProtocollo());
		lDocumentoBean.setDataRicezione(documento.getDataRicezione());
		lDocumentoBean.setFlgIdUdAppartenenzaContieneAllegati(documento.getFlgIdUdAppartenenzaContieneAllegati() == Flag.SETTED);
		lDocumentoBean.setFlgTipoProvXProt(documento.getFlgTipoProvXProt());
		return lDocumentoBean;
	}
		
	protected AllegatoProtocolloBean recuperaDocumentoCaricaPregressa(AllegatiOutBean documento, String dictionaryEntrySezione) throws Exception {
		AllegatoProtocolloBean lDocumentoBean = new AllegatoProtocolloBean();
		lDocumentoBean.setIdUdAppartenenza(documento.getIdUd());
		lDocumentoBean.setRuoloUd(documento.getRuoloUd());
		lDocumentoBean.setIdDocAllegato(StringUtils.isNotBlank(documento.getIdDoc()) ? new BigDecimal(documento.getIdDoc()) : null);
		lDocumentoBean.setDescrizioneFileAllegato(documento.getDescrizioneOggetto());
		lDocumentoBean.setIdTipoFileAllegato(documento.getIdDocType());
		lDocumentoBean.setDescTipoFileAllegato(documento.getNomeDocType());
		lDocumentoBean.setListaTipiFileAllegato(documento.getIdDocType());
		if(StringUtils.isNotBlank(dictionaryEntrySezione)) {
			lDocumentoBean.setDictionaryEntrySezione(dictionaryEntrySezione);
		}
		lDocumentoBean.setNomeFileAllegato(documento.getDisplayFileName());
		lDocumentoBean.setFlgParteDispositivo(documento.getFlgParteDispositivo() == Flag.SETTED);
		lDocumentoBean.setFlgParteDispositivoSalvato(documento.getFlgParteDispositivo() == Flag.SETTED);
		lDocumentoBean.setIdTask(documento.getIdTask());
		lDocumentoBean.setFlgNoPubblAllegato(documento.getFlgNoPubbl() == Flag.SETTED); 
		lDocumentoBean.setIsPubblicato(documento.getFlgPubblicato() != null && "1".equals(documento.getFlgPubblicato()) ? true : false);
		lDocumentoBean.setFlgOriginaleCartaceo(documento.getFlgOriginaleCartaceo() == Flag.SETTED);
		lDocumentoBean.setFlgCopiaSostitutiva(documento.getFlgCopiaSostitutiva() == Flag.SETTED);
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lDocumentoBean.setUriFileAllegato(documento.getUri());
		lDocumentoBean.setRemoteUri(true);
		if (StringUtils.isNotBlank(documento.getImpronta())) {
			lMimeTypeFirmaBean.setImpronta(documento.getImpronta());
		}
		lMimeTypeFirmaBean.setCorrectFileName(lDocumentoBean.getNomeFileAllegato());
		lMimeTypeFirmaBean.setFirmato(documento.getFlgFileFirmato() == Flag.SETTED);
		lMimeTypeFirmaBean.setFirmaValida(documento.getFlgFileFirmato() == Flag.SETTED);
		lMimeTypeFirmaBean.setConvertibile(documento.getFlgConvertibilePdf() == Flag.SETTED);
		lMimeTypeFirmaBean.setDaScansione(false);
		lMimeTypeFirmaBean.setMimetype(documento.getMimetype());
		lMimeTypeFirmaBean.setBytes(documento.getDimensione() != null ? documento.getDimensione().longValue() : 0);
		if (lMimeTypeFirmaBean.isFirmato()) {
			lMimeTypeFirmaBean.setTipoFirma(documento.getDisplayFileName().toUpperCase().endsWith("P7M")
					|| documento.getDisplayFileName().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
			lMimeTypeFirmaBean.setFirmatari(StringUtils.isNotBlank(documento.getFirmatari()) ? documento.getFirmatari().split(",") : null);
		}
		InfoFirmaMarca lInfoFirmaMarca = new InfoFirmaMarca();
		lInfoFirmaMarca.setBustaCrittograficaFattaDaAuriga(documento.getFlgBustaCrittograficaAuriga() == Flag.SETTED);
		lInfoFirmaMarca.setFirmeNonValideBustaCrittografica(documento.getFlgFirmeNonValideBustaCrittografica() == Flag.SETTED);
		lInfoFirmaMarca.setFirmeExtraAuriga(documento.getFlgFirmeExtraAuriga() == Flag.SETTED);
		lInfoFirmaMarca.setMarcaTemporaleAppostaDaAuriga(documento.getFlgMarcaTemporaleAuriga() == Flag.SETTED);
		lInfoFirmaMarca.setDataOraMarcaTemporale(documento.getDataOraMarcaTemporale());
		lInfoFirmaMarca.setMarcaTemporaleNonValida(documento.getFlgMarcaTemporaleNonValida() == Flag.SETTED);
		lMimeTypeFirmaBean.setInfoFirmaMarca(lInfoFirmaMarca);
		lDocumentoBean.setInfoFile(lMimeTypeFirmaBean);
		lDocumentoBean.setIdAttachEmail(documento.getIdAttachEmail());
		lDocumentoBean.setMimetypeVerPreFirma(documento.getMimetypeVerPreFirma());
		lDocumentoBean.setUriFileVerPreFirma(documento.getUriVerPreFirma());
		lDocumentoBean.setNomeFileVerPreFirma(documento.getDisplayFilenameVerPreFirma());
		lDocumentoBean.setFlgConvertibilePdfVerPreFirma(documento.getFlgConvertibilePdfVerPreFirma());
		lDocumentoBean.setImprontaVerPreFirma(documento.getImprontaVerPreFirma());		
		lDocumentoBean.setNroUltimaVersione(documento.getNroUltimaVersione());
		lDocumentoBean.setIdEmail(documento.getIdEmail());
		lDocumentoBean.setEstremiProtUd(documento.getEstremiProtUd());
		lDocumentoBean.setNroProtocollo(documento.getNroProtocollo());
		lDocumentoBean.setAnnoProtocollo(documento.getAnnoProtocollo());
		lDocumentoBean.setDataProtocollo(documento.getDataProtocollo());
		lDocumentoBean.setDataRicezione(documento.getDataRicezione());
		lDocumentoBean.setFlgDatiSensibili(documento.getFlgDatiSensibili() == Flag.SETTED);
		// Vers. omissis
		lDocumentoBean.setIdDocOmissis(StringUtils.isNotBlank(documento.getIdDocOmissis()) ? new BigDecimal(documento.getIdDocOmissis()) : null);
		lDocumentoBean.setNomeFileOmissis(documento.getDisplayFileNameOmissis());
		MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = new MimeTypeFirmaBean();
		lDocumentoBean.setUriFileOmissis(documento.getUriOmissis());
		lDocumentoBean.setRemoteUriOmissis(true);
		if (StringUtils.isNotBlank(documento.getImprontaOmissis())) {
			lMimeTypeFirmaBeanOmissis.setImpronta(documento.getImprontaOmissis());
		}
		lMimeTypeFirmaBeanOmissis.setCorrectFileName(lDocumentoBean.getNomeFileOmissis());
		lMimeTypeFirmaBeanOmissis.setFirmato(documento.getFlgFileFirmatoOmissis() == Flag.SETTED);
		lMimeTypeFirmaBeanOmissis.setFirmaValida(documento.getFlgFileFirmatoOmissis() == Flag.SETTED);
		lMimeTypeFirmaBeanOmissis.setConvertibile(documento.getFlgConvertibilePdfOmissis() == Flag.SETTED);
		lMimeTypeFirmaBeanOmissis.setDaScansione(false);
		lMimeTypeFirmaBeanOmissis.setMimetype(documento.getMimetypeOmissis());
		lMimeTypeFirmaBeanOmissis.setBytes(documento.getDimensioneOmissis() != null ? documento.getDimensioneOmissis().longValue() : 0);
		if (lMimeTypeFirmaBeanOmissis.isFirmato()) {
			lMimeTypeFirmaBeanOmissis.setTipoFirma(documento.getDisplayFileNameOmissis().toUpperCase().endsWith("P7M")
					|| documento.getDisplayFileNameOmissis().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
			lMimeTypeFirmaBeanOmissis.setFirmatari(StringUtils.isNotBlank(documento.getFirmatariOmissis()) ? documento.getFirmatariOmissis().split(",") : null);
		}
		lDocumentoBean.setInfoFileOmissis(lMimeTypeFirmaBeanOmissis);
		lDocumentoBean.setUriFileVerPreFirmaOmissis(documento.getUriVerPreFirmaOmissis());
		lDocumentoBean.setNomeFileVerPreFirmaOmissis(documento.getDisplayFilenameVerPreFirmaOmissis());
		lDocumentoBean.setFlgConvertibilePdfVerPreFirmaOmissis(documento.getFlgConvertibilePdfVerPreFirmaOmissis());
		lDocumentoBean.setImprontaVerPreFirmaOmissis(documento.getImprontaVerPreFirmaOmissis());
		lDocumentoBean.setNroUltimaVersioneOmissis(documento.getNroUltimaVersioneOmissis());
		return lDocumentoBean;
	}

	@Override
	public ArchivioBean update(ArchivioBean bean, ArchivioBean oldvalue) throws Exception {

		boolean isRichiestaAccessoAtti = getExtraparams().get("isRichiestaAccessoAtti") != null && "true".equals(getExtraparams().get("isRichiestaAccessoAtti"));
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		controlloFirmeDocIstruttoriaXNumerazione(bean);
		if(bean.getErroriFile() != null && !bean.getErroriFile().isEmpty()) {
			return bean;
		}

		// creo l'input
		ModificaFascicoloIn lModificaFascicoloIn = new ModificaFascicoloIn();

		// ID FOLDER
		lModificaFascicoloIn.setIdFolderIn(StringUtils.isNotEmpty(bean.getIdUdFolder()) ? new BigDecimal(bean.getIdUdFolder()) : null);

		// creo l'XML
		XmlFascicoloIn xmlFascicoloIn = new XmlFascicoloIn();
		xmlFascicoloIn.setFlgSottoFascInserto(bean.getFlgSottoFascInserto());
		if (StringUtils.isNotBlank(bean.getIdFolderType())) {
			xmlFascicoloIn.setIdFolderType(new BigDecimal(bean.getIdFolderType()));
		} else if (StringUtils.isNotBlank(bean.getFolderType())) {
			xmlFascicoloIn.setIdFolderType(new BigDecimal(bean.getFolderType()));
		}
		xmlFascicoloIn.setNomeFolder(bean.getNomeFascicolo());
		xmlFascicoloIn.setNroSecondario(bean.getCodice());
		xmlFascicoloIn.setIdClassificazione(null);
		
		xmlFascicoloIn.setTimestampGetData(bean.getTimestampGetData());
		
		if (bean.getFlgFascTitolario() != null && !bean.getFlgFascTitolario()) {
			xmlFascicoloIn.setFlgFascTitolario(Flag.NOT_SETTED);
		}

		// FOLDER APPARTENENZA
		if (StringUtils.isNotBlank(bean.getIdFolderApp())) {
			xmlFascicoloIn.setFolderAppartenenza(new ArrayList<FolderAppartenenzaBean>());
		} else {
			List<FolderAppartenenzaBean> folderAppartenenza = new ArrayList<FolderAppartenenzaBean>();
			FolderAppartenenzaBean lFolderAppartenenzaBean = new FolderAppartenenzaBean();
			lFolderAppartenenzaBean.setIdFolder(new BigDecimal(bean.getIdFolderApp()));
			folderAppartenenza.add(lFolderAppartenenzaBean);
			xmlFascicoloIn.setFolderAppartenenza(folderAppartenenza);
		}

		xmlFascicoloIn.setDesOgg(bean.getDescContenutiFascicolo());

		// // ID UO REPONSABILE (se TYPE NODO == UO)
		// if (bean.getTypeNodo()!= null && bean.getTypeNodo().equalsIgnoreCase("UO"))
		// xmlFascicoloIn.setIdUOResponsabile(StringUtils.isNotEmpty(bean.getResponsabileFascicolo()) ? new BigDecimal(bean.getResponsabileFascicolo()) : null);
		//
		// // ID SCRIV REPONSABILE (se TYPE NODO == SV)
		// if (bean.getTypeNodo()!= null && bean.getTypeNodo().equalsIgnoreCase("SV"))
		// xmlFascicoloIn.setIdScrivResponsabile(StringUtils.isNotEmpty(bean.getResponsabileFascicolo()) ? new BigDecimal(bean.getResponsabileFascicolo()) :
		// null);

		xmlFascicoloIn.setLivRiservatezza(bean.getLivelloRiservatezza());
		xmlFascicoloIn.setDtTermineRiservatezza(bean.getDtTermineRiservatezza());
		if (bean.getPropagaRiservatezzaContenuti() != null && bean.getPropagaRiservatezzaContenuti()) {
			xmlFascicoloIn.setPropagaRiservatezzaContenuti("1");
		}
		xmlFascicoloIn.setLivEvidenza(bean.getPriorita() != null ? "" + bean.getPriorita().intValue() : null);

		salvaAssegnatari(bean, xmlFascicoloIn);
		salvaInvioDestCC(bean, xmlFascicoloIn);
		salvaACL(bean, xmlFascicoloIn);

		CollocazioneFisicaFascicoloBean lCollocazioneFisicaFascicoloBean = new CollocazioneFisicaFascicoloBean();
		lCollocazioneFisicaFascicoloBean.setIdTopografico(bean.getIdTopografico());
		lCollocazioneFisicaFascicoloBean.setCodRapido(bean.getCodRapidoTopografico());
		lCollocazioneFisicaFascicoloBean.setDescrizione(bean.getNomeTopografico());

		xmlFascicoloIn.setCollocazioneFisica(lCollocazioneFisicaFascicoloBean);
		xmlFascicoloIn.setNote(bean.getNoteFascicolo());

		salvaIstruttoreProc(bean, xmlFascicoloIn);
				
		xmlFascicoloIn.setFlgDocumentazioneCompleta(bean.getFlgDocumentazioneCompleta() != null && bean.getFlgDocumentazioneCompleta() ? Flag.SETTED : null);
		
		xmlFascicoloIn.setNumProtocolloGenerale(bean.getNumProtocolloGenerale());
		xmlFascicoloIn.setAnnoProtocolloGenerale(bean.getAnnoProtocolloGenerale());
		xmlFascicoloIn.setSiglaProtocolloSettore(bean.getSiglaProtocolloSettore());
		xmlFascicoloIn.setNumProtocolloSettore(bean.getNumProtocolloSettore());
		xmlFascicoloIn.setSubProtocolloSettore(bean.getSubProtocolloSettore());
		xmlFascicoloIn.setAnnoProtocolloSettore(bean.getAnnoProtocolloSettore());
		xmlFascicoloIn.setDtRichPraticaUrbanistica(bean.getDtRichPraticaUrbanistica());
		xmlFascicoloIn.setCodClassFascEdilizio(bean.getCodClassFascEdilizio());
		xmlFascicoloIn.setNumeroDeposito(bean.getNumeroDeposito());
		xmlFascicoloIn.setAnnoDeposito(bean.getAnnoDeposito());	
		xmlFascicoloIn.setNumeroEP(bean.getNumeroEP());
		xmlFascicoloIn.setAnnoEP(bean.getAnnoEP());	
		xmlFascicoloIn.setNumeroLicenza(bean.getNumeroLicenza());
		xmlFascicoloIn.setAnnoLicenza(bean.getAnnoLicenza());	
		xmlFascicoloIn.setNumProtocolloGeneraleRichPratica(bean.getNumProtocolloGeneraleRichPratica());
		xmlFascicoloIn.setAnnoProtocolloGeneraleRichPratica(bean.getAnnoProtocolloGeneraleRichPratica());
		xmlFascicoloIn.setNumProtocolloGeneraleRichPraticaWF(bean.getNumProtocolloGeneraleRichPraticaWF());
		xmlFascicoloIn.setAnnoProtocolloGeneraleRichPraticaWF(bean.getAnnoProtocolloGeneraleRichPraticaWF());
		
		if (bean.getListaEsibentiPraticaPregressa() != null && !bean.getListaEsibentiPraticaPregressa().isEmpty()) {
			List<ValueBean> listaEsibentiPraticaPregressa = new ArrayList<ValueBean>();
			for(ValueBean esibenteItem : bean.getListaEsibentiPraticaPregressa()) {
				ValueBean lValueBean = new ValueBean();
				lValueBean.setValue(esibenteItem.getValue());
				
				listaEsibentiPraticaPregressa.add(lValueBean);
			}
			xmlFascicoloIn.setListaEsibentiPraticaPregressa(listaEsibentiPraticaPregressa);
		}
		
		salvaAltreUbicazioni(bean, xmlFascicoloIn);
		
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		salvaAttributiCustomSemplici(bean, sezioneCacheAttributiDinamici);
		salvaAttributiCustomLista(bean, sezioneCacheAttributiDinamici);
		xmlFascicoloIn.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);

		lModificaFascicoloIn.setModificaFascicolo(xmlFascicoloIn);
		if(isRichiestaAccessoAtti) {
			lModificaFascicoloIn.setFlgAppendDocFascIstruttoria(Flag.SETTED);
		}

		boolean isPraticaPregressa = getExtraparams().get("isPraticaPregressa") != null && "true".equals(getExtraparams().get("isPraticaPregressa"));
		if(isPraticaPregressa) {		
			lModificaFascicoloIn.setIsCaricaPraticaPregressa(true);
			salvaDocumentiPraticaPregressa(bean, lModificaFascicoloIn);		
		}  else {
			salvaDocumentiIstruttoria(bean, lModificaFascicoloIn);
		}	
		
		GestioneFascicoli lGestioneFascicoli = new GestioneFascicoli();
		ModificaFascicoloOut modificaFascicoloOut = new ModificaFascicoloOut();

		try {
			modificaFascicoloOut = lGestioneFascicoli.modificafascicolo(getLocale(), lAurigaLoginBean, lModificaFascicoloIn);
		} catch (Exception e) {
			throw new StoreException("Si è verificato un errore durante il salvataggio del fascicolo: " + e.getMessage());
		}
		
		if (modificaFascicoloOut.isInError()) {
			throw new StoreException(modificaFascicoloOut);
		} else if (StringUtils.isNotBlank(modificaFascicoloOut.getDefaultMessage())) {
			addMessage(modificaFascicoloOut.getDefaultMessage(), "", MessageType.WARNING);
		}

		StringBuffer lStringBuffer = new StringBuffer();
		if (modificaFascicoloOut.getFileInErrors() != null && modificaFascicoloOut.getFileInErrors().size() > 0) {
			lStringBuffer.append("Si sono verificati degli errori durante il salvataggio dei documenti istruttoria. ");
			for (String lStrFileInError : modificaFascicoloOut.getFileInErrors().values()) {
				lStringBuffer.append(lStrFileInError + ". ");
			}
			throw new StoreException(lStringBuffer.toString());
		}

		invioNotificheAssegnazioneInvioCC(bean, lModificaFascicoloIn.getModificaFascicolo(),true);

		return bean;
	}
		
	public ArchivioBean updateDocumentiIstruttoriaFascicoloFromUdAtto(ArchivioBean bean, BigDecimal idUdAtto) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		ModificaFascicoloIn lModificaFascicoloIn = new ModificaFascicoloIn();
		
		// ID FOLDER
		lModificaFascicoloIn.setIdFolderIn(StringUtils.isNotEmpty(bean.getIdUdFolder()) ? new BigDecimal(bean.getIdUdFolder()) : null);
		// IMPORTANTE: l'idUdAtto va passato solo se proveniamo dalla maschera di dettaglio proposta atto 
		// in questo caso in update il confronto dei documenti di istruttoria deve essere fatto con quelli in ouput alla load di dettaglio UD e non alla load di dettaglio fascicolo
		lModificaFascicoloIn.setIdUdAtto(idUdAtto);

		// passo a null l'XML per non fare la UpdFolder e salvare solo i documenti istruttoria del fascicolo e non i metadati
		lModificaFascicoloIn.setModificaFascicolo(null);
		
//		lModificaFascicoloIn.setFlgAppendDocFascIstruttoria(Flag.SETTED);
		salvaDocumentiIstruttoria(bean, lModificaFascicoloIn);
		
		GestioneFascicoli lGestioneFascicoli = new GestioneFascicoli();
		ModificaFascicoloOut modificaFascicoloOut = new ModificaFascicoloOut();

		try {
			modificaFascicoloOut = lGestioneFascicoli.modificafascicolo(getLocale(), loginBean, lModificaFascicoloIn);
		} catch (Exception e) {
			throw new StoreException("Si è verificato un errore durante il salvataggio dei documenti fascicolo: " + e.getMessage());
		}
		
		if (modificaFascicoloOut.isInError()) {
			throw new StoreException(modificaFascicoloOut);
		} else if (StringUtils.isNotBlank(modificaFascicoloOut.getDefaultMessage())) {
			addMessage(modificaFascicoloOut.getDefaultMessage(), "", MessageType.WARNING);
		}

		StringBuffer lStringBuffer = new StringBuffer();
		if (modificaFascicoloOut.getFileInErrors() != null && modificaFascicoloOut.getFileInErrors().size() > 0) {
			lStringBuffer.append("Si sono verificati degli errori durante il salvataggio dei documenti fascicolo. ");
			for (String lStrFileInError : modificaFascicoloOut.getFileInErrors().values()) {
				lStringBuffer.append(lStrFileInError + ". ");
			}
			throw new StoreException(lStringBuffer.toString());
		}
				
		return bean;
	}
	
	public void salvaDocumentiPraticaPregressa(ArchivioBean bean, AllegatiBean lAllegatiBean) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		if (bean.getListaDocumentiPraticaPregressa() != null) {
			List<BigDecimal> idUd = new ArrayList<BigDecimal>();			
			List<BigDecimal> idDocumento = new ArrayList<BigDecimal>();			
			List<String> nomeDocType = new ArrayList<String>();
			List<String> descrizione = new ArrayList<String>();
			List<Boolean> flgAllegato = new ArrayList<Boolean>();
			List<Boolean> flgCapofila = new ArrayList<Boolean>();
			List<String> tipoProt = new ArrayList<String>();
			List<String> siglaProtSettore = new ArrayList<String>();
			List<String> nroProt = new ArrayList<String>();
			List<String> annoProt = new ArrayList<String>();
			List<String> nroDeposito = new ArrayList<String>();
			List<String> annoDeposito = new ArrayList<String>();
			List<String> mittentiEsibenti = new ArrayList<String>();
			List<Boolean> flgUdDaDataEntryScansioni = new ArrayList<Boolean>();
			List<Boolean> flgNextDocAllegato = new ArrayList<Boolean>();
			List<File> fileAllegati = new ArrayList<File>();
			List<Boolean> isNull = new ArrayList<Boolean>();
			List<Boolean> isNewOrChanged = new ArrayList<Boolean>();
			List<String> displayFilename = new ArrayList<String>();
			List<FileInfoBean> info = new ArrayList<FileInfoBean>();				
			List<Boolean> flgDatiSensibili = new ArrayList<Boolean>();			
			// Vers. con omissis
			List<BigDecimal> idDocumentoOmissis = new ArrayList<BigDecimal>();
			List<File> fileAllegatiOmissis = new ArrayList<File>();
			List<Boolean> isNullOmissis = new ArrayList<Boolean>();
			List<Boolean> isNewOrChangedOmissis = new ArrayList<Boolean>();
			List<String> displayFilenameOmissis = new ArrayList<String>();
			List<FileInfoBean> infoOmissis = new ArrayList<FileInfoBean>();				
			int i = 1;
			for (DocPraticaPregressaBean allegato : bean.getListaDocumentiPraticaPregressa()) {
				String tipoDocScansionePraticaEdilizia = ParametriDBUtil.getParametroDB(getSession(), "TIPO_DOC_SCANSIONE_PRATICA_EDILIZIA");
				nomeDocType.add(StringUtils.isNotBlank(tipoDocScansionePraticaEdilizia) ? tipoDocScansionePraticaEdilizia : "Scansione pratica urbanistica");
				flgAllegato.add(allegato.getFlgAllegato());
				flgCapofila.add(allegato.getFlgCapofila());
				if(allegato.getFlgCapofila() != null && allegato.getFlgCapofila()) {
					BigDecimal idUdCapofila = null;
					if (StringUtils.isNotBlank(allegato.getIdUdAppartenenza())) {
						idUdCapofila = new BigDecimal(allegato.getIdUdAppartenenza());
					} else if (StringUtils.isNotBlank(bean.getIdUdCapofila())) {
						idUdCapofila = new BigDecimal(bean.getIdUdCapofila());							
					} 
					BigDecimal idDocAllegato = null;
					if (allegato.getIdDocAllegato() != null) {
						// se mi arriva da dettaglio l'idDoc uso quello
						idDocAllegato = allegato.getIdDocAllegato();
					} else if (StringUtils.isNotBlank(bean.getIdDocPrimario())) {
						// se il file da inserire per quell'UD è il primario
						boolean isNextDocAllegato = bean.getFlgNextDocAllegato() != null && bean.getFlgNextDocAllegato();
						boolean isDocPrimarioGiaPresente = false;
						for(int j = 0; j < idDocumento.size(); j++) {
							String idDocCorrente = idDocumento.get(j) != null ? String.valueOf(idDocumento.get(j).longValue()) : null; 
							if(idDocCorrente != null && idDocCorrente.equals(bean.getIdDocPrimario())) {
								isDocPrimarioGiaPresente = true;
								break;
							}
						}
						if (!isNextDocAllegato && !isDocPrimarioGiaPresente) {
							idDocAllegato = new BigDecimal(bean.getIdDocPrimario());
						}
					}	
					if(idUdCapofila != null) {
						idUd.add(idUdCapofila);
						idDocumento.add(idDocAllegato);					
						flgUdDaDataEntryScansioni.add(bean.getFlgUdDaDataEntryScansioni());	
						flgNextDocAllegato.add(bean.getFlgNextDocAllegato());
					} else {
						idUd.add(null);
						idDocumento.add(null);
						flgUdDaDataEntryScansioni.add(true);
						flgNextDocAllegato.add(false);
					}						
					if(StringUtils.isNotBlank(bean.getNumProtocolloGenerale())) {
						tipoProt.add("PG"); 
						siglaProtSettore.add(null);
						nroProt.add(bean.getNumProtocolloGenerale());
						annoProt.add(bean.getAnnoProtocolloGenerale());
					} else if(StringUtils.isNotBlank(bean.getNumProtocolloSettore())) {
						tipoProt.add("PP");
						siglaProtSettore.add(bean.getSiglaProtocolloSettore());
						nroProt.add(bean.getNumProtocolloSettore());
						annoProt.add(bean.getAnnoProtocolloSettore());
					}
					nroDeposito.add(bean.getNumeroDeposito());
					annoDeposito.add(bean.getAnnoDeposito());
					if(bean.getListaEsibentiPraticaPregressa() != null) {
						String mittEsib = null;
						for(int j = 0; j < bean.getListaEsibentiPraticaPregressa().size(); j++) {
							if(mittEsib == null) {
								mittEsib = bean.getListaEsibentiPraticaPregressa().get(j).getValue();
							} else {
								mittEsib += "; " + bean.getListaEsibentiPraticaPregressa().get(j).getValue();
							}
						}
						mittentiEsibenti.add(mittEsib);
					}
					descrizione.add(bean.getDescContenutiFascicolo());
				} else {
					if(allegato.getIdDocAllegato() != null) {
						idUd.add(StringUtils.isNotBlank(allegato.getIdUdAppartenenza()) ? new BigDecimal(allegato.getIdUdAppartenenza()) : null);
						// se il file da inserire per quell'UD è il primario
						boolean isNextDocAllegato = allegato.getFlgNextDocAllegato() != null && allegato.getFlgNextDocAllegato();
						String idDoc = allegato.getIdDocAllegato() != null ? String.valueOf(allegato.getIdDocAllegato().longValue()) : null; 
						boolean isDocGiaPresente = false;
						for(int j = 0; j < idDocumento.size(); j++) {
							String idDocCorrente = idDocumento.get(j) != null ? String.valueOf(idDocumento.get(j).longValue()) : null; 
							if(idDocCorrente != null && idDocCorrente.equals(idDoc)) {
								isDocGiaPresente = true;
								break;
							}
						}
						if (!isNextDocAllegato && !isDocGiaPresente) {
							idDocumento.add(allegato.getIdDocAllegato());					
						} else {
							idDocumento.add(null);
						}
						flgUdDaDataEntryScansioni.add(allegato.getFlgUdDaDataEntryScansioni());
						flgNextDocAllegato.add(allegato.getFlgNextDocAllegato());
					} else {
						idUd.add(null);
						idDocumento.add(null);
						flgUdDaDataEntryScansioni.add(true);
						flgNextDocAllegato.add(false);						
					}	
					tipoProt.add(allegato.getTipoProt());
					if(allegato.getTipoProt() != null && allegato.getTipoProt().equalsIgnoreCase("PP")) {
						siglaProtSettore.add(allegato.getSiglaProtSettore());
					} else {
						siglaProtSettore.add(null);
					}
					nroProt.add(allegato.getNroProt());
					annoProt.add(allegato.getAnnoProt());
					nroDeposito.add(allegato.getNroDeposito());
					annoDeposito.add(allegato.getAnnoDeposito());
					mittentiEsibenti.add(allegato.getEsibenti());
					descrizione.add(allegato.getOggetto());					
				}
				displayFilename.add(allegato.getNomeFileAllegato());
				flgDatiSensibili.add(allegato.getFlgDatiSensibili());
				File lFile = null;
				if (StringUtils.isNotBlank(allegato.getUriFileAllegato()) && StringUtils.isNotBlank(allegato.getNomeFileAllegato())) {
					if (allegato.getRemoteUri() != null && allegato.getRemoteUri()) {
						// Il file è esterno
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileAllegato());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
						lFile = out.getExtracted();
					} else {
						// File locale
						lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
					}						
					if (allegato.getInfoFile() == null || StringUtils.isBlank(allegato.getInfoFile().getImpronta())) {
						allegato.setInfoFile(new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), allegato.getNomeFileAllegato(), false, null));
						if (allegato.getInfoFile() == null || StringUtils.isBlank(allegato.getInfoFile().getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato " + allegato.getNomeFileAllegato());
						}
					}
				}
				if (lFile != null) {
					isNull.add(false);
					isNewOrChanged.add(allegato.getIdDocAllegato() == null || (allegato.getIsChanged() != null && allegato.getIsChanged()));
					if (allegato.getIdDocAllegato() == null || (allegato.getIsChanged() != null && allegato.getIsChanged())) {					
						fileAllegati.add(lFile);
					}
					MimeTypeFirmaBean lMimeTypeFirmaBean = allegato.getInfoFile();
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.PRIMARIO);
					GenericFile lGenericFile = new GenericFile();
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename(allegato.getNomeFileAllegato());
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBean.isDaScansione()) {
						lGenericFile.setDaScansione(Flag.SETTED);
						lGenericFile.setDataScansione(new Date());
						lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
					}
					lFileInfoBean.setPosizione(i);
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					info.add(lFileInfoBean);
				} else {
					isNull.add(true);
					isNewOrChanged.add(null);
					info.add(new FileInfoBean());
				}
				// Vers. con omissis
				idDocumentoOmissis.add(allegato.getIdDocOmissis());
				displayFilenameOmissis.add(allegato.getNomeFileOmissis());
				File lFileAllegatoOmissis = null;
				if ((allegato.getFlgDatiSensibili() != null && allegato.getFlgDatiSensibili()) && StringUtils.isNotBlank(allegato.getUriFileOmissis()) && StringUtils.isNotBlank(allegato.getNomeFileOmissis())) {
					if (allegato.getRemoteUriOmissis() != null && allegato.getRemoteUriOmissis()) {
						// Il file è esterno
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileOmissis());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
						lFileAllegatoOmissis = out.getExtracted();
					} else {
						// File locale
						lFileAllegatoOmissis = StorageImplementation.getStorage().extractFile(allegato.getUriFileOmissis());
					}
					if (allegato.getInfoFileOmissis() == null || StringUtils.isBlank(allegato.getInfoFileOmissis().getImpronta())) {
						allegato.setInfoFileOmissis(new InfoFileUtility().getInfoFromFile(lFileAllegatoOmissis.toURI().toString(), allegato.getNomeFileOmissis(), false, null));
						if (allegato.getInfoFileOmissis() == null || StringUtils.isBlank(allegato.getInfoFileOmissis().getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato " + allegato.getNomeFileOmissis() + " (vers. con omissis)");
						}
					}
				}
				if (lFileAllegatoOmissis != null) {
					isNullOmissis.add(false);
					isNewOrChangedOmissis.add(allegato.getIdDocOmissis() == null || (allegato.getIsChangedOmissis() != null && allegato.getIsChangedOmissis()));					
					if (allegato.getIdDocOmissis() == null || (allegato.getIsChangedOmissis() != null && allegato.getIsChangedOmissis())){
						fileAllegatiOmissis.add(lFileAllegatoOmissis);
					}
					MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = allegato.getInfoFileOmissis();					
					FileInfoBean lFileInfoBeanOmissis = new FileInfoBean();
					lFileInfoBeanOmissis.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFileOmissis = new GenericFile();
					setProprietaGenericFile(lGenericFileOmissis, lMimeTypeFirmaBeanOmissis);
					lGenericFileOmissis.setMimetype(lMimeTypeFirmaBeanOmissis.getMimetype());
					lGenericFileOmissis.setDisplayFilename(allegato.getNomeFileOmissis());
					lGenericFileOmissis.setImpronta(lMimeTypeFirmaBeanOmissis.getImpronta());
					lGenericFileOmissis.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFileOmissis.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBeanOmissis.isDaScansione()) {
						lGenericFileOmissis.setDaScansione(Flag.SETTED);
						lGenericFileOmissis.setDataScansione(new Date());
						lGenericFileOmissis.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
					}
					lFileInfoBeanOmissis.setPosizione(i);
					lFileInfoBeanOmissis.setAllegatoRiferimento(lGenericFileOmissis);
					infoOmissis.add(lFileInfoBeanOmissis);
				} else {
					isNullOmissis.add(true);
					isNewOrChangedOmissis.add(null);
					infoOmissis.add(new FileInfoBean());
				}
				i++;
			}
			lAllegatiBean.setIdUd(idUd);			
			lAllegatiBean.setIdDocumento(idDocumento);			
			lAllegatiBean.setNomeDocType(nomeDocType);	
			lAllegatiBean.setDescrizione(descrizione);
			lAllegatiBean.setFlgAllegato(flgAllegato);
			lAllegatiBean.setFlgCapofila(flgCapofila);
			lAllegatiBean.setTipoProt(tipoProt);
			lAllegatiBean.setSiglaProtSettore(siglaProtSettore);
			lAllegatiBean.setNroProt(nroProt);
			lAllegatiBean.setAnnoProt(annoProt);
			lAllegatiBean.setNroDeposito(nroDeposito);
			lAllegatiBean.setAnnoDeposito(annoDeposito);
			lAllegatiBean.setMittentiEsibenti(mittentiEsibenti);			
			lAllegatiBean.setFlgUdDaDataEntryScansioni(flgUdDaDataEntryScansioni);
			lAllegatiBean.setFlgNextDocAllegato(flgNextDocAllegato);
			lAllegatiBean.setDisplayFilename(displayFilename);			
			lAllegatiBean.setIsNull(isNull);
			lAllegatiBean.setIsNewOrChanged(isNewOrChanged);
			lAllegatiBean.setFileAllegati(fileAllegati);
			lAllegatiBean.setInfo(info);
			lAllegatiBean.setFlgDatiSensibili(flgDatiSensibili);			
			lAllegatiBean.setIdDocumentoOmissis(idDocumentoOmissis);
			lAllegatiBean.setDisplayFilenameOmissis(displayFilenameOmissis);
			lAllegatiBean.setIsNullOmissis(isNullOmissis);
			lAllegatiBean.setIsNewOrChangedOmissis(isNewOrChangedOmissis);
			lAllegatiBean.setFileAllegatiOmissis(fileAllegatiOmissis);
			lAllegatiBean.setInfoOmissis(infoOmissis);
		}
	}
	
	public void salvaDocumentiIstruttoria(ArchivioBean bean, AllegatiBean lAllegatiBean) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		if (bean.getListaDocumentiIstruttoria() != null) {
			List<Boolean> flgSalvato = new ArrayList<Boolean>(); 
			List<Boolean> flgNoModificaDati = new ArrayList<Boolean>();			
			List<BigDecimal> idDocumento = new ArrayList<BigDecimal>();
			List<String> descrizione = new ArrayList<String>();
			List<Integer> docType = new ArrayList<Integer>();
			List<String> sezionePratica = new ArrayList<String>();
			List<File> fileAllegati = new ArrayList<File>();
			List<Boolean> isNull = new ArrayList<Boolean>();
			List<Boolean> isNewOrChanged = new ArrayList<Boolean>();
			List<String> displayFilename = new ArrayList<String>();
			List<FileInfoBean> info = new ArrayList<FileInfoBean>();
			List<Boolean> flgParteDispositivo = new ArrayList<Boolean>();
			List<String> idTask = new ArrayList<String>();
			List<String> idTaskVer = new ArrayList<String>();
			List<Boolean> flgNoPubbl = new ArrayList<Boolean>();
			List<Boolean> flgOriginaleCartaceo = new ArrayList<Boolean>();
			List<Boolean> flgCopiaSostitutiva = new ArrayList<Boolean>();
			List<Boolean> flgDaProtocollare = new ArrayList<Boolean>();
			int i = 1;
			for (AllegatoProtocolloBean allegato : bean.getListaDocumentiIstruttoria()) {
				flgSalvato.add(allegato.getFlgSalvato() != null && allegato.getFlgSalvato());
				//TODO qui non leggo il valore flgNoModificaDati che mi arriva dalla lista allegati ma lo setto a true solo se è una UD protocollata o repertoriata
				flgNoModificaDati.add(allegato.getEstremiProtUd() != null && !"".equals(allegato.getEstremiProtUd())); // se è una UD protocollata o repertoriata non devo fare l'update dei metadati ma solo agganciare il folder
				idDocumento.add(allegato.getIdDocAllegato());
				descrizione.add(allegato.getDescrizioneFileAllegato());
				try {
					docType.add(StringUtils.isNotBlank(allegato.getListaTipiFileAllegato()) ? Integer.valueOf(allegato.getListaTipiFileAllegato()) : null);
				} catch(Exception e) {
					docType.add(null);
				}
				try {
					sezionePratica.add(StringUtils.isNotBlank(allegato.getListaTipiFileAllegato()) ? allegato.getListaTipiFileAllegato() : null);
				} catch(Exception e) {
					sezionePratica.add(null);
				}
				displayFilename.add(allegato.getNomeFileAllegato());
				flgParteDispositivo.add(allegato.getFlgParteDispositivo());
				// solo se è un allegato inserito in quella attività e se l'attività è readonly salvo l'idTask
				if (allegato.getIdDocAllegato() == null) {
					idTask.add(getExtraparams().get("idTaskCorrente"));
				} else {
					idTask.add(allegato.getIdTask());
				}
				idTaskVer.add(allegato.getIdTaskVer());				
				flgNoPubbl.add(allegato.getFlgNoPubblAllegato());
				flgOriginaleCartaceo.add(allegato.getFlgOriginaleCartaceo());
				flgCopiaSostitutiva.add(allegato.getFlgCopiaSostitutiva()); 
				flgDaProtocollare.add(allegato.getFlgDaProtocollare());
				File lFile = null;
				if (StringUtils.isNotBlank(allegato.getUriFileAllegato()) && StringUtils.isNotBlank(allegato.getNomeFileAllegato())) {
					if (allegato.getRemoteUri() != null && allegato.getRemoteUri()) {
						// Il file è esterno
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileAllegato());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
						lFile = out.getExtracted();
					} else {
						// File locale
						lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
					}						
					if (allegato.getInfoFile() == null || StringUtils.isBlank(allegato.getInfoFile().getImpronta())) {
						allegato.setInfoFile(new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), allegato.getNomeFileAllegato(), false, null));
						if (allegato.getInfoFile() == null || StringUtils.isBlank(allegato.getInfoFile().getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato " + allegato.getNomeFileAllegato());
						}
					}
				}
				boolean flgGenDaModelloDaFirmare = allegato.getFlgGenDaModelloDaFirmare() != null && allegato.getFlgGenDaModelloDaFirmare();
				boolean flgGenDaModelloFirmato = allegato.getInfoFile() != null && allegato.getInfoFile().isFirmato();
				// se ho un documento fascicolo generato da modello che era da firmare ma non è stato firmato quindi ci tolgo il file (e sarebbe da fare anche la rollback della numerazione)
				if(flgGenDaModelloDaFirmare && !flgGenDaModelloFirmato) {
					mLogger.error("ATTENZIONE: ho un documento fascicolo generato da modello che era da firmare ma non è stato firmato quindi ci tolgo il file -> " + allegato.getNomeFileAllegato());
					allegato.setUriFileAllegato(null);
					allegato.setNomeFileAllegato(null);
					lFile = null;
				}
				if (lFile != null) {
					isNull.add(false);
					isNewOrChanged.add(allegato.getIdDocAllegato() == null || (allegato.getIsChanged() != null && allegato.getIsChanged()));
					if (allegato.getIdDocAllegato() == null || (allegato.getIsChanged() != null && allegato.getIsChanged())) {					
						fileAllegati.add(lFile);
					}
					MimeTypeFirmaBean lMimeTypeFirmaBean = allegato.getInfoFile();
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.PRIMARIO);
					GenericFile lGenericFile = new GenericFile();
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename(allegato.getNomeFileAllegato());
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBean.isDaScansione()) {
						lGenericFile.setDaScansione(Flag.SETTED);
						lGenericFile.setDataScansione(new Date());
						lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
					}
					lFileInfoBean.setPosizione(i);
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					info.add(lFileInfoBean);
				} else {
					isNull.add(true);
					isNewOrChanged.add(null);
					info.add(new FileInfoBean());
				}
				i++;
			}
			lAllegatiBean.setFlgSalvato(flgSalvato);					
			lAllegatiBean.setFlgNoModificaDati(flgNoModificaDati);		
			lAllegatiBean.setIdDocumento(idDocumento);
			lAllegatiBean.setDescrizione(descrizione);
			lAllegatiBean.setDisplayFilename(displayFilename);
			lAllegatiBean.setDocType(docType);
			lAllegatiBean.setSezionePratica(sezionePratica);			
			lAllegatiBean.setIsNull(isNull);
			lAllegatiBean.setIsNewOrChanged(isNewOrChanged);
			lAllegatiBean.setFileAllegati(fileAllegati);
			lAllegatiBean.setInfo(info);
			lAllegatiBean.setFlgParteDispositivo(flgParteDispositivo);
			lAllegatiBean.setIdTask(idTask);
			lAllegatiBean.setIdTaskVer(idTaskVer);
			lAllegatiBean.setFlgNoPubbl(flgNoPubbl);
			lAllegatiBean.setFlgOriginaleCartaceo(flgOriginaleCartaceo);
			lAllegatiBean.setFlgCopiaSostitutiva(flgCopiaSostitutiva);
			lAllegatiBean.setFlgDaProtocollare(flgDaProtocollare);
		}
	}

	@Override
	public ArchivioBean add(ArchivioBean bean) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		controlloFirmeDocIstruttoriaXNumerazione(bean);
		if(bean.getErroriFile() != null && !bean.getErroriFile().isEmpty()) {
			return bean;
		}

		// creo l'input
		SalvaFascicoloIn lSalvaFascicoloIn = new SalvaFascicoloIn();

		// creo l'XML
		XmlFascicoloIn xmlFascicoloIn = new XmlFascicoloIn();
		// // FLAG SOTTOFASCICOLO/INSERTO (da fare)
		// xmlFascicoloIn.setFlgSottoFascInserto(bean.getFlgSottoFascInserto());
		if (StringUtils.isNotBlank(bean.getIdFolderType())) {
			xmlFascicoloIn.setIdFolderType(new BigDecimal(bean.getIdFolderType()));
		} else if (StringUtils.isNotBlank(bean.getFolderType())) {
			xmlFascicoloIn.setIdFolderType(new BigDecimal(bean.getFolderType()));
		}
		xmlFascicoloIn.setNomeFolder(bean.getNomeFascicolo());
		xmlFascicoloIn.setNroSecondario(bean.getCodice());
		// // ID CLASSIFICAZIONE ( valorizzare SOLO SE e' un FASCICOLO, NO se e' un sotto-fascicolo oppure e' un inserto )
		// if ((bean.getNroFascicolo()!=null && !bean.getNroFascicolo().equalsIgnoreCase("") ) &&
		// (bean.getNroSottofascicolo()==null || bean.getNroSottofascicolo().equalsIgnoreCase("")) &&
		// (!bean.getFlgSottoFascInserto().equals(FlagSottoFasc.INSERTO) )
		// ){
		// xmlFascicoloIn.setIdClassificazione(StringUtils.isNotEmpty(bean.getIdClassifica()) ? new BigDecimal(bean.getIdClassifica()) : null);
		// }

		if (bean.getFlgFascTitolario() != null && !bean.getFlgFascTitolario()) {
			xmlFascicoloIn.setFlgFascTitolario(Flag.NOT_SETTED);
		}

		if (StringUtils.isBlank(bean.getIdFolderApp())) {
			xmlFascicoloIn.setIdClassificazione(StringUtils.isNotEmpty(bean.getIdClassifica()) ? new BigDecimal(bean.getIdClassifica()) : null);
			xmlFascicoloIn.setFolderAppartenenza(new ArrayList<FolderAppartenenzaBean>());
		} else {
			// FOLDER APPARTENENZA
			List<FolderAppartenenzaBean> folderAppartenenza = new ArrayList<FolderAppartenenzaBean>();
			FolderAppartenenzaBean lFolderAppartenenzaBean = new FolderAppartenenzaBean();
			lFolderAppartenenzaBean.setIdFolder(new BigDecimal(bean.getIdFolderApp()));
			folderAppartenenza.add(lFolderAppartenenzaBean);
			xmlFascicoloIn.setFolderAppartenenza(folderAppartenenza);
		}

		xmlFascicoloIn.setDesOgg(bean.getDescContenutiFascicolo());

		// // ID UO REPONSABILE (se TYPE NODO == UO)
		// if (bean.getTypeNodo()!= null && bean.getTypeNodo().equalsIgnoreCase("UO"))
		// xmlFascicoloIn.setIdUOResponsabile(StringUtils.isNotEmpty(bean.getResponsabileFascicolo()) ? new BigDecimal(bean.getResponsabileFascicolo()) : null);
		//
		// // ID SCRIV REPONSABILE (se TYPE NODO == SV)
		// if (bean.getTypeNodo()!= null && bean.getTypeNodo().equalsIgnoreCase("SV"))
		// xmlFascicoloIn.setIdScrivResponsabile(StringUtils.isNotEmpty(bean.getResponsabileFascicolo()) ? new BigDecimal(bean.getResponsabileFascicolo()) :
		// null);

		xmlFascicoloIn.setLivRiservatezza(bean.getLivelloRiservatezza());
		xmlFascicoloIn.setDtTermineRiservatezza(bean.getDtTermineRiservatezza());
		xmlFascicoloIn.setLivEvidenza(bean.getPriorita() != null ? "" + bean.getPriorita().intValue() : null);

		salvaAssegnatari(bean, xmlFascicoloIn);
		salvaInvioDestCC(bean, xmlFascicoloIn);
		salvaACL(bean, xmlFascicoloIn);

		CollocazioneFisicaFascicoloBean lCollocazioneFisicaFascicoloBean = new CollocazioneFisicaFascicoloBean();
		lCollocazioneFisicaFascicoloBean.setIdTopografico(bean.getIdTopografico());
		lCollocazioneFisicaFascicoloBean.setCodRapido(bean.getCodRapidoTopografico());
		lCollocazioneFisicaFascicoloBean.setDescrizione(bean.getNomeTopografico());

		xmlFascicoloIn.setCollocazioneFisica(lCollocazioneFisicaFascicoloBean);

		xmlFascicoloIn.setNote(bean.getNoteFascicolo());
		
		xmlFascicoloIn.setFlgDocumentazioneCompleta(bean.getFlgDocumentazioneCompleta() != null && bean.getFlgDocumentazioneCompleta() ? Flag.SETTED : null);
		
		xmlFascicoloIn.setNumProtocolloGenerale(bean.getNumProtocolloGenerale());
		xmlFascicoloIn.setAnnoProtocolloGenerale(bean.getAnnoProtocolloGenerale());
		xmlFascicoloIn.setSiglaProtocolloSettore(bean.getSiglaProtocolloSettore());
		xmlFascicoloIn.setNumProtocolloSettore(bean.getNumProtocolloSettore());
		xmlFascicoloIn.setSubProtocolloSettore(bean.getSubProtocolloSettore());
		xmlFascicoloIn.setAnnoProtocolloSettore(bean.getAnnoProtocolloSettore());
		xmlFascicoloIn.setDtRichPraticaUrbanistica(bean.getDtRichPraticaUrbanistica());
		xmlFascicoloIn.setCodClassFascEdilizio(bean.getCodClassFascEdilizio());	
		xmlFascicoloIn.setNumeroDeposito(bean.getNumeroDeposito());
		xmlFascicoloIn.setAnnoDeposito(bean.getAnnoDeposito());
		xmlFascicoloIn.setNumeroEP(bean.getNumeroEP());
		xmlFascicoloIn.setAnnoEP(bean.getAnnoEP());	
		xmlFascicoloIn.setNumeroLicenza(bean.getNumeroLicenza());
		xmlFascicoloIn.setAnnoLicenza(bean.getAnnoLicenza());	
		xmlFascicoloIn.setNumProtocolloGeneraleRichPratica(bean.getNumProtocolloGeneraleRichPratica());
		xmlFascicoloIn.setAnnoProtocolloGeneraleRichPratica(bean.getAnnoProtocolloGeneraleRichPratica());
		xmlFascicoloIn.setNumProtocolloGeneraleRichPraticaWF(bean.getNumProtocolloGeneraleRichPraticaWF());
		xmlFascicoloIn.setAnnoProtocolloGeneraleRichPraticaWF(bean.getAnnoProtocolloGeneraleRichPraticaWF());
		
		if (bean.getListaEsibentiPraticaPregressa() != null && !bean.getListaEsibentiPraticaPregressa().isEmpty()) {
			List<ValueBean> listaEsibentiPraticaPregressa = new ArrayList<ValueBean>();
			for(ValueBean esibenteItem : bean.getListaEsibentiPraticaPregressa()) {
				ValueBean lValueBean = new ValueBean();
				lValueBean.setValue(esibenteItem.getValue());
				
				listaEsibentiPraticaPregressa.add(lValueBean);
			}
			xmlFascicoloIn.setListaEsibentiPraticaPregressa(listaEsibentiPraticaPregressa);
		}
		
		salvaAltreUbicazioni(bean, xmlFascicoloIn);

		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		salvaAttributiCustomSemplici(bean, sezioneCacheAttributiDinamici);
		salvaAttributiCustomLista(bean, sezioneCacheAttributiDinamici);
		xmlFascicoloIn.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);		

		lSalvaFascicoloIn.setXmlFascicolo(xmlFascicoloIn);

		boolean isPraticaPregressa = getExtraparams().get("isPraticaPregressa") != null && "true".equals(getExtraparams().get("isPraticaPregressa"));
		if(isPraticaPregressa) {			
			lSalvaFascicoloIn.setIsCaricaPraticaPregressa(true);
			salvaDocumentiPraticaPregressa(bean, lSalvaFascicoloIn);					
		}  else {
			salvaDocumentiIstruttoria(bean, lSalvaFascicoloIn);
		}		
		
		GestioneFascicoli lGestioneFascicoli = new GestioneFascicoli();
		SalvaFascicoloOut salvaFascicoloOut = new SalvaFascicoloOut();
		try {
			salvaFascicoloOut = lGestioneFascicoli.salvafascicolo(getLocale(), lAurigaLoginBean, lSalvaFascicoloIn);
			bean.setIdUdFolder(salvaFascicoloOut.getIdFolderOut() != null ? (StringUtils.isNotBlank(salvaFascicoloOut.getIdFolderOut().toString()) ? salvaFascicoloOut
					.getIdFolderOut().toString() : null)
					: null);
		} catch (Exception e) {
			throw new StoreException(" Errore : " + e.getMessage());
		}
	
		if (salvaFascicoloOut.isInError()) {
			throw new StoreException(salvaFascicoloOut);
		} else if (StringUtils.isNotBlank(salvaFascicoloOut.getDefaultMessage())) {
			addMessage(salvaFascicoloOut.getDefaultMessage(), "", MessageType.WARNING);
		}		

		invioNotificheAssegnazioneInvioCC(bean, lSalvaFascicoloIn.getXmlFascicolo(), false);

		// Leggo ANNO, NRO FASCICOLO, SOTTOFASCICOLO
		ArchivioBean archivioIn = new ArchivioBean();
		ArchivioBean archivioOut = new ArchivioBean();
		try {
			archivioIn.setIdUdFolder(bean.getIdUdFolder());
			archivioOut = get(archivioIn);
		} catch (Exception e) {
			throw new StoreException(" Errore : " + e.getMessage());
		}

		// sezione SEGNATURA
		bean.setIndiceClassifica(archivioOut.getIndiceClassifica()); // INDICE CLASSIFICA
		bean.setDescClassifica(archivioOut.getDescClassifica()); // DESCRIZIONE CLASSIFICA
		bean.setAnnoFascicolo(archivioOut.getAnnoFascicolo()); // ANNO FASCICOLO
		bean.setFlgSottoFascInserto(archivioOut.getFlgSottoFascInserto()); // FLG INSERTO
		bean.setNroFascicolo(archivioOut.getNroFascicolo()); // NRO FASCICOLO
		bean.setNroSottofascicolo(archivioOut.getNroSottofascicolo()); // NRO SOTTO FASCICOLO		
		bean.setNroInserto(archivioOut.getNroInserto()); // NRO INSERTO		
		bean.setTsApertura(archivioOut.getTsApertura()); // DATA APERTURA
		bean.setTsChiusura(archivioOut.getTsChiusura()); // DATA CHIUSURA
		bean.setNome(archivioOut.getNome()); // NOME
		bean.setNomeFascicolo(archivioOut.getNomeFascicolo()); // NOME FASCICOLO
		bean.setRowidFolder(archivioOut.getRowidFolder()); // ROWID

		return bean;
	}

	private void salvaAssegnatari(ArchivioBean bean, XmlFascicoloIn lXmlFascicoloIn) {
		// per i task dett. fascicolo non generici (es.: istanze e autotutela) li salvo perchè in questa lista viene passato l'ufficio di competenza
		if (!isTaskDettFascGen()) {
			List<AssegnatariBean> lListAssegnatari = new ArrayList<AssegnatariBean>();
			if (bean.getListaAssegnazioni() != null && bean.getListaAssegnazioni().size() > 0) {
				for (AssegnazioneBean lAssegnazioneBean : bean.getListaAssegnazioni()) {
					AssegnatariBean lAssegnatariBean = new AssegnatariBean();
					if (isTaskDettFasc()) {
						lAssegnatariBean.setTipo(TipoAssegnatario.UNITA_ORGANIZZATIVA);
						lAssegnatariBean.setIdSettato(lAssegnazioneBean.getIdUo());
					} else {
						lAssegnatariBean.setTipo(getTipoAssegnatario(lAssegnazioneBean));
						if (lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.GRUPPO)) {
							lAssegnatariBean.setIdSettato(lAssegnazioneBean.getGruppo());
						} else {
							lAssegnatariBean.setIdSettato(lAssegnazioneBean.getIdUo());
						}
					}
					lAssegnatariBean.setPermessiAccesso("FC");
					//TODO ASSEGNAZIONE SAVE OK
					if(lAssegnazioneBean.getOpzioniInvio() != null) {
						lAssegnatariBean.setMotivoInvio(lAssegnazioneBean.getOpzioniInvio().getMotivoInvio());
						lAssegnatariBean.setLivelloPriorita(lAssegnazioneBean.getOpzioniInvio().getLivelloPriorita());
						lAssegnatariBean.setMessaggioInvio(lAssegnazioneBean.getOpzioniInvio().getMessaggioInvio());
						if (lAssegnazioneBean.getOpzioniInvio().getFlgPresaInCarico() != null && lAssegnazioneBean.getOpzioniInvio().getFlgPresaInCarico()) {
							lAssegnatariBean.setFeedback(Flag.SETTED);
						}
						if (lAssegnazioneBean.getOpzioniInvio().getFlgMancataPresaInCarico() != null && lAssegnazioneBean.getOpzioniInvio().getFlgMancataPresaInCarico()) {
							lAssegnatariBean.setNumeroGiorniFeedback(lAssegnazioneBean.getOpzioniInvio().getGiorniTrascorsi());
						}
						if (lAssegnazioneBean.getOpzioniInvio().getFlgMandaNotificaMail() != null && lAssegnazioneBean.getOpzioniInvio().getFlgMandaNotificaMail()) {
							lAssegnatariBean.setFlgMandaNotificaMail(Flag.SETTED);
						}
					}
					if(StringUtils.isNotBlank(lAssegnatariBean.getIdSettato())) {
						lListAssegnatari.add(lAssegnatariBean);
					}
				}
			}
			lXmlFascicoloIn.setAssegnatari(lListAssegnatari);
		}
	}

	private void salvaInvioDestCC(ArchivioBean bean, XmlFascicoloIn lXmlFascicoloIn) {
		if (!isTaskDettFascGen() && !isTaskDettFasc()) {
			List<AssegnatariBean> lListInvioDestCC = new ArrayList<AssegnatariBean>();
			if (bean.getListaDestInvioCC() != null && bean.getListaDestInvioCC().size() > 0) {
				for (DestInvioCCBean lDestInvioCCBean : bean.getListaDestInvioCC()) {
					AssegnatariBean lAssegnatariBean = new AssegnatariBean();
					lAssegnatariBean.setTipo(getTipoAssegnatario(lDestInvioCCBean));
					if (lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.GRUPPO)) {
						lAssegnatariBean.setIdSettato(lDestInvioCCBean.getGruppo());
					} else {
						lAssegnatariBean.setIdSettato(lDestInvioCCBean.getIdUo());
					}
					lAssegnatariBean.setPermessiAccesso("V");
					//TODO CONDIVISIONE SAVE OK
					if(lDestInvioCCBean.getOpzioniInvio() != null) {
						lAssegnatariBean.setMotivoInvio(lDestInvioCCBean.getOpzioniInvio().getMotivoInvio());
						lAssegnatariBean.setLivelloPriorita(lDestInvioCCBean.getOpzioniInvio().getLivelloPriorita());
						lAssegnatariBean.setMessaggioInvio(lDestInvioCCBean.getOpzioniInvio().getMessaggioInvio());
					}
					if(StringUtils.isNotBlank(lAssegnatariBean.getIdSettato())) {
						lListInvioDestCC.add(lAssegnatariBean);
					}	
				}
			}
			lXmlFascicoloIn.setInvioDestCC(lListInvioDestCC);
		}
	}

	private TipoAssegnatario getTipoAssegnatario(DestInvioBean lDestInvioBean) {
		TipoAssegnatario[] lTipoAssegnatarios = TipoAssegnatario.values();
		for (TipoAssegnatario lTipoAssegnatario : lTipoAssegnatarios) {
			if (lTipoAssegnatario.toString().equals(lDestInvioBean.getTypeNodo()))
				return lTipoAssegnatario;
		}
		return null;
	}

	private void salvaACL(ArchivioBean bean, XmlFascicoloIn lXmlFascicoloIn) throws Exception {
		if (!isTaskDettFascGen() && !isTaskDettFasc()) {
			List<ACLFolderBean> lListACLFolder = new ArrayList<ACLFolderBean>();
			if (bean.getListaACL() != null) {
				for (ACLBean lACLBean : bean.getListaACL()) {
					ACLFolderBean lACLFolderBean = new ACLFolderBean();
					String tipoDestinatario = lACLBean.getTipoDestinatario();
					if ("AOO".equals(tipoDestinatario)) {
						lACLFolderBean.setTipoDestinatario("AOO");
					} else if ("UO+SV".equals(tipoDestinatario)) {
						lACLFolderBean.setTipoDestinatario(lACLBean.getTipoOrganigramma());
						lACLFolderBean.setIdDestinatario(lACLBean.getIdOrganigramma());
						lACLFolderBean.setCodiceRapido(lACLBean.getCodiceUo());
					} else if ("UT".equals(tipoDestinatario)) {
						lACLFolderBean.setTipoDestinatario("UT");
						lACLFolderBean.setIdDestinatario(lACLBean.getIdUtente());
						lACLFolderBean.setCodiceRapido(lACLBean.getCodiceRapido());
					} else if ("G".equals(tipoDestinatario)) {
						lACLFolderBean.setTipoDestinatario("G");
						lACLFolderBean.setIdDestinatario(lACLBean.getIdGruppoInterno());
						lACLFolderBean.setCodiceRapido(lACLBean.getCodiceRapido());
					}
					if ("V".equals(lACLBean.getOpzioniAccesso())) {// 17, 20
						lACLFolderBean.setFlgVisualizzaMetadati(Flag.SETTED);
						lACLFolderBean.setFlgCopiaFolder(Flag.SETTED);
					} else if ("D".equals(lACLBean.getOpzioniAccesso())) { // 17, 20, 23
						lACLFolderBean.setFlgVisualizzaMetadati(Flag.SETTED);
						lACLFolderBean.setFlgCopiaFolder(Flag.SETTED);
						lACLFolderBean.setFlgModificaContenutoUd(Flag.SETTED);
					} else if ("C".equals(lACLBean.getOpzioniAccesso())) { // 17, 18, 19, 20, 21, 22, 23, 24
						lACLFolderBean.setFlgVisualizzaMetadati(Flag.SETTED);
						lACLFolderBean.setFlgModificaMetadati(Flag.SETTED);
						lACLFolderBean.setFlgModificaACL(Flag.SETTED);
						lACLFolderBean.setFlgCopiaFolder(Flag.SETTED);
						lACLFolderBean.setFlgEliminaFolder(Flag.SETTED);
						lACLFolderBean.setFlgRipristinoFolder(Flag.SETTED);
						lACLFolderBean.setFlgModificaContenutoUd(Flag.SETTED);
						lACLFolderBean.setFlgModificaContenutoFolder(Flag.SETTED);
					}
					if (lACLBean.getFlgEreditata() != null) {
						lACLFolderBean.setFlgEreditata(lACLBean.getFlgEreditata() ? Flag.SETTED : Flag.NOT_SETTED);
					}
					lListACLFolder.add(lACLFolderBean);
				}
			}
			lXmlFascicoloIn.setPermessi(lListACLFolder);
		}
	}

	private void salvaIstruttoreProc(ArchivioBean bean, XmlFascicoloIn lXmlFascicoloIn) {
		// sezione ISTRUTTORE PROC.
		if (bean.getListaIstruttoriProc() != null && bean.getListaIstruttoriProc().size() > 0) {
			IstruttoreProcBean istruttoreProc = bean.getListaIstruttoriProc().get(0);
			lXmlFascicoloIn.setTipoIstruttoreProc(istruttoreProc.getTipoIstruttoreProc());
			if (lXmlFascicoloIn.getTipoIstruttoreProc() != null && "UT".equals(lXmlFascicoloIn.getTipoIstruttoreProc())) {
				lXmlFascicoloIn.setIdIstruttoreProc(istruttoreProc.getIdUtenteIstruttoreProc());
				lXmlFascicoloIn.setNomeIstruttoreProc(istruttoreProc.getNomeUtenteIstruttoreProc());
			}
			if (lXmlFascicoloIn.getTipoIstruttoreProc() != null && "LD".equals(lXmlFascicoloIn.getTipoIstruttoreProc())) {
				lXmlFascicoloIn.setIdIstruttoreProc(istruttoreProc.getIdGruppoIstruttoreProc());
				lXmlFascicoloIn.setCodRapidoIstruttoreProc(istruttoreProc.getCodRapidoGruppoIstruttoreProc());
				lXmlFascicoloIn.setNomeIstruttoreProc(istruttoreProc.getNomeGruppoIstruttoreProc());
			}
		}
	}
	
	private void salvaAltreUbicazioni(ArchivioBean bean, XmlFascicoloIn lXmlFascicoloIn) throws Exception {
		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
		if (bean.getListaAltreVie() != null && bean.getListaAltreVie().size() > 0 && !bean.getListaAltreVie().isEmpty()) {
			ArrayList<AltreUbicazioniBean> altreUbicazioni = new ArrayList<AltreUbicazioniBean>();
			for (AltraViaProtBean lAltraViaProtBean : bean.getListaAltreVie()) {
				
				if(!lAltraViaProtBean.isNull()){				
					AltreUbicazioniBean lAltreUbicazioniBean = new AltreUbicazioniBean();
					lAltreUbicazioniBean.setStato(lAltraViaProtBean.getStato());
					lAltreUbicazioniBean.setNomeStato(lAltraViaProtBean.getNomeStato());
					if (StringUtils.isBlank(lAltreUbicazioniBean.getStato()) || ProtocolloUtility._COD_ISTAT_ITALIA.equals(lAltreUbicazioniBean.getStato())
							|| ProtocolloUtility._NOME_STATO_ITALIA.equals(lAltreUbicazioniBean.getStato())) {
						if (StringUtils.isNotBlank(lAltraViaProtBean.getCodToponimo())) {
							lAltreUbicazioniBean.setCodToponimo(lAltraViaProtBean.getCodToponimo());
							lAltreUbicazioniBean.setToponimoIndirizzo(lAltraViaProtBean.getIndirizzo());
							lAltreUbicazioniBean.setComune(lProtocolloUtility.getCodIstatComuneRif());
							lAltreUbicazioniBean.setNomeComuneCitta(lProtocolloUtility.getNomeComuneRif());
						} else {
							lAltreUbicazioniBean.setTipoToponimo(lAltraViaProtBean.getTipoToponimo());
							lAltreUbicazioniBean.setToponimoIndirizzo(lAltraViaProtBean.getToponimo());
							lAltreUbicazioniBean.setComune(lAltraViaProtBean.getComune());
							lAltreUbicazioniBean.setNomeComuneCitta(lAltraViaProtBean.getNomeComune());
						}
						lAltreUbicazioniBean.setFrazione(lAltraViaProtBean.getFrazione());
						lAltreUbicazioniBean.setCap(lAltraViaProtBean.getCap());
					} else {
						lAltreUbicazioniBean.setToponimoIndirizzo(lAltraViaProtBean.getIndirizzo());
						lAltreUbicazioniBean.setNomeComuneCitta(lAltraViaProtBean.getCitta());
					}
					lAltreUbicazioniBean.setCivico(lAltraViaProtBean.getCivico());
					lAltreUbicazioniBean.setAppendici(lAltraViaProtBean.getAppendici());
					lAltreUbicazioniBean.setZona(lAltraViaProtBean.getZona());
					lAltreUbicazioniBean.setComplementoIndirizzo(lAltraViaProtBean.getComplementoIndirizzo());
					
					altreUbicazioni.add(lAltreUbicazioniBean);
				}
			}
			lXmlFascicoloIn.setAltreUbicazioni(altreUbicazioni);			
		}
	}

	@Override
	public ArchivioBean remove(ArchivioBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkCoreDelfolderBean input = new DmpkCoreDelfolderBean();
		input.setIdfolderin(new BigDecimal(bean.getIdUdFolder()));
		input.setFlgcancfisicain(new Integer(0));
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		DmpkCoreDelfolder dmpkCoreDelfolder = new DmpkCoreDelfolder();
		StoreResultBean<DmpkCoreDelfolderBean> output = dmpkCoreDelfolder.execute(getLocale(), loginBean, input);
		
		if (output.isInError()) {
			throw new StoreException(output);
		} else if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
		}		

		return bean;
	}

	// Federico Cacco 14.01.2016
	// Commento questo metodo perchè ne creo uno analogo che aggiunge gli allegati del primario ai file da firmare
	public FirmaMassivaFilesBean getListaPerFirmaMassiva(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<FileDaFirmareBean> lListFiles = new ArrayList<FileDaFirmareBean>();
		List<String> documentiFirmati = new ArrayList<String>();
		for (int i = 0; i < bean.getListaRecord().size(); i++) {
			String flgSoloAbilAzioni = getExtraparams().get("flgSoloAbilAzioni");
			BigDecimal idUd = new BigDecimal(bean.getListaRecord().get(i).getIdUdFolder());
			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
			lRecuperaDocumentoInBean.setIdUd(idUd);
			lRecuperaDocumentoInBean.setFlgSoloAbilAzioni(flgSoloAbilAzioni);
			lRecuperaDocumentoInBean.setTsAnnDati(getExtraparams().get("tsAnnDati"));
			RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), lAurigaLoginBean, lRecuperaDocumentoInBean);
			if(lRecuperaDocumentoOutBean.isInError()) {
				throw new StoreException(lRecuperaDocumentoOutBean);
			}
			DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
			ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
			ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);
			documento.setIdUd(idUd);
			documento.setIdEmailArrivo(lDocumentoXmlOutBean.getIdEmailArrivo());
			documento.setEmailArrivoInteroperabile(lDocumentoXmlOutBean.getEmailArrivoInteroperabile());
			documento.setEmailInviataFlgPEC(lDocumentoXmlOutBean.getEmailInviataFlgPEC());
			documento.setEmailInviataFlgPEO(lDocumentoXmlOutBean.getEmailInviataFlgPEO());
			if (documento.getInfoFile() != null && documento.getInfoFile().isFirmato() && documento.getInfoFile().getTipoFirma().startsWith("CAdES")) {
				documentiFirmati.add(bean.getListaRecord().get(i).getSegnatura());
			} else {
				FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
				lFileDaFirmareBean.setIdFile(String.valueOf(documento.getIdDocPrimario()));
				lFileDaFirmareBean.setNomeFile(documento.getNomeFilePrimario());
				lFileDaFirmareBean.setUri(documento.getUriFilePrimario());
				lFileDaFirmareBean.setIdUd(String.valueOf(documento.getIdUd()));
				lFileDaFirmareBean.setInfoFile(documento.getInfoFile());
				lListFiles.add(lFileDaFirmareBean);
			}
		}
		if (documentiFirmati.size() > 0) {
			if (documentiFirmati.size() == bean.getListaRecord().size()) {
				if (documentiFirmati.size() == 1) {
					addMessage("Il documento selezionato risulta già firmato", "", MessageType.ERROR);
				} else {
					addMessage("Tutti i documenti selezionati risultano già firmati", "", MessageType.ERROR);
				}
			} else {
				String message = null;
				if (documentiFirmati.size() == 1) {
					message = "Il documento " + documentiFirmati.get(0) + " risulta già firmato";
				} else {
					message = "I documenti ";
					boolean first = true;
					for (String lStr : documentiFirmati) {
						if (first)
							first = false;
						else
							message += ", ";
						message += lStr;
					}
					message += " risultano già firmati";
				}
				addMessage(message, "", MessageType.WARNING);
			}
		}
		lFirmaMassivaFilesBean.setFiles(lListFiles);
		return lFirmaMassivaFilesBean;
	}

	// Federico Cacco 18.02.2016
	// Restituisce i file da firmare relativi alle unità doumentali ricevute in ingresso
	// Restituisce sia primario che allegati
	public FirmaMassivaFilesBean getListaPerFirmaMassivaConAllegati(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<FileDaFirmareBean> lListaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		List<String> documentiDaNonFirmare = new ArrayList<String>();
		for (int i = 0; i < bean.getListaRecord().size(); i++) {
			boolean udDaFirmare = true;
			String idUd = bean.getListaRecord().get(i).getIdUdFolder();
			// Recupero il file primario e gli allegati dell'unità documentale
			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
			lRecuperaDocumentoInBean.setIdUd(new BigDecimal(idUd));
			RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), lAurigaLoginBean, lRecuperaDocumentoInBean);
			if(lRecuperaDocumentoOutBean.isInError()) {
				throw new StoreException(lRecuperaDocumentoOutBean);
			}
			List<FileDaFirmareBean> listaAllegatiDaFirmareUd = new ArrayList<FileDaFirmareBean>();
			DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
			ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());			
			//la variabile documento ha al suo interno tutti i valori come nomeDocumento, allegati,....
			ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);
			if (StringUtils.isNotBlank(documento.getUriFilePrimario()) || (documento.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(documento.getFilePrimarioOmissis().getUriFile()))) {				
				if (StringUtils.isNotBlank(documento.getUriFilePrimario())) {
					FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
					lFileDaFirmareBean.setIdFile(documento.getIdDocPrimario() != null ? String.valueOf(documento.getIdDocPrimario()) : null);
					lFileDaFirmareBean.setNomeFile(documento.getNomeFilePrimario());
					lFileDaFirmareBean.setUri(documento.getUriFilePrimario());
					lFileDaFirmareBean.setIdUd(idUd);
					lFileDaFirmareBean.setInfoFile(documento.getInfoFile());
					lFileDaFirmareBean.setCodiceTipoRelazione("P");
					listaAllegatiDaFirmareUd.add(lFileDaFirmareBean);
				}				
				if(documento.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(documento.getFilePrimarioOmissis().getUriFile())) {
					FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
					lFileDaFirmareBeanOmissis.setIdFile(documento.getFilePrimarioOmissis().getIdDoc() != null ? String.valueOf(documento.getFilePrimarioOmissis().getIdDoc()) : null);
					lFileDaFirmareBeanOmissis.setNomeFile(documento.getFilePrimarioOmissis().getNomeFile());
					lFileDaFirmareBeanOmissis.setUri(documento.getFilePrimarioOmissis().getUriFile());
					lFileDaFirmareBeanOmissis.setIdUd(idUd);
					lFileDaFirmareBeanOmissis.setInfoFile(documento.getFilePrimarioOmissis().getInfoFile());
					lFileDaFirmareBeanOmissis.setCodiceTipoRelazione("P");
					listaAllegatiDaFirmareUd.add(lFileDaFirmareBeanOmissis);
				}				
			} else {
				documentiDaNonFirmare.add(bean.getListaRecord().get(i).getSegnatura());
				udDaFirmare = false;
			}
			if (udDaFirmare && documento.getListaAllegati() != null) {
				for (AllegatoProtocolloBean allegatoUdBean : documento.getListaAllegati()) {
					if ((StringUtils.isNotBlank(allegatoUdBean.getUriFileAllegato()) ||  StringUtils.isNotBlank(allegatoUdBean.getUriFileOmissis())) && 
							allegatoUdBean.getFlgParteDispositivo() != null && allegatoUdBean.getFlgParteDispositivo()) {						
						if (StringUtils.isNotBlank(allegatoUdBean.getUriFileAllegato())) {
							FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
							lFileDaFirmareBean.setIdFile(allegatoUdBean.getIdDocAllegato() != null ? String.valueOf(allegatoUdBean.getIdDocAllegato()) : null);
							lFileDaFirmareBean.setNomeFile(allegatoUdBean.getNomeFileAllegato());
							lFileDaFirmareBean.setUri(allegatoUdBean.getUriFileAllegato());
							lFileDaFirmareBean.setIdUd(idUd);
							lFileDaFirmareBean.setInfoFile(allegatoUdBean.getInfoFile());
							lFileDaFirmareBean.setCodiceTipoRelazione("ALL");
							listaAllegatiDaFirmareUd.add(lFileDaFirmareBean);	
						}
						if (StringUtils.isNotBlank(allegatoUdBean.getUriFileOmissis())) {
							FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
							lFileDaFirmareBeanOmissis.setIdFile(allegatoUdBean.getIdDocOmissis() != null ? String.valueOf(allegatoUdBean.getIdDocOmissis()) : null);
							lFileDaFirmareBeanOmissis.setNomeFile(allegatoUdBean.getNomeFileOmissis());
							lFileDaFirmareBeanOmissis.setUri(allegatoUdBean.getUriFileOmissis());
							lFileDaFirmareBeanOmissis.setIdUd(idUd);
							lFileDaFirmareBeanOmissis.setInfoFile(allegatoUdBean.getInfoFileOmissis());
							lFileDaFirmareBeanOmissis.setCodiceTipoRelazione("ALL");
							listaAllegatiDaFirmareUd.add(lFileDaFirmareBeanOmissis);
						}
					}
				}
			}
			// Verifico se l'unità documentale è da firmare
			if (udDaFirmare) {
				// L'unità documentale deve essere firmata, aggiunto allegati e
				// primario alla lista file da firmare
				for (FileDaFirmareBean allegatoUd : listaAllegatiDaFirmareUd) {
					lListaFileDaFirmare.add(allegatoUd);
				}
			}
		}
		if (documentiDaNonFirmare.size() > 0) {
			if (documentiDaNonFirmare.size() == bean.getListaRecord().size()) {
				if (documentiDaNonFirmare.size() == 1) {
					addMessage("Il documento selezionato risulta già firmato o senza file primario", "", MessageType.ERROR);
				} else {
					addMessage("Tutti i documenti selezionati risultano già firmati o senza file primario", "", MessageType.ERROR);
				}
			} else {
				String message = null;
				if (documentiDaNonFirmare.size() == 1) {
					message = "Il documento " + documentiDaNonFirmare.get(0) + " risulta già firmato o senza file primario";
				} else {
					message = "I documenti ";
					boolean first = true;
					for (String lStr : documentiDaNonFirmare) {
						if (first)
							first = false;
						else
							message += ", ";
						message += lStr;
					}
					message += " risultano già firmati o senza file primario";
				}
				addMessage(message, "", MessageType.WARNING);
			}
		}
		lFirmaMassivaFilesBean.setFiles(lListaFileDaFirmare);		
		GenericConfigBean config = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		String prefTipoFirma = getExtraparams().get("prefTipoFirma");
		if(prefTipoFirma != null && !"".equals(prefTipoFirma)) {
			if("C".equalsIgnoreCase(prefTipoFirma)) {
				if(config.getMaxNroDocFirmaMassivaClient() >= 1) {
					if( lListaFileDaFirmare.size() > config.getMaxNroDocFirmaMassivaClient()){
						lListaFileDaFirmare.clear();
						addMessage("Selezionato per firma un n.ro di documenti superiore al massimo consentito di "
								+ config.getMaxNroDocFirmaMassivaClient(), "", MessageType.ERROR);
					}
				}
			} else if("R".equalsIgnoreCase(prefTipoFirma)) {
				if(config.getMaxNroDocFirmaMassivaRemotaNonAuto() >= 1) {
					if(lListaFileDaFirmare.size() > config.getMaxNroDocFirmaMassivaRemotaNonAuto()){
						lListaFileDaFirmare.clear();
						addMessage("Selezionato per firma un n.ro di documenti superiore al massimo consentito di "
								+ config.getMaxNroDocFirmaMassivaRemotaNonAuto(), "", MessageType.ERROR);
					}
				}
			} 
//			else  if("A".equalsIgnoreCase(prefTipoFirma)) {
//				
//			}
		}		
		return lFirmaMassivaFilesBean;
	}
	
	public FirmaMassivaFilesBean getListaPerFirmaSingolaConAllegati(ProtocollazioneBean documento) throws Exception {
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<String> documentiDaNonFirmare = new ArrayList<String>();
		List<FileDaFirmareBean> listaAllegatiDaFirmareUd = new ArrayList<FileDaFirmareBean>();
		List<FileDaFirmareBean> lListaFileDaFirmare = new ArrayList<FileDaFirmareBean>();		
		boolean udDaFirmare = true;
		String idUd = documento.getIdUd() != null ? String.valueOf(documento.getIdUd()): null;
		//la variabile documento ha al suo interno tutti i valori come nomeDocumento, allegati,....
		if (StringUtils.isNotBlank(documento.getUriFilePrimario()) || (documento.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(documento.getFilePrimarioOmissis().getUriFile()))) {			
			if (StringUtils.isNotBlank(documento.getUriFilePrimario())) {
				FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
				lFileDaFirmareBean.setIdFile(documento.getIdDocPrimario() != null ? String.valueOf(documento.getIdDocPrimario()) : null);
				lFileDaFirmareBean.setNomeFile(documento.getNomeFilePrimario());
				lFileDaFirmareBean.setUri(documento.getUriFilePrimario());
				lFileDaFirmareBean.setIdUd(idUd);
				lFileDaFirmareBean.setInfoFile(documento.getInfoFile());
				lFileDaFirmareBean.setCodiceTipoRelazione("P");
				listaAllegatiDaFirmareUd.add(lFileDaFirmareBean);
			}			
			if(documento.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(documento.getFilePrimarioOmissis().getUriFile())) {
				FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
				lFileDaFirmareBeanOmissis.setIdFile(documento.getFilePrimarioOmissis().getIdDoc() != null ? String.valueOf(documento.getFilePrimarioOmissis().getIdDoc()) : null);
				lFileDaFirmareBeanOmissis.setNomeFile(documento.getFilePrimarioOmissis().getNomeFile());
				lFileDaFirmareBeanOmissis.setUri(documento.getFilePrimarioOmissis().getUriFile());
				lFileDaFirmareBeanOmissis.setIdUd(idUd);
				lFileDaFirmareBeanOmissis.setInfoFile(documento.getFilePrimarioOmissis().getInfoFile());
				lFileDaFirmareBeanOmissis.setCodiceTipoRelazione("P");
				listaAllegatiDaFirmareUd.add(lFileDaFirmareBeanOmissis);
			}			
		} else {
			documentiDaNonFirmare.add(documento.getSegnatura());
			udDaFirmare = false;			
		}		
		if (udDaFirmare && documento.getListaAllegati() != null) {
			for (AllegatoProtocolloBean allegatoUdBean : documento.getListaAllegati()) {
				if ((StringUtils.isNotBlank(allegatoUdBean.getUriFileAllegato()) ||  StringUtils.isNotBlank(allegatoUdBean.getUriFileOmissis())) && 
						allegatoUdBean.getFlgParteDispositivo() != null && allegatoUdBean.getFlgParteDispositivo()) {	
					if (StringUtils.isNotBlank(allegatoUdBean.getUriFileAllegato())) {
						FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
						lFileDaFirmareBean.setIdFile(allegatoUdBean.getIdDocAllegato() != null ? String.valueOf(allegatoUdBean.getIdDocAllegato()) : null);
						lFileDaFirmareBean.setNomeFile(allegatoUdBean.getNomeFileAllegato());
						lFileDaFirmareBean.setUri(allegatoUdBean.getUriFileAllegato());
						lFileDaFirmareBean.setIdUd(idUd);
						lFileDaFirmareBean.setInfoFile(allegatoUdBean.getInfoFile());
						lFileDaFirmareBean.setCodiceTipoRelazione("ALL");
						listaAllegatiDaFirmareUd.add(lFileDaFirmareBean);	
					}
					if (StringUtils.isNotBlank(allegatoUdBean.getUriFileOmissis())) {
						FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
						lFileDaFirmareBeanOmissis.setIdFile(allegatoUdBean.getIdDocOmissis() != null ? String.valueOf(allegatoUdBean.getIdDocOmissis()) : null);
						lFileDaFirmareBeanOmissis.setNomeFile(allegatoUdBean.getNomeFileOmissis());
						lFileDaFirmareBeanOmissis.setUri(allegatoUdBean.getUriFileOmissis());
						lFileDaFirmareBeanOmissis.setIdUd(idUd);
						lFileDaFirmareBeanOmissis.setInfoFile(allegatoUdBean.getInfoFileOmissis());
						lFileDaFirmareBeanOmissis.setCodiceTipoRelazione("ALL");
						listaAllegatiDaFirmareUd.add(lFileDaFirmareBeanOmissis);
					}
				}
			}
		}
		// Verifico se l'unità documentale è da firmare
		if (udDaFirmare) {
			// L'unità documentale deve essere firmata, aggiunto allegati e
			// primario alla lista file da firmare
			for (FileDaFirmareBean allegatoUd : listaAllegatiDaFirmareUd) {
				lListaFileDaFirmare.add(allegatoUd);
			}
		}
		lFirmaMassivaFilesBean.setFiles(lListaFileDaFirmare);
		return lFirmaMassivaFilesBean;
	}

	public FirmaMassivaFilesBean aggiornaDocumentoFirmato(FirmaMassivaFilesBean pFirmaMassivaFilesBean) throws Exception {
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<FileDaFirmareBean> filesInError = new ArrayList<FileDaFirmareBean>();
		for (FileDaFirmareBean lFileDaFirmareBean : pFirmaMassivaFilesBean.getFiles()) {
			try {
				String modalitaFirma = ParametriDBUtil.getParametroDB(getSession(), "MODALITA_FIRMA");
				String appletFirmaSpec = ParametriDBUtil.getParametroDB(getSession(), "APPLET_FIRMA_SPEC");
				if ((StringUtils.isBlank(modalitaFirma) || modalitaFirma.equalsIgnoreCase("APPLET")) && StringUtils.isBlank(appletFirmaSpec)) {
					// Aggiungo l'estensione p7m al nome del file firmato perchè l'applet di firma multipla non lo fa
					boolean isCAdES = lFileDaFirmareBean.getInfoFile() != null && lFileDaFirmareBean.getInfoFile().isFirmato() && lFileDaFirmareBean.getInfoFile().getTipoFirma().startsWith("CAdES");
					if (isCAdES && !lFileDaFirmareBean.getNomeFile().toLowerCase().endsWith(".p7m")) {
						lFileDaFirmareBean.setNomeFile(lFileDaFirmareBean.getNomeFile() + ".p7m");
					}
				}
				boolean firmaEseguita = lFileDaFirmareBean.getFirmaEseguita() != null && lFileDaFirmareBean.getFirmaEseguita();
				if (!firmaEseguita || lFileDaFirmareBean.getInfoFile() == null || !lFileDaFirmareBean.getInfoFile().isFirmato() || !lFileDaFirmareBean.getInfoFile().isFirmaValida()) {
					throw new Exception("Firma non presente o non valida");
				}
				versionaDocumento(lFileDaFirmareBean);				
			} catch (Exception e) {
				filesInError.add(lFileDaFirmareBean);
			}
		}
		lFirmaMassivaFilesBean.setFiles(filesInError);
		return lFirmaMassivaFilesBean;
	}

	public void versionaDocumento(IdFileBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento(new BigDecimal(bean.getIdFile()));
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(StorageImplementation.getStorage().store(StorageImplementation.getStorage().extractFile(bean.getUri()))));
		
		MimeTypeFirmaBean lMimeTypeFirmaBean;
		if(bean.getInfoFile() == null) {
			lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
		} else {
			lMimeTypeFirmaBean = bean.getInfoFile();
		}
		
		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);
		GenericFile lGenericFile = new GenericFile();
		setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
		lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
		lGenericFile.setDisplayFilename(bean.getNomeFile());
		lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
		lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
		lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
		if (lMimeTypeFirmaBean.isDaScansione()) {
			lGenericFile.setDaScansione(Flag.SETTED);
			lGenericFile.setDataScansione(new Date());
			lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
		}
		lFileInfoBean.setAllegatoRiferimento(lGenericFile);
		lRebuildedFile.setInfo(lFileInfoBean);

		VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);

		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBean);
		if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
			mLogger.error("VersionaDocumento: " + lVersionaDocumentoOutBean.getDefaultMessage());
			throw new StoreException(lVersionaDocumentoOutBean);
		}
	}

	public ExportBean stampaAttestato(AttestatoConformitaBean attestatoConformitaBean) throws Exception {
		
		// INIZIO GENERAZIONE ATTESTATO TRAMITE MODELLO
		
//		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
//
//		// Ricavo il modello del'attestato
//		DmpkModelliDocTrovamodelliBean input = new DmpkModelliDocTrovamodelliBean();
//		input.setCodidconnectiontokenin(loginBean.getToken());
//		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
//		input.setNomemodelloio("ATTESTATO_CONFORMITA_ORIGINALE");		
//		input.setColorderbyio(null);
//		input.setFlgdescorderbyio(null);
//		input.setFlgsenzapaginazionein(new Integer(1));
//		input.setBachsizeio(null);
//		input.setOverflowlimitin(null);
//		input.setFlgsenzatotin(null);
//		input.setDescrizionemodelloio(null);
//		input.setNotemodelloio(null);
//		input.setIdtyobjrelatedio(null);
//		input.setTyobjrelatedio(null);
//		
//		DmpkModelliDocTrovamodelli dmpkModelliDocTrovamodelli = new DmpkModelliDocTrovamodelli();
//		
//		StoreResultBean<DmpkModelliDocTrovamodelliBean> output = dmpkModelliDocTrovamodelli.execute(getLocale(), loginBean, input);
//			
//		if(output.getDefaultMessage() != null) {
//			throw new StoreException(output.getDefaultMessage());
//		}
//		
//		List<ModelliDocBean> listaModelli = new ArrayList<ModelliDocBean>();
//		if(output.getResultBean().getListaxmlout() != null) {
//			List<ModelliDocXmlBean> lista = XmlListaUtility.recuperaLista(output.getResultBean().getListaxmlout(), ModelliDocXmlBean.class);
//			if(lista != null) {
//				for(ModelliDocXmlBean lModelliDocXmlBean : lista) {
//					ModelliDocBean lModelliDocBean = new ModelliDocBean();
//					lModelliDocBean.setIdModello(lModelliDocXmlBean.getIdModello());
//					lModelliDocBean.setNomeModello(lModelliDocXmlBean.getNome());
//					lModelliDocBean.setDesModello(lModelliDocXmlBean.getDescrizione());
//					lModelliDocBean.setFormatoFile(lModelliDocXmlBean.getNomeFormatoFile());
//					lModelliDocBean.setTipoEntitaAssociata(lModelliDocXmlBean.getTipoEntitaAssociata());
//					lModelliDocBean.setIdEntitaAssociata(lModelliDocXmlBean.getIdEntitaAssociata());
//					lModelliDocBean.setNomeEntitaAssociata(lModelliDocXmlBean.getNomeEntitaAssociata());
//					lModelliDocBean.setNomeTabella(lModelliDocXmlBean.getNomeTabella());
//					lModelliDocBean.setIdDoc(lModelliDocXmlBean.getIdDoc());
//					lModelliDocBean.setNote(lModelliDocXmlBean.getAnnotazioni());
//					lModelliDocBean.setFlgProfCompleta(lModelliDocXmlBean.getFlgProfCompleta() != null && "1".equals(lModelliDocXmlBean.getFlgProfCompleta()));	
//					lModelliDocBean.setUriFileModello(lModelliDocXmlBean.getUriFile());	
//					lModelliDocBean.setNomeFileModello(lModelliDocXmlBean.getNomeFile());	
//					lModelliDocBean.setFlgGeneraPdf(lModelliDocXmlBean.getFlgGeneraPdf() != null && "1".equals(lModelliDocXmlBean.getFlgGeneraPdf()));	
//					lModelliDocBean.setTipoModello(lModelliDocXmlBean.getTipoModello());					
//					lModelliDocBean.setTsCreazione(lModelliDocXmlBean.getTsCreazione() != null ? lModelliDocXmlBean.getTsCreazione() : null);
//					lModelliDocBean.setCreatoDa(lModelliDocXmlBean.getDesUteCreazione());
//					lModelliDocBean.setTsUltimoAgg(lModelliDocXmlBean.getDesUltimoAgg() != null ? lModelliDocXmlBean.getDesUltimoAgg() : null);
//					lModelliDocBean.setUltimoAggEffDa(lModelliDocXmlBean.getIdUteUltimoAgg());
//					lModelliDocBean.setFlgValido(lModelliDocXmlBean.getFlgAnnLogico() != null && "0".equals(lModelliDocXmlBean.getFlgAnnLogico()));
//					listaModelli.add(lModelliDocBean);
//				}
//			}
//		}				
//		
//		ModelliDocBean modelloAttestatoConformita = new ModelliDocBean();
//		if (listaModelli.isEmpty()){
//			throw new Exception("Impossibile l'attestato di conformità. Nessun modello trovato");
//		} else {
//			modelloAttestatoConformita = listaModelli.get(0);
//		}
//		
//		//Genero i dati da iniettare
//		InfoFileUtility lFileUtility = new InfoFileUtility();
//		MimeTypeFirmaBean infoFileRecord = lFileUtility.getInfoFromFile(StorageImplementation.getStorage().getRealFile(attestatoConformitaBean.getUriAttach()).toURI().toString(), attestatoConformitaBean.getInfoFileAttach().getCorrectFileName(), false, null);
//		// MimeTypeFirmaBean infoFileRecord = attestatoConformitaBean.getInfoFileAttach();
//		
//		SezioneCache lSezioneCache = new SezioneCache();
//
//		if (attestatoConformitaBean.getIdUd() != null) {
//			Variabile var = new Variabile();
//			var.setNome("IdUd");
//			var.setValoreSemplice(attestatoConformitaBean.getIdUd() + "");
//			lSezioneCache.getVariabile().add(var);
//		}
//		if (attestatoConformitaBean.getIdDoc() != null) {
//			Variabile var = new Variabile();
//			var.setNome("IdDoc");
//			var.setValoreSemplice(attestatoConformitaBean.getIdDoc() + "");
//			lSezioneCache.getVariabile().add(var);
//		}
//		if (attestatoConformitaBean.getAttestatoFirmatoDigitalmente() != null) {
//			Variabile var = new Variabile();
//			var.setNome("FlgAttestatoFirmatoDigitalmente");
//			var.setValoreSemplice(attestatoConformitaBean.getAttestatoFirmatoDigitalmente().toString());
//			lSezioneCache.getVariabile().add(var);
//		}
//		if (infoFileRecord != null && infoFileRecord.getCorrectFileName() != null) {
//			Variabile var = new Variabile();
//			var.setNome("DisplayFileName");
//			var.setValoreSemplice(infoFileRecord.getCorrectFileName());
//			lSezioneCache.getVariabile().add(var);
//		}
//		if (infoFileRecord != null && infoFileRecord.getImpronta() != null) {
//			Variabile var = new Variabile();
//			var.setNome("ImprontaFile");
//			var.setValoreSemplice(infoFileRecord.getImpronta());
//			lSezioneCache.getVariabile().add(var);
//		}
//		if (infoFileRecord != null && infoFileRecord.getEncoding() != null) {
//			Variabile var = new Variabile();
//			var.setNome("EncodingImprontaFile");
//			var.setValoreSemplice(infoFileRecord.getEncoding());
//			lSezioneCache.getVariabile().add(var);
//		}
//		if (infoFileRecord != null && infoFileRecord.getAlgoritmo() != null) {
//			Variabile var = new Variabile();
//			var.setNome("AlgoritmoImprontaFile");
//			var.setValoreSemplice(infoFileRecord.getAlgoritmo());
//			lSezioneCache.getVariabile().add(var);
//		}
//		
//		if (infoFileRecord != null && infoFileRecord.getMimetype().equals("application/pdf")) {
//			File realFile = StorageImplementation.getStorage().getRealFile(attestatoConformitaBean.getUriAttach());
//			byte[] bytesRealFile = getFileBytes(realFile);
//			
//			PdfReader reader = new PdfReader(bytesRealFile);
//			int numPagine = reader.getNumberOfPages();
//			Variabile var = new Variabile();
//			var.setNome("NroPagineFile");
//			var.setValoreSemplice(numPagine + "");
//			lSezioneCache.getVariabile().add(var);
//		} 
//		
//		StringWriter lStringWriter = new StringWriter();
//		Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
//		lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//		lMarshaller.marshal(lSezioneCache, lStringWriter);
//		String xmlSezioneCacheAttributiAdd = lStringWriter.toString();
//				
//		DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
//		DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
//		lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
//		lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
//		lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(attestatoConformitaBean.getIdUd() + "");
//		lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("U");
//		lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin("ATTESTATO_CONFORMITA_ORIGINALE");
//		lDmpkModelliDocGetdatixgendamodelloInput.setAttributiaddin(xmlSezioneCacheAttributiAdd);
//
//		
//		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lDmpkModelliDocGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(getLocale(), loginBean, lDmpkModelliDocGetdatixgendamodelloInput);
//		if (lDmpkModelliDocGetdatixgendamodelloOutput.isInError()) {
//			throw new StoreException(lDmpkModelliDocGetdatixgendamodelloOutput);
//		}
//		
//		String templateValues = lDmpkModelliDocGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();
//		Map<String, Object> mappaValori = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
//		
//		// Genero il modello
//		FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(null, modelloAttestatoConformita.getIdModello(), mappaValori, true, true, getSession()); 
//		
//		// Restituisto l'attestato
//		ExportBean exportBean = new ExportBean();
//		
//		StorageService storageService = StorageImplementation.getStorage();
//		exportBean.setTempFileOut(fillModelBean.getUri());
//
//		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
//		lMimeTypeFirmaBean.setCorrectFileName("Attestazione.pdf");
//
//		lMimeTypeFirmaBean.setFirmato(false);
//		lMimeTypeFirmaBean.setFirmaValida(false);
//		lMimeTypeFirmaBean.setConvertibile(true);
//		lMimeTypeFirmaBean.setDaScansione(false);
//		lMimeTypeFirmaBean.setMimetype("application/pdf");
//		exportBean.setInfoFileOut(lMimeTypeFirmaBean);
//
//		return exportBean;
		
		// FINE GENERAZIONE ATTESTATO TRAMITE MODELLO
		
		InputStream lInputStream = null;
		try {
	
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
			String oggi = sdf.format(new Date());
	
			MimeTypeFirmaBean infoFileRecord = attestatoConformitaBean.getInfoFileAttach();
	
			ExportBean exportBean = new ExportBean();
	
			File file = File.createTempFile("attestato", "");
			Font font_10 = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
	
			document.setMargins(20, 20, 20, 20);
	
			document.newPage();
			document.open();
	
			String testo = "Si attesta che la presente copia del documento ";
			testo += "N° " + (attestatoConformitaBean.getSiglaProtocollo() == null ? "" : attestatoConformitaBean.getSiglaProtocollo()) + " "
					+ attestatoConformitaBean.getNroProtocollo();
			testo += " del " + attestatoConformitaBean.getDataProtocollo() + " ";
			// testo += " redatto da " + attestatoConformitaBean.getDesUserProtocollo();
			// testo += " U.O proponente " + attestatoConformitaBean.getDesUOProtocollo() + " ";
	
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(attestatoConformitaBean.getUriAttach());
			RecuperoFile lRecuperoFile = new RecuperoFile();
			FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);
			File lFile = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));
	
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
	
			if (infoFileRecord.isFirmato() && infoFileRecord.getTipoFirma().startsWith("CAdES")) {
				lInputStream = lInfoFileUtility.sbusta(lFile.toURI().toString(), "");
			} else {
				lInputStream = new FileInputStream(lFile);
			}
	
			String uri = StorageImplementation.getStorage().storeStream(lInputStream);
	
			File realFile = StorageImplementation.getStorage().getRealFile(uri);
			byte[] bytesRealFile = getFileBytes(realFile);
	
			if (infoFileRecord.getMimetype().equals("application/pdf")) {
	
				PdfReader reader = new PdfReader(bytesRealFile);
				int numPagine = reader.getNumberOfPages();
				testo += "composta da N° " + numPagine + " facciate, è copia ";
	
			} else
				testo += ", è ";
	
			testo += "conforme all'originale digitale, depositato presso " + ParametriDBUtil.getParametroDB(getSession(), "DES_SOGG_PROD_X_ATT_CONF");
			testo += ", la cui impronta è quella riportata nel timbro seguente:\n\n";
	
			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			InfoFileUtility lFileUtility = new InfoFileUtility();
			lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null);
	
			// PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(lMimeTypeFirmaBean.getCorrectFileName()));
			HeaderFooterPageEvent header = new HeaderFooterPageEvent();
			writer.setPageEvent(header);
	
			// document.setMargins(20, 20, 20, 20);
			//
			// document.newPage();
			// document.open();
	
			document.add(new Paragraph(testo, font_10));
	
			// TIMBRO
			DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
	
			BarcodePDF417 pdf417 = new BarcodePDF417();
			pdf417.setText(lDocumentConfiguration.getAlgoritmo().value() + " " + lDocumentConfiguration.getEncoding().value() + " "
					+ lMimeTypeFirmaBean.getImpronta());
			Image img = pdf417.getImage();
	
			document.add(img);
	
			testo = "\n\n";
			testo += ParametriDBUtil.getParametroDB(getSession(), "SEDE_SOGG_PROD_AOO");
	
			testo += ", li " + oggi + "\n\n";
	
			testo += "Firma " + AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getDesUserOut();
	
			document.add(new Paragraph(testo, font_10));
	
			document.close();
	
			StorageService storageService = StorageImplementation.getStorage();
			exportBean.setTempFileOut(storageService.store(file));
	
			lMimeTypeFirmaBean.setCorrectFileName("Attestazione.pdf");
	
			lMimeTypeFirmaBean.setFirmato(false);
			lMimeTypeFirmaBean.setFirmaValida(false);
			lMimeTypeFirmaBean.setConvertibile(true);
			lMimeTypeFirmaBean.setDaScansione(false);
			lMimeTypeFirmaBean.setMimetype("application/pdf");
			exportBean.setInfoFileOut(lMimeTypeFirmaBean);
	
			return exportBean;
		} finally {
			if(lInputStream != null) {
				try {
					lInputStream.close();
				} catch (Exception e) { }
			}
		}
	}

	protected static byte[] getFileBytes(File file) {
		byte[] bFile = new byte[(int) file.length()];

		try {
			// convert file into array of bytes
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

		} catch (Exception e) {
			mLogger.warn(e);
		}
		return bFile;
	}

	private class HeaderFooterPageEvent extends PdfPageEventHelper {

		public void onStartPage(PdfWriter writer, Document document) {

			GenericConfigBean lGenericPropertyConfigurator = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");

			String logoDominioImage = null;
			String logoUtente = null;

			if (lGenericPropertyConfigurator.getLogoDominioFolder() != null && !"".equals(lGenericPropertyConfigurator.getLogoDominioFolder())) {
				logoDominioImage = lGenericPropertyConfigurator.getLogoDominioFolder();
				if (!lGenericPropertyConfigurator.getLogoDominioFolder().endsWith("/")) {
					logoDominioImage += "/";
				}

				String idDominio = null;
				if (AurigaUserUtil.getLoginInfo(getSession()).getDominio() != null && !"".equals(AurigaUserUtil.getLoginInfo(getSession()).getDominio())) {
					if (AurigaUserUtil.getLoginInfo(getSession()).getDominio().split(":").length == 2) {
						idDominio = AurigaUserUtil.getLoginInfo(getSession()).getDominio().split(":")[1];
					}
				}
				// se l'utente ha un suo logo mostra quello altrimenti mostra il logo di default del dominio
				if (AurigaUserUtil.getLoginInfo(getSession()).getLogoUtente() != null && !"".equals(AurigaUserUtil.getLoginInfo(getSession()).getLogoUtente())) {
					logoUtente = (AurigaUserUtil.getLoginInfo(getSession()).getLogoUtente()).toString();
					logoDominioImage += logoUtente;
				} else if (idDominio != null && !"".equals(idDominio)) {
					logoDominioImage += "Logo_" + idDominio + ".png";
				} else {
					logoDominioImage = "images/blank.png";
				}

				try {

					Image logo = Image.getInstance(getExtraparams().get("urlContext") + logoDominioImage);
					logo.setAlignment(Image.ALIGN_CENTER);
					// logo.setWidthPercentage(50);
					document.add(logo);

					String footerLogo = ParametriDBUtil.getParametroDB(getSession(), "FOOTER_LOGO_SOGG_PROD_AOO").replaceAll("<br/>", "\n");

					Font font_10 = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
					Paragraph footerLogoParagraph = new Paragraph(footerLogo + "\n\n", font_10);
					footerLogoParagraph.setAlignment(Element.ALIGN_CENTER);
					document.add(footerLogoParagraph);

				} catch (Exception e) {
					mLogger.warn(e);
				}

			}

		}

		public void onEndPage(PdfWriter writer, Document document) {
		}

	}

	protected FindRepositoryObjectBean createFindRepositoryObjectBean(boolean forNroRecordTot, AdvancedCriteria criteria, AurigaLoginBean loginBean) throws Exception {

		String colsToReturn;

		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVO_MODULO_PROT")) {
			colsToReturn = "1,2,3,4,5,6,7,10,14,15,16,17,18,20,29,30,31,32,33,36,37,39,42,53,72,88,89,90,91,92,93,94,95,96,97,99,100,201,203,204,205,206,207,208,214,215,227,232,258,259,260,261,216,281,282,284,285,298,300,CAUSALE_AGG_NOTE,FLG_IMMEDIATAMENTE_ESEGUIBILE,FLG_SOTTOPOSTO_CONTROLLO_REG_AMM,ID_PROCESS_CONTROLLO_REG_AMM,315";
			if (ParametriDBUtil.getParametroDB(getSession(), "CLIENTE") != null && "ADSP".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "CLIENTE"))) {
				colsToReturn += ",296";
			}
			if (ParametriDBUtil.getParametroDB(getSession(), "CLIENTE") != null && "COTO".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "CLIENTE"))) {
				colsToReturn += ",312,313,314";
			}
		} else {
			colsToReturn = "1,2,3,4,6,7,10,14,15,16,17,18,20,29,30,31,32,33,36,37,39,53,72,88,89,90,93,94,95,99,100,201,205,232,258,261,216,281,282,284,285,298,300,CAUSALE_AGG_NOTE,FLG_IMMEDIATAMENTE_ESEGUIBILE,FLG_SOTTOPOSTO_CONTROLLO_REG_AMM,ID_PROCESS_CONTROLLO_REG_AMM,315";
			if (ParametriDBUtil.getParametroDB(getSession(), "CLIENTE") != null && "COTO".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "CLIENTE"))) {
				colsToReturn += ",312,313,314";
			}
		}

		String filtroFullText = null;
		String[] checkAttributes = null;
		String formatoEstremiReg = null;
		Integer searchAllTerms = null;
		String idNode = null;
		Long idFolder = null;
		String criteriAvanzati = null;
		// in modalita' di esplora il valore di default e' 0
		String includiSottoCartelle = "0";
		String advancedFilters = null;
		String customFilters = null;
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer overflowLimit = null;
		Integer bachSize = null;
		Integer nroPagina = null;

		Integer online = null;
		String percorsoRicerca = null;
		String flgTipoRicerca = null;
		String finalita = null;
		String annoBozzaDa = null;
		String annoBozzaA = null;
		String nroBozzaDa = null;
		String nroBozzaA = null;
		Date tsBozzaDa = null;
		Date tsBozzaA = null;
		Date dtRaccomandataDal = null;
		Date dtRaccomandataAl = null;
		String nroRaccomandata = null;
		String annoStampaDa = null;
		String annoStampaA = null;
		String nroStampaDa = null;
		String nroStampaA = null;
		Date tsStampaDa = null;
		Date tsStampaA = null;
		String annoProtDa = null;
		String annoProtA = null;
		String nroProtDa = null;
		String nroProtA = null;
		String tipoProt = null;
		Date tsRegistrazioneDa = null;
		Date tsRegistrazioneA = null;
		Date tsAperturaFascicoloDa = null;
		Date tsAperturaFascicoloA = null;
		Date presaInCaricoDal = null;
		Date presaInCaricoAl = null;
		Date tsChiusuraFascicoloDa = null;
		Date tsChiusuraFascicoloA = null;
		String flgSoloDaLeggere = null;
		Date tsInvioDa = null;
		Date tsInvioA = null;
		List<DestInvioNotifica> inviatiA = null;
		List<DestInvioNotifica> assegnatiA = null;
		List<DestInvioNotifica> inConoscenzaA = null;
		List<DestInvioNotifica> conNotificheAutoA = null;
		Date tsArchiviazioneDa = null;
		Date tsArchiviazioneA = null;
		Date tsEliminazioneDa = null;
		Date tsEliminazioneA = null;
		String sezioneEliminazione = null;
		String restringiSottofascInserti = null;
		String flgUdFolder = "";
		String soloDocRicevutiViaEmail = null;
		String inviatiViaEmail = null;
		String statoPresaInCarico = null;
		String statoRichAnnullamento = null;
		String statoAutorizzazione = null;
		Date tsAssegnazioneDa = null;
		Date tsAssegnazioneA = null;
		Date tsInvioNotificaDa = null;
		Date tsInvioNotificaA = null;
		String soloRegAnnullate = null;
		String mittente = null;
		String operMittente = null;
		String idRubricaMitt = null;
		String destinatario = null;
		String operDestinatario = null;
		String idRubricaDest = null;
		String esibente = null;
		String operEsibente = null;
		String idRubricaEsib = null;
		String oggetto = null;
		String nomeFascicolo = null;
		String regEffettuataDa = null;
		String fileAssociati = null;
		String mezzoTrasmissioneFilter = null;
		// FILTRI @Registrazioni, visibili in tutte le sezioni della scrivania e ricerca documenti tranne bozze e stampe
		String altraNumerazioneSigla = null;
		String altraNumerazioneAnnoDa = null;
		String altraNumerazioneAnnoA = null;
		Date altraNumerazioneDataDa = null;
		Date altraNumerazioneDataA = null;
		String altraNumerazioneNrDa = null;
		String altraNumerazioneNrA = null;
		// FILTRI MODULO ATTI
		List<DestInvioNotifica> uoProponente = null;
		List<DestInvioNotifica> uoCompetente = null;
		List<DestInvioNotifica> uoRegistrazione = null;
		List<DestInvioNotifica> uoApertura = null;
		String utentiAvvioAtto = null;
		String utentiAdozioneAtto = null;
		String noteUd = null;
		String statiDoc = null;
		String statiFolder = null;
		Date dataFirmaAttoDa = null;
		Date dataFirmaAttoA = null;
		String tipoDoc = null;
		String supportoProt = null;
		AdvancedCriteria criteriaAttributiCustomDoc = null;
		Map<String, String> mappaTipiAttributiCustomDoc = null;
		String tipoFolder = null;
		AdvancedCriteria criteriaAttributiCustomFolder = null;
		Map<String, String> mappaTipiAttributiCustomFolder = null;
		String nroProtRicevuto = null;
		Date dataProtRicevutoDa = null;
		Date dataProtRicevutoA = null;
		String statoTrasmissioneEmail = null;
		String tipoDocStampa = null;
		String nroRichiestaStampaExpDa = null;
		String nroRichiestaStampaExpA = null;
		Date tsRichiestaStampaExpDa = null;
		Date tsRichiestaStampaExpA = null;
		String capoFilaFasc = null;
		String protocolloCapofila = null;
		String docCollegatiProt = null;
		String idIndirizzoInVario = null;
		String indirizzo = null;
		String flgAppostoTimbro = null;
		String subDa = null;
		String subA = null;
		String sigla = null;
		String idClassificazione = null;
		String livelliClassificazione =  null;
		String flgIncludiSottoClassifiche = null;
		
		String annoFascicoloDa = null;
		String annoFascicoloA = null;
		String nroFascicoloDa = null;
		String nroFascicoloA = null;
		String nroSottoFascicoloDa = null;
		String nroSottoFascicoloA = null;
		String codiceFascicolo = null;
		String attoAutAnnullamento = null;
		Date dtStesuraDal = null;
		Date dtStesuraAl = null;
		String nominativoUD   = null;
		String operNominativoUD  = null;
		String tipiNominativoUD = null; 
		
		String centroDiCosto = null;
		Date dataScadenzaDa = null;
		Date dataScadenzaA = null;
		
		String cfNominativoCollegato = null;
		String flgRiservatezza = null;
		String presoInCaricoDa = null;
		String presaVisioneEffettuataDa = null;
		String flgConFileFirmati = null;
		String formatiElettronici = null;
		String flgPassaggioDaSmistamento = null;
		
		// COTO
		String attoCircoscrizione = null;
		String organoCollegialeODG = null;
		String dataODG = null;
		String esitoODG = null;
		
		Date dtScansioneMassivaDal = null;
		Date dtScansioneMassivaAl = null;
		
		String nroImmagineScansioneMassivaDa = null;
		String nroImmagineScansioneMassivaA  = null;
		
		String presenzaOpere = null;
		String sottoTipologiaAtto = null;
		String statiTrasfBloomfleet = null;
		String regoleRegistrazioneAutomaticaEmail = null;
		String rdAeAttiCollegati = null;
		String statoClassFascDocumenti = null;
		
		
		
		boolean setNullFlgSubfolderSearch = getExtraparams().get("setNullFlgSubfolderSearch") != null ? new Boolean(getExtraparams().get(
				"setNullFlgSubfolderSearch")) : false;
		String interesseCessato = getExtraparams().get("interesseCessato");

		List<CriteriPersonalizzati> listCustomFilters = new ArrayList<CriteriPersonalizzati>();

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("maxRecordVisualizzabili".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						String[] filterMaxRecordVisualizzabili = getNumberFilterValue(crit);
						overflowLimit = filterMaxRecordVisualizzabili[0] != null ? Integer.valueOf(filterMaxRecordVisualizzabili[0]) : null;
					}
				} else if ("nroRecordXPagina".equals(crit.getFieldName())) {
					//TODO PAGINAZIONE
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						String[] filterNroRecordXPagina = getNumberFilterValue(crit);
						bachSize = filterNroRecordXPagina[0] != null ? Integer.valueOf(filterNroRecordXPagina[0]) : null;
					}
				} else if ("nroPagina".equals(crit.getFieldName())) {
					//TODO PAGINAZIONE
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						String[] filterNroPagina = getNumberFilterValue(crit);
						nroPagina = filterNroPagina[0] != null ? Integer.valueOf(filterNroPagina[0]) : null;
					}
				} else if ("idNode".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						idNode = (String) crit.getValue();
					}
				} else if ("idFolder".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						idFolder = Long.parseLong((String) crit.getValue());
					}
				} else if ("flgUdFolder".equals(crit.getFieldName())) {
					// Se e' stato aggiunto il filtro "Restringi ricerca a" oppure il filtro "Restringi ricerca a ( su nodi con solo fascicoli )
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						if ("flgUdFolder".equals(crit.getFieldName())) {
							flgUdFolder = String.valueOf(crit.getValue());
						}
						if (flgUdFolder == null)
							flgUdFolder = "";
						// Se stato selezionato il valore "fascicoli e sotto-fascicoli"
						if (flgUdFolder.equalsIgnoreCase("FS")) {
							flgUdFolder = "F";
							restringiSottofascInserti = "F;S;I";
						}
						// Se e' stato selezionato il valore "fascicoli"
						else if (flgUdFolder.equalsIgnoreCase("F")) {
							flgUdFolder = null;
							restringiSottofascInserti = "F";
						}
						// Se stato selezionato il valore "sotto-fascicoli", setto RestringiSottofascInserti = S
						else if (flgUdFolder.equalsIgnoreCase("S")) {
							flgUdFolder = null;
							restringiSottofascInserti = "S";
						}
						// Se e' stato selezionato il valore "documenti"
						else if (flgUdFolder.equalsIgnoreCase("U")) {
							flgUdFolder = "U";
							restringiSottofascInserti = null;
						}
					}
				} else if ("soloDocRicevutiViaEmail".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						soloDocRicevutiViaEmail = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
					}
				} else if ("inviatiViaEmail".equals(crit.getFieldName())) {
					inviatiViaEmail = getTextFilterValue(crit);
				} else if ("statoPresaInCarico".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						statoPresaInCarico = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
					}
				} else if ("flgAppostoTimbro".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgAppostoTimbro = new Boolean(String.valueOf(crit.getValue())) ? "SI" : "NO";
					}
				} else if ("criteriAvanzati".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						criteriAvanzati = String.valueOf(crit.getValue());
					}
				} else if ("finalita".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						finalita = String.valueOf(crit.getValue());
					}
				} else if ("searchFulltext".equals(crit.getFieldName())) {
					// se sono entrato qui sono in modalita' di ricerca con i filtri quindi imposto il valore di default a 1
					includiSottoCartelle = "1";
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						filtroFullText = (String) map.get("parole");
						ArrayList<String> lArrayList = (ArrayList<String>) map.get("attributi");
						checkAttributes = lArrayList != null ? lArrayList.toArray(new String[] {}) : null;
						Boolean flgRicorsiva = (Boolean) map.get("flgRicorsiva");
						if (flgRicorsiva != null) {
							includiSottoCartelle = flgRicorsiva ? "1" : "0";
						}
						String operator = crit.getOperator();
						if (StringUtils.isNotBlank(operator)) {
							if ("allTheWords".equals(operator)) {
								searchAllTerms = new Integer("1");
							} else if ("oneOrMoreWords".equals(operator)) {
								searchAllTerms = new Integer("0");
							}
						}
					}
				} else if ("annoBozza".equals(crit.getFieldName())) {
					String[] estremiAnnoBozza = getNumberFilterValue(crit);
					annoBozzaDa = estremiAnnoBozza[0] != null ? estremiAnnoBozza[0] : null;
					annoBozzaA = estremiAnnoBozza[1] != null ? estremiAnnoBozza[1] : null;
				} else if ("nroBozza".equals(crit.getFieldName())) {
					String[] estremiNroBozza = getNumberFilterValue(crit);
					nroBozzaDa = estremiNroBozza[0] != null ? estremiNroBozza[0] : null;
					nroBozzaA = estremiNroBozza[1] != null ? estremiNroBozza[1] : null;
				} else if ("tsBozza".equals(crit.getFieldName())) {
					Date[] estremiDataBozza = getDateEstesaFilterValue(crit);
					if (tsBozzaDa != null) {
						tsBozzaDa = tsBozzaDa.compareTo(estremiDataBozza[0]) < 0 ? estremiDataBozza[0] : tsBozzaDa;
					} else {
						tsBozzaDa = estremiDataBozza[0];
					}
					if (tsBozzaA != null) {
						tsBozzaA = tsBozzaA.compareTo(estremiDataBozza[1]) > 0 ? estremiDataBozza[1] : tsBozzaA;
					} else {
						tsBozzaA = estremiDataBozza[1];
					}
				} else if ("dtRaccomandata".equals(crit.getFieldName())) {
					Date[] estremiDataRaccomandata = getDateFilterValue(crit);
					if (dtRaccomandataDal != null) {
						dtRaccomandataDal = dtRaccomandataDal.compareTo(estremiDataRaccomandata[0]) < 0 ? estremiDataRaccomandata[0] : dtRaccomandataDal;
					} else {
						dtRaccomandataDal = estremiDataRaccomandata[0];
					}
					if (dtRaccomandataAl != null) {
						dtRaccomandataAl = dtRaccomandataAl.compareTo(estremiDataRaccomandata[1]) > 0 ? estremiDataRaccomandata[1] : dtRaccomandataAl;
					} else {
						dtRaccomandataAl = estremiDataRaccomandata[1];
					}
				} else if ("nroRaccomandata".equals(crit.getFieldName())) {
					nroRaccomandata = getTextFilterValue(crit);
				} else if ("tipoDocStampa".equals(crit.getFieldName())) {
					tipoDocStampa = getTextFilterValue(crit);
				} else if ("annoStampa".equals(crit.getFieldName())) {
					String[] estremiAnnoStampa = getNumberFilterValue(crit);
					annoStampaDa = estremiAnnoStampa[0] != null ? estremiAnnoStampa[0] : null;
					annoStampaA = estremiAnnoStampa[1] != null ? estremiAnnoStampa[1] : null;
				} else if ("nroStampa".equals(crit.getFieldName())) {
					String[] estremiNroStampa = getNumberFilterValue(crit);
					nroStampaDa = estremiNroStampa[0] != null ? estremiNroStampa[0] : null;
					nroStampaA = estremiNroStampa[1] != null ? estremiNroStampa[1] : null;
				} else if ("tsStampa".equals(crit.getFieldName())) {
					Date[] estremiDataStampa = getDateFilterValue(crit);
					if (tsStampaDa != null) {
						tsStampaDa = tsStampaDa.compareTo(estremiDataStampa[0]) < 0 ? estremiDataStampa[0] : tsStampaDa;
					} else {
						tsStampaDa = estremiDataStampa[0];
					}
					if (tsStampaA != null) {
						tsStampaA = tsStampaA.compareTo(estremiDataStampa[1]) > 0 ? estremiDataStampa[1] : tsStampaA;
					} else {
						tsStampaA = estremiDataStampa[1];
					}
				} else if ("nroRichiestaStampaExp".equals(crit.getFieldName())) {
					String[] estremiNroRichiestaStampaExp = getNumberFilterValue(crit);
					nroRichiestaStampaExpDa = estremiNroRichiestaStampaExp[0] != null ? estremiNroRichiestaStampaExp[0] : null;
					nroRichiestaStampaExpA = estremiNroRichiestaStampaExp[1] != null ? estremiNroRichiestaStampaExp[1] : null;
				} else if ("tsRichiestaStampaExp".equals(crit.getFieldName())) {
					Date[] estremiDataRichiestaStampaExp = getDateFilterValue(crit);
					if (tsRichiestaStampaExpDa != null) {
						tsRichiestaStampaExpDa = tsRichiestaStampaExpDa.compareTo(estremiDataRichiestaStampaExp[0]) < 0 ? estremiDataRichiestaStampaExp[0]
								: tsRichiestaStampaExpDa;
					} else {
						tsRichiestaStampaExpDa = estremiDataRichiestaStampaExp[0];
					}
					if (tsRichiestaStampaExpA != null) {
						tsRichiestaStampaExpA = tsRichiestaStampaExpA.compareTo(estremiDataRichiestaStampaExp[1]) > 0 ? estremiDataRichiestaStampaExp[1]
								: tsRichiestaStampaExpA;
					} else {
						tsRichiestaStampaExpA = estremiDataRichiestaStampaExp[1];
					}
				} else if ("annoProt".equals(crit.getFieldName())) {
					String[] estremiAnnoProt = getNumberFilterValue(crit);
					annoProtDa = estremiAnnoProt[0] != null ? estremiAnnoProt[0] : null;
					annoProtA = estremiAnnoProt[1] != null ? estremiAnnoProt[1] : null;
				} else if ("nroProt".equals(crit.getFieldName())) {
					String[] estremiNroProt = getNumberFilterValue(crit);
					nroProtDa = estremiNroProt[0] != null ? estremiNroProt[0] : null;
					nroProtA = estremiNroProt[1] != null ? estremiNroProt[1] : null;
				} else if ("tipoProt".equals(crit.getFieldName())) {
					tipoProt = getTextFilterValue(crit);
				} else if ("tsRegistrazione".equals(crit.getFieldName())) {
					Date[] estremiDataReg = getDateEstesaFilterValue(crit);
					if (tsRegistrazioneDa != null) {
						tsRegistrazioneDa = tsRegistrazioneDa.compareTo(estremiDataReg[0]) < 0 ? estremiDataReg[0] : tsRegistrazioneDa;
					} else {
						tsRegistrazioneDa = estremiDataReg[0];
					}
					if (tsRegistrazioneA != null) {
						tsRegistrazioneA = tsRegistrazioneA.compareTo(estremiDataReg[1]) > 0 ? estremiDataReg[1] : tsRegistrazioneA;
					} else {
						tsRegistrazioneA = estremiDataReg[1];
					}
				} else if ("tsAperturaFascicolo".equals(crit.getFieldName())) {
					Date[] estremiDataAperturaFascicolo = getDateEstesaFilterValue(crit);
					if (tsAperturaFascicoloDa != null) {
						tsAperturaFascicoloDa = tsAperturaFascicoloDa.compareTo(estremiDataAperturaFascicolo[0]) < 0 ? estremiDataAperturaFascicolo[0]
								: tsAperturaFascicoloDa;
					} else {
						tsAperturaFascicoloDa = estremiDataAperturaFascicolo[0];
					}
					if (tsAperturaFascicoloA != null) {
						tsAperturaFascicoloA = tsAperturaFascicoloA.compareTo(estremiDataAperturaFascicolo[1]) > 0 ? estremiDataAperturaFascicolo[1]
								: tsAperturaFascicoloA;
					} else {
						tsAperturaFascicoloA = estremiDataAperturaFascicolo[1];
					}
				} else if ("tsPresaInCarico".equals(crit.getFieldName())) {
					Date[] estremiDataPresaInCarico = getDateEstesaFilterValue(crit);
					if (presaInCaricoDal!= null) {
						presaInCaricoDal = presaInCaricoDal.compareTo(estremiDataPresaInCarico[0]) < 0 ? estremiDataPresaInCarico[0]
								: presaInCaricoDal;
					} else {
						presaInCaricoDal = estremiDataPresaInCarico[0];
					}
					if (presaInCaricoAl != null) {
						presaInCaricoAl = presaInCaricoAl.compareTo(estremiDataPresaInCarico[1]) > 0 ? estremiDataPresaInCarico[1]
								: presaInCaricoAl;
					} else {
						presaInCaricoAl = estremiDataPresaInCarico[1];
					}
				} else if ("tsChiusuraFascicolo".equals(crit.getFieldName())) {
					Date[] estremiDataChiusuraFascicolo = getDateFilterValue(crit);
					if (tsChiusuraFascicoloDa != null) {
						tsChiusuraFascicoloDa = tsChiusuraFascicoloDa.compareTo(estremiDataChiusuraFascicolo[0]) < 0 ? estremiDataChiusuraFascicolo[0]
								: tsChiusuraFascicoloDa;
					} else {
						tsChiusuraFascicoloDa = estremiDataChiusuraFascicolo[0];
					}
					if (tsChiusuraFascicoloA != null) {
						tsChiusuraFascicoloA = tsChiusuraFascicoloA.compareTo(estremiDataChiusuraFascicolo[1]) > 0 ? estremiDataChiusuraFascicolo[1]
								: tsChiusuraFascicoloA;
					} else {
						tsChiusuraFascicoloA = estremiDataChiusuraFascicolo[1];
					}
				} else if ("flgSoloDaLeggere".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgSoloDaLeggere = new Boolean(String.valueOf(crit.getValue())) ? "1" : null;
					}
				} else if ("tsInvio".equals(crit.getFieldName())) {
					Date[] estremiDataInvio = getDateFilterValue(crit);
					if (tsInvioDa != null) {
						tsInvioDa = tsInvioDa.compareTo(estremiDataInvio[0]) < 0 ? estremiDataInvio[0] : tsInvioDa;
					} else {
						tsInvioDa = estremiDataInvio[0];
					}
					if (tsInvioA != null) {
						tsInvioA = tsInvioA.compareTo(estremiDataInvio[1]) > 0 ? estremiDataInvio[1] : tsInvioA;
					} else {
						tsInvioA = estremiDataInvio[1];
					}
				} else if ("destinatarioInvio".equals(crit.getFieldName())) {
					inviatiA = getListaSceltaOrganigrammaFilterValue(crit);
				} else if ("tsArchiviazione".equals(crit.getFieldName())) {
					Date[] estremiDataArchiviazione = getDateFilterValue(crit);
					if (tsArchiviazioneDa != null) {
						tsArchiviazioneDa = tsArchiviazioneDa.compareTo(estremiDataArchiviazione[0]) < 0 ? estremiDataArchiviazione[0] : tsArchiviazioneDa;
					} else {
						tsArchiviazioneDa = estremiDataArchiviazione[0];
					}
					if (tsArchiviazioneA != null) {
						tsArchiviazioneA = tsArchiviazioneA.compareTo(estremiDataArchiviazione[1]) > 0 ? estremiDataArchiviazione[1] : tsArchiviazioneA;
					} else {
						tsArchiviazioneA = estremiDataArchiviazione[1];
					}
				} else if ("tsEliminazione".equals(crit.getFieldName())) {
					Date[] estremiDataEliminazione = getDateFilterValue(crit);
					if (tsEliminazioneDa != null) {
						tsEliminazioneDa = tsEliminazioneDa.compareTo(estremiDataEliminazione[0]) < 0 ? estremiDataEliminazione[0] : tsEliminazioneDa;
					} else {
						tsEliminazioneDa = estremiDataEliminazione[0];
					}
					if (tsEliminazioneA != null) {
						tsEliminazioneA = tsEliminazioneA.compareTo(estremiDataEliminazione[1]) > 0 ? estremiDataEliminazione[1] : tsEliminazioneA;
					} else {
						tsEliminazioneA = estremiDataEliminazione[1];
					}
				} else if ("sezioneEliminazioneDoc".equals(crit.getFieldName()) || "sezioneEliminazioneFasc".equals(crit.getFieldName())) {
					sezioneEliminazione = getTextFilterValue(crit);
				} else if ("statoRichAnnullamento".equals(crit.getFieldName())) {
					statoRichAnnullamento = getTextFilterValue(crit);
				} else if ("statoAutorizzazione".equals(crit.getFieldName())) {
					statoAutorizzazione = getTextFilterValue(crit);
				} else if ("tsAssegnazione".equals(crit.getFieldName())) {
					Date[] estremiDataAssegnazione = getDateEstesaFilterValue(crit);
					if (tsAssegnazioneDa != null) {
						tsAssegnazioneDa = tsAssegnazioneDa.compareTo(estremiDataAssegnazione[0]) < 0 ? estremiDataAssegnazione[0] : tsAssegnazioneDa;
					} else {
						tsAssegnazioneDa = estremiDataAssegnazione[0];
					}
					if (tsAssegnazioneA != null) {
						tsAssegnazioneA = tsAssegnazioneA.compareTo(estremiDataAssegnazione[1]) > 0 ? estremiDataAssegnazione[1] : tsAssegnazioneA;
					} else {
						tsAssegnazioneA = estremiDataAssegnazione[1];
					}
				} else if ("assegnatoA".equals(crit.getFieldName())) {
					assegnatiA = getListaSceltaOrganigrammaFilterValue(crit);
				} else if ("tsNotificaCC".equals(crit.getFieldName()) || "tsNotificaNA".equals(crit.getFieldName())) {
					Date[] estremiDataNotifica = getDateEstesaFilterValue(crit);
					if (tsInvioNotificaDa != null) {
						tsInvioNotificaDa = tsInvioNotificaDa.compareTo(estremiDataNotifica[0]) < 0 ? estremiDataNotifica[0] : tsInvioNotificaDa;
					} else {
						tsInvioNotificaDa = estremiDataNotifica[0];
					}
					if (tsInvioNotificaA != null) {
						tsInvioNotificaA = tsInvioNotificaA.compareTo(estremiDataNotifica[1]) > 0 ? estremiDataNotifica[1] : tsInvioNotificaA;
					} else {
						tsInvioNotificaA = estremiDataNotifica[1];
					}
				} else if ("inviatoA".equals(crit.getFieldName())) {
					inConoscenzaA = getListaSceltaOrganigrammaFilterValue(crit);
				} else if ("notificatoA".equals(crit.getFieldName())) {
					conNotificheAutoA = getListaSceltaOrganigrammaFilterValue(crit);
				} else if ("soloRegAnnullate".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						soloRegAnnullate = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
					}
				} else if ("mittente".equals(crit.getFieldName())) {
					mittente = getValueStringaFullTextMista(crit);
					operMittente = getOperatorStringaFullTextMista(crit);
				} else if ("mittenteInRubrica".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						idRubricaMitt = (String) map.get("id");
					}
				} else if ("destinatario".equals(crit.getFieldName())) {
					destinatario = getValueStringaFullTextMista(crit);
					operDestinatario = getOperatorStringaFullTextMista(crit);
				} else if ("destinatarioInRubrica".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						idRubricaDest = (String) map.get("id");
					}
				} else if ("esibente".equals(crit.getFieldName())) {
					esibente = getValueStringaFullTextMista(crit);
					operEsibente = getOperatorStringaFullTextMista(crit);
				} else if ("esibenteInRubrica".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						idRubricaEsib = (String) map.get("id");
					}
				} else if ("oggetto".equals(crit.getFieldName())) {
					oggetto = getTextFilterValue(crit);
				} else if ("nomeFascicolo".equals(crit.getFieldName())) {
					nomeFascicolo = getTextFilterValue(crit);
				} else if ("regEffettuataDa".equals(crit.getFieldName())) {
					regEffettuataDa = getTextFilterValue(crit);
				} else if ("fileAssociati".equals(crit.getFieldName())) {
					fileAssociati = getTextFilterValue(crit);
				} else if ("altraNumerazioneSigla".equals(crit.getFieldName())) {
					altraNumerazioneSigla = getTextFilterValue(crit);
				} else if ("altraNumerazioneSiglaArchivioLista".equals(crit.getFieldName())) {
					altraNumerazioneSigla = getTextFilterValue(crit);
				} else if ("altraNumerazioneNr".equals(crit.getFieldName())) {
					String[] estremiAnnoStampa = getNumberFilterValue(crit);
					altraNumerazioneNrDa = estremiAnnoStampa[0] != null ? estremiAnnoStampa[0] : null;
					altraNumerazioneNrA = estremiAnnoStampa[1] != null ? estremiAnnoStampa[1] : null;
				} else if ("altraNumerazioneAnno".equals(crit.getFieldName())) {
					String[] estremiAnnoBozza = getNumberFilterValue(crit);
					altraNumerazioneAnnoDa = estremiAnnoBozza[0] != null ? estremiAnnoBozza[0] : null;
					altraNumerazioneAnnoA = estremiAnnoBozza[1] != null ? estremiAnnoBozza[1] : null;
				} else if ("altraNumerazioneData".equals(crit.getFieldName())) {
					Date[] estremiDataRegistrazione = getDateEstesaFilterValue(crit);
					if (altraNumerazioneDataDa != null) {
						altraNumerazioneDataDa = tsStampaDa.compareTo(estremiDataRegistrazione[0]) < 0 ? estremiDataRegistrazione[0] : tsStampaDa;
					} else {
						altraNumerazioneDataDa = estremiDataRegistrazione[0];
					}
					if (altraNumerazioneDataA != null) {
						altraNumerazioneDataA = tsStampaA.compareTo(estremiDataRegistrazione[1]) > 0 ? estremiDataRegistrazione[1] : tsStampaA;
					} else {
						altraNumerazioneDataA = estremiDataRegistrazione[1];
					}
				} else if ("mezzoTrasmissione".equals(crit.getFieldName())) {
					mezzoTrasmissioneFilter = getTextFilterValue(crit);
				}
				// FILTRI MODULO ATTI
				else if ("uoCompetente".equals(crit.getFieldName())) {
					uoCompetente = getListaSceltaOrganigrammaFilterValue(crit);
				} else if ("uoProponente".equals(crit.getFieldName())) {
					uoProponente = getListaSceltaOrganigrammaFilterValue(crit);
				} else if ("utentiAvvioAtto".equals(crit.getFieldName())) {
					utentiAvvioAtto = getTextFilterValue(crit);
				} else if ("utentiAdozioneAtto".equals(crit.getFieldName())) {
					utentiAdozioneAtto = getTextFilterValue(crit);
				} else if ("noteUd".equals(crit.getFieldName())) {
					noteUd = getTextFilterValue(crit);
				} else if ("statiDoc".equals(crit.getFieldName())) {
					statiDoc = getTextFilterValue(crit);
				} else if("statiFolder".equals(crit.getFieldName())) {
					statiFolder = getTextFilterValue(crit);
				} else if ("dataFirmaAtto".equals(crit.getFieldName())) {
					Date[] estremiDataFirmaAtto = getDateFilterValue(crit);
					if (dataFirmaAttoDa != null) {
						dataFirmaAttoDa = dataFirmaAttoDa.compareTo(estremiDataFirmaAtto[0]) < 0 ? estremiDataFirmaAtto[0] : dataFirmaAttoDa;
					} else {
						dataFirmaAttoDa = estremiDataFirmaAtto[0];
					}
					if (dataFirmaAttoA != null) {
						dataFirmaAttoA = dataFirmaAttoA.compareTo(estremiDataFirmaAtto[1]) > 0 ? estremiDataFirmaAtto[1] : dataFirmaAttoA;
					} else {
						dataFirmaAttoA = estremiDataFirmaAtto[1];
					}
				} else if ("tipoDoc".equals(crit.getFieldName())) {
					// tipoDoc = getTextFilterValue(crit);
					tipoDoc = getTipoAttributiCustomDelTipoFilter(crit);
					criteriaAttributiCustomDoc = getCriteriaAttributiCustomDelTipoFilter(crit);
					mappaTipiAttributiCustomDoc = getMappaTipiAttributiCustomDelTipoFilter(crit);
				} else if ("supportoProt".equals(crit.getFieldName())) {
					supportoProt = getTextFilterValue(crit);
				} else if("uoRegistrazione".equals(crit.getFieldName())) {
					uoRegistrazione = getListaSceltaOrganigrammaFilterValue(crit);
				} else if("uoApertura".equals(crit.getFieldName())) {
					uoApertura = getListaSceltaOrganigrammaFilterValue(crit);
				}  else if ("tipoFolder".equals(crit.getFieldName())) {
					tipoFolder = getTipoAttributiCustomDelTipoFilter(crit);
					criteriaAttributiCustomFolder = getCriteriaAttributiCustomDelTipoFilter(crit);
					mappaTipiAttributiCustomFolder = getMappaTipiAttributiCustomDelTipoFilter(crit);
				} else if ("nroProtRicevuto".equals(crit.getFieldName())) {
					nroProtRicevuto = getTextFilterValue(crit);
				} else if ("dataProtRicevuto".equals(crit.getFieldName())) {
					Date[] estremiDataProtRicevuto = getDateFilterValue(crit);
					if (dataProtRicevutoDa != null) {
						dataProtRicevutoDa = dataProtRicevutoDa.compareTo(estremiDataProtRicevuto[0]) < 0 ? estremiDataProtRicevuto[0] : dataProtRicevutoDa;
					} else {
						dataProtRicevutoDa = estremiDataProtRicevuto[0];
					}
					if (dataProtRicevutoA != null) {
						dataProtRicevutoA = dataProtRicevutoA.compareTo(estremiDataProtRicevuto[1]) > 0 ? estremiDataProtRicevuto[1] : dataProtRicevutoA;
					} else {
						dataProtRicevutoA = estremiDataProtRicevuto[1];
					}
				} else if ("statoTrasmissioneEmail".equals(crit.getFieldName())) {
					statoTrasmissioneEmail = getTextFilterValue(crit);
				} else if ("capoFilaFasc".equals(crit.getFieldName())) {
					capoFilaFasc = getTextFilterValue(crit);
				} else if ("registroAltriRifTipo".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("REGISTRO_TIPO_RIF_UD", crit));
				} else if ("registroAltriRifNro".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("NRO_RIF_UD", crit));
				} else if ("registroAltriRifData".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("DATA_RIF_UD", crit, TipoFiltro.DATA_SENZA_ORA));
				} else if ("registroAltriRifAnno".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("ANNO_RIF_UD", crit, TipoFiltro.NUMERO));
				} else if ("protocolloCapofila".equals(crit.getFieldName())) {
					protocolloCapofila = getTextFilterValue(crit);
				} else if ("docCollegatiProt".equals(crit.getFieldName())) {
					docCollegatiProt = getTextFilterValue(crit);
				} else if ("idIndirizzoInVario".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						idIndirizzoInVario = (String) map.get("id");	
					}
				} else if ("indirizzo".equals(crit.getFieldName())) {
					indirizzo = getTextFilterValue(crit);
				} else if ("sub".equals(crit.getFieldName())) {
					String[] estremiSub = getNumberFilterValue(crit);
					subDa = estremiSub[0] != null ? estremiSub[0] : null;
					subA = estremiSub[1] != null ? estremiSub[1] : null;
				} else if ("sigla".equals(crit.getFieldName())) {
					sigla = getTextFilterValue(crit);
				} else if ("classificazioneArchivio".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						idClassificazione = (String) map.get("classifiche");
						livelliClassificazione = (String) map.get("livelliClassificazione");
						flgIncludiSottoClassifiche = new Boolean(String.valueOf(map.get("flgIncludiSottoClassifiche"))) ? "1" : "0";
					}
				} else if ("annoFascicolo".equals(crit.getFieldName())) {
						String[] estremiAnnoFascicolo = getNumberFilterValue(crit);
						annoFascicoloDa = estremiAnnoFascicolo[0] != null ? estremiAnnoFascicolo[0] : null;
						annoFascicoloA  = estremiAnnoFascicolo[1] != null ? estremiAnnoFascicolo[1] : null;
				} else if ("nroFascicolo".equals(crit.getFieldName())) {
					String[] estremiNroFascicolo = getNumberFilterValue(crit);
					nroFascicoloDa = estremiNroFascicolo[0] != null ? estremiNroFascicolo[0] : null;
					nroFascicoloA  = estremiNroFascicolo[1] != null ? estremiNroFascicolo[1] : null;
				} else if ("nroSottoFascicolo".equals(crit.getFieldName())) {
					String[] estremiNroSottoFascicolo = getNumberFilterValue(crit);
					nroSottoFascicoloDa = estremiNroSottoFascicolo[0] != null ? estremiNroSottoFascicolo[0] : null;
					nroSottoFascicoloA  = estremiNroSottoFascicolo[1] != null ? estremiNroSottoFascicolo[1] : null;
				} else if ("codiceFascicolo".equals(crit.getFieldName())) {
					codiceFascicolo = getTextFilterValue(crit);
				} else if("attoAutAnnullamento".equals(crit.getFieldName())) {
					attoAutAnnullamento = getTextFilterValue(crit);
				} else if("dtStesura".equals(crit.getFieldName())) {
					Date[] estremiDataStesura = getDateFilterValue(crit);
					if (dtStesuraDal != null) {
						dtStesuraDal = dtStesuraDal.compareTo(estremiDataStesura[0]) < 0 ? estremiDataStesura[0] : dtStesuraDal;
					} else {
						dtStesuraDal = estremiDataStesura[0];
					}
					if (dtStesuraAl != null) {
						dtStesuraAl = dtStesuraAl.compareTo(estremiDataStesura[1]) > 0 ? estremiDataStesura[1] : dtStesuraAl;
					} else {
						dtStesuraAl = estremiDataStesura[1];
					}
					
				} else if("docCollegatiNominativo".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						if((String) map.get("nominativo") !=null ){
							nominativoUD = (String) map.get("nominativo");
							operNominativoUD = getOperatorStringaFullTextMista(crit);	
						}
						if (map.get("tipiNominativi") instanceof ArrayList) {
							ArrayList<String>  lArrayList = (ArrayList<String>) map.get("tipiNominativi");
							tipiNominativoUD = StringUtils.join(lArrayList, ";");
						}
					}	
				} else if ("cig".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("CIG", crit));
				} else if ("perizia".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("COD_PERIZIA_ADSP", crit));
				} else if("centroDiCosto".equals(crit.getFieldName())) {
//					centroDiCosto = getTextFilterValue(crit);
					centroDiCosto = getValueStringaFullTextMista(crit);
				} else if("dataScadenza".equals(crit.getFieldName())) {
					Date[] estremiDataScadenza = getDateFilterValue(crit);
					dataScadenzaDa = estremiDataScadenza[0];
					dataScadenzaA = estremiDataScadenza[1];
				} else if("cfNominativoCollegato".equals(crit.getFieldName())) {
					cfNominativoCollegato = getTextFilterValue(crit);			  
				} else if("flgRiservatezza".equals(crit.getFieldName())) {
					if(StringUtils.isNotBlank((String) crit.getValue())) {
						flgRiservatezza = String.valueOf(crit.getValue());
					}
				} else if ("presoInCaricoDa".equals(crit.getFieldName())) {
					presoInCaricoDa = getTextFilterValue(crit);
				} else if ("presaVisioneEffettuataDa".equals(crit.getFieldName())) {
					presaVisioneEffettuataDa = getTextFilterValue(crit);
				} else if ("flgConFileFirmati".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgConFileFirmati = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
					}
				} else if ("formatiElettronici".equals(crit.getFieldName())) {
					formatiElettronici = getTextFilterValue(crit);
				}
				// Sottoposto a controllo reg.amm
				else if ("flgSottopostoControlloRegAmm".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("FLG_SOTTOPOSTO_CONTROLLO_REG_AMM", crit, TipoFiltro.CHECK));
				} 
				// PER COTO
				else if ("attoCircoscrizione".equals(crit.getFieldName())) {
					attoCircoscrizione = getTextFilterValue(crit);
				} else if("inseritoInOdGDiscussioneSeduta".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						organoCollegialeODG = (String) map.get("organoCollegiale");
						dataODG = (String) map.get("data");
						esitoODG = (String) map.get("esito");									
					}
				} else if ("flgPassaggioDaSmistamento".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgPassaggioDaSmistamento = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
					}
				}
				// Data Scansione massiva
				else if("dataScansioneMassiva".equals(crit.getFieldName())) {
					Date[] estremiDataScansioneMassiva = getDateFilterValue(crit);
					if (dtScansioneMassivaDal != null) {
						dtScansioneMassivaDal = dtScansioneMassivaDal.compareTo(estremiDataScansioneMassiva[0]) < 0 ? estremiDataScansioneMassiva[0] : dtScansioneMassivaDal;
					} else {
						dtScansioneMassivaDal = estremiDataScansioneMassiva[0];
					}
					if (dtScansioneMassivaAl != null) {
						dtScansioneMassivaAl = dtScansioneMassivaAl.compareTo(estremiDataScansioneMassiva[1]) > 0 ? estremiDataScansioneMassiva[1] : dtScansioneMassivaAl;
					} else {
						dtScansioneMassivaAl = estremiDataScansioneMassiva[1];
					}
				}
				// Nro Immagine Scansione Massiva
				else if ("nroImmagineScansioneMassiva".equals(crit.getFieldName())) {
					String[] estremiNroImmagineScansioneMassiva = getNumberFilterValue(crit);
					nroImmagineScansioneMassivaDa = estremiNroImmagineScansioneMassiva[0] != null ? estremiNroImmagineScansioneMassiva[0] : null;
					nroImmagineScansioneMassivaA  = estremiNroImmagineScansioneMassiva[1] != null ? estremiNroImmagineScansioneMassiva[1] : null;
				}
				// Sede
				else if ("sedeScansioneMassiva".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("SEDE_SCANSIONE", crit));
				} 
				
				// Presenza opere (solo per ADSP)
				else if ("presenzaOpere".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						presenzaOpere = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
					}
				}
				
				// Sotto tipologia (solo per ADSP)
				else if("sottoTipologiaAtto".equals(crit.getFieldName())) {
					sottoTipologiaAtto = getTextFilterValue(crit);
				}
				
				// Stato trasferimento a Bloomfleet (solo per A2A)
				else if("statiTrasfBloomfleet".equals(crit.getFieldName())) {
					statiTrasfBloomfleet = getTextFilterValue(crit);
				}
				
				// Regola reg. automatica (solo per A2A)
				else if("regoleRegistrazioneAutomaticaEmail".equals(crit.getFieldName())) {
					regoleRegistrazioneAutomaticaEmail = getTextFilterValue(crit);
				}
				
				// RdA e atti collegati
				else if("rdAeAttiCollegati".equals(crit.getFieldName())) {
					rdAeAttiCollegati = getTextFilterValue(crit);
				}
				
				// Documenti classificati/fascicolati
				else if("statoClassFascDocumenti".equals(crit.getFieldName())) {
					statoClassFascDocumenti = getTextFilterValue(crit);
				}
			}
		}

		if (criteriaAttributiCustomDoc != null && criteriaAttributiCustomDoc.getCriteria() != null) {
			for (Criterion crit : criteriaAttributiCustomDoc.getCriteria()) {
				String tipo = mappaTipiAttributiCustomDoc != null ? mappaTipiAttributiCustomDoc.get(crit.getFieldName()) : null;
				TipoFiltro tipoFiltro = CriteriPersonalizzatiUtil.getTipoFiltroFromTipoAttributoCustom(tipo);
				CriteriPersonalizzati criterioPersonalizzato = CriteriPersonalizzatiUtil.getCriterioPersonalizzato(crit.getFieldName(), crit, tipoFiltro);
				if(criterioPersonalizzato != null) {
					listCustomFilters.add(criterioPersonalizzato);
				}
			}
		}

		if (criteriaAttributiCustomFolder != null && criteriaAttributiCustomFolder.getCriteria() != null) {
			for (Criterion crit : criteriaAttributiCustomFolder.getCriteria()) {
				String tipo = mappaTipiAttributiCustomFolder != null ? mappaTipiAttributiCustomFolder.get(crit.getFieldName()) : null;
				TipoFiltro tipoFiltro = CriteriPersonalizzatiUtil.getTipoFiltroFromTipoAttributoCustom(tipo);
				CriteriPersonalizzati criterioPersonalizzato = CriteriPersonalizzatiUtil.getCriterioPersonalizzato(crit.getFieldName(), crit, tipoFiltro);
				if(criterioPersonalizzato != null) {
					listCustomFilters.add(criterioPersonalizzato);
				}
			}
		}

		boolean daProtocollare = false;
		CriteriAvanzati scCriteriAvanzati = new CriteriAvanzati();
		if (criteriAvanzati != null) {
			XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			scCriteriAvanzati = lXmlUtilityDeserializer.unbindXml(criteriAvanzati, CriteriAvanzati.class);
			if (scCriteriAvanzati.getNomeWorkspaceApp() != null && "<BOZZE>".equals(scCriteriAvanzati.getNomeWorkspaceApp())) {
				daProtocollare = true;
			}
			if (scCriteriAvanzati.getStatiDettDoc() != null && scCriteriAvanzati.getStatiDettDoc().size() > 0) {
				for (Stato stato : scCriteriAvanzati.getStatiDettDoc()) {
					if ("DP".equals(stato.getCodice())) {
						daProtocollare = true;
					}
				}
			}
		}
		
		scCriteriAvanzati.setIdNode(idNode);
		
		// su tutti i filtri che sono variabili lista (es. @Registrazioni) il filtro caricato da DB deve andare in append con quello
		// eventuale a GUI relativo alla stessa variabile lista

		List<Registrazione> listaRegistrazioni = new ArrayList<Registrazione>();
		if (scCriteriAvanzati.getRegistrazioni() != null && scCriteriAvanzati.getRegistrazioni().size() > 0) {
			listaRegistrazioni = scCriteriAvanzati.getRegistrazioni();
		}
		
		if (!daProtocollare
				&& ((StringUtils.isNotBlank(annoProtDa)) || (StringUtils.isNotBlank(annoProtA)) || (StringUtils.isNotBlank(nroProtDa))
						|| (StringUtils.isNotBlank(nroProtA)) || (tsRegistrazioneDa != null) || (tsRegistrazioneA != null)
						|| (StringUtils.isNotBlank(subDa)) 
						|| (StringUtils.isNotBlank(subA)) || (StringUtils.isNotBlank(sigla)) )) {
			Registrazione registrazione = new Registrazione();
			registrazione.setCategoria("PG");
			registrazione.setSigla(sigla);
			registrazione.setAnno(null);
			registrazione.setNumeroDa(nroProtDa);
			registrazione.setNumeroA(nroProtA);
			registrazione.setDataDa(tsRegistrazioneDa);
			registrazione.setDataA(tsRegistrazioneA);
			registrazione.setAnnoDa(annoProtDa);
			registrazione.setAnnoA(annoProtA);
			registrazione.setEffettuataDa(regEffettuataDa);
			registrazione.setSubDa(subDa);
			registrazione.setSubA(subA);
			listaRegistrazioni.add(registrazione);
		}

		if ((StringUtils.isNotBlank(annoBozzaDa)) || (StringUtils.isNotBlank(annoBozzaA)) || (StringUtils.isNotBlank(nroBozzaDa))
				|| (StringUtils.isNotBlank(nroBozzaA)) || (tsBozzaDa != null) || (tsBozzaA != null)) {
			Registrazione registrazione = new Registrazione();
			registrazione.setCategoria("I");
			registrazione.setSigla("N.I.");
			registrazione.setAnno(null);
			registrazione.setNumeroDa(nroBozzaDa);
			registrazione.setNumeroA(nroBozzaA);
			registrazione.setDataDa(tsBozzaDa);
			registrazione.setDataA(tsBozzaA);
			registrazione.setAnnoDa(annoBozzaDa);
			registrazione.setAnnoA(annoBozzaA);
			registrazione.setEffettuataDa(regEffettuataDa);
			listaRegistrazioni.add(registrazione);
		}

		if ((StringUtils.isNotBlank(tipoDocStampa)) || (StringUtils.isNotBlank(annoStampaDa)) || (StringUtils.isNotBlank(annoStampaA))
				|| (StringUtils.isNotBlank(nroStampaDa)) || (StringUtils.isNotBlank(nroStampaA)) || (tsStampaDa != null) || (tsStampaA != null)) {
			Registrazione registrazione = new Registrazione();
			registrazione.setCategoria("I");
			registrazione.setSigla(tipoDocStampa);
			registrazione.setAnno(null);
			registrazione.setNumeroDa(nroStampaDa);
			registrazione.setNumeroA(nroStampaA);
			registrazione.setDataDa(tsStampaDa);
			registrazione.setDataA(tsStampaA);
			registrazione.setAnnoDa(annoStampaDa);
			registrazione.setAnnoA(annoStampaA);
			listaRegistrazioni.add(registrazione);
		}

		if ((StringUtils.isNotBlank(altraNumerazioneAnnoDa)) || (StringUtils.isNotBlank(altraNumerazioneSigla))
				|| (StringUtils.isNotBlank(altraNumerazioneAnnoA)) || (StringUtils.isNotBlank(altraNumerazioneNrDa))
				|| (StringUtils.isNotBlank(altraNumerazioneNrA)) || (altraNumerazioneDataDa != null) || (altraNumerazioneDataA != null)) {
			Registrazione registrazione = new Registrazione();
			registrazione.setCategoria("R;I;A");
			registrazione.setSigla(altraNumerazioneSigla);
			registrazione.setAnno(null);
			registrazione.setNumeroDa(altraNumerazioneNrDa);
			registrazione.setNumeroA(altraNumerazioneNrA);
			registrazione.setDataDa(altraNumerazioneDataDa);
			registrazione.setDataA(altraNumerazioneDataA);
			registrazione.setAnnoDa(altraNumerazioneAnnoDa);
			registrazione.setAnnoA(altraNumerazioneAnnoA);
			registrazione.setEffettuataDa(regEffettuataDa);
			listaRegistrazioni.add(registrazione);
		}
						
		// Se il filtro "Registrazione effettuata da" e' valorizzato verifico se ' presente nell'ArrayList
		if(regEffettuataDa!=null){
			boolean regEffettuataDaExist = false;
			if(listaRegistrazioni!=null && listaRegistrazioni.size()>0){
				for (int i = 0; i < listaRegistrazioni.size(); i++) {
					if (listaRegistrazioni.get(i) != null && 
						listaRegistrazioni.get(i).getEffettuataDa() !=null && 
						!listaRegistrazioni.get(i).getEffettuataDa().equalsIgnoreCase("")
							) {
						regEffettuataDaExist = true;
					}
				}
			}
			
			// Se non e' presente lo aggiugo
			if(!regEffettuataDaExist){
				Registrazione registrazione = new Registrazione();
				registrazione.setEffettuataDa(regEffettuataDa);
				listaRegistrazioni.add(registrazione);
			}				
		}		
		
		scCriteriAvanzati.setRegistrazioni(listaRegistrazioni);
		
		List<SupportiOrigDoc> listaSupportiOrigDoc = new ArrayList<SupportiOrigDoc>();
		if ((StringUtils.isNotBlank(supportoProt))) {
			StringSplitterServer supportoProtST = new StringSplitterServer(supportoProt, ";");
			while (supportoProtST.hasMoreElements()) {
				SupportiOrigDoc supportoOrigDoc = new SupportiOrigDoc();
				supportoOrigDoc.setCodSupporto(supportoProtST.nextToken());
				listaSupportiOrigDoc.add(supportoOrigDoc);
			} 
			scCriteriAvanzati.setSupportiOrigDoc(listaSupportiOrigDoc);
		}

		// su tutti i filtri che sono variabili semplici (es. DataAperturaDa e DataAperturaA) il filtro caricato da DB dovrebbe essere settato
		// a meno che il filtro corrispondente nella GUI non sia impostato, nel qual caso predomina quello e sovrascrive il filtro da DB

		if (tsAperturaFascicoloDa != null) {
			scCriteriAvanzati.setDataAperturaDa(tsAperturaFascicoloDa);
		}

		if (tsAperturaFascicoloA != null) {
			scCriteriAvanzati.setDataAperturaA(tsAperturaFascicoloA);
		}

		if (tsChiusuraFascicoloDa != null) {
			scCriteriAvanzati.setDataChiusuraDa(tsChiusuraFascicoloDa);
		}

		if (tsChiusuraFascicoloA != null) {
			scCriteriAvanzati.setDataChiusuraA(tsChiusuraFascicoloA);
		}

		if (StringUtils.isNotBlank(flgSoloDaLeggere)) {
			scCriteriAvanzati.setSoloDaLeggere(flgSoloDaLeggere);
		}

		if (tsInvioDa != null) {
			scCriteriAvanzati.setDataInvioDa(tsInvioDa);
		}

		if (tsInvioA != null) {
			scCriteriAvanzati.setDataInvioA(tsInvioA);
		}

		if (inviatiA != null) {
			scCriteriAvanzati.setInviatiA(inviatiA);
		}

		if (tsArchiviazioneDa != null) {
			scCriteriAvanzati.setDataArchiviazioneDa(tsArchiviazioneDa);
		}

		if (tsArchiviazioneA != null) {
			scCriteriAvanzati.setDataArchiviazioneA(tsArchiviazioneA);
		}

		if (tsEliminazioneDa != null) {
			scCriteriAvanzati.setDataEliminazioneDa(tsEliminazioneDa);
		}

		if (tsEliminazioneA != null) {
			scCriteriAvanzati.setDataEliminazioneA(tsEliminazioneA);
		}

		if (StringUtils.isNotBlank(sezioneEliminazione)) {
			scCriteriAvanzati.setSezioneEliminazione(sezioneEliminazione);
		}

		if (StringUtils.isNotBlank(soloDocRicevutiViaEmail)) {
			scCriteriAvanzati.setSoloDocRicevutiViaEmail(soloDocRicevutiViaEmail);
		}

		if (StringUtils.isNotBlank(inviatiViaEmail)) {
			scCriteriAvanzati.setInviatiViaEmail(inviatiViaEmail);
		}

		if (StringUtils.isNotBlank(statoPresaInCarico)) {
			scCriteriAvanzati.setStatoPresaInCarico(statoPresaInCarico);
		}
		
		if (StringUtils.isNotBlank(flgAppostoTimbro)) {
			scCriteriAvanzati.setArchiviatoFileTimbrato(flgAppostoTimbro);
		}
		
		if(StringUtils.isNotBlank(attoCircoscrizione)) {
			scCriteriAvanzati.setOrganiDecentratiCompetenti(attoCircoscrizione);
		}
		
		if(StringUtils.isNotBlank(organoCollegialeODG)) {
			scCriteriAvanzati.setSedutaCollegialeOrgano(organoCollegialeODG);
			if(StringUtils.isNotBlank(dataODG)) {
				scCriteriAvanzati.setDtSedutaCollegialeData(new SimpleDateFormat(DATE_ATTR_FORMAT).parse(dataODG));
			}
			scCriteriAvanzati.setSedutaCollegialeEsito(esitoODG);
		}

		if (StringUtils.isNotBlank(statoRichAnnullamento)) {
			List<StatoRichAnnullamentoReg> statiRichiestaAnnullamentoReg = new ArrayList<StatoRichAnnullamentoReg>();
			StringSplitterServer st = new StringSplitterServer(statoRichAnnullamento, ";");
			while (st.hasMoreTokens()) {
				StatoRichAnnullamentoReg statoRichAnnullamentoReg = new StatoRichAnnullamentoReg();
				statoRichAnnullamentoReg.setStato(st.nextToken());
				statiRichiestaAnnullamentoReg.add(statoRichAnnullamentoReg);
			}
			scCriteriAvanzati.setStatiRichiestaAnnullamentoReg(statiRichiestaAnnullamentoReg);
		}

		if (StringUtils.isNotBlank(statoAutorizzazione)) {
			List<StatoRichAnnullamentoReg> statiRichiestaAnnullamentoReg = new ArrayList<StatoRichAnnullamentoReg>();
			StatoRichAnnullamentoReg statoRichAnnullamentoReg = new StatoRichAnnullamentoReg();
			statoRichAnnullamentoReg.setStato(statoAutorizzazione);
			statiRichiestaAnnullamentoReg.add(statoRichAnnullamentoReg);
			scCriteriAvanzati.setStatiRichiestaAnnullamentoReg(statiRichiestaAnnullamentoReg);
		}

		if (tsAssegnazioneDa != null) {
			scCriteriAvanzati.setDataAssegnSmistDa(tsAssegnazioneDa);
		}

		if (tsAssegnazioneA != null) {
			scCriteriAvanzati.setDataAssegnSmistA(tsAssegnazioneA);
		}

		if (assegnatiA != null) {
			scCriteriAvanzati.setAssegnatiA(assegnatiA);
		}

		if (tsInvioNotificaDa != null) {
			scCriteriAvanzati.setDataNotificaDa(tsInvioNotificaDa);
		}

		if (tsInvioNotificaA != null) {
			scCriteriAvanzati.setDataNotificaA(tsInvioNotificaA);
		}

		if (inConoscenzaA != null) {
			scCriteriAvanzati.setInConoscenzaA(inConoscenzaA);
		}

		if (conNotificheAutoA != null) {
			scCriteriAvanzati.setConNotificheAutoA(conNotificheAutoA);
		}

		if (StringUtils.isNotBlank(soloRegAnnullate)) {
			scCriteriAvanzati.setSoloRegAnnullate(soloRegAnnullate);
		}

		if (StringUtils.isNotBlank(tipoProt)) {
			scCriteriAvanzati.setFlagTipoProvDoc(tipoProt);
		}

		if (StringUtils.isNotBlank(restringiSottofascInserti)) {
			scCriteriAvanzati.setRestringiSottofascInserti(restringiSottofascInserti);
		}

		if (StringUtils.isNotBlank(interesseCessato)) {
			scCriteriAvanzati.setInteresseCessato(interesseCessato);
		}

		if (StringUtils.isNotBlank(mittente)) {
			scCriteriAvanzati.setMittenteUD(mittente);
		}

		if (StringUtils.isNotBlank(operMittente)) {
			scCriteriAvanzati.setOperMittenteUD(operMittente);
		}

		if (StringUtils.isNotBlank(idRubricaMitt)) {
			scCriteriAvanzati.setIdMittenteUDInRubrica(idRubricaMitt);
		}

		if (StringUtils.isNotBlank(destinatario)) {
			scCriteriAvanzati.setDestinatarioUD(destinatario);
		}

		if (StringUtils.isNotBlank(operDestinatario)) {
			scCriteriAvanzati.setOperDestinatarioUD(operDestinatario);
		}

		if (StringUtils.isNotBlank(idRubricaDest)) {
			scCriteriAvanzati.setIdDestinatarioUDInRubrica(idRubricaDest);
		}
		
		if (StringUtils.isNotBlank(esibente)) {
			scCriteriAvanzati.setEsibenteUD(esibente);
		}
		
		if (StringUtils.isNotBlank(operEsibente)) {
			scCriteriAvanzati.setOperEsibenteUD(operEsibente);
		}
		
		if (StringUtils.isNotBlank(idRubricaEsib)) {
			scCriteriAvanzati.setIdEsibenteUDInRubrica(idRubricaEsib);
		}

		if (StringUtils.isNotBlank(oggetto)) {
			scCriteriAvanzati.setOggettoUD(oggetto);
		}

		if (StringUtils.isNotBlank(nomeFascicolo)) {
			scCriteriAvanzati.setNomeFolder(nomeFascicolo);
		}

		if (StringUtils.isNotBlank(fileAssociati)) {
			scCriteriAvanzati.setPresenzaFile(fileAssociati);
		}

		if (StringUtils.isNotBlank(mezzoTrasmissioneFilter)) {
			List<MezziTrasmissioneFilterBean> listMezzoTrasmissione = new ArrayList<MezziTrasmissioneFilterBean>();
			StringSplitterServer st = new StringSplitterServer(mezzoTrasmissioneFilter, ";");
			while (st.hasMoreTokens()) {
				MezziTrasmissioneFilterBean lMezziTrasmissioneFilter = new MezziTrasmissioneFilterBean();
				String chiave = st.nextToken();
				lMezziTrasmissioneFilter.setIdMezzoTrasmissione(chiave);
				listMezzoTrasmissione.add(lMezziTrasmissioneFilter);
			}
			scCriteriAvanzati.setMezziTrasmissione(listMezzoTrasmissione);
		}

		if (uoProponente != null) {
			List<UoProponente> listaUoProponente = new ArrayList<UoProponente>();
			for (DestInvioNotifica item : uoProponente) {
				UoProponente lUoProponente = new UoProponente();
				lUoProponente.setIdUo(item.getId());
				lUoProponente.setFlgIncludiSottoUo(item.getFlgIncludiSottoUO() == Flag.SETTED ? Flag.SETTED : Flag.NOT_SETTED);
				listaUoProponente.add(lUoProponente);
			}
			scCriteriAvanzati.setUoProponente(listaUoProponente);
		}
		
		// Filtro UO competente atto (solo per ADSP)
		if (uoCompetente != null) {
			List<UoCompetente> listaUoCompetente = new ArrayList<UoCompetente>();
			for (DestInvioNotifica item : uoCompetente) {
				UoCompetente lUoCompetente = new UoCompetente();
				lUoCompetente.setIdUo(item.getId());
				lUoCompetente.setFlgIncludiSottoUo(item.getFlgIncludiSottoUO() == Flag.SETTED ? Flag.SETTED : Flag.NOT_SETTED);
				listaUoCompetente.add(lUoCompetente);
			}
			scCriteriAvanzati.setUoCompetente(listaUoCompetente);
		}
		
		
		
		if (uoRegistrazione != null) { 
			List<UoRegistrazione> listaUoRegistrazione = new ArrayList<UoRegistrazione>();
			for (DestInvioNotifica item : uoRegistrazione) {
				UoRegistrazione lUoRegistrazione = new UoRegistrazione();
				lUoRegistrazione.setIdUo(item.getId());
				lUoRegistrazione.setFlgIncludiSottoUo(item.getFlgIncludiSottoUO() == Flag.SETTED ? Flag.SETTED : Flag.NOT_SETTED);
				listaUoRegistrazione.add(lUoRegistrazione);
			}
			scCriteriAvanzati.setUoRegistrazione(listaUoRegistrazione);
		}
		
		if (uoApertura != null) { 
			List<UoApertura> listaUoApertura = new ArrayList<UoApertura>();
			for (DestInvioNotifica item : uoApertura) {
				UoApertura lUoApertura = new UoApertura();
				lUoApertura.setIdUo(item.getId());
				lUoApertura.setFlgIncludiSottoUo(item.getFlgIncludiSottoUO() == Flag.SETTED ? Flag.SETTED : Flag.NOT_SETTED);
				listaUoApertura.add(lUoApertura);
			}
			scCriteriAvanzati.setUoApertura(listaUoApertura);
		}

		if (StringUtils.isNotBlank(utentiAvvioAtto)) {
			List<Utente> listaUtentiAvvioAtto = new ArrayList<Utente>();
			StringSplitterServer st = new StringSplitterServer(utentiAvvioAtto, ";");
			while (st.hasMoreTokens()) {
				Utente lUtente = new Utente();
				lUtente.setIdUser(st.nextToken());
				listaUtentiAvvioAtto.add(lUtente);
			}
			scCriteriAvanzati.setUtentiAvvioAtto(listaUtentiAvvioAtto);
		}

		if (StringUtils.isNotBlank(utentiAdozioneAtto)) {
			List<Utente> listaUtentiAdozioneAtto = new ArrayList<Utente>();
			StringSplitterServer st = new StringSplitterServer(utentiAdozioneAtto, ";");
			while (st.hasMoreTokens()) {
				Utente lUtente = new Utente();
				lUtente.setIdUser(st.nextToken());
				listaUtentiAdozioneAtto.add(lUtente);
			}
			scCriteriAvanzati.setUtentiAdozioneAtto(listaUtentiAdozioneAtto);
		}

		if (StringUtils.isNotBlank(noteUd)) {
			scCriteriAvanzati.setNoteUd(noteUd);
		}

		if (StringUtils.isNotBlank(statiDoc)) {
			List<Stato> listaStatiDoc = new ArrayList<Stato>();
			StringSplitterServer st = new StringSplitterServer(statiDoc, ";");
			while (st.hasMoreTokens()) {
				Stato lStato = new Stato();
				lStato.setCodice(st.nextToken());
				listaStatiDoc.add(lStato);
			}
			scCriteriAvanzati.setStatiDettDoc(listaStatiDoc);
		}
		
		if (StringUtils.isNotBlank(statiFolder)) {
			List<Stato> listaStatiFolder = new ArrayList<Stato>();
			StringSplitterServer st = new StringSplitterServer(statiFolder, ";");
			while (st.hasMoreTokens()) {
				Stato lStato = new Stato();
				lStato.setCodice(st.nextToken());
				listaStatiFolder.add(lStato);
			}
			scCriteriAvanzati.setStatiDettFolder(listaStatiFolder);
		}

		if (dataFirmaAttoDa != null) {
			scCriteriAvanzati.setDataFirmaAttoDa(dataFirmaAttoDa);
		}

		if (dataFirmaAttoA != null) {
			scCriteriAvanzati.setDataFirmaAttoA(dataFirmaAttoA);
		}
		
		if (presaInCaricoDal != null) {
			scCriteriAvanzati.setPresaInCaricoDal(presaInCaricoDal);
		}
		
		if (presaInCaricoAl != null) {
			scCriteriAvanzati.setPresaInCaricoAl(presaInCaricoAl);
		}

		if (StringUtils.isNotBlank(tipoDoc)) {
			scCriteriAvanzati.setIdDocType(tipoDoc);
		}

		if (StringUtils.isNotBlank(tipoFolder)) {
			scCriteriAvanzati.setIdFolderType(tipoFolder);
		}

		if (StringUtils.isNotBlank(nroProtRicevuto)) {
			scCriteriAvanzati.setNroProtRicevuto(nroProtRicevuto);
		}

		if (dataProtRicevutoDa != null) {
			scCriteriAvanzati.setDataProtRicevutoDa(dataProtRicevutoDa);
		}

		if (statoTrasmissioneEmail != null) {
			scCriteriAvanzati.setStatoTrasmissioneEmail(statoTrasmissioneEmail);
		}

		if (StringUtils.isNotBlank(nroRichiestaStampaExpDa)) {
			scCriteriAvanzati.setNroRichiestaStampaExpDa(nroRichiestaStampaExpDa);
		}

		if (StringUtils.isNotBlank(nroRichiestaStampaExpA)) {
			scCriteriAvanzati.setNroRichiestaStampaExpA(nroRichiestaStampaExpA);
		}

		if (tsRichiestaStampaExpDa != null) {
			scCriteriAvanzati.setTsRichiestaStampaExpDa(tsRichiestaStampaExpDa);
		}

		if (tsRichiestaStampaExpA != null) {
			scCriteriAvanzati.setTsRichiestaStampaExpA(tsRichiestaStampaExpA);
		}

		if (capoFilaFasc != null) {
			scCriteriAvanzati.setCapoFilaFasc(capoFilaFasc);
		}
		if (protocolloCapofila != null) {
			scCriteriAvanzati.setProtocolloCapofila(protocolloCapofila);
		}
		if (docCollegatiProt != null) {
			scCriteriAvanzati.setProtocolloCollegato(docCollegatiProt);
		}		
		if (idIndirizzoInVario != null) {
			scCriteriAvanzati.setIdIndirizzoInVario(idIndirizzoInVario);
		}
		if (indirizzo != null) {
			scCriteriAvanzati.setIndirizzo(indirizzo);
		}
		
		if (dtRaccomandataAl != null) {
			scCriteriAvanzati.setDtRaccomandataAl(dtRaccomandataAl);
		}

		if (dtRaccomandataDal != null) {
			scCriteriAvanzati.setDtRaccomandataDal(dtRaccomandataDal);
		}
	
		if (StringUtils.isNotBlank(nroRaccomandata)) {
			scCriteriAvanzati.setNroRaccomandata(nroRaccomandata);
		}
		
		if (StringUtils.isNotBlank(idClassificazione)) {
			scCriteriAvanzati.setIdClassificazione(idClassificazione);
		}
		if (StringUtils.isNotBlank(livelliClassificazione)) {
			scCriteriAvanzati.setLivelliClassificazione(livelliClassificazione);
		}
		if (StringUtils.isNotBlank(flgIncludiSottoClassifiche)) {
			scCriteriAvanzati.setFlgIncludiSottoClassifiche(flgIncludiSottoClassifiche);
		}
		if (StringUtils.isNotBlank(annoFascicoloDa)) {
			scCriteriAvanzati.setAnnoFascicoloDa(annoFascicoloDa);
		}
		if (StringUtils.isNotBlank(annoFascicoloA)) {
			scCriteriAvanzati.setAnnoFascicoloA(annoFascicoloA);
		}
		if (StringUtils.isNotBlank(nroFascicoloDa)) {
			scCriteriAvanzati.setNroFascicoloDa(nroFascicoloDa);
		}
		if (StringUtils.isNotBlank(nroFascicoloA)) {
			scCriteriAvanzati.setNroFascicoloA(nroFascicoloA);
		}
		if (StringUtils.isNotBlank(nroSottoFascicoloDa)) {
			scCriteriAvanzati.setNroSottoFascicoloDa(nroSottoFascicoloDa);
		}
		if (StringUtils.isNotBlank(nroSottoFascicoloA)) {
			scCriteriAvanzati.setNroSottoFascicoloA(nroSottoFascicoloA);
		}
		if (StringUtils.isNotBlank(codiceFascicolo)) {
			scCriteriAvanzati.setCodiceFascicolo(codiceFascicolo);
		}
		if (StringUtils.isNotBlank(attoAutAnnullamento)) {
			scCriteriAvanzati.setEstremiAttoAutAnnullamento(attoAutAnnullamento);
		}
		
		if (dtStesuraDal != null) {
			scCriteriAvanzati.setDtStesuraDal(dtStesuraDal);
		}
	
		if (dtStesuraAl != null) {
			scCriteriAvanzati.setDtStesuraAl(dtStesuraAl);
		}

		if (StringUtils.isNotBlank(nominativoUD)) {
			scCriteriAvanzati.setNominativoUD(nominativoUD);
		}

		if (StringUtils.isNotBlank(operNominativoUD)) {
			scCriteriAvanzati.setOperNominativoUD(operNominativoUD);
		}
		
		if (StringUtils.isNotBlank(tipiNominativoUD)) {
			scCriteriAvanzati.setTipiNominativoUD(tipiNominativoUD);
		}
		
		if (StringUtils.isNotBlank(centroDiCosto)) {
			scCriteriAvanzati.setCentroDiCosto(centroDiCosto);
		}
		
		if (dataScadenzaDa != null) {
			scCriteriAvanzati.setDataScadenzaDa(dataScadenzaDa);
		}
	
		if (dataScadenzaA != null) {
			scCriteriAvanzati.setDataScadenzaA(dataScadenzaA);
		}
		
		if (cfNominativoCollegato != null) {
			scCriteriAvanzati.setCfNominativoCollegato(cfNominativoCollegato);
		}
		
		if (flgRiservatezza != null) {
			scCriteriAvanzati.setFlgRiservatezza(flgRiservatezza);
		}
	
		// Preso in carico da
		if (StringUtils.isNotBlank(presoInCaricoDa)) {
			List<Utente> listaUtentiPresoInCaricoDa = new ArrayList<Utente>();
			StringSplitterServer st = new StringSplitterServer(presoInCaricoDa, ";");
			while (st.hasMoreTokens()) {
				Utente lUtente = new Utente();
				lUtente.setIdUser(st.nextToken());
				listaUtentiPresoInCaricoDa.add(lUtente);
			}
			scCriteriAvanzati.setUtentiPresoInCarico(listaUtentiPresoInCaricoDa);
		}
		
		// Presa visione effettuata da
		if (StringUtils.isNotBlank(presaVisioneEffettuataDa)) {
			List<Utente> listaUtentiPresaVisioneEffettuataDa = new ArrayList<Utente>();
			StringSplitterServer st = new StringSplitterServer(presaVisioneEffettuataDa, ";");
			while (st.hasMoreTokens()) {
				Utente lUtente = new Utente();
				lUtente.setIdUser(st.nextToken());
				listaUtentiPresaVisioneEffettuataDa.add(lUtente);
			}
			scCriteriAvanzati.setUtentiPresaVisioneEffettuata(listaUtentiPresaVisioneEffettuataDa);
		}
		
		// Con file firmati
		if (StringUtils.isNotBlank(flgConFileFirmati)) {
			scCriteriAvanzati.setFlgConFileFirmati(flgConFileFirmati);
		}
		
		// Con file di formato
		if (StringUtils.isNotBlank(formatiElettronici)) {
			List<FormatiFile> listaFormatiElettronici = new ArrayList<FormatiFile>();
			StringSplitterServer st = new StringSplitterServer(formatiElettronici, ";");
			while (st.hasMoreTokens()) {
				FormatiFile lFormatiFile = new FormatiFile();
				lFormatiFile.setIdFormato(st.nextToken());
				listaFormatiElettronici.add(lFormatiFile);
			}
			scCriteriAvanzati.setFormatiElettronici(listaFormatiElettronici);
		}
		
		
		// Passato dallo smistamento
		if (StringUtils.isNotBlank(flgPassaggioDaSmistamento)) {
			scCriteriAvanzati.setFlgPassaggioDaSmistamento(flgPassaggioDaSmistamento);
		}
		
		// Data scansione (DA)
		if (dtScansioneMassivaAl != null) {
			scCriteriAvanzati.setDtScansioneAl(dtScansioneMassivaAl);
		}

		// Data scansione (A) 
		if (dtScansioneMassivaDal != null) {
			scCriteriAvanzati.setDtScansioneDal(dtScansioneMassivaDal);
		}
		
		// Ricerca in archivio pregresso		
		if (StringUtils.isNotBlank(getExtraparams().get("flgArchivioPregresso"))) {
			scCriteriAvanzati.setFlgArchivioPregresso(getExtraparams().get("flgArchivioPregresso"));
		}
		
		// N° immagine 
		if ( (StringUtils.isNotBlank(nroImmagineScansioneMassivaDa)) || 
			 (StringUtils.isNotBlank(nroImmagineScansioneMassivaA))
		){
			Registrazione registrazione = new Registrazione();
			registrazione.setNumeroDa(nroImmagineScansioneMassivaDa);
			registrazione.setNumeroA(nroImmagineScansioneMassivaA);
			listaRegistrazioni.add(registrazione);
		}
		
		// Presenza opere (solo ADSP) 
		if (StringUtils.isNotBlank(presenzaOpere)) {
			scCriteriAvanzati.setPresenzaOpere(presenzaOpere);
		}
		
		// Sotto tipologia atto (solo ADSP)
		if (StringUtils.isNotBlank(sottoTipologiaAtto)) {
			scCriteriAvanzati.setSottoTipologiaAtto(sottoTipologiaAtto);
		}
		
		// Stato trasferimento a Bloomfleet (solo per A2A)
		if (StringUtils.isNotBlank(statiTrasfBloomfleet)) {
			scCriteriAvanzati.setStatiTrasfBloomfleet(statiTrasfBloomfleet);
		}
		
		// Regola reg. automatica (solo per A2A)
		if (StringUtils.isNotBlank(regoleRegistrazioneAutomaticaEmail)) {
			scCriteriAvanzati.setRegoleRegistrazioneAutomaticaEmail(regoleRegistrazioneAutomaticaEmail);
		}
					
		// RdA e atti collegati
		if (StringUtils.isNotBlank(rdAeAttiCollegati)) {
			scCriteriAvanzati.setRdAeAttiCollegati(rdAeAttiCollegati);
		}
		
		// Documenti classificati/fascicolati
		if (StringUtils.isNotBlank(statoClassFascDocumenti)) {
			scCriteriAvanzati.setStatoClassFascDocumenti(statoClassFascDocumenti);
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		advancedFilters = lXmlUtilitySerializer.bindXml(scCriteriAvanzati);

		List<CriteriPersonalizzati> listCustomFiltersSkipNullValues = new ArrayList<CriteriPersonalizzati>();
		for (int i = 0; i < listCustomFilters.size(); i++) {
			if (listCustomFilters.get(i) != null) {
				listCustomFiltersSkipNullValues.add(listCustomFilters.get(i));
			}
		}
		customFilters = lXmlUtilitySerializer.bindXmlList(listCustomFiltersSkipNullValues);

		// if(parametri != null) {
		// try {
		// HashMap <String, String> parameters = new HashMap<String, String>();
		// StringTokenizer st = new StringTokenizer(parametri, "|*|");
		// if(st.countTokens() > 0 && st.countTokens()%2 == 0) {
		// while(st.hasMoreTokens()) {
		// String key = st.nextToken();
		// String value = st.nextToken();
		// parameters.put(key, value);
		// }
		// }
		// idFolder = parameters.get("CercaInFolderIO") != null ? Integer.parseInt(parameters.get("CercaInFolderIO")) : null;
		// if(flgUdFolder == null) {
		// flgUdFolder = parameters.get("FlgUDFolderIO");
		// }
		// } catch (Exception e) {
		// }
		// }

		if (idFolder != null) {
			// Se sto cercando nelle NEWS
			if (idFolder.equals(new Long(-9))) {
				colsToReturn += ",60,61,62";
			}
			// Se sto cercando tra gli ELIMINATI
			else if (idFolder.equals(new Long(-99999))) {
				colsToReturn += ",36,37";
			}
			// Se sto cercando tra gli INVIATI
			else if (idFolder.equals(new Long(-9999)) || idFolder.equals(new Long(-99991))) {
				colsToReturn += ",35,38";
			}
		}

		// ottavio - AURIGA-357 : le colonne "Inviato a me" (209), "Ricevuto il" (60), "Dati invio" (62)  devono essere richieste alla Find se ci_nodo inizia con D.2A o F.2A o DF.2A o D.2CCo F.2CC o DF.2CC 
		if (idNode != null) {
			if (idNode.startsWith("D.2A")  ||
				idNode.startsWith("F.2A")  ||
				idNode.startsWith("DF.2A") ||
				idNode.startsWith("D.2CC") ||
				idNode.startsWith("F.2CC") ||
				idNode.startsWith("DF.2CC") 
			   ){
				 colsToReturn += ",209,60,62";
				}
		}
				
		// ottavio - AURIGA-357 : le colonne "Notificato a me" (209), "Notifica del" (60) e "Dati notifica" (62) devono essere richieste alla Find se ci_nodo inizia con D.2NA o F.2NA o DF.2NA
		if (idNode != null) {
			if (idNode.startsWith("D.2NA")  ||
				idNode.startsWith("F.2NA")  ||
				idNode.startsWith("DF.2NA")
			   ){
				 colsToReturn += ",209,60,62";
				}
		}
						
		// ottavio - AURIGA-357 : la colonna "Priorita' invio" (61) deve essere richiesta alla Find se ci_nodo inizia con D.2A o F.2A o DF.2A o D.2CC o F.2CC o DF.2CC o D.2NA o F.2NA o DF.2NA 
		if (idNode != null) {
			if (idNode.startsWith("D.2A")   ||    
				idNode.startsWith("F.2A")   ||
				idNode.startsWith("DF.2A")  ||
				idNode.startsWith("D.2CC")  ||
				idNode.startsWith("F.2CC")  ||
				idNode.startsWith("DF.2CC") ||     
				idNode.startsWith("D.2NA")  ||
				idNode.startsWith("F.2NA")  ||
				idNode.startsWith("DF.2NA")
			   ){
				 colsToReturn += ",61";
				}
		}

		// ottavio - AURIGA-357 : le colonne "Data presa in carico" (263) deve essere richiesta alla Find se il ci_nodo inizia con D.2A o F.2A o DF.2A e NON contiene la stringa ".DP"  
		if (idNode != null) {
			if ( (idNode.startsWith("D.2A")  || 
				  idNode.startsWith("F.2A")  || 
				  idNode.startsWith("DF.2A")
				 ) 
					&& !idNode.contains(".DP")    
				){
				  colsToReturn += ",263";
				 }
		}
				
		// ottavio - AURIGA-357 : la colonna "Dati completi per protocollazione" (297)  deve apparire nella sezione con ci_nodo che inizia con D.2A.DAFIRM o D.BOZZE
		if (idNode != null) {
			if ( (idNode.startsWith("D.2A.DAFIRM")  || 
				  idNode.startsWith("D.BOZZE") 
				 ) 
				){
				  colsToReturn += ",297";
				 }
		}
		
		// ottavio - AURIGA-389 : la colonna "Messaggio di invio" deve apparire in tutte e solo le sezioni con ci_nodo che inizia con D.2A o F.2A o DF.2A o D.2CC o F.2CC o DF.2CC o D.2NA o F.2NA o DF.2NA
		if (idNode != null) {
			if (idNode.startsWith("D.2A")   ||    
				idNode.startsWith("F.2A")   ||
				idNode.startsWith("DF.2A")  ||
				idNode.startsWith("D.2CC")  ||
				idNode.startsWith("F.2CC")  ||
				idNode.startsWith("DF.2CC") ||     
				idNode.startsWith("D.2NA")  ||
				idNode.startsWith("F.2NA")  ||
				idNode.startsWith("DF.2NA")
			   ){
				 colsToReturn += ",299";
				}
		}			
		
		// ottavio - AURIGA-517 : la colonna "Presente documentazione contratti" deve apparire in tutte e solo le sezioni con ci_nodo = D.2A.DP.R
		if (idNode != null) {
			if (idNode.equalsIgnoreCase("D.2A.DP.R")){
				 colsToReturn += ",308";
				}
		}			
		
		
		/**
		 * Commentato controllo poichè la colonna 217 se non valorizzata impedisce la creazione del menù contestuale
		 * di Fascicoli/Folder senza percorsoNome e/o nroFascicolo
		 */
		//if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_FOLDER_CUSTOM")) {
			colsToReturn += ",217";
		//}

		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVATO_MODULO_ATTI")) {
			colsToReturn += ",14,15,52,FLG_ATTO_PREGRESSO,226";
		}
		
		if (ParametriDBUtil.getParametroDB(getSession(), "CLIENTE") != null ) {
			if ("CMTO".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "CLIENTE"))) {
				colsToReturn += ",286,287,288,289,290,291,292,293,294,295";	
			}
			
			if ("A2A".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "CLIENTE"))) {
				colsToReturn += ",307";	
			}			
			
		}

		// Se e' stato selezionato il nodo "Immagini non associate ai protocolli", mostro solo le colonne :  
		// "Anno"                      (colonna 284) 
		// "N° immagine"               (colonna 285)
		// "Data e ora ricezione"      (colonna 202)
		// "Data e ora scansione"      (colonna 301)
		// "Dati barcode"              (colonna 302)
		// "Sede scansione"            (colonna 303)
		// "Timbro prot. riconosciuto" (colonna 304)
		// "N° prot. riconosciuto"     (colonna 305)
		// "Motivo scarto"             (colonna 306)
		
		if (idNode != null && idNode.startsWith("D.SCANNOASS")) {			
			colsToReturn = "1,2,6,20,33,39,201,284,285,202,301,302,303,304,305,306";
		}
		
		
		// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.BOZZE.DAPROT, aggiungo le colonne :
		// Data di invio al protocollo (35)
		// Protocollo assegnatario (42)
		if (idNode != null) {
			if (idNode.equalsIgnoreCase("D.BOZZE.DAPROT")){
				 colsToReturn += ",35,42";
				}
		}
		
				
		FindRepositoryObjectBean lFindRepositoryObjectBean = new FindRepositoryObjectBean();

		lFindRepositoryObjectBean.setFiltroFullText(filtroFullText);
		lFindRepositoryObjectBean.setCheckAttributes(checkAttributes);
		lFindRepositoryObjectBean.setFormatoEstremiReg(formatoEstremiReg);
		lFindRepositoryObjectBean.setSearchAllTerms(searchAllTerms);
		lFindRepositoryObjectBean.setFlgUdFolder(flgUdFolder);
		lFindRepositoryObjectBean.setIdFolderSearchIn(idFolder);
		if (!setNullFlgSubfolderSearch) {
			lFindRepositoryObjectBean.setFlgSubfoderSearchIn(includiSottoCartelle);
		} else {
			lFindRepositoryObjectBean.setFlgSubfoderSearchIn(null);
		}

		lFindRepositoryObjectBean.setAdvancedFilters(advancedFilters);
		lFindRepositoryObjectBean.setCustomFilters(customFilters);
				
		//TODO PAGINAZIONE START
		lFindRepositoryObjectBean.setColsToReturn(colsToReturn);
		if(bachSize != null && bachSize.intValue() > 0) {
			lFindRepositoryObjectBean.setFlgSenzaPaginazione(null);
			lFindRepositoryObjectBean.setNumRighePagina(bachSize); // numRighePagina == Bachsizeio
			lFindRepositoryObjectBean.setNumPagina(nroPagina); // se è null significa che è la ricerca iniziale e devo dare il warning dell'ordinamento lato server
		} else {
			lFindRepositoryObjectBean.setFlgSenzaPaginazione(1);
			lFindRepositoryObjectBean.setNumRighePagina(overflowLimit); // numRighePagina == Bachsizeio
			if (forNroRecordTot) {
				lFindRepositoryObjectBean.setOverflowlimitin(new Integer(-1));
				lFindRepositoryObjectBean.setColsToReturn("1");
			} else {
				lFindRepositoryObjectBean.setOverflowlimitin(overflowLimit);
			}
		}
		//TODO PAGINAZIONE END
		
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FORZA_ORDINAMENTO_REP_DOC_OVERFLOW")) {
			lFindRepositoryObjectBean.setColsOrderBy("201");
			lFindRepositoryObjectBean.setFlgDescOrderBy("1");
		} else { 
			// Se in lista è attivo il limite di overflow o la paginazione, allora devo fare l'ordinamento lato server
			if((bachSize != null && bachSize.intValue() > 0) || (overflowLimit != null && overflowLimit.intValue() > 0)) {
				String[] colAndFlgDescOrderBy = getColAndFlgDescOrderBy(PostaElettronicaBean.class, null);
				colsOrderBy = colAndFlgDescOrderBy[0];
				flgDescOrderBy = colAndFlgDescOrderBy[1];
				lFindRepositoryObjectBean.setColsOrderBy(colsOrderBy);
				lFindRepositoryObjectBean.setFlgDescOrderBy(flgDescOrderBy);
			}
		}

		lFindRepositoryObjectBean.setOnline(online);
		lFindRepositoryObjectBean.setPercorsoRicerca(percorsoRicerca);
		lFindRepositoryObjectBean.setFlagTipoRicerca(flgTipoRicerca);
		lFindRepositoryObjectBean.setFinalita(finalita);
		lFindRepositoryObjectBean.setUserIdLavoro(loginBean.getIdUserLavoro());

		return lFindRepositoryObjectBean;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {

		AdvancedCriteria criteria = bean.getCriteria();

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(false, criteria, loginBean);

		lFindRepositoryObjectBean.setOverflowlimitin(-2);

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findrepositoryobject(getLocale(), loginBean, lFindRepositoryObjectBean).getList();
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		String errorMessageOut = null;

		if (resFinder.size() > 5) {
			errorMessageOut = (String) resFinder.get(5);
		}

		if (errorMessageOut != null && !"".equals(errorMessageOut)) {
			addMessage(errorMessageOut, "", MessageType.WARNING);
		}

		// imposto l'id del job creato
		Integer jobId = Integer.valueOf((String) resFinder.get(6));
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), ArchivioXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(), ArchivioXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Richiesta di esportazione su file registrata con Nr. " + jobId.toString()
					+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return null;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean filterBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = filterBean.getCriteria();

		// creo il bean per la ricerca, ma inizializzo anche le variabili che mi servono per determinare se effettivamente posso eseguire il recupero dei dati
		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(true, criteria, loginBean);

		lFindRepositoryObjectBean.setColsToReturn("1");
		lFindRepositoryObjectBean.setOverflowlimitin(-1);

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findrepositoryobject(getLocale(), loginBean, lFindRepositoryObjectBean).getList();
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		String numTotRecOut = (String) resFinder.get(1);

		String errorMessageOut = null;

		if (resFinder.size() > 5) {
			errorMessageOut = (String) resFinder.get(5);
		}

		if (errorMessageOut != null && !"".equals(errorMessageOut)) {
			addMessage(errorMessageOut, "", MessageType.WARNING);
		}

		NroRecordTotBean retValue = new NroRecordTotBean();
		retValue.setNroRecordTot(Integer.valueOf(numTotRecOut));
		return retValue;
	}

	public ArchivioBean recuperaDatiProtocolloPregresso(ArchivioBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
//		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkUtilityFindud_outputBean input = new DmpkUtilityFindud_outputBean();
		input.setCodidconnectiontokenin(token);
//		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodcategoriaregin(bean.getTipoProtocolloPregresso());
		if(bean.getTipoProtocolloPregresso() != null && bean.getTipoProtocolloPregresso().equalsIgnoreCase("PP")) {
			input.setSiglaregin(bean.getSiglaProtocolloPregresso());
		}
		input.setNumregin(StringUtils.isNotBlank(bean.getNumProtocolloPregresso()) ? new BigDecimal(bean.getNumProtocolloPregresso()) : null);
		input.setAnnoregin(StringUtils.isNotBlank(bean.getAnnoProtocolloPregresso()) ? Integer.parseInt(bean.getAnnoProtocolloPregresso()) : null);
		
		DmpkUtilityFindud_output store = new DmpkUtilityFindud_output();
		StoreResultBean<DmpkUtilityFindud_outputBean> output = store.execute(getLocale(), loginBean, input);
		if (output.isInError()) {
			if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.WARNING);				
			}
		}		
		
		if(output.getResultBean() != null) {
			bean.setIdUdCapofila(output.getResultBean().getIdudout() != null ? String.valueOf(output.getResultBean().getIdudout().longValue()) : null);
			bean.setIdDocPrimario(output.getResultBean().getIddocprimarioout() != null ? String.valueOf(output.getResultBean().getIddocprimarioout().longValue()) : null);
			if(StringUtils.isNotBlank(output.getResultBean().getDatiudout())) {
				DatiUdOutBean lDatiUdOutBean = new DatiUdOutBean();		        
				lDatiUdOutBean = new XmlUtilityDeserializer().unbindXml(output.getResultBean().getDatiudout(), DatiUdOutBean.class);      	       
		        if(lDatiUdOutBean != null) {
		        	bean.setListaEsibentiPraticaPregressa(lDatiUdOutBean.getListaMittentiEsibenti());
		        	bean.setOggetto(lDatiUdOutBean.getOggetto());
		        	bean.setFlgUdDaDataEntryScansioni(lDatiUdOutBean.getFlgUdDaDataEntryScansioni() == Flag.SETTED);
		        	bean.setFlgNextDocAllegato(lDatiUdOutBean.getFlgNextDocAllegato() == Flag.SETTED);		        	
		        }			
			}
		}
		
		return bean;
	}
	
	/**
	 * @author FEBUONO
	 * 
	 * @param bean
	 * @throws Exception 
	 */
	
	public OperazioneMassivaArchivioBean aggiornaStatoDocumenti(OperazioneMassivaArchivioBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		
		for (int i = 0; i < bean.getListaRecord().size(); ++i) {
			ArchivioBean udFolder = (ArchivioBean) bean.getListaRecord().get(i);
			
			if ("U".equals(udFolder.getFlgUdFolder())) {
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setFlgtipotargetin("U");
				input.setIduddocin(new BigDecimal(udFolder.getIdUdFolder()));
				
				CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
				if("NESSUN_TIPO".equalsIgnoreCase(getExtraparams().get("stato"))) {
					creaModDocumentoInBean.setCodStato("NULL");
					creaModDocumentoInBean.setCodStatoDett("NULL");	
				} else {
					creaModDocumentoInBean.setCodStatoDett(getExtraparams().get("stato"));	
				}
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));
				
				DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
				if (output.getDefaultMessage() != null) {					
					errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
				}
			} else if ("F".equals(udFolder.getFlgUdFolder())) {
				DmpkCoreUpdfolderBean input = new DmpkCoreUpdfolderBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setIdfolderin(new BigDecimal(udFolder.getIdUdFolder()));
				
				XmlFascicoloIn xmlFascicoloIn = new XmlFascicoloIn();
				if("NESSUN_TIPO".equalsIgnoreCase(getExtraparams().get("stato"))) {
					xmlFascicoloIn.setCodStato("NULL");
					xmlFascicoloIn.setCodStatoDett("NULL");	
				} else {
					xmlFascicoloIn.setCodStato(getExtraparams().get("stato"));	
					xmlFascicoloIn.setCodStatoDett(getExtraparams().get("stato"));	
				}
				List<String> lListNomiVariabiliToSet = new ArrayList<String>();
				lListNomiVariabiliToSet.add("#CodStato");
				lListNomiVariabiliToSet.add("#CodStatoDett");
				input.setAttributixmlin(lXmlUtilitySerializer.bindXmlParziale(xmlFascicoloIn,lListNomiVariabiliToSet));
				
				DmpkCoreUpdfolder dmpkCoreUpdfolder = new DmpkCoreUpdfolder();
				StoreResultBean<DmpkCoreUpdfolderBean> output = dmpkCoreUpdfolder.execute(this.getLocale(), loginBean, input);
				if (output.getDefaultMessage() != null) {					
					errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
				}
			}
		}
		
		if(errorMessages != null && !errorMessages.isEmpty()) {
			bean.setErrorMessages(errorMessages);
		}
		return bean;
	}
	
	
	public OperazioneMassivaArchivioBean apposizioneCommenti(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		for (int i = 0; i < bean.getListaRecord().size(); ++i) {
			ArchivioBean udFolder = (ArchivioBean) bean.getListaRecord().get(i);
			if ("U".equals(udFolder.getFlgUdFolder())) {
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setFlgtipotargetin("U");
				input.setIduddocin(new BigDecimal(udFolder.getIdUdFolder()));
				NoteUdBean lNoteUdBean = new NoteUdBean();
				lNoteUdBean.setNoteUd(udFolder.getNote());
				if (StringUtils.isNotBlank(udFolder.getCausaleAggNote())) {
					ArrayList<CausaleAggNoteBean> listaCausaleAggNoteUd = new ArrayList<CausaleAggNoteBean>();
					CausaleAggNoteBean lCausaleAggNoteUdBean = new CausaleAggNoteBean();
					lCausaleAggNoteUdBean.setCausaleAggNote(udFolder.getCausaleAggNote());
					listaCausaleAggNoteUd.add(lCausaleAggNoteUdBean);
					lNoteUdBean.setListaCausaleAggNote(listaCausaleAggNoteUd);
				}
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXmlCompleta((Object) lNoteUdBean));
				DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
				if (output.getDefaultMessage() != null) {					
					errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
				}
			} else {
				DmpkCoreUpdfolderBean input = new DmpkCoreUpdfolderBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setIdfolderin(new BigDecimal(udFolder.getIdUdFolder()));
				NoteFolderBean lNoteFolderBean = new NoteFolderBean();
				lNoteFolderBean.setNote(udFolder.getNote());
				if (StringUtils.isNotBlank(udFolder.getCausaleAggNote())) {
					ArrayList<CausaleAggNoteBean> listaCausaleAggNoteFolder = new ArrayList<CausaleAggNoteBean>();
					CausaleAggNoteBean lCausaleAggNoteFolderBean = new CausaleAggNoteBean();
					lCausaleAggNoteFolderBean.setCausaleAggNote(udFolder.getCausaleAggNote());
					listaCausaleAggNoteFolder.add(lCausaleAggNoteFolderBean);
					lNoteFolderBean.setListaCausaleAggNote(listaCausaleAggNoteFolder);
				}
				input.setAttributixmlin(lXmlUtilitySerializer.bindXmlCompleta((Object) lNoteFolderBean));
				DmpkCoreUpdfolder dmpkCoreUpdfolder = new DmpkCoreUpdfolder();
				StoreResultBean<DmpkCoreUpdfolderBean> output = dmpkCoreUpdfolder.execute(this.getLocale(), loginBean, (DmpkCoreUpdfolderBean) input);
				if (output.getDefaultMessage() != null) {
					errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
				}
			}
		}
		if(errorMessages != null && !errorMessages.isEmpty()) {
			bean.setErrorMessages(errorMessages);
		}
		return bean;
	}

	
	public OperazioneMassivaArchivioBean segnaComeVisionato(OperazioneMassivaArchivioBean bean) throws Exception {
			
	    if (bean.getListaRecord()!=null && bean.getListaRecord().size()>0){
	    	
	    	HashMap<String, String> errorMessages = new HashMap<String, String>();
	    	
	    	AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
			String token = loginBean.getToken();
			String idUserLavoro = loginBean.getIdUserLavoro();
			
			DmpkCoreSetvisionatoBean input = new DmpkCoreSetvisionatoBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setFlgancheperutentein(StringUtils.isNotBlank(bean.getFlgAnchePerUtente()) ? new Integer(bean.getFlgAnchePerUtente()) : null);

			if(bean.getListaUoSelezionate() != null && bean.getListaUoSelezionate().size() > 0) {
				if(bean.getListaUoSelezionate().size() == 1) {
					input.setIduoin(StringUtils.isNotBlank(bean.getListaUoSelezionate().get(0).getKey()) ? new BigDecimal(bean.getListaUoSelezionate().get(0).getKey()) : null);
				} else {
					input.setIduoin(new BigDecimal("-1")); // se ho selezionato tutte le UO passo -1
				}
			}
			
			// Prendo le NOTE dal primo record
			String note = "";
			if (bean.getListaRecord().get(0)!=null){
				note = bean.getListaRecord().get(0).getNote();	
			}
			
			// Estrae la lista degli id delle UNITA' DOCUMENTARIE (U) e la lista degli id dei FOLDER (F)
			List<SimpleValueBean> listaIdUd     = new ArrayList<SimpleValueBean>();	
			List<SimpleValueBean> listaIdFolder = new ArrayList<SimpleValueBean>();
			estraiIdUdIdFolder(bean, listaIdUd, listaIdFolder);
			
			// Se ci sono unita' documentarie chiamo la store e li setto come visionati
			if (listaIdUd !=null && listaIdUd.size()>0){
				// -- (ogglig) Tipo di oggetti da segnare come visionati: U = Unità documentarie
				input.setFlgtpobjin("U");  
				
				// -- Lista XML Standard con una sola colonna con gli ID delle UD o folder da marcare come visionati	
				input.setIdobjlistin(new XmlUtilitySerializer().bindXmlList(listaIdUd));
							  
				// -- Annotazioni alla presa visione
				input.setNotein(note);
								
				DmpkCoreSetvisionato servizio = new DmpkCoreSetvisionato();
				StoreResultBean<DmpkCoreSetvisionatoBean> output = servizio.execute(this.getLocale(), loginBean, input);
				
				if(output.isInError()) {
					throw new StoreException(output);
				}
				
				// Leggo gli esiti
				String xmlEsitiOut = output.getResultBean().getEsitiout();
				if (xmlEsitiOut != null) {
					List<EsistiVisionatoXmlBean> esiti = XmlListaUtility.recuperaLista(xmlEsitiOut, EsistiVisionatoXmlBean.class);
					if (esiti!=null && esiti.size()>0){
						for (EsistiVisionatoXmlBean esitoBean : esiti) {
							// Se e' KO
							if (StringUtils.isNotBlank(esitoBean.getEsito()) && esitoBean.getEsito().equalsIgnoreCase("KO")){
								errorMessages.put(esitoBean.getIdUdFolder(), esitoBean.getMessaggioErrore());
							}								   
						}
					}
				}
			}
			
			// Se ci sono folder chiamo la store e li setto come visionati
			if (listaIdFolder !=null && listaIdFolder.size()>0){
				// -- (ogglig) Tipo di oggetti da segnare come visionati: F = Folder/aggregati
				input.setFlgtpobjin("F");  
				
				// -- Lista XML Standard con una sola colonna con gli ID delle UD o folder da marcare come visionati							
				input.setIdobjlistin(new XmlUtilitySerializer().bindXmlList(listaIdFolder));
										  
				// -- Annotazioni alla presa visione
				input.setNotein(note);
											
				DmpkCoreSetvisionato servizio = new DmpkCoreSetvisionato();
				StoreResultBean<DmpkCoreSetvisionatoBean> output = servizio.execute(this.getLocale(), loginBean, input);
				
				if(output.isInError()) {
					throw new StoreException(output);
				}
				
				// Leggo gli esiti
				String xmlEsitiOut = output.getResultBean().getEsitiout();
				if (xmlEsitiOut != null) {
					List<EsistiVisionatoXmlBean> esiti = XmlListaUtility.recuperaLista(xmlEsitiOut, EsistiVisionatoXmlBean.class);
					if (esiti!=null && esiti.size()>0){
						for (EsistiVisionatoXmlBean esitoBean : esiti) {
							// Se e' KO
							if (StringUtils.isNotBlank(esitoBean.getEsito()) && esitoBean.getEsito().equalsIgnoreCase("KO")){
								errorMessages.put(esitoBean.getIdUdFolder(), esitoBean.getMessaggioErrore());
							}								   
						}
					}
				}			
			}
			
			if(errorMessages != null && !errorMessages.isEmpty()) {
				bean.setErrorMessages(errorMessages);
			}
	    }	    
	    
		return bean;
	}
	
	public OperazioneMassivaArchivioBean segnaComeArchiviato(OperazioneMassivaArchivioBean bean) throws Exception {
		
		if (bean.getListaRecord()!=null && bean.getListaRecord().size()>0){
			
			HashMap<String, String> errorMessages = new HashMap<String, String>();
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
			String token = loginBean.getToken();
			String idUserLavoro = loginBean.getIdUserLavoro();
			
			DmpkCoreSetvisionatoBean input = new DmpkCoreSetvisionatoBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setFlgancheperutentein(StringUtils.isNotBlank(bean.getFlgAnchePerUtente()) ? new Integer(bean.getFlgAnchePerUtente()) : null);
			input.setTipoazionein("A");
			input.setCinodoscrivaniain(bean.getIdNodo());
			
			if(bean.getListaUoSelezionate() != null && bean.getListaUoSelezionate().size() > 0) {
				if(bean.getListaUoSelezionate().size() == 1) {
					input.setIduoin(StringUtils.isNotBlank(bean.getListaUoSelezionate().get(0).getKey()) ? new BigDecimal(bean.getListaUoSelezionate().get(0).getKey()) : null);
				} else {
					input.setIduoin(new BigDecimal("-1")); // se ho selezionato tutte le UO passo -1
				}
			}
			
			// Prendo le NOTE dal primo record
			String note = "";
			if (bean.getListaRecord().get(0)!=null){
				note = bean.getListaRecord().get(0).getNote();	
			}
			
			// Estrae la lista degli id delle UNITA' DOCUMENTARIE (U) e la lista degli id dei FOLDER (F)
			List<SimpleValueBean> listaIdUd     = new ArrayList<SimpleValueBean>();	
			List<SimpleValueBean> listaIdFolder = new ArrayList<SimpleValueBean>();
			estraiIdUdIdFolder(bean, listaIdUd, listaIdFolder);
			
			// Se ci sono unita' documentarie chiamo la store e li setto come visionati
			if (listaIdUd !=null && listaIdUd.size()>0){
				// -- (ogglig) Tipo di oggetti da segnare come visionati: U = Unità documentarie
				input.setFlgtpobjin("U");  
				
				// -- Lista XML Standard con una sola colonna con gli ID delle UD o folder da marcare come visionati	
				input.setIdobjlistin(new XmlUtilitySerializer().bindXmlList(listaIdUd));
				
				// -- Annotazioni alla presa visione
				input.setNotein(note);
				
				DmpkCoreSetvisionato servizio = new DmpkCoreSetvisionato();
//				StoreResultBean<DmpkCoreSetvisionatoBean> output = new StoreResultBean<>();
				StoreResultBean<DmpkCoreSetvisionatoBean> output = servizio.execute(this.getLocale(), loginBean, input);
				
				if(output.isInError()) {
					throw new StoreException(output);
				}
				
				// Leggo gli esiti
				String xmlEsitiOut = output.getResultBean().getEsitiout();
				if (xmlEsitiOut != null) {
					List<EsistiVisionatoXmlBean> esiti = XmlListaUtility.recuperaLista(xmlEsitiOut, EsistiVisionatoXmlBean.class);
					if (esiti!=null && esiti.size()>0){
						for (EsistiVisionatoXmlBean esitoBean : esiti) {
							// Se e' KO
							if (StringUtils.isNotBlank(esitoBean.getEsito()) && esitoBean.getEsito().equalsIgnoreCase("KO")){
								errorMessages.put(esitoBean.getIdUdFolder(), esitoBean.getMessaggioErrore());
							}								   
						}
					}
				}
			}
			
			// Se ci sono folder chiamo la store e li setto come visionati
			if (listaIdFolder !=null && listaIdFolder.size()>0){
				// -- (ogglig) Tipo di oggetti da segnare come visionati: F = Folder/aggregati
				input.setFlgtpobjin("F");  
				
				// -- Lista XML Standard con una sola colonna con gli ID delle UD o folder da marcare come visionati							
				input.setIdobjlistin(new XmlUtilitySerializer().bindXmlList(listaIdFolder));
				
				// -- Annotazioni alla presa visione
				input.setNotein(note);
				
				DmpkCoreSetvisionato servizio = new DmpkCoreSetvisionato();
//				StoreResultBean<DmpkCoreSetvisionatoBean> output = new StoreResultBean<>();
				StoreResultBean<DmpkCoreSetvisionatoBean> output = servizio.execute(this.getLocale(), loginBean, input);
				
				if(output.isInError()) {
					throw new StoreException(output);
				}
				
				// Leggo gli esiti
				String xmlEsitiOut = output.getResultBean().getEsitiout();
				if (xmlEsitiOut != null) {
					List<EsistiVisionatoXmlBean> esiti = XmlListaUtility.recuperaLista(xmlEsitiOut, EsistiVisionatoXmlBean.class);
					if (esiti!=null && esiti.size()>0){
						for (EsistiVisionatoXmlBean esitoBean : esiti) {
							// Se e' KO
							if (StringUtils.isNotBlank(esitoBean.getEsito()) && esitoBean.getEsito().equalsIgnoreCase("KO")){
								errorMessages.put(esitoBean.getIdUdFolder(), esitoBean.getMessaggioErrore());
							}								   
						}
					}
				}			
			}
			
			if(errorMessages != null && !errorMessages.isEmpty()) {
				bean.setErrorMessages(errorMessages);
			}
		}	    
		
		return bean;
	}


	private void estraiIdUdIdFolder(OperazioneMassivaArchivioBean bean, List<SimpleValueBean> listaIdUdOut, List<SimpleValueBean> listaIdFolderOut ) throws Exception {	
	
		for (int i = 0; i < bean.getListaRecord().size(); ++i) {
			ArchivioBean item = (ArchivioBean) bean.getListaRecord().get(i);
			if (StringUtils.isNotBlank(item.getFlgUdFolder())){
				if (item.getFlgUdFolder().equalsIgnoreCase("U")){
					SimpleValueBean lSimpleValueBean = new SimpleValueBean();
					lSimpleValueBean.setValue(item.getIdUdFolder());
					listaIdUdOut.add(lSimpleValueBean);					
				}
				if (item.getFlgUdFolder().equalsIgnoreCase("F")){
					SimpleValueBean lSimpleValueBean = new SimpleValueBean();
					lSimpleValueBean.setValue(item.getIdUdFolder());
					listaIdFolderOut.add(lSimpleValueBean);					
				}
			}
		}
		
	}
	
	
	public DownloadDocsZipBean generateDocsZip(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
		String limiteMaxZipErrorMessage = MessageUtil.getValue(getLocale().getLanguage(), null, "alert_archivio_list_limiteMaxZipError");

		String errorMessage = getExtraparams().get("messageError");
		String operazione = getExtraparams().get("operazione");
		String posizioneTimbro = getExtraparams().get("posizioneTimbro");
		String rotazioneTimbro = getExtraparams().get("rotazioneTimbro");
		String tipoPagina = getExtraparams().get("tipoPagina");
		
		OpzioniTimbroBean opzioniTimbroScelteUtente = new OpzioniTimbroBean();
		opzioniTimbroScelteUtente.setPosizioneTimbro(posizioneTimbro);
		opzioniTimbroScelteUtente.setRotazioneTimbro(rotazioneTimbro);
		opzioniTimbroScelteUtente.setTipoPagina(tipoPagina);

		String maxSize = ParametriDBUtil.getParametroDB(getSession(), "MAX_SIZE_ZIP");

		long MAX_SIZE = (maxSize != null && maxSize.length() > 0 ? Long.decode(maxSize) : 104857600);
		long lengthZip = 0;

		DownloadDocsZipBean retValue = new DownloadDocsZipBean();
		
		String tempPath = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");
		
		Date date = new Date();
	    long timeMilli = date.getTime();

	    String prefissoNomeFileZip = AurigaAbstractDataSourceUtilities.getPrefissoNomeFileZip (operazione, true);
		File zipFile = new File(tempPath + prefissoNomeFileZip +  timeMilli + ".zip");

		Map<String, Integer> zipFileNames = new HashMap<String, Integer>();
		List<FileScaricoZipBean> listaFileDaScaricare = new ArrayList<>();

		for (int i = 0; i < bean.getListaRecord().size(); i++) {
			String idUd = bean.getListaRecord().get(i).getIdUdFolder();
			String segnatura = getSegnature(bean.getListaRecord().get(i).getSegnatura());

			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
			lRecuperaDocumentoInBean.setIdUd(new BigDecimal(idUd));
			RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();

			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);

			if (StringUtils.isNotEmpty(lRecuperaDocumentoOutBean.getDefaultMessage())) {
				String message = lRecuperaDocumentoOutBean.getDefaultMessage();

				throw new StoreException(errorMessage != null ? errorMessage : message);
			}

			DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
			ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
			ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);

			String attachmentFileName = null;
			String attachmentFileNameOmissis = null;
			
			
			if (documento.getNomeFilePrimario() != null && documento.getFilePrimarioOmissis().getNomeFile() == null) {
				// il file primario ha solo versione integrale
				if (documento.getInfoFile() != null) {
					lengthZip += documento.getInfoFile().getBytes();

					if (lengthZip > MAX_SIZE) {
						throw new StoreException(limiteMaxZipErrorMessage);
					}

					attachmentFileName = segnatura + " - File primario - " + documento.getNomeFilePrimario();
					attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);

					FileScaricoZipBean filePrimarioDaScaricare = new FileScaricoZipBean();
					filePrimarioDaScaricare.setUriFile(documento.getUriFilePrimario());
					filePrimarioDaScaricare.setNomeFile(attachmentFileName);
					filePrimarioDaScaricare.setFirmato(documento.getInfoFile().isFirmato());
					filePrimarioDaScaricare.setIdUd(new BigDecimal(idUd));
					filePrimarioDaScaricare.setMimeType(documento.getInfoFile().getMimetype());
					filePrimarioDaScaricare.setIdDoc(documento.getIdDocPrimario());
					
					listaFileDaScaricare.add(filePrimarioDaScaricare);
				}
			} else if (documento.getNomeFilePrimario() != null && documento.getFilePrimarioOmissis().getNomeFile() != null) {
				// il file primario ha versione integrale e versione con omissis
				if (documento.getInfoFile() != null) {
					lengthZip += documento.getInfoFile().getBytes();

					if (lengthZip > MAX_SIZE) {
						throw new StoreException(limiteMaxZipErrorMessage);
					}

					attachmentFileName = segnatura + " - File primario (vers. integrale) - " + documento.getNomeFilePrimario();
					attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);
					
					FileScaricoZipBean filePrimarioDaScaricare = new FileScaricoZipBean();
					filePrimarioDaScaricare.setUriFile(documento.getUriFilePrimario());
					filePrimarioDaScaricare.setNomeFile(attachmentFileName);
					filePrimarioDaScaricare.setFirmato(documento.getInfoFile().isFirmato());
					filePrimarioDaScaricare.setIdUd(new BigDecimal(idUd));
					filePrimarioDaScaricare.setMimeType(documento.getInfoFile().getMimetype());
					filePrimarioDaScaricare.setIdDoc(documento.getIdDocPrimario());
					
					listaFileDaScaricare.add(filePrimarioDaScaricare);

				}

				if (documento.getFilePrimarioOmissis().getInfoFile() != null) {
					lengthZip += documento.getFilePrimarioOmissis().getInfoFile().getBytes();

					if (lengthZip > MAX_SIZE) {
						throw new StoreException(limiteMaxZipErrorMessage);
					}

					attachmentFileNameOmissis = segnatura + " - File primario (vers. con omissis) - " + documento.getFilePrimarioOmissis().getNomeFile();
					attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);
					
					FileScaricoZipBean filePrimarioDaScaricare = new FileScaricoZipBean();
					filePrimarioDaScaricare.setUriFile(documento.getFilePrimarioOmissis().getUriFile());
					filePrimarioDaScaricare.setNomeFile(attachmentFileNameOmissis);
					filePrimarioDaScaricare.setFirmato(documento.getFilePrimarioOmissis().getInfoFile().isFirmato());
					filePrimarioDaScaricare.setIdUd(new BigDecimal(idUd));
					filePrimarioDaScaricare.setMimeType(documento.getFilePrimarioOmissis().getInfoFile().getMimetype());
					filePrimarioDaScaricare.setIdDoc(new BigDecimal(documento.getFilePrimarioOmissis().getIdDoc()));
					
					listaFileDaScaricare.add(filePrimarioDaScaricare);
				
				}
			} else if (documento.getNomeFilePrimario() == null && documento.getFilePrimarioOmissis().getNomeFile() != null) {
				// il file primario ha solo versione con omissis
				if (documento.getFilePrimarioOmissis().getInfoFile() != null) {
					lengthZip += documento.getFilePrimarioOmissis().getInfoFile().getBytes();

					if (lengthZip > MAX_SIZE) {
						throw new StoreException(limiteMaxZipErrorMessage);
					}

					attachmentFileNameOmissis = segnatura + " - File primario (vers. con omissis) - " + documento.getFilePrimarioOmissis().getNomeFile();
					attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);

					FileScaricoZipBean filePrimarioDaScaricare = new FileScaricoZipBean();
					filePrimarioDaScaricare.setUriFile(documento.getFilePrimarioOmissis().getUriFile());
					filePrimarioDaScaricare.setNomeFile(attachmentFileNameOmissis);
					filePrimarioDaScaricare.setFirmato(documento.getFilePrimarioOmissis().getInfoFile().isFirmato());
					filePrimarioDaScaricare.setIdUd(new BigDecimal(idUd));
					filePrimarioDaScaricare.setMimeType(documento.getFilePrimarioOmissis().getInfoFile().getMimetype());
					filePrimarioDaScaricare.setIdDoc(new BigDecimal(documento.getFilePrimarioOmissis().getIdDoc()));
					
					listaFileDaScaricare.add(filePrimarioDaScaricare);				
					
				}
			}

			if (documento.getListaAllegati() != null) {
				for (AllegatoProtocolloBean allegatoUdBean : documento.getListaAllegati()) {

					if (allegatoUdBean.getNomeFileAllegato() != null && allegatoUdBean.getNomeFileOmissis() != null) {
						// il file allegato ha versione integrale e versione con omissis
						if (allegatoUdBean.getInfoFileOmissis() != null ) {
							lengthZip += allegatoUdBean.getInfoFileOmissis().getBytes();

							if (lengthZip > MAX_SIZE) {
								throw new StoreException(limiteMaxZipErrorMessage);
							}

							attachmentFileNameOmissis = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. con omissis) - " + allegatoUdBean.getNomeFileOmissis();
							attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);
						
							FileScaricoZipBean fileAllegatoDaScaricare = new FileScaricoZipBean();
							fileAllegatoDaScaricare.setNomeFile(attachmentFileNameOmissis);
							fileAllegatoDaScaricare.setUriFile(allegatoUdBean.getUriFileOmissis());
							fileAllegatoDaScaricare.setFirmato(allegatoUdBean.getInfoFileOmissis().isFirmato());
							fileAllegatoDaScaricare.setIdUd(new BigDecimal(idUd));
							fileAllegatoDaScaricare.setMimeType(allegatoUdBean.getInfoFileOmissis().getMimetype());
							fileAllegatoDaScaricare.setIdDoc(allegatoUdBean.getIdDocOmissis());
							
							listaFileDaScaricare.add(fileAllegatoDaScaricare);
						
						}

						if (allegatoUdBean.getInfoFile() != null) {
							lengthZip += allegatoUdBean.getInfoFile().getBytes();

							if (lengthZip > MAX_SIZE) {
								throw new StoreException(limiteMaxZipErrorMessage);
							}

							attachmentFileName = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. integrale) - " + allegatoUdBean.getNomeFileAllegato();
							attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);
							
							FileScaricoZipBean fileAllegatoDaScaricare = new FileScaricoZipBean();
							fileAllegatoDaScaricare.setNomeFile(attachmentFileName);
							fileAllegatoDaScaricare.setUriFile(allegatoUdBean.getUriFileAllegato());
							fileAllegatoDaScaricare.setFirmato(allegatoUdBean.getInfoFile().isFirmato());
							fileAllegatoDaScaricare.setIdUd(new BigDecimal(idUd));
							fileAllegatoDaScaricare.setMimeType(allegatoUdBean.getInfoFile().getMimetype());
							fileAllegatoDaScaricare.setIdDoc(allegatoUdBean.getIdDocAllegato());
							
							listaFileDaScaricare.add(fileAllegatoDaScaricare);
							
						
						}
					} else if (allegatoUdBean.getNomeFileAllegato() == null && allegatoUdBean.getNomeFileOmissis() != null) {
						// il file allegato ha solo versione con omissis
						if (allegatoUdBean.getInfoFileOmissis() != null ) {
							lengthZip += allegatoUdBean.getInfoFileOmissis().getBytes();

							if (lengthZip > MAX_SIZE) {
								throw new StoreException(limiteMaxZipErrorMessage);
							}
							attachmentFileNameOmissis = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. con omissis) - " + allegatoUdBean.getNomeFileOmissis();
							attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);
							
							FileScaricoZipBean fileAllegatoDaScaricare = new FileScaricoZipBean();
							fileAllegatoDaScaricare.setNomeFile(attachmentFileNameOmissis);
							fileAllegatoDaScaricare.setUriFile(allegatoUdBean.getUriFileOmissis());
							fileAllegatoDaScaricare.setFirmato(allegatoUdBean.getInfoFileOmissis().isFirmato());
							fileAllegatoDaScaricare.setIdUd(new BigDecimal(idUd));
							fileAllegatoDaScaricare.setMimeType(allegatoUdBean.getInfoFileOmissis().getMimetype());
							fileAllegatoDaScaricare.setIdDoc(allegatoUdBean.getIdDocOmissis());
							
							listaFileDaScaricare.add(fileAllegatoDaScaricare);
						}
					} else if (allegatoUdBean.getNomeFileAllegato() != null && allegatoUdBean.getNomeFileOmissis() == null) {
						// il file allegato ha solo versione integrale
						if (allegatoUdBean.getInfoFile() != null) {
							lengthZip += allegatoUdBean.getInfoFile().getBytes();

							if (lengthZip > MAX_SIZE) {
								throw new StoreException(limiteMaxZipErrorMessage);
							}
							attachmentFileName = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" - " + allegatoUdBean.getNomeFileAllegato();
							attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);

							FileScaricoZipBean fileAllegatoDaScaricare = new FileScaricoZipBean();
							fileAllegatoDaScaricare.setNomeFile(attachmentFileName);
							fileAllegatoDaScaricare.setUriFile(allegatoUdBean.getUriFileAllegato());
							fileAllegatoDaScaricare.setFirmato(allegatoUdBean.getInfoFile().isFirmato());
							fileAllegatoDaScaricare.setIdUd(new BigDecimal(idUd));
							fileAllegatoDaScaricare.setMimeType(allegatoUdBean.getInfoFile().getMimetype());
							fileAllegatoDaScaricare.setIdDoc(allegatoUdBean.getIdDocAllegato());
							
							listaFileDaScaricare.add(fileAllegatoDaScaricare);
						}
					}
				}
			}
		}
				
		Integer warning = AurigaAbstractDataSourceUtilities.addFileToZip(zipFileNames, zipFile, listaFileDaScaricare, operazione, opzioniTimbroScelteUtente, getSession());

		try {
			if (!zipFileNames.isEmpty()) {
				String zipUri = StorageImplementation.getStorage().store(zipFile);

				retValue.setStorageZipRemoteUri(zipUri);
				retValue.setZipName(zipFile.getName());
				MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
				infoFile.setBytes(zipFile.length());
				retValue.setInfoFile(infoFile);
				if(warning!=null) {
					if(warning == OK_CON_WARNING_TIMBRO) {
						retValue.setWarningTimbro("Per alcuni file NON è stato possibile apporre il timbro; sono presenti nello zip nella forma originale");
					}
					else if(warning == OK_CON_WARNING_SBUSTA) {
						retValue.setWarningSbusta("Per alcuni file NON è stato possibile estrarre il file contenuto della busta firma p7m; sono presenti nello zip nella forma originale di p7m");
					}
				}
			} else {
				retValue.setMessage("Le unità documentarie selezionate non hanno nessun file");
			}

		} finally {

			// una volta salvato in storage lo posso eliminare localmente
			FileDeleteStrategy.FORCE.delete(zipFile);

		}

		return retValue;

	}

	
	public DownloadDocsZipBean generateDocsPubblicatiZip(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
		String limiteMaxZipErrorMessage = MessageUtil.getValue(getLocale().getLanguage(), null, "alert_archivio_list_limiteMaxZipError");

		String errorMessage = getExtraparams().get("messageError");

		String maxSize = ParametriDBUtil.getParametroDB(getSession(), "MAX_SIZE_ZIP");

		long MAX_SIZE = (maxSize != null && maxSize.length() > 0 ? Long.decode(maxSize) : 104857600);
		long lengthZip = 0;

		DownloadDocsZipBean retValue = new DownloadDocsZipBean();

		String tempPath = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");
		
		Date date = new Date();
	    long timeMilli = date.getTime();

		File zipFile = new File(tempPath + "FileSelezioneDocumenti_" +  timeMilli + ".zip");

		Map<String, Integer> zipFileNames = new HashMap<String, Integer>();

		for (int i = 0; i < bean.getListaRecord().size(); i++) {
			String idUd = bean.getListaRecord().get(i).getIdUdFolder();
			String segnatura = getSegnature(bean.getListaRecord().get(i).getSegnatura());

			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
			lRecuperaDocumentoInBean.setIdUd(new BigDecimal(idUd));
			RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();

			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);

			if (StringUtils.isNotEmpty(lRecuperaDocumentoOutBean.getDefaultMessage())) {
				String message = lRecuperaDocumentoOutBean.getDefaultMessage();

				throw new StoreException(errorMessage != null ? errorMessage : message);
			}

			DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
			
			ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
			ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);

			String attachmentFileName = null;
			String attachmentFileNameOmissis = null;

			// Prendo solo i file PUBBLICATI 
			if (documento.getIsFilePrimarioPubblicato()!=null && documento.getIsFilePrimarioPubblicato()){

				if (documento.getNomeFilePrimario() != null /*&& documento.getFilePrimarioOmissis().getNomeFile() == null*/) {
					// il file primario ha solo versione integrale
					if (documento.getInfoFile() != null) {
						lengthZip += documento.getInfoFile().getBytes();

						if (lengthZip > MAX_SIZE) {
							throw new StoreException(limiteMaxZipErrorMessage);
						}

						attachmentFileName = segnatura + " - File primario - " + documento.getNomeFilePrimario();
						attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);

						File currentAttachmentPrimario = File.createTempFile("temp",  attachmentFileName);

						addFileZip( documento.getInfoFile(), documento.getUriFilePrimario(), attachmentFileName, currentAttachmentPrimario, zipFile);
					}
				} 
				
				/*COMMENTATO PER SEGNALAZIONE DA AOSTA: NELLO ZIP NON VOLEVANO LA VERSIONE OMISSIS MA SOLO IL FILE PRIMARIO CHE (AD AOSTA) E' QUELLO CHE VA IN PUBBLICAZIONE
				else if (documento.getNomeFilePrimario() != null && documento.getFilePrimarioOmissis().getNomeFile() != null) {
					// il file allegato ha versione integrale e versione con omissis
					if (documento.getInfoFile() != null) {
						lengthZip += documento.getInfoFile().getBytes();

						if (lengthZip > MAX_SIZE) {
							throw new StoreException(limiteMaxZipErrorMessage);
						}

						attachmentFileName = segnatura + " - File primario - " + documento.getNomeFilePrimario();
						attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);

						File currentAttachmentPrimarioIntegrale = new File(tempPath + attachmentFileName);
						addFileZip(tempPath, documento.getInfoFile(), documento.getUriFilePrimario(), attachmentFileName, currentAttachmentPrimarioIntegrale, zipFile);
					}
					
					if (documento.getFilePrimarioOmissis().getInfoFile() != null) {
						lengthZip += documento.getFilePrimarioOmissis().getInfoFile().getBytes();

						if (lengthZip > MAX_SIZE) {
							throw new StoreException(limiteMaxZipErrorMessage);
						}
						
						attachmentFileNameOmissis = segnatura + " - File primario (vers. con omissis) - " + documento.getFilePrimarioOmissis().getNomeFile();
						attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);

						File currentAttachmentPrimarioOmissis = new File (tempPath + attachmentFileNameOmissis);

						addFileZip(tempPath, documento.getFilePrimarioOmissis().getInfoFile(),  documento.getFilePrimarioOmissis().getUriFile(), attachmentFileNameOmissis, currentAttachmentPrimarioOmissis, zipFile);
					}
				
				} 
				/* COMMENTATO PERCHE IN TEORIA IN FASE DI PUBBLICAZIONE NON AVRO MAI L'OMISSIS SENZA PRIMARIO CHE AD AOSTA E' QUELLO CHE VA IN PUBBLICAZIONE
				else if (documento.getNomeFilePrimario() == null && documento.getFilePrimarioOmissis().getNomeFile() != null) {
					// il file primario ha solo versione con omissis
					if (documento.getFilePrimarioOmissis().getInfoFile() != null) {
						lengthZip += documento.getFilePrimarioOmissis().getInfoFile().getBytes();

						if (lengthZip > MAX_SIZE) {
							throw new StoreException(limiteMaxZipErrorMessage);
						}
						
						attachmentFileNameOmissis = segnatura + " - File primario (vers. con omissis) - " + documento.getFilePrimarioOmissis().getNomeFile();
						attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);
	
						File currentAttachmentPrimarioOmissis = new File (tempPath + attachmentFileNameOmissis);
	
						addFileZip(tempPath, documento.getFilePrimarioOmissis().getInfoFile(),  documento.getFilePrimarioOmissis().getUriFile(), attachmentFileNameOmissis, currentAttachmentPrimarioOmissis, zipFile);
					}
				}
				*/
			}
			
			if (documento.getListaAllegati() != null) {
				for (AllegatoProtocolloBean allegatoUdBean : documento.getListaAllegati()) {

					// Prendo solo i file PUBBLICATI
					if(allegatoUdBean.getIsPubblicato() !=null && allegatoUdBean.getIsPubblicato()){

						if (allegatoUdBean.getNomeFileAllegato() != null && allegatoUdBean.getNomeFileOmissis() != null) {
							// il file allegato ha versione integrale e versione con omissis
							if (allegatoUdBean.getInfoFileOmissis() != null ) {
								lengthZip += allegatoUdBean.getInfoFileOmissis().getBytes();

								if (lengthZip > MAX_SIZE) {
									throw new StoreException(limiteMaxZipErrorMessage);
								}

								attachmentFileNameOmissis = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. con omissis) - " + allegatoUdBean.getNomeFileOmissis();
								attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);
								File currentAttachmentOmissis = File.createTempFile("temp", attachmentFileNameOmissis);

								addFileZip(allegatoUdBean.getInfoFileOmissis(), allegatoUdBean.getUriFileOmissis(), attachmentFileNameOmissis, currentAttachmentOmissis, zipFile);
							}

							if (allegatoUdBean.getInfoFile() != null) {
								lengthZip += allegatoUdBean.getInfoFile().getBytes();

								if (lengthZip > MAX_SIZE) {
									throw new StoreException(limiteMaxZipErrorMessage);
								}

								attachmentFileName = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. integrale) - " + allegatoUdBean.getNomeFileAllegato();
								attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);
								File currentAttachment = File.createTempFile("temp", attachmentFileName);

								addFileZip(allegatoUdBean.getInfoFile(), allegatoUdBean.getUriFileAllegato(), attachmentFileName, currentAttachment, zipFile);
							}
						} else if (allegatoUdBean.getNomeFileAllegato() == null && allegatoUdBean.getNomeFileOmissis() != null) {
							// il file allegato ha solo versione con omissis
							if (allegatoUdBean.getInfoFileOmissis() != null ) {
								lengthZip += allegatoUdBean.getInfoFileOmissis().getBytes();

								if (lengthZip > MAX_SIZE) {
									throw new StoreException(limiteMaxZipErrorMessage);
								}
								attachmentFileNameOmissis = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. con omissis) - " + allegatoUdBean.getNomeFileOmissis();
								attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);
								File currentAttachmentOmissis = File.createTempFile("temp", attachmentFileNameOmissis);

								addFileZip(allegatoUdBean.getInfoFileOmissis(), allegatoUdBean.getUriFileOmissis(), attachmentFileNameOmissis, currentAttachmentOmissis, zipFile);
							}
						} else if (allegatoUdBean.getNomeFileAllegato() != null && allegatoUdBean.getNomeFileOmissis() == null) {
							// il file allegato ha solo versione integrale
							if (allegatoUdBean.getInfoFile() != null) {
								lengthZip += allegatoUdBean.getInfoFile().getBytes();

								if (lengthZip > MAX_SIZE) {
									throw new StoreException(limiteMaxZipErrorMessage);
								}
								attachmentFileName = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. integrale) - " + allegatoUdBean.getNomeFileAllegato();
								attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);
								File currentAttachment = File.createTempFile("temp", attachmentFileName);

								addFileZip(allegatoUdBean.getInfoFile(), allegatoUdBean.getUriFileAllegato(), attachmentFileName, currentAttachment, zipFile);
							}
						}
					}
				}
			}
		}

		try {
			if (!zipFileNames.isEmpty()) {
				String zipUri = StorageImplementation.getStorage().store(zipFile);

				retValue.setStorageZipRemoteUri(zipUri);
				retValue.setZipName(zipFile.getName());
				MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
				infoFile.setBytes(zipFile.length());
				retValue.setInfoFile(infoFile);
			} else {
				retValue.setMessage("Le unità documentarie selezionate non hanno nessun file");
			}

		} finally {

			// una volta salvato in storage lo posso eliminare localmente
			FileDeleteStrategy.FORCE.delete(zipFile);

		}

		return retValue;

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

	public String getSegnature(String segnatura) {
		if (segnatura != null) {
			segnatura = segnatura.replace(" ", "_");
			segnatura = segnatura.replace(".", "_");
			segnatura = segnatura.replace("\\", "_");
			segnatura = segnatura.replace("/", "_");
		} else {

			Calendar currentDate = Calendar.getInstance();

			segnatura = Integer.toString(currentDate.get(Calendar.DAY_OF_WEEK)) + "_" + Integer.toString(currentDate.get(Calendar.HOUR))
					+ Integer.toString(currentDate.get(Calendar.MINUTE)) + Integer.toString(currentDate.get(Calendar.SECOND))
					+ Integer.toString(currentDate.get(Calendar.MILLISECOND));
		}

		return segnatura;
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

	public AllegatoProtocolloBean unioneFileFasc(ArchivioBean pInBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		File fileUnione = null;
		try {
			fileUnione = buildUnioneFileFascPdf(pInBean);
		} catch (Exception e) {
			throw new StoreException("Si è verificato un errore durante la generazione del file di risposta: " + e.getMessage());
		}
		if (fileUnione == null) {
			throw new StoreException("Si è verificato un errore durante la generazione del file di risposta");
		}

		String nomeFileUnione = "RispostaCompleta.pdf";
		String uriFileUnione = StorageImplementation.getStorage().store(fileUnione, new String[] {});
		String uriFileUnioneTimbrato = null;

		CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
		lCreaModDocumentoInBean.setTipoDocumento(null);
		lCreaModDocumentoInBean.setNomeDocType(null);
		lCreaModDocumentoInBean.setOggetto("Risposta completa (con allegati integrati)");
		List<FolderCustom> listaFolderCustom = new ArrayList<FolderCustom>();
		FolderCustom folderCustom = new FolderCustom();
		folderCustom.setId(String.valueOf(pInBean.getIdUdFolder()));
		listaFolderCustom.add(folderCustom);
		lCreaModDocumentoInBean.setFolderCustom(listaFolderCustom);

		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		CreaModDocumentoOutBean lCreaModDocumentoOutBean = null;
		MimeTypeFirmaBean lMimeTypeFirmaBean = null;

		boolean fileUnioneDaTimbrare = getExtraparams().get("fileUnioneDaTimbrare") != null ? new Boolean(getExtraparams().get("fileUnioneDaTimbrare")) : false;
		if (fileUnioneDaTimbrare) {

			lCreaModDocumentoOutBean = lGestioneDocumenti.creadocumento(getLocale(), loginBean, lCreaModDocumentoInBean, null, null);

			if (lCreaModDocumentoOutBean.getDefaultMessage() != null) {
				throw new StoreException(lCreaModDocumentoOutBean);
			}

			// timbra
			OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
			lOpzioniTimbroBean.setMimetype("application/pdf");
			lOpzioniTimbroBean.setUri(uriFileUnione);
			lOpzioniTimbroBean.setNomeFile(nomeFileUnione);
			lOpzioniTimbroBean.setIdUd(String.valueOf(lCreaModDocumentoOutBean.getIdUd()));
			lOpzioniTimbroBean.setIdDoc(String.valueOf(lCreaModDocumentoOutBean.getIdDoc()!= null ? lCreaModDocumentoOutBean.getIdDoc() : "" ));
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
				// Aggiungo il file timbrato nella lista dei file da pubblicare
				uriFileUnioneTimbrato = lTimbraResultBean.getUri();
			} else {
				// // La timbratura è fallita, pubblico il file sbustato
				// files.add(StorageImplementation.getStorage().extractFile(uriFileSbustato));
				String errorMessage = "Si è verificato un errore durante la timbratura del file di risposta";
				if (StringUtils.isNotBlank(lTimbraResultBean.getError())) {
					errorMessage += ": " + lTimbraResultBean.getError();
				}
				throw new Exception(errorMessage);
			}

			VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
			lVersionaDocumentoInBean.setIdDocumento(lCreaModDocumentoOutBean.getIdDoc());
			lVersionaDocumentoInBean.setFile(StorageImplementation.getStorage().extractFile(uriFileUnioneTimbrato));

			lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			InfoFileUtility lFileUtility = new InfoFileUtility();
			lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lVersionaDocumentoInBean.getFile().toURI().toString(), nomeFileUnione, false, null);
			lMimeTypeFirmaBean.setFirmato(false);
			lMimeTypeFirmaBean.setFirmaValida(false);
			lMimeTypeFirmaBean.setConvertibile(false);
			lMimeTypeFirmaBean.setDaScansione(false);

			FileInfoBean lFileInfoBean = new FileInfoBean();
			lFileInfoBean.setTipo(TipoFile.PRIMARIO);

			GenericFile lGenericFile = new GenericFile();
			setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
			lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
			lGenericFile.setDisplayFilename(nomeFileUnione);
			lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
			lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
			lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
			lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());

			lFileInfoBean.setAllegatoRiferimento(lGenericFile);

			lVersionaDocumentoInBean.setInfo(lFileInfoBean);

			VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), loginBean, lVersionaDocumentoInBean);

			if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
				mLogger.error("VersionaDocumento: " + lVersionaDocumentoOutBean.getDefaultMessage());
				throw new StoreException(lVersionaDocumentoOutBean);
			}

		} else {

			RebuildedFile lRebuildedFile = new RebuildedFile();
			lRebuildedFile.setIdDocumento(null);
			lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uriFileUnione));

			lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			InfoFileUtility lFileUtility = new InfoFileUtility();
			lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), nomeFileUnione, false, null);
			lMimeTypeFirmaBean.setFirmato(false);
			lMimeTypeFirmaBean.setFirmaValida(false);
			lMimeTypeFirmaBean.setConvertibile(false);
			lMimeTypeFirmaBean.setDaScansione(false);

			FileInfoBean lFileInfoBean = new FileInfoBean();
			lFileInfoBean.setTipo(TipoFile.PRIMARIO);

			GenericFile lGenericFile = new GenericFile();
			setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
			lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
			lGenericFile.setDisplayFilename(nomeFileUnione);
			lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
			lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
			lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());

			lFileInfoBean.setAllegatoRiferimento(lGenericFile);

			lRebuildedFile.setInfo(lFileInfoBean);

			FilePrimarioBean lFilePrimarioBean = new FilePrimarioBean();
			lFilePrimarioBean.setIdDocumento(null);
			lFilePrimarioBean.setIsNewOrChanged(true);
			lFilePrimarioBean.setInfo(lFileInfoBean);
			lFilePrimarioBean.setFile(fileUnione);

			lCreaModDocumentoOutBean = lGestioneDocumenti.creadocumento(getLocale(), loginBean, lCreaModDocumentoInBean, lFilePrimarioBean, null);

			if (lCreaModDocumentoOutBean.getDefaultMessage() != null) {
				throw new StoreException(lCreaModDocumentoOutBean.getDefaultMessage(), lCreaModDocumentoOutBean.getErrorCode(), lCreaModDocumentoOutBean.getErrorContext());
			}
		}

		AllegatoProtocolloBean lRispostaCompletaBean = new AllegatoProtocolloBean();
		lRispostaCompletaBean.setIdUdAppartenenza(String.valueOf(lCreaModDocumentoOutBean.getIdUd()));
		lRispostaCompletaBean.setIdDocAllegato(lCreaModDocumentoOutBean.getIdDoc());
		lRispostaCompletaBean.setNomeFileAllegato(nomeFileUnione);
		lRispostaCompletaBean.setRemoteUri(false);
		lRispostaCompletaBean.setUriFileAllegato(fileUnioneDaTimbrare ? uriFileUnioneTimbrato : uriFileUnione);
		lRispostaCompletaBean.setInfoFile(lMimeTypeFirmaBean);

		return lRispostaCompletaBean;

	}

	private File buildUnioneFileFascPdf(ArchivioBean pInBean) throws Exception {
		if (pInBean.getListaDocumentiIstruttoria() != null) {
			List<InputStream> llFileDaUnireStreams = new ArrayList<InputStream>();
			int i = 0;
			for (AllegatoProtocolloBean lDocIstruttoriaBean : pInBean.getListaDocumentiIstruttoria()) {
				// il primo file è la risposta e lo devo includere
				if (i == 0 || (lDocIstruttoriaBean.getFlgParteDispositivo() != null && lDocIstruttoriaBean.getFlgParteDispositivo())) {
					InputStream lInputStreamPdf = null;
					if (StringUtils.isNotEmpty(lDocIstruttoriaBean.getNomeFileAllegato())
							&& lDocIstruttoriaBean.getNomeFileAllegato().toLowerCase().endsWith(".p7m")) {
						mLogger.debug("Ho un file firmato");
						InputStream lInputStream = sbusta(lDocIstruttoriaBean.getUriFileAllegato(), lDocIstruttoriaBean.getNomeFileAllegato());
						if (lDocIstruttoriaBean.getInfoFile().getMimetype().equals("application/pdf")) {
							mLogger.debug("Il file sbustato è un pdf");
							lInputStreamPdf = lInputStream;
						} else {
							mLogger.debug("Il file sbustato non è un pdf: lo converto");
							mLogger.debug("mimetype: " + lDocIstruttoriaBean.getInfoFile().getMimetype());
							lInputStreamPdf = convertiFileToPdf(lInputStream);
						}
					} else {
						if (lDocIstruttoriaBean.getInfoFile().getMimetype().equals("application/pdf")) {
							mLogger.debug("Il file è un pdf");
							lInputStreamPdf = StorageImplementation.getStorage().extract(lDocIstruttoriaBean.getUriFileAllegato());
						} else {
							mLogger.debug("Il file non è un pdf: lo converto");
							mLogger.debug("mimetype: " + lDocIstruttoriaBean.getInfoFile().getMimetype());
							lInputStreamPdf = convertiFileToPdf(StorageImplementation.getStorage().extract(lDocIstruttoriaBean.getUriFileAllegato()));
						}
					}
					llFileDaUnireStreams.add(lInputStreamPdf);
				}
				i++;
			}
			return unisciPdf(llFileDaUnireStreams);
		}
		return null;
	}

	public File unisciPdf(List<InputStream> lListPdf) throws Exception {
		Document document = new Document();
		// Istanzio una copia nell'output
		File lFile = File.createTempFile("pdf", ".pdf");
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(lFile));
		// Apro per la modifica il nuovo documento
		document.open();
		// Istanzio un nuovo reader che ci servirà per leggere i singoli file
		PdfReader reader;

		// Per ogni file passato
		for (InputStream lInputStream : lListPdf) {
			// Leggo il file
			reader = new PdfReader(lInputStream);
			// Prendo il numero di pagine
			int n = reader.getNumberOfPages();
			// e per ogni pagina
			for (int page = 0; page < n;) {
				copy.addPage(copy.getImportedPage(reader, ++page));
			}
			// con questo metodo faccio il flush del contenuto e libero il rader
			copy.freeReader(reader);
		}
		// Chiudo il documento
		document.close();
		return lFile;
	}

	private InputStream convertiFileToPdf(InputStream lInputStream) throws StoreException {
		try {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().storeStream(lInputStream, new String[] {}))
					.toURI().toString();
			return lInfoFileUtility.converti(fileUrl, "");
		} catch (Exception e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			throw new StoreException("Non è stato possibile convertire il file");
		}
	}

	private InputStream sbusta(String uriFile, String displayFilename) throws IOException, StorageException, StoreException {
		try {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(uriFile).toURI().toString();
			return lInfoFileUtility.sbusta(fileUrl, "");
		} catch (Exception e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			throw new StoreException("Non è stato possibile sbustare il file");
		}
	}

	public boolean isTaskDettFascGen() {
		boolean isTaskDettFascGen = getExtraparams().get("isTaskDettFascGen") != null && "true".equals(getExtraparams().get("isTaskDettFascGen"));
		return isTaskDettFascGen;
	}

	public boolean isTaskDettFasc() {
		boolean isTaskDettFasc = getExtraparams().get("isTaskDettFasc") != null && "true".equals(getExtraparams().get("isTaskDettFasc"));
		return isTaskDettFasc;
	}
	
	/**
	 * ************************************************************************************************************************************************
	 */
	
	private void invioNotificheAssegnazioneInvioCC(ArchivioBean bean, XmlFascicoloIn xmlFascicolo,Boolean isUpdate) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		List<TipoIdBean> listaAssegnatari = getListaAssegnatari(xmlFascicolo);
		List<TipoIdBean> listaInvioCC =	getListaAssegnatariPerConoscenza(xmlFascicolo);
		
		/**
		 * Operazioni per la presenza di utenti assegnatari
		 */
		if(listaAssegnatari != null && !listaAssegnatari.isEmpty()){
			manageAssegnazioni(bean, isUpdate, lAurigaLoginBean, lXmlUtilitySerializer, listaAssegnatari);
		}
		/**
		 * Operazioni per la presenza di utenti per invio in conoscenza
		 */
		if(listaInvioCC != null && !listaInvioCC.isEmpty()){
			manageInvioCC(bean, isUpdate, lAurigaLoginBean, lXmlUtilitySerializer, listaInvioCC);
		}
	}
	
	private void manageAssegnazioni(ArchivioBean bean, Boolean isUpdate, AurigaLoginBean lAurigaLoginBean, XmlUtilitySerializer lXmlUtilitySerializer,
			List<TipoIdBean> listaAssegnatari) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {

		if (StringUtils.isBlank(bean.getFlgUdFolder()) || bean.getFlgUdFolder().equalsIgnoreCase("F")) {
			/**
			 * Assegnazione per conoscenza di un FOLDER, di conseguenza controllo il parametro nella dmt_def_config_param ATTIVA_NOT_EMAIL_ASSEGN_FLD
			 */
			if (attivaNotEmailAssegnFLD(listaAssegnatari)) {
				if (listaAssegnatari != null && !listaAssegnatari.isEmpty()) {
					sendNotificaAssegnazioneInvioCC(bean,lAurigaLoginBean,lXmlUtilitySerializer,listaAssegnatari,"ASSEGNAZIONE",isUpdate);
				} else {
					logger.warn("Non è stata inviata alcuna notifica e-mail assegnazione" + "lista destinatari listaAssegnatari: " + listaAssegnatari.size()
							+ ", verificare parametro ATTIVA_NOT_EMAIL_ASSEGN_FLD");
					//addMessage("Non è stata inviata alcuna notifica e-mail dell'assegnazione", "", MessageType.WARNING);
				}
			}
		} else if (bean.getFlgUdFolder().equalsIgnoreCase("U")) {
			/**
			 * Assegnazione per conoscenza di un DOCUMENTO, di conseguenza controllo il parametro nella dmt_def_config_param ATTIVA_NOT_EMAIL_ASSEGN_UD
			 */
			if (attivaNotEmailAssegnUD(listaAssegnatari)) {
				if (listaAssegnatari != null && !listaAssegnatari.isEmpty()) {
					sendNotificaAssegnazioneInvioCC(bean,lAurigaLoginBean,lXmlUtilitySerializer,listaAssegnatari,"ASSEGNAZIONE",isUpdate);
				} else {
					logger.warn("Non è stata inviata alcuna notifica e-mail assegnazione" + "lista destinatari listaAssegnatari: " + listaAssegnatari.size()
							+ ", verificare parametro ATTIVA_NOT_EMAIL_ASSEGN_UD");
					//addMessage("Non è stata inviata alcuna notifica e-mail dell'assegnazione", "", MessageType.WARNING);
				}
			}	
		}
	}
	
	private void manageInvioCC(ArchivioBean bean, Boolean isUpdate, AurigaLoginBean lAurigaLoginBean, XmlUtilitySerializer lXmlUtilitySerializer,
			List<TipoIdBean> listaAssegnatari) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {
		
		if (StringUtils.isBlank(bean.getFlgUdFolder()) || bean.getFlgUdFolder().equalsIgnoreCase("F")) {
			if (attivaNotEmailInvioCCFLD(listaAssegnatari)) {
				if (listaAssegnatari != null && listaAssegnatari.size() > 0) {
					sendNotificaAssegnazioneInvioCC(bean,lAurigaLoginBean,lXmlUtilitySerializer,listaAssegnatari,"INVIOCC",isUpdate);
				} else {
					logger.warn("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza" + "lista destinatari listaInvioDestCC: "
							+ listaAssegnatari.size() + ", verificare parametro ATTIVA_NOT_EMAIL_INVIO_CC_FLD");
					//addMessage("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza", "", MessageType.WARNING);
				}
			}
		} else if (bean.getFlgUdFolder().equalsIgnoreCase("U")) {
			/**
			 * Invio per conoscenza di un DOCUMENTO, di conseguenza controllo il parametro nella dmt_def_config_param ATTIVA_NOT_EMAIL_ASSEGN_UD
			 */
			if (attivaNotEmailInvioCCUD(listaAssegnatari)) {
				if (listaAssegnatari != null && listaAssegnatari.size() > 0) {
					sendNotificaAssegnazioneInvioCC(bean,lAurigaLoginBean,lXmlUtilitySerializer,listaAssegnatari,"INVIOCC",isUpdate);
				} else {
					logger.warn("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza" + "lista destinatari listaInvioDestCC: "
							+ listaAssegnatari.size() + ", verificare parametro ATTIVA_NOT_EMAIL_INVIO_CC_UD");
					//addMessage("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza", "", MessageType.WARNING);
				}
			}
		}
	}
	
	private void sendNotificaAssegnazioneInvioCC(ArchivioBean bean, AurigaLoginBean lAurigaLoginBean,XmlUtilitySerializer lXmlUtilitySerializer, 
			List<TipoIdBean> listaAssegnatari,String tipoOperazione,Boolean isUpdate) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {
		
		String token = lAurigaLoginBean.getToken();
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro();
		
		DmpkIntMgoEmailPreparaemailnotassinvioccBean lInputBean = new DmpkIntMgoEmailPreparaemailnotassinvioccBean();
		lInputBean.setCodidconnectiontokenin(token);
		lInputBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		if(isUpdate){
			/**
			 * OPERAZIONE IN MODIFICA
			 */
			if("ASSEGNAZIONE".equals(tipoOperazione)){
				lInputBean.setAssinvioccin("assegnazione_competenza");
			}else{
				lInputBean.setAssinvioccin("invio_conoscenza");
			}
		} else {
			/**
			 * OPERAZIONE IN INSERIMENTO
			 */
			if("ASSEGNAZIONE".equals(tipoOperazione)){
				lInputBean.setAssinvioccin("assegnazione_competenza");
			}else{
				lInputBean.setAssinvioccin("invio_conoscenza");
			}
		}

		List<TipoIdBean> listaUdFolder = new ArrayList<TipoIdBean>();
		TipoIdBean udFolder = new TipoIdBean();

		udFolder.setTipo(StringUtils.isNotBlank(bean.getFlgUdFolder()) ? bean.getFlgUdFolder() : "F");
		udFolder.setId(bean.getIdUdFolder());
		listaUdFolder.add(udFolder);
		lInputBean.setUdfolderxmlin(lXmlUtilitySerializer.bindXmlList(listaUdFolder));
		lInputBean.setDestassinvioccxmlin(lXmlUtilitySerializer.bindXmlList(listaAssegnatari));
		lInputBean.setCodmotivoinvioin(null);
		lInputBean.setMotivoinvioin(null);
		lInputBean.setMessaggioinvioin(null);
		lInputBean.setLivelloprioritain(null);
		lInputBean.setTsdecorrassinvioccin(null);

		DmpkIntMgoEmailPreparaemailnotassinviocc preparaemailnotassinviocc = new DmpkIntMgoEmailPreparaemailnotassinviocc();
		StoreResultBean<DmpkIntMgoEmailPreparaemailnotassinvioccBean> lStoreResultBean = preparaemailnotassinviocc.execute(getLocale(), lAurigaLoginBean,
				lInputBean);

		if (lStoreResultBean.isInError()) {
			String message = "Fallito invio notifica e-mail dell'assegnazione";
			if (StringUtils.isNotBlank(lStoreResultBean.getDefaultMessage())) {
				logger.error("Fallito invio notifica e-mail dell'assegnazionem per il seguente motivo: " + lStoreResultBean.getDefaultMessage());
				message += " per il seguente motivo: " + lStoreResultBean.getDefaultMessage();
			}
			addMessage(message, "", MessageType.WARNING);
		} else {
			if (lStoreResultBean.getResultBean().getFlgemailtosendout() != null && lStoreResultBean.getResultBean().getFlgemailtosendout().intValue() == 1) {
				SenderBean sender = new SenderBean();
				sender.setIdUtenteModPec(lAurigaLoginBean.getSpecializzazioneBean().getIdUtenteModPec());
				sender.setAccount(lStoreResultBean.getResultBean().getIndirizzomittemailout());
				sender.setAddressFrom(lStoreResultBean.getResultBean().getIndirizzomittemailout());
				List<String> addressTo = new ArrayList<String>();
				StringReader sr = new StringReader(lStoreResultBean.getResultBean().getIndirizzidestemailout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
						addressTo.add(v.get(0));
					}
				}
				sender.setAddressTo(addressTo);
				sender.setSubject(lStoreResultBean.getResultBean().getOggemailout());
				sender.setBody(lStoreResultBean.getResultBean().getBodyemailout());
				sender.setIsHtml(true);
				sender.setIsPec(false);
				try {
					EmailSentReferenceBean lEmailSentReferenceBean = AurigaMailService.getMailSenderService().send(getLocale(), sender);
					String notSentMails = null;
					for (SenderBean lSenderBean : lEmailSentReferenceBean.getSentMails()) {
						if (!lSenderBean.getIsSent()) {
							if (notSentMails == null) {
								notSentMails = lSenderBean.getAddressTo().get(0);
							} else {
								notSentMails += ", " + lSenderBean.getAddressTo().get(0);
							}
						}
					}
					if (notSentMails != null) {
						if("ASSEGNAZIONE".equals(tipoOperazione)){
							logger.warn("Fallito invio notifica e-mail dell'assegnazione per i seguenti indirizzi: " + notSentMails);
							addMessage("Fallito invio notifica e-mail dell'assegnazione per i seguenti indirizzi: " + notSentMails, "", MessageType.WARNING);
						}else{
							logger.warn("Warning, Fallito invio notifica e-mail dell'invio per conoscenza per i seguenti indirizzi: " + notSentMails);
							addMessage("Fallito invio notifica e-mail dell'invio per conoscenza per i seguenti indirizzi: " + notSentMails, "", MessageType.WARNING);
						}
					}
				} catch (Exception e) {
					if("ASSEGNAZIONE".equals(tipoOperazione)){
						logger.error("Fallito invio notifica e-mail dell'assegnazione:" + e.getMessage(), e);
						addMessage("Fallito invio notifica e-mail dell'assegnazione: " + e.getMessage(), "", MessageType.WARNING);
					}else{
						logger.error("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza" + e.getMessage(), e);
						addMessage("Fallito invio notifica e-mail dell'invio per conoscenza: " + e.getMessage(), "", MessageType.WARNING);
					}
				}
			} else {
				if("ASSEGNAZIONE".equals(tipoOperazione)){
					logger.warn("Non è stata inviata alcuna notifica e-mail dell'assegnazione");
				}else{
					logger.warn("Warning, Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza,getFlgemailtosendout: "
							+ lStoreResultBean.getResultBean().getFlgemailtosendout());
				}
			}
		}
	}
	
	private List<TipoIdBean> getListaAssegnatari(XmlFascicoloIn xmlFascicolo) throws Exception {
		List<TipoIdBean> listaDestAssCC = new ArrayList<TipoIdBean>();
		if (xmlFascicolo.getAssegnatari() != null && !xmlFascicolo.getAssegnatari().isEmpty()) {
			for (AssegnatariBean assegnatarioBean : xmlFascicolo.getAssegnatari()) {
				
				TipoIdBean tipoIdBean = new TipoIdBean();
				tipoIdBean.setTipo(getTipoAssegnatarioInvioCC(assegnatarioBean));
				tipoIdBean.setId(assegnatarioBean.getIdSettato());
				tipoIdBean.setFlgMandaNotificaMail(assegnatarioBean.getFlgMandaNotificaMail() != null && assegnatarioBean.getFlgMandaNotificaMail() == Flag.SETTED ?
						true : false );
				listaDestAssCC.add(tipoIdBean);
			}
		}
		return listaDestAssCC;
	}
	
	private List<TipoIdBean> getListaAssegnatariPerConoscenza(XmlFascicoloIn xmlFascicolo) {
		List<TipoIdBean> listaInvioCC = new ArrayList<TipoIdBean>();
		if (xmlFascicolo.getInvioDestCC() != null && !xmlFascicolo.getInvioDestCC().isEmpty()) {
			for (AssegnatariBean assegnatarioBean : xmlFascicolo.getInvioDestCC()) {
				
				TipoIdBean tipoIdBean = new TipoIdBean();
				tipoIdBean.setTipo(getTipoAssegnatarioInvioCC(assegnatarioBean));
				tipoIdBean.setId(assegnatarioBean.getIdSettato());
				tipoIdBean.setFlgMandaNotificaMail(assegnatarioBean.getFlgMandaNotificaMail() != null && assegnatarioBean.getFlgMandaNotificaMail() == Flag.SETTED ?
						true : false );
				listaInvioCC.add(tipoIdBean);
			}
		}
		return listaInvioCC;
	}
	
	private String getTipoAssegnatarioInvioCC(AssegnatariBean lAssegnazioneBean) {
		TipoAssegnatario[] lTipoAssegnatarios = TipoAssegnatario.values();
		for (TipoAssegnatario lTipoAssegnatario : lTipoAssegnatarios) {
			if (lTipoAssegnatario.toString().equals(lAssegnazioneBean.getTipo().toString()))
				return lTipoAssegnatario.toString();
		}
		return null;
	}
	
	/**
	 * **************************************************************************************************************************************
	 */
	public AzioneSuccessivaBean azionePostFirma(AzioneSuccessivaBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		if(bean != null && bean.getListUD() != null){
			
			//Prelevo i valori dei flag selezionati
			Boolean flgProtReg = bean.getFlgProtReg() != null && bean.getFlgProtReg() ? true : false;
			Boolean flgFirma = bean.getFlgFirma() != null && bean.getFlgFirma() ? true : false;
			Boolean flgVistoEle = bean.getFlgVistoEle() != null && bean.getFlgVistoEle() ? true : false;
			Boolean flgMandaAlMittenteDocumento = bean.getFlgMandaAlMittenteDocumento() != null && bean.getFlgMandaAlMittenteDocumento() ? true : false;
			Boolean flgRestituzioneAlMittente = bean.getFlgRestituzioneAlMittente() != null && bean.getFlgRestituzioneAlMittente() ? true : false;
			
			for(ArchivioBean archivioBean : bean.getListUD()) {
			
				mLogger.debug("AZIONE POST FIRMA " + archivioBean.getSegnatura());
				mLogger.debug("idUd: " + archivioBean.getIdUdFolder());
				
				//Imposto i valori del bean da passare alla store
				DmpkCollaborationInvioBean input = new DmpkCollaborationInvioBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);				
				input.setFlgtypeobjtosendin("U");
				input.setIdobjtosendin(archivioBean.getIdUdFolder() != null && !"".equals(archivioBean.getIdUdFolder()) ? new BigDecimal(archivioBean.getIdUdFolder()) : null);
				input.setFlgcallbyguiin(new Integer(0));
				
				if(flgProtReg) {
					mLogger.debug("Invio alla protocollazione/registrazione presso");
					input.setCodmotivoinvioin("PP");
					if(bean.getProtRegAssegnazione() != null && !bean.getProtRegAssegnazione().isEmpty()){
						input.setRecipientsxmlin(getXmlAssegnatariFirmaUD(bean,"PP"));
					}
				} else if(flgFirma) {
					mLogger.debug("Invio alla firma digitale");
					input.setCodmotivoinvioin("PAF");
					if(bean.getInvioFirmaDigitaleAssegnazione()!= null && !bean.getInvioFirmaDigitaleAssegnazione().isEmpty()){
						input.setRecipientsxmlin(getXmlAssegnatariFirmaUD(bean,"PAF"));
					}
				} else if(flgVistoEle) {
					mLogger.debug("Invio al visto elettronico");
					input.setCodmotivoinvioin("PAV");
					if(bean.getInvioVistoElettronicoAssegnazione() != null && !bean.getInvioVistoElettronicoAssegnazione().isEmpty()){
						input.setRecipientsxmlin(getXmlAssegnatariFirmaUD(bean,"PAV"));
					}
				} else if(flgRestituzioneAlMittente) {
					mLogger.debug("Restituzione al mittente");					
					input.setCodmotivoinvioin("");
					mLogger.debug("tipoMittUltimoInvioUD: " + archivioBean.getTipoMittUltimoInvioUDOut());
					mLogger.debug("idMittUltimoInvioUD: " + archivioBean.getIdMittUltimoInvioUDOut());
					if(StringUtils.isNotBlank(archivioBean.getTipoMittUltimoInvioUDOut()) && archivioBean.getIdMittUltimoInvioUDOut() != null){
						List<AssegnatariBean> listaAssegnatari = new ArrayList<AssegnatariBean>();
						AssegnatariBean assegnatariBean = new AssegnatariBean();
						assegnatariBean.setTipo(getTipoAssegnatarioFirmaUD(archivioBean.getTipoMittUltimoInvioUDOut()));
						assegnatariBean.setIdSettato(String.valueOf(archivioBean.getIdMittUltimoInvioUDOut().intValue()));
						assegnatariBean.setPermessiAccesso("FC");						
						//TODO ASSEGNAZIONE SAVE OK
						if(StringUtils.isNotBlank(assegnatariBean.getIdSettato())) {
							listaAssegnatari.add(assegnatariBean);	
						}
						input.setRecipientsxmlin(lXmlUtilitySerializer.bindXmlList(listaAssegnatari));
					}
				} else if(flgMandaAlMittenteDocumento) {
					mLogger.debug("Manda per protocollazione al mittente indicato sul documento");
					input.setCodmotivoinvioin("PP");
					mLogger.debug("tipoMittIntUD: " + archivioBean.getTipoMittIntUDOut());
					mLogger.debug("idMittUD: " + archivioBean.getIdMittUDOut());
					if(StringUtils.isNotBlank(archivioBean.getTipoMittIntUDOut()) && archivioBean.getIdMittUDOut() != null){
						List<AssegnatariBean> listaAssegnatari = new ArrayList<AssegnatariBean>();
						AssegnatariBean assegnatariBean = new AssegnatariBean();
						assegnatariBean.setTipo(getTipoAssegnatarioFirmaUD(archivioBean.getTipoMittIntUDOut()));
						assegnatariBean.setIdSettato(String.valueOf(archivioBean.getIdMittUDOut().intValue()));
						assegnatariBean.setPermessiAccesso("FC");						
						//TODO ASSEGNAZIONE SAVE OK
						if(StringUtils.isNotBlank(assegnatariBean.getIdSettato())) {
							listaAssegnatari.add(assegnatariBean);		
						}
						input.setRecipientsxmlin(lXmlUtilitySerializer.bindXmlList(listaAssegnatari));
					}
				} else {
					mLogger.debug("Nessuna azione selezionata");
					return bean;
				}
				
				mLogger.debug("codMotivoInvio: " + input.getCodmotivoinvioin());
				mLogger.debug("recipientsXml: " + input.getRecipientsxmlin());
				
				if(flgRestituzioneAlMittente && (StringUtils.isBlank(archivioBean.getTipoMittUltimoInvioUDOut()) || archivioBean.getIdMittUltimoInvioUDOut() == null)) {					
					mLogger.error("ATTENZIONE: siamo nel caso in cui si è selezionato 'Restituzione al mittente' e il valore di TipoMittUltimoInvioUDOut o IdMittUltimoInvioUDOut è null quindi non eseguo la chiamata alla DmpkCollaborationInvio");
					errorMessages.put(archivioBean.getIdUdFolder(), "Invio non effettuato: il mittente non è valorizzato");
				} else if(flgMandaAlMittenteDocumento && (StringUtils.isBlank(archivioBean.getTipoMittIntUDOut()) || archivioBean.getIdMittUDOut() == null)) {					
					mLogger.error("ATTENZIONE: siamo nel caso in cui si è selezionato 'Manda in protocollazione al mittente indicato sul documento' e il valore di TipoMittIntUDOut o IdMittUDOut è null quindi non eseguo la chiamata alla DmpkCollaborationInvio");
					errorMessages.put(archivioBean.getIdUdFolder(), "Invio non effettuato: il mittente non è valorizzato");
				} else {
					mLogger.debug("Eseguo la chiamata alla DmpkCollaborationInvio");					
					DmpkCollaborationInvio dmpkCollaborationInvio = new DmpkCollaborationInvio();
					StoreResultBean<DmpkCollaborationInvioBean> output = dmpkCollaborationInvio.execute(getLocale(), loginBean, input);					
					if (StringUtils.isNotBlank(output.getDefaultMessage())) {						
						mLogger.error("Si è verificato un errore durante la chiamata alla DmpkCollaborationInvio: " + output.getDefaultMessage());
						errorMessages.put(archivioBean.getIdUdFolder(), output.getDefaultMessage());
					} else {
						mLogger.debug("Chiamata alla DmpkCollaborationInvio effettuata con successo");
					}
				}				
			}
		}
		
		if(errorMessages != null && !errorMessages.isEmpty()) {
			bean.setErrorMessages(errorMessages);
		}
		
		return bean;
	}
	
	public String getXmlAssegnatariFirmaUD(AzioneSuccessivaBean bean,String tipo) throws Exception {
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(getAssegnatariFirmaUD(bean,tipo));
	}

	private List<AssegnatariBean> getAssegnatariFirmaUD(AzioneSuccessivaBean bean,String tipo) throws Exception {
		List<AssegnatariBean> listaAssegnatari = new ArrayList<AssegnatariBean>();
		
		if("PP".equals(tipo)){
			for (AssegnazioneBean assegnazioneBean : bean.getProtRegAssegnazione()) {
					populateAssegnatariFirmaUD(bean, listaAssegnatari, assegnazioneBean);
			}
		} else if("PAF".equals(tipo)){
			for (AssegnazioneBean assegnazioneBean : bean.getInvioFirmaDigitaleAssegnazione()) {
					populateAssegnatariFirmaUD(bean, listaAssegnatari, assegnazioneBean);
			}
		} else if("PAV".equals(tipo)){
			for (AssegnazioneBean assegnazioneBean : bean.getInvioVistoElettronicoAssegnazione()) {
					populateAssegnatariFirmaUD(bean, listaAssegnatari, assegnazioneBean);
			}
		}
		
		return listaAssegnatari;
	}

	private void populateAssegnatariFirmaUD(AzioneSuccessivaBean bean, List<AssegnatariBean> listaAssegnatari, AssegnazioneBean assegnazioneBean)
			throws StoreException {
		AssegnatariBean lAssegnatariBean = new AssegnatariBean();
		lAssegnatariBean.setTipo(getTipoAssegnatarioFirmaUD(assegnazioneBean.getTypeNodo()));
		if(lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.GRUPPO)) {
			lAssegnatariBean.setIdSettato(assegnazioneBean.getGruppo());
		} else {
			lAssegnatariBean.setIdSettato(assegnazioneBean.getIdUo());
		}	
		if (bean.getMotivo() != null ) {							
			if ("PAF".equals(bean.getMotivo())) {
				if (lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.UNITA_ORGANIZZATIVA)) {
					throw new StoreException("L'assegnazione per firma/approvazione deve essere fatta a persona/e e non ad una U.O.");
				}	
			}
			if ("PAV".equals(bean.getMotivo())) {
				if (lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.UNITA_ORGANIZZATIVA)) {
					throw new StoreException("L'assegnazione per apposizione visto elettronico deve essere fatta a persona/e e non ad una U.O.");
				}	
			}							
			lAssegnatariBean.setRuolo("F");
		}
		lAssegnatariBean.setPermessiAccesso("FC");
		//TODO ASSEGNAZIONE SAVE OK
		if(assegnazioneBean.getOpzioniInvio() != null) {			
			lAssegnatariBean.setMotivoInvio(assegnazioneBean.getOpzioniInvio().getMotivoInvio());
			lAssegnatariBean.setLivelloPriorita(assegnazioneBean.getOpzioniInvio().getLivelloPriorita());
			lAssegnatariBean.setMessaggioInvio(assegnazioneBean.getOpzioniInvio().getMessaggioInvio());
			if (assegnazioneBean.getOpzioniInvio().getFlgPresaInCarico() != null && assegnazioneBean.getOpzioniInvio().getFlgPresaInCarico()) {
				lAssegnatariBean.setFeedback(Flag.SETTED);
			}
			if (assegnazioneBean.getOpzioniInvio().getFlgMancataPresaInCarico() != null && assegnazioneBean.getOpzioniInvio().getFlgMancataPresaInCarico()) {
				lAssegnatariBean.setNumeroGiorniFeedback(assegnazioneBean.getOpzioniInvio().getGiorniTrascorsi());
			}
		}
		if(StringUtils.isNotBlank(lAssegnatariBean.getIdSettato())) {
			listaAssegnatari.add(lAssegnatariBean);
		}
	}

	private TipoAssegnatario getTipoAssegnatarioFirmaUD(String typeNodo) {
		TipoAssegnatario[] tipiAssegnatari = TipoAssegnatario.values();
		for (TipoAssegnatario tipoAssegnatario : tipiAssegnatari) {
			if (tipoAssegnatario.toString().equals(typeNodo)) {
				return tipoAssegnatario;
			}
		}
		return null;
	}

	public ApposizioneVistoBean vistoDocumento(ApposizioneVistoBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		
		//I vari record che sono stati selezionati si trovano all'interno del campo listaRecord
		List<ArchivioBean> udList = bean.getListaRecord();
		List<ArchivioBean> newList = new ArrayList<ArchivioBean>();
		
//		int INDEXCABLATO = 1;
		
		//Per ognuno dei record presenti prelevo l'idUdFolder, ovvero il documento su cui bisogna apporre il visto
		for(ArchivioBean udBean : udList){
			String idUd = udBean.getIdUdFolder(); //Parametro da passare alla stored
			
			DmpkCoreVistadocumentoBean input = new DmpkCoreVistadocumentoBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdudin(idUd != null && !"".equals(idUd) ? new BigDecimal(idUd) : null);
			if(bean.getEsito() != null && bean.getEsito().equals("apposto")){
				//Imposto il flag relativo nel caso in cui sia apposto il visto
				input.setFlgappostorifiutatoin("A");
			}else if(bean.getEsito() != null && bean.getEsito().equals("rifiutato")){
				//Imposto il flag relativo nel caso in cui sia rifiutato
				input.setFlgappostorifiutatoin("R");
			}
			
			//Imposto il messaggio nel caso quest'ultimo sia presente
			if(bean.getMotivazione() != null && !bean.getMotivazione().isEmpty()){
				 input.setMessaggioin(bean.getMotivazione().toString());
			}
			
			DmpkCoreVistadocumento dmpkCoreVistaDocumento = new DmpkCoreVistadocumento();
			StoreResultBean<DmpkCoreVistadocumentoBean> output = dmpkCoreVistaDocumento.execute(getLocale(), loginBean, input);
	
			if (output.getDefaultMessage() != null) {
				errorMessages.put(idUd, output.getDefaultMessage());
			}else{
				//TODO CANCELLARE
//				if(INDEXCABLATO % 2 == 0){
//					errorMessages.put(idUd, "ERRORE CABLATO");
//					
//				}
//				INDEXCABLATO++;
				
			}
			
			if(output.getResultBean().getTipomittintudout() != null){
				udBean.setTipoMittIntUDOut(output.getResultBean().getTipomittintudout());
			}
			
			if(output.getResultBean().getIdmittudout() != null){
				udBean.setIdMittUDOut(output.getResultBean().getIdmittudout());
			}
			
			if(output.getResultBean().getTipomittultimoinvioudout() != null){
				udBean.setTipoMittUltimoInvioUDOut(output.getResultBean().getTipomittultimoinvioudout());
			}
			
			if(output.getResultBean().getIdmittultimoinvioudout() != null){
				udBean.setIdMittUltimoInvioUDOut(output.getResultBean().getIdmittultimoinvioudout());
			}
			newList.add(udBean);
		}
		bean.setErrorMessages(errorMessages);
		bean.setListaRecord(newList);
		
		return bean;
	}
	
	public ApposizioneVistoBean firmatoDocumento(ApposizioneVistoBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		
		//I vari record che sono stati selezionati si trovano all'interno del campo listaRecord
		List<ArchivioBean> udList = bean.getListaRecord(); 
		List<ArchivioBean> newList = new ArrayList<ArchivioBean>();
		
//		int INDEXCABLATO = 1;
		
		//Per ognuno dei record presenti prelevo l'idUdFolder, ovvero il documento su cui bisogna eseguire l'apposizione 
		for(ArchivioBean udBean : udList){
			String idUd = udBean.getIdUdFolder(); //Parametro da passare alla stored
			
			DmpkCoreFirmadocumentoBean input = new DmpkCoreFirmadocumentoBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdudin(idUd != null && !"".equals(idUd) ? new BigDecimal(idUd) : null);
			if(bean.getEsito() != null && bean.getEsito().equals("apposto")){
				//Imposto il flag relativo nel caso in cui sia apposto il visto
				input.setFlgappostarifiutatain("A");
			}else if(bean.getEsito() != null && bean.getEsito().equals("rifiutato")){
				//Imposto il flag relativo nel caso in cui sia rifiutato
				input.setFlgappostarifiutatain("R");
			}
			
			//Imposto il messaggio nel caso quest'ultimo sia presente
			if(bean.getMotivazione() != null && !bean.getMotivazione().isEmpty()){
				 input.setMessaggioin(bean.getMotivazione().toString());
			}
			
			DmpkCoreFirmadocumento dmpkCoreFirmaDocumento = new DmpkCoreFirmadocumento();
			StoreResultBean<DmpkCoreFirmadocumentoBean> output = dmpkCoreFirmaDocumento.execute(getLocale(), loginBean, input);
	
			if (output.getDefaultMessage() != null) {
				errorMessages.put(idUd, output.getDefaultMessage());
			}else{
				//TODO CANCELLARE
//				if(INDEXCABLATO % 2 == 0){
//					errorMessages.put(idUd, "ERRORE CABLATO");
//					
//				}
//				INDEXCABLATO++;
			}
			
			if(output.getResultBean().getTipomittintudout() != null){
				udBean.setTipoMittIntUDOut(output.getResultBean().getTipomittintudout());
			}
			
			if(output.getResultBean().getIdmittudout() != null){
				udBean.setIdMittUDOut(output.getResultBean().getIdmittudout());
			}
			
			if(output.getResultBean().getTipomittultimoinvioudout() != null){
				udBean.setTipoMittUltimoInvioUDOut(output.getResultBean().getTipomittultimoinvioudout());
			}
			
			if(output.getResultBean().getIdmittultimoinvioudout() != null){
				udBean.setIdMittUltimoInvioUDOut(output.getResultBean().getIdmittultimoinvioudout());
			}
			
			newList.add(udBean);
		}
		bean.setErrorMessages(errorMessages);
		bean.setListaRecord(newList);
		return bean;
	}
	
	protected void salvaAttributiCustomSemplici(ArchivioBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {
		// Salvo classifica
		if (StringUtils.isNotBlank(bean.getClassifPregressa())){
			sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("CLASSIF_PREGRESSA", bean.getClassifPregressa()));
		}
		
		// Salvo unità di carico
		if (StringUtils.isNotBlank(bean.getUdc())){
			sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("UDC", bean.getUdc()));
		}
		
		// Salvo presenza in archivio
		if (StringUtils.isNotBlank(bean.getPresenzaInArchivioDeposito())){
			sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("PRESENZA_IN_ARCHIVIO_DEPOSITO", bean.getPresenzaInArchivioDeposito()));
		}
		
		// Salvo competenza pratica
		if (StringUtils.isNotBlank(bean.getCompetenzaPratica())){
			sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("COMPETENZA_PRATICA", bean.getCompetenzaPratica()));
		}
		
		// Salvo reperibilità cartaceo pratica
		if (StringUtils.isNotBlank(bean.getReperibilitaCartaceoPratica())){
			sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("REPERIBILITA_CARTACEO_PRATICA", bean.getReperibilitaCartaceoPratica()));
		}
		
		// Salvo pres cartaceo pratica
		if (StringUtils.isNotBlank(bean.getArchivioPresCartaceoPratica())){
			sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("ARCHIVIO_PRES_CARTACEO_PRATICA", bean.getArchivioPresCartaceoPratica()));
		}
	}
	
	protected void salvaAttributiCustomLista(ArchivioBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {
		
	}
	
	public StampaFileBean creaStampa(ArchivioBean bean) throws Exception {		
		
		try {		
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			
			String nomeModello = getExtraparams().get("nomeModello");
			String nomeFileStampa = getExtraparams().get("nomeFileStampa");
			
			DmpkModelliDocExtractvermodello lExtractvermodello = new DmpkModelliDocExtractvermodello();
			DmpkModelliDocExtractvermodelloBean lExtractvermodelloInput = new DmpkModelliDocExtractvermodelloBean();
			lExtractvermodelloInput.setCodidconnectiontokenin(loginBean.getToken());
			lExtractvermodelloInput.setNomemodelloin(nomeModello);
			
			StoreResultBean<DmpkModelliDocExtractvermodelloBean> lExtractvermodelloOutput = lExtractvermodello.execute(getLocale(), loginBean, lExtractvermodelloInput);
			
			if(lExtractvermodelloOutput.isInError()) {
				throw new StoreException(lExtractvermodelloOutput);
			}
			
			String uriModello = lExtractvermodelloOutput.getResultBean().getUriverout();
			
			DmpkModelliDocGetdatixgendamodello lGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
			DmpkModelliDocGetdatixgendamodelloBean lGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
			lGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
			lGetdatixgendamodelloInput.setIdobjrifin(bean.getIdUdFolder());
			lGetdatixgendamodelloInput.setFlgtpobjrifin("F");
			lGetdatixgendamodelloInput.setNomemodelloin(nomeModello);
			
			StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lGetdatixgendamodelloOutput = lGetdatixgendamodello.execute(getLocale(), loginBean,
					lGetdatixgendamodelloInput);
			
			if(lGetdatixgendamodelloOutput.isInError()) {
				throw new StoreException(lGetdatixgendamodelloOutput);
			}
			
			String templateValues = lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();					
			File fileModelloPdf = null;
			if (nomeModello != null &&
					nomeModello.equalsIgnoreCase("LISTA_CONTENUTI_FASC")) {
				fileModelloPdf = ModelliUtil.fillTemplateAndConvertToPdf(null, uriModello, null, templateValues, getSession());
			} else
				fileModelloPdf = ModelliUtil.fillTemplateAndConvertToPdf(null, uriModello, "DOCX_FORM", templateValues, getSession());
			
			StampaFileBean output = new StampaFileBean();
			output.setNomeFile(nomeFileStampa);
			output.setUri(StorageImplementation.getStorage().store(fileModelloPdf));
			
			MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
			infoFile.setMimetype("application/pdf");
			infoFile.setDaScansione(false);
			infoFile.setFirmato(false);
			infoFile.setFirmaValida(false);
			infoFile.setBytes(fileModelloPdf.length());
			infoFile.setCorrectFileName(output.getNomeFile());
			File realFile = StorageImplementation.getStorage().getRealFile(output.getUri());
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), output.getNomeFile()));
			output.setInfoFile(infoFile);
			
			return output;
			
		} catch(Exception e) {
			mLogger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante la generazione della stampa");
		}		
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_ASSEGN_UD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailAssegnUD(List<TipoIdBean> listaAssegnatari) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD"))) {
			if(listaAssegnatari != null && !listaAssegnatari.isEmpty()) {
				for(int i=0; i < listaAssegnatari.size(); i++) {
					if(listaAssegnatari.get(i) != null &&
					   listaAssegnatari.get(i).getFlgMandaNotificaMail() != null && listaAssegnatari.get(i).getFlgMandaNotificaMail()) {
						attivo = true;
						break;
					}
				}
			}
		}
		return attivo;
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_ASSEGN_FLD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailAssegnFLD(List<TipoIdBean> listaAssegnatari) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_FLD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_FLD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_FLD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_FLD"))) {
			if(listaAssegnatari != null && !listaAssegnatari.isEmpty()) {
				for(int i=0; i < listaAssegnatari.size(); i++) {
					if(listaAssegnatari.get(i) != null &&
					   listaAssegnatari.get(i).getFlgMandaNotificaMail() != null && listaAssegnatari.get(i).getFlgMandaNotificaMail()) {
						attivo = true;
						break;
					}
				}
			}
		}
		return attivo;
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_INVIO_CC_UD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailInvioCCUD(List<TipoIdBean> listaAssegnatari) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD"))) {
			if(listaAssegnatari != null && !listaAssegnatari.isEmpty()) {
				for(int i=0; i < listaAssegnatari.size(); i++) {
					if(listaAssegnatari.get(i) != null &&
					   listaAssegnatari.get(i).getFlgMandaNotificaMail() != null && listaAssegnatari.get(i).getFlgMandaNotificaMail() ) {
							attivo = true;
							break;
					}
				}
			}
		}
		return attivo;
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_INVIO_CC_FLD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailInvioCCFLD(List<TipoIdBean> listaAssegnatari) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_FLD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_FLD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_FLD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_FLD"))) {
			if(listaAssegnatari != null && !listaAssegnatari.isEmpty()) {
				for(int i=0; i < listaAssegnatari.size(); i++) {
					if(listaAssegnatari.get(i) != null &&
					   listaAssegnatari.get(i).getFlgMandaNotificaMail() != null && listaAssegnatari.get(i).getFlgMandaNotificaMail() ) {
							attivo = true;
							break;
					}
				}
			}
		}
		return attivo;
	}
	
	public ArchivioBean modificaTipologia(ArchivioBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		//se il bean è un unità documentale
		if ("U".equals(bean.getFlgUdFolder())) {
			DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setFlgtipotargetin("D");
			input.setIduddocin(new BigDecimal(bean.getIdDocPrimario()));

			CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
			creaModDocumentoInBean.setTipoDocumento(getExtraparams().get("tipoDocumento"));
			input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));

			DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
			StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
			if (output.isInError()) {
				throw new StoreException(output);
			}
		} else {
			DmpkCoreUpdfolderBean input = new DmpkCoreUpdfolderBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdfolderin(new BigDecimal(bean.getIdUdFolder()));

			XmlFascicoloIn xmlFascicoloIn = new XmlFascicoloIn();
			xmlFascicoloIn.setIdFolderType(new BigDecimal(getExtraparams().get("idFolderType")));

			lXmlUtilitySerializer = new XmlUtilitySerializer();
			input.setAttributixmlin(lXmlUtilitySerializer.bindXml((Object) xmlFascicoloIn));
			
			DmpkCoreUpdfolder dmpkCoreUpdfolder = new DmpkCoreUpdfolder();
			StoreResultBean<DmpkCoreUpdfolderBean> output = dmpkCoreUpdfolder.execute(this.getLocale(), loginBean,
					(DmpkCoreUpdfolderBean) input);
			if (output.isInError()) {
				throw new StoreException(output);
			}
		}
		
		return bean;

	}
	
	public OperazioneMassivaArchivioBean modificaTipologiaMassiva(OperazioneMassivaArchivioBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		HashMap<String, String> errorMessages = new HashMap<String, String>();

		for (ArchivioBean archivioBean : bean.getListaRecord()) {
			
			//se il bean è un unità documentale
			if ("U".equals(archivioBean.getFlgUdFolder())) {
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(
						StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setFlgtipotargetin("D");
				input.setIduddocin(new BigDecimal(archivioBean.getIdDocPrimario()));

				CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
				lCreaModDocumentoInBean.setTipoDocumento(getExtraparams().get("tipoDocumento"));
				
				lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml((Object) lCreaModDocumentoInBean));

				DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean,
						(DmpkCoreUpddocudBean) input);

				if (output.isInError() && output.getDefaultMessage() != null) {
					if (errorMessages == null)
						errorMessages = new HashMap<String, String>();
					errorMessages.put(archivioBean.getIdUdFolder(), output.getDefaultMessage());
				}
			//se il bean è una folder
			} else {
				DmpkCoreUpdfolderBean input = new DmpkCoreUpdfolderBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(
						StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setIdfolderin(new BigDecimal(archivioBean.getIdUdFolder()));

				XmlFascicoloIn xmlFascicoloIn = new XmlFascicoloIn();
				xmlFascicoloIn.setIdFolderType(new BigDecimal(getExtraparams().get("idFolderType")));

				lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributixmlin(lXmlUtilitySerializer.bindXml((Object) xmlFascicoloIn));

				DmpkCoreUpdfolder dmpkCoreUpdfolder = new DmpkCoreUpdfolder();
				StoreResultBean<DmpkCoreUpdfolderBean> output = dmpkCoreUpdfolder.execute(this.getLocale(), loginBean,
						(DmpkCoreUpdfolderBean) input);

				if (output.isInError() && output.getDefaultMessage() != null) {
					if (errorMessages == null)
						errorMessages = new HashMap<String, String>();
					errorMessages.put(archivioBean.getIdUdFolder(), output.getDefaultMessage());
				}
			}
		}
		if (errorMessages != null && !errorMessages.isEmpty()) {
			bean.setErrorMessages(errorMessages);
		}
		
		return bean;
	}
	
	public ProtocollazioneBean protocollazioneDocumentiIstruttoria(ArchivioBean bean) throws Exception {
		ProtocollazioneBean lProtocollazioneBeanInput = new ProtocollazioneBean();
		String idUdDaProtocollare = bean.getIdUdDaProtocollare();
		String uoProcollante = bean.getDatiProtDocIstrUOProtocollante();
		String codUOProtocollante = uoProcollante != null && uoProcollante.startsWith("UO") ? uoProcollante.substring(2) : uoProcollante;
		lProtocollazioneBeanInput.setIdUd(StringUtils.isNotBlank(idUdDaProtocollare) ? new BigDecimal(idUdDaProtocollare) : null);
		lProtocollazioneBeanInput = getProtocolloDataSource().get(lProtocollazioneBeanInput);
				
		// Setto i mittenti
		List<MittenteProtBean> listaMittenti = new ArrayList<>();
		MittenteProtBean mittente = new MittenteProtBean();
		mittente.setIdSoggetto(bean.getDatiProtDocIstrMittenteId());
		listaMittenti.add(mittente);
		lProtocollazioneBeanInput.setListaMittenti(listaMittenti);
		
		// Setto i destinatari
		List<DestinatarioProtBean> listaDestinatari = new ArrayList<>();
		DestinatarioProtBean destinatario = new DestinatarioProtBean();
		destinatario.setTipoDestinatario("1".equalsIgnoreCase(bean.getDatiProtDocIstrDestinatarioFlgPersonaFisica()) ? "PF" : "PG");
		destinatario.setDenominazioneDestinatario(bean.getDatiProtDocIstrDestinatarioDenomCognome());
		destinatario.setCognomeDestinatario(bean.getDatiProtDocIstrDestinatarioDenomCognome());
		destinatario.setNomeDestinatario(bean.getDatiProtDocIstrDestinatarioNome());
		destinatario.setCodfiscaleDestinatario(bean.getDatiProtDocIstrCodFiscale());
		listaDestinatari.add(destinatario);
		lProtocollazioneBeanInput.setListaDestinatari(listaDestinatari);
		
		// Setto gli assegnatari
		List<AssegnazioneBean> lListAssegnatari = new ArrayList<AssegnazioneBean>();
		AssegnazioneBean lAssegnatariBean = new AssegnazioneBean();
		lAssegnatariBean.setTypeNodo("UO");
		lAssegnatariBean.setIdUo(codUOProtocollante);
		lListAssegnatari.add(lAssegnatariBean);
		lProtocollazioneBeanInput.setListaAssegnazioni(lListAssegnatari);
		
		// Setto il tipo di protocollo
		lProtocollazioneBeanInput.setFlgTipoProv("U");
		
		// Setto la UO protoollante
		// lProtocollazioneBeanInput.setUoProtocollante("UO100024");
		lProtocollazioneBeanInput.setUoProtocollante(uoProcollante);
		
		// Setto l'oggetto
		lProtocollazioneBeanInput.setOggetto(bean.getDescFolderType() + " - " + bean.getNome() + " - " + bean.getDesAllegatoDaProtocollare());
		
		// Setto la numerazione
		List<TipoNumerazioneBean> listaTipiNumerazioneDaDare = new ArrayList<TipoNumerazioneBean>();
		TipoNumerazioneBean lTipoProtocolloGenerale = new TipoNumerazioneBean();		
		lTipoProtocolloGenerale.setCategoria("PG");
		lTipoProtocolloGenerale.setIdUo(StringUtils.isNotBlank(uoProcollante) ? uoProcollante.substring(2) : null);
		listaTipiNumerazioneDaDare.add(lTipoProtocolloGenerale);
		lProtocollazioneBeanInput.setListaTipiNumerazioneDaDare(listaTipiNumerazioneDaDare);
	
		ProtocollazioneBean output = new ProtocollazioneBean();
		try {
			output = getProtocolloDataSource().update(lProtocollazioneBeanInput, null);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		return output;
	}
	
    private ProtocolloDataSource getProtocolloDataSource() {	
		
		ProtocolloDataSource lProtocolloDataSource = new ProtocolloDataSource() {
			// FIXME Controllare
//			@Override
//			public boolean isAppendRelazioniVsUD(ProtocollazioneBean beanDettaglio) {
//				return false;
//			}		
//			
//			@Override
//			protected void salvaAttributiCustom(ProtocollazioneBean pProtocollazioneBean, SezioneCache pSezioneCacheAttributiDinamici) throws Exception {
//				super.salvaAttributiCustom(pProtocollazioneBean, pSezioneCacheAttributiDinamici);
//				if(pNuovaPropostaAtto2Bean != null) {
//					salvaAttributiCustomProposta(pNuovaPropostaAtto2Bean, pSezioneCacheAttributiDinamici);
//				}
//			};		
		};		
		lProtocolloDataSource.setSession(getSession());
		lProtocolloDataSource.setExtraparams(getExtraparams());	
		// devo settare in ProtocolloDataSource i messages di ArchivioDataSource per mostrare a video gli errori in salvataggio dei file
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lProtocolloDataSource.setMessages(getMessages()); 
		
		return lProtocolloDataSource;
	}

	private String buildListaIdUdXml(ArchivioXmlBean bean) throws Exception {
		List<SimpleValueBean> listaIdUd = new ArrayList<SimpleValueBean>();

		SimpleValueBean lSimpleValueBean = new SimpleValueBean();
		lSimpleValueBean.setValue(bean.getIdUdFolder());
		listaIdUd.add(lSimpleValueBean);
		
		return new XmlUtilitySerializer().bindXmlList(listaIdUd);
	}	
	
	public OperazioneMassivaArchivioBean protocollazioneBeforeTimbraEFirmaMassiva(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		List<ArchivioBean> listaRecordCorrettamenteProtocollati = new ArrayList<>();
		HashMap<String, String> mappaErrori = new HashMap<>();
		
		if (bean.getListaRecord() != null) {
			for (ArchivioBean archivioBean : bean.getListaRecord()) {
				if (archivioBean.getNroProt() == null) {
					DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
					input.setCodidconnectiontokenin(token);
					input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
					input.setFlgtipotargetin("U");
					input.setIduddocin(new BigDecimal(archivioBean.getIdUdFolder()));
										
					// Se uscita o se interno AND ATTIVA_REGISTRO_PG_X_PROT_INTERNA = true si setta #@NumerazioniDaDare con colonna 3 = PG; 
					// altrimenti si setta #@NumerazioniDaDare con colonna 1 = P.I. e colonna 3 = I											
					List<TipoNumerazioneBean> listaTipiNumerazioneDaDare = new ArrayList<TipoNumerazioneBean>();
					TipoProvenienza lTipoProvenienza = null;
					if (archivioBean.getFlgTipoProv() != null) {
						if (archivioBean.getFlgTipoProv().equalsIgnoreCase("I")) {							
							TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();							
							if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_REGISTRO_PG_X_PROT_INTERNA")) {
								lTipoNumerazioneBean.setSigla(null);
								lTipoNumerazioneBean.setCategoria("PG");
							} else {
								lTipoNumerazioneBean.setSigla("P.I.");
								lTipoNumerazioneBean.setCategoria("I");
							}							
							listaTipiNumerazioneDaDare.add(lTipoNumerazioneBean);
							lTipoProvenienza = TipoProvenienza.INTERNA;
						} else if (archivioBean.getFlgTipoProv().equalsIgnoreCase("U")) {
							TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();							
							lTipoNumerazioneBean.setSigla(null);
							lTipoNumerazioneBean.setCategoria("PG");			
							listaTipiNumerazioneDaDare.add(lTipoNumerazioneBean);
							lTipoProvenienza = TipoProvenienza.USCITA;
						}			
					}		
								
					CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
					creaModDocumentoInBean.setTipoNumerazioni(listaTipiNumerazioneDaDare);
					creaModDocumentoInBean.setFlgTipoProv(lTipoProvenienza);
				
					XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
					input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));
					
					DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
					StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
					if(output.isInError()) {
						mappaErrori.put(archivioBean.getIdUdFolder(), output.getDefaultMessage());
					} else {
						listaRecordCorrettamenteProtocollati.add(archivioBean); 
					}
				} else {
					listaRecordCorrettamenteProtocollati.add(archivioBean);
				}
			}
		}
		bean.setListaRecord(listaRecordCorrettamenteProtocollati);
		bean.setErrorMessages(mappaErrori);
		return bean;
	}
	
	public FirmaMassivaFilesBean getListaFileDaTimbrareEFirmareAfterProtocolla(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<FileDaFirmareBean> lListaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		HashMap<String, String> mappaErrori = new HashMap<>();
		List<String> listaIdUdInErrore = new ArrayList<>();
		for (int i = 0; i < bean.getListaRecord().size(); i++) {
			String idUd = bean.getListaRecord().get(i).getIdUdFolder();
			// Recupero il file primario e gli allegati dell'unità documentale
			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
			lRecuperaDocumentoInBean.setIdUd(new BigDecimal(idUd));
			RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), lAurigaLoginBean, lRecuperaDocumentoInBean);
			if(!lRecuperaDocumentoOutBean.isInError()) {
				List<FileDaFirmareBean> listaAllegatiDaFirmareUd = new ArrayList<FileDaFirmareBean>();
				DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
				ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());			
				//la variabile documento ha al suo interno tutti i valori come nomeDocumento, allegati,....
				ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);
				if (StringUtils.isNotBlank(documento.getUriFilePrimario()) || (documento.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(documento.getFilePrimarioOmissis().getUriFile()))) {				
					if (StringUtils.isNotBlank(documento.getUriFilePrimario())) {
						FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
						lFileDaFirmareBean.setIdFile(documento.getIdDocPrimario() != null ? String.valueOf(documento.getIdDocPrimario()) : null);
						lFileDaFirmareBean.setNomeFile(documento.getNomeFilePrimario());
						lFileDaFirmareBean.setUri(documento.getUriFilePrimario());
						lFileDaFirmareBean.setIdUd(idUd);
						lFileDaFirmareBean.setInfoFile(documento.getInfoFile());
						lFileDaFirmareBean.setCodiceTipoRelazione("P");
						listaAllegatiDaFirmareUd.add(lFileDaFirmareBean);
					}				
					if(documento.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(documento.getFilePrimarioOmissis().getUriFile())) {
						FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
						lFileDaFirmareBeanOmissis.setIdFile(documento.getFilePrimarioOmissis().getIdDoc() != null ? String.valueOf(documento.getFilePrimarioOmissis().getIdDoc()) : null);
						lFileDaFirmareBeanOmissis.setNomeFile(documento.getFilePrimarioOmissis().getNomeFile());
						lFileDaFirmareBeanOmissis.setUri(documento.getFilePrimarioOmissis().getUriFile());
						lFileDaFirmareBeanOmissis.setIdUd(idUd);
						lFileDaFirmareBeanOmissis.setInfoFile(documento.getFilePrimarioOmissis().getInfoFile());
						lFileDaFirmareBeanOmissis.setCodiceTipoRelazione("P");
						listaAllegatiDaFirmareUd.add(lFileDaFirmareBeanOmissis);
					}				
				} 
				if (documento.getListaAllegati() != null) {
					for (AllegatoProtocolloBean allegatoUdBean : documento.getListaAllegati()) {
						if ((StringUtils.isNotBlank(allegatoUdBean.getUriFileAllegato()) ||  StringUtils.isNotBlank(allegatoUdBean.getUriFileOmissis())) && 
								allegatoUdBean.getFlgParteDispositivo() != null && allegatoUdBean.getFlgParteDispositivo()) {						
							if (StringUtils.isNotBlank(allegatoUdBean.getUriFileAllegato())) {
								FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
								lFileDaFirmareBean.setIdFile(allegatoUdBean.getIdDocAllegato() != null ? String.valueOf(allegatoUdBean.getIdDocAllegato()) : null);
								lFileDaFirmareBean.setNomeFile(allegatoUdBean.getNomeFileAllegato());
								lFileDaFirmareBean.setUri(allegatoUdBean.getUriFileAllegato());
								lFileDaFirmareBean.setIdUd(idUd);
								lFileDaFirmareBean.setInfoFile(allegatoUdBean.getInfoFile());
								lFileDaFirmareBean.setCodiceTipoRelazione("ALL");
								lFileDaFirmareBean.setNroProgAllegato(allegatoUdBean.getNumeroProgrAllegato() != null ? Integer.parseInt(allegatoUdBean.getNumeroProgrAllegato()) : null);
								listaAllegatiDaFirmareUd.add(lFileDaFirmareBean);	
							}
							if (StringUtils.isNotBlank(allegatoUdBean.getUriFileOmissis())) {
								FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
								lFileDaFirmareBeanOmissis.setIdFile(allegatoUdBean.getIdDocOmissis() != null ? String.valueOf(allegatoUdBean.getIdDocOmissis()) : null);
								lFileDaFirmareBeanOmissis.setNomeFile(allegatoUdBean.getNomeFileOmissis());
								lFileDaFirmareBeanOmissis.setUri(allegatoUdBean.getUriFileOmissis());
								lFileDaFirmareBeanOmissis.setIdUd(idUd);
								lFileDaFirmareBeanOmissis.setInfoFile(allegatoUdBean.getInfoFileOmissis());
								lFileDaFirmareBeanOmissis.setCodiceTipoRelazione("ALL");
								lFileDaFirmareBeanOmissis.setNroProgAllegato(allegatoUdBean.getNumeroProgrAllegato() != null ? Integer.parseInt(allegatoUdBean.getNumeroProgrAllegato()) : null);
								listaAllegatiDaFirmareUd.add(lFileDaFirmareBeanOmissis);
							}
						}
					}
				}
				// L'unità documentale deve essere firmata, aggiunto allegati e
				// primario alla lista file da firmare
				for (FileDaFirmareBean allegatoUd : listaAllegatiDaFirmareUd) {
					lListaFileDaFirmare.add(allegatoUd);
				}
			} else {
				listaIdUdInErrore.add(bean.getListaRecord().get(i).getIdUdFolder());
				mappaErrori.put(bean.getListaRecord().get(i).getIdUdFolder(), lRecuperaDocumentoOutBean.getDefaultMessage());
			}
		}
		// Rimuovo le idUd andate in errore dalla lista di elaborazione dei passi succeessivi
		Iterator<FileDaFirmareBean> iterator = lListaFileDaFirmare.iterator();
		while (iterator.hasNext()) {
			FileDaFirmareBean current = iterator.next();
			if (listaIdUdInErrore.contains(current.getIdUd())){
				iterator.remove();
			}
		}
		lFirmaMassivaFilesBean.setFiles(lListaFileDaFirmare);		
		lFirmaMassivaFilesBean.setErrorMessages(mappaErrori);
		return lFirmaMassivaFilesBean;
	}
	
	public FirmaMassivaFilesBean timbraFilePerProtocollaTimbraEFirma(FirmaMassivaFilesBean bean) throws Exception {
		HashMap<String, String> mappaErrori = new HashMap<>();
		List<String> listaIdUdNonTimbrate = new ArrayList<>();
		for (FileDaFirmareBean lFileDaTimbrareBean : bean.getFiles()) {
			MimeTypeFirmaBean lMimeType = lFileDaTimbrareBean.getInfoFile();
			if (lMimeType != null && !lMimeType.isFirmato()) {
				// Timbro il file
				TimbraResultBean lTimbraResultBean;
				if ("P".equalsIgnoreCase(lFileDaTimbrareBean.getCodiceTipoRelazione())) {
					ProtocolloDataSource protocolloDataSource = new ProtocolloDataSource();
					protocolloDataSource.setSession(getSession());
					OpzioniTimbroBean lOpzioniTimbroBean = protocolloDataSource.buildOpzioniApponiTimbro(lFileDaTimbrareBean.getIdUd(), lFileDaTimbrareBean, bean.getOpzioniTimbro());
					// Timbro il file
					lTimbraResultBean = new TimbraUtility().timbra(lOpzioniTimbroBean, getSession());
				} else {
					ProtocolloDataSource protocolloDataSource = new ProtocolloDataSource();
					protocolloDataSource.setSession(getSession());
					OpzioniTimbroBean lOpzioniTimbroBean = protocolloDataSource.buildOpzioniApponiTimbroAllegato(lFileDaTimbrareBean.getIdUd(), lFileDaTimbrareBean, bean.getOpzioniTimbro());
					// Timbro il file
					lTimbraResultBean = new TimbraUtility().timbra(lOpzioniTimbroBean, getSession());
				}
				
				// Verifico se la timbratura è andata a buon fine
				if (lTimbraResultBean.isResult()) {
					// Aggiungo il file timbrato nella lista dei file da pubblicare
					lFileDaTimbrareBean.setUri(lTimbraResultBean.getUri());
					File lFileTimbrato = StorageImplementation.getStorage().extractFile(lFileDaTimbrareBean.getUri());		
					MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
					lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lFileTimbrato.toURI().toString(), lFileDaTimbrareBean.getNomeFile(), false, null);
					lFileDaTimbrareBean.setNomeFile(FilenameUtils.getBaseName(lFileDaTimbrareBean.getNomeFile()) + ".pdf");
					lMimeTypeFirmaBean.setFirmato(false);
					lMimeTypeFirmaBean.setFirmaValida(false);
					lMimeTypeFirmaBean.setConvertibile(false);
					lMimeTypeFirmaBean.setDaScansione(false);
					lFileDaTimbrareBean.setInfoFile(lMimeTypeFirmaBean);
				} else {
					// La timbratura è fallita, setto l'errore e tengo traccia della idUd in modo da non proseguire con la sua elaborazione
					mappaErrori.put(lFileDaTimbrareBean.getIdUd(), "Il documento è stato protocollato. Non è stato tuttavia portare a termine l'operazione di firma");
					listaIdUdNonTimbrate.add(lFileDaTimbrareBean.getIdUd());
				}
			}
		}			
		// Rimuovo le idUd andate in errore dalla lista di elaborazione dei passi succeessivi
		List<FileDaFirmareBean> listaFile = bean.getFiles();
		Iterator<FileDaFirmareBean> iterator = listaFile.iterator();
		while (iterator.hasNext()) {
			FileDaFirmareBean current = iterator.next();
			if (listaIdUdNonTimbrate.contains(current.getIdUd())){
				iterator.remove();
			}
		}
		bean.setFiles(listaFile);
		bean.setErrorMessages(mappaErrori);
		return bean;
	}
	
	public FirmaMassivaFilesBean aggiornaDocumentoAfterProtocollazioneTimbraEFirma(FirmaMassivaFilesBean pFirmaMassivaFilesBean) throws Exception {
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		HashMap<String, String> filesInError = new HashMap<>();
		// Mantengouna lista di idUd versionate correttamente
		List<String> listaIdUdAggiornateCorretamente = new ArrayList<>();
		for (FileDaFirmareBean lFileDaFirmareBean : pFirmaMassivaFilesBean.getFiles()) {
			if (!listaIdUdAggiornateCorretamente.contains(lFileDaFirmareBean.getIdUd())) {
				listaIdUdAggiornateCorretamente.add(lFileDaFirmareBean.getIdUd());
			}
		}
		for (FileDaFirmareBean lFileDaFirmareBean : pFirmaMassivaFilesBean.getFiles()) {
			try {
				String modalitaFirma = ParametriDBUtil.getParametroDB(getSession(), "MODALITA_FIRMA");
				String appletFirmaSpec = ParametriDBUtil.getParametroDB(getSession(), "APPLET_FIRMA_SPEC");
				if ((StringUtils.isBlank(modalitaFirma) || modalitaFirma.equalsIgnoreCase("APPLET")) && StringUtils.isBlank(appletFirmaSpec)) {
					// Aggiungo l'estensione p7m al nome del file firmato perchè l'applet di firma multipla non lo fa
					boolean isCAdES = lFileDaFirmareBean.getInfoFile() != null && lFileDaFirmareBean.getInfoFile().isFirmato() && lFileDaFirmareBean.getInfoFile().getTipoFirma().startsWith("CAdES");
					if (isCAdES && !lFileDaFirmareBean.getNomeFile().toLowerCase().endsWith(".p7m")) {
						lFileDaFirmareBean.setNomeFile(lFileDaFirmareBean.getNomeFile() + ".p7m");
					}
				}
				boolean firmaEseguita = lFileDaFirmareBean.getFirmaEseguita() != null && lFileDaFirmareBean.getFirmaEseguita();
				if (!firmaEseguita || lFileDaFirmareBean.getInfoFile() == null || !lFileDaFirmareBean.getInfoFile().isFirmato() || !lFileDaFirmareBean.getInfoFile().isFirmaValida()) {
					throw new Exception("Firma non presente o non valida");
				}
				versionaDocumento(lFileDaFirmareBean);				
			} catch (Exception e) {
				filesInError.put(lFileDaFirmareBean.getIdUd(), "Il documento è stato protocollato. Non è stato tuttavia portare a termine l'operazione di firma");
				// Aggiorno la lista delle idUd aggiornate correttamente, rimuovendo quella attuale
				listaIdUdAggiornateCorretamente.remove(lFileDaFirmareBean.getIdUd());
			}
		}
		
		// Chiamo la DmpkCoreFirmadocumento per aggiornare lo stato delle idUd elaborate correttamente
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();
		for (String idUdDaAggiornare : listaIdUdAggiornateCorretamente) {
			DmpkCoreFirmadocumentoBean input = new DmpkCoreFirmadocumentoBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdudin(idUdDaAggiornare != null && !"".equals(idUdDaAggiornare) ? new BigDecimal(idUdDaAggiornare) : null);
			input.setFlgappostarifiutatain("A");
			
			DmpkCoreFirmadocumento dmpkCoreFirmaDocumento = new DmpkCoreFirmadocumento();
			StoreResultBean<DmpkCoreFirmadocumentoBean> output = dmpkCoreFirmaDocumento.execute(getLocale(), loginBean, input);
	
			if (output.getDefaultMessage() != null) {
				filesInError.put(idUdDaAggiornare, output.getDefaultMessage());
			}
		}
				
		lFirmaMassivaFilesBean.setErrorMessages(filesInError);
		return lFirmaMassivaFilesBean;
	}
	
	
	public FilePerBustaConTimbroBean recuperaFilePerTimbroConBusta(ArchivioBean bean)
			throws Exception {

		HttpSession session = getSession();
	
		return callStoreDmpkEepositoryGuiLoadFilePerTimbroConBusta(bean, session);
	}

	/**
	 * @param bean
	 * @param token
	 * @param idUserLavoro
	 * @return
	 * @throws Exception 
	 */
	public FilePerBustaConTimbroBean callStoreDmpkEepositoryGuiLoadFilePerTimbroConBusta(ArchivioBean bean,
			HttpSession session) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRepositoryGuiLoadfilepertimbroconbustaBean input = new DmpkRepositoryGuiLoadfilepertimbroconbustaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudin(bean.getIdUdFolder() != null && !"".equals(bean.getIdUdFolder()) ? new BigDecimal(bean.getIdUdFolder()) : null);
		input.setIddocin(bean.getIdDocPrimario() != null && !"".equals(bean.getIdDocPrimario()) ? new BigDecimal(bean.getIdDocPrimario()) : null);

		DmpkRepositoryGuiLoadfilepertimbroconbusta store = new DmpkRepositoryGuiLoadfilepertimbroconbusta();
		StoreResultBean<DmpkRepositoryGuiLoadfilepertimbroconbustaBean> output = store.execute(getLocale(), loginBean,input);

		// Leggo l'esito
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException("Errore durante recupero informazioni per la busta pdf del timbro: " + output.getDefaultMessage());
			} else {
				addMessage("Errore durante recupero informazioni per la busta pdf del timbro: " + output.getDefaultMessage(), "", MessageType.WARNING);
			}
		} 
		
		FilePerBustaConTimbroBean result = new FilePerBustaConTimbroBean();
				
		// Restituisco i dati
		result.setFlgVersionePubblicabile(output.getResultBean().getFlgpubblconomissisout()==1 ? true : false);                                 
        
		if (output.getResultBean().getListafileout() != null && !output.getResultBean().getListafileout().equalsIgnoreCase("")){
			List<InfoFilePerBustaTimbro> listaFileDaAggiungereBusta    = new ArrayList<InfoFilePerBustaTimbro>();
			listaFileDaAggiungereBusta = XmlListaUtility.recuperaLista(output.getResultBean().getListafileout(), InfoFilePerBustaTimbro.class);
			result.setListaFilePerBustaTimbro(listaFileDaAggiungereBusta);
		}		
				
		return result;
	}
	
	public HashMap<String, String> getMappaTipiDocFlgRichiestaFirmaDigitale(HashSet<String> setTipiDocumento) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String tipiDocumento = null;
		if (setTipiDocumento != null) {
			for (String tipoDoc : setTipiDocumento) {
				if(StringUtils.isNotBlank(tipoDoc)) {
					if(tipiDocumento == null) {
						tipiDocumento = tipoDoc;
					} else {
						tipiDocumento += "," + tipoDoc;
					}
				}
			}
		}
		HashMap<String, String> mappaTipiDocFlgRichiestaFirmaDigitale = new HashMap<String, String>();
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPO_DOC");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + (loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "") + "|*|ID_DOC_TYPE|*|" + tipiDocumento);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), loginBean, lDmpkLoadComboDmfn_load_comboBean);
		if(!lStoreResultBean.isInError()) {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<TipoDocumentoBean> lListXml = XmlListaUtility.recuperaLista(xmlLista, TipoDocumentoBean.class);
			for (TipoDocumentoBean lRiga : lListXml) {
				mappaTipiDocFlgRichiestaFirmaDigitale.put(lRiga.getIdTipoDocumento(), lRiga.getFlgRichiestaFirmaDigitale());
			}
		}
		return mappaTipiDocFlgRichiestaFirmaDigitale;
	}
	
	public void controlloFirmeDocIstruttoriaXNumerazione(ArchivioBean bean) throws Exception {
		
		HashMap<String, String> mappaErroriFile = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		HashSet<String> setTipiDocumento = new HashSet<String>();
		if (bean.getListaDocumentiIstruttoria() != null) {
			for (AllegatoProtocolloBean allegato : bean.getListaDocumentiIstruttoria()) {
				if (StringUtils.isNotBlank(allegato.getUriFileAllegato()) && StringUtils.isNotBlank(allegato.getNomeFileAllegato())) {
					if(StringUtils.isNotBlank(allegato.getListaTipiFileAllegato())) {
						setTipiDocumento.add(allegato.getListaTipiFileAllegato());
					}
				}
			}
		}	
		HashMap<String, String> mappaTipiDocFlgRichiestaFirmaDigitale = getMappaTipiDocFlgRichiestaFirmaDigitale(setTipiDocumento);		
		boolean valid = true;
		sb.append("Alcuni file non hanno superato i controlli previsti sulla presenza e validità di firma digitale:<br/>"); 
		sb.append("<ul>");
		if (bean.getListaDocumentiIstruttoria() != null) {
			int n = 0;
			for (AllegatoProtocolloBean allegato : bean.getListaDocumentiIstruttoria()) {
				n++;
				String flgRichiestaFirmaDigitaleAllegato = StringUtils.isNotBlank(allegato.getListaTipiFileAllegato()) ? mappaTipiDocFlgRichiestaFirmaDigitale.get(allegato.getListaTipiFileAllegato()) : null;
				if (StringUtils.isNotBlank(flgRichiestaFirmaDigitaleAllegato)) {
					// Se si protocollo o repertoria un documento di istruttoria
					if(StringUtils.isNotBlank(allegato.getEstremiProtUd())) {
						// in questo caso fileOp non andrà mai chiamato perchè dopo la protocollazione/repertoriazione del documento di istruttoria il file viene sempre rigenerato
						// quindi non avrò mai il caso in cui il file è stato salvato precedentemente alla protocollazione/repertoriazione del documento di istruttoria
						if (StringUtils.isNotBlank(allegato.getUriFileAllegato()) && StringUtils.isNotBlank(allegato.getNomeFileAllegato())) {
							if ("P".equals(flgRichiestaFirmaDigitaleAllegato) && allegato.getInfoFile() != null && !allegato.getInfoFile().isFirmato()) {
								valid = false;
								sb.append("<li>Per la tipologia del documento di istruttoria N. " + n + " è obbligatorio che il file sia firmato digitalmente.</li>");
								mappaErroriFile.put("" + n, "Per la tipologia del documento di istruttoria è obbligatorio che il file sia firmato digitalmente");
							} else if ("V".equals(flgRichiestaFirmaDigitaleAllegato) && allegato.getInfoFile() != null && (!allegato.getInfoFile().isFirmato() || !allegato.getInfoFile().isFirmaValida())) {
								valid = false;
								sb.append("<li>Per la tipologia del documento di istruttoria N. " + n + " è obbligatorio che il file sia firmato digitalmente con firma valida.</li>");
								mappaErroriFile.put("" + n, "Per la tipologia del documento di istruttoria è obbligatorio che il file sia firmato digitalmente con firma valida");
							}						
						} else {
							if ("P".equals(flgRichiestaFirmaDigitaleAllegato)) {
								valid = false;
								sb.append("<li>Per la tipologia del documento di istruttoria N. " + n + " è obbligatorio che ci sia un file firmato digitalmente.</li>");
								mappaErroriFile.put("" + n, "Per la tipologia del documento di istruttoria è obbligatorio che ci sia un file firmato digitalmente");
							} else if ("V".equals(flgRichiestaFirmaDigitaleAllegato)) {
								valid = false;
								sb.append("<li>Per la tipologia del documento di istruttoria N. " + n + " è obbligatorio che ci sia un file firmato digitalmente con firma valida.</li>");
								mappaErroriFile.put("" + n, "Per la tipologia del documento di istruttoria è obbligatorio che ci sia un file firmato digitalmente con firma valida");
							}	
						}
					}
				}
			}
		}
		sb.append("</ul>");
		if(!valid) {
			mLogger.error(sb.toString());
			if(getExtraparams().get("isAttivaGestioneErroriFile") != null && "true".equals(getExtraparams().get("isAttivaGestioneErroriFile"))) {
				addMessage("Alcuni file non hanno superato i controlli previsti sulla presenza e validità di firma digitale", "", MessageType.ERROR);
				bean.setErroriFile(mappaErroriFile);	
			} else {
				throw new StoreException(sb.toString());
			}
		}
	}
	
}