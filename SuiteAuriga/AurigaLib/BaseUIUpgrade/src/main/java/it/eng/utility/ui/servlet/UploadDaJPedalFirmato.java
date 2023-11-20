/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.FormatConverterInterface;
import it.eng.utility.module.config.ModuleConfig;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;
import it.eng.utility.ui.module.core.server.service.GsonBuilderFactory;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

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

@Controller
@RequestMapping("/UploadDaJPedalFirmato")
public class UploadDaJPedalFirmato {
	private StorageService storageService;
	private static Logger logger = Logger.getLogger(UploadDaJPedalFirmato.class);

	@RequestMapping(value="/", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> post(@RequestParam("idSelected") String idSelected,
			@RequestParam("userfile") MultipartFile userfile, 
			final HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {
		initStorageService();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		String fileSavedPDF = null;
		String lStrPath = null;
		String lInputStream = null;
		try {
			lStrPath = storageService.storeStream(userfile.getInputStream());
			lInputStream = storageService.getRealFile(lStrPath).toURI().toString();
			MimeTypeFirmaBean lMimeTypeFirmaBean = retrieveInfo(lInputStream, idSelected, session);
			GsonBuilder builder = GsonBuilderFactory.getIstance();			Gson gson = builder.create();
			String result = gson.toJson(lMimeTypeFirmaBean);
			fileSavedPDF = StringEscapeUtils.escapeJava(lMimeTypeFirmaBean.getCorrectFileName()+"#"+lStrPath + "#" + result);
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append(fileSavedPDF);
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("OPERATION_ERROR", responseHeaders, HttpStatus.CREATED);     	
		}
	}
	
	private MimeTypeFirmaBean retrieveInfo(String pInputStream, String pStrDisplayFileName, HttpSession session){
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lMimeTypeFirmaBean.setDaScansione(false);
		try {
			lMimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(pInputStream, pStrDisplayFileName, false, null);
			lMimeTypeFirmaBean.setConvertibile(SpringAppContext.getContext().getBean(FormatConverterInterface.class).isValidFormat(lMimeTypeFirmaBean.getMimetype(), session));
			return lMimeTypeFirmaBean;
		} catch (Exception e) {
			return lMimeTypeFirmaBean;
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
