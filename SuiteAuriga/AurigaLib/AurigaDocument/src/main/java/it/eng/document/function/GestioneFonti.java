/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddverdocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDel_ud_doc_verBean;
import it.eng.auriga.database.store.dmpk_core.store.Addverdoc;
import it.eng.auriga.database.store.dmpk_core.store.Del_ud_doc_ver;
import it.eng.auriga.database.store.dmpk_fonti.bean.DmpkFontiIufonteBean;
import it.eng.auriga.database.store.dmpk_fonti.store.impl.IufonteImpl;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetestremiregnumud_jBean;
import it.eng.auriga.database.store.dmpk_utility.store.Getestremiregnumud_j;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.AggiungiDocumentoInBean;
import it.eng.document.function.bean.AggiungiDocumentoOutBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModFonteInBean;
import it.eng.document.function.bean.CreaModFonteOutBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.FileStoreBean;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.document.storage.DocumentStorage;
import it.eng.storeutil.AnalyzeResult;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.xml.XmlUtilitySerializer;

@Service(name = "GestioneFonti")
public class GestioneFonti {

	private static Logger mLogger = Logger.getLogger(GestioneFonti.class);

	@Operation(name = "creaFonte")
	public CreaModFonteOutBean creaFonte(AurigaLoginBean pAurigaLoginBean, CreaModFonteInBean pCreaModFonteInBean,
			CreaModDocumentoInBean pCreaModDocumentoInBean, FilePrimarioBean pFilePrimarioBean) throws JAXBException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();

		CreaModFonteOutBean lCreaModFonteOutBean = new CreaModFonteOutBean();
		try {
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {

				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();

				DmpkFontiIufonteBean lIufonteBean = new DmpkFontiIufonteBean();
				lIufonteBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lIufonteBean.setAttributifontexmlin(lXmlUtilitySerializer.bindXmlCompleta(pCreaModFonteInBean));
				lIufonteBean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXmlCompleta(pCreaModDocumentoInBean));

				final IufonteImpl lIufonteImpl = new IufonteImpl();
				lIufonteImpl.setBean(lIufonteBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						lIufonteImpl.execute(paramConnection);
					}
				});
				StoreResultBean<DmpkFontiIufonteBean> lIufonteResult = new StoreResultBean<DmpkFontiIufonteBean>();
				AnalyzeResult.analyze(lIufonteBean, lIufonteResult);
				lIufonteResult.setResultBean(lIufonteBean);

				if (lIufonteResult.isInError()) {
					mLogger.error("ERROR: " + lIufonteResult.getDefaultMessage());
				} else {
					
					lCreaModFonteOutBean.setIdFonte(lIufonteResult.getResultBean().getIdfonteio());
					lCreaModFonteOutBean.setIdUd(lIufonteResult.getResultBean().getIdudout());
					lCreaModFonteOutBean.setIdDoc(lIufonteResult.getResultBean().getIddocout());
					
					if (pFilePrimarioBean != null) {
						RebuildedFile lRebuildedFile = new RebuildedFile();
						lRebuildedFile.setIdDocumento(lIufonteResult.getResultBean().getIddocout());
						lRebuildedFile.setFile(pFilePrimarioBean.getFile());
						lRebuildedFile.setInfo(pFilePrimarioBean.getInfo());
						versioni.add(lRebuildedFile);
					}
				}

				session.flush();
				lTransaction.commit();
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lCreaModFonteOutBean, ((StoreException) e).getError());
					return lCreaModFonteOutBean;
				} else {
					lCreaModFonteOutBean.setDefaultMessage(e.getMessage());
					return lCreaModFonteOutBean;
				}
			} finally {
				HibernateUtil.release(session);
			}

			// Parte di versionamento
			Map<String, String> fileErrors = aggiungiFiles(pAurigaLoginBean, versioni);
			lCreaModFonteOutBean.setFileInErrors(fileErrors);

			recuperaEstremi(pAurigaLoginBean, pCreaModDocumentoInBean, lCreaModFonteOutBean);

		} catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lCreaModFonteOutBean, ((StoreException) e).getError());
				return lCreaModFonteOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lCreaModFonteOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lCreaModFonteOutBean;
			}
		}

		return lCreaModFonteOutBean;
	}

	@Operation(name = "modificaFonte")
	public CreaModFonteOutBean modificaFonte(AurigaLoginBean pAurigaLoginBean, BigDecimal pIdFonte, CreaModFonteInBean pCreaModFonteInBean,
			CreaModDocumentoInBean pCreaModDocumentoInBean, FilePrimarioBean pFilePrimarioBean) throws JAXBException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		List<RebuildedFile> versioniDaRimuovere = new ArrayList<RebuildedFile>();

		CreaModFonteOutBean lCreaModFonteOutBean = new CreaModFonteOutBean();
		try {
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {

				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();

				DmpkFontiIufonteBean lIufonteBean = new DmpkFontiIufonteBean();
				lIufonteBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lIufonteBean.setIdfonteio(pIdFonte);
				lIufonteBean.setAttributifontexmlin(lXmlUtilitySerializer.bindXmlCompleta(pCreaModFonteInBean));
				lIufonteBean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXmlCompleta(pCreaModDocumentoInBean));

				final IufonteImpl lIufonteImpl = new IufonteImpl();
				lIufonteImpl.setBean(lIufonteBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						lIufonteImpl.execute(paramConnection);
					}
				});
				StoreResultBean<DmpkFontiIufonteBean> lIufonteResult = new StoreResultBean<DmpkFontiIufonteBean>();
				AnalyzeResult.analyze(lIufonteBean, lIufonteResult);
				lIufonteResult.setResultBean(lIufonteBean);

				if (lIufonteResult.isInError()) {
					mLogger.error("ERROR: " + lIufonteResult.getDefaultMessage());
				} else {

					lCreaModFonteOutBean.setIdFonte(pIdFonte);
					lCreaModFonteOutBean.setIdUd(lIufonteResult.getResultBean().getIdudout());
					lCreaModFonteOutBean.setIdDoc(lIufonteResult.getResultBean().getIddocout());
	
					RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
					lRecuperaDocumentoInBean.setIdUd(lCreaModFonteOutBean.getIdUd());
	
					RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
	
					RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = null;
					DocumentoXmlOutBean lDocumentoOutBean = null;
	
					try {
						lRecuperaDocumentoOutBean = lRecuperoDocumenti.loadDocumento(pAurigaLoginBean, lRecuperaDocumentoInBean);
						lDocumentoOutBean = lRecuperaDocumentoOutBean.getDocumento();
					} catch (Exception e) {
						mLogger.error("Errore " + e.getMessage(), e);
						lCreaModFonteOutBean.setDefaultMessage("Il documento da aggiornare è inesistente");
						return lCreaModFonteOutBean;
					}
	
					if (pFilePrimarioBean != null && pFilePrimarioBean.getIsNewOrChanged()) {
						RebuildedFile lRebuildedFile = new RebuildedFile();
						lRebuildedFile.setIdDocumento(pFilePrimarioBean.getIdDocumento());
						lRebuildedFile.setFile(pFilePrimarioBean.getFile());
						lRebuildedFile.setInfo(pFilePrimarioBean.getInfo());
						versioni.add(lRebuildedFile);
					} else if (pFilePrimarioBean == null) {
						if (lDocumentoOutBean.getFilePrimario() != null && StringUtils.isNotBlank(lDocumentoOutBean.getFilePrimario().getUri())) {
							RebuildedFile lRebuildedFile = new RebuildedFile();
							lRebuildedFile.setIdDocumento(lCreaModFonteOutBean.getIdDoc());
							FileInfoBean info = new FileInfoBean();
							info.setTipo(TipoFile.PRIMARIO);
							GenericFile allegatoRiferimento = new GenericFile();
							allegatoRiferimento.setDisplayFilename(lDocumentoOutBean.getFilePrimario().getDisplayFilename());
							info.setAllegatoRiferimento(allegatoRiferimento);
							lRebuildedFile.setInfo(info);
							versioniDaRimuovere.add(lRebuildedFile);
						}
					}
				
				}

				session.flush();
				lTransaction.commit();
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lCreaModFonteOutBean, ((StoreException) e).getError());
					return lCreaModFonteOutBean;
				} else {
					lCreaModFonteOutBean.setDefaultMessage(e.getMessage());
					return lCreaModFonteOutBean;
				}
			} finally {
				HibernateUtil.release(session);
			}

			// Parte di versionamento
			Map<String, String> fileErrors = new HashMap<String, String>();
			fileErrors.putAll(aggiungiFiles(pAurigaLoginBean, versioni));
			fileErrors.putAll(rimuoviFiles(pAurigaLoginBean, versioniDaRimuovere));
			lCreaModFonteOutBean.setFileInErrors(fileErrors);

			recuperaEstremi(pAurigaLoginBean, pCreaModDocumentoInBean, lCreaModFonteOutBean);

		} catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lCreaModFonteOutBean, ((StoreException) e).getError());
				return lCreaModFonteOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lCreaModFonteOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lCreaModFonteOutBean;
			}
		}

		return lCreaModFonteOutBean;
	}

	protected Map<String, String> aggiungiFiles(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioni) {

		Map<String, String> fileErrors = new HashMap<String, String>();

		for (RebuildedFile lRebuildedFile : versioni) {
			try {
				VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
				VersionaDocumentoOutBean lVersionaDocumentoOutBean = versionaDocumento(pAurigaLoginBean, lVersionaDocumentoInBean);
				if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
					throw new Exception(lVersionaDocumentoOutBean.getDefaultMessage());
				}
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (lRebuildedFile.getInfo().getTipo() == TipoFile.PRIMARIO) {
					fileErrors.put("0", "Il file primario " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename()
							+ " non è stato salvato correttamente." + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				}
			}
		}

		return fileErrors;
	}

	protected Map<String, String> rimuoviFiles(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioniDaRimuovere) {

		Map<String, String> fileErrors = new HashMap<String, String>();

		for (RebuildedFile lRebuildedFile : versioniDaRimuovere) {
			try {
				rimuoviVersioneDocumento(lRebuildedFile, pAurigaLoginBean);
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (lRebuildedFile.getInfo().getTipo() == TipoFile.PRIMARIO) {
					fileErrors.put("0", "Il file primario " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename()
							+ " non è stato eliminato." + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				}
			}
		}

		return fileErrors;
	}

	protected void recuperaEstremi(AurigaLoginBean pAurigaLoginBean, CreaModDocumentoInBean pCreaModDocumentoInBean, CreaModFonteOutBean pCreaModFonteOutBean)
			throws Exception, StoreException {

		DmpkUtilityGetestremiregnumud_jBean lDmpkUtilityGetestremiregnumud_jBean = new DmpkUtilityGetestremiregnumud_jBean();
		lDmpkUtilityGetestremiregnumud_jBean.setIdudin(pCreaModFonteOutBean.getIdUd());

		Getestremiregnumud_j lGetestremiregnumud_j = new Getestremiregnumud_j();

		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(pAurigaLoginBean.getSchema());

		StoreResultBean<DmpkUtilityGetestremiregnumud_jBean> result = lGetestremiregnumud_j.execute(lSchemaBean, lDmpkUtilityGetestremiregnumud_jBean);
		if (result.isInError()) {
			throw new StoreException(result);
		}

		pCreaModFonteOutBean.setAnno(result.getResultBean().getAnnoregout());
		pCreaModFonteOutBean.setNumero(result.getResultBean().getNumregout());
		pCreaModFonteOutBean.setData(result.getResultBean().getTsregout());

		if (pCreaModDocumentoInBean != null && pCreaModDocumentoInBean.getTipoNumerazioni() != null && pCreaModDocumentoInBean.getTipoNumerazioni().size() > 0) {
			TipoNumerazioneBean tipoNumerazione = pCreaModDocumentoInBean.getTipoNumerazioni().get(0);
			pCreaModFonteOutBean.setSigla(tipoNumerazione != null ? tipoNumerazione.getSigla() : null);
		}

	}

	@Operation(name = "versionaDocumento")
	public VersionaDocumentoOutBean versionaDocumento(AurigaLoginBean pAurigaLoginBean, VersionaDocumentoInBean lVersionaDocumentoInBean) throws Exception {
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

			String lStringXml = "";
			try {
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
				mLogger.debug("lStringXml " + lStringXml);
			} catch (Exception e) {
				mLogger.warn(e);
			}

			DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
			lAddverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lAddverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
			lAddverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
			lAddverdocBean.setAttributiverxmlin(lStringXml);
			Addverdoc lAddverdoc = new Addverdoc();
			mLogger.debug("Chiamo la addVerdoc " + lAddverdocBean);

			StoreResultBean<DmpkCoreAddverdocBean> lStoreResultBean = lAddverdoc.execute(pAurigaLoginBean, lAddverdocBean);
			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean);
			}

		} catch (Throwable e) {
			if (e instanceof StoreException) {
				mLogger.error(e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, ((StoreException) e).getError());
				return lVersionaDocumentoOutBean;
			} else {
				if (StringUtils.isNotBlank(e.getMessage())) {
					mLogger.error(e.getMessage(), e);
					lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage());
				} else {
					mLogger.error("Errore generico", e);
					lVersionaDocumentoOutBean.setDefaultMessage("Errore generico");
				}
				return lVersionaDocumentoOutBean;
			}
		}
		return lVersionaDocumentoOutBean;
	}

	private void rimuoviVersioneDocumento(RebuildedFile lRebuildedFile, AurigaLoginBean pAurigaLoginBean) throws Exception {
		try {

			DmpkCoreDel_ud_doc_verBean lDel_ud_doc_verBean = new DmpkCoreDel_ud_doc_verBean();
			lDel_ud_doc_verBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lDel_ud_doc_verBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
			lDel_ud_doc_verBean.setFlgtipotargetin("V");
			lDel_ud_doc_verBean.setNroprogrverio(new BigDecimal(-1)); // in questo modo annullo tutte le versioni presenti sula file e non solo l'ultima
			lDel_ud_doc_verBean.setFlgcancfisicain(new Integer(0));
			lDel_ud_doc_verBean.setIduddocin(lRebuildedFile.getIdDocumento());
			Del_ud_doc_ver lDel_ud_doc_ver = new Del_ud_doc_ver();
			mLogger.debug("Chiamo la del_Ud_Doc_Ver");

			StoreResultBean<DmpkCoreDel_ud_doc_verBean> lStoreResultBean = lDel_ud_doc_ver.execute(pAurigaLoginBean, lDel_ud_doc_verBean);
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

	@Operation(name = "aggiungiDocumento")
	public AggiungiDocumentoOutBean aggiungiDocumento(AurigaLoginBean pAurigaLoginBean, AggiungiDocumentoInBean pAggiungiDocumentoInBean)
			throws StorageException, FileNotFoundException, IOException, JAXBException {
		String uriVer = DocumentStorage.store(pAggiungiDocumentoInBean.getFile(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
		mLogger.debug(uriVer);
		return null;
	}

	protected BigDecimal getIdUserLavoro(AurigaLoginBean pAurigaLoginBean) {
		return StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
	}

}