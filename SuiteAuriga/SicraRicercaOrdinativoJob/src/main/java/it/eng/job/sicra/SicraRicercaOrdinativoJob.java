/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Named;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.JAXBElement;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddverdocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdverdocBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.AdddocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.AddverdocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpddocudImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpdverdocImpl;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.dao.beans.JobBean;
import it.eng.auriga.module.business.dao.beans.JobParameterBean;
import it.eng.bean.AllegatoStoreBean;
import it.eng.bean.ExecutionResultBean;
import it.eng.client.DaoBmtJobParameters;
import it.eng.client.DaoBmtJobs;
import it.eng.client.DmpkLoginLogin;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TOrderBy;
import it.eng.core.business.TOrderBy.OrderByType;
import it.eng.database.utility.HibernateUtil;
import it.eng.core.business.TPagingList;
import it.eng.document.function.bean.AllegatoDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.DatiContabiliADSPXmlBean;
import it.eng.document.function.bean.FileStoreBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.entity.BmtJobParameters;
import it.eng.entity.BmtJobParametersId;
import it.eng.entity.BmtJobs;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.job.SpringHelper;
import it.eng.job.avanzamento.bean.AvanzamentoConfig;
import it.eng.job.esportazioneAsincrona.bean.JobGeneralConfigurationBean;
import it.eng.job.exception.AurigaDAOException;
import it.eng.job.exception.DocumentUtilException;
import it.eng.job.exception.StoreException;
import it.eng.job.util.StorageImplementation;
import it.eng.simplesendermail.service.bean.MailToSendBean;
import it.eng.simplesendermail.service.bean.SmtpSenderBean;
import it.eng.storeutil.AnalyzeResult;
import it.eng.utility.data.Output;
import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;
import it.eng.utility.sicra.contabilita.ClientSpringSicra;
import it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Richiesta;
import it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato;
import it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione;
import it.eng.utility.springBeanWrapper.bean.SpringBeanWrapperConfigBean;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utils.AurigaFileUtils;
import it.eng.utils.DocumentStorage;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;
/**
 * 
 *
 */
@Job(type = "SicraRicercaOrdinativoJob")
@Named
public class SicraRicercaOrdinativoJob extends AbstractJob<String> {

	private static final String JOBATTRKEY_SCHEMA = "schema";
	private static final String JOBATTRKEY_LOCALE = "locale";
	private static final String JOBATTRKEY_DATE_FORMAT = "dateFormat";
	private static final String JOBATTRKEY_TIPO_DOMINIO = "tipoDominio";
	private static final String JOBATTRKEY_ENTITY_PACKAGE = "entityPackage";
	private static final String JOBATTRKEY_HIBERNATE_CONFIG_FILE = "hibernateConfigFile";
	private static final String JOBATTRKEY_TOKEN = "token";
	
	private Logger logger = Logger.getLogger(SicraRicercaOrdinativoJob.class);

	private static final String JOB_CLASS_NAME = "JobBatch";

	private ApplicationContext context;
	private AvanzamentoConfig config;
	private AurigaLoginBean aurigaLoginBean;

	private String schema;
	private Locale locale;
//	private DocumentConfiguration lDocumentConfiguration;
	private Integer tipoDominio;
	protected DaoBmtJobParameters bmtJobParametersClient;
    protected DaoBmtJobs bmtJobsClient;
    protected it.eng.database.dao.DaoBmtJobs daoBmtJobsClient = new it.eng.database.dao.DaoBmtJobs();
    protected JobGeneralConfigurationBean jobGeneralConfiguration;

	@Override
	public List<String> load() {

		ArrayList<String> ret = new ArrayList<String>();
		ret.add(JOB_CLASS_NAME);

		return ret;
	}

	@Override
	public void execute(String schema) {

		boolean validConfiguration = configura();
		String entityPackage = (String) getAttribute(JOBATTRKEY_ENTITY_PACKAGE);
		String hibernateConfigFile = (String) getAttribute(JOBATTRKEY_HIBERNATE_CONFIG_FILE);
		HibernateUtil.setEntitypackage(entityPackage);
		SessionFactory sf = HibernateUtil.buildSessionFactory(hibernateConfigFile, JOB_CLASS_NAME);
		logger.debug("--- Elaborazione ---- ");
		if (sf == null) {
			logger.info("SessionFactory sf is null");
		} else {
			logger.info("SessionFactory sf is NOT null");
			try {
				elabora();
			} catch (Exception e) {
				logger.error("Exception: " + e.getMessage());

			}
		}
		logger.debug("--- Fine Elaborazione ---- ");
	}

	protected void elabora() throws Exception {
        
		Session sessionJob = null;
		String token = (String) getAttribute(JOBATTRKEY_TOKEN);
		try {
			ExecutionResultBean<TPagingList<JobBean>> result = retrieveJobs();	
			
		if (result.isSuccessful()) {
			
			
			sessionJob = it.eng.database.utility.HibernateUtil.begin(JOB_CLASS_NAME);
			
			it.eng.database.dao.DaoBmtJobParameters jb = new it.eng.database.dao.DaoBmtJobParameters();
			
			TPagingList<JobBean> results = result.getResult();

			logger.info(String.format("Sono stati trovati %1$s job da esportare", results.getData().size()));

            // per ogni job trovato effettuo la generazione del file di
			// esportazione
			for (JobBean currentJob : results.getData()) {
                
				
				// esportazione è in stato E
				if (currentJob.getIdJob()!= null) {

					logger.info("Iniziata elaborazione del job " + currentJob.getIdJob());
					
					String idDoc = "";
					// aggiorno il login bean con i dati del job corrente
					try {

					//	updateLoginBeanSpaoo(currentJob);

					} catch (Exception e) {

						// poichè si tratta di un errore di autenticazione, mi
						// aspetto sia successo per problemi nel server, quindi
						// non
						// pongo il job in stato di errore
						logger.error(
								String.format("Durante l'autenticazione si è verificata la seguente eccezione %1$s",
										ExceptionUtils.getFullStackTrace(e)));
						continue;
					}
					
					try {
							markJobAsInit(currentJob);
						} catch (Exception e2) {
							currentJob.setMessage("Errore: "+e2.getMessage());
							try {
								markJobAsInError(currentJob);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								logger.error("Errore: "+e.getMessage());
							}
						}
					
					JobParameterBean filterBean = new JobParameterBean();
					filterBean.setIdJob(currentJob.getIdJob());
					filterBean.setParameterSubtype("NRO_ATTO");
                    TFilterFetch<JobParameterBean> filter = new TFilterFetch<JobParameterBean>();
					filter.setFilter(filterBean);
					
					
					JobParameterBean filterBean1 = new JobParameterBean();
					filterBean1.setIdJob(currentJob.getIdJob());
					filterBean1.setParameterSubtype("ANNO_ATTO");
                    TFilterFetch<JobParameterBean> filter1 = new TFilterFetch<JobParameterBean>();
					filter1.setFilter(filterBean1);
					
					JobParameterBean filterBean2 = new JobParameterBean();
					filterBean2.setIdJob(currentJob.getIdJob());
					filterBean2.setParameterSubtype("SIGLA_ATTO");
                    TFilterFetch<JobParameterBean> filter2 = new TFilterFetch<JobParameterBean>();
					filter2.setFilter(filterBean2);
					
					JobParameterBean filterBean3 = new JobParameterBean();
					filterBean3.setIdJob(currentJob.getIdJob());
					filterBean3.setParameterSubtype("ID_DOC_ATTO");
                    TFilterFetch<JobParameterBean> filter3 = new TFilterFetch<JobParameterBean>();
					filter3.setFilter(filterBean3);
                    
					it.eng.entity.BmtJobParameters parameter = new BmtJobParameters();
					try {
                        
						
						it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Richiesta ricLiq = new it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Richiesta(); 
                        
						
						
						it.eng.entity.BmtJobParameters parameter1 = jb.searchEntity(filter1);

						if (parameter1 != null && parameter1.getParameterValue() != null && !"".equals(parameter1.getParameterValue() ))
						{
							logger.debug("ANNO_ATTO: "+parameter1.getParameterValue());
							ricLiq.setAnnoAtto(new BigInteger(parameter1.getParameterValue()));
						}
						
						it.eng.entity.BmtJobParameters parameter2 = jb.searchEntity(filter2);

						if (parameter2 != null && parameter2.getParameterValue() != null && !"".equals(parameter2.getParameterValue()))
						{
							logger.debug("SIGLA_ATTO: "+parameter2.getParameterValue());
							ricLiq.setSiglaTipoAtto(parameter2.getParameterValue());
						}
						
						it.eng.entity.BmtJobParameters parameter3 = jb.searchEntity(filter3);

						if (parameter3 != null && parameter3.getParameterValue() != null && !"".equals(parameter3.getParameterValue()))
						{
							logger.debug("ID_DOC_ATTO: "+parameter3.getParameterValue());
							idDoc=parameter3.getParameterValue();
					    }
                        
						logger.info(String.format("Il job %1$s è stato caricato in con successo ",currentJob.getIdJob()));
                        
						parameter = jb.searchEntity(filter);

						if (parameter != null && parameter.getParameterValue() != null && !"".equals(parameter.getParameterValue()))
						{
							logger.debug("NRO_ATTO: "+parameter.getParameterValue());
							ricLiq.setNumeroAtto(new BigInteger(parameter.getParameterValue()));
					        
							logger.info("Inizio chiamata al WS - NRO_ATTO: "+ricLiq.getNumeroAtto());
							logger.info("Inizio chiamata al WS : "+ricLiq.toString());
							Output<it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato> output = ClientSpringSicra.getClient().ricercaOrdinativoAttoLiquidazione(ricLiq);
							
							if (output.getOutcome().isOk()) {
								logger.info("Inizio chiamata al WS - OK : "+output.getOutcome().isOk());
								logger.info("Inizio chiamata al WS - OK descrizione: "+output.getData().getEsito().getDescrizione());
								logger.info("Inizio chiamata al WS - OK descrizione: "+output.getData());
								logger.info("Inizio chiamata al WS - OK descrizione: "+output.getData().getLiquidazione());
								it.eng.entity.BmtJobParameters parameterE = new BmtJobParameters();
								BmtJobParametersId id = new BmtJobParametersId();
								id.setIdJob(currentJob.getIdJob());

								try {
									id.setParameterId(new BigDecimal("5"));
									outParameters(id, "OK", "OUT", "VARCHAR2", "ESITO_STRINGA");
									id.setParameterId(new BigDecimal("6"));
									outParameters(id, output.getData().getEsito().getDescrizione(), "OUT", "CLOB", "MSG_OUTPUT");
									currentJob.setMessage("Messaggio: " + output.getData().getEsito().getDescrizione());
									
								} catch (Exception e1) {
									logger.error(String.format("Non è stato possibile porre in stato X il job %1$s",
											currentJob.getIdJob()));
								}
								aggiornaDatiContabilia(sessionJob,  token, idDoc,output.getData());
							} else {
								logger.info("Inizio chiamata al WS - KO : "+output.getOutcome().getMessaggio());
								/*it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato rs = new it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato();
								
								it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione res = new it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione();
								res.setCodice(347483);
								res.setAnno(new BigInteger("2021"));
								res.setCodiceAnnuale(4334);
							
								rs.getLiquidazione().add(res);
								aggiornaDatiContabilia(sessionJob,  token, idDoc,rs);*/
							     throw new Exception(output.getOutcome().getMessaggio());
								//aggiornaDatiContabilia(sessionJob,  token, idDoc,rs);
							}
							
							
							
							
							
						}	
						
						
						markJobAsCompleted(currentJob);

					} catch (Exception e) {
							logger.error("Errore: " + e.getMessage());
							it.eng.entity.BmtJobParameters parameterE = new BmtJobParameters();
							BmtJobParametersId id = new BmtJobParametersId();
							id.setIdJob(currentJob.getIdJob());

							try {
								id.setParameterId(new BigDecimal("5"));
								outParameters(id, "KO", "OUT", "VARCHAR2", "ESITO_STRINGA");
								id.setParameterId(new BigDecimal("6"));
								outParameters(id, e.getMessage(), "OUT", "CLOB", "MSG_OUTPUT");
								currentJob.setMessage("Errore: " + e.getMessage());
								markJobAsInError(currentJob);
							} catch (Exception e1) {
								logger.error(String.format("Non è stato possibile porre in stato X il job %1$s",
										currentJob.getIdJob()));
							}

						continue;
					}
				} else {
					logger.error(String.format(
							"Non è stato possibile generare l'esportazione del documento %1$s del job %2$s, in quanto l'idJob è null",
							currentJob.getExportFilename(), currentJob.getIdJob()));
				}
			}
		}
		} catch (Exception e) {
			logger.error("3.2 Caricamento interrotto; Errore generico", e);
			String allTCodaXExportSTr1 = "SicraRicercaOrdinativoJob: Errore generico: "+e.getMessage();
			
			StringWriter messageWriter = new StringWriter();
			
			invioSegnalazione(JOB_CLASS_NAME,
			        allTCodaXExportSTr1 + "\n\n"
					+ messageWriter.toString() +"\n\n",
					null);
		}
		finally {
							try {
								it.eng.database.utility.HibernateUtil.release(sessionJob);
							} catch (Exception e) {
								throw new AurigaDAOException(e);
							}
						
							logger.info("4. Fine elaborazione Avanzamento");
							logger.info("******************************************");
					}
	}
    
	public void outParameters (BmtJobParametersId id,
			                   String parameterValue,
			                   String parameterDir,
			                   String parameterType,
			                   String parameterSubtype) throws Exception
	{
		logger.info("outParameters - INIZIO");
		it.eng.entity.BmtJobParameters parameterE = new BmtJobParameters();
		parameterE.setId(id);
		parameterE.setParameterValue(parameterValue);
		parameterE.setParameterDir(parameterDir);
		parameterE.setParameterType(parameterType);
		parameterE.setParameterSubtype(parameterSubtype);
		it.eng.database.dao.DaoBmtJobParameters jb = new it.eng.database.dao.DaoBmtJobParameters();
		try {
			it.eng.entity.BmtJobParameters parameter3 = jb.saveParameter(parameterE);
		} catch (Exception e1) {
			logger.info("Exception(e1.getMessage(): "+e1.getMessage());
			throw new Exception(e1.getMessage());
		}
		logger.info("outParameters - FINE");
	}
	
	private void invioSegnalazione(String oggetto, String corpo, List<File> allegati) {
		logger.info("Invio report per mail INIZIO");
		
		JavaMailSenderImpl mail = (JavaMailSenderImpl) context.getBean("mailSender");
		SmtpSenderBean sender = new SmtpSenderBean();
		MailToSendBean pMailToSendBean = new MailToSendBean();
		
		pMailToSendBean.setBody(corpo);
		pMailToSendBean.setSubject(oggetto);
		
		sender.setSmtpEndpoint(mail.getHost());
		sender.setSmtpPort(mail.getPort());
		sender.setUsernameAccount(mail.getUsername());
		logger.info("fine sender");
		pMailToSendBean.setSmptSenderBean(sender);
		pMailToSendBean.setAddressTo(config.getIndirizziMailSegnalazioni().getAddressTo());
		List<InternetAddress> internetAddressTo = new ArrayList<InternetAddress>();
		if (pMailToSendBean.getAddressTo() != null) {
			for (String addressto : pMailToSendBean.getAddressTo()) {
				try {
					internetAddressTo.add(new InternetAddress(addressto));
				} catch (AddressException e) {
					logger.info("Exception: "+e.getMessage());
				}
			}
		}
	    
	    javax.mail.Session session = javax.mail.Session.getDefaultInstance(mail.getJavaMailProperties()); 
	    logger.info("fine properties");
		try {
			 MimeMessage message = new MimeMessage(session);  
	         message.setFrom(new InternetAddress(mail.getUsername()));  
	         message.setRecipients(RecipientType.TO, internetAddressTo.toArray(new InternetAddress[0]));
	         message.setSubject(oggetto);  
	         message.setText(corpo);  
	         logger.info("message sent begin");
	         // Send message  
	         Transport.send(message); 
	         logger.info("message sent successfully....");
		} catch (Exception e) {
			logger.info("Exception: "+e.getMessage());
		}
		
		logger.info("Invio report per mail FINE");
	}
	
	/**
	 * In BMT_JOBS per ogni atto di liquidazione di cui recuperare gli ordinativi 
	
	 * <ul>
	 * <li>Tipo = GET_ORDINATIVI_ATTO_LIQ</li>
	 * <li>Status = E</li>
	 * </ul>
	 * 
	 * @return
	 * @throws Exception
	 */
	protected ExecutionResultBean<TPagingList<JobBean>> retrieveJobs() {

		ExecutionResultBean<TPagingList<JobBean>> retValue = new ExecutionResultBean<TPagingList<JobBean>>();

		try {

			TFilterFetch<JobBean> filter = new TFilterFetch<JobBean>();

			JobBean filterBean = new JobBean();

			// recupero tutti i job di tipo esportazione, di cui è stata
			// completato il recupero dei dati
			filterBean.setTipo("GET_ORDINATIVI_ATTO_LIQ");
			filterBean.setStatus("E");
			filter.setFilter(filterBean);

			List<TOrderBy> orders = new ArrayList<TOrderBy>();
			TOrderBy orderBy = new TOrderBy();
			orderBy.setPropname("ID_JOB");

			// dal più vecchio al più recente
			orderBy.setType(OrderByType.ASCENDING);

			filter.setOrders(orders);
			TPagingList<JobBean> results =daoBmtJobsClient.search(filter);

			if (results == null) {
				results = new TPagingList<JobBean>();
				results.setData(new ArrayList<JobBean>());
			}

			if (results.getData() == null) {
				results.setData(new ArrayList<JobBean>());
			}

			retValue.setSuccessful(true);
			retValue.setResult(results);

		} catch (Exception e) {

			String message = String.format("Durante il recupero dei jobs si è verificata la seguente eccezione %1$s",
					ExceptionUtils.getFullStackTrace(e));
			logger.error(message);

			retValue.setMessage(message);
			retValue.setSuccessful(false);
		}

		return retValue;
	}

	/**
	 * @param currentJob
	 * @throws Exception
	 */
	protected void updateLoginBeanSpaoo(JobBean currentJob) throws Exception {
		DmpkLoginLoginBean loginBean = authenticate(currentJob.getIdSpAoo(), tipoDominio, currentJob.getIdUser());
		aurigaLoginBean.setToken(loginBean.getCodidconnectiontokenout());
		aurigaLoginBean.setIdUser(loginBean.getIduserout());
		aurigaLoginBean.setIdUserLavoro(loginBean.getIduserout().toString());
		aurigaLoginBean.getSpecializzazioneBean().setIdDominio(currentJob.getIdSpAoo());
	}
	
	private void inserimentoFileAllegati(File allegatoDoc, AllegatoDocumentoInBean inBean) throws Exception {

		String uriStoredFile = null;
		StorageService storage = StorageImplementation.getStorage();
		String storageError = null;
		try {
			uriStoredFile = storage.store(allegatoDoc);
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				storageError = "File fattura non trovato";
			} else if (e instanceof StorageException) {
				storageError = "Errore nelle procedure di storage della fattura";
			} else if (e instanceof IOException) {
				storageError = "Errore nella lettura/scrittura del file fattura";
			} else {
				storageError = "Errore generico nell'elaborazione del file fattura";
			}
			e.printStackTrace();
		}
        
		
		// lista dei file da versionare
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		// mappa degli errori di versionamento
		Map<String, String> fileErrors = new HashMap<>();

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		Session session = null;
		Transaction transaction = null;
		try {
			            session = HibernateUtil.begin(JOB_CLASS_NAME);
			            transaction = session.beginTransaction();
						AllegatoStoreBean lAllegatoStoreBean = new AllegatoStoreBean();
						lAllegatoStoreBean.setIdUd(inBean.getIdUD());
						lAllegatoStoreBean.setDescrizione(inBean.getOggetto());
						lAllegatoStoreBean.setIdDocType(Integer.parseInt(inBean.getNomeDocType()));
						lAllegatoStoreBean.setFlgEreditaPermessi("1");
						
						String attributi = lXmlUtilitySerializer.bindXmlCompleta(lAllegatoStoreBean);
						DmpkCoreAdddocBean lAdddocBeanAllegato = new DmpkCoreAdddocBean();
						lAdddocBeanAllegato.setCodidconnectiontokenin(aurigaLoginBean.getToken());
						lAdddocBeanAllegato.setIduserlavoroin(aurigaLoginBean.getIdUser());
						lAdddocBeanAllegato.setAttributiuddocxmlin(attributi);
						final AdddocImpl storeAllegato = new AdddocImpl();
						storeAllegato.setBean(lAdddocBeanAllegato);
						session.doWork(new Work() {

							@Override
							public void execute(Connection paramConnection) throws SQLException {
								paramConnection.setAutoCommit(false);
								storeAllegato.execute(paramConnection);
							}
						});
						StoreResultBean<DmpkCoreAdddocBean> resultAllegato = new StoreResultBean<DmpkCoreAdddocBean>();
						AnalyzeResult.analyze(lAdddocBeanAllegato, resultAllegato);
						resultAllegato.setResultBean(lAdddocBeanAllegato);

						if (resultAllegato.isInError()) {
							throw new StoreException(resultAllegato);
						} else {
							
								//FileInfoBean info = getFileInfo(allegatoDoc);
								RebuildedFile lRebuildedFile = new RebuildedFile();
								lRebuildedFile.setFile(allegatoDoc);
								//lRebuildedFile.setInfo(info);
								lRebuildedFile.setIdDocumento(resultAllegato.getResultBean().getIddocout());
								lRebuildedFile.setPosizione(1);
								versioni.add(lRebuildedFile);
							
						}
		

			// Parte di versionamento
			fileErrors = versionamentiInTransaction(versioni, session);

			if (!fileErrors.isEmpty()) {
				logger.error("Mappa errori versionamento : \n "+ fileErrors.toString());
				throw new Exception("Errore durante il versionamento dei file di attacch"); //TODO : riscrivere
			}

		} catch (Exception e) {
			logger.error("Errore durante l'attacch dei file all UD: " + e.getMessage(), e);
			if (transaction != null)
			{	
				transaction.rollback();
			}	
		}finally {
			transaction.commit();
			HibernateUtil.release(session);
		}
		
		
	}

     
	/**
	 * Autentica senza controllo di password l'utente specificato presso il
	 * dominio fornito
	 * 
	 * @param idDominio
	 * @param tipoDominio
	 * @param username
	 * @return
	 * @throws Exception
	 */
	private DmpkLoginLoginBean authenticate(BigDecimal idDominio, int tipoDominio, String username) throws Exception {

		DmpkLoginLoginBean lLoginInput = new DmpkLoginLoginBean();
		lLoginInput.setUsernamein(username);
		lLoginInput.setFlgtpdominioautio(tipoDominio);
		lLoginInput.setIddominioautio(idDominio);
		lLoginInput.setCodapplicazioneestin(null);
		lLoginInput.setCodistanzaapplestin(null);
		lLoginInput.setFlgrollbckfullin(null);
		lLoginInput.setFlgautocommitin(1);
		lLoginInput.setFlgnoctrlpasswordin(1);

		DmpkLoginLogin lLogin = new DmpkLoginLogin();

		StoreResultBean<DmpkLoginLoginBean> lLoginOutput = lLogin.execute(locale, aurigaLoginBean, lLoginInput);

		if (lLoginOutput.getDefaultMessage() != null) {
			throw new Exception(lLoginOutput.getErrorContext() + " - " + lLoginOutput.getErrorCode() + " - "
					+ lLoginOutput.getDefaultMessage());
		} else {

			DmpkLoginLoginBean retValue = lLoginOutput.getResultBean();
			logger.debug(String.format("Autenticazione avvenuta con successo, token %1$s",
					retValue.getCodidconnectiontokenout()));

			return retValue;
		}

	}

	private Boolean configura() {

		Boolean valid = Boolean.TRUE;

		context = SpringHelper.getMainApplicationContext();
		schema = (String) getAttribute(JOBATTRKEY_SCHEMA);
		
	//	lDocumentConfiguration = (DocumentConfiguration) context.getBean("DocumentConfiguration");
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
		it.eng.core.business.subject.SubjectBean subject = new it.eng.core.business.subject.SubjectBean();
		subject.setIdDominio(tipoDominio+"");
		it.eng.core.business.subject.SubjectUtil.subject.set(subject);
		
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

		bmtJobsClient = (DaoBmtJobs) context.getBean("bmtJobsClient");

		if (bmtJobsClient == null) {

			logger.error("BmtJobs non correttamente inizializzato");

			valid = Boolean.FALSE;
		}

		bmtJobParametersClient = (DaoBmtJobParameters) context.getBean("bmtJobParametersClient");

		return valid;
	}
	protected void markJobAsInError(JobBean currentJob) throws Exception {
		updateJobStatus(currentJob, "X");
	}
	protected void markJobAsInit(JobBean currentJob) throws Exception {
		currentJob.setStartTime(new Date());
		updateJobStatus(currentJob, "D");
	}
	protected void markJobAsCompleted(JobBean currentJob) throws Exception {
		currentJob.setEndTime(new Date());
		updateJobStatus(currentJob, "R");
	}
	protected void updateJobStatus(JobBean currentJob, String status) throws Exception {
		currentJob.setStatus(status);
		daoBmtJobsClient.update(currentJob);
	}
	
	@Override
	public void end(String arg0) {
		HibernateUtil.closeConnection(JOB_CLASS_NAME);
		logger.info("Elaborazione completata");
	}
	/*public FileInfoBean getFileInfo(File fileDoc) throws BeansException, DocumentUtilException {
		FileInfoBean lFileInfoFileBean = new FileInfoBean();
		try {
			String algoritmo = lDocumentConfiguration.getAlgoritmo().value();
			String encoding = lDocumentConfiguration.getEncoding().value();
			lFileInfoFileBean = AurigaFileUtils.createFileInfoBean(fileDoc, algoritmo, encoding);
			
			
			
		} catch (Exception e) {
			throw new DocumentUtilException(e);
		}

		return lFileInfoFileBean;
	}*/
	

	private Map<String, String> versionamentiInTransaction(List<RebuildedFile> versioni, Session session) {
		Map<String, String> fileErrors = new HashMap<String, String>();

		for (RebuildedFile lRebuildedFile : versioni) {
			try {
				VersionaDocumentoInBean lVersionaDocumentoInBean = getVersionaDocumentoInBean(lRebuildedFile);
				VersionaDocumentoOutBean lVersionaDocumentoOutBean = versionaDocumentoInTransaction(
						lVersionaDocumentoInBean, session);
				if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
					throw new Exception(lVersionaDocumentoOutBean.getDefaultMessage());
				}
			} catch (Exception e) {
				logger.error("Errore " + e.getMessage(), e);
				fileErrors.put("" + lRebuildedFile.getInfo().getPosizione(),
						"Il file allegato " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename()
								+ " in posizione " + lRebuildedFile.getInfo().getPosizione()
								+ " non E stato salvato correttamente."
								+ (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));

			}
		}

		return fileErrors;
	}
	protected VersionaDocumentoInBean getVersionaDocumentoInBean(RebuildedFile lRebuildedFile)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
		return lVersionaDocumentoInBean;
	}
	private VersionaDocumentoOutBean versionaDocumentoInTransaction(VersionaDocumentoInBean lVersionaDocumentoInBean,
			Session session) {

		VersionaDocumentoOutBean lVersionaDocumentoOutBean = new VersionaDocumentoOutBean();
		try {

			String uriVer = DocumentStorage.store(lVersionaDocumentoInBean.getFile(),
					aurigaLoginBean.getSpecializzazioneBean().getIdDominio());
			logger.debug("Salvato " + uriVer);

			FileStoreBean lFileStoreBean = new FileStoreBean();
			lFileStoreBean.setUri(uriVer);
			lFileStoreBean.setDimensione(lVersionaDocumentoInBean.getFile().length());

			try {
				BeanUtilsBean2.getInstance().copyProperties(lFileStoreBean,
						lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento());
			} catch (Exception e) {
				logger.warn(e);
			}

			String lStringXml = "";
			try {
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
				logger.debug("attributiVerXml " + lStringXml);
			} catch (Exception e) {
				logger.warn(e);
			}

			if (lVersionaDocumentoInBean.getUpdateVersion() != null && lVersionaDocumentoInBean.getUpdateVersion()) {

				DmpkCoreUpdverdocBean lUpdverdocBean = new DmpkCoreUpdverdocBean();
				lUpdverdocBean.setCodidconnectiontokenin(aurigaLoginBean.getToken());
				lUpdverdocBean.setIduserlavoroin(null);
				lUpdverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
				lUpdverdocBean.setNroprogrverio(null);
				lUpdverdocBean.setAttributixmlin(lStringXml);

				final UpdverdocImpl store = new UpdverdocImpl();
				store.setBean(lUpdverdocBean);
				logger.debug("Chiamo la updverdoc " + lUpdverdocBean);

				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreUpdverdocBean> lUpdverdocStoreResultBean = new StoreResultBean<DmpkCoreUpdverdocBean>();
				AnalyzeResult.analyze(lUpdverdocBean, lUpdverdocStoreResultBean);
				lUpdverdocStoreResultBean.setResultBean(lUpdverdocBean);

				if (lUpdverdocStoreResultBean.isInError()) {
					logger.error("Default message: " + lUpdverdocStoreResultBean.getDefaultMessage());
					logger.error("Error context: " + lUpdverdocStoreResultBean.getErrorContext());
					logger.error("Error code: " + lUpdverdocStoreResultBean.getErrorCode());
					BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, lUpdverdocStoreResultBean);
					return lVersionaDocumentoOutBean;
				}

			} else {

				DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
				lAddverdocBean.setCodidconnectiontokenin(aurigaLoginBean.getToken());
				lAddverdocBean.setIduserlavoroin(null);
				lAddverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
				lAddverdocBean.setAttributiverxmlin(lStringXml);

				final AddverdocImpl store = new AddverdocImpl();
				store.setBean(lAddverdocBean);
				logger.debug("Chiamo la addVerdoc " + lAddverdocBean);

				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreAddverdocBean> lAddverdocStoreResultBean = new StoreResultBean<DmpkCoreAddverdocBean>();
				AnalyzeResult.analyze(lAddverdocBean, lAddverdocStoreResultBean);
				lAddverdocStoreResultBean.setResultBean(lAddverdocBean);

				if (lAddverdocStoreResultBean.isInError()) {
					logger.error("Default message: " + lAddverdocStoreResultBean.getDefaultMessage());
					logger.error("Error context: " + lAddverdocStoreResultBean.getErrorContext());
					logger.error("Error code: " + lAddverdocStoreResultBean.getErrorCode());
					BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, lAddverdocStoreResultBean);
					return lVersionaDocumentoOutBean;
				}

			}

		} catch (Throwable e) {

			if (StringUtils.isNotBlank(e.getMessage())) {
				logger.error(e.getMessage(), e);
				lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage());
			} else {
				logger.error("Errore generico", e);
				lVersionaDocumentoOutBean.setDefaultMessage("Errore generico");
			}
			return lVersionaDocumentoOutBean;
		}

		return lVersionaDocumentoOutBean;
	}
	public void aggiornaDatiContabilia(Session sessionJob,  String token, String idDocPrimario,it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato res)
			throws Exception {

		
			Transaction transaction = null;
			try {
				logger.info("sessionJob: "+sessionJob);
				logger.info("token: "+token);
				logger.info("idDocPrimario: "+idDocPrimario);
				logger.info("res: "+res.toString());
				
			/*	final SpringBeanWrapperConfigBean springBeanWrapperConfig = new SpringBeanWrapperConfigBean();
				   
				springBeanWrapperConfig.setEnableSpringBeanWrapper(true);*/
				SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
				
				List<OrdinativiSICRAXmlBean> listaOrdinativiSicra = new ArrayList<OrdinativiSICRAXmlBean>();
			    int m =0;
				for (int i =0 ; i< res.getLiquidazione().size() ; i++)
				{
					OrdinativiSICRAXmlBean attrEventTypeBean = new OrdinativiSICRAXmlBean();
					logger.info("res.getLiquidazione().size(): "+res.getLiquidazione().size());
					try {
						attrEventTypeBean.setCodiceLiquidazione((int) res.getLiquidazione().get(i).getCodice());
						logger.info("A: "+m+1);
						attrEventTypeBean.setCodiceAnnualeLiquidazione((int) res.getLiquidazione().get(i).getCodiceAnnuale());
						logger.info("A: "+m++);
						attrEventTypeBean.setLiquidazioneAnno(res.getLiquidazione().get(i).getAnno().toString());
						logger.info("A: "+m++);
						try {
							Liquidazione liq = res.getLiquidazione().get(i);
							logger.info("date: "+liq.getData());
							Date date = null; 
							if (res.getLiquidazione().get(i).getData().getTime()!=null)
							{
								date = res.getLiquidazione().get(i).getData().getTime();
							}
							else
							{
								date = new Date();
							}
							attrEventTypeBean.setLiquidazioneData(date);
							logger.info("attrEventTypeBean: "+attrEventTypeBean.getLiquidazioneData());
						} catch (Exception e) {
							logger.error("Exception: "+attrEventTypeBean.getLiquidazioneData());
						}
						logger.info("A: "+m++);
						attrEventTypeBean.setLiquidazioneOggetto(res.getLiquidazione().get(i).getOggetto());
						logger.info("A: "+m++);
						try {
							
						if(res.getLiquidazione().get(i).getTipoLiquidazione().intValue() == 0)
						{
							attrEventTypeBean.setLiquidazioneTipo("LIQUIDAZIONE TECNICA");
						}
						else
						{   
							attrEventTypeBean.setLiquidazioneTipo("LIQUIDAZIONE CONTABILE");
						}
						} catch (Exception e) {
							// TODO: handle exception
						}
						logger.info("A: "+m++);
					} catch (Exception e) {
						logger.info("error : "+e.getMessage());
					}
					if(res.getLiquidazione().get(i).getOrdinativi()!=null)
					{	
					for (int j =0 ; j< res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().size(); j++)
					{
						logger.info("getOrdinativi.size(): "+res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().size());
						attrEventTypeBean.setOrdinativoTipo(res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getTipo().value());
						attrEventTypeBean.setOrdinativoNumero((int)res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getNumero());
						attrEventTypeBean.setOrdinativoAnno(res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getAnno().intValue());
						attrEventTypeBean.setOrdinativoData(res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getData().getTime());
						attrEventTypeBean.setOrdinativoCausale(res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getCausale());
						attrEventTypeBean.setOrdinativoAnnullato(res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getAnnullato().intValue());
						
						for (int k =0 ; k< res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().size(); k++)
						{
							logger.info("getBeneficiari.size(): "+res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().size());
							try
							{
							int n =0;
							
							// Modifica Picone 04/01/2023: canellazione eventuali a capo in valore nominativo restituito da Sicra
							String nominativo = res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().get(k).getNominativo();
							nominativo = nominativo.replaceAll("\\r", " ").replaceAll("\\n", " ");
							nominativo = nominativo.replaceAll("<br>", " ").replaceAll("<br/>", " ").replaceAll("<BR>", " ").replaceAll("<BR/>", " ");
							
							logger.info("A: "+n);
							attrEventTypeBean.setBenefOridinativoDes(nominativo);
							logger.info("A: "+n++);
							attrEventTypeBean.setBenefOridinativoCf(res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().get(k).getCodiceFiscale());
							logger.info("A: "+n++);
							attrEventTypeBean.setBenefOridinativoPiva(res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().get(k).getPartitaIva());
							logger.info("A: "+n++);
							attrEventTypeBean.setBenefOridinativoImpLord(String.format("%,.2f",res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().get(k).getImportoLordo()));
							logger.info("A: "+n++);
							attrEventTypeBean.setBenefOridinativoImpRitenute(String.format("%,.2f",res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().get(k).getImportoRitenute()));
							logger.info("A: "+n++);
							attrEventTypeBean.setBenefOridinativoImpNetto(String.format("%,.2f",res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().get(k).getImportoNetto()));
							logger.info("A: "+n++);
							}catch (Exception e) {
								logger.error("Exception : "+e.getMessage());
							}
							try
							{
							for (int n =0 ; n< res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().get(k).getQuietanze().getQuietanza().size(); n++)
							{
								
								
								logger.info("getQuietanze.size(): "+res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().get(k).getQuietanze().getQuietanza().size());
								attrEventTypeBean.setQuietanzaNumero((int)res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().get(k).getQuietanze().getQuietanza().get(n).getNumero());
								attrEventTypeBean.setQuietanzaAnno(res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().get(k).getQuietanze().getQuietanza().get(n).getAnno().intValue());
								attrEventTypeBean.setQuietanzaData(res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().get(k).getQuietanze().getQuietanza().get(n).getData().getTime());
								attrEventTypeBean.setQuietanzaImporto(String.format("%,.2f",res.getLiquidazione().get(i).getOrdinativi().getOrdinativo().get(j).getBeneficiari().getBeneficiario().get(k).getQuietanze().getQuietanza().get(n).getImporto()));
								
							}//Chiudo for Quietanza
							}catch (Exception e) {
								logger.error("Exception : "+e.getMessage());
							}
						}//Chiudo for Beneficiario
						OrdinativiSICRABean lOrdinativiSICRABean = new OrdinativiSICRABean();
						lOrdinativiSICRABean.setId(i + "");
						BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lOrdinativiSICRABean,
								attrEventTypeBean);
						listaOrdinativiSicra.add(lOrdinativiSICRABean);
					}//Chiudo for Ordinativi
					}//chiudo if ordinativi
					else
					{	
					OrdinativiSICRABean lOrdinativiSICRABean = new OrdinativiSICRABean();
					lOrdinativiSICRABean.setId(i + "");
					BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lOrdinativiSICRABean,
							attrEventTypeBean);
					listaOrdinativiSicra.add(lOrdinativiSICRABean);
					}
				}//Chiudo for Liquidazione
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ORDINATIVI_SICRA_Doc",
						new XmlUtilitySerializer().createVariabileLista(listaOrdinativiSicra));
				
				
				if (sezioneCacheAttributiDinamici.getVariabile().size() > 0) {
					
					transaction = sessionJob.beginTransaction();
					final UpddocudImpl lDmpkCoreUpddocud = new UpddocudImpl();
					DmpkCoreUpddocudBean bean = new DmpkCoreUpddocudBean();
					bean.setCodidconnectiontokenin(token);
					bean.setIduddocin(StringUtils.isNotBlank(idDocPrimario) ? new BigDecimal(idDocPrimario) : null);
					bean.setFlgtipotargetin("D");
					
					CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
					lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);

					XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
					bean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));
					logger.info("bean: "+bean.getAttributiuddocxmlin());
					lDmpkCoreUpddocud.setBean(bean);
					sessionJob.doWork(new Work() {

						@Override
						public void execute(Connection paramConnection) throws SQLException {
							paramConnection.setAutoCommit(false);
							lDmpkCoreUpddocud.execute(paramConnection);
						}
					});
					StoreResultBean<DmpkCoreUpddocudBean> out = new StoreResultBean<DmpkCoreUpddocudBean>();
					AnalyzeResult.analyze(bean, out);
					out.setResultBean(bean);
					
					if (out.isInError() ) {
						logger.error("Il servizio lDmpkCoreUpddocud ha restituito getDefaultMessage: " + out.getDefaultMessage());
						logger.error("Il servizio lDmpkCoreUpddocud ha restituito l'errore: " + out.isInError() );
						logger.error("Il servizio lDmpkCoreUpddocud ha restituito l'errore: " + out.getErrorContext());
						logger.error("Il servizio lDmpkCoreUpddocud ha restituito l'errore: " + out.getErrorCode());
						logger.error("Il servizio lDmpkCoreUpddocud ha restituito l'errore: " + out.getResultBean().getErrcontextout());
						logger.error("Il servizio lDmpkCoreUpddocud ha restituito l'errore: " + out.getStoreName());
						logger.error("Il servizio lDmpkCoreUpddocud ha restituito l'errore: " + out.getResultBean().getErrmsgout());
						
						throw new Exception(out.getDefaultMessage());
					}else
					{
						logger.info("Il servizio lDmpkCoreUpddocud ha restituito getDefaultMessage: " + out.getDefaultMessage());
					}
				}
			} catch (Exception e) {
				logger.error("error: "+e.getMessage());
				if (transaction != null)
				{	
					try {
						transaction.rollback();
					} catch (Exception ex) {
						logger.error(ex);
					}
				}
				throw new StoreException(e.getMessage());
			}finally {
				if (transaction != null && !transaction.wasCommitted()) {
					try {
						logger.info("transaction.commit() closed");
						transaction.commit();
					} catch (Exception e) {
						logger.error(e);
					}

				}
			}	
		}
	
	private void putVariabileListaSezioneCache(SezioneCache sezioneCache, String nomeVariabile, Lista lista) {		
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setLista(lista);	
		} else {
			sezioneCache.getVariabile().add(createVariabileLista(nomeVariabile, lista));
		}
	}
	
	public static Variabile createVariabileLista(String nome, Lista lista) {
		Variabile var = new Variabile();
		var.setNome(nome);
		var.setLista(lista);
		return var;
	}
	
	private Variabile createVariabileSemplice(String nome, String valoreSemplice) {
		Variabile var = new Variabile();
		var.setNome(nome);
		var.setValoreSemplice(valoreSemplice);
		return var;
	}
	private int getPosVariabileSezioneCache(SezioneCache sezioneCache, String nomeVariabile) {
		if (sezioneCache != null && sezioneCache.getVariabile() != null) {
			for (int i = 0; i < sezioneCache.getVariabile().size(); i++) {
				Variabile var = sezioneCache.getVariabile().get(i);
				if (var.getNome().equals(nomeVariabile)) {
					return i;
				}
			}
		}
		return -1;
	}
}
