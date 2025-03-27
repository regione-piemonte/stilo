/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.eng.utility.ui.servlet.fileExtractor.FileExtractor;
import it.eng.utility.ui.servlet.fileExtractor.FileExtractorInstance;
import it.eng.utility.ui.servlet.fileExtractor.impl.LocalFileExtractor;

/**
 * Servlet di init per istanziare il server ADempiere
 * 
 * @author michele
 *
 */
@Controller
@RequestMapping("/download")
public class DownloadServlet {

	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(DownloadServlet.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String filename = null;
		long fileLength = 0;
		// Distinguo che tiupo di download sto facendo
		Boolean fromRecord = StringUtils.isNotBlank(req.getParameter("fromRecord"))
				? Boolean.valueOf(req.getParameter("fromRecord"))
				: null;
		try {
			InputStream stream = null;
			if (fromRecord != null) {
				FileExtractor lFileExtractor;
				if (!fromRecord) {
					try {
						lFileExtractor = new LocalFileExtractor(req);
						filename = lFileExtractor.getFileName();
						stream = lFileExtractor.getStream();
						fileLength = lFileExtractor.getFileLength();
					} catch (Exception e) {
						throw new ServletException(e);
					}
				} else {
					String recordType = req.getParameter("recordType");
					try {
						lFileExtractor = FileExtractorInstance.getInstance().getRelatedFileExtractor(recordType, req);
						filename = lFileExtractor.getFileName();
						stream = lFileExtractor.getStream();
						fileLength = lFileExtractor.getFileLength();
					} catch (Exception e) {
						throw new ServletException(e);
					}
				}
			} else {
				filename = (String) req.getParameter("filename");
				String uri = (String) req.getParameter("uri");
				if (uri != null && !"".equals(uri)) {
					File file = new File(uri);
					fileLength = file.length();
					stream = FileUtils.openInputStream(file);
				}

			}
			
			resp.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
			resp.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			resp.setHeader("Content-Length", ""+fileLength);
//			resp.setContentLength((int) fileLength);
//			resp.setContentLength(stream.available()); //aviable da un numero non corretto
			//per far uscire la dimensione totale durante il download è necessario settare in questo punto la grandezza del file

			try (InputStream inputStream = new BufferedInputStream(stream);
					OutputStream outputStream = new BufferedOutputStream(resp.getOutputStream())) {

				byte[] buffer = new byte[4096]; // Dimensione del buffer in byte
				int bytesRead;
				long totalBytesRead = 0;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
					outputStream.flush();
					totalBytesRead += bytesRead;
				}
				
			    resp.setHeader("Content-Length", String.valueOf(totalBytesRead));
			    outputStream.close();
			} catch (IOException e) {
			    throw new ServletException(e);
			} 
		} catch (Exception e) {
			
			logger.error("Errore download " + e.getMessage(), e);

			// Imposta il tipo di contenuto della risposta
			resp.setContentType(MediaType.TEXT_HTML.toString());
			resp.addHeader("Content-Type", "text/html;charset=ISO-8859-1");
			resp.addHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

			resp.setStatus(HttpServletResponse.SC_OK);

			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append("<html>");
			lStringBuffer.append("<head>");
			lStringBuffer.append("<body>");
			lStringBuffer.append("<script>");
			lStringBuffer.append("try {window.top.errorCallback('Impossibile effettuare il download del file'); } "
					+ "catch(err) {for (var p in err) alert(err[p])}");
			lStringBuffer.append("</script>");
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");

			// Scrivi il contenuto del StringBuffer nella risposta
			PrintWriter writer = resp.getWriter();
			writer.print(lStringBuffer.toString());
			writer.flush();
			writer.close();
		}
	}

}
