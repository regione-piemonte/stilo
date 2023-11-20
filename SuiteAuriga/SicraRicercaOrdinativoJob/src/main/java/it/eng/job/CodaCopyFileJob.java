/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Clob;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.bean.DocumentConfiguration;
import it.eng.database.dao.TCodaCopyFileDAOImpl;
import it.eng.database.dao.TParametersDAOImpl;
import it.eng.database.utility.HibernateUtil;
import it.eng.entity.TCodaCopyFile;
import it.eng.job.SpringHelper;
import it.eng.job.util.StorageImplementation;
import it.eng.mail.bean.MailAddress;
import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;
import it.eng.utils.CopyFiles;

/**
 * XML di metadati e file da esportare su file-system di scambio con altri sistemi
 * 
 *
 */
@Job(type = "CodaCopyFileJob")
@Named
public class CodaCopyFileJob extends AbstractJob<String> {
	private static final String JOB_CLASS_NAME = CodaCopyFileJob.class.getName();
			
	private static final String JOBATTRKEY_ENTITY_PACKAGE = "entityPackage";
	private static final String JOBATTRKEY_HIBERNATE_CONFIG_FILE = "hibernateConfigFile";
	private static final String JOBATTRKEY_addressFrom = "addressFrom";
	private static final String JOBATTRKEY_destinatariCc = "destinatariCc";
	private static final String JOBATTRKEY_SCHEMA = "schema";
	private static final String JOBATTRKEY_LOCALE = "locale";
	private static final String JOBATTRKEY_TIPO_DOMINIO = "tipoDominio";
	private static final String JOBATTRKEY_DATE_FORMAT = "dateFormat";
    private static final Logger logger = LogManager.getLogger(CodaCopyFileJob.class);

    private ApplicationContext context;
    private AurigaLoginBean aurigaLoginBean;
    private String schema;
	private Locale locale;
	private DocumentConfiguration lDocumentConfiguration;
	private Integer tipoDominio;
	TCodaCopyFileDAOImpl TCodaCopyFileDAO = new TCodaCopyFileDAOImpl();
	TParametersDAOImpl tParametersDAOImpl = new TParametersDAOImpl();
	MailAddress indirizziMail = new MailAddress();
    List<String> addressCc = new ArrayList<String>();
    
	
	@Override
	public List<String> load() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.add(JOB_CLASS_NAME);
		
		return ret;
	}
	private Boolean configura() {

		Boolean valid = Boolean.TRUE;

		context = SpringHelper.getMainApplicationContext();
		schema = (String) getAttribute(JOBATTRKEY_SCHEMA);
		
		valid = schema != null && !schema.isEmpty();

		if (!valid) {
			logger.error("Schema non configurato");
			return valid;
		}

		aurigaLoginBean = new AurigaLoginBean();

		String localeValue = (String) getAttribute(JOBATTRKEY_LOCALE);

		valid = localeValue != null && !localeValue.isEmpty();

		if (!valid) {
			logger.error("Locale non specificato");
			return valid;
		}

		aurigaLoginBean.setLinguaApplicazione(localeValue);
		aurigaLoginBean.setSchema(schema);

		String tipoDominioValue = (String) getAttribute(JOBATTRKEY_TIPO_DOMINIO);

		valid = tipoDominioValue != null && !tipoDominioValue.isEmpty();

		if (valid) {

			tipoDominio = Integer.valueOf(tipoDominioValue);

			SpecializzazioneBean specializzazioneBean = new SpecializzazioneBean();
			specializzazioneBean.setTipoDominio(Integer.valueOf(tipoDominio));

			aurigaLoginBean.setSpecializzazioneBean(specializzazioneBean);

		} else {
			logger.error("Tipo dominio non specificato");
			return valid;
		}

		locale = new Locale(localeValue);

		String dateFormatValue = (String) getAttribute(JOBATTRKEY_DATE_FORMAT);

		valid = dateFormatValue != null && !dateFormatValue.isEmpty();

		if (!valid) {
			logger.error("Formato della data non specificato");
			return valid;
		}

		try {

			// solo effettuando una qualsiasi ricerca sullo storage posso
			// ricavare qualche informazioni se è correttamente configurato o
			// meno
			String test = StorageImplementation.getStorage().getConfigurazioniStorage();
			logger.info(String.format("Configurazione in uso %1$s", test));
		} catch (Exception e) {

			logger.error("Verificare i puntamenti al file di storage nell'aurigajob.xml, lo storage è nullo", e);

			valid = Boolean.FALSE;

		}


		return valid;
	}
	@Override
	protected void execute(String config) {

		logger.info("In esecuzione l'istanza {}");
		String entityPackage = (String) getAttribute(JOBATTRKEY_ENTITY_PACKAGE);
		String hibernateConfigFile = (String) getAttribute(JOBATTRKEY_HIBERNATE_CONFIG_FILE);
		HibernateUtil.setEntitypackage( entityPackage );
		SessionFactory sf = HibernateUtil.buildSessionFactory( hibernateConfigFile, JOB_CLASS_NAME );
		boolean validConfiguration = configura();
		try {
			// Inizio l'elaborazione solo se le configurazioni sono valide
			logger.debug("--- Elaborazione ---- ");
			if (sf == null)
			{
				logger.info("SessionFactory sf is null");
			}
			else
			{
				logger.info("SessionFactory sf is NOT null");
			 elabora();
			}
			logger.debug("--- Fine Elaborazione ---- ");
			
		} catch (Exception e) {
			logger.error("Exception : ", e);
		}
	}




	private void elabora() {

		logger.info(JOB_CLASS_NAME+" elabora - INIZIO");
		
		List<TCodaCopyFile> allTCodaCopyFile = new ArrayList<TCodaCopyFile>();
		TCodaCopyFile app = new TCodaCopyFile();
		allTCodaCopyFile = TCodaCopyFileDAO.getAllTCodaCopyFile(JOB_CLASS_NAME, "KO");
		logger.info("allTCodaCopyFile: " + allTCodaCopyFile.size());
		int siz = allTCodaCopyFile.size();
        
		aurigaLoginBean = new AurigaLoginBean();
		
		for (TCodaCopyFile rec : allTCodaCopyFile) {
			
			try {
/*				TParameters obj = new TParameters();
				obj.setParKey("T_CODA_X_EXPORT_" + fruitore + "_DEL");
				obj = tParametersDAOImpl.getTParameters(JOB_CLASS_NAME, obj);*/


					File extract = null;
				    
					logger.info("file da esportare da: "+rec.getFromExpPath());
					extract = StorageImplementation.getStorage().extractFile(rec.getFromExpPath());
					logger.info("extract: "+extract.getAbsolutePath());
					InputStream inputStream = new FileInputStream(extract);
					Path targetDir = Paths.get(rec.getToExpPath());
					Path targetFile = targetDir.resolve(rec.getExpFilename());
					logger.info("targetFile: "+targetFile);
					CopyFiles cp =  new CopyFiles();
					long appl=cp.copiaStream(inputStream, targetFile);
					logger.info("app: "+appl);
				//	String uri = StorageImplementation.getStorage().store(extract);
					
					
      			    Timestamp dataIns = new Timestamp(Calendar.getInstance().getTime().getTime());
					rec.setTsLastTry(dataIns);
					rec.setNumTry(rec.getNumTry().add(new BigDecimal(1)));
					rec.setEsitoElab("OK");
					rec = TCodaCopyFileDAO.update(JOB_CLASS_NAME, rec);
				    
					logger.info("Id. oggetto : " + rec.getIdOggetto() 
			           + " esportato: " + rec.getEsitoElab());
				

			} catch (Exception e) {
				try {
					rec.setEsitoElab("KO");
					Timestamp dataIns = new Timestamp(Calendar.getInstance().getTime().getTime());
					rec.setTsLastTry(dataIns);
					rec.setNumTry(rec.getNumTry().add(new BigDecimal(1)));
					rec = TCodaCopyFileDAO.updateError(JOB_CLASS_NAME, rec, e.getMessage());

				} catch (Exception e1) {
					logger.error("elabora - updateError:" + e1.getMessage());
				}
				logger.error("elabora - exc:" + e.getMessage());
			}
		} // chiudo for

    }
   

	// Fine recupero lista job
	@Override
	protected void end(String config) {
		HibernateUtil.closeConnection(JOB_CLASS_NAME);
		logger.info("Elaborazione completata");
	}
    
}
