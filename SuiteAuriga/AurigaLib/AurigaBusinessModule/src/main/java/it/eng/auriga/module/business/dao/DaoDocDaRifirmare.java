/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.DocDaRifirmareBean;
import it.eng.auriga.module.business.dao.beansconverters.DocDaRifirmareBeanToDocDaRifirmareConverter;
import it.eng.auriga.module.business.dao.beansconverters.DocDaRifirmareToDocDaRifirmareBeanConverter;
import it.eng.auriga.module.business.entity.DocDaRifirmare;
import it.eng.auriga.module.business.entity.DocDaRifirmareId;
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

@Service(name="DaoDocDaRifirmare")
public class DaoDocDaRifirmare extends DaoGenericOperations<DocDaRifirmareBean>{
	
	private static final Logger logger = Logger.getLogger(DaoDocDaRifirmare.class);
	
	public DaoDocDaRifirmare() {
		// TODO Auto-generated constructor stub
	}	

	@Override
	public TPagingList<DocDaRifirmareBean> search(TFilterFetch<DocDaRifirmareBean> filter) throws Exception {		
		Session session = null;
		try {			
			session = HibernateUtil.begin();			
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);				
			Long count = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();			
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;			
			//Creo l'oggetto paginatore
			TPagingList<DocDaRifirmareBean> paginglist = new TPagingList<DocDaRifirmareBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);					
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for(Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				DocDaRifirmareBean bean = (DocDaRifirmareBean) UtilPopulate.populate((DocDaRifirmare) obj, DocDaRifirmareBean.class, new DocDaRifirmareToDocDaRifirmareBeanConverter());
				paginglist.addData(bean);
			}							
			return paginglist;			
		} catch(Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}		
	}
	
	@Operation(name="search")	
	public TPagingList<DocDaRifirmareBean> searchWithLogin(AurigaLoginBean pAurigaLoginBean, TFilterFetch<DocDaRifirmareBean> filter)
	throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.search(filter);
	}
	
	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<DocDaRifirmareBean> filter) throws Exception {
		Criteria criteria = session.createCriteria(DocDaRifirmare.class);
		if(filter != null) {
			DocDaRifirmareBean bean = filter.getFilter();
			if(bean != null) {						
				if(bean.getFirmatario() != null && !bean.getFirmatario().equals("")) { 
					criteria.add(Restrictions.eq("id.firmatario", bean.getFirmatario()));				        		
				}				
				criteria.add(Restrictions.isNull("tsFirmaApposta"));				        		
			}
			HibernateUtil.addOrderCriteria(criteria, filter.getOrders());			
		}			
		return criteria;		
	}
	
	public DocDaRifirmareBean get(DocDaRifirmareBean bean)	throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();					
			DocDaRifirmare lDocDaRifirmare = (DocDaRifirmare) session.get(DocDaRifirmare.class, new DocDaRifirmareId(bean.getIdDoc(), bean.getFirmatario()));
			return (DocDaRifirmareBean) UtilPopulate.populate(lDocDaRifirmare, DocDaRifirmareBean.class, new DocDaRifirmareToDocDaRifirmareBeanConverter());							
		} catch(Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name="get")	
	public DocDaRifirmareBean getWithLogin(AurigaLoginBean pAurigaLoginBean, DocDaRifirmareBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.get(bean);
	}	

	@Override
	public DocDaRifirmareBean save(DocDaRifirmareBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {			
			session = HibernateUtil.begin();	
			ltransaction = session.beginTransaction();
			if(bean != null) {					
				DocDaRifirmare lDocDaRifirmare = (DocDaRifirmare) UtilPopulate.populate(bean, DocDaRifirmare.class, new DocDaRifirmareBeanToDocDaRifirmareConverter());			
				session.save(lDocDaRifirmare);				
			}								
			session.flush();
			ltransaction.commit();
			return bean;
		} catch(Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}		
	}
	
	@Operation(name="save")	
	public DocDaRifirmareBean saveWithLogin(AurigaLoginBean pAurigaLoginBean, DocDaRifirmareBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.save(bean);
	}
	
	@Override
	public DocDaRifirmareBean update(DocDaRifirmareBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {			
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if(bean != null) {					
				DocDaRifirmare lDocDaRifirmare = (DocDaRifirmare) UtilPopulate.populate(bean, DocDaRifirmare.class, new DocDaRifirmareBeanToDocDaRifirmareConverter());			
				session.update(lDocDaRifirmare);				
			}							
			session.flush();
			ltransaction.commit();
			return bean;
		} catch(Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}		
	}
	
	@Operation(name="update")	
	public DocDaRifirmareBean updateWithLogin(AurigaLoginBean pAurigaLoginBean, DocDaRifirmareBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		return this.update(bean);
	}

	@Override
	public void delete(DocDaRifirmareBean bean) throws Exception {		
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if(bean != null) {					
				DocDaRifirmare lDocDaRifirmare = (DocDaRifirmare) UtilPopulate.populate(bean, DocDaRifirmare.class, new DocDaRifirmareBeanToDocDaRifirmareConverter());			
				session.delete(lDocDaRifirmare);				
			}				
			session.flush();
			ltransaction.commit();
		} catch(Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}				
	}
	
	@Operation(name="delete")	
	public void deleteWithLogin(AurigaLoginBean pAurigaLoginBean, DocDaRifirmareBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		this.delete(bean);
	}

	@Override
	public void forcedelete(DocDaRifirmareBean bean) throws Exception {
		this.delete(bean);	
	}
	
	@Operation(name="forcedelete")	
	public void forcedeleteWithLogin(AurigaLoginBean pAurigaLoginBean, DocDaRifirmareBean bean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject); 
		this.forcedelete(bean);
	}	

}

