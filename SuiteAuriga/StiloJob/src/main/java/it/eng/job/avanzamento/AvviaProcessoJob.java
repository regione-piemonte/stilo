/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.inject.Named;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.LobHelper;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiLoaddettudxguimodprotBean;
import it.eng.auriga.database.store.dmpk_repository_gui.store.impl.LoaddettudxguimodprotImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.client.ConsultazioneContabiliaImpl;
import it.eng.database.dao.DmtCodaProcWFToStartDAOImpl;
import it.eng.database.dao.TCodaOperConWfDAOImpl;
import it.eng.database.utility.HibernateUtil;
import it.eng.dm.engine.manage.EngineManager;
import it.eng.dm.engine.manage.bean.ActivitiProcess;
import it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest;
import it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.EseguiTaskInBean;
import it.eng.document.function.bean.EseguiTaskOutBean;
import it.eng.entity.ActHiActinst;
import it.eng.entity.DmtCodaProcWfToStart;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.job.avanzamento.bean.AvanzamentoConfig;
import it.eng.job.avanzamento.messages.MessageCodeEnum;
import it.eng.job.avanzamento.messages.MessageUtils;
import it.eng.job.avanzamento.wsclient.WebServicesUtils;
import it.eng.job.exception.AurigaDAOException;
import it.eng.job.exception.AurigaJobConfigurationException;
import it.eng.job.exception.AurigaJobException;
import it.eng.job.exception.StoreException;
import it.eng.simplesendermail.service.SimpleSenderMail;
import it.eng.simplesendermail.service.bean.MailToSendBean;
import it.eng.simplesendermail.service.bean.ResultSendMail;
import it.eng.simplesendermail.service.bean.SmtpSenderBean;
import it.eng.storeutil.AnalyzeResult;
import it.eng.utility.jobmanager.SpringAppContext;
import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;
import it.eng.utils.Contabilia;
import it.eng.xml.XmlUtilityDeserializer;

@Job(type = "AvviaProcessoJob")
@Named
public class AvviaProcessoJob extends AbstractJob<String> {

	private static Logger log = Logger.getLogger(AvviaProcessoJob.class);

	private static final String JOBATTRKEY_ENTITY_PACKAGE = "entityPackage";
	private static final String JOBATTRKEY_HIBERNATE_CONFIG_FILE = "hibernateConfigFile";
	private static final String SPRINGBEAN_JOB_CONFIG = "avanzamentoConfig";
	private static final String JOB_CLASS_NAME = AvviaProcessoJob.class.getName();

	private AvanzamentoConfig config;
	private ApplicationContext context;

	
	private static Locale locale;

	private String schema; 

	private AurigaLoginBean loginBean;
	
	@Override
	public List<String> load() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.add(JOB_CLASS_NAME);

		String entityPackage = (String) getAttribute(JOBATTRKEY_ENTITY_PACKAGE);
		String hibernateConfigFile = (String) getAttribute(JOBATTRKEY_HIBERNATE_CONFIG_FILE);
		HibernateUtil.setEntitypackage(entityPackage);
		HibernateUtil.buildSessionFactory(hibernateConfigFile, JOB_CLASS_NAME);

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
				invioSegnalazione(config.getJobName(),
						MessageUtils.getMessaggio(MessageCodeEnum.ERRORE_GRAVE_CONTROLLA_LOG) + "\n\n"
								+ messageWriter.toString(),
						null);
			}
		}
	}

	private void configura() throws AurigaJobConfigurationException {
		try {
			context = SpringAppContext.getContext();
			config = (AvanzamentoConfig) context.getBean(SPRINGBEAN_JOB_CONFIG);
			
			
			if (log.isDebugEnabled())
				log.debug("config : " + config);

			// try {
			// FileUtils.deleteDirectory(config.getTempDir());
			// } catch (Exception ioex) {
			// }
		} catch (Exception e) {
			throw new AurigaJobConfigurationException("Errore nella configurazione del job", e);
		}

	}

	private void elabora() throws Exception {
		AurigaLoginBean loginBean = new AurigaLoginBean();
		
		
		loginBean.setSchema(config.getSchema());
		SpecializzazioneBean spec = new SpecializzazioneBean();
		spec.setIdDominio(new BigDecimal(3));
		loginBean.setSpecializzazioneBean(spec);

		//FileWriter fileWriter = null;
		Session sessionJob = null;
		try {
			log.info("******************************************");
			log.info("0. Inizio elaborazione Avvia Processo Job");
			log.info("1. recupero IDs");
			DmtCodaProcWFToStartDAOImpl avanzamentoDAOImpl = new DmtCodaProcWFToStartDAOImpl();
			int numTryMax = config.getNumTryMax();
			sessionJob = HibernateUtil.openSession(JOB_CLASS_NAME);
			
			Map<String, List<DmtCodaProcWfToStart>> results1 = avanzamentoDAOImpl.searchAvanzamentoEsito("EL",sessionJob);
			log.info("1.1 Erano presenti in stato EL numero: "+results1.size());
			Map<String, List<DmtCodaProcWfToStart>> results = avanzamentoDAOImpl.searchAvanzamento(numTryMax,sessionJob);
			
			
				
				log.info("2.2 INIZIO Chiamata task");
				log.info("results1.size(): "+results1.size());
				results.putAll(results1);
				log.info("results.putAll(): "+results.size());
				
				Set<String> keys = results.keySet();
				boolean continua = false;
				for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					continua = false;
					
					
					{
						DmtCodaProcWfToStart tCodaOperConWfWf = null;
						
						List<DmtCodaProcWfToStart> listTCodaOperConWf = (List<DmtCodaProcWfToStart>) results.get(key);
						for (Iterator iterator2 = listTCodaOperConWf.iterator(); iterator2.hasNext();) {
							DmtCodaProcWfToStart tCodaOperConWf = (DmtCodaProcWfToStart) iterator2.next();
							
								tCodaOperConWfWf = tCodaOperConWf;
					

						}
						
							if (tCodaOperConWfWf != null) {

								try {
									log.info("tCodaOperConWfWf.getId().getTipoFlusso(): " + tCodaOperConWfWf.getTipoFlusso());
									String processInstanceId = avviaProcesso(tCodaOperConWfWf.getTipoFlusso());
									tCodaOperConWfWf.setEsitoElab("OK");
									tCodaOperConWfWf.setProcInstId(processInstanceId);
									ActHiActinst act = avanzamentoDAOImpl.getActHiActinst(JOB_CLASS_NAME,processInstanceId);
									tCodaOperConWfWf.setProcDefId(act.getProcDefId());
								} catch (Exception e) {
									LobHelper lobHelper = sessionJob.getLobHelper();
									Clob clob = lobHelper.createClob(e.getMessage());
									tCodaOperConWfWf.setErrMsg(clob);
									tCodaOperConWfWf.setEsitoElab("KO");
									log.error("4.2.ERR Chiamata NON inviata " + e.getMessage(), e);
								} finally {
									log.info("4.3 Tracciamneto chiamata ");
									tCodaOperConWfWf.setTryNum(tCodaOperConWfWf.getTryNum().add(new BigDecimal("1")));
									tCodaOperConWfWf.setTsStartProc(new Date());
									avanzamentoDAOImpl.updateAvanzamento(tCodaOperConWfWf,sessionJob);
								}	
						
								
							}//Chiudo //STEP:complete_task_wf
						}
				}
				log.info("5.1 FINE Caricamento");
			

		} catch (Exception e) {
			log.error("3.2 Caricamento interrotto; Errore generico", e);
			throw e;
		} finally {
				try {
					HibernateUtil.release(sessionJob);
				} catch (Exception e) {
					throw new AurigaDAOException(e);
				}
			
			log.info("4. Fine elaborazione Avanzamento");
			log.info("******************************************");
		}
	}

	public Vector<String> getValoriRiga(Riga r) {
		Vector<String> v = new Vector<String>();
		int oldNumCol = 0;
		for (int j = 0; j < r.getColonna().size(); j++) {
			// Aggiungo le colonne vuote
			for (int k = (oldNumCol + 1); k < r.getColonna().get(j).getNro().intValue(); k++)
				v.add(null);
			String content = r.getColonna().get(j).getContent();
			// aggiungo la colonna
			if (StringUtils.isNotBlank(content)) {
				try {
					SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(content));
					v.add(content);
				} catch (JAXBException e) {
					v.add(content.replace("\n", "<br/>"));
				}
			} else {
				v.add(null);
			}
			oldNumCol = r.getColonna().get(j).getNro().intValue(); // aggiorno
																	// l'ultimo
																	// numero di
																	// colonna
		}
		return v;
	}

	public String dateFolders() {
		Calendar cal = new GregorianCalendar();
		int minuto = cal.get(Calendar.MINUTE);
		int ora = cal.get(Calendar.HOUR_OF_DAY);
		int giorno = cal.get(Calendar.DAY_OF_MONTH);
		int mese = 1 + cal.get(Calendar.MONTH);
		int anno = cal.get(Calendar.YEAR);
		return anno + "/" + mese + "/" + giorno + "/" + ora + "/" + minuto + "/";
	}

	/**
	 * 
	 * @param corpo
	 * @param report
	 *            da inviare
	 */
	private void invioSegnalazione(String oggetto, String corpo, List<File> allegati) {
		log.info("Invio report per mail INIZIO");
		
		JavaMailSenderImpl mail = (JavaMailSenderImpl) context.getBean("mailSender");
		SmtpSenderBean sender = new SmtpSenderBean();
		MailToSendBean pMailToSendBean = new MailToSendBean();
		
		pMailToSendBean.setBody(corpo);
		pMailToSendBean.setSubject(oggetto);
		
		sender.setSmtpEndpoint(mail.getHost());
		sender.setSmtpPort(mail.getPort());
		sender.setUsernameAccount(mail.getUsername());
		
		pMailToSendBean.setSmptSenderBean(sender);
	//	pMailToSendBean.setAddressTo(config.getIndirizziMailSegnalazioni().getAddressTo());
		
		ResultSendMail lResultSendMail = new SimpleSenderMail().simpleSendMail(pMailToSendBean);
		log.info("lResultSendMail "+lResultSendMail.isInviata());
		log.info("Invio report per mail FINE");
	}

	@Override
	public void end(String obj) {
		HibernateUtil.closeConnection(JOB_CLASS_NAME);
		log.info("Elaborazione completata");
	}
	
	public static void main (String args[]){
		GregorianCalendar calPubblicazione = new GregorianCalendar();
		calPubblicazione.setTime(new Date());// lDocumentoXmlOutBean.getDataInizioPubbl());
		System.out.println("Data esposizione inizio: " + calPubblicazione.getTime());

		calPubblicazione.add(Calendar.DAY_OF_YEAR, 15);// lDocumentoXmlOutBean.getGiorniDurataPubbl()));
		System.out.println("Data esposizione fine: " + calPubblicazione.getTime());
	}
	public String avviaProcesso(String flowTypeId) throws Exception{		
		try{
			EngineManager lEngineManager = new EngineManager();
			log.debug("EngineManager.startProcess()");
			long start = new Date().getTime();			
			String processInstanceId = lEngineManager.startProcess(flowTypeId);
			long end = new Date().getTime();			
			log.debug("Eseguito in " + (end - start) + " ms");
			log.debug("Procedimento avviato con processInstanceId: " + processInstanceId );
			return processInstanceId;
		} catch( Throwable t){
			log.error("Errore ", t);
			throw new Exception("Errore nella creazione del processo activity");
		}
	}
	public static List<ActivitiProcess> getProcessi(String flowTypeId){		
		try{
			EngineManager lEngineManager = new EngineManager();
			log.debug("EngineManager.getListaProcessi()");
			long start = new Date().getTime();			
			List<ActivitiProcess> processes = lEngineManager.getListaProcessi(flowTypeId);
			long end = new Date().getTime();			
			log.debug("Eseguito in " + (end - start) + " ms");
			
			return processes;
		} catch( Throwable t){
			log.error("Errore ", t);
			return null;
		}
	}

}