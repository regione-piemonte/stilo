/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.module.business.dao;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.DocToSignBean;
import it.eng.auriga.module.business.dao.beansconverters.DocToSignBeanToTDocToSignConverter;
import it.eng.auriga.module.business.dao.beansconverters.TDocToSignToDocToSignBeanConverter;
import it.eng.auriga.module.business.entity.TDocToSign;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

/**
 * 
 * Classe che implementa le operazioni hibernate sulla tabella T_DOC_TO_SIGN
 * @author matzanin
 *
 */
@Service(name="DaoTDocToSign")
public class DaoTDocToSign extends DaoGenericOperations<DocToSignBean>{

	private static final Logger logger = Logger.getLogger(DaoTDocToSign.class);

	public DaoTDocToSign() {		
	}

	//Metodo privato per costruire i criteri di ricerca
	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<DocToSignBean> filter) throws Exception {		
		Criteria criteria = session.createCriteria(TDocToSign.class);
		if(filter != null) {
			DocToSignBean bean = filter.getFilter();
			if(bean != null) {		
				if(StringUtils.isNotBlank(bean.getDocRecId())) {
					criteria.add(Restrictions.eq("docRecId", bean.getDocRecId()));
				}
				//TODO completare i filtri di ricerca, se necessario...
			}
			HibernateUtil.addOrderCriteria(criteria, filter.getOrders());			
		}			
		return criteria;
	}
		
	@Override
	public TPagingList<DocToSignBean> search(TFilterFetch<DocToSignBean> filter) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();			
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);			
			Long count = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();			
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;			
			TPagingList<DocToSignBean> paginglist = new TPagingList<DocToSignBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);			
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for(Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				DocToSignBean bean = (DocToSignBean) UtilPopulate.populate((TDocToSign) obj, DocToSignBean.class, new TDocToSignToDocToSignBeanConverter());
				paginglist.addData(bean);
			}			
			return paginglist;
		}catch(Exception e){
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}

	@Operation(name="search")	
	public TPagingList<DocToSignBean> searchWithLogin(AurigaLoginBean pAurigaLoginBean, TFilterFetch<DocToSignBean> filter) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.search(filter);
	}
	
	@Override
	public DocToSignBean save(DocToSignBean bean) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {			
			session = HibernateUtil.begin();	
			transaction = session.beginTransaction();
			if(bean != null) {					
				String id = bean.getDocRecId();	
				if(session.get(TDocToSign.class, id) != null) {
					return bean;
				}
				TDocToSign docToSign = (TDocToSign) UtilPopulate.populate(bean, TDocToSign.class, new DocToSignBeanToTDocToSignConverter(session));			
				session.save(docToSign);
				bean = (DocToSignBean) UtilPopulate.populate(docToSign, DocToSignBean.class, new TDocToSignToDocToSignBeanConverter());
			}								
			session.flush();
			transaction.commit();
			return bean;
		} catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}	
	
	@Operation(name="save")	
	public DocToSignBean saveWithLogin(AurigaLoginBean pAurigaLoginBean, DocToSignBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.save(bean);
	}

	@Override
	public DocToSignBean update(DocToSignBean bean) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {			
			session = HibernateUtil.begin();
			transaction = session.beginTransaction();
			if(bean != null) {													
				TDocToSign docToSign = (TDocToSign) UtilPopulate.populateForUpdate(session, bean, TDocToSign.class, new DocToSignBeanToTDocToSignConverter(session));
				session.update(docToSign);	
				bean = (DocToSignBean) UtilPopulate.populate(docToSign, DocToSignBean.class, new TDocToSignToDocToSignBeanConverter());
			}								
			session.flush();
			transaction.commit();
			return bean;
		} catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}
	
	@Operation(name="update")	
	public DocToSignBean updateWithLogin(AurigaLoginBean pAurigaLoginBean, DocToSignBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.update(bean);
	}

	@Override
	public void delete(DocToSignBean bean) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.begin();
			transaction = session.beginTransaction();
			String id = bean.getDocRecId();	
			TDocToSign docToSign = (TDocToSign) session.get(TDocToSign.class, id);
			if(docToSign != null) {
				session.delete(docToSign);
			}
			session.flush();
			transaction.commit();
		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}		
	}
	
	@Operation(name="delete")	
	public void deleteWithLogin(AurigaLoginBean pAurigaLoginBean, DocToSignBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		this.delete(bean);
	}

	@Override
	public void forcedelete(DocToSignBean bean) throws Exception {
		this.delete(bean);
	}

	@Operation(name="forcedelete")	
	public void forcedeleteWithLogin(AurigaLoginBean pAurigaLoginBean, DocToSignBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		this.forcedelete(bean);
	}

	public DocToSignBean get(DocToSignBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();			
			String id = bean.getDocRecId();	
			TDocToSign docToSign = (TDocToSign) session.get(TDocToSign.class, id);
			return (DocToSignBean) UtilPopulate.populate(docToSign, DocToSignBean.class, new TDocToSignToDocToSignBeanConverter());	
		}catch(Exception e){
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}

	@Operation(name="get")	
	public DocToSignBean getWithLogin(AurigaLoginBean pAurigaLoginBean, DocToSignBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.get(bean);
	}

}
