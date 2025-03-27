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
import it.eng.auriga.module.business.dao.beans.FileDocToSignBean;
import it.eng.auriga.module.business.dao.beansconverters.FileDocToSignBeanToTFileDocToSignConverter;
import it.eng.auriga.module.business.dao.beansconverters.TFileDocToSignToFileDocToSignBeanConverter;
import it.eng.auriga.module.business.entity.TFileDocToSign;
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
 * Classe che implementa le operazioni hibernate sulla tabella T_FILE_DOC_TO_SIGN
 * @author matzanin
 *
 */
@Service(name="DaoTFileDocToSign")
public class DaoTFileDocToSign extends DaoGenericOperations<FileDocToSignBean>{

	private static final Logger logger = Logger.getLogger(DaoTFileDocToSign.class);

	public DaoTFileDocToSign() {		
	}

	//Metodo privato per costruire i criteri di ricerca
	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<FileDocToSignBean> filter) throws Exception {		
		Criteria criteria = session.createCriteria(TFileDocToSign.class);
		if(filter != null) {
			FileDocToSignBean bean = filter.getFilter();
			if(bean != null) {		
				if(StringUtils.isNotBlank(bean.getFileRecId())) {
					criteria.add(Restrictions.eq("fileRecId", bean.getFileRecId()));
				}
				//TODO completare i filtri di ricerca, se necessario...
			}
			HibernateUtil.addOrderCriteria(criteria, filter.getOrders());			
		}			
		return criteria;
	}
	
	@Override
	public TPagingList<FileDocToSignBean> search(TFilterFetch<FileDocToSignBean> filter) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();			
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);			
			Long count = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();			
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;			
			TPagingList<FileDocToSignBean> paginglist = new TPagingList<FileDocToSignBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);			
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for(Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				FileDocToSignBean bean = (FileDocToSignBean) UtilPopulate.populate((TFileDocToSign) obj, FileDocToSignBean.class, new TFileDocToSignToFileDocToSignBeanConverter());
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
	public TPagingList<FileDocToSignBean> searchWithLogin(AurigaLoginBean pAurigaLoginBean, TFilterFetch<FileDocToSignBean> filter) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.search(filter);
	}
	
	@Override
	public FileDocToSignBean save(FileDocToSignBean bean) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {			
			session = HibernateUtil.begin();	
			transaction = session.beginTransaction();
			if(bean != null) {					
				String id = bean.getFileRecId();	
				if(session.get(TFileDocToSign.class, id) != null) {
					return bean;
				}
				TFileDocToSign fileDocToSign = (TFileDocToSign) UtilPopulate.populate(bean, TFileDocToSign.class, new FileDocToSignBeanToTFileDocToSignConverter(session));			
				session.save(fileDocToSign);
				bean = (FileDocToSignBean) UtilPopulate.populate(fileDocToSign, FileDocToSignBean.class, new TFileDocToSignToFileDocToSignBeanConverter());
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
	public FileDocToSignBean saveWithLogin(AurigaLoginBean pAurigaLoginBean, FileDocToSignBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.save(bean);
	}

	@Override
	public FileDocToSignBean update(FileDocToSignBean bean) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {			
			session = HibernateUtil.begin();
			transaction = session.beginTransaction();
			if(bean != null) {													
				TFileDocToSign fileDocToSign = (TFileDocToSign) UtilPopulate.populateForUpdate(session, bean, TFileDocToSign.class, new FileDocToSignBeanToTFileDocToSignConverter(session));
				session.update(fileDocToSign);	
				bean = (FileDocToSignBean) UtilPopulate.populate(fileDocToSign, FileDocToSignBean.class, new TFileDocToSignToFileDocToSignBeanConverter());
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
	public FileDocToSignBean updateWithLogin(AurigaLoginBean pAurigaLoginBean, FileDocToSignBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.update(bean);
	}

	@Override
	public void delete(FileDocToSignBean bean) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.begin();
			transaction = session.beginTransaction();
			String id = bean.getFileRecId();	
			TFileDocToSign fileDocToSign = (TFileDocToSign) session.get(TFileDocToSign.class, id);
			if(fileDocToSign != null) {
				session.delete(fileDocToSign);
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
	public void deleteWithLogin(AurigaLoginBean pAurigaLoginBean, FileDocToSignBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		this.delete(bean);
	}

	@Override
	public void forcedelete(FileDocToSignBean bean) throws Exception {
		this.delete(bean);
	}

	@Operation(name="forcedelete")	
	public void forcedeleteWithLogin(AurigaLoginBean pAurigaLoginBean, FileDocToSignBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		this.forcedelete(bean);
	}

	public FileDocToSignBean get(FileDocToSignBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();			
			String id = bean.getFileRecId();	
			TFileDocToSign fileDocToSign = (TFileDocToSign) session.get(TFileDocToSign.class, id);
			return (FileDocToSignBean) UtilPopulate.populate(fileDocToSign, FileDocToSignBean.class, new TFileDocToSignToFileDocToSignBeanConverter());
		}catch(Exception e){
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}

	@Operation(name="get")	
	public FileDocToSignBean getWithLogin(AurigaLoginBean pAurigaLoginBean, FileDocToSignBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.get(bean);
	}

}
