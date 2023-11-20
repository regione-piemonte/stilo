/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.inject.Named;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import it.eng.bean.ProvvedimentoTrasparenzaBean;
import it.eng.database.dao.TCodaTrasparenzaDAOImpl;
import it.eng.database.utility.HibernateUtil;
import it.eng.entity.TCodaXExportTrasp;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.job.exception.AurigaDAOException;
import it.eng.job.exception.AurigaJobConfigurationException;
import it.eng.job.trasparenza.bean.FruitoreEnum;
import it.eng.job.trasparenza.bean.TrasparenzaConfig;
import it.eng.simplesendermail.service.SimpleSenderMail;
import it.eng.simplesendermail.service.bean.MailToSendBean;
import it.eng.simplesendermail.service.bean.ResultSendMail;
import it.eng.simplesendermail.service.bean.SmtpSenderBean;
import it.eng.utility.jobmanager.SpringAppContext;
import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;
import it.eng.utils.Trasparenza;
import it.eng.utils.XmlUtility;
import it.eng.utils.bean.ProvvedimentoExport;

@Job(type = "TrasparenzaJob")
@Named
public class TrasparenzaJob extends AbstractJob<String> {

	private static Logger log = Logger.getLogger(TrasparenzaJob.class);

	private static final String JOBATTRKEY_ENTITY_PACKAGE = "entityPackage";
	private static final String JOBATTRKEY_HIBERNATE_CONFIG_FILE = "hibernateConfigFile";
	private static final String SPRINGBEAN_JOB_CONFIG = "trasparenzaConfig";
	private static final String JOB_CLASS_NAME = TrasparenzaJob.class.getName();

	private TrasparenzaConfig config;
	private ApplicationContext context;
	
	@Override
	public List<String> load() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.add(JOB_CLASS_NAME);

		String entityPackage = (String) getAttribute(JOBATTRKEY_ENTITY_PACKAGE);
		String hibernateConfigFile = (String) getAttribute(JOBATTRKEY_HIBERNATE_CONFIG_FILE);
		String numMax = (String) getAttribute("numTryMax");
		HibernateUtil.setEntitypackage("it.eng.entity");
		HibernateUtil.buildSessionFactory("hibernate.cfg.xml", JOB_CLASS_NAME);
		
		return ret;
	}

	@Override
	public void execute(String obj) {

		Exception exTrace = null;
		try {
			configura();

			elabora();
		} catch (AurigaJobConfigurationException e) {
			log.error("Elaborazione non effettuata.", e);
			exTrace = e;
		} catch (Exception e) {
			log.error("Errore generico nell'execute", e);
			exTrace = e;
		} finally {
			if (exTrace != null) {
				StringWriter messageWriter = new StringWriter();
				exTrace.printStackTrace(new PrintWriter(messageWriter));
				/*
				invioSegnalazione(config.getJobName(),
						MessageUtils.getMessaggio(MessageCodeEnum.ERRORE_GRAVE_CONTROLLA_LOG) + "\n\n"
								+ messageWriter.toString(),
						null);
				*/
			}
		}
	}
	
	private void configura() throws AurigaJobConfigurationException {
		try {
			context = SpringAppContext.getContext();
			config = (TrasparenzaConfig) context.getBean(SPRINGBEAN_JOB_CONFIG);
			
			if (log.isDebugEnabled())
				log.debug("config : " + config);
		} catch (Exception e) {
			throw new AurigaJobConfigurationException("Errore nella configurazione del job", e);
		}

	}

	private void elabora() throws Exception {
		//AurigaLoginBean loginBean = new AurigaLoginBean();
		
		//loginBean.setSchema(config.getSchema());
		//SpecializzazioneBean spec = new SpecializzazioneBean();
		//spec.setIdDominio(new BigDecimal(3));
		//loginBean.setSpecializzazioneBean(spec);
		
		Session sessionJob = null;
		int numRecordElaborati = 0;
		try {
			log.info("******************************************");
			log.info("0. Inizio elaborazione Trasparenza");
			log.info("1. recupero IDs");
			
			String hibernateConfigFile1 = (String) getAttribute(JOBATTRKEY_HIBERNATE_CONFIG_FILE);
			
			int numTryMax = config.getNumTryMax();
			sessionJob = HibernateUtil.openSession(JOB_CLASS_NAME);
			
			TCodaTrasparenzaDAOImpl trasparenzaDAOImpl = new TCodaTrasparenzaDAOImpl();
			List<TCodaXExportTrasp> codaTraspList = trasparenzaDAOImpl.getCodaExportTrasparenza(FruitoreEnum.ATTITRASP.getDescription(), numTryMax, sessionJob);
			numRecordElaborati = codaTraspList.size();
			
			log.info("2. record da elaborare " + numRecordElaborati);
			if (numRecordElaborati > 0) {
				Trasparenza trasparenza = new Trasparenza();
				
				for (TCodaXExportTrasp item : codaTraspList) {
					TCodaXExportTrasp tCodaXExportTraspUpd = new TCodaXExportTrasp();
					
					try {
						tCodaXExportTraspUpd = item;
						if (item.getOggDaEsportare() != null && !item.getOggDaEsportare().equals("")) {
							log.info("3. unmarshal xml " + item.getOggDaEsportare());
							
							// acuisizione da xml dati trasparenza da passare al WS clearo
							ProvvedimentoExport provExp = XmlUtility.xmlToBean(item.getOggDaEsportare());
							
							if (provExp != null) {
								ProvvedimentoTrasparenzaBean provvedimentoTrasparenzaBean = new ProvvedimentoTrasparenzaBean();
								provvedimentoTrasparenzaBean.setAnno(provExp.getAnno());
								provvedimentoTrasparenzaBean.setOggetto(provExp.getOggetto());
								provvedimentoTrasparenzaBean.setSemestre(provExp.getSemestre());
								provvedimentoTrasparenzaBean.setNumProv(provExp.getNum_Provvedimento());
								provvedimentoTrasparenzaBean.setDataProv(provExp.getData_Provvedimento());
								provvedimentoTrasparenzaBean.setFlagConcessione(provExp.getTipo_Provvedimento());
								
								log.info("4. parametri input per WS Clearo " + provvedimentoTrasparenzaBean.toString());
								
								// chiamata WS clearo per salvataggio dati provvedimento
								provvedimentoTrasparenzaBean = trasparenza.aggiungiRecordTrasparenza(provvedimentoTrasparenzaBean);
								
								if (provvedimentoTrasparenzaBean.getEsitoAddRecordTrasparenza().equals("OK")) {
									log.info("5. WS Clearo eseguito correttamente"); 
									
									tCodaXExportTraspUpd.setEsitoElab(provvedimentoTrasparenzaBean.getEsitoAddRecordTrasparenza());
									tCodaXExportTraspUpd.setErrCode(null);
									tCodaXExportTraspUpd.setErrMsg(null);
								}
								else {
									log.info("5. Errore nel WS Clearo  " + provvedimentoTrasparenzaBean.getErrorMsgAddRecordTrasparenza());
									
									tCodaXExportTraspUpd.setEsitoElab(provvedimentoTrasparenzaBean.getEsitoAddRecordTrasparenza());
									tCodaXExportTraspUpd.setErrCode(new BigDecimal("1"));
									tCodaXExportTraspUpd.setErrMsg(provvedimentoTrasparenzaBean.getErrorMsgAddRecordTrasparenza());
								}
							}
						}
					} catch (Exception e) {
						tCodaXExportTraspUpd.setEsitoElab("KO");
						tCodaXExportTraspUpd.setErrCode(new BigDecimal("1"));
						tCodaXExportTraspUpd.setErrMsg(e.getMessage());
						
						log.error(e.getMessage());
					} finally {
						log.info("6. Tracciamento esito chiamata WS ");
						
						tCodaXExportTraspUpd.setNumTry(item.getNumTry().add(new BigDecimal("1")));
						tCodaXExportTraspUpd.setTsLastTry(new Date());
						trasparenzaDAOImpl.updateTrasparenza(tCodaXExportTraspUpd, sessionJob);
						
						log.info("7. Aggiornato record con  esito e numero tentativi");
					}
				}	
			}
			
		} catch (Exception e) {
			log.error("Caricamento interrotto; Errore generico", e);
			throw e;
		} finally {
				try {
					HibernateUtil.release(sessionJob);
				} catch (Exception e) {
					throw new AurigaDAOException(e);
				}
			
			log.info("8. Fine job Trasparenza: elaborati " + numRecordElaborati + " record");
			log.info("******************************************");
		}
	}

	@Override
	public void end(String obj) {
		HibernateUtil.closeConnection(JOB_CLASS_NAME);
		log.info("Elaborazione completata");
	}

}