/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGetbarcodedacapofilapraticaBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrodigregBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrospecxtipoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkRegistrazionedocGetbarcodedacapofilapratica;
import it.eng.client.DmpkRegistrazionedocGettimbrodigreg;
import it.eng.client.DmpkRegistrazionedocGettimbrospecxtipo;
import it.eng.fileOperation.clientws.CodificaTimbro;
import it.eng.fileOperation.clientws.PaginaTimbro;
import it.eng.fileOperation.clientws.PosizioneTimbroNellaPagina;
import it.eng.fileOperation.clientws.TestoTimbro;
import it.eng.fileOperation.clientws.TipoPagina;
import it.eng.fileOperation.clientws.TipoRotazione;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.TimbraUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniCopertinaTimbroBean;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

/**
 * 
 * @author DANCRIST
 *
 */

/*
 * Classe di utility per la generazione delle copertine relative alla segnatura con barcode o con timbro
 * Singola e Multipla
 */
public class TimbraCopertinaUtility {
	
	private static final Logger log = Logger.getLogger(TimbraCopertinaUtility.class);
	
	public CopertinaResultBean apponiCopertina(OpzioniCopertinaBean bean, HttpSession session,Locale locale) throws Exception {
		
		log.info("Apponi copertina");
	
		CopertinaResultBean lCopertinaResultBean = new CopertinaResultBean();
		
		//RECUPERO LE PREFERENCE DI GENERAZIONE DEI BARCODE
		OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail = populatePreference(bean);
		
		TimbraUtil lTimbraUtil = new TimbraUtil();

		//VERIFICA DI STAMPA SINGOLA O MULTIPLA
		if(bean.getNumeroAllegati() != null && !"".equals(bean.getNumeroAllegati())){
			manageBarcodeMultiplo(bean, session, locale, lCopertinaResultBean, lTimbraUtil);
		}else{
			try {
				final File tempFile = File.createTempFile("Copertina",".txt");
				tempFile.deleteOnExit();
			
				String storageUri = StorageImplementation.getStorage().store(tempFile);
				File inputFile = StorageImplementation.getStorage().extractFile(storageUri);
			
				String uriFilePdf = StorageImplementation.getStorage().storeStream(
					lTimbraUtil.converti(inputFile, "copertina"));

				File fileDaTimbrare = StorageImplementation.getStorage().extractFile(uriFilePdf);
				boolean generaPdfA = ParametriDBUtil.getParametroDBAsBoolean(session, "TIMBRATURA_ABILITA_PDFA");
				String lUriFinale = StorageImplementation.getStorage().storeStream(lTimbraUtil.timbra(fileDaTimbrare, "Copertina", lOpzioniTimbroAttachEmail, generaPdfA));
			
				lCopertinaResultBean.setResult(true);
				lCopertinaResultBean.setUri(lUriFinale);
			
				MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(fileDaTimbrare.toURI().toString(), "Copertina.pdf", false, null);
				lCopertinaResultBean.setInfoFile(lMimeTypeFirmaBean);
			
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				lCopertinaResultBean.setResult(false);
				lCopertinaResultBean.setError(e.getMessage());
				return lCopertinaResultBean;
			}
		}
		return lCopertinaResultBean;
	}

	/**
	 * METODO RELATIVO ALLA GENERAZIONE DEI BARCODE CON TIPOLOGIA O SEGNATURA E CON POSIZIONE O SENZA
	 */
	private void manageBarcodeMultiplo(OpzioniCopertinaBean bean, HttpSession session, Locale locale, CopertinaResultBean lCopertinaResultBean,
			TimbraUtil lTimbraUtil) throws Exception, StoreException, IOException, StorageException {
		
		OpzioniTimbroAttachEmail lOpzTimbAttEmail = null;
		List<InputStream> listFileToMerge = new ArrayList<InputStream>();
		boolean generaPdfA = ParametriDBUtil.getParametroDBAsBoolean(session, "TIMBRATURA_ABILITA_PDFA");
		//PROVENGO DA UN ALLEGATO
		if(bean.getProvenienza() != null && "A".equals(bean.getProvenienza())){
			//NR DOCUMENTO CON POSIZIONE
			if(bean.getPosizionale() != null && "P".equals(bean.getPosizionale())){
				Integer size = Integer.valueOf(bean.getNumeroAllegati());
				Integer nrAllegatoTemp = Integer.valueOf(bean.getNumeroAllegato());
				Integer cont = 0;
				while(cont < size){
					OpzioniCopertinaBean lOpzCopBean = null;
					if(bean.getTipoTimbroCopertina() != null && "T".equals(bean.getTipoTimbroCopertina())){
						//DATI TIPOLOGIA
						lOpzCopBean = loadTimbroTipologiaDefault(bean, session, locale, nrAllegatoTemp);
					}else{
						//DATI SEGNATURA
						Integer nrAllegato = bean.getPosizionale() != null && "P".equals(bean.getPosizionale()) ? nrAllegatoTemp : null;
						lOpzCopBean = loadTimbroSegnaturaDefault(bean, session, locale,nrAllegato);
					}
					
					//RECUPERO LE PREFERENCE DI GENERAZIONE DEI BARCODE
					lOpzTimbAttEmail = populatePreference(lOpzCopBean);
					
					InputStream lInputStreamPdf = null;
					
					final File tempFile = File.createTempFile("Copertina",".txt");
					tempFile.deleteOnExit();
				
					String storageUri = StorageImplementation.getStorage().store(tempFile);
					File inputFile = StorageImplementation.getStorage().extractFile(storageUri);
				
					String uriFilePdf = StorageImplementation.getStorage().storeStream(
						lTimbraUtil.converti(inputFile, "copertina"));
		
					File fileDaTimbrare = StorageImplementation.getStorage().extractFile(uriFilePdf);
					String lUriFinale = StorageImplementation.getStorage().storeStream(lTimbraUtil.timbra(fileDaTimbrare, "Copertina", lOpzTimbAttEmail, generaPdfA));
					lInputStreamPdf = StorageImplementation.getStorage().extract(lUriFinale);
					
					listFileToMerge.add(lInputStreamPdf);
					
					nrAllegatoTemp++;
					cont++;
				}
			}else{
				//NR DOCUMENTO SENZA POSIZIONE
				Integer size = Integer.valueOf(bean.getNumeroAllegati());
				for(int index = 0; index < size; index++){
					OpzioniCopertinaBean lOpzCopBean = null;
					if(bean.getTipoTimbroCopertina() != null && "T".equals(bean.getTipoTimbroCopertina())){
						//DATI TIPOLOGIA
						lOpzCopBean = loadTimbroTipologiaDefault(bean, session, locale, null);
					}else{
						//DATI SEGNATURA
						Integer nrAllegato = bean.getPosizionale() != null && "P".equals(bean.getPosizionale()) ? null : null;
						lOpzCopBean = loadTimbroSegnaturaDefault(bean, session, locale,nrAllegato);
					}
					
					//RECUPERO LE PREFERENCE DI GENERAZIONE DEI BARCODE
					lOpzTimbAttEmail = populatePreference(lOpzCopBean);
					
					InputStream lInputStreamPdf = null;
					
					final File tempFile = File.createTempFile("Copertina",".txt");
					tempFile.deleteOnExit();
				
					String storageUri = StorageImplementation.getStorage().store(tempFile);
					File inputFile = StorageImplementation.getStorage().extractFile(storageUri);
				
					String uriFilePdf = StorageImplementation.getStorage().storeStream(
						lTimbraUtil.converti(inputFile, "copertina"));
		
					File fileDaTimbrare = StorageImplementation.getStorage().extractFile(uriFilePdf);
					String lUriFinale = StorageImplementation.getStorage().storeStream(lTimbraUtil.timbra(fileDaTimbrare, "Copertina", lOpzTimbAttEmail, generaPdfA));
					lInputStreamPdf = StorageImplementation.getStorage().extract(lUriFinale);
					
					listFileToMerge.add(lInputStreamPdf);
				}
			}
		}else{
			// PROVENGO DA FILE PRIMARIO
			int size = Integer.valueOf(bean.getNumeroAllegati());
			for(int index = 0; index < size; index ++){
				OpzioniCopertinaBean lOpzCopBean = null;
				if(bean.getTipoTimbroCopertina() != null && "T".equals(bean.getTipoTimbroCopertina())){
					//DATI TIPOLOGIA
					Integer nrAllegato = bean.getPosizionale() != null && "P".equals(bean.getPosizionale()) ? index : null;
					lOpzCopBean = loadTimbroTipologiaDefault(bean, session, locale, nrAllegato);
				}else{
					//DATI SEGNATURA
					Integer nrAllegato = bean.getPosizionale() != null && "P".equals(bean.getPosizionale()) ? index : null;
					lOpzCopBean = loadTimbroSegnaturaDefault(bean, session, locale, nrAllegato);
				}
				//RECUPERO LE PREFERENCE DI GENERAZIONE DEI BARCODE
				lOpzTimbAttEmail = populatePreference(lOpzCopBean);
				InputStream lInputStreamPdf = null;
				
				final File tempFile = File.createTempFile("Copertina",".txt");
				tempFile.deleteOnExit();
			
				String storageUri = StorageImplementation.getStorage().store(tempFile);
				File inputFile = StorageImplementation.getStorage().extractFile(storageUri);
			
				String uriFilePdf = StorageImplementation.getStorage().storeStream(
					lTimbraUtil.converti(inputFile, "copertina"));
	
				File fileDaTimbrare = StorageImplementation.getStorage().extractFile(uriFilePdf);
				String lUriFinale = StorageImplementation.getStorage().storeStream(lTimbraUtil.timbra(fileDaTimbrare, "Copertina", lOpzTimbAttEmail, generaPdfA));
				lInputStreamPdf = StorageImplementation.getStorage().extract(lUriFinale);
				
				listFileToMerge.add(lInputStreamPdf);
			}
		}
		/**
		 * Logica di merge dei file timbrati
		 */
		File fileOutput = unisciPdf(listFileToMerge);
		
		String uriFileUnione = StorageImplementation.getStorage().store(fileOutput, new String[] {});
		lCopertinaResultBean.setResult(true);
		lCopertinaResultBean.setUri(uriFileUnione);
		MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(fileOutput.toURI().toString(), "Copertina.pdf", false, null);
		lCopertinaResultBean.setInfoFile(lMimeTypeFirmaBean);
	}
	
	/**
	 * RECUPERO LE PREFERENCE RELATIVA ALLA GENERAZIONE DEI BARCODE
	 * SE NON SONO DEFINITE LATO INTERFACCIA DALL'UTENTE ALLORA PROCEDO A RECUPERARLE DAL CONFIG.XML ( OpzioniCopertinaTimbroBean )
	 * LA Codifica del barcode viene definita nel config.xml, se non definita di default è del tipo BARCODE_PDF_417
	 */
	public OpzioniTimbroAttachEmail populatePreference(OpzioniCopertinaBean bean){
		
		OpzioniTimbroAttachEmail result = new OpzioniTimbroAttachEmail();
		OpzioniCopertinaTimbroBean beanConfig = (OpzioniCopertinaTimbroBean) SpringAppContext.getContext().getBean("OpzioniCopertinaTimbroBean");
		
		/**
		 * VIENE VERIFICATO SE L'UTENTE HA SCELTO DI SKIPPARE LA SCELTE DELLE PREFERENCE DI STAMPA DEI BARCODE
		 * Se skipScelteOpzioniCopertina = true ALLORA RECUPERO I VALORI DALLA
		 * PREFERENCE SE PRESENTI, ALTRIMENTI dal config.xml
		 */
		if(bean.getSkipScelteOpzioniCopertina() != null && "true".equals(bean.getSkipScelteOpzioniCopertina())){
			
			result.setPosizioneTimbro(bean.getPosizioneTimbroPref() != null && !"".equals(bean.getPosizioneTimbroPref()) ?
					PosizioneTimbroNellaPagina.fromValue(bean.getPosizioneTimbroPref()) : beanConfig.getPosizioneTimbro());
		
			result.setRotazioneTimbro(bean.getRotazioneTimbroPref() != null && !"".equals(bean.getRotazioneTimbroPref()) ?
					TipoRotazione.fromValue(bean.getRotazioneTimbroPref()) : beanConfig.getRotazioneTimbro());
			
			TestoTimbro lTestoTimbro = new TestoTimbro();
			lTestoTimbro.setTesto(bean.getTesto());
			result.setTestoTimbro(lTestoTimbro);
			
			TestoTimbro lTestoTimbroIntestazione = new TestoTimbro();
			lTestoTimbroIntestazione.setTesto(bean.getTestoIntestazione());
			result.setIntestazioneTimbro(lTestoTimbroIntestazione);
			
			PaginaTimbro lPaginaTimbro = new PaginaTimbro();
			lPaginaTimbro.setTipoPagina(TipoPagina.PRIMA);
			result.setPaginaTimbro(lPaginaTimbro);
			
			result.setPosizioneIntestazione(beanConfig.getPosizioneIntestazione());
			
			result.setTimeout(beanConfig != null && beanConfig.getTimeout() != null ? beanConfig.getTimeout() : 60000);
			result.setCodifica(beanConfig != null && beanConfig.getCodifica() != null ? beanConfig.getCodifica() : CodificaTimbro.BARCODE_PDF_417);
			result.setTimbroSingolo(false);
			result.setRigheMultiple(false);
		
			result.setMappaParametri(beanConfig.getMappaParametri());		
		}
		/**
		 * RECUPERO I VALORI DALLA MASCHERA DI SCELTA PER { posizione - rotazione - testo & testo intestazione }
		 * GLI ALTRI VALORI VENGONO RECUPERATI DAL config.xml
		 */
		else{
			result.setPosizioneTimbro(PosizioneTimbroNellaPagina.fromValue(bean.getPosizioneTimbro()));
			result.setRotazioneTimbro(TipoRotazione.fromValue(bean.getRotazioneTimbro()));
				
			PaginaTimbro lPaginaTimbro = new PaginaTimbro();
			lPaginaTimbro.setTipoPagina(TipoPagina.PRIMA);
			result.setPaginaTimbro(lPaginaTimbro);
				
			TestoTimbro lTestoTimbro = new TestoTimbro();
			lTestoTimbro.setTesto(bean.getTesto());
			result.setTestoTimbro(lTestoTimbro);
				
			TestoTimbro lTestoTimbroIntestazione = new TestoTimbro();
			lTestoTimbroIntestazione.setTesto(bean.getTestoIntestazione());
			result.setIntestazioneTimbro(lTestoTimbroIntestazione);
			
			result.setPosizioneIntestazione(beanConfig.getPosizioneIntestazione());
				
			result.setTimeout(beanConfig != null && beanConfig.getTimeout() != null ? beanConfig.getTimeout() : 60000);
			result.setCodifica(beanConfig != null && beanConfig.getCodifica() != null ? beanConfig.getCodifica() : CodificaTimbro.BARCODE_PDF_417);
			result.setTimbroSingolo(false);
			result.setRigheMultiple(false);
			
			result.setMappaParametri(beanConfig.getMappaParametri());	
		}
		return result;
	}
	
	// ***************************************************** OPZIONI DI DEFAULT *****************************************************
	
	/**
	 * Caricamento preference per i barcode con TIPOLOGIA
	 * PRATICA PREGRESSA: DmpkRegistrazionedocGettimbrospecxtipo Flgdocfolderin = F
	 * PROTOCOLLAZIONE:   DmpkRegistrazionedocGettimbrospecxtipo Flgdocfolderin = D
	 */
	public OpzioniCopertinaBean loadTimbroTipologiaDefault(OpzioniCopertinaBean bean, HttpSession session, Locale locale,Integer nrAllegato) throws Exception, StoreException {
		
		OpzioniCopertinaBean lOpzioniCopertinaBean = new OpzioniCopertinaBean();
		
		if(bean.getTipoTimbroCopertina() != null && "T".equals(bean.getTipoTimbroCopertina())){
							
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);

			String token = loginBean.getToken();
		
			DmpkRegistrazionedocGettimbrospecxtipoBean input = new DmpkRegistrazionedocGettimbrospecxtipoBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
			if(StringUtils.isNotBlank(bean.getIdDoc())) {
				input.setIddocfolderin(bean.getIdDoc() != null && !"".equals(bean.getIdDoc()) ? new BigDecimal(bean.getIdDoc()) : null);
				input.setFlgdocfolderin("D");
			}else{
				input.setIddocfolderin(bean.getIdFolder() != null && !"".equals(bean.getIdFolder()) ? new BigDecimal(bean.getIdFolder()) : null);
				input.setFlgdocfolderin("F");
			}
			
			DmpkRegistrazionedocGettimbrospecxtipo lDmpkRegistrazionedocGettimbrospecxtipo = new
					DmpkRegistrazionedocGettimbrospecxtipo();
			StoreResultBean<DmpkRegistrazionedocGettimbrospecxtipoBean> output = lDmpkRegistrazionedocGettimbrospecxtipo.execute(locale, loginBean, input);
			
			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (output.isInError()) {
					log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
					throw new StoreException(output);
				}
			}
		
			lOpzioniCopertinaBean.setTesto(output.getResultBean().getContenutobarcodeout());
			lOpzioniCopertinaBean.setTestoIntestazione(output.getResultBean().getTestoinchiaroout());
			lOpzioniCopertinaBean.setInfoFile(bean.getInfoFile() != null ? bean.getInfoFile() : null);
			
		}else{
				
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);

			String token = loginBean.getToken();
			String idUserLavoro = loginBean.getIdUserLavoro();
		
			DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
			if(StringUtils.isNotBlank(bean.getIdDoc())) {
				input.setIdudio(bean.getIdUd() != null && !"".equals(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
			}else{
				input.setIdudio(bean.getIdFolder() != null && !"".equals(bean.getIdFolder()) ? new BigDecimal(bean.getIdFolder()) : null);
			}
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
			input.setNroallegatoin(nrAllegato);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			
			DmpkRegistrazionedocGettimbrodigreg lDmpkRegistrazionedocGettimbrodigreg = new
					DmpkRegistrazionedocGettimbrodigreg();
			StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> output = lDmpkRegistrazionedocGettimbrodigreg.execute(locale, loginBean, input);
			
			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (output.isInError()) {
					log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
					throw new StoreException(output);
				}
			}
		
			lOpzioniCopertinaBean.setTesto(output.getResultBean().getContenutobarcodeout());
			lOpzioniCopertinaBean.setTestoIntestazione(output.getResultBean().getTestoinchiaroout());
			lOpzioniCopertinaBean.setInfoFile(bean.getInfoFile() != null ? bean.getInfoFile() : null);
			
		}
		
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
				bean.getPosizioneTimbroPref() : bean.getPosizioneTimbro();
		String rotaTimbroPref = bean.getRotazioneTimbroPref() != null && !"".equalsIgnoreCase(bean.getRotazioneTimbroPref()) ?
				bean.getRotazioneTimbroPref() : bean.getRotazioneTimbro();
									
		lOpzioniCopertinaBean.setPosizioneTimbro(posTimbroPref != null ? posTimbroPref : posTimbroConfig);
		lOpzioniCopertinaBean.setRotazioneTimbro(rotaTimbroPref != null ? rotaTimbroPref : rotaTimbroConfig);
		lOpzioniCopertinaBean.setRotazioneTimbro(rotaTimbroPref != null ? rotaTimbroPref : paginaTimbroConfig);

		return lOpzioniCopertinaBean;
	}
	
	/**
	 * Caricamento preference per i barcode con segnatura
	 * PRATICA PREGRESSA: DmpkRegistrazionedocGetbarcodedacapofilapratica
	 * PROTOCOLLAZIONE:   DmpkRegistrazionedocGettimbrodigreg
	 */
	public OpzioniCopertinaBean loadTimbroSegnaturaDefault(OpzioniCopertinaBean bean, HttpSession session, Locale locale,Integer nrAllegato) throws Exception, StoreException {
		
		OpzioniCopertinaBean lOpzioniCopertinaBean = new OpzioniCopertinaBean();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		//Pratica pregressa
		if(bean.getBarcodePraticaPregressa() != null && "true".equals(bean.getBarcodePraticaPregressa())){
			
			DmpkRegistrazionedocGetbarcodedacapofilapraticaBean input = new DmpkRegistrazionedocGetbarcodedacapofilapraticaBean();
			input.setIdfolderin(bean.getIdFolder() != null && !"".equals(bean.getIdFolder()) ? new BigDecimal(bean.getIdFolder()) : null);
			input.setSezionepraticain(bean.getSezionePratica());
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean
					.getIdUserLavoro()) : null);
			input.setNroposizionein(nrAllegato);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			
			DmpkRegistrazionedocGetbarcodedacapofilapratica lDmpkRegistrazionedocGetbarcodedacapofilapratica = new
					DmpkRegistrazionedocGetbarcodedacapofilapratica();
		
			StoreResultBean<DmpkRegistrazionedocGetbarcodedacapofilapraticaBean> output = lDmpkRegistrazionedocGetbarcodedacapofilapratica.execute(locale, loginBean, input);
			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (output.isInError()) {
					log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
					throw new StoreException(output);
				}
			}
		
			lOpzioniCopertinaBean.setTesto(output.getResultBean().getContenutobarcodeout());
			lOpzioniCopertinaBean.setTestoIntestazione(output.getResultBean().getTestoinchiaroout());
			lOpzioniCopertinaBean.setInfoFile(bean.getInfoFile() != null ? bean.getInfoFile() : null);
			lOpzioniCopertinaBean.setNumeroAllegato(nrAllegato != null ? String.valueOf(nrAllegato) : null);
			lOpzioniCopertinaBean.setBarcodePraticaPregressa("true");
			lOpzioniCopertinaBean.setIdFolder(bean.getIdFolder());
			lOpzioniCopertinaBean.setSezionePratica(bean.getSezionePratica());
			
		}else{
		
			lOpzioniCopertinaBean.setIdUd(bean.getIdUd());
			lOpzioniCopertinaBean.setNumeroAllegato(bean.getNumeroAllegato());
		
			DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
			input.setIdudio(bean.getIdUd() != null && !"".equals(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean
					.getIdUserLavoro()) : null);
			input.setNroallegatoin(nrAllegato);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			
			DmpkRegistrazionedocGettimbrodigreg lDmpkRegistrazionedocGettimbrodigreg = new
					DmpkRegistrazionedocGettimbrodigreg();
		
			StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> output = lDmpkRegistrazionedocGettimbrodigreg.execute(locale, loginBean, input);
			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (output.isInError()) {
					log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
					throw new StoreException(output);
				}
			}
		
			lOpzioniCopertinaBean.setTesto(output.getResultBean().getContenutobarcodeout());
			lOpzioniCopertinaBean.setTestoIntestazione(output.getResultBean().getTestoinchiaroout());
			lOpzioniCopertinaBean.setInfoFile(bean.getInfoFile() != null ? bean.getInfoFile() : null);
			
		}
			
		String posTimbroPref = bean.getPosizioneTimbroPref() != null && !"".equalsIgnoreCase(bean.getPosizioneTimbroPref()) ?
				bean.getPosizioneTimbroPref() : null;
		String rotaTimbroPref = bean.getRotazioneTimbroPref() != null && !"".equalsIgnoreCase(bean.getRotazioneTimbroPref()) ?
				bean.getRotazioneTimbroPref() : null;
									
		lOpzioniCopertinaBean.setPosizioneTimbro(posTimbroPref != null ? posTimbroPref : bean.getPosizioneTimbro());
		lOpzioniCopertinaBean.setRotazioneTimbro(rotaTimbroPref != null ? rotaTimbroPref : bean.getRotazioneTimbro());

		return lOpzioniCopertinaBean;
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

}
