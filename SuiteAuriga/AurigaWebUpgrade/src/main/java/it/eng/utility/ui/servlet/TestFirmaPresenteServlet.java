/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.FirmaUtility;
import it.eng.utility.module.config.ModuleConfig;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;

@Controller
@RequestMapping("/TestFirmaPresenteServlet")
public class TestFirmaPresenteServlet {

	private StorageService storageService;
	private static Logger logger = Logger.getLogger(TestFirmaPresenteServlet.class);

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> post(@RequestParam("userfile") MultipartFile userfile, final HttpSession session, HttpServletRequest servletrequest,
			HttpServletResponse servletresponse) throws Exception {
		StringBuffer lStringBuffer = new StringBuffer();
		initStorageService();
		String lStrPath = storageService.storeStream(userfile.getInputStream());
		File lFile = StorageImplementation.getStorage().extractFile(
				StorageImplementation.getStorage().store(StorageImplementation.getStorage().extractFile(lStrPath)));
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		lStringBuffer.append(FirmaUtility.isSigned(lFile.toURI().toString(), lFile.getName()));
		return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);
	}

	private void initStorageService() {
		storageService = StorageServiceImpl.newInstance(new GenericStorageInfo() {

			public String getUtilizzatoreStorageId() {
				ModuleConfig mc = (ModuleConfig) SpringAppContext.getContext().getBean("moduleConfig");
				logger.debug("Recuperato module config");
				if (mc != null && StringUtils.isNotBlank(mc.getIdDbStorage())) {
					logger.debug("Id Storage vale " + mc.getIdDbStorage());
					return mc.getIdDbStorage();
				} else {
					logger.error("L'identificativo del DB di storage non è correttamente configurato, " + "controllare il file di configurazione del modulo.");
					return null;
				}
			}
		});
	}
}
