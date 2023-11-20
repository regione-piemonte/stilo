/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddverdocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreExtractverdocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdverdocBean;
import it.eng.auriga.database.store.dmpk_core.store.Extractverdoc;
import it.eng.auriga.database.store.dmpk_core.store.impl.AddverdocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.ExtractverdocImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.RecuperoDocumenti;
import it.eng.client.DmpkCoreAddverdoc;
import it.eng.client.DmpkCoreUpdverdoc;
import it.eng.client.GestioneDocumenti;
//import it.eng.client.RecuperoFile;
//import it.eng.document.function.RecuperoFile;
//import it.eng.document.function.StoreException;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
//import it.eng.document.function.GestioneDocumenti;
//import it.eng.document.function.RecuperoFile;
import it.eng.document.function.bean.ExtractVerDocOutBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.FilePrimarioOutBean;
import it.eng.document.function.bean.FileStoreBean;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.job.AurigaSpringContext;
import it.eng.job.SpringHelper;
import it.eng.job.aggiungiMarca.bean.FileDaMarcareBean;
import it.eng.job.aggiungiMarca.bean.FileMarcatoBean;
import it.eng.job.aggiungiMarca.bean.HandlerResultBean;
import it.eng.job.aggiungiMarca.bean.MarcaConfigBean;
import it.eng.job.aggiungiMarca.bean.MarcaGenericFile;
import it.eng.job.aggiungiMarca.bean.MarcaturaFileStoreBean;
import it.eng.job.aggiungiMarca.bean.VersionamentoFileBean;
import it.eng.job.aggiungiMarca.constants.AggiuntaMarcaConstants;
import it.eng.job.aggiungiMarca.dao.DaoCodaFileDaMarcare;
import it.eng.job.aggiungiMarca.dao.DaoFileDaMarcare;
import it.eng.job.aggiungiMarca.dao.HibernateUtil;
import it.eng.job.aggiungiMarca.entity.CodaFileDaMarcare;
import it.eng.job.aggiungiMarca.entity.FileDaMarcare;
import it.eng.job.aggiungiMarca.exceptions.AggiungiMarcaTemporaleException;
import it.eng.job.aggiungiMarca.util.MarcaUtils;
import it.eng.job.aggiungiMarca.util.TsrGenerator;
import it.eng.job.aggiungiMarca.util.WSClientUtils;
import it.eng.storeutil.AnalyzeResult;
import it.eng.utility.storageutil.StorageService;
import it.eng.xml.XmlUtilitySerializer;

public class ElaborazioneSingoloFileTask extends RecursiveTask<HandlerResultBean<Void>> {

	static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private static final long serialVersionUID = 4272278979801291534L;

	private final String idProcessoAggiuntaMarca;
	private final FileDaMarcareBean fileDaMarcare;
	private final AurigaLoginBean aurigaLoginBean;
	private final Locale locale;
	private final StorageService storageService;
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm");
	
	public ElaborazioneSingoloFileTask(String idProcessoAggiuntaMarca, FileDaMarcareBean fileDaMarcare, AurigaLoginBean aurigaLoginBean, 
			Locale locale, StorageService storageService )
			throws AggiungiMarcaTemporaleException {
		super();
		this.idProcessoAggiuntaMarca = idProcessoAggiuntaMarca;
		this.fileDaMarcare = fileDaMarcare;
		this.aurigaLoginBean = aurigaLoginBean;
		this.locale = locale;
		this.storageService = storageService;
	}

	@Override
	protected HandlerResultBean<Void> compute() {

		logger.debug("Task elaborazione singolo file " + idProcessoAggiuntaMarca);
		ThreadContext.put(AggiuntaMarcaConstants.ROUTINGKEY,
				AggiuntaMarcaConstants.ELABORAZIONE + idProcessoAggiuntaMarca);

		HandlerResultBean<Void> result = new HandlerResultBean<>();
		result.setSuccessful(false);

		try {

			configurazioneSubjectBean();
			
			logger.debug("elaborazione file con idUd " + fileDaMarcare.getIdUd() + " e idDoc " + fileDaMarcare.getIdDoc() + " - tipo file " + fileDaMarcare.getTipoFile() );

			logger.debug("setto file::::"+ fileDaMarcare);
			result.addAdditionalInformation(AggiuntaMarcaConstants.RESULT_ELAB, fileDaMarcare);
			
			DaoCodaFileDaMarcare dao = new DaoCodaFileDaMarcare();
			
			String uri = null;
			String displayName = null;
			Session session = null;
			
			try {
				session = HibernateUtil.openSession("elaborazioneFile");
				
				DmpkCoreExtractverdocBean lDmpkCoreExtractverdocBean = new DmpkCoreExtractverdocBean();
				lDmpkCoreExtractverdocBean.setCodidconnectiontokenin(aurigaLoginBean.getToken());			
				lDmpkCoreExtractverdocBean.setIduserlavoroin(StringUtils.isNotBlank(aurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(aurigaLoginBean.getIdUserLavoro()) : null);
				lDmpkCoreExtractverdocBean.setIddocin(fileDaMarcare.getIdDoc());
				//lDmpkCoreExtractverdocBean.setNroprogrverio(null);
				
				final ExtractverdocImpl lExtractverdoc = new ExtractverdocImpl();
				
				lExtractverdoc.setBean(lDmpkCoreExtractverdocBean);
				
				session.doWork(new Work() {
					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						lExtractverdoc.execute(paramConnection);
					}
				});
				StoreResultBean<DmpkCoreExtractverdocBean> extractverdocOut = new StoreResultBean<DmpkCoreExtractverdocBean>();
				AnalyzeResult.analyze(lDmpkCoreExtractverdocBean, extractverdocOut);
				extractverdocOut.setResultBean(lDmpkCoreExtractverdocBean);
			
				if (extractverdocOut.isInError()){
					//throw new StoreException(extractverdocOut);
					logger.error(extractverdocOut.getDefaultMessage());
					CodaFileDaMarcare bean = new CodaFileDaMarcare();
					bean.setIdUd( fileDaMarcare.getIdUd());
					bean.setIdDoc( fileDaMarcare.getIdDoc());
					bean.setTipoBustaCritt( fileDaMarcare.getTipoFile());
					bean.setTsFirma( fileDaMarcare.getTsFirma() );
					bean.setNumTry( fileDaMarcare.getNumTry() );
					dao.updateErrore(bean, "Errore nell'extractVerDoc");
					result.setSuccessful(false);
				}
				
				//RecuperoFile recuperoFile = new RecuperoFile();
				//ExtractVerDocOutBean extractverdocOut = recuperoFile.extractverdoc(Locale.ITALIAN, aurigaLoginBean, fileDaMarcare.getIdDoc(), null);
			
				if (extractverdocOut.getDefaultMessage() != null) {
					logger.error(extractverdocOut.getDefaultMessage());
					CodaFileDaMarcare bean = new CodaFileDaMarcare();
					bean.setIdUd( fileDaMarcare.getIdUd());
					bean.setIdDoc( fileDaMarcare.getIdDoc());
					bean.setTipoBustaCritt( fileDaMarcare.getTipoFile());
					bean.setTsFirma( fileDaMarcare.getTsFirma() );
					bean.setNumTry( fileDaMarcare.getNumTry() );
					dao.updateErrore(bean, "Errore nell'extractVerDoc");
					result.setSuccessful(false);
				} else {
					if (extractverdocOut.getResultBean() != null) { 
						displayName = extractverdocOut.getResultBean().getDisplayfilenameverout();
						logger.debug("displayName " + displayName);
						fileDaMarcare.setNomeFile(displayName);
						uri = extractverdocOut.getResultBean().getUriverout();
						fileDaMarcare.setUriFileFirmato(uri);
					}
				}
			}
			catch (Exception e) {
				logger.error(e.getMessage());
			}
			finally {
				HibernateUtil.release(session);
			}
			
			if( uri!=null ){
				File fileFirmato = null;
				try {
					fileFirmato = storageService.extractFile(uri);
					logger.info("fileFirmato " + fileFirmato);
				} catch (Exception e){
					CodaFileDaMarcare bean = new CodaFileDaMarcare();
					bean.setIdUd( fileDaMarcare.getIdUd());
					bean.setIdDoc( fileDaMarcare.getIdDoc());
					bean.setTipoBustaCritt( fileDaMarcare.getTipoFile());
					bean.setTsFirma( fileDaMarcare.getTsFirma() );
					bean.setNumTry( fileDaMarcare.getNumTry() );
					dao.updateErrore(bean, "Errore nell'estrazione del file ");
					result.setSuccessful(false);
				}
				
				if( fileFirmato!=null ){
					logger.info("Verifico se il file " + fileFirmato + " e' marcato");
					
					FileMarcatoBean fileMarcatoBean = null;
					boolean isFileMarcato = true; 
					boolean verificaMarcaEffettuata = false;
					try {
						fileMarcatoBean = MarcaUtils.isMarcato(fileFirmato, fileDaMarcare.getTipoFile());
						
						isFileMarcato = fileMarcatoBean.isMarcato();
						logger.info("Esito verifica presenza marca " + isFileMarcato );
						verificaMarcaEffettuata = true;
					} catch (Exception e){
						logger.error("ERRORE NELLA VERIFICA PRESENZA MARCA ",e);
						isFileMarcato = false;
						verificaMarcaEffettuata = false;
					}
					
					if( !isFileMarcato && verificaMarcaEffettuata){
						logger.info("Il file non e' marcato, provo a marcarlo ");
						
						MarcaConfigBean marcaConfigBean = SpringHelper.getSpecializedBean(AurigaSpringContext.SPRINGBEAN_MARCA_CONFIG, null, MarcaConfigBean.class);
						String serverMarcaUrl = marcaConfigBean.getMarcaServiceUrl();
						String serverMarcaUid = marcaConfigBean.getMarcaServiceUid();
						String serverMarcaPwd = marcaConfigBean.getMarcaServicePwd();
						logger.info("serverMarcaUrl " + serverMarcaUrl);
						logger.info("serverMarcaUid " + serverMarcaUid);
						logger.info("serverMarcaPwd " + serverMarcaPwd);
						
						boolean useAuth = false;
						if(marcaConfigBean!=null && serverMarcaUid!=null && 
								!serverMarcaUid.equalsIgnoreCase("")){
							useAuth = true;
						}
						logger.info("useAuth " + useAuth);
						
						TsrGenerator tsrGenerator = new TsrGenerator(
								serverMarcaUrl, 
								serverMarcaUid, 
								serverMarcaPwd,
								useAuth);
						logger.info("tsrGenerator " + tsrGenerator);
						
						try {
							  
							byte[] byteFileMarcato = null;
							logger.info("fileDaMarcare.getTipoFile() " + fileDaMarcare.getTipoFile());
							if( fileDaMarcare.getTipoFile()==null || fileDaMarcare.getTipoFile().equalsIgnoreCase("PADES")){
								byteFileMarcato = tsrGenerator.addMarcaPdf(fileFirmato);  
							} else if( fileDaMarcare.getTipoFile()==null || fileDaMarcare.getTipoFile().equalsIgnoreCase("CADES")){
								byteFileMarcato = tsrGenerator.addMarca(fileFirmato);  
							} 
							fileDaMarcare.setTsMarca( formatter.format( new Date() ) );
							
							if( byteFileMarcato!=null ){
								String uriFileMarcato =storageService.storeStream(new ByteArrayInputStream(byteFileMarcato));
								logger.debug("uriFileMarcato " + uriFileMarcato);
								fileDaMarcare.setUriFileMarcato(uriFileMarcato);
								
								File fileMarcato = storageService.extractFile(uriFileMarcato);
								logger.info("fileMarcato " + fileMarcato);
												
								VersionamentoFileBean versionamentoFileBean = versionaDoc(fileMarcato, fileDaMarcare, aurigaLoginBean);
								boolean esitoVersionamento = versionamentoFileBean.isEsitoVersionamento();
								if( esitoVersionamento ){
									result.setSuccessful(true);	
								} else {
									CodaFileDaMarcare bean = new CodaFileDaMarcare();
									bean.setIdUd( fileDaMarcare.getIdUd());
									bean.setIdDoc( fileDaMarcare.getIdDoc());
									bean.setTipoBustaCritt( fileDaMarcare.getTipoFile());
									bean.setTsFirma( fileDaMarcare.getTsFirma() );
									bean.setNumTry( fileDaMarcare.getNumTry() );
									dao.updateErrore(bean, "Errore nel versionamento " + versionamentoFileBean.getMessaggioErrore());
									result.setSuccessful(false);
								}
							}
							else {
								logger.error("Errore nella marca ");
								CodaFileDaMarcare bean = new CodaFileDaMarcare();
								bean.setIdUd( fileDaMarcare.getIdUd());
								bean.setIdDoc( fileDaMarcare.getIdDoc());
								bean.setTipoBustaCritt( fileDaMarcare.getTipoFile());
								bean.setTsFirma( fileDaMarcare.getTsFirma() );
								bean.setNumTry( fileDaMarcare.getNumTry() );
								dao.updateErrore(bean, "Errore nella marca - impossibile recuperare il file marcato");
								result.setSuccessful(false);
							}
							
							
						} catch(Throwable e){
							logger.error("", e);
							CodaFileDaMarcare bean = new CodaFileDaMarcare();
							bean.setIdUd( fileDaMarcare.getIdUd());
							bean.setIdDoc( fileDaMarcare.getIdDoc());
							bean.setTipoBustaCritt( fileDaMarcare.getTipoFile());
							bean.setTsFirma( fileDaMarcare.getTsFirma() );
							bean.setNumTry( fileDaMarcare.getNumTry() );
							dao.updateErrore(bean, "Errore nella marca " + e.getMessage());
							result.setSuccessful(false);
						}
					}
					else {
						String messaggio = "";
						if( verificaMarcaEffettuata ){
							String dataMarca = fileMarcatoBean.getTsMarca();
							logger.info("Il file e' gia' marcato in data " + dataMarca);
							messaggio = "File gia' marcato in data " + dataMarca;
						} else {
							messaggio = "Impossibile verificare la marca";
						}
							
						CodaFileDaMarcare bean = new CodaFileDaMarcare();
						bean.setIdUd( fileDaMarcare.getIdUd());
						bean.setIdDoc( fileDaMarcare.getIdDoc());
						bean.setTipoBustaCritt( fileDaMarcare.getTipoFile());
						bean.setTsFirma( fileDaMarcare.getTsFirma() );
						bean.setNumTry( fileDaMarcare.getNumTry() );
						dao.updateErrore(bean, messaggio);
						result.setSuccessful(false);
						//result.setSuccessful(true);
					}
				}
				
				
					
			} else {
				logger.error("Errore, nessun file firmato individuato");
				CodaFileDaMarcare bean = new CodaFileDaMarcare();
				bean.setIdUd( fileDaMarcare.getIdUd());
				bean.setIdDoc( fileDaMarcare.getIdDoc());
				bean.setTipoBustaCritt( fileDaMarcare.getTipoFile());
				bean.setTsFirma( fileDaMarcare.getTsFirma() );
				bean.setNumTry( fileDaMarcare.getNumTry() );
				dao.updateErrore(bean, "Errore, nessun file firmato individuato");
				result.setSuccessful(false);
			}
			

		} catch (Exception e) {
			logger.error("Si è verificato un errore nel task di ", e);
			result = new HandlerResultBean<>();
			result.setSuccessful(false);
		}finally {
			
		}

		return result;

	}
	
	private void configurazioneSubjectBean() {
		SubjectBean subject = SubjectUtil.subject.get();
		if (subject == null) {
			subject = new SubjectBean();
			SubjectUtil.subject.set(subject);
		}
	}
	
	public static VersionamentoFileBean versionaDoc(File fileMarcato, FileDaMarcareBean fileDaMarcare, AurigaLoginBean loginBean) throws Exception{
		logger.debug("Metodo di versionamento file - idDoc: " + fileDaMarcare.getIdDoc() );
		boolean esito = false;
		VersionamentoFileBean versionamentoFileBean = new VersionamentoFileBean();
		
		MarcaGenericFile lGenericFile = new WSClientUtils().getInfo( fileMarcato );
		
		MarcaturaFileStoreBean lFileStoreBean = new MarcaturaFileStoreBean();
		lFileStoreBean.setDisplayFilename( fileDaMarcare.getNomeFile() );
		lFileStoreBean.setUri(fileDaMarcare.getUriFileMarcato() );
		logger.debug("uri salvato : " + fileDaMarcare.getUriFileMarcato());
		lFileStoreBean.setDimensione(fileMarcato.length());
		logger.debug("dimensione : " + fileMarcato.length());
		
		lFileStoreBean.setAlgoritmo( lGenericFile.getAlgoritmo() );
		lFileStoreBean.setEncoding( lGenericFile.getEncoding() );
		lFileStoreBean.setImpronta( lGenericFile.getImpronta() );
		
		lFileStoreBean.setMimetype( lGenericFile.getMimetype() );
		
		lFileStoreBean.setFirmatari( lGenericFile.getFirmatari() );
		lFileStoreBean.setFirmato( lGenericFile.getFirmato() );
		
		lFileStoreBean.setFlgMarcaTemporaleAurigaVer( "1" );
		lFileStoreBean.setTsMarcaTemporaleVer( fileDaMarcare.getTsMarca() );
		lFileStoreBean.setTsVerificaMarcaTemporaleVer( formatter.format( new Date() ) );
		lFileStoreBean.setTipoBustaCrittograficaVer( fileDaMarcare.getTipoFile() );
		lFileStoreBean.setTipoMarcaTemporaleVer( "embedded su " + fileDaMarcare.getTipoFile());
		lFileStoreBean.setInfoVerificaMarcaTemporaleVer( lGenericFile.getTsa() );
		
		String lStringXml = "";
		try {
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
			logger.debug("attributiVerXml : " + lStringXml);
		} catch (Exception e) {
			logger.error(e);
		}
		
		/*GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		VersionaDocumentoInBean lVersionaDocumentoInput = new VersionaDocumentoInBean();
		
		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento( idDoc );
		lRebuildedFile.setFile( file );
		
		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);
		
		lGenericFile.setDisplayFilename( displayName );
		lFileInfoBean.setAllegatoRiferimento(lGenericFile);
		lRebuildedFile.setInfo(lFileInfoBean);
		
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInput, lRebuildedFile);*/
		
		//lVersionaDocumentoInput.se
		
		//VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionaDocumento(loginBean, lVersionaDocumentoInput);
		/*VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionadocumento(Locale.ITALIAN, loginBean, lVersionaDocumentoInput);
		if (lVersionaDocumentoOutput.getDefaultMessage() != null) {
			logger.error(lVersionaDocumentoOutput.getDefaultMessage());
			esito = true;
		} else {
			esito = true;
		}*/
		
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.openSession("versionaFile");
			transaction = session.beginTransaction();
			DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
			lAddverdocBean.setCodidconnectiontokenin(loginBean.getToken());
			lAddverdocBean.setIduserlavoroin(getIdUserLavoro(loginBean));
			lAddverdocBean.setIddocin( fileDaMarcare.getIdDoc() );
			//lUpdverdocBean.setNroprogrverio(null);
			lAddverdocBean.setAttributiverxmlin(lStringXml);
			//DmpkCoreAddverdoc lAddverdoc = new DmpkCoreAddverdoc();
			//logger.debug("Chiamo la addVerdoc " + lAddverdocBean);

			//StoreResultBean<DmpkCoreAddverdocBean> lUpdverdocStoreResultBean = lAddverdoc.execute(Locale.ITALIAN, loginBean, lAddverdocBean);
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

			StoreResultBean<DmpkCoreAddverdocBean> lUpdverdocStoreResultBean = new StoreResultBean<DmpkCoreAddverdocBean>();
			AnalyzeResult.analyze(lAddverdocBean, lUpdverdocStoreResultBean);
			lUpdverdocStoreResultBean.setResultBean(lAddverdocBean);
			
			
			if (lUpdverdocStoreResultBean.isInError()) {
				
				logger.error("Default message: "+ lUpdverdocStoreResultBean.getDefaultMessage());
				logger.error("Error context: " + lUpdverdocStoreResultBean.getErrorContext());
				logger.error("Error code: " + lUpdverdocStoreResultBean.getErrorCode());
				
				versionamentoFileBean.setMessaggioErrore( lUpdverdocStoreResultBean.getDefaultMessage() );
				esito = false;
			} else {
				esito = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			
			if (transaction != null) {	
				try {
					transaction.rollback();
				} catch (Exception ex) {
					logger.error(ex);
				}
			}
		}
		finally {
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
				logger.error(e);
			}
		}
		
		versionamentoFileBean.setEsitoVersionamento(esito); 
		return versionamentoFileBean;
	}
	
	protected static BigDecimal getIdUserLavoro(AurigaLoginBean pAurigaLoginBean) {
		return StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
	}
	
	/*public static boolean versionaPrimario(File file, BigDecimal idDoc, AurigaLoginBean loginBean) throws Exception{
		logger.debug("Metodo di versionamento file primario - idDoc: " + idDoc );
		boolean esito = false;
		
		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		VersionaDocumentoInBean lVersionaDocumentoInput = new VersionaDocumentoInBean();
		
		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento( idDoc );
		lRebuildedFile.setFile( file );
		
		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);
		GenericFile lGenericFile = new WSClientUtils().getInfo( file );
		lGenericFile.setDisplayFilename(file.getName() );
		lFileInfoBean.setAllegatoRiferimento(lGenericFile);
		lRebuildedFile.setInfo(lFileInfoBean);
		
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInput, lRebuildedFile);
		
		VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionaDocumento(loginBean, lVersionaDocumentoInput);
		if (lVersionaDocumentoOutput.getDefaultMessage() != null) {
			logger.error(lVersionaDocumentoOutput.getDefaultMessage());
		} else {
			esito = true;
		}
		
		return esito;
	}
	
	public static boolean versionaAllegato(File file, BigDecimal idDoc, AurigaLoginBean loginBean) throws Exception{
		logger.debug("Metodo di versionamento file allegato - idDoc: " + idDoc );
		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento( idDoc );
		lRebuildedFile.setFile( file );

		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.ALLEGATO);
		GenericFile lGenericFile = new WSClientUtils().getInfo( file );
		lGenericFile.setDisplayFilename( file.getName() );
		lFileInfoBean.setAllegatoRiferimento(lGenericFile);
		lRebuildedFile.setInfo(lFileInfoBean);

		VersionaDocumentoInBean lVersionaDocumentoInput = new VersionaDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInput, lRebuildedFile);
		
		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionaDocumento( loginBean, lVersionaDocumentoInput);

		if (lVersionaDocumentoOutput.getDefaultMessage() != null) {
			String message = lVersionaDocumentoOutput.getDefaultMessage();
			logger.error(message);
			return false;
		} else 
			return true;
	}*/
}
