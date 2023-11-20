/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.NotificaSdIBean;
import it.eng.auriga.module.business.entity.TNotificheSdI;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Service(name="DaoTNotificheSdI")
public class DaoTNotificheSdI extends DaoGenericOperations<NotificaSdIBean> {

	private static final Logger logger = Logger.getLogger(DaoTNotificheSdI.class.getName());
	
	@Operation(name="search")	
	public TPagingList<NotificaSdIBean> searchWithLogin(AurigaLoginBean loginBean, TFilterFetch<NotificaSdIBean> filter) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.search(filter);
	}
	
	@Operation(name="searchNuoviJob")	
	public TPagingList<NotificaSdIBean> searchNuoviJobWithLogin(AurigaLoginBean loginBean, TFilterFetch<NotificaSdIBean> filter) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.searchNewJob(filter);
	}
	
	public TPagingList<NotificaSdIBean> searchNewJob(TFilterFetch<NotificaSdIBean> filter) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();			
			//Creo i criteri di ricerca
			Criteria criteria = session.createCriteria(TNotificheSdI.class);
			criteria.add(Restrictions.isNull("tsCarAuriga"));			
			//aggiungo una condizione per non riprocessare sempre le stesse notifiche andate in errore
			int nroGiorni = (filter != null && filter.getFilter() != null && filter.getFilter().getNroGiorni() != null) ? filter.getFilter().getNroGiorni().intValue() : 5; 
			GregorianCalendar cal = new GregorianCalendar();
			cal.add(Calendar.DATE, - nroGiorni);
			criteria.add(Restrictions.or(Restrictions.isNull("tsErrCarAuriga"), Restrictions.lt("tsErrCarAuriga", cal.getTime())));
			//Conto i record totali
			Long count = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();			
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;			
			//Creo l'oggetto paginatore
			TPagingList<NotificaSdIBean> paginglist = new TPagingList<NotificaSdIBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);			
			//Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for(Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				NotificaSdIBean bean = (NotificaSdIBean) UtilPopulate.populate((TNotificheSdI) obj, NotificaSdIBean.class);
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
	
	@Override
	public TPagingList<NotificaSdIBean> search(TFilterFetch<NotificaSdIBean> filter) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();			
			//Creo i criteri di ricerca
			Criteria criteria = session.createCriteria(TNotificheSdI.class);		
			//Conto i record totali
			Long count = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();			
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;			
			//Creo l'oggetto paginatore
			TPagingList<NotificaSdIBean> paginglist = new TPagingList<NotificaSdIBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);			
			//Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for(Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				NotificaSdIBean bean = (NotificaSdIBean) UtilPopulate.populate((TNotificheSdI) obj, NotificaSdIBean.class);
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
	
	@Operation(name="save")	
	public NotificaSdIBean saveWithLogin(AurigaLoginBean loginBean, NotificaSdIBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.save(bean);
	}

	@Override
	public NotificaSdIBean save(NotificaSdIBean bean) throws Exception {
		Session session = null;
		Transaction lTransaction = null;
		try {
			session = HibernateUtil.begin();
			lTransaction = session.beginTransaction();
			TNotificheSdI notificaSdI = (TNotificheSdI) UtilPopulate.populate(bean, TNotificheSdI.class);			
			session.save(notificaSdI);			
			session.flush();
			lTransaction.commit();	
			return bean;
		}catch(Exception e){
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}
	
	@Operation(name="update")	
	public NotificaSdIBean updateWithLogin(AurigaLoginBean loginBean, NotificaSdIBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.update(bean);
	}
	
	@Override
	public NotificaSdIBean update(NotificaSdIBean bean) throws Exception {
		Session session = null;
		Transaction lTransaction = null;
		try {			
			session = HibernateUtil.begin();	
			lTransaction = session.beginTransaction();
			if(bean != null) {													
				TNotificheSdI notificaSdI = (TNotificheSdI) UtilPopulate.populateForUpdate(session, bean, TNotificheSdI.class);
				session.update(notificaSdI);					
			}								
			session.flush();
			lTransaction.commit();
			return bean;
		} catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}
	
	@Operation(name="delete")	
	public void deleteWithLogin(AurigaLoginBean loginBean, NotificaSdIBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		this.delete(bean);
	}
	
	@Override
	public void delete(NotificaSdIBean bean) throws Exception {
		Session session = null;
		Transaction lTransaction = null;
		try {
			session = HibernateUtil.begin();
			lTransaction = session.beginTransaction();
			TNotificheSdI notificaSdI = (TNotificheSdI) session.get(TNotificheSdI.class, bean.getIdMsgNotifica());
			if(notificaSdI != null) {
				session.delete(notificaSdI);
			}
			session.flush();
			lTransaction.commit();
		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}		
	}
	
	@Override
	public void forcedelete(NotificaSdIBean bean) throws Exception {
		return;
	}

}
