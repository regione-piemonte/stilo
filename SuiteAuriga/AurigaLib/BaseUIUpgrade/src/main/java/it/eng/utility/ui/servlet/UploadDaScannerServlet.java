/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.digest.DigestCtrl;
import it.eng.utility.module.config.ModuleConfig;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.impl.StorageServiceImpl;
import it.eng.utility.ui.module.core.server.service.GsonBuilderFactory;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

@Controller
@RequestMapping("/UploadDaScannerServlet")
public class UploadDaScannerServlet {

	private static Logger logger = Logger.getLogger(UploadDaScannerServlet.class);
	private StorageService storageService;

	@RequestMapping(value="/", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> post(@RequestParam("idSelected") String idSelected,
			@RequestParam("userfile") MultipartFile userfile,
			final HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {
		initStorageService();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		try {
			String filename = userfile.getOriginalFilename();
			String lStrPath = storageService.storeStream(userfile.getInputStream());
			String fileSavedPDF = StringEscapeUtils.escapeJava(filename+"#"+lStrPath);
			StringBuffer lStringBuffer = new StringBuffer();
			GsonBuilder builder = GsonBuilderFactory.getIstance();
			Gson gson = builder.create();
			String result = gson.toJson(retrieveInfo(userfile.getInputStream(), filename, lStrPath));
			lStringBuffer.append(fileSavedPDF + "---" + result);
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);		  	   
		} catch (Exception e) {
			logger.error("Errore: " + e.getMessage(), e);
			return new ResponseEntity<String>(e.getMessage(), responseHeaders, HttpStatus.CREATED);      	
		}

	}

	private MimeTypeFirmaBean retrieveInfo(InputStream pInputStream, String pStrDisplayFileName, String lStrPath) throws Exception{
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lMimeTypeFirmaBean.setConvertibile(true);
		lMimeTypeFirmaBean.setCorrectFileName(pStrDisplayFileName);
		lMimeTypeFirmaBean.setFirmato(false);
		lMimeTypeFirmaBean.setMimetype("application/pdf");
		lMimeTypeFirmaBean.setDaScansione(true);
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
		DigestCtrl lDigestCtrl = new DigestCtrl();
		lDigestCtrl.setDigestAlgId(lDocumentConfiguration.getAlgoritmo());
		lDigestCtrl.setEncoding(lDocumentConfiguration.getEncoding());
		lMimeTypeFirmaBean.setImpronta(lDigestCtrl.execute(pInputStream));
		lMimeTypeFirmaBean.setConvertibile(true);
		lMimeTypeFirmaBean.setCorrectFileName(pStrDisplayFileName);
		lMimeTypeFirmaBean.setFirmato(false);
		lMimeTypeFirmaBean.setMimetype("application/pdf");
		lMimeTypeFirmaBean.setDaScansione(true);
		File extractFile = storageService.getRealFile(lStrPath);
		lMimeTypeFirmaBean.setBytes(extractFile.length());
		lMimeTypeFirmaBean.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(extractFile));		
		return lMimeTypeFirmaBean;

	}

	private void initStorageService() {
		storageService = StorageServiceImpl.newInstance(
				new GenericStorageInfo() {			

					public String getUtilizzatoreStorageId() {
						ModuleConfig mc = (ModuleConfig)SpringAppContext.getContext().getBean("moduleConfig");
						logger.debug("Recuperato module config");
						if(mc!=null && StringUtils.isNotBlank(mc.getIdDbStorage())) {
							logger.debug("Id Storage vale " + mc.getIdDbStorage());
							return mc.getIdDbStorage();
						}
						else {
							logger.error("L'identificativo del DB di storage non è correttamente configurato, " +
							"controllare il file di configurazione del modulo.");
							return null;
						}
					}
				}
		);
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> get(final HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {
		return new ResponseEntity<String>("<html><h1>Hi this is Upload Da Scanner</h1></html>", HttpStatus.OK);
	}
}
