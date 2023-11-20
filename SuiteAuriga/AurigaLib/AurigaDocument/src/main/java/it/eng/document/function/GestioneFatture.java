/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.CreaModFatturaInBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.xml.XmlUtilitySerializer;

@Service(name = "GestioneFatture")
public class GestioneFatture extends GestioneDocumenti {
	
	private static Logger mLogger = Logger.getLogger(GestioneFatture.class);
	
	@Operation(name = "creaFattura")
	public CreaModDocumentoOutBean creaFattura(AurigaLoginBean pAurigaLoginBean, CreaModFatturaInBean pCreaFatturaInBean, 
			FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		DmpkCoreAdddocBean lAdddocBean = new DmpkCoreAdddocBean();
		lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lAdddocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();				
		lAdddocBean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(pCreaFatturaInBean));	
		
		lAdddocBean.setFlgautocommitin(0); // blocco l'autocommit
		
		//Preparo la risposta
		BigDecimal idUdResult = null;
		//Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
						
		CreaModDocumentoOutBean lCreaDocumentoOutBean = new CreaModDocumentoOutBean();
		try {
			SubjectBean subject =  SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject); 
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {
				idUdResult = manageAddDocs(pAurigaLoginBean, pFilePrimarioBean,
						pAllegatiBean, lAdddocBean, lXmlUtilitySerializer,
						versioni, session);
				HibernateUtil.commit(session);				
			}catch(Exception e){
				mLogger.error("Errore " + e.getMessage(), e);
				if (e instanceof StoreException){
					BeanUtilsBean2.getInstance().copyProperties(lCreaDocumentoOutBean, ((StoreException)e).getError());
					return lCreaDocumentoOutBean;
				} else {
					lCreaDocumentoOutBean.setDefaultMessage(e.getMessage());
					return lCreaDocumentoOutBean;
				}
			}finally{
				HibernateUtil.release(session);
			}
			lCreaDocumentoOutBean.setIdUd(idUdResult);
			//Parte di versionamento
			Map<String, String> fileErrors = aggiungiFiles(pAurigaLoginBean, versioni);
			lCreaDocumentoOutBean.setFileInErrors(fileErrors);
			recuperaEstremi(pAurigaLoginBean, pCreaFatturaInBean, lCreaDocumentoOutBean);
		} catch (Exception e) {
			if (e instanceof StoreException){
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lCreaDocumentoOutBean, ((StoreException)e).getError());
				return lCreaDocumentoOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lCreaDocumentoOutBean.setDefaultMessage(e.getMessage());
				return lCreaDocumentoOutBean;
			}
		}

		return lCreaDocumentoOutBean;
	}
	
	
	@Operation(name = "modificaFattura")
	public CreaModDocumentoOutBean modificaFattura(AurigaLoginBean pAurigaLoginBean, 
			BigDecimal pIdUd, BigDecimal pIdDocPrimario, 
			CreaModDocumentoInBean pModificaDocumentoInBean,
			FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{

		CreaModDocumentoOutBean lModificaDocumentoOutBean = new CreaModDocumentoOutBean();
		
		BigDecimal idUd = (pIdUd != null) ? new BigDecimal(pIdUd.longValue() + "") : null;
		BigDecimal idDocPrimario = (pIdDocPrimario != null) ? new BigDecimal(pIdDocPrimario.longValue() + "") : null;
		
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(idUd);
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean;
		
		DocumentoXmlOutBean lDocumentoXmlOutBean = null;
		try {
			lRecuperaDocumentoOutBean = lRecuperoDocumenti.loadDocumento(pAurigaLoginBean, lRecuperaDocumentoInBean);
			lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		} catch (Exception e) {
			mLogger.error("Errore " + e.getMessage(), e);
			lModificaDocumentoOutBean.setDefaultMessage("Il documento da aggiornare e' inesistente");
			return lModificaDocumentoOutBean;
		} 		
		
		DmpkCoreUpddocudBean lUpddocudBean = new DmpkCoreUpddocudBean();
		lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lUpddocudBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlBean = null;
		try {
			List<String> campiDaAggiornare = pModificaDocumentoInBean.getCampiDaAggiornare();
			if (campiDaAggiornare != null) {
				xmlBean = lXmlUtilitySerializer.bindXmlParziale(pModificaDocumentoInBean, campiDaAggiornare);
				mLogger.debug("attributiBindXml " + xmlBean);
			}
		} catch (Exception e) {
			mLogger.error("Errore bindXmlParziale: " + e.getMessage(), e);
		}
		lUpddocudBean.setAttributiuddocxmlin(xmlBean);
		
		lUpddocudBean.setFlgtipotargetin("D");
		lUpddocudBean.setIduddocin(idDocPrimario);				
		lUpddocudBean.setFlgautocommitin(0); // blocco l'autocommit
		
		//Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		List<RebuildedFile> versioniDaRimuovere = new ArrayList<RebuildedFile>();
		List<RebuildedFile> allegatiDaRimuovere = new ArrayList<RebuildedFile>();
		
		try {
			SubjectBean subject =  SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject); 
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {
				manageAddUpdDocs(pAurigaLoginBean, pFilePrimarioBean,
						pAllegatiBean, lUpddocudBean, lXmlUtilitySerializer,
						idUd, idDocPrimario, lDocumentoXmlOutBean, versioni, 
						versioniDaRimuovere, allegatiDaRimuovere, session);
				lTransaction.commit();	
			} catch (StoreException e) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException)e).getError());
				return lModificaDocumentoOutBean;
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaDocumentoOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");				
				return lModificaDocumentoOutBean;
			} finally {
				HibernateUtil.release(session);
			}
			lModificaDocumentoOutBean.setIdUd(idUd);
			//Parte di versionamento
			Map<String, String> fileErrors = new HashMap<String, String>();
			fileErrors.putAll(aggiungiFiles(pAurigaLoginBean, versioni));
			fileErrors.putAll(rimuoviFiles(pAurigaLoginBean, versioniDaRimuovere, allegatiDaRimuovere));
			lModificaDocumentoOutBean.setFileInErrors(fileErrors);
			recuperaEstremi(pAurigaLoginBean, pModificaDocumentoInBean, lModificaDocumentoOutBean);
			
		} catch (StoreException e) {
			mLogger.error("Errore " + e.getMessage(), e);
			BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException)e).getError());
		} catch (Exception e) {
			mLogger.error("Errore " + e.getMessage(), e);
			lModificaDocumentoOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");				
		}

		return lModificaDocumentoOutBean;
	}

}
