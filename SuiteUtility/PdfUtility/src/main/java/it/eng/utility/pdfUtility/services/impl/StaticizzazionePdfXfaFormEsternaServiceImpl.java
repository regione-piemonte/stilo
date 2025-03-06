package it.eng.utility.pdfUtility.services.impl;

import java.io.File;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.utility.pdfUtility.services.StaticizzazionePdfXfaFormService;
import it.eng.utility.pdfUtility.services.client.StaticizzaPdfXfaFormClient;
import it.eng.utility.pdfUtility.services.client.StaticizzaPdfXfaFormConfigBean;
import it.eng.utility.pdfUtility.services.client.XmlSpecificheBean;

public class StaticizzazionePdfXfaFormEsternaServiceImpl implements StaticizzazionePdfXfaFormService {

	private String endpoint;
	private String username;
	private String password;
	private Integer timeout;
	
	public static final Logger logger = LogManager.getLogger(StaticizzazionePdfXfaFormEsternaServiceImpl.class);
			
	public StaticizzazionePdfXfaFormEsternaServiceImpl(String endpoint, String username, String password,
			Integer timeout) {
		super();
		this.endpoint = endpoint;
		this.username = username;
		this.password = password;
		this.timeout = timeout;
	}


	public InputStream staticizzaFile(File fileDaStaticizzare, XmlSpecificheBean xmlSpecifiche){
		StaticizzaPdfXfaFormConfigBean configBean = new StaticizzaPdfXfaFormConfigBean();
		configBean.setEndpoint(endpoint);
		configBean.setUsername(username);
		configBean.setPassword(password);
		configBean.setTimeout(timeout);
		logger.debug("configBean: " + configBean);
		try {
			StaticizzaPdfXfaFormClient client = new StaticizzaPdfXfaFormClient(configBean);
			InputStream response = client.staticizzaPdfXfaForm(fileDaStaticizzare, xmlSpecifiche);
			logger.debug("restituisco il file ricevuto dal servizio di download");
			return response;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
}
