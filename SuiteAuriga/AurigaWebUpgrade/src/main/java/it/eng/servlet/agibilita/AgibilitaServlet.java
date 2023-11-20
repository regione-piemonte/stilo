/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import antlr.StringUtils;
//import bsh.StringUtil;
import org.apache.commons.lang3.StringUtils;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.utility.crypto.CryptoUtility;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;

/**
 * @author Antonio Peluso
 */

@Controller
@RequestMapping("/agibilita")
public class AgibilitaServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AgibilitaServlet.class);

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 10;
	private static final long DEFAULT_VALIDATE_HOURS = 24;
	// ===================================================================================================
	private static final String VALUE_CONTENT_DISPOSITION_INLINE = "inline";
	private static final String VALUE_CONTENT_DISPOSITION_ATTACH = "attachment";
	// ===================================================================================================
	private static final String VALUE_MEDIA_TYPE_APPL_BINARY = "application/octet-stream";
	// private static final String VALUE_MEDIA_TYPE_APPL_PDF = "application/pdf";
	private static final String VALUE_MEDIA_TYPE_APPL_ZIP = "application/zip";
	// ===================================================================================================
	private static final String DEFAULT_VALUE_MIME_TYPE = VALUE_MEDIA_TYPE_APPL_BINARY;
	// ===================================================================================================
	private static final String PARAM_NAME_DOCUMENTS = "docs";
	private static final String PARAM_NAME_TOKEN = "token";
	private static final String OPERATION_PREVIEW = "preview";
	private static final String OPERATION_DOWNLOAD = "download";
	private Tika tika = new Tika();

	@RequestMapping(value = "/download", method = { RequestMethod.GET })
	private void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// =====================================================================
		String contentType = DEFAULT_VALUE_MIME_TYPE;
		long contentLength = 0;
		String contentDisposition = VALUE_CONTENT_DISPOSITION_ATTACH;
		String fileName;
		String operation;

		// ===================================================================== (1)
		// read the request data

		// Recupero il file dal path
		final String[] docs = request.getParameterValues(PARAM_NAME_DOCUMENTS);
		final String[] token = request.getParameterValues(PARAM_NAME_TOKEN);
		
		//Funzione che verifica la validità del token
		validateToken(token);

		String uri = docs[0].substring(0, docs[0].indexOf(";"));
		String nomeFile = docs[0].substring(docs[0].indexOf(";") + 1);
		String ext = FilenameUtils.getExtension(nomeFile);

		if (ext.equalsIgnoreCase("zip")) {
			operation = OPERATION_DOWNLOAD;
		} else {
			operation = OPERATION_PREVIEW;
		}

		fileName = nomeFile;

		final File file = generateTempFile(uri, nomeFile, request);

		if (operation.equalsIgnoreCase("preview")) {
			final String mimeType = tika.detect(file);
			contentType = mimeType;
			contentLength = file.length();
			contentDisposition = VALUE_CONTENT_DISPOSITION_INLINE;
		} else {
			contentType = VALUE_MEDIA_TYPE_APPL_ZIP;
			contentDisposition = VALUE_CONTENT_DISPOSITION_ATTACH;
		}

		// ======================================================================= (2)
		// write the response headers
		response.setContentType(contentType);

		if (contentLength > 0) {
			response.setContentLength((int) contentLength);
		}

		final String cdValue = String.format("%s; filename=\"%s\"", contentDisposition, fileName);
		response.addHeader("Content-Disposition", cdValue);

		// ================================================ (3) get the response's
		// output stream object
		final ServletOutputStream os = response.getOutputStream();
		// ======================================================================= (4)
		// write the response data

		fillResponse(os, file);

	}// download

	private File generateTempFile(String uri, String nomeFile, HttpServletRequest request) throws Exception {
		final File file = getFile(request.getSession(), uri);
		String ext = FilenameUtils.getExtension(nomeFile);
		String name = FilenameUtils.getBaseName(nomeFile);
		File tempFile = File.createTempFile(name, "." + ext);
		FileUtils.copyFile(file, tempFile);

		return tempFile;
	}

	private void fillResponse(OutputStream out, File file) throws IOException {
		try (InputStream in = openInputStream(file)) {
			writeData(in, out, DEFAULT_BUFFER_SIZE);
		}
	}

	private FileInputStream openInputStream(File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}

	private File getFile(HttpSession session, String uri) throws StorageException {
		RecuperoFile lRecuperoFile = new RecuperoFile();
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(session);
		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
		lFileExtractedIn.setUri(uri);
		FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
		return out.getExtracted();
	}

	private void writeData(InputStream in, OutputStream out, int bufferSize) throws IOException {
		final byte[] buffer = new byte[bufferSize];
		int length;
		while ((length = in.read(buffer)) >= 0) {
			out.write(buffer, 0, length);
		}
	}

	/**
	 * 
	 * @param token
	 * @throws Exception
	 */
	private void validateToken(String[] token) throws Exception {
		if (token.length == 0) {
			logger.error("Campo Token non presente.");
			throw new AccessoNegatoAgibilitaException("Campo Token non presente.");
		} else if(StringUtils.isBlank(token[0])) {
			logger.error("Token di accesso non presente.");
			throw new AccessoNegatoAgibilitaException("Token di accesso non presente.");
		} else {
			String decoded = CryptoUtility.decrypt64FromString(token[0]);
			Long timeGenLink = Long.parseLong(decoded);
			Long currentTime = System.currentTimeMillis();
			long hours = TimeUnit.MILLISECONDS.toHours(currentTime - timeGenLink);
			if (hours > retrieveValidateHours()) {
				logger.error("Il link risulta scaduto.");
				throw new AccessoNegatoAgibilitaException("Il link risulta scaduto.");
			}
		}
		logger.debug("Token valido");
	}

	private long retrieveValidateHours() {
		// TODO: da vedere come/dove recuperare. Valore che mi indica la validita del token espressa in ore
		return DEFAULT_VALIDATE_HOURS;
	}

}// DownloadFileAgibilitaServlet
