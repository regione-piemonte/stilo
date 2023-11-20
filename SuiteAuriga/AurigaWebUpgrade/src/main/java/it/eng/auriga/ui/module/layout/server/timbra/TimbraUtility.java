/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.PdfFileSpecification;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocExtractvermodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocTrovamodelliBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrodigregBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrospecxtipoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.ArchivioDatasource;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.filePerBustaTimbro.FilePerBustaConTimbroBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.filePerBustaTimbro.InfoFilePerBustaTimbro;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.ModelliDocBean;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.ModelliDocXmlBean;
import it.eng.client.DmpkModelliDocExtractvermodello;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkModelliDocTrovamodelli;
import it.eng.client.DmpkRegistrazionedocGettimbrodigreg;
import it.eng.client.DmpkRegistrazionedocGettimbrospecxtipo;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.fileOperation.clientws.CodificaTimbro;
import it.eng.fileOperation.clientws.PaginaTimbro;
import it.eng.fileOperation.clientws.PaginaTimbro.Pagine;
import it.eng.fileOperation.clientws.PosizioneTimbroNellaPagina;
import it.eng.fileOperation.clientws.TestoTimbro;
import it.eng.fileOperation.clientws.TipoPagina;
import it.eng.fileOperation.clientws.TipoRotazione;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.TimbraUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniCopertinaTimbroBean;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

public class TimbraUtility {

	private static final Logger log = Logger.getLogger(TimbraUtility.class);
	
	public TimbraResultBean timbra(OpzioniTimbroBean bean, HttpSession session) throws StorageException, FileNotFoundException {
		return this.timbra(bean, session, new Locale("it","IT"));
	}


	public TimbraResultBean timbra(OpzioniTimbroBean bean, HttpSession session, Locale locale) throws StorageException, FileNotFoundException {
		log.debug("Invoco timbra per il file " + bean.getNomeFile());
		TimbraResultBean lTimbraResultBean = new TimbraResultBean();
		OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail = populatePreference(bean);
		boolean generaPdfA = ParametriDBUtil.getParametroDBAsBoolean(session, "TIMBRATURA_ABILITA_PDFA");		
		TimbraUtil lTimbraUtil = new TimbraUtil();
		String uri = bean.getUri();
		File file = null;
		if (!bean.isRemote()) {
			file = StorageImplementation.getStorage().extractFile(uri);
		} else {
			RecuperoFile lRecuperoFile = new RecuperoFile();
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(uri);
			FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(session), AurigaUserUtil.getLoginInfo(session), lFileExtractedIn);
			file = out.getExtracted();
		}
		File fileDaTimbrare = null;
		String nomeFile = bean.getNomeFile();
		try {
			if(bean.getMimetype() == null) {
				MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(file.toURI().toString(), nomeFile, false, null);
				bean.setMimetype(lMimeTypeFirmaBean != null ? lMimeTypeFirmaBean.getMimetype() : null);
			}			
			log.debug("Il file ha mimetype " + bean.getMimetype());
			
			/*FUNZIONE PER TIMBRARE I FILE FIRMATI CREANDO UN PDF AVENTE I DATI DI TIMBRATURA E IL FILE FIRMATO COME ALLEGATO*/
			if(StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(session, "ATTIVA_BUSTA_PDF_FILE_FIRMATO")) &&
					   "true".equalsIgnoreCase(ParametriDBUtil.getParametroDB(session, "ATTIVA_BUSTA_PDF_FILE_FIRMATO")) &&
					   /*Se il tipo di firma e conforme per stampa NON deve fare la busta ma deve timbrare lo sbustato come prima*/
					   (bean.getFinalita()==null || bean.getFinalita()!=null && !bean.getFinalita().equalsIgnoreCase("CONFORMITA_ORIG_DIGITALE_STAMPA"))) {
				
				MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(file.toURI().toString(), nomeFile, false, null);
				
				if(lMimeTypeFirmaBean.isFirmato()) {
					
					List<InfoFilePerBustaTimbro> listaFileDaAggiungereAllaBusta = recuperaFilePerBustaTimbro(bean, session, file, nomeFile);					
					
					TimbraResultBean timbraResultBean = creaTimbraturaPerFileFirmato(session, locale, generaPdfA, lOpzioniTimbroAttachEmail, bean.getIdUd(), bean.getIdDoc(), bean.getFinalita(), listaFileDaAggiungereAllaBusta);
					
					if(timbraResultBean!=null) {
						return timbraResultBean;
					}
				}
			}
							
			
			if (!bean.getMimetype().equals("application/pdf")) {
				try {
					log.debug("Non è application/pdf, lo converto");
					String uriFilePdf = StorageImplementation.getStorage().storeStream(lTimbraUtil.converti(file, bean.getNomeFile()));
					fileDaTimbrare = StorageImplementation.getStorage().extractFile(uriFilePdf);
					nomeFile = bean.getNomeFile() + ".pdf";
				} catch (Exception e) {
					log.error("Errore nella conversione del file " + bean.getNomeFile() + " " + e.getMessage(), e);
					throw new Exception("Il formato del file non consente di apporvi il timbro", e);
				}
			} else {
				fileDaTimbrare = file;
			}
			
			String lUriFinale;
			if("COPIA_CONFORME_CUSTOM".equalsIgnoreCase(bean.getFinalita())) {
				log.debug("Creo il file di conformità custom");
				
				String path = "/images/loghiXConformitaCustom/";
				
				String nomeImmagineLogoDB = ParametriDBUtil.getParametroDB(session, "NOME_IMMAGINE_COPIA_CONFORME_CUSTOM");
				
				String nomeImmagineLogo = StringUtils.isNotBlank(nomeImmagineLogoDB) ? nomeImmagineLogoDB :  "logoConformita.png";
							
				File imgLogo = new File(session.getServletContext().getRealPath(path + nomeImmagineLogo));
				
				String percentualeLarghezzaTesto = ParametriDBUtil.getParametroDB(session, "PERC_LARGHEZZA_TESTO_COPIA_CONFORME_CUSTOM");
				String testoAggiuntivo = ParametriDBUtil.getParametroDB(session, "TESTO_AGGIUNTIVO_COPIA_CONFORME_CUSTOM");
				
				lUriFinale = StorageImplementation.getStorage().storeStream(lTimbraUtil.timbraConformitaCustom(fileDaTimbrare, nomeFile , lOpzioniTimbroAttachEmail, imgLogo, percentualeLarghezzaTesto, testoAggiuntivo, generaPdfA));
			}else {
				log.debug("Timbro il file PDF");
				lUriFinale = StorageImplementation.getStorage().storeStream(lTimbraUtil.timbra(fileDaTimbrare, nomeFile , lOpzioniTimbroAttachEmail, generaPdfA));
			}
			
			lTimbraResultBean.setResult(true);
			lTimbraResultBean.setUri(lUriFinale);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			lTimbraResultBean.setResult(false);
			lTimbraResultBean.setError(e.getMessage());
			return lTimbraResultBean;
		}
		return lTimbraResultBean;
	}


	/**
	 * @param bean
	 * @param session 
	 * @param nomeFileFirmato 
	 * @param fileFirmato 
	 * @return 
	 * @throws Exception
	 */
	public List<InfoFilePerBustaTimbro> recuperaFilePerBustaTimbro(OpzioniTimbroBean bean, HttpSession session, File fileFirmato, String nomeFileFirmato) throws Exception {
		List<InfoFilePerBustaTimbro> listaFileDaAggiungereAllaBusta = new ArrayList<InfoFilePerBustaTimbro>();

		
		if(bean.getFlgVersionePubblicabile()!=null) {
			
			ArchivioBean input = new ArchivioBean();
			input.setIdUdFolder(bean.getIdUd());
			input.setIdDocPrimario(bean.getIdDoc());
			ArchivioDatasource archivioDataSource = new ArchivioDatasource();
			FilePerBustaConTimbroBean result = archivioDataSource.callStoreDmpkEepositoryGuiLoadFilePerTimbroConBusta(input, session);
						
			if(bean.getFlgVersionePubblicabile()) {
				if(result.getListaFilePerBustaTimbro()!=null && result.getListaFilePerBustaTimbro().size()>0) {						
					for(InfoFilePerBustaTimbro filePerBustaTimbro : result.getListaFilePerBustaTimbro()) {								
						if(filePerBustaTimbro.getVersione().contains("P")){
							listaFileDaAggiungereAllaBusta.add(filePerBustaTimbro);
						}								
					}
				}else {    /*Se la store non mi ritorna file devo aggiungere solo quello sulla quale ho selezionato l'operazione di timbratura*/
					InfoFilePerBustaTimbro fileTimbrato = new InfoFilePerBustaTimbro();
					fileTimbrato.setFile(fileFirmato);
					fileTimbrato.setNomeFile(nomeFileFirmato);
					
					listaFileDaAggiungereAllaBusta.add(fileTimbrato);
				}
			}else if(!bean.getFlgVersionePubblicabile()) {
				if(result.getListaFilePerBustaTimbro()!=null && result.getListaFilePerBustaTimbro().size()>0) {						
					for(InfoFilePerBustaTimbro filePerBustaTimbro : result.getListaFilePerBustaTimbro()) {								
						if(filePerBustaTimbro.getVersione().contains("I")){
							listaFileDaAggiungereAllaBusta.add(filePerBustaTimbro);
						}								
					}
				}else {    /*Se la store non mi ritorna file devo aggiungere solo quello sulla quale ho selezionato l'operazione di timbratura*/
					InfoFilePerBustaTimbro fileTimbrato = new InfoFilePerBustaTimbro();
					fileTimbrato.setFile(fileFirmato);
					fileTimbrato.setNomeFile(nomeFileFirmato);
					
					listaFileDaAggiungereAllaBusta.add(fileTimbrato);
				}
			}			
		}else {    /*Se non sono passato per la store che mi ritorna i file da allegare alla busta, devo aggiungere solo quello sulla quale ho selezionato l'operazione di timbratura*/
			InfoFilePerBustaTimbro fileTimbrato = new InfoFilePerBustaTimbro();
			fileTimbrato.setFile(fileFirmato);
			fileTimbrato.setNomeFile(nomeFileFirmato);
			
			listaFileDaAggiungereAllaBusta.add(fileTimbrato);
		}
		
		for(InfoFilePerBustaTimbro infoFileBustaTimbro : listaFileDaAggiungereAllaBusta) {
			String uriFile = infoFileBustaTimbro.getUriFile();
			File fileBustaTimbro = StorageImplementation.getStorage().extractFile(uriFile);
			infoFileBustaTimbro.setFile(fileBustaTimbro);
		}
		
		return listaFileDaAggiungereAllaBusta;
	}

	public static TimbraResultBean creaTimbraturaPerFileFirmato(HttpSession session,
			Locale locale, boolean generaPdfA, OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail,
			String idUd, String idDoc, String finalita, List<InfoFilePerBustaTimbro> listaFileDaAggiungereAllaBusta) throws Exception {
		TimbraResultBean timbraResultBean = null;
		TimbraUtil lTimbraUtil = new TimbraUtil();

		try {
			
			File pdfModelloTimbratura = creaModelloTimbratura(session, idDoc, idUd, locale, finalita);
			
//			InputStream inputStreamModelloTimbrato = lTimbraUtil.timbra(pdfModelloTimbratura, bean.getNomeFile() , lOpzioniTimbroAttachEmail, generaPdfA);
			
			File bustaTimbrataFile = addAttacchToPdf(pdfModelloTimbratura, listaFileDaAggiungereAllaBusta);
			
			String lUriFinale = StorageImplementation.getStorage().storeStream(new FileInputStream(bustaTimbrataFile));
			
			timbraResultBean = new TimbraResultBean();
			timbraResultBean.setResult(true);
			timbraResultBean.setUri(lUriFinale);
			
		} catch (Exception e) {
			log.error("Errore durante la creazione della busta di timbratura per il file firmato: " + e.getMessage(), e);
			throw new Exception("Errore durante la creazione della busta di timbratura per il file firmato: " + e.getMessage(), e);
		}
		
		return timbraResultBean;				
	}
	
	private static File creaModelloTimbratura(HttpSession session, String idDoc, String idUd, Locale locale, String finalita) throws Exception {

		String datiTimbratura = getDatiTimbratura(session, idUd, idDoc, locale, finalita);
		
//		String uriModello = recuperaUriModello(session, locale);
		
		ModelliDocBean modelloDocBean = recuperaModello(session, locale);
		
//		Map<String, Object> mappaValoriTimbratura = ModelliUtil.createMapToFillTemplateFromSezioneCache(datiTimbratura, true);
		
		FileDaFirmareBean lFileDaFirmareBean = ModelliUtil.fillTemplate(null, modelloDocBean.getIdModello(), datiTimbratura, true, session);
		
		File modelloTimbratura = StorageImplementation.getStorage().extractFile(lFileDaFirmareBean.getUri());

		return modelloTimbratura;				

	}

	private static ModelliDocBean recuperaModello(HttpSession session, Locale locale) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
		
		DmpkModelliDocTrovamodelliBean input = new DmpkModelliDocTrovamodelliBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		input.setNomemodelloio("BUSTA_ACCOMPAGNAMENTO_CON_TIMBRO_REGISTRAZIONE");		

		DmpkModelliDocTrovamodelli dmpkModelliDocTrovamodelli = new DmpkModelliDocTrovamodelli();

		StoreResultBean<DmpkModelliDocTrovamodelliBean> output = dmpkModelliDocTrovamodelli.execute(locale, loginBean, input);
		
		if (output.isInError()){
			throw new StoreException(StringUtils.isNotBlank(output.getDefaultMessage()) ? output.getDefaultMessage() : "Non è stato possibile recuperare il modello della lista accompagnatoria protocollazioni");
		}

		List<ModelliDocBean> data = new ArrayList<ModelliDocBean>();
		if(output.getResultBean().getListaxmlout() != null) {
			List<ModelliDocXmlBean> lista = XmlListaUtility.recuperaLista(output.getResultBean().getListaxmlout(), ModelliDocXmlBean.class);
			if(lista != null) {
				for(ModelliDocXmlBean lModelliDocXmlBean : lista) {
					ModelliDocBean lModelliDocBean = new ModelliDocBean();
					lModelliDocBean.setIdModello(lModelliDocXmlBean.getIdModello());
					lModelliDocBean.setNomeModello(lModelliDocXmlBean.getNome());
					lModelliDocBean.setDesModello(lModelliDocXmlBean.getDescrizione());
					lModelliDocBean.setFormatoFile(lModelliDocXmlBean.getNomeFormatoFile());
					lModelliDocBean.setTipoEntitaAssociata(lModelliDocXmlBean.getTipoEntitaAssociata());
					lModelliDocBean.setIdEntitaAssociata(lModelliDocXmlBean.getIdEntitaAssociata());
					lModelliDocBean.setNomeEntitaAssociata(lModelliDocXmlBean.getNomeEntitaAssociata());
					lModelliDocBean.setNomeTabella(lModelliDocXmlBean.getNomeTabella());
					lModelliDocBean.setIdDoc(lModelliDocXmlBean.getIdDoc());
					lModelliDocBean.setNote(lModelliDocXmlBean.getAnnotazioni());
					lModelliDocBean.setFlgProfCompleta(lModelliDocXmlBean.getFlgProfCompleta() != null && "1".equals(lModelliDocXmlBean.getFlgProfCompleta()));	
					lModelliDocBean.setUriFileModello(lModelliDocXmlBean.getUriFile());	
					lModelliDocBean.setNomeFileModello(lModelliDocXmlBean.getNomeFile());	
					lModelliDocBean.setFlgGeneraPdf(lModelliDocXmlBean.getFlgGeneraPdf() != null && "1".equals(lModelliDocXmlBean.getFlgGeneraPdf()));	
					lModelliDocBean.setTipoModello(lModelliDocXmlBean.getTipoModello());					
					lModelliDocBean.setTsCreazione(lModelliDocXmlBean.getTsCreazione() != null ? lModelliDocXmlBean.getTsCreazione() : null);
					lModelliDocBean.setCreatoDa(lModelliDocXmlBean.getDesUteCreazione());
					lModelliDocBean.setTsUltimoAgg(lModelliDocXmlBean.getDesUltimoAgg() != null ? lModelliDocXmlBean.getDesUltimoAgg() : null);
					lModelliDocBean.setUltimoAggEffDa(lModelliDocXmlBean.getIdUteUltimoAgg());
					lModelliDocBean.setFlgValido(lModelliDocXmlBean.getFlgAnnLogico() != null && "0".equals(lModelliDocXmlBean.getFlgAnnLogico()));
					data.add(lModelliDocBean);
				}
			}
		}
		
		if(data.size()>0) {
			return data.get(0);
		}else {
			throw new Exception("Il modello BUSTA_ACCOMPAGNAMENTO_CON_TIMBRO_REGISTRAZIONE non e' stato trovato");
		}
	}


	private String recuperaUriModello(HttpSession session, Locale locale) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
		
		// Recupero l'uri del modello della copertina
		DmpkModelliDocExtractvermodello retrieveVersion = new DmpkModelliDocExtractvermodello();
		DmpkModelliDocExtractvermodelloBean modelloBean = new DmpkModelliDocExtractvermodelloBean();
		modelloBean.setCodidconnectiontokenin(loginBean.getToken());
		modelloBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		modelloBean.setNomemodelloin("BUSTA_ACCOMPAGNAMENTO_CON_TIMBRO_REGISTRAZIONE");

		StoreResultBean<DmpkModelliDocExtractvermodelloBean> resultModello = retrieveVersion.execute(locale, loginBean, modelloBean);
		if (resultModello.isInError()){
			throw new StoreException(StringUtils.isNotBlank(resultModello.getDefaultMessage()) ? resultModello.getDefaultMessage() : "Non è stato possibile recuperare il modello della lista accompagnatoria protocollazioni");
		}

		String uriModelloCopertinaDaCompilare = resultModello.getResultBean().getUriverout();
		
		return uriModelloCopertinaDaCompilare;	
	}


	/**
	 * @param session
	 * @param idUd
	 * @param locale 
	 * @param finalita 
	 * @return 
	 * @throws Exception 
	 */
	public static String getDatiTimbratura(HttpSession session, String idUd, String idDoc, Locale locale, String finalita) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
		
		if(StringUtils.isNotBlank(idUd)) {
			BigDecimal idUdBigDecimal = new BigDecimal(idUd);
			idUd = String.valueOf(idUdBigDecimal.intValue());
		}
		if(StringUtils.isNotBlank(idDoc)) {
			BigDecimal idDocBigDecimal = new BigDecimal(idDoc);		
			idDoc = String.valueOf(idDocBigDecimal.intValue());
		}		
		
		DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
		DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(StringUtils.isNotBlank(idUd) ? idUd : "");
		lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("U");
		lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin("BUSTA_ACCOMPAGNAMENTO_CON_TIMBRO_REGISTRAZIONE");
		
		SezioneCache sezioneCache = new SezioneCache();
		sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("ID_DOC", StringUtils.isNotBlank(idDoc) ? idDoc : ""));
		sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("FINALITA", StringUtils.isNotBlank(finalita) ? finalita : ""));
		
		// Creo gli attributi addizionali
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(sezioneCache);
		lDmpkModelliDocGetdatixgendamodelloInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));
		
		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(locale, loginBean,
				lDmpkModelliDocGetdatixgendamodelloInput);
		
		if (lGetdatixgendamodelloOutput.isInError()) {
			throw new StoreException(lGetdatixgendamodelloOutput);
		}		
		
		String datiModello = lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();				
		
		return datiModello;
	}
	

	public static File addAttacchToPdf (File pdfModelloTimbratura, List<InfoFilePerBustaTimbro> listaFileDaAggiungereBustaTimbro) throws Exception {
		File resultFile = File.createTempFile("tmp", ".pdf");
		
	    PdfReader reader = new PdfReader(new FileInputStream(pdfModelloTimbratura));
	    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(resultFile));
	    
	    int i = 1;
	    for(InfoFilePerBustaTimbro fileDaAggiungereBusta : listaFileDaAggiungereBustaTimbro) {	    	
	    	File fileAllegato = fileDaAggiungereBusta.getFile();
	    	String nomeFileAllegato = fileDaAggiungereBusta.getNomeFile();
	    	
	    	byte [] byteAllegato = FileUtils.readFileToByteArray(fileAllegato);
	    	PdfFileSpecification fs = PdfFileSpecification.fileEmbedded(
		            stamper.getWriter(), null, nomeFileAllegato, byteAllegato);
		    stamper.addFileAttachment(StringUtils.isNotBlank(fileDaAggiungereBusta.getDescrizione()) ? fileDaAggiungereBusta.getDescrizione() : "File" + i, fs);
		    i++;
	    }
	    
	    if(stamper!=null) {
	    	stamper.close();
	    }
	    if(reader!=null) {
	    	reader.close();
	    }
	    
	    return resultFile;
	}

	public OpzioniTimbroAttachEmail populatePreference(OpzioniTimbroBean bean) {
		
		OpzioniTimbroAttachEmail result = new OpzioniTimbroAttachEmail();
		OpzioniTimbroAttachEmail lOpzioniTimbroConfig = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAttachEmail");
		
		/**
		 * Se skipScelteOpzioniCopertina = true allora recupero i valori dalla preference se ci sono, altrimenti dal config.xml
		 */
		if(bean.getSkipScelteOpzioniCopertina() != null && "true".equals(bean.getSkipScelteOpzioniCopertina())){
			
			result.setPosizioneTimbro(bean.getPosizioneTimbroPref() != null && !"".equals(bean.getPosizioneTimbroPref()) ?
					PosizioneTimbroNellaPagina.fromValue(bean.getPosizioneTimbroPref()) : lOpzioniTimbroConfig.getPosizioneTimbro());
		
			result.setRotazioneTimbro(bean.getRotazioneTimbroPref() != null && !"".equals(bean.getRotazioneTimbroPref()) ?
					TipoRotazione.fromValue(bean.getRotazioneTimbroPref()) : lOpzioniTimbroConfig.getRotazioneTimbro());

		    if (bean.getTipoPagina() == null || (bean.getTipoPagina() != null && "intervallo".equals(bean.getTipoPagina()))) {
				Pagine lPaginaTimbroPagine = new Pagine();
				lPaginaTimbroPagine.setPaginaDa(bean.getPaginaDa() != null ?  Integer.valueOf(bean.getPaginaDa()) : 1);
				lPaginaTimbroPagine.setPaginaA(bean.getPaginaA() != null ? Integer.valueOf(bean.getPaginaA()) : 1);
				PaginaTimbro lPaginaTimbro = new PaginaTimbro();
				lPaginaTimbro.setPagine(lPaginaTimbroPagine);
				result.setPaginaTimbro(lPaginaTimbro);
			} else {
				PaginaTimbro lPaginaTimbro = new PaginaTimbro();
				lPaginaTimbro.setTipoPagina(bean.getTipoPagina() != null ? TipoPagina.fromValue(bean.getTipoPagina()) : null);
				result.setPaginaTimbro(lPaginaTimbro);
			}
			
			TestoTimbro lTestoTimbro = new TestoTimbro();
			lTestoTimbro.setTesto(bean.getTesto());
			result.setTestoTimbro(lTestoTimbro);
			
			TestoTimbro lTestoTimbroIntestazione = new TestoTimbro();
			lTestoTimbroIntestazione.setTesto(bean.getTestoIntestazione());
			result.setIntestazioneTimbro(lTestoTimbroIntestazione);
			
			result.setPosizioneIntestazione(lOpzioniTimbroConfig.getPosizioneIntestazione());
			
			result.setTimeout(lOpzioniTimbroConfig != null && lOpzioniTimbroConfig.getTimeout() != null ? lOpzioniTimbroConfig.getTimeout() : 60000);
			result.setCodifica(lOpzioniTimbroConfig != null && lOpzioniTimbroConfig.getCodifica() != null ? lOpzioniTimbroConfig.getCodifica() : CodificaTimbro.BARCODE_PDF_417);
			result.setTimbroSingolo(false);
			result.setRigheMultiple(true);
		
			result.setMappaParametri(lOpzioniTimbroConfig.getMappaParametri());	
			
		} else {
			
			result.setPosizioneTimbro(PosizioneTimbroNellaPagina.fromValue(bean.getPosizioneTimbro()));
			result.setRotazioneTimbro(TipoRotazione.fromValue(bean.getRotazioneTimbro()));
			
			PaginaTimbro lPaginaTimbro = new PaginaTimbro();
			if (bean.getTipoPagina() == null || (bean.getTipoPagina() != null && "intervallo".equals(bean.getTipoPagina()))) {
				Pagine lPaginaTimbroPagine = new Pagine();
				lPaginaTimbroPagine.setPaginaDa(bean.getPaginaDa() != null ?  Integer.valueOf(bean.getPaginaDa()) : 1);
				lPaginaTimbroPagine.setPaginaA(bean.getPaginaA() != null ? Integer.valueOf(bean.getPaginaA()) : 1);
				lPaginaTimbro.setPagine(lPaginaTimbroPagine);
				result.setPaginaTimbro(lPaginaTimbro);
			} else {
				lPaginaTimbro.setTipoPagina(bean.getTipoPagina() != null ? TipoPagina.fromValue(bean.getTipoPagina()) : null);
				result.setPaginaTimbro(lPaginaTimbro);
			}
				
			TestoTimbro lTestoTimbro = new TestoTimbro();
			lTestoTimbro.setTesto(bean.getTesto());
			result.setTestoTimbro(lTestoTimbro);
				
			TestoTimbro lTestoTimbroIntestazione = new TestoTimbro();
			lTestoTimbroIntestazione.setTesto(bean.getTestoIntestazione());
			result.setIntestazioneTimbro(lTestoTimbroIntestazione);
			
			result.setPosizioneIntestazione(lOpzioniTimbroConfig.getPosizioneIntestazione());
			
			result.setTimeout(lOpzioniTimbroConfig != null && lOpzioniTimbroConfig.getTimeout() != null ? lOpzioniTimbroConfig.getTimeout() : 60000);
			result.setCodifica(lOpzioniTimbroConfig != null && lOpzioniTimbroConfig.getCodifica() != null ? lOpzioniTimbroConfig.getCodifica() : CodificaTimbro.BARCODE_PDF_417);
			result.setTimbroSingolo(false);
			result.setRigheMultiple(true);
		}
		
		return result;
	}
	
	// ***************************************************** OPZIONI DI DEFAULT *****************************************************

	public OpzioniTimbroBean loadTimbraDefault(OpzioniTimbroBean bean, HttpSession session, Locale locale) throws Exception, StoreException {
		
		OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
		
		if (StringUtils.isNotBlank(bean.getIdDoc())) {
			
			lOpzioniTimbroBean.setNomeFile(bean.getNomeFile());
			lOpzioniTimbroBean.setUri(bean.getUri());
			lOpzioniTimbroBean.setRemote(bean.isRemote());
			lOpzioniTimbroBean.setMimetype(bean.getMimetype());
			lOpzioniTimbroBean.setIdUd(bean.getIdUd());
			lOpzioniTimbroBean.setIdDoc(bean.getIdDoc());
			
			DmpkRegistrazionedocGettimbrospecxtipo store = new DmpkRegistrazionedocGettimbrospecxtipo();
			DmpkRegistrazionedocGettimbrospecxtipoBean input = new DmpkRegistrazionedocGettimbrospecxtipoBean();

			if (StringUtils.isNotBlank(bean.getIdDoc())){
				input.setIddocfolderin(new BigDecimal(bean.getIdDoc()));
				input.setFlgdocfolderin("D");
			}else{
				input.setIddocfolderin(new BigDecimal(bean.getIdFolder()));
				input.setFlgdocfolderin("F");
			}

			StoreResultBean<DmpkRegistrazionedocGettimbrospecxtipoBean> result = store.execute(locale, AurigaUserUtil.getLoginInfo(session), input);

			if (result.isInError()) {
				throw new StoreException(result);
			}
			lOpzioniTimbroBean.setTesto(result.getResultBean().getContenutobarcodeout());
			lOpzioniTimbroBean.setTestoIntestazione(result.getResultBean().getTestoinchiaroout());
		}
		
		OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAttachEmail");

		String posTimbroConfig = "";
		String rotaTimbroConfig = "";
		String paginaTimbroConfig = "";
		OpzioniCopertinaTimbroBean lOpzioniCopertinaTimbroBean = (OpzioniCopertinaTimbroBean) SpringAppContext.getContext().getBean("OpzioniCopertinaTimbroBean");
		if(lOpzioniCopertinaTimbroBean != null){
			 posTimbroConfig = lOpzioniCopertinaTimbroBean.getPosizioneTimbro() != null ? lOpzioniCopertinaTimbroBean.getPosizioneTimbro().value() : null;
			 rotaTimbroConfig = lOpzioniCopertinaTimbroBean.getRotazioneTimbro() != null ? lOpzioniCopertinaTimbroBean.getRotazioneTimbro().value() : null;
			 paginaTimbroConfig = lOpzioniCopertinaTimbroBean.getPaginaTimbro()!= null && lOpzioniCopertinaTimbroBean.getPaginaTimbro().getTipoPagina()!= null ? lOpzioniCopertinaTimbroBean.getPaginaTimbro().getTipoPagina().value() : null;
		}
			
		String posTimbroPref = bean.getPosizioneTimbroPref() != null && !"".equalsIgnoreCase(bean.getPosizioneTimbroPref()) ?
				bean.getPosizioneTimbroPref() : null;
		String rotaTimbroPref = bean.getRotazioneTimbroPref() != null && !"".equalsIgnoreCase(bean.getRotazioneTimbroPref()) ?
				bean.getRotazioneTimbroPref() : null;
		String tipoPaginaPref = bean.getTipoPagina() != null && !"".equalsIgnoreCase(bean.getTipoPagina()) ? 
				bean.getTipoPagina() : null;
											
		lOpzioniTimbroBean.setPosizioneTimbro(posTimbroPref != null ? posTimbroPref : posTimbroConfig);
		lOpzioniTimbroBean.setRotazioneTimbro(rotaTimbroPref != null ? rotaTimbroPref : rotaTimbroConfig);	
		lOpzioniTimbroBean.setTipoPagina(tipoPaginaPref != null ? tipoPaginaPref : paginaTimbroConfig);
		lOpzioniTimbroBean.setPosizioneIntestazione(lOpzioniTimbroAttachEmail.getPosizioneIntestazione().value());

		if (lOpzioniTimbroAttachEmail.getPaginaTimbro() != null) {
			if (lOpzioniTimbroAttachEmail.getPaginaTimbro().getPagine() != null) {
				lOpzioniTimbroBean.setTipoPagina("intervallo");
				if (lOpzioniTimbroAttachEmail.getPaginaTimbro().getPagine().getPaginaDa() != null) {
					lOpzioniTimbroBean.setPaginaDa(String.valueOf(lOpzioniTimbroAttachEmail.getPaginaTimbro().getPagine().getPaginaDa()));
				}
				if (lOpzioniTimbroAttachEmail.getPaginaTimbro().getPagine().getPaginaDa() != null) {
					lOpzioniTimbroBean.setPaginaA(String.valueOf(lOpzioniTimbroAttachEmail.getPaginaTimbro().getPagine().getPaginaA()));
				}
			}
		}

		lOpzioniTimbroBean.setCodifica(lOpzioniTimbroAttachEmail.getCodifica() != null ? lOpzioniTimbroAttachEmail.getCodifica().value() : null);
		lOpzioniTimbroBean.setTimbroSingolo(lOpzioniTimbroAttachEmail.isTimbroSingolo());
		lOpzioniTimbroBean.setMoreLines(lOpzioniTimbroAttachEmail.isRigheMultiple());

		return lOpzioniTimbroBean;
	}

	public OpzioniTimbroBean loadSegnatureRegistrazioneDefault(OpzioniTimbroBean bean, HttpSession session, Locale locale) throws Exception, StoreException {
		
		OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
		
		if (StringUtils.isNotBlank(bean.getIdUd())) {
			
			lOpzioniTimbroBean.setNomeFile(bean.getNomeFile());
			lOpzioniTimbroBean.setUri(bean.getUri());
			lOpzioniTimbroBean.setRemote(bean.isRemote());
			lOpzioniTimbroBean.setMimetype(bean.getMimetype());
			lOpzioniTimbroBean.setIdUd(bean.getIdUd());
			lOpzioniTimbroBean.setIdDoc(bean.getIdDoc());
			lOpzioniTimbroBean.setFinalita(bean.getFinalita());
			lOpzioniTimbroBean.setSkipScelteOpzioniCopertina(bean.getSkipScelteOpzioniCopertina());
			lOpzioniTimbroBean.setNroProgAllegato(bean.getNroProgAllegato());
			
			DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
			DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
			input.setIdudio(new BigDecimal(bean.getIdUd()));
			input.setIddocin(bean.getIdDoc() != null && !"".equals(bean.getIdDoc()) ? new BigDecimal(bean.getIdDoc()) : null);
			input.setNroallegatoin(bean.getNroProgAllegato());
			input.setFinalitain(bean.getFinalita());
			
			StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> result = store.execute(locale, AurigaUserUtil.getLoginInfo(session), input);

			if (result.isInError()) {
				throw new StoreException(result);
			}
			lOpzioniTimbroBean.setTesto(result.getResultBean().getContenutobarcodeout());
			if(input.getNroallegatoin()!=null && isClienteArpaLazio(session)) {
				lOpzioniTimbroBean.setTestoIntestazione("Allegato n.".concat(result.getResultBean().getTestoinchiaroout()));
			} else {
				lOpzioniTimbroBean.setTestoIntestazione(result.getResultBean().getTestoinchiaroout());
			}
			
			
		}
		
		OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAttachEmail");
		
		String posTimbroConfig = "";
		String rotaTimbroConfig = "";
		String paginaTimbroConfig = "";
		OpzioniCopertinaTimbroBean lOpzioniCopertinaTimbroBean = (OpzioniCopertinaTimbroBean) SpringAppContext.getContext().getBean("OpzioniCopertinaTimbroBean");
		if(lOpzioniCopertinaTimbroBean != null){
			 posTimbroConfig = lOpzioniCopertinaTimbroBean.getPosizioneTimbro() != null ? lOpzioniCopertinaTimbroBean.getPosizioneTimbro().value() : null;
			 rotaTimbroConfig = lOpzioniCopertinaTimbroBean.getRotazioneTimbro() != null ? lOpzioniCopertinaTimbroBean.getRotazioneTimbro().value() : null;
			 paginaTimbroConfig = lOpzioniCopertinaTimbroBean.getPaginaTimbro()!= null && lOpzioniCopertinaTimbroBean.getPaginaTimbro().getTipoPagina()!= null ? lOpzioniCopertinaTimbroBean.getPaginaTimbro().getTipoPagina().value() : null;
		}

		lOpzioniTimbroBean.setPosizioneIntestazione(lOpzioniTimbroAttachEmail.getPosizioneIntestazione().value());
		
		String posTimbroPref = bean.getPosizioneTimbroPref() != null && !"".equalsIgnoreCase(bean.getPosizioneTimbroPref()) ?
				bean.getPosizioneTimbroPref() : null;
		String rotaTimbroPref = bean.getRotazioneTimbroPref() != null && !"".equalsIgnoreCase(bean.getRotazioneTimbroPref()) ?
				bean.getRotazioneTimbroPref() : null;
		String tipoPaginaPref = bean.getTipoPagina() != null && !"".equalsIgnoreCase(bean.getTipoPagina()) ? 
				bean.getTipoPagina() : null;
									
		lOpzioniTimbroBean.setPosizioneTimbro(posTimbroPref != null ? posTimbroPref : posTimbroConfig);
		lOpzioniTimbroBean.setRotazioneTimbro(rotaTimbroPref != null ? rotaTimbroPref : rotaTimbroConfig);	
		lOpzioniTimbroBean.setTipoPagina(tipoPaginaPref != null ? tipoPaginaPref : paginaTimbroConfig);
		
		if (lOpzioniTimbroAttachEmail.getPaginaTimbro() != null) {
			if (lOpzioniTimbroAttachEmail.getPaginaTimbro().getPagine() != null) {
				lOpzioniTimbroBean.setTipoPagina("intervallo");
				if (lOpzioniTimbroAttachEmail.getPaginaTimbro().getPagine().getPaginaDa() != null) {
					lOpzioniTimbroBean.setPaginaDa(String.valueOf(lOpzioniTimbroAttachEmail.getPaginaTimbro().getPagine().getPaginaDa()));
				}
				if (lOpzioniTimbroAttachEmail.getPaginaTimbro().getPagine().getPaginaDa() != null) {
					lOpzioniTimbroBean.setPaginaA(String.valueOf(lOpzioniTimbroAttachEmail.getPaginaTimbro().getPagine().getPaginaA()));
				}
			}
		}

		lOpzioniTimbroBean.setCodifica(lOpzioniTimbroAttachEmail.getCodifica() != null ? lOpzioniTimbroAttachEmail.getCodifica().value() : null);
		lOpzioniTimbroBean.setTimbroSingolo(lOpzioniTimbroAttachEmail.isTimbroSingolo());
		lOpzioniTimbroBean.setMoreLines(lOpzioniTimbroAttachEmail.isRigheMultiple());

		return lOpzioniTimbroBean;
	}
	
	public boolean isClienteArpaLazio(HttpSession session) {
		return ParametriDBUtil.getParametroDB(session, "CLIENTE") != null && 
			   ParametriDBUtil.getParametroDB(session, "CLIENTE").equalsIgnoreCase("ARPA_LAZ");
	}
}
