/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.fileExtractor.FileExtractor;
import it.eng.utility.ui.servlet.fileExtractor.FileExtractorInstance;
import it.eng.utility.ui.servlet.fileExtractor.impl.LocalFileExtractor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Servlet di init per istanziare il server ADempiere
 * @author michele
 *
 */
@Controller
@RequestMapping("/download")
public class DownloadServlet {

	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(DownloadServlet.class);

	@RequestMapping(value="", method=RequestMethod.GET)
	@ResponseBody
	public HttpEntity download(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("octet", "stream"));
		String filename = null;
		byte[] documentBody = null;

		//Distinguo che tiupo di download sto facendo
		Boolean fromRecord = StringUtils.isNotBlank(req.getParameter("fromRecord")) ? Boolean.valueOf(req.getParameter("fromRecord")) : null;
		try {
			if(fromRecord != null) {
				FileExtractor lFileExtractor;
				InputStream stream;
				if (!fromRecord){
					try {
						lFileExtractor = new LocalFileExtractor(req);
						filename = lFileExtractor.getFileName();
						stream = lFileExtractor.getStream();
					} catch (Exception e) {
						throw new ServletException(e);
					}
				} else {
					String recordType = req.getParameter("recordType");
					try {
						lFileExtractor = FileExtractorInstance.getInstance().getRelatedFileExtractor(recordType, req);
						filename = lFileExtractor.getFileName();
						stream = lFileExtractor.getStream();
					} catch (Exception e) {
						throw new ServletException(e);
					}
				}
				try {
					documentBody = IOUtils.toByteArray(stream);
				} catch (Exception e2) {
					throw new ServletException(e2);
				}
			} else {
				filename = (String) req.getParameter("filename");
				String uri = (String) req.getParameter("uri");
				if(uri != null && !"".equals(uri)) {
					File file = new File(uri);	
					try {
						documentBody = IOUtils.toByteArray(FileUtils.openInputStream(file));
					} catch (Exception e2) {
						throw new ServletException(e2);
					}
				} 

			}
			header.set("Content-Disposition",
					"attachment; filename=\"" + filename+"\"");
			header.setContentLength(documentBody.length);
			return new HttpEntity<byte[]>(documentBody, header);
		} catch (Exception e) {
			logger.error("Errore download " + e.getMessage(), e);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentType(MediaType.TEXT_HTML);
			responseHeaders.setCacheControl("private, no-store, no-cache, must-revalidate");
			responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
			responseHeaders.add("Cache-Control", "private, no-store, no-cache, must-revalidate");
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append("<html>");
			lStringBuffer.append("<head>");
			lStringBuffer.append("<body>");
			lStringBuffer.append("<script>");
			lStringBuffer.append("try {window.top.errorCallback('Impossibile effettuare il download del file'); } " +
			"catch(err) {for (var p in err) alert(err[p])}");
			lStringBuffer.append("</script>");
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);
		}
	}



	/**
	 * Scrive il file sullo stream di output
	 * @param fileName
	 * @param file
	 * @param pHttpServletResponse 
	 * @throws IOException 
	 * @throws Exception 
	 */
	private void writeStreamToStream(String fileName, InputStream inputStream, HttpServletResponse pHttpServletResponse) throws IOException  {
		BufferedInputStream lBufferedInputStream = null;
		BufferedOutputStream lBufferedOutputStream = null;
		try {	
			pHttpServletResponse.setHeader("Content-Type", "octet/stream");
			pHttpServletResponse.setHeader("Content-Length", String.valueOf(inputStream.available()));
			pHttpServletResponse.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
			lBufferedInputStream = new BufferedInputStream(inputStream);
			lBufferedOutputStream = new BufferedOutputStream(pHttpServletResponse.getOutputStream());
			byte[] buf = new byte[1024];
			while (true) {
				int length = lBufferedInputStream.read(buf);
				if (length == -1)
					break;
				lBufferedOutputStream.write(buf, 0, length);
			}
			lBufferedOutputStream.flush();	        
		} catch (IOException e) {
			throw e;
		} finally {
			try {lBufferedOutputStream.close();} catch (Exception e) {}
			try {lBufferedInputStream.close();} catch (Exception e) {}
		}		
	}

}




