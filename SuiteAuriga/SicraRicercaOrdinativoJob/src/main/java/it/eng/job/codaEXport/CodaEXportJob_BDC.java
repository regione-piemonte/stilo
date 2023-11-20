/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;
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
import org.w3c.dom.Document;

import it.eng.database.dao.TCodaXExportDAOImpl;
import it.eng.database.dao.TParametersDAOImpl;
import it.eng.database.utility.HibernateUtil;
import it.eng.entity.TCodaXExport;
import it.eng.entity.TParameters;
import it.eng.job.codaEXport.constants.ErrorInfoEnum;
import it.eng.job.codaEXport.util.TCodaXExportUtils;
import it.eng.mail.bean.MailAddress;
//import it.eng.mail.bean.SimpleMailSender;
import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;

/**
 * XML di metadati e file da esportare su file-system di scambio con altri sistemi
 * 
 *
 */
@Job(type = "CodaEXportJob_BDC")
@Named
public class CodaEXportJob_BDC extends AbstractJob<String> {
	private static final String fruitore="BDC";
	private static final String JOB_CLASS_NAME = CodaEXportJob_BDC.class.getName();
			
	private static final String JOBATTRKEY_ENTITY_PACKAGE = "entityPackage";
	private static final String JOBATTRKEY_HIBERNATE_CONFIG_FILE = "hibernateConfigFile";
	private static final String JOBATTRKEY_addressFrom = "addressFrom";
	private static final String JOBATTRKEY_destinatariCc = "destinatariCc";
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	
	
	TCodaXExportDAOImpl tCodaXExportDAO = new TCodaXExportDAOImpl();
	TParametersDAOImpl tParametersDAOImpl = new TParametersDAOImpl();
	//SimpleMailSender   simpleMailSender = new SimpleMailSender();
	TCodaXExportUtils tCodaXExportUtils = new TCodaXExportUtils();
	MailAddress indirizziMail = new MailAddress();
    List<String> addressCc = new ArrayList<String>();
    
	
	@Override
	public List<String> load() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.add(JOB_CLASS_NAME);
		
		return ret;
	}
	
	@Override
	protected void execute(String config) {

		logger.info("In esecuzione l'istanza {}");
		String entityPackage = (String) getAttribute(JOBATTRKEY_ENTITY_PACKAGE);
		String hibernateConfigFile = (String) getAttribute(JOBATTRKEY_HIBERNATE_CONFIG_FILE);
		HibernateUtil.setEntitypackage( entityPackage );
		SessionFactory sf = HibernateUtil.buildSessionFactory( hibernateConfigFile, JOB_CLASS_NAME );
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
			logger.error(ErrorInfoEnum.ERRORE_ELABORAZIONE_JOB.getDescription(), e);
		}
	}





	private void elabora() {

		logger.info(JOB_CLASS_NAME+" elabora - INIZIO");
		String addressFrom = (String) getAttribute(JOBATTRKEY_addressFrom);
	    indirizziMail.setAddressFrom(addressFrom);
        String destinatariCc = (String) getAttribute(JOBATTRKEY_destinatariCc);
	    addressCc.add(destinatariCc);
		indirizziMail.setAddressCc(addressCc);
		List<TCodaXExport> allTCodaXExport = new ArrayList<TCodaXExport>();
		TCodaXExport app = new TCodaXExport();
		app.setFruitore(fruitore);
		allTCodaXExport = tCodaXExportDAO.getAllTCodaXExport(JOB_CLASS_NAME, "KO", fruitore);
		logger.info("allTCodaXExport: " + allTCodaXExport.size());
		int siz = allTCodaXExport.size();

		for (TCodaXExport rec : allTCodaXExport) {
			if (rec.getDipendeDaExpOgg() != null) {
				TCodaXExport dip = new TCodaXExport();
				dip.setIdOggetto(rec.getDipendeDaExpOgg());
				dip = tCodaXExportDAO.getTCodaXExport(JOB_CLASS_NAME, dip);
				logger.info("Id. di altro oggetto : " + dip.getIdOggetto() + " esito:" + dip.getEsitoElab());
				if (dip.getEsitoElab().equals("KO")) {
					Timestamp dataIns = new Timestamp(Calendar.getInstance().getTime().getTime());
					rec.setTsLastTry(dataIns);
					rec.setNumTry(rec.getNumTry().add(new BigDecimal(1)));
					rec.setEsitoElab("KO");
					try {
						rec = tCodaXExportDAO.update(JOB_CLASS_NAME,indirizziMail, rec);
					} catch (Exception e) {
						try {
							
							rec = tCodaXExportDAO.updateError(JOB_CLASS_NAME,indirizziMail, rec, e.getMessage());
							//simpleMailSender.mailErroreCaricamento(JOB_CLASS_NAME,
							//		                               indirizziMail, 
							//		                               rec, 
							//		                               e.getMessage());

						} catch (Exception e1) {
							logger.error("elabora - updateError:" + e1.getMessage());
						}
						logger.error("elabora - exc:" + e.getMessage());
					}
					continue;
				}
			} // chiudo if
			try {
				TParameters obj = new TParameters();
				obj.setParKey("T_CODA_X_EXPORT_" + fruitore + "_DEL");
				obj = tParametersDAOImpl.getTParameters(JOB_CLASS_NAME, obj);

				if (obj.getNumValue() != null && obj.getNumValue().intValue() > 0) {

					rec=tCodaXExportUtils.esportaFile(JOB_CLASS_NAME,
                            indirizziMail,
                            rec);
					if (rec.getEsitoElab().equals("OK"))
					{	
					 boolean res = tCodaXExportDAO.delete(JOB_CLASS_NAME, rec);
					 logger.info("Id. oggetto : " + rec.getIdOggetto() 
					           + " cancellato: " + res);
					}

				} else {
					
					rec=tCodaXExportUtils.esportaFile(JOB_CLASS_NAME,
                                                      indirizziMail,
                                                      rec);
				    
					
					Timestamp dataIns = new Timestamp(Calendar.getInstance().getTime().getTime());
					rec.setTsLastTry(dataIns);
					rec.setNumTry(rec.getNumTry().add(new BigDecimal(1)));
					rec = tCodaXExportDAO.update(JOB_CLASS_NAME,indirizziMail, rec);
				    
					logger.info("Id. oggetto : " + rec.getIdOggetto() 
			           + " esportato: " + rec.getEsitoElab());
				}

			} catch (Exception e) {
				try {
					rec.setEsitoElab("KO");
					Timestamp dataIns = new Timestamp(Calendar.getInstance().getTime().getTime());
					rec.setTsLastTry(dataIns);
					rec = tCodaXExportDAO.updateError(JOB_CLASS_NAME,indirizziMail, rec, e.getMessage());
					//simpleMailSender.mailErroreCaricamento(JOB_CLASS_NAME,
					//		                               indirizziMail, 
					//		                               rec, 
					//		                               e.getMessage());

				} catch (Exception e1) {
					logger.error("elabora - updateError:" + e1.getMessage());
				}
				logger.error("elabora - exc:" + e.getMessage());
			}
		} // chiudo for

		TParameters obj = new TParameters();
		obj.setParKey("T_CODA_X_EXPORT_INS");
		obj = tParametersDAOImpl.getTParameters(JOB_CLASS_NAME, obj);

		if (obj!= null && obj.getNumValue() != null && obj.getNumValue().intValue() > 0) {

			tCodaXExportUtils.caricaFile(JOB_CLASS_NAME, indirizziMail, app);
		}
		//simpleMailSender.mailCaricamento(JOB_CLASS_NAME,indirizziMail, app, siz);
		logger.info(JOB_CLASS_NAME+" elabora - FINE");

	}
   

	// Fine recupero lista job
	@Override
	protected void end(String config) {
		HibernateUtil.closeConnection(JOB_CLASS_NAME);
		logger.info("Elaborazione completata");
	}

}
