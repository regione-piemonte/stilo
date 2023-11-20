/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.converter.DateConverter;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.jobmanager.quartz.Job;
import it.eng.utility.jobmanager.quartz.config.JobManager;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.oomanager.OpenOfficeConverter;
import it.eng.utility.oomanager.config.OpenOfficeConfiguration;
import it.eng.utility.oomanager.exception.OpenOfficeException;
import it.eng.utility.ui.servlet.ServletInitialize;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.log4j.Logger;
import org.reflections.Reflections;

import com.itextpdf.text.pdf.PdfReader;


/**
 * Servlet di init per istanziare il server ADempiere
 * @author michele
 *
 */
public class AurigaServletInitialize extends ServletInitialize {

	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(AurigaServletInitialize.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);		
		
		SubjectBean subject = new SubjectBean();
		subject.setIdDominio("ente1");
		subject.setIduser("ADMIN");
		SubjectUtil.subject.set(subject);
		
//		 BusinessInit lBusinessInit = new BusinessInit();
//		    lBusinessInit.configure();
		
		//Inizializzo il business client	
//		Configuration.getInstance().setUrl("http://localhost:8080/AurigaBusiness");
//		Configuration.getInstance().setBusinesstype(BusinessType.REST);		
//		Configuration.getInstance().setSerializationtype(SerializationType.JSON);
		
//		try {
//			Configuration.getInstance().initClient();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			throw new ServletException(e.getMessage(), e);
//		}
		
		//Recupero il jobmanager e setto i job
		try {
			JobManager manager = (JobManager)SpringAppContext.getContext().getBean("jobmanager");

			HashMap<String, Class<?>> jobclassesmap = new HashMap<String, Class<?>>();		

			Reflections reflections = new Reflections("it.eng.auriga.job");
			Set<Class<?>> jobclasses = reflections.getTypesAnnotatedWith(Job.class);

			for(Class classe : jobclasses){
				String jobtype = ((Job) classe.getAnnotation(Job.class)).type();		  				
				jobclassesmap.put(jobtype, classe);	
			}

			manager.initialize(jobclassesmap);
		} catch (Exception e) {
			logger.error("Errore inizializzazione job", e);
		}			
		
		StorageImplementation.init();
		
		ConvertUtils.register(new DateConverter(), Date.class);    // Native type
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
			
		try {
			OpenOfficeConfiguration lOpenOfficeConfiguration = (OpenOfficeConfiguration)SpringAppContext.getContext().getBean("officemanager");
			OpenOfficeConverter.configure(lOpenOfficeConfiguration);
		} catch (OpenOfficeException e) {
			// TODO Auto-generated catch block
			logger.error("Errore configurazione OpenOfficeConverter", e);
		}
		
		//Inizializzo il PdfReader, settandogli la proprietà per poter leggere tutti i pdf protetti da password in modifica
		// (che davano problemi in lettura e quindi non permettevano operazioni come timbratura, unione ecc)
		PdfReader.unethicalreading = true;
		
//		try {
//			File fileChatServerNodeJS = new File(URLDecoder.decode(getClass().getClassLoader().getResource("chatserver.js").getFile(), "UTF-8"));
//			Runtime rt = Runtime.getRuntime(); 
//			String[] cmd = { "C:/Program Files/nodejs/node", fileChatServerNodeJS.getPath()};
//			Process proc = rt.exec(cmd);			 			
//		} catch (Exception e) {
//			logger.error("Errore inizializzazione server chat", e);
//		}	
		 
	    logger.info("Application GUI started successfully");
	}
}
