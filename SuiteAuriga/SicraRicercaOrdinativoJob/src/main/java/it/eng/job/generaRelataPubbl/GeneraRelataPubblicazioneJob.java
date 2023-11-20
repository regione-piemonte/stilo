/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddverdocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdverdocBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.AdddocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.AddverdocImpl;
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
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.database.utility.HibernateUtil;
import it.eng.core.business.TPagingList;
import it.eng.document.function.bean.AllegatoDocumentoInBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FileStoreBean;
import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.entity.BmtJobParameters;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.job.SpringHelper;
import it.eng.job.esportazioneAsincrona.bean.JobGeneralConfigurationBean;
import it.eng.job.exception.AurigaDAOException;
import it.eng.job.exception.DocumentUtilException;
import it.eng.job.exception.StoreException;
import it.eng.job.util.StorageImplementation;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;
import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.servlet.bean.Firmatari;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utils.AurigaFileUtils;
import it.eng.utils.DocumentStorage;
import it.eng.utils.ModelliUtility;
import it.eng.utils.RelataAttoParamBean;
import it.eng.utils.XmlUtility;
import it.eng.xml.XmlUtilitySerializer;
/**
 * 
 *
 */
@Job(type = "GeneraRelataPubblicazioneJob")
@Named
public class GeneraRelataPubblicazioneJob extends AbstractJob<String> {

	private static final String JOBATTRKEY_SCHEMA = "schema";
	private static final String JOBATTRKEY_LOCALE = "locale";
	private static final String JOBATTRKEY_DATE_FORMAT = "dateFormat";
	private static final String JOBATTRKEY_TOKEN = "token";
	private static final String JOBATTRKEY_Id_LAVORO= "id_lavoro";
	private static final String JOBATTRKEY_TIPO_DOMINIO = "tipoDominio";
	private static final String JOBATTRKEY_ENTITY_PACKAGE = "entityPackage";
	private static final String JOBATTRKEY_FIRMA_REMOTA = "firmaremota";
	private static final String JOBATTRKEY_HIBERNATE_CONFIG_FILE = "hibernateConfigFile";
	
	private Logger logger = Logger.getLogger(GeneraRelataPubblicazioneJob.class);

	private static final String JOB_CLASS_NAME = "JobBatch";

	private ApplicationContext context;
	private AurigaLoginBean aurigaLoginBean;

	private String schema;
	private Locale locale;
	private it.eng.utility.DocumentConfiguration lDocumentConfiguration;
	private Integer tipoDominio;
	protected it.eng.database.dao.DaoBmtJobParameters bmtJobParametersClient =new it.eng.database.dao.DaoBmtJobParameters();
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
		System.out.println("process execute");
		boolean validConfiguration = configura();
		String entityPackage = (String) getAttribute(JOBATTRKEY_ENTITY_PACKAGE);
		String hibernateConfigFile = (String) getAttribute(JOBATTRKEY_HIBERNATE_CONFIG_FILE);
		HibernateUtil.setEntitypackage( entityPackage );
		SessionFactory sf = HibernateUtil.buildSessionFactory( hibernateConfigFile, JOB_CLASS_NAME );
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
	}

	protected void elabora() {

		ExecutionResultBean<TPagingList<JobBean>> result = retrieveJobs();
		
		if (result.isSuccessful()) {

			TPagingList<JobBean> results = result.getResult();
            System.out.print(String.format("Sono stati trovati %1$s job da esportare", results.getData().size()));
			logger.info(String.format("Sono stati trovati %1$s job da esportare", results.getData().size()));
			it.eng.spring.utility.SpringAppContext.setContext(context);
			
            // per ogni job trovato effettuo la generazione del file di
			// esportazione
			for (JobBean currentJob : results.getData()) {
                
				
				// esportazione è in stato E
				if (currentJob.getIdJob()!= null) {

					logger.info("Iniziata elaborazione del job " + currentJob.getIdJob());
					AllegatoDocumentoInBean inBean = new AllegatoDocumentoInBean();
					RelataAttoParamBean bean = new RelataAttoParamBean();
					// aggiorno il login bean con i dati del job corrente
					/*try {

						updateLoginBeanSpaoo(currentJob);

					} catch (Exception e) {

						// poichè si tratta di un errore di autenticazione, mi
						// aspetto sia successo per problemi nel server, quindi
						// non
						// pongo il job in stato di errore
						logger.error(
								String.format("Durante l'autenticazione si è verificata la seguente eccezione %1$s",
										ExceptionUtils.getFullStackTrace(e)));
						continue;
					}*/
					
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
					filterBean.setParameterId(new BigDecimal(4));
					filterBean.setParameterSubtype("XML_LIST_UD_PUBBL_TERM");
                    TFilterFetch<JobParameterBean> filter = new TFilterFetch<JobParameterBean>();
					filter.setFilter(filterBean);
					
					logger.info("Iniziata elaborazione del XML_LIST_UD_PUBBL_TERM " + currentJob.getIdJob());
					
					JobParameterBean filterBean1 = new JobParameterBean();
					filterBean1.setIdJob(currentJob.getIdJob());
					filterBean1.setParameterId(new BigDecimal(2));
					filterBean1.setParameterSubtype("ID_DOC_TYPE_RELATA_PUBBL");
                    TFilterFetch<JobParameterBean> filter1 = new TFilterFetch<JobParameterBean>();
					filter1.setFilter(filterBean1);
					logger.info("Iniziata elaborazione del ID_DOC_TYPE_RELATA_PUBBL " + currentJob.getIdJob());
					JobParameterBean filterBean2 = new JobParameterBean();
					filterBean2.setIdJob(currentJob.getIdJob());
					filterBean2.setParameterId(new BigDecimal(3));
					filterBean2.setParameterSubtype("NOME_MODELLO_IN");
                    TFilterFetch<JobParameterBean> filter2 = new TFilterFetch<JobParameterBean>();
					filter2.setFilter(filterBean2);
					logger.info("Iniziata elaborazione del NOME_MODELLO_IN " + currentJob.getIdJob());
					JobParameterBean filterBean3 = new JobParameterBean();
					filterBean3.setIdJob(currentJob.getIdJob());
					filterBean3.setParameterId(new BigDecimal(1));
					filterBean3.setParameterSubtype("URI_MODELLO_RELATA");
                    TFilterFetch<JobParameterBean> filter3 = new TFilterFetch<JobParameterBean>();
					filter3.setFilter(filterBean3);
					logger.info("Iniziata elaborazione del URI_MODELLO_RELATA " + currentJob.getIdJob());
					try {
                        
						
						BmtJobParameters parameter1 = bmtJobParametersClient.get(filterBean1);
						inBean.setNomeDocType(parameter1.getParameterValue());
						BmtJobParameters parameter2 = bmtJobParametersClient
								.get(filterBean2);
						inBean.setOggetto(parameter2.getParameterValue());
						BmtJobParameters parameter3 = bmtJobParametersClient
								.get(filterBean3);
						bean.setUriModello(parameter3.getParameterValue());
						
						logger.info(String.format("Il job %1$s è stato caricato in con successo ",currentJob.getIdJob()));
                        
						BmtJobParameters parameter = bmtJobParametersClient
								.get(filterBean);

						if (parameter != null && parameter.getParameterValue()!=null)
						{
							logger.debug("XML_LIST_UD_PUBBL_TERM: "+parameter.getParameterValue());
					        
							
								
							StringReader sr = new StringReader(parameter.getParameterValue());
							Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
							String app ="";
							String error ="";
							for (int i = 0; i < lista.getRiga().size(); i++) {
								
                               try {
								Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
								String idUd = v.get(0);
								logger.info("idUd: "+idUd);
								app=idUd;
								logger.info("INIZIO GENERA MODELLO");
								
								
								bean.setTipoModello("odt_con_freemarkers");
								bean.setNomeModello(inBean.getOggetto());
								bean.setIdUd(idUd);
								
								logger.info("bean.setNomeModello: "+bean.getNomeModello());
								
								logger.info("ModelliUtility");
								File allegatoDoc = ModelliUtility.getInstance().generaRelataAtto(aurigaLoginBean, bean);
								
								logger.info("FINE GENERA MODELLO");
								String firmaremota = (String) getAttribute(JOBATTRKEY_FIRMA_REMOTA);
								
								if(firmaremota != null && 
								   !firmaremota.equals("") &&
								   firmaremota.toLowerCase().equals("si"))
								{	
								logger.info("INIZIO FIRMA FILE");
								
								byte[] fileFirmato = null;
								HsmArubaPdf pades = new HsmArubaPdf();
								fileFirmato = pades.firmaPades(allegatoDoc);
								
								logger.info("FINE FIRMA FILE");
								logger.info("INIZIO SCRITTURA FILE");
								if (fileFirmato != null )
								{	
								try {
									FileUtils.writeByteArrayToFile(
											allegatoDoc,
											fileFirmato);
								} catch (IOException e) {
									logger.error("Errore di firma: "+e.getMessage());
								}catch(Exception e)
								{
								 logger.error("Exception: "+e.getMessage());
								}
								}
								/*
								 * Solo per sviluppo
								 */
								/*File allegatoDocLoc = new File("C:/Users/Bcsoft/Downloads/firmatoArubaPades.pdf");
								try {
									FileUtils.writeByteArrayToFile(
											allegatoDocLoc,
											fileFirmato);
								} catch (IOException e) {
									logger.error("Errore di firma: "+e.getMessage());
								}*/
								
							//	String uri = StorageImplementation.getStorage().store(allegatoDoc);
								
								logger.info("FINE SCRITTURA FILE");
								}//chiusira firma remota
								logger.info("INIZIO INSERIMENTO ALLEGATO");
								
								inBean.setIdUD(new BigDecimal(idUd));
								inserimentoFileAllegati(allegatoDoc,
										                inBean);
								
								logger.info("FINE INSERIMENTO ALLEGATO : "+idUd);
							} catch (Exception e) {
								error = error+ " Errore [id_ud "+app+": "+e.getMessage() +" ] ";
								logger.error(
										"Errore ["+app+": "+e.getMessage());
								try {
									currentJob.setMessage("Errore[LIST_UD] : "+error);
									markJobAsInError(currentJob);
								} catch (Exception e1) {
									logger.error(String.format("Non è stato possibile porre in stato X il job %1$s",
											currentJob.getIdJob()));
								}

								continue;
							}
							}//chiudo for
							
						}//chiudo if
						
						if(!currentJob.getStatus().equals("X"))
						{	
						 markJobAsCompleted(currentJob);
						} 

					} catch (Exception e) {
						logger.error(
								"Errore: "+e.getMessage());
						try {
							currentJob.setMessage("Errore: "+e.getMessage());
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
	}

	/**
	 * Estrae i job di cui generare i file di esportazione, filtrati i job
	 * <ul>
	 * <li>Tipo = GEN_RELATE_PUBBL</li>
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
			filterBean.setTipo("GEN_RELATE_PUBBL");
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
		StorageService storage = DocumentStorage.getStorage(null);
		String storageError = null;
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		ApplicationContext appContext = new ClassPathXmlApplicationContext("document.xml");
		it.eng.utility.DocumentConfiguration lDocumentConfiguration = (it.eng.utility.DocumentConfiguration) appContext
				.getBean("DocumentConfiguration");
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
					//	lAllegatoStoreBean.setFirmato(Flag.SETTED);
						lAllegatoStoreBean.setIdDocType(Integer.parseInt(inBean.getNomeDocType()));
						lAllegatoStoreBean.setFlgEreditaPermessi("1");
						
						RERFileStoreBean lFileStoreBean = new RERFileStoreBean();
						lFileStoreBean.setIdUd(inBean.getIdUD().toString());
						lFileStoreBean.setDesNaturaRelVsUD("allegato");
						lFileStoreBean.setIdDocType(inBean.getNomeDocType());
						lFileStoreBean.setFlgEreditaPermessi("1");
						lFileStoreBean.setDescrizione(inBean.getOggetto());
						String fileUrl = "file://"+allegatoDoc.getPath();
						logger.info("fileUrl: "+fileUrl);
						logger.info("Uri: "+uriStoredFile);
						logger.info("setDescrizione: "+inBean.getOggetto());
						logger.info("allegatoDoc: "+allegatoDoc.getName());
						try
						{	
						 lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFileNoOut(fileUrl, allegatoDoc.getName(), false, null,false);
						} catch (Exception e) {
							logger.error("primo tentativo allegato: "+e.getMessage());
							try {
								String fileUrl1 = "file://"+allegatoDoc.getPath();
								lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFileNoOut(fileUrl1, allegatoDoc.getName(), false, null,false);
							} catch (Exception e2) {
								String errMsg = e2.getMessage();
								logger.error("Secondo ed ultimo tentativo allegato: "+errMsg);
								throw new Exception("Si è¨ verificato un errore durante il controllo del file  " +allegatoDoc.getName());
								
							}
						}
						logger.info("*** FINE RECUPERO FILE E FILEOP ALLEGATO ****");
						if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
							try {
								throw new Exception("Si è¨ verificato un errore durante il controllo del file  " + allegatoDoc.getName());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								logger.error("Secondo ed ultimo tentativo allegato: "+e.getMessage());
							
							}
						}
						//lFileStoreBean.setNoteFile(doc.getDisplayFilename());							
						lFileStoreBean.setUri(uriStoredFile);
						logger.info("lMimeTypeFirmaBean.getImpronta(): "+lMimeTypeFirmaBean.getImpronta());
						lFileStoreBean.setImpronta(lMimeTypeFirmaBean.getImpronta());
						lFileStoreBean.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
						lFileStoreBean.setEncoding(lDocumentConfiguration.getEncoding().value());
						lFileStoreBean.setMimetype(lMimeTypeFirmaBean.getMimetype());
						
						if (lMimeTypeFirmaBean.getFirmatari() != null) {
							List<Firmatario> lList = new ArrayList<Firmatario>();
							for (String lString : lMimeTypeFirmaBean.getFirmatari()) {
								Firmatario lFirmatario = new Firmatario();
								lFirmatario.setCommonName(lString);
								lList.add(lFirmatario);
							}
							//lGenericFile.setFirmatari(lList);
							lFileStoreBean.setFirmatari(lList);
							lFileStoreBean.setFirmato(Flag.SETTED);
						}
						if (lMimeTypeFirmaBean.getBuste() != null) {
							List<Firmatario> lList = new ArrayList<Firmatario>();
							for (Firmatari busta : lMimeTypeFirmaBean.getBuste()) {
								Firmatario lFirmatario = new Firmatario();
								lFirmatario.setCommonName(busta.getNomeFirmatario());
								lFirmatario.setDataOraFirma(busta.getDataFirma());
								lFirmatario.setFirmatoDaAuriga(busta.isFirmaExtraAuriga() ? Flag.NOT_SETTED : Flag.SETTED);
								lFirmatario.setDataOraVerificaFirma(busta.getDataVerificaFirma());
								lFirmatario.setFirmaNonValida(busta.isFirmaValida() ? Flag.NOT_SETTED : Flag.SETTED);
								lFirmatario.setInfoFirma(busta.getInfoFirma());
								lFirmatario.setTipoFirma(busta.getTipoFirma());
								lFirmatario.setBustaEsterna((busta.getFiglioDi() != null && "1".equalsIgnoreCase(busta.getFiglioDi())) ? Flag.SETTED : Flag.NOT_SETTED);
								lList.add(lFirmatario);
							}
							lFileStoreBean.setFirmatari(lList);
							lFileStoreBean.setFirmato(Flag.SETTED);
						}
						
						//lFileStoreBean.setAlgoritmo(algoritmoMd5);								
						lFileStoreBean.setDimensione(lMimeTypeFirmaBean.getBytes());
						lFileStoreBean.setDisplayFilename(allegatoDoc.getName());
						
						String attributi = lXmlUtilitySerializer.bindXmlCompleta(lFileStoreBean);
						logger.info("attributi: "+attributi);
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
							logger.error("Il servizio DmpkCoreAdddoc ha restituito l'errore: " + resultAllegato.getDefaultMessage());
							logger.error("Il servizio DmpkCoreAdddoc ha restituito l'errore: " + resultAllegato.isInError() );
							logger.error("Il servizio DmpkCoreAdddoc ha restituito l'errore: " + resultAllegato.getErrorContext());
							logger.error("Il servizio DmpkCoreAdddoc ha restituito l'errore: " + resultAllegato.getErrorCode());
							logger.error("Il servizio DmpkCoreAdddoc ha restituito l'errore: " + resultAllegato.getResultBean().getErrcontextout());
							logger.error("Il servizio DmpkCoreAdddoc ha restituito l'errore: " + resultAllegato.getStoreName());
							logger.error("Il servizio DmpkCoreAdddoc ha restituito l'errore: " + resultAllegato.getResultBean().getErrmsgout());
							
							throw new Exception(resultAllegato.getDefaultMessage());
				/*		} else {
							
								FileInfoBean info = getFileInfo(allegatoDoc);
								RebuildedFile lRebuildedFile = new RebuildedFile();
								lRebuildedFile.setFile(allegatoDoc);
								lRebuildedFile.setInfo(info);
								lRebuildedFile.setIdDocumento(resultAllegato.getResultBean().getIddocout());
								lRebuildedFile.setPosizione(1);
								versioni.add(lRebuildedFile);
							
						}
		

			// Parte di versionamento
			fileErrors = versionamentiInTransaction(versioni, session);*/
						} else{
							logger.info("Il servizio DmpkCoreAdddoc ha restituito getDefaultMessage: " + resultAllegato.getDefaultMessage());
						}
							

			/*if (!fileErrors.isEmpty()) {
				logger.error("Mappa errori versionamento : \n "+ fileErrors.toString());
				throw new Exception("Errore durante il versionamento dei file di attacch"); //TODO : riscrivere
			}*/

		} catch (Exception e) {
			logger.error("Errore durante l'attacch dei file all UD: " + e.getMessage(), e);
			String errMsg = e.getMessage();
			logger.error(errMsg);
			if (transaction != null)
			{	
				try {
					transaction.rollback();
				} catch (Exception ex) {
					logger.error(ex);
				}
			}
			throw new Exception (errMsg);
		}finally {
			if (transaction != null && !transaction.wasCommitted()) {
				try {
					transaction.commit();
				} catch (Exception e) {
					logger.error(e);
				}

			} // chiudo if
			try {
				HibernateUtil.release(session);
			} catch (Exception e) {
				throw new AurigaDAOException(e);
			}
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
		
		lDocumentConfiguration = (it.eng.utility.DocumentConfiguration) context.getBean("DocumentConfiguration");
		valid = schema != null && !schema.isEmpty();

		if (!valid) {
			logger.error("Schema non configurato");
			return valid;
		}

		aurigaLoginBean = new AurigaLoginBean();

		String localeValue = "it";

		

		aurigaLoginBean.setLinguaApplicazione(localeValue);
		aurigaLoginBean.setSchema(schema);

		String tipoDominioValue = (String) getAttribute(JOBATTRKEY_TIPO_DOMINIO);

		valid = tipoDominioValue != null && !tipoDominioValue.isEmpty();
		
		String token = (String) getAttribute(JOBATTRKEY_TOKEN);
		String idLavoro = (String) getAttribute(JOBATTRKEY_Id_LAVORO);
		
		if (valid) {

			tipoDominio = Integer.valueOf(tipoDominioValue);

			SpecializzazioneBean specializzazioneBean = new SpecializzazioneBean();
			specializzazioneBean.setTipoDominio(Integer.valueOf(tipoDominio));

			aurigaLoginBean.setSpecializzazioneBean(specializzazioneBean);
			aurigaLoginBean.setToken(token);
			aurigaLoginBean.setIdUserLavoro(idLavoro);
		} else {
			logger.error("Tipo dominio non specificato");
			return valid;
		}

		locale = new Locale(localeValue);

		SubjectBean subject = new SubjectBean();
		subject.setIdDominio(tipoDominio+"");
		SubjectUtil.subject.set(subject);
        
		logger.info("aurigaLoginBean: "+aurigaLoginBean.getSchema());
		
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
	public FileInfoBean getFileInfo(File fileDoc) throws BeansException, DocumentUtilException {
		FileInfoBean lFileInfoFileBean = new FileInfoBean();
		try {
			String algoritmo = lDocumentConfiguration.getAlgoritmo().value();
			String encoding = lDocumentConfiguration.getEncoding().value();
			lFileInfoFileBean = AurigaFileUtils.createFileInfoBean(fileDoc, algoritmo, encoding);
			
			
			
		} catch (Exception e) {
			throw new DocumentUtilException(e);
		}

		return lFileInfoFileBean;
	}
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
			lFileStoreBean.setDisplayFilename(lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getDisplayFilename());            
			lFileStoreBean.setEncoding(lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getEncoding());
			lFileStoreBean.setImpronta(lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getImpronta());
			lFileStoreBean.setMimetype(lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getMimetype());
			
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
}
