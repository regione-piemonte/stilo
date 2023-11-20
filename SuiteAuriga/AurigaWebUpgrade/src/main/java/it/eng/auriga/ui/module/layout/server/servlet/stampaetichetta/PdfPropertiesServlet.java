/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.PreferenceBean;
import it.eng.auriga.ui.module.layout.server.etichette.EtichetteService;
import it.eng.auriga.ui.module.layout.shared.util.SharedConstants;

/**
 * Servlet che ha lo scopo di fornire all'applet di Stampa Etichetta
 * il file di properties sulla base dell'utente
 * 
 * @author Rametta
 *
 */
@Controller
@RequestMapping("/pdfProperties")
public class PdfPropertiesServlet {
	private static final long serialVersionUID = 1L;
	
	private static String PDF_PROPERTIES = "pdf.properties";

	Logger logger = Logger.getLogger(PdfPropertiesServlet.class);

	@RequestMapping(value="", method=RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> download(HttpServletRequest req, HttpServletResponse resp, @RequestParam("idUtente") String pStrIdUtente,
			@RequestParam("idSchema") String pStrSchema, @RequestParam("idDominio") String pStrIdDominio,
			@RequestParam(required = false, value="version") String version)
	throws Exception {
		logger.debug(pStrIdUtente);
		logger.debug(version);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("octet", "stream"));
		String filename = PDF_PROPERTIES;
		InputStream lInputStream = obtainFile(pStrIdUtente, pStrSchema, pStrIdDominio);
		byte[] documentBody = IOUtils.toByteArray(lInputStream);
		header.set("Content-Disposition",
				"attachment; filename=" + filename);
		header.setContentLength(documentBody.length);
		return new HttpEntity<byte[]>(documentBody, header);
	}

	private InputStream obtainFile(String pStrIdUtente, String pStrSchema, String pStrIdDominio) throws Exception {
		AurigaLoginBean lAurigaLoginBean = new AurigaLoginBean();
		// Inserisco la lingua di default
		lAurigaLoginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
		lAurigaLoginBean.setIdUtente(pStrIdUtente);
		lAurigaLoginBean.setSchema(pStrSchema);
		PreferenceBean lPreferenceBean = 
			(new EtichetteService()).getPreference(EtichetteService.PREF_KEY_PDF_PROPERTIES, EtichetteService.PREF_NAME_PROPERTIES, pStrIdDominio + ".DEFAULT" , lAurigaLoginBean);
		if (lPreferenceBean!=null){
			return IOUtils.toInputStream(lPreferenceBean.getValue());
		} else {
			lPreferenceBean = 
				(new EtichetteService()).getPreference(EtichetteService.PREF_KEY_PDF_PROPERTIES, EtichetteService.PREF_NAME_PROPERTIES, EtichetteService.DEFAULT , lAurigaLoginBean);
			return IOUtils.toInputStream(lPreferenceBean.getValue());
		}
	}
}
