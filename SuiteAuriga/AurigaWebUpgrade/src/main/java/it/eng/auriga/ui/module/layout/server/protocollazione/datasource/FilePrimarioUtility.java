/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.ParametriDBUtil;

import java.io.File;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

/**
 * Gestisce la generazione del file primario per la protocollazione atti
 * @author massimo malvestio
 *
 */
public class FilePrimarioUtility {

	protected static Logger logger = Logger.getLogger(FilePrimarioUtility.class);
	
	/**
	 * Genera il file primario associato alla protocollazione specificata
	 * @param filePrimarioStorageUri bean di protcollazione atti da cui si attingono le informazioni principali
	 * @param loginBean necessario per l'esecuzione delle store
	 * @param locale necessario per l'esecuzione delle store
	 * @param session necessario per l'esecuzione delle store 
	 * @return in base a quanto eventualmente specificato nella tabella di configurazione DMT_DEF_CONFIG_PARAM mediante il l'entry ATTI_FILE_PRIMARIO_CUSTOM, 
	 * estrae il file primario utilizzando la modalità standard oppure quella custom, al momento è implementata solo la modalità milano
	 * @throws Exception
	 */
	public static FileDaFirmareBean generateFilePrimario(ProtocollazioneBean pProtocollazioneBean, String processId, 
			AurigaLoginBean loginBean, Locale locale, HttpSession session) throws Exception {
		
		FileDaFirmareBean retValue = null;
		
		if(ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_PROPOSTA_ATTO_2")) {					
			
			retValue = generateFileMilano(pProtocollazioneBean, processId, session);
			
		} else {
			
			retValue = generateFile(pProtocollazioneBean);
		}
		
		return retValue;

	}
	
	/**
	 * Genera il file primario in modo standard
	 * @param pProtocollazioneBean
	 * @return
	 */
	private static FileDaFirmareBean generateFile(ProtocollazioneBean pProtocollazioneBean) {
		
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		lFileDaFirmareBean.setIdFile("primario");
		lFileDaFirmareBean.setNomeFile(pProtocollazioneBean.getNomeFilePrimario());
		lFileDaFirmareBean.setUri(pProtocollazioneBean.getUriFilePrimario());
		lFileDaFirmareBean.setUriVerPreFirma(pProtocollazioneBean.getUriFileVerPreFirma());
		lFileDaFirmareBean.setIsFilePrincipaleAtto(true);
		lFileDaFirmareBean.setInfoFile(pProtocollazioneBean.getInfoFile());
		
		return lFileDaFirmareBean;
	}
	
	/**
	 * Genera il file primario in formato pdf unendo la pagina di testata compilata mediante un set "statico" di dati associati al processo 
	 * al file primario originale
	 * @param session 
	 */
	private static FileDaFirmareBean generateFileMilano(ProtocollazioneBean pProtocollazioneBean, String processId, HttpSession session)
			throws Exception {
				
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		
		if(!pProtocollazioneBean.getInfoFile().getMimetype().equalsIgnoreCase("application/pdf")) {
			File  fileModello = ModelliUtil.fillTemplateAndConvertToPdf(processId, pProtocollazioneBean.getUriFilePrimario(), pProtocollazioneBean.getTipoModFilePrimario(), session);			
			String storageUri = StorageImplementation.getStorage().store(fileModello);
			lFileDaFirmareBean.setIdFile("primario");
			lFileDaFirmareBean.setNomeFile(FilenameUtils.getBaseName(pProtocollazioneBean.getNomeFilePrimario()) + ".pdf");
			lFileDaFirmareBean.setUri(storageUri);
			lFileDaFirmareBean.setIsFilePrincipaleAtto(true);
			MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
			infoFile.setMimetype("application/pdf");
			infoFile.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(fileModello));
			infoFile.setDaScansione(false);
			infoFile.setFirmato(false);
			infoFile.setFirmaValida(false);
			infoFile.setBytes(fileModello.length());
			infoFile.setCorrectFileName(lFileDaFirmareBean.getNomeFile());		
			File realFile = StorageImplementation.getStorage().getRealFile(lFileDaFirmareBean.getUri());		
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), lFileDaFirmareBean.getNomeFile()));
			lFileDaFirmareBean.setInfoFile(infoFile);
		} else {			
			lFileDaFirmareBean.setIdFile("primario");
			lFileDaFirmareBean.setNomeFile(pProtocollazioneBean.getNomeFilePrimario());
			lFileDaFirmareBean.setUri(pProtocollazioneBean.getUriFilePrimario());
			lFileDaFirmareBean.setIsFilePrincipaleAtto(true);
			lFileDaFirmareBean.setInfoFile(pProtocollazioneBean.getInfoFile());		
		} 
		
		return lFileDaFirmareBean;
	}
	
}
