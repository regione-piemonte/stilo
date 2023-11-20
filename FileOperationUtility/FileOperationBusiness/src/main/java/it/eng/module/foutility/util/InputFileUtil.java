/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.activation.DataHandler;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.FileOperation;
import it.eng.module.foutility.beans.generated.InputFile;
import it.eng.module.foutility.fo.CtrlBuilder;
import it.eng.module.foutility.fo.FileCtrlFactory;
import it.eng.module.foutility.fo.IFileController;
import it.eng.module.foutility.fo.InputFileBean;

/**
 * costruisce un file in base alle varie possibilità date da InputFile
 *
 */

public class InputFileUtil {
	
	private static final Logger log = LogManager.getLogger(InputFileUtil.class);

	public static File getTempFile(InputFile input, File tempDir, boolean launchErrorifNotFound, String sistemaOperativo, String fileName, String requestKey) throws Exception {
		File file=null;
		log.debug(requestKey + " - FileName " + fileName);
		if (input.getFileStream() != null) {
			log.debug(requestKey + " - Ho ricevuto il file per stream");
			//read File from DataHandler
			DataHandler dh=input.getFileStream();
			String ext = "stemp";
			if (!StringUtils.isBlank(fileName)) {
//				int index = -1;
//				if( (index = fileName.lastIndexOf(".")) > -1){
//					ext = fileName.substring(index);
//					log.debug("Estensione file di input " + ext );
//				}
				fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "");
				ext = FilenameUtils.getExtension(fileName);
				
				if (ext != null && (ext.equalsIgnoreCase("p7m"))) {
					//nomeFilePassato = nomeFilePassato.substring(0, nomeFilePassato.indexOf(estensione) - 1);
					String fileName1 = FilenameUtils.getBaseName(fileName);
					//estensione = nomeFilePassato.substring(nomeFilePassato.lastIndexOf(".") + 1, nomeFilePassato.length());
					ext = FilenameUtils.getExtension(fileName1) + "." + ext;
				}
				
				if (StringUtils.isNotBlank(ext)) {
					ext = ext.toLowerCase();
					String baseName = FilenameUtils.getBaseName(fileName);
					fileName = baseName + "." + ext;
				}
				
				log.debug(requestKey + " - Estensione file di input " + ext );
			}
			
			log.debug(requestKey + " - Creo il file temp nella dir " + tempDir + " con estensione " + ext);
			file=File.createTempFile("input", "." + ext, tempDir);
			log.debug(requestKey + " - File temp creato " + file);
			try {
				FileOutputStream lFileOutputStream = new FileOutputStream(file);
				InputStream is = dh.getInputStream();
				IOUtils.copyLarge(is, lFileOutputStream);
				if (is != null )
					is.close();
				if (lFileOutputStream != null)
					lFileOutputStream.close();
				//IOUtils.closeQuietly(is);
				//IOUtils.closeQuietly(lFileOutputStream);
			} catch (Exception e) {
				log.fatal(requestKey + " - fatal creating file from stream", e);
				throw new Exception(requestKey + " - impossibile leggere il file in ingresso", e);
			}
			return file;
		}else if (input.getFileUrl() != null){
			log.debug(requestKey + " - Ho ricevuto il file per url");
			// produce from URL
			try{
				String fileUrl = input.getFileUrl();
				String ext = "stemp";
				if (!StringUtils.isBlank(fileName)) {
//					int index = -1;
//					if( (index = fileName.lastIndexOf(".")) > -1){
//						ext = fileName.substring(index);
//						log.debug("Estensione file di input " + ext );
//					}
					fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "");
					ext = FilenameUtils.getExtension(fileName);
					if (ext != null && (ext.equalsIgnoreCase("p7m") ) ) {
						//nomeFilePassato = nomeFilePassato.substring(0, nomeFilePassato.indexOf(estensione) - 1);
						String fileName1 = FilenameUtils.getBaseName(fileName);
						//estensione = nomeFilePassato.substring(nomeFilePassato.lastIndexOf(".") + 1, nomeFilePassato.length());
						ext = FilenameUtils.getExtension(fileName1) + "." + ext;
					}
					if (StringUtils.isNotBlank(ext)) {
						ext = ext.toLowerCase();
						String baseName = FilenameUtils.getBaseName(fileName);
						fileName = baseName + "." + ext;
					}
					log.debug(requestKey + " - Estensione file di input " + ext );
				} else {
//					int index = -1;
//					if( (index = fileUrl.lastIndexOf(".")) > -1){
//						ext = fileUrl.substring(index);
//						log.debug("Estensione file di input " + ext );
//					}
					File fUrl = new File(fileUrl);
					if (!StringUtils.isBlank(fUrl.getName())) {
						String fUrlNormalized = fUrl.getName().replaceAll("[^a-zA-Z0-9\\.\\-]", "");
						ext = FilenameUtils.getExtension(fUrlNormalized);
						log.debug(requestKey + " - Estensione file di input " + ext );
						
						if (StringUtils.isNotBlank(ext)) {
							ext = ext.toLowerCase();
							String baseName = FilenameUtils.getBaseName(fUrl.getName());
							fileName = baseName + "." + ext;
						}
					}
				}
			
				if (sistemaOperativo != null && sistemaOperativo.equalsIgnoreCase(SOType.LINUX.name())){
					if (fileUrl !=null && !fileUrl.startsWith("file:/"))
						fileUrl = fileUrl.replace("file:", "file:/");
				}
				log.debug(requestKey + " - fileUrl " + fileUrl);
				file=File.createTempFile("input", "." + ext, tempDir);
				FileUtils.copyURLToFile(new URL(fileUrl), file);
			}catch(Exception ex){
				throw new Exception(requestKey + " - impossibile leggere il file dalla url", ex);
			}
		}else if (input.getOther() != null) {
			//TODO
			throw new Exception(requestKey + " - not supported yet!");
		}else{
			if (launchErrorifNotFound)
				throw new Exception(requestKey + " - file non trovato");
		}
		log.info(requestKey + " - File di input " + file);
		return file;
	}
	
	public static String getTempFileName(InputFileBean input) {
		if (!StringUtils.isBlank( input.getFileName())) {
			return input.getFileName();
		} else if (input.getInputFile() != null) {
			File file = input.getInputFile();
			String estensioneFile = FilenameUtils.getExtension(file.getName());
			if (!StringUtils.isBlank(estensioneFile))
				return file.getName();
		}
		return null;
	}
	
	public static String getIdOperazioneByInput(AbstractInputOperationType input){
		CtrlBuilder builder=FileCtrlFactory.getCtrlBuilder();
		IFileController ctrl = builder.build(input);
		if (ctrl != null)
			return ctrl.getOperationType();
		return null;
	}
	
	public static InputFileBean readInputFileBean(FileOperation input,File tempDirectory, String sistemaOperativo, String requestKey)throws Exception{
		InputFileBean ifb= new InputFileBean();
		File file;
		//recupero il file in input
		file=InputFileUtil.getTempFile(input.getFileOperationInput().getInputType(),tempDirectory,true, sistemaOperativo, input.getFileOperationInput().getOriginalName(), requestKey );
		ifb.setInputFile(file);
		//recupero il timestamp se passato
		if(input.getFileOperationInput().getTimeStampFile()!=null){
			File timestamp=InputFileUtil.getTempFile(input.getFileOperationInput().getTimeStampFile(),tempDirectory,false, sistemaOperativo, input.getFileOperationInput().getOriginalName(), requestKey);
			ifb.setTimestamp(timestamp);
		}
		//leggo il filename se passato
		if(input.getFileOperationInput().getOriginalName()!=null && !input.getFileOperationInput().getOriginalName().equalsIgnoreCase("") ){
			String originalName = input.getFileOperationInput().getOriginalName();
			
			String ext = FilenameUtils.getExtension(originalName);
			if (StringUtils.isNotBlank(ext)) {
				ext = ext.toLowerCase();
				String baseName = FilenameUtils.getBaseName(originalName);
				originalName = baseName + "." + ext;
			}
			log.info(requestKey + " - Setto original filename " +  originalName);
			
			ifb.setFileName( originalName );
		}
		return ifb;

	}
	
}
