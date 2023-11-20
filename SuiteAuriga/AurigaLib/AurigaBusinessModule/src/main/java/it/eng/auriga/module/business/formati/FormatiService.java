/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.formati.bean.FormatiServiceBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TPagingList;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

/**
 * Classe di servizio che espone i metodi per verificare se un determinato
 * mimetype risulta essere convertibile in pdf
 * @author rametta
 *
 */
@Service(name="FormatiService")
public class FormatiService {

	private static Logger mLogger = Logger.getLogger(FormatiService.class);

	/**
	 * Dato un mimetype recupera dal db la possibilità o meno di essere convertito in pdf
	 * @param pFormatiServiceBean Bean che contiene il mimetype
	 * @return bean con l'informazione sulla conversione in pdf
	 * @throws Exception 
	 */
	@Operation(name = "checkPdfConvertibile")
	public FormatiServiceBean checkPdfConvertibile(AurigaLoginBean pAurigaLoginBean, FormatiServiceBean pFormatiServiceBean) {
		mLogger.debug("Start checkPdfConvertibile");
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		Session lSession = null;
		try {
			mLogger.debug("Richiesto checkPdfConvertibile per mimetype " + pFormatiServiceBean.getMimetype());
			lSession = HibernateUtil.begin();
			BigDecimal lBigDecimal = (BigDecimal)lSession.createSQLQuery("select NVL(FLG_CONVERTIBILE_PDF, 0) from DMT_FORMATI_EL_AMMESSI where INSTR(';'||MIMETYPE||';', ';'||lower(:mimetype)||';')>0").
			setString("mimetype", pFormatiServiceBean.getMimetype()).uniqueResult();
			if (lBigDecimal == null || lBigDecimal.intValue() == 0) 
				pFormatiServiceBean.setConvertibile(false);
			else pFormatiServiceBean.setConvertibile(true);
		} catch (Exception e) {
			mLogger.error("Errore " + e.getMessage() , e);
			pFormatiServiceBean.setConvertibile(false);
		} finally {
			if (lSession != null){
				lSession.flush();
				lSession.close();
			}
		}
		mLogger.debug("End checkPdfConvertibile");
		return pFormatiServiceBean;

	}
	
	@Operation(name = "loadAll")
	public TPagingList<FormatiServiceBean> loadAll(AurigaLoginBean pAurigaLoginBean) {
		mLogger.debug("Start loadAll");
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		Session lSession = null;
		try {
			mLogger.debug("Richiesto tutti i checkPdfConvertibile");
			lSession = HibernateUtil.begin();
			Query lQuery = lSession.createSQLQuery("select NVL(FLG_CONVERTIBILE_PDF, 0), MIMETYPE from DMT_FORMATI_EL_AMMESSI");
			List<Object[]> results = (List<Object[]>)lQuery.list();
			List<FormatiServiceBean> lList = new ArrayList<FormatiServiceBean>(results.size());
		     for(Object[] result : results){
		    	 BigDecimal lBigDecimal  = (BigDecimal)result[0];
		         String mimetype = (String)result[1];
		         FormatiServiceBean lFormatiServiceBean = new FormatiServiceBean();
		         lFormatiServiceBean.setMimetype(mimetype);
		         if (lBigDecimal == null || lBigDecimal.intValue() == 0) 
		        	 lFormatiServiceBean.setConvertibile(false);
					else lFormatiServiceBean.setConvertibile(true);
		         lList.add(lFormatiServiceBean);
		         
		     }
		     TPagingList<FormatiServiceBean> lTPagingList = new TPagingList<FormatiServiceBean>();
		     lTPagingList.setData(lList);
		     lTPagingList.setStartRow(0);
		     lTPagingList.setEndRow(lList.size());
		     return lTPagingList;
		} catch (Exception e) {
			mLogger.error("Errore " + e.getMessage() , e);
			return null;
		} finally {
			if (lSession != null){
				lSession.flush();
				lSession.close();
			}
		}

	}
}
