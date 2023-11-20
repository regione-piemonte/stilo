/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.DownloadTicketBean;
import it.eng.auriga.module.business.entity.TDownloadTicket;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Service(name="DaoDownloadTicket")
public class DaoTDownloadTicket extends DaoGenericOperations<DownloadTicketBean>{
	
	private static final Logger logger = Logger.getLogger(DaoTDownloadTicket.class);
	
	public DaoTDownloadTicket() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public DownloadTicketBean save(DownloadTicketBean bean) throws Exception {
		
		Session session = null;
		Transaction ltransaction = null;
		try {			
			session = HibernateUtil.begin();	
			ltransaction = session.beginTransaction();
			if(bean != null) {					
				TDownloadTicket lTDownloadTicket = (TDownloadTicket) UtilPopulate.populate(bean, TDownloadTicket.class, null);			
				session.save(lTDownloadTicket);				
			}								
			session.flush();
			ltransaction.commit();
			return bean;
		} catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
		
	}
	
	@Operation(name="save")	
	public DownloadTicketBean saveWithLogin(AurigaLoginBean pAurigaLoginBean, DownloadTicketBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.save(bean);
	}

	@Override
	public TPagingList<DownloadTicketBean> search(TFilterFetch<DownloadTicketBean> filter) throws Exception {
		
		Session session = null;
		try {
			
			session = HibernateUtil.begin();
			
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);	
			
			Long count = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();			
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;
			
			//Creo l'oggetto paginatore
			TPagingList<DownloadTicketBean> paginglist = new TPagingList<DownloadTicketBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);		
			
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for(Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				DownloadTicketBean bean = (DownloadTicketBean) UtilPopulate.populate((TDownloadTicket) obj, DownloadTicketBean.class, null);
				paginglist.addData(bean);
			}			
				
			return paginglist;
			
		}
		
		catch(Exception e){
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		}
		finally{
			HibernateUtil.release(session);
		}
		
	}
	
	@Operation(name="search")	
	public TPagingList<DownloadTicketBean> searchWithLogin(AurigaLoginBean pAurigaLoginBean, TFilterFetch<DownloadTicketBean> filter)
	throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.search(filter);
	}

	@Override
	public DownloadTicketBean update(DownloadTicketBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {			
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if(bean != null) {					
				TDownloadTicket lTDownloadTicket = (TDownloadTicket) UtilPopulate.populate(bean, TDownloadTicket.class, null);			
				session.update(lTDownloadTicket);				
			}							
			session.flush();
			ltransaction.commit();
			return bean;
		} catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}		
	}
	
	@Operation(name="update")	
	public DownloadTicketBean updateWithLogin(AurigaLoginBean pAurigaLoginBean, DownloadTicketBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.update(bean);
	}

	@Override
	public void delete(DownloadTicketBean bean) throws Exception {		
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if(bean != null) {					
				TDownloadTicket lTDownloadTicket = (TDownloadTicket) UtilPopulate.populate(bean, TDownloadTicket.class, null);			
				session.delete(lTDownloadTicket);				
			}				
			session.flush();
			ltransaction.commit();
		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}				
	}
	
	@Operation(name="delete")	
	public void deleteWithLogin(AurigaLoginBean pAurigaLoginBean, DownloadTicketBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		this.delete(bean);
	}

	@Override
	public void forcedelete(DownloadTicketBean bean) throws Exception {
		
		this.delete(bean);
		
	}
	
	@Operation(name="forcedelete")	
	public void forcedeleteWithLogin(AurigaLoginBean pAurigaLoginBean, DownloadTicketBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		this.forcedelete(bean);
	}
	
	public DownloadTicketBean get(DownloadTicketBean bean)	throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();			
		
			String ticketId = bean.getTicketId();

			TDownloadTicket downloadTicketTable = (TDownloadTicket) session.get(TDownloadTicket.class, ticketId);
			return (DownloadTicketBean) UtilPopulate.populate(downloadTicketTable, DownloadTicketBean.class, null);			
				
		}catch(Exception e){
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
	}

	@Operation(name="get")	
	public DownloadTicketBean getWithLogin(AurigaLoginBean pAurigaLoginBean, DownloadTicketBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.get(bean);
	}
	
	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<DownloadTicketBean> filter) throws Exception {
		
		Criteria criteria = session.createCriteria(TDownloadTicket.class);
		if(filter != null) {
			DownloadTicketBean bean = filter.getFilter();
			if(bean != null) {		
				if(bean.getTicketId() != null && !bean.getTicketId().equals("")) 
					criteria.add(Restrictions.eq("ticketId", bean.getTicketId()));				        		
				
			}
			HibernateUtil.addOrderCriteria(criteria, filter.getOrders());			
		}			
		return criteria;
		
	}

}

