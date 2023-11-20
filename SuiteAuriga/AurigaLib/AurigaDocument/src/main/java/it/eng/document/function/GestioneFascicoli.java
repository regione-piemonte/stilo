/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddfolderBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddverdocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDel_ud_doc_verBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDelfolderBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreToglidaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreTrovarepositoryobjBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdfolderBean;
import it.eng.auriga.database.store.dmpk_core.store.Addfolder;
import it.eng.auriga.database.store.dmpk_core.store.Delfolder;
import it.eng.auriga.database.store.dmpk_core.store.Trovarepositoryobj;
import it.eng.auriga.database.store.dmpk_core.store.Updfolder;
import it.eng.auriga.database.store.dmpk_core.store.impl.AdddocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.AddfolderImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.AddverdocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.Del_ud_doc_verImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.ToglidaImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpddocudImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpdfolderImpl;
import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiLoaddettfascxguimodprotBean;
import it.eng.auriga.database.store.dmpk_repository_gui.store.Loaddettfascxguimodprot;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.login.service.LoginService;
import it.eng.auriga.repository2.generic.VersionHandler;
import it.eng.auriga.repository2.jaxws.webservices.common.ConnectionWrapper;
import it.eng.auriga.repository2.versionhandler.synchronous.SynchronousVersionHandler;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.CancellaFascicoloIn;
import it.eng.document.function.bean.CancellaFascicoloOut;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FileStoreBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.FolderCustom;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.LoadFascicoloIn;
import it.eng.document.function.bean.LoadFascicoloOut;
import it.eng.document.function.bean.MittentiDocumentoBean;
import it.eng.document.function.bean.ModificaFascicoloIn;
import it.eng.document.function.bean.ModificaFascicoloOut;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.RegistroEmergenza;
import it.eng.document.function.bean.SalvaFascicoloIn;
import it.eng.document.function.bean.SalvaFascicoloOut;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.TrovaDocFolderOut;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.document.function.bean.XmlFascicoloOut;
import it.eng.document.storage.DocumentStorage;
import it.eng.storeutil.AnalyzeResult;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

@Service(name = "GestioneFascicoli")
public class GestioneFascicoli {

	private static Logger mLogger = Logger.getLogger(GestioneFascicoli.class);
	
	@Operation(name = "loadFascicolo")
	public LoadFascicoloOut loadFascicolo(AurigaLoginBean pAurigaLoginBean, LoadFascicoloIn pLoadFascicoloIn) throws Exception {
		mLogger.debug("Recupero fascicolo con idFolder " + pLoadFascicoloIn.getIdFolderIn());
	
		DmpkRepositoryGuiLoaddettfascxguimodprotBean bean = new DmpkRepositoryGuiLoaddettfascxguimodprotBean();
		bean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		bean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
		bean.setIdfolderin(pLoadFascicoloIn.getIdFolderIn());
		bean.setFlgsoloabilazioniin(StringUtils.isNotBlank(pLoadFascicoloIn.getFlgSoloAbilAzioni()) ? new Integer(pLoadFascicoloIn.getFlgSoloAbilAzioni()) : null);
		bean.setFlgfascincreazionein(StringUtils.isNotBlank(pLoadFascicoloIn.getFlgFascInCreazione()) ? new Integer(pLoadFascicoloIn.getFlgFascInCreazione()) : null);
		bean.setCinodoscrivaniain(pLoadFascicoloIn.getIdNodeScrivania());
		
		Loaddettfascxguimodprot store = new Loaddettfascxguimodprot();
		StoreResultBean<DmpkRepositoryGuiLoaddettfascxguimodprotBean> lStoreResultBean = store.execute(pAurigaLoginBean, bean);
		if (lStoreResultBean.isInError()) {
			mLogger.error("Default message: "+ lStoreResultBean.getDefaultMessage());
			mLogger.error("Error context: " + lStoreResultBean.getErrorContext());
			mLogger.error("Error code: " + lStoreResultBean.getErrorCode());
			LoadFascicoloOut lLoadFascicoloOut = new LoadFascicoloOut();
			BeanUtilsBean2.getInstance().copyProperties(lLoadFascicoloOut, lStoreResultBean);
			return lLoadFascicoloOut;
		}

		String lStrXml = lStoreResultBean.getResultBean().getDatifascxmlout();
		mLogger.debug(lStrXml);
		XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
		XmlFascicoloOut lXmlFascicoloOut = new XmlFascicoloOut();
		lXmlFascicoloOut = lXmlUtilityDeserializer.unbindXml(lStrXml, XmlFascicoloOut.class);

		LoadFascicoloOut result = new LoadFascicoloOut();
		result.setXmlFascicoloOut(lXmlFascicoloOut);

		return result;
	}

	@Operation(name = "cancellaFascicolo")
	public CancellaFascicoloOut cancellaFascicolo(AurigaLoginBean pAurigaLoginBean, CancellaFascicoloIn pCancellaFascicoloIn) throws Exception {

		DmpkCoreDelfolderBean bean = new DmpkCoreDelfolderBean();
		bean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		bean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
		bean.setFlgcancfisicain(pCancellaFascicoloIn.getFlgCancFisicaIn());
		bean.setFolderpathin(pCancellaFascicoloIn.getFolderPath());
		bean.setIdfolderin(pCancellaFascicoloIn.getIdFolderIn());
		bean.setIdlibraryin(pCancellaFascicoloIn.getIdLibrary());
		bean.setNomelibraryin(pCancellaFascicoloIn.getNomeLibrary());

		Delfolder store = new Delfolder();
		StoreResultBean<DmpkCoreDelfolderBean> lStoreResultBean = store.execute(pAurigaLoginBean, bean);

		if (lStoreResultBean.isInError()) {
			mLogger.error("Default message: "+ lStoreResultBean.getDefaultMessage());
			mLogger.error("Error context: " + lStoreResultBean.getErrorContext());
			mLogger.error("Error code: " + lStoreResultBean.getErrorCode());
			CancellaFascicoloOut lCancellaFascicoloOut = new CancellaFascicoloOut();
			BeanUtilsBean2.getInstance().copyProperties(lCancellaFascicoloOut, lStoreResultBean);
			return lCancellaFascicoloOut;
		}

		CancellaFascicoloOut lCancellaFascicoloOut = new CancellaFascicoloOut();
		lCancellaFascicoloOut.setUriOut(lStoreResultBean.getResultBean().getUriout());

		return lCancellaFascicoloOut;

	}
	
	@Operation(name = "salvaFascicolo")
	public SalvaFascicoloOut salvaFascicolo(AurigaLoginBean pAurigaLoginBean, SalvaFascicoloIn pSalvaFascicoloIn) throws Exception {
		
		if(pSalvaFascicoloIn.getIsCaricaPraticaPregressa() != null && pSalvaFascicoloIn.getIsCaricaPraticaPregressa()) {
			return salvaPraticaPregressa(pAurigaLoginBean, pSalvaFascicoloIn);
		}

		SalvaFascicoloOut lSalvaFascicoloOut = new SalvaFascicoloOut();
		
		DmpkCoreAddfolderBean lAddfolderBean = new DmpkCoreAddfolderBean();
		lAddfolderBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lAddfolderBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String lStrXml = lXmlUtilitySerializer.bindXml(pSalvaFascicoloIn.getXmlFascicolo());
		mLogger.debug(lStrXml);
		lAddfolderBean.setAttributixmlin(lStrXml);
		
		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();

		try {
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {
				final AddfolderImpl store = new AddfolderImpl();
				store.setBean(lAddfolderBean);

				LoginService lLoginService = new LoginService();
				lLoginService.login(pAurigaLoginBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreAddfolderBean> result = new StoreResultBean<DmpkCoreAddfolderBean>();
				AnalyzeResult.analyze(lAddfolderBean, result);
				result.setResultBean(lAddfolderBean);

				lSalvaFascicoloOut.setIdFolderOut(result.getResultBean().getIdfolderout());

				if (result.isInError()) {
					throw new StoreException(result);
				} else {
					if (pSalvaFascicoloIn != null) {
						
						Iterator<File> nextFile = null;
						if (pSalvaFascicoloIn.getFileAllegati() != null)
							nextFile = pSalvaFascicoloIn.getFileAllegati().iterator();
						
						if (pSalvaFascicoloIn.getIsNull() != null) {							
							for (int i = 0; i < pSalvaFascicoloIn.getIsNull().size(); i++) {
								AllegatoStoreBean lDocumentoBean = new AllegatoStoreBean();
								// Setto la natura a NULL perchè non lo sto salvando come allegato ma come file primario
								lDocumentoBean.setNatura("P");
								lDocumentoBean.setDescrizione(pSalvaFascicoloIn.getDescrizione().get(i));
								lDocumentoBean.setIdDocType(pSalvaFascicoloIn.getDocType().get(i));
								lDocumentoBean.setSezionePratica(pSalvaFascicoloIn.getSezionePratica().get(i));													
								List<FolderCustom> listaFolderCustom = new ArrayList<FolderCustom>();
								FolderCustom folderCustom = new FolderCustom();
								folderCustom.setId(String.valueOf(lSalvaFascicoloOut.getIdFolderOut().longValue()));
								listaFolderCustom.add(folderCustom);
								lDocumentoBean.setFolderCustom(listaFolderCustom);
//								lDocumentoBean.setFlgAppendFolderCustom("1"); // qui non serve l'append perchè chiamo sempre la AddDoc e non la UpdDoc
								lDocumentoBean.setFlgEreditaPermessi("1");
								lDocumentoBean.setIdFolderEreditaPerm(lSalvaFascicoloOut.getIdFolderOut());
								if (pSalvaFascicoloIn.getFlgParteDispositivo() != null) {
									Boolean flgParteDispositivo = pSalvaFascicoloIn.getFlgParteDispositivo().get(i);
									lDocumentoBean.setFlgParteDispositivo(flgParteDispositivo != null && flgParteDispositivo ? "1" : null);
								}
								if (pSalvaFascicoloIn.getIdTask() != null) {
									lDocumentoBean.setIdTask(pSalvaFascicoloIn.getIdTask().get(i));
								}
								if (pSalvaFascicoloIn.getFlgNoPubbl() != null) {
									Boolean flgNoPubbl = pSalvaFascicoloIn.getFlgNoPubbl().get(i);
									lDocumentoBean.setFlgNoPubbl(flgNoPubbl != null && flgNoPubbl ? "1" : null);
								}
								if (pSalvaFascicoloIn.getFlgPubblicaSeparato() != null) {
									Boolean flgPubblicaSeparato = pSalvaFascicoloIn.getFlgPubblicaSeparato().get(i);
									lDocumentoBean.setFlgPubblicaSeparato(flgPubblicaSeparato != null && flgPubblicaSeparato ? "1" : null);
								}
								if (pSalvaFascicoloIn.getFlgOriginaleCartaceo() != null) {
									Boolean flgOriginaleCartaceo = pSalvaFascicoloIn.getFlgOriginaleCartaceo().get(i);
									lDocumentoBean.setFlgOriginaleCartaceo(flgOriginaleCartaceo != null && flgOriginaleCartaceo ? "1" : null);
								}
								if (pSalvaFascicoloIn.getFlgCopiaSostitutiva() != null) {
									Boolean flgCopiaSostitutiva = pSalvaFascicoloIn.getFlgCopiaSostitutiva().get(i);
									lDocumentoBean.setFlgCopiaSostitutiva(flgCopiaSostitutiva != null && flgCopiaSostitutiva ? "1" : null);
								}
								String attributi = lXmlUtilitySerializer.bindXmlCompleta(lDocumentoBean);
								
								DmpkCoreAdddocBean lAdddocBean = new DmpkCoreAdddocBean();
								lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
								lAdddocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
								lAdddocBean.setAttributiuddocxmlin(attributi);
								final AdddocImpl lAdddoc = new AdddocImpl();
								lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
								lAdddoc.setBean(lAdddocBean);
								session.doWork(new Work() {
	
									@Override
									public void execute(Connection paramConnection) throws SQLException {
										lAdddoc.execute(paramConnection);
									}
								});
								StoreResultBean<DmpkCoreAdddocBean> lAdddocResult = new StoreResultBean<DmpkCoreAdddocBean>();
								AnalyzeResult.analyze(lAdddocBean, lAdddocResult);
								lAdddocResult.setResultBean(lAdddocBean);								
								if (lAdddocResult.isInError()) {
									throw new StoreException(lAdddocResult);
								} else {
									mLogger.debug("idDoc vale " + lAdddocResult.getResultBean().getIddocout());
									if (!pSalvaFascicoloIn.getIsNull().get(i)) {
										RebuildedFile lRebuildedFile = new RebuildedFile();
										lRebuildedFile.setFile(nextFile.next());
										lRebuildedFile.setInfo(pSalvaFascicoloIn.getInfo().get(i));
										lRebuildedFile.setIdDocumento(lAdddocResult.getResultBean().getIddocout());
										lRebuildedFile.setIdTask(pSalvaFascicoloIn.getIdTaskVer().get(i));
										versioni.add(lRebuildedFile);
									}
								}
							}		
						}
					}
				}

				// Parte di versionamento
				Map<String, String> fileErrors = new HashMap<String, String>();
				fileErrors.putAll(aggiungiFilesInTransaction(pAurigaLoginBean, versioni, session));
				lSalvaFascicoloOut.setFileInErrors(fileErrors);

				session.flush();
				lTransaction.commit();
				
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				lSalvaFascicoloOut.setInError(true);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lSalvaFascicoloOut, ((StoreException) e).getError());
					return lSalvaFascicoloOut;
				} else {
					lSalvaFascicoloOut.setDefaultMessage(e.getMessage());
					return lSalvaFascicoloOut;
				}
			} finally {
				HibernateUtil.release(session);
			}

		} catch (Exception e) {
			lSalvaFascicoloOut.setInError(true);
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lSalvaFascicoloOut, ((StoreException) e).getError());
				return lSalvaFascicoloOut;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lSalvaFascicoloOut.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lSalvaFascicoloOut;
			}
		}

		return lSalvaFascicoloOut;
	}

	public SalvaFascicoloOut salvaPraticaPregressa(AurigaLoginBean pAurigaLoginBean, SalvaFascicoloIn pSalvaFascicoloIn) throws Exception {

		SalvaFascicoloOut lSalvaFascicoloOut = new SalvaFascicoloOut();
		
		DmpkCoreAddfolderBean lAddfolderBean = new DmpkCoreAddfolderBean();
		lAddfolderBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lAddfolderBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String lStrXml = lXmlUtilitySerializer.bindXml(pSalvaFascicoloIn.getXmlFascicolo());
		mLogger.debug(lStrXml);
		lAddfolderBean.setAttributixmlin(lStrXml);
		
		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		List<RebuildedFile> versioniDaRimuovere = new ArrayList<RebuildedFile>();
		
		try {
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {
				final AddfolderImpl store = new AddfolderImpl();
				store.setBean(lAddfolderBean);

				LoginService lLoginService = new LoginService();
				lLoginService.login(pAurigaLoginBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreAddfolderBean> result = new StoreResultBean<DmpkCoreAddfolderBean>();
				AnalyzeResult.analyze(lAddfolderBean, result);
				result.setResultBean(lAddfolderBean);

				lSalvaFascicoloOut.setIdFolderOut(result.getResultBean().getIdfolderout());
				
				if (result.isInError()) {
					throw new StoreException(result);
				} else {
					if (pSalvaFascicoloIn != null) {
						HashMap<Integer, BigDecimal> mappaIdUdAllegato = new HashMap<Integer, BigDecimal>();
						HashMap<Integer, BigDecimal> mappaIdDocAllegato = new HashMap<Integer, BigDecimal>();						
						HashSet<String> setEstremiDocProtGiaInseriti = new HashSet<String>();
						HashMap<String, BigDecimal> mappaEstremiDocProtIdUd = new HashMap<String, BigDecimal>();						
						int count = 1;						
						Iterator<File> nextFile = null;
						if (pSalvaFascicoloIn.getFileAllegati() != null)
							nextFile = pSalvaFascicoloIn.getFileAllegati().iterator();
						
						if (pSalvaFascicoloIn.getIsNull() != null) {							
							for (int i = 0; i < pSalvaFascicoloIn.getIsNull().size(); i++) {
								AllegatoStoreBean lDocumentoBean = new AllegatoStoreBean();
								BigDecimal idUdAllegato = pSalvaFascicoloIn.getIdUd() != null ? pSalvaFascicoloIn.getIdUd().get(i) : null;								
								BigDecimal idDocAllegato = pSalvaFascicoloIn.getIdDocumento() != null ? pSalvaFascicoloIn.getIdDocumento().get(i) : null;
								boolean isProtGiaInserito = false;
								String tipoProt = pSalvaFascicoloIn.getTipoProt() != null ? pSalvaFascicoloIn.getTipoProt().get(i) : null;
								String siglaProtSettore = pSalvaFascicoloIn.getSiglaProtSettore() != null ? pSalvaFascicoloIn.getSiglaProtSettore().get(i) : null;
								String nroProt = pSalvaFascicoloIn.getNroProt() != null ? pSalvaFascicoloIn.getNroProt().get(i) : null;
								String annoProt = pSalvaFascicoloIn.getAnnoProt() != null ? pSalvaFascicoloIn.getAnnoProt().get(i) : null;
								String estremiDoc = null;
								if(StringUtils.isNotBlank(tipoProt) && StringUtils.isNotBlank(nroProt) && StringUtils.isNotBlank(annoProt)) {
									if(siglaProtSettore != null) {
										estremiDoc = tipoProt + " " + siglaProtSettore + " " + nroProt + " " + annoProt;
									} else{ 
										estremiDoc = tipoProt + " " + nroProt + " " + annoProt;											
									}
									if(!setEstremiDocProtGiaInseriti.contains(estremiDoc)) {
										setEstremiDocProtGiaInseriti.add(estremiDoc);																						
										lDocumentoBean.setRegistrazioniDate(new ArrayList<RegistroEmergenza>());
										RegistroEmergenza registro = new RegistroEmergenza();
										registro.setFisso(tipoProt);
										registro.setRegistro(siglaProtSettore);
										registro.setNro(nroProt);
										registro.setAnno(annoProt);
										lDocumentoBean.getRegistrazioniDate().add(registro);	
									} else {
										isProtGiaInserito = true;
										if(idUdAllegato == null) {
											idUdAllegato = mappaEstremiDocProtIdUd.containsKey(estremiDoc) ? mappaEstremiDocProtIdUd.get(estremiDoc) : null;
										}
									}										
								}
								lDocumentoBean.setNroDeposito(pSalvaFascicoloIn.getNroDeposito() != null ? pSalvaFascicoloIn.getNroDeposito().get(i) : null);
								lDocumentoBean.setAnnoDeposito(pSalvaFascicoloIn.getAnnoDeposito() != null ? pSalvaFascicoloIn.getAnnoDeposito().get(i) : null);
								Boolean flgNextDocAllegato = pSalvaFascicoloIn.getFlgNextDocAllegato() != null ? pSalvaFascicoloIn.getFlgNextDocAllegato().get(i) : null;
								Boolean flgAllegato = pSalvaFascicoloIn.getFlgAllegato() != null ? pSalvaFascicoloIn.getFlgAllegato().get(i) : null;
								if(isProtGiaInserito || (flgNextDocAllegato != null && flgNextDocAllegato) || (flgAllegato != null && flgAllegato)) {
									lDocumentoBean.setNatura("ALL");
								} else {
									// Setto la natura a NULL perchè non lo sto salvando come allegato ma come file primario
									lDocumentoBean.setNatura("P");		
								}									
								lDocumentoBean.setDescrizione(pSalvaFascicoloIn.getDescrizione() != null ? pSalvaFascicoloIn.getDescrizione().get(i) : null);
								lDocumentoBean.setIdDocType(pSalvaFascicoloIn.getDocType() != null ? pSalvaFascicoloIn.getDocType().get(i) : null);
								lDocumentoBean.setNomeDocType(pSalvaFascicoloIn.getNomeDocType() != null ? pSalvaFascicoloIn.getNomeDocType().get(i) : null);
								lDocumentoBean.setSezionePratica(pSalvaFascicoloIn.getSezionePratica() != null ? pSalvaFascicoloIn.getSezionePratica().get(i) : null);																
								if(pSalvaFascicoloIn.getMittentiEsibenti() != null) {
									List<MittentiDocumentoBean> listaMittentiEsibenti = new ArrayList<MittentiDocumentoBean>();
									if(StringUtils.isNotBlank(pSalvaFascicoloIn.getMittentiEsibenti().get(i))) {
										String[] tokens = pSalvaFascicoloIn.getMittentiEsibenti().get(i).replace(",", ";").split(";");
										for(int j = 0; j < tokens.length; j++) {
											MittentiDocumentoBean mittenteEsibente = new MittentiDocumentoBean();
											mittenteEsibente.setDenominazioneCognome(tokens[j]);	
											listaMittentiEsibenti.add(mittenteEsibente);	
										}
									}
									lDocumentoBean.setMittentiEsibenti(listaMittentiEsibenti);
								}
								if (pSalvaFascicoloIn.getFlgUdDaDataEntryScansioni() != null) {
									Boolean flgUdDaDataEntryScansioni = pSalvaFascicoloIn.getFlgUdDaDataEntryScansioni().get(i);									
									lDocumentoBean.setFlgUdDaDataEntryScansioni(flgUdDaDataEntryScansioni != null && flgUdDaDataEntryScansioni ? "1" : null);									
								}
								if (pSalvaFascicoloIn.getFlgDatiSensibili() != null) {
									Boolean flgDatiSensibili = pSalvaFascicoloIn.getFlgDatiSensibili().get(i);
									lDocumentoBean.setFlgDatiSensibili(flgDatiSensibili != null && flgDatiSensibili ? "1" : null);
								}
								List<FolderCustom> listaFolderCustom = new ArrayList<FolderCustom>();
								FolderCustom folderCustom = new FolderCustom();
								folderCustom.setId(lSalvaFascicoloOut.getIdFolderOut() != null ? String.valueOf(lSalvaFascicoloOut.getIdFolderOut().longValue()) : null);
								if (pSalvaFascicoloIn.getFlgCapofila() != null) {
									Boolean flgCapofila = pSalvaFascicoloIn.getFlgCapofila().get(i);									
									folderCustom.setTipoRelazione(flgCapofila != null && flgCapofila ? "CPF" : null);
								}
								listaFolderCustom.add(folderCustom);
								lDocumentoBean.setFolderCustom(listaFolderCustom);
//								lDocumentoBean.setFlgAppendFolderCustom("1"); // qui non serve l'append perchè chiamo sempre la AddDoc e non la UpdDoc
								lDocumentoBean.setFlgEreditaPermessi("1");
								lDocumentoBean.setIdFolderEreditaPerm(lSalvaFascicoloOut.getIdFolderOut());								
								if (pSalvaFascicoloIn.getFlgParteDispositivo() != null) {
									Boolean flgParteDispositivo = pSalvaFascicoloIn.getFlgParteDispositivo().get(i);
									lDocumentoBean.setFlgParteDispositivo(flgParteDispositivo != null && flgParteDispositivo ? "1" : null);
								}
								if (pSalvaFascicoloIn.getIdTask() != null) {
									lDocumentoBean.setIdTask(pSalvaFascicoloIn.getIdTask().get(i));
								}
								if (pSalvaFascicoloIn.getFlgNoPubbl() != null) {
									Boolean flgNoPubbl = pSalvaFascicoloIn.getFlgNoPubbl().get(i);
									lDocumentoBean.setFlgNoPubbl(flgNoPubbl != null && flgNoPubbl ? "1" : null);
								}
								if (pSalvaFascicoloIn.getFlgPubblicaSeparato() != null) {
									Boolean flgPubblicaSeparato = pSalvaFascicoloIn.getFlgPubblicaSeparato().get(i);
									lDocumentoBean.setFlgPubblicaSeparato(flgPubblicaSeparato != null && flgPubblicaSeparato ? "1" : null);
								}
								if (pSalvaFascicoloIn.getFlgOriginaleCartaceo() != null) {
									Boolean flgOriginaleCartaceo = pSalvaFascicoloIn.getFlgOriginaleCartaceo().get(i);
									lDocumentoBean.setFlgOriginaleCartaceo(flgOriginaleCartaceo != null && flgOriginaleCartaceo ? "1" : null);
								}
								if (pSalvaFascicoloIn.getFlgCopiaSostitutiva() != null) {
									Boolean flgCopiaSostitutiva = pSalvaFascicoloIn.getFlgCopiaSostitutiva().get(i);
									lDocumentoBean.setFlgCopiaSostitutiva(flgCopiaSostitutiva != null && flgCopiaSostitutiva ? "1" : null);
								}								
								boolean isAllegato = lDocumentoBean.getNatura() != null && "ALL".equals(lDocumentoBean.getNatura());									
								if (idDocAllegato != null && !isAllegato) {
									// se è un documento primario allora esiste già e devo fare update
									mappaIdUdAllegato.put(new Integer(count), idUdAllegato);
									mappaIdDocAllegato.put(new Integer(count), idDocAllegato);
									mappaEstremiDocProtIdUd.put(estremiDoc, idUdAllegato);																											
									AllegatoStoreBean lDocumentoBeanProtEsistente = new AllegatoStoreBean();
									lDocumentoBeanProtEsistente.setIdUd(idUdAllegato);									
									lDocumentoBeanProtEsistente.setNatura(lDocumentoBean.getNatura());									
									lDocumentoBeanProtEsistente.setFolderCustom(lDocumentoBean.getFolderCustom());
									lDocumentoBeanProtEsistente.setFlgAppendFolderCustom("1"); // devo salvare in append altrimenti in update mi cancella tutti gli altri folder a cui il documento appartiene
									lDocumentoBeanProtEsistente.setFlgDatiSensibili(lDocumentoBean.getFlgDatiSensibili());									
									String attributi = lXmlUtilitySerializer.bindXml(lDocumentoBeanProtEsistente);
									DmpkCoreUpddocudBean lUpddocudBean = new DmpkCoreUpddocudBean();
									lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lUpddocudBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
									lUpddocudBean.setFlgtipotargetin("D");
									lUpddocudBean.setIduddocin(idDocAllegato);
									lUpddocudBean.setAttributiuddocxmlin(attributi);
									final UpddocudImpl lUpddocud = new UpddocudImpl();
									lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lUpddocud.setBean(lUpddocudBean);
									session.doWork(new Work() {

										@Override
										public void execute(Connection paramConnection) throws SQLException {
											lUpddocud.execute(paramConnection);
										}
									});
									StoreResultBean<DmpkCoreUpddocudBean> lUpddocudResult = new StoreResultBean<DmpkCoreUpddocudBean>();
									AnalyzeResult.analyze(lUpddocudBean, lUpddocudResult);
									lUpddocudResult.setResultBean(lUpddocudBean);									
									if (lUpddocudResult.isInError()) {
										throw new StoreException(lUpddocudResult);
									}
									if (!pSalvaFascicoloIn.getIsNull().get(i) && pSalvaFascicoloIn.getIsNewOrChanged().get(i)) {
										RebuildedFile lRebuildedFile = new RebuildedFile();
										lRebuildedFile.setFile(nextFile.next());
										lRebuildedFile.setInfo(pSalvaFascicoloIn.getInfo() != null ? pSalvaFascicoloIn.getInfo().get(i) : null);
										lRebuildedFile.setIdDocumento(idDocAllegato);										
										lRebuildedFile.setIdTask(pSalvaFascicoloIn.getIdTaskVer() != null ? pSalvaFascicoloIn.getIdTaskVer().get(i) : null);
										versioni.add(lRebuildedFile);
									} else if (pSalvaFascicoloIn.getIsNull().get(i)) {
//										for (AllegatiOutBean doc : lXmlFascicoloOut.getListaDocumentiIstruttoria()) {
//											if (StringUtils.isNotBlank(doc.getIdDoc()) && String.valueOf(idDocAllegato.longValue()).equals(doc.getIdDoc())) {												
//												if (doc.getUri() != null && StringUtils.isNotBlank(doc.getUri())) {
													RebuildedFile lRebuildedFile = new RebuildedFile();
													lRebuildedFile.setIdDocumento(idDocAllegato);
													FileInfoBean info = new FileInfoBean();		
													if(lDocumentoBean.getNatura() != null && "ALL".equals(lDocumentoBean.getNatura())) {
														info.setTipo(TipoFile.ALLEGATO);
													} else {
														info.setTipo(TipoFile.PRIMARIO);
													}
													GenericFile allegatoRiferimento = new GenericFile();
//													allegatoRiferimento.setDisplayFilename(doc.getDisplayFileName());
													info.setAllegatoRiferimento(allegatoRiferimento);
													lRebuildedFile.setInfo(info);
													versioniDaRimuovere.add(lRebuildedFile);
//												}
//											}
//										}											
									}
								} else {
									// Se sto facendo l'AddDoc del primario passo tutti i dati altrimenti NO
									String attributi = null;
									if(isAllegato) {										
										AllegatoStoreBean lDocumentoAllegato = new AllegatoStoreBean();
										lDocumentoAllegato.setIdUd(idUdAllegato);										
										lDocumentoAllegato.setNatura(lDocumentoBean.getNatura());
										lDocumentoAllegato.setFolderCustom(lDocumentoBean.getFolderCustom());
//										lDocumentoAllegato.setFlgAppendFolderCustom("1"); // qui non serve l'append perchè chiamo sempre la AddDoc e non la UpdDoc
										lDocumentoAllegato.setFlgDatiSensibili(lDocumentoBean.getFlgDatiSensibili());										
										attributi = lXmlUtilitySerializer.bindXml(lDocumentoAllegato);
									} else {
										attributi = lXmlUtilitySerializer.bindXmlCompleta(lDocumentoBean);	
									}
									DmpkCoreAdddocBean lAdddocBean = new DmpkCoreAdddocBean();
									lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lAdddocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
									lAdddocBean.setAttributiuddocxmlin(attributi);
									final AdddocImpl lAdddoc = new AdddocImpl();
									lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lAdddoc.setBean(lAdddocBean);
									session.doWork(new Work() {
		
										@Override
										public void execute(Connection paramConnection) throws SQLException {
											lAdddoc.execute(paramConnection);
										}
									});
									StoreResultBean<DmpkCoreAdddocBean> lAdddocResult = new StoreResultBean<DmpkCoreAdddocBean>();
									AnalyzeResult.analyze(lAdddocBean, lAdddocResult);
									lAdddocResult.setResultBean(lAdddocBean);									
									if (lAdddocResult.isInError()) {
										throw new StoreException(lAdddocResult);
									}
									mLogger.debug("idDoc vale " + lAdddocResult.getResultBean().getIddocout());
									mappaIdUdAllegato.put(new Integer(count), lAdddocResult.getResultBean().getIdudout());
									mappaIdDocAllegato.put(new Integer(count), lAdddocResult.getResultBean().getIddocout());
									mappaEstremiDocProtIdUd.put(estremiDoc, lAdddocResult.getResultBean().getIdudout());																		
									if (!pSalvaFascicoloIn.getIsNull().get(i)) {
										RebuildedFile lRebuildedFile = new RebuildedFile();
										lRebuildedFile.setFile(nextFile.next());
										lRebuildedFile.setInfo(pSalvaFascicoloIn.getInfo() != null ? pSalvaFascicoloIn.getInfo().get(i) : null);
										lRebuildedFile.setIdDocumento(lAdddocResult.getResultBean().getIddocout());
										lRebuildedFile.setIdTask(pSalvaFascicoloIn.getIdTaskVer() != null ? pSalvaFascicoloIn.getIdTaskVer().get(i) : null);
										versioni.add(lRebuildedFile);
									}
								}	
								count++;
							}
						}
						
						// Vers. con omissis						
						int countOmissis = 1;
						Iterator<File> nextFileOmissis = null;
						if (pSalvaFascicoloIn.getFileAllegatiOmissis() != null)
							nextFileOmissis = pSalvaFascicoloIn.getFileAllegatiOmissis().iterator();
													
						if (pSalvaFascicoloIn.getIsNullOmissis() != null) {						
							for (int i = 0; i < pSalvaFascicoloIn.getIsNullOmissis().size(); i++) {												
								BigDecimal idDocAllegatoOmissis = pSalvaFascicoloIn.getIdDocumentoOmissis() != null ? pSalvaFascicoloIn.getIdDocumentoOmissis().get(i) : null;
								if (idDocAllegatoOmissis != null) {
									// Non ha senso fare la Upddocud per la versione con omissis
									if (!pSalvaFascicoloIn.getIsNullOmissis().get(i) && pSalvaFascicoloIn.getIsNewOrChangedOmissis().get(i)) {
										RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
										lRebuildedFileOmissis.setFile(nextFileOmissis.next());
										lRebuildedFileOmissis.setInfo(pSalvaFascicoloIn.getInfoOmissis().get(i));
										lRebuildedFileOmissis.setIdDocumento(idDocAllegatoOmissis);
										versioni.add(lRebuildedFileOmissis);
									} else if (pSalvaFascicoloIn.getIsNullOmissis().get(i)) {
//										for (AllegatiOutBean doc : lXmlFascicoloOut.getListaDocumentiIstruttoria()) {
//											if (StringUtils.isNotBlank(doc.getIdDocOmissis()) && String.valueOf(idDocAllegatoOmissis.longValue()).equals(doc.getIdDocOmissis())) {
//												if (StringUtils.isNotBlank(doc.getUriOmissis())) {
													RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
													lRebuildedFileOmissis.setIdDocumento(idDocAllegatoOmissis);
													FileInfoBean infoOmissis = new FileInfoBean();
													infoOmissis.setTipo(TipoFile.ALLEGATO);
													GenericFile allegatoRiferimentoOmissis = new GenericFile();
//													allegatoRiferimentoOmissis.setDisplayFilename(doc.getDisplayFileNameOmissis());
													infoOmissis.setAllegatoRiferimento(allegatoRiferimentoOmissis);
													lRebuildedFileOmissis.setInfo(infoOmissis);
													versioniDaRimuovere.add(lRebuildedFileOmissis);
//												}
//												break;
//											}
//										}
									}						
								} else if (!pSalvaFascicoloIn.getIsNullOmissis().get(i)) {
									BigDecimal idUdAllegato = mappaIdUdAllegato.get(new Integer(countOmissis));									
									BigDecimal idDocAllegato = mappaIdDocAllegato.get(new Integer(countOmissis));
									AllegatoVersConOmissisStoreBean lAllegatoStoreBeanOmissis = new AllegatoVersConOmissisStoreBean();
									lAllegatoStoreBeanOmissis.setIdUd(idUdAllegato);						
									lAllegatoStoreBeanOmissis.setFlgVersConOmissis("1");
									lAllegatoStoreBeanOmissis.setIdDocVersIntegrale(idDocAllegato != null ? String.valueOf(idDocAllegato.longValue()) : null);				
									String attributiAllegatoOmissis = lXmlUtilitySerializer.bindXml(lAllegatoStoreBeanOmissis);	
									DmpkCoreAdddocBean lAdddocBeanAllegatoOmissis = new DmpkCoreAdddocBean();
									lAdddocBeanAllegatoOmissis.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lAdddocBeanAllegatoOmissis.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
									lAdddocBeanAllegatoOmissis.setAttributiuddocxmlin(attributiAllegatoOmissis);
									final AdddocImpl storeAllegatoOmissis = new AdddocImpl();
									storeAllegatoOmissis.setBean(lAdddocBeanAllegatoOmissis);
									lLoginService.login(pAurigaLoginBean);
									session.doWork(new Work() {

										@Override
										public void execute(Connection paramConnection) throws SQLException {
											paramConnection.setAutoCommit(false);
											storeAllegatoOmissis.execute(paramConnection);
										}
									});									
									StoreResultBean<DmpkCoreAdddocBean> resultAllegatoOmissis = new StoreResultBean<DmpkCoreAdddocBean>();
									AnalyzeResult.analyze(lAdddocBeanAllegatoOmissis, resultAllegatoOmissis);
									resultAllegatoOmissis.setResultBean(lAdddocBeanAllegatoOmissis);									
									if (resultAllegatoOmissis.isInError()) {
										throw new StoreException(resultAllegatoOmissis);
									} else {
										mLogger.debug("idDocAllegatoOmissis vale " + resultAllegatoOmissis.getResultBean().getIddocout());
										RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
										lRebuildedFileOmissis.setFile(nextFileOmissis.next());
										lRebuildedFileOmissis.setInfo(pSalvaFascicoloIn.getInfoOmissis().get(i));
										lRebuildedFileOmissis.setIdDocumento(resultAllegatoOmissis.getResultBean().getIddocout());
										versioni.add(lRebuildedFileOmissis);								
									}
								}								
								countOmissis++;
							}
						}
					}
				}

				// Parte di versionamento
				Map<String, String> fileErrors = new HashMap<String, String>();
				fileErrors.putAll(aggiungiFilesInTransaction(pAurigaLoginBean, versioni, session, true));
				fileErrors.putAll(rimuoviFilesInTransaction(pAurigaLoginBean, versioniDaRimuovere, new ArrayList<RebuildedFile>(), session, true));				
				lSalvaFascicoloOut.setFileInErrors(fileErrors);
				
				session.flush();
				lTransaction.commit();
				
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				lSalvaFascicoloOut.setInError(true);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lSalvaFascicoloOut, ((StoreException) e).getError());
					return lSalvaFascicoloOut;
				} else {
					lSalvaFascicoloOut.setDefaultMessage(e.getMessage());
					return lSalvaFascicoloOut;
				}
			} finally {
				HibernateUtil.release(session);
			}

		} catch (Exception e) {
			lSalvaFascicoloOut.setInError(true);
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lSalvaFascicoloOut, ((StoreException) e).getError());
				return lSalvaFascicoloOut;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lSalvaFascicoloOut.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lSalvaFascicoloOut;
			}
		}

		return lSalvaFascicoloOut;
	}
	
	@Operation(name = "modificaFascicolo")
	public ModificaFascicoloOut modificaFascicolo(AurigaLoginBean pAurigaLoginBean, ModificaFascicoloIn pModificaFascicoloIn) throws Exception {

		if(pModificaFascicoloIn.getIsCaricaPraticaPregressa() != null && pModificaFascicoloIn.getIsCaricaPraticaPregressa()) {
			return modificaPraticaPregressa(pAurigaLoginBean, pModificaFascicoloIn);
		}
		
		ModificaFascicoloOut lModificaFascicoloOut = new ModificaFascicoloOut(); 

		List<AllegatiOutBean> listaDocumentiIstruttoria = null;
		if(pModificaFascicoloIn.getIdUdAtto() != null) {
			try {
				RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
				lRecuperaDocumentoInBean.setIdUd(pModificaFascicoloIn.getIdUdAtto());
				RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
				RecuperaDocumentoOutBean lRecuperaDocumentoOutBean;
				DocumentoXmlOutBean lDocumentoXmlOutBean = null;
				lRecuperaDocumentoOutBean = lRecuperoDocumenti.loadDocumento(pAurigaLoginBean, lRecuperaDocumentoInBean);
				lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
				listaDocumentiIstruttoria = lDocumentoXmlOutBean != null ? lDocumentoXmlOutBean.getDocumentiProcFolder() : null;
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaFascicoloOut.setDefaultMessage("Il documento dell'atto del fascicolo da aggiornare e' inesistente");
				return lModificaFascicoloOut;
			}
		} else {
			try {
				LoadFascicoloIn lLoadFascicoloIn = new LoadFascicoloIn();
				lLoadFascicoloIn.setIdFolderIn(pModificaFascicoloIn.getIdFolderIn());
				LoadFascicoloOut lLoadFascicoloOut = loadFascicolo(pAurigaLoginBean, lLoadFascicoloIn);
				XmlFascicoloOut lXmlFascicoloOut = lLoadFascicoloOut.getXmlFascicoloOut();
				listaDocumentiIstruttoria = lXmlFascicoloOut != null ? lXmlFascicoloOut.getListaDocumentiIstruttoria() : null;
			} catch (Exception e) {
				lModificaFascicoloOut.setInError(true);
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaFascicoloOut.setDefaultMessage("Il fascicolo da aggiornare e' inesistente");
				return lModificaFascicoloOut;
			} 
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();		

		DmpkCoreUpdfolderBean lUpdfolderBean = new DmpkCoreUpdfolderBean();
		lUpdfolderBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lUpdfolderBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
		lUpdfolderBean.setIdfolderin(pModificaFascicoloIn.getIdFolderIn());
		lUpdfolderBean.setIdlibraryin(pModificaFascicoloIn.getIdLibrary());
		lUpdfolderBean.setNomelibraryin(pModificaFascicoloIn.getNomeLibrary());
		lUpdfolderBean.setFolderpathin(pModificaFascicoloIn.getFolderPath());

		if(pModificaFascicoloIn.getModificaFascicolo() != null) {
			String lStrXml = lXmlUtilitySerializer.bindXmlCompleta(pModificaFascicoloIn.getModificaFascicolo());
			lUpdfolderBean.setAttributixmlin(lStrXml);
		}

		lUpdfolderBean.setFlgautocommitin(0); // blocco l'autocommit

		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		List<RebuildedFile> versioniDaRimuovere = new ArrayList<RebuildedFile>();
		List<RebuildedFile> documentiDaRimuovere = new ArrayList<RebuildedFile>();

		try {
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {

				if(lUpdfolderBean.getAttributixmlin() != null) {
					
					final UpdfolderImpl store = new UpdfolderImpl();
					store.setBean(lUpdfolderBean);
	
					LoginService lLoginService = new LoginService();
					lLoginService.login(pAurigaLoginBean);
					session.doWork(new Work() {
	
						@Override
						public void execute(Connection paramConnection) throws SQLException {
							paramConnection.setAutoCommit(false);
							store.execute(paramConnection);
						}
					});
	
					StoreResultBean<DmpkCoreUpdfolderBean> result = new StoreResultBean<DmpkCoreUpdfolderBean>();
					AnalyzeResult.analyze(lUpdfolderBean, result);
					result.setResultBean(lUpdfolderBean);
	
					lModificaFascicoloOut.setUriPerAggiornamentoContainer(result.getResultBean().getUrixaggcontainerout());
					
					if (result.isInError()) {
						throw new StoreException(result);
					}
				}

				if (pModificaFascicoloIn != null) {
					if (listaDocumentiIstruttoria != null) {
						HashMap<String, AllegatiOutBean> documentiMap = new HashMap<String, AllegatiOutBean>();
						for (AllegatiOutBean doc : listaDocumentiIstruttoria) {
							boolean trovato = false;
							if (pModificaFascicoloIn.getIdDocumento() != null) {
								for (int i = 0; i < pModificaFascicoloIn.getIdDocumento().size(); i++) {
									String idDoc = (pModificaFascicoloIn.getIdDocumento().get(i) != null) ? pModificaFascicoloIn.getIdDocumento().get(i)
											.longValue()
											+ "" : null;
									if (idDoc != null && idDoc.equals(doc.getIdDoc())) {
										trovato = true;
										documentiMap.put(idDoc, doc);
										break;
									}
								}
							}																
							if (!trovato && pModificaFascicoloIn.getFlgAppendDocFascIstruttoria() != Flag.SETTED) {
								RebuildedFile lRebuildedFile = new RebuildedFile();
								lRebuildedFile.setIdDocumento(new BigDecimal(doc.getIdDoc()));
								FileInfoBean info = new FileInfoBean();
								info.setTipo(TipoFile.PRIMARIO);
								GenericFile allegatoRiferimento = new GenericFile();
								allegatoRiferimento.setDisplayFilename(doc.getDisplayFileName());
								info.setAllegatoRiferimento(allegatoRiferimento);
								lRebuildedFile.setInfo(info);
								// devo passare anche idFolder e idUd per togliere la unita doc. dal folder
								lRebuildedFile.setIdFolder(pModificaFascicoloIn.getIdFolderIn());
								lRebuildedFile.setIdUd(new BigDecimal(doc.getIdUd()));
								documentiDaRimuovere.add(lRebuildedFile);
							}
						}
						mLogger.debug("documentiMap " + documentiMap);
					}
					Iterator<File> nextFile = null;
					if (pModificaFascicoloIn.getFileAllegati() != null)
						nextFile = pModificaFascicoloIn.getFileAllegati().iterator();

					if (pModificaFascicoloIn.getIdDocumento() != null) {
						for (int i = 0; i < pModificaFascicoloIn.getIdDocumento().size(); i++) {
							boolean flgSalvato = pModificaFascicoloIn.getFlgSalvato() != null && pModificaFascicoloIn.getFlgSalvato().get(i) != null && pModificaFascicoloIn.getFlgSalvato().get(i);
							boolean isDocumentoNewOrChanged = (pModificaFascicoloIn.getIdDocumento().get(i) == null) || (pModificaFascicoloIn.getIsNewOrChanged().get(i) != null && pModificaFascicoloIn.getIsNewOrChanged().get(i));
							boolean skipAggiornaMetadatiDocumento = pModificaFascicoloIn.getFlgNoModificaDati() != null && pModificaFascicoloIn.getFlgNoModificaDati().get(i) != null && pModificaFascicoloIn.getFlgNoModificaDati().get(i) && !isDocumentoNewOrChanged;
							String attributi = null;
							if(skipAggiornaMetadatiDocumento) {
								if(!flgSalvato) {
									// Se è una UD protocollata o repertoriata non devo salvare tutti i metadati ma solo agganciarla al fascicolo, se non era già stata agganciata								
									AllegatoStoreBean lDocumentoBeanProtEsistente = new AllegatoStoreBean();
									lDocumentoBeanProtEsistente.setNatura("P");
									List<FolderCustom> listaFolderCustom = new ArrayList<FolderCustom>();
									FolderCustom folderCustom = new FolderCustom();
									folderCustom.setId(String.valueOf(pModificaFascicoloIn.getIdFolderIn().longValue()));
									listaFolderCustom.add(folderCustom);
									lDocumentoBeanProtEsistente.setFolderCustom(listaFolderCustom);
									lDocumentoBeanProtEsistente.setFlgAppendFolderCustom("1");  // devo salvare in append altrimenti in update mi cancella tutti gli altri folder a cui il documento appartiene
									attributi = lXmlUtilitySerializer.bindXml(lDocumentoBeanProtEsistente);
								}
							} else {
								AllegatoStoreBean lDocumentoBean = new AllegatoStoreBean();
								// Setto la natura a NULL perchè non lo sto salvando come allegato ma come file primario
								lDocumentoBean.setNatura("P");
								lDocumentoBean.setDescrizione(pModificaFascicoloIn.getDescrizione().get(i));
								lDocumentoBean.setIdDocType(pModificaFascicoloIn.getDocType().get(i));
								lDocumentoBean.setSezionePratica(pModificaFascicoloIn.getSezionePratica().get(i));																																			
								if(!flgSalvato) {
									List<FolderCustom> listaFolderCustom = new ArrayList<FolderCustom>();
									FolderCustom folderCustom = new FolderCustom();
									folderCustom.setId(String.valueOf(pModificaFascicoloIn.getIdFolderIn().longValue()));
									listaFolderCustom.add(folderCustom);
									lDocumentoBean.setFolderCustom(listaFolderCustom);
									lDocumentoBean.setFlgAppendFolderCustom("1"); // devo salvare in append altrimenti in update mi cancella tutti gli altri folder a cui il documento appartiene
								}
								lDocumentoBean.setFlgEreditaPermessi("1");
								lDocumentoBean.setIdFolderEreditaPerm(pModificaFascicoloIn.getIdFolderIn());
								if (pModificaFascicoloIn.getFlgParteDispositivo() != null) {
									Boolean flgParteDispositivo = pModificaFascicoloIn.getFlgParteDispositivo().get(i);
									lDocumentoBean.setFlgParteDispositivo(flgParteDispositivo != null && flgParteDispositivo ? "1" : null);
								}
								if (pModificaFascicoloIn.getIdTask() != null) {
									lDocumentoBean.setIdTask(pModificaFascicoloIn.getIdTask().get(i));
								}
								if (pModificaFascicoloIn.getFlgNoPubbl() != null) {
									Boolean flgNoPubbl = pModificaFascicoloIn.getFlgNoPubbl().get(i);
									lDocumentoBean.setFlgNoPubbl(flgNoPubbl != null && flgNoPubbl ? "1" : null);
								}
								if (pModificaFascicoloIn.getFlgPubblicaSeparato() != null) {
									Boolean flgPubblicaSeparato = pModificaFascicoloIn.getFlgPubblicaSeparato().get(i);
									lDocumentoBean.setFlgPubblicaSeparato(flgPubblicaSeparato != null && flgPubblicaSeparato ? "1" : null);
								}								
								if (pModificaFascicoloIn.getFlgOriginaleCartaceo() != null) {
									Boolean flgOriginaleCartaceo = pModificaFascicoloIn.getFlgOriginaleCartaceo().get(i);
									lDocumentoBean.setFlgOriginaleCartaceo(flgOriginaleCartaceo != null && flgOriginaleCartaceo ? "1" : null);
								}
								if (pModificaFascicoloIn.getFlgCopiaSostitutiva() != null) {
									Boolean flgCopiaSostitutiva = pModificaFascicoloIn.getFlgCopiaSostitutiva().get(i);
									lDocumentoBean.setFlgCopiaSostitutiva(flgCopiaSostitutiva != null && flgCopiaSostitutiva ? "1" : null);
								}								
								if (pModificaFascicoloIn.getFlgDaProtocollare() != null) {
									Boolean flgDaProtocollare = pModificaFascicoloIn.getFlgDaProtocollare().get(i);
									if(flgDaProtocollare != null && flgDaProtocollare) {
										List<TipoNumerazioneBean> listaTipiNumerazioni = new ArrayList<TipoNumerazioneBean>();
										TipoNumerazioneBean lTipoProtocolloGenerale = new TipoNumerazioneBean();								
										lTipoProtocolloGenerale.setCategoria("PG");
										lTipoProtocolloGenerale.setSigla(null);
										listaTipiNumerazioni.add(lTipoProtocolloGenerale);
										lDocumentoBean.setTipoNumerazioni(listaTipiNumerazioni);	
									}
								}								
								attributi = lXmlUtilitySerializer.bindXml(lDocumentoBean);
							}
							if(attributi != null) {
								String idDoc = (pModificaFascicoloIn.getIdDocumento().get(i) != null) ? pModificaFascicoloIn.getIdDocumento().get(i).longValue() + "" : null;
								if (idDoc != null) {
									DmpkCoreUpddocudBean lUpddocudBean = new DmpkCoreUpddocudBean();
									lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lUpddocudBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
									lUpddocudBean.setFlgtipotargetin("D");
									lUpddocudBean.setIduddocin(pModificaFascicoloIn.getIdDocumento().get(i));
									lUpddocudBean.setAttributiuddocxmlin(attributi);
									final UpddocudImpl lUpddocud = new UpddocudImpl();
									lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lUpddocud.setBean(lUpddocudBean);
									session.doWork(new Work() {
	
										@Override
										public void execute(Connection paramConnection) throws SQLException {
											lUpddocud.execute(paramConnection);
										}
									});
									StoreResultBean<DmpkCoreUpddocudBean> lUpddocudResult = new StoreResultBean<DmpkCoreUpddocudBean>();
									AnalyzeResult.analyze(lUpddocudBean, lUpddocudResult);
									lUpddocudResult.setResultBean(lUpddocudBean);									
									if (lUpddocudResult.isInError()) {
										throw new StoreException(lUpddocudResult);
									}
									if (!pModificaFascicoloIn.getIsNull().get(i) && pModificaFascicoloIn.getIsNewOrChanged().get(i)) {
										RebuildedFile lRebuildedFile = new RebuildedFile();
										lRebuildedFile.setFile(nextFile.next());
										lRebuildedFile.setInfo(pModificaFascicoloIn.getInfo().get(i));
										lRebuildedFile.setIdDocumento(new BigDecimal(idDoc));
										lRebuildedFile.setIdTask(pModificaFascicoloIn.getIdTaskVer().get(i));
										versioni.add(lRebuildedFile);
									} else if (pModificaFascicoloIn.getIsNull().get(i)) {
										int pos = 1;
										for (AllegatiOutBean doc : listaDocumentiIstruttoria) {
											if (doc.getIdDoc().equals(idDoc)) {
												if (doc.getUri() != null && StringUtils.isNotBlank(doc.getUri())) {
													RebuildedFile lRebuildedFile = new RebuildedFile();
													lRebuildedFile.setIdDocumento(new BigDecimal(idDoc));
													FileInfoBean info = new FileInfoBean();
													info.setTipo(TipoFile.PRIMARIO);
													GenericFile allegatoRiferimento = new GenericFile();
													allegatoRiferimento.setDisplayFilename(doc.getDisplayFileName());
													info.setAllegatoRiferimento(allegatoRiferimento);
													info.setPosizione(pos);
													lRebuildedFile.setInfo(info);
													lRebuildedFile.setPosizione(pos);
													versioniDaRimuovere.add(lRebuildedFile);
												}
											}
											pos++;
										}
									}
								} else {
									DmpkCoreAdddocBean lAdddocBean = new DmpkCoreAdddocBean();
									lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lAdddocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
									lAdddocBean.setAttributiuddocxmlin(attributi);
									final AdddocImpl lAdddoc = new AdddocImpl();
									lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lAdddoc.setBean(lAdddocBean);
									session.doWork(new Work() {
	
										@Override
										public void execute(Connection paramConnection) throws SQLException {
											lAdddoc.execute(paramConnection);
										}
									});
									StoreResultBean<DmpkCoreAdddocBean> lAdddocResult = new StoreResultBean<DmpkCoreAdddocBean>();
									AnalyzeResult.analyze(lAdddocBean, lAdddocResult);
									lAdddocResult.setResultBean(lAdddocBean);									
									if (lAdddocResult.isInError()) {
										throw new StoreException(lAdddocResult);
									}
									mLogger.debug("idDoc vale " + lAdddocResult.getResultBean().getIddocout());
									if (!pModificaFascicoloIn.getIsNull().get(i)) {
										RebuildedFile lRebuildedFile = new RebuildedFile();
										lRebuildedFile.setFile(nextFile.next());
										lRebuildedFile.setInfo(pModificaFascicoloIn.getInfo().get(i));
										lRebuildedFile.setIdDocumento(lAdddocResult.getResultBean().getIddocout());
										lRebuildedFile.setIdTask(pModificaFascicoloIn.getIdTaskVer().get(i));
										versioni.add(lRebuildedFile);
									}
								}
							}
						}
					}
				}

				// Parte di versionamento
				Map<String, String> fileErrors = new HashMap<String, String>();
				fileErrors.putAll(aggiungiFilesInTransaction(pAurigaLoginBean, versioni, session));
				fileErrors.putAll(rimuoviFilesInTransaction(pAurigaLoginBean, versioniDaRimuovere, documentiDaRimuovere, session));
				lModificaFascicoloOut.setFileInErrors(fileErrors);

				session.flush();
				lTransaction.commit();

			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaFascicoloOut.setInError(true);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lModificaFascicoloOut, ((StoreException) e).getError());
					return lModificaFascicoloOut;
				} else {
					lModificaFascicoloOut.setDefaultMessage(e.getMessage());
					return lModificaFascicoloOut;
				}
			} finally {
				HibernateUtil.release(session);
			}

		} catch (Exception e) {
			lModificaFascicoloOut.setInError(true);
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lModificaFascicoloOut, ((StoreException) e).getError());
				return lModificaFascicoloOut;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaFascicoloOut.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lModificaFascicoloOut;
			}
		}

		return lModificaFascicoloOut;
	}

	public ModificaFascicoloOut modificaPraticaPregressa(AurigaLoginBean pAurigaLoginBean, ModificaFascicoloIn pModificaFascicoloIn) throws Exception {

		ModificaFascicoloOut lModificaFascicoloOut = new ModificaFascicoloOut(); 

		XmlFascicoloOut lXmlFascicoloOut = null;
		try {
			LoadFascicoloIn lLoadFascicoloIn = new LoadFascicoloIn();
			lLoadFascicoloIn.setIdFolderIn(pModificaFascicoloIn.getIdFolderIn());
			LoadFascicoloOut lLoadFascicoloOut = loadFascicolo(pAurigaLoginBean, lLoadFascicoloIn);
			lXmlFascicoloOut = lLoadFascicoloOut.getXmlFascicoloOut();
		} catch (Exception e) {
			lModificaFascicoloOut.setInError(true);
			mLogger.error("Errore " + e.getMessage(), e);
			lModificaFascicoloOut.setDefaultMessage("Il fascicolo da aggiornare e' inesistente");
			return lModificaFascicoloOut;
		}

		DmpkCoreUpdfolderBean lUpdfolderBean = new DmpkCoreUpdfolderBean();
		lUpdfolderBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lUpdfolderBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
		lUpdfolderBean.setIdfolderin(pModificaFascicoloIn.getIdFolderIn());
		lUpdfolderBean.setIdlibraryin(pModificaFascicoloIn.getIdLibrary());
		lUpdfolderBean.setNomelibraryin(pModificaFascicoloIn.getNomeLibrary());
		lUpdfolderBean.setFolderpathin(pModificaFascicoloIn.getFolderPath());

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String lStrXml = lXmlUtilitySerializer.bindXmlCompleta(pModificaFascicoloIn.getModificaFascicolo());
		lUpdfolderBean.setAttributixmlin(lStrXml);

		lUpdfolderBean.setFlgautocommitin(0); // blocco l'autocommit

		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		List<RebuildedFile> versioniDaRimuovere = new ArrayList<RebuildedFile>();
		List<RebuildedFile> documentiDaRimuovere = new ArrayList<RebuildedFile>();
		
		try {
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {

				final UpdfolderImpl store = new UpdfolderImpl();
				store.setBean(lUpdfolderBean);

				LoginService lLoginService = new LoginService();
				lLoginService.login(pAurigaLoginBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreUpdfolderBean> result = new StoreResultBean<DmpkCoreUpdfolderBean>();
				AnalyzeResult.analyze(lUpdfolderBean, result);
				result.setResultBean(lUpdfolderBean);

				lModificaFascicoloOut.setUriPerAggiornamentoContainer(result.getResultBean().getUrixaggcontainerout());

				if (result.isInError()) {
					throw new StoreException(result);
				} else {
					if (pModificaFascicoloIn != null) {
						HashMap<String, AllegatiOutBean> documentiMap = new HashMap<String, AllegatiOutBean>();
						if (lXmlFascicoloOut.getListaDocumentiIstruttoria() != null) {
							for (AllegatiOutBean doc : lXmlFascicoloOut.getListaDocumentiIstruttoria()) {
								boolean trovato = false;
								if (pModificaFascicoloIn.getIdDocumento() != null) {
									for (int i = 0; i < pModificaFascicoloIn.getIdDocumento().size(); i++) {
										String idDoc = (pModificaFascicoloIn.getIdDocumento().get(i) != null) ? pModificaFascicoloIn.getIdDocumento().get(i)
												.longValue()
												+ "" : null;
										if (idDoc != null && idDoc.equals(doc.getIdDoc())) {
											trovato = true;
											documentiMap.put(idDoc, doc);
											break;
										}
									}
								}
								if (!trovato) {
									RebuildedFile lRebuildedFile = new RebuildedFile();
									lRebuildedFile.setIdDocumento(new BigDecimal(doc.getIdDoc()));
									FileInfoBean info = new FileInfoBean();
									info.setTipo(doc.getFlgAllegato() == Flag.SETTED ? TipoFile.ALLEGATO : TipoFile.PRIMARIO);									
									GenericFile allegatoRiferimento = new GenericFile();
									allegatoRiferimento.setDisplayFilename(doc.getDisplayFileName());
									info.setAllegatoRiferimento(allegatoRiferimento);
									lRebuildedFile.setInfo(info);
									// Se in carica pratica pregressa viene tolta la riga di un documento non va' eliminata quell'unita doc. ma solo annullata la versione
//									documentiDaRimuovere.add(lRebuildedFile);
									versioniDaRimuovere.add(lRebuildedFile);
								}
							}
							mLogger.debug("documentiMap " + documentiMap);
						}
						
						HashMap<Integer, BigDecimal> mappaIdUdAllegato = new HashMap<Integer, BigDecimal>();
						HashMap<Integer, BigDecimal> mappaIdDocAllegato = new HashMap<Integer, BigDecimal>();
						HashSet<String> setEstremiDocProtGiaInseriti = new HashSet<String>();						
						HashMap<String, BigDecimal> mappaEstremiDocProtIdUd = new HashMap<String, BigDecimal>();						
						int count = 1;
						Iterator<File> nextFile = null;
						if (pModificaFascicoloIn.getFileAllegati() != null)
							nextFile = pModificaFascicoloIn.getFileAllegati().iterator();

						if (pModificaFascicoloIn.getIdDocumento() != null) {
							for (int i = 0; i < pModificaFascicoloIn.getIdDocumento().size(); i++) {
								AllegatoStoreBean lDocumentoBean = new AllegatoStoreBean();
								BigDecimal idUdAllegato = pModificaFascicoloIn.getIdUd() != null ? pModificaFascicoloIn.getIdUd().get(i) : null;								
								BigDecimal idDocAllegato = pModificaFascicoloIn.getIdDocumento() != null ? pModificaFascicoloIn.getIdDocumento().get(i) : null;								
								boolean isProtGiaInserito = false;
								String tipoProt = pModificaFascicoloIn.getTipoProt() != null ? pModificaFascicoloIn.getTipoProt().get(i) : null;
								String siglaProtSettore = pModificaFascicoloIn.getSiglaProtSettore() != null ? pModificaFascicoloIn.getSiglaProtSettore().get(i) : null;
								String nroProt = pModificaFascicoloIn.getNroProt() != null ? pModificaFascicoloIn.getNroProt().get(i) : null;
								String annoProt = pModificaFascicoloIn.getAnnoProt() != null ? pModificaFascicoloIn.getAnnoProt().get(i) : null;
								String estremiDoc = null;
								if(StringUtils.isNotBlank(tipoProt) && StringUtils.isNotBlank(nroProt) && StringUtils.isNotBlank(annoProt)) {
									if(siglaProtSettore != null) {
										estremiDoc = tipoProt + " " + siglaProtSettore + " " + nroProt + " " + annoProt;
									} else{ 
										estremiDoc = tipoProt + " " + nroProt + " " + annoProt;											
									}
									if(!setEstremiDocProtGiaInseriti.contains(estremiDoc)) {
										setEstremiDocProtGiaInseriti.add(estremiDoc);																						
										lDocumentoBean.setRegistrazioniDate(new ArrayList<RegistroEmergenza>());
										RegistroEmergenza registro = new RegistroEmergenza();
										registro.setFisso(tipoProt);
										registro.setRegistro(siglaProtSettore);
										registro.setNro(nroProt);
										registro.setAnno(annoProt);
										lDocumentoBean.getRegistrazioniDate().add(registro);	
									} else {
										isProtGiaInserito = true;
										if(idUdAllegato == null) {
											idUdAllegato = mappaEstremiDocProtIdUd.containsKey(estremiDoc) ? mappaEstremiDocProtIdUd.get(estremiDoc) : null;
										}
									}		
								}								
								lDocumentoBean.setNroDeposito(pModificaFascicoloIn.getNroDeposito() != null ? pModificaFascicoloIn.getNroDeposito().get(i) : null);
								lDocumentoBean.setAnnoDeposito(pModificaFascicoloIn.getAnnoDeposito() != null ? pModificaFascicoloIn.getAnnoDeposito().get(i) : null);
								Boolean flgNextDocAllegato = pModificaFascicoloIn.getFlgNextDocAllegato() != null ? pModificaFascicoloIn.getFlgNextDocAllegato().get(i) : null;
								Boolean flgAllegato = pModificaFascicoloIn.getFlgAllegato() != null ? pModificaFascicoloIn.getFlgAllegato().get(i) : null;
								if(isProtGiaInserito || (flgNextDocAllegato != null && flgNextDocAllegato) || (flgAllegato != null && flgAllegato)) {
									lDocumentoBean.setNatura("ALL");
								} else {
									// Setto la natura a NULL perchè non lo sto salvando come allegato ma come file primario
									lDocumentoBean.setNatura("P");		
								}
								lDocumentoBean.setDescrizione(pModificaFascicoloIn.getDescrizione() != null ? pModificaFascicoloIn.getDescrizione().get(i) : null);
								lDocumentoBean.setIdDocType(pModificaFascicoloIn.getDocType() != null ? pModificaFascicoloIn.getDocType().get(i) : null);
								lDocumentoBean.setNomeDocType(pModificaFascicoloIn.getNomeDocType() != null ? pModificaFascicoloIn.getNomeDocType().get(i) : null);
								lDocumentoBean.setSezionePratica(pModificaFascicoloIn.getSezionePratica() != null ? pModificaFascicoloIn.getSezionePratica().get(i) : null);																
								if(pModificaFascicoloIn.getMittentiEsibenti() != null) {
									List<MittentiDocumentoBean> listaMittentiEsibenti = new ArrayList<MittentiDocumentoBean>();
									if(StringUtils.isNotBlank(pModificaFascicoloIn.getMittentiEsibenti().get(i))) {
										String[] tokens = pModificaFascicoloIn.getMittentiEsibenti().get(i).replace(",", ";").split(";");
										for(int j = 0; j < tokens.length; j++) {
											MittentiDocumentoBean mittenteEsibente = new MittentiDocumentoBean();
											mittenteEsibente.setDenominazioneCognome(tokens[j]);	
											listaMittentiEsibenti.add(mittenteEsibente);	
										}
									}
									lDocumentoBean.setMittentiEsibenti(listaMittentiEsibenti);
								}
								if (pModificaFascicoloIn.getFlgUdDaDataEntryScansioni() != null) {
									Boolean flgUdDaDataEntryScansioni = pModificaFascicoloIn.getFlgUdDaDataEntryScansioni().get(i);									
									lDocumentoBean.setFlgUdDaDataEntryScansioni(flgUdDaDataEntryScansioni != null && flgUdDaDataEntryScansioni ? "1" : null);									
								}
								if (pModificaFascicoloIn.getFlgDatiSensibili() != null) {
									Boolean flgDatiSensibili = pModificaFascicoloIn.getFlgDatiSensibili().get(i);
									lDocumentoBean.setFlgDatiSensibili(flgDatiSensibili != null && flgDatiSensibili ? "1" : null);
								}
								List<FolderCustom> listaFolderCustom = new ArrayList<FolderCustom>();
								FolderCustom folderCustom = new FolderCustom();
								folderCustom.setId(pModificaFascicoloIn.getIdFolderIn() != null ? String.valueOf(pModificaFascicoloIn.getIdFolderIn().longValue()) : null);
								if (pModificaFascicoloIn.getFlgCapofila() != null) {
									Boolean flgCapofila = pModificaFascicoloIn.getFlgCapofila().get(i);									
									folderCustom.setTipoRelazione(flgCapofila != null && flgCapofila ? "CPF" : null);
								}
								listaFolderCustom.add(folderCustom);
								lDocumentoBean.setFolderCustom(listaFolderCustom);
//								lDocumentoBean.setFlgAppendFolderCustom("1"); // qui non serve l'append perchè chiamo sempre la AddDoc e non la UpdDoc
								lDocumentoBean.setFlgEreditaPermessi("1");
								lDocumentoBean.setIdFolderEreditaPerm(pModificaFascicoloIn.getIdFolderIn());
								if (pModificaFascicoloIn.getFlgParteDispositivo() != null) {
									Boolean flgParteDispositivo = pModificaFascicoloIn.getFlgParteDispositivo().get(i);
									lDocumentoBean.setFlgParteDispositivo(flgParteDispositivo != null && flgParteDispositivo ? "1" : null);
								}
								if (pModificaFascicoloIn.getIdTask() != null) {
									lDocumentoBean.setIdTask(pModificaFascicoloIn.getIdTask().get(i));
								}
								if (pModificaFascicoloIn.getFlgNoPubbl() != null) {
									Boolean flgNoPubbl = pModificaFascicoloIn.getFlgNoPubbl().get(i);
									lDocumentoBean.setFlgNoPubbl(flgNoPubbl != null && flgNoPubbl ? "1" : null);
								}
								if (pModificaFascicoloIn.getFlgPubblicaSeparato() != null) {
									Boolean flgPubblicaSeparato = pModificaFascicoloIn.getFlgPubblicaSeparato().get(i);
									lDocumentoBean.setFlgPubblicaSeparato(flgPubblicaSeparato != null && flgPubblicaSeparato ? "1" : null);
								}								
								if (pModificaFascicoloIn.getFlgOriginaleCartaceo() != null) {
									Boolean flgOriginaleCartaceo = pModificaFascicoloIn.getFlgOriginaleCartaceo().get(i);
									lDocumentoBean.setFlgOriginaleCartaceo(flgOriginaleCartaceo != null && flgOriginaleCartaceo ? "1" : null);
								}
								if (pModificaFascicoloIn.getFlgCopiaSostitutiva() != null) {
									Boolean flgCopiaSostitutiva = pModificaFascicoloIn.getFlgCopiaSostitutiva().get(i);
									lDocumentoBean.setFlgCopiaSostitutiva(flgCopiaSostitutiva != null && flgCopiaSostitutiva ? "1" : null);
								}								
								if (pModificaFascicoloIn.getFlgDaProtocollare() != null) {
									Boolean flgDaProtocollare = pModificaFascicoloIn.getFlgDaProtocollare().get(i);
									if(flgDaProtocollare != null && flgDaProtocollare) {
										List<TipoNumerazioneBean> listaTipiNumerazioni = new ArrayList<TipoNumerazioneBean>();
										TipoNumerazioneBean lTipoProtocolloGenerale = new TipoNumerazioneBean();								
										lTipoProtocolloGenerale.setCategoria("PG");
										lTipoProtocolloGenerale.setSigla(null);
										listaTipiNumerazioni.add(lTipoProtocolloGenerale);
										lDocumentoBean.setTipoNumerazioni(listaTipiNumerazioni);	
									}
								}							
								boolean isAllegato = lDocumentoBean.getNatura() != null && "ALL".equals(lDocumentoBean.getNatura());									
								boolean fromLoadDett = idDocAllegato != null ? documentiMap.containsKey(idDocAllegato.longValue() + "") : false;							
								if (idDocAllegato != null && (!isAllegato || fromLoadDett)) {
									// se è un documento primario, o un allegato caricato da dettaglio, allora esiste già e devo fare update
									mappaIdUdAllegato.put(new Integer(count), idUdAllegato);
									mappaIdDocAllegato.put(new Integer(count), idDocAllegato);
									mappaEstremiDocProtIdUd.put(estremiDoc, idUdAllegato);								
									AllegatoStoreBean lDocumentoBeanProtEsistente = new AllegatoStoreBean();
									lDocumentoBeanProtEsistente.setIdUd(idUdAllegato);
									lDocumentoBeanProtEsistente.setNatura(lDocumentoBean.getNatura());
									lDocumentoBeanProtEsistente.setFolderCustom(lDocumentoBean.getFolderCustom());
									lDocumentoBeanProtEsistente.setFlgAppendFolderCustom("1"); // devo salvare in append altrimenti in update mi cancella tutti gli altri folder a cui il documento appartiene
									lDocumentoBeanProtEsistente.setFlgDatiSensibili(lDocumentoBean.getFlgDatiSensibili());
									String attributi = lXmlUtilitySerializer.bindXml(lDocumentoBeanProtEsistente);
									DmpkCoreUpddocudBean lUpddocudBean = new DmpkCoreUpddocudBean();
									lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lUpddocudBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
									lUpddocudBean.setFlgtipotargetin("D");
									lUpddocudBean.setIduddocin(idDocAllegato);
									lUpddocudBean.setAttributiuddocxmlin(attributi);
									final UpddocudImpl lUpddocud = new UpddocudImpl();
									lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lUpddocud.setBean(lUpddocudBean);
									session.doWork(new Work() {

										@Override
										public void execute(Connection paramConnection) throws SQLException {
											lUpddocud.execute(paramConnection);
										}
									});
									StoreResultBean<DmpkCoreUpddocudBean> lUpddocudResult = new StoreResultBean<DmpkCoreUpddocudBean>();
									AnalyzeResult.analyze(lUpddocudBean, lUpddocudResult);
									lUpddocudResult.setResultBean(lUpddocudBean);								
									if (lUpddocudResult.isInError()) {
										throw new StoreException(lUpddocudResult);
									}
									if (!pModificaFascicoloIn.getIsNull().get(i) && pModificaFascicoloIn.getIsNewOrChanged().get(i)) {
										RebuildedFile lRebuildedFile = new RebuildedFile();
										lRebuildedFile.setFile(nextFile.next());
										lRebuildedFile.setInfo(pModificaFascicoloIn.getInfo() != null ? pModificaFascicoloIn.getInfo().get(i) : null);
										lRebuildedFile.setIdDocumento(idDocAllegato);
										lRebuildedFile.setIdTask(pModificaFascicoloIn.getIdTaskVer() != null ? pModificaFascicoloIn.getIdTaskVer().get(i) : null);
										versioni.add(lRebuildedFile);
									} else if (pModificaFascicoloIn.getIsNull().get(i)) {
										for (AllegatiOutBean doc : lXmlFascicoloOut.getListaDocumentiIstruttoria()) {
											if (StringUtils.isNotBlank(doc.getIdDoc()) && String.valueOf(idDocAllegato.longValue()).equals(doc.getIdDoc())) {												
												if (doc.getUri() != null && StringUtils.isNotBlank(doc.getUri())) {
													RebuildedFile lRebuildedFile = new RebuildedFile();
													lRebuildedFile.setIdDocumento(idDocAllegato);
													FileInfoBean info = new FileInfoBean();
													info.setTipo(doc.getFlgAllegato() == Flag.SETTED ? TipoFile.ALLEGATO : TipoFile.PRIMARIO);														
													GenericFile allegatoRiferimento = new GenericFile();
													allegatoRiferimento.setDisplayFilename(doc.getDisplayFileName());
													info.setAllegatoRiferimento(allegatoRiferimento);
													lRebuildedFile.setInfo(info);
													versioniDaRimuovere.add(lRebuildedFile);
												}
											}
										}
									}
								} else {
									// Se sto facendo l'AddDoc del primario passo tutti i dati altrimenti NO
									String attributi = null;
									if(isAllegato) {										
										AllegatoStoreBean lDocumentoAllegato = new AllegatoStoreBean();
										lDocumentoAllegato.setIdUd(idUdAllegato);
										lDocumentoAllegato.setNatura(lDocumentoBean.getNatura());
										lDocumentoAllegato.setFolderCustom(lDocumentoBean.getFolderCustom());
//										lDocumentoAllegato.setFlgAppendFolderCustom("1");  // qui non serve l'append perchè chiamo sempre la AddDoc e non la UpdDoc
										lDocumentoAllegato.setFlgDatiSensibili(lDocumentoBean.getFlgDatiSensibili());
										attributi = lXmlUtilitySerializer.bindXml(lDocumentoAllegato);
									} else {
										attributi = lXmlUtilitySerializer.bindXmlCompleta(lDocumentoBean);	
									}
									DmpkCoreAdddocBean lAdddocBean = new DmpkCoreAdddocBean();
									lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lAdddocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
									lAdddocBean.setAttributiuddocxmlin(attributi);
									final AdddocImpl lAdddoc = new AdddocImpl();
									lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lAdddoc.setBean(lAdddocBean);
									session.doWork(new Work() {

										@Override
										public void execute(Connection paramConnection) throws SQLException {
											lAdddoc.execute(paramConnection);
										}
									});
									StoreResultBean<DmpkCoreAdddocBean> lAdddocResult = new StoreResultBean<DmpkCoreAdddocBean>();
									AnalyzeResult.analyze(lAdddocBean, lAdddocResult);
									lAdddocResult.setResultBean(lAdddocBean);									
									if (lAdddocResult.isInError()) {
										throw new StoreException(lAdddocResult);
									}
									mLogger.debug("idDoc vale " + lAdddocResult.getResultBean().getIddocout());
									mappaIdUdAllegato.put(new Integer(count), lAdddocResult.getResultBean().getIdudout());
									mappaIdDocAllegato.put(new Integer(count), lAdddocResult.getResultBean().getIddocout());
									mappaEstremiDocProtIdUd.put(estremiDoc, lAdddocResult.getResultBean().getIdudout());
									if (!pModificaFascicoloIn.getIsNull().get(i)) {
										RebuildedFile lRebuildedFile = new RebuildedFile();
										lRebuildedFile.setFile(nextFile.next());
										lRebuildedFile.setInfo(pModificaFascicoloIn.getInfo() != null ? pModificaFascicoloIn.getInfo().get(i) : null);
										lRebuildedFile.setIdDocumento(lAdddocResult.getResultBean().getIddocout());
										lRebuildedFile.setIdTask(pModificaFascicoloIn.getIdTaskVer() != null ? pModificaFascicoloIn.getIdTaskVer().get(i) : null);
										versioni.add(lRebuildedFile);
									}
								}
								count++;
							}
						}
						
						// Vers. con omissis
						int countOmissis = 1;
						Iterator<File> nextFileOmissis = null;
						if (pModificaFascicoloIn.getFileAllegatiOmissis() != null)
							nextFileOmissis = pModificaFascicoloIn.getFileAllegatiOmissis().iterator();
						
						if (pModificaFascicoloIn.getIdDocumentoOmissis() != null) {
							for (int i = 0; i < pModificaFascicoloIn.getIdDocumentoOmissis().size(); i++) {												
								BigDecimal idDocAllegatoOmissis = pModificaFascicoloIn.getIdDocumentoOmissis() != null ? pModificaFascicoloIn.getIdDocumentoOmissis().get(i) : null;
								if (idDocAllegatoOmissis != null) {
									// Non ha senso fare la Upddocud per la versione con omissis
									if (!pModificaFascicoloIn.getIsNullOmissis().get(i) && pModificaFascicoloIn.getIsNewOrChangedOmissis().get(i)) {
										RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
										lRebuildedFileOmissis.setFile(nextFileOmissis.next());
										lRebuildedFileOmissis.setInfo(pModificaFascicoloIn.getInfoOmissis().get(i));
										lRebuildedFileOmissis.setIdDocumento(idDocAllegatoOmissis);		
										versioni.add(lRebuildedFileOmissis);
									} else if (pModificaFascicoloIn.getIsNullOmissis().get(i)) {
										for (AllegatiOutBean doc : lXmlFascicoloOut.getListaDocumentiIstruttoria()) {
											if (StringUtils.isNotBlank(doc.getIdDocOmissis()) && String.valueOf(idDocAllegatoOmissis.longValue()).equals(doc.getIdDocOmissis())) {
												if (StringUtils.isNotBlank(doc.getUriOmissis())) {
													RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
													lRebuildedFileOmissis.setIdDocumento(idDocAllegatoOmissis);
													FileInfoBean infoOmissis = new FileInfoBean();
													infoOmissis.setTipo(TipoFile.ALLEGATO);
													GenericFile allegatoRiferimentoOmissis = new GenericFile();
													allegatoRiferimentoOmissis.setDisplayFilename(doc.getDisplayFileNameOmissis());
													infoOmissis.setAllegatoRiferimento(allegatoRiferimentoOmissis);
													lRebuildedFileOmissis.setInfo(infoOmissis);
													versioniDaRimuovere.add(lRebuildedFileOmissis);
												}
												break;
											}
										}
									}						
								} else if (!pModificaFascicoloIn.getIsNullOmissis().get(i)) {	
									BigDecimal idUdAllegato = mappaIdUdAllegato.get(new Integer(countOmissis));
									BigDecimal idDocAllegato = mappaIdDocAllegato.get(new Integer(countOmissis));
									AllegatoVersConOmissisStoreBean lAllegatoStoreBeanOmissis = new AllegatoVersConOmissisStoreBean();
									lAllegatoStoreBeanOmissis.setIdUd(idUdAllegato);					
									lAllegatoStoreBeanOmissis.setFlgVersConOmissis("1");
									lAllegatoStoreBeanOmissis.setIdDocVersIntegrale(idDocAllegato != null ? String.valueOf(idDocAllegato.longValue()) : null);				
									String attributiAllegatoOmissis = lXmlUtilitySerializer.bindXml(lAllegatoStoreBeanOmissis);	
									DmpkCoreAdddocBean lAdddocBeanAllegatoOmissis = new DmpkCoreAdddocBean();
									lAdddocBeanAllegatoOmissis.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lAdddocBeanAllegatoOmissis.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
									lAdddocBeanAllegatoOmissis.setAttributiuddocxmlin(attributiAllegatoOmissis);
									final AdddocImpl storeAllegatoOmissis = new AdddocImpl();
									lAdddocBeanAllegatoOmissis.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									storeAllegatoOmissis.setBean(lAdddocBeanAllegatoOmissis);
									lLoginService.login(pAurigaLoginBean);
									session.doWork(new Work() {

										@Override
										public void execute(Connection paramConnection) throws SQLException {
											paramConnection.setAutoCommit(false);
											storeAllegatoOmissis.execute(paramConnection);
										}
									});
									StoreResultBean<DmpkCoreAdddocBean> resultAllegatoOmissis = new StoreResultBean<DmpkCoreAdddocBean>();
									AnalyzeResult.analyze(lAdddocBeanAllegatoOmissis, resultAllegatoOmissis);
									resultAllegatoOmissis.setResultBean(lAdddocBeanAllegatoOmissis);									
									if (resultAllegatoOmissis.isInError()) {
										throw new StoreException(resultAllegatoOmissis);
									} else {
										mLogger.debug("idDocAllegatoOmissis vale " + resultAllegatoOmissis.getResultBean().getIddocout());
										RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
										lRebuildedFileOmissis.setFile(nextFileOmissis.next());
										lRebuildedFileOmissis.setInfo(pModificaFascicoloIn.getInfoOmissis().get(i));
										lRebuildedFileOmissis.setIdDocumento(resultAllegatoOmissis.getResultBean().getIddocout());
										versioni.add(lRebuildedFileOmissis);								
									}
								}
								countOmissis++;
							}
						}
					}
				}

				// Parte di versionamento
				Map<String, String> fileErrors = new HashMap<String, String>();
				fileErrors.putAll(aggiungiFilesInTransaction(pAurigaLoginBean, versioni, session, true));				
				fileErrors.putAll(rimuoviFilesInTransaction(pAurigaLoginBean, versioniDaRimuovere, documentiDaRimuovere, session, true));	
				lModificaFascicoloOut.setFileInErrors(fileErrors);

				session.flush();
				lTransaction.commit();

			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaFascicoloOut.setInError(true);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lModificaFascicoloOut, ((StoreException) e).getError());
					return lModificaFascicoloOut;
				} else {
					lModificaFascicoloOut.setDefaultMessage(e.getMessage());
					return lModificaFascicoloOut;
				}
			} finally {
				HibernateUtil.release(session);
			}

		} catch (Exception e) {
			lModificaFascicoloOut.setInError(true);
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lModificaFascicoloOut, ((StoreException) e).getError());
				return lModificaFascicoloOut;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaFascicoloOut.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lModificaFascicoloOut;
			}
		}

		return lModificaFascicoloOut;
	}

	protected Map<String, String> aggiungiFilesInTransaction(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioni, Session session) {
		return aggiungiFilesInTransaction(pAurigaLoginBean, versioni, session, false);
	}

	protected Map<String, String> aggiungiFilesInTransaction(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioni, Session session, boolean isCaricaPraticaPregressa) {

		Map<String, String> fileErrors = new HashMap<String, String>();

		for (RebuildedFile lRebuildedFile : versioni) {
			try {
				VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
				VersionaDocumentoOutBean lVersionaDocumentoOutBean = versionaDocumentoInTransaction(pAurigaLoginBean, lVersionaDocumentoInBean, session, isCaricaPraticaPregressa);
				if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
					throw new Exception(lVersionaDocumentoOutBean.getDefaultMessage());
				}
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				fileErrors.put(String.valueOf(lRebuildedFile.getIdDocumento()),
						"Il file " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename() + " non è stato salvato correttamente."
								+ (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
			}
		}

		return fileErrors;
	}

	protected Map<String, String> rimuoviFilesInTransaction(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioniDaRimuovere,
			List<RebuildedFile> documentiDaRimuovere, Session session) {
		return rimuoviFilesInTransaction(pAurigaLoginBean, versioniDaRimuovere, documentiDaRimuovere, session, false);
	}
	
	protected Map<String, String> rimuoviFilesInTransaction(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioniDaRimuovere,
			List<RebuildedFile> documentiDaRimuovere, Session session, boolean isCaricaPraticaPregressa) {

		Map<String, String> fileErrors = new HashMap<String, String>();

		for (RebuildedFile lRebuildedFile : versioniDaRimuovere) {
			try {
				rimuoviVersioneDocumentoInTransaction(lRebuildedFile, pAurigaLoginBean, session, isCaricaPraticaPregressa);
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				fileErrors.put(
						String.valueOf(lRebuildedFile.getIdDocumento()),
						"Il file " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename() + " non è stato eliminato."
								+ (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
			}
		}

		for (RebuildedFile lRebuildedFile : documentiDaRimuovere) {
			
				// Se viene tolta la riga di un documento non va' eliminata quell'unita doc. ma solo tolta da quel fascicolo
				
//				try {
//					rimuoviUnitaDocumentaleInTransaction(lRebuildedFile, pAurigaLoginBean, session, isCaricaPraticaPregressa);
//				} catch (Exception e) {
//					mLogger.error("Errore " + e.getMessage(), e);
//					fileErrors.put(String.valueOf(lRebuildedFile.getIdDocumento()), "Il documento con id " + lRebuildedFile.getIdDocumento() + " non è stato eliminato " + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
//				}
				
				if(lRebuildedFile.getIdFolder() != null && lRebuildedFile.getIdUd() != null) {
					try {
						DmpkCoreToglidaBean lToglidaBean = new DmpkCoreToglidaBean();
						lToglidaBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
						lToglidaBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
						lToglidaBean.setFlgtpobjinnerin("U");
						lToglidaBean.setIdobjinnerin(lRebuildedFile.getIdUd());
						lToglidaBean.setFlgtpobjouterin("F");
						lToglidaBean.setIdobjouterin(lRebuildedFile.getIdFolder());
						
						final ToglidaImpl lToglida = new ToglidaImpl();
						lToglida.setBean(lToglidaBean);
						session.doWork(new Work() {
		
							@Override
							public void execute(Connection paramConnection) throws SQLException {
								lToglida.execute(paramConnection);
							}
						});
						StoreResultBean<DmpkCoreToglidaBean> lToglidaResult = new StoreResultBean<DmpkCoreToglidaBean>();
						AnalyzeResult.analyze(lToglidaBean, lToglidaResult);
						lToglidaResult.setResultBean(lToglidaBean);									
						if (lToglidaResult.isInError()) {
							throw new StoreException(lToglidaResult);
						}
					} catch (Exception e) {
						mLogger.error("Errore " + e.getMessage(), e);
						fileErrors.put(String.valueOf(lRebuildedFile.getIdDocumento()), "L'unita documentaria con id " + lRebuildedFile.getIdUd() + " non è stata tolta dal folder con id " + lRebuildedFile.getIdFolder() + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
					}
				}
			
		}

		return fileErrors;
	}

	public void rimuoviVersioneDocumentoInTransaction(RebuildedFile lRebuildedFile, AurigaLoginBean pAurigaLoginBean, Session session, boolean isCaricaPraticaPregressa) throws Exception {
		try {

			DmpkCoreDel_ud_doc_verBean lDel_ud_doc_verBean = new DmpkCoreDel_ud_doc_verBean();
			lDel_ud_doc_verBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lDel_ud_doc_verBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
			lDel_ud_doc_verBean.setFlgtipotargetin("V");
			lDel_ud_doc_verBean.setNroprogrverio(new BigDecimal(-1)); // in questo modo annullo tutte le versioni presenti sula file e non solo l'ultima
			lDel_ud_doc_verBean.setFlgcancfisicain(new Integer(0));
			lDel_ud_doc_verBean.setIduddocin(lRebuildedFile.getIdDocumento());

			final Del_ud_doc_verImpl store = new Del_ud_doc_verImpl();
			store.setBean(lDel_ud_doc_verBean);
			mLogger.debug("Chiamo la del_Ud_Doc_Ver");

			LoginService lLoginService = new LoginService();
			lLoginService.login(pAurigaLoginBean);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					store.execute(paramConnection);
				}
			});

			StoreResultBean<DmpkCoreDel_ud_doc_verBean> lStoreResultBean = new StoreResultBean<DmpkCoreDel_ud_doc_verBean>();
			AnalyzeResult.analyze(lDel_ud_doc_verBean, lStoreResultBean);
			lStoreResultBean.setResultBean(lDel_ud_doc_verBean);

			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean);
			}

		} catch (Exception e) {
			String errorMessage = e.getMessage();
			if (e instanceof StoreException) {
				errorMessage = ((StoreException) e).getError() != null ? ((StoreException) e).getError().getDefaultMessage() : ((StoreException) e)
						.getMessage();
			}
			mLogger.error("Errore " + errorMessage, e);
			throw new Exception(errorMessage);
		}
	}

	private void rimuoviUnitaDocumentaleInTransaction(RebuildedFile lRebuildedFile, AurigaLoginBean pAurigaLoginBean, Session session, boolean isCaricaPraticaPregressa) throws Exception {

		try {

			final ConnectionWrapper lConnectionWrapper = new ConnectionWrapper();
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lConnectionWrapper.setConnection(paramConnection);
				}
			});

			BigDecimal idUd = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				pstmt = lConnectionWrapper.getConnection().prepareStatement("select t.ID_UD from DMT_DOCUMENTS t where t.ID_DOC = ?");
				pstmt.setBigDecimal(1, lRebuildedFile.getIdDocumento());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					idUd = rs.getBigDecimal(1);
				}
			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (Exception e) {
				}
				try {
					if (pstmt != null)
						pstmt.close();
				} catch (Exception e) {
				}
			}

			if (idUd != null) {

				DmpkCoreDel_ud_doc_verBean lDel_ud_doc_verBean = new DmpkCoreDel_ud_doc_verBean();
				lDel_ud_doc_verBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lDel_ud_doc_verBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
				lDel_ud_doc_verBean.setFlgtipotargetin("U");
				lDel_ud_doc_verBean.setNroprogrverio(null);
				lDel_ud_doc_verBean.setFlgcancfisicain(new Integer(0)); // faccio la cancellazione logica
				lDel_ud_doc_verBean.setIduddocin(idUd);

				final Del_ud_doc_verImpl store = new Del_ud_doc_verImpl();
				store.setBean(lDel_ud_doc_verBean);
				mLogger.debug("Chiamo la del_Ud_Doc_Ver");

				LoginService lLoginService = new LoginService();
				lLoginService.login(pAurigaLoginBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreDel_ud_doc_verBean> lStoreResultBean = new StoreResultBean<DmpkCoreDel_ud_doc_verBean>();
				AnalyzeResult.analyze(lDel_ud_doc_verBean, lStoreResultBean);
				lStoreResultBean.setResultBean(lDel_ud_doc_verBean);

				if (lStoreResultBean.isInError()) {
					throw new StoreException(lStoreResultBean);
				}

			} else {
				mLogger.debug("Non è stato possibile recuperare l'unita documentaria relativa al documento da eliminare -> idDoc = "
						+ lRebuildedFile.getIdDocumento());
				throw new Exception("Non è stato possibile recuperare l'unita documentaria relativa al documento da eliminare");
			}

		} catch (Exception e) {
			String errorMessage = e.getMessage();
			if (e instanceof StoreException) {
				errorMessage = ((StoreException) e).getError() != null ? ((StoreException) e).getError().getDefaultMessage() : ((StoreException) e)
						.getMessage();
			}
			mLogger.error("Errore " + errorMessage, e);
			throw new Exception(errorMessage);
		}
	}
	
	private void rimuoviDocumentoInTransaction(RebuildedFile lRebuildedFile, AurigaLoginBean pAurigaLoginBean, Session session, boolean isCaricaPraticaPregressa) throws Exception {

		try {

			DmpkCoreDel_ud_doc_verBean lDel_ud_doc_verBean = new DmpkCoreDel_ud_doc_verBean();
			lDel_ud_doc_verBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lDel_ud_doc_verBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
			lDel_ud_doc_verBean.setFlgtipotargetin("D");
			lDel_ud_doc_verBean.setNroprogrverio(null);
			lDel_ud_doc_verBean.setFlgcancfisicain(new Integer(0)); // faccio la cancellazione logica
			lDel_ud_doc_verBean.setIduddocin(lRebuildedFile.getIdDocumento());

			final Del_ud_doc_verImpl store = new Del_ud_doc_verImpl();
			store.setBean(lDel_ud_doc_verBean);
			mLogger.debug("Chiamo la del_Ud_Doc_Ver");

			LoginService lLoginService = new LoginService();
			lLoginService.login(pAurigaLoginBean);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					store.execute(paramConnection);
				}
			});

			StoreResultBean<DmpkCoreDel_ud_doc_verBean> lStoreResultBean = new StoreResultBean<DmpkCoreDel_ud_doc_verBean>();
			AnalyzeResult.analyze(lDel_ud_doc_verBean, lStoreResultBean);
			lStoreResultBean.setResultBean(lDel_ud_doc_verBean);

			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean);
			}

		} catch (Exception e) {
			String errorMessage = e.getMessage();
			if (e instanceof StoreException) {
				errorMessage = ((StoreException) e).getError() != null ? ((StoreException) e).getError().getDefaultMessage() : ((StoreException) e)
						.getMessage();
			}
			mLogger.error("Errore " + errorMessage, e);
			throw new Exception(errorMessage);
		}
	}

	public VersionaDocumentoOutBean versionaDocumentoInTransaction(AurigaLoginBean pAurigaLoginBean, VersionaDocumentoInBean lVersionaDocumentoInBean,
			Session session, boolean isCaricaPraticaPregressa) throws Exception {
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = new VersionaDocumentoOutBean();
		try {

			String uriVer = DocumentStorage.store(lVersionaDocumentoInBean.getFile(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
			mLogger.debug("Salvato " + uriVer);

			FileStoreBean lFileStoreBean = new FileStoreBean();
			lFileStoreBean.setUri(uriVer);
			lFileStoreBean.setDimensione(lVersionaDocumentoInBean.getFile().length());

			try {
				BeanUtilsBean2.getInstance().copyProperties(lFileStoreBean, lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento());
			} catch (Exception e) {
				mLogger.warn(e);
			}

			lFileStoreBean.setIdTask(lVersionaDocumentoInBean.getIdTask());
			
			String lStringXml = "";
			try {
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
				mLogger.debug("attributiVerXml " + lStringXml);
			} catch (Exception e) {
				mLogger.warn(e);
			}

			DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
			lAddverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lAddverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
			lAddverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
			lAddverdocBean.setAttributiverxmlin(lStringXml);

			final AddverdocImpl store = new AddverdocImpl();
			store.setBean(lAddverdocBean);
			mLogger.debug("Chiamo la addVerdoc " + lAddverdocBean);

			LoginService lLoginService = new LoginService();
			lLoginService.login(pAurigaLoginBean);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					store.execute(paramConnection);
				}
			});

			StoreResultBean<DmpkCoreAddverdocBean> lStoreResultBean = new StoreResultBean<DmpkCoreAddverdocBean>();
			AnalyzeResult.analyze(lAddverdocBean, lStoreResultBean);
			lStoreResultBean.setResultBean(lAddverdocBean);

			if (lStoreResultBean.isInError()) {
				mLogger.error("Default message: "+ lStoreResultBean.getDefaultMessage());
				mLogger.error("Error context: " + lStoreResultBean.getErrorContext());
				mLogger.error("Error code: " + lStoreResultBean.getErrorCode());
				BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, lStoreResultBean);
				return lVersionaDocumentoOutBean;
			}

		} catch (Throwable e) {

			if (StringUtils.isNotBlank(e.getMessage())) {
				mLogger.error(e.getMessage(), e);
				lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage());
			} else {
				mLogger.error("Errore generico", e);
				lVersionaDocumentoOutBean.setDefaultMessage("Errore generico");
			}
			return lVersionaDocumentoOutBean;
		}

		return lVersionaDocumentoOutBean;
	}

	protected BigDecimal getIdUserLavoro(AurigaLoginBean pAurigaLoginBean) {
		return StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
	}

	/***********************************************************************************
	 * 
	 * Funzioni utilizzate dai WS
	 * 
	 ************************************************************************************/

	/*
	 * WS per creare un nuovo fascicolo
	 * 
	 * @param XML : XML con gli attributi del fascicolo da creare
	 * 
	 * @return IdFolderOut : id del folder creato
	 * 
	 * @exception Exception
	 * 
	 * Viene invocato dal ws WSAddFolder.
	 */

	@Operation(name = "salvaFascicoloWS")
	public SalvaFascicoloOut salvaFascicoloWS(AurigaLoginBean pAurigaLoginBean, String xmlIn) throws Exception {

		DmpkCoreAddfolderBean bean = new DmpkCoreAddfolderBean();

		bean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		bean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		bean.setAttributixmlin(xmlIn);

		Addfolder store = new Addfolder();
		StoreResultBean<DmpkCoreAddfolderBean> lStoreResultBean = store.execute(pAurigaLoginBean, bean);
		if (lStoreResultBean.isInError()) {
			mLogger.error("Default message: "+ lStoreResultBean.getDefaultMessage());
			mLogger.error("Error context: " + lStoreResultBean.getErrorContext());
			mLogger.error("Error code: " + lStoreResultBean.getErrorCode());
			SalvaFascicoloOut lSalvaFascicoloOut = new SalvaFascicoloOut();
			BeanUtilsBean2.getInstance().copyProperties(lSalvaFascicoloOut, lStoreResultBean);
			return lSalvaFascicoloOut;
		}

		SalvaFascicoloOut lSalvaFascicoloOut = new SalvaFascicoloOut();
		lSalvaFascicoloOut.setIdFolderOut(lStoreResultBean.getResultBean().getIdfolderout());

		return lSalvaFascicoloOut;
	}

	/*
	 * WS per modificare i metadati di un fascicolo
	 * 
	 * @param idFolder : riferimento del folder
	 * 
	 * @param XML : XML con gli attributi da modicare
	 * 
	 * @return URI : riferimento uri
	 * 
	 * @exception Exception
	 * 
	 * Viene invocato dal ws WSUpdFolder.
	 */
	@Operation(name = "modificaFascicoloWS")
	public ModificaFascicoloOut modificaFascicoloWS(AurigaLoginBean pAurigaLoginBean, String idFolderIn, String attributiXmlIn) throws Exception {

		DmpkCoreUpdfolderBean bean = new DmpkCoreUpdfolderBean();

		bean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		bean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		if (idFolderIn != null && !idFolderIn.equalsIgnoreCase(""))
			bean.setIdfolderin(new BigDecimal(idFolderIn));

		bean.setAttributixmlin(attributiXmlIn);

		Updfolder store = new Updfolder();
		StoreResultBean<DmpkCoreUpdfolderBean> lStoreResultBean = store.execute(pAurigaLoginBean, bean);
		if (lStoreResultBean.isInError()) {
			mLogger.error("Default message: "+ lStoreResultBean.getDefaultMessage());
			mLogger.error("Error context: " + lStoreResultBean.getErrorContext());
			mLogger.error("Error code: " + lStoreResultBean.getErrorCode());
			ModificaFascicoloOut lModificaFascicoloOut = new ModificaFascicoloOut();
			BeanUtilsBean2.getInstance().copyProperties(lModificaFascicoloOut, lStoreResultBean);
			return lModificaFascicoloOut;
		}

		ModificaFascicoloOut lModificaFascicoloOut = new ModificaFascicoloOut();
		lModificaFascicoloOut.setUriPerAggiornamentoContainer(lStoreResultBean.getResultBean().getUrixaggcontainerout());

		return lModificaFascicoloOut;
	}

	/*
	 * WS per cercare i documenti/folder
	 * 
	 * @param FindRepositoryObjectBean : tutti i valori del bean
	 * 
	 * @return DettagliCercaInFolderOut : XML contenente attributi e dati del folder CercaInFolderIO, e se questo e' NULL quelli dell'eventuale workspace
	 * specificato nei criteri avanzati di ricerca
	 * 
	 * @return NroRecInPaginaOut : E' il numero di record nella pagina
	 * 
	 * @return NroTotRecOut : E' il n.ro di record complessivi trovati
	 * 
	 * @return Percorsoricercaxmlio : Lista con nomi e id. (da quella di livello più alto in giù) delle cartelle/folder (libreria inclusa) che compongono il
	 * percorso in cui si cerca ora (ovvero CercaInFolderIO).
	 * 
	 * @return ResultOut : Lista delle unita' documentarie e folder trovati
	 * 
	 * @exception Exception
	 * 
	 * Viene invocato dal ws WSTrovaDocFolder.
	 */
//	@Operation(name = "trovaDocFolderWS")
	public TrovaDocFolderOut trovaDocFolderWS(AurigaLoginBean pAurigaLoginBean, FindRepositoryObjectBean pBeanIn,VersionHandler vh) throws Exception {

		DmpkCoreTrovarepositoryobjBean bean = new DmpkCoreTrovarepositoryobjBean();
		bean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		bean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		if (pBeanIn.getIdFolderSearchIn() != null)
			bean.setCercainfolderio(new BigDecimal(pBeanIn.getIdFolderSearchIn()));

		if (pBeanIn.getFlgSubfoderSearchIn() != null && !pBeanIn.getFlgSubfoderSearchIn().equalsIgnoreCase(""))
			bean.setFlgcercainsubfolderio(Integer.valueOf(pBeanIn.getFlgSubfoderSearchIn()));

		bean.setColorderbyio(pBeanIn.getColsOrderBy());
		bean.setColtoreturnin(pBeanIn.getColsToReturn());
		bean.setCriteriavanzatiio(pBeanIn.getAdvancedFilters());
		bean.setCriteripersonalizzatiio(pBeanIn.getCustomFilters());
		bean.setFinalitain(pBeanIn.getFinalita());
		bean.setFlgbatchsearchin(pBeanIn.getOnline());
		bean.setFlgdescorderbyio(pBeanIn.getFlgDescOrderBy());
		bean.setFlgfmtestremiregio(pBeanIn.getFormatoEstremiReg());
		bean.setFlgsenzapaginazionein(pBeanIn.getFlgSenzaPaginazione());
		bean.setFlgudfolderio(pBeanIn.getFlgUdFolder());
		bean.setNropaginaio(pBeanIn.getNumPagina());
		bean.setOverflowlimitin(pBeanIn.getOverflowlimitin());
		bean.setPercorsoricercaxmlio(pBeanIn.getPercorsoRicerca());
		bean.setBachsizeio(pBeanIn.getNumRighePagina());
		//Gestione parametri fullText
		if (pBeanIn.getFiltroFullText() != null && !"".equals(pBeanIn.getFiltroFullText()))
		{		
		mLogger.info("fullText: "+pBeanIn.getFiltroFullText());
		String match= null;
		try 
		{
			SynchronousVersionHandler sync = (SynchronousVersionHandler) vh;
			match= sync.getMatchbyindexerin(pBeanIn,pAurigaLoginBean);
		}
		catch (Exception e) {
			mLogger.error("getMatchbyindexerin: "+e.getMessage());
		}
		
		mLogger.info("match: "+match);
		bean.setMatchbyindexerin(match);
		}
        //fine gestione parametri fullText
		Trovarepositoryobj store = new Trovarepositoryobj();
		StoreResultBean<DmpkCoreTrovarepositoryobjBean> lStoreResultBean = store.execute(pAurigaLoginBean, bean);
		if (lStoreResultBean.isInError()) {
			mLogger.error("Default message: "+ lStoreResultBean.getDefaultMessage());
			mLogger.error("Error context: " + lStoreResultBean.getErrorContext());
			mLogger.error("Error code: " + lStoreResultBean.getErrorCode());
			TrovaDocFolderOut lTrovaDocFolderOut = new TrovaDocFolderOut();
			BeanUtilsBean2.getInstance().copyProperties(lTrovaDocFolderOut, lStoreResultBean);
			return lTrovaDocFolderOut;
		}

		TrovaDocFolderOut lTrovaDocFolderOut = new TrovaDocFolderOut();
		lTrovaDocFolderOut.setDettagliCercaInFolderOut(lStoreResultBean.getResultBean().getDettaglicercainfolderout());
		lTrovaDocFolderOut.setNroRecInPaginaOut(lStoreResultBean.getResultBean().getNrorecinpaginaout());
		lTrovaDocFolderOut.setNroTotRecOut(lStoreResultBean.getResultBean().getNrototrecout());
		lTrovaDocFolderOut.setPercorsoRicercaXMLOut(lStoreResultBean.getResultBean().getPercorsoricercaxmlio());
		lTrovaDocFolderOut.setResultOut(lStoreResultBean.getResultBean().getResultout());
		
		lTrovaDocFolderOut.setDefaultMessage(lStoreResultBean.getDefaultMessage());
		lTrovaDocFolderOut.setErrorContext(lStoreResultBean.getErrorContext());
		lTrovaDocFolderOut.setErrorCode(lStoreResultBean.getErrorCode());
		

		return lTrovaDocFolderOut;
	}
	
	
	
	
	
	
}