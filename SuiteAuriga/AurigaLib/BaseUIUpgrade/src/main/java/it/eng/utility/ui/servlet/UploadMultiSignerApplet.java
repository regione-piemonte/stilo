/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.ModuleConfig;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;
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

@Controller
@RequestMapping("/UploadMultiSignerApplet")
public class UploadMultiSignerApplet {

	private StorageService storageService;
	private static Logger logger = Logger.getLogger(UploadMultiSignerApplet.class);

	@RequestMapping(value="/", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> post(@RequestParam("idSelected") String idSelected,
			@RequestParam("idFile") String idFile,
			@RequestParam("userfile") MultipartFile userfile, 
			final HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {
		initStorageService();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		String fileSavedPDF = null;
		String lStrPath = null;
		try {
			logger.debug("Recupero il file originale");
			String[] infoFile = idSelected.split("#");
			String fileNameOriginale = infoFile[0];			
			if(!fileNameOriginale.toLowerCase().endsWith(".p7m")){
				fileNameOriginale = fileNameOriginale + ".p7m";
			}			
			lStrPath = storageService.storeStream(userfile.getInputStream());			
			//fileSavedPDF = StringEscapeUtils.escapeJava(idSelected+"#"+lStrPath + "#" + idFile);			
			fileSavedPDF = StringEscapeUtils.escapeJava(java.net.URLEncoder.encode(fileNameOriginale,"UTF-8")+"#"+lStrPath + "#" + idFile);			
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append(fileSavedPDF);
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("OPERATION_ERROR", responseHeaders, HttpStatus.CREATED);     	
		}
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
	public ResponseEntity<String> get(HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		StringBuffer lStringBuffer = new StringBuffer();
		return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.CREATED);
	}
}