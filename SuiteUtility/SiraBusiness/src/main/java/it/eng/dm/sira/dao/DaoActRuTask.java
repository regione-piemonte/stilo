package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.dm.sira.entity.ActRuTask;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoActRuTask {

	public ActRuTask save(ActRuTask bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			session.save(bean);
			session.flush();
			HibernateUtil.commit(session);
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<ActRuTask> search(TFilterFetch<ActRuTask> filter) throws Exception {
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<ActRuTask> paginglist = new TPagingList<ActRuTask>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				paginglist.addData((ActRuTask) obj);
			}
			//HibernateUtil.commit(session);
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	
	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<ActRuTask> filter) {
		Criteria lCriteria = session.createCriteria(ActRuTask.class);
		if (filter.getFilter().getProcInstId() != null) {
			lCriteria.add(Restrictions.eq("procInstId", filter.getFilter().getProcInstId()));
		}
		if (filter.getFilter().getTaskDefKey() != null) {
			lCriteria.add(Restrictions.eq("taskDefKey", filter.getFilter().getTaskDefKey()));
		}
		return lCriteria;
	}

}