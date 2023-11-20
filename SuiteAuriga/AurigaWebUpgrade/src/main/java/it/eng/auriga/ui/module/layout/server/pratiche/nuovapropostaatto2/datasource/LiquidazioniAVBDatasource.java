/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.foglioImportato.bean.FileAssociatoFoglioBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DatiLiquidazioneAVBBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.LiquidazioneAvbDSBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ListaLiquidazioneAvbDSBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ListaLiquidazioneBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.OperazioneMassivaZipLiquidazioniXmlBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ZipLiquidazioniBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DownloadDocsZipBean;
import it.eng.document.function.bean.DatiLiquidazioneAVBXmlBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.FileUtil;
import it.eng.utility.MessageUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "LiquidazioniAVBDatasource")
public class LiquidazioniAVBDatasource extends AbstractFetchDataSource<DatiLiquidazioneAVBBean>{
	
	private static final Logger logger = Logger.getLogger(LiquidazioniAVBDatasource.class);
	
	public ListaLiquidazioneBean importaDatiLiquidazioniFromExcel(ListaLiquidazioneAvbDSBean bean)
			throws Exception {

//		String cdrUOCompetente = getExtraparams().get("cdrUOCompetente");

		ListaLiquidazioneBean listaLiquidazioni = new ListaLiquidazioneBean();
		List<DatiLiquidazioneAVBBean> caricaDatiFromXlsx = new ArrayList<DatiLiquidazioneAVBBean>();		

		LiquidazioniAVBExcelUtility lLiquidazioniAVBExcelUtility = new LiquidazioniAVBExcelUtility();
		try {

			for (LiquidazioneAvbDSBean beanDs : bean.getListaDati()) {
				
				String uriExcel = beanDs.getUriExcel();
				String mimeType = beanDs.getMimeType();
				boolean isXls = mimeType.equals("application/excel");
				boolean isXlsx = mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				DatiLiquidazioneAVBBean datiLiq = new DatiLiquidazioneAVBBean ();
				
				/**
				 * Implementazione HSSF (Horrible SpreadSheet Format): indica un'API che funziona con Excel 2003 o versioni precedenti.
				 */
				if (isXls) {
					// ATTENZIONE : CUSTOMIZZARE METODO PER XLS
					datiLiq = lLiquidazioniAVBExcelUtility.caricaDatiFromXls(uriExcel, getSession());
					datiLiq.setNomeFile(beanDs.getNomeFile());
					caricaDatiFromXlsx.add(datiLiq);
				}
				
				/**
				 * Implementazione XSSF (XML SpreadSheet Format): indica un'API che funziona con Excel 2007 o versioni successive.
				 */
				else if (isXlsx) { 
					datiLiq = lLiquidazioniAVBExcelUtility.caricaDatiFromXlsx(uriExcel, getSession());
					datiLiq.setNomeFile(beanDs.getNomeFile());
					caricaDatiFromXlsx.add(datiLiq);
				} 
				
				else {
					String message = "Il formato del documento non è supportato, solo xls e xlsx sono ammessi come documenti validi";
					logger.error(message);
					
					throw new StoreException(message);
				}
			}
			
		} catch (Exception e) {
			String errorMessage = e.getMessage() != null ? e.getMessage() : e.getCause() != null ? e.getCause().getMessage() : null;

			String message = "Durante il caricamento delle righe del file, si è verificata la seguente eccezione: " + errorMessage;
			logger.error(message, e);

			throw new StoreException(message); 
		}


		listaLiquidazioni.setListaLiquidazioni(caricaDatiFromXlsx);
		return listaLiquidazioni;
	}
	
	public DownloadDocsZipBean createZipExportLiquidazioni(OperazioneMassivaZipLiquidazioniXmlBean bean) throws Exception {
		
		String limiteMaxZipErrorMessage = MessageUtil.getValue(getLocale().getLanguage(), null, "alert_archivio_list_limiteMaxZipError");
		String maxSize = ParametriDBUtil.getParametroDB(getSession(), "MAX_SIZE_ZIP");

		long MAX_SIZE = (maxSize != null && maxSize.length() > 0 ? Long.decode(maxSize) : 104857600);
		long lengthZip = 0;

		String tempPath = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");
		
		Date date = new Date();
	    long timeMilli = date.getTime();

		File zipFile = new File(tempPath + "FileSelezioneDocumenti_" +  timeMilli + ".zip");

		Map<String, Integer> zipFileNames = new HashMap<String, Integer>();
		
		for (int i = 0; i < bean.getListaRecord().size(); ++i) {
			DatiLiquidazioneAVBXmlBean liquidazione = (DatiLiquidazioneAVBXmlBean) bean.getListaRecord().get(i);
			if (liquidazione.getUriExcel()!=null && !liquidazione.getUriExcel().equalsIgnoreCase("")){
				
				
				ZipLiquidazioniBean lZipLiquidazioniBean = new ZipLiquidazioniBean();
				lZipLiquidazioniBean.setUriFile(liquidazione.getUriExcel());
				
				MimeTypeFirmaBean lMimeTypeFirmaBean = calcolaInfoFile (lZipLiquidazioniBean);
				
				lengthZip += lMimeTypeFirmaBean.getBytes(); 
				
				if (lengthZip > MAX_SIZE) {
					throw new StoreException(limiteMaxZipErrorMessage);
				}
				String fileNameToAdd = "";
				String fileExt = "";				
				if(lMimeTypeFirmaBean.getMimetype()!=null && !lMimeTypeFirmaBean.getMimetype().equalsIgnoreCase("")){
					if(lMimeTypeFirmaBean.getMimetype().equalsIgnoreCase("application/excel")){
						fileExt = ".xls";
					}
					if(lMimeTypeFirmaBean.getMimetype().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
						fileExt = ".xlsx";
					}
				}
				fileNameToAdd = "Dati_Liquidazioni_AVB_" + String.valueOf(i+1) + fileExt;
				
				fileNameToAdd = checkAndRenameDuplicate(zipFileNames, fileNameToAdd);
				File fileToAddTemp = File.createTempFile("temp",  fileNameToAdd);
				addFileZip( liquidazione.getUriExcel(), fileNameToAdd, fileToAddTemp, zipFile);
			}
		}
		
		DownloadDocsZipBean retValue = new DownloadDocsZipBean();		
		try {
			if (!zipFileNames.isEmpty()) {
				String zipUri = StorageImplementation.getStorage().store(zipFile);

				retValue.setStorageZipRemoteUri(zipUri);
				retValue.setZipName(zipFile.getName());
				MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
				infoFile.setBytes(zipFile.length());
				retValue.setInfoFile(infoFile);
			} else {
				retValue.setMessage("I documenti selezionati non hanno nessun file");
			}

		} finally {
			// una volta salvato in storage lo posso eliminare localmente
			FileDeleteStrategy.FORCE.delete(zipFile);
		}
		return retValue;
	}
	
	public void addFileZip(String uriFile, String attachmentFileName, File currentAttachmentTemp, File zipFile) throws Exception, StorageException, IOException, FileNotFoundException {
		
		File currentAttachment;
		try {
			String pathWithoutTempName= currentAttachmentTemp.getPath().replaceAll(FilenameUtils.getName(currentAttachmentTemp.getPath()), attachmentFileName);
			currentAttachment = new File(pathWithoutTempName);
			currentAttachmentTemp.renameTo(currentAttachment);
		} catch (Exception e) {
			currentAttachment = currentAttachmentTemp;
			throw new StoreException("Errore durante la rinomina del file da inserire nello zip: " + e.getMessage());
		}
		if (!StringUtils.isBlank(uriFile)) {
			FileUtil.writeStreamToFile(StorageImplementation.getStorage().extract(uriFile), currentAttachment);
			StorageUtil.addFileToZip(currentAttachment, zipFile.getAbsolutePath());
		} 
		FileDeleteStrategy.FORCE.delete(currentAttachment);
	}
	
	@Override
	public PaginatorBean<DatiLiquidazioneAVBBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		return null;
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
	
	public MimeTypeFirmaBean calcolaInfoFile (ZipLiquidazioniBean bean) throws Exception {
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();		
		String uriFile = bean.getUriFile();	
		String displayFileName = bean.getFileName();	
		InfoFileUtility lFileUtility = new InfoFileUtility();
		File file= StorageImplementation.getStorage().extractFile(uriFile);
		lMimeTypeFirmaBean= lFileUtility.getInfoFromFile(file.toURI().toString(), displayFileName, false, null);
		return lMimeTypeFirmaBean;
	}
}
