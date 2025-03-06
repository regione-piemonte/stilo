package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.dm.sira.entity.ActGeBytearray;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoActGeByteArray {

	public ActGeBytearray save(ActGeBytearray bean) throws Exception {
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

	public TPagingList<ActGeBytearray> search(TFilterFetch<ActGeBytearray> filter) throws Exception {
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
			TPagingList<ActGeBytearray> paginglist = new TPagingList<ActGeBytearray>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				paginglist.addData((ActGeBytearray) obj);
			}
			//HibernateUtil.commit(session);
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<ActGeBytearray> filter) {
		Criteria lCriteria = session.createCriteria(ActGeBytearray.class);
		if (filter.getFilter().getName() != null) {
			lCriteria.add(Restrictions.eq("name", filter.getFilter().getName()));
		}
		if (filter.getFilter().getGenerated() != null) {
			lCriteria.add(Restrictions.eq("generated", filter.getFilter().getGenerated()));
		}
		if (filter.getFilter().getDeploymentId() != null) {
			lCriteria.add(Restrictions.eq("deploymentId", filter.getFilter().getDeploymentId()));
		}
		return lCriteria;
	}

}