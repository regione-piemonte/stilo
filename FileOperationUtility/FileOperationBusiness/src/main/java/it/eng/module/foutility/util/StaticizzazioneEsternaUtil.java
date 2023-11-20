/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import it.eng.module.foutility.beans.ServizioStaticizzazioneBean;
import it.eng.module.foutility.beans.VerificaPdfBean;
import it.eng.utility.pdfUtility.services.StaticizzazionePdfXfaFormService;
import it.eng.utility.pdfUtility.services.client.XmlSpecificheBean;
import it.eng.utility.pdfUtility.services.impl.StaticizzazionePdfXfaFormEsternaServiceImpl;

public class StaticizzazioneEsternaUtil {

	public static final Logger log = LogManager.getLogger(StaticizzazioneEsternaUtil.class);
	
	public static boolean isAttivaIntegrazioneServizioStaticizzazione() {
		try {
			ApplicationContext context = FileoperationContextProvider.getApplicationContext();
			VerificaPdfBean formatoBean = (VerificaPdfBean) context.getBean("VerificaPdfBean");
			
			// INTEGRAZIONE SERVIZIO STATICIZZAZIONE = ATTIVA
			if("true".equalsIgnoreCase(formatoBean.getAttivaIntegrazioneServizioStaticizzazione())) {
				return true;
			} else {
				return false;
			}
		} catch (NoSuchBeanDefinitionException e) {
			//log.error(e);
			log.warn("Integrazione Servizio Staticizzazione non attiva, bean di configurazione non presente");
		} catch (Exception e1) {
			log.error(e1);
		} finally {
			
		}

		return false;
	}
	
	
	public static File richiamaServizio(File file) {
		log.debug("Richiamo il servizio esterno di staticizzazione ");
		File fileStaticizzato = null;

		try {
			ApplicationContext context = FileoperationContextProvider.getApplicationContext();
			ServizioStaticizzazioneBean servizioStaticizzazioneBean = (ServizioStaticizzazioneBean) context.getBean("ServizioStaticizzazioneBean");
			
			String endpoint = servizioStaticizzazioneBean.getEndpoint();
			String username = servizioStaticizzazioneBean.getUsername();
			String password = servizioStaticizzazioneBean.getPassword();
			Integer timeout = servizioStaticizzazioneBean.getTimeout();
			XmlSpecificheBean xmlSpecifiche=new XmlSpecificheBean();
			StaticizzazionePdfXfaFormService service = new StaticizzazionePdfXfaFormEsternaServiceImpl(
					endpoint, username, password, timeout
					);
			InputStream is = service.staticizzaFile(file, xmlSpecifiche);
			
			fileStaticizzato = File.createTempFile("input", ".pdf" , file.getParentFile());
		    FileUtils.copyInputStreamToFile(is, fileStaticizzato);
		} catch (IOException e) {
			fileStaticizzato = null;
			log.error("",e);
		}
		return fileStaticizzato;
	}
	
}
