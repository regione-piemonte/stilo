/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocAggiornajobstamparegistroBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGetjobsstamparegprontixgenpdfBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGetpaginaxmljobstamparegBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.dao.beans.JobParameterBean;
import it.eng.client.DaoBmtJobParameters;
import it.eng.client.DmpkRegistrazionedocAggiornajobstamparegistro;
import it.eng.client.DmpkRegistrazionedocGetjobsstamparegprontixgenpdf;
import it.eng.client.DmpkRegistrazionedocGetpaginaxmljobstampareg;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.database.utility.HibernateUtil;
import it.eng.document.function.bean.AllegatoDocumentoInBean;
import it.eng.document.function.bean.FileStoreBean;
import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.job.SpringHelper;
import it.eng.job.generaRelataPubbl.HsmArubaPdf;
import it.eng.job.registri.bean.JobStampaRegistriJobConfBean;
import it.eng.job.registri.bean.StampaRegistriProtocollazioneConfig;
import it.eng.job.util.AurigaLoginUtil;
import it.eng.job.util.FTPManager;
import it.eng.job.util.StorageImplementation;
import it.eng.services.document.DocumentUtil;
import it.eng.services.exception.DocumentUtilException;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.FileUtil;
import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.xml.XmlListaUtility;

@Job(type = "StampaRegistroProtocollazioniJob")
@Named
public class StampaRegistroProtocollazioniJob extends AbstractJob<String> {

	protected AurigaLoginUtil aurigaLoginUtil;

	private static final String JOBATTRKEY_ENTITY_PACKAGE = "entityPackage";
	private static final String JOBATTRKEY_HIBERNATE_CONFIG_FILE = "hibernateConfigFile";
	private static final String JOBATTRKEY_SCHEMA = "schema";
	private static final String JOB_CLASS_NAME = StampaRegistroProtocollazioniJob.class.getName();
	private static final String JOBATTRKEY_LOCALE = "locale";
	private static final String JOBATTRKEY_FIRMA_REMOTA = "firmaremota";
	
	private static Locale locale;

	private String schema; 

	private AurigaLoginBean loginBean;

	private Logger logger = Logger.getLogger(StampaRegistroProtocollazioniJob.class);

	private ApplicationContext context;

	private StampaRegistriProtocollazioneConfig stampaRegistriProtocollazioneConfig;

	private StampaRegistriProtocollazioneConfig stampaRegRepertoriAttiConfig;

	private StampaRegistriProtocollazioneConfig stampaRegRepertoriConfig;

	private StampaRegistriProtocollazioneConfig stampaRegProposteAttiConfig;
	
	private StampaRegistriProtocollazioneConfig stampaRegPubblicazioniConfig;
	
	private StampaRegistriProtocollazioneConfig stampaRegEmergenzaConfig;
	
	protected DaoBmtJobParameters daoBmtJobParameters;

	@Override
	public List<String> load() {

		ArrayList<String> ret = new ArrayList<String>();
		ret.add(JOB_CLASS_NAME);

		String entityPackage = (String) getAttribute(JOBATTRKEY_ENTITY_PACKAGE);

		logger.debug("Entity package " + entityPackage);

		String hibernateConfigFile = (String) getAttribute(JOBATTRKEY_HIBERNATE_CONFIG_FILE);

		logger.debug("Hibernate config file " + hibernateConfigFile);

		HibernateUtil.setEntitypackage(entityPackage);

		logger.debug("Set entity package");

		HibernateUtil.buildSessionFactory(hibernateConfigFile, JOB_CLASS_NAME);

		logger.debug("Built session factory ");
		
		locale = new Locale((String) getAttribute(JOBATTRKEY_LOCALE));
		
		return ret;
	}

	@Override
	public void execute(String schema) {

		try {

			configura();

			try {
				elabora();
			} catch (Exception e) {
				logger.error("Si è verificata la seguente eccezione durante l'esecuzione del job ", e);
			}

		} finally {
			HibernateUtil.closeConnection();
		}
	}

	private void configura() {

		context = SpringHelper.getMainApplicationContext();
		
		schema = (String) getAttribute(JOBATTRKEY_SCHEMA);

		aurigaLoginUtil = (AurigaLoginUtil) context.getBean("aurigaLoginUtil");

		stampaRegistriProtocollazioneConfig = (StampaRegistriProtocollazioneConfig) context.getBean("StampaRegistriProtocollazioneConfig");

		stampaRegRepertoriAttiConfig = (StampaRegistriProtocollazioneConfig) context.getBean("StampaRegRepertoriAttiConfig");

		stampaRegRepertoriConfig = (StampaRegistriProtocollazioneConfig) context.getBean("StampaRegRepertoriConfig");

		stampaRegProposteAttiConfig = (StampaRegistriProtocollazioneConfig) context.getBean("StampaRegProposteAttiConfig");
		
		stampaRegPubblicazioniConfig = (StampaRegistriProtocollazioneConfig) context.getBean("StampaRegPubblicazioniConfig");
		
		stampaRegEmergenzaConfig = (StampaRegistriProtocollazioneConfig) context.getBean("StampaRegEmergenzaConfig");
		
		//bean utilizzato come base per la successiva autenticazione
		loginBean = new AurigaLoginBean();
		
		loginBean.setSchema(schema);
		loginBean.setLinguaApplicazione("it");
		
		loginBean.setSpecializzazioneBean(new SpecializzazioneBean());
		
		daoBmtJobParameters = (DaoBmtJobParameters) context.getBean("bmtJobParametersClient");
		
	}

	protected void elabora() throws Exception {
		
		context = SpringHelper.getMainApplicationContext();
		logger.info("TEST context " + context);
		// it.eng.utility.jobmanager.SpringAppContext.setContext(context);
		SpringAppContext.setContext(context);
		
		DmpkRegistrazionedocGetjobsstamparegprontixgenpdfBean jobsRetrieverInput = new DmpkRegistrazionedocGetjobsstamparegprontixgenpdfBean();

		DmpkRegistrazionedocGetjobsstamparegprontixgenpdf jobsRetriever = new DmpkRegistrazionedocGetjobsstamparegprontixgenpdf();

		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(schema);
		
		StoreResultBean<DmpkRegistrazionedocGetjobsstamparegprontixgenpdfBean> jobsRetrieverResult = jobsRetriever
				.execute(locale, lSchemaBean, jobsRetrieverInput);
		
		if (jobsRetrieverResult.getDefaultMessage() != null) {
			String errorMessage = "";
			if (jobsRetrieverResult.getErrorContext() != null) {
				errorMessage += jobsRetrieverResult.getErrorContext() + " - ";
			}
			if (jobsRetrieverResult.getErrorCode() != null) {
				errorMessage += jobsRetrieverResult.getErrorCode() + " - ";
			}
			errorMessage += jobsRetrieverResult.getDefaultMessage();
			throw new Exception(errorMessage);
		}
		
		//if (jobsRetrieverResult.getDefaultMessage() != null) {
		//	throw new Exception(jobsRetrieverResult.getDefaultMessage());
		//}

		if (jobsRetrieverResult.getResultBean().getListajobxmlout() != null) {

			List<JobStampaRegistriJobConfBean> data = XmlListaUtility.recuperaLista(
					jobsRetrieverResult.getResultBean().getListajobxmlout(), JobStampaRegistriJobConfBean.class);

			logger.info(String.format("Sono stati trovati %1$s job da eseguire", data.size()));
			
			for (JobStampaRegistriJobConfBean currentJob : data) {
			
				logger.info("Elaborazione del job " + currentJob.getIdJob());
				
				aurigaLoginUtil.login(currentJob.getIdDominio(), currentJob.getTipoDominio(), currentJob.getUsername(), loginBean);
				
				List<File> fileDatiRegList = new ArrayList<File>();
				List<File> fileDatiRegVarList = new ArrayList<File>();
               
				logger.info("currentJob.isFlgRegistroGenerato() " + currentJob.isFlgRegistroGenerato());
				
				try {

					File fileRegistroGenerato = null;

					if (!currentJob.isFlgRegistroGenerato()) {
						
						logger.info("currentJob.isFlgRegistroGenerato() IF " + currentJob.isFlgRegistroGenerato());
						
						ArrayList<File> fileToMerge = new ArrayList<File>();	
						
						if(currentJob.getNroPagine() != null && currentJob.getNroPagine().intValue() > 0){

							for (int n = 1; n <= currentJob.getNroPagine(); n++) {
	
								DmpkRegistrazionedocGetpaginaxmljobstamparegBean jobPagineRetrieverInput = new DmpkRegistrazionedocGetpaginaxmljobstamparegBean();
								jobPagineRetrieverInput.setIdjobin(currentJob.getIdJob());
								jobPagineRetrieverInput.setNropaginain(n);
	
								DmpkRegistrazionedocGetpaginaxmljobstampareg jobPagineRetriever = new DmpkRegistrazionedocGetpaginaxmljobstampareg();
	
								StoreResultBean<DmpkRegistrazionedocGetpaginaxmljobstamparegBean> jobPagineOutput = jobPagineRetriever
										.execute(locale, lSchemaBean, jobPagineRetrieverInput);
	
								if (jobPagineOutput.getDefaultMessage() != null) {
									String errorMessage = "";
									if (jobPagineOutput.getErrorContext() != null) {
										errorMessage += jobPagineOutput.getErrorContext() + " - ";
									}
									if (jobPagineOutput.getErrorCode() != null) {
										errorMessage += jobPagineOutput.getErrorCode() + " - ";
									}
									errorMessage += jobPagineOutput.getDefaultMessage();
									throw new Exception(errorMessage);
								}
								logger.info("currentJob.getNroPagine() " + currentJob.getNroPagine());
								
								File file = File.createTempFile("datireg", ".xml");
								FileUtils.writeStringToFile(file, jobPagineOutput.getResultBean().getPaginaxmlout());
								fileDatiRegList.add(file);
								
								logger.info("datireg " + file.getAbsolutePath());
							}
						}
						
						File fileRegistro = File.createTempFile("stamparegprot", ".pdf");
						StampaRegProt.getInstance().stampaRegProtMassive(fileRegistro, fileDatiRegList,
								currentJob.getHeader(), currentJob.getFooter(), currentJob.getTipoReport(), getLayoutConfig(currentJob.getTipoReport()));
						
						fileToMerge.add(fileRegistro);
						
						logger.info("fileRegistro " + fileRegistro.getAbsolutePath());
						if(currentJob.getNroPagineVar() != null && currentJob.getNroPagineVar().intValue() > 0){

							for (int n = 1; n <= currentJob.getNroPagineVar(); n++) {
								
								DmpkRegistrazionedocGetpaginaxmljobstamparegBean jobPagineRetrieverInput = new DmpkRegistrazionedocGetpaginaxmljobstamparegBean();
								jobPagineRetrieverInput.setIdjobin(currentJob.getIdJob());								
								jobPagineRetrieverInput.setNropaginain(currentJob.getNroPagine() != null ? n + currentJob.getNroPagine().intValue() : n);
	
								DmpkRegistrazionedocGetpaginaxmljobstampareg jobPagineRetriever = new DmpkRegistrazionedocGetpaginaxmljobstampareg();
	
								StoreResultBean<DmpkRegistrazionedocGetpaginaxmljobstamparegBean> jobPagineOutput = jobPagineRetriever
										.execute(locale, lSchemaBean, jobPagineRetrieverInput);
	
								if (jobPagineOutput.getDefaultMessage() != null) {
									String errorMessage = "";
									if (jobPagineOutput.getErrorContext() != null) {
										errorMessage += jobPagineOutput.getErrorContext() + " - ";
									}
									if (jobPagineOutput.getErrorCode() != null) {
										errorMessage += jobPagineOutput.getErrorCode() + " - ";
									}
									errorMessage += jobPagineOutput.getDefaultMessage();
									throw new Exception(errorMessage);
								}
	
								File file = File.createTempFile("datiregvar", ".xml");
								FileUtils.writeStringToFile(file, jobPagineOutput.getResultBean().getPaginaxmlout());
								fileDatiRegVarList.add(file);
								logger.info("datiregvar " + file.getAbsolutePath());
							}
							
							File fileVarRegistro = File.createTempFile("stampavarregprot", ".pdf");
							StampaRegProt.getInstance().stampaRegProtMassive(fileVarRegistro, fileDatiRegVarList,
									currentJob.getHeaderVar(), currentJob.getFooterVar(), currentJob.getTipoReport(), getLayoutConfig(currentJob.getTipoReport()));
							
							fileToMerge.add(fileVarRegistro);
							logger.info("fileVarRegistro " + fileVarRegistro.getAbsolutePath());
						}

						File fileOut = null;
						if(fileToMerge.size() == 1) {
							fileOut = fileToMerge.get(0);						
						} else {
							fileOut = File.createTempFile("mergestamparegprot", ".pdf");
							StampaRegProt.concatFiles(fileToMerge, fileOut);
						}
						String uri = StorageImplementation.getStorage().store(fileOut);
						logger.info("uri " + uri);
						fileRegistroGenerato = StorageImplementation.getStorage().extractFile(uri);
						logger.info("fileRegistroGenerato " + fileRegistroGenerato.getAbsolutePath());
						
						boolean flgFirmato = false;
						String firmaremota = (String) getAttribute(JOBATTRKEY_FIRMA_REMOTA);
						
						if (firmaremota != null && 
						    !firmaremota.equals("") &&
						    firmaremota.toLowerCase().equals("si")) {
							
							logger.info("INIZIO FIRMA FILE");
							
							byte[] fileFirmato = null;
							HsmArubaPdf pades = new HsmArubaPdf();
							fileFirmato = pades.firmaPades(fileRegistroGenerato);
							
							logger.info("FINE FIRMA FILE");
							logger.info("INIZIO SCRITTURA FILE");
							if (fileFirmato != null ) {	
								try {
									FileUtils.writeByteArrayToFile(
											fileRegistroGenerato,
											fileFirmato);
									flgFirmato = true;
								} catch (IOException e) {
									logger.error("Errore di firma: "+e.getMessage());
								} catch(Exception e) {
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
							// se il file è stato firmato dovrò chiamare FileOperation
							
							//AllegatoDocumentoInBean allegato = DocumentUtil.preparaAllegatoDocumentoInBean(fileRegistroGenerato.getName(), fileRegistroGenerato, flgFirmato);
							
							//logger.info("allegato " + allegato.getIdUD());
							//DocumentUtil.versionaDocumento(aurigaLoginUtil, false, currentJob.getIdDoc(), fileRegistroGenerato,	allegato);
						}
						
						logger.info("INIZIO INSERIMENTO ALLEGATO");
						AllegatoDocumentoInBean allegato = preparaAllegatoDocumentoInBean(currentJob.getNomeFileExport(), fileRegistroGenerato, flgFirmato);
						logger.info("allegato con nome " + allegato.getFileStoreBean().getDisplayFilename());
						
						logger.info("allegato " + allegato.getIdUD());
						DocumentUtil.versionaDocumento(aurigaLoginUtil, false, currentJob.getIdDoc(), fileRegistroGenerato,	allegato);
						
						logger.info("FINE INSERIMENTO ALLEGATO");
					} else {

						fileRegistroGenerato = StorageImplementation.getStorage().extractFile(currentJob.getUriRegistroGenerato());

					}

					Integer flgInvioConservazione = null;

					if (currentJob.isFlgAutomatico()) {
						
						flgInvioConservazione = inviaInConservazione(currentJob, fileRegistroGenerato, flgInvioConservazione);
					}
					logger.info("flgInvioConservazione " + flgInvioConservazione);
					if (!currentJob.isFlgRegistroGenerato() || flgInvioConservazione != null) {

						aggiornaRecordStampaRegistroSuccesso(lSchemaBean, currentJob, flgInvioConservazione);
						
					}

				} catch (Exception e) {

					logger.error(e.getMessage(), e);

					aggiornaRecordStampaRegistroFallimento(lSchemaBean, currentJob, e);

				} finally {

					for (File file : fileDatiRegList) {
						FileUtil.deleteFile(file);
					}
					
					for (File file : fileDatiRegVarList) {
						FileUtil.deleteFile(file);
					}
					
				}
				logger.info("end ");
			}
		}
	}
	
	private AllegatoDocumentoInBean preparaAllegatoDocumentoInBean(String nomeFileDoc, File fileDoc, boolean flgFirmato) throws DocumentUtilException {
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		
		String fileUrl = "file://"+fileDoc.getPath();
		try {
			lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFileNoOut(fileUrl, nomeFileDoc, false, null, false);
		} catch (Exception e) {
			logger.info("Errore " + e.getMessage());
		}
		
		logger.info("nomeFileDoc: " + nomeFileDoc + " - nomeFileDocGenerato: " + fileDoc.getName());
		
		FileStoreBean fileStoreBean = new FileStoreBean();
		fileStoreBean.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
		fileStoreBean.setEncoding(lMimeTypeFirmaBean.getEncoding());
		fileStoreBean.setDimensione(fileDoc.length());
		fileStoreBean.setImpronta(lMimeTypeFirmaBean.getImpronta());
		fileStoreBean.setMimetype(lMimeTypeFirmaBean.getMimetype());
		fileStoreBean.setDisplayFilename(nomeFileDoc);
		
		if (flgFirmato) {
			fileStoreBean.setFirmato(Flag.SETTED);
			
			logger.info("Documento firmato " + flgFirmato);
		}
		else {
			fileStoreBean.setFirmato(Flag.NOT_SETTED);
			
			logger.info("Documento non firmato " + flgFirmato);
		}
		
		final String[] firmatari = lMimeTypeFirmaBean.getFirmatari();
		if (firmatari != null) {
			List<Firmatario> listaFirmatari = new ArrayList<Firmatario>();
			for (int i = 0; i < firmatari.length; i++) {
				final Firmatario firmatario = new Firmatario();
				firmatario.setCommonName(firmatari[i]);
				listaFirmatari.add(firmatario);
			}
			fileStoreBean.setFirmatari(listaFirmatari);
		}
		
		AllegatoDocumentoInBean allegatoInBean = new AllegatoDocumentoInBean();
		allegatoInBean.setFileStoreBean(fileStoreBean);

		return allegatoInBean;
	}
	
	private StampaRegistriProtocollazioneConfig getLayoutConfig(String tipoReport) throws Exception {

		if (tipoReport == null)
			throw new Exception("Il tipo di report e' sconosciuto.");

		if (tipoReport.equalsIgnoreCase("PROTOCOLLO")) {
			return stampaRegistriProtocollazioneConfig;
		} else if (tipoReport.equalsIgnoreCase("REPERTORIO_ATTI")) {
			return stampaRegRepertoriAttiConfig;
		} else if (tipoReport.equalsIgnoreCase("REPERTORIO")) {
			return stampaRegRepertoriConfig;
		} else if (tipoReport.equalsIgnoreCase("PROPOSTE_ATTO")) {
			return stampaRegProposteAttiConfig;
		}else if (tipoReport.equalsIgnoreCase("PUBBLICAZIONI")) {
			return stampaRegPubblicazioniConfig;
	    }else if (tipoReport.equalsIgnoreCase("EMERGENZA")) {
		    return stampaRegEmergenzaConfig;
		} else {
			throw new Exception("Il tipo di report e' sconosciuto.");
		}

	}

	/**
	 * @param currentJob
	 * @param fileRegistro
	 * @param flgInvioConservazione
	 * @return
	 */
	protected Integer inviaInConservazione(JobStampaRegistriJobConfBean currentJob, File fileRegistro,
			Integer flgInvioConservazione) {
		try {

			FTPManager lFTPManager = new FTPManager("stampaRegProtFtp.properties");
			if (lFTPManager.upload(Arrays.asList(new File[] { fileRegistro }),
					Arrays.asList(new String[] { currentJob.getNomeFileInvioConserv() }))) {
				
				File tempFileXml = File.createTempFile("xmldatiinvioconserv", ".xml");
				
				String xml = recuperaXmlDatiInvioConserv(currentJob.getIdJob(), currentJob.getIdParamXmlDatiInvioConserv());
				FileUtils.writeStringToFile(tempFileXml, xml);			
				
				File fileXml = StorageImplementation.getStorage().extractFile(StorageImplementation.getStorage().store(tempFileXml));
				if (lFTPManager.upload(Arrays.asList(new File[] { fileXml }),
						Arrays.asList(new String[] { currentJob.getNomeXmlDatiInvioConserv() }))) {
					flgInvioConservazione = Integer.valueOf(1);
				}
			}
			
		} catch (Exception e) {
			logger.error("Invio in conservazione fallito:" + e.getMessage());
		}
		return flgInvioConservazione;
	}
	
	/**
	 * Recupera l'xml dati invio in conservazione salvato tra i parametri del job
	 * 
	 * @param idJob
	 * @param parameterId
	 */
	protected String recuperaXmlDatiInvioConserv(BigDecimal idJob, BigDecimal parameterId) {

		JobParameterBean filterBean = new JobParameterBean();
		filterBean.setIdJob(idJob);
		filterBean.setParameterId(parameterId);

		TFilterFetch<JobParameterBean> filter = new TFilterFetch<JobParameterBean>();
		filter.setFilter(filterBean);

		try {

			TPagingList<JobParameterBean> result = daoBmtJobParameters.search(locale, loginBean, filter);

			JobParameterBean xml = result.getData().get(0);

			logger.info("Recuperato l'xml dati invio in conservazione");
			
			return xml.getParameterValue();

		} catch (Exception e) {

			logger.error("Impossibile recuperare l'xml dati invio in conservazione", e);

		}
		
		return null;
	}

	/**
	 * @param lSchemaBean
	 * @param currentJob
	 * @param e
	 * @throws Exception
	 */
	protected void aggiornaRecordStampaRegistroFallimento(SchemaBean lSchemaBean, JobStampaRegistriJobConfBean currentJob,
			Exception e) throws Exception {
		DmpkRegistrazionedocAggiornajobstamparegistroBean lAggiornajobstamparegistroInput = new DmpkRegistrazionedocAggiornajobstamparegistroBean();
		lAggiornajobstamparegistroInput.setIdjobin(currentJob.getIdJob());
		
		lAggiornajobstamparegistroInput.setEsitostampain("KO");
		lAggiornajobstamparegistroInput.setMotivierrorein(e.getMessage());

		aggiornaStatoStampaRegistroRecord(lSchemaBean, lAggiornajobstamparegistroInput);
	}

	/**
	 * @param lSchemaBean
	 * @param currentJob
	 * @param flgInvioConservazione
	 * @throws Exception
	 */
	protected void aggiornaRecordStampaRegistroSuccesso(SchemaBean lSchemaBean, JobStampaRegistriJobConfBean currentJob,
			Integer flgInvioConservazione) throws Exception {
		
		DmpkRegistrazionedocAggiornajobstamparegistroBean lAggiornajobstamparegistroInput = new DmpkRegistrazionedocAggiornajobstamparegistroBean();
		lAggiornajobstamparegistroInput.setIdjobin(currentJob.getIdJob());
		
		lAggiornajobstamparegistroInput.setEsitostampain("OK");
		lAggiornajobstamparegistroInput.setFlginvioconservazionein(flgInvioConservazione);

		aggiornaStatoStampaRegistroRecord(lSchemaBean, lAggiornajobstamparegistroInput);
	}

	/**
	 * @param lSchemaBean
	 * @param lAggiornajobstamparegistroInput
	 * @throws Exception
	 */
	protected void aggiornaStatoStampaRegistroRecord(SchemaBean lSchemaBean,
			DmpkRegistrazionedocAggiornajobstamparegistroBean lAggiornajobstamparegistroInput) throws Exception {
		DmpkRegistrazionedocAggiornajobstamparegistro lAggiornajobstamparegistro = new DmpkRegistrazionedocAggiornajobstamparegistro();

		StoreResultBean<DmpkRegistrazionedocAggiornajobstamparegistroBean> lAggiornajobstamparegistroOutput = lAggiornajobstamparegistro
				.execute(locale, lSchemaBean, lAggiornajobstamparegistroInput);

		if (lAggiornajobstamparegistroOutput.getDefaultMessage() != null) {
			throw new Exception(lAggiornajobstamparegistroOutput.getDefaultMessage());
		}
	}

	@Override
	public void end(String arg0) {
		HibernateUtil.closeConnection(JOB_CLASS_NAME);
		logger.info("Elaborazione completata");
	}
}
