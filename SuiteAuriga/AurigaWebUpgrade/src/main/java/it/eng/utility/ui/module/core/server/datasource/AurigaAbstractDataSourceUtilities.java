/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrodigregBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FileScaricoZipBean;
import it.eng.auriga.ui.module.layout.server.timbra.OpzioniTimbroBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraResultBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraUtility;
import it.eng.client.DmpkRegistrazionedocGettimbrodigreg;
import it.eng.core.business.FileUtil;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;

/*
 * Classe utilizzata per condividere metodi tra DataSource
 */

public class AurigaAbstractDataSourceUtilities {
	
	private static final Logger logProtDS = Logger.getLogger(AurigaAbstractDataSourceUtilities.class);
	
	
	public static Integer addFileToZip(Map<String, Integer> zipFileNames, File zipFile,
			List<FileScaricoZipBean> listaFileDaScaricare, String operazione, OpzioniTimbroBean opzioniTimbroScelteUtente, HttpSession session) throws Exception, StorageException {
		Integer warning = null;

		for (FileScaricoZipBean fileDaScaricare : listaFileDaScaricare) {

			if ("scaricaTimbratiSegnatura".equalsIgnoreCase(operazione)) {
				if(insertFileTimbratoInZip(zipFile, fileDaScaricare, null, opzioniTimbroScelteUtente, session)!=null)
					warning = 3;
			}

			else if ("scaricaTimbratiConformeStampa".equalsIgnoreCase(operazione)) {
				if(insertFileTimbratoInZip(zipFile, fileDaScaricare, "CONFORMITA_ORIG_DIGITALE_STAMPA", opzioniTimbroScelteUtente, session)!=null)
					warning = 3;
			}

			else if ("scaricaTimbratiConformeDigitale".equalsIgnoreCase(operazione)) {
				if(insertFileTimbratoInZip(zipFile, fileDaScaricare, "CONFORMITA_ORIG_DIGITALE_SUPP_DIG", opzioniTimbroScelteUtente, session)!=null)
					warning = 3;
			}

			else if ("scaricaTimbratiConformeCartaceo".equalsIgnoreCase(operazione)) {
				if(insertFileTimbratoInZip(zipFile, fileDaScaricare, "CONFORMITA_ORIG_CARTACEO", opzioniTimbroScelteUtente, session)!=null)
					warning = 3;
			}
			
			else if ("scaricaConformitaCustom".equalsIgnoreCase(operazione)) {
				OpzioniTimbroAttachEmail opzioniConformitaCustom = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniConformitaCustom");
				/*Per la conformita custom prendo le configurazioni associate per apporre il testo alla pagina*/
				if(opzioniConformitaCustom!=null) {
					opzioniTimbroScelteUtente.setPosizioneTimbro(opzioniConformitaCustom.getPosizioneTimbro().value());
					opzioniTimbroScelteUtente.setRotazioneTimbro(opzioniConformitaCustom.getRotazioneTimbro().value());
					opzioniTimbroScelteUtente.setTipoPagina(opzioniConformitaCustom.getPaginaTimbro().getTipoPagina().value());
					opzioniTimbroScelteUtente.setMoreLines(opzioniConformitaCustom.isRigheMultiple());
					opzioniTimbroScelteUtente.setPosizioneIntestazione(opzioniConformitaCustom.getPosizioneIntestazione().value());
					opzioniTimbroScelteUtente.setCodifica(opzioniConformitaCustom.getCodifica().value());
				}
				if(insertFileTimbratoInZip(zipFile, fileDaScaricare, "COPIA_CONFORME_CUSTOM", opzioniTimbroScelteUtente, session)!=null)
					warning = 3;
			}

			else if ("scaricaSbustati".equalsIgnoreCase(operazione)) {
				File fileOriginale = File.createTempFile("temp", fileDaScaricare.getNomeFile());
				FileUtil.writeStreamToFile(StorageImplementation.getStorage().extract(fileDaScaricare.getUriFile()),
						fileOriginale);

				if (fileDaScaricare.isFirmato() && (fileDaScaricare.getNomeFile().toLowerCase().endsWith(".p7m") || fileDaScaricare.getNomeFile().toLowerCase().endsWith(".tsd"))) {
					try {
						File fileSbustato = sbustaFileDaInserireNelloZip(fileOriginale, fileDaScaricare.getNomeFile());
						String nomeFileSbustato = getNomeFileSbustato(fileDaScaricare.getNomeFile());

						StorageUtil.addFileToZip(fileSbustato, nomeFileSbustato, zipFile.getAbsolutePath());

					} catch (Exception e) {
						logProtDS.error("Errore durante lo sbustamento del file " + fileDaScaricare.getNomeFile() + ": "
								+ e.getMessage(), e);

						StorageUtil.addFileToZip(fileOriginale, fileDaScaricare.getNomeFile(),
								zipFile.getAbsolutePath());
						
						warning = 4;
					}
				} else {
					StorageUtil.addFileToZip(fileOriginale, fileDaScaricare.getNomeFile(), zipFile.getAbsolutePath());
				}
			} else {
				File fileOriginale = File.createTempFile("temp", fileDaScaricare.getNomeFile());
				FileUtil.writeStreamToFile(StorageImplementation.getStorage().extract(fileDaScaricare.getUriFile()),fileOriginale);

				StorageUtil.addFileToZip(fileOriginale, fileDaScaricare.getNomeFile(), zipFile.getAbsolutePath());
			}
		}
		
		return warning;
	}

	/**
	 * @param fileDaScaricare
	 * @return
	 */
	public static String getNomeFileSbustato(String nomeFile) {
		String ext = ".p7m.m7m.std";
		String nomeFileSbustato = nomeFile;
		while (nomeFileSbustato.length() > 3 && ext.contains(nomeFileSbustato.substring(nomeFileSbustato.length() - 4))){
				nomeFileSbustato = nomeFileSbustato.substring(0, nomeFileSbustato.length() - 4);
				}
		return nomeFileSbustato;
	}

	/**
	 * @param fileDaScaricare
	 * @param fileOriginale2 
	 * @return 
	 * @throws IOException
	 * @throws Exception
	 * @throws StorageException
	 */
	public static File sbustaFileDaInserireNelloZip(File fileOriginale, String nomeFile)
			throws IOException, Exception, StorageException {
		
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		InputStream sbustatoStream = lInfoFileUtility.sbusta(fileOriginale, nomeFile);
		
		File fileSbustato = File.createTempFile("temp", nomeFile);
		
		FileUtil.writeStreamToFile(sbustatoStream, fileSbustato);
		
		return fileSbustato;
	}

	/**
	 * @param zipFile
	 * @param fileDaScaricare
	 * @param currentAttachment
	 * @return 
	 * @throws IOException
	 * @throws Exception
	 * @throws StorageException
	 */
	public static Integer insertFileTimbratoInZip(File zipFile, FileScaricoZipBean fileDaScaricare, String tipoTimbro, OpzioniTimbroBean opzioniTimbroScelteUtente, HttpSession session)
			throws IOException, Exception, StorageException {
		
		Integer warning = null;
	
		try {
			File fileTimbrato = timbraFileDaScaricare(fileDaScaricare, tipoTimbro, opzioniTimbroScelteUtente, session);
			
			String nomeFileTimbrato = opzioniTimbroScelteUtente!=null && StringUtils.isNotBlank(tipoTimbro) 
					&& "COPIA_CONFORME_CUSTOM".equalsIgnoreCase(tipoTimbro) ?
					fileDaScaricare.getNomeFile()
					: FilenameUtils.getBaseName(getNomeFileSbustato(fileDaScaricare.getNomeFile())) + "_timbrato.pdf";
			
			StorageUtil.addFileToZip(fileTimbrato, nomeFileTimbrato, zipFile.getAbsolutePath());
			
		}catch(Exception e) {
			logProtDS.error("Errore durante la timbratura del file " + fileDaScaricare.getNomeFile() + ": " + e.getMessage(), e);
			File fileOriginale = File.createTempFile("temp", fileDaScaricare.getNomeFile());
			
			FileUtil.writeStreamToFile(StorageImplementation.getStorage().extract(fileDaScaricare.getUriFile()),fileOriginale);
			
			StorageUtil.addFileToZip(fileOriginale, fileDaScaricare.getNomeFile(), zipFile.getAbsolutePath());	
			
			warning = 3;
		}
		
		return warning;
	}
	
	private static File timbraFileDaScaricare(FileScaricoZipBean fileDaScaricare, String finalita, OpzioniTimbroBean opzioniTimbroScelteUtente, HttpSession session) throws Exception {
		
		OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
		lOpzioniTimbroBean.setMimetype(fileDaScaricare.getMimeType());
		lOpzioniTimbroBean.setUri(fileDaScaricare.getUriFile());
		lOpzioniTimbroBean.setNomeFile(fileDaScaricare.getNomeFile());
		lOpzioniTimbroBean.setIdUd(String.valueOf(fileDaScaricare.getIdUd()));
		lOpzioniTimbroBean.setIdDoc(String.valueOf(fileDaScaricare.getIdDoc()));
		lOpzioniTimbroBean.setRemote(true);
		lOpzioniTimbroBean.setFinalita(finalita);
		lOpzioniTimbroBean.setRotazioneTimbro(opzioniTimbroScelteUtente.getRotazioneTimbro());
		lOpzioniTimbroBean.setPosizioneTimbro(opzioniTimbroScelteUtente.getPosizioneTimbro());
		lOpzioniTimbroBean.setTipoPagina(opzioniTimbroScelteUtente.getTipoPagina());
		lOpzioniTimbroBean.setMoreLines(opzioniTimbroScelteUtente.isMoreLines());
		lOpzioniTimbroBean.setCodifica(opzioniTimbroScelteUtente.getCodifica());
		lOpzioniTimbroBean.setPosizioneIntestazione(opzioniTimbroScelteUtente.getPosizioneIntestazione());
		lOpzioniTimbroBean.setPosizioneTestoInChiaro(opzioniTimbroScelteUtente.getPosizioneTestoInChiaro());
		lOpzioniTimbroBean.setTimbroSingolo(opzioniTimbroScelteUtente.isTimbroSingolo());
		
		TimbraUtility timbraUtility = new TimbraUtility();
		lOpzioniTimbroBean = getProprietaTimbro(lOpzioniTimbroBean, session, UserUtil.getLocale(session));

		String uriFileTimbrato;
		
		// Timbro il file
		TimbraResultBean lTimbraResultBean = timbraUtility.timbra(lOpzioniTimbroBean, session);
		// Verifico se la timbratura è andata a buon fine
		if (lTimbraResultBean.isResult()) {
			// Aggiungo il file timbrato nella lista dei file da pubblicare
			uriFileTimbrato = lTimbraResultBean.getUri();
		} else {
			// // La timbratura è fallita, pubblico il file sbustato
			// files.add(StorageImplementation.getStorage().extractFile(uriFileSbustato));
			String errorMessage = "Si è verificato un errore durante la timbratura del file";
			if (StringUtils.isNotBlank(lTimbraResultBean.getError())) {
				errorMessage += ": " + lTimbraResultBean.getError();
			}
			throw new Exception(errorMessage);
		}	
		
		File lFileTimbrato = StorageImplementation.getStorage().extractFile(uriFileTimbrato);
			
		return lFileTimbrato;
			
	}

	private static OpzioniTimbroBean getProprietaTimbro(OpzioniTimbroBean lOpzioniTimbroBean, HttpSession session,
			Locale locale) throws Exception {

		DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
		DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
		input.setIdudio(new BigDecimal(lOpzioniTimbroBean.getIdUd()));
		input.setIddocin(lOpzioniTimbroBean.getIdDoc() != null && !"".equals(lOpzioniTimbroBean.getIdDoc()) ? new BigDecimal(lOpzioniTimbroBean.getIdDoc()) : null);
		input.setNroallegatoin(lOpzioniTimbroBean.getNroProgAllegato());
		input.setFinalitain(lOpzioniTimbroBean.getFinalita());

		StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> result = store.execute(locale, AurigaUserUtil.getLoginInfo(session), input);

		if (result.isInError()) {
			throw new StoreException(result);
		}
		lOpzioniTimbroBean.setTesto(result.getResultBean().getContenutobarcodeout());
		lOpzioniTimbroBean.setTestoIntestazione(result.getResultBean().getTestoinchiaroout());

		OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAttachEmail");

		lOpzioniTimbroBean.setPosizioneIntestazione(lOpzioniTimbroAttachEmail.getPosizioneIntestazione().value());

		if (lOpzioniTimbroBean.getRotazioneTimbro() == null || "".equalsIgnoreCase(lOpzioniTimbroBean.getRotazioneTimbro())) {
			lOpzioniTimbroBean.setRotazioneTimbro(lOpzioniTimbroAttachEmail.getRotazioneTimbro().value());
		} 
		if (lOpzioniTimbroBean.getPosizioneTimbro() == null || "".equalsIgnoreCase(lOpzioniTimbroBean.getPosizioneTimbro())) {
			lOpzioniTimbroBean.setPosizioneTimbro(lOpzioniTimbroAttachEmail.getPosizioneTimbro().value());
		}
		if (lOpzioniTimbroBean.getTipoPagina() == null || "".equalsIgnoreCase(lOpzioniTimbroBean.getTipoPagina())) {
			lOpzioniTimbroBean.setTipoPagina(lOpzioniTimbroAttachEmail.getPaginaTimbro().getTipoPagina().value());
		}
		if (lOpzioniTimbroBean.getCodifica() == null || "".equalsIgnoreCase(lOpzioniTimbroBean.getCodifica())) {
			lOpzioniTimbroBean.setCodifica(lOpzioniTimbroAttachEmail.getCodifica() != null ? lOpzioniTimbroAttachEmail.getCodifica().value() : null);
		}		
		if (lOpzioniTimbroBean.getTipoPagina() == null || "".equalsIgnoreCase(lOpzioniTimbroBean.getTipoPagina())) {
			lOpzioniTimbroBean.setTipoPagina(lOpzioniTimbroAttachEmail.getPaginaTimbro().getTipoPagina().value());
		}

		return lOpzioniTimbroBean;

	}
	
	public static String getPrefissoNomeFileZip(String operazione, boolean operazionemultipla) {
		String prefix = "";
		
		if(operazionemultipla) {
			prefix = "FileSelezioneDocumenti_";
		}else {
			prefix = "File_";
		}
		
		switch(operazione) {
			case "scaricaCompletiXAtti":
				return prefix + "DocumentazioneCompleta_";
			case "scaricaOriginali":
				return prefix + "Originali_";
			case "scaricaTimbratiSegnatura":
				return prefix + "TimbroSegnatura_";
			case "scaricaTimbratiConformeStampa":
				return prefix + "TimbroCopiaConfStampa_";
			case "scaricaTimbratiConformeDigitale":
				return prefix + "TimbroCopiaConfDigitale_";
			case "scaricaTimbratiConformeCartaceo":
				return prefix + "TimbroConformitaOrig_";
			case "scaricaConformitaCustom":
				return prefix + "CopiaConforme_";
			case "scaricaSbustati":
				return prefix + "ContenutiBusteP7M_";
			default:
				return"";
		}
	}
}
