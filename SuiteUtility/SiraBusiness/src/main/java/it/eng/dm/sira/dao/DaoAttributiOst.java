package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.dm.sira.entity.AttributiOst;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoAttributiOst {

	public AttributiOst save(AttributiOst bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			session.save(bean);
			session.flush();
			lTransaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<AttributiOst> search(TFilterFetch<AttributiOst> filter) throws Exception {
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
			TPagingList<AttributiOst> paginglist = new TPagingList<AttributiOst>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				paginglist.addData((AttributiOst) obj);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<AttributiOst> filter) {
		Criteria lCriteria = session.createCriteria(AttributiOst.class);
		if (filter.getFilter().getCategoria() != null) {
			lCriteria.add(Restrictions.eq("categoria", filter.getFilter().getCategoria()));
		}
		if (filter.getFilter().getNatura() != null) {
			lCriteria.add(Restrictions.eq("natura", filter.getFilter().getNatura()));
		}
		Order ordine = Order.asc("idAttributo");
		lCriteria.addOrder(ordine);
		return lCriteria;
	}

}