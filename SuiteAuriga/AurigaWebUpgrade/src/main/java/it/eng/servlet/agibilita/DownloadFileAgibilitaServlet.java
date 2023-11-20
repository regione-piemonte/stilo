/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.semanticdesktop.aperture.util.FileUtil;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;

public class DownloadFileAgibilitaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(DownloadFileAgibilitaServlet.class);

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 10;
	private static final String DEFAULT_ZIP_FILE_NAME = "docs.zip";
	// ===================================================================================================
	private static final String VALUE_CONTENT_DISPOSITION_INLINE = "inline";
	private static final String VALUE_CONTENT_DISPOSITION_ATTACH = "attachment";
	// ===================================================================================================
	private static final String VALUE_MEDIA_TYPE_APPL_BINARY = "application/octet-stream";
	// private static final String VALUE_MEDIA_TYPE_APPL_PDF = "application/pdf";
	private static final String VALUE_MEDIA_TYPE_APPL_ZIP = "application/zip";
	// private static final String VALUE_MEDIA_TYPE_MULTIPART_MIXED = "multipart/mixed";
	// ====================================================================================================
	private static final String DEFAULT_VALUE_MIME_TYPE = VALUE_MEDIA_TYPE_APPL_BINARY;
	private static final String DEFAULT_VALUE_CONTENT_DISPOSITION = VALUE_CONTENT_DISPOSITION_INLINE;
	// ======================================================================================================
	private static final String INIT_PARAM_NAME_ZIP_FILE_NAME = "zipFileName";
	private static final String PARAM_NAME_DOCUMENTS = "docs";
	private Tika tika = new Tika();
	private String zipFileName;

	@Override
	public void init() throws ServletException {
		final String zipFileNameValue = getInitParameter(INIT_PARAM_NAME_ZIP_FILE_NAME);
		if (StringUtils.isNotBlank(zipFileNameValue)) {
			zipFileName = zipFileNameValue;
		} else {
			zipFileName = DEFAULT_ZIP_FILE_NAME;
		}
		logger.debug("zipFileName: " + zipFileName);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doDownload(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doDownload(request, response);
	}

	private void doDownload(HttpServletRequest request, HttpServletResponse response) throws DownloadFileAgibilitaException {
		try {
			download(request, response);
		} catch (Exception e) {
			getServletContext().log("Errore nello scarico documenti.", e);
			throw new DownloadFileAgibilitaException(e);
		}
	}

	private void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final ServletContext ctx = getServletContext();
		// ========================================================================================================
		String contentType = DEFAULT_VALUE_MIME_TYPE;
		long contentLength = 0;
		String contentDisposition = VALUE_CONTENT_DISPOSITION_ATTACH;
		String fileName = zipFileName;
		// ===================================================================== (1) read the request data
		final String[] docs = request.getParameterValues(PARAM_NAME_DOCUMENTS);
		
		logger.debug("INIZIOIO SERVLET AGIBILITA");
	
		/*Logica per dare risultati cablati (fake) da cancellare e utilizzare la logica sottostante, quando la store
		  del servizio agibilita restiutira dei risultati
		*/
		final File[] files = new File[docs.length];
		switch (docs.length) {
		case 1:
			
			logger.debug("SERVLET AGIBILITA - PREVIEW");
			
			final File file = getFileCablati(ctx, fileName = docs[0]); // Reading a resource inside a web application
			files[0] = file;
			// String mimeType = ctx.getMimeType(filePath);
			final String mimeType = tika.detect(file);
			logger.debug("Media Type del file '" + fileName + "': " + mimeType);
			contentType = mimeType;
			contentLength = file.length();
			contentDisposition = VALUE_CONTENT_DISPOSITION_INLINE;
			break;
		default:
			
			logger.debug("SERVLET AGIBILITA - DOWNLOAD");
			for (int i = 0; i < docs.length; i++) {
				final File file_i = getFileCablati(ctx, docs[i]);
				files[i] = file_i;
			} // for
			contentType = VALUE_MEDIA_TYPE_APPL_ZIP;
			contentDisposition = VALUE_CONTENT_DISPOSITION_ATTACH;
			break;
		}
					
		/**
		 * 
		 *  LOGICA CORRETTA DA COMMENTARE QUANDO LA STORE DEL SERVIZIO AGIBILITA DARA RISULTATI
		 * 
		 * 
		final File[] files = generateTempFile(docs, request);
		
		switch (files.length) {
		case 1:		
			final String mimeType = tika.detect(files[0]);
			logger.debug("Media Type del file '" + fileName + "': " + mimeType);
			contentType = mimeType;
			contentLength = files[0].length();
			contentDisposition = VALUE_CONTENT_DISPOSITION_INLINE;
			break;
		default:
			contentType = VALUE_MEDIA_TYPE_APPL_ZIP;
			contentDisposition = VALUE_CONTENT_DISPOSITION_ATTACH;
			break;
		}
		*/
		
		// ======================================================================= (2) write the response headers
		response.setContentType(contentType);

		if (contentLength > 0) {
			response.setContentLength((int) contentLength);
		}

		final String cdValue = String.format("%s; filename=\"%s\"", contentDisposition, fileName);
		response.addHeader("Content-Disposition", cdValue);

		// ================================================ (3) get the response's output stream object
		final ServletOutputStream os = response.getOutputStream();
		// ======================================================================= (4) write the response data

		switch (files.length) {
		case 1:
			fillResponse(os, files[0]);
			break;
		default:
			fillResponse(os, files);
			break;
		}
		
		
		logger.debug("FINE SERVLET AGIBILITA");
		
	}// download
	
	private File[] generateTempFile(String[] docs, HttpServletRequest request) throws Exception {
		final File[] files = new File[docs.length];
		for(int i = 0; i < docs.length; i++) {
			String uri = docs[i].substring(0, docs[i].indexOf(";"));
			String nomeFile = docs[i].substring(docs[i].indexOf(";") + 1);	
			
			final File file = getFile(request.getSession(), uri);
			String ext = FilenameUtils.getExtension(nomeFile);
			String name = FilenameUtils.getBaseName(nomeFile);
			File tempFile = File.createTempFile(name, "." + ext);
			FileUtil.copyFile(file, tempFile);
			files[i] = tempFile;
		}
		return files;
	}

	private void fillResponse(OutputStream out, File file) throws IOException {
		try (InputStream in = openInputStream(file)) {
			writeData(in, out, DEFAULT_BUFFER_SIZE);
			// IOUtils.copy(in, out);
		}
	}

	private void fillResponse(OutputStream out, File[] files) throws IOException {
		try (ZipOutputStream zos = new ZipOutputStream(out)) {
			for (File file : files) {
				addToZipFile(file, zos);
			}
		}
	}

	private void addToZipFile(File file, ZipOutputStream out) throws IOException {
		logger.debug("Writing '" + file + "' to zip file");
		final InputStream in = openInputStream(file);
		final ZipEntry zipEntry = new ZipEntry(file.getName());
		try {
			out.putNextEntry(zipEntry);
			writeData(in, out, DEFAULT_BUFFER_SIZE);
			// IOUtils.copy(in, out);
		} finally {
			out.closeEntry();
			in.close();
		}
	}

	private FileInputStream openInputStream(File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}

	private File getLocalFile(String uri) throws StorageException {
		File storedFile = StorageImplementation.getStorage().getRealFile(uri);
		return storedFile;
	}
	
	private File getFile(HttpSession session, String uri) throws StorageException {
		RecuperoFile lRecuperoFile = new RecuperoFile();
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(session);
		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
		lFileExtractedIn.setUri(uri);
		FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
		return out.getExtracted();
	}


	// N.B. da cancellare/commentare quando verrà utilizzata la logica reale di implementazione con dati concreti
	private File getFileCablati(ServletContext ctx, String fileName) throws FileNotFoundException {
		final String filePath = ctx.getRealPath("/WEB-INF/resources/") + "/" + fileName;
		return new File(filePath);
	}

	private void writeData(InputStream in, OutputStream out, int bufferSize) throws IOException {
		final byte[] buffer = new byte[bufferSize];
		int length;
		while ((length = in.read(buffer)) >= 0) {
			out.write(buffer, 0, length);
		}
	}

}// DownloadFileAgibilitaServlet
