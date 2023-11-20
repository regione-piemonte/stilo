/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.persistence.Entity;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

import it.eng.cryptoutil.verify.util.ResponseUtils;
import it.eng.fileOperation.clientws.AbstractResponseOperationType;
import it.eng.fileOperation.clientws.FileOperationRequest;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.FileOperationWS;
import it.eng.fileOperation.clientws.InputFile;
import it.eng.fileOperation.clientws.InputFileOperationType;
import it.eng.fileOperation.clientws.InputFormatRecognitionType;
import it.eng.fileOperation.clientws.InputUnpackMultipartType;
import it.eng.fileOperation.clientws.InputUnpackType;
import it.eng.fileOperation.clientws.MultipartContentType;
import it.eng.fileOperation.clientws.Operations;
import it.eng.fileOperation.clientws.ResponseFormatRecognitionType;
import it.eng.fileOperation.clientws.ResponseUnpackMultipartType;
import it.eng.gd.lucenemanager.bean.FileVO;
import it.eng.gd.lucenemanager.dao.DaoTMimeTypeFmtDig;
import it.eng.gd.lucenemanager.document.AbstractDocumentReader;
import it.eng.gd.lucenemanager.document.reader.OcrDocumentReader;
import it.eng.gd.lucenemanager.document.reader.PdfDocumentReader;
import it.eng.gd.lucenemanager.exception.LuceneManagerException;
import it.eng.gd.lucenemanager.manager.entity.TMimetypeFmtDig;
import net.sf.sevenzipjbinding.ExtractAskMode;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IArchiveExtractCallback;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.ISevenZipInArchive;
import net.sf.sevenzipjbinding.PropID;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;

/**
 * Classe di utilita' per i Job
 * 
 * @author Ubaldo
 *
 */

@Entity
public class JobUtils {

	private static final Log log = LogFactory.getLog(JobUtils.class.getName());
	private static final String ZIP_TYPE = "zip";
	// private static final String XML_TYPE = "xml";
	private static final String GZ_TYPE = "gz";
	private static final String P7M_TYPE = "p7m";

	private File tmpextract = null;

	private String wsEndpoint = null;

	private DaoTMimeTypeFmtDig daoMimeType;

	public static String getTimeInMillisAsString() {
		Calendar calend = Calendar.getInstance();
		String tempName = calend.getTimeInMillis() + "";
		return tempName;
	}

	// ZIP
	public static List<String> unzipFile(File zippedFile, String directory, boolean switchVersion) throws IOException {
		ZipInputStream zip = null;
		FileInputStream fis = null;
		List<String> fileNames = new ArrayList<String>();
		try {
			fis = new FileInputStream(zippedFile);
			zip = new ZipInputStream(fis);
			ZipEntry entry;
			while ((entry = zip.getNextEntry()) != null) {
				fileNames.add(entry.getName());
				if (!entry.isDirectory()) {
					File file = new File(directory, entry.getName());
					if (switchVersion) {
						if (file.exists()) {
							FileUtils.forceDelete(file);
						}
					}
					if (!file.exists()) {
						IOUtils.copy(zip, new FileOutputStream(file));
					}
				} else {
					File dir = new File(directory, entry.getName());
					if (switchVersion) {
						if (dir.exists()) {
							FileUtils.forceDelete(dir);
						}
					}
					if (!dir.exists()) {
						dir.mkdirs();
					}
				}
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (Exception e) {
				}
			if (zip != null)
				try {
					zip.close();
				} catch (Exception e) {
				}
		}

		return fileNames;
	}

	public static File unzipFile(File zippedFile, String fileName) throws IOException {
		FileInputStream fis = null;
		ZipInputStream zip = null;
		try {
			fis = new FileInputStream(zippedFile);
			zip = new ZipInputStream(fis);
			ZipEntry entry;
			while ((entry = zip.getNextEntry()) != null) {
				if (!entry.isDirectory() && entry.getName().equalsIgnoreCase(fileName)) {
					File file = File.createTempFile("Extract", "");
					new File(file.getParent()).mkdirs();
					IOUtils.copy(zip, new FileOutputStream(file));
					return file;
				}
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (Exception e) {
				}
			if (zip != null)
				try {
					zip.close();
				} catch (Exception e) {
				}
		}

		return null;
	}

	// TAR.GZ
	public static List<String> untarFile(String directoryPath, File compressedFile) throws IOException {
		File destFolder = new File(directoryPath);
		List<String> fileNames = new ArrayList<String>();
		GZIPInputStream gzipIS = new GZIPInputStream(new FileInputStream(compressedFile));
		TarArchiveInputStream tarIS = new TarArchiveInputStream(gzipIS);
		TarArchiveEntry entry = null;
		while ((entry = tarIS.getNextTarEntry()) != null) {
			String entryName = entry.getName();
			fileNames.add(entryName);
			File newFile = new File(destFolder, entryName);
			if (entry.isDirectory()) {
				newFile.mkdirs();
			} else {
				File entryFolder = newFile.getParentFile();
				if (!entryFolder.exists())
					entryFolder.mkdirs();
				OutputStream os = new FileOutputStream(newFile);
				try {
					IOUtils.copy(tarIS, os);
				} finally {
					if (os != null) {
						os.flush();
						os.close();
					}
				}
			}
		}

		return fileNames;
	}

	public static File untarFile(File compressedFile, String fileName) {
		GZIPInputStream gzipIS = null;
		TarArchiveInputStream tarIS = null;
		try {
			gzipIS = new GZIPInputStream(new FileInputStream(compressedFile));
			tarIS = new TarArchiveInputStream(gzipIS);
			TarArchiveEntry entry = null;
			while ((entry = tarIS.getNextTarEntry()) != null) {
				if (!entry.isDirectory() && entry.getName().equalsIgnoreCase(fileName)) {
					File file = File.createTempFile("Extract", "");
					new File(file.getParent()).mkdirs();
					IOUtils.copy(tarIS, new FileOutputStream(file));
					return file;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (tarIS != null)
				try {
					tarIS.close();
				} catch (Exception e) {
				}
			if (gzipIS != null)
				try {
					gzipIS.close();
				} catch (Exception e) {
				}
		}

		return null;
	}

	public static File un7ZipFile(File compressedFile, String fileName) {
		ISevenZipInArchive inArchive = null;
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(compressedFile, "r");
			inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));
			int[] items = filterIds(inArchive, fileName);

			File returnedFile = null;
			inArchive.extract(items, false, new MyExtractCallback(inArchive, returnedFile));

			return returnedFile;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SevenZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inArchive != null) {
				try {
					inArchive.close();
				} catch (SevenZipException e) {
					System.err.println("Error closing archive: " + e);
				}
			}
			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
					System.err.println("Error closing file: " + e);
				}
			}
		}

		return null;
	}

	private static int[] filterIds(ISevenZipInArchive inArchive, String nomeFile) throws SevenZipException {
		List<Integer> idList = new ArrayList<Integer>();
		int numberOfItems = inArchive.getNumberOfItems();
		for (int i = 0; i < numberOfItems; i++) {
			String path = (String) inArchive.getProperty(i, PropID.PATH);
			String fileName = new File(path).getName();
			if (nomeFile.equalsIgnoreCase(fileName)) {
				idList.add(i);
			}
		}
		int[] result = new int[idList.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = idList.get(i);
		}

		return result;
	}

	public static class MyExtractCallback implements IArchiveExtractCallback {

		private ISevenZipInArchive inArchive;
		private int index;
		private OutputStream outputStream;
		private boolean isFolder;
		private File extractedFile;

		public MyExtractCallback(ISevenZipInArchive inArchive, File extractedFile) {
			this.inArchive = inArchive;
			this.extractedFile = extractedFile;
		}

		public ISequentialOutStream getStream(final int index, ExtractAskMode extractAskMode) throws SevenZipException {
			closeOutputStream();

			this.index = index;
			this.isFolder = (Boolean) inArchive.getProperty(index, PropID.IS_FOLDER);

			if (extractAskMode != ExtractAskMode.EXTRACT) {
				return null;
			}
			String path = (String) inArchive.getProperty(index, PropID.PATH);

			try {
				File outputDirectory = File.createTempFile("Extract", "");
				outputDirectory.delete();
				outputDirectory.mkdir();

				extractedFile = new File(outputDirectory, path);
				if (isFolder) {
					createDirectory(extractedFile);
					return null;
				}

				createDirectory(extractedFile.getParentFile());
				outputStream = new FileOutputStream(extractedFile);
			} catch (FileNotFoundException e) {
				throw new SevenZipException("Error opening file: " + extractedFile.getAbsolutePath(), e);
			} catch (IOException e) {
				throw new SevenZipException("Error opening file: " + extractedFile.getAbsolutePath(), e);
			}

			return new ISequentialOutStream() {

				public int write(byte[] data) throws SevenZipException {
					try {
						outputStream.write(data);
					} catch (IOException e) {
						throw new SevenZipException("Error writing to file: " + extractedFile.getAbsolutePath());
					}
					return data.length; // Return amount of consumed data
				}
			};

		}

		private void createDirectory(File parentFile) throws SevenZipException {
			if (!parentFile.exists()) {
				if (!parentFile.mkdirs()) {
					throw new SevenZipException("Error creating directory: " + parentFile.getAbsolutePath());
				}
			}
		}

		private void closeOutputStream() throws SevenZipException {
			if (outputStream != null) {
				try {
					outputStream.close();
					outputStream = null;
				} catch (IOException e) {
					throw new SevenZipException("Error closing file: " + extractedFile.getAbsolutePath());
				}
			}
		}

		public void prepareOperation(ExtractAskMode extractAskMode) throws SevenZipException {
		}

		public void setOperationResult(ExtractOperationResult extractOperationResult) throws SevenZipException {
			closeOutputStream();
			String path = (String) inArchive.getProperty(index, PropID.PATH);
			if (extractOperationResult != ExtractOperationResult.OK) {
				throw new SevenZipException("Invalid file: " + path);
			}

		}

		public void setCompleted(long completeValue) throws SevenZipException {
		}

		public void setTotal(long total) throws SevenZipException {
		}
	}

	public static File unRarFile(File compressedFile, String fileName) {
		Archive arch = null;
		try {
			arch = new Archive(compressedFile);
			List<FileHeader> fileHeaders = arch.getFileHeaders();
			for (FileHeader fh : fileHeaders) {
				String fileEstrattoName;
				if (fh.isFileHeader() && fh.isUnicode()) {
					fileEstrattoName = fh.getFileNameW();
				} else {
					fileEstrattoName = fh.getFileNameString();
				}
				if (!fh.isDirectory() && fileEstrattoName.equalsIgnoreCase(fileName)) {
					File file = File.createTempFile("Extract", "");
					new File(file.getParent()).mkdirs();
					arch.extractFile(fh, new FileOutputStream(file));
					return file;
				}
			}
		} catch (RarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo che sbusta un file una sola volta
	 * 
	 * @param file
	 * @return
	 */
	public InputStream singleEnvelopedFileDecription(File file) throws LuceneManagerException {
		try {
			URL url = new URL(wsEndpoint);
			QName qname = new QName("it.eng.fileoperation.ws", "FOImplService");
			Service service = Service.create(url, qname);
			FileOperationWS fooService = service.getPort(FileOperationWS.class);

			// enable mtom on client
			((BindingProvider) fooService).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest request = new FileOperationRequest();

			Operations operations = new Operations();
			InputUnpackType input = new InputUnpackType();
			operations.getOperation().add(input);

			request.setOperations(operations);
			DataHandler fileStream = new DataHandler(new FileDataSource(file));
			InputFile inputFile = new InputFile();
			inputFile.setFileStream(fileStream);
			InputFileOperationType fileOp = new InputFileOperationType();
			fileOp.setInputType(inputFile);
			request.setFileOperationInput(fileOp);
			FileOperationResponse risposta = fooService.execute(request);

			if (risposta != null && risposta.getFileoperationResponse() != null && ResponseUtils.isResponseOK(risposta)) {
				DataHandler fileResultDH = risposta.getFileoperationResponse().getFileResult();
				if (fileResultDH != null)
					return fileResultDH.getInputStream();
			}
		} catch (MalformedURLException e) {
			log.error("Impossibile terminare l'operazione di sbustamente", e);
			throw new LuceneManagerException("Impossibile terminare l'operazione di sbustamente", e);
		} catch (IOException e) {
			log.error("Impossibile terminare l'operazione di sbustamente", e);
			throw new LuceneManagerException("Impossibile terminare l'operazione di sbustamente", e);
		} catch (Exception e) {
			log.error("Impossibile terminare l'operazione di sbustamente", e);
			throw new LuceneManagerException("Impossibile terminare l'operazione di sbustamente", e);
		}

		return null;
	}

	public String getFormato(File file) throws LuceneManagerException {
		try {
			URL url = new URL(wsEndpoint);
			QName qname = new QName("it.eng.fileoperation.ws", "FOImplService");
			Service service = Service.create(url, qname);
			FileOperationWS fooService = service.getPort(FileOperationWS.class);

			// enable mtom on client
			((BindingProvider) fooService).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest request = new FileOperationRequest();

			Operations operations = new Operations();
			InputFormatRecognitionType input = new InputFormatRecognitionType();
			operations.getOperation().add(input);
			request.setOperations(operations);

			DataHandler fileStream = new DataHandler(new FileDataSource(file));
			InputFile inputFile = new InputFile();
			inputFile.setFileStream(fileStream);
			InputFileOperationType fileOp = new InputFileOperationType();
			fileOp.setInputType(inputFile);
			request.setFileOperationInput(fileOp);
			FileOperationResponse risposta = fooService.execute(request);

			if (risposta != null && risposta.getFileoperationResponse() != null && ResponseUtils.isResponseOK(risposta)) {
				if (risposta.getFileoperationResponse().getFileOperationResults() != null
						&& risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult() != null) {
					for (AbstractResponseOperationType opResponse : risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult()) {
						if (opResponse instanceof ResponseFormatRecognitionType) {
							ResponseFormatRecognitionType formatResponse = (ResponseFormatRecognitionType) opResponse;
							return formatResponse.getMimeType();
						}
					}
				}
			}
		} catch (MalformedURLException e) {
			log.error("Impossibile riconoscere il formato del file", e);
			throw new LuceneManagerException("Impossibile riconoscere il formato del file", e);
		} catch (Exception e) {
			log.error("Impossibile riconoscere il formato del file", e);
			throw new LuceneManagerException("Impossibile riconoscere il formato del file", e);
		}

		return null;
	}

	/**
	 * Metodo che sbusta, ricorsivamente, un file
	 * 
	 * @param file
	 * @return
	 * @throws LuceneManagerException
	 */
	public InputStream recursiveEnvelopedFileDecription(File file) throws LuceneManagerException {
		try {
			URL url = new URL(wsEndpoint);
			QName qname = new QName("it.eng.fileoperation.ws", "FOImplService");
			Service service = Service.create(url, qname);
			FileOperationWS fooService = service.getPort(FileOperationWS.class);

			// enable mtom on client
			((BindingProvider) fooService).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest request = new FileOperationRequest();

			Operations operations = new Operations();
			InputUnpackType input = new InputUnpackType();
			operations.getOperation().add(input);

			request.setOperations(operations);

			DataHandler fileStream = new DataHandler(new FileDataSource(file));
			InputFile inputFile = new InputFile();
			inputFile.setFileStream(fileStream);
			InputFileOperationType fileOp = new InputFileOperationType();
			fileOp.setInputType(inputFile);
			request.setFileOperationInput(fileOp);

			FileOperationResponse risposta = fooService.execute(request);
			if (risposta != null && risposta.getFileoperationResponse() != null && ResponseUtils.isResponseOK(risposta)) {
				DataHandler fileResultDH = risposta.getFileoperationResponse().getFileResult();
				if (fileResultDH != null)
					return fileResultDH.getInputStream();
			}
		} catch (MalformedURLException e) {
			log.error("Impossibile terminare l'operazione di sbustamente", e);
			throw new LuceneManagerException("Impossibile terminare l'operazione di sbustamente", e);
		} catch (IOException e) {
			log.error("Impossibile terminare l'operazione di sbustamente", e);
			throw new LuceneManagerException("Impossibile terminare l'operazione di sbustamente", e);
		} catch (Exception e) {
			log.error("Impossibile terminare l'operazione di sbustamente", e);
			throw new LuceneManagerException("Impossibile terminare l'operazione di sbustamente", e);
		}

		return null;
	}

	public static File getFileContentEntry(String directoryPath, File responseFile) throws IOException, LuceneManagerException {
		String fileExtension = FilenameUtils.getExtension(responseFile.getName());
		// caso default, la response � di tipo xml
		File xmlResponseFile;
		if (fileExtension.equals(ZIP_TYPE)) {
			// Il responseFile � zippato, lo estraggo con un metodo di utility
			List<String> fileNames = unzipFile(responseFile, directoryPath, true);
			// File di response estratto dallo zip
			xmlResponseFile = new File(directoryPath + File.separator + fileNames.get(0));
			// File xmlResponseFile = new File(directoryPath+File.separator+IDT_FILENAME);
		} else if (fileExtension.equals(P7M_TYPE)) {
			JobUtils utilsjob = new JobUtils();
			InputStream stream = utilsjob.recursiveEnvelopedFileDecription(responseFile);
			xmlResponseFile = new File(directoryPath + File.separator + FilenameUtils.removeExtension(responseFile.getName()));
			IOUtils.copy(stream, new FileOutputStream(xmlResponseFile));
			utilsjob.deletetmpfile();
		} else {
			if (fileExtension.equals(GZ_TYPE)) {
				// Il responseFile � zippato, lo estraggo con un metodo di utility
				List<String> fileNames = untarFile(directoryPath, responseFile);
				// File di response estratto dallo zip
				xmlResponseFile = new File(directoryPath + File.separator + fileNames.get(0));
				// File xmlResponseFile = new File(directoryPath+File.separator+IDT_FILENAME);
			} else {
				xmlResponseFile = responseFile;
			}
		}
		return xmlResponseFile;
	}

	/**
	 * Metodo che scompatta un file una sola volta
	 * 
	 * @param file
	 * @return
	 */
	public List<FileVO> getContentOfZipFile(File file, String mimetype) throws LuceneManagerException {
		List<FileVO> fileVOs = new ArrayList<FileVO>();
		try {
			URL url = new URL(wsEndpoint);
			QName qname = new QName("it.eng.fileoperation.ws", "FOImplService");
			Service service = Service.create(url, qname);
			FileOperationWS fooService = service.getPort(FileOperationWS.class);

			// enable mtom on client
			((BindingProvider) fooService).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest request = new FileOperationRequest();

			Operations operations = new Operations();
			InputUnpackMultipartType input = new InputUnpackMultipartType();
			input.setRecursive(true);
			operations.getOperation().add(input);

			request.setOperations(operations);
			DataHandler fileStream = new DataHandler(new FileDataSource(file));
			InputFile inputFile = new InputFile();
			inputFile.setFileStream(fileStream);
			InputFileOperationType fileOp = new InputFileOperationType();
			fileOp.setInputType(inputFile);
			request.setFileOperationInput(fileOp);
			FileOperationResponse risposta = fooService.execute(request);

			if (risposta != null && risposta.getFileoperationResponse() != null) {// && ResponseUtils.isResponseOK( risposta ) ){
				if (risposta != null && risposta.getFileoperationResponse() != null) {// && ResponseUtils.isResponseOK( risposta ) ){
					if (risposta.getFileoperationResponse().getFileOperationResults() != null
							&& risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult() != null) {
						for (AbstractResponseOperationType opResponse : risposta.getFileoperationResponse().getFileOperationResults()
								.getFileOperationResult()) {
							if (opResponse instanceof ResponseUnpackMultipartType) {
								ResponseUnpackMultipartType unpackMultipartResponse = (ResponseUnpackMultipartType) opResponse;

								if (unpackMultipartResponse.getMultipartContents() != null) {
									List<MultipartContentType> multipartContentTypes = unpackMultipartResponse.getMultipartContents().getMultipartContent();

									for (Iterator<MultipartContentType> iterator = multipartContentTypes.iterator(); iterator.hasNext();) {
										MultipartContentType multipartContentType = (MultipartContentType) iterator.next();
										fileVOs.add(creaFileVO(multipartContentType, file, mimetype));
									}
								}
							}
						}
					}
				}

			}
		} catch (MalformedURLException e) {
			log.error("Impossibile terminare l'operazione di unzip", e);
			throw new LuceneManagerException("Impossibile terminare l'operazione di unzip", e);
		} catch (IOException e) {
			log.error("Impossibile terminare l'operazione di unzip", e);
			e.printStackTrace();
			throw new LuceneManagerException("Impossibile terminare l'operazione di unzip", e);
		} catch (Exception e) {
			log.error("Impossibile terminare l'operazione di unzip", e);
			throw new LuceneManagerException("Impossibile terminare l'operazione di unzip", e);
		}

		return fileVOs;
	}

	private FileVO creaFileVO(MultipartContentType multipartContentType, File file, String fileMimetype) throws Exception {
		FileVO fileVo = new FileVO();
		String nome = multipartContentType.getNome();
		String mimeType = multipartContentType.getMimeType();
		String path = multipartContentType.getPath();
		System.out.println(nome + " " + mimeType + " " + path);
		fileVo.setNome(nome);
		fileVo.setMimeType(mimeType);
		fileVo.setPath(path);

		if (fileMimetype.equalsIgnoreCase("application/x-compressed")) {
			File fileEstratto = unzipFile(file, nome);
			if (fileEstratto != null)
				fileVo.setFile(fileEstratto);
		}

		else if (fileMimetype.equalsIgnoreCase("application/x-gzip-compressed")) {
			File fileEstratto = untarFile(file, nome);
			if (fileEstratto != null)
				fileVo.setFile(fileEstratto);
		}

		else if (fileMimetype.equalsIgnoreCase("application/x-rar-compressed")) {
			File fileEstratto = unRarFile(file, nome);
			if (fileEstratto != null)
				fileVo.setFile(fileEstratto);
		}

		else if (fileMimetype.equalsIgnoreCase("application/x-7z")) {
			File fileEstratto = un7ZipFile(file, nome);
			if (fileEstratto != null)
				fileVo.setFile(fileEstratto);
		}

		List<TMimetypeFmtDig> listaReaders = daoMimeType.getAllRecords();
		AbstractDocumentReader abstractreader = null;
		for (TMimetypeFmtDig bean : listaReaders) {
			if (bean.getId().getMimetype().contains(mimeType)) {
				log.debug("cerco di istanziare il seguente reader: " + bean.getRifReader());
				if (bean.getRifReader() != null) {
					abstractreader = (AbstractDocumentReader) Class.forName(bean.getRifReader()).newInstance();
					boolean ocr = false;
					System.out.println(abstractreader);
					if (abstractreader instanceof OcrDocumentReader || abstractreader instanceof PdfDocumentReader)
						ocr = true;
					abstractreader.setOcrOperation(ocr);
					break;
				} else {
					log.error("Nessun reader associato al documento con il seguente mimetype: " + mimeType);
					throw new LuceneManagerException("Nessun reader associato al documento con il seguente mimetype: " + mimeType);
				}
			}
		}
		fileVo.setAbstractreader(abstractreader);

		if (multipartContentType.getMultipartContent() != null && multipartContentType.getMultipartContent().size() > 0) {
			for (MultipartContentType multipartContent : multipartContentType.getMultipartContent()) {
				fileVo.getListaFileVO().add(creaFileVO(multipartContent, fileVo.getFile(), multipartContent.getMimeType()));
			}
		}

		return fileVo;
	}

	public void deletetmpfile() {
		if (this.tmpextract != null) {
			tmpextract.delete();
		}
	}

	public String getWsEndpoint() {
		return wsEndpoint;
	}

	public void setWsEndpoint(String wsEndpoint) {
		this.wsEndpoint = wsEndpoint;
	}

	public DaoTMimeTypeFmtDig getDaoMimeType() {
		return daoMimeType;
	}

	public void setDaoMimeType(DaoTMimeTypeFmtDig daoMimeType) {
		this.daoMimeType = daoMimeType;
	}

}
