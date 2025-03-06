package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.dm.sira.entity.TTaskDeadline;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoTTaskDeadLine {

	public TTaskDeadline save(TTaskDeadline bean) throws Exception {
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

	public TPagingList<TTaskDeadline> search(TFilterFetch<TTaskDeadline> filter) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<TTaskDeadline> paginglist = new TPagingList<TTaskDeadline>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				paginglist.addData((TTaskDeadline) obj);
			}
			HibernateUtil.commit(session);
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TTaskDeadline> filter) {
		Criteria lCriteria = session.createCriteria(TTaskDeadline.class);
		if (filter.getFilter().getProcessInstanceId() != null) {
			lCriteria.add(Restrictions.eq("processInstanceId", filter.getFilter().getProcessInstanceId()));
		}
		if (filter.getFilter().getIdTask() != null) {
			lCriteria.add(Restrictions.eq("idTask", filter.getFilter().getIdTask()));
		}
		return lCriteria;
	}
}