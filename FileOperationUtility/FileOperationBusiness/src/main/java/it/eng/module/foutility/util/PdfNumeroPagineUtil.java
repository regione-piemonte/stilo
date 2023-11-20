/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import it.eng.module.foutility.beans.VerificaPdfBean;

public class PdfNumeroPagineUtil {

	public static final Logger logger = LogManager.getLogger(PdfNumeroPagineUtil.class);
	
	public static boolean isAttivaVerificaPdfNumeriPagina() {
		try {
			ApplicationContext context = FileoperationContextProvider.getApplicationContext();
			VerificaPdfBean formatoBean = (VerificaPdfBean) context.getBean("VerificaPdfBean");
			
			// VERIFICA FORMATO PDF CON COMMENTI = ATTIVA
			if("true".equalsIgnoreCase(formatoBean.getAttivaVerificaNumeroPagine())) {
				return true;
			} else {
				return false;
			}
		} catch (NoSuchBeanDefinitionException e) {
			//log.error(e);
			logger.warn("Verifica pdf numero pagine non attiva, bean di configurazione non presente");
		} catch (Exception e1) {
			logger.error(e1);
		} finally {
			
		}

		return false;
	}
	
	

	
}
