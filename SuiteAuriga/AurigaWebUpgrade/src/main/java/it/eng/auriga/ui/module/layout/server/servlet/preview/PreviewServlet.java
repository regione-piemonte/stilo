/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.eng.utility.ConvertToPdfUtil;
import it.eng.utility.ui.servlet.fileExtractor.FileExtractor;
import it.eng.utility.ui.servlet.fileExtractor.FileExtractorInstance;


@Controller
@RequestMapping("/preview")
public class PreviewServlet {
	
	private static Logger logger = Logger.getLogger(PreviewServlet.class);
	
	@RequestMapping(value="saveSelection", method=RequestMethod.GET)
	public ResponseEntity<String> saveSelection(HttpServletRequest req,	HttpServletResponse resp) {
		HttpSession session = req.getSession();
		//Salvo i dati in sessione
		String selectionId = req.getParameter("selectionId");
		String selection = req.getParameter("selection");
		session.setAttribute("PREVIEW_WINDOW_PAGE_SELECTION_" + selectionId, selection);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<String>("PAGINE SELEZIONATE SALVATE IN SESSIONE", responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value="saveRotation", method=RequestMethod.GET)
	public ResponseEntity<String> saveRotation(HttpServletRequest req,	HttpServletResponse resp) {
		HttpSession session = req.getSession();
		//Salvo i dati in sessione
		String selectionId = req.getParameter("selectionId");
		String rotateDegree = req.getParameter("rotateDegree");
		session.setAttribute("PREVIEW_WINDOW_PAGE_ROTATION_" + selectionId, rotateDegree);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<String>("ROTAZIONE SALVATA IN SESSIONE", responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value="getRotation", method=RequestMethod.GET)
	public HttpEntity<String> getRotation(@RequestParam("selectionId") String selectionId, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		//Prendo i dati dalla sessione
		String rotateDegree = (String) session.getAttribute("PREVIEW_WINDOW_PAGE_ROTATION_" + selectionId);
		
		//Pulisco la sessione dai dati
		session.removeAttribute("PREVIEW_WINDOW_PAGE_ROTATION_" + selectionId);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<String>(rotateDegree, responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value="getSelection", method=RequestMethod.GET)
	public HttpEntity<String> getSelection(@RequestParam("selectionId") String selectionId, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		//Prendo i dati dalla sessione
		String selection = (String) session.getAttribute("PREVIEW_WINDOW_PAGE_SELECTION_" + selectionId);
		
		//Pulisco la sessione dai dati
		session.removeAttribute("PREVIEW_WINDOW_PAGE_SELECTION_" + selectionId);
		
		//Prendo i dati dalla sessione
		String rotateDegree = (String) session.getAttribute("PREVIEW_WINDOW_PAGE_ROTATION_" + selectionId);
		
		//Pulisco la sessione dai dati
		session.removeAttribute("PREVIEW_WINDOW_PAGE_ROTATION_" + selectionId);
		
		String responseData = (StringUtils.isNotBlank(selection) ? selection : "") + "|*|" + (StringUtils.isNotBlank(rotateDegree) ? rotateDegree : "0");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<String>(responseData, responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value="", method=RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> download(@RequestParam("fromRecord") boolean fromRecord, 
			@RequestParam("recordType") String recordType,
			@RequestParam("record") String record,
			@RequestParam("mimetype") String mimetype,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException{
		
		HttpHeaders header = new HttpHeaders();
		if(mimetype != null && !"".equals(mimetype)) {
			// oltre a "text/html", anche per i mimetype "application/xhtml+xml" e "image/svg+xml" restituire il content-type = "text/plain" per non farlo eseguire dal client in caso ci fossero degli script al suo interno
			if("text/html".equalsIgnoreCase(mimetype) || "application/xhtml+xml".equalsIgnoreCase(mimetype) || "image/svg+xml".equalsIgnoreCase(mimetype)) {
				header.setContentType(MediaType.parseMediaType("text/plain"));
			} else {
				header.setContentType(MediaType.parseMediaType(mimetype));	
			}
		} else {
			header.setContentType(new MediaType("octet", "stream"));
		}
//		header.setContentDispositionFormData("inline", "automatic_start.pdf");
		
		byte[] documentBody = null;
		try {
			FileExtractor lFileExtractor = FileExtractorInstance.getInstance().getRelatedFileExtractor(recordType, req);
			lFileExtractor.getFileName();
			InputStream stream = lFileExtractor.getStream();
			
			JSONObject jsonObject = new JSONObject(record);
			if (mimetype.startsWith("image") && jsonObject.has("enableToSaveInOmissis") && jsonObject.getBoolean("enableToSaveInOmissis")) {
				try {
					stream = ConvertToPdfUtil.convertToPdf(jsonObject.getString("uri"), false, false);
				} catch (Exception e1) {
					logger.error("Si è verificato un errore durante la conversione in pdf del file" + e1.getMessage(), e1);
					throw new Exception("Si è verificato un errore durante la conversione in pdf del file");
				}
			}
			
			documentBody = IOUtils.toByteArray(stream);
			stream.close();
			
			header.setContentLength(documentBody.length);
			header.setCacheControl("public");
			header.add("Cache-Control", "public");
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
			lStringBuffer.append("<h1>File not found</h1>");
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");
			return new ResponseEntity<byte[]>(lStringBuffer.toString().getBytes(), responseHeaders, HttpStatus.OK);
		}
	}
}
