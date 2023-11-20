/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
// import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.DataHandler;
import javax.activation.MimeType;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.mail.smime.SMIMESignedParser;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.exception.RarException.RarExceptionType;
import com.github.junrar.rarfile.FileHeader;

import it.eng.module.archiveUtility.ArchiveUtils;
import it.eng.module.archiveUtility.FileProtetto;
import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.SbustamentoBean;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.InputUnpackMultipartType;
import it.eng.module.foutility.beans.generated.MultipartContentType;
import it.eng.module.foutility.beans.generated.ResponseUnpackMultipartType;
import it.eng.module.foutility.beans.generated.ResponseUnpackMultipartType.MultipartContents;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.exception.FOException;
import it.eng.module.foutility.util.FileOpMessage;
import it.eng.module.foutility.util.FileoperationContextProvider;
import it.eng.module.foutility.util.InfoFile;
import it.eng.suiteutility.mimedetector.MimeDetectorException;
import it.eng.suiteutility.mimedetector.implementations.mimeutils.MimeUtilsAdapter;
import it.eng.suiteutility.module.mimedb.DaoAnagraficaFormatiDigitali;
import it.eng.suiteutility.module.mimedb.entity.TAnagFormatiDig;
//import net.sf.sevenzipjbinding.ExtractAskMode;
//import net.sf.sevenzipjbinding.ExtractOperationResult;
//import net.sf.sevenzipjbinding.IArchiveExtractCallback;
//import net.sf.sevenzipjbinding.IInArchive;
//import net.sf.sevenzipjbinding.ISequentialOutStream;
//import net.sf.sevenzipjbinding.ISevenZipInArchive;
//import net.sf.sevenzipjbinding.ISevenZipInArchive;
//import net.sf.sevenzipjbinding.PropID;
//import net.sf.sevenzipjbinding.SevenZip;
//import net.sf.sevenzipjbinding.SevenZipException;
//import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;

/**
 * operazione di scompattamento
 * 
 * @author MGarofalo
 * 
 */
public class UnpackMultipartCtrl extends AbstractFileController {

	public static final Logger log = LogManager.getLogger(UnpackMultipartCtrl.class);

	public String operationType;

	public static final String UnpackCtrlCode = UnpackMultipartCtrl.class.getName();

	private FormatRecognitionCtrl formatRecognitionCtrl;
	private UnpackCtrl unpackCtrl;
	private DaoAnagraficaFormatiDigitali dao;

	public final String POSTA_CERTIFICATA = "posta-certificata";
	public final String ERRORE = "errore";

	// file estratto dalle buste (File)
	public static final String EXTRACTED_FILE = "extractedFile";
	// indica se il file è firmato (boolean)
	public static final String IS_ROOT_SIGNED = "isRootSigned";

	@Override
	public boolean execute(InputFileBean input, AbstractInputOperationType customInput, OutputOperations output, String requestKey) {
		log.debug("Metodo execute di UnpackMultipartCtrl ");
		boolean ret = false;
		// risultato dell'operazione
		ResponseUnpackMultipartType opResult = new ResponseUnpackMultipartType();
		InputUnpackMultipartType inputUnpackMultipart = ((InputUnpackMultipartType) customInput);

		File file = output.getPropOfType(UnpackCtrl.EXTRACTED_FILE, File.class);
		if (file == null) {
			log.warn("file sbustato non trovato, uso il file di input");
			file = input.getInputFile();
		}
		log.debug("isRecursive? " + inputUnpackMultipart.isRecursive());
		log.debug("abilitaSbustamento? " + inputUnpackMultipart.isAbilitaSbustamento());

		String unpackPath = inputUnpackMultipart.getUnpackPath();
		log.debug("unpackPath = " + unpackPath);
		
		// invoco l'operazione di unpack
		try {
			opResult.setMultipartContents(new MultipartContents());
			String extractedFileName = output.getPropOfType(UnpackCtrl.EXTRACTED_FILE_NAME, String.class);
			log.info("extractedFileName " + extractedFileName);

			ret = addResponseContentMultipart(file, input.getTemporaryDir().getAbsolutePath(), "", inputUnpackMultipart.isRecursive(),
					inputUnpackMultipart.isAbilitaSbustamento(), opResult, null, opResult.getMultipartContents().getMultipartContent(), 
					1, extractedFileName, unpackPath, requestKey);
			if (ret) {
				opResult.setVerificationStatus(VerificationStatusType.OK);
			} else {
				OutputOperations.addError(opResult, FileOpMessage.UNPACKMULTIPART_OP_ERROR, VerificationStatusType.KO);
			}
		} catch (FOException e) {
			OutputOperations.addError(opResult, e.getErrMessage(), VerificationStatusType.KO, e.getArgumets());
		} catch (Exception e) {
			OutputOperations.addError(opResult, e.getMessage(), VerificationStatusType.KO);
		}

		output.addResult(this.getClass().getName(), opResult);

		log.debug("Fine servizio di unpack multipart");

		return ret;
	}

	public boolean addResponseContentMultipart(File file, String root, String parentDir, Boolean recursive, Boolean abilitaSbustamento,
			ResponseUnpackMultipartType opResult, MultipartContentType currentMultipartContentType, List<MultipartContentType> multipartContentList, int level,
			String extractedFileName, String unpackPath, String requestKey) throws Exception {

		formatRecognitionCtrl = FileoperationContextProvider.getApplicationContext().getBean("FormatRecognitionCtrl", FormatRecognitionCtrl.class);
		dao = new DaoAnagraficaFormatiDigitali();
		unpackCtrl = FileoperationContextProvider.getApplicationContext().getBean("UnpackCtrl", UnpackCtrl.class);

		log.debug("Livello di elaborazione " + level);
		List<InfoFile> contentFiles = null;
		try {
			contentFiles = getContentZIPMultipart(opResult, file, file.getParent(), level, abilitaSbustamento, extractedFileName, unpackPath, requestKey);
			// log.debug("ContentFiles " + contentFiles);
		} catch (FOException e) {
			log.error("Errore durante l'elaborazione del file " + file.getName()/* , e */);
			throw e;
		}

		boolean result = true;
		if (contentFiles != null) {

			for (InfoFile contentFile : contentFiles) {
				log.debug("Aggiungo alla response " + contentFile);
				if (contentFile.getError() != null && contentFile.getNome() == null && currentMultipartContentType != null) {
					log.debug("Setto l'errore " + contentFile.getError());
					currentMultipartContentType.setError(contentFile.getError());
					result = false;
				} else {
					MultipartContentType multipartContentType = new MultipartContentType();
					log.debug("Imposto il nome " + contentFile.getNome());
					multipartContentType.setNome(contentFile.getNome());
					log.debug("parentDir " + parentDir);
					log.debug("root " + root);
					try {
						String pathFile = contentFile.getPath().substring(root.length(), contentFile.getPath().length());
						if (contentFile.isDirectory()) {
							multipartContentType.setPath(pathFile);
						} else {
							if (pathFile.endsWith(File.separator))
								multipartContentType.setPath(pathFile + contentFile.getNome());
							else
								multipartContentType.setPath(pathFile + File.separator + contentFile.getNome());
						}
						log.debug("Imposto il path: " + multipartContentType.getPath());

					} catch (Throwable e) {
						log.error("", e);
					}

					log.debug("Imposto il mime " + contentFile.getMimeType());
					multipartContentType.setMimeType(contentFile.getMimeType());
					if (contentFile.getError() != null) {
						multipartContentType.setError(contentFile.getError());
						result = false;
					}
					// se richiesto ripeto ciclicamente l'operazione altrimenti
					// mi fermo al primo livello
					// il dafaul è true quindi eseguo la ricorsione se il parametro non e' specificato o
					// e' settato a true
					if (recursive == null || recursive) {
						result = result && addResponseContentMultipart(contentFile.getFile(), root, parentDir + contentFile.getNome(), recursive,
								abilitaSbustamento, null, multipartContentType, multipartContentType.getMultipartContent(), ++level, extractedFileName, null, requestKey);
						level--;
					}
					multipartContentList.add(multipartContentType);
				}

			}
		}

		return result;
	}

	public List<InfoFile> getContentZIPMultipart(ResponseUnpackMultipartType opResult, File input, String tempDir, int level, Boolean abilitaSbustamento,
			String extractedFileName, String unpackPath, String requestKey) throws FOException {
		List<InfoFile> files = new ArrayList<InfoFile>();
		log.info("Elaborazione file " + input);

		if (level > 1 && input.isDirectory()) {
			File[] filesInDir = input.listFiles();
			elaboraDirectory(filesInDir, tempDir, files, requestKey);
			return files;
		}

		// recuperare il mimetype del file in input per verificare se zip
		// oppure eml
		MimeType mimeType = null;
		TAnagFormatiDig formato = null;
		String mime = "";
		try {
			MimeUtilsAdapter adapter = new MimeUtilsAdapter();
			if (level == 1) {
				if (extractedFileName != null) {
					log.debug("Nome File sbustato " + extractedFileName);
					String estensioneFile = FilenameUtils.getExtension(extractedFileName);
					log.debug("estensioneFile " + estensioneFile);
					if (estensioneFile != null && !estensioneFile.equalsIgnoreCase("")) {
						mimeType = formatRecognitionCtrl.getMimeTypeExt(input, estensioneFile, requestKey, adapter);
						if (mimeType.getBaseType() == null || mimeType.getBaseType().equalsIgnoreCase("application/unknown")) {
							mimeType = formatRecognitionCtrl.getBestMimeType(input, adapter);
						}
					} else
						mimeType = formatRecognitionCtrl.getBestMimeType(input, adapter);
				} else
					mimeType = formatRecognitionCtrl.getBestMimeType(input, adapter);
			} else {
				String estensioneFile = FilenameUtils.getExtension(input.getName());
				log.debug("estensioneFile " + estensioneFile);
				if (estensioneFile != null && !estensioneFile.equalsIgnoreCase(""))
					mimeType = formatRecognitionCtrl.getMimeTypeExt(input, estensioneFile, requestKey, adapter);
				else
					mimeType = formatRecognitionCtrl.getBestMimeType(input, adapter);
			}
			log.debug("mimeType " + mimeType);
			if (mimeType != null) {
				formato = dao.findFormatByMimeType(mimeType.getBaseType());
				// log.debug("formato file in elaborazione " + formato);
				if (formato != null) {
					mime = formato.getMimetypePrincipale();
				} // else
					// mime= "undefined";
			}
		} catch (MimeDetectorException e) {
			log.error("Eccezione getContentZIPMultipart", e);
		} catch (Exception e) {
			log.error("Eccezione getContentZIPMultipart", e);
		}

		log.info("Mime principale del file in elaborazione " + mime);

		if (opResult != null) {
			opResult.setMultipartMimeType(mime);
		}

		if (formato != null) {
			
			if( unpackPath!=null ){
				try {
					ArchiveUtils.getArchiveContent(input, formato.getMimetypePrincipale(), unpackPath);
				} catch (Exception e) {
					log.error("", e);
				}
			}
			
			// smime
			if ("message/rfc822".equalsIgnoreCase(formato.getMimetypePrincipale()) || "multipart/mixed".equalsIgnoreCase(formato.getMimetypePrincipale())) {
				// smime
				MimeMessage msg = null;
				FileInputStream streamInput = null;
				try {
					streamInput = new FileInputStream(input);
					msg = new MimeMessage(null, streamInput);

					MimeMultipart multipart = null;

					multipart = (MimeMultipart) msg.getContent();

					// log.info("multipart.getCount() " + multipart.getCount() );

					for (int j = 0; j < multipart.getCount(); j++) {

						MimeUtilsAdapter adapter = new MimeUtilsAdapter();
						BodyPart bodyPart = multipart.getBodyPart(j);

						String disposition = bodyPart.getDisposition();
						// log.info("disposition " + disposition );
						String fileName;
						DataHandler handler = bodyPart.getDataHandler();
						fileName = handler.getName();
						log.info("fileName " + fileName);

						if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) {
							log.debug("Attachment");
						} else {
							if (fileName == null || "".equals(fileName)) {
								fileName = "corpoMessaggio.txt";
							} else {
								// log.debug("file name : " + fileName );
								fileName = "elemento" + j;
							}
						}
						log.debug("file name : " + handler.getName());
						File newFile = new File(tempDir + File.separator + fileName);

						FileOutputStream fos = new FileOutputStream(newFile);
						byte[] buffer = new byte[1024];
						InputStream inputStream = handler.getInputStream();
						int len;
						while ((len = inputStream.read(buffer)) > 0) {
							fos.write(buffer, 0, len);
						}
						fos.close();
						mimeType = formatRecognitionCtrl.getBestMimeType(newFile, adapter);
						dao = new DaoAnagraficaFormatiDigitali();
						formato = dao.findFormatByMimeType(mimeType.getBaseType());

						InfoFile infoFile = new InfoFile();
						infoFile.setFile(newFile);
						infoFile.setNome(fileName);
						infoFile.setPath(newFile.getParent());
						infoFile.setMimeType(formato.getMimetypePrincipale());

						files.add(infoFile);

					}
				} catch (IOException e) {
					log.error("Eccezione getContentZIPMultipart", e);
				} catch (MessagingException e) {
					log.error("Eccezione getContentZIPMultipart", e);
				} catch (MimeDetectorException e) {
					log.error("Eccezione getContentZIPMultipart", e);
				} catch (Exception e) {
					log.error("Eccezione getContentZIPMultipart", e);
				} catch (Throwable e) {
					log.error("Eccezione getContentZIPMultipart", e);
				} finally {
					if (streamInput != null)
						IOUtils.closeQuietly(streamInput);
				}
			} else if ("multipart/signed".equalsIgnoreCase(formato.getMimetypePrincipale())) {
				log.debug("Cerco di allegati per il file con formato multipart/signed");
				MimeMessage msg = null;
				FileInputStream streamInput = null;
				boolean isMultipartSigned = false;
				try {
					streamInput = new FileInputStream(input);
					msg = new MimeMessage(null, streamInput);

					try {
						new SMIMESignedParser(new BcDigestCalculatorProvider(), (MimeMultipart) msg.getContent());
						isMultipartSigned = true;
					} catch (Throwable t) {
						log.error("Eccezione getContentZIPMultipart", t);
					}

					log.debug("isMultipartSigned " + isMultipartSigned);
					if (isMultipartSigned) {
						final SMIMESignedParser s = new SMIMESignedParser(new BcDigestCalculatorProvider(), (MimeMultipart) msg.getContent());
						DataHandler data;
						data = s.getContent().getDataHandler();

						final MimeMultipart multiPart = (MimeMultipart) data.getContent();
						for (int i = 0; i < multiPart.getCount(); i++) {
							MimeUtilsAdapter adapter = new MimeUtilsAdapter();
							final BodyPart bodyPiece = multiPart.getBodyPart(i);

							DataHandler dataHandlerMulti = bodyPiece.getDataHandler();

							String fileName = bodyPiece.getFileName();
							if (fileName == null || "".equals(fileName)) {
								fileName = "corpoMessaggio.txt";
							}
							log.debug("Allegato " + fileName);
							File newFile = new File(tempDir + File.separator + fileName);
							new File(newFile.getParent()).mkdirs();

							FileOutputStream fos = new FileOutputStream(newFile);
							byte[] buffer = new byte[1024];
							InputStream inputStream = dataHandlerMulti.getInputStream();
							int len;
							while ((len = inputStream.read(buffer)) > 0) {
								fos.write(buffer, 0, len);
							}
							fos.close();
							inputStream.close();
							mimeType = formatRecognitionCtrl.getBestMimeType(newFile, adapter);
							dao = new DaoAnagraficaFormatiDigitali();
							formato = dao.findFormatByMimeType(mimeType.getBaseType());
							log.debug("Formato allegato " + formato.getMimetypePrincipale());

							InfoFile infoFile = new InfoFile();
							infoFile.setFile(newFile);
							infoFile.setNome(fileName);
							infoFile.setPath(newFile.getParent());
							infoFile.setMimeType(formato.getMimetypePrincipale());

							files.add(infoFile);
						}
					}

					else {
						MimeMultipart multipart = null;

						multipart = (MimeMultipart) msg.getContent();

						log.debug("multipart.getCount() " + multipart.getCount());

						for (int j = 0; j < multipart.getCount(); j++) {

							MimeUtilsAdapter adapter = new MimeUtilsAdapter();
							BodyPart bodyPart = multipart.getBodyPart(j);

							String disposition = bodyPart.getDisposition();
							// log.info("disposition " + disposition );
							String fileName;
							DataHandler handler = bodyPart.getDataHandler();
							fileName = handler.getName();

							if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) {

							} else {
								if (fileName == null || "".equals(fileName)) {
									fileName = "corpoMessaggio.txt";
								} else {
									fileName = "elemento" + j;
								}
							}
							log.debug("Allegato " + fileName);

							File newFile = new File(tempDir + File.separator + fileName);
							new File(newFile.getParent()).mkdirs();

							FileOutputStream fos = new FileOutputStream(newFile);
							byte[] buffer = new byte[1024];
							InputStream inputStream = handler.getInputStream();
							int len;
							while ((len = inputStream.read(buffer)) > 0) {
								fos.write(buffer, 0, len);
							}
							fos.close();
							inputStream.close();
							mimeType = formatRecognitionCtrl.getBestMimeType(newFile, adapter);
							dao = new DaoAnagraficaFormatiDigitali();
							formato = dao.findFormatByMimeType(mimeType.getBaseType());
							log.debug("Formato allegato " + formato.getMimetypePrincipale());

							InfoFile infoFile = new InfoFile();
							infoFile.setFile(newFile);
							infoFile.setNome(fileName);
							infoFile.setPath(newFile.getParent());
							infoFile.setMimeType(formato.getMimetypePrincipale());

							files.add(infoFile);
						}
					}
				} catch (MessagingException e) {
					log.error("Eccezione getContentZIPMultipart", e);
				} catch (FileNotFoundException e) {
					log.error("Eccezione getContentZIPMultipart", e);
				} catch (IOException e) {
					log.error("Eccezione getContentZIPMultipart", e);
				} catch (CMSException e) {
					log.error("Eccezione getContentZIPMultipart", e);
				} catch (MimeDetectorException e) {
					log.error("Eccezione getContentZIPMultipart", e);
				} catch (Exception e) {
					log.error("Eccezione getContentZIPMultipart", e);
				} finally {
					if (streamInput != null)
						IOUtils.closeQuietly(streamInput);
				}
			}
			// zip
			else if ("application/x-compressed".equalsIgnoreCase(mime)) {
				elaboraZip(input, mime, tempDir, level, files, abilitaSbustamento, requestKey);
			} // rar application/x-rar-compressed
			else if ("application/x-rar-compressed".equalsIgnoreCase(mime)) {
				elaboraRar(input, mime, tempDir, level, files, abilitaSbustamento, requestKey);
			} else if ("application/x-gzip-compressed".equalsIgnoreCase(mime)) {// tar
				elaboraTar(input, mime, tempDir, level, files, abilitaSbustamento, requestKey);
			} else if ("application/x-7z".equalsIgnoreCase(mime)) {// 7zip
				elabora7Zip(input, mime, tempDir, level, files, abilitaSbustamento, requestKey);
			}
			// non si tratta di un file valido
			else {
				if (level == 1) {
					log.info("Non si tratta di un file valido per questo tipo di operazione");
					throw new FOException(FileOpMessage.UNPACKMULTIPART_OP_ERROR_FILE_NOTVALID);
				} else {
					log.info("Non si tratta di un archivio");
				}
			}
		}

		// log.info("Fine metodo " + files );
		return files;
	}

	@Override
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

//	public static class MyExtractCallback implements IArchiveExtractCallback {
//
//		private /*IInArchive*/ ISevenZipInArchive  inArchive;
//		private int index;
//		private OutputStream outputStream;
//		private File file;
//		private boolean isFolder;
//		private String path;
//		private File outputDirectory;
//		private FormatRecognitionCtrl formatRecognitionCtrl;
//		private UnpackCtrl unpackCtrl;
//		private DaoAnagraficaFormatiDigitali dao;
//		private List<InfoFile> files;
//		private Boolean abilitaSbustamento;
//
//		public MyExtractCallback(/*IInArchive */ISevenZipInArchive  inArchive, File outputDirectory, FormatRecognitionCtrl formatRecognitionCtrl, UnpackCtrl unpackCtrl,
//				DaoAnagraficaFormatiDigitali dao, List<InfoFile> files, Boolean abilitaSbustamento) {
//			this.inArchive = inArchive;
//			this.outputDirectory = outputDirectory;
//			this.formatRecognitionCtrl = formatRecognitionCtrl;
//			this.unpackCtrl = unpackCtrl;
//			this.dao = dao;
//			this.files = files;
//			this.abilitaSbustamento = abilitaSbustamento;
//		}
//
//		@Override
//		public ISequentialOutStream getStream(final int index, ExtractAskMode extractAskMode) throws SevenZipException {
//			closeOutputStream();
//
//			this.index = index;
//			this.isFolder = (Boolean) inArchive.getProperty(index, PropID.IS_FOLDER);
//
//			if (extractAskMode != ExtractAskMode.EXTRACT) {
//				return null;
//			}
//			path = (String) inArchive.getProperty(index, PropID.PATH);
//			log.debug("Path nell'archivio " + path);
//
//			try {
//				file = new File(outputDirectory.getParent(), path);
//				if (isFolder) {
//					createDirectory(file);
//					return null;
//				}
//
//				createDirectory(file.getParentFile());
//				outputStream = new FileOutputStream(file);
//
//			} catch (FileNotFoundException e) {
//				throw new SevenZipException("Error opening file: " + file.getAbsolutePath(), e);
//			}
//
//			return new ISequentialOutStream() {
//
//				public int write(byte[] data) throws SevenZipException {
//					try {
//						outputStream.write(data);
//					} catch (IOException e) {
//						throw new SevenZipException("Error writing to file: " + file.getAbsolutePath());
//					}
//					return data.length; // Return amount of consumed data
//				}
//			};
//		}
//
//		private void createDirectory(File parentFile) throws SevenZipException {
//			if (!parentFile.exists()) {
//				if (!parentFile.mkdirs()) {
//					throw new SevenZipException("Error creating directory: " + parentFile.getAbsolutePath());
//				}
//			}
//		}
//
//		private void closeOutputStream() throws SevenZipException {
//			if (outputStream != null) {
//				try {
//					log.debug("Chiudo lo stream ");
//					outputStream.close();
//					outputStream = null;
//				} catch (IOException e) {
//					log.error("", e);
//					throw new SevenZipException("Error closing file: " + file.getAbsolutePath());
//				}
//			}
//		}
//
//		@Override
//		public void prepareOperation(ExtractAskMode extractAskMode) throws SevenZipException {
//		}
//
//		@Override
//		public void setOperationResult(ExtractOperationResult extractOperationResult) throws SevenZipException {
//			closeOutputStream();
//			String path = (String) inArchive.getProperty(index, PropID.PATH);
//			if (extractOperationResult != ExtractOperationResult.OK) {
//				//throw new SevenZipException("Invalid file: " + path);
//			}
//
//			MimeType mimeType;
//			TAnagFormatiDig formato = null;
//			String mime = "";
//
//			if (isFolder) {
//				mime = "";
//			} else {
//				try {
//					log.debug("File estratto " + file);
//
//					String fileName = file.getName();
//					if (abilitaSbustamento == null || abilitaSbustamento == true) {
//						File fileSbustato = unpackCtrl.extractDocumentFile(file, file.getParentFile(), file.getName());
//						log.debug("fileSbustato");
//						String nuovoNomeFile = unpackCtrl.rename(fileSbustato, fileName, unpackCtrl.getFormatoBusta());
//						log.debug("nuovoNomeFile " + nuovoNomeFile);
//
//						try {
//							File nuovoFile = new File(file.getParent() + "/" + nuovoNomeFile);
//							log.debug("nuovoFile " + nuovoFile);
//
//							try {
//								FileUtils.copyFile(fileSbustato, nuovoFile);
//							} catch (IOException e) {
//								log.error("Errore nella copia file: " + e.getMessage());
//							}
//							// Files.copy(fileSbustato.toPath(), nuovoFile.toPath());
//
//							file = nuovoFile;
//						} catch (Exception e) {
//							log.error("Eccezione setOperationResult", e);
//						}
//					}
//
//					String estensioneFile = FilenameUtils.getExtension(file.getName());
//					log.debug("estensioneFile " + estensioneFile);
//					if (estensioneFile != null && !estensioneFile.equalsIgnoreCase(""))
//						mimeType = formatRecognitionCtrl.getMimeTypeExt(file, estensioneFile);
//					else
//						mimeType = formatRecognitionCtrl.getBestMimeType(file);
//					// mimeType = formatRecognitionCtrl.getBestMimeType(file);
//					log.debug("mimeType " + mimeType);
//					formato = dao.findFormatByMimeType(mimeType.getBaseType());
//					log.debug("formato " + formato);
//					if (formato != null)
//						mime = formato.getMimetypePrincipale();
//					log.debug("Mime del file estratto " + mime);
//				} catch (MimeDetectorException e) {
//					log.error("Eccezione setOperationResult", e);
//				} catch (Exception e) {
//					log.error("Eccezione setOperationResult", e);
//				}
//			}
//
//			if (isFolder || (!isFolder && !path.contains(File.separator))) {
//				InfoFile infoFile = new InfoFile();
//				infoFile.setFile(file);
//				infoFile.setNome(file.getName());
//				infoFile.setPath(file.getParent());
//				infoFile.setMimeType(mime);
//				// if (error != null)
//				// infoFile.setError(error);
//
//				files.add(infoFile);
//			}
//		}
//
//		@Override
//		public void setCompleted(long completeValue) throws SevenZipException {
//		}
//
//		@Override
//		public void setTotal(long total) throws SevenZipException {
//		}
//	}

	private void elaboraDirectory(File[] filesInDir, String tempDir, List<InfoFile> files, String requestKey) {
		log.debug("Gestisco il caso della directory");

		for (File fileInDir : filesInDir) {
			File newFile = new File(tempDir + File.separator + fileInDir.getName());
			new File(newFile.getParent()).mkdirs();
			if (fileInDir.isFile()) {
				try {
					FileUtils.copyFile(fileInDir, newFile);
				} catch (IOException e) {
					log.error("Eccezione elaboraDirectory", e);
				}
			} else if (fileInDir.isDirectory())
				newFile.mkdir();

			InfoFile infoFile = new InfoFile();
			log.debug("Aggiungo il file " + fileInDir.getName());
			infoFile.setFile(fileInDir);
			infoFile.setNome(fileInDir.getName());
			infoFile.setPath(fileInDir.getParent());
			if (fileInDir.isFile()) {
				MimeType mimeType;
				try {
					MimeUtilsAdapter adapter = new MimeUtilsAdapter();
					String estensioneFile = FilenameUtils.getExtension(fileInDir.getName());
					log.debug("estensioneFile " + estensioneFile);
					if (estensioneFile != null && !estensioneFile.equalsIgnoreCase(""))
						mimeType = formatRecognitionCtrl.getMimeTypeExt(fileInDir, estensioneFile, requestKey, adapter);
					else
						mimeType = formatRecognitionCtrl.getBestMimeType(fileInDir, adapter);
					// mimeType = formatRecognitionCtrl.getBestMimeType(fileInDir);
					TAnagFormatiDig formato = dao.findFormatByMimeType(mimeType.getBaseType());
					if (formato != null && formato.getMimetypePrincipale() != null) {
						log.debug("Mime del file " + formato.getMimetypePrincipale());
						infoFile.setMimeType(formato.getMimetypePrincipale());
					}
				} catch (MimeDetectorException e) {
					log.error("Eccezione elaboraDirectory", e);
				} catch (Exception e) {
					log.error("Eccezione elaboraDirectory", e);
				}
			} else {
				infoFile.setMimeType("");
			}

			files.add(infoFile);
		}
	}

	private void elaboraZip(File file, String mime, String tempDir, int level, List<InfoFile> files, Boolean abilitaSbustamento, String requestKey) throws FOException {
		log.debug("Gestisco il caso dell'archivio ZIP");
		ZipInputStream zis = null;
		ZipEntry ze = null;
		String error = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			zis = new ZipInputStream(fis);
			ze = zis.getNextEntry();
		} catch (FileNotFoundException e) {
			log.error("Errore ", e);
		} catch (IOException e) {
			log.error("Errore ", e);
			error = "Errore nella lettura del file";

			// distinguo il caso dell'archivio principale da quello degli
			// archivi interni
			if (level == 1)
				throw new FOException(FileOpMessage.UNPACKMULTIPART_OP_ERROR, e);

			InfoFile infoFile = new InfoFile();
			if (error != null)
				infoFile.setError(error);
			files.add(infoFile);
			try {
				zis.closeEntry();
				ze = zis.getNextEntry();
			} catch (IOException e1) {
				log.error("Errore ", e1);
			} catch (Exception e1) {
				log.error("Errore ", e1);
			}
		} catch (Exception e) {
			log.error("Errore ", e);
			error = "Errore nella lettura del file";
			InfoFile infoFile = new InfoFile();
			infoFile.setFile(file);
			infoFile.setNome(file.getName());
			infoFile.setPath("");
			infoFile.setMimeType(mime);
			if (error != null)
				infoFile.setError(error);

			files.add(infoFile);
			try {
				zis.closeEntry();
				ze = zis.getNextEntry();
			} catch (IOException e1) {
				log.error("Errore ", e1);
			} catch (Exception e1) {
				log.error("Errore ", e1);
			}
		}

		byte[] buffer = new byte[1024];
		mime = "";
		String path = "";
		boolean isDir = false;
		while (ze != null) {
			String fileName = ze.getName();
			log.debug("fileName file estratto " + fileName);
			File newFile = new File(tempDir + File.separator + fileName);
			log.debug("file estratto " + newFile.getAbsolutePath());
			new File(newFile.getParent()).mkdirs();

			if (ze.isDirectory()) {
				isDir = true;
				newFile.mkdirs();
				mime = "";
				path = newFile.getPath();
				log.debug("directory interna all'archivio: " + path);
			} else {
				isDir = false;
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(newFile);
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();

				} catch (FileNotFoundException e) {
					log.error("Errore ", e);
				} catch (IOException e) {
					log.error("Errore ", e);
					error = e.getMessage();
				} catch (Exception e) {
					log.error("Errore ", e);
					error = e.getMessage();
				}
				// log.debug("ERROR " + error);

				try {

					if (abilitaSbustamento == null || abilitaSbustamento == true) {
						String fileNameOriginale = newFile.getName();
						log.debug("fileNameOriginale " + fileNameOriginale);
						SbustamentoBean sbustamentoFileBean = unpackCtrl.extractDocumentFile(newFile, new File(tempDir), fileNameOriginale, true, requestKey, null );
						File fileSbustato = sbustamentoFileBean.getExtracttempfile();
						log.debug("fileSbustato " + fileSbustato);
						//String nuovoNomeFile = unpackCtrl.rename(fileSbustato, fileNameOriginale, unpackCtrl.getFormatoBusta(), timestamp);
						String nuovoNomeFile = unpackCtrl.rename(fileSbustato, fileNameOriginale, sbustamentoFileBean.getFormatoBusta(), requestKey, sbustamentoFileBean);
						log.debug("nuovoNomeFile " + nuovoNomeFile);

						File nuovoFile = new File(fileSbustato.getParent() + "/" + nuovoNomeFile);
						log.debug("nuovoFile " + nuovoFile);

						try {
							FileUtils.copyFile(fileSbustato, nuovoFile);
						} catch (IOException e) {
							log.error("Errore nella copia file: " + e.getMessage());
						}
						// Files.copy(fileSbustato.toPath(), nuovoFile.toPath());

						newFile = nuovoFile;
					}

					path = newFile.getParent();
					fileName = newFile.getName();

					MimeUtilsAdapter adapter = new MimeUtilsAdapter();
					String estensioneFile = FilenameUtils.getExtension(newFile.getName());
					log.debug("estensioneFile " + estensioneFile);
					MimeType mimeType = null;
					if (estensioneFile != null && !estensioneFile.equalsIgnoreCase("")) {
						mimeType = formatRecognitionCtrl.getMimeTypeExt(newFile, estensioneFile, requestKey, adapter);
						if (mimeType.getBaseType() == null || mimeType.getBaseType().equalsIgnoreCase("application/unknown")) {
							mimeType = formatRecognitionCtrl.getBestMimeType(newFile, adapter);
						}
					} else
						mimeType = formatRecognitionCtrl.getBestMimeType(newFile, adapter);
					// MimeType mimeType = formatRecognitionCtrl.getBestMimeType(newFile);
					TAnagFormatiDig formato = dao.findFormatByMimeType(mimeType.getBaseType());
					if (formato != null)
						mime = formato.getMimetypePrincipale();
					else {
						log.debug("Imposto il mime undefined");
						mime = "undefined";
					}
					log.debug("Mime del file " + mime);
				} catch (MimeDetectorException e) {
					log.error("Eccezione elaboraZip", e);
				} catch (Exception e) {
					log.error("Eccezione elaboraZip", e);
				} catch (Throwable e) {
					log.error("Eccezione elaboraZip", e);
				}
			}

			if (isDir || (!isDir && !fileName.contains(File.separator))) {
				InfoFile infoFile = new InfoFile();
				infoFile.setFile(newFile);
				infoFile.setNome(fileName);
				infoFile.setPath(path);
				infoFile.setMimeType(mime);
				infoFile.setDirectory(isDir);
				if (error != null)
					infoFile.setError(error);

				files.add(infoFile);
			}

			if (error != null)
				break;

			try {
				zis.closeEntry();
				ze = zis.getNextEntry();
			} catch (IOException e) {
				log.error("Errore ", e);
				error = e.getMessage();
			}
		}
		try {
			zis.close();

			if (fis != null)
				IOUtils.closeQuietly(fis);
		} catch (IOException e) {
			log.error("Errore ", e);
			error = e.getMessage();
		}
	}

	private void elaboraRar(File file, String mime, String tempDir, int level, List<InfoFile> files, Boolean abilitaSbustamento, String requestKey) throws FOException {
		log.info("Gestisco il caso dell'archivio RAR - file rar: " + file);
		Archive archivio = null;
		String error = null;
		try {
			archivio = new Archive(file);
		} catch (RarException e) {
			log.error("Errore ", e);
		} catch (IOException e) {
			log.error("Errore ", e);
			error = "Errore nella lettura del file";

			// distinguo il caso dell'archivio principale da quello degli
			// archivi interni
			if (level == 1)
				throw new FOException(FileOpMessage.UNPACKMULTIPART_OP_ERROR, e);

			InfoFile infoFile = new InfoFile();
			if (error != null)
				infoFile.setError(error);
			files.add(infoFile);
		} catch (Exception e) {
			log.error("Errore ", e);
			error = "Errore nella lettura del file";
			InfoFile infoFile = new InfoFile();
			infoFile.setFile(file);
			infoFile.setNome(file.getName());
			infoFile.setPath("");
			infoFile.setMimeType(mime);
			if (error != null)
				infoFile.setError(error);

			files.add(infoFile);
		}

		mime = "";
		String path = "";
		boolean isDir = false;
		List<FileHeader> fileHeaders = archivio.getFileHeaders();
		for (FileHeader fh : fileHeaders) {
			String fileNameArchivio;

			if (fh.isFileHeader() && fh.isUnicode()) {
				fileNameArchivio = fh.getFileNameW();
			} else {
				fileNameArchivio = fh.getFileNameString();
			}

			if (fileNameArchivio.contains("\\"))
				fileNameArchivio = fileNameArchivio.replace("\\", "/");
			log.debug("fileName file estratto dall'archivio " + fileNameArchivio);
			File fileArchivio = new File(tempDir + File.separator + fileNameArchivio);
			log.debug("file estratto dall'archivio " + fileArchivio.getAbsolutePath());
			new File(fileArchivio.getParent()).mkdirs();

			if (fh.isDirectory()) {
				isDir = true;
				fileArchivio.mkdirs();
				mime = "";
				path = fileArchivio.getPath();
				log.debug("directory interna all'archivio: " + path);
			} else {
				isDir = false;
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(fileArchivio);
					archivio.extractFile(fh, fos);
				} catch (RarException e) {
					if (e.getType().equals(RarExceptionType.notImplementedYet)) {
						log.error("error extracting unsupported file: " + fh.getFileNameString());
					}
					log.error("error extracting file: " + fh.getFileNameString());
				} catch (Exception e) {
					log.error("Errore elaboraRar", e);
					error = e.getMessage();
				} finally {
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							log.error("Errore elaboraRar", e);
						}
					}
				}
				// log.debug("ERROR " + error);

				path = fileArchivio.getParent();
				try {
					if (abilitaSbustamento == null || abilitaSbustamento == true) {
						unpackCtrl.reset();
						//File fileArchivioSbustato = unpackCtrl.extractDocumentFile(fileArchivio, new File(tempDir), fileNameArchivio, true, timestamp);
						SbustamentoBean sbustamentobean = unpackCtrl.extractDocumentFile(fileArchivio, new File(tempDir), fileNameArchivio, true, requestKey, null);
						File fileArchivioSbustato = sbustamentobean.getExtracttempfile();
						log.debug("file archivio sbustato " + fileArchivioSbustato);
						//String nuovoNomeFile = unpackCtrl.getNomeFileSbustato();
						String nuovoNomeFile = sbustamentobean.getNomeFileSbustato();
						log.debug("nuovoNomeFile " + nuovoNomeFile);

						File fileArchivio1 = new File(file.getParent() + "/" + nuovoNomeFile);
						log.debug("file archivio (eventualmente sbustato) " + fileArchivio1);

						try {
							FileUtils.copyFile(fileArchivioSbustato, fileArchivio1);
						} catch (IOException e) {
							log.warn("Errore nella copia file: " + e.getMessage());
						}
						// Files.copy(fileSbustato.toPath(), nuovoFile.toPath());

						fileArchivio = fileArchivio1;
					}

					path = fileArchivio.getParent();
					fileNameArchivio = fileArchivio.getName();

					MimeUtilsAdapter adapter = new MimeUtilsAdapter();
					String estensioneFile = FilenameUtils.getExtension(fileNameArchivio);
					log.debug("estensioneFile " + estensioneFile);
					MimeType mimeType = null;
					if (estensioneFile != null && !estensioneFile.equalsIgnoreCase(""))
						mimeType = formatRecognitionCtrl.getMimeTypeExt(fileArchivio, estensioneFile, requestKey, adapter);
					else
						mimeType = formatRecognitionCtrl.getBestMimeType(fileArchivio, adapter);
					TAnagFormatiDig formato = dao.findFormatByMimeType(mimeType.getBaseType());
					if (formato != null)
						mime = formato.getMimetypePrincipale();
					else
						mime = "undefined";
					log.debug("Mime del file " + mime);
				} catch (MimeDetectorException e) {
					log.error("Errore elaboraRar", e);
				} catch (Exception e) {
					log.error("Errore elaboraRar", e);
				}

			}

			if (isDir || (!isDir && !fileNameArchivio.contains("\\") && !fileNameArchivio.contains("/"))) {
				InfoFile infoFile = new InfoFile();
				infoFile.setFile(fileArchivio);
				infoFile.setNome(fileNameArchivio);
				infoFile.setPath(path);
				infoFile.setMimeType(mime);
				infoFile.setDirectory(isDir);
				if (error != null)
					infoFile.setError(error);

				files.add(infoFile);
			}

			if (error != null)
				break;

		}

		if (archivio != null) {
			try {
				archivio.close();
			} catch (IOException e) {
				log.error("Errore elaboraRar", e);
			}
		}
	}

	private void elaboraTar(File file, String mime, String tempDir, int level, List<InfoFile> files, Boolean abilitaSbustamento, String requestKey) throws FOException {
		log.debug("Gestisco il caso dell'archivio TAR");
		GZIPInputStream gzipIS = null;
		TarArchiveInputStream tarIS = null;
		String error = null;

		try {
			gzipIS = new GZIPInputStream(new FileInputStream(file));
			tarIS = new TarArchiveInputStream(gzipIS);
			TarArchiveEntry gze = null;

			mime = "";
			String path = "";
			boolean isDir = false;
			while ((gze = tarIS.getNextTarEntry()) != null) {
				String fileName = gze.getName();
				log.info("fileName file estratto " + fileName);
				File newFile = new File(tempDir + File.separator + fileName);
				log.info("file estratto " + newFile);
				new File(newFile.getParent()).mkdirs();

				if (gze.isDirectory()) {
					isDir = true;
					newFile.mkdirs();
					mime = "";
					path = newFile.getPath();
					log.debug("directory interna all'archivio: " + path);
				} else {
					isDir = false;
					try {
						final OutputStream outputFileStream = new FileOutputStream(newFile);
						IOUtils.copy(tarIS, outputFileStream);
						outputFileStream.close();
					} catch (FileNotFoundException e) {
						log.error("Errore elaboraTar", e);
					} catch (IOException e) {
						log.error("Errore elaboraTar", e);
						error = e.getMessage();
					} catch (Exception e) {
						log.error("Errore elaboraTar", e);
						error = e.getMessage();
					}

					path = newFile.getParent();
					try {
						if (abilitaSbustamento == null || abilitaSbustamento == true) {
							String fileNameOriginale = newFile.getName();
							log.debug("fileNameOriginale " + fileNameOriginale);
							//File fileSbustato = unpackCtrl.extractDocumentFile(newFile, new File(tempDir), fileNameOriginale, true, requestKey);
							SbustamentoBean sbustamentoBean = unpackCtrl.extractDocumentFile(newFile, new File(tempDir), fileNameOriginale, true, requestKey, null);
							File fileSbustato = sbustamentoBean.getExtracttempfile();
							log.debug("fileSbustato " + fileSbustato);
							//String nuovoNomeFile = unpackCtrl.rename(fileSbustato, fileNameOriginale, unpackCtrl.getFormatoBusta(), timestamp);
							String nuovoNomeFile = unpackCtrl.rename(fileSbustato, fileNameOriginale, sbustamentoBean.getFormatoBusta(), requestKey, sbustamentoBean);
							log.debug("nuovoNomeFile " + nuovoNomeFile);

							File nuovoFile = new File(file.getParent() + "/" + nuovoNomeFile);
							log.debug("nuovoFile " + nuovoFile);

							try {
								FileUtils.copyFile(fileSbustato, nuovoFile);
							} catch (IOException e) {
								log.error("Errore nella copia file: " + e.getMessage());
							}
							// Files.copy(fileSbustato.toPath(), nuovoFile.toPath());

							newFile = nuovoFile;
						}

						path = newFile.getParent();
						fileName = newFile.getName();

						String estensioneFile = FilenameUtils.getExtension(newFile.getName());
						log.debug("estensioneFile " + estensioneFile);
						MimeUtilsAdapter adapter = new MimeUtilsAdapter();
						MimeType mimeType = null;
						if (estensioneFile != null && !estensioneFile.equalsIgnoreCase(""))
							mimeType = formatRecognitionCtrl.getMimeTypeExt(newFile, estensioneFile, requestKey, adapter);
						else
							mimeType = formatRecognitionCtrl.getBestMimeType(newFile, adapter);
						// MimeType mimeType = formatRecognitionCtrl.getBestMimeType(newFile);
						TAnagFormatiDig formato = dao.findFormatByMimeType(mimeType.getBaseType());
						if (formato != null)
							mime = formato.getMimetypePrincipale();
						else
							mime = "undefined";
						log.debug("Mime del file " + mime);
					} catch (MimeDetectorException e) {
						log.error("Errore elaboraTar", e);
					} catch (Exception e) {
						log.error("Errore elaboraTar", e);
					}

				}

				if (isDir || (!isDir && !fileName.contains(File.separator))) {
					InfoFile infoFile = new InfoFile();
					infoFile.setFile(newFile);
					infoFile.setNome(fileName);
					infoFile.setPath(path);
					infoFile.setMimeType(mime);
					infoFile.setDirectory(isDir);
					if (error != null)
						infoFile.setError(error);

					files.add(infoFile);
				}

				log.debug("error " + error);
				if (error != null)
					break;
			}

		} catch (FileNotFoundException e) {
			log.error("Errore ", e);
			error = "Errore nella lettura del file";
			InfoFile infoFile = new InfoFile();
			infoFile.setFile(file);
			infoFile.setNome(file.getName());
			infoFile.setPath("");
			infoFile.setMimeType(mime);
			if (error != null)
				infoFile.setError(error);

			files.add(infoFile);
		} catch (IOException e) {
			log.error("Errore ", e);
			error = "Errore nella lettura del file";
			InfoFile infoFile = new InfoFile();
			infoFile.setFile(file);
			infoFile.setNome(file.getName());
			infoFile.setPath("");
			infoFile.setMimeType(mime);
			if (error != null)
				infoFile.setError(error);

			files.add(infoFile);
		}

		try {
			tarIS.close();
			gzipIS.close();
		} catch (IOException e) {
			log.error("Errore ", e);
			// error = e.getMessage();
		}
	}
	//

	private void elabora7Zip(File file, String mime, String tempDir, int level, List<InfoFile> files, Boolean abilitaSbustamento, 
			String requestKey) throws FOException {
		log.debug("Gestisco il caso dell'archivio 7Zip");
		SevenZFile inArchive = null;
		try {
			
			inArchive = new SevenZFile (file);
			 
			//File outputDirectory = File.createTempFile("outputDirectory", "", new File(tempDir));
			//outputDirectory.delete();
			//outputDirectory.mkdir();
			
			SevenZArchiveEntry entry;
			TAnagFormatiDig formato = null;
			String path = "";
			boolean isDir = false;
			String fileName = null;
			while ((entry = inArchive.getNextEntry()) != null){
				fileName = entry.getName();
				File currentfile = new File(tempDir, fileName);
				log.debug("File estratto " + entry.getName());
				
				if (entry.isDirectory()){
					log.debug("Dir interna all'archivio ");
					mime = "";
					currentfile.mkdirs();
					path = currentfile.getPath();
					isDir = true;
					
				} else {
					isDir = false;
					
					FileOutputStream out = new FileOutputStream(currentfile);
					byte[] content = new byte[(int) entry.getSize()];
					inArchive.read(content, 0, content.length);
					out.write(content);
					out.close();
					
					if (abilitaSbustamento == null || abilitaSbustamento == true) {
						String fileNameOriginale = currentfile.getName();
						log.debug("fileNameOriginale " + fileNameOriginale);
						//File fileSbustato = unpackCtrl.extractDocumentFile(currentfile, new File(tempDir), fileNameOriginale, true, timestamp);
						SbustamentoBean sbustamentoBean = unpackCtrl.extractDocumentFile(currentfile, new File(tempDir), fileNameOriginale, true, requestKey, null);
						File fileSbustato = sbustamentoBean.getExtracttempfile();
						log.debug("fileSbustato " + fileSbustato);
						//String nuovoNomeFile = unpackCtrl.rename(fileSbustato, fileNameOriginale, unpackCtrl.getFormatoBusta(), timestamp  );
						String nuovoNomeFile = unpackCtrl.rename(fileSbustato, fileNameOriginale, sbustamentoBean.getFormatoBusta(), requestKey, sbustamentoBean );
						log.debug("nuovoNomeFile " + nuovoNomeFile);

						File nuovoFile = new File(fileSbustato.getParent() + "/" + nuovoNomeFile);
						log.debug("nuovoFile " + nuovoFile);

						try {
							FileUtils.copyFile(fileSbustato, nuovoFile);
						} catch (IOException e) {
							log.error("Errore nella copia file: " + e.getMessage());
						}
						// Files.copy(fileSbustato.toPath(), nuovoFile.toPath());

						currentfile = nuovoFile;
					}
					
					path = currentfile.getParent();
					fileName = currentfile.getName();

					String estensioneFile = FilenameUtils.getExtension(currentfile.getName());
					log.debug("estensioneFile " + estensioneFile);
					MimeType mimeType = null;
					MimeUtilsAdapter adapter = new MimeUtilsAdapter();
					if (estensioneFile != null && !estensioneFile.equalsIgnoreCase("")) {
						mimeType = formatRecognitionCtrl.getMimeTypeExt(currentfile, estensioneFile, requestKey, adapter);
						if (mimeType.getBaseType() == null || mimeType.getBaseType().equalsIgnoreCase("application/unknown")) {
							mimeType = formatRecognitionCtrl.getBestMimeType(currentfile, adapter);
						}
					} else
						mimeType = formatRecognitionCtrl.getBestMimeType(currentfile, adapter);
					// MimeType mimeType = formatRecognitionCtrl.getBestMimeType(newFile);
					formato = dao.findFormatByMimeType(mimeType.getBaseType());
					if (formato != null)
						mime = formato.getMimetypePrincipale();
					else {
						log.debug("Imposto il mime undefined");
						mime = "undefined";
					}
					log.debug("Mime del file " + mime);
					
				}
				
				if (isDir || (!isDir && !fileName.contains(File.separator))) {
					InfoFile infoFile = new InfoFile();
					infoFile.setFile(currentfile);
					infoFile.setNome(fileName);
					infoFile.setPath(path);
					infoFile.setMimeType(mime);
					infoFile.setDirectory(isDir);
					//if (error != null)
					//	infoFile.setError(error);

					files.add(infoFile);
				}

				//if (error != null)
				//	break;
				
			}
				
			
//			int[] items = new int[inArchive.getNumberOfItems()];
//			for (int i = 0; i < inArchive.getNumberOfItems(); i++) {
//				items[i] = (Integer.valueOf(i));
//			}

			

			//inArchive.extract(items, false,
			//		new MyExtractCallback(inArchive, outputDirectory, formatRecognitionCtrl, unpackCtrl, dao, files, abilitaSbustamento));
		} catch (FileNotFoundException e) {
			log.error("Eccezione elabora7Zip", e);
		} /*catch (SevenZipException e) {
			log.error("Eccezione elabora7Zip", e);
		} */catch (Exception e) {
			log.error("Eccezione elabora7Zip", e);
		} finally {
			if (inArchive != null) {
				try {
					inArchive.close();
				} catch (/*SevenZipException*/IOException e) {
					log.error("Error closing archive: " + e);
				}
			}
			/*if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
					log.error("Error closing file: " + e);
				}
			}*/
		}
	}

	// public static void main(String[] args) {
	// ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	// try {
	// ConfigUtil.initialize();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// Security.addProvider(new BouncyCastleProvider());
	// // configuro OpenOffice
	// OpenOfficeConfiguration config = (OpenOfficeConfiguration) FileoperationContextProvider.getApplicationContext().getBean("oomanager");
	// try {
	// OpenOfficeConverter.configure(config);
	// } catch (OpenOfficeException e) {
	// e.printStackTrace();
	// }
	//
	// FileOperation inputFileOp = new FileOperation();
	//
	// InputUnpackMultipartType customInput = new InputUnpackMultipartType();
	// customInput.setRecursive(true);
	//
	// InputFileOperationType inputFileOpType = new InputFileOperationType();
	// InputFile inputFile = new InputFile();
	// DataHandler fileStream = new DataHandler(new FileDataSource(new File("/root/Scrivania/protocolloEmergenza.7z")));
	// inputFile.setFileStream(fileStream);
	// inputFileOpType.setInputType(inputFile);
	// inputFileOp.setFileOperationInput(inputFileOpType);
	// Operations operations = new Operations();
	// operations.getOperation().add(customInput);
	// inputFileOp.setOperations(operations);
	//
	// OutputOperations output = new OutputOperations();
	// FOImpl foImpl = new FOImpl();
	// Response response = foImpl.execute(inputFileOp);
	//
	// List<AbstractResponseOperationType> listFOResults = response.getFileoperationResponse().getFileOperationResults().getFileOperationResult();
	//
	// for (AbstractResponseOperationType foResult : listFOResults) {
	// if (foResult instanceof ResponseUnpackMultipartType) {
	// ResponseUnpackMultipartType unpackMultipartResponse = (ResponseUnpackMultipartType) foResult;
	// List<MultipartContentType> multipartContentList = unpackMultipartResponse.getMultipartContent();
	// showFileProperty(multipartContentList);
	//
	// }
	// }
	//
	// }

	// public static void main(String[] args) {
	// String file = "/root/Scrivania/test.zip";
	// try {
	// ZipInputStream zis = new ZipInputStream( new FileInputStream( file ) );
	// ZipEntry ze = zis.getNextEntry();
	//
	// while (ze != null) {
	// String fileName = ze.getName();
	// log.debug("fileName file estratto " + fileName);
	//
	// if (ze.isDirectory()) {
	// System.out.println("DIR");
	// } else {
	// System.out.println("FILE");
	// }
	//
	// String[] fileNames = fileName.split( File.separator );
	// log.debug("fileNames.length " + fileNames.length);
	// if (fileNames.length == 1 || fileNames.length == 0) {
	// System.out.println("NELL'IF");
	// }
	//
	// try {
	// zis.closeEntry();
	// ze = zis.getNextEntry();
	// } catch (IOException e) {
	// log.error("Errore ", e);
	//
	// }
	// }
	//
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }

	// String file = "/root/Scrivania/test.rar";
	// try {
	// Archive arch = new Archive( new File(file) );
	//
	// List<FileHeader> fileHeaders = arch.getFileHeaders();
	// for (FileHeader fh : fileHeaders) {
	// String fileName;
	// log.debug("fh.getFileNameW() " + fh.getFileNameW() );
	// log.debug("fh.getFileNameString() " + fh.getFileNameString() );
	// log.debug("fh.isUnicode() " + fh.isUnicode() );
	// if (fh.isFileHeader() && fh.isUnicode()) {
	// fileName = fh.getFileNameW();
	// } else {
	// fileName = fh.getFileNameString();
	// }
	// log.debug("fileName file estratto " + fileName );
	//
	// if (fh.isDirectory()) {
	// System.out.println("DIR");
	// } else {
	// System.out.println("FILE");
	// }
	//
	// String[] fileNames = fileName.split( "/");
	// log.debug("fileNames.length " + fileNames.length);
	// if (fileNames.length == 1 || fileNames.length == 0) {
	// System.out.println("NELL'IF");
	// }
	// }
	// } catch (RarException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }

	// }

	public static Integer countLevel = 1;

	private static void showFileProperty(List<MultipartContentType> multipartContentList) {
		if (multipartContentList != null)
			for (MultipartContentType multipartContent : multipartContentList) {
				String prefix = "";
				for (int i = 1; i < countLevel; i++) {
					prefix += "    ";
				}
				log.debug(prefix + "Livello " + countLevel);
				log.debug(prefix + "Nome " + multipartContent.getNome());
				log.debug(prefix + "Path " + multipartContent.getPath());
				log.debug(prefix + "MimeType " + multipartContent.getMimeType());
				if (multipartContent.getMultipartContent().size() > 0) {
					countLevel++;
					showFileProperty(multipartContent.getMultipartContent());
					countLevel--;
				}
			}
	}

	public static void main(String[] args) throws FOException {
		// UnpackMultipartCtrl ctr = new UnpackMultipartCtrl();
		// ctr.formatRecognitionCtrl = new FormatRecognitionCtrl();
		// File file = new File("C:/Users/Anna Tesauro/Desktop/test - Copia.rar");
		//
		// String mime = "";
		// int level = 1;
		// String tempDir = null;
		// List<InfoFile> files = new ArrayList<InfoFile>();
		// ctr.elaboraRar(file, mime, tempDir, level, files , false);
		//
		// boolean esitoCancellazione = file.delete();
		// System.out.println("esito cancellazione " + esitoCancellazione);

		// File file = new File("C:/Users/Anna Tesauro/Desktop/20160628122905743208212901/20160628122905743208212901.eml");

	}

}