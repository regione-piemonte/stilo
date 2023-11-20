/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import it.eng.database.dao.TCodaOperConWfDAOImpl;
import it.eng.database.utility.HibernateUtil;
import it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest;
import it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.EseguiTaskInBean;
import it.eng.document.function.bean.EseguiTaskOutBean;
import it.eng.entity.TCodaOperConWf;
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

@Job(type = "AvanzamentoJob")
@Named
public class AvanzamentoJob extends AbstractJob<String> {

	private static Logger log = Logger.getLogger(AvanzamentoJob.class);
	private static final String JOBATTRKEY_SCHEMA = "schema";
	private static final String JOBATTRKEY_ENTITY_PACKAGE = "entityPackage";
	private static final String JOBATTRKEY_HIBERNATE_CONFIG_FILE = "hibernateConfigFile";
	private static final String SPRINGBEAN_JOB_CONFIG = "avanzamentoConfig";
	private static final String JOBATTRKEY_LOCALE = "locale";
	private static final String JOBATTRKEY_DATE_FORMAT = "dateFormat";
	private static final String JOBATTRKEY_TIPO_DOMINIO = "tipoDominio";
	private static final String JOB_CLASS_NAME = AvanzamentoJob.class.getName();
	private AurigaLoginBean aurigaLoginBean;
	private AvanzamentoConfig config;
	private ApplicationContext context;

	
	private static Locale locale;

	private String schema; 

	private AurigaLoginBean loginBean;
	
	private Integer tipoDominio;
	
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
			Boolean valid = Boolean.TRUE;
			context = SpringAppContext.getContext();
			config = (AvanzamentoConfig) context.getBean(SPRINGBEAN_JOB_CONFIG);
			schema = (String) getAttribute(JOBATTRKEY_SCHEMA);
			
			//	lDocumentConfiguration = (DocumentConfiguration) context.getBean("DocumentConfiguration");
				valid = schema != null && !schema.isEmpty();

				if (!valid) {
					log.error("Schema non configurato");
					
				}

				aurigaLoginBean = new AurigaLoginBean();

				String localeValue = (String) getAttribute(JOBATTRKEY_LOCALE);

				valid = localeValue != null && !localeValue.isEmpty();

				if (!valid) {
					log.error("Locale non specificato");
					
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
					log.error("Tipo dominio non specificato");
					
				}

				locale = new Locale(localeValue);
				it.eng.core.business.subject.SubjectBean subject = new it.eng.core.business.subject.SubjectBean();
				subject.setIdDominio(tipoDominio+"");
				it.eng.core.business.subject.SubjectUtil.subject.set(subject);
				
				String dateFormatValue = (String) getAttribute(JOBATTRKEY_DATE_FORMAT);

				valid = dateFormatValue != null && !dateFormatValue.isEmpty();

				if (!valid) {
					log.error("Formato della data non specificato");
				
				}
		}catch (Exception e) {
			log.error("Exception "+e.getMessage());
		}
	}

	private void elabora() throws Exception {
		AurigaLoginBean loginBean = new AurigaLoginBean();
		
		
		loginBean.setSchema(config.getSchema());
		SpecializzazioneBean spec = new SpecializzazioneBean();
		spec.setIdDominio(new BigDecimal(3));
		loginBean.setSpecializzazioneBean(spec);

		WebServicesUtils webServicesUtils = new WebServicesUtils();
		//FileWriter fileWriter = null;
		Session sessionJob = null;
		try {
			log.info("******************************************");
			log.info("0. Inizio elaborazione Avanzamento");
			log.info("1. recupero IDs");
			TCodaOperConWfDAOImpl avanzamentoDAOImpl = new TCodaOperConWfDAOImpl();
			int numTryMax = config.getNumTryMax();
			sessionJob = HibernateUtil.openSession(JOB_CLASS_NAME);
			
			Map<String, List<TCodaOperConWf>> results1 = avanzamentoDAOImpl.searchAvanzamentoEsito("EL",sessionJob);
			log.info("1.1 Erano presenti in stato EL numero: "+results1.size());
			Map<String, List<TCodaOperConWf>> results = avanzamentoDAOImpl.searchAvanzamento(numTryMax,sessionJob);
			
			/*	String pathReport = config.getReportPath() + "/" + dateFolders();
				new File(pathReport).mkdirs();
				File csv = new File(
						pathReport + "/report_num_item_" + (results != null ? "" + results.size() : "null") + ".csv");
				fileWriter = new FileWriter(csv);
			    fileWriter.append("InstanceId;ActivityName;IdProcess;esito;errore;numTry\n");
				fileWriter.flush();*/
				
				log.info("2.2 INIZIO Chiamata task");
			//	it.eng.spring.utility.SpringAppContext.setContext(SpringAppContext.getContext());
				
				Set<String> keys = results.keySet();
				boolean continua = false;
				for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					continua = false;
					
					
					{
						TCodaOperConWf tCodaOperConWfSIB = null;
						TCodaOperConWf tCodaOperConWfAlbo = null;
						TCodaOperConWf tCodaOperConWfWf = null;
						TCodaOperConWf tCodaOperConWfContabilia = null;
						
						List<TCodaOperConWf> listTCodaOperConWf = (List<TCodaOperConWf>) results.get(key);
						for (Iterator iterator2 = listTCodaOperConWf.iterator(); iterator2.hasNext();) {
							TCodaOperConWf tCodaOperConWf = (TCodaOperConWf) iterator2.next();
							
							if (tCodaOperConWf.getTipoOperazione().equals("invio_esecutivita_contabilita_SIB")) {
								log.debug("SIB " + tCodaOperConWf.getIdOperazione());
								tCodaOperConWfSIB = tCodaOperConWf;
							}
							if (tCodaOperConWf.getTipoOperazione().equals("invio_albo_CMMI")) {
								log.debug("ALBO " + tCodaOperConWf.getIdOperazione());
								tCodaOperConWfAlbo = tCodaOperConWf;
							}
							if (tCodaOperConWf.getTipoOperazione().equals("complete_task_wf")) {
								log.debug("WF " + tCodaOperConWf.getIdOperazione());
								tCodaOperConWfWf = tCodaOperConWf;
							}
							if (tCodaOperConWf.getTipoOperazione().equals("invio_archiviazione_contabilita_CONTABILIA")) {
								log.debug("CONTABILIA " + tCodaOperConWf.getIdOperazione());
								tCodaOperConWfContabilia = tCodaOperConWf;
							}

						}
						
							
							//STEP:complete_task_wf
							if (tCodaOperConWfWf != null) {

								try {
									
									
									log.info("tCodaOperConWfWf: "+ tCodaOperConWfWf.toString());
									
									loginBean.setToken(tCodaOperConWfWf.getCiConnectionToken());
									
				                    EseguiTaskInBean lEseguiTaskInBean = new EseguiTaskInBean();
									lEseguiTaskInBean.setInstanceId(tCodaOperConWfWf.getTaskInstanceId());
									lEseguiTaskInBean.setActivityName(tCodaOperConWfWf.getTaskDefKey());
									lEseguiTaskInBean.setIdProcess("" + tCodaOperConWfWf.getIdProcess());
									if(tCodaOperConWfWf.getIdEventType()!=null)
									{	
									lEseguiTaskInBean.setIdEventType(tCodaOperConWfWf.getIdEventType().toString());
									log.info("4.2.1 input:  INSTANCE ID: " + lEseguiTaskInBean.getInstanceId()
									+ "; ACTICITY_NAME: " + lEseguiTaskInBean.getActivityName()
									+ "; ID PROCESS: " + lEseguiTaskInBean.getIdProcess()
									+ "; ID TYPE EVENTO: " + lEseguiTaskInBean.getIdEventType()
							     	+ "; xml: " + tCodaOperConWfWf.getAttrEventType() );	
									}
									else
									{
										log.info("4.2.1 input:  INSTANCE ID: " + lEseguiTaskInBean.getInstanceId()
										+ "; ACTICITY_NAME: " + lEseguiTaskInBean.getActivityName()
										+ "; ID PROCESS: " + lEseguiTaskInBean.getIdProcess());	
									}
									
//									lEseguiTaskInBean.setaIdEventType(tCodaOperConWfWf.getAttrEventType());
//									lEseguiTaskInBean.setIdEventType("136");
										

									GestioneTaskJob gestioneTask = new GestioneTaskJob();
									EseguiTaskOutBean eseguiTaskOutBean = gestioneTask.salvaTask(sessionJob, loginBean,
											lEseguiTaskInBean,tCodaOperConWfWf.getAttrEventType(),tCodaOperConWfWf);
									

									log.info("4.2.2 Chiamata inviata ");

									Integer errorCode = eseguiTaskOutBean.getErrorCode();
									String message = "";
									if (eseguiTaskOutBean.getDefaultMessage() != null && !"".equals(eseguiTaskOutBean.getDefaultMessage())){
									message += eseguiTaskOutBean.getDefaultMessage();
									}
//									if (eseguiTaskOutBean.getWarningMessage() != null && !"".equals(eseguiTaskOutBean.getWarningMessage())){
//									message += eseguiTaskOutBean.getWarningMessage();
//									}
									if (eseguiTaskOutBean.getErrorContext() != null && !"".equals(eseguiTaskOutBean.getErrorContext())){
									message += eseguiTaskOutBean.getErrorContext();
									}
									if (errorCode == null && (message == null || "".equals(message))) {
										tCodaOperConWfWf.setEsitoElab("OK");
										tCodaOperConWfWf.setErrCode(null);
										tCodaOperConWfWf.setErrMsg(null);
										log.info("4.2.2:  chiamata Wf OK");
									} else {
										tCodaOperConWfWf.setErrCode(new BigDecimal(errorCode));
										tCodaOperConWfWf.setErrMsg(message);
										tCodaOperConWfWf.setEsitoElab("KO");
										log.info("4.2.2.ERR: errore da chiamata: " + errorCode + " - " + message);
									}

								} catch (Exception e) {
									tCodaOperConWfWf.setErrCode(new BigDecimal("0"));
									tCodaOperConWfWf.setErrMsg(e.getMessage());
									tCodaOperConWfWf.setEsitoElab("KO");
									log.error("4.2.ERR Chiamata NON inviata " + e.getMessage(), e);
								} finally {
									log.info("4.3 Tracciamneto chiamata ");
									tCodaOperConWfWf.setNumTry(tCodaOperConWfWf.getNumTry().add(new BigDecimal("1")));
									tCodaOperConWfWf.setTsLastTry(new Date());
									avanzamentoDAOImpl.updateAvanzamento(tCodaOperConWfWf,sessionJob);
/*									fileWriter.append(tCodaOperConWfWf.getTaskInstanceId() + ";"
											+ tCodaOperConWfWf.getTaskDefKey() + ";" + tCodaOperConWfWf.getIdProcess()
											+ ";" + tCodaOperConWfWf.getEsitoElab() + ";" + tCodaOperConWfWf.getErrMsg()
											+ ";" + tCodaOperConWfWf.getNumTry() + "\n");
									fileWriter.flush();*/
								}
							}//Chiudo //STEP:complete_task_wf
							
							
							//STEP:invio_archiviazione_contabilita_Contabilia
							if (tCodaOperConWfContabilia != null) {
								
								try {
									
									Contabilia cont = new Contabilia();
									/* Parametri
									 *  ID_OBJ -> ID_UD
									 *  ID_DOMINIO
									 */ 
									NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = new NuovaPropostaAtto2CompletaBean(); 
									lNuovaPropostaAtto2CompletaBean.setIdUd(tCodaOperConWfContabilia.getIdObj().toString());		
									lNuovaPropostaAtto2CompletaBean.setIdDominio(tCodaOperConWfContabilia.getIdDominio().toString());
									log.info("IdUd: "+lNuovaPropostaAtto2CompletaBean.getIdUd());
									log.info("IdDominio: "+lNuovaPropostaAtto2CompletaBean.getIdDominio());
									//Recupero Registrazioni - Provvisorie e definitve
									
									AttrEventTypeBean attrEventTypeBean = new XmlUtilityDeserializer().unbindXml(tCodaOperConWfContabilia.getAttrEventType(),
											AttrEventTypeBean.class);
									
									if(attrEventTypeBean!= null)
									{
										lNuovaPropostaAtto2CompletaBean.setSiglaRegistrazione(attrEventTypeBean.getSiglaRegistrazione());
										lNuovaPropostaAtto2CompletaBean.setNumeroRegistrazione(attrEventTypeBean.getNumeroRegistrazione());
										lNuovaPropostaAtto2CompletaBean.setSiglaRegProvvisoria(attrEventTypeBean.getSiglaRegProvvisoria());
										lNuovaPropostaAtto2CompletaBean.setNumeroRegProvvisoria(attrEventTypeBean.getNumeroRegProvvisoria());	
										lNuovaPropostaAtto2CompletaBean.setAnnoRegistrazione(attrEventTypeBean.getAnnoAttoDefinitivo());
										SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										lNuovaPropostaAtto2CompletaBean.setDataRegistrazione(textFormat.parse(attrEventTypeBean.getDataRegistrazione()));
										lNuovaPropostaAtto2CompletaBean.setAnnoRegProvvisoria(attrEventTypeBean.getAnnoRegProvvisoria());
										lNuovaPropostaAtto2CompletaBean.setDataRegProvvisoria(textFormat.parse(attrEventTypeBean.getDataRegProvvisoria()));
									}
									
									 //lNuovaPropostaAtto2CompletaBean = avanzamentoDAOImpl.getRegistroContabilia(JOB_CLASS_NAME, lNuovaPropostaAtto2CompletaBean);
									
									if (lNuovaPropostaAtto2CompletaBean.getSiglaRegProvvisoria()!=null);
									{
										log.info("Sigla: "+lNuovaPropostaAtto2CompletaBean.getSiglaRegProvvisoria());
										log.info("Numero: "+lNuovaPropostaAtto2CompletaBean.getNumeroRegProvvisoria());
								    }
									if (lNuovaPropostaAtto2CompletaBean.getNumeroRegistrazione()!=null);
									{
										log.info("Sigla: "+lNuovaPropostaAtto2CompletaBean.getSiglaRegistrazione());
										log.info("Numero: "+lNuovaPropostaAtto2CompletaBean.getNumeroRegistrazione());
								    }
									
									
									DocumentoXmlOutBean lDocumentoXmlOutBean = null;
									
											
										
										final LoaddettudxguimodprotImpl dmpkRepositoryGuiLoaddettudxguimodprot = new LoaddettudxguimodprotImpl();
										DmpkRepositoryGuiLoaddettudxguimodprotBean bean = new DmpkRepositoryGuiLoaddettudxguimodprotBean();
										
										bean.setIdudin(tCodaOperConWfContabilia.getIdObj());
										bean.setCodidconnectiontokenin(tCodaOperConWfContabilia.getCiConnectionToken());
										bean.setIdprocessin(tCodaOperConWfContabilia.getIdProcess());
										bean.setTasknamein(tCodaOperConWfContabilia.getTaskDefKey());
										dmpkRepositoryGuiLoaddettudxguimodprot.setBean(bean);
										sessionJob.doWork(new Work() {

											@Override
											public void execute(Connection paramConnection) throws SQLException {
												paramConnection.setAutoCommit(false);
												dmpkRepositoryGuiLoaddettudxguimodprot.execute(paramConnection);
											}
										});
										StoreResultBean<DmpkRepositoryGuiLoaddettudxguimodprotBean> out = new StoreResultBean<DmpkRepositoryGuiLoaddettudxguimodprotBean>();
										AnalyzeResult.analyze(bean, out);
										out.setResultBean(bean);

										if (out.isInError()) {
											throw new AurigaJobException(StringUtils.isNotBlank(out.getDefaultMessage())
													? out.getDefaultMessage()
													: "Si è verificato un errore durante il recupero dei dati del documento");
										}
										DmpkRepositoryGuiLoaddettudxguimodprotBean response = out.getResultBean();
										log.debug("XML response:" + response.getDatiudxmlout());
										lDocumentoXmlOutBean = new XmlUtilityDeserializer().unbindXml(response.getDatiudxmlout(),
												DocumentoXmlOutBean.class);
								
										// Oggetto
										lNuovaPropostaAtto2CompletaBean.setOggetto(lDocumentoXmlOutBean.getOggetto());
										log.info("Oggetto : "+lNuovaPropostaAtto2CompletaBean.getOggetto());
										
										lNuovaPropostaAtto2CompletaBean.setDirigenteResponsabileContabilia(lDocumentoXmlOutBean.getDirigenteResponsabileContabilia());
										lNuovaPropostaAtto2CompletaBean.setCentroResponsabilitaContabilia(lDocumentoXmlOutBean.getCentroResponsabilitaContabilia());
										lNuovaPropostaAtto2CompletaBean.setCentroCostoContabilia(lDocumentoXmlOutBean.getCentroCostoContabilia());
									    
										String idPropostaAMC = lDocumentoXmlOutBean.getCodPropostaSistContabile();
										
										log.info("idPropostaAMC : "+idPropostaAMC);
										log.info("DirigenteResponsabileContabilia : "+lNuovaPropostaAtto2CompletaBean.getDirigenteResponsabileContabilia());
										log.info("CentroResponsabilitaContabilia: "+lNuovaPropostaAtto2CompletaBean.getCentroResponsabilitaContabilia());
										log.info("CentroCostoContabilia : "+lNuovaPropostaAtto2CompletaBean.getCentroCostoContabilia());
										
									    log.info("4.2.2 Chiamata inviata ");
									    
									    lNuovaPropostaAtto2CompletaBean = cont.annullamentoProposta(lNuovaPropostaAtto2CompletaBean);
									    									    
									
									Integer errorCode = 0;
									if ( lNuovaPropostaAtto2CompletaBean.getEsitoEventoContabilia().equals("OK")) {
										tCodaOperConWfContabilia.setEsitoElab("OK");
										tCodaOperConWfContabilia.setErrCode(null);
										tCodaOperConWfContabilia.setErrMsg(null);
										log.info("4.2.2:  chiamata invio_archiviazione_contabilita_Contabilia OK");
									} else {
										tCodaOperConWfContabilia.setEsitoElab("KO");
										tCodaOperConWfContabilia.setErrCode(new BigDecimal(errorCode));
										tCodaOperConWfContabilia.setErrMsg(lNuovaPropostaAtto2CompletaBean.getErrMsgEventoContabilia());
										log.info("4.2.2.ERR: errore da chiamata: " + errorCode + " - " + lNuovaPropostaAtto2CompletaBean.getErrMsgEventoContabilia());
									}
									
									
								} catch (Exception e) {
									tCodaOperConWfContabilia.setErrCode(new BigDecimal("0"));
									tCodaOperConWfContabilia.setErrMsg(e.getMessage());
									tCodaOperConWfContabilia.setEsitoElab("KO");
									log.error("4.2.ERR Chiamata NON inviata " + e.getMessage(), e);
								} finally {
									log.info("4.3 Tracciamneto chiamata ");
									tCodaOperConWfContabilia.setNumTry(tCodaOperConWfContabilia.getNumTry().add(new BigDecimal("1")));
									tCodaOperConWfContabilia.setTsLastTry(new Date());
									avanzamentoDAOImpl.updateAvanzamento(tCodaOperConWfContabilia,sessionJob);
									
									
/*									fileWriter.append(tCodaOperConWfWf.getTaskInstanceId() + ";"
											+ tCodaOperConWfContabilia.getTaskDefKey() + ";" + tCodaOperConWfContabilia.getIdProcess()
											+ ";" + tCodaOperConWfContabilia.getEsitoElab() + ";" + tCodaOperConWfContabilia.getErrMsg()
											+ ";" + tCodaOperConWfContabilia.getNumTry() + "\n");
									fileWriter.flush();*/
									
								}
								
							}//chiudo //STEP:invio_archiviazione_contabilita_Contabilia
							
							
						
					}
				}
				log.info("5.1 FINE Caricamento");
			

		} catch (Exception e) {
			log.error("3.2 Caricamento interrotto; Errore generico", e);
			throw e;
		} finally {
/*			if (fileWriter != null) {
				fileWriter.close();
			} else {
				String pathReport = config.getReportPath() + "/" + dateFolders();
				new File(pathReport).mkdirs();
				new File(pathReport + "/report_errore.csv");
			}*/
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
		pMailToSendBean.setAddressTo(config.getIndirizziMailSegnalazioni().getAddressTo());
		
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
	

}